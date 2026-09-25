package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_MARCAS_PATRON_SUJETO_OBLIG database table.
 * 
 */
@Entity
@Table(name="DIT_MARCAS_PATRON_SUJETO_OBLIG")
public class DitMarcasPatronSujetoOblig implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitMarcasPatronSujetoObligPK id;

	@Column(name="CVE_ID_DOC", precision=22)
	private BigDecimal cveIdDoc;

	@Column(name="CVE_ID_MEDIO_PAGO", precision=22)
	private BigDecimal cveIdMedioPago;

	@Column(name="CVE_ID_USU", precision=22)
	private BigDecimal cveIdUsu;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CAP")
	private Date fecCap;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_NOT")
	private Date fecNot;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_VAL")
	private Date fecVal;

	@Column(name="HORA_CAP", precision=22)
	private BigDecimal horaCap;

	@Column(name="NUM_CAJA", precision=22)
	private BigDecimal numCaja;

	@Column(name="NUM_DIAS_AUSENTISMO", precision=22)
	private BigDecimal numDiasAusentismo;

	@Column(name="NUM_DIAS_COP", precision=22)
	private BigDecimal numDiasCop;

	@Column(name="NUM_DIAS_INCAPACIDAD", precision=22)
	private BigDecimal numDiasIncapacidad;

	@Column(name="NUM_DIAS_RCV", precision=22)
	private BigDecimal numDiasRcv;

	@Column(name="NUM_ERROR", precision=22)
	private BigDecimal numError;

	@Column(name="NUM_FOLIO_SUA", precision=22)
	private BigDecimal numFolioSua;

	@Column(name="NUM_FON_BEN", precision=22)
	private BigDecimal numFonBen;

	@Column(name="NUM_INPC_ACT", precision=22)
	private BigDecimal numInpcAct;

	@Column(name="NUM_OPE_CAJA", precision=22)
	private BigDecimal numOpeCaja;

	@Column(name="NUM_ORI_RET", precision=22)
	private BigDecimal numOriRet;

	@Column(name="NUM_REG_OBRA", precision=22)
	private BigDecimal numRegObra;

	@Column(name="NUM_TRAB_COP", precision=22)
	private BigDecimal numTrabCop;

	@Column(name="NUM_TRAB_RCV", precision=22)
	private BigDecimal numTrabRcv;

	@Column(name="SEC_NOT", precision=22)
	private BigDecimal secNot;

	@Column(name="TIPO_AJUSTE", precision=22)
	private BigDecimal tipoAjuste;

	@Column(name="TIPO_REG", precision=22)
	private BigDecimal tipoReg;

	//bi-directional many-to-one association to DitCuotasPatronSujetoOblig
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO", referencedColumnName="CVE_ID_PATRON_SUJETO_OBLIGADO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NUM_CRED", referencedColumnName="NUM_CRED", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NUM_PER", referencedColumnName="NUM_PER", nullable=false, insertable=false, updatable=false)
		})
	private DitCuotasPatronSujetoOblig ditCuotasPatronSujetoOblig;

    public DitMarcasPatronSujetoOblig() {
    }

	public DitMarcasPatronSujetoObligPK getId() {
		return this.id;
	}

	public void setId(DitMarcasPatronSujetoObligPK id) {
		this.id = id;
	}
	
	public BigDecimal getCveIdDoc() {
		return this.cveIdDoc;
	}

	public void setCveIdDoc(BigDecimal cveIdDoc) {
		this.cveIdDoc = cveIdDoc;
	}

	public BigDecimal getCveIdMedioPago() {
		return this.cveIdMedioPago;
	}

	public void setCveIdMedioPago(BigDecimal cveIdMedioPago) {
		this.cveIdMedioPago = cveIdMedioPago;
	}

	public BigDecimal getCveIdUsu() {
		return this.cveIdUsu;
	}

	public void setCveIdUsu(BigDecimal cveIdUsu) {
		this.cveIdUsu = cveIdUsu;
	}

	public Date getFecCap() {
		return this.fecCap;
	}

	public void setFecCap(Date fecCap) {
		this.fecCap = fecCap;
	}

	public Date getFecNot() {
		return this.fecNot;
	}

	public void setFecNot(Date fecNot) {
		this.fecNot = fecNot;
	}

	public Date getFecVal() {
		return this.fecVal;
	}

	public void setFecVal(Date fecVal) {
		this.fecVal = fecVal;
	}

	public BigDecimal getHoraCap() {
		return this.horaCap;
	}

	public void setHoraCap(BigDecimal horaCap) {
		this.horaCap = horaCap;
	}

	public BigDecimal getNumCaja() {
		return this.numCaja;
	}

	public void setNumCaja(BigDecimal numCaja) {
		this.numCaja = numCaja;
	}

	public BigDecimal getNumDiasAusentismo() {
		return this.numDiasAusentismo;
	}

	public void setNumDiasAusentismo(BigDecimal numDiasAusentismo) {
		this.numDiasAusentismo = numDiasAusentismo;
	}

	public BigDecimal getNumDiasCop() {
		return this.numDiasCop;
	}

	public void setNumDiasCop(BigDecimal numDiasCop) {
		this.numDiasCop = numDiasCop;
	}

	public BigDecimal getNumDiasIncapacidad() {
		return this.numDiasIncapacidad;
	}

	public void setNumDiasIncapacidad(BigDecimal numDiasIncapacidad) {
		this.numDiasIncapacidad = numDiasIncapacidad;
	}

	public BigDecimal getNumDiasRcv() {
		return this.numDiasRcv;
	}

	public void setNumDiasRcv(BigDecimal numDiasRcv) {
		this.numDiasRcv = numDiasRcv;
	}

	public BigDecimal getNumError() {
		return this.numError;
	}

	public void setNumError(BigDecimal numError) {
		this.numError = numError;
	}

	public BigDecimal getNumFolioSua() {
		return this.numFolioSua;
	}

	public void setNumFolioSua(BigDecimal numFolioSua) {
		this.numFolioSua = numFolioSua;
	}

	public BigDecimal getNumFonBen() {
		return this.numFonBen;
	}

	public void setNumFonBen(BigDecimal numFonBen) {
		this.numFonBen = numFonBen;
	}

	public BigDecimal getNumInpcAct() {
		return this.numInpcAct;
	}

	public void setNumInpcAct(BigDecimal numInpcAct) {
		this.numInpcAct = numInpcAct;
	}

	public BigDecimal getNumOpeCaja() {
		return this.numOpeCaja;
	}

	public void setNumOpeCaja(BigDecimal numOpeCaja) {
		this.numOpeCaja = numOpeCaja;
	}

	public BigDecimal getNumOriRet() {
		return this.numOriRet;
	}

	public void setNumOriRet(BigDecimal numOriRet) {
		this.numOriRet = numOriRet;
	}

	public BigDecimal getNumRegObra() {
		return this.numRegObra;
	}

	public void setNumRegObra(BigDecimal numRegObra) {
		this.numRegObra = numRegObra;
	}

	public BigDecimal getNumTrabCop() {
		return this.numTrabCop;
	}

	public void setNumTrabCop(BigDecimal numTrabCop) {
		this.numTrabCop = numTrabCop;
	}

	public BigDecimal getNumTrabRcv() {
		return this.numTrabRcv;
	}

	public void setNumTrabRcv(BigDecimal numTrabRcv) {
		this.numTrabRcv = numTrabRcv;
	}

	public BigDecimal getSecNot() {
		return this.secNot;
	}

	public void setSecNot(BigDecimal secNot) {
		this.secNot = secNot;
	}

	public BigDecimal getTipoAjuste() {
		return this.tipoAjuste;
	}

	public void setTipoAjuste(BigDecimal tipoAjuste) {
		this.tipoAjuste = tipoAjuste;
	}

	public BigDecimal getTipoReg() {
		return this.tipoReg;
	}

	public void setTipoReg(BigDecimal tipoReg) {
		this.tipoReg = tipoReg;
	}

	public DitCuotasPatronSujetoOblig getDitCuotasPatronSujetoOblig() {
		return this.ditCuotasPatronSujetoOblig;
	}

	public void setDitCuotasPatronSujetoOblig(DitCuotasPatronSujetoOblig ditCuotasPatronSujetoOblig) {
		this.ditCuotasPatronSujetoOblig = ditCuotasPatronSujetoOblig;
	}
	
}