package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

public class DomicilioAsignacionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String subdelegacion;
    private String calle;
    private String numero;
    private String colonia;
    private String cp;
    private String municipio;
    private String entidadFederativa;

    public String getSubdelegacion() {
        return subdelegacion;
    }

    public void setSubdelegacion(String subdelegacion) {
        this.subdelegacion = subdelegacion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEntidadFederativa() {
        return entidadFederativa;
    }

    public void setEntidadFederativa(String entidadFederativa) {
        this.entidadFederativa = entidadFederativa;
    }

}
