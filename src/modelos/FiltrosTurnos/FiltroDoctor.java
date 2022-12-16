package modelos.FiltrosTurnos;

import modelos.Doctor;
import modelos.Turno;

public class FiltroDoctor implements Filtro {
    private Doctor doctor;

    public FiltroDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    public FiltroDoctor(){};

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    @Override
    public boolean cumple(Turno t) {
        if(t.getConsultorio().getDoctor().equals(doctor.getDni())){
            return true;
        }
        return false;
    }
}
