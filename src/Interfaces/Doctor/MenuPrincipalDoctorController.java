package Interfaces.Doctor;

import Interfaces.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuPrincipalDoctorController {

    @FXML
    private Button btn_cerrarSesion;

    @FXML
    private Button btn_pacientes;

    @FXML
    private Button btn_salir;

    @FXML
    private Button btn_turnos;

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
    void pacientesButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Doctor/Pacientes.fxml", "Pacientes");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void salirButtonClicked(ActionEvent event) {
        Stage stage = (Stage) btn_salir.getScene().getWindow();
        stage.close();
    }

    @FXML
    void turnosButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Doctor/TurnosDoctor.fxml", "Turnos");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
