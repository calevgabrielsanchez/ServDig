package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_PAIS database table.
 * 
 */
@Entity
@Table(name="ADC_PAIS")
@NamedQuery(name="AdcPai.findAll", query="SELECT a FROM AdcPai a")
public class AdcPai implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_PAIS")
	private String cvePais;

	@Column(name="DES_PAIS")
	private String desPais;

	//bi-directional many-to-one association to AdtCartaNatural
	@OneToMany(mappedBy="adcPai")
	private List<AdtCartaNatural> adtCartaNaturals;

	public AdcPai() {
	}

	public String getCvePais() {
		return this.cvePais;
	}

	public void setCvePais(String cvePais) {
		this.cvePais = cvePais;
	}

	public String getDesPais() {
		return this.desPais;
	}

	public void setDesPais(String desPais) {
		this.desPais = desPais;
	}

	public List<AdtCartaNatural> getAdtCartaNaturals() {
		return this.adtCartaNaturals;
	}

	public void setAdtCartaNaturals(List<AdtCartaNatural> adtCartaNaturals) {
		this.adtCartaNaturals = adtCartaNaturals;
	}

	public AdtCartaNatural addAdtCartaNatural(AdtCartaNatural adtCartaNatural) {
		getAdtCartaNaturals().add(adtCartaNatural);
		adtCartaNatural.setAdcPai(this);

		return adtCartaNatural;
	}

	public AdtCartaNatural removeAdtCartaNatural(AdtCartaNatural adtCartaNatural) {
		getAdtCartaNaturals().remove(adtCartaNatural);
		adtCartaNatural.setAdcPai(null);

		return adtCartaNatural;
	}

}