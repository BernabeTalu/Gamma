package Interfaces.Administrador;

import Interfaces.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
                m.changeScene("src/Interfaces/Administrador/AreaEspecializacion.fxml", "Áreas de especialización");
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
            m.changeScene("src/Interfaces/Administrador/Empleados.fxml", "Empleados");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resumenButtonClicked(ActionEvent event) {
        //TODO
    }

    @FXML
    void salirButtonClicked(ActionEvent event) {
        Stage stage = (Stage) btn_salir.getScene().getWindow();
        stage.close();
    }

    @FXML
    void turnosButtonClicked(ActionEvent event) {
        //TODO
    }

}
