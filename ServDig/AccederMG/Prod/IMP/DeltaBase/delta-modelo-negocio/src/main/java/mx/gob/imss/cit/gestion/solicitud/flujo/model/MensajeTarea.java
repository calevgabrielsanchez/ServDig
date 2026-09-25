package mx.gob.imss.cit.gestion.solicitud.flujo.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Bean para el mensaje de tarea
 * 
 * @author softtek
 * 
 */
public class MensajeTarea implements Serializable {

	/**
	 * Numero de version
	 */
	private static final long serialVersionUID = 6446464769774721949L;

	private String tipoTransicion;

	private String estado;

	private String fechaActualizacion;

	private String data;

	private String tipoRequerimiento;

	private Long idSubProceso;

	private Map<Long, String> subProcesos;

	private Map<String, Long> parametros;

	private Map<String, String> participantes;
	
	private String usuario;
	
	private String asignado;

	private String observacion;

	public String getTipoTransicion() {
		return tipoTransicion;
	}

	public void setTipoTransicion(String tipoTransicion) {
		this.tipoTransicion = tipoTransicion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTipoRequerimiento() {
		return tipoRequerimiento;
	}

	public void setTipoRequerimiento(String tipoRequerimiento) {
		this.tipoRequerimiento = tipoRequerimiento;
	}

	public Long getIdSubProceso() {
		return idSubProceso;
	}

	public void setIdSubProceso(Long idSubProceso) {
		this.idSubProceso = idSubProceso;
	}

	public Map<Long, String> getSubProcesos() {
		return subProcesos;
	}

	public void setSubProcesos(Map<Long, String> subProcesos) {
		this.subProcesos = subProcesos;
	}

	public Map<String, Long> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Long> parametros) {
		this.parametros = parametros;
	}

	public Map<String, String> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(Map<String, String> participantes) {
		this.participantes = participantes;
	}

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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
}
