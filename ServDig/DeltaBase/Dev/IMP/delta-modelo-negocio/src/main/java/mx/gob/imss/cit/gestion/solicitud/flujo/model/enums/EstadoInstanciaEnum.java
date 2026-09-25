package mx.gob.imss.cit.gestion.solicitud.flujo.model.enums;

/**
 * Enumerador que representa los estados de las instancias
 * 
 * @author softtek
 *
 */
public enum EstadoInstanciaEnum {

	ACTIVA(1, "Activa"), TERMINADA(2, "Terminada");

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
	private EstadoInstanciaEnum(final long clave, final String descripcion) {
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
	public static EstadoInstanciaEnum parse(long clave) {
		EstadoInstanciaEnum right = null; // Default
		for (EstadoInstanciaEnum item : EstadoInstanciaEnum.values()) {
			if (item.getClave() == clave) {
				right = item;
				break;
			}
		}
		return right;
	}

}
