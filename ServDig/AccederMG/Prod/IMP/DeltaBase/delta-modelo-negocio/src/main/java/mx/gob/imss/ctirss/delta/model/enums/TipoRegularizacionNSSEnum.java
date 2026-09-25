package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Enumeraci&oacute;n que incluye los Tipos de certificaci&oacute;n permitidos en una 
 * correcci&oacute;n de datos del asegurado.
 * 
 * @author softtek
 *
 */
public enum TipoRegularizacionNSSEnum {
	
	CORRECCION_NOMBRE(1),
	CORRECCION_DATOS_ESTADISTICOS(2),
	REGULARIZAR_CUENTA_INDIVIDUAL(3),
	CANCELADO_POR_DUPLICIDAD(4),
	NO_EXISTE_EN_CANASE(5),
	CORRESPONA_UN_HOMONIMO(6),
	CORRESPONA_OTRO_ASEGURADO(7);
	
	/**
	 * Identificador del Tipo de certificaci&oacute;n en BDTU
	 */
	private Long id;
	
	/**
	 * Constructor de la enumeraci&oacute;n 
	 * @param id identificador del Tipo de certificaci&oacute;n
	 */
	private TipoRegularizacionNSSEnum(long id) {
		this.id = id;
	}
	
	/**
     * Obtiene el identificador del Tipo de certificaci&oacute;n
     * @return
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Obtiene el Tipo de certificaci&oacute;n por su identificador
     * @param id del Tipo de certificaci&oacute;n 
     * @return el enum asociado o null si no se encuentra
     */
    public static TipoRegularizacionNSSEnum fromId(Long id) {
    	TipoRegularizacionNSSEnum[] tipos = values();
        for(TipoRegularizacionNSSEnum tipo : tipos) {
            if (tipo.getId() == id) {
                return tipo;
            }
        }
        return null;
    }

}
