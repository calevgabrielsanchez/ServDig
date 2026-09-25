package mx.gob.imss.ctirss.delta.cobranza.modelo;

import java.util.Collection;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoCobroRcv extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7381576298367224032L;

	public TipoCobroRcv(){
	}
	
	private String crDoc;
	private String tipoCobro;
	private Collection<CreditoRCV> creditos;
	private String subTotalCuotas;
	private String subTotalAct;
	private String subTotalRec;
	private String subTotal;
	
	public String getCrDoc() {
		return crDoc;
	}
	public void setCrDoc(String crDoc) {
		this.crDoc = crDoc;
	}
	public String getTipoCobro() {
		return tipoCobro;
	}
	public void setTipoCobro(String tipoCobro) {
		this.tipoCobro = tipoCobro;
	}
	public Collection<CreditoRCV> getCreditos() {
		return creditos;
	}
	public void setCreditos(Collection<CreditoRCV> creditos) {
		this.creditos = creditos;
	}
	public String getSubTotalCuotas() {
		return subTotalCuotas;
	}
	public void setSubTotalCuotas(String subTotalCuotas) {
		this.subTotalCuotas = subTotalCuotas;
	}
	public String getSubTotalAct() {
		return subTotalAct;
	}
	public void setSubTotalAct(String subTotalAct) {
		this.subTotalAct = subTotalAct;
	}
	public String getSubTotalRec() {
		return subTotalRec;
	}
	public void setSubTotalRec(String subTotalRec) {
		this.subTotalRec = subTotalRec;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	
	
}
