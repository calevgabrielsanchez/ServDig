package mx.gob.imss.ctirss.delta.model.enums;

public enum EstadoNssEnum {

	ASIGNADO(1, "ASIGNADO"), RECUPERADO(2, "RECUPERADO");

	private int clave;
	private String desc;

	private EstadoNssEnum(int clave, String desc) {
		this.clave = clave;
		this.desc = desc;
	}

	/**
	 * @return the clave
	 */
	public int getClave() {
		return clave;
	}
	
	public String getDesc() {
		return desc;
	}

}
