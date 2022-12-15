package Interfaces.Recepcionista;

import Interfaces.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.Doctor;
import modelos.Paciente;


import java.net.URL;
import java.util.*;

public class AgregarPacienteController {

    @FXML
    private Button btn_agregar;

    @FXML
    private TextField txt_DNI;

    @FXML
    private TextField txt_apellido;

    @FXML
    private TextField txt_cobertura;

    @FXML
    private TextField txt_nombre;

    @FXML
    private TextField txt_telefono;

    @FXML
    void agregarButtonClicked(ActionEvent event) {
        Paciente nuevoPaciente = new Paciente(
                Integer.parseInt(this.txt_DNI.getText()),
                this.txt_nombre.getText(),
                this.txt_apellido.getText(),
                Integer.parseInt(this.txt_telefono.getText()),
                this.txt_cobertura.getText()
        );
        if (!Main.manager.getTransaction().isActive())
            Main.manager.getTransaction().begin();

        Main.manager.persist(nuevoPaciente);
        Main.manager.getTransaction().commit();

        Stage stage = (Stage) btn_agregar.getScene().getWindow();
        stage.close();
    }

}

