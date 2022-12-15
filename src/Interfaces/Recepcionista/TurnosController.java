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
import modelos.*;


import javax.persistence.Query;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TurnosController implements Initializable {

    private ObservableList<Turno> turnos;
    private ObservableList<Area> areas;
    private ObservableList<Consultorio> consultorios;
    private Area area;

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private ComboBox<Area> cb_area;

    @FXML
    private ComboBox<Consultorio> cb_consultorio;

    @FXML
    private TableColumn<?, ?> col_doctor;

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
        this.col_pago.setCellValueFactory(new PropertyValueFactory<>("Pago"));
        this.col_precio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        this.col_doctor.setCellValueFactory(new PropertyValueFactory<>("Doctor"));



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



        cb_area.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarTabla();
                actualizarConsultorios();
            }
        });

        cb_consultorio.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarTabla();
            }
        });
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
            listaTurnos = (ArrayList<Turno>) Main.manager.createQuery("FROM Turno").getResultList();
        }
        else{
            if (cb_consultorio.getSelectionModel().getSelectedItem() == null){
                listaTurnos = (ArrayList<Turno>) Main.manager.createQuery("FROM Turno WHERE Area = :nombreArea").setParameter("nombreArea", cb_area.getSelectionModel().getSelectedItem()).getResultList();
            }
            else{
                Query query = Main.manager.createQuery("FROM Turno WHERE Area = :nombreArea AND Consultorio = :nombreConsultorio");
                query.setParameter("nombreArea",cb_area.getSelectionModel().getSelectedItem());
                query.setParameter("nombreConsultorio",cb_consultorio.getSelectionModel().getSelectedItem());
                listaTurnos = query.getResultList();
            }
        }

        this.turnos = FXCollections.observableArrayList(listaTurnos);
        this.table_turnos.setItems(this.turnos);
        this.table_turnos.refresh();
    }

    @FXML
    void agregarButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("src/Interfaces/Recepcionista/TurnosDisponibles.fxml", "Menu Gamma");
    }

    @FXML
    void eliminarButtonClicked(ActionEvent event) throws IOException {

    }

    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("src/Interfaces/Recepcionista/MenuPrincipalRecepcionista.fxml", "Menu Gamma");
    }

}
