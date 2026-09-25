package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_EDO_CIVIL database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_EDO_CIVIL")
@NamedQuery(name="AdtCatEdoCivil.findAll", query="SELECT a FROM AdtCatEdoCivil a")
public class AdtCatEdoCivil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_EDO_CIVIL")
	private long cveEdoCivil;

	@Column(name="DES_EDO_CIVIL")
	private String desEdoCivil;

	public AdtCatEdoCivil() {
	}

	public long getCveEdoCivil() {
		return this.cveEdoCivil;
	}

	public void setCveEdoCivil(long cveEdoCivil) {
		this.cveEdoCivil = cveEdoCivil;
	}

	public String getDesEdoCivil() {
		return this.desEdoCivil;
	}

	public void setDesEdoCivil(String desEdoCivil) {
		this.desEdoCivil = desEdoCivil;
	}

}