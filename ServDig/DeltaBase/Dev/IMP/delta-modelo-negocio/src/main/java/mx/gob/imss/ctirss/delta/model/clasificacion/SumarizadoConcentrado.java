package mx.gob.imss.ctirss.delta.model.clasificacion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class SumarizadoConcentrado extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private Integer idDel;
	private Integer idSubDel;
	private String del;
	private String subDel;
	private int totalRegistros;
	private int ratificado;
	private int rectificado;
	private int improcedente;
	private int baja;
	private int pendientes;
	private int arp;
	private int psp;
	private int rpc;
	
	public Integer getIdDel() {
		return idDel;
	}
	public void setIdDel(Integer idDel) {
		this.idDel = idDel;
	}
	public Integer getIdSubDel() {
		return idSubDel;
	}
	public void setIdSubDel(Integer idSubDel) {
		this.idSubDel = idSubDel;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getSubDel() {
		return subDel;
	}
	public void setSubDel(String subDel) {
		this.subDel = subDel;
	}
	public int getTotalRegistros() {
		return totalRegistros;
	}
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	public int getRatificado() {
		return ratificado;
	}
	public void setRatificado(int ratificado) {
		this.ratificado = ratificado;
	}
	public int getRectificado() {
		return rectificado;
	}
	public void setRectificado(int rectificado) {
		this.rectificado = rectificado;
	}
	public int getImprocedente() {
		return improcedente;
	}
	public void setImprocedente(int improcedente) {
		this.improcedente = improcedente;
	}
	public int getBaja() {
		return baja;
	}
	public void setBaja(int baja) {
		this.baja = baja;
	}
	public int getPendientes() {
		return pendientes;
	}
	public void setPendientes(int pendientes) {
		this.pendientes = pendientes;
	}
	public int getArp() {
		return arp;
	}
	public void setArp(int arp) {
		this.arp = arp;
	}
	public int getPsp() {
		return psp;
	}
	public void setPsp(int psp) {
		this.psp = psp;
	}
	public int getRpc() {
		return rpc;
	}
	public void setRpc(int rpc) {
		this.rpc = rpc;
	}
	
	
}
