package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.retroactividad.vo;

import java.util.List;

public class FilaAnioVigencia {
    private int anio;
    private List<MesEstadoRetroactividad> meses;

    public FilaAnioVigencia(int anio, List<MesEstadoRetroactividad> meses) {
        this.anio = anio;
        this.meses = meses;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public List<MesEstadoRetroactividad> getMeses() {
        return meses;
    }

    public void setMeses(List<MesEstadoRetroactividad> meses) {
        this.meses = meses;
    }
}
