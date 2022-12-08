package Interfaces;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelos.Empleado;


import java.io.IOException;

public class LoginControlller {


    @FXML
    private TextField txt_nombreUsuario;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Button btn_login;
    @FXML
    private Label label_error;

    public void LogIn(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException {

        try {
            Main m = new Main();

            if(txt_nombreUsuario.getText().isEmpty() || txt_password.getText().isEmpty())
                label_error.setText("Por favor ingresa tus datos.");
            else {
                int dniIngresado = Integer.parseInt(txt_nombreUsuario.getText());
                Main.usuarioLogeado = Main.manager.find(Empleado.class, dniIngresado);
                if (Main.usuarioLogeado != null) {
                    if (Main.usuarioLogeado.getPassword().equals(txt_password.getText())) {
                        txt_password.setText("Ingreso exitoso!");
                        System.out.println("Ingreso exitoso!");

                        switch (Main.usuarioLogeado.getTipo()){
                            case "Administrador":
                                m.changeScene("src/Interfaces/Administrador/MenuPrincipalRoot.fxml", "Menu Principal Administrador");
                                break;
                            case "Doctor":
                                m.changeScene("src/Interfaces/Doctor/MenuPrincipalDoctor.fxml", "Menu Principal Doctor");
                                break;
                            case "Recepcionista":
                                m.changeScene("src/Interfaces/Recepcionista/MenuPrincipalRecepcionista.fxml", "Menu Principal Recepcionista");
                                break;
                        }

                    } else {
                        txt_password.setText("Contraseña incorrecta!");
                        System.out.println("Contraseña incorrecta!");
                    }
                } else {
                    txt_password.setText("Usuario inexistente");
                    System.out.println("Usuario inexistente");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
