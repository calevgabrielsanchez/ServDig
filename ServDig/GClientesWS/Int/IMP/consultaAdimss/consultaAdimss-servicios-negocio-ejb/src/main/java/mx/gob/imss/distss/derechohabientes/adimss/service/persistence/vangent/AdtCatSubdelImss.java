package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_SUBDEL_IMSS database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_SUBDEL_IMSS")
@NamedQuery(name="AdtCatSubdelImss.findAll", query="SELECT a FROM AdtCatSubdelImss a")
public class AdtCatSubdelImss implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdtCatSubdelImssPK id;

	@Column(name="DES_SUBDEL_IMSS")
	private String desSubdelImss;

	public AdtCatSubdelImss() {
	}

	public AdtCatSubdelImssPK getId() {
		return this.id;
	}

	public void setId(AdtCatSubdelImssPK id) {
		this.id = id;
	}

	public String getDesSubdelImss() {
		return this.desSubdelImss;
	}

	public void setDesSubdelImss(String desSubdelImss) {
		this.desSubdelImss = desSubdelImss;
	}

}