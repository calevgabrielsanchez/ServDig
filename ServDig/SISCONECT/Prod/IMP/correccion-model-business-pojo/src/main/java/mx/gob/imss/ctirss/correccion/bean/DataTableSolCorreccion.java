package mx.gob.imss.ctirss.correccion.bean;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public class DataTableSolCorreccion extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private String nuFolioSolCorreccion;
	private String fdLimiteSolicitud;
	private String estatus;
	private long idEstatus;
	private int idSolicitudCorreccion;
	private String tipoCorreccion;
	private String motivoRechazo;
	
	
	public int getIdSolicitudCorreccion() {
		return idSolicitudCorreccion;
	}
	public void setIdSolicitudCorreccion(int idSolicitudCorreccion) {
		this.idSolicitudCorreccion = idSolicitudCorreccion;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	public String getNuFolioSolCorreccion() {
		return nuFolioSolCorreccion;
	}
	public long getIdEstatus() {
		return idEstatus;
	}
	public void setIdEstatus(long idEstatus) {
		this.idEstatus = idEstatus;
	}
	public void setNuFolioSolCorreccion(String nuFolioSolCorreccion) {
		this.nuFolioSolCorreccion = nuFolioSolCorreccion;
	}
	public String getFdLimiteSolicitud() {
		return fdLimiteSolicitud;
	}
	public void setFdLimiteSolicitud(String fdLimiteSolicitud) {
		this.fdLimiteSolicitud = fdLimiteSolicitud;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getTipoCorreccion() {
		return tipoCorreccion;
	}
	public void setTipoCorreccion(String tipoCorreccion) {
		this.tipoCorreccion = tipoCorreccion;
	}
}
