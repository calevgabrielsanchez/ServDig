package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_NIVEL_ATEN database table.
 * 
 */
@Entity
@Table(name="ADC_NIVEL_ATEN")
@NamedQuery(name="AdcNivelAten.findAll", query="SELECT a FROM AdcNivelAten a")
public class AdcNivelAten implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_NIVEL_ATENCION")
	private long numNivelAtencion;

	@Column(name="DESC_NIVEL_ATENCION")
	private String descNivelAtencion;

	//bi-directional many-to-one association to AdcCtrosEnrol
	@OneToMany(mappedBy="adcNivelAten")
	private List<AdcCtrosEnrol> adcCtrosEnrols;

	public AdcNivelAten() {
	}

	public long getNumNivelAtencion() {
		return this.numNivelAtencion;
	}

	public void setNumNivelAtencion(long numNivelAtencion) {
		this.numNivelAtencion = numNivelAtencion;
	}

	public String getDescNivelAtencion() {
		return this.descNivelAtencion;
	}

	public void setDescNivelAtencion(String descNivelAtencion) {
		this.descNivelAtencion = descNivelAtencion;
	}

	public List<AdcCtrosEnrol> getAdcCtrosEnrols() {
		return this.adcCtrosEnrols;
	}

	public void setAdcCtrosEnrols(List<AdcCtrosEnrol> adcCtrosEnrols) {
		this.adcCtrosEnrols = adcCtrosEnrols;
	}

	public AdcCtrosEnrol addAdcCtrosEnrol(AdcCtrosEnrol adcCtrosEnrol) {
		getAdcCtrosEnrols().add(adcCtrosEnrol);
		adcCtrosEnrol.setAdcNivelAten(this);

		return adcCtrosEnrol;
	}

	public AdcCtrosEnrol removeAdcCtrosEnrol(AdcCtrosEnrol adcCtrosEnrol) {
		getAdcCtrosEnrols().remove(adcCtrosEnrol);
		adcCtrosEnrol.setAdcNivelAten(null);

		return adcCtrosEnrol;
	}

}