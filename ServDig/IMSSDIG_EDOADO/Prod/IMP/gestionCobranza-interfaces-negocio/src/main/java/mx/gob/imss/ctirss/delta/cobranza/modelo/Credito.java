package mx.gob.imss.ctirss.delta.cobranza.modelo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Credito extends AbstractModel
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -273121869000455420L;


	public Credito(){
	}
	
	String act;//ACT    19        NUMBER (12,2)        None           
	String actAcu;//ACT_ACU    29        NUMBER (12,2)        None           
	String crAcuEje;//CR_ACU_EJE    31        NUMBER (12,2)        None           
	String crCred;//CR_CRED    6        CHAR (9 Byte)        None           
	String crDelCtl;//CR_DEL_CTL    9        NUMBER (2)        None           
	String crDelEmi;//CR_DEL_EMI    7        NUMBER (2)        None           
	String crDoc;//CR_DOC    11        NUMBER (2)        None           
	String crFecAlta;//CR_FEC_ALTA    15        DATE        None           
	String crFecNot;//CR_FEC_NOT    16        DATE        None           
	String crFonBen;//CR_FON_BEN    12        NUMBER (1)        None           
	String crIncAct;//CR_INC_ACT    13        NUMBER (2)        None           
	String crInt;//CR_INT    20        NUMBER (12,2)        None           
	String crMod;//CR_MOD    2        CHAR (2 Byte)        None           
	String crNombre;//CR_NOMBRE    4        CHAR (80 Byte)        None           
	String crOriRet;//CR_ORI_RET    14        NUMBER (1)        None           
	String crPat;//CR_PAT    1        CHAR (8 Byte)        None           
	String crPer;//CR_PER    5        NUMBER (6)        None           
	String crRegObra;//CR_REG_OBRA    17        CHAR (10 Byte)        None           
	String crSalEyMAdy;//CR_SAL_EYM_ADY    23        NUMBER (12,2)        None           
	String crSalEyMDin;//CR_SAL_EYM_DIN    24        NUMBER (12,2)        None           
	String crSalEyMFija;//CR_SAL_EYM_FIJA    22        NUMBER (12,2)        None           
	String crSalGuar;//CR_SAL_GUAR    28        NUMBER (12,2)        None           
	String crSalIV;//CR_SAL_IV    27        NUMBER (12,2)        None           
	String crSalRt;//CR_SAL_RT    26        NUMBER (12,2)        None           
	String crSalTot;//CR_SAL_TOT    18        NUMBER (12,2)        None           
	String crSaleyMPen;//CR_SALEYM_PEN    25        NUMBER (12,2)        None           
	String crSubCtl;//CR_SUB_CTL    10        NUMBER (2)        None           
	String crSubEmi;//CR_SUB_EMI    8        NUMBER (2)        None           
	String crTotalAdeudo;//CR_TOTAL_ADEUDO    21        NUMBER (12,2)        None           
	String digVer;//DIG_VER    3        CHAR (1 Byte)        None           
	String fecCarga;//FEC_CARGA    32        DATE        None           
	String intAcu;//INT_ACU    30        NUMBER (12,2)        None
	String regPatCor;//REG_PATRONAL_COR
	String modCor;//MODALIDAD_COR
	String tipoNotificacion;//T_NOTIFICACION
	
	String delegacionDesc;//No viene en la tabla común
	String subDelegacionDesc;//No viene en la tabla común
	String porcentajeRecargos;//No viene en la tabla común
	String factorAct;//No viene en la tabla común
	String tipoCobro;//No viene en la tabla común
	String situacionCobro;//No viene en la tabla común
	String saldoMulta;//No viene en la tabla común
	String saldoActMulta;//No viene en la tabla común
	String enfermedadesMaternidad;//No viene en la tabla común
	String sumaTotal;

	public String getSumaTotal() {
		return sumaTotal;
	}
	public void setSumaTotal(String sumaTotal) {
		this.sumaTotal = sumaTotal;
	}
	public String getRegPatCor() {
		return regPatCor;
	}
	public void setRegPatCor(String regPatCor) {
		this.regPatCor = regPatCor;
	}
	public String getModCor() {
		return modCor;
	}
	public void setModCor(String modCor) {
		this.modCor = modCor;
	}
	public String getTipoNotificacion() {
		return tipoNotificacion;
	}
	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}
	public String getEnfermedadesMaternidad() {
		return enfermedadesMaternidad;
	}
	public void setEnfermedadesMaternidad(String enfermedadesMaternidad) {
		this.enfermedadesMaternidad = enfermedadesMaternidad;
	}
	public String getSaldoActMulta() {
		return saldoActMulta;
	}
	public void setSaldoActMulta(String saldoActMulta) {
		this.saldoActMulta = saldoActMulta;
	}
	public String getSaldoMulta() {
		return saldoMulta;
	}
	public void setSaldoMulta(String saldoMulta) {
		this.saldoMulta = saldoMulta;
	}

	public String getDelegacionDesc() {
		return delegacionDesc;
	}
	public void setDelegacionDesc(String delegacionDesc) {
		this.delegacionDesc = delegacionDesc;
	}
	public String getSubDelegacionDesc() {
		return subDelegacionDesc;
	}
	public void setSubDelegacionDesc(String subDelegacionDesc) {
		this.subDelegacionDesc = subDelegacionDesc;
	}
	public String getPorcentajeRecargos() {
		return porcentajeRecargos;
	}
	public void setPorcentajeRecargos(String porcentajeRecargos) {
		this.porcentajeRecargos = porcentajeRecargos;
	}
	public String getFactorAct() {
		return factorAct;
	}
	public void setFactorAct(String factorAct) {
		this.factorAct = factorAct;
	}
	public String getTipoCobro() {
		return tipoCobro;
	}
	public void setTipoCobro(String tipoCobro) {
		this.tipoCobro = tipoCobro;
	}
	public String getSituacionCobro() {
		return situacionCobro;
	}
	public void setSituacionCobro(String situacionCobro) {
		this.situacionCobro = situacionCobro;
	}


	
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public String getActAcu() {
		return actAcu;
	}
	public void setActAcu(String actAcu) {
		this.actAcu = actAcu;
	}
	public String getCrAcuEje() {
		return crAcuEje;
	}
	public void setCrAcuEje(String crAcuEje) {
		this.crAcuEje = crAcuEje;
	}
	public String getCrCred() {
		return crCred;
	}
	public void setCrCred(String crCred) {
		this.crCred = crCred;
	}
	public String getCrDelCtl() {
		return crDelCtl;
	}
	public void setCrDelCtl(String crDelCtl) {
		this.crDelCtl = crDelCtl;
	}
	public String getCrDelEmi() {
		return crDelEmi;
	}
	public void setCrDelEmi(String crDelEmi) {
		this.crDelEmi = crDelEmi;
	}
	public String getCrDoc() {
		return crDoc;
	}
	public void setCrDoc(String crDoc) {
		this.crDoc = crDoc;
	}
	public String getCrFecAlta() {
		return crFecAlta;
	}
	public void setCrFecAlta(String crFecAlta) {
		this.crFecAlta = crFecAlta;
	}
	public String getCrFecNot() {
		return crFecNot;
	}
	public void setCrFecNot(String crFecNot) {
		this.crFecNot = crFecNot;
	}
	public String getCrFonBen() {
		return crFonBen;
	}
	public void setCrFonBen(String crFonBen) {
		this.crFonBen = crFonBen;
	}
	public String getCrIncAct() {
		return crIncAct;
	}
	public void setCrIncAct(String crIncAct) {
		this.crIncAct = crIncAct;
	}
	public String getCrInt() {
		return crInt;
	}
	public void setCrInt(String crInt) {
		this.crInt = crInt;
	}
	public String getCrMod() {
		return crMod;
	}
	public void setCrMod(String crMod) {
		this.crMod = crMod;
	}
	public String getCrNombre() {
		return crNombre;
	}
	public void setCrNombre(String crNombre) {
		this.crNombre = crNombre;
	}
	public String getCrOriRet() {
		return crOriRet;
	}
	public void setCrOriRet(String crOriRet) {
		this.crOriRet = crOriRet;
	}
	public String getCrPat() {
		return crPat;
	}
	public void setCrPat(String crPat) {
		this.crPat = crPat;
	}
	public String getCrPer() {
		return crPer;
	}
	public void setCrPer(String crPer) {
		this.crPer = crPer;
	}
	public String getCrRegObra() {
		return crRegObra;
	}
	public void setCrRegObra(String crRegObra) {
		this.crRegObra = crRegObra;
	}
	public String getCrSalEyMAdy() {
		return crSalEyMAdy;
	}
	public void setCrSalEyMAdy(String crSalEyMAdy) {
		this.crSalEyMAdy = crSalEyMAdy;
	}
	public String getCrSalEyMDin() {
		return crSalEyMDin;
	}
	public void setCrSalEyMDin(String crSalEyMDin) {
		this.crSalEyMDin = crSalEyMDin;
	}
	public String getCrSalEyMFija() {
		return crSalEyMFija;
	}
	public void setCrSalEyMFija(String crSalEyMFija) {
		this.crSalEyMFija = crSalEyMFija;
	}
	public String getCrSalGuar() {
		return crSalGuar;
	}
	public void setCrSalGuar(String crSalGuar) {
		this.crSalGuar = crSalGuar;
	}
	public String getCrSalIV() {
		return crSalIV;
	}
	public void setCrSalIV(String crSalIV) {
		this.crSalIV = crSalIV;
	}
	public String getCrSalRt() {
		return crSalRt;
	}
	public void setCrSalRt(String crSalRt) {
		this.crSalRt = crSalRt;
	}
	public String getCrSalTot() {
		return crSalTot;
	}
	public void setCrSalTot(String crSalTot) {
		this.crSalTot = crSalTot;
	}
	public String getCrSaleyMPen() {
		return crSaleyMPen;
	}
	public void setCrSaleyMPen(String crSaleyMPen) {
		this.crSaleyMPen = crSaleyMPen;
	}
	public String getCrSubCtl() {
		return crSubCtl;
	}
	public void setCrSubCtl(String crSubCtl) {
		this.crSubCtl = crSubCtl;
	}
	public String getCrSubEmi() {
		return crSubEmi;
	}
	public void setCrSubEmi(String crSubEmi) {
		this.crSubEmi = crSubEmi;
	}
	public String getCrTotalAdeudo() {
		return crTotalAdeudo;
	}
	public void setCrTotalAdeudo(String crTotalAdeudo) {
		this.crTotalAdeudo = crTotalAdeudo;
	}
	public String getDigVer() {
		return digVer;
	}
	public void setDigVer(String digVer) {
		this.digVer = digVer;
	}
	public String getFecCarga() {
		return fecCarga;
	}
	public void setFecCarga(String fecCarga) {
		this.fecCarga = fecCarga;
	}
	public String getIntAcu() {
		return intAcu;
	}
	public void setIntAcu(String intAcu) {
		this.intAcu = intAcu;
	}
	public Credito(String act, String actAcu, String crAcuEje, String crCred,
			String crDelCtl, String crDelEmi, String crDoc, String crFecAlta,
			String crFecNot, String crFonBen, String crIncAct, String crInt,
			String crMod, String crNombre, String crOriRet, String crPat,
			String crPer, String crRegObra, String crSalEyMAdy,
			String crSalEyMDin, String crSalEyMFija, String crSalGuar,
			String crSalIV, String crSalRt, String crSalTot,
			String crSaleyMPen, String crSubCtl, String crSubEmi,
			String crTotalAdeudo, String digVer, String fecCarga, String intAcu) {
		super();
		this.act = act;
		this.actAcu = actAcu;
		this.crAcuEje = crAcuEje;
		this.crCred = crCred;
		this.crDelCtl = crDelCtl;
		this.crDelEmi = crDelEmi;
		this.crDoc = crDoc;
		this.crFecAlta = crFecAlta;
		this.crFecNot = crFecNot;
		this.crFonBen = crFonBen;
		this.crIncAct = crIncAct;
		this.crInt = crInt;
		this.crMod = crMod;
		this.crNombre = crNombre;
		this.crOriRet = crOriRet;
		this.crPat = crPat;
		this.crPer = crPer;
		this.crRegObra = crRegObra;
		this.crSalEyMAdy = crSalEyMAdy;
		this.crSalEyMDin = crSalEyMDin;
		this.crSalEyMFija = crSalEyMFija;
		this.crSalGuar = crSalGuar;
		this.crSalIV = crSalIV;
		this.crSalRt = crSalRt;
		this.crSalTot = crSalTot;
		this.crSaleyMPen = crSaleyMPen;
		this.crSubCtl = crSubCtl;
		this.crSubEmi = crSubEmi;
		this.crTotalAdeudo = crTotalAdeudo;
		this.digVer = digVer;
		this.fecCarga = fecCarga;
		this.intAcu = intAcu;
	}

	@Override
	public String toString() {
		return "Credito [crCred=" + crCred + "]";
	}
	
	
}
