package Interfaces;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelos.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    private static Stage stg;

    public static EntityManager manager;
    public static EntityManagerFactory emf;
    public static Empleado usuarioLogeado;
    public static Paciente pacienteConsultado;
    public static boolean agregandoRecepcionista = false;
    public static boolean agregandoDoctor = false;
    public static Recepcionista nuevoRecepcionista;
    public static Area areaSeleccionada;
    public static Consultorio consultorioSeleccionado;
    public static Doctor nuevoDoctor;
    public static Turno turnoActual;
    public static boolean editandoConsultorio = false;
    public static boolean editandoArea = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stg = primaryStage;
        primaryStage.setResizable(true);

        URL url = new File("src/Interfaces/Login.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        primaryStage.setTitle("Gamma Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("Persistencia");
        manager = emf.createEntityManager();

        Persona rootExistente = manager.find(Persona.class, 1);
        if (rootExistente == null) {
            Empleado root = new Empleado(
                    1,
                    "root",
                    "root",
                    0,
                    "root123",
                    "Administrador",
                    0);

            if (!Main.manager.getTransaction().isActive())
                Main.manager.getTransaction().begin();

            manager.persist(root);
            Paciente paciente = new Paciente(37852343,"Carlos", "Gomez",2424242, "OSDE");
            Area areaGral = new Area("Area",null);
            manager.persist(paciente);
            manager.persist(areaGral);
            Main.manager.getTransaction().commit();
        }
        cargarTurnos();
        launch(args);
    }

    public void changeScene(String fxml, String titulo) throws IOException {

        URL url = new File(fxml).toURI().toURL();
        Parent pane = FXMLLoader.load(url);

        stg.getScene().setRoot(pane);
        stg.setTitle(titulo);
    }

    public void changeSceneOnParent(String fxml, String titulo) throws IOException {
        URL url = new File(fxml).toURI().toURL();
        Parent pane = FXMLLoader.load(url);

        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void sendAlert(Alert.AlertType alertType, String title, String content){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    //Metodo para asegurarse que haya turnos desde la fecha actual hasta 30 dias en adelante para todos los consultorios.
    public static void cargarTurnos() {
        LocalDate diaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        List<Consultorio> consultorios = (ArrayList<Consultorio>) Main.manager.createQuery("FROM Consultorio").getResultList();
        if (consultorios.size() > 0) {

            Turno turno;
            for (Consultorio consultorio : consultorios) {
                List<LocalDate> listaFechas = (ArrayList<LocalDate>) Main.manager.createQuery("SELECT max(fecha) FROM Turno WHERE consultorio = " + consultorio.getId()).getResultList();
                LocalDate fechaUltimoTurno;
                if (listaFechas.get(0) == null) {
                    fechaUltimoTurno = diaActual.minusDays(1);
                } else {
                    fechaUltimoTurno = listaFechas.get(0);
                }

                int i = 1;
                int h;
                while (i <= 30 && fechaUltimoTurno.isBefore(diaActual.plusDays(30))) {
                    if(fechaUltimoTurno.isEqual(diaActual))
                        h = horaActual.getHour() + 1;
                    else
                        h = 9;

                    boolean enPunto = true;
                    while (h <= 15) {
                        if (!Main.manager.getTransaction().isActive()) {
                            Main.manager.getTransaction().begin(); // La abro
                        }
                        if (enPunto) {
                            if (((!fechaUltimoTurno.isBefore(diaActual)) || (fechaUltimoTurno.isEqual(diaActual) && LocalTime.of(h, 0).isAfter(horaActual)))) {
                                turno = new Turno(null, fechaUltimoTurno, LocalTime.of(h, 0), consultorio.getPrecioTurno(), false, consultorio, false);
                                Main.manager.merge(consultorio);
                                Main.manager.persist(turno);
                                consultorio.setTurno(turno);
                            }
                                enPunto = false;

                            }

                        else {
                            if (((!fechaUltimoTurno.isBefore(diaActual)) || (fechaUltimoTurno.isEqual(diaActual) && LocalTime.of(h, 30).isAfter(horaActual)))) {
                                turno = new Turno(null, fechaUltimoTurno, LocalTime.of(h, 30), consultorio.getPrecioTurno(), false, consultorio, false);
                                Main.manager.merge(consultorio);
                                Main.manager.persist(turno);
                                consultorio.setTurno(turno);
                            }
                                h++;
                                enPunto = true;
                            }


                        Main.manager.getTransaction().commit();
                    }
                    fechaUltimoTurno = fechaUltimoTurno.plusDays(1);
                    i++;
                }
            }
        }
    }

    //Metodo para poblar la base con turnos sin asignar para un nuevo consultorio
    public static void cargarTurnosConsultorio(Consultorio c) {
        LocalDate diaActual = LocalDate.now();
        LocalDate fechaUltimoTurno = diaActual;

        boolean enPunto = true;
        Turno turno;
        int i = 1;
        while (i <= 30 && fechaUltimoTurno.isBefore(diaActual.plusDays(30))) {
            int h = 9;
            while (h <= 15) {
                if (!Main.manager.getTransaction().isActive()) {
                    Main.manager.getTransaction().begin(); // La abro
                }
                if (enPunto) {
                    turno = new Turno(null, fechaUltimoTurno.plusDays(1), LocalTime.of(h, 0), c.getPrecioTurno(), false, c, false);
                    c.setTurno(turno);
                    Main.manager.merge(c);
                    Main.manager.persist(turno);
                    c.setTurno(turno);
                    enPunto = false;
                }
                else {
                    turno = new Turno(null, fechaUltimoTurno.plusDays(1), LocalTime.of(h, 30), c.getPrecioTurno(), false, c, false);
                    c.setTurno(turno);
                    Main.manager.merge(c);
                    Main.manager.persist(turno);
                    c.setTurno(turno);
                    h++;
                    enPunto = true;
                }

                Main.manager.getTransaction().commit();
            }
            fechaUltimoTurno = fechaUltimoTurno.plusDays(1);
            i++;
        }
        System.out.println("efewfwfwe");
    }

}
