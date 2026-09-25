package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

public class ConfigTramite implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EstadoTramite estadoTramite;
	private TipoTramite tipoTramite;
	private Date fechaConclusion;
	private Date fechaPresentacion;
	private Date fechaTramite;
	
	public EstadoTramite getEstadoTramite() {
		return estadoTramite;
	}
	public void setEstadoTramite(EstadoTramite estadoTramite) {
		this.estadoTramite = estadoTramite;
	}
	public TipoTramite getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(TipoTramite tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public Date getFechaConclusion() {
		return fechaConclusion;
	}
	public void setFechaConclusion(Date fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}
	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public Date getFechaTramite() {
		return fechaTramite;
	}
	public void setFechaTramite(Date fechaTramite) {
		this.fechaTramite = fechaTramite;
	}
	
	
	
	
}
