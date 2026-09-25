package mx.gob.imss.digital.modelo.sindo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vigenciaContVoluntaria", namespace = "http://mx.gob.imss.digital.modelo.sindo")
@XmlRootElement(name = "vigenciaContVoluntaria", namespace = "http://mx.gob.imss.digital.modelo.sindo")
public class VigenciaContVoluntaria implements Serializable {
	private static final long serialVersionUID = -1201969241653672316L;
	private String claveError;
	private String mensajeError;
	private ResultadoVigenciaContVoluntaria modalidad40;

	public String getClaveError() {
		return claveError;
	}

	public void setClaveError(String claveError) {
		this.claveError = claveError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public ResultadoVigenciaContVoluntaria getModalidad40() {
		return modalidad40;
	}

	public void setModalidad40(ResultadoVigenciaContVoluntaria modalidad40) {
		this.modalidad40 = modalidad40;
	}
}
