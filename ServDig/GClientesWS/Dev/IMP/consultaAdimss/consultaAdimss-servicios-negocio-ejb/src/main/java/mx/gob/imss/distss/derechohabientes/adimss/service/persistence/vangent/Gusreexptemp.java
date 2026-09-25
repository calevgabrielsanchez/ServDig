package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the GUSREEXPTEMP database table.
 * 
 */
@Embeddable
@NamedQuery(name="Gusreexptemp.findAll", query="SELECT g FROM Gusreexptemp g")
public class Gusreexptemp implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal idenrol;

	private BigDecimal idenrollmentstation;

	@Column(name="NUM_FOLIO_PERSONA")
	private BigDecimal numFolioPersona;

	public Gusreexptemp() {
	}

	public BigDecimal getIdenrol() {
		return this.idenrol;
	}

	public void setIdenrol(BigDecimal idenrol) {
		this.idenrol = idenrol;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public BigDecimal getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(BigDecimal numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

}