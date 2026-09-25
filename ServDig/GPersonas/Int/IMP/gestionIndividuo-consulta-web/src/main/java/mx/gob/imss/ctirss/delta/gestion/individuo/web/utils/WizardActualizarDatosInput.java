package mx.gob.imss.ctirss.delta.gestion.individuo.web.utils;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class WizardActualizarDatosInput extends AbstractModel {

	private static final long serialVersionUID = 7592662090805105298L;

	private Long idPersonaSesion;
	private Integer idTipoPersona;
	private Long idPersona;
	private String curp;
	private String rfc;
	private Long idPersonaInteresadaSol;
	private Boolean consultaRenapo;
	private Boolean consultaSat;

	public Long getIdPersonaSesion() {
		return idPersonaSesion;
	}

	public void setIdPersonaSesion(Long idPersonaSesion) {
		this.idPersonaSesion = idPersonaSesion;
	}

	public Integer getIdTipoPersona() {
		return idTipoPersona;
	}

	public void setIdTipoPersona(Integer idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Long getIdPersonaInteresadaSol() {
		return idPersonaInteresadaSol;
	}

	public void setIdPersonaInteresadaSol(Long idPersonaInteresadaSol) {
		this.idPersonaInteresadaSol = idPersonaInteresadaSol;
	}

	public Boolean getConsultaRenapo() {
		return consultaRenapo;
	}

	public void setConsultaRenapo(Boolean consultaRenapo) {
		this.consultaRenapo = consultaRenapo;
	}

	public Boolean getConsultaSat() {
		return consultaSat;
	}

	public void setConsultaSat(Boolean consultaSat) {
		this.consultaSat = consultaSat;
	}

}
