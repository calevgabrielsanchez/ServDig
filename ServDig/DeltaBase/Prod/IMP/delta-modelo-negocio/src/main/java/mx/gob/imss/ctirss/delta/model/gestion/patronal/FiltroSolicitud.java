package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Martinez Chamonica
 * @Proyecto: delta
 * @Archivo: FiltroSolicitud.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 * @Fecha: 29 Agosto 2012
 */
public class FiltroSolicitud extends AbstractModel {

	private static final long serialVersionUID = -9075767965537224614L;

	private String folio;
	private String rfc;
	private String rp;
	private Date fechaInicioPresentacion;
	private Date fechaFinPresentacion;
	private Date fechaInicioConclusion;
	private Date fechaFinConclusion;
	private Long tramiteId;
	private Long idEstadoSolicitud;
	private Long idDelegacion;
	private Long idSubdelegacion;
	private List<TipoTramite> listaTipoTramite;
	private List<Modulo> listaModulos;
	private Long idSolicitud;

	public List<Modulo> getListaModulos() {
		return listaModulos;
	}

	public void setListaModulos(List<Modulo> listaModulos) {
		this.listaModulos = listaModulos;
	}

	// Atributos agragados para el visor de solicitud
	private Date fechaSolicitud;
	private Long idTipoSolicitud;
	private String curp;
	private String nss;
	private Long idOrigenSolicitud;

	// Atributos agragados para consulta exacta de solicitudes
	private Boolean indFechaPresentacionExacta = false;
	private Boolean indFechaConclusionExacta = false;

	/*
	 * Atributo usado en las gráficas, que indica si la gráfica es mensual o
	 * diaria
	 */
	private boolean agruparPorMes;

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getRp() {
		return rp;
	}

	public void setRp(String rp) {
		this.rp = rp;
	}

	public Date getFechaInicioPresentacion() {
		return fechaInicioPresentacion;
	}

	public void setFechaInicioPresentacion(Date fechaInicioPresentacion) {
		this.fechaInicioPresentacion = fechaInicioPresentacion;
	}

	public Date getFechaFinPresentacion() {
		return fechaFinPresentacion;
	}

	public void setFechaFinPresentacion(Date fechaFinPresentacion) {
		this.fechaFinPresentacion = fechaFinPresentacion;
	}

	public Date getFechaInicioConclusion() {
		return fechaInicioConclusion;
	}

	public void setFechaInicioConclusion(Date fechaInicioConclusion) {
		this.fechaInicioConclusion = fechaInicioConclusion;
	}

	public Date getFechaFinConclusion() {
		return fechaFinConclusion;
	}

	public void setFechaFinConclusion(Date fechaFinConclusion) {
		this.fechaFinConclusion = fechaFinConclusion;
	}

	public Long getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(Long tramiteId) {
		this.tramiteId = tramiteId;
	}

	public Long getIdEstadoSolicitud() {
		return idEstadoSolicitud;
	}

	public void setIdEstadoSolicitud(Long idEstadoSolicitud) {
		this.idEstadoSolicitud = idEstadoSolicitud;
	}

	public Long getIdDelegacion() {
		return idDelegacion;
	}

	public void setIdDelegacion(Long idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	public Long getIdSubdelegacion() {
		return idSubdelegacion;
	}

	public void setIdSubdelegacion(Long idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}

	public List<TipoTramite> getListaTipoTramite() {
		return listaTipoTramite;
	}

	public void setListaTipoTramite(List<TipoTramite> listaTipoTramite) {
		this.listaTipoTramite = listaTipoTramite;
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Long getIdTipoSolicitud() {
		return idTipoSolicitud;
	}

	public void setIdTipoSolicitud(Long idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
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

	public Long getIdOrigenSolicitud() {
		return idOrigenSolicitud;
	}

	public void setIdOrigenSolicitud(Long idOrigenSolicitud) {
		this.idOrigenSolicitud = idOrigenSolicitud;
	}

	public Boolean getIndFechaPresentacionExacta() {
		return indFechaPresentacionExacta;
	}

	public void setIndFechaPresentacionExacta(Boolean indFechaPresentacionExacta) {
		this.indFechaPresentacionExacta = indFechaPresentacionExacta;
	}

	public Boolean getIndFechaConclusionExacta() {
		return indFechaConclusionExacta;
	}

	public void setIndFechaConclusionExacta(Boolean indFechaConclusionExacta) {
		this.indFechaConclusionExacta = indFechaConclusionExacta;
	}

	public boolean isAgruparPorMes() {
		return agruparPorMes;
	}

	public void setAgruparPorMes(boolean agruparPorMes) {
		this.agruparPorMes = agruparPorMes;
	}
}
