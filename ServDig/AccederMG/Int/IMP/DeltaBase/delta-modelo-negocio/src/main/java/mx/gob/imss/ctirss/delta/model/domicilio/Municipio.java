/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:Municipio.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.domicilio
 *  @Fecha:02/04/2012
 */
package mx.gob.imss.ctirss.delta.model.domicilio;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class Municipio extends AbstractModel {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7762864607084703065L;

	private String clave;
	private String nombre;
	private EntidadFederativa entidadFederativa;
	private List<Localidad> localidades;

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the entidadFederativa
	 */
	public EntidadFederativa getEntidadFederativa() {
		return entidadFederativa;
	}

	/**
	 * @param entidadFederativa the entidadFederativa to set
	 */
	public void setEntidadFederativa(EntidadFederativa entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	/**
	 * @return the localidades
	 */
	public List<Localidad> getLocalidades() {
		return localidades;
	}

	/**
	 * @param localidades the localidades to set
	 */
	public void setLocalidades(List<Localidad> localidades) {
		this.localidades = localidades;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Municipio [clave=" + clave + ", entidadFederativa="
				+ entidadFederativa + ", localidades=" + localidades
				+ ", nombre=" + nombre + "]";
	}
	
	
	

}
