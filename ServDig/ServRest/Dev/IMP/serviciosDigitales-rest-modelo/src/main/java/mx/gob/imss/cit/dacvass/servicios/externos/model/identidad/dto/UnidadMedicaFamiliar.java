package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

public class UnidadMedicaFamiliar implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2439636799699521575L;
	
	private String cvePresupuestalUMF;
	private String descUMF;
	private String numUMF;
	
	
	public String getCvePresupuestalUMF() {
		return cvePresupuestalUMF;
	}
	public void setCvePresupuestalUMF(String cvePresupuestalUMF) {
		this.cvePresupuestalUMF = cvePresupuestalUMF;
	}
	public String getDescUMF() {
		return descUMF;
	}
	public void setDescUMF(String descUMF) {
		this.descUMF = descUMF;
	}
	public String getNumUMF() {
		return numUMF;
	}
	public void setNumUMF(String numUMF) {
		this.numUMF = numUMF;
	}

}
