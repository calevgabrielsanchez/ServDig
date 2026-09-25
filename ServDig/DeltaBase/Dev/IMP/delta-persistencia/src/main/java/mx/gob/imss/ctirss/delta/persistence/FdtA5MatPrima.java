package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A5_MAT_PRIMA database table.
 * 
 */
@Entity
@Table(name="FDT_A5_MAT_PRIMA")
public class FdtA5MatPrima implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA5MatPrimaPK id;

	@Column(name="ID_MATERIAOPROD", precision=22)
	private BigDecimal idMateriaoprod;

    @Lob()
	@Column(name="TX_MAT_PRIMA", nullable=false)
	private byte[] txMatPrima;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA5MatPrima() {
    }

	public FdtA5MatPrimaPK getId() {
		return this.id;
	}

	public void setId(FdtA5MatPrimaPK id) {
		this.id = id;
	}
	
	public BigDecimal getIdMateriaoprod() {
		return this.idMateriaoprod;
	}

	public void setIdMateriaoprod(BigDecimal idMateriaoprod) {
		this.idMateriaoprod = idMateriaoprod;
	}

	public byte[] getTxMatPrima() {
		return this.txMatPrima;
	}

	public void setTxMatPrima(byte[] txMatPrima) {
		this.txMatPrima = txMatPrima != null ? txMatPrima.clone() : null;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}