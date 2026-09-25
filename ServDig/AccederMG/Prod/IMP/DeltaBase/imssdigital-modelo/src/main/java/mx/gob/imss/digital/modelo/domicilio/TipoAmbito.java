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
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoAmbito", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "tipoAmbito", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class TipoAmbito implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8455383050876676159L;
	
	private Long clave;
	
	private String descripcion;

	/**
	 * @return the clave
	 */
	public Long getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(Long clave) {
		this.clave = clave;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
