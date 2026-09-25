/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart-nez Cham-nica
 * @Proyecto: delta
 * @Archivo: Division.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 * @Fecha: 17:32:51
 */
@XmlRootElement
public class Division extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2876338001840976123L;
	private Long id;
	private String descripcion;
	private Boolean activo;
	private String numDivision;
	
	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

	public String getNumDivision() {
		return numDivision;
	}

	public void setNumDivision(String numDivision) {
		this.numDivision = numDivision;
	}
	
	
}
