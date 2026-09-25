package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AvisoUbicacionObra extends UbicacionObra implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6461679066064363415L;
	
	private String cveRegistroAvisoObra;
	private Date fechaRegistroAlta;
	private String descDelegacionImss;
	private String descSubDelegacionImss;
	
	public String getCveRegistroAvisoObra() {
		return cveRegistroAvisoObra;
	}
	public void setCveRegistroAvisoObra(String cveRegistroAvisoObra) {
		this.cveRegistroAvisoObra = cveRegistroAvisoObra;
	}
	public Date getFechaRegistroAlta() {
		return fechaRegistroAlta;
	}
	public void setFechaRegistroAlta(Date fechaRegistroAlta) {
		this.fechaRegistroAlta = fechaRegistroAlta;
	}
	public String getDescDelegacionImss() {
		return descDelegacionImss;
	}
	public void setDescDelegacionImss(String descDelegacionImss) {
		this.descDelegacionImss = descDelegacionImss;
	}
	public String getDescSubDelegacionImss() {
		return descSubDelegacionImss;
	}
	public void setDescSubDelegacionImss(String descSubDelegacionImss) {
		this.descSubDelegacionImss = descSubDelegacionImss;
	}
	
	
	
}
