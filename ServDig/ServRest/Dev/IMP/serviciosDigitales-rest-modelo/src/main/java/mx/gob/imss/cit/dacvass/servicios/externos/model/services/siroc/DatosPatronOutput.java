package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class DatosPatronOutput implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String rp;
	private String razonSocial;
	private String rfc;
	private Date   fechaRegistroPatron;
	
	
	
	public String getRp() {
		return rp;
	}
	public void setRp(String rp) {
		this.rp = rp;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public Date getFechaRegistroPatron() {
		return fechaRegistroPatron;
	}
	public void setFechaRegistroPatron(Date fechaRegistroPatron) {
		this.fechaRegistroPatron = fechaRegistroPatron;
	}
	
	
}
