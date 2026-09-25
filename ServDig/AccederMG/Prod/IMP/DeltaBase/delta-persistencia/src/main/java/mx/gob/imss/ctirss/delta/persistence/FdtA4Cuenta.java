package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A4_CUENTA database table.
 * 
 */
@Entity
@Table(name="FDT_A4_CUENTA")
public class FdtA4Cuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA4CuentaPK id;

	@Column(name="IM_REMUNERACIONES", nullable=false, precision=12, scale=2)
	private BigDecimal imRemuneraciones;

	@Column(name="NU_GASTOCUENTA_PADRE", precision=22)
	private BigDecimal nuGastocuentaPadre;

	@Column(name="TX_CUENTA", length=30)
	private String txCuenta;

	@Column(name="TX_REMUNERACIONES", nullable=false, length=50)
	private String txRemuneraciones;

	//bi-directional many-to-one association to FdtA4GastoBalance
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NU_GASTO", referencedColumnName="NU_GASTO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtA4GastoBalance fdtA4GastoBalance;

    public FdtA4Cuenta() {
    }

	public FdtA4CuentaPK getId() {
		return this.id;
	}

	public void setId(FdtA4CuentaPK id) {
		this.id = id;
	}
	
	public BigDecimal getImRemuneraciones() {
		return this.imRemuneraciones;
	}

	public void setImRemuneraciones(BigDecimal imRemuneraciones) {
		this.imRemuneraciones = imRemuneraciones;
	}

	public BigDecimal getNuGastocuentaPadre() {
		return this.nuGastocuentaPadre;
	}

	public void setNuGastocuentaPadre(BigDecimal nuGastocuentaPadre) {
		this.nuGastocuentaPadre = nuGastocuentaPadre;
	}

	public String getTxCuenta() {
		return this.txCuenta;
	}

	public void setTxCuenta(String txCuenta) {
		this.txCuenta = txCuenta;
	}

	public String getTxRemuneraciones() {
		return this.txRemuneraciones;
	}

	public void setTxRemuneraciones(String txRemuneraciones) {
		this.txRemuneraciones = txRemuneraciones;
	}

	public FdtA4GastoBalance getFdtA4GastoBalance() {
		return this.fdtA4GastoBalance;
	}

	public void setFdtA4GastoBalance(FdtA4GastoBalance fdtA4GastoBalance) {
		this.fdtA4GastoBalance = fdtA4GastoBalance;
	}
	
}