package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_DOC_PROB database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_DOC_PROB")
@NamedQuery(name="AdtCatDocProb.findAll", query="SELECT a FROM AdtCatDocProb a")
public class AdtCatDocProb implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdtCatDocProbPK id;

	@Column(name="DES_DOC_PROB")
	private String desDocProb;

	public AdtCatDocProb() {
	}

	public AdtCatDocProbPK getId() {
		return this.id;
	}

	public void setId(AdtCatDocProbPK id) {
		this.id = id;
	}

	public String getDesDocProb() {
		return this.desDocProb;
	}

	public void setDesDocProb(String desDocProb) {
		this.desDocProb = desDocProb;
	}

}