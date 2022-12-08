package Interfaces.Recepcionista;

import Interfaces.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuPrincipalRecepcionistaController {

    @FXML
    private Button btn_cerrarSesion;

    @FXML
    private Button btn_consultorios;

    @FXML
    private Button btn_pacientes;

    @FXML
    private Button btn_salir;

    @FXML
    private Button btn_turnos;

    @FXML
    void adminPacientesClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Recepcionista/AdministradorPacientes.fxml", "Login Gamma");
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
    void consultoriosButtonClicked(ActionEvent event) {

    }

    @FXML
    void salirButtonClicked(ActionEvent event) {
        Stage stage = (Stage) btn_salir.getScene().getWindow();
        stage.close();
    }

    @FXML
    void turnosButtonClicked(ActionEvent event) {

    }

}

