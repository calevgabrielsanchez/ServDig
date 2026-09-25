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
public class TramitesAsignados extends BaseModel {

	private static final long serialVersionUID = -8714786375849977962L;
	private String idTramite;
	private String idTarea;
	private String folio;
	private String fechaSolicitud;
	private String curp;
	private String nssInvolucrados;
	private String origen;
	private String responsable;
	private String autorizo;
	private String estatus;
	private String ultimaActualizacion;
	private String tipo;
	private String nombreCompleto;
	private String nombreCompletoAutorizo;
	private Boolean esPropietario;

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 *            the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the fechaSolicitud
	 */
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * @param fechaSolicitud
	 *            the fechaSolicitud to set
	 */
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	/**
	 * @return the nssInvolucrados
	 */
	public String getNssInvolucrados() {
		return nssInvolucrados;
	}

	/**
	 * @param nssInvolucrados
	 *            the nssInvolucrados to set
	 */
	public void setNssInvolucrados(String nssInvolucrados) {
		this.nssInvolucrados = nssInvolucrados;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen
	 *            the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable
	 *            the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus
	 *            the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return the ultimaActualizacion
	 */
	public String getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	/**
	 * @param ultimaActualizacion
	 *            the ultimaActualizacion to set
	 */
	public void setUltimaActualizacion(String ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
   * @return nombreCompleto
   */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	* @param nombreCompleto the nombreCompleto to set
	*/
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the esPropietario
	 */
	public Boolean getEsPropietario() {
		return esPropietario;
	}

	/**
	 * @param esPropietario
	 *            the esPropietario to set
	 */
	public void setEsPropietario(Boolean esPropietario) {
		this.esPropietario = esPropietario;
	}

	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp
	 *            the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the autorizo
	 */
	public String getAutorizo() {
		return autorizo;
	}

	/**
	 * @param autorizo
	 *            the autorizo to set
	 */
	public void setAutorizo(String autorizo) {
		this.autorizo = autorizo;
	}

	/**
	 * @return the nombreCompletoAutorizo
	 */
	public String getNombreCompletoAutorizo() {
		return nombreCompletoAutorizo;
	}

	/**
	 * @param nombreCompletoAutorizo
	 *            the nombreCompletoAutorizo to set
	 */
	public void setNombreCompletoAutorizo(String nombreCompletoAutorizo) {
		this.nombreCompletoAutorizo = nombreCompletoAutorizo;
	}

	/**
	 * @return the TramitesAsignados
	 */
	@Override
	public String toString() {
		return "TramitesAsignados [idTramite=" + idTramite + ", idTarea="
				+ idTarea + ", folio=" + folio + ", fechaSolicitud="
				+ fechaSolicitud + ", curp=" + curp + ", nssInvolucrados="
				+ nssInvolucrados + ", origen=" + origen + ", responsable="
				+ responsable + ", autorizo=" + autorizo + ", estatus="
				+ estatus + ", ultimaActualizacion=" + ultimaActualizacion
				+ ", tipo=" + tipo + ", nombreCompleto=" + nombreCompleto
				+ ", esPropietario=" + esPropietario + "]";
	}	
}
