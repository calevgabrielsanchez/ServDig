package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.io.Serializable;

/**
 * Se agrega la clase de regimen para almacenar los datos de respuesta SAT
 * y posteriormente evaluar la regla que determina si una persona
 * puede estar adscripta a los beneficios otorgados por el regimen RIF
 * @author NOVUTEK101
 *
 */

public class Regimen implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8678773632667721569L;

	
	protected String claveRegimen;
    protected String descripcionRegimen;
    protected String fechaAltaReg;
    protected String fechaBajaReg;
    protected String fechaEfectoAReg;
    protected String fechaEfectoBReg;

    public String getClaveRegimen() {
		return claveRegimen;
	}
	public void setClaveRegimen(String claveRegimen) {
		this.claveRegimen = claveRegimen;
	}
	public String getDescripcionRegimen() {
		return descripcionRegimen;
	}
	public void setDescripcionRegimen(String descripcionRegimen) {
		this.descripcionRegimen = descripcionRegimen;
	}
	public String getFechaAltaReg() {
		return fechaAltaReg;
	}
	public void setFechaAltaReg(String fechaAltaReg) {
		this.fechaAltaReg = fechaAltaReg;
	}
	public String getFechaBajaReg() {
		return fechaBajaReg;
	}
	public void setFechaBajaReg(String fechaBajaReg) {
		this.fechaBajaReg = fechaBajaReg;
	}
	public String getFechaEfectoAReg() {
		return fechaEfectoAReg;
	}
	public void setFechaEfectoAReg(String fechaEfectoAReg) {
		this.fechaEfectoAReg = fechaEfectoAReg;
	}
	public String getFechaEfectoBReg() {
		return fechaEfectoBReg;
	}
	public void setFechaEfectoBReg(String fechaEfectoBReg) {
		this.fechaEfectoBReg = fechaEfectoBReg;
	}

    
    
    
}
