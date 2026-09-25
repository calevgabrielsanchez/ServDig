package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_TIPO_MOV database table.
 * 
 */
@Entity
@Table(name="ADC_TIPO_MOV")
@NamedQuery(name="AdcTipoMov.findAll", query="SELECT a FROM AdcTipoMov a")
public class AdcTipoMov implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_TIPO_REGISTRO")
	private long cveTipoRegistro;

	@Column(name="DES_TIPO_MOV")
	private String desTipoMov;

	//bi-directional many-to-one association to AdtCredencial
	@OneToMany(mappedBy="adcTipoMov")
	private List<AdtCredencial> adtCredencials;

	public AdcTipoMov() {
	}

	public long getCveTipoRegistro() {
		return this.cveTipoRegistro;
	}

	public void setCveTipoRegistro(long cveTipoRegistro) {
		this.cveTipoRegistro = cveTipoRegistro;
	}

	public String getDesTipoMov() {
		return this.desTipoMov;
	}

	public void setDesTipoMov(String desTipoMov) {
		this.desTipoMov = desTipoMov;
	}

	public List<AdtCredencial> getAdtCredencials() {
		return this.adtCredencials;
	}

	public void setAdtCredencials(List<AdtCredencial> adtCredencials) {
		this.adtCredencials = adtCredencials;
	}

	public AdtCredencial addAdtCredencial(AdtCredencial adtCredencial) {
		getAdtCredencials().add(adtCredencial);
		adtCredencial.setAdcTipoMov(this);

		return adtCredencial;
	}

	public AdtCredencial removeAdtCredencial(AdtCredencial adtCredencial) {
		getAdtCredencials().remove(adtCredencial);
		adtCredencial.setAdcTipoMov(null);

		return adtCredencial;
	}

}