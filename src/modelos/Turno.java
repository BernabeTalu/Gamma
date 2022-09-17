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

    @OneToOne()
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

    @ManyToOne
    @JoinColumn(name = "consultorio")
    private Consultorio consultorio;

    public Turno(){
    }

    public Turno(int idTurno, Paciente paciente, LocalDate fecha, LocalTime hora, double precioTurno, boolean pagado, Consultorio c) {
        this.idTurno = idTurno;
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTurno = precioTurno;
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
}
