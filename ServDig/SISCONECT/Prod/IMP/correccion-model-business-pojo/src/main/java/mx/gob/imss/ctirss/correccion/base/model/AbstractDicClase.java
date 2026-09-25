package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.DicFraccion;

/**
 * The persistent class for the DIC_CLASE database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDicClase extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIC_CLASE_CVEIDCLASE_GENERATOR", sequenceName = "SEQ_DIC_CLASE")
	@GeneratedValue(generator = "DIC_CLASE_CVEIDCLASE_GENERATOR")
	@Column(name = "CVE_ID_CLASE")
	public long cveIdClase;

	@Column(name = "DES_CLASE")
	private String desClase;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_FIN")
	private Date fecFin;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_INI")
	private Date fecIni;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name = "IND_PRIMA_MEDIA")
	private BigDecimal indPrimaMedia;

	@Column(name = "NUM_GRADO_RIESGO")
	private BigDecimal numGradoRiesgo;

	@Column(name = "NUM_PORCENTAJE")
	private BigDecimal numPorcentaje;

	// bi-directional many-to-one association to AbstractDicFraccion
	@OneToMany(mappedBy = "dicClase")
	private Set<DicFraccion> dicFraccions;

	public AbstractDicClase() {
	}

	public long getCveIdClase() {
		return this.cveIdClase;
	}

	public void setCveIdClase(long cveIdClase) {
		this.cveIdClase = cveIdClase;
	}

	public String getDesClase() {
		return this.desClase;
	}

	public void setDesClase(String desClase) {
		this.desClase = desClase;
	}

	public Date getFecFin() {
		return this.fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFecIni() {
		return this.fecIni;
	}

	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
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

	public BigDecimal getIndPrimaMedia() {
		return this.indPrimaMedia;
	}

	public void setIndPrimaMedia(BigDecimal indPrimaMedia) {
		this.indPrimaMedia = indPrimaMedia;
	}

	public BigDecimal getNumGradoRiesgo() {
		return this.numGradoRiesgo;
	}

	public void setNumGradoRiesgo(BigDecimal numGradoRiesgo) {
		this.numGradoRiesgo = numGradoRiesgo;
	}

	public BigDecimal getNumPorcentaje() {
		return this.numPorcentaje;
	}

	public void setNumPorcentaje(BigDecimal numPorcentaje) {
		this.numPorcentaje = numPorcentaje;
	}

	public Set<DicFraccion> getDicFraccions() {
		return this.dicFraccions;
	}

	public void setDicFraccions(Set<DicFraccion> dicFraccions) {
		this.dicFraccions = dicFraccions;
	}

}