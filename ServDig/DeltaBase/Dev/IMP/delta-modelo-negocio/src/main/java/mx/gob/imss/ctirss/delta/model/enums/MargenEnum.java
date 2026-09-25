package mx.gob.imss.ctirss.delta.model.enums;

public enum MargenEnum {
	DERECHO(1, "DERECHO"), IZQUIERDO(2, "IZQUIERDO");

	private int clave;
	private String descripcion;

	private MargenEnum(int clave, String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

	public int getClave() {
		return clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
