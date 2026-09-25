/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.reportes.vo;

import java.util.Date;

import mx.gob.imss.cit.cda.web.common.vo.Filter;

/**
 *
 * Filtros para las bandejas de reportes
 */
public class FilterReporte extends Filter {

    private static final long serialVersionUID = 1L;
    private String delegacion;
    private String subdelegacion;
    private String nssInvolucrado;
    private String tipoTramite;
    private String curpBeneficiario;
    private boolean vencida;
    private Date fechaSolicitudDesde;
    private Date fechaSolicitudHasta;
    private Date fechaFinalizacionDesde;
    private Date fechaFinalizacionHasta;
    private Date fechaActualizacionDesde;
    private Date fechaActualizacionHasta;
    private String variable;

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public String getSubdelegacion() {
        return subdelegacion;
    }

    public void setSubdelegacion(String subdelegacion) {
        this.subdelegacion = subdelegacion;
    }

    public String getNssInvolucrado() {
        return nssInvolucrado;
    }

    public void setNssInvolucrado(String nssInvolucrado) {
        this.nssInvolucrado = nssInvolucrado;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getCurpBeneficiario() {
        return curpBeneficiario;
    }

    public void setCurpBeneficiario(String curpBeneficiario) {
        this.curpBeneficiario = curpBeneficiario;
    }

    public boolean isVencida() {
        return vencida;
    }

    public void setVencida(boolean vencida) {
        this.vencida = vencida;
    }

    public Date getFechaSolicitudDesde() {
        return fechaSolicitudDesde != null ? (Date) fechaSolicitudDesde.clone() : null;
    }

    public void setFechaSolicitudDesde(Date fechaSolicitudDesde) {
        this.fechaSolicitudDesde = fechaSolicitudDesde != null ? (Date) fechaSolicitudDesde.clone() : null;
    }

    public Date getFechaSolicitudHasta() {
        return fechaSolicitudHasta != null ? (Date) fechaSolicitudHasta.clone() : null;
    }

    public void setFechaSolicitudHasta(Date fechaSolicitudHasta) {
        this.fechaSolicitudHasta = fechaSolicitudHasta != null ? (Date) fechaSolicitudHasta.clone() : null;
    }

    public Date getFechaFinalizacionDesde() {
        return fechaFinalizacionDesde != null ? (Date) fechaFinalizacionDesde.clone() : null;
    }

    public void setFechaFinalizacionDesde(Date fechaFinalizacionDesde) {
        this.fechaFinalizacionDesde = fechaFinalizacionDesde != null ? (Date) fechaFinalizacionDesde.clone() : null;
    }

    public Date getFechaFinalizacionHasta() {
        return fechaFinalizacionHasta != null ? (Date) fechaFinalizacionHasta.clone() : null;
    }

    public void setFechaFinalizacionHasta(Date fechaFinalizacionHasta) {
        this.fechaFinalizacionHasta = fechaFinalizacionHasta != null ? (Date) fechaFinalizacionHasta.clone() : null;
    }

    public Date getFechaActualizacionDesde() {
        return fechaActualizacionDesde != null ? (Date) fechaActualizacionDesde.clone() : null;
    }

    public void setFechaActualizacionDesde(Date fechaActualizacionDesde) {
        this.fechaActualizacionDesde = fechaActualizacionDesde != null ? (Date) fechaActualizacionDesde.clone() : null;
    }

    public Date getFechaActualizacionHasta() {
        return fechaActualizacionHasta != null ? (Date) fechaActualizacionHasta.clone() : null;
    }

    public void setFechaActualizacionHasta(Date fechaActualizacionHasta) {
        this.fechaActualizacionHasta = fechaActualizacionHasta != null ? (Date) fechaActualizacionHasta.clone() : null;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

}
