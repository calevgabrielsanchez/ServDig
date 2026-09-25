/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 *
 * @author Yisus
 */
public class Filter extends BaseModel{
  
	private static final long serialVersionUID = 1L;
	
	private String folio;
	private String fechaSolicitud;
	private String nss;
	private String origen;
	private String responsable;
	private String autorizo;
	private String estado;
	private String curp; 
	private String tramite;
	private String fechaActualizacion;
	private Boolean foliosAsociados;
	private Boolean foliosVencidos;
	
	private String fecha;
	
	
	public String getFolio() {
		return folio;
	}
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getAutorizo() {
		return autorizo;
	}
	public void setAutorizo(String autorizo) {
		this.autorizo = autorizo;
	}
	public String getTramite() {
		return tramite;
	}
	public void setTramite(String tramite) {
		this.tramite = tramite;
	}
	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Boolean getFoliosVencidos() {
		return foliosVencidos;
	}
	public void setFoliosVencidos(Boolean foliosVencidos) {
		this.foliosVencidos = foliosVencidos;
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
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Boolean getFoliosAsociados() {
		return foliosAsociados;
	}
	public void setFoliosAsociados(Boolean foliosAsociados) {
		this.foliosAsociados = foliosAsociados;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "Filter [folio=" + folio + ", fechaSolicitud=" + fechaSolicitud
				+ ", nss=" + nss + ", origen=" + origen + ", responsable="
				+ responsable + ", autorizo=" + autorizo + ", estado=" + estado
				+ ", curp=" + curp + ", tramite=" + tramite
				+ ", fechaActualizacion=" + fechaActualizacion
				+ ", foliosAsociados=" + foliosAsociados + ", foliosVencidos="
				+ foliosVencidos + ", fecha=" + fecha + "]";
	}	
}
