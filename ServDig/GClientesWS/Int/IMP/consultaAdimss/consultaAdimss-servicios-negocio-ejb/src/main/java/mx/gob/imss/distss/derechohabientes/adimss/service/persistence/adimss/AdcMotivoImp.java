package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADC_MOTIVO_IMP database table.
 * 
 */
@Entity
@Table(name="ADC_MOTIVO_IMP")
@NamedQuery(name="AdcMotivoImp.findAll", query="SELECT a FROM AdcMotivoImp a")
public class AdcMotivoImp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_MOTIVO_IMPRESION")
	private long cveMotivoImpresion;

	@Column(name="DESC_MOTIVO_IMPRESION")
	private String descMotivoImpresion;

	//bi-directional many-to-one association to AdtImpresione
	@OneToMany(mappedBy="adcMotivoImp")
	private List<AdtImpresione> adtImpresiones;

	public AdcMotivoImp() {
	}

	public long getCveMotivoImpresion() {
		return this.cveMotivoImpresion;
	}

	public void setCveMotivoImpresion(long cveMotivoImpresion) {
		this.cveMotivoImpresion = cveMotivoImpresion;
	}

	public String getDescMotivoImpresion() {
		return this.descMotivoImpresion;
	}

	public void setDescMotivoImpresion(String descMotivoImpresion) {
		this.descMotivoImpresion = descMotivoImpresion;
	}

	public List<AdtImpresione> getAdtImpresiones() {
		return this.adtImpresiones;
	}

	public void setAdtImpresiones(List<AdtImpresione> adtImpresiones) {
		this.adtImpresiones = adtImpresiones;
	}

	public AdtImpresione addAdtImpresione(AdtImpresione adtImpresione) {
		getAdtImpresiones().add(adtImpresione);
		adtImpresione.setAdcMotivoImp(this);

		return adtImpresione;
	}

	public AdtImpresione removeAdtImpresione(AdtImpresione adtImpresione) {
		getAdtImpresiones().remove(adtImpresione);
		adtImpresione.setAdcMotivoImp(null);

		return adtImpresione;
	}

}