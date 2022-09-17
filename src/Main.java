import modelos.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class Main {

    public static EntityManager manager;
    public static EntityManagerFactory emf;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("Persistencia");
        manager = emf.createEntityManager();

        Persona per = new Persona(371223,"Francisco", "Gonzalez", 228455);
        Paciente paciente = new Paciente(37852343,"Carlos", "Gomez",2424242, "OSDE");

        if (!Main.manager.getTransaction().isActive())
            Main.manager.getTransaction().begin(); // La abro
//
        manager.persist(paciente);

        List<Persona> personas = (List<Persona>) manager.createQuery("FROM Persona").getResultList();
        System.out.println("En esta base de datos hay " + personas.size() + " personas");
        System.out.println("#############################################");
        Main.manager.getTransaction().commit();
    }
}
