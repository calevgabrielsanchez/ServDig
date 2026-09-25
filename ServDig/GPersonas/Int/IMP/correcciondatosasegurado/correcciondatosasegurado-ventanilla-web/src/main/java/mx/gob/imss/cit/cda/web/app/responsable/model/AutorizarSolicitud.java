/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorizarSolicitud extends BaseModel {

	private static final long serialVersionUID = -7939352436183271747L;
	private String detalle;
	private String idTarea;
	private String idTramite;
	private String folio;
	private String fechaInicio;
	private String nombreCompletoAutorizador;
	private String nss;
	private InformacionRENAPO informacionRENAPO;
	private String curpResponsable;

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
	 * @return the idTarea
	 */
	public String getIdTarea() {
		return idTarea;
	}

	/**
	 * @param idTarea
	 *            the idTarea to set
	 */
	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * @return the idTramite
	 */
	public String getIdTramite() {
		return idTramite;
	}

	/**
	 * @param idTramite
	 *            the idTramite to set
	 */
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombreCompletoAutorizador() {
		return nombreCompletoAutorizador;
	}

	public void setNombreCompletoAutorizador(String nombreCompletoAutorizador) {
		this.nombreCompletoAutorizador = nombreCompletoAutorizador;
	}
	
	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public InformacionRENAPO getInformacionRENAPO() {
		return informacionRENAPO;
	}

	public void setInformacionRENAPO(InformacionRENAPO informacionRENAPO) {
		this.informacionRENAPO = informacionRENAPO;
	}

	public String getCurpResponsable() {
		return curpResponsable;
	}

	public void setCurpResponsable(String curpResponsable) {
		this.curpResponsable = curpResponsable;
	}
	
	
}
