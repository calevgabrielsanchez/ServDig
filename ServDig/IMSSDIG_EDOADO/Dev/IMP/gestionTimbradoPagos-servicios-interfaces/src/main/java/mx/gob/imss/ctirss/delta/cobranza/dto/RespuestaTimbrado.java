package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;

public class RespuestaTimbrado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3095979348681002670L;
	private String comprobanteTimbrado;
	private Integer codigoEstatus;
	private String descripcionError;
	private ErrorValidacion[] erroresValidacion;
	
	public String getComprobanteTimbrado() {
		return comprobanteTimbrado;
	}
	public void setComprobanteTimbrado(String comprobanteTimbrado) {
		this.comprobanteTimbrado = comprobanteTimbrado;
	}
	public Integer getCodigoEstatus() {
		return codigoEstatus;
	}
	public void setCodigoEstatus(Integer codigoEstatus) {
		this.codigoEstatus = codigoEstatus;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	public ErrorValidacion[] getErroresValidacion() {
		return erroresValidacion;
	}
	public void setErroresValidacion(ErrorValidacion[] erroresValidacion) {
		this.erroresValidacion = erroresValidacion;
	}
	
}
