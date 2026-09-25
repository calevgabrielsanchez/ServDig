/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:Localidad.java
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
public class Localidad extends AbstractModel {
	
	
	private String clave;
	
	private String nombre;
	
	private Municipio municipio;
	
	private List<Asentamiento> asentamientos;

	
	

	/*
	 * Constructor de un asentamiento con la localidad, municipio y entidad federativa cada uno con 
	 * sus claves correspondientes. 
	 */
	public Localidad(String clave, String nombre, String claveEntidad , String claveMunicipio){
		
		//Datos del asentamiento
		this.clave = clave;
		this.nombre = nombre;
		this.setMunicipio(new Municipio());
		this.getMunicipio().setClave(claveMunicipio);
		this.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		this.getMunicipio().getEntidadFederativa().setClave(claveEntidad);
		
	}
	
	public Localidad(){};
	

	
	
	
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
	 * @return the municipio
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the asentamientos
	 */
	public List<Asentamiento> getAsentamientos() {
		return asentamientos;
	}

	/**
	 * @param asentamientos the asentamientos to set
	 */
	public void setAsentamientos(List<Asentamiento> asentamientos) {
		this.asentamientos = asentamientos;
	}

	@Override
	public String toString() {
		return "Localidad [asentamientos=" + asentamientos + ", clave=" + clave
				+ ", municipio=" + municipio + ", nombre=" + nombre + "]";
	}
	
	
	

}