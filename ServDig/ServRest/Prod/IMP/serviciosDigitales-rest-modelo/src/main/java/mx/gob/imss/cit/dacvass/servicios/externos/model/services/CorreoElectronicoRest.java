package mx.gob.imss.cit.dacvass.servicios.externos.model.services;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CorreoElectronicoRest implements Serializable {
	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5350923393509152650L;
	private String[] correoPara;
	private String[] correoCopia;
	private String asunto;
	private String cuerpoCorreo;
	
	private ArrayList<Adjunto> adjuntos ;
	//private Map<String, String> adjuntos;
	
	private String remitente;
	
	
	public String getRemitente() {
		return remitente;
	}
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
	public ArrayList<Adjunto> getAdjuntos() {
		return adjuntos;
	}
	public void setAdjuntos(ArrayList<Adjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}
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
	
	
	/*
	public Map<String, String> getAdjuntos() {
		return adjuntos;
	}
	public void setAdjuntos(Map<String, String> adjuntos) {
		this.adjuntos = adjuntos;
	}
	*/
	
	public String toString() {
		
		return " correoPara[" +this.getCorreoPara() +"]  correoCopia[" +this.getCorreoCopia()+"] asunto[" +this.getAsunto()
					+"] cuerpoCorreo[" +this.getCuerpoCorreo()+"]" ;
	}

}
