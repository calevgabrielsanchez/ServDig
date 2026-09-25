package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudJsonVO implements Serializable {

    private static final long serialVersionUID = 5564785705499763078L;
    private String nssInvolucrados;
    private String origen;
    private String responsableRegistro;

    public String getNssInvolucrados() {
        return nssInvolucrados;
    }

    public void setNssInvolucrados(String nssInvolucrados) {
        this.nssInvolucrados = nssInvolucrados;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getResponsableRegistro() {
        return responsableRegistro;
    }

    public void setResponsableRegistro(String responsableRegistro) {
        this.responsableRegistro = responsableRegistro;
    }

}
