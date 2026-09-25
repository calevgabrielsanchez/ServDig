package mx.gob.imss.cit.cda.web.cuentaindividual.vo;

import java.util.List;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Modelo Conjunto de Registros Patronales (Cuentas individiales) por un NSS
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NssCuentaIndividual extends BaseModel{
    

    private static final long serialVersionUID = -3951180553320842449L;
    
    private String nss;
    private String tipoRegularizacion;
    private List<CuentaIndividual> listaCuentaIndividual;

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public List<CuentaIndividual> getListaCuentaIndividual() {
        return listaCuentaIndividual;
    }

    public void setListaCuentaIndividual(List<CuentaIndividual> listaCuentaIndividual) {
        this.listaCuentaIndividual = listaCuentaIndividual;
    }

    public String getTipoRegularizacion() {
        return tipoRegularizacion;
    }

    public void setTipoRegularizacion(String tipoRegularizacion) {
        this.tipoRegularizacion = tipoRegularizacion;
    }

}
