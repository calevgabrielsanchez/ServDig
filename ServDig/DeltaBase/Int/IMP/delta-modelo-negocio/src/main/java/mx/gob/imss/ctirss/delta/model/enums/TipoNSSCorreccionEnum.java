package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Enumeraci&oacute;n que incluye los Tipos de NSS para una aclaraci&oacute;n permitidos en una 
 * correcci&oacute;n de datos del asegurado.
 * @author softtek
 *
 */
public enum TipoNSSCorreccionEnum {
	
	CERTIFICADOR(1L),
	ASOCIADO_AL_CERTIFICADOR(2L),
	CORRESPONDE_A_OTRA_PERSONA(3L),
	NO_EXISTE_EN_CANASE(4L);
	
	/**
	 * Identificador del Tipo de NSS para la aclaraci&oacute;n en BDTU de la 
	 * correcci&oacute; de datos del asegurado
	 * 
	 */
	private Long id;
	
	/**
	 * Constructor de la enumeraci&oacute;n 
	 * @param id del Tipo de NSS para la aclaraci&oacute;n de la 
	 * correcci&oacute;n de datos del asegurado
	 */
	private TipoNSSCorreccionEnum(Long id) {
		this.id = id;
	}
	
	/**
     * Obtiene el identificador del Tipo de NSS para la aclaraci&oacute;n 
     * @return
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Obtiene el Tipo de NSS para la aclaraci&oacute;n por su identificador
     * @param id identificador del Tipo de NSS para la aclaraci&oacute;n  
     * @return el enum asociado o null si no se encuentra
     */
    public static TipoNSSCorreccionEnum fromId(long id) {
    	TipoNSSCorreccionEnum[] tipos = values();
        for(TipoNSSCorreccionEnum tipo : tipos) {
            if (tipo.getId() == id) {
                return tipo;
            }
        }
        return null;
    }

}
