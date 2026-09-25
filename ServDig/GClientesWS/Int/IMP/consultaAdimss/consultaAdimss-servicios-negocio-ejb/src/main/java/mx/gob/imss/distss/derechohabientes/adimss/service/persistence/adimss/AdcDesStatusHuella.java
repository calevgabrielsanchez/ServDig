package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADC_DES_STATUS_HUELLA database table.
 * 
 */
@Entity
@Table(name="ADC_DES_STATUS_HUELLA")
@NamedQuery(name="AdcDesStatusHuella.findAll", query="SELECT a FROM AdcDesStatusHuella a")
public class AdcDesStatusHuella implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_STATUS_HUELLA")
	private String cveStatusHuella;

	@Column(name="DES_STATUS_HUELLA")
	private String desStatusHuella;

	public AdcDesStatusHuella() {
	}

	public String getCveStatusHuella() {
		return this.cveStatusHuella;
	}

	public void setCveStatusHuella(String cveStatusHuella) {
		this.cveStatusHuella = cveStatusHuella;
	}

	public String getDesStatusHuella() {
		return this.desStatusHuella;
	}

	public void setDesStatusHuella(String desStatusHuella) {
		this.desStatusHuella = desStatusHuella;
	}

}