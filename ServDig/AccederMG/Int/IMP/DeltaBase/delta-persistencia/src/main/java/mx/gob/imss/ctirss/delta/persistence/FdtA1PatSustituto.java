package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the FDT_A1_PAT_SUSTITUTO database table.
 * 
 */
@Entity
@Table(name="FDT_A1_PAT_SUSTITUTO")
public class FdtA1PatSustituto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA1PatSustitutoPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_SUSTITUCION_REG_ANT")
	private Date fhSustitucionRegAnt;

	@Column(name="TX_REG_ACTUAL", nullable=false, length=10)
	private String txRegActual;

	@Column(name="TX_REG_ANTERIOR", nullable=false, length=10)
	private String txRegAnterior;

	//bi-directional many-to-one association to FdtPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
	private FdtPeriodo fdtPeriodo;

    public FdtA1PatSustituto() {
    }

	public FdtA1PatSustitutoPK getId() {
		return this.id;
	}

	public void setId(FdtA1PatSustitutoPK id) {
		this.id = id;
	}
	
	public Date getFhSustitucionRegAnt() {
		return this.fhSustitucionRegAnt;
	}

	public void setFhSustitucionRegAnt(Date fhSustitucionRegAnt) {
		this.fhSustitucionRegAnt = fhSustitucionRegAnt;
	}

	public String getTxRegActual() {
		return this.txRegActual;
	}

	public void setTxRegActual(String txRegActual) {
		this.txRegActual = txRegActual;
	}

	public String getTxRegAnterior() {
		return this.txRegAnterior;
	}

	public void setTxRegAnterior(String txRegAnterior) {
		this.txRegAnterior = txRegAnterior;
	}

	public FdtPeriodo getFdtPeriodo() {
		return this.fdtPeriodo;
	}

	public void setFdtPeriodo(FdtPeriodo fdtPeriodo) {
		this.fdtPeriodo = fdtPeriodo;
	}
	
}