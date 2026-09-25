package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A3_GRUPO_FACTOR_OTRO database table.
 * 
 */
@Entity
@Table(name="FDT_A3_GRUPO_FACTOR_OTRO")
public class FdtA3GrupoFactorOtro implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3GrupoFactorOtroPK id;

	@Column(name="IM_CANTIDAD", nullable=false, precision=12, scale=4)
	private BigDecimal imCantidad;

	@Column(name="TX_CONCEPTO", nullable=false, length=50)
	private String txConcepto;

	//bi-directional many-to-one association to FdtA3GrupoFactore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CV_GRUPO", referencedColumnName="CV_GRUPO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NU_FACTORES", referencedColumnName="NU_FACTORES", nullable=false, insertable=false, updatable=false)
		})
	private FdtA3GrupoFactore fdtA3GrupoFactore;

    public FdtA3GrupoFactorOtro() {
    }

	public FdtA3GrupoFactorOtroPK getId() {
		return this.id;
	}

	public void setId(FdtA3GrupoFactorOtroPK id) {
		this.id = id;
	}
	
	public BigDecimal getImCantidad() {
		return this.imCantidad;
	}

	public void setImCantidad(BigDecimal imCantidad) {
		this.imCantidad = imCantidad;
	}

	public String getTxConcepto() {
		return this.txConcepto;
	}

	public void setTxConcepto(String txConcepto) {
		this.txConcepto = txConcepto;
	}

	public FdtA3GrupoFactore getFdtA3GrupoFactore() {
		return this.fdtA3GrupoFactore;
	}

	public void setFdtA3GrupoFactore(FdtA3GrupoFactore fdtA3GrupoFactore) {
		this.fdtA3GrupoFactore = fdtA3GrupoFactore;
	}
	
}