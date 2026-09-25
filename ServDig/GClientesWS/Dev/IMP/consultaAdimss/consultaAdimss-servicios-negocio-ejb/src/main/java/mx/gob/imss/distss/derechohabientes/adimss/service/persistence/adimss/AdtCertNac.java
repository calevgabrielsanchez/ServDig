package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ADT_CERT_NAC database table.
 * 
 */
@Entity
@Table(name="ADT_CERT_NAC")
@NamedQuery(name="AdtCertNac.findAll", query="SELECT a FROM AdtCertNac a")
public class AdtCertNac implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_FOLIO_PERSONA")
	private long numFolioPersona;

	@Temporal(TemporalType.DATE)
	@Column(name="NUM_ANO_EXPEDICION_CER_NAT")
	private Date numAnoExpedicionCerNat;

	@Column(name="NUM_FOLIO_CER_NAT")
	private BigDecimal numFolioCerNat;

	//bi-directional one-to-one association to AdtPersonaCredencializada
	@OneToOne
	@JoinColumn(name="NUM_FOLIO_PERSONA")
	private AdtPersonaCredencializada adtPersonaCredencializada;

	public AdtCertNac() {
	}

	public long getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(long numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

	public Date getNumAnoExpedicionCerNat() {
		return this.numAnoExpedicionCerNat;
	}

	public void setNumAnoExpedicionCerNat(Date numAnoExpedicionCerNat) {
		this.numAnoExpedicionCerNat = numAnoExpedicionCerNat;
	}

	public BigDecimal getNumFolioCerNat() {
		return this.numFolioCerNat;
	}

	public void setNumFolioCerNat(BigDecimal numFolioCerNat) {
		this.numFolioCerNat = numFolioCerNat;
	}

	public AdtPersonaCredencializada getAdtPersonaCredencializada() {
		return this.adtPersonaCredencializada;
	}

	public void setAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		this.adtPersonaCredencializada = adtPersonaCredencializada;
	}

}