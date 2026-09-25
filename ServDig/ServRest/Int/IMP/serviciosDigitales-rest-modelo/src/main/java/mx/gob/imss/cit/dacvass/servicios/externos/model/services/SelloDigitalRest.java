package mx.gob.imss.cit.dacvass.servicios.externos.model.services;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SelloDigitalRest implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 728460302565539645L;
	
	private String sello;
	private String id;
	private String noSerie;
	
	
	private String cadenaOriginal;
	private String secuenciaNotaria; 
	private String rfc;
	
	public SelloDigitalRest(String cadenaOriginal, String secuenciaNotaria,  String rfc) {
		this.cadenaOriginal = cadenaOriginal;
		this.secuenciaNotaria = secuenciaNotaria;
		this.rfc = rfc;
		
	}
	
	public SelloDigitalRest() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoSerie() {
		return noSerie;
	}
	public void setNoSerie(String noSerie) {
		this.noSerie = noSerie;
	}
	
	
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}
	public String getSecuenciaNotaria() {
		return secuenciaNotaria;
	}
	public void setSecuenciaNotaria(String secuenciaNotaria) {
		this.secuenciaNotaria = secuenciaNotaria;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	public String getSello() {
		return sello;
	}
	public void setSello(String sello) {
		this.sello = sello;
	}
	
	
	public String toString() {
		return "sello [" +this.getCadenaOriginal() +
				"] secuenciaNotaria [" +this.getSecuenciaNotaria()+"] rfc [ "+this.getRfc()+"]";
	}
	

}
