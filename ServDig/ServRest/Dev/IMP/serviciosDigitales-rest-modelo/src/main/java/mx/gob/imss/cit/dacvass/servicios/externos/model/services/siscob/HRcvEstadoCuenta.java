package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class HRcvEstadoCuenta implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8945650976482789759L;

	private HRcvEstadoCuentaPK id;

	private BigDecimal act;

	private BigDecimal actAct;

	private BigDecimal crDelCtl;

	private BigDecimal crDelEmi;

	private BigDecimal crDoc;
	
	private Date crFecAlta;
	
	private Date crFecNot;

	private BigDecimal crFonBen;

	private BigDecimal crIncAct;

	private BigDecimal crInt;

	private BigDecimal crOriRet;

	private String crRegObra;

	private BigDecimal crSalCyv;

	private BigDecimal crSalRet;

	private BigDecimal crSalTot;

	private BigDecimal crSubCtl;

	private BigDecimal crSubEmi;

	private String cveCiz;

	private String digVer;
	
	private Date fecCarga;

	private BigDecimal intRec;

	private String modCor;

	private String patronCor;

	private BigDecimal rcrAcuEje;

	private String rcrNombre;

	private BigDecimal rcrSalTotal;

	private BigDecimal tipoNot;
	
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