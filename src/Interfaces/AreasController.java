package Interfaces;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AreasController {

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_eliminar;

    @FXML
    void backButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/MenuPrincipalRoot.fxml", "Menu Gamma");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}


