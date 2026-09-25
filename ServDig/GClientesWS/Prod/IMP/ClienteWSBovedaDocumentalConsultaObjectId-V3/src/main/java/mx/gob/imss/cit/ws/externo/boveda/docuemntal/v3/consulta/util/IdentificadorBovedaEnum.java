package mx.gob.imss.cit.ws.externo.boveda.docuemntal.v3.consulta.util;



public enum IdentificadorBovedaEnum {
	
	IDENTIFICADOR_TSPI_PRUEBAS(1, "tspiPruebas"), IDENTIFICADOR_TSPI(2, "tspi");

	/**
	 * Clave del estado
	 */
	private long clave;
	/**
	 * Descripcion del estado
	 */
	private String descripcion;

	/**
	 * Constructor del enumerador
	 * 
	 * @param clave
	 * @param descripcion
	 */
	private IdentificadorBovedaEnum(final long clave, final String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

	/**
	 * 
	 * @return clave
	 */
	public long getClave() {
		return clave;
	}

	/**
	 * 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Metodo que busca y devuelve el tipo de estado de la instancia
	 * 
	 * @param clave
	 * @return
	 */
	public static IdentificadorBovedaEnum parse(long clave) {
		IdentificadorBovedaEnum right = null; // Default
		for (IdentificadorBovedaEnum item : IdentificadorBovedaEnum.values()) {
			if (item.getClave() == clave) {
				right = item;
				break;
			}
		}
		return right;
	}
	

}
