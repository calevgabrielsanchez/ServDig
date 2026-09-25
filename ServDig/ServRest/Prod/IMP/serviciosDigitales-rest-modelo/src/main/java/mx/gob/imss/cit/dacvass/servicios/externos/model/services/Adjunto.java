package mx.gob.imss.cit.dacvass.servicios.externos.model.services;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Adjunto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3856736670779976625L;
	
	
	private String nombreAdjunto;
	private String adjuntoBase64;
	
	
	public String getNombreAdjunto() {
		return nombreAdjunto;
	}
	public void setNombreAdjunto(String nombreAdjunto) {
		this.nombreAdjunto = nombreAdjunto;
	}
	public String getAdjuntoBase64() {
		return adjuntoBase64;
	}
	public void setAdjuntoBase64(String adjuntoBase64) {
		this.adjuntoBase64 = adjuntoBase64;
	}
	
	
	

}
