package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_STATUS_FIRMA database table.
 * 
 */
@Entity
@Table(name="ADC_STATUS_FIRMA")
@NamedQuery(name="AdcStatusFirma.findAll", query="SELECT a FROM AdcStatusFirma a")
public class AdcStatusFirma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_STATUS_FIRMA")
	private String cveStatusFirma;

	@Column(name="DESC_STATUS_FIRMA")
	private String descStatusFirma;

	//bi-directional many-to-one association to AdtCredencial
	@OneToMany(mappedBy="adcStatusFirma")
	private List<AdtCredencial> adtCredencials;

	public AdcStatusFirma() {
	}

	public String getCveStatusFirma() {
		return this.cveStatusFirma;
	}

	public void setCveStatusFirma(String cveStatusFirma) {
		this.cveStatusFirma = cveStatusFirma;
	}

	public String getDescStatusFirma() {
		return this.descStatusFirma;
	}

	public void setDescStatusFirma(String descStatusFirma) {
		this.descStatusFirma = descStatusFirma;
	}

	public List<AdtCredencial> getAdtCredencials() {
		return this.adtCredencials;
	}

	public void setAdtCredencials(List<AdtCredencial> adtCredencials) {
		this.adtCredencials = adtCredencials;
	}

	public AdtCredencial addAdtCredencial(AdtCredencial adtCredencial) {
		getAdtCredencials().add(adtCredencial);
		adtCredencial.setAdcStatusFirma(this);

		return adtCredencial;
	}

	public AdtCredencial removeAdtCredencial(AdtCredencial adtCredencial) {
		getAdtCredencials().remove(adtCredencial);
		adtCredencial.setAdcStatusFirma(null);

		return adtCredencial;
	}

}