package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class IdsIntegranteGrupo extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4397884951073901597L;
	private Long idAsignacionNSS;
	private String nss;
	private Long idPersona;
	private Long idPersonaDerechohabiente;
	private Boolean estudianteCL3;
	private String idee;
	
	public IdsIntegranteGrupo(Long idAsignacionNSS, String nss, Long idPersona, Long idPersonaDerechohabiente, Boolean estudianteCL3) {
		this.idAsignacionNSS = idAsignacionNSS;
		this.nss = nss;
		this.idPersona = idPersona;
		this.idPersonaDerechohabiente = idPersonaDerechohabiente;
		this.estudianteCL3 = estudianteCL3 == null ? false: estudianteCL3;
	}
	
	public Long getIdAsignacionNSS() {
		return idAsignacionNSS;
	}
	
	public void setIdAsignacionNSS(Long idAsignacionNSS) {
		this.idAsignacionNSS = idAsignacionNSS;
	}
	
	public String getNss() {
		return nss;
	}
	
	public void setNss(String nss) {
		this.nss = nss;
	}
	
	public Long getIdPersona() {
		return idPersona;
	}
	
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	public Long getIdPersonaDerechohabiente() {
		return idPersonaDerechohabiente;
	}
	
	public void setIdPersonaDerechohabiente(Long idPersonaDerechohabiente) {
		this.idPersonaDerechohabiente = idPersonaDerechohabiente;
	}
	
	public Boolean getEstudianteCL3() {
		return estudianteCL3;
	}
	
	public void setEstudianteCL3(Boolean estudianteCL3) {
		this.estudianteCL3 = estudianteCL3;
	}

	public String getIdee() {
		return idee;
	}

	public void setIdee(String idee) {
		this.idee = idee;
	}
	
	@Override
	public String toString() {
		String datos = "";
		
		datos = "Integrante con NSS( num:"+this.nss+", id:"+this.idAsignacionNSS+"), idPersona: " + this.idPersona +" con idPersonaDer: "+this.idPersonaDerechohabiente +
				", es estudiante: " +this.estudianteCL3 + " e IDEE: " + this.idee;
		return datos;
	}
}