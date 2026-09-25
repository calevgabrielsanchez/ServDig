package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIC_FACTOR_MODALIDAD_RAMA database table.
 * 
 */
@Entity
@Table(name="DIC_FACTOR_MODALIDAD_RAMA")
@NamedQuery(name="DicFactorModalidadRama.findAll", query="SELECT d FROM DicFactorModalidadRama d")
public class DicFactorModalidadRama implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_FACTOR_MODALIDAD_RAMA")
	private long cveIdFactorModalidadRama;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_FACTOR")
	private BigDecimal numFactor;

	//bi-directional many-to-one association to DicRama
	@ManyToOne
	@JoinColumn(name="CVE_ID_RAMA")
	private DicRama dicRama;

	//bi-directional many-to-one association to DicTipoAportacion
	@ManyToOne
	@JoinColumn(name="CVE_ID_TIPO_APORTACION")
	private DicTipoAportacion dicTipoAportacion;

	//bi-directional many-to-one association to DicModalidad
	@ManyToOne
	@JoinColumn(name="CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;

	@OneToMany(mappedBy="dicFactorModalidadRama")
	private List<DicFactorCostosMod33> dicFactorCostosMod33s;

	public DicFactorModalidadRama() {
	}

	public long getCveIdFactorModalidadRama() {
		return this.cveIdFactorModalidadRama;
	}

	public void setCveIdFactorModalidadRama(long cveIdFactorModalidadRama) {
		this.cveIdFactorModalidadRama = cveIdFactorModalidadRama;
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

	public BigDecimal getNumFactor() {
		return this.numFactor;
	}

	public void setNumFactor(BigDecimal numFactor) {
		this.numFactor = numFactor;
	}

	public DicRama getDicRama() {
		return this.dicRama;
	}

	public void setDicRama(DicRama dicRama) {
		this.dicRama = dicRama;
	}

	public DicTipoAportacion getDicTipoAportacion() {
		return this.dicTipoAportacion;
	}

	public void setDicTipoAportacion(DicTipoAportacion dicTipoAportacion) {
		this.dicTipoAportacion = dicTipoAportacion;
	}

	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}

	public List<DicFactorCostosMod33> getDicFactorCostosMod33s() {
		return this.dicFactorCostosMod33s;
	}

	public void setDicFactorCostosMod33s(List<DicFactorCostosMod33> dicFactorCostosMod33s) {
		this.dicFactorCostosMod33s = dicFactorCostosMod33s;
	}
}