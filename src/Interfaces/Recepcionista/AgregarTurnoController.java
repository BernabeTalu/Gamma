package Interfaces.Recepcionista;

import Interfaces.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import modelos.Area;
import modelos.Paciente;
import modelos.Recepcionista;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AgregarTurnoController implements Initializable {

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_nuevoPaciente;

    @FXML
    private ComboBox<Paciente> cbox_pacientes;

    @FXML
    private Label label_error;

    private ObservableList<Paciente> listPacientes;

    @FXML
    void agregarButtonClicked(ActionEvent event)  {
        Main m = new Main();

        if(this.cbox_pacientes.getSelectionModel().getSelectedItem() == null) {
            m.sendAlert(Alert.AlertType.ERROR, "Error", "Debe elegir un paciente de los disponibles. Reintente");
        }
        else {
            Paciente paciente = this.cbox_pacientes.getSelectionModel().getSelectedItem();
            if (paciente != null) {
                Main.turnoActual.setPaciente(paciente);
                if (!Main.manager.getTransaction().isActive()) {
                    Main.manager.getTransaction().begin();
                }
                    Main.manager.persist(Main.turnoActual);
                    Main.manager.getTransaction().commit();

                Main.manager.getTransaction().begin();
                Main.turnoActual.setAsignado(true);
                Main.manager.merge(Main.turnoActual);
                Main.manager.getTransaction().commit();

                Stage stage = (Stage) btn_agregar.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    void nuevoPacienteButtonClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeSceneOnParent("src/Interfaces/Recepcionista/AgregarPaciente.fxml", "Agregar paciente");
        this.listPacientes = FXCollections.observableArrayList();
        this.listPacientes.addAll(Main.manager.createQuery("FROM Paciente ").getResultList());
        this.cbox_pacientes.setItems(this.listPacientes);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.listPacientes = FXCollections.observableArrayList();
        this.listPacientes.addAll(Main.manager.createQuery("FROM Paciente ").getResultList());
        this.cbox_pacientes.setItems(this.listPacientes);

        StringConverter<Paciente> converterPaciente = new StringConverter<Paciente>() {
            @Override
            public String toString(Paciente paciente) {
                return paciente.getDni() +" - "+paciente.getNombre();
            }

            @Override
            public Paciente fromString(String string) {
                return null;
            }
        };
        this.cbox_pacientes.setConverter(converterPaciente);
    }
}
