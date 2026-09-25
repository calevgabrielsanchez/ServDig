package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

import java.io.Serializable;

public class TramiteDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long idTramite;
	private Long idTipoTramite;
	private Long idSolicitud;
	private Long idPersona;
	private String observaciones;
	private String motivo;
	private String matricula;
	private String fundamentoLegal;
	private String fechaDefuncion;
	
	public Long getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}
	public Long getIdTipoTramite() {
		return idTipoTramite;
	}
	public void setIdTipoTramite(Long idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
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
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFechaDefuncion() {
		return fechaDefuncion;
	}
	public void setFechaDefuncion(String fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getFundamentoLegal() {
		return fundamentoLegal;
	}
	public void setFundamentoLegal(String fundamentoLegal) {
		this.fundamentoLegal = fundamentoLegal;
	}
}