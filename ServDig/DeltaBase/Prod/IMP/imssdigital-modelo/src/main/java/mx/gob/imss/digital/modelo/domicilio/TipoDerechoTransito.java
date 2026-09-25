package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoDerechoTransito", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "tipoDerechoTransito", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class TipoDerechoTransito implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4988155674994438250L;
	private Long clave;
	private String descripcion;
	public Long getClave() {
		return clave;
	}
	public void setClave(Long clave) {
		this.clave = clave;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
