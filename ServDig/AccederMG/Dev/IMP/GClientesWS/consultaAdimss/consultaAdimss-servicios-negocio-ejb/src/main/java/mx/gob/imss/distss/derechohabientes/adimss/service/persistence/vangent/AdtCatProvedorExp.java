package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_PROVEDOR_EXP database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_PROVEDOR_EXP")
@NamedQuery(name="AdtCatProvedorExp.findAll", query="SELECT a FROM AdtCatProvedorExp a")
public class AdtCatProvedorExp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_PROVEDOR_EXPIDE")
	private long cveProvedorExpide;

	@Column(name="DES_PROVEDOR_EXPIDE")
	private String desProvedorExpide;

	public AdtCatProvedorExp() {
	}

	public long getCveProvedorExpide() {
		return this.cveProvedorExpide;
	}

	public void setCveProvedorExpide(long cveProvedorExpide) {
		this.cveProvedorExpide = cveProvedorExpide;
	}

	public String getDesProvedorExpide() {
		return this.desProvedorExpide;
	}

	public void setDesProvedorExpide(String desProvedorExpide) {
		this.desProvedorExpide = desProvedorExpide;
	}

}