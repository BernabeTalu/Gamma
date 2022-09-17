package modelos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("C")
@Table(name = "consultorio")
public class Consultorio extends Elemento implements Serializable {
    @Column(name = "dniDoctor")
    private int dniDoctor;

    @OneToMany(mappedBy = "consultorio")
    @Column(name = "turnos")
    private List<Turno> turnos;

    @Column(name = "precioTurno")
    private double precioTurno;

    @Column(name = "ocupado")
    private boolean ocupado;

    //@Column(name = "coberturasMedicas")
    //private List<String> coberturasMedicas;

    //@Column(name = "estudiosBrindados")
    //private List<String> estudiosBrindados;

    public Consultorio(){
    }

    public Consultorio(int id, String nombre, int dniDoctor, double precioTurno){
        super(id,nombre);
        this.dniDoctor = dniDoctor;
        this.precioTurno = precioTurno;
        this.turnos = new ArrayList<>();
        //this.coberturasMedicas = new ArrayList<>();
        //this.estudiosBrindados = new ArrayList<>();
    }

    public void eliminarCoberturaMedica(String c){
        //if(this.coberturasMedicas.contains(c))
          //  this.coberturasMedicas.remove(c);
    }

    public List<String> getCoberturasMedicas() {
        //return new ArrayList<>(this.coberturasMedicas);
        return null;
    }

    public void setCoberturasMedicas(String nuevaCobertura) {
        //if(!this.coberturasMedicas.contains(nuevaCobertura))
          //  this.coberturasMedicas.add(nuevaCobertura);
    }

    public int getDoctor() {
        return this.dniDoctor;
    }

    public void setDoctor(int dniDoctor) {
        this.dniDoctor = dniDoctor;
    }

    public double getPrecioTurno() {
        return this.precioTurno;
    }

    public void setPrecioTurno(double precioTurno) {
        this.precioTurno = precioTurno;
    }

    public List<String> getEstudiosBrindados() {
        //return new ArrayList<>(this.estudiosBrindados);
        return null;
    }

    public void setEstudiosBrindados(String nuevoEstudio) {
        //if(!this.estudiosBrindados.contains(nuevoEstudio))
            //this.estudiosBrindados.add(nuevoEstudio);

    }

    private boolean consultarDisponibilidadDeEstudio(String estudio){ //Consulto si un consultorio brinda un estudio en particular.
        //if(this.estudiosBrindados.contains(estudio))
            //return true;
        return false;
    }

    @Override
    public List<Turno> getTurnos() {
        return new ArrayList<>(this.turnos);
    }

    @Override
    public List<Consultorio> getConsultorios(){
        List<Consultorio> salida = new ArrayList<>();
        salida.add(this);
        return salida;
    }

    @Override
    public List<Consultorio> getConsultoriosFiltrados(){
        return null;
    }

    @Override
    public void setTurno(Turno t) {
        if(!this.turnos.contains(t)) //Corroboro que no exista un turno asignado para un dia y hora determinadas.
            this.turnos.add(t);
    }

    @Override
    public void cancelarTurno(Turno t){
        if(this.turnos.contains(t)){
            this.turnos.remove(t);
        }
    }

    @Override
    public boolean isOcupado() {
        return this.ocupado;
    }

    @Override
    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
}
