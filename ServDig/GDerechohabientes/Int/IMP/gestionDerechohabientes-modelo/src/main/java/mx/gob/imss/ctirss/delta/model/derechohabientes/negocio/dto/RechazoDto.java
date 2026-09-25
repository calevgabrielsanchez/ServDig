package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

/**
 * 
 * @author Juan Manuel Marquez
 *
 */
public class RechazoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String SES_NAME="miRechazo";
	
	private long estadoSolicitud;
	private long idSolicitud;
	private long idPersona;
	private long idTipoTramite;
	private long idRazonResultado;
	private String doble;
	private String observaciones;
	private long idTramite;
	private long idRazonRechazo;
	
	/**
	 * @return the estadoSolicitud
	 */
	public long getEstadoSolicitud() {
		return estadoSolicitud;
	}
	/**
	 * @param estadoSolicitud the estadoSolicitud to set
	 */
	public void setEstadoSolicitud(long estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}
	/**
	 * @return the idSolicitud
	 */
	public long getIdSolicitud() {
		return idSolicitud;
	}
	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	/**
	 * @return the idPersona
	 */
	public long getIdPersona() {
		return idPersona;
	}
	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}
	/**
	 * @return the idTipoTramite
	 */
	public long getIdTipoTramite() {
		return idTipoTramite;
	}
	/**
	 * @param idTipoTramite the idTipoTramite to set
	 */
	public void setIdTipoTramite(long idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}
	/**
	 * @return the idRazonResultado
	 */
	public long getIdRazonResultado() {
		return idRazonResultado;
	}
	/**
	 * @param idRazonResultado the idRazonResultado to set
	 */
	public void setIdRazonResultado(long idRazonResultado) {
		this.idRazonResultado = idRazonResultado;
	}
	/**
	 * @return the doble
	 */
	public String getDoble() {
		return doble;
	}
	/**
	 * @param doble the doble to set
	 */
	public void setDoble(String doble) {
		this.doble = doble;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public long getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(long idTramite) {
		this.idTramite = idTramite;
	}
	public long getIdRazonRechazo() {
		return idRazonRechazo;
	}
	public void setIdRazonRechazo(long idRazonRechazo) {
		this.idRazonRechazo = idRazonRechazo;
	}
}
