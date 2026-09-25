package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_CALIDAD_DERECHOHABIENT database table.
 * 
 */
@Entity
@Table(name="ADC_CALIDAD_DERECHOHABIENT")
@NamedQuery(name="AdcCalidadDerechohabient.findAll", query="SELECT a FROM AdcCalidadDerechohabient a")
public class AdcCalidadDerechohabient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_CALIDAD_ASEG")
	private long cveCalidadAseg;

	@Column(name="DES_CALIDAD")
	private String desCalidad;

	//bi-directional many-to-one association to AdtCredencial
	@OneToMany(mappedBy="adcCalidadDerechohabient")
	private List<AdtCredencial> adtCredencials;

	public AdcCalidadDerechohabient() {
	}

	public long getCveCalidadAseg() {
		return this.cveCalidadAseg;
	}

	public void setCveCalidadAseg(long cveCalidadAseg) {
		this.cveCalidadAseg = cveCalidadAseg;
	}

	public String getDesCalidad() {
		return this.desCalidad;
	}

	public void setDesCalidad(String desCalidad) {
		this.desCalidad = desCalidad;
	}

	public List<AdtCredencial> getAdtCredencials() {
		return this.adtCredencials;
	}

	public void setAdtCredencials(List<AdtCredencial> adtCredencials) {
		this.adtCredencials = adtCredencials;
	}

	public AdtCredencial addAdtCredencial(AdtCredencial adtCredencial) {
		getAdtCredencials().add(adtCredencial);
		adtCredencial.setAdcCalidadDerechohabient(this);

		return adtCredencial;
	}

	public AdtCredencial removeAdtCredencial(AdtCredencial adtCredencial) {
		getAdtCredencials().remove(adtCredencial);
		adtCredencial.setAdcCalidadDerechohabient(null);

		return adtCredencial;
	}

}