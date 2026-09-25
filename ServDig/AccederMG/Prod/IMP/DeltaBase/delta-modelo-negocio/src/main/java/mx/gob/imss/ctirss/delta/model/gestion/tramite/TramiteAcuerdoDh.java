package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoAcuerdoDh;

@XmlRootElement
public class TramiteAcuerdoDh extends Tramite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idAcuerdo;
	private Long idAsignacionNSS;
	private String numeroAcuerdo;
	private Date fechaAcuerdo;
	private Date fechaFinAcuerdo;
	private EstadoAcuerdoDh estadoAcuerdoDh;
	
	public Long getIdAcuerdo() {
		return idAcuerdo;
	}
	
	public void setIdAcuerdo(Long idAcuerdo) {
		this.idAcuerdo = idAcuerdo;
	}
	
	public Long getIdAsignacionNSS() {
		return idAsignacionNSS;
	}
	
	public void setIdAsignacionNSS(Long idAsignacionNSS) {
		this.idAsignacionNSS = idAsignacionNSS;
	}
	
	public String getNumeroAcuerdo() {
		return numeroAcuerdo;
	}
	
	public void setNumeroAcuerdo(String numeroAcuerdo) {
		this.numeroAcuerdo = numeroAcuerdo;
	}
	
	public Date getFechaAcuerdo() {
		return fechaAcuerdo;
	}
	
	public void setFechaAcuerdo(Date fechaAcuerdo) {
		this.fechaAcuerdo = fechaAcuerdo;
	}

	public Date getFechaFinAcuerdo() {
		return fechaFinAcuerdo;
	}

	public void setFechaFinAcuerdo(Date fechaFinAcuerdo) {
		this.fechaFinAcuerdo = fechaFinAcuerdo;
	}

	public EstadoAcuerdoDh getEstadoAcuerdoDh() {
		return estadoAcuerdoDh;
	}

	public void setEstadoAcuerdoDh(EstadoAcuerdoDh estadoAcuerdoDh) {
		this.estadoAcuerdoDh = estadoAcuerdoDh;
	}
}