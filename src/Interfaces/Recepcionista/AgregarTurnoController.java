package Interfaces.Recepcionista;

import Interfaces.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.Paciente;

import java.io.IOException;

public class AgregarTurnoController {

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_nuevoPaciente;

    @FXML
    private TextField txt_dni;

    @FXML
    private Label label_error;


    @FXML
    void agregarButtonClicked(ActionEvent event)  {
        Main m = new Main();

        if(txt_dni.getText().isEmpty()) {
            label_error.setText("Por favor ingresa el DNI del paciente");
        }
        else {
            int dniIngresado = Integer.parseInt(txt_dni.getText());
            Paciente paciente = Main.manager.find(Paciente.class, dniIngresado);
            if (paciente != null) {
                Main.turnoActual.setPaciente(paciente);
                if (!Main.manager.getTransaction().isActive()) {
                    Main.manager.getTransaction().begin();
                }
                    Main.manager.persist(Main.turnoActual);
                    Main.manager.getTransaction().commit();

                label_error.setText("Turno registrado exitosamente");
                Main.manager.getTransaction().begin();
                Main.turnoActual.setAsignado(true);
                Main.manager.merge(Main.turnoActual);
                Main.manager.getTransaction().commit();

                Stage stage = (Stage) btn_agregar.getScene().getWindow();
                stage.close();
            }
            else {
                label_error.setText("Paciente inexistente");
            }
        }
    }

    @FXML
    void nuevoPacienteButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeSceneOnParent("src/Interfaces/Recepcionista/AgregarPaciente.fxml", "Agregar paciente");

    }

}
