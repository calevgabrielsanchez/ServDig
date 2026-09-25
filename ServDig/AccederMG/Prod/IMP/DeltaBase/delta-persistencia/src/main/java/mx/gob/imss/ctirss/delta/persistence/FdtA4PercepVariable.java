package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A4_PERCEP_VARIABLES database table.
 * 
 */
@Entity
@Table(name="FDT_A4_PERCEP_VARIABLES")
public class FdtA4PercepVariable implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA4PercepVariablePK id;

	@Column(name="IM_PERCEPVAR", precision=12, scale=2)
	private BigDecimal imPercepvar;

	@Column(name="IN_TP_PERCEPCION", nullable=false, length=1)
	private String inTpPercepcion;

	@Column(name="TX_PERCEPCION", nullable=false, length=200)
	private String txPercepcion;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA4PercepVariable() {
    }

	public FdtA4PercepVariablePK getId() {
		return this.id;
	}

	public void setId(FdtA4PercepVariablePK id) {
		this.id = id;
	}
	
	public BigDecimal getImPercepvar() {
		return this.imPercepvar;
	}

	public void setImPercepvar(BigDecimal imPercepvar) {
		this.imPercepvar = imPercepvar;
	}

	public String getInTpPercepcion() {
		return this.inTpPercepcion;
	}

	public void setInTpPercepcion(String inTpPercepcion) {
		this.inTpPercepcion = inTpPercepcion;
	}

	public String getTxPercepcion() {
		return this.txPercepcion;
	}

	public void setTxPercepcion(String txPercepcion) {
		this.txPercepcion = txPercepcion;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}