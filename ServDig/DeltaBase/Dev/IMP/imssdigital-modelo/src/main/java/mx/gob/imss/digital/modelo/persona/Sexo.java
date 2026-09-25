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
 * @Archivo: Sexo.java
 * @Fecha: 13:01:04
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sexo", namespace = "http://mx.gob.imss.digital.modelo.persona")
@XmlRootElement(name = "sexo", namespace = "http://mx.gob.imss.digital.modelo.persona")
public class Sexo implements Serializable {

    /**
     * Serial
     */
	private static final long serialVersionUID = 5319162186037290419L;
	/**
	 * Id del sexo
	 */
	private Integer idSexo;
	/**
	 * Descripcion
	 */
	private String descripcion;
	/**
	 * genero
	 */
	private String genero;
	
	

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the idSexo
	 */
	public Integer getIdSexo() {
		return idSexo;
	}

	/**
	 * @param idSexo
	 *            the idSexo to set
	 */
	public void setIdSexo(Integer idSexo) {
		this.idSexo = idSexo;
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
