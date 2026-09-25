package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the ADT_ARC_MIN_HUELLAS_ESP database table.
 * 
 */
@Embeddable
@Table(name="ADT_ARC_MIN_HUELLAS_ESP")
@NamedQuery(name="AdtArcMinHuellasEsp.findAll", query="SELECT a FROM AdtArcMinHuellasEsp a")
public class AdtArcMinHuellasEsp implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal fingerprints;

	@Column(name="NUM_FOLIO_PERSONA")
	private BigDecimal numFolioPersona;

	public AdtArcMinHuellasEsp() {
	}

	public BigDecimal getFingerprints() {
		return this.fingerprints;
	}

	public void setFingerprints(BigDecimal fingerprints) {
		this.fingerprints = fingerprints;
	}

	public BigDecimal getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(BigDecimal numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

}