package mx.gob.imss.ctirss.delta.model.enums;

public enum DerechoTransitoEnum {
	CUOTA(1, "CUOTA"), LIBRE(2, "LIBRE");

	private int clave;
	private String descripcion;

	private DerechoTransitoEnum(int clave, String descripcion) {
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
