package Interfaces.Administrador;

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
import modelos.Elemento;
import modelos.Paciente;
import modelos.Recepcionista;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AgregarAreaController implements Initializable {

    @FXML
    private Button btn_addArea;

    @FXML
    private Button btn_addRecepcionista;

    @FXML
    private ComboBox<Recepcionista> cbox_recepcionistas;

    @FXML
    private TextField txt_idNuevaArea;

    @FXML
    private TextField txt_nuevaArea;

    private ObservableList<Recepcionista> recepcionistas;

    @FXML
    private Label txt_recepCreado;

    @FXML
    void addButtonClicked(ActionEvent event) {
        Main m = new Main();
        if(!this.txt_nuevaArea.getText().equals("")) {
            if (this.cbox_recepcionistas.getSelectionModel().getSelectedItem() != null) {
                Main.nuevoRecepcionista = Main.manager.find(Recepcionista.class, this.cbox_recepcionistas.getSelectionModel().getSelectedItem().getDni());

                Area nuevaArea = new Area(
                        this.txt_nuevaArea.getText(),
                        Main.nuevoRecepcionista
                );

                if (!Main.manager.getTransaction().isActive())
                    Main.manager.getTransaction().begin();
                Main.areaSeleccionada.setComponente(nuevaArea);
                Main.manager.persist(nuevaArea);
                Main.manager.merge(Main.areaSeleccionada);
                Main.manager.getTransaction().commit();

                Stage stage = (Stage) btn_addArea.getScene().getWindow();
                stage.close();
            }
            else{
                m.sendAlert(Alert.AlertType.ERROR, "Error", "Debe elegir un recepcionista para el area. Reintente");
            }
        }else{
            m.sendAlert(Alert.AlertType.ERROR, "Error", "Debe ingresar el nombre del area. Reintente.");
        }
    }

    @FXML
    void addButtonRecepcionistaClicked(ActionEvent event) {
        Main.agregandoRecepcionista = true;
        Main m = new Main();
        try {
            m.changeSceneOnParent("src/Interfaces/Administrador/AgregarEmpleado.fxml", "Nuevo Recepcionista");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.recepcionistas.addAll(Main.manager.createQuery("FROM Recepcionista WHERE dni NOT IN (SELECT recepcionista FROM Area )").getResultList());
        this.cbox_recepcionistas.setItems(this.recepcionistas);
        m.sendAlert(Alert.AlertType.INFORMATION, "Recepcionista creado", "Recepcionista creado existosamente.");
        Main.agregandoRecepcionista = false;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.recepcionistas = FXCollections.observableArrayList();
        this.recepcionistas.addAll(Main.manager.createQuery("FROM Recepcionista WHERE dni NOT IN (SELECT recepcionista FROM Area )").getResultList());
        this.cbox_recepcionistas.setItems(this.recepcionistas);

        StringConverter<Recepcionista> converterPaciente = new StringConverter<Recepcionista>() {
            @Override
            public String toString(Recepcionista recepcionista) {
                return recepcionista.getDni() +" - "+recepcionista.getNombre();
            }

            @Override
            public Recepcionista fromString(String string) {
                return null;
            }
        };
        this.cbox_recepcionistas.setConverter(converterPaciente);

    }
}
