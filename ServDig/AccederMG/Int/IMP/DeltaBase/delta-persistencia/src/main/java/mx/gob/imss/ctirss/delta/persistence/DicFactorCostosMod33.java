package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIC_FACTOR_COSTOS_MOD33 database table.
 * 
 */
@Entity
@Table(name="DIC_FACTOR_COSTOS_MOD33")
@NamedQuery(name="DicFactorCostosMod33.findAll", query="SELECT d FROM DicFactorCostosMod33 d")
public class DicFactorCostosMod33 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_COSTOS")
	private long cveIdCostos;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IMP_COSTO_RAMA")
	private BigDecimal impCostoRama;

	@Column(name="IMP_COSTO_TOTAL")
	private BigDecimal impCostoTotal;

	@Column(name="NUM_RANGO_EDAD_FIN")
	private BigDecimal numRangoEdadFin;

	@Column(name="NUM_RANGO_EDAD_INI")
	private BigDecimal numRangoEdadIni;

	//bi-directional many-to-one association to DicFactorModalidadRama
	@ManyToOne
	@JoinColumn(name="CVE_ID_FACTOR_MODALIDAD_RAMA")
	private DicFactorModalidadRama dicFactorModalidadRama;

	public DicFactorCostosMod33() {
	}

	public long getCveIdCostos() {
		return this.cveIdCostos;
	}

	public void setCveIdCostos(long cveIdCostos) {
		this.cveIdCostos = cveIdCostos;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public BigDecimal getImpCostoRama() {
		return this.impCostoRama;
	}

	public void setImpCostoRama(BigDecimal impCostoRama) {
		this.impCostoRama = impCostoRama;
	}

	public BigDecimal getImpCostoTotal() {
		return this.impCostoTotal;
	}

	public void setImpCostoTotal(BigDecimal impCostoTotal) {
		this.impCostoTotal = impCostoTotal;
	}

	public BigDecimal getNumRangoEdadFin() {
		return this.numRangoEdadFin;
	}

	public void setNumRangoEdadFin(BigDecimal numRangoEdadFin) {
		this.numRangoEdadFin = numRangoEdadFin;
	}

	public BigDecimal getNumRangoEdadIni() {
		return this.numRangoEdadIni;
	}

	public void setNumRangoEdadIni(BigDecimal numRangoEdadIni) {
		this.numRangoEdadIni = numRangoEdadIni;
	}

	public DicFactorModalidadRama getDicFactorModalidadRama() {
		return this.dicFactorModalidadRama;
	}

	public void setDicFactorModalidadRama(DicFactorModalidadRama dicFactorModalidadRama) {
		this.dicFactorModalidadRama = dicFactorModalidadRama;
	}

}