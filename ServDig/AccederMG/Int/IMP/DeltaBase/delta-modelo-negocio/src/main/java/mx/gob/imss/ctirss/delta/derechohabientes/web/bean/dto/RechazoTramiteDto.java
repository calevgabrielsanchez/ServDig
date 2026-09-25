package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class RechazoTramiteDto extends AbstractModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idSolicitud;
	private Long idPersona;
	private Long idTipoTramite;
	private Long idRazonRechazo;
	private Long idTramite;
	private String observaciones;
	
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public Long getIdTipoTramite() {
		return idTipoTramite;
	}
	public void setIdTipoTramite(Long itTipoTramite) {
		this.idTipoTramite = itTipoTramite;
	}
	public Long getIdRazonRechazo() {
		return idRazonRechazo;
	}
	public void setIdRazonRechazo(Long idRazonRechazo) {
		this.idRazonRechazo = idRazonRechazo;
	}
	public Long getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}
	
}
