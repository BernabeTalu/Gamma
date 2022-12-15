package modelos.FiltrosTurnos;

import modelos.Turno;

public class FiltroEstudio implements Filtro{

    private String estudio;

    public FiltroEstudio(String estudio){
        this.estudio = estudio;
    }


    @Override
    public boolean cumple(Turno t) {
        if(t.getConsultorio().getEstudiosBrindados().contains(this.estudio)){
            return true;
        }
        return false;
    }
}
