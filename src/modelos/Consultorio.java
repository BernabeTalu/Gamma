package modelos;

import modelos.FiltrosTurnos.Filtro;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@DiscriminatorValue("C")
@Table(name = "consultorio")
public class Consultorio extends Elemento implements Serializable {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor")
    private Doctor doctor;

    @OneToMany(mappedBy = "consultorio", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "turnos")
    private List<Turno> turnos;

    @Column(name = "precioTurno")
    private double precioTurno;

    @Column(name = "ocupado")
    private boolean ocupado;

    @ElementCollection
    @Column(name = "coberturasMedicas")
    private List<String> coberturasMedicas;

    @ElementCollection
    @Column(name = "estudiosBrindados")
    private List<String> estudiosBrindados;

    @Column(name = "gananciaMensual")
    private double gananciaMensual;

    public Consultorio(){
        super();
    }

    public Consultorio(String nombre, Doctor doctor, double precioTurno){
        super(nombre);
        this.doctor = doctor;
        this.precioTurno = precioTurno;
        this.turnos = new ArrayList<>();
        this.coberturasMedicas = new ArrayList<>();
        this.estudiosBrindados = new ArrayList<>();
        this.gananciaMensual = 0.0;
        this.ocupado = false;
    }

    public void eliminarCoberturaMedica(String c){
        if(this.coberturasMedicas.contains(c))
            this.coberturasMedicas.remove(c);
    }

    public List<String> getCoberturasMedicas() {
        return new ArrayList<>(this.coberturasMedicas);
    }

    public void setCoberturasMedicas(String nuevaCobertura) {
        if(!this.coberturasMedicas.contains(nuevaCobertura))
            this.coberturasMedicas.add(nuevaCobertura);
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public double getPrecioTurno() {
        return this.precioTurno;
    }

    public void setPrecioTurno(double precioTurno) {
        this.precioTurno = precioTurno;
    }

    public List<String> getEstudiosBrindados() {
        return new ArrayList<>(this.estudiosBrindados);
    }

    public void setEstudiosBrindados(String nuevoEstudio) {
        if(!this.estudiosBrindados.contains(nuevoEstudio))
            this.estudiosBrindados.add(nuevoEstudio);
    }

    private boolean consultarDisponibilidadDeEstudio(String estudio){ //Consulto si un consultorio brinda un estudio en particular.
        if(this.estudiosBrindados.contains(estudio))
            return true;
        return false;
    }

    public boolean isOcupado() { //Chequea si un consultorio esta ocupado en el momento de la consulta.
        for(Turno t : this.turnos){
            if(t.getFecha().equals(LocalDate.now()) && ((LocalTime.now().getHour() - t.getHora().getHour()) >=0) &&((LocalTime.now().getHour() - t.getHora().getHour()) <= 1 )){
                return true;
            }
        }
        return false;
    }

    public double getGananciaMensual() {
        return this.gananciaMensual;
    }

    public void setGananciaMensual(double gananciaMensual) {
        this.gananciaMensual = gananciaMensual;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
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
    public List<Consultorio> getConsultoriosFiltrados(){ //Falta todavia.
        return null;
    }

    @Override
    public boolean turnoOcupado(LocalDate date, LocalTime time) {
        for(Turno t:this.turnos){
            if(t.getFecha().equals(date) && t.getHora().equals(time))
                return true;
        }
        return false;
    }

    @Override
    public void setTurno(Turno t) {
        if(t.getConsultorio().equals(this)) {
           /*
            if (t.getPaciente() != null) {
                if (this.coberturasMedicas.contains(t.getPaciente().getCoberturaMedica())) {
                    this.turnos.add(t); //Seteo el turno para una fecha y hora libres, con chequeo previo desde Area.
                    if (t.isPagado())
                        this.gananciaMensual += t.getPrecioTurno();
                }
            }
         */
        }
        this.turnos.add(t);
    }

    @Override
    public void cancelarTurno(Turno t){
        if(this.turnos.contains(t)){
            this.turnos.remove(t);
            if(t.isPagado())
                this.gananciaMensual -= t.getPrecioTurno();
        }
    }

    @Override
    public List<Elemento> obtenerElementos() {
        List<Elemento> retorno = new ArrayList<>();
        retorno.add(this);
        return retorno;
    }

    @Override
    public List<Turno> getTurnosFiltrados (Filtro filtro) {
        List<Turno> turnosSalida = new ArrayList<>();
        for(Turno turno:this.turnos)
            if (filtro.cumple(turno))
                turnosSalida.add(turno);
        return turnosSalida;
    }

}
