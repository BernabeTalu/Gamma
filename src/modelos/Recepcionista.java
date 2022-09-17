package modelos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("R")
@Table(name = "recepcionista")
public class Recepcionista extends Empleado implements Serializable{

    @OneToOne()
    @JoinColumn(name = "idArea")
    private Area area;

    public Recepcionista(){
    }

    public Recepcionista(int dni, String nombre, String apellido, int telefono, int idEmpleado, double sueldo,Area area){
        super(dni,nombre,apellido,telefono,idEmpleado,sueldo);
        this.area = area;
    }

    public void setTurnosEnAgenda(Turno t, Consultorio c){
        List<Elemento> compArea = new ArrayList<>(this.area.getComponentes());
        if(compArea.contains(c)){ //Corroboro que el consultorio pertenezca al area administrada por el recepcionista
            for(Elemento e:compArea){
                if(e.getId() == c.getId()){
                    e.setTurno(t);
                }
            }
        }
        else
            System.out.println("El recepcionista no administra ese consultorio");
    }
}
