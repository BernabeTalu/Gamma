package modelos;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Embeddable
@Entity
@Inheritance(strategy = InheritanceType.JOINED) //Defino que tipo de herencia se utiliza
@DiscriminatorColumn(name = "tipoElemento") //El discriminator referencia el tipo de elemento que estamos usando en los hijos.
@Table(name = "elemento")
public abstract class Elemento {
    @Id
    @Column(name = "idElemento")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    public Elemento(){
    }

    public Elemento(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public abstract List<Turno> getTurnos(); //Done
    public abstract List<Consultorio> getConsultoriosFiltrados();
    public abstract List<Consultorio> getConsultorios(); //Done
    public abstract boolean turnoOcupado(LocalDate date, LocalTime time); //Done
    public abstract void setTurno(Turno t); //Done
    public abstract void cancelarTurno(Turno t);
}
