/**
 * 
 */
package mx.gob.imss.digital.modelo.persona;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart-nez Cham-nica
 * @Proyecto: delta
 * @Archivo: EstadoCivil.java
 * @Fecha: 13:06:07
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estadoCivil", namespace = "http://mx.gob.imss.digital.modelo.persona")
@XmlRootElement(name = "estadoCivil", namespace = "http://mx.gob.imss.digital.modelo.persona")
public class EstadoCivil implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -4769338214556479392L;
	/**
	 * identificador del estado civil
	 */
	private Integer idEstadoCivil;
	/**
	 * descripcion
	 */
	private String descripcion;

	/**
	 * @return the idEstadoCivil
	 */
	public Integer getIdEstadoCivil() {
		return idEstadoCivil;
	}

	/**
	 * @param idEstadoCivil
	 *            the idEstadoCivil to set
	 */
	public void setIdEstadoCivil(Integer idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
