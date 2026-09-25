package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_DOC_NACIONALIDAD database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_DOC_NACIONALIDAD")
@NamedQuery(name="AdtCatDocNacionalidad.findAll", query="SELECT a FROM AdtCatDocNacionalidad a")
public class AdtCatDocNacionalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_TIPO_DOC_PROB_NAC")
	private long cveTipoDocProbNac;

	@Column(name="DESC_TIPO_DOC_PROB_NAC")
	private String descTipoDocProbNac;

	public AdtCatDocNacionalidad() {
	}

	public long getCveTipoDocProbNac() {
		return this.cveTipoDocProbNac;
	}

	public void setCveTipoDocProbNac(long cveTipoDocProbNac) {
		this.cveTipoDocProbNac = cveTipoDocProbNac;
	}

	public String getDescTipoDocProbNac() {
		return this.descTipoDocProbNac;
	}

	public void setDescTipoDocProbNac(String descTipoDocProbNac) {
		this.descTipoDocProbNac = descTipoDocProbNac;
	}

}