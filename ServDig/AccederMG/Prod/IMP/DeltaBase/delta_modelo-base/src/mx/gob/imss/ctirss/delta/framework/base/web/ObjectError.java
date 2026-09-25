/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:ObjectError.java
 *  @Paquete:mx.gob.imss.ctirss.delta.framework.base.web
 *  @Fecha:07/02/2012
 */
package mx.gob.imss.ctirss.delta.framework.base.web;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class ObjectError extends AbstractModel {
	
	/*
	 * Nombre del campo validado
	 */
	private String campo;
	
	
	/*
	 * Mensaje con la descripcion del error
	 */
	private String mensaje;


	/**
	 * @return the campo
	 */
	public String getCampo() {
		return campo;
	}


	/**
	 * @param campo the campo to set
	 */
	public void setCampo(String campo) {
		this.campo = campo;
	}


	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}


	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	

}
