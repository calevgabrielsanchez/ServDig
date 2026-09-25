package mx.gob.imss.ctirss.delta.global.model;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ProcesoTO extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5982405834854611289L;
	
	
	private Long clave;
	private String desInicial;
	private String desIntermedio;
	private String desFinal;
	
	private RegistroPatronalTO registroPatronal;

	public Long getClave() {
		return clave;
	}

	public void setClave(Long clave) {
		this.clave = clave;
	}

	public String getDesInicial() {
		return desInicial;
	}

	public void setDesInicial(String desInicial) {
		this.desInicial = desInicial;
	}

	public String getDesIntermedio() {
		return desIntermedio;
	}

	public void setDesIntermedio(String desIntermedio) {
		this.desIntermedio = desIntermedio;
	}

	public String getDesFinal() {
		return desFinal;
	}

	public void setDesFinal(String desFinal) {
		this.desFinal = desFinal;
	}

	public RegistroPatronalTO getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(RegistroPatronalTO registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	
}
