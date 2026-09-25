package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;
import java.math.BigDecimal;

public class TotalRpEjercVO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String registroPatronal;
	private String periodo;
	private BigDecimal baseCotPagada;
	private BigDecimal difBaseCotPagada;
	private BigDecimal totalBase;
	private BigDecimal totalPorAclarar;
	private String razonable;
	private String estatus;
	
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public BigDecimal getBaseCotPagada() {
		return baseCotPagada;
	}
	public void setBaseCotPagada(BigDecimal baseCotPagada) {
		this.baseCotPagada = baseCotPagada;
	}
	public BigDecimal getDifBaseCotPagada() {
		return difBaseCotPagada;
	}
	public void setDifBaseCotPagada(BigDecimal difBaseCotPagada) {
		this.difBaseCotPagada = difBaseCotPagada;
	}
	public BigDecimal getTotalBase() {
		return totalBase;
	}
	public void setTotalBase(BigDecimal totalBase) {
		this.totalBase = totalBase;
	}
	public BigDecimal getTotalPorAclarar() {
		return totalPorAclarar;
	}
	public void setTotalPorAclarar(BigDecimal totalPorAclarar) {
		this.totalPorAclarar = totalPorAclarar;
	}
	public String getRazonable() {
		return razonable;
	}
	public void setRazonable(String razonable) {
		this.razonable = razonable;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	
}
