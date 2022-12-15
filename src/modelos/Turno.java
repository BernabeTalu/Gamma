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
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="empid_generator")
    @SequenceGenerator(name = "empid_generator",initialValue=1,allocationSize=1,sequenceName="empid_seq")
    @Column(name = "idTurno")
    private Integer idTurno;

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

    @Column(name = "asignado")
    private boolean asignado;

    @ManyToOne(fetch = FetchType.LAZY) //Gracias a este fetch, solo se carga el consultorio del turno cuando haga un getConsultorio.
    @JoinColumn(name = "consultorio")
    private Consultorio consultorio;

    public Turno(){
    }

    public Turno( Paciente paciente, LocalDate fecha, LocalTime hora, double precioTurno, boolean pagado, Consultorio c, boolean asignado) {
        this.paciente = paciente;
        if (paciente != null) {
            if (!paciente.getDoctores().contains(c.getDoctor())) {
                paciente.setDoctores(c.getDoctor()); //Seteo como nuevo doctor del paciente, el asignado en el turno.
                c.getDoctor().agregarPaciente(paciente); //Y viceversa.
            }
        }
        this.fecha = fecha;
        this.hora = hora;
        this.precioTurno = this.checkPrecioTurno(c,precioTurno); //Restrinjo el precio del turno al del consultorio al que pertenece.
        this.pagado = pagado;
        this.consultorio = c;
        this.asignado = asignado;
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
    public boolean getPagado() {
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

    public String getDoctor(){
        if (this.getConsultorio().getDoctor() != null)
            return this.getConsultorio().getDoctor().getApellido();
        else
            return "No asignado";
    }

    public double getPrecio(){
        return this.getConsultorio().getPrecioTurno();
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

    public boolean isAsignado() {
        return asignado;
    }

    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }
}
