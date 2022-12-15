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
                Main.manager.getTransaction().begin(); // La abro
                //manager.persist(paciente);
                manager.persist(root);
//
            Paciente paciente = new Paciente(37852343,"Carlos", "Gomez",2424242, "OSDE");
//            Paciente paciente2 = new Paciente(20807233,"Cesar", "Basualdo",343434, "IOMA");
//
//            Doctor doc1 = new Doctor(35678890,"Bernabe", "Talu", 24946014,"pass",25, 80000, 234789);
//            Doctor doc2 = new Doctor(23664890,"Eduardo", "Veniabuscarajuancruz", 2223456,"pass", 26, 90000, 244767);
//            Doctor doc3 = new Doctor(18908998,"Gustavo","Suarez",5556668,"pass", 27,100000,456788);
//
//            Recepcionista rec = new Recepcionista(27908889, "Javier", "Perez",22845677, "pass",27, 40000);
//            Recepcionista rec2 = new Recepcionista(45678908,"Leo","Messi",345678,"pass",28,40000);
//
//            Consultorio cons2 = new Consultorio(02, "FalsoConsultorio2", doc2, 80.90);
//            Consultorio cons3 = new Consultorio(03,"FalsoConsultorio3",doc3,100.00);
//
//            cons.setCoberturasMedicas("OSDE");
//            cons2.setCoberturasMedicas("IOMA");
//
//            Turno turno = new Turno(1, paciente, LocalDate.now(), LocalTime.now(),100.50, true, cons);
//            Turno turno2 = new Turno(2, paciente, LocalDate.now(), LocalTime.now(),100.50, false, cons);
//            Turno turno3 = new Turno(3, paciente2, LocalDate.now(), LocalTime.now(),100.50, false, cons3);
//
            Consulta consulta1 = new Consulta(404,LocalDate.now(),"Resonancia",paciente,123);
//            paciente.setConsultaHistoriaClinica(consulta1);
//
            Area areaGral = new Area(0,"Area",null);
            //Area area1 = new Area(51, "Kinesiologia", null);
            //Area area2 = new Area(52, "Cardiologia",null);

//            area1.setComponente(cons);
//            area1.setComponente(cons2);
//            area2.setComponente(cons3);
//
//            rec.setTurnosEnAgenda(turno);
//            rec.setTurnosEnAgenda(turno2);
//            rec.setTurnosEnAgenda(turno3);
//
//            doc1.setEspecialidades("Cardiologo");
//            doc1.setEspecialidades("Cirujano");
//            doc2.setEspecialidades("Obstetra");
//            doc2.setEspecialidades("Kinesiologo");
//
//            doc1.agregarPaciente(paciente);
//            doc2.agregarPaciente(paciente);
//
//            paciente.setDoctores(doc1);
//            paciente.setDoctores(doc2);
//
//            rec.setArea(area1);
//
//            System.out.println(cons.getTurnos());
//
//            if (!Main.manager.getTransaction().isActive())
//                Main.manager.getTransaction().begin(); // La abro
//
            manager.persist(paciente);
//            manager.persist(paciente);
            manager.persist(consulta1);
//            manager.persist(cons3);
//            manager.persist(doc1);
//            manager.persist(doc2);
//            manager.persist(doc3);
//            manager.persist(turno);
//            manager.persist(turno2);
//            //manager.persist(turno3);
            //manager.persist(area1);
            //manager.persist(area2);
            manager.persist(areaGral);
//
//            List<Persona> personas = (List<Persona>) manager.createQuery("FROM Persona").getResultList();
//            System.out.println("En esta base de datos hay " + personas.size() + " personas");
//            System.out.println(personas);
//            System.out.println("#############################################");
//
//


        /*
        LocalTime hour = LocalTime.parse("18:00:00");
        System.out.println(hour.getMinute());
        System.out.println(hour.getSecond());
        System.out.println("resta");
        System.out.println(LocalTime.now().getHour() - hour.getHour());
        System.out.println(LocalTime.now());
         */

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

    public static void cargarTurnos() {
        LocalDate diaActual = LocalDate.now();
        List<Consultorio> consultorios = (ArrayList<Consultorio>) Main.manager.createQuery("FROM Consultorio").getResultList();
        System.out.println(consultorios);
        if (consultorios.size() > 0) {

            List<LocalDate> listaFechas = (ArrayList<LocalDate>) Main.manager.createQuery("SELECT max(fecha) FROM Turno").getResultList();
            System.out.println(listaFechas);
            LocalDate fechaUltimoTurno;
            if(listaFechas.get(0)==null){
                fechaUltimoTurno = diaActual;
            }
            else{
                fechaUltimoTurno = listaFechas.get(0);
                System.out.println("lwsnhfonhowqfn");
            }

            boolean enPunto = true;
            Turno turno;
            int idTurnoMax = 0;
            if (fechaUltimoTurno.isBefore(diaActual.plusDays(30))) {
                int i = 1;

                while (i <= 30 && fechaUltimoTurno.isBefore(diaActual.plusDays(30))) {
                    for (Consultorio consultorio : consultorios) {
                        //Maximo id de turno

                        if (Main.manager.createQuery("SELECT max(idTurno) FROM Turno").getResultList().get(0) != null)
                            idTurnoMax = (Integer) Main.manager.createQuery("SELECT max(idTurno) FROM Turno").getResultList().get(0);


                        int h = 9;
                        while(h<= 15) {
                            if (!Main.manager.getTransaction().isActive())
                                Main.manager.getTransaction().begin(); // La abro
                            if (enPunto) {
                                turno = new Turno( null, fechaUltimoTurno.plusDays(1), LocalTime.of(h, 0), consultorio.getPrecioTurno(), false, consultorio,false);
                                consultorio.setTurno(turno);
                                Main.manager.persist(turno);
                                enPunto = false;
                            } else {
                                turno = new Turno( null, fechaUltimoTurno.plusDays(1), LocalTime.of(h, 30), consultorio.getPrecioTurno(), false, consultorio,false);
                                consultorio.setTurno(turno);
                                Main.manager.persist(turno);
                                h++;
                                enPunto = true;
                            }

                            Main.manager.getTransaction().commit();
                        }


                    }

                    fechaUltimoTurno = fechaUltimoTurno.plusDays(1);
                    i= i+1;

                }
            }
        }
    }
}
