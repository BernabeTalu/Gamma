package Interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.Area;
import modelos.Doctor;
import modelos.Empleado;
import modelos.Recepcionista;

import java.net.URL;
import java.util.*;

public class AgregarEmpleadoController implements Initializable {

    @FXML
    private Button btn_agregar;

    @FXML
    private ComboBox<String> cbox_area;

    @FXML
    private ComboBox<String>  cbox_tipoEmpleado;

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

    private ObservableList<String> areas;


    @FXML
    void agregarButtonClicked(ActionEvent event) {
        if (this.cbox_tipoEmpleado.getSelectionModel().getSelectedItem().equals("Doctor")){
            Doctor nuevoDoctor = new Doctor(
                    Integer.parseInt(this.txt_DNI.getText()),
                    this.txt_nombre.getText(),
                    this.txt_apellido.getText(),
                    Integer.parseInt(this.txt_telefono.getText()),
                    this.txt_password.getText(),
                    Double.parseDouble(this.txt_sueldo.getText()),
                    2333
            );

            if (!Main.manager.getTransaction().isActive())
                Main.manager.getTransaction().begin();

            Main.manager.persist(nuevoDoctor);
            Main.manager.getTransaction().commit();
        }
        else {
            Recepcionista nuevoRecepcionista = new Recepcionista(
                    Integer.parseInt(this.txt_DNI.getText()),
                    this.txt_nombre.getText(),
                    this.txt_apellido.getText(),
                    Integer.parseInt(this.txt_telefono.getText()),
                    this.txt_password.getText(),
                    Double.parseDouble(this.txt_sueldo.getText())

            );
            if (!Main.manager.getTransaction().isActive())
                Main.manager.getTransaction().begin();

            Main.manager.persist(nuevoRecepcionista);
            Main.manager.getTransaction().commit();
        }


        Stage stage = (Stage) btn_agregar.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.areas = FXCollections.observableArrayList();
        List<Area> areasDisponibles = Main.manager.createQuery("FROM Area WHERE idRecepcionista is null").getResultList();

        for(Area a:areasDisponibles)
            this.areas.add(a.getNombre());
        this.cbox_area.setItems(this.areas);

        ObservableList<String> tipos;
        List<String> tiposEmpleado = new ArrayList<String>() {{ add("Recepcionista"); add("Doctor"); }};
        tipos = FXCollections.observableArrayList();
        tipos.addAll(tiposEmpleado);
        this.cbox_tipoEmpleado.setItems(tipos);

    }
}
