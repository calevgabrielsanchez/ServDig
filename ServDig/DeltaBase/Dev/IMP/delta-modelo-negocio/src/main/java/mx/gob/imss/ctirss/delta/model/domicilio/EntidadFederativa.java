/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:EntidadFederativa.java
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
public class EntidadFederativa extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7569803133541238434L;
	//Base
	private String clave;
	private String nombre;
	private List<Municipio> municipios;

	//Derechohabiente
    private Long idRenapo;
    private String claveRenapo;

    
    
	public Long getIdRenapo() {
		return idRenapo;
	}

	public void setIdRenapo(Long idRenapo) {
		this.idRenapo = idRenapo;
	}

	public String getClaveRenapo() {
		return claveRenapo;
	}

	public void setClaveRenapo(String claveRenapo) {
		this.claveRenapo = claveRenapo;
	}

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
	 * @return the municipios
	 */
	public List<Municipio> getMunicipios() {
		return municipios;
	}

	/**
	 * @param municipios the municipios to set
	 */
	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}

	@Override
	public String toString() {
		return "EntidadFederativa [clave=" + clave + ", municipios="
				+ municipios + ", nombre=" + nombre + ", getClave()="
				+ getClave() + ", getMunicipios()=" + getMunicipios()
				+ ", getNombre()=" + getNombre() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
	

}
