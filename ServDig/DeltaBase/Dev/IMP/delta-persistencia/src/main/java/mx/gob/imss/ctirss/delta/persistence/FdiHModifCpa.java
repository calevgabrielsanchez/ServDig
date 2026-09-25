package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the FDI_H_MODIF_CPA database table.
 * 
 */
@Entity
@Table(name="FDI_H_MODIF_CPA")
public class FdiHModifCpa implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdiHModifCpaPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_PRESENTACAMBIO")
	private Date fecPresentacambio;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_MODIF", nullable=false)
	private Date fhModif;

	@Column(name="IN_STATUS_REG", length=1)
	private String inStatusReg;

	@Column(name="TX_DATO_ANTERIOR", length=1600)
	private String txDatoAnterior;

	//bi-directional many-to-one association to FdiCpa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CV_CURP", nullable=false, insertable=false, updatable=false)
	private FdiCpa fdiCpa;

	//bi-directional many-to-one association to FdcTpoproceso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TPOPROCESO")
	private FdcTpoproceso fdcTpoproceso;

    public FdiHModifCpa() {
    }

	public FdiHModifCpaPK getId() {
		return this.id;
	}

	public void setId(FdiHModifCpaPK id) {
		this.id = id;
	}
	
	public Date getFecPresentacambio() {
		return this.fecPresentacambio;
	}

	public void setFecPresentacambio(Date fecPresentacambio) {
		this.fecPresentacambio = fecPresentacambio;
	}

	public Date getFhModif() {
		return this.fhModif;
	}

	public void setFhModif(Date fhModif) {
		this.fhModif = fhModif;
	}

	public String getInStatusReg() {
		return this.inStatusReg;
	}

	public void setInStatusReg(String inStatusReg) {
		this.inStatusReg = inStatusReg;
	}

	public String getTxDatoAnterior() {
		return this.txDatoAnterior;
	}

	public void setTxDatoAnterior(String txDatoAnterior) {
		this.txDatoAnterior = txDatoAnterior;
	}

	public FdiCpa getFdiCpa() {
		return this.fdiCpa;
	}

	public void setFdiCpa(FdiCpa fdiCpa) {
		this.fdiCpa = fdiCpa;
	}
	
	public FdcTpoproceso getFdcTpoproceso() {
		return this.fdcTpoproceso;
	}

	public void setFdcTpoproceso(FdcTpoproceso fdcTpoproceso) {
		this.fdcTpoproceso = fdcTpoproceso;
	}
	
}