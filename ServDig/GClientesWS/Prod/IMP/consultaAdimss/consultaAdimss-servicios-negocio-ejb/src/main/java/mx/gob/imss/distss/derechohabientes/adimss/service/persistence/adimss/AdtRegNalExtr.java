package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the ADT_REG_NAL_EXTR database table.
 * 
 */
@Entity
@Table(name="ADT_REG_NAL_EXTR")
@NamedQuery(name="AdtRegNalExtr.findAll", query="SELECT a FROM AdtRegNalExtr a")
public class AdtRegNalExtr implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="NUM_REG_NAL_EXTR")
	private BigDecimal numRegNalExtr;

	//bi-directional many-to-one association to AdtPersonaCredencializada
	@ManyToOne
	@JoinColumn(name="NUM_FOLIO_PERSONA")
	private AdtPersonaCredencializada adtPersonaCredencializada;

	public AdtRegNalExtr() {
	}

	public BigDecimal getNumRegNalExtr() {
		return this.numRegNalExtr;
	}

	public void setNumRegNalExtr(BigDecimal numRegNalExtr) {
		this.numRegNalExtr = numRegNalExtr;
	}

	public AdtPersonaCredencializada getAdtPersonaCredencializada() {
		return this.adtPersonaCredencializada;
	}

	public void setAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		this.adtPersonaCredencializada = adtPersonaCredencializada;
	}

}