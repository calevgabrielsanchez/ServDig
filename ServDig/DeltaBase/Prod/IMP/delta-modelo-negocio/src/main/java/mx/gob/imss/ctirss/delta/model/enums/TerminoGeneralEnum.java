package mx.gob.imss.ctirss.delta.model.enums;

public enum TerminoGeneralEnum {
	CARRETERA(1, "CARRETERA"),
	CAMINO(2, "CAMINO"),
	TERRACERIA(3, "TERRACERIA"),
	BRECHA(4, "BRECHA"),
	VEREDA(5, "VEREDA");

	private int clave;
	private String descripcion;

	private TerminoGeneralEnum(int clave, String descripcion) {
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
