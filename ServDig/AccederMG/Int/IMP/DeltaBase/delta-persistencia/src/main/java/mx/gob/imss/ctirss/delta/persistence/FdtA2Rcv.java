package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A2_RCV database table.
 * 
 */
@Entity
@Table(name="FDT_A2_RCV")
public class FdtA2Rcv implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA2RcvPK id;

	@Column(name="IM_ACT", precision=12, scale=2)
	private BigDecimal imAct;

	@Column(name="IM_CESANTIA_VEJEZ", nullable=false, precision=12, scale=2)
	private BigDecimal imCesantiaVejez;

	@Column(name="IM_REC", precision=12, scale=2)
	private BigDecimal imRec;

	@Column(name="IM_RETIRO", nullable=false, precision=12, scale=2)
	private BigDecimal imRetiro;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA2Rcv() {
    }

	public FdtA2RcvPK getId() {
		return this.id;
	}

	public void setId(FdtA2RcvPK id) {
		this.id = id;
	}
	
	public BigDecimal getImAct() {
		return this.imAct;
	}

	public void setImAct(BigDecimal imAct) {
		this.imAct = imAct;
	}

	public BigDecimal getImCesantiaVejez() {
		return this.imCesantiaVejez;
	}

	public void setImCesantiaVejez(BigDecimal imCesantiaVejez) {
		this.imCesantiaVejez = imCesantiaVejez;
	}

	public BigDecimal getImRec() {
		return this.imRec;
	}

	public void setImRec(BigDecimal imRec) {
		this.imRec = imRec;
	}

	public BigDecimal getImRetiro() {
		return this.imRetiro;
	}

	public void setImRetiro(BigDecimal imRetiro) {
		this.imRetiro = imRetiro;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}