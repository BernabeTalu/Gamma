package Interfaces.Administrador;

import Interfaces.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.Consultorio;
import modelos.Doctor;
import modelos.Elemento;


public class AgregarConsultorioController {

    @FXML
    private Button btn_addCobertura;

    @FXML
    private Button btn_addDoctor;

    @FXML
    private Button btn_addEstudio;

    @FXML
    private Button btn_agregar;

    @FXML
    private ComboBox<?> cbox_doctores;

    @FXML
    private TextField txt_IdCons;

    @FXML
    private TextField txt_coberturas;

    @FXML
    private TextField txt_estudios;

    @FXML
    private TextField txt_nombreCons;

    @FXML
    private TextField txt_precioTurno;

    @FXML
    void agregarButtonClicked(ActionEvent event) {

        if(this.cbox_doctores.getSelectionModel().getSelectedItem() != null){
            Main.nuevoDoctor = Main.manager.find(Doctor.class,this.cbox_doctores.getSelectionModel().getSelectedItem());
        }
        Elemento c = Main.manager.find(Elemento.class,Integer.parseInt(this.txt_IdCons.getText()));
        if(c == null) {

            Consultorio cons = new Consultorio(
                    Integer.parseInt(this.txt_IdCons.getText()),
                    this.txt_nombreCons.getText(),
                    Main.nuevoDoctor,
                    Double.parseDouble(this.txt_precioTurno.getText())
            );

            if (!Main.manager.getTransaction().isActive())
                Main.manager.getTransaction().begin();
            Main.areaSeleccionada.setComponente(cons);
            Main.manager.persist(cons);
            Main.manager.merge(Main.areaSeleccionada);
            Main.manager.getTransaction().commit();

            Main.cargarTurnos();
            Stage stage = (Stage) btn_agregar.getScene().getWindow();
            stage.close();
        }
        else{
            Main m = new Main();
            m.sendAlert(Alert.AlertType.ERROR,"Error de identificador","Ya existe un elemento con ese identificador presente en el sistema. Vuelva a ingresar los datos");
        }
    }

    @FXML
    void addCobButtonClicked(ActionEvent event) {

    }

    @FXML
    void addestudioButtonClicked(ActionEvent event) {

    }

    @FXML
    void addDoctorButtonClicked(ActionEvent event) {
        Main.agregandoDoctor = true;
        Main m = new Main();
        try {
            m.changeSceneOnParent("src/Interfaces/Administrador/AgregarEmpleado.fxml", "Nuevo Doctor");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.agregandoDoctor = false;
    }

}

