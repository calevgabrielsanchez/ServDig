package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDT_A3_MUESTRA_OTRO_CONCEPTO database table.
 * 
 */
@Entity
@Table(name="FDT_A3_MUESTRA_OTRO_CONCEPTO")
public class FdtA3MuestraOtroConcepto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3MuestraOtroConceptoPK id;

	@Column(name="IM_CONCEPTO", nullable=false, precision=13, scale=3)
	private BigDecimal imConcepto;

	@Column(name="TX_CONCEPTO", nullable=false, length=50)
	private String txConcepto;

	//bi-directional many-to-one association to FdtA3MuestraDatoConcepto
	@OneToMany(mappedBy="fdtA3MuestraOtroConcepto")
	private List<FdtA3MuestraDatoConcepto> fdtA3MuestraDatoConceptos;

	//bi-directional many-to-one association to FdtA3Muestra
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NU_MUESTRA", referencedColumnName="NU_MUESTRA", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtA3Muestra fdtA3Muestra;

    public FdtA3MuestraOtroConcepto() {
    }

	public FdtA3MuestraOtroConceptoPK getId() {
		return this.id;
	}

	public void setId(FdtA3MuestraOtroConceptoPK id) {
		this.id = id;
	}
	
	public BigDecimal getImConcepto() {
		return this.imConcepto;
	}

	public void setImConcepto(BigDecimal imConcepto) {
		this.imConcepto = imConcepto;
	}

	public String getTxConcepto() {
		return this.txConcepto;
	}

	public void setTxConcepto(String txConcepto) {
		this.txConcepto = txConcepto;
	}

	public List<FdtA3MuestraDatoConcepto> getFdtA3MuestraDatoConceptos() {
		return this.fdtA3MuestraDatoConceptos;
	}

	public void setFdtA3MuestraDatoConceptos(List<FdtA3MuestraDatoConcepto> fdtA3MuestraDatoConceptos) {
		this.fdtA3MuestraDatoConceptos = fdtA3MuestraDatoConceptos;
	}
	
	public FdtA3Muestra getFdtA3Muestra() {
		return this.fdtA3Muestra;
	}

	public void setFdtA3Muestra(FdtA3Muestra fdtA3Muestra) {
		this.fdtA3Muestra = fdtA3Muestra;
	}
	
}