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
@XmlType(name = "delegacion", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "delegacion", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class Delegacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3028779998561296188L;

	private Long id;
	private String clave;
	private String descripcion;
	protected Integer ciz;
	
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
	public Integer getCiz() {
		return ciz;
	}
	public void setCiz(Integer ciz) {
		this.ciz = ciz;
	}
	
	
	

}
