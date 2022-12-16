package Interfaces.Administrador;

import Interfaces.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.*;

import java.net.URL;
import java.util.*;

public class AgregarEmpleadoController implements Initializable {

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_verificarEmpleado;

    @FXML
    private ComboBox<Integer> cbox_area;

    @FXML
    private ComboBox<String>  cbox_tipoEmpleado;

    @FXML
    private ComboBox<Integer> cbox_consultorios;

    @FXML
    private TextField txt_DNI;

    @FXML
    private TextField txt_apellido;

    @FXML
    private TextField txt_nombre;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_sueldo;

    @FXML
    private TextField txt_telefono;

    private ObservableList<Integer> areas;

    private ObservableList<Integer> consultorios;


    @FXML
    void agregarButtonClicked(ActionEvent event) {
        if(!this.txt_DNI.getText().equals("") && !this.txt_nombre.getText().equals("") && !this.txt_telefono.getText().equals("") && !this.txt_apellido.getText().equals("") && !this.txt_password.getText().equals("") && !this.txt_sueldo.getText().equals("")) {
            if (this.cbox_tipoEmpleado.getSelectionModel().getSelectedItem() != null) {
                if (this.cbox_tipoEmpleado.getSelectionModel().getSelectedItem().equals("Doctor")) {
                    Doctor nuevoDoctor = new Doctor(
                            Integer.parseInt(this.txt_DNI.getText()),
                            this.txt_nombre.getText(),
                            this.txt_apellido.getText(),
                            Integer.parseInt(this.txt_telefono.getText()),
                            this.txt_password.getText(),
                            Double.parseDouble(this.txt_sueldo.getText()),
                            2333
                    );
                    if (Main.agregandoDoctor) {
                        Main.nuevoDoctor = nuevoDoctor;
                    }
                    if (!Main.manager.getTransaction().isActive())
                        Main.manager.getTransaction().begin();

                    Main.manager.persist(nuevoDoctor);

                    if (this.cbox_consultorios.isVisible()) {
                        if (this.cbox_consultorios.getSelectionModel().getSelectedItem() != null) { //Si elijo asignarle un consultorio al doctor.
                            Consultorio c = Main.manager.find(Consultorio.class, this.cbox_consultorios.getSelectionModel().getSelectedItem());
                            c.setDoctor(nuevoDoctor);
                            Main.manager.merge(c);
                        }
                    }
                    Main.manager.getTransaction().commit();
                } else {
                    if (this.cbox_tipoEmpleado.getSelectionModel().getSelectedItem().equals("Recepcionista")) {
                        Recepcionista nuevoRecepcionista = new Recepcionista(
                                Integer.parseInt(this.txt_DNI.getText()),
                                this.txt_nombre.getText(),
                                this.txt_apellido.getText(),
                                Integer.parseInt(this.txt_telefono.getText()),
                                this.txt_password.getText(),
                                Double.parseDouble(this.txt_sueldo.getText())
                        );
                        if (Main.agregandoRecepcionista) {
                            Main.nuevoRecepcionista = nuevoRecepcionista;
                        }
                        if (!Main.manager.getTransaction().isActive()) {
                            Main.manager.getTransaction().begin();
                        }
                        Main.manager.persist(nuevoRecepcionista);

                        if (this.cbox_area.isVisible()) {
                            if (this.cbox_area.getSelectionModel().getSelectedItem() != null) { //Si elijo asignarle un area al recepcionista.
                                Area a = Main.manager.find(Area.class, this.cbox_area.getSelectionModel().getSelectedItem());
                                a.setRecepcionista(nuevoRecepcionista);
                                Main.manager.merge(a);
                            }
                        }
                        Main.manager.getTransaction().commit();
                    }
                }
                Stage stage = (Stage) btn_agregar.getScene().getWindow();
                stage.close();
            } else {
                Main m = new Main();
                m.sendAlert(Alert.AlertType.ERROR, "Error en seleccion de tipo", "Debe elegir el tipo de empleado a añadir.Intentelo de nuevo");
            }
        }
        else {
            Main m = new Main();
            m.sendAlert(Alert.AlertType.ERROR, "Campos incompletos", "Faltan llenar algunos campos del empleado.");
        }
    }

    @FXML
    void verificarEmpleadoButtonClicked(ActionEvent event) {
        if (this.cbox_tipoEmpleado.getSelectionModel().getSelectedItem().equals("Doctor")) {
            if(!Main.agregandoDoctor)
                this.cbox_consultorios.setVisible(true);
        }
        else if (this.cbox_tipoEmpleado.getSelectionModel().getSelectedItem().equals("Recepcionista")) {
            if(!Main.agregandoRecepcionista)
                this.cbox_area.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.areas = FXCollections.observableArrayList();
        List<Area> areasDisponibles = Main.manager.createQuery("FROM Area WHERE idRecepcionista is null").getResultList();

        for(Area a:areasDisponibles)
            this.areas.add(a.getId());
        this.cbox_area.setItems(this.areas);

        ObservableList<String> tipos;
        List<String> tiposEmpleado = new ArrayList<String>() {{ add("Recepcionista"); add("Doctor"); }};
        tipos = FXCollections.observableArrayList();
        tipos.addAll(tiposEmpleado);
        this.cbox_tipoEmpleado.setItems(tipos);

        this.consultorios = FXCollections.observableArrayList();
        List<Consultorio> consultoriosSinDoctor = Main.manager.createQuery("FROM Consultorio  where doctor is null").getResultList();
        for(Consultorio c :consultoriosSinDoctor)
            this.consultorios.add(c.getId());
        this.cbox_consultorios.setItems(this.consultorios);

        if(Main.agregandoDoctor){
            this.cbox_consultorios.setVisible(false);
        }

        if(Main.agregandoRecepcionista){
            this.cbox_area.setVisible(false);
        }

    }
}
