/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart-nez Cham-nica
 * @Proyecto: delta
 * @Archivo: Sexo.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.individuo
 * @Fecha: 13:01:04
 */
public class Sexo extends AbstractModel {

	private static final long serialVersionUID = 5319162186037290419L;
	private Integer idSexo;
	private String descripcion;
	private String genero;
	
	
	public Sexo(){
		
	}
	
	public Sexo(Integer idSexo){
		this.idSexo = idSexo;
	}
	
	public Sexo(String descripcion) {
		this.descripcion = descripcion;
	}

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
