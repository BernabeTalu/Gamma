package modelos.FiltrosTurnos;

import modelos.Turno;

import java.util.ArrayList;
import java.util.List;

public class FiltroAnd implements Filtro{

    private List<Filtro> filtros;

    public FiltroAnd(List<Filtro> filtros){
        this.filtros = filtros;
    }

    public FiltroAnd(){
        this.filtros = new ArrayList<>();
    };

    public void agregarFiltro(Filtro f){
        this.filtros.add(f);
    }

    public List<Filtro> getFiltros(){
        return this.filtros;
    }

    public boolean tieneFiltrosCargados(){
        return this.filtros.size() > 0;
    }

    public void eliminarFiltro(Filtro f){
        this.filtros.remove(f);
    }

    public void  setFiltros(List<Filtro> filtros) {
        this.filtros = filtros;
    }

    public boolean contieneFiltro(Filtro f){
        return this.filtros.contains(f);
    }

    @Override
    public boolean cumple(Turno t) {
       for (Filtro f:this.filtros){
           if(!f.cumple(t)){
               return false;
           }
       }
       return true;
    }
}
