package mx.gob.imss.cit.cda.web.app.responsable.model;

public class DocumentosNss extends Documentos {

    private static final long serialVersionUID = 4426472817402420551L;

    private String nss;
    private String origen;
    private String observacion;

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    @Override
    public String toString() {
        super.toString();
        return "DocumentosNss [nss=" + nss + ", origen=" + origen + "]";
    }

}
