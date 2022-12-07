package Interfaces;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EmpleadosController {

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;

    @FXML
    private void home(MouseEvent event){
        bp.setCenter(ap);
    }




    private void loadPage(String page){
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE,null, ex);
        }
        bp.setCenter(root);
    }


    public void Resumen(javafx.scene.input.MouseEvent mouseEvent) {
        System.out.println("lo hace");
        loadPage("Resumen");
    }
}
