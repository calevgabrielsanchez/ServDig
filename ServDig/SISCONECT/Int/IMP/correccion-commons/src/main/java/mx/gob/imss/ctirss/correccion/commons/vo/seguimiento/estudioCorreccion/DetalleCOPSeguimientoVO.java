package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Bidi;

public class DetalleCOPSeguimientoVO implements Serializable {

	
	private String concepto;
	private BigDecimal suertePrincipal;
	private BigDecimal actualizaciones;
	private BigDecimal recargos;
	private BigDecimal totales;
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public BigDecimal getSuertePrincipal() {
		return suertePrincipal;
	}
	public void setSuertePrincipal(BigDecimal suertePrincipal) {
		this.suertePrincipal = suertePrincipal;
	}
	public BigDecimal getActualizaciones() {
		return actualizaciones;
	}
	public void setActualizaciones(BigDecimal actualizaciones) {
		this.actualizaciones = actualizaciones;
	}
	public BigDecimal getRecargos() {
		return recargos;
	}
	public void setRecargos(BigDecimal recargos) {
		this.recargos = recargos;
	}
	public BigDecimal getTotales() {
		return totales;
	}
	public void setTotales(BigDecimal totales) {
		this.totales = totales;
	}
	
	
	
}
