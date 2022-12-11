package Interfaces.Administrador;

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
import modelos.Area;
import modelos.Recepcionista;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AgregarAreaController implements Initializable {

    @FXML
    private Button btn_addArea;

    @FXML
    private Button btn_addRecepcionista;

    @FXML
    private ComboBox<Integer> cbox_recepcionistas;

    @FXML
    private TextField txt_idNuevaArea;

    @FXML
    private TextField txt_nuevaArea;

    private ObservableList<Integer> recepcionistas;

    @FXML
    private Label txt_recepCreado;

    @FXML
    void addButtonClicked(ActionEvent event) {

        if (this.cbox_recepcionistas.getSelectionModel().getSelectedItem() != null) {
            Main.nuevoRecepcionista = Main.manager.find(Recepcionista.class, this.cbox_recepcionistas.getSelectionModel().getSelectedItem());
        }

        Area nuevaArea = new Area(
                Integer.parseInt(this.txt_idNuevaArea.getText()),
                this.txt_nuevaArea.getText(),
                Main.nuevoRecepcionista
        );

        if(Main.areaSeleccionada != null && Main.agregandoSubArea)
            Main.areaSeleccionada.setComponente(nuevaArea);

        if (!Main.manager.getTransaction().isActive())
            Main.manager.getTransaction().begin();

        Main.manager.persist(nuevaArea);
        Main.manager.getTransaction().commit();

        Stage stage = (Stage) btn_addArea.getScene().getWindow();
        stage.close();
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
        Main.agregandoRecepcionista = false;
        this.txt_recepCreado.setVisible(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.recepcionistas = FXCollections.observableArrayList();
        List<Recepcionista> recepcionistasLibres = Main.manager.createQuery("FROM Recepcionista WHERE dni NOT IN (SELECT recepcionista FROM Area )").getResultList();

        for(Recepcionista r:recepcionistasLibres){
            this.recepcionistas.add(r.getDni());
        }
        this.cbox_recepcionistas.setItems(this.recepcionistas);
    }
}
