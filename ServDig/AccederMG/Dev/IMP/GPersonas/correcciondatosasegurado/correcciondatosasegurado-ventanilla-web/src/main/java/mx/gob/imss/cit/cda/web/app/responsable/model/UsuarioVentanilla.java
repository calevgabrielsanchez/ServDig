package mx.gob.imss.cit.cda.web.app.responsable.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.app.common.model.Combo;

/**
*
* Modelo para Uso de combos de usuarios de ventanilla
* Si se requieren atributos especificos para cada tipo
* se debe generar modelo en especifico
*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioVentanilla extends Combo {

	private static final long serialVersionUID = 1L;
	private String correoElectronico;
	private String curp;

	public UsuarioVentanilla() {
		super();
	}

	public UsuarioVentanilla(String value, String key) {
		super(value, key);
	}

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