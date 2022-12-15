package modelos.FiltrosTurnos;

import modelos.Turno;

import java.time.LocalDate;

public class FiltroFecha implements Filtro{
    private LocalDate fecha;

    public FiltroFecha(){};

    public FiltroFecha(LocalDate fecha){
        this.fecha = fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean cumple(Turno t) {
        return t.getFecha().isEqual(this.fecha);
    }
}
