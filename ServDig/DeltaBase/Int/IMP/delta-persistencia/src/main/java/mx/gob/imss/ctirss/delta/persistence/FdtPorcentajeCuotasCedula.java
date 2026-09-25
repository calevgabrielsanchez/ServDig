package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDT_PORCENTAJE_CUOTAS_CEDULA database table.
 * 
 */
@Entity
@Table(name="FDT_PORCENTAJE_CUOTAS_CEDULA")
public class FdtPorcentajeCuotasCedula implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="NU_CUOTA", nullable=false, precision=22)
	private long nuCuota;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_INI", nullable=false)
	private Date fhIni;

	@Column(name="PORC_EXCED_3_SMGDF", nullable=false, precision=5, scale=3)
	private BigDecimal porcExced3Smgdf;

	@Column(name="PORC_FIJA", nullable=false, precision=5, scale=3)
	private BigDecimal porcFija;

	@Column(name="PORC_GTOS_MED_PEN", nullable=false, precision=5, scale=3)
	private BigDecimal porcGtosMedPen;

	@Column(name="PORC_GUARDERIAS", nullable=false, precision=5, scale=3)
	private BigDecimal porcGuarderias;

	@Column(name="PORC_INVALIDEZ_VIDA", nullable=false, precision=5, scale=3)
	private BigDecimal porcInvalidezVida;

	@Column(name="PORC_PREST_DINERO", nullable=false, precision=5, scale=3)
	private BigDecimal porcPrestDinero;

	@Column(name="PORC_RCV_CESANTIA", nullable=false, precision=5, scale=3)
	private BigDecimal porcRcvCesantia;

	@Column(name="PORC_RCV_RETIRO", nullable=false, precision=5, scale=3)
	private BigDecimal porcRcvRetiro;

	@Column(name="PORC_RIESGOS_TRABAJO", nullable=false, precision=5, scale=3)
	private BigDecimal porcRiesgosTrabajo;

    public FdtPorcentajeCuotasCedula() {
    }

	public long getNuCuota() {
		return this.nuCuota;
	}

	public void setNuCuota(long nuCuota) {
		this.nuCuota = nuCuota;
	}

	public Date getFhIni() {
		return this.fhIni;
	}

	public void setFhIni(Date fhIni) {
		this.fhIni = fhIni;
	}

	public BigDecimal getPorcExced3Smgdf() {
		return this.porcExced3Smgdf;
	}

	public void setPorcExced3Smgdf(BigDecimal porcExced3Smgdf) {
		this.porcExced3Smgdf = porcExced3Smgdf;
	}

	public BigDecimal getPorcFija() {
		return this.porcFija;
	}

	public void setPorcFija(BigDecimal porcFija) {
		this.porcFija = porcFija;
	}

	public BigDecimal getPorcGtosMedPen() {
		return this.porcGtosMedPen;
	}

	public void setPorcGtosMedPen(BigDecimal porcGtosMedPen) {
		this.porcGtosMedPen = porcGtosMedPen;
	}

	public BigDecimal getPorcGuarderias() {
		return this.porcGuarderias;
	}

	public void setPorcGuarderias(BigDecimal porcGuarderias) {
		this.porcGuarderias = porcGuarderias;
	}

	public BigDecimal getPorcInvalidezVida() {
		return this.porcInvalidezVida;
	}

	public void setPorcInvalidezVida(BigDecimal porcInvalidezVida) {
		this.porcInvalidezVida = porcInvalidezVida;
	}

	public BigDecimal getPorcPrestDinero() {
		return this.porcPrestDinero;
	}

	public void setPorcPrestDinero(BigDecimal porcPrestDinero) {
		this.porcPrestDinero = porcPrestDinero;
	}

	public BigDecimal getPorcRcvCesantia() {
		return this.porcRcvCesantia;
	}

	public void setPorcRcvCesantia(BigDecimal porcRcvCesantia) {
		this.porcRcvCesantia = porcRcvCesantia;
	}

	public BigDecimal getPorcRcvRetiro() {
		return this.porcRcvRetiro;
	}

	public void setPorcRcvRetiro(BigDecimal porcRcvRetiro) {
		this.porcRcvRetiro = porcRcvRetiro;
	}

	public BigDecimal getPorcRiesgosTrabajo() {
		return this.porcRiesgosTrabajo;
	}

	public void setPorcRiesgosTrabajo(BigDecimal porcRiesgosTrabajo) {
		this.porcRiesgosTrabajo = porcRiesgosTrabajo;
	}

}