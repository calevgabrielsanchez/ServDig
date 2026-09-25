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
@XmlType(name = "tipoAsentamiento", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "tipoAsentamiento", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class TipoAsentamiento implements Serializable {

	
	/**
     * Serial version UID
     */
	private static final long serialVersionUID = 1L;
    /**
     * Identificador del tipo asentamiento
     */
	private Long clave;
    /**
     * Descripcion tipo asentamiento
     */
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
