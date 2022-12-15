package modelos.FiltrosTurnos;

import modelos.Consultorio;
import modelos.Turno;

public class FiltroConsultorio implements Filtro{

    private Consultorio consultorio;


    public FiltroConsultorio(Consultorio consultorio){
        this.consultorio = consultorio;
    }

    public FiltroConsultorio(){
        this.consultorio = null;
    };

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    @Override
    public boolean cumple(Turno t) {
        return (t.getConsultorio() == this.consultorio);
    }
}
