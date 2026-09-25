package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FDT_A4_GASTOB_CUENTA database table.
 * 
 */
@Entity
@Table(name="FDT_A4_GASTOB_CUENTA")
public class FdtA4GastobCuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA4GastobCuentaPK id;

	@Column(name="IM_REMUNERACIONES", length=18)
	private String imRemuneraciones;

	@Column(name="IN_TP_CUENTA", length=1)
	private String inTpCuenta;

	@Column(name="TX_GASTOCUENTA", length=30)
	private String txGastocuenta;

	@Column(name="TX_REMUNERACIONES", length=18)
	private String txRemuneraciones;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA4GastobCuenta() {
    }

	public FdtA4GastobCuentaPK getId() {
		return this.id;
	}

	public void setId(FdtA4GastobCuentaPK id) {
		this.id = id;
	}
	
	public String getImRemuneraciones() {
		return this.imRemuneraciones;
	}

	public void setImRemuneraciones(String imRemuneraciones) {
		this.imRemuneraciones = imRemuneraciones;
	}

	public String getInTpCuenta() {
		return this.inTpCuenta;
	}

	public void setInTpCuenta(String inTpCuenta) {
		this.inTpCuenta = inTpCuenta;
	}

	public String getTxGastocuenta() {
		return this.txGastocuenta;
	}

	public void setTxGastocuenta(String txGastocuenta) {
		this.txGastocuenta = txGastocuenta;
	}

	public String getTxRemuneraciones() {
		return this.txRemuneraciones;
	}

	public void setTxRemuneraciones(String txRemuneraciones) {
		this.txRemuneraciones = txRemuneraciones;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}