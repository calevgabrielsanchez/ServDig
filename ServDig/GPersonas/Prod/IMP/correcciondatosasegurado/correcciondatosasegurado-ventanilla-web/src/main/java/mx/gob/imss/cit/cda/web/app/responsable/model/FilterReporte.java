/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import java.util.Date;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 *
 * @author Yisus
 */
public class FilterReporte extends BaseModel{
  
	private static final long serialVersionUID = 1L;
	private String delegacion;
	private String subdelegacion;
	private String autorizo;
	private String responsable;
	private String origen;
	private String folio;
	private String curp;
	private String nssInvolucrado;
	private String tipoTramite;
	private String estado;
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
	public String getAutorizo() {
		return autorizo;
	}
	public void setAutorizo(String autorizo) {
		this.autorizo = autorizo;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
		return fechaSolicitudDesde;
	}
	public void setFechaSolicitudDesde(Date fechaSolicitudDesde) {
		this.fechaSolicitudDesde = fechaSolicitudDesde;
	}
	public Date getFechaSolicitudHasta() {
		return fechaSolicitudHasta;
	}
	public void setFechaSolicitudHasta(Date fechaSolicitudHasta) {
		this.fechaSolicitudHasta = fechaSolicitudHasta;
	}
	public Date getFechaFinalizacionDesde() {
		return fechaFinalizacionDesde;
	}
	public void setFechaFinalizacionDesde(Date fechaFinalizacionDesde) {
		this.fechaFinalizacionDesde = fechaFinalizacionDesde;
	}
	public Date getFechaFinalizacionHasta() {
		return fechaFinalizacionHasta;
	}
	public void setFechaFinalizacionHasta(Date fechaFinalizacionHasta) {
		this.fechaFinalizacionHasta = fechaFinalizacionHasta;
	}
	public Date getFechaActualizacionDesde() {
		return fechaActualizacionDesde;
	}
	public void setFechaActualizacionDesde(Date fechaActualizacionDesde) {
		this.fechaActualizacionDesde = fechaActualizacionDesde;
	}
	public Date getFechaActualizacionHasta() {
		return fechaActualizacionHasta;
	}
	public void setFechaActualizacionHasta(Date fechaActualizacionHasta) {
		this.fechaActualizacionHasta = fechaActualizacionHasta;
	}
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	
	
	
}
