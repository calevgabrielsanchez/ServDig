package mx.gob.imss.cit.cda.web.app.reportes.model;

import java.util.List;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

public class VariablesReportes extends BaseModel {

	private static final long serialVersionUID = -5538939021804649871L;

	//estadistica
	private String descripcion;
	private String cantidad;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

}
