package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_TIPO_DOC_PROB database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_TIPO_DOC_PROB")
@NamedQuery(name="AdtCatTipoDocProb.findAll", query="SELECT a FROM AdtCatTipoDocProb a")
public class AdtCatTipoDocProb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_TIPO_DOC_PROBATORIO")
	private long cveTipoDocProbatorio;

	@Column(name="DES_TIPO_DOC_PROBATORIO")
	private String desTipoDocProbatorio;

	public AdtCatTipoDocProb() {
	}

	public long getCveTipoDocProbatorio() {
		return this.cveTipoDocProbatorio;
	}

	public void setCveTipoDocProbatorio(long cveTipoDocProbatorio) {
		this.cveTipoDocProbatorio = cveTipoDocProbatorio;
	}

	public String getDesTipoDocProbatorio() {
		return this.desTipoDocProbatorio;
	}

	public void setDesTipoDocProbatorio(String desTipoDocProbatorio) {
		this.desTipoDocProbatorio = desTipoDocProbatorio;
	}

}