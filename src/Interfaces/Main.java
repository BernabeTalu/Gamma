package Interfaces;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelos.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main extends Application {
    private static Stage stg;

    public static EntityManager manager;
    public static EntityManagerFactory emf;
    public static Empleado usuarioLogeado;

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

        Persona per = new Persona(371223, "Francisco", "Gonzalez", 228455);
        Paciente paciente = new Paciente(37852343, "Carlos", "Gomez", 2424242, "OSDE");




        Persona rootExistente = manager.find(Persona.class, 1);
        if (rootExistente == null) {
            Empleado root = new Empleado(
                    1,
                    "root",
                    "root",
                    0,
                    "root123",
                    0);

            if (!Main.manager.getTransaction().isActive())
                Main.manager.getTransaction().begin(); // La abro
                //manager.persist(paciente);
                manager.persist(root);
                List<Persona> personas = (List<Persona>) manager.createQuery("FROM Persona").getResultList();
                System.out.println("En esta base de datos hay " + personas.size() + " personas");
                Main.manager.getTransaction().commit();

        }
        launch(args);
    }
    public void changeScene(String fxml, String titulo) throws IOException {
        //cambiar pantalla 100%
        System.out.println("ENTRA======================================");
        URL url = new File(fxml).toURI().toURL();
        Parent pane = FXMLLoader.load(url);

        //Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
        stg.setTitle(titulo);
    }
}
