package modelos.FiltrosTurnos;

import modelos.Turno;

import java.time.LocalDate;
import java.time.LocalTime;

public class FiltroFechaIgual implements Filtro {

    private LocalTime hora;
    private LocalDate fecha;

    public FiltroFechaIgual(LocalDate fecha, LocalTime hora) {
        this.fecha = fecha;
        this.hora = hora;
    }


    @Override
    public boolean cumple(Turno t) {
        if (t.getFecha().isEqual(this.fecha)) {
            if (t.getHora().getHour() == this.hora.getHour()) {
                if (t.getHora().getMinute() == 30) {
                    if (this.hora.getMinute() > 30)
                        return true;
                    else
                        return false;
                }
                if (t.getHora().getMinute() == 0) {
                    if (this.hora.getMinute() < 30)
                        return true;
                    else
                        return false;
                }
            }
            return false;
        }
        return false;
    }
}