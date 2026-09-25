package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A5_PERSONAL database table.
 * 
 */
@Entity
@Table(name="FDT_A5_PERSONAL")
public class FdtA5Personal implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA5PersonalPK id;

	@Column(name="NU_TRABAJADORES", nullable=false, precision=6)
	private BigDecimal nuTrabajadores;

	@Column(name="TX_OCUPACION", nullable=false, length=50)
	private String txOcupacion;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA5Personal() {
    }

	public FdtA5PersonalPK getId() {
		return this.id;
	}

	public void setId(FdtA5PersonalPK id) {
		this.id = id;
	}
	
	public BigDecimal getNuTrabajadores() {
		return this.nuTrabajadores;
	}

	public void setNuTrabajadores(BigDecimal nuTrabajadores) {
		this.nuTrabajadores = nuTrabajadores;
	}

	public String getTxOcupacion() {
		return this.txOcupacion;
	}

	public void setTxOcupacion(String txOcupacion) {
		this.txOcupacion = txOcupacion;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}