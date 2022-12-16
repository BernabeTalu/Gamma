package modelos.FiltrosTurnos;

import modelos.Turno;

public class FiltroObraSocial implements Filtro{

    private String obraSocial;

    public FiltroObraSocial(String obraSocial){
        this.obraSocial = obraSocial;
    }

    public FiltroObraSocial(){};

    public void setObraSocial(String obraSocial){
        this.obraSocial = obraSocial;
    }

    @Override
    public boolean cumple(Turno t) {
        return t.getConsultorio().getCoberturasMedicas().contains(this.obraSocial);
    }
}
