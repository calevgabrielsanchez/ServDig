package mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado;

import java.io.Serializable;
import java.math.BigDecimal;

public class DatosGeneralesAsegurado implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4571864490572481143L;
	private String nombre;

	private String primerApellido;
	private String segundoApellido;
 
 	private String fechaNacimiento;
	private String curp;
    private String nss;
    private String rfc;
    private BigDecimal cveIdAsignacionNss;
    private BigDecimal cveIdPersona;
    
	public String getNombre() {
		
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	
	
	
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public BigDecimal getCveIdAsignacionNss() {
		return cveIdAsignacionNss;
	}
	public void setCveIdAsignacionNss(BigDecimal cveIdAsignacionNss) {
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}
	public BigDecimal getCveIdPersona() {
		return cveIdPersona;
	}
	public void setCveIdPersona(BigDecimal cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}
	
	   

}
