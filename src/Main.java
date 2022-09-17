import modelos.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.print.Doc;
import java.util.List;

public class Main {

    public static EntityManager manager;
    public static EntityManagerFactory emf;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("Persistencia");
        manager = emf.createEntityManager();

        Persona per = new Persona(371223,"Francisco", "Gonzalez", 228455);

        if (!Main.manager.getTransaction().isActive())
            Main.manager.getTransaction().begin(); // La abro
//
        manager.persist(per);

        List<Persona> personas = (List<Persona>) manager.createQuery("FROM Persona").getResultList();
        System.out.println("En esta base de datos hay " + personas.size() + " personas");
        System.out.println("#############################################");
        Main.manager.getTransaction().commit();
    }
}
