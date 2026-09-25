package mx.gob.imss.cit.ws.externo.boveda.docuemntal.v3.consulta.util;

public enum TipoDocumentalBovedaEnum {

	TIPO_DOCUMENTAL_TSPI(1, "D:tspi:tspi_padre"), TIPO_DOCUMENTAL_CDA(2, "D:cda:imss");

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
	private TipoDocumentalBovedaEnum(final long clave, final String descripcion) {
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
	public static TipoDocumentalBovedaEnum parse(long clave) {
		TipoDocumentalBovedaEnum right = null; // Default
		for (TipoDocumentalBovedaEnum item : TipoDocumentalBovedaEnum.values()) {
			if (item.getClave() == clave) {
				right = item;
				break;
			}
		}
		return right;
	}
	

	
}
