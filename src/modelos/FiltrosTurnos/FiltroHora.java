package modelos.FiltrosTurnos;

import modelos.Turno;

import java.time.LocalDate;
import java.time.LocalTime;

public class FiltroHora implements Filtro {

        private LocalTime hora;

        public FiltroHora(){};

        public FiltroHora(LocalTime hora){
            this.hora = hora;
        }

        public void setHora(LocalTime hora) {
            this.hora = hora;
        }

        @Override
        public boolean cumple(Turno t) {
            return t.getHora().compareTo(this.hora) == 0;
        }

    }
