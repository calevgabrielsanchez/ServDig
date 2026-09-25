package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A3_ACT_PER_NO_SUJETO database table.
 * 
 */
@Entity
@Table(name="FDT_A3_ACT_PER_NO_SUJETO")
public class FdtA3ActPerNoSujeto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3ActPerNoSujetoPK id;

	@Column(name="IM_ACTIVIDAD", nullable=false, precision=12, scale=2)
	private BigDecimal imActividad;

	@Column(name="TX_ACTIVIDAD", nullable=false, length=50)
	private String txActividad;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA3ActPerNoSujeto() {
    }

	public FdtA3ActPerNoSujetoPK getId() {
		return this.id;
	}

	public void setId(FdtA3ActPerNoSujetoPK id) {
		this.id = id;
	}
	
	public BigDecimal getImActividad() {
		return this.imActividad;
	}

	public void setImActividad(BigDecimal imActividad) {
		this.imActividad = imActividad;
	}

	public String getTxActividad() {
		return this.txActividad;
	}

	public void setTxActividad(String txActividad) {
		this.txActividad = txActividad;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}