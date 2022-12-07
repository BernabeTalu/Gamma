package modelos;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turno")
public class Turno {
    @Id
    @Column(name = "idTurno")
    private int idTurno;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}) //Si borro un turno, no necesariamente tengo q borrar el paciente-
    @JoinColumn(name = "paciente")
    private Paciente paciente;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "precioTurno")
    private double precioTurno;

    @Column(name = "pagado")
    private boolean pagado;

    @ManyToOne(fetch = FetchType.LAZY) //Gracias a este fetch, solo se carga el consultorio del turno cuando haga un getConsultorio.
    @JoinColumn(name = "consultorio")
    private Consultorio consultorio;

    public Turno(){
    }

    public Turno(int idTurno, Paciente paciente, LocalDate fecha, LocalTime hora, double precioTurno, boolean pagado, Consultorio c) {
        this.idTurno = idTurno;
        this.paciente = paciente;
        if(!paciente.getDoctores().contains(c.getDoctor())) {
            paciente.setDoctores(c.getDoctor()); //Seteo como nuevo doctor del paciente, el asignado en el turno.
            c.getDoctor().agregarPaciente(paciente); //Y viceversa.
        }
        this.fecha = fecha;
        this.hora = hora;
        this.precioTurno = this.checkPrecioTurno(c,precioTurno); //Restrinjo el precio del turno al del consultorio al que pertenece.
        this.pagado = pagado;
        this.consultorio = c;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public double getPrecioTurno() {
        return precioTurno;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setPrecioTurno(double precioTurno) {
        this.precioTurno = precioTurno;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public double checkPrecioTurno(Consultorio c,double precio){
        if(precio != c.getPrecioTurno())
            return c.getPrecioTurno();
        return precio;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "idTurno=" + idTurno +
                ", paciente=" + paciente +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", precioTurno=" + precioTurno +
                ", pagado=" + pagado +
                ", consultorio=" + consultorio +
                '\n'+
                '}';
    }
}
