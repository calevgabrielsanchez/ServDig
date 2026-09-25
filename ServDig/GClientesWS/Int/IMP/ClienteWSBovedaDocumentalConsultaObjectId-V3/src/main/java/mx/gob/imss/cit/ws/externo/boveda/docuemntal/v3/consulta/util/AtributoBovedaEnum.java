package mx.gob.imss.cit.ws.externo.boveda.docuemntal.v3.consulta.util;

public enum AtributoBovedaEnum {
	
	
	ATRIBUTO_ID(1, "id"), 
	ATRIBUTO_DOCUMENTO(2, "documento"), 
	ATRIBUTO_RUTA(3, "ruta"), 
	ATRIBUTO_TIPO_DOCUMENTO(4, "tipo"), 
	ATRIBUTO_TIPO_DOCUMENTAL(5, "tipoDocumental"),
	ATRIBUTO_NOMBRE_DOCUMENTO(6, "name"),
	ATRIBUTO_FOLIO_TRAMITE(7, "folioTramite"),
	ATRIBUTO_IDENTIFICADOR(7, "identificador");

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
	private AtributoBovedaEnum(final long clave, final String descripcion) {
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
	public static AtributoBovedaEnum parse(long clave) {
		AtributoBovedaEnum right = null; // Default
		for (AtributoBovedaEnum item : AtributoBovedaEnum.values()) {
			if (item.getClave() == clave) {
				right = item;
				break;
			}
		}
		return right;
	}
	

}
