package mx.gob.imss.cit.cda.web.app.reportes.model;

import java.util.List;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

public class TramitesReportes extends BaseModel {

	private static final long serialVersionUID = -8714786375849977962L;
	private String idTramite;
	private String idTarea;
	private String folio;
	private String curp;
	private String nssInvolucrados;
	private String vencida;
	private String delegacion;
	private String subdelegacion;
	private String autorizo;
	private String responsable;
	private String origen;
	private String tipo;
	private String fechaSolicitud;
	private String fechaFinalizacion;
	//fecha
	private String ultimaActualizacion;
	private String estatus;

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

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getVencida() {
		return vencida;
	}

	public void setVencida(String vencida) {
		this.vencida = vencida;
	}

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

	public String getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(String fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
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

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	public String getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}


}
