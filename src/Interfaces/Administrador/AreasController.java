package Interfaces.Administrador;

import Interfaces.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class AreasController {

    @FXML
    private TableView<?> btn_addElem;

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_eliminar;

    @FXML
    void addButtonClicked(ActionEvent event) {

    }

    @FXML
    void delActionClicked(ActionEvent event) {

    }

    @FXML
    void backButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Administrador/MenuPrincipalRoot.fxml", "Menu Gamma");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}


