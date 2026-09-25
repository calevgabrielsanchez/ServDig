package mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro;

public enum EstadoFolioCertificacionEnum {

	INACTIVO(1, "INACTIVO"), ACTIVO(2, "ACTIVO"), BAJA(3, "BAJA");

	private int id;
	private String desc;

	private EstadoFolioCertificacionEnum(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}
}