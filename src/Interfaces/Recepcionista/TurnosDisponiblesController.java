package Interfaces.Recepcionista;

import Interfaces.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import modelos.Area;
import modelos.Consultorio;
import modelos.FiltrosTurnos.Filtro;
import modelos.FiltrosTurnos.FiltroAnd;
import modelos.FiltrosTurnos.FiltroEstudio;
import modelos.Turno;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TurnosDisponiblesController implements Initializable {

    private ObservableList<Turno> turnos;
    private ObservableList<Area> areas;
    private ObservableList<Consultorio> consultorios;

    private FiltroAnd filtroAnd;

    @FXML
    private Button btn_atras;

    @FXML
    private Button btn_seleccionar;

    @FXML
    private ComboBox<Area> cb_area;

    @FXML
    private ComboBox<Consultorio> cb_consultorio;

    @FXML
    private ComboBox<?> cb_doctor;

    @FXML
    private ComboBox<?> cb_fecha;

    @FXML
    private ComboBox<?> cb_hora;

    @FXML
    private ComboBox<String> cb_estudio;

    @FXML
    private ComboBox<?> cb_obrasocial;

    @FXML
    private ComboBox<?> cb_precio;

    @FXML
    private TableColumn<?, ?> col_consultorio;

    @FXML
    private TableColumn<?, ?> col_doctor;

    @FXML
    private TableColumn<?, ?> col_fecha;

    @FXML
    private TableColumn<?, ?> col_hora;

    @FXML
    private TableColumn<?, ?> col_precio;

    @FXML
    private TableView table_turnos;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.col_fecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        this.col_hora.setCellValueFactory(new PropertyValueFactory<>("Hora"));
        this.col_precio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        this.col_doctor.setCellValueFactory(new PropertyValueFactory<>("Doctor"));
        this.col_consultorio.setCellValueFactory(new PropertyValueFactory<>("Consultorio"));


        this.filtroAnd = new FiltroAnd();

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

        this.areas = FXCollections.observableArrayList();
        this.areas.addAll((ArrayList<Area>) Main.manager.createQuery("FROM Area WHERE idRecepcionista =" +Main.usuarioLogeado.getDni()).getResultList());
        this.cb_area.setItems(this.areas);

        this.cb_area.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                actualizarTabla();
                actualizarConsultorios();
            }
        });

        this.cb_consultorio.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarTabla();

            }
        });

        this.cb_fecha.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarTabla();

            }
        });

        this.cb_hora.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarTabla();

            }
        });

        this.cb_doctor.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarTabla();

            }
        });

        this.cb_estudio.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            FiltroEstudio filtroEstudio = new FiltroEstudio(cb_estudio.getSelectionModel().getSelectedItem());
            if (newSelection != null) {
                this.filtroAnd.agregarFiltro(filtroEstudio);
            }
            else{
                this.filtroAnd.eliminarFiltro(filtroEstudio);
            }
            actualizarTabla();
        });

        this.cb_obrasocial.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarTabla();
                actualizarConsultorios();
            }
        });


        actualizarTabla();

        table_turnos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn_seleccionar.setVisible(true);
            }
        });
    }

    private void actualizarTabla() {
        List<Turno> listaTurnos;
        if(this.cb_area.getSelectionModel().getSelectedItem() == null){
            listaTurnos = (ArrayList<Turno>) Main.manager.createQuery("FROM Turno").getResultList();
            this.turnos = FXCollections.observableArrayList(listaTurnos);
            this.table_turnos.setItems(this.turnos);
            this.table_turnos.refresh();
        }
    }

    private void actualizarConsultorios() {
        Area area =  cb_area.getSelectionModel().getSelectedItem();
        consultorios = FXCollections.observableArrayList();
        consultorios.addAll(area.getConsultorios());
        this.cb_consultorio.setItems(consultorios);

    }



    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("src/Interfaces/Recepcionista/Turnos.fxml", "Turnos");
    }

    @FXML
    void seleccionarButtonClicked(ActionEvent event) throws IOException {
        Main.turnoActual = (Turno) table_turnos.getSelectionModel().getSelectedItem();
        Main m = new Main();
        m.changeSceneOnParent("src/Interfaces/Recepcionista/AgregarTurno.fxml", "Asignar Turno");
    }


}
