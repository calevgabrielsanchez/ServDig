package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HCopEstadoCuenta implements Serializable {
	
	private static final long serialVersionUID = 3931582307525341406L;

	private HCopEstadoCuentaPK id;
	
	private BigDecimal act;

	private BigDecimal actAcu;

	private BigDecimal crAcuEje;

	private BigDecimal crDelCtl;

	private BigDecimal crDelEmi;

	private BigDecimal crDoc;
	
	private Date crFecAlta;
	
	private Date crFecNot;

	private BigDecimal crFonBen;
	private BigDecimal crIncAct;

	private BigDecimal crInt;

	private String crNombre;

	private BigDecimal crOriRet;

	private String crRegObra;

	private BigDecimal crSalEymAdy;

	private BigDecimal crSalEymDin;
	private BigDecimal crSalEymFija;

	private BigDecimal crSalGuar;

	private BigDecimal crSalIv;
	private BigDecimal crSalRt;

	private BigDecimal crSalTot;
	private BigDecimal crSaleymPen;

	private BigDecimal crSubCtl;

	private BigDecimal crSubEmi;

	private BigDecimal crTotalAdeudo;

	private String cveCiz;

	private String digVer;

	private BigDecimal facAct;

	private Date fecCarga;

	private BigDecimal intAcu;

	private String modCor;

	private String patronCor;

	private BigDecimal tipoNot;
	
	private BigDecimal impCrImpObrExc;
	
	private BigDecimal impCrImpObrDin;
	
	private BigDecimal impCrImpObrPen;
	
	private BigDecimal impCrImpObrIv;
	
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