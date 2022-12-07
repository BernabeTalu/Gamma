package Interfaces;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class MenuPrincipalRootController {

    @FXML
    private Button btn_areas;

    @FXML
    private Button btn_cerrarSesion;

    @FXML
    private Button btn_empleado;

    @FXML
    private Button btn_resumen;

    @FXML
    private Button btn_salir;

    @FXML
    private Button btn_turnos;

    @FXML
    void areasButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
                m.changeScene("src/Interfaces/AreaEspecializacion.fxml", "Áreas de especialización");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cerrarSesionButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Login.fxml", "Login Gamma");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void empleadosButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Empleados.fxml", "Empleados");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resumenButtonClicked(ActionEvent event) {
        Main m = new Main();
    }

    @FXML
    void salirButtonClicked(ActionEvent event) {
        Main m = new Main();
    }

    @FXML
    void turnosButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Turnos.fxml", "Turnos");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
