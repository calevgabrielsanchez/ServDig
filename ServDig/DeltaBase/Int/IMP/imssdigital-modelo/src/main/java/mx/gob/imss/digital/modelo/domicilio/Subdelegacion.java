/**
 * 
 */
package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @author User
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subdelegacion", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "subdelegacion", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class Subdelegacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7419829087359791787L;
	
	private Long id;
	private String clave;
	private String descripcion;
	private Delegacion delegacion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Delegacion getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(Delegacion delegacion) {
		this.delegacion = delegacion;
	}
	
}
