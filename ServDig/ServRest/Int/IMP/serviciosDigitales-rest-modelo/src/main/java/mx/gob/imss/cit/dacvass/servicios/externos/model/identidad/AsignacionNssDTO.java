package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AsignacionNssDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7784288345542561436L;
	
	private BigDecimal cveIdAsignacionNSS;
	private BigDecimal cveIdPersona;
	private String fecRegistroBaja;
	
	
	
	public String getFecRegistroBaja() {
		return fecRegistroBaja;
	}
	public void setFecRegistroBaja(String fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	public BigDecimal getCveIdAsignacionNSS() {
		return cveIdAsignacionNSS;
	}
	public void setCveIdAsignacionNSS(BigDecimal cveIdAsignacionNSS) {
		this.cveIdAsignacionNSS = cveIdAsignacionNSS;
	}
	public BigDecimal getCveIdPersona() {
		return cveIdPersona;
	}
	public void setCveIdPersona(BigDecimal cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}
	
	
	
	
	

}
