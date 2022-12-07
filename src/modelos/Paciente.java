package modelos;

import org.hibernate.annotations.OnDelete;
import javax.persistence.*;
import javax.print.Doc;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("P")
@Table(name = "Paciente")
public class Paciente extends Persona implements Serializable{

    @OneToMany(mappedBy = "paciente", cascade = {CascadeType.ALL})
    @Column(name = "historia")
    private List<Consulta> historiaClinica;

    @Column(name = "coberturaMedica")
    private String coberturaMedica;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Doctor> doctores;

    public Paciente(){
        super();
    }

    public Paciente(int dni,String nombre, String apellido, int telefono,String coberturaMedica){
        super(dni,nombre,apellido,telefono);
        this.historiaClinica = new ArrayList<>();
        this.coberturaMedica = coberturaMedica;
        this.doctores = new ArrayList<>();
    }

    public void setCoberturaMedica(String coberturaMedica) {
        this.coberturaMedica = coberturaMedica;
    }

    public String getCoberturaMedica() {
        return coberturaMedica;
    }

    public List<Consulta> getHistoriaClinica(){
        return new ArrayList<>(this.historiaClinica);
    }

    public void setConsultaHistoriaClinica(Consulta c){
        this.historiaClinica.add(c);
    }

    public void setDoctores(Doctor doctor) {
        if(!this.doctores.contains(doctor))
            this.doctores.add(doctor);
    }

    public List<Doctor> getDoctores() {
        return this.doctores;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "historiaClinica=" + historiaClinica +
                ", coberturaMedica='" + coberturaMedica + '\'' +
                ", doctores=" + doctores +
                '}';
    }
}
