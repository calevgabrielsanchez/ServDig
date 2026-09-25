package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_CONSTRUCCION_SUBCONTRATIST database table.
 * 
 */
@Entity
@Table(name="FDT_CONSTRUCCION_SUBCONTRATIST")
public class FdtConstruccionSubcontratist implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtConstruccionSubcontratistPK id;

	@Column(name="IMPORTE_SUBCONTRATADO", precision=14, scale=2)
	private BigDecimal importeSubcontratado;

	@Column(name="RAZON_SOCIAL_SUBCONTRATISTA", nullable=false, length=50)
	private String razonSocialSubcontratista;

	//bi-directional many-to-one association to FdtFasesSubcontratista
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FASE_SUBCONTRATADA", nullable=false, insertable=false, updatable=false)
	private FdtFasesSubcontratista fdtFasesSubcontratista;

	//bi-directional many-to-one association to FdtConstruccionObra
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NU_OBRA", referencedColumnName="NU_OBRA", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtConstruccionObra fdtConstruccionObra;

    public FdtConstruccionSubcontratist() {
    }

	public FdtConstruccionSubcontratistPK getId() {
		return this.id;
	}

	public void setId(FdtConstruccionSubcontratistPK id) {
		this.id = id;
	}
	
	public BigDecimal getImporteSubcontratado() {
		return this.importeSubcontratado;
	}

	public void setImporteSubcontratado(BigDecimal importeSubcontratado) {
		this.importeSubcontratado = importeSubcontratado;
	}

	public String getRazonSocialSubcontratista() {
		return this.razonSocialSubcontratista;
	}

	public void setRazonSocialSubcontratista(String razonSocialSubcontratista) {
		this.razonSocialSubcontratista = razonSocialSubcontratista;
	}

	public FdtFasesSubcontratista getFdtFasesSubcontratista() {
		return this.fdtFasesSubcontratista;
	}

	public void setFdtFasesSubcontratista(FdtFasesSubcontratista fdtFasesSubcontratista) {
		this.fdtFasesSubcontratista = fdtFasesSubcontratista;
	}
	
	public FdtConstruccionObra getFdtConstruccionObra() {
		return this.fdtConstruccionObra;
	}

	public void setFdtConstruccionObra(FdtConstruccionObra fdtConstruccionObra) {
		this.fdtConstruccionObra = fdtConstruccionObra;
	}
	
}