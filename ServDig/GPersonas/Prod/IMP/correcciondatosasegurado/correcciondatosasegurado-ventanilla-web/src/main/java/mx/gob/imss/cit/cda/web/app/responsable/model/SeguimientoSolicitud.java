/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 *
 * @author antonio
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeguimientoSolicitud extends BaseModel {
	
	private static final long serialVersionUID = 714522471822230928L;
	private String idSolicitud;
	private String folio;
	private String resumen;
	private String detalle;
	private String idTarea;
	private String idTramite;
	private InformacionRENAPO informacionRENAPO;
	private String fechaInicio;
	private SubDelegacion subDelegacion;
	private String responsable;
	private String curpResponsable;
	private String nss;
	private String correoAsegurado;

	/**
	 * @return the idSolicitud
	 */
	public String getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud
	 *            the idSolicitud to set
	 */
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the resumen
	 */
	public String getResumen() {
		return resumen;
	}

	/**
	 * @param resumen
	 *            the resumen to set
	 */
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	/**
	 * @return the detalle
	 */
	public String getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle
	 *            the detalle to set
	 */
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	/**
	 * @return the detalle
	 */
	public String getIdTarea() {
		return idTarea;
	}

	/**
	 * @param detalle
	 *            the detalle to set
	 */
	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public InformacionRENAPO getInformacionRENAPO() {
		return informacionRENAPO;
	}

	public void setInformacionRENAPO(InformacionRENAPO informacionRENAPO) {
		this.informacionRENAPO = informacionRENAPO;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public SubDelegacion getSubDelegacion() {
		return subDelegacion;
	}

	public void setSubDelegacion(SubDelegacion subDelegacion) {
		this.subDelegacion = subDelegacion;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getCurpResponsable() {
		return curpResponsable;
	}

	public void setCurpResponsable(String curpResponsable) {
		this.curpResponsable = curpResponsable;
	}

	public String getCorreoAsegurado() {
		return correoAsegurado;
	}

	public void setCorreoAsegurado(String correoAsegurado) {
		this.correoAsegurado = correoAsegurado;
	}

	@Override
	public String toString() {
		return "SeguimientoSolicitud [idSolicitud=" + idSolicitud + ", folio="
				+ folio + ", resumen=" + resumen + ", detalle=" + detalle
				+ ", idTarea=" + idTarea + ", idTramite=" + idTramite
				+ ", informacionRENAPO=" + informacionRENAPO + ", fechaInicio="
				+ fechaInicio + ", subDelegacion=" + subDelegacion
				+ ", responsable=" + responsable + ", curpResponsable="
				+ curpResponsable + ", nss=" + nss + ", correoAsegurado="
				+ correoAsegurado + "]";
	}
	
}
