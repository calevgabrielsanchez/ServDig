package mx.gob.imss.ctirss.delta.model.enums;

public enum CausaBajaPatronEnum {
	
	
	
	HUELGA(1, "HUELGA"), 
	BAJA(2, "BAJA"),
	FECHA_DE_HUELGA(3, "0001/01/01"),
	ART_251_LEY_IMSS(7,"ARTICULO 251 LEY IMSS");

	private int clave;
	private String descripcion;

	public int getClave() {
		return clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	private CausaBajaPatronEnum(int clave, String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

}
