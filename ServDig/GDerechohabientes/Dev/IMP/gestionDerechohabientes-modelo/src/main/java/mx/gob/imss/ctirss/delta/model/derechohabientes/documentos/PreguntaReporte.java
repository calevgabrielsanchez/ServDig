/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.derechohabientes.documentos;

import java.io.Serializable;

/**
 * @author ghdolores
 * 
 */
public class PreguntaReporte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7273444993946538300L;
	private String preguntaPrimeraPersona;
	private String preguntaTerceraPersona;
	private String numPregunta;
	private String respuesta;
	private String numRespuesta;


	public String getPreguntaPrimeraPersona() {
		return preguntaPrimeraPersona;
	}

	public void setPreguntaPrimeraPersona(String preguntaPrimeraPersona) {
		this.preguntaPrimeraPersona = preguntaPrimeraPersona;
	}

	public String getPreguntaTerceraPersona() {
		return preguntaTerceraPersona;
	}

	public void setPreguntaTerceraPersona(String preguntaTerceraPersona) {
		this.preguntaTerceraPersona = preguntaTerceraPersona;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getNumRespuesta() {
		return numRespuesta;
	}

	public void setNumRespuesta(String numRespuesta) {
		this.numRespuesta = numRespuesta;
	}

	public void setNumPregunta(String numPregunta) {
		this.numPregunta = numPregunta;
	}

	public String getNumPregunta() {
		return numPregunta;
	}

}
