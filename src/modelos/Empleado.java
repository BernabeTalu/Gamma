package modelos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("E")
@Inheritance(strategy = InheritanceType.JOINED) //Defino que tipo de herencia se utiliza
@DiscriminatorColumn(name = "tipoEmpleado")
@Table(name = "Empleado")
public class Empleado extends Persona implements Serializable{

    @Column(name = "sueldo")
    private double sueldo;

    @Column(name = "password")
    private String password;

    public Empleado(){
        super();
    }

    public Empleado(int dni, String nombre, String apellido, int telefono, String password, double sueldo){
        super(dni,nombre,apellido,telefono);
        this.password = password;
        this.sueldo = sueldo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getPassword() {
        return this.password;
    }

    public double getSueldo() {
        return this.sueldo;
    }
}
