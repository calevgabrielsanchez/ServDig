package mx.gob.imss.cit.cda.web.app.autorizador.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltroResponsables extends BaseModel {

    private static final long serialVersionUID = -7684784263323259429L;
    private String subDelegacion;
    private String curpResponsable;

    /**
     * @return the subDelegacion
     */
    public String getSubDelegacion() {
        return subDelegacion;
    }

    /**
     * @param subDelegacion
     *            the subDelegacion to set
     */
    public void setSubDelegacion(String subDelegacion) {
        this.subDelegacion = subDelegacion;
    }

    public String getCurpResponsable() {
        return curpResponsable;
    }

    public void setCurpResponsable(String curpResponsable) {
        this.curpResponsable = curpResponsable;
    }

}
