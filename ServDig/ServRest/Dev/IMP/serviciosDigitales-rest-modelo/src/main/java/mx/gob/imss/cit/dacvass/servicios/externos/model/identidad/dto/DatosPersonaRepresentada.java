package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

public class DatosPersonaRepresentada implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5889353215949037792L;
	private String rfc;
	private String nrp;
	
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNrp() {
		return nrp;
	}
	public void setNrp(String nrp) {
		this.nrp = nrp;
	}
	
	

}
