package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;

/**
 * 
 * @author Juan Manuel Marquez
 *
 */
public class ProrrogasDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String SES_NAME="miProrroga";
	
	private String observaciones;
	private String fechaInicio;
	private String fechaFin;
	private String idCaracter;
	private TramiteProrroga prorroga;
	private Long documentos;
	private RechazoDto rechazo;

	
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getIdCaracter() {
		return idCaracter;
	}
	public void setIdCaracter(String idCaracter) {
		this.idCaracter = idCaracter;
	}
	public TramiteProrroga getProrroga() {
		return prorroga;
	}
	public void setProrroga(TramiteProrroga prorroga) {
		this.prorroga = prorroga;
	}
	public Long getDocumentos() {
		return documentos;
	}
	public void setDocumentos(Long documentos) {
		this.documentos = documentos;
	}
	public RechazoDto getRechazo() {
		return rechazo;
	}
	public void setRechazo(RechazoDto rechazo) {
		this.rechazo = rechazo;
	}
}
