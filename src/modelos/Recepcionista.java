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

<<<<<<< HEAD
    public Recepcionista(int dni, String nombre, String apellido, int telefono,String password, int idEmpleado, double sueldo,Area area){
        super(dni,nombre,apellido,telefono,password,sueldo);
        this.area = area;
=======
    public Recepcionista(int dni, String nombre, String apellido, int telefono, int idEmpleado, double sueldo){
        super(dni,nombre,apellido,telefono,idEmpleado,sueldo);
>>>>>>> aba35cc7e30c5bebf509836bbeb2c9e732ade6a6
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
