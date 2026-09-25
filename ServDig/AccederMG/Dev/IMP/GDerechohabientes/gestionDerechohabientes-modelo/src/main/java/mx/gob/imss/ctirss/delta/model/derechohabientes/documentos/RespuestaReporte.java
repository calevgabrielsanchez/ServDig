package mx.gob.imss.ctirss.delta.model.derechohabientes.documentos;

import java.io.Serializable;

public class RespuestaReporte implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1251548206420002074L;
	private String respuesta;
	private Integer numRespuesta;

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Integer getNumRespuesta() {
		return numRespuesta;
	}

	public void setNumRespuesta(Integer numRespuesta) {
		this.numRespuesta = numRespuesta;
	}

}
