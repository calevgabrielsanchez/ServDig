package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FDT_A3_MUESTRA_DATO_CONCEPTO database table.
 * 
 */
@Entity
@Table(name="FDT_A3_MUESTRA_DATO_CONCEPTO")
public class FdtA3MuestraDatoConcepto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3MuestraDatoConceptoPK id;

	//bi-directional many-to-one association to FdtA3MuestraOtroConcepto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_CONCEPTO", referencedColumnName="CVE_CONCEPTO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="IN_TP_CONCEPTO", referencedColumnName="IN_TP_CONCEPTO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NU_MUESTRA", referencedColumnName="NU_MUESTRA", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtA3MuestraOtroConcepto fdtA3MuestraOtroConcepto;

    public FdtA3MuestraDatoConcepto() {
    }

	public FdtA3MuestraDatoConceptoPK getId() {
		return this.id;
	}

	public void setId(FdtA3MuestraDatoConceptoPK id) {
		this.id = id;
	}
	
	public FdtA3MuestraOtroConcepto getFdtA3MuestraOtroConcepto() {
		return this.fdtA3MuestraOtroConcepto;
	}

	public void setFdtA3MuestraOtroConcepto(FdtA3MuestraOtroConcepto fdtA3MuestraOtroConcepto) {
		this.fdtA3MuestraOtroConcepto = fdtA3MuestraOtroConcepto;
	}
	
}