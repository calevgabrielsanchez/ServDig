package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_DOC_NACIONALIDAD database table.
 * 
 */
@Entity
@Table(name="ADC_DOC_NACIONALIDAD")
@NamedQuery(name="AdcDocNacionalidad.findAll", query="SELECT a FROM AdcDocNacionalidad a")
public class AdcDocNacionalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_TIPO_DOC_PROB_NAC")
	private long cveTipoDocProbNac;

	@Column(name="DESC_TIPO_DOC_PROB_NAC")
	private String descTipoDocProbNac;

	//bi-directional many-to-one association to AdtPersonaCredencializada
	@OneToMany(mappedBy="adcDocNacionalidad")
	private List<AdtPersonaCredencializada> adtPersonaCredencializadas;

	public AdcDocNacionalidad() {
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

	public List<AdtPersonaCredencializada> getAdtPersonaCredencializadas() {
		return this.adtPersonaCredencializadas;
	}

	public void setAdtPersonaCredencializadas(List<AdtPersonaCredencializada> adtPersonaCredencializadas) {
		this.adtPersonaCredencializadas = adtPersonaCredencializadas;
	}

	public AdtPersonaCredencializada addAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().add(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdcDocNacionalidad(this);

		return adtPersonaCredencializada;
	}

	public AdtPersonaCredencializada removeAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().remove(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdcDocNacionalidad(null);

		return adtPersonaCredencializada;
	}

}