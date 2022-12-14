package modelos;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

@Entity
@DiscriminatorValue("A")
@Table(name = "area")
public class Area extends Elemento implements Serializable{
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn (name = "idRecepcionista")
    private Recepcionista recepcionista;

    @ElementCollection(fetch = FetchType.LAZY)
    //@Cascade({org.hibernate.annotations.CascadeType.REMOVE})
    @Column(name = "idElemento")
    private List<Elemento> componentes;

    public Area(){
        super();
    }

    public Area(int id, String nombre, Recepcionista recepcionista){
        super(id,nombre);
        if(recepcionista != null) {
            recepcionista.setArea(this);
        }
        this.recepcionista = recepcionista;
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
    public boolean turnoOcupado(LocalDate date, LocalTime time) {
        for(Elemento e: this.componentes)
            if(e.turnoOcupado(date,time))
                return true;
        return false;
    }

    @Override
    public void setTurno(Turno t){ //Los turnos duran hora siempre.
        for(Elemento e : this.componentes){
            if(!e.turnoOcupado(t.getFecha(),t.getHora()))
                e.setTurno(t);
        }
    }

    @Override
    public void cancelarTurno(Turno t){
        for(Elemento e : this.componentes){
            e.cancelarTurno(t);
        }
    }

    @Override
    public List<Elemento> obtenerElementos() {
        List<Elemento> elementosArea = new ArrayList<>();
        for(Elemento e: this.componentes){
            elementosArea.addAll(e.obtenerElementos());
        }
        return elementosArea;
    }

    public void setRecepcionista(Recepcionista recepcionista) {
        this.recepcionista = recepcionista;
    }

    public Recepcionista getRecepcionista() {
        return recepcionista;
    }

}
