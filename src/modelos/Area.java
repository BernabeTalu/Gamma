package modelos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("A")
@Table(name = "area")
public class Area extends Elemento implements Serializable{
    @Column(name = "idRecepcionista")
    private int dniRecepcionista;

    @ElementCollection
    @Column(name = "idElemento")
    private List<Elemento> componentes;

    public Area(){
    }

    public Area(int id, String nombre, int dniRecepcionista){
        super(id,nombre);
        this.dniRecepcionista = dniRecepcionista;
        this.componentes = new ArrayList<>();
    }

    public void setComponente(Elemento e){
        if(!this.componentes.contains(e))
            this.componentes.add(e);
    }

    public void eliminarComponente(Elemento e){
        if(this.componentes.contains(e))
            this.componentes.remove(e);
    }

    public List<Elemento> getComponentes(){ //Devuelve todos los elementos dentro del Area.
        List<Elemento> salida = new ArrayList<>(this.componentes);
        return salida;
    }

    @Override
    public List<Turno> getTurnos(){
        List<Turno> turnosArea = new ArrayList<>();
        for(Elemento e: this.componentes){
            turnosArea.addAll(e.getTurnos());
        }
        return turnosArea;
    }

    @Override
    public List<Consultorio> getConsultorios(){
        List<Consultorio> consultoriosArea = new ArrayList<>();
        for(Elemento e: this.componentes){
            consultoriosArea.addAll(e.getConsultorios());
        }
        return consultoriosArea;
    }

    @Override
    public List<Consultorio> getConsultoriosFiltrados(){
        return null;
    }

    @Override
    public void setTurno(Turno t){

    }

    @Override
    public void cancelarTurno(Turno t){

    }

    @Override
    public void setOcupado(boolean ocupado){

    }

    @Override
    public boolean isOcupado(){
        return false; //queda asi hasta que defina el comportamiento.
    }
}
