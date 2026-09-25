package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;

public class ErrorValidacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2097193218810847386L;
	private Integer codigoError;
	private String descripcionError;
	
	
	public Integer getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(Integer codigoError) {
		this.codigoError = codigoError;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	
	
	
}
