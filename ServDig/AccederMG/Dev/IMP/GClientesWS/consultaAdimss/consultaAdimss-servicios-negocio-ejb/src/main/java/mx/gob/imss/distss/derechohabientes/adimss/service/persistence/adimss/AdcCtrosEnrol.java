package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_CTROS_ENROL database table.
 * 
 */
@Entity
@Table(name="ADC_CTROS_ENROL")
@NamedQuery(name="AdcCtrosEnrol.findAll", query="SELECT a FROM AdcCtrosEnrol a")
public class AdcCtrosEnrol implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdcCtrosEnrolPK id;

	@Column(name="CVE_PREI")
	private String cvePrei;

	@Column(name="DES_CTRO_ENROL")
	private String desCtroEnrol;

	//bi-directional many-to-one association to AdcDelegacion
	@ManyToOne
	@JoinColumn(name="CVE_DELEGACION", insertable=false, updatable=false)
	private AdcDelegacion adcDelegacion;

	//bi-directional many-to-one association to AdcNivelAten
	@ManyToOne
	@JoinColumn(name="NUM_NIVEL_ATENCION", insertable=false, updatable=false)
	private AdcNivelAten adcNivelAten;

	//bi-directional many-to-one association to AdtCredencial
	@OneToMany(mappedBy="adcCtrosEnrol")
	private List<AdtCredencial> adtCredencials;

	public AdcCtrosEnrol() {
	}

	public AdcCtrosEnrolPK getId() {
		return this.id;
	}

	public void setId(AdcCtrosEnrolPK id) {
		this.id = id;
	}

	public String getCvePrei() {
		return this.cvePrei;
	}

	public void setCvePrei(String cvePrei) {
		this.cvePrei = cvePrei;
	}

	public String getDesCtroEnrol() {
		return this.desCtroEnrol;
	}

	public void setDesCtroEnrol(String desCtroEnrol) {
		this.desCtroEnrol = desCtroEnrol;
	}

	public AdcDelegacion getAdcDelegacion() {
		return this.adcDelegacion;
	}

	public void setAdcDelegacion(AdcDelegacion adcDelegacion) {
		this.adcDelegacion = adcDelegacion;
	}

	public AdcNivelAten getAdcNivelAten() {
		return this.adcNivelAten;
	}

	public void setAdcNivelAten(AdcNivelAten adcNivelAten) {
		this.adcNivelAten = adcNivelAten;
	}

	public List<AdtCredencial> getAdtCredencials() {
		return this.adtCredencials;
	}

	public void setAdtCredencials(List<AdtCredencial> adtCredencials) {
		this.adtCredencials = adtCredencials;
	}

	public AdtCredencial addAdtCredencial(AdtCredencial adtCredencial) {
		getAdtCredencials().add(adtCredencial);
		adtCredencial.setAdcCtrosEnrol(this);

		return adtCredencial;
	}

	public AdtCredencial removeAdtCredencial(AdtCredencial adtCredencial) {
		getAdtCredencials().remove(adtCredencial);
		adtCredencial.setAdcCtrosEnrol(null);

		return adtCredencial;
	}

}