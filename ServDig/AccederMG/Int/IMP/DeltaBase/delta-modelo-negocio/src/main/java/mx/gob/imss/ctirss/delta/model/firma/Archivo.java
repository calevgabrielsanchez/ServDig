package mx.gob.imss.ctirss.delta.model.firma;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Archivo extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -686283767401125761L;
	String id;
	String nombre;
	String buffer;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getBuffer() {
		return buffer;
	}
	
	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
