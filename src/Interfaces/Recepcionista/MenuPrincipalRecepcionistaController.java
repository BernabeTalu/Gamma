package Interfaces.Recepcionista;

import Interfaces.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import modelos.Recepcionista;

import java.io.IOException;

public class MenuPrincipalRecepcionistaController {

    @FXML
    private Button btn_cerrarSesion;


    @FXML
    private Button btn_pacientes;

    @FXML
    private Button btn_salir;

    @FXML
    private Button btn_turnos;

    @FXML
    void adminPacientesClicked(ActionEvent event) throws IOException {
        Recepcionista rec = Main.manager.find(Recepcionista.class,Main.usuarioLogeado.getDni());
        Main m = new Main();
        if(rec.getArea() != null) {
            m.changeScene("src/Interfaces/Recepcionista/AdministradorPacientes.fxml", "Administrador de pacientes");
        }
        else{
            m.sendAlert(Alert.AlertType.ERROR, "Error", "Usted no tiene un area asignada todavia. Por favor espere.");
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
    void salirButtonClicked(ActionEvent event) {
        Stage stage = (Stage) btn_salir.getScene().getWindow();
        stage.close();
    }

    @FXML
    void turnosButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        Recepcionista rec = Main.manager.find(Recepcionista.class,Main.usuarioLogeado.getDni());
        if(rec.getArea() != null) {
            m.changeScene("src/Interfaces/Recepcionista/Turnos.fxml", "Turnos");
        }
        else{
            m.sendAlert(Alert.AlertType.ERROR, "Error", "Usted no tiene un area asignada todavia. Por favor espere.");
        }
    }

}

