package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoVialidad", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "tipoVialidad", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class TipoVialidad implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4677988110334788593L;

	private Integer clave;
	
	private String descripcion;

	/**
	 * @return the clave
	 */
	public Integer getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(Integer clave) {
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

	
}
