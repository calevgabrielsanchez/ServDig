package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_NAL database table.
 * 
 */
@Entity
@Table(name="ADC_NAL")
@NamedQuery(name="AdcNal.findAll", query="SELECT a FROM AdcNal a")
public class AdcNal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_NACIONALIDAD")
	private String cveNacionalidad;

	@Column(name="DES_NACIONALIDAD")
	private String desNacionalidad;

	@Column(name="DES_PAIS")
	private String desPais;

	//bi-directional many-to-one association to AdtPersonaCredencializada
	@OneToMany(mappedBy="adcNal")
	private List<AdtPersonaCredencializada> adtPersonaCredencializadas;

	public AdcNal() {
	}

	public String getCveNacionalidad() {
		return this.cveNacionalidad;
	}

	public void setCveNacionalidad(String cveNacionalidad) {
		this.cveNacionalidad = cveNacionalidad;
	}

	public String getDesNacionalidad() {
		return this.desNacionalidad;
	}

	public void setDesNacionalidad(String desNacionalidad) {
		this.desNacionalidad = desNacionalidad;
	}

	public String getDesPais() {
		return this.desPais;
	}

	public void setDesPais(String desPais) {
		this.desPais = desPais;
	}

	public List<AdtPersonaCredencializada> getAdtPersonaCredencializadas() {
		return this.adtPersonaCredencializadas;
	}

	public void setAdtPersonaCredencializadas(List<AdtPersonaCredencializada> adtPersonaCredencializadas) {
		this.adtPersonaCredencializadas = adtPersonaCredencializadas;
	}

	public AdtPersonaCredencializada addAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().add(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdcNal(this);

		return adtPersonaCredencializada;
	}

	public AdtPersonaCredencializada removeAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().remove(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdcNal(null);

		return adtPersonaCredencializada;
	}

}