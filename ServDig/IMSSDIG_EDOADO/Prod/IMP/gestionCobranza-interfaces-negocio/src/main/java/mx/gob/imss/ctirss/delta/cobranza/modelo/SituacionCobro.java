package mx.gob.imss.ctirss.delta.cobranza.modelo;

import java.util.Collection;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class SituacionCobro extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 787003639281844157L;
	public SituacionCobro(){
	}
	
	private String crInc;
	private String situacionCobro;
	private Collection<Credito> creditos;
	private String subTotalCuotas;
	private String subTotalAct;
	private String subTotalRec;
	private String subTotal;
	
	public String getCrInc() {
		return crInc;
	}
	public void setCrInc(String crInc) {
		this.crInc = crInc;
	}
	public String getSituacionCobro() {
		return situacionCobro;
	}
	public void setSituacionCobro(String situacionCobro) {
		this.situacionCobro = situacionCobro;
	}
	public Collection<Credito> getCreditos() {
		return creditos;
	}
	public void setCreditos(Collection<Credito> creditos) {
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
