package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_EDO_CIVIL database table.
 * 
 */
@Entity
@Table(name="ADC_EDO_CIVIL")
@NamedQuery(name="AdcEdoCivil.findAll", query="SELECT a FROM AdcEdoCivil a")
public class AdcEdoCivil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_EDO_CIVIL")
	private long cveEdoCivil;

	@Column(name="DES_EDO_CIVIL")
	private String desEdoCivil;

	//bi-directional many-to-one association to AdtPersonaCredencializada
	@OneToMany(mappedBy="adcEdoCivil")
	private List<AdtPersonaCredencializada> adtPersonaCredencializadas;

	public AdcEdoCivil() {
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

	public List<AdtPersonaCredencializada> getAdtPersonaCredencializadas() {
		return this.adtPersonaCredencializadas;
	}

	public void setAdtPersonaCredencializadas(List<AdtPersonaCredencializada> adtPersonaCredencializadas) {
		this.adtPersonaCredencializadas = adtPersonaCredencializadas;
	}

	public AdtPersonaCredencializada addAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().add(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdcEdoCivil(this);

		return adtPersonaCredencializada;
	}

	public AdtPersonaCredencializada removeAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().remove(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdcEdoCivil(null);

		return adtPersonaCredencializada;
	}

}