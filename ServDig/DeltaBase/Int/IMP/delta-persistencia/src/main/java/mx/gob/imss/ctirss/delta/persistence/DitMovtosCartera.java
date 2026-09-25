package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_MOVTOS_CARTERA database table.
 * 
 */
@Entity
@Table(name="DIT_MOVTOS_CARTERA")
public class DitMovtosCartera implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitMovtosCarteraPK id;

	@Column(name="CVE_CONFIR_CREDITOS", precision=22)
	private BigDecimal cveConfirCreditos;

	@Column(name="CVE_DEL_MOVTO_T1", precision=22)
	private BigDecimal cveDelMovtoT1;

	@Column(name="CVE_DEL_MOVTO_T2", precision=22)
	private BigDecimal cveDelMovtoT2;

	@Column(name="CVE_ID_SEGUROS_IMSS_RCV", precision=2)
	private BigDecimal cveIdSegurosImssRcv;

	@Column(name="CVE_RECLASIFICA", precision=2)
	private BigDecimal cveReclasifica;

	@Column(name="CVE_SUBDEL_MOVTO_T1", precision=22)
	private BigDecimal cveSubdelMovtoT1;

	@Column(name="CVE_SUBDEL_MOVTO_T2", precision=22)
	private BigDecimal cveSubdelMovtoT2;

	@Column(name="DES_ORI_EMI_MANUAL", length=50)
	private String desOriEmiManual;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVTO")
	private Date fecMovto;

	@Column(name="IMP_CREDITO", precision=12, scale=2)
	private BigDecimal impCredito;

	@Column(name="IMP_RECAUDADO", precision=12, scale=2)
	private BigDecimal impRecaudado;

	@Column(name="IMP_RECLASIFICA", precision=12, scale=2)
	private BigDecimal impReclasifica;

	@Column(name="NUM_CAJA_RECAUDACION", precision=22)
	private BigDecimal numCajaRecaudacion;

	@Column(name="NUM_CTA_CONTABLE_RECLASIFICA", precision=15)
	private BigDecimal numCtaContableReclasifica;

	@Column(name="NUM_INC_ACTUAL", precision=2)
	private BigDecimal numIncActual;

	@Column(name="NUM_INC_ANTERIOR", precision=2)
	private BigDecimal numIncAnterior;

	@Column(name="TIPO_DOCTO", precision=22)
	private BigDecimal tipoDocto;

	@Column(name="TIPO_REG_COB", precision=2)
	private BigDecimal tipoRegCob;

	//bi-directional many-to-one association to DitCuotasPatronSujetoOblig
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO", referencedColumnName="CVE_ID_PATRON_SUJETO_OBLIGADO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NUM_CRED", referencedColumnName="NUM_CRED", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NUM_PER", referencedColumnName="NUM_PER", nullable=false, insertable=false, updatable=false)
		})
	private DitCuotasPatronSujetoOblig ditCuotasPatronSujetoOblig;

    public DitMovtosCartera() {
    }

	public DitMovtosCarteraPK getId() {
		return this.id;
	}

	public void setId(DitMovtosCarteraPK id) {
		this.id = id;
	}
	
	public BigDecimal getCveConfirCreditos() {
		return this.cveConfirCreditos;
	}

	public void setCveConfirCreditos(BigDecimal cveConfirCreditos) {
		this.cveConfirCreditos = cveConfirCreditos;
	}

	public BigDecimal getCveDelMovtoT1() {
		return this.cveDelMovtoT1;
	}

	public void setCveDelMovtoT1(BigDecimal cveDelMovtoT1) {
		this.cveDelMovtoT1 = cveDelMovtoT1;
	}

	public BigDecimal getCveDelMovtoT2() {
		return this.cveDelMovtoT2;
	}

	public void setCveDelMovtoT2(BigDecimal cveDelMovtoT2) {
		this.cveDelMovtoT2 = cveDelMovtoT2;
	}

	public BigDecimal getCveIdSegurosImssRcv() {
		return this.cveIdSegurosImssRcv;
	}

	public void setCveIdSegurosImssRcv(BigDecimal cveIdSegurosImssRcv) {
		this.cveIdSegurosImssRcv = cveIdSegurosImssRcv;
	}

	public BigDecimal getCveReclasifica() {
		return this.cveReclasifica;
	}

	public void setCveReclasifica(BigDecimal cveReclasifica) {
		this.cveReclasifica = cveReclasifica;
	}

	public BigDecimal getCveSubdelMovtoT1() {
		return this.cveSubdelMovtoT1;
	}

	public void setCveSubdelMovtoT1(BigDecimal cveSubdelMovtoT1) {
		this.cveSubdelMovtoT1 = cveSubdelMovtoT1;
	}

	public BigDecimal getCveSubdelMovtoT2() {
		return this.cveSubdelMovtoT2;
	}

	public void setCveSubdelMovtoT2(BigDecimal cveSubdelMovtoT2) {
		this.cveSubdelMovtoT2 = cveSubdelMovtoT2;
	}

	public String getDesOriEmiManual() {
		return this.desOriEmiManual;
	}

	public void setDesOriEmiManual(String desOriEmiManual) {
		this.desOriEmiManual = desOriEmiManual;
	}

	public Date getFecMovto() {
		return this.fecMovto;
	}

	public void setFecMovto(Date fecMovto) {
		this.fecMovto = fecMovto;
	}

	public BigDecimal getImpCredito() {
		return this.impCredito;
	}

	public void setImpCredito(BigDecimal impCredito) {
		this.impCredito = impCredito;
	}

	public BigDecimal getImpRecaudado() {
		return this.impRecaudado;
	}

	public void setImpRecaudado(BigDecimal impRecaudado) {
		this.impRecaudado = impRecaudado;
	}

	public BigDecimal getImpReclasifica() {
		return this.impReclasifica;
	}

	public void setImpReclasifica(BigDecimal impReclasifica) {
		this.impReclasifica = impReclasifica;
	}

	public BigDecimal getNumCajaRecaudacion() {
		return this.numCajaRecaudacion;
	}

	public void setNumCajaRecaudacion(BigDecimal numCajaRecaudacion) {
		this.numCajaRecaudacion = numCajaRecaudacion;
	}

	public BigDecimal getNumCtaContableReclasifica() {
		return this.numCtaContableReclasifica;
	}

	public void setNumCtaContableReclasifica(BigDecimal numCtaContableReclasifica) {
		this.numCtaContableReclasifica = numCtaContableReclasifica;
	}

	public BigDecimal getNumIncActual() {
		return this.numIncActual;
	}

	public void setNumIncActual(BigDecimal numIncActual) {
		this.numIncActual = numIncActual;
	}

	public BigDecimal getNumIncAnterior() {
		return this.numIncAnterior;
	}

	public void setNumIncAnterior(BigDecimal numIncAnterior) {
		this.numIncAnterior = numIncAnterior;
	}

	public BigDecimal getTipoDocto() {
		return this.tipoDocto;
	}

	public void setTipoDocto(BigDecimal tipoDocto) {
		this.tipoDocto = tipoDocto;
	}

	public BigDecimal getTipoRegCob() {
		return this.tipoRegCob;
	}

	public void setTipoRegCob(BigDecimal tipoRegCob) {
		this.tipoRegCob = tipoRegCob;
	}

	public DitCuotasPatronSujetoOblig getDitCuotasPatronSujetoOblig() {
		return this.ditCuotasPatronSujetoOblig;
	}

	public void setDitCuotasPatronSujetoOblig(DitCuotasPatronSujetoOblig ditCuotasPatronSujetoOblig) {
		this.ditCuotasPatronSujetoOblig = ditCuotasPatronSujetoOblig;
	}
	
}