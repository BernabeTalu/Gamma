package Interfaces.Administrador;

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
import modelos.Empleado;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmpleadosController implements Initializable {


    private ObservableList<Empleado> empleados;


    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_eliminar;

    @FXML
    private TableColumn<?, ?> col_apellido;

    @FXML
    private TableColumn<?, ?> col_dni;

    @FXML
    private TableColumn<?, ?> col_nombre;

    @FXML
    private TableColumn<?, ?> col_sueldo;

    @FXML
    private TableColumn<?, ?> col_telefono;

    @FXML
    private TableColumn<?, ?> col_tipo;

    @FXML
    private TableColumn<?, ?> col_password;

    @FXML
    private TableView table_empleados;


    @FXML
    void agregarButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeSceneOnParent("src/Interfaces/Administrador/AgregarEmpleado.fxml", "Nuevo empleado");
        actualizarTabla();
    }

    @FXML
    void backButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Administrador/MenuPrincipalRoot.fxml", "Menu Gamma");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void eliminarButtonClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.col_dni.setCellValueFactory(new PropertyValueFactory<>("Dni"));
        this.col_nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        this.col_apellido.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
        this.col_telefono.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        this.col_password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        this.col_tipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
        this.col_sueldo.setCellValueFactory(new PropertyValueFactory<>("Sueldo"));

        actualizarTabla();
    }

    public void actualizarTabla(){

        List<Empleado> listaEmpleados= (ArrayList<Empleado>)Main.manager.createQuery("FROM Empleado").getResultList();
        this.empleados = FXCollections.observableArrayList(listaEmpleados);
        this.table_empleados.setItems(this.empleados);
        this.table_empleados.refresh();


    }
}
