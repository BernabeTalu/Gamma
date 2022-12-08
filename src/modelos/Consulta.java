package modelos;

import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Consulta")
public class Consulta {
    @Id
    @Column(name = "IdConsulta")
    private int idConsulta;

    @Column(name = "Date")
    private LocalDate fecha;

    @Column(name = "Descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dni_paciente")
    private Paciente paciente;

    @Column(name = "Doctor")
    private int dniDoctor;


    public Consulta(){
    }

    public Consulta(int idConsulta, LocalDate fecha, String descripcion, Paciente p,int dniDoctor){
        this.idConsulta = idConsulta;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.paciente = p;
        this.dniDoctor = dniDoctor;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDniDoctor() {
        return dniDoctor;
    }

    public int getPaciente() {
        return paciente.getDni();
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setDniDoctor(int dniDoctor) {
        this.dniDoctor = dniDoctor;
    }
}
