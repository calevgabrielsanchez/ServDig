package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_DOC_PROB database table.
 * 
 */
@Entity
@Table(name="ADC_DOC_PROB")
@NamedQuery(name="AdcDocProb.findAll", query="SELECT a FROM AdcDocProb a")
public class AdcDocProb implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdcDocProbPK id;

	@Column(name="DES_DOC_PROB")
	private String desDocProb;

	//bi-directional many-to-one association to AdcTipoDocProb
	@ManyToOne
	@JoinColumn(name="CVE_TIPO_DOC_PROBATORIO", insertable=false, updatable=false)
	private AdcTipoDocProb adcTipoDocProb;

	//bi-directional many-to-one association to AdtTramiteDocProb
	@OneToMany(mappedBy="adcDocProb")
	private List<AdtTramiteDocProb> adtTramiteDocProbs;

	public AdcDocProb() {
	}

	public AdcDocProbPK getId() {
		return this.id;
	}

	public void setId(AdcDocProbPK id) {
		this.id = id;
	}

	public String getDesDocProb() {
		return this.desDocProb;
	}

	public void setDesDocProb(String desDocProb) {
		this.desDocProb = desDocProb;
	}

	public AdcTipoDocProb getAdcTipoDocProb() {
		return this.adcTipoDocProb;
	}

	public void setAdcTipoDocProb(AdcTipoDocProb adcTipoDocProb) {
		this.adcTipoDocProb = adcTipoDocProb;
	}

	public List<AdtTramiteDocProb> getAdtTramiteDocProbs() {
		return this.adtTramiteDocProbs;
	}

	public void setAdtTramiteDocProbs(List<AdtTramiteDocProb> adtTramiteDocProbs) {
		this.adtTramiteDocProbs = adtTramiteDocProbs;
	}

	public AdtTramiteDocProb addAdtTramiteDocProb(AdtTramiteDocProb adtTramiteDocProb) {
		getAdtTramiteDocProbs().add(adtTramiteDocProb);
		adtTramiteDocProb.setAdcDocProb(this);

		return adtTramiteDocProb;
	}

	public AdtTramiteDocProb removeAdtTramiteDocProb(AdtTramiteDocProb adtTramiteDocProb) {
		getAdtTramiteDocProbs().remove(adtTramiteDocProb);
		adtTramiteDocProb.setAdcDocProb(null);

		return adtTramiteDocProb;
	}

}