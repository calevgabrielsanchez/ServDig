package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoriaLaboralVO implements Serializable {

    private static final long serialVersionUID = 1206708659857021257L;
    private String nombrePatron;
    private String entidadFederativa;
    private String claveEntidad;
    private String fechaInscripcion;
    private String fechaBaja;
    private String numeroRegistroPatronal;
    private String domicilioEmpresa;
    private String actividadEmpresa;

    public String getNombrePatron() {
        return nombrePatron;
    }

    public void setNombrePatron(String nombrePatron) {
        this.nombrePatron = nombrePatron;
    }

    public String getEntidadFederativa() {
        return entidadFederativa;
    }

    public void setEntidadFederativa(String entidadFederativa) {
        this.entidadFederativa = entidadFederativa;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getNumeroRegistroPatronal() {
        return numeroRegistroPatronal;
    }

    public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
        this.numeroRegistroPatronal = numeroRegistroPatronal;
    }

    public String getDomicilioEmpresa() {
        return domicilioEmpresa;
    }

    public void setDomicilioEmpresa(String domicilioEmpresa) {
        this.domicilioEmpresa = domicilioEmpresa;
    }

    public String getActividadEmpresa() {
        return actividadEmpresa;
    }

    public void setActividadEmpresa(String actividadEmpresa) {
        this.actividadEmpresa = actividadEmpresa;
    }

    @Override
    public String toString() {
        return "HistoriaLaboralVO [nombrePatron=" + nombrePatron
                + ", entidadFederativa=" + entidadFederativa
                + ", fechaInscripcion=" + fechaInscripcion + ", fechaBaja="
                + fechaBaja + "]";
    }

    public String getClaveEntidad() {
        return claveEntidad;
    }

    public void setClaveEntidad(String claveEntidad) {
        this.claveEntidad = claveEntidad;
    }

}
