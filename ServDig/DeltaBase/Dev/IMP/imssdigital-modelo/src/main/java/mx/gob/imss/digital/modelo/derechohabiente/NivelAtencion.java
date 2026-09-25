package mx.gob.imss.digital.modelo.derechohabiente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nivelAtencion", namespace = "mx.gob.imss.digital.modelo.derechohabiente")
@XmlRootElement(name = "nivelAtencion", namespace = "mx.gob.imss.digital.modelo.derechohabiente")
public class NivelAtencion implements Serializable {
	private static final long serialVersionUID = 1L;
	private long idNivelAtencion;
	private String descripcion;

	public long getIdNivelAtencion() {
		return idNivelAtencion;
	}

	public void setIdNivelAtencion(long idNivelAtencion) {
		this.idNivelAtencion = idNivelAtencion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
