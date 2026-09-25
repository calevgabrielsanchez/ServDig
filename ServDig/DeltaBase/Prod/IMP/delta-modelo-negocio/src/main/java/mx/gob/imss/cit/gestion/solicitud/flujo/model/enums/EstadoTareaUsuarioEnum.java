package mx.gob.imss.cit.gestion.solicitud.flujo.model.enums;

/**
 * 
 * @author softtek
 *
 */
public enum EstadoTareaUsuarioEnum {

	DISPONIBLE(1, "Disponible"), ACTIVA(2, "Activa"), COMPLETADA(3, "Completada"), REASIGNADA(4,
			"Reasignada"), ABORTADA(5, "Abortada");

	/**
	 * Clave de la tarea
	 */
	private long clave;
	/**
	 * Descripcion de la tarea
	 */
	private String descripcion;

	/**
	 * Constructor del enumerador
	 * 
	 * @param clave
	 * @param descripcion
	 */
	private EstadoTareaUsuarioEnum(final long clave, final String descripcion) {
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
	 * Metodo que busca y devuelve el tipo de estado de la tarea del usuario
	 * 
	 * @param clave
	 * @return right
	 */
	public static EstadoTareaUsuarioEnum parse(long clave) {
		EstadoTareaUsuarioEnum right = null; // Default
		for (EstadoTareaUsuarioEnum item : EstadoTareaUsuarioEnum.values()) {
			if (item.getClave() == clave) {
				right = item;
				break;
			}
		}
		return right;
	}

}
