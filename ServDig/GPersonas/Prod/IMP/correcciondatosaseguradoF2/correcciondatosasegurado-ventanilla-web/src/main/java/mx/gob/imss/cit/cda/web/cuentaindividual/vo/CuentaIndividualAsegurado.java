package mx.gob.imss.cit.cda.web.cuentaindividual.vo;

import java.util.List;
import mx.gob.imss.cit.cda.web.support.model.BaseModel;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Modelo que representa la cuenta individual de un tramite correcion 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaIndividualAsegurado extends BaseModel {

    private static final long serialVersionUID = 3404555453443832391L;
    
    private List<NssCuentaIndividual> registrosPatronalesCertificador;
    private List<NssCuentaIndividual> registrosPatronalesAsociados;
    private List<NssCuentaIndividual> registrosPatronalesNoPertenece;

    public List<NssCuentaIndividual> getRegistrosPatronalesCertificador() {
        return registrosPatronalesCertificador;
    }

    public void setRegistrosPatronalesCertificador(List<NssCuentaIndividual> registrosPatronalesCertificador) {
        this.registrosPatronalesCertificador = registrosPatronalesCertificador;
    }

    public List<NssCuentaIndividual> getRegistrosPatronalesAsociados() {
        return registrosPatronalesAsociados;
    }

    public void setRegistrosPatronalesAsociados(List<NssCuentaIndividual> registrosPatronalesAsociados) {
        this.registrosPatronalesAsociados = registrosPatronalesAsociados;
    }

    public List<NssCuentaIndividual> getRegistrosPatronalesNoPertenece() {
        return registrosPatronalesNoPertenece;
    }

    public void setRegistrosPatronalesNoPertenece(List<NssCuentaIndividual> registrosPatronalesNoPertenece) {
        this.registrosPatronalesNoPertenece = registrosPatronalesNoPertenece;
    }
    
}
