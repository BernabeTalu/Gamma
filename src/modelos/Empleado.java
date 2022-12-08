package modelos;

import javafx.scene.control.Button;

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

    @Column(name = "tipo")
    private String tipo;

    public Empleado(){
        super();
    }

    public Empleado(int dni, String nombre, String apellido, int telefono, String password,String tipo, double sueldo){
        super(dni,nombre,apellido,telefono);
        this.password = password;
        this.tipo = tipo;
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

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}
}
