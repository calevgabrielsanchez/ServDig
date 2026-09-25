package mx.gob.imss.distss.gestion.cuestionario.modelo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoCuestionario extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private int clave;
	private String descripcion;

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

}
