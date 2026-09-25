/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:Asentamiento.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.domicilio
 *  @Fecha:02/04/2012
 */
package mx.gob.imss.ctirss.delta.model.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class Asentamiento extends AbstractModel {

	private String clave;
	
	private String nombre;
	
	private Localidad localidad;
	
	private Municipio municipio;
	
	public Municipio getMunicipio() {
		return municipio;
	}


	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}


	private CodigoPostal codigoPostal;
	
	private TipoAsentamiento tipoAsentamiento;
	
	private long periodo;
	
	/*Constructor Minimo*/
	public Asentamiento(){
		
	}
	
	
	/*
	 * Constructor de un asentamiento con la localidad, municipio y entidad federativa cada uno con 
	 * sus claves correspondientes. 
	 */
	public Asentamiento(String clave, String nombre, String claveEntidad , String claveMunicipio){
		
		//Datos del asentamiento
		this.clave = clave;
		this.nombre = nombre;
		this.setMunicipio(new Municipio());
		this.getMunicipio().setClave(claveMunicipio);
		this.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		this.getMunicipio().getEntidadFederativa().setClave(claveEntidad);
		
		// Datos de la localidad
		this.setLocalidad( new Localidad());
		
		//this.getLocalidad().setClave(claveLocalidad);
		//this.localidad.setNombre(nombreLocalidad);
		
		//Municipio
		this.localidad.setMunicipio(new Municipio());
		this.localidad.getMunicipio().setClave(claveMunicipio);
		//Entidad Federativa
		
		this.localidad.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		this.localidad.getMunicipio().getEntidadFederativa().setClave(claveEntidad);
		
	}
	
	/*
	 * Constructor de un asentamiento con la localidad, municipio y entidad federativa cada uno con 
	 * sus claves correspondientes. 
	 */
	public Asentamiento(String clave, String nombre, String claveEntidad , String claveMunicipio, String nomEstado, String nomMunicipio){
		
		//Datos del asentamiento
		this.clave = clave;
		this.nombre = nombre;
		this.setMunicipio(new Municipio());
		this.getMunicipio().setClave(claveMunicipio);
		this.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		this.getMunicipio().getEntidadFederativa().setClave(claveEntidad);
		
		// Datos de la localidad
		this.setLocalidad( new Localidad());
		
		//this.getLocalidad().setClave(claveLocalidad);
		//this.localidad.setNombre(nombreLocalidad);
		
		//Municipio
		this.localidad.setMunicipio(new Municipio());
		this.localidad.getMunicipio().setClave(claveMunicipio);
		this.localidad.getMunicipio().setNombre(nomMunicipio);		
		//Entidad Federativa
		this.localidad.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		this.localidad.getMunicipio().getEntidadFederativa().setClave(claveEntidad);
		this.localidad.getMunicipio().getEntidadFederativa().setNombre(nomEstado);
		
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
	 * @return the localidad
	 */
	
	public Localidad getLocalidad() {
		return localidad;
	}
	
	/**
	 * @param localidad the localidad to set
	 */
	
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	
	/**
	 * @return the codigoPostal
	 */
	public CodigoPostal getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(CodigoPostal codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * @return the tipoAsentamiento
	 */
	public TipoAsentamiento getTipoAsentamiento() {
		return tipoAsentamiento;
	}

	/**
	 * @param tipoAsentamiento the tipoAsentamiento to set
	 */
	public void setTipoAsentamiento(TipoAsentamiento tipoAsentamiento) {
		this.tipoAsentamiento = tipoAsentamiento;
	}


	@Override
	public String toString() {
		return "Asentamiento [clave=" + clave + ", codigoPostal="
				+ codigoPostal + ", localidad=" + localidad + ", nombre="
				+ nombre + ", tipoAsentamiento=" + tipoAsentamiento + "]";
	}


	/**
	 * @return the periodo
	 */
	public long getPeriodo() {
		return periodo;
	}


	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(long periodo) {
		this.periodo = periodo;
	}
	
	
	
	
	
}