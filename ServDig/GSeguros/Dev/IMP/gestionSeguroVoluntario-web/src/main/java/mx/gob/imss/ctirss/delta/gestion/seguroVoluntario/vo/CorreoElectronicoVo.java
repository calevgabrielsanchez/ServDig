package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo;

import java.io.Serializable;
import java.util.Map;


public class CorreoElectronicoVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -376766683181314655L;
	
	private String asunto;
	private String cuerpoCorreo;
	private boolean formatoHMTL;
	private String correoCopia;
	private String destinatario;
	private String origen;
	private Map<String, byte[]> adjuntos;
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
	public boolean isFormatoHMTL() {
		return formatoHMTL;
	}
	public void setFormatoHMTL(boolean formatoHMTL) {
		this.formatoHMTL = formatoHMTL;
	}
	public String getCorreoCopia() {
		return correoCopia;
	}
	public void setCorreoCopia(String correoCopia) {
		this.correoCopia = correoCopia;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public Map<String, byte[]> getAdjuntos() {
		return adjuntos;
	}
	public void setAdjuntos(Map<String, byte[]> adjuntos) {
		this.adjuntos = adjuntos;
	}
	
	@Override
	public String toString(){
		return "Asunto: "+getAsunto()+" Con copia : "+ getCorreoCopia()+" Cuerpo del Correo : "+getCuerpoCorreo()+" Destinatario: "+getDestinatario()
		+" From: "+getOrigen()+" Con Adjuntos: "+getAdjuntos() + " Es Formato HTML : "+ isFormatoHMTL();
		
	}
 
}
