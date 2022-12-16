package Interfaces.Recepcionista;

import Interfaces.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import modelos.*;
import modelos.FiltrosTurnos.FiltroAnd;
import modelos.FiltrosTurnos.FiltroAsignado;
import modelos.FiltrosTurnos.FiltroConsultorio;
import modelos.FiltrosTurnos.FiltroFechaMayor;


import javax.persistence.Query;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TurnosController implements Initializable {

    private ObservableList<Turno> turnos;
    private ObservableList<Area> areas;
    private ObservableList<Consultorio> consultorios;
    private Area area;
    private FiltroAnd filtroAnd;
    private FiltroConsultorio filtroConsultorio;

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private Button btn_pagar;

    @FXML
    private Button btn_reset;

    @FXML
    private ComboBox<Area> cb_area;

    @FXML
    private ComboBox<Consultorio> cb_consultorio;

    @FXML
    private TableColumn<?, ?> col_doctor;

    @FXML
    private TableColumn<?, ?> col_consultorio;

    @FXML
    private TableColumn<?, ?> col_fecha;

    @FXML
    private TableColumn<?, ?> col_hora;

    @FXML
    private TableColumn<?, ?> col_paciente;

    @FXML
    private TableColumn<?, ?> col_pago;

    @FXML
    private TableColumn<?, ?> col_precio;

    @FXML
    private TableView table_turnos;

    @FXML
    private Button btn_atras;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.col_fecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        this.col_hora.setCellValueFactory(new PropertyValueFactory<>("Hora"));
        this.col_paciente.setCellValueFactory(new PropertyValueFactory<>("Paciente"));
        this.col_pago.setCellValueFactory(new PropertyValueFactory<>("Pagado"));
        this.col_precio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        this.col_doctor.setCellValueFactory(new PropertyValueFactory<>("Doctor"));
        this.col_consultorio.setCellValueFactory(new PropertyValueFactory<>("Consultorio"));

        this.filtroConsultorio = new FiltroConsultorio();
        this.filtroAnd = new FiltroAnd();
        this.filtroAnd.agregarFiltro(new FiltroAsignado(true));
        this.filtroAnd.agregarFiltro(new FiltroFechaMayor(LocalDate.now(), LocalTime.now()));

        StringConverter<Area> converterArea = new StringConverter<Area>() {
            @Override
            public String toString(Area area) {
                return area.getNombre();
            }

            @Override
            public Area fromString(String string) {
                return null;
            }
        };
        this.cb_area.setConverter(converterArea);

        StringConverter<Consultorio> converterConsultorio = new StringConverter<Consultorio>() {
            @Override
            public String toString(Consultorio consultorio) {
                return consultorio.getNombre();
            }

            @Override
            public Consultorio fromString(String string) {
                return null;
            }
        };
        this.cb_consultorio.setConverter(converterConsultorio);

        areas = FXCollections.observableArrayList();
        areas.addAll((ArrayList<Area>) Main.manager.createQuery("FROM Area WHERE idRecepcionista =" +Main.usuarioLogeado.getDni()).getResultList());
        this.cb_area.setItems(areas);

        table_turnos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn_eliminar.setVisible(true);
                btn_pagar.setVisible(true);
            }
        });

        cb_area.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarTabla();
                actualizarConsultorios();
            }
        });

        cb_consultorio.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (this.filtroAnd.contieneFiltro(filtroConsultorio)) {
                    this.filtroAnd.eliminarFiltro(filtroConsultorio);
                    this.filtroConsultorio.setConsultorio(cb_consultorio.getSelectionModel().getSelectedItem());
                    this.filtroAnd.agregarFiltro(filtroConsultorio);
                } else {
                    this.filtroConsultorio.setConsultorio(cb_consultorio.getSelectionModel().getSelectedItem());
                    this.filtroAnd.agregarFiltro(filtroConsultorio);
                }

            }
            actualizarTabla();
        });

        actualizarTabla();
    }

    private void actualizarConsultorios() {
        Area area =  cb_area.getSelectionModel().getSelectedItem();
        consultorios = FXCollections.observableArrayList();
        consultorios.addAll(area.getConsultorios());
        this.cb_consultorio.setItems(consultorios);
    }

    public void actualizarTabla() {
        List<Turno> listaTurnos;

        if(this.cb_area.getSelectionModel().getSelectedItem() == null){
            this.table_turnos.setItems(null);
        }
        else{

                listaTurnos = this.cb_area.getSelectionModel().getSelectedItem().getTurnosFiltrados(this.filtroAnd);
                this.turnos = FXCollections.observableArrayList(listaTurnos);
                this.table_turnos.setItems(this.turnos);


        }

        this.table_turnos.refresh();
    }

    @FXML
    void agregarButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("src/Interfaces/Recepcionista/TurnosDisponibles.fxml", "Menu Gamma");
    }

    @FXML
    void eliminarButtonClicked(ActionEvent event) throws IOException {
        if (table_turnos.getSelectionModel().getSelectedItem() != null) {
            Turno turnoSeleccionado = (Turno) this.table_turnos.getSelectionModel().getSelectedItem();


            if (!Main.manager.getTransaction().isActive())
                Main.manager.getTransaction().begin();

            turnoSeleccionado.setAsignado(false);
            turnoSeleccionado.setPaciente(null);
            Main.manager.merge(turnoSeleccionado);
            Main.manager.getTransaction().commit();
        }
        else{
            Main m = new Main();
            m.sendAlert(Alert.AlertType.ERROR,"No selecciono turno","No se selecciono un turno. Inténtelo de nuevo");
        }
        this.actualizarTabla();
    }

    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("src/Interfaces/Recepcionista/MenuPrincipalRecepcionista.fxml", "Menu Gamma");
    }

    @FXML
    void resetButtonClicked(ActionEvent event) {
        this.cb_area.getSelectionModel().clearSelection();
        this.cb_consultorio.getSelectionModel().clearSelection();
        this.filtroAnd.removerFiltros();
        this.filtroAnd.agregarFiltro(new FiltroAsignado(true));
        this.actualizarTabla();
    }

    @FXML
    void pagarButtonClicked(ActionEvent event) {
        if (!Main.manager.getTransaction().isActive()) {
            Main.manager.getTransaction().begin();
        }

        Turno turno = (Turno)this.table_turnos.getSelectionModel().getSelectedItem();
        turno.setPagado(true);
        turno.getConsultorio().setGananciaMensual(turno.getPrecioTurno());
        Main.manager.merge(turno);
        Main.manager.getTransaction().commit();
        this.actualizarTabla();
    }

}
