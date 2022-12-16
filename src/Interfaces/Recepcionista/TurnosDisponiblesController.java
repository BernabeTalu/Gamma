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
import javafx.util.converter.LocalTimeStringConverter;
import modelos.Area;
import modelos.Consultorio;
import modelos.Doctor;
import modelos.FiltrosTurnos.*;
import modelos.Turno;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TurnosDisponiblesController implements Initializable {

    private ObservableList<Turno> turnos;
    private ObservableList<Area> areas;
    private ObservableList<Consultorio> consultorios;
    private ObservableList<LocalTime> horarios;
    private ObservableList<String> estudios;
    private ObservableList<String> coberturas;
    private ObservableList<Doctor> doctores;


    private FiltroAnd filtroAnd;
    private FiltroConsultorio filtroConsultorio;
    private FiltroFecha filtroFecha;
    private FiltroHora filtroHora;
    private FiltroEstudio filtroEstudio;
    private FiltroCobertura filtroObrasocial;
    private FiltroDoctor filtroDoctor;

    @FXML
    private Button btn_reset;

    @FXML
    private Button btn_atras;

    @FXML
    private Button btn_seleccionar;

    @FXML
    private ComboBox<Area> cb_area;

    @FXML
    private ComboBox<Consultorio> cb_consultorio;

    @FXML
    private ComboBox<Doctor> cb_doctor;

    @FXML
    private DatePicker dp_fecha;

    @FXML
    private ComboBox<LocalTime> cb_hora;

    @FXML
    private ComboBox<String> cb_estudio;

    @FXML
    private ComboBox<String> cb_obrasocial;



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
        this.filtroConsultorio = new FiltroConsultorio();
        this.filtroFecha = new FiltroFecha();
        this.filtroHora = new FiltroHora();
        this.filtroEstudio = new FiltroEstudio();
        this.filtroObrasocial = new FiltroCobertura();
        this.filtroDoctor = new FiltroDoctor();
        this.filtroAnd.agregarFiltro(new FiltroAsignado(false));
        this.filtroAnd.agregarFiltro(new FiltroFechaMayor(LocalDate.now(), LocalTime.now()));

        this.cargarHorarios();
        this.cargarCoberturas();
        this.cargarEstudios();
        this.cargarDoctores();
        this.actualizarTabla();

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


        StringConverter<Doctor> converterDoctor = new StringConverter<Doctor>() {
            @Override
            public String toString(Doctor doctor) {
                return doctor.getNombre() +" "+ doctor.getApellido();
            }

            @Override
            public Doctor fromString(String string) {
                return null;
            }
        };
        this.cb_doctor.setConverter(converterDoctor);


        //Cargar areas
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
                    if (this.filtroAnd.contieneFiltro(filtroConsultorio)) {
                        this.filtroAnd.eliminarFiltro(filtroConsultorio);
                        this.filtroConsultorio.setConsultorio(cb_consultorio.getSelectionModel().getSelectedItem());
                        this.filtroAnd.agregarFiltro(filtroConsultorio);
                    } else {
                        this.filtroConsultorio.setConsultorio(cb_consultorio.getSelectionModel().getSelectedItem());
                        this.filtroAnd.agregarFiltro(filtroConsultorio);
                    }
                actualizarTabla();

            }
        });

        this.dp_fecha.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(newValue != null){
                if (this.filtroAnd.contieneFiltro(filtroFecha)) {
                    this.filtroAnd.eliminarFiltro(filtroFecha);
                    this.filtroFecha.setFecha(dp_fecha.getValue());
                    this.filtroAnd.agregarFiltro(filtroFecha);
                } else {
                    this.filtroFecha.setFecha(dp_fecha.getValue());
                    this.filtroAnd.agregarFiltro(filtroFecha);
                }
                actualizarTabla();
            }
        });


        this.cb_hora.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (this.filtroAnd.contieneFiltro(filtroHora)) {
                    this.filtroAnd.eliminarFiltro(filtroHora);
                    this.filtroHora.setHora(cb_hora.getSelectionModel().getSelectedItem());
                    this.filtroAnd.agregarFiltro(filtroHora);
                } else {
                    this.filtroHora.setHora(cb_hora.getSelectionModel().getSelectedItem());
                    this.filtroAnd.agregarFiltro(filtroHora);
                }
                actualizarTabla();
            }
        });

        this.cb_doctor.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (this.filtroAnd.contieneFiltro(filtroDoctor)) {
                    this.filtroAnd.eliminarFiltro(filtroDoctor);
                    this.filtroDoctor.setDoctor(cb_doctor.getSelectionModel().getSelectedItem());
                    this.filtroAnd.agregarFiltro(filtroDoctor);
                } else {
                    this.filtroDoctor.setDoctor(cb_doctor.getSelectionModel().getSelectedItem());
                    this.filtroAnd.agregarFiltro(filtroDoctor);
                }
                actualizarTabla();
            }
        });

        this.cb_estudio.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (this.filtroAnd.contieneFiltro(filtroEstudio)) {
                    this.filtroAnd.eliminarFiltro(filtroEstudio);
                    this.filtroEstudio.setEstudio(cb_estudio.getSelectionModel().getSelectedItem());
                    this.filtroAnd.agregarFiltro(filtroEstudio);
                } else {
                    this.filtroEstudio.setEstudio(cb_estudio.getSelectionModel().getSelectedItem());
                    this.filtroAnd.agregarFiltro(filtroEstudio);
                }
            }
            actualizarTabla();
        });

        this.cb_obrasocial.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (this.filtroAnd.contieneFiltro(filtroObrasocial)) {
                    this.filtroAnd.eliminarFiltro(filtroObrasocial);
                    this.filtroObrasocial.setCobertura(cb_obrasocial.getSelectionModel().getSelectedItem());
                    this.filtroAnd.agregarFiltro(filtroObrasocial);
                } else {
                    this.filtroObrasocial.setCobertura(cb_obrasocial.getSelectionModel().getSelectedItem());
                    this.filtroAnd.agregarFiltro(filtroObrasocial);
                }
                actualizarTabla();
            }
        });




        table_turnos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn_seleccionar.setVisible(true);
            }
        });
    }

    private void actualizarTabla() {
        List<Turno> listaTurnos;
        if(this.cb_area.getSelectionModel().getSelectedItem() == null){
            this.table_turnos.setItems(null);

        }
        else {
            if (filtroAnd.tieneFiltrosCargados()){
                listaTurnos = this.cb_area.getSelectionModel().getSelectedItem().getTurnosFiltrados(this.filtroAnd);
                this.turnos = FXCollections.observableArrayList(listaTurnos);
                this.table_turnos.setItems(this.turnos);
            }
            else {
                listaTurnos = this.cb_area.getSelectionModel().getSelectedItem().getTurnos();
                this.turnos = FXCollections.observableArrayList(listaTurnos);
                this.table_turnos.setItems(this.turnos);
            }
        }

        this.table_turnos.refresh();
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
            if(table_turnos.getSelectionModel().getSelectedItem() != null) {
                Main m = new Main();
                m.changeSceneOnParent("src/Interfaces/Recepcionista/AgregarTurno.fxml", "Asignar Turno");
                this.actualizarTabla();
            }
    }

    @FXML
    void resetButtonClicked(ActionEvent event) {
        this.cb_hora.getSelectionModel().clearSelection();
        this.cb_estudio.getSelectionModel().clearSelection();
        this.cb_doctor.getSelectionModel().clearSelection();
        this.cb_estudio.getSelectionModel().clearSelection();
        this.cb_obrasocial.getSelectionModel().clearSelection();
        this.cb_consultorio.getSelectionModel().clearSelection();
        this.dp_fecha.getEditor().clear();
        this.filtroAnd.removerFiltros();
        this.actualizarTabla();

    }

    public void cargarHorarios(){
        this.horarios = FXCollections.observableArrayList();
        List<LocalTime> horas = new ArrayList<>();

        //Carga de horarios
        int h = 9;
        boolean enPunto = true;
        while (h <= 15) {

            if (enPunto) {
                horas.add(LocalTime.of(h, 0));
                enPunto = false;
            } else {
                horas.add(LocalTime.of(h, 30));
                h++;
                enPunto = true;
            }
        }
        this.horarios.addAll(horas);
        this.cb_hora.setItems(this.horarios);
    }

    public void cargarEstudios(){
        List<Consultorio> consultorios = Main.manager.createQuery("FROM Consultorio").getResultList();

        List<String> estudios = new ArrayList<>();

        for(Consultorio c:consultorios){
            estudios.addAll(c.getEstudiosBrindados());
        }
        estudios = estudios.stream().distinct().collect(Collectors.toList());


        this.estudios = FXCollections.observableArrayList();
        this.estudios.addAll(estudios);
        this.cb_estudio.setItems(this.estudios);
    }

    public void cargarCoberturas(){

        List<Consultorio> consultorios = Main.manager.createQuery("FROM Consultorio").getResultList();
        List<String> obrasSociales = new ArrayList<>();

        for(Consultorio c:consultorios){
            obrasSociales.addAll(c.getCoberturasMedicas());
        }
        obrasSociales = obrasSociales.stream().distinct().collect(Collectors.toList());


        this.coberturas = FXCollections.observableArrayList();
        this.coberturas.addAll(obrasSociales);
        this.cb_obrasocial.setItems(this.coberturas);
    }

    public void cargarDoctores(){
        List<Doctor> docs = Main.manager.createQuery("FROM Doctor").getResultList();
        this.doctores =  FXCollections.observableArrayList();
        this.doctores.addAll(docs);
        this.cb_doctor.setItems(this.doctores);
    }
}
