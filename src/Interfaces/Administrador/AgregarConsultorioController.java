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
import modelos.Consultorio;
import modelos.Doctor;
import modelos.Elemento;
import modelos.Recepcionista;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AgregarConsultorioController implements Initializable {

    private ObservableList doctores;
    @FXML
    private Button btn_addCobertura;

    @FXML
    private Button btn_addDoctor;

    @FXML
    private Button btn_addEstudio;

    @FXML
    private Button btn_agregar;

    @FXML
    private ComboBox<Doctor> cbox_doctores;

    @FXML
    private TextField txt_coberturas;

    @FXML
    private TextField txt_estudios;

    @FXML
    private TextField txt_nombreCons;

    @FXML
    private TextField txt_precioTurno;

    private List<String> nuevasCoberturas = new ArrayList<>();

    private List<String> nuevosEstudios = new ArrayList<>();
    @FXML
    void agregarButtonClicked(ActionEvent event) {

        if(this.cbox_doctores.getSelectionModel().getSelectedItem() != null){
            Main.nuevoDoctor = Main.manager.find(Doctor.class,this.cbox_doctores.getSelectionModel().getSelectedItem());
        }
        Consultorio cons = new Consultorio(
                this.txt_nombreCons.getText(),
                Main.nuevoDoctor,
                Double.parseDouble(this.txt_precioTurno.getText())
        );

        cons.agregarNuevasCoberturas(this.nuevasCoberturas);
        cons.agregarNuevosEstudios(this.nuevosEstudios);

        if (!Main.manager.getTransaction().isActive()) {
            Main.manager.getTransaction().begin();
        }
        Main.areaSeleccionada.setComponente(cons);
        Main.manager.persist(cons);
        Main.manager.merge(Main.areaSeleccionada);
        Main.manager.getTransaction().commit();

        Main.cargarTurnos();
        Stage stage = (Stage) btn_agregar.getScene().getWindow();
        stage.close();

    }

    @FXML
    void addCobButtonClicked(ActionEvent event) {
        if(!this.txt_coberturas.getText().equals("")) {
            this.nuevasCoberturas.add(this.txt_coberturas.getText());
            this.txt_coberturas.clear();
        }
    }

    @FXML
    void addestudioButtonClicked(ActionEvent event) {
        if(this.txt_estudios.getText().equals("")) {
            this.nuevosEstudios.add(this.txt_estudios.getText());
            this.txt_estudios.clear();
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.doctores = FXCollections.observableArrayList();
        List<Doctor> docList = Main.manager.createQuery("FROM Doctor WHERE dni NOT IN (SELECT doctor FROM Consultorio )").getResultList();

        for(Doctor d:docList){
            this.doctores.add(d.getDni());
        }
        this.cbox_doctores.setItems(this.doctores);
    }
}

