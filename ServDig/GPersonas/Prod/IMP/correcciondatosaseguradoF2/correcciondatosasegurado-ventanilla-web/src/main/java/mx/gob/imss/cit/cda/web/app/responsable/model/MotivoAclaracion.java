/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 *
 * @author antonio
 */
public class MotivoAclaracion extends BaseModel {
    private boolean cobroIncapacidad;
    private boolean pension;
    private boolean retiroDesempleo;
    private boolean registroBeneficiarios;
    private boolean adscripcionUMF;
    private boolean cambioUMF;
    private boolean gastosMatrimonio;
    private boolean gastosFuneral;
    private boolean obtenerCredito;
    private boolean conclusionCredito;
    private boolean prorrogaReestructuraCredito;
    private boolean descuentoIndebidoCredito;
    private boolean registroAfore;
    private boolean aclaracionSaldoSupuestaVivienda;
    private boolean otro;
    private String numeroCredito;
    private String otroMotivo;

    /**
     * @return the cobroIncapacidad
     */
    public boolean isCobroIncapacidad() {
        return cobroIncapacidad;
    }

    /**
     * @param cobroIncapacidad
     *            the cobroIncapacidad to set
     */
    public void setCobroIncapacidad(boolean cobroIncapacidad) {
        this.cobroIncapacidad = cobroIncapacidad;
    }

    /**
     * @return the pension
     */
    public boolean isPension() {
        return pension;
    }

    /**
     * @param pension
     *            the pension to set
     */
    public void setPension(boolean pension) {
        this.pension = pension;
    }

    /**
     * @return the retiroDesempleo
     */
    public boolean isRetiroDesempleo() {
        return retiroDesempleo;
    }

    /**
     * @param retiroDesempleo
     *            the retiroDesempleo to set
     */
    public void setRetiroDesempleo(boolean retiroDesempleo) {
        this.retiroDesempleo = retiroDesempleo;
    }

    /**
     * @return the registroBeneficiarios
     */
    public boolean isRegistroBeneficiarios() {
        return registroBeneficiarios;
    }

    /**
     * @param registroBeneficiarios
     *            the registroBeneficiarios to set
     */
    public void setRegistroBeneficiarios(boolean registroBeneficiarios) {
        this.registroBeneficiarios = registroBeneficiarios;
    }

    /**
     * @return the adscripcionUMF
     */
    public boolean isAdscripcionUMF() {
        return adscripcionUMF;
    }

    /**
     * @param adscripcionUMF
     *            the adscripcionUMF to set
     */
    public void setAdscripcionUMF(boolean adscripcionUMF) {
        this.adscripcionUMF = adscripcionUMF;
    }

    /**
     * @return the cambioUMF
     */
    public boolean isCambioUMF() {
        return cambioUMF;
    }

    /**
     * @param cambioUMF
     *            the cambioUMF to set
     */
    public void setCambioUMF(boolean cambioUMF) {
        this.cambioUMF = cambioUMF;
    }

    /**
     * @return the gastosMatrimonio
     */
    public boolean isGastosMatrimonio() {
        return gastosMatrimonio;
    }

    /**
     * @param gastosMatrimonio
     *            the gastosMatrimonio to set
     */
    public void setGastosMatrimonio(boolean gastosMatrimonio) {
        this.gastosMatrimonio = gastosMatrimonio;
    }

    /**
     * @return the gastosFuneral
     */
    public boolean isGastosFuneral() {
        return gastosFuneral;
    }

    /**
     * @param gastosFuneral
     *            the gastosFuneral to set
     */
    public void setGastosFuneral(boolean gastosFuneral) {
        this.gastosFuneral = gastosFuneral;
    }

    /**
     * @return the obtenerCredito
     */
    public boolean isObtenerCredito() {
        return obtenerCredito;
    }

    /**
     * @param obtenerCredito
     *            the obtenerCredito to set
     */
    public void setObtenerCredito(boolean obtenerCredito) {
        this.obtenerCredito = obtenerCredito;
    }

    /**
     * @return the conclusionCredito
     */
    public boolean isConclusionCredito() {
        return conclusionCredito;
    }

    /**
     * @param conclusionCredito
     *            the conclusionCredito to set
     */
    public void setConclusionCredito(boolean conclusionCredito) {
        this.conclusionCredito = conclusionCredito;
    }

    /**
     * @return the prorrogaCredito
     */
    public boolean isProrrogaRestructuraCredito() {
        return prorrogaReestructuraCredito;
    }

    /**
     * @param prorrogaCredito
     *            the prorrogaCredito to set
     */
    public void setProrrogaRestructuraCredito(
            boolean prorrogaReestructuraCredito) {
        this.prorrogaReestructuraCredito = prorrogaReestructuraCredito;
    }

    /**
     * @return the descuentoCredito
     */
    public boolean isDescuentoIndebidoCredito() {
        return descuentoIndebidoCredito;
    }

    /**
     * @param descuentoCredito
     *            the descuentoCredito to set
     */
    public void setDescuentoIndebidoCredito(boolean descuentoIndebidoCredito) {
        this.descuentoIndebidoCredito = descuentoIndebidoCredito;
    }

    /**
     * @return the registroAfore
     */
    public boolean isRegistroAfore() {
        return registroAfore;
    }

    /**
     * @param registroAfore
     *            the registroAfore to set
     */
    public void setRegistroAfore(boolean registroAfore) {
        this.registroAfore = registroAfore;
    }

    /**
     * @return the aclaracionSaldo
     */
    public boolean isAclaracionSaldoSupuestaVivienda() {
        return aclaracionSaldoSupuestaVivienda;
    }

    /**
     * @param aclaracionSaldo
     *            the aclaracionSaldo to set
     */
    public void setAclaracionSaldoSupuestaVivienda(
            boolean aclaracionSaldoSupuestaVivienda) {
        this.aclaracionSaldoSupuestaVivienda = aclaracionSaldoSupuestaVivienda;
    }

    /**
     * @return the numeroCredito
     */
    public String getNumeroCredito() {
        return numeroCredito;
    }

    /**
     * @param numeroCredito
     *            the numeroCredito to set
     */
    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }

    /**
     * @return the otro
     */
    public boolean isOtro() {
        return otro;
    }

    /**
     * @param otro
     *            the otro to set
     */
    public void setOtro(boolean otro) {
        this.otro = otro;
    }

    public String getOtroMotivo() {
        return otroMotivo;
    }

    public void setOtroMotivo(String otroMotivo) {
        this.otroMotivo = otroMotivo;
    }

}
