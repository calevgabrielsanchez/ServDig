package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import java.math.BigDecimal;

/**
 * The persistent class for the CRT_NROFOLIO database table.
 * 
 */

@MappedSuperclass
public class AbstractCrtNroFolio extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_PK_FOLIO")
	@SequenceGenerator(name = "NROFOLIO_CVEIDCLASE_GENERATOR", sequenceName = "CRS_CVE_PK_FOLIO")
	@GeneratedValue(generator = "NROFOLIO_CVEIDCLASE_GENERATOR")
	public long cvePkFolio;

	@Column(name = "CVE_DELEGACION")
	private BigDecimal cveDelegacion;

	@Column(name = "CVE_SUBDELEGACION")
	private BigDecimal cveSubdelegacion;

	@Column(name = "NUM_ANIO")
	private BigDecimal numAnio;

	@Column(name = "NUM_NUMERO")
	private BigDecimal numNumero;

	// bi-directional many-to-one association to CrcTipocorr
	@ManyToOne
	@JoinColumn(name = "CVE_TIPOCORR")
	private CrcTipoCorr crcTipoCorr;

	public AbstractCrtNroFolio() {
	}

	public long getCvePkFolio() {
		return this.cvePkFolio;
	}

	public void setCvePkFolio(long cvePkFolio) {
		this.cvePkFolio = cvePkFolio;
	}

	public BigDecimal getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(BigDecimal cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public BigDecimal getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(BigDecimal cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public BigDecimal getNumAnio() {
		return this.numAnio;
	}

	public void setNumAnio(BigDecimal numAnio) {
		this.numAnio = numAnio;
	}

	public BigDecimal getNumNumero() {
		return this.numNumero;
	}

	public void setNumNumero(BigDecimal numNumero) {
		this.numNumero = numNumero;
	}

	public CrcTipoCorr getCrcTipoCorr() {
		return crcTipoCorr;
	}

	public void setCrcTipoCorr(CrcTipoCorr crcTipoCorr) {
		this.crcTipoCorr = crcTipoCorr;
	}

}