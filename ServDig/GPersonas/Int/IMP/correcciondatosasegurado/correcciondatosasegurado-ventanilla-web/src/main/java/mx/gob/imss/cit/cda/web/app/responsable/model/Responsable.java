package mx.gob.imss.cit.cda.web.app.responsable.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.app.common.model.Combo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Responsable extends Combo {

	private static final long serialVersionUID = 1L;
	private String correoElectronico;
	private String curp;

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}


}