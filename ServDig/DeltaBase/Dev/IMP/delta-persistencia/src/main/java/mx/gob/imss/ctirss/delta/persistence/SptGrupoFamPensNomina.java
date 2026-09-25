package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_GRUPO_FAM_PENS_NOMINA database table.
 * 
 */
@Entity
@Table(name="SPT_GRUPO_FAM_PENS_NOMINA")
@NamedQuery(name="SptGrupoFamPensNomina.findAll", query="SELECT s FROM SptGrupoFamPensNomina s")
public class SptGrupoFamPensNomina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTGRUPOFAMPENSNOMINA", sequenceName = "SEQ_SPTGRUPOFAMPENSNOMINA")
	@GeneratedValue(generator = "SEQ_SPTGRUPOFAMPENSNOMINA")
	@Column(name="CVE_ID_GRUPO_FAM_PENS_NOMINA")
	private long cveIdGrupoFamPensNomina;

	@Column(name="CVE_PERIODO_NOMINA")
	private BigDecimal cvePeriodoNomina;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_DERECHO")
	private Date fecInicioDerecho;

	@Column(name="IMP_PENSION_ALIMENTICIA")
	private BigDecimal impPensionAlimenticia;

	@Column(name="POR_PENSION_ALIMENTICIA")
	private BigDecimal porPensionAlimenticia;

	//bi-directional many-to-one association to SptGrupoFamiliarPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_GRUPO_FAMILIAR_PENSION")
	private SptGrupoFamiliarPension sptGrupoFamiliarPension;

	public SptGrupoFamPensNomina() {
	}

	public long getCveIdGrupoFamPensNomina() {
		return this.cveIdGrupoFamPensNomina;
	}

	public void setCveIdGrupoFamPensNomina(long cveIdGrupoFamPensNomina) {
		this.cveIdGrupoFamPensNomina = cveIdGrupoFamPensNomina;
	}

	public BigDecimal getCvePeriodoNomina() {
		return this.cvePeriodoNomina;
	}

	public void setCvePeriodoNomina(BigDecimal cvePeriodoNomina) {
		this.cvePeriodoNomina = cvePeriodoNomina;
	}

	public Date getFecInicioDerecho() {
		return this.fecInicioDerecho;
	}

	public void setFecInicioDerecho(Date fecInicioDerecho) {
		this.fecInicioDerecho = fecInicioDerecho;
	}

	public BigDecimal getImpPensionAlimenticia() {
		return this.impPensionAlimenticia;
	}

	public void setImpPensionAlimenticia(BigDecimal impPensionAlimenticia) {
		this.impPensionAlimenticia = impPensionAlimenticia;
	}

	public BigDecimal getPorPensionAlimenticia() {
		return this.porPensionAlimenticia;
	}

	public void setPorPensionAlimenticia(BigDecimal porPensionAlimenticia) {
		this.porPensionAlimenticia = porPensionAlimenticia;
	}

	public SptGrupoFamiliarPension getSptGrupoFamiliarPension() {
		return this.sptGrupoFamiliarPension;
	}

	public void setSptGrupoFamiliarPension(SptGrupoFamiliarPension sptGrupoFamiliarPension) {
		this.sptGrupoFamiliarPension = sptGrupoFamiliarPension;
	}

}