package Interfaces.Administrador;

import Interfaces.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import modelos.Consultorio;
import modelos.Empleado;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResumenController implements Initializable {

    @FXML
    private Button btn_back;

    @FXML
    private Text txt_consLibres;

    @FXML
    private Text txt_consOcupados;

    @FXML
    private Text txt_costoEmpleados;

    @FXML
    private Text txt_gananciaTotal;

    @FXML
    private Text txt_nroConsultorios;

    @FXML
    private Text txt_nroEmpleados;

    @FXML
    void backButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Administrador/MenuPrincipalRoot.fxml", "Menu Gamma");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Empleado> nroEmpleados = Main.manager.createQuery("from Empleado ").getResultList();
        this.txt_nroEmpleados.setText(Integer.toString(nroEmpleados.size()));

        Double gananciaTotal = 0.0;
        List<Consultorio> consultorios = Main.manager.createQuery("from Consultorio ").getResultList();
        for(Consultorio c :consultorios){
           gananciaTotal += c.getGananciaMensual();
        }
        this.txt_gananciaTotal.setText(Double.toString(gananciaTotal));

        Double costoPorPersonal = 0.0;
        for(Empleado e :nroEmpleados){
            costoPorPersonal += e.getSueldo();
        }
        this.txt_costoEmpleados.setText(Double.toString(costoPorPersonal));

        this.txt_nroConsultorios.setText(Integer.toString(consultorios.size()));

        int consLibres = 0;
        int consOcupados = 0;
        for(Consultorio c :consultorios){
            if(c.isOcupado())
                consOcupados += 1;
            else
                consLibres += 1;
        }
        this.txt_consLibres.setText(Integer.toString(consLibres));
        this.txt_consOcupados.setText(Integer.toString(consOcupados));
    }
}
