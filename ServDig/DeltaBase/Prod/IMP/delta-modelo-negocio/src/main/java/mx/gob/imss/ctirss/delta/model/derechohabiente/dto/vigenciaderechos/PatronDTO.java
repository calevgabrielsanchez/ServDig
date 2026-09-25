package mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos;


import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class PatronDTO extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2013424029520265873L;
	private String registroPatronal;
	private String modalidad;
	private String nombreRazonSocial;
	private String desModalidad;
	
	
	
	public String getDesModalidad() {
		return desModalidad;
	}
	public void setDesModalidad(String desModalidad) {
		this.desModalidad = desModalidad;
	}
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	
}