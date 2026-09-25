package mx.gob.imss.ctirss.delta.solicitudes.sipare.service.model;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class GraficaSolicitudes extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String label;
	private int value;

	private String fecha;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
