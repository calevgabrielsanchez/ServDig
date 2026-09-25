package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the FDT_A4_GASTO_BALANCE database table.
 * 
 */
@Entity
@Table(name="FDT_A4_GASTO_BALANCE")
public class FdtA4GastoBalance implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA4GastoBalancePK id;

	@Column(name="IN_TP_CUENTA", nullable=false, length=1)
	private String inTpCuenta;

	@Column(name="TX_GASTOS", nullable=false, length=50)
	private String txGastos;

	//bi-directional many-to-one association to FdtA4Cuenta
	@OneToMany(mappedBy="fdtA4GastoBalance")
	private List<FdtA4Cuenta> fdtA4Cuentas;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA4GastoBalance() {
    }

	public FdtA4GastoBalancePK getId() {
		return this.id;
	}

	public void setId(FdtA4GastoBalancePK id) {
		this.id = id;
	}
	
	public String getInTpCuenta() {
		return this.inTpCuenta;
	}

	public void setInTpCuenta(String inTpCuenta) {
		this.inTpCuenta = inTpCuenta;
	}

	public String getTxGastos() {
		return this.txGastos;
	}

	public void setTxGastos(String txGastos) {
		this.txGastos = txGastos;
	}

	public List<FdtA4Cuenta> getFdtA4Cuentas() {
		return this.fdtA4Cuentas;
	}

	public void setFdtA4Cuentas(List<FdtA4Cuenta> fdtA4Cuentas) {
		this.fdtA4Cuentas = fdtA4Cuentas;
	}
	
	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}