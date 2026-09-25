package mx.gob.imss.consulta.tramites.business;

import java.io.Serializable;

public class Respuesta implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer codigoError;
	private String mensajeError;

	public Integer getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(Integer codigoError) {
		this.codigoError = codigoError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

}
