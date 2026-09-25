package mx.gob.imss.ctirss.delta.model.enums;

public enum EstadoAdministracionEnum {

	NUEVO(1), MODIFICADO(2), ELIMINADO(3);

	private int clave;

	private EstadoAdministracionEnum(int clave) {
		this.clave = clave;
	}

	/**
	 * @return the clave
	 */
	public int getClave() {
		return clave;
	}

}
