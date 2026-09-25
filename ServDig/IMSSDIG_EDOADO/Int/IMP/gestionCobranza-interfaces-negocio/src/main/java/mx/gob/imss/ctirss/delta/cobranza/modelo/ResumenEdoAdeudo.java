package mx.gob.imss.ctirss.delta.cobranza.modelo;

import java.io.Serializable;

public class ResumenEdoAdeudo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8596256720963955401L;
	
	private Boolean existeAdeudo;
	private double adeudoImss;
	private double adeudoRcv;
	private double totalAdeudo;
	
	public Boolean getExisteAdeudo() {
		return existeAdeudo;
	}
	
	public void setExisteAdeudo(Boolean existeAdeudo) {
		this.existeAdeudo = existeAdeudo;
	}
	
	public double getAdeudoImss() {
		return adeudoImss;
	}
	
	public void setAdeudoImss(double adeudoImss) {
		this.adeudoImss = adeudoImss;
	}
	
	public double getAdeudoRcv() {
		return adeudoRcv;
	}
	
	public void setAdeudoRcv(double adeudoRcv) {
		this.adeudoRcv = adeudoRcv;
	}
	
	public double getTotalAdeudo() {
		return totalAdeudo;
	}
	
	public void setTotalAdeudo(double totalAdeudo) {
		this.totalAdeudo = totalAdeudo;
	}
}
