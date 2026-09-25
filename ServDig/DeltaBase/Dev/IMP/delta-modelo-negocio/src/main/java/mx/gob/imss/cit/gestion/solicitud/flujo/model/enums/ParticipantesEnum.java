package mx.gob.imss.cit.gestion.solicitud.flujo.model.enums;

/**
 * Enumerador que reepresenta la lista de ParticipantesEnum
 * 
 * @author softtek
 *
 */
public enum ParticipantesEnum {

	SISTEMA(0L, "Sistema"), RESPONSABLE(1L, "Responsable"), AUTORIZADOR(2L, "Autorizador");

	/**
	 * Clave del participante
	 */
	private Long clave;
	/**
	 * Descripcion del participante
	 */
	private String descripcion;

	/**
	 * Constructor del enumerador
	 * 
	 * @param clave
	 * @param descripcion
	 */
	private ParticipantesEnum(final Long clave, final String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene la clave del participante
	 * 
	 * @return
	 */
	public Long getClave() {
		return clave;
	}

	/**
	 * Obtiene la descripcion del participante
	 * 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Metodo que busca y devuelve el tipo de participante
	 * 
	 * @param clave
	 * @return right
	 */
	public static ParticipantesEnum parse(Long clave) {
		ParticipantesEnum right = null; // Default
		for (ParticipantesEnum item : ParticipantesEnum.values()) {
			if (item.getClave().equals(clave)) {
				right = item;
				break;
			}
		}
		return right;
	}

}
