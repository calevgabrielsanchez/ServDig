package mx.gob.imss.ctirss.delta.cobranza.modelo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Factor extends AbstractModel
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9143974762660821797L;
	public Factor(){
	}
	
	String cveConcepto; //CVE_CONCEPTO    2    2    CHAR (1 Byte)        None           
	String facAct; //FAC_ACT    3        NUMBER (5,4)        None           
	String facInt; //FAC_INT    4        NUMBER (6,3)        None           
	String facRecDof; //FACREC_DOF    9        DATE        None           
	String fechaApl; //FECHA_APL    5    3    DATE        None           
	String fechaVigencia; //FECHA_VIGENCIA    7        DATE        None           
	String inpc; //INPC    6        NUMBER (6,3)        None           
	String inpcRec; //INPC_REC    8        DATE        None           
	String periodo; //PERIODO    1    1    NUMBER (6)        None           
	String recargos; //RECARGOS    10        NUMBER (6,3)        None      	
	public String getCveConcepto() {
		return cveConcepto;
	}
	public void setCveConcepto(String cveConcepto) {
		this.cveConcepto = cveConcepto;
	}
	public String getFacAct() {
		return facAct;
	}
	public void setFacAct(String facAct) {
		this.facAct = facAct;
	}
	public String getFacInt() {
		return facInt;
	}
	public void setFacInt(String facInt) {
		this.facInt = facInt;
	}
	public String getFacRecDof() {
		return facRecDof;
	}
	public void setFacRecDof(String facRecDof) {
		this.facRecDof = facRecDof;
	}
	public String getFechaApl() {
		return fechaApl;
	}
	public void setFechaApl(String fechaApl) {
		this.fechaApl = fechaApl;
	}
	public String getFechaVigencia() {
		return fechaVigencia;
	}
	public void setFechaVigencia(String fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}
	public String getInpc() {
		return inpc;
	}
	public void setInpc(String inpc) {
		this.inpc = inpc;
	}
	public String getInpcRec() {
		return inpcRec;
	}
	public void setInpcRec(String inpcRec) {
		this.inpcRec = inpcRec;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getRecargos() {
		return recargos;
	}
	public void setRecargos(String recargos) {
		this.recargos = recargos;
	}
	
}
