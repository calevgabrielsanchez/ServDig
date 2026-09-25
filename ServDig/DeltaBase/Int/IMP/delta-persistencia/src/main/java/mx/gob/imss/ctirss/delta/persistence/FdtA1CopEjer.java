package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A1_COP_EJER database table.
 * 
 */
@Entity
@Table(name="FDT_A1_COP_EJER")
public class FdtA1CopEjer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA1CopEjerPK id;

	@Column(name="NU_CUOTA_EXCED_3_SMGDF", precision=10, scale=2)
	private BigDecimal nuCuotaExced3Smgdf;

	@Column(name="NU_CUOTA_FIJA", precision=10, scale=2)
	private BigDecimal nuCuotaFija;

	@Column(name="NU_CUOTA_GTOS_MED_PEN", precision=10, scale=2)
	private BigDecimal nuCuotaGtosMedPen;

	@Column(name="NU_CUOTA_GUARD_PREST", precision=10, scale=2)
	private BigDecimal nuCuotaGuardPrest;

	@Column(name="NU_CUOTA_INVALIDEZ_VIDA", precision=10, scale=2)
	private BigDecimal nuCuotaInvalidezVida;

	@Column(name="NU_CUOTA_PREST_DINERO", precision=10, scale=2)
	private BigDecimal nuCuotaPrestDinero;

	@Column(name="NU_CUOTA_RCV_CESANTIA", precision=10, scale=2)
	private BigDecimal nuCuotaRcvCesantia;

	@Column(name="NU_CUOTA_RCV_RETIRO", precision=10, scale=2)
	private BigDecimal nuCuotaRcvRetiro;

	@Column(name="NU_CUOTA_RIESGO_TRABAJO", precision=10, scale=2)
	private BigDecimal nuCuotaRiesgoTrabajo;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

	//bi-directional many-to-one association to FdcPorcentaje
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_PORCENTAJE")
	private FdcPorcentaje fdcPorcentaje;

    public FdtA1CopEjer() {
    }

	public FdtA1CopEjerPK getId() {
		return this.id;
	}

	public void setId(FdtA1CopEjerPK id) {
		this.id = id;
	}
	
	public BigDecimal getNuCuotaExced3Smgdf() {
		return this.nuCuotaExced3Smgdf;
	}

	public void setNuCuotaExced3Smgdf(BigDecimal nuCuotaExced3Smgdf) {
		this.nuCuotaExced3Smgdf = nuCuotaExced3Smgdf;
	}

	public BigDecimal getNuCuotaFija() {
		return this.nuCuotaFija;
	}

	public void setNuCuotaFija(BigDecimal nuCuotaFija) {
		this.nuCuotaFija = nuCuotaFija;
	}

	public BigDecimal getNuCuotaGtosMedPen() {
		return this.nuCuotaGtosMedPen;
	}

	public void setNuCuotaGtosMedPen(BigDecimal nuCuotaGtosMedPen) {
		this.nuCuotaGtosMedPen = nuCuotaGtosMedPen;
	}

	public BigDecimal getNuCuotaGuardPrest() {
		return this.nuCuotaGuardPrest;
	}

	public void setNuCuotaGuardPrest(BigDecimal nuCuotaGuardPrest) {
		this.nuCuotaGuardPrest = nuCuotaGuardPrest;
	}

	public BigDecimal getNuCuotaInvalidezVida() {
		return this.nuCuotaInvalidezVida;
	}

	public void setNuCuotaInvalidezVida(BigDecimal nuCuotaInvalidezVida) {
		this.nuCuotaInvalidezVida = nuCuotaInvalidezVida;
	}

	public BigDecimal getNuCuotaPrestDinero() {
		return this.nuCuotaPrestDinero;
	}

	public void setNuCuotaPrestDinero(BigDecimal nuCuotaPrestDinero) {
		this.nuCuotaPrestDinero = nuCuotaPrestDinero;
	}

	public BigDecimal getNuCuotaRcvCesantia() {
		return this.nuCuotaRcvCesantia;
	}

	public void setNuCuotaRcvCesantia(BigDecimal nuCuotaRcvCesantia) {
		this.nuCuotaRcvCesantia = nuCuotaRcvCesantia;
	}

	public BigDecimal getNuCuotaRcvRetiro() {
		return this.nuCuotaRcvRetiro;
	}

	public void setNuCuotaRcvRetiro(BigDecimal nuCuotaRcvRetiro) {
		this.nuCuotaRcvRetiro = nuCuotaRcvRetiro;
	}

	public BigDecimal getNuCuotaRiesgoTrabajo() {
		return this.nuCuotaRiesgoTrabajo;
	}

	public void setNuCuotaRiesgoTrabajo(BigDecimal nuCuotaRiesgoTrabajo) {
		this.nuCuotaRiesgoTrabajo = nuCuotaRiesgoTrabajo;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
	public FdcPorcentaje getFdcPorcentaje() {
		return this.fdcPorcentaje;
	}

	public void setFdcPorcentaje(FdcPorcentaje fdcPorcentaje) {
		this.fdcPorcentaje = fdcPorcentaje;
	}
	
}