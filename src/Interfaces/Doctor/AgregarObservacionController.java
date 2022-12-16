package Interfaces.Doctor;

import Interfaces.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.Consulta;

import java.time.LocalDate;

public class AgregarObservacionController {

    @FXML
    private Button btn_agregar;

    @FXML
    private TextField txt_observacion;

    @FXML
    void agregarButtonClicked(ActionEvent event) {
        if(txt_observacion.getText().isEmpty()){
            Main m = new Main();
            m.sendAlert(Alert.AlertType.ERROR,"Observación vacia","Se debe ingresar datos en la observación");
        }
        else{
            Consulta nuevaConsulta = new Consulta(LocalDate.now(),this.txt_observacion.getText(), Main.pacienteConsultado, Main.usuarioLogeado.getDni());
            if (!Main.manager.getTransaction().isActive())
                Main.manager.getTransaction().begin();

            Main.manager.persist(nuevaConsulta);
            Main.pacienteConsultado.setConsultaHistoriaClinica(nuevaConsulta);
            Main.manager.merge(Main.pacienteConsultado);
            Main.manager.getTransaction().commit();

            Stage stage = (Stage) btn_agregar.getScene().getWindow();
            stage.close();
        }
    }

}
