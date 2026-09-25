package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_ENT_FED database table.
 * 
 */
@Entity
@Table(name="ADC_ENT_FED")
@NamedQuery(name="AdcEntFed.findAll", query="SELECT a FROM AdcEntFed a")
public class AdcEntFed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ENTIDAD")
	private long cveEntidad;

	@Column(name="DES_ABREV_EDO")
	private String desAbrevEdo;

	@Column(name="DES_ENTIDAD")
	private String desEntidad;

	//bi-directional many-to-one association to AdcMunicipio
	@OneToMany(mappedBy="adcEntFed")
	private List<AdcMunicipio> adcMunicipios;

	public AdcEntFed() {
	}

	public long getCveEntidad() {
		return this.cveEntidad;
	}

	public void setCveEntidad(long cveEntidad) {
		this.cveEntidad = cveEntidad;
	}

	public String getDesAbrevEdo() {
		return this.desAbrevEdo;
	}

	public void setDesAbrevEdo(String desAbrevEdo) {
		this.desAbrevEdo = desAbrevEdo;
	}

	public String getDesEntidad() {
		return this.desEntidad;
	}

	public void setDesEntidad(String desEntidad) {
		this.desEntidad = desEntidad;
	}

	public List<AdcMunicipio> getAdcMunicipios() {
		return this.adcMunicipios;
	}

	public void setAdcMunicipios(List<AdcMunicipio> adcMunicipios) {
		this.adcMunicipios = adcMunicipios;
	}

	public AdcMunicipio addAdcMunicipio(AdcMunicipio adcMunicipio) {
		getAdcMunicipios().add(adcMunicipio);
		adcMunicipio.setAdcEntFed(this);

		return adcMunicipio;
	}

	public AdcMunicipio removeAdcMunicipio(AdcMunicipio adcMunicipio) {
		getAdcMunicipios().remove(adcMunicipio);
		adcMunicipio.setAdcEntFed(null);

		return adcMunicipio;
	}

}