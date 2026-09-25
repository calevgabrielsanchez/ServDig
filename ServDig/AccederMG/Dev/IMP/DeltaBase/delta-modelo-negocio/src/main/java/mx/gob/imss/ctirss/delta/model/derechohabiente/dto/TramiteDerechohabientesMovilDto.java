package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import java.io.Serializable;


public class TramiteDerechohabientesMovilDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8745784276343549413L;
	private Long idAsignacionNSS;
	private Long idPersona;
	private Long idParentesco;
	private Long idConsultorio;
	
	public TramiteDerechohabientesMovilDto(Long idAsignacionNSS, Long idPersona, Long idParentesco, Long idconsultorio) {
		this.idAsignacionNSS = idAsignacionNSS;
		this.idPersona = idPersona;
		this.idParentesco = idParentesco;
		this.idConsultorio = idconsultorio;
	}
	
	public TramiteDerechohabientesMovilDto(){
		
	}
	
	public Long getIdAsignacionNSS() {
		return idAsignacionNSS;
	}
	
	public void setIdAsignacionNSS(Long idAsignacionNSS) {
		this.idAsignacionNSS = idAsignacionNSS;
	}
	
	public Long getIdPersona() {
		return idPersona;
	}
	
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	public Long getIdParentesco() {
		return idParentesco;
	}
	
	public void setIdParentesco(Long idParentesco) {
		this.idParentesco = idParentesco;
	}
	
	public Long getIdConsultorio() {
		return idConsultorio;
	}
	
	public void setIdConsultorio(Long idConsultorio) {
		this.idConsultorio = idConsultorio;
	}
	
}