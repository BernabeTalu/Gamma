package modelos;

import javax.persistence.*;
import javax.print.Doc;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("D")
@Table(name = "doctor")
public class Doctor extends Empleado implements Serializable{
    @Column(name = "matricula")
    private int matricula;

    @OneToOne(mappedBy = "doctor",fetch = FetchType.LAZY)//Un doctor solo ejerce en un consultorio.
    private Consultorio consultorio;

    @ElementCollection
    @Column(name = "especialidades")
    private List<String> especialidades;

    @ManyToMany(mappedBy = "doctores")
    private List<Paciente> pacientes;

    public Doctor(){
        super();
    }


    public Doctor(int dni, String nombre, String apellido, int telefono,String password, int idEmpleado, double sueldo, int matricula){
        super(dni,nombre,apellido,telefono,password,sueldo);

        this.matricula = matricula;
        this.especialidades = new ArrayList<>();
        this.pacientes = new ArrayList<>();
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getMatricula() {
        return matricula;
    }

    public List<String> getEspecialidades(){
        return new ArrayList<>(this.especialidades);
    }

    public void setEspecialidades(String e){
        this.especialidades.add(e);
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public void agregarPaciente(Paciente p){
        if(!this.pacientes.contains(p))
            this.pacientes.add(p);
    }

    public void eliminarPaciente(Paciente p){
        if(this.pacientes.contains(p))
            this.pacientes.remove(p);
    }
}
