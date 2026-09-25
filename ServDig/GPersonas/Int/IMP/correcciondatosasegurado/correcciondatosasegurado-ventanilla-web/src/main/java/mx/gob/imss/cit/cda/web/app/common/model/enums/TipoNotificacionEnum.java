package mx.gob.imss.cit.cda.web.app.common.model.enums;

/**
 * Enumerador que representa los tipos de notificaciones por correo
 * 
 * @author softtek
 *
 */
public enum TipoNotificacionEnum {
	
	CANCELACION(1), RECHAZO(2), SOLICITARINFO(3), REASIGNACION(4);
	
	/**
	 * Clave de la notificacion
	 */
	private long clave;
	
	/**
	 * Constructor del enumerador
	 * 
	 * @param clave
	 */
	private TipoNotificacionEnum(final long clave){
		this.clave = clave;
	}

	/**
	 * 
	 * @return clave
	 */
	public long getClave() {
		return clave;
	}
	
	

}
