package mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the H_COP_ESTADO_CUENTA database table.
 * 
 */
@Entity
@Table(name="H_COP_ESTADO_CUENTA")
public class HCopEstadoCuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HCopEstadoCuentaPK id;
	
	@Column(name="ACT")
	private BigDecimal act;

	@Column(name="ACT_ACU")
	private BigDecimal actAcu;

	@Column(name="CR_ACU_EJE")
	private BigDecimal crAcuEje;

	@Column(name="CR_DEL_CTL")
	private BigDecimal crDelCtl;

	@Column(name="CR_DEL_EMI")
	private BigDecimal crDelEmi;

	@Column(name="CR_DOC")
	private BigDecimal crDoc;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CR_FEC_ALTA")
	private Date crFecAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CR_FEC_NOT")
	private Date crFecNot;

	@Column(name="CR_FON_BEN")
	private BigDecimal crFonBen;

	@Column(name="CR_INC_ACT")
	private BigDecimal crIncAct;

	@Column(name="CR_INT")
	private BigDecimal crInt;

	@Column(name="CR_NOMBRE")
	private String crNombre;

	@Column(name="CR_ORI_RET")
	private BigDecimal crOriRet;

	@Column(name="CR_REG_OBRA")
	private String crRegObra;

	@Column(name="CR_SAL_EYM_ADY")
	private BigDecimal crSalEymAdy;

	@Column(name="CR_SAL_EYM_DIN")
	private BigDecimal crSalEymDin;

	@Column(name="CR_SAL_EYM_FIJA")
	private BigDecimal crSalEymFija;

	@Column(name="CR_SAL_GUAR")
	private BigDecimal crSalGuar;

	@Column(name="CR_SAL_IV")
	private BigDecimal crSalIv;

	@Column(name="CR_SAL_RT")
	private BigDecimal crSalRt;

	@Column(name="CR_SAL_TOT")
	private BigDecimal crSalTot;

	@Column(name="CR_SALEYM_PEN")
	private BigDecimal crSaleymPen;

	@Column(name="CR_SUB_CTL")
	private BigDecimal crSubCtl;

	@Column(name="CR_SUB_EMI")
	private BigDecimal crSubEmi;

	@Column(name="CR_TOTAL_ADEUDO")
	private BigDecimal crTotalAdeudo;

	@Column(name="CVE_CIZ")
	private String cveCiz;

	@Column(name="DIG_VER")
	private String digVer;

	@Column(name="FAC_ACT")
	private BigDecimal facAct;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_CARGA")
	private Date fecCarga;

	@Column(name="INT_ACU")
	private BigDecimal intAcu;

	@Column(name="MOD_COR")
	private String modCor;

	@Column(name="PATRON_COR")
	private String patronCor;

	@Column(name="TIPO_NOT")
	private BigDecimal tipoNot;
	
	@Column(name="IMP_CR_IMP_OBR_EXC")
	private BigDecimal impCrImpObrExc;
	
	@Column(name="IMP_CR_IMP_OBR_DIN")
	private BigDecimal impCrImpObrDin;
	
	@Column(name="IMP_CR_IMP_OBR_PEN")
	private BigDecimal impCrImpObrPen;
	
	@Column(name="IMP_CR_IMP_OBR_IV")
	private BigDecimal impCrImpObrIv;
	
	@Column(name="IMP_CR_IMP_TOT_OBR")
	private BigDecimal impCrImpTotObr;
	
	
	
	 


	public BigDecimal getImpCrImpObrExc() {
		return impCrImpObrExc;
	}

	public void setImpCrImpObrExc(BigDecimal impCrImpObrExc) {
		this.impCrImpObrExc = impCrImpObrExc;
	}

	public BigDecimal getImpCrImpObrDin() {
		return impCrImpObrDin;
	}

	public void setImpCrImpObrDin(BigDecimal impCrImpObrDin) {
		this.impCrImpObrDin = impCrImpObrDin;
	}

	public BigDecimal getImpCrImpObrPen() {
		return impCrImpObrPen;
	}

	public void setImpCrImpObrPen(BigDecimal impCrImpObrPen) {
		this.impCrImpObrPen = impCrImpObrPen;
	}

	public BigDecimal getImpCrImpObrIv() {
		return impCrImpObrIv;
	}

	public void setImpCrImpObrIv(BigDecimal impCrImpObrIv) {
		this.impCrImpObrIv = impCrImpObrIv;
	}

	public BigDecimal getImpCrImpTotObr() {
		return impCrImpTotObr;
	}

	public void setImpCrImpTotObr(BigDecimal impCrImpTotObr) {
		this.impCrImpTotObr = impCrImpTotObr;
	}

	public HCopEstadoCuenta() {
	}

	public HCopEstadoCuentaPK getId() {
		return id;
	}

	public void setId(HCopEstadoCuentaPK id) {
		this.id = id;
	}

	public BigDecimal getAct() {
		return act;
	}

	public void setAct(BigDecimal act) {
		this.act = act;
	}

	public BigDecimal getActAcu() {
		return actAcu;
	}

	public void setActAcu(BigDecimal actAcu) {
		this.actAcu = actAcu;
	}

	public BigDecimal getCrAcuEje() {
		return crAcuEje;
	}

	public void setCrAcuEje(BigDecimal crAcuEje) {
		this.crAcuEje = crAcuEje;
	}

	public BigDecimal getCrDelCtl() {
		return crDelCtl;
	}

	public void setCrDelCtl(BigDecimal crDelCtl) {
		this.crDelCtl = crDelCtl;
	}

	public BigDecimal getCrDelEmi() {
		return crDelEmi;
	}

	public void setCrDelEmi(BigDecimal crDelEmi) {
		this.crDelEmi = crDelEmi;
	}

	public BigDecimal getCrDoc() {
		return crDoc;
	}

	public void setCrDoc(BigDecimal crDoc) {
		this.crDoc = crDoc;
	}

	public Date getCrFecAlta() {
		return crFecAlta;
	}

	public void setCrFecAlta(Date crFecAlta) {
		this.crFecAlta = crFecAlta;
	}

	public Date getCrFecNot() {
		return crFecNot;
	}

	public void setCrFecNot(Date crFecNot) {
		this.crFecNot = crFecNot;
	}

	public BigDecimal getCrFonBen() {
		return crFonBen;
	}

	public void setCrFonBen(BigDecimal crFonBen) {
		this.crFonBen = crFonBen;
	}

	public BigDecimal getCrIncAct() {
		return crIncAct;
	}

	public void setCrIncAct(BigDecimal crIncAct) {
		this.crIncAct = crIncAct;
	}

	public BigDecimal getCrInt() {
		return crInt;
	}

	public void setCrInt(BigDecimal crInt) {
		this.crInt = crInt;
	}

	public String getCrNombre() {
		return crNombre;
	}

	public void setCrNombre(String crNombre) {
		this.crNombre = crNombre;
	}

	public BigDecimal getCrOriRet() {
		return crOriRet;
	}

	public void setCrOriRet(BigDecimal crOriRet) {
		this.crOriRet = crOriRet;
	}

	public String getCrRegObra() {
		return crRegObra;
	}

	public void setCrRegObra(String crRegObra) {
		this.crRegObra = crRegObra;
	}

	public BigDecimal getCrSalEymAdy() {
		return crSalEymAdy;
	}

	public void setCrSalEymAdy(BigDecimal crSalEymAdy) {
		this.crSalEymAdy = crSalEymAdy;
	}

	public BigDecimal getCrSalEymDin() {
		return crSalEymDin;
	}

	public void setCrSalEymDin(BigDecimal crSalEymDin) {
		this.crSalEymDin = crSalEymDin;
	}

	public BigDecimal getCrSalEymFija() {
		return crSalEymFija;
	}

	public void setCrSalEymFija(BigDecimal crSalEymFija) {
		this.crSalEymFija = crSalEymFija;
	}

	public BigDecimal getCrSalGuar() {
		return crSalGuar;
	}

	public void setCrSalGuar(BigDecimal crSalGuar) {
		this.crSalGuar = crSalGuar;
	}

	public BigDecimal getCrSalIv() {
		return crSalIv;
	}

	public void setCrSalIv(BigDecimal crSalIv) {
		this.crSalIv = crSalIv;
	}

	public BigDecimal getCrSalRt() {
		return crSalRt;
	}

	public void setCrSalRt(BigDecimal crSalRt) {
		this.crSalRt = crSalRt;
	}

	public BigDecimal getCrSalTot() {
		return crSalTot;
	}

	public void setCrSalTot(BigDecimal crSalTot) {
		this.crSalTot = crSalTot;
	}

	public BigDecimal getCrSaleymPen() {
		return crSaleymPen;
	}

	public void setCrSaleymPen(BigDecimal crSaleymPen) {
		this.crSaleymPen = crSaleymPen;
	}

	public BigDecimal getCrSubCtl() {
		return crSubCtl;
	}

	public void setCrSubCtl(BigDecimal crSubCtl) {
		this.crSubCtl = crSubCtl;
	}

	public BigDecimal getCrSubEmi() {
		return crSubEmi;
	}

	public void setCrSubEmi(BigDecimal crSubEmi) {
		this.crSubEmi = crSubEmi;
	}

	public BigDecimal getCrTotalAdeudo() {
		return crTotalAdeudo;
	}

	public void setCrTotalAdeudo(BigDecimal crTotalAdeudo) {
		this.crTotalAdeudo = crTotalAdeudo;
	}

	public String getCveCiz() {
		return cveCiz;
	}

	public void setCveCiz(String cveCiz) {
		this.cveCiz = cveCiz;
	}

	public String getDigVer() {
		return digVer;
	}

	public void setDigVer(String digVer) {
		this.digVer = digVer;
	}

	public BigDecimal getFacAct() {
		return facAct;
	}

	public void setFacAct(BigDecimal facAct) {
		this.facAct = facAct;
	}

	public Date getFecCarga() {
		return fecCarga;
	}

	public void setFecCarga(Date fecCarga) {
		this.fecCarga = fecCarga;
	}

	public BigDecimal getIntAcu() {
		return intAcu;
	}

	public void setIntAcu(BigDecimal intAcu) {
		this.intAcu = intAcu;
	}

	public String getModCor() {
		return modCor;
	}

	public void setModCor(String modCor) {
		this.modCor = modCor;
	}

	public String getPatronCor() {
		return patronCor;
	}

	public void setPatronCor(String patronCor) {
		this.patronCor = patronCor;
	}

	public BigDecimal getTipoNot() {
		return tipoNot;
	}

	public void setTipoNot(BigDecimal tipoNot) {
		this.tipoNot = tipoNot;
	}

	
}