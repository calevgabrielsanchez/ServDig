  /**
 * 
 */
package mx.imss.ctirss.bean;

import java.io.Serializable;
import java.math.BigDecimal;

//probando

/**
 * @author Juan Manuel Lopez Lozano
 * @since 08/10/2011
 *
 */
public class SelectBean implements Serializable{
	
	private String descripcion;
	private String id;
	
	public SelectBean(){
		
	}
	
	public SelectBean(String id , String descripcion){
		this.id = id.toString();
		this.descripcion = descripcion;
	}

	public SelectBean(Integer id , String descripcion){
		this.id = id.toString();
		this.descripcion = descripcion;
	}
	
	public SelectBean(long id , String descripcion){
		this.id = String.valueOf(id);
		this.descripcion = descripcion;
	}	
	
	public SelectBean(BigDecimal id , String descripcion){
		this.id = String.valueOf(id);
		this.descripcion = descripcion;
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

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**  
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	public void setId2(String id) {
		this.id = id;
	}
	
}
