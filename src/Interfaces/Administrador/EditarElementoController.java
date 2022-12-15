package Interfaces.Administrador;

import Interfaces.Main;
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
import modelos.Consultorio;
import modelos.Doctor;
import modelos.Recepcionista;

import javax.persistence.criteria.CriteriaBuilder;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditarElementoController implements Initializable {

    @FXML
    private Button btn_addCobertura;

    @FXML
    private Button btn_addDoctor;

    @FXML
    private Button btn_addEstudio;

    @FXML
    private Button btn_addRec;

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_verificarSeleccion;

    @FXML
    private ComboBox<Integer> cbox_doctores;

    @FXML
    private ComboBox<Integer> cbox_recepcionistas;

    @FXML
    private TextField txt_coberturas;

    @FXML
    private TextField txt_nombreArea;

    @FXML
    private TextField txt_estudios;

    @FXML
    private TextField txt_nombreCons;

    @FXML
    private TextField txt_precioTurno;

    @FXML
    private ComboBox<String> cbox_seleccionAtributos;

    private List<String> nuevasCoberturas = new ArrayList<>();

    private List<String> nuevosEstudios = new ArrayList<>();

    private String seleccion;

    @FXML
    void actualizarButtonClicked(ActionEvent event) {
        if(Main.editandoConsultorio) {
            switch (this.seleccion) {
                case "NombreConsultorio":
                    if (!this.txt_nombreCons.getText().equals("")) {
                        if (!Main.manager.getTransaction().isActive())
                            Main.manager.getTransaction().begin();
                        Main.consultorioSeleccionado.setNombre(this.txt_nombreCons.getText());
                        Main.manager.merge(Main.consultorioSeleccionado);
                        Main.manager.getTransaction().commit();
                    }
                    this.txt_nombreCons.setVisible(false);
                    this.seleccion = null;
                    break;
                case "Precio":
                    if (!this.txt_precioTurno.getText().equals("")) {
                        if (!Main.manager.getTransaction().isActive())
                            Main.manager.getTransaction().begin();
                        Main.consultorioSeleccionado.setPrecioTurno(Double.parseDouble(this.txt_precioTurno.getText()));
                        Main.manager.merge(Main.consultorioSeleccionado);
                        Main.manager.getTransaction().commit();
                    }
                    this.txt_precioTurno.setVisible(false);
                    this.seleccion = null;
                    break;
                case "Coberturas":
                    if (!this.nuevasCoberturas.isEmpty()) {
                        if (!Main.manager.getTransaction().isActive())
                            Main.manager.getTransaction().begin();
                        Main.consultorioSeleccionado.agregarNuevasCoberturas(this.nuevasCoberturas);
                        Main.manager.merge(Main.consultorioSeleccionado);
                        Main.manager.getTransaction().commit();
                    }
                    this.txt_coberturas.setVisible(false);
                    this.btn_addCobertura.setVisible(false);
                    this.seleccion = null;
                    break;
                case "Estudios":
                    if (!this.nuevosEstudios.isEmpty()) {
                        if (!Main.manager.getTransaction().isActive())
                            Main.manager.getTransaction().begin();
                        Main.consultorioSeleccionado.agregarNuevosEstudios(this.nuevosEstudios);
                        Main.manager.merge(Main.consultorioSeleccionado);
                        Main.manager.getTransaction().commit();
                    }
                    this.txt_estudios.setVisible(false);
                    this.btn_addEstudio.setVisible(false);
                    this.seleccion = null;
                    break;
                case "Doctor":
                    if (this.cbox_doctores.getSelectionModel().getSelectedItem() != null)
                        Main.nuevoDoctor = Main.manager.find(Doctor.class, this.cbox_doctores.getSelectionModel().getSelectedItem());
                    if(Main.consultorioSeleccionado.getDoctor() != Main.nuevoDoctor) {
                        if (!Main.manager.getTransaction().isActive())
                            Main.manager.getTransaction().begin();
                        Main.consultorioSeleccionado.setDoctor(Main.nuevoDoctor);
                        Main.manager.merge(Main.consultorioSeleccionado);
                        Main.manager.getTransaction().commit();
                    }
                    this.cbox_doctores.setVisible(false);
                    this.btn_addDoctor.setVisible(false);
                    this.seleccion = null;
                    break;
            }
        }
        else if(Main.editandoArea){
            switch (this.seleccion) {
                case "NombreArea":
                    if (!this.txt_nombreArea.getText().equals("")) {
                        if (!Main.manager.getTransaction().isActive())
                            Main.manager.getTransaction().begin();
                        Main.areaSeleccionada.setNombre(this.txt_nombreArea.getText());
                        Main.manager.merge(Main.areaSeleccionada);
                        Main.manager.getTransaction().commit();
                    }
                    this.txt_nombreArea.setVisible(false);
                    this.seleccion = null;
                    break;
                case "Recepcionista":
                    if (this.cbox_recepcionistas.getSelectionModel().getSelectedItem() != null)
                        Main.nuevoRecepcionista = Main.manager.find(Recepcionista.class, this.cbox_recepcionistas.getSelectionModel().getSelectedItem());
                    if(Main.areaSeleccionada.getRecepcionista() != Main.nuevoRecepcionista) {
                        if (!Main.manager.getTransaction().isActive())
                            Main.manager.getTransaction().begin();
                        Main.areaSeleccionada.setRecepcionista(Main.nuevoRecepcionista);
                        Main.manager.merge(Main.areaSeleccionada);
                        Main.manager.getTransaction().commit();
                    }
                    this.cbox_recepcionistas.setVisible(false);
                    this.btn_addRec.setVisible(false);
                    this.seleccion = null;
                    break;
            }
        }
        Stage stage = (Stage) btn_agregar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void verificarSeleccionButtonClicked(ActionEvent event) {
        this.txt_nombreCons.setVisible(false);
        this.txt_precioTurno.setVisible(false);
        this.txt_coberturas.setVisible(false);
        this.btn_addCobertura.setVisible(false);
        this.txt_estudios.setVisible(false);
        this.btn_addEstudio.setVisible(false);
        this.cbox_doctores.setVisible(false);
        this.btn_addDoctor.setVisible(false);
        if (this.cbox_seleccionAtributos.getSelectionModel().getSelectedItem() != null) {
            this.seleccion = this.cbox_seleccionAtributos.getSelectionModel().getSelectedItem();
            if (Main.editandoConsultorio) {
                switch (this.seleccion) {
                    case "NombreConsultorio":
                        System.out.println("nombre consul");
                        this.txt_nombreCons.setVisible(true);
                        break;
                    case "Precio":
                        this.txt_precioTurno.setVisible(true);
                        break;
                    case "Coberturas":
                        this.txt_coberturas.setVisible(true);
                        this.btn_addCobertura.setVisible(true);
                        break;
                    case "Estudios":
                        this.txt_estudios.setVisible(true);
                        this.btn_addEstudio.setVisible(true);
                        break;
                    case "Doctor":
                        this.cbox_doctores.setVisible(true);
                        this.btn_addDoctor.setVisible(true);
                        break;
                }
            } else {
                if (Main.editandoArea) {
                    switch (this.seleccion) {
                        case "NombreArea":
                            this.txt_nombreArea.setVisible(true);
                            break;
                        case "Recepcionista":
                            this.cbox_recepcionistas.setVisible(true);
                            this.btn_addRec.setVisible(true);
                            break;
                    }
                }
            }
        }
    }

    @FXML
    void addCobButtonClicked(ActionEvent event) {
        if(!this.txt_coberturas.getText().equals("")) {
            this.nuevasCoberturas.add(this.txt_coberturas.getText());
            this.txt_coberturas.clear();
            System.out.println(nuevasCoberturas);
        }
    }

    @FXML
    void addestudioButtonClicked(ActionEvent event) {
        if(!this.txt_estudios.getText().equals("")) {
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

    @FXML
    void addRecButtonClicked(ActionEvent event) {
        Main.agregandoRecepcionista = true;
        Main m = new Main();
        try {
            m.changeSceneOnParent("src/Interfaces/Administrador/AgregarEmpleado.fxml", "Nuevo Recepcionista");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.agregandoRecepcionista = false;
        //this.txt_recepCreado.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> atributosConsultorio = null;
        List<String> tiposAtributosConsultorio = new ArrayList<String>() {{ add("NombreConsultorio"); add("Precio");  add("Coberturas");  add("Estudios"); add("Doctor");  }};
        ObservableList<String> atributosArea = null;
        List<String> tiposAtributosArea = new ArrayList<String>() {{ add("NombreArea"); add("Recepcionista"); }};
        atributosArea = FXCollections.observableArrayList();
        atributosConsultorio = FXCollections.observableArrayList();
        atributosArea.addAll(tiposAtributosArea);
        atributosConsultorio.addAll(tiposAtributosConsultorio);

        if(Main.editandoArea)
            this.cbox_seleccionAtributos.setItems(atributosArea);
        else if(Main.editandoConsultorio)
            this.cbox_seleccionAtributos.setItems(atributosConsultorio);

        ObservableList<Integer> recepcionistas = FXCollections.observableArrayList();
        List<Recepcionista> recepcionistasLibres = Main.manager.createQuery("FROM Recepcionista WHERE dni NOT IN (SELECT recepcionista FROM Area )").getResultList();

        for(Recepcionista r:recepcionistasLibres){
            recepcionistas.add(r.getDni());
        }
        this.cbox_recepcionistas.setItems(recepcionistas);

        ObservableList<Integer> consultorios = FXCollections.observableArrayList();
        List<Consultorio> consultoriosSinDoctor = Main.manager.createQuery("FROM Doctor WHERE dni NOT IN (SELECT doctor FROM Consultorio )").getResultList();
        for(Consultorio c :consultoriosSinDoctor)
            consultorios.add(c.getId());
        this.cbox_doctores.setItems(consultorios);
    }
}
