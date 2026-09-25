package mx.gob.imss.ctirss.delta.model.enums;

public enum CarreteraAdministracionEnum {
	ESTATAL(1, "ESTATAL"),
	FEDERAL(2, "FEDERAL"),
	MUNICIPAL(3, "MUNICIPAL"),
	PARTICULAR(4, "PARTICULAR");

	private int clave;
	private String descripcion;

	private CarreteraAdministracionEnum(int clave, String descripcion) {
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
