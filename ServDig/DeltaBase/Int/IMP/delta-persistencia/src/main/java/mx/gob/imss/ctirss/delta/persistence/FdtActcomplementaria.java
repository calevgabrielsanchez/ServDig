package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the FDT_ACTCOMPLEMENTARIAS database table.
 * 
 */
@Entity
@Table(name="FDT_ACTCOMPLEMENTARIAS")
public class FdtActcomplementaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ACTCOMPLE", nullable=false, precision=22)
	private long cveActcomple;

	@Column(name="IND_NODISTRIBUYE", precision=22)
	private BigDecimal indNodistribuye;

	@Column(name="IND_PRESTASERV", precision=22)
	private BigDecimal indPrestaserv;

	@Column(name="IND_RPCLASE", precision=22)
	private BigDecimal indRpclase;

	@Column(name="IND_TRANSPORTEAJENO", precision=22)
	private BigDecimal indTransporteajeno;

	@Column(name="IND_TRANSPORTEPROPIO", precision=22)
	private BigDecimal indTransportepropio;

	@Column(name="NU_CENTROSTRAJO", precision=22)
	private BigDecimal nuCentrostrajo;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtActcomplementaria() {
    }

	public long getCveActcomple() {
		return this.cveActcomple;
	}

	public void setCveActcomple(long cveActcomple) {
		this.cveActcomple = cveActcomple;
	}

	public BigDecimal getIndNodistribuye() {
		return this.indNodistribuye;
	}

	public void setIndNodistribuye(BigDecimal indNodistribuye) {
		this.indNodistribuye = indNodistribuye;
	}

	public BigDecimal getIndPrestaserv() {
		return this.indPrestaserv;
	}

	public void setIndPrestaserv(BigDecimal indPrestaserv) {
		this.indPrestaserv = indPrestaserv;
	}

	public BigDecimal getIndRpclase() {
		return this.indRpclase;
	}

	public void setIndRpclase(BigDecimal indRpclase) {
		this.indRpclase = indRpclase;
	}

	public BigDecimal getIndTransporteajeno() {
		return this.indTransporteajeno;
	}

	public void setIndTransporteajeno(BigDecimal indTransporteajeno) {
		this.indTransporteajeno = indTransporteajeno;
	}

	public BigDecimal getIndTransportepropio() {
		return this.indTransportepropio;
	}

	public void setIndTransportepropio(BigDecimal indTransportepropio) {
		this.indTransportepropio = indTransportepropio;
	}

	public BigDecimal getNuCentrostrajo() {
		return this.nuCentrostrajo;
	}

	public void setNuCentrostrajo(BigDecimal nuCentrostrajo) {
		this.nuCentrostrajo = nuCentrostrajo;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}