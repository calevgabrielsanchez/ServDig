package mx.gob.imss.cit.gestion.solicitud.flujo.model;

import java.util.Map;
import java.io.Serializable;

/**
 * Bean para el inicio del tramite
 * 
 * @author softtek
 * 
 */

public class InicioTramite implements Serializable {

	/**
	 * Numero de version
	 */
	private static final long serialVersionUID = 5518054673085918824L;

	private String folio;
	
	private Integer idTramite;

	private String fechaSolicitud;

	private String fechaActualizacion;

	private String estatus;

	private String data;

	private Map<String, String> participantes;

	private Map<String, Long> parametros;

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Map<String, String> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(Map<String, String> participantes) {
		this.participantes = participantes;
	}

	public Map<String, Long> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Long> parametros) {
		this.parametros = parametros;
	}

	public Integer getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(Integer idTramite) {
		this.idTramite = idTramite;
	}	

}
