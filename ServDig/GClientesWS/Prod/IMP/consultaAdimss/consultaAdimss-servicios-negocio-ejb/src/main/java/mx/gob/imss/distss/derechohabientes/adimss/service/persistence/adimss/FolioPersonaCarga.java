package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the FOLIO_PERSONA_CARGA database table.
 * 
 */
@Embeddable
@Table(name="FOLIO_PERSONA_CARGA")
@NamedQuery(name="FolioPersonaCarga.findAll", query="SELECT f FROM FolioPersonaCarga f")
public class FolioPersonaCarga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_FOLIO_PERSONA")
	private BigDecimal numFolioPersona;

	public FolioPersonaCarga() {
	}

	public BigDecimal getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(BigDecimal numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

}