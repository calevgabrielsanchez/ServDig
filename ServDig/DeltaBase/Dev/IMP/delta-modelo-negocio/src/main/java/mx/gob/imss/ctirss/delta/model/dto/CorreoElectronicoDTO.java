package mx.gob.imss.ctirss.delta.model.dto;

import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class CorreoElectronicoDTO extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1269075171319339330L;
	/**
	 * 
	 */
	
	private String[] correoPara;
	private String[] correoCopia;
	private String asunto;
	private String cuerpoCorreo;
	private Map<String, byte[]> adjuntos;
	
	public String[] getCorreoPara() {
		return correoPara;
	}
	public void setCorreoPara(String[] correoPara) {
		this.correoPara = correoPara;
	}
	public String[] getCorreoCopia() {
		return correoCopia;
	}
	public void setCorreoCopia(String[] correoCopia) {
		this.correoCopia = correoCopia;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}
	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}
	public Map<String, byte[]> getAdjuntos() {
		return adjuntos;
	}
	public void setAdjuntos(Map<String, byte[]> adjuntos) {
		this.adjuntos = adjuntos;
	}
}