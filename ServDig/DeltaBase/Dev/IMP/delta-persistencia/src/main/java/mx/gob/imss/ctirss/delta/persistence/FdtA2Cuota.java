package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A2_CUOTAS database table.
 * 
 */
@Entity
@Table(name="FDT_A2_CUOTAS")
public class FdtA2Cuota implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA2CuotaPK id;

	@Column(name="IM_ACT", precision=12, scale=2)
	private BigDecimal imAct;

	@Column(name="IM_EXCED", nullable=false, precision=12, scale=2)
	private BigDecimal imExced;

	@Column(name="IM_FIJA_EXCED", nullable=false, precision=12, scale=2)
	private BigDecimal imFijaExced;

	@Column(name="IM_GTOS_MED", nullable=false, precision=12, scale=2)
	private BigDecimal imGtosMed;

	@Column(name="IM_GUARDERIA", nullable=false, precision=12, scale=2)
	private BigDecimal imGuarderia;

	@Column(name="IM_INVAL_VIDA", nullable=false, precision=12, scale=2)
	private BigDecimal imInvalVida;

	@Column(name="IM_PREST", nullable=false, precision=12, scale=2)
	private BigDecimal imPrest;

	@Column(name="IM_REC", precision=12, scale=2)
	private BigDecimal imRec;

	@Column(name="IM_RIESGO_TRABAJO", nullable=false, precision=12, scale=2)
	private BigDecimal imRiesgoTrabajo;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA2Cuota() {
    }

	public FdtA2CuotaPK getId() {
		return this.id;
	}

	public void setId(FdtA2CuotaPK id) {
		this.id = id;
	}
	
	public BigDecimal getImAct() {
		return this.imAct;
	}

	public void setImAct(BigDecimal imAct) {
		this.imAct = imAct;
	}

	public BigDecimal getImExced() {
		return this.imExced;
	}

	public void setImExced(BigDecimal imExced) {
		this.imExced = imExced;
	}

	public BigDecimal getImFijaExced() {
		return this.imFijaExced;
	}

	public void setImFijaExced(BigDecimal imFijaExced) {
		this.imFijaExced = imFijaExced;
	}

	public BigDecimal getImGtosMed() {
		return this.imGtosMed;
	}

	public void setImGtosMed(BigDecimal imGtosMed) {
		this.imGtosMed = imGtosMed;
	}

	public BigDecimal getImGuarderia() {
		return this.imGuarderia;
	}

	public void setImGuarderia(BigDecimal imGuarderia) {
		this.imGuarderia = imGuarderia;
	}

	public BigDecimal getImInvalVida() {
		return this.imInvalVida;
	}

	public void setImInvalVida(BigDecimal imInvalVida) {
		this.imInvalVida = imInvalVida;
	}

	public BigDecimal getImPrest() {
		return this.imPrest;
	}

	public void setImPrest(BigDecimal imPrest) {
		this.imPrest = imPrest;
	}

	public BigDecimal getImRec() {
		return this.imRec;
	}

	public void setImRec(BigDecimal imRec) {
		this.imRec = imRec;
	}

	public BigDecimal getImRiesgoTrabajo() {
		return this.imRiesgoTrabajo;
	}

	public void setImRiesgoTrabajo(BigDecimal imRiesgoTrabajo) {
		this.imRiesgoTrabajo = imRiesgoTrabajo;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}