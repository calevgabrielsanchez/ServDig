package mx.gob.imss.ctirss.delta.model.derechohabiente;

public class CabezaGrupoFamiliarTE extends CabezaGrupoFamiliar {

    private static final long serialVersionUID = 2997580595005248172L;

    private boolean articulo82;
    private boolean articulo83;
    private boolean articulo84;
    private boolean articulo85;
    private String tiemposEspera;

    public boolean isArticulo82() {

        return articulo82;
    }

    public void setArticulo82(boolean articulo82) {

        this.articulo82 = articulo82;
    }

    public boolean isArticulo83() {

        return articulo83;
    }

    public void setArticulo83(boolean articulo83) {

        this.articulo83 = articulo83;
    }

    public boolean isArticulo84() {

        return articulo84;
    }

    public void setArticulo84(boolean articulo84) {

        this.articulo84 = articulo84;
    }

    public boolean isArticulo85() {

        return articulo85;
    }

    public void setArticulo85(boolean articulo85) {

        this.articulo85 = articulo85;
    }

    public String getTiemposEspera() {

        return tiemposEspera;
    }

    public void setTiemposEspera(String tiemposEspera) {

        this.tiemposEspera = tiemposEspera;
    }
}
