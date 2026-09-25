package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_DELEGACION database table.
 * 
 */
@Entity
@Table(name="ADC_DELEGACION")
@NamedQuery(name="AdcDelegacion.findAll", query="SELECT a FROM AdcDelegacion a")
public class AdcDelegacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_DELEGACION")
	private long cveDelegacion;

	@Column(name="DES_DELEGACION")
	private String desDelegacion;

	//bi-directional many-to-one association to AdcCtrosEnrol
	@OneToMany(mappedBy="adcDelegacion")
	private List<AdcCtrosEnrol> adcCtrosEnrols;

	public AdcDelegacion() {
	}

	public long getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getDesDelegacion() {
		return this.desDelegacion;
	}

	public void setDesDelegacion(String desDelegacion) {
		this.desDelegacion = desDelegacion;
	}

	public List<AdcCtrosEnrol> getAdcCtrosEnrols() {
		return this.adcCtrosEnrols;
	}

	public void setAdcCtrosEnrols(List<AdcCtrosEnrol> adcCtrosEnrols) {
		this.adcCtrosEnrols = adcCtrosEnrols;
	}

	public AdcCtrosEnrol addAdcCtrosEnrol(AdcCtrosEnrol adcCtrosEnrol) {
		getAdcCtrosEnrols().add(adcCtrosEnrol);
		adcCtrosEnrol.setAdcDelegacion(this);

		return adcCtrosEnrol;
	}

	public AdcCtrosEnrol removeAdcCtrosEnrol(AdcCtrosEnrol adcCtrosEnrol) {
		getAdcCtrosEnrols().remove(adcCtrosEnrol);
		adcCtrosEnrol.setAdcDelegacion(null);

		return adcCtrosEnrol;
	}

}