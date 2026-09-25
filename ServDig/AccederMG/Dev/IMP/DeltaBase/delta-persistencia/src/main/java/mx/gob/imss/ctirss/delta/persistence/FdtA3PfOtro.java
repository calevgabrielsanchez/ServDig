package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A3_PF_OTROS database table.
 * 
 */
@Entity
@Table(name="FDT_A3_PF_OTROS")
public class FdtA3PfOtro implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3PfOtroPK id;

	@Column(name="IM_A3_PF_OTROS", precision=12, scale=2)
	private BigDecimal imA3PfOtros;

	@Column(name="TX_A3_PF_OTROS", length=50)
	private String txA3PfOtros;

	//bi-directional many-to-one association to FdtPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
	private FdtPeriodo fdtPeriodo;

    public FdtA3PfOtro() {
    }

	public FdtA3PfOtroPK getId() {
		return this.id;
	}

	public void setId(FdtA3PfOtroPK id) {
		this.id = id;
	}
	
	public BigDecimal getImA3PfOtros() {
		return this.imA3PfOtros;
	}

	public void setImA3PfOtros(BigDecimal imA3PfOtros) {
		this.imA3PfOtros = imA3PfOtros;
	}

	public String getTxA3PfOtros() {
		return this.txA3PfOtros;
	}

	public void setTxA3PfOtros(String txA3PfOtros) {
		this.txA3PfOtros = txA3PfOtros;
	}

	public FdtPeriodo getFdtPeriodo() {
		return this.fdtPeriodo;
	}

	public void setFdtPeriodo(FdtPeriodo fdtPeriodo) {
		this.fdtPeriodo = fdtPeriodo;
	}
	
}