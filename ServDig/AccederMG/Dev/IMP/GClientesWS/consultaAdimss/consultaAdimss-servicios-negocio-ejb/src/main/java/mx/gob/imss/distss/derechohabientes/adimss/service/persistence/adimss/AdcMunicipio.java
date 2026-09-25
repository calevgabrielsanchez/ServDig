package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_MUNICIPIOS database table.
 * 
 */
@Entity
@Table(name="ADC_MUNICIPIOS")
@NamedQuery(name="AdcMunicipio.findAll", query="SELECT a FROM AdcMunicipio a")
public class AdcMunicipio implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdcMunicipioPK id;

	@Column(name="DES_MUNICIPIO")
	private String desMunicipio;

	//bi-directional many-to-one association to AdcEntFed
	@ManyToOne
	@JoinColumn(name="CVE_ENT_FEDERATIVA", insertable=false, updatable=false)
	private AdcEntFed adcEntFed;

	//bi-directional many-to-one association to AdtPersonaCredencializada
	@OneToMany(mappedBy="adcMunicipio")
	private List<AdtPersonaCredencializada> adtPersonaCredencializadas;

	public AdcMunicipio() {
	}

	public AdcMunicipioPK getId() {
		return this.id;
	}

	public void setId(AdcMunicipioPK id) {
		this.id = id;
	}

	public String getDesMunicipio() {
		return this.desMunicipio;
	}

	public void setDesMunicipio(String desMunicipio) {
		this.desMunicipio = desMunicipio;
	}

	public AdcEntFed getAdcEntFed() {
		return this.adcEntFed;
	}

	public void setAdcEntFed(AdcEntFed adcEntFed) {
		this.adcEntFed = adcEntFed;
	}

	public List<AdtPersonaCredencializada> getAdtPersonaCredencializadas() {
		return this.adtPersonaCredencializadas;
	}

	public void setAdtPersonaCredencializadas(List<AdtPersonaCredencializada> adtPersonaCredencializadas) {
		this.adtPersonaCredencializadas = adtPersonaCredencializadas;
	}

	public AdtPersonaCredencializada addAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().add(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdcMunicipio(this);

		return adtPersonaCredencializada;
	}

	public AdtPersonaCredencializada removeAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().remove(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdcMunicipio(null);

		return adtPersonaCredencializada;
	}

}