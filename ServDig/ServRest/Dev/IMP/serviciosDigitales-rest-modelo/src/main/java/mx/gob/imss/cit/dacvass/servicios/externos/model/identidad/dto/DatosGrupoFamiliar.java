package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

public class DatosGrupoFamiliar implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3075505601953943962L;
	Long cveIdAsignacionNssGrupoFamiliar;
	Long cveIdPersonaIntegrante;
	String nssCabezaGrupoFamiliar;
	String umf;
	
	Turno turno;
	String consultorio;
	Parentesco parentesco;
	

	
	
	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	
	public Long getCveIdAsignacionNssGrupoFamiliar() {
		return cveIdAsignacionNssGrupoFamiliar;
	}

	public void setCveIdAsignacionNssGrupoFamiliar(Long cveIdAsignacionNssGrupoFamiliar) {
		this.cveIdAsignacionNssGrupoFamiliar = cveIdAsignacionNssGrupoFamiliar;
	}

	public String getNssCabezaGrupoFamiliar() {
		return nssCabezaGrupoFamiliar;
	}
	public void setNssCabezaGrupoFamiliar(String nssCabezaGrupoFamiliar) {
		this.nssCabezaGrupoFamiliar = nssCabezaGrupoFamiliar;
	}
	public String getUmf() {
		return umf;
	}
	public void setUmf(String umf) {
		this.umf = umf;
	}
	
	public String getConsultorio() {
		return consultorio;
	}
	public void setConsultorio(String consultorio) {
		this.consultorio = consultorio;
	}
	public Long getCveIdPersonaIntegrante() {
		return cveIdPersonaIntegrante;
	}

	public void setCveIdPersonaIntegrante(Long cveIdPersonaIntegrante) {
		this.cveIdPersonaIntegrante = cveIdPersonaIntegrante;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}
	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}
	
	
	
}
