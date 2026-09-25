package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_DES_STATUS_HUELLA database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_DES_STATUS_HUELLA")
@NamedQuery(name="AdtCatDesStatusHuella.findAll", query="SELECT a FROM AdtCatDesStatusHuella a")
public class AdtCatDesStatusHuella implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_STATUS_HUELLA")
	private String cveStatusHuella;

	@Column(name="DES_STATUS_HUELLA")
	private String desStatusHuella;

	public AdtCatDesStatusHuella() {
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