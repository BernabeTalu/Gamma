package modelos.FiltrosTurnos;

import modelos.Turno;

public class FiltroAsignado implements Filtro{

    public FiltroAsignado(){};


    @Override
    public boolean cumple(Turno t) {
        return t.isAsignado();
    }
}
