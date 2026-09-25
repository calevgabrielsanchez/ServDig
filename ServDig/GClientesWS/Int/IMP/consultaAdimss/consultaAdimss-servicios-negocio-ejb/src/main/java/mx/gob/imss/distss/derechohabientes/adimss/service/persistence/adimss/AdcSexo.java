package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_SEXO database table.
 * 
 */
@Entity
@Table(name="ADC_SEXO")
@NamedQuery(name="AdcSexo.findAll", query="SELECT a FROM AdcSexo a")
public class AdcSexo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_SEXO")
	private String cveSexo;

	@Column(name="DES_SEXO")
	private String desSexo;

	//bi-directional many-to-one association to AdtPersonaCredencializada
	@OneToMany(mappedBy="adcSexo")
	private List<AdtPersonaCredencializada> adtPersonaCredencializadas;

	public AdcSexo() {
	}

	public String getCveSexo() {
		return this.cveSexo;
	}

	public void setCveSexo(String cveSexo) {
		this.cveSexo = cveSexo;
	}

	public String getDesSexo() {
		return this.desSexo;
	}

	public void setDesSexo(String desSexo) {
		this.desSexo = desSexo;
	}

	public List<AdtPersonaCredencializada> getAdtPersonaCredencializadas() {
		return this.adtPersonaCredencializadas;
	}

	public void setAdtPersonaCredencializadas(List<AdtPersonaCredencializada> adtPersonaCredencializadas) {
		this.adtPersonaCredencializadas = adtPersonaCredencializadas;
	}

	public AdtPersonaCredencializada addAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().add(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdcSexo(this);

		return adtPersonaCredencializada;
	}

	public AdtPersonaCredencializada removeAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().remove(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdcSexo(null);

		return adtPersonaCredencializada;
	}

}