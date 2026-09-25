package mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TipoPension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4634079026268798184L;
	
	private BigDecimal cveIdTIpoPension;
	private String desTipoPension;
	private String refMMarcaPension;
	
	
	public BigDecimal getCveIdTIpoPension() {
		return cveIdTIpoPension;
	}
	public void setCveIdTIpoPension(BigDecimal cveIdTIpoPension) {
		this.cveIdTIpoPension = cveIdTIpoPension;
	}
	public String getDesTipoPension() {
		return desTipoPension;
	}
	public void setDesTipoPension(String desTipoPension) {
		this.desTipoPension = desTipoPension;
	}
	public String getRefMMarcaPension() {
		return refMMarcaPension;
	}
	public void setRefMMarcaPension(String refMMarcaPension) {
		this.refMMarcaPension = refMMarcaPension;
	}
	
	
	
	
}
