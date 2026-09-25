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
 * The persistent class for the H_RCV_ESTADO_CUENTA database table.
 * 
 */
@Entity
@Table(name="H_RCV_ESTADO_CUENTA")
public class HRcvEstadoCuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HRcvEstadoCuentaPK id;

	@Column(name="ACT")
	private BigDecimal act;

	@Column(name="ACT_ACT")
	private BigDecimal actAct;

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

	@Column(name="CR_ORI_RET")
	private BigDecimal crOriRet;

	@Column(name="CR_REG_OBRA")
	private String crRegObra;

	@Column(name="CR_SAL_CYV")
	private BigDecimal crSalCyv;

	@Column(name="CR_SAL_RET")
	private BigDecimal crSalRet;

	@Column(name="CR_SAL_TOT")
	private BigDecimal crSalTot;

	@Column(name="CR_SUB_CTL")
	private BigDecimal crSubCtl;

	@Column(name="CR_SUB_EMI")
	private BigDecimal crSubEmi;

	@Column(name="CVE_CIZ")
	private String cveCiz;

	@Column(name="DIG_VER")
	private String digVer;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_CARGA")
	private Date fecCarga;

	@Column(name="INT_REC")
	private BigDecimal intRec;

	@Column(name="MOD_COR")
	private String modCor;

	@Column(name="PATRON_COR")
	private String patronCor;

	@Column(name="RCR_ACU_EJE")
	private BigDecimal rcrAcuEje;

	@Column(name="RCR_NOMBRE")
	private String rcrNombre;

	@Column(name="RCR_SAL_TOTAL")
	private BigDecimal rcrSalTotal;

	@Column(name="TIPO_NOT")
	private BigDecimal tipoNot;
	
	@Column(name="IMP_CR_IMP_OBR_CYV")
	private BigDecimal impCrmpObrCyv;
	
	
	 

	public BigDecimal getImpCrmpObrCyv() {
		return impCrmpObrCyv;
	}

	public void setImpCrmpObrCyv(BigDecimal impCrmpObrCyv) {
		this.impCrmpObrCyv = impCrmpObrCyv;
	}

	public HRcvEstadoCuenta() {
	}

	public HRcvEstadoCuentaPK getId() {
		return id;
	}

	public void setId(HRcvEstadoCuentaPK id) {
		this.id = id;
	}

	public BigDecimal getAct() {
		return act;
	}

	public void setAct(BigDecimal act) {
		this.act = act;
	}

	public BigDecimal getActAct() {
		return actAct;
	}

	public void setActAct(BigDecimal actAct) {
		this.actAct = actAct;
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

	public BigDecimal getCrSalCyv() {
		return crSalCyv;
	}

	public void setCrSalCyv(BigDecimal crSalCyv) {
		this.crSalCyv = crSalCyv;
	}

	public BigDecimal getCrSalRet() {
		return crSalRet;
	}

	public void setCrSalRet(BigDecimal crSalRet) {
		this.crSalRet = crSalRet;
	}

	public BigDecimal getCrSalTot() {
		return crSalTot;
	}

	public void setCrSalTot(BigDecimal crSalTot) {
		this.crSalTot = crSalTot;
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

	public Date getFecCarga() {
		return fecCarga;
	}

	public void setFecCarga(Date fecCarga) {
		this.fecCarga = fecCarga;
	}

	public BigDecimal getIntRec() {
		return intRec;
	}

	public void setIntRec(BigDecimal intRec) {
		this.intRec = intRec;
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

	public BigDecimal getRcrAcuEje() {
		return rcrAcuEje;
	}

	public void setRcrAcuEje(BigDecimal rcrAcuEje) {
		this.rcrAcuEje = rcrAcuEje;
	}

	public String getRcrNombre() {
		return rcrNombre;
	}

	public void setRcrNombre(String rcrNombre) {
		this.rcrNombre = rcrNombre;
	}

	public BigDecimal getRcrSalTotal() {
		return rcrSalTotal;
	}

	public void setRcrSalTotal(BigDecimal rcrSalTotal) {
		this.rcrSalTotal = rcrSalTotal;
	}

	public BigDecimal getTipoNot() {
		return tipoNot;
	}

	public void setTipoNot(BigDecimal tipoNot) {
		this.tipoNot = tipoNot;
	}

	
}