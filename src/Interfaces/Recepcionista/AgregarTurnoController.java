package Interfaces.Recepcionista;

import Interfaces.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
            label_error.setText("Por favor ingresa el DNI del paciente");
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
