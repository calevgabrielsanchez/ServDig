package mx.gob.imss.digital.modelo.sindo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vigenciaSeguroFamiliar", namespace = "http://mx.gob.imss.digital.modelo.sindo")
@XmlRootElement(name = "vigenciaSeguroFamiliar", namespace = "http://mx.gob.imss.digital.modelo.sindo")
public class VigenciaSeguroFamiliar implements Serializable {
	private static final long serialVersionUID = -6269991384615675674L;
	private int claveError;
	private String mensajeError;
	private ResultadoVigenciaSeguroFamiliar resultado;

	public int getClaveError() {
		return claveError;
	}

	public void setClaveError(int claveError) {
		this.claveError = claveError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public ResultadoVigenciaSeguroFamiliar getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoVigenciaSeguroFamiliar resultado) {
		this.resultado = resultado;
	}
}
