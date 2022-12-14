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
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Doctor;
import modelos.FiltrosTurnos.FiltroAnd;
import modelos.FiltrosTurnos.FiltroAsignado;
import modelos.FiltrosTurnos.FiltroFechaMayor;
import modelos.Turno;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class TurnosDoctorController implements Initializable {

    private ObservableList<Turno> turnos;

    @FXML
    private Button btn_atras;

    @FXML
    private TableColumn<?, ?> col_consultorio;

    @FXML
    private TableColumn<?, ?> col_fecha;

    @FXML
    private TableColumn<?, ?> col_hora;

    @FXML
    private TableColumn<?, ?> col_paciente;

    @FXML
    private TableView table_turnos;

    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("src/Interfaces/Doctor/MenuPrincipalDoctor.fxml", "Menu Gamma");
    }


    private FiltroAnd filtroAnd;

    public void actualizarTabla() {


        Doctor doc = Main.manager.find(Doctor.class,Main.usuarioLogeado.getDni());
        if(doc.getConsultorio() != null) {
            List<Turno> listaTurnos = doc.getConsultorio().getTurnosFiltrados(this.filtroAnd);
            turnos = FXCollections.observableArrayList();
            turnos.addAll(listaTurnos);
            this.table_turnos.setItems(turnos);
            this.table_turnos.refresh();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.col_fecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        this.col_hora.setCellValueFactory(new PropertyValueFactory<>("Hora"));
        this.col_consultorio.setCellValueFactory(new PropertyValueFactory<>("Consultorio"));
        this.col_paciente.setCellValueFactory(new PropertyValueFactory<>("Paciente"));
        this.filtroAnd = new FiltroAnd();
        this.filtroAnd.agregarFiltro(new FiltroAsignado(true));
        this.filtroAnd.agregarFiltro(new FiltroFechaMayor(LocalDate.now(), LocalTime.now()));
        this.actualizarTabla();
    }
}
