package mx.gob.imss.cit.cda.web.app.common.model;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltroDocumentoProbatorio extends BaseModel {

    private static final long serialVersionUID = -4752741090151537233L;

    private String tipoSolicitante;
    private String tipoBeneficiario;
    private boolean defuncion;
    private int idTipo;
    private int idDocumento;
    
    public String getTipoSolicitante() {
        return tipoSolicitante;
    }
    public void setTipoSolicitante(String tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }
    public String getTipoBeneficiario() {
        return tipoBeneficiario;
    }
    public void setTipoBeneficiario(String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }
    public boolean isDefuncion() {
        return defuncion;
    }
    public void setDefuncion(boolean defuncion) {
        this.defuncion = defuncion;
    }
    public int getIdTipo() {
        return idTipo;
    }
    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }
    public int getIdDocumento() {
        return idDocumento;
    }
    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }
    
    
}
