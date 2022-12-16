package modelos;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Consulta")
public class Consulta {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="consultaid_generator")
    @SequenceGenerator(name = "consultaid_generator",initialValue=1,allocationSize=1,sequenceName="consultaid_seq")
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

    public Consulta( LocalDate fecha, String descripcion, Paciente p,int dniDoctor){
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
