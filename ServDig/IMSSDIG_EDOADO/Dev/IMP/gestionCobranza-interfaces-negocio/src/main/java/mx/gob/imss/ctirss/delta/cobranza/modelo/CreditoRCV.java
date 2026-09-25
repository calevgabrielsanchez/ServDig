package mx.gob.imss.ctirss.delta.cobranza.modelo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class CreditoRCV extends AbstractModel
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 96171901647056587L;



	public CreditoRCV(){
	}
	
		
	String actua;//ACTUA	13		NUMBER		None			
	String crSalRet;//CR_SAL_RET	10		NUMBER		None			
	String credito;//CREDITO	8		CHAR (10 Byte)		None			
	String cyv;//CYV	11		NUMBER		None			
	String fecAlta;//FEC_ALTA	17		DATE		None			
	String fecNot;//FEC_NOT	16		DATE		None			
	String incAct;//INC_ACT	15		NUMBER (2)		None			
	String modalidad;//MODALIDAD	2		CHAR (8 Byte)		None			
	String modalidadCor;//MODALIDAD_COR	6		CHAR (8 Byte)		None			
	String periodo;//PERIODO	3		NUMBER (6)		None			
	String recar;//RECAR	14		NUMBER		None			
	String regPatronal;//REG_PATRONAL	1		CHAR (8 Byte)		None			
	String regPatronalCor;//REG_PATRONAL_COR	5		CHAR (8 Byte)		None			
	String saldoTotal;//SALDO_TOTAL	12		NUMBER		None			
	String tDocumento;//T_DOCUMENTO	9		NUMBER (2)		None			
	String tNotificacion;//T_NOTIFICACION	7		NUMBER (2)		None			
	String totalAdeudo;//TOTAL_ADEUDO	4		NUMBER		None			
	String facAct;//FAC_ACT	18		NUMBER (6,4)		None			
	String facRec;//FAC_REC	19		NUMBER (7,4)		None		
	
	String delegacionDesc;//No viene en la tabla común
	String subDelegacionDesc;//No viene en la tabla común
	String porcentajeRecargos;//Calculado en ParamBean
	//String factorAct;//No viene en la tabla común
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




	public String getFacAct() {
		return facAct;
	}




	public void setFacAct(String facAct) {
		this.facAct = facAct;
	}




	public String getFacRec() {
		return facRec;
	}




	public void setFacRec(String facRec) {
		this.facRec = facRec;
	}




	public String getActua() {
		return actua;
	}




	public void setActua(String actua) {
		this.actua = actua;
	}




	public String getCrSalRet() {
		return crSalRet;
	}




	public void setCrSalRet(String crSalRet) {
		this.crSalRet = crSalRet;
	}




	public String getCredito() {
		return credito;
	}




	public void setCredito(String credito) {
		this.credito = credito;
	}




	public String getCyv() {
		return cyv;
	}




	public void setCyv(String cyv) {
		this.cyv = cyv;
	}




	public String getFecAlta() {
		return fecAlta;
	}




	public void setFecAlta(String fecAlta) {
		this.fecAlta = fecAlta;
	}




	public String getFecNot() {
		return fecNot;
	}




	public void setFecNot(String fecNot) {
		this.fecNot = fecNot;
	}




	public String getIncAct() {
		return incAct;
	}




	public void setIncAct(String incAct) {
		this.incAct = incAct;
	}




	public String getModalidad() {
		return modalidad;
	}




	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}




	public String getModalidadCor() {
		return modalidadCor;
	}




	public void setModalidadCor(String modalidadCor) {
		this.modalidadCor = modalidadCor;
	}




	public String getPeriodo() {
		return periodo;
	}




	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}




	public String getRecar() {
		return recar;
	}




	public void setRecar(String recar) {
		this.recar = recar;
	}




	public String getRegPatronal() {
		return regPatronal;
	}




	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}




	public String getRegPatronalCor() {
		return regPatronalCor;
	}




	public void setRegPatronalCor(String regPatronalCor) {
		this.regPatronalCor = regPatronalCor;
	}




	public String getSaldoTotal() {
		return saldoTotal;
	}




	public void setSaldoTotal(String saldoTotal) {
		this.saldoTotal = saldoTotal;
	}




	public String gettDocumento() {
		return tDocumento;
	}




	public void settDocumento(String tDocumento) {
		this.tDocumento = tDocumento;
	}




	public String gettNotificacion() {
		return tNotificacion;
	}




	public void settNotificacion(String tNotificacion) {
		this.tNotificacion = tNotificacion;
	}




	public String getTotalAdeudo() {
		return totalAdeudo;
	}




	public void setTotalAdeudo(String totalAdeudo) {
		this.totalAdeudo = totalAdeudo;
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




	public String getSaldoMulta() {
		return saldoMulta;
	}




	public void setSaldoMulta(String saldoMulta) {
		this.saldoMulta = saldoMulta;
	}




	public String getSaldoActMulta() {
		return saldoActMulta;
	}




	public void setSaldoActMulta(String saldoActMulta) {
		this.saldoActMulta = saldoActMulta;
	}




	public String getEnfermedadesMaternidad() {
		return enfermedadesMaternidad;
	}




	public void setEnfermedadesMaternidad(String enfermedadesMaternidad) {
		this.enfermedadesMaternidad = enfermedadesMaternidad;
	}


	
	
	
	
	
	@Override
	public String toString() {
		return "Credito [rcrCred=" + credito + "]";
	}
	
	
}
