package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ADT_CARTA_NATURAL database table.
 * 
 */
@Entity
@Table(name="ADT_CARTA_NATURAL")
@NamedQuery(name="AdtCartaNatural.findAll", query="SELECT a FROM AdtCartaNatural a")
public class AdtCartaNatural implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_FOLIO_PERSONA")
	private long numFolioPersona;

	@Column(name="NUM_ANO_EXPEDICION_CARTA")
	private BigDecimal numAnoExpedicionCarta;

	@Column(name="NUM_FOLIO_CARTA")
	private BigDecimal numFolioCarta;

	//bi-directional many-to-one association to AdcPai
	@ManyToOne
	@JoinColumn(name="CVE_PAIS_ORIGEN_NAC")
	private AdcPai adcPai;

	//bi-directional one-to-one association to AdtPersonaCredencializada
	@OneToOne
	@JoinColumn(name="NUM_FOLIO_PERSONA")
	private AdtPersonaCredencializada adtPersonaCredencializada;

	public AdtCartaNatural() {
	}

	public long getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(long numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

	public BigDecimal getNumAnoExpedicionCarta() {
		return this.numAnoExpedicionCarta;
	}

	public void setNumAnoExpedicionCarta(BigDecimal numAnoExpedicionCarta) {
		this.numAnoExpedicionCarta = numAnoExpedicionCarta;
	}

	public BigDecimal getNumFolioCarta() {
		return this.numFolioCarta;
	}

	public void setNumFolioCarta(BigDecimal numFolioCarta) {
		this.numFolioCarta = numFolioCarta;
	}

	public AdcPai getAdcPai() {
		return this.adcPai;
	}

	public void setAdcPai(AdcPai adcPai) {
		this.adcPai = adcPai;
	}

	public AdtPersonaCredencializada getAdtPersonaCredencializada() {
		return this.adtPersonaCredencializada;
	}

	public void setAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		this.adtPersonaCredencializada = adtPersonaCredencializada;
	}

}