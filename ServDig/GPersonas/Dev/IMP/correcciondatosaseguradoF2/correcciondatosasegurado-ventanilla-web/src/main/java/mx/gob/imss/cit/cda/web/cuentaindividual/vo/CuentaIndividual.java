package mx.gob.imss.cit.cda.web.cuentaindividual.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 * Modelo que representa un registro patronal 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaIndividual extends BaseModel { //

    private static final long serialVersionUID = 1L;
    
    private Long idTramite;
    private String nombreDelegacionOrigen;
    private String nombreRP;
    private String numeroRP;
    private int claveCiz;
    private int indice;
    private String nssDestino;
    private List<String> listaNss;

    public String getNombreDelegacionOrigen() {
        return nombreDelegacionOrigen;
    }

    public void setNombreDelegacionOrigen(String nombreDelegacionOrigen) {
        this.nombreDelegacionOrigen = nombreDelegacionOrigen;
    }

    public String getNombreRP() {
        return nombreRP;
    }

    public void setNombreRP(String nombreRP) {
        this.nombreRP = nombreRP;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public String getNssDestino() {
        return nssDestino;
    }

    public void setNssDestino(String nssDestino) {
        this.nssDestino = nssDestino;
    }

    private String registroPatronal;

    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }

    private int claveDelegacionOrigen;

    public int getClaveDelegacionOrigen() {
        return claveDelegacionOrigen;
    }

    public void setClaveDelegacionOrigen(int claveDelegacionOrigen) {
        this.claveDelegacionOrigen = claveDelegacionOrigen;
    }

    public int getClaveCiz() {
        return claveCiz;
    }

    public void setClaveCiz(int claveCiz) {
        this.claveCiz = claveCiz;
    }

    public List<String> getListaNss() {
        return listaNss;
    }

    public void setListaNss(List<String> listaNss) {
        this.listaNss = listaNss;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getNumeroRP() {
        return numeroRP;
    }

    public void setNumeroRP(String numeroRP) {
        this.numeroRP = numeroRP;
    }
    
}
