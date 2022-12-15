package modelos.FiltrosTurnos;

import modelos.Turno;

public class FiltroAsignado implements Filtro{
    private boolean asignado;

    public FiltroAsignado(boolean asignado){
        this.asignado = asignado;
    };

    public boolean isAsignado() {
        return asignado;
    }

    @Override
    public boolean cumple(Turno t) {
        return t.isAsignado() == this.asignado;
    }
}
