package modelos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("P")
@Table(name = "Paciente")
public class Paciente extends Persona implements Serializable{

    @OneToMany(mappedBy = "paciente")
    @Column(name = "historiaClinica")
    private List<Consulta> historiaClinica;

    @Column(name = "coberturaMedica")
    private String coberturaMedica;

    public Paciente(){
    }

    public Paciente(int dni,String nombre, String apellido, int telefono,String coberturaMedica){
        super(dni,nombre,apellido,telefono);
        this.historiaClinica = new ArrayList<>();
        this.coberturaMedica = coberturaMedica;
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
}
