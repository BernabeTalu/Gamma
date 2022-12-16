package Interfaces.Recepcionista;

import Interfaces.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Paciente;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdministrarPacientesController implements Initializable {

    private ObservableList<Paciente> pacientes;

    @FXML
    private Button btn_agregarPaciente;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_eliminarPaciente;

    @FXML
    private TableColumn<?, ?> col_apellido;

    @FXML
    private TableColumn<?, ?> col_cobertura;

    @FXML
    private TableColumn<?, ?> col_dni;

    @FXML
    private TableColumn<?, ?> col_nombre;

    @FXML
    private TableColumn<?, ?> col_telefono;

    @FXML
    private TableView<Paciente> table_pacientes;

    @FXML
    void addPacienteButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeSceneOnParent("src/Interfaces/Recepcionista/AgregarPaciente.fxml", "Nuevo Paciente");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.actualizarTabla();
    }

    @FXML
    void backButonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Recepcionista/MenuPrincipalRecepcionista.fxml", "Menu Gamma");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delPacienteButtonClicked(ActionEvent event) {
        //TODO
    }

    public void actualizarTabla() {

        List<Paciente> listaPacientes = (ArrayList<Paciente>) Main.manager.createQuery("FROM Paciente ").getResultList();
        this.pacientes = FXCollections.observableArrayList(listaPacientes);
        this.table_pacientes.setItems(this.pacientes);
        this.table_pacientes.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.col_dni.setCellValueFactory(new PropertyValueFactory<>("Dni"));
        this.col_nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        this.col_apellido.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
        this.col_telefono.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        this.col_cobertura.setCellValueFactory(new PropertyValueFactory<>("coberturaMedica"));
        this.actualizarTabla();
        table_pacientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn_eliminarPaciente.setVisible(true);
            }
        });
    }
}
