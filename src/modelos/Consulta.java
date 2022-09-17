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
    @Column(name = "IdCOnsulta")
    private int idConsulta;

    @Column(name = "Date")
    private LocalDate fecha;

    @Column(name = "Descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "paciente")
    private Paciente paciente;

    public Consulta(){
    }

    public Consulta(int idConsulta, LocalDate fecha, String descripcion){
        this.idConsulta = idConsulta;
        this.fecha = fecha;
        this.descripcion = descripcion;
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
}
