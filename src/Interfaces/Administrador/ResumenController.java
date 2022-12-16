package Interfaces.Administrador;

import Interfaces.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import modelos.Area;
import modelos.Consultorio;
import modelos.Empleado;
import modelos.FiltrosTurnos.FiltroAnd;
import modelos.FiltrosTurnos.FiltroAsignado;
import modelos.FiltrosTurnos.FiltroFechaIgual;
import modelos.FiltrosTurnos.FiltroFechaMayor;
import modelos.Turno;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    private FiltroAnd filtroAnd;

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
        this.filtroAnd = new FiltroAnd();
        this.filtroAnd.agregarFiltro(new FiltroAsignado(true));
        this.filtroAnd.agregarFiltro(new FiltroFechaIgual(LocalDate.now().plusDays(1), LocalTime.now()));
        int consLibres = 0;
        int consOcupados = 0;

        Area area =  Main.manager.find(Area.class, 1);
        List<Turno> turnos = area.getTurnosFiltrados(filtroAnd);
        List<Consultorio> cons = new ArrayList<>();
        for (Turno t :turnos){
            cons.add(t.getConsultorio());
        }
        cons = cons.stream().distinct().collect(Collectors.toList());

        consOcupados = cons.size();
        consLibres = consultorios.size() - consOcupados;
        this.txt_consLibres.setText(Integer.toString(consLibres));
        this.txt_consOcupados.setText(Integer.toString(consOcupados));
    }
}
