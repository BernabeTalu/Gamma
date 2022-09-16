package modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Persona")
public class Persona {
    @Id
    @Column(name = "dni")
    private int dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "telefono")
    private int telefono;



    public Persona() {
    }

    public Persona(int dni, String nombre, String apellido, int telefono) {

        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public int getDni() {
        return dni;
    }

    public Persona setDni(int dni) {
        this.dni = dni;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Persona setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellido() {
        return apellido;
    }

    public Persona setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public int getTelefono() {
        return telefono;
    }

    public Persona setTelefono(int telefono) {
        this.telefono = telefono;
        return this;
    }
}
