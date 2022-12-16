package Interfaces.Doctor;

import Interfaces.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import modelos.Consulta;
import modelos.Paciente;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListadoConsultasController implements Initializable {

    private ObservableList<Consulta> consultas;

    @FXML
    private Button btn_back;

    @FXML
    private TableColumn<?, ?> col_Fecha;

    @FXML
    private TableColumn<?, ?> col_dniPaciente;

    @FXML
    private TableColumn<?, ?> col_idConsulta;

    @FXML
    private TableColumn<?, ?> col_Doctor;

    @FXML
    private TableView<Consulta> table_consultas;

    @FXML
    private TextArea txt_detalles;

    @FXML
    void backButonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Doctor/Pacientes.fxml", "Listado de Pacientes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarTabla() {

        List<Consulta> listaConsulta = (ArrayList<Consulta>) Main.manager.createQuery("FROM Consulta where dni_paciente ="+Main.pacienteConsultado.getDni()).getResultList();
        this.consultas = FXCollections.observableArrayList(listaConsulta);
        this.table_consultas.setItems(this.consultas);
        this.table_consultas.refresh();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.col_idConsulta.setCellValueFactory(new PropertyValueFactory<>("idConsulta"));
        this.col_dniPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        this.col_Fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        this.col_Doctor.setCellValueFactory(new PropertyValueFactory<>("dniDoctor"));
        this.actualizarTabla();
        table_consultas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Consulta seleccion = (Consulta) table_consultas.getSelectionModel().getSelectedItem();
                this.txt_detalles.setText(seleccion.getDescripcion());
            }
        });
    }

    @FXML
    void agregarButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeSceneOnParent("src/Interfaces/Doctor/AgregarObservacion.fxml", "Nueva Observación");
        this.actualizarTabla();
    }
}