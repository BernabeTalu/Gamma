package modelos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("R")
@Table(name = "recepcionista")
public class Recepcionista extends Empleado implements Serializable{

    @OneToOne(mappedBy = "recepcionista",fetch = FetchType.LAZY)
    private Area area;

    public Recepcionista(){
        super();
    }

    public Recepcionista(int dni, String nombre, String apellido, int telefono,String password, double sueldo) {
        super(dni, nombre, apellido, telefono, password,"Recepcionista", sueldo);
    }

    public void setTurnosEnAgenda(Turno t){
        List<Elemento> compArea = new ArrayList<>(this.area.getComponentes());
        if(compArea.contains(t.getConsultorio())){ //Corroboro que el consultorio pertenezca al area administrada por el recepcionista
            for(Elemento e:compArea){
                if(e.getId() == t.getConsultorio().getId()){
                    e.setTurno(t);
                }
            }
        }
        else
            System.out.println("El recepcionista no administra ese consultorio");
    }

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
