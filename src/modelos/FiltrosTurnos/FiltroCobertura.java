package modelos.FiltrosTurnos;

import modelos.Turno;

public class FiltroCobertura implements Filtro {
    private String cobertura;

    public FiltroCobertura(){};

    public FiltroCobertura(String cobertura){
        this.cobertura = cobertura;
    }

    public void setCobertura(String cobertura){
        this.cobertura = cobertura;
    }

    @Override
    public boolean cumple(Turno t) {
        return t.getConsultorio().getCoberturasMedicas().contains(this.cobertura);
    }
}
