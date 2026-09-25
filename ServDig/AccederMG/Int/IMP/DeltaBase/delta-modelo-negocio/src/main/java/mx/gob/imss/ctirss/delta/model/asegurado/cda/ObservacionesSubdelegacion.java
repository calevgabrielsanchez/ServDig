package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ObservacionesSubdelegacion extends AbstractModel  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idTarea;
	private String resumen;
	private String detalle;
	private Integer cveEstado;
	private String usuario;
	private String asignado;
	private Date fechaActualizacion;	
	
	public ObservacionesSubdelegacion() {
		super();
	}
	
	public ObservacionesSubdelegacion(String idTarea, String resumen, String detalle, Integer cveEstado, String usuario,
			String asignado, Date fechaActualizacion) {
		super();
		this.idTarea = idTarea;
		this.resumen = resumen;
		this.detalle = detalle;
		this.cveEstado = cveEstado;
		this.usuario = usuario;
		this.asignado = asignado;
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getIdTarea() {
		return idTarea;
	}
	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Integer getCveEstado() {
		return cveEstado;
	}
	public void setCveEstado(Integer cveEstado) {
		this.cveEstado = cveEstado;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getAsignado() {
		return asignado;
	}
	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
}