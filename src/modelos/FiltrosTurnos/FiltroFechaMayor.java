package modelos.FiltrosTurnos;

import modelos.Turno;

import java.time.LocalDate;
import java.time.LocalTime;

public class FiltroFechaMayor implements Filtro {

    private LocalTime hora;
    private LocalDate fecha;

    public FiltroFechaMayor(LocalDate fecha, LocalTime hora){
        this.fecha = fecha;
        this.hora = hora;
    }


    @Override
    public boolean cumple(Turno t) {
        return ((t.getFecha().isAfter(this.fecha)) || (t.getFecha().isEqual(this.fecha) && t.getHora().isAfter(this.hora)));
    }
}
