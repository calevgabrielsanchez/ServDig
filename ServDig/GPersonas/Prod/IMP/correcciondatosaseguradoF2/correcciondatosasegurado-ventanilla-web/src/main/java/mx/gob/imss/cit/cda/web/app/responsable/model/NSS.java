/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import java.util.List;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 * 
 * @author antonio
 */
public class NSS extends BaseModel {

    private static final long serialVersionUID = 1L;
    private Long tramiteId;
    private String nss;
    // Tipo de NSS en la correccion
    private String tipoCorreccion;
    private TipoNSSCorreccion tipoNSS;
    private boolean convencional;
    private TipoRegularizacionNSS grupoCorreccion;
    private InformacionRENAPO informacionRENAPO;
    private InformacionRENAPO informacionBDTU;
    private List<InformacionRENAPO> informacionFuentesNSS;
    private String origenCaptura;
    
    
    

    public Long getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(Long tramiteId) {
        this.tramiteId = tramiteId;
    }

    /**
     * @return the nss
     */
    public String getNss() {
        return nss;
    }

    /**
     * @param nss
     *            the nss to set
     */
    public void setNss(String nss) {
        this.nss = nss;
    }

    /**
     * @return the tipoCorreccion
     */
    public String getTipoCorreccion() {
        return tipoCorreccion;
    }

    /**
     * @param tipoCorreccion
     *            the tipoCorreccion to set
     */
    public void setTipoCorreccion(String tipoCorreccion) {
        this.tipoCorreccion = tipoCorreccion;
    }

    /**
     * @return the convencional
     */
    public boolean isConvencional() {
        return convencional;
    }

    /**
     * @param convencional
     *            the convencional to set
     */
    public void setConvencional(boolean convencional) {
        this.convencional = convencional;
    }

    /**
     * @return the grupoCorreccion
     */
    public TipoRegularizacionNSS getGrupoCorreccion() {
        return grupoCorreccion;
    }

    /**
     * @param grupoCorreccion
     *            the grupoCorreccion to set
     */
    public void setGrupoCorreccion(TipoRegularizacionNSS grupoCorreccion) {
        this.grupoCorreccion = grupoCorreccion;
    }

    /**
     * @return the informacionRENAPO
     */
    public InformacionRENAPO getInformacionRENAPO() {
        return informacionRENAPO;
    }

    /**
     * @param informacionRENAPO
     *            the informacionRENAPO to set
     */
    public void setInformacionRENAPO(InformacionRENAPO informacionRENAPO) {
        this.informacionRENAPO = informacionRENAPO;
    }

    /**
     * @return the informacionBDTU
     */
    public InformacionRENAPO getInformacionBDTU() {
        return informacionBDTU;
    }

    /**
     * @param informacionBDTU
     *            the informacionBDTU to set
     */
    public void setInformacionBDTU(InformacionRENAPO informacionBDTU) {
        this.informacionBDTU = informacionBDTU;
    }

    public List<InformacionRENAPO> getInformacionFuentesNSS() {
        return informacionFuentesNSS;
    }

    public void setInformacionFuentesNSS(
            List<InformacionRENAPO> informacionFuentesNSS) {
        this.informacionFuentesNSS = informacionFuentesNSS;
    }

    public TipoNSSCorreccion getTipoNSS() {
        return tipoNSS;
    }

    public void setTipoNSS(TipoNSSCorreccion tipoNSS) {
        this.tipoNSS = tipoNSS;
    }

    public String getOrigenCaptura() {
        return origenCaptura;
    }

    public void setOrigenCaptura(String origenCaptura) {
        this.origenCaptura = origenCaptura;
    }

}
