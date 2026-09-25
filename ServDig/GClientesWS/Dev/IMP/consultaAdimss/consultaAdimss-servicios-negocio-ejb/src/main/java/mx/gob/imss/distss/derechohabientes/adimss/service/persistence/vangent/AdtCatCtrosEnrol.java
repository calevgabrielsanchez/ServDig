package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_CTROS_ENROL database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_CTROS_ENROL")
@NamedQuery(name="AdtCatCtrosEnrol.findAll", query="SELECT a FROM AdtCatCtrosEnrol a")
public class AdtCatCtrosEnrol implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdtCatCtrosEnrolPK id;

	@Column(name="CVE_PREI")
	private String cvePrei;

	@Column(name="DES_CTRO_ENROL")
	private String desCtroEnrol;

	public AdtCatCtrosEnrol() {
	}

	public AdtCatCtrosEnrolPK getId() {
		return this.id;
	}

	public void setId(AdtCatCtrosEnrolPK id) {
		this.id = id;
	}

	public String getCvePrei() {
		return this.cvePrei;
	}

	public void setCvePrei(String cvePrei) {
		this.cvePrei = cvePrei;
	}

	public String getDesCtroEnrol() {
		return this.desCtroEnrol;
	}

	public void setDesCtroEnrol(String desCtroEnrol) {
		this.desCtroEnrol = desCtroEnrol;
	}

}