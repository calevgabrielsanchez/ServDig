package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_TIPO_DOC_PROB database table.
 * 
 */
@Entity
@Table(name="ADC_TIPO_DOC_PROB")
@NamedQuery(name="AdcTipoDocProb.findAll", query="SELECT a FROM AdcTipoDocProb a")
public class AdcTipoDocProb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_TIPO_DOC_PROBATORIO")
	private long cveTipoDocProbatorio;

	@Column(name="DES_TIPO_DOC_PROBATORIO")
	private String desTipoDocProbatorio;

	//bi-directional many-to-one association to AdcDocProb
	@OneToMany(mappedBy="adcTipoDocProb")
	private List<AdcDocProb> adcDocProbs;

	public AdcTipoDocProb() {
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

	public List<AdcDocProb> getAdcDocProbs() {
		return this.adcDocProbs;
	}

	public void setAdcDocProbs(List<AdcDocProb> adcDocProbs) {
		this.adcDocProbs = adcDocProbs;
	}

	public AdcDocProb addAdcDocProb(AdcDocProb adcDocProb) {
		getAdcDocProbs().add(adcDocProb);
		adcDocProb.setAdcTipoDocProb(this);

		return adcDocProb;
	}

	public AdcDocProb removeAdcDocProb(AdcDocProb adcDocProb) {
		getAdcDocProbs().remove(adcDocProb);
		adcDocProb.setAdcTipoDocProb(null);

		return adcDocProb;
	}

}