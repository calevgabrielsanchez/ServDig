package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_CEDRAZ_PERCEPCIONES database table.
 * 
 */
@Entity
@Table(name="FDT_CEDRAZ_PERCEPCIONES")
public class FdtCedrazPercepcione implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtCedrazPercepcionePK id;

	@Column(name="IM_PERCEPCION", precision=12, scale=2)
	private BigDecimal imPercepcion;

	@Column(name="TP_PERSEPCION", precision=22)
	private BigDecimal tpPersepcion;

	@Column(name="TX_PERSEPCION", length=100)
	private String txPersepcion;

	//bi-directional many-to-one association to FdtCedrazRegpat
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_CEDULA", referencedColumnName="ID_CEDULA", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtCedrazRegpat fdtCedrazRegpat;

    public FdtCedrazPercepcione() {
    }

	public FdtCedrazPercepcionePK getId() {
		return this.id;
	}

	public void setId(FdtCedrazPercepcionePK id) {
		this.id = id;
	}
	
	public BigDecimal getImPercepcion() {
		return this.imPercepcion;
	}

	public void setImPercepcion(BigDecimal imPercepcion) {
		this.imPercepcion = imPercepcion;
	}

	public BigDecimal getTpPersepcion() {
		return this.tpPersepcion;
	}

	public void setTpPersepcion(BigDecimal tpPersepcion) {
		this.tpPersepcion = tpPersepcion;
	}

	public String getTxPersepcion() {
		return this.txPersepcion;
	}

	public void setTxPersepcion(String txPersepcion) {
		this.txPersepcion = txPersepcion;
	}

	public FdtCedrazRegpat getFdtCedrazRegpat() {
		return this.fdtCedrazRegpat;
	}

	public void setFdtCedrazRegpat(FdtCedrazRegpat fdtCedrazRegpat) {
		this.fdtCedrazRegpat = fdtCedrazRegpat;
	}
	
}