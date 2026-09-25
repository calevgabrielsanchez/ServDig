package mx.gob.imss.distss.gestion.cuestionario.modelo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoRespuesta extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	private int clave;
	private String descripcion;
	private String elemento;

	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getElemento() {
		return elemento;
	}

	public void setElemento(String elemento) {
		this.elemento = elemento;
	}

}
