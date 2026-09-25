package mx.gob.imss.digital.modelo.persona;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parentesco", namespace = "http://mx.gob.imss.digital.modelo.persona")
@XmlRootElement(name = "parentesco", namespace = "http://mx.gob.imss.digital.modelo.persona")
public class Parentesco implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idParentesco;
	private String descripcion;

	public Long getIdParentesco() {
		return idParentesco;
	}

	public void setIdParentesco(Long idParentesco) {
		this.idParentesco = idParentesco;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
