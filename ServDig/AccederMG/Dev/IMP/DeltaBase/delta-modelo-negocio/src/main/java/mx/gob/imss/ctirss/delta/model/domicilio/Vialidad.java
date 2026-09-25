/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:Vialidad.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.domicilio
 *  @Fecha:02/04/2012
 */
package mx.gob.imss.ctirss.delta.model.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class Vialidad extends AbstractModel {
	
	
	
	private Integer clave;
	
	private String nombre;
		
	private TipoVialidad tipoVialidad;
	
	
	
	/**
	 * Constructor default
	 */
	public Vialidad(){
		
	}
	
	/**
	 * 
	 * @param cveVia
	 * @param nomVia
	 */
	public Vialidad(Integer cveVia , String nomVia , Integer cveTipoVial , String nomTipoVial){
		this.clave = cveVia;
		this.nombre = nomVia;
		TipoVialidad tipoVialidad =  new TipoVialidad();
		tipoVialidad.setClave(cveTipoVial);
		tipoVialidad.setDescripcion(nomTipoVial);
		this.setTipoVialidad(tipoVialidad);
		
	}
	
	
	

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
	 * @return the tipoVialidad
	 */
	public TipoVialidad getTipoVialidad() {
		return tipoVialidad;
	}

	/**
	 * @param tipoVialidad the tipoVialidad to set
	 */
	public void setTipoVialidad(TipoVialidad tipoVialidad) {
		this.tipoVialidad = tipoVialidad;
	}	
	
	
	

}
