package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Enumeraci&oacute;n que incluye los Tipos de NSS para una aclaraci&oacute;n permitidos en una 
 * correcci&oacute;n de datos del asegurado.
 * @author softtek
 *
 */
public enum TipoNSSAclaracionEnum {

	CANCELADO_POR_DUPLICIDAD(3L, 1L, "Cancelado por duplicidad"),
	CORRESPONDE_A_UN_HOMONIMO(4L, 2L, "Corresponde a un hom\u00F3nimio"),
	NO_EXISTE_EN_CANASE(6L, 3L, "No existe en CANASE"),
	CORRESPONDE_A_OTRO_ASEGURADO(5L, 4L, "Corresponde a otro asegurado"),
	CORRECCION_DE_NOMBRE(1L, 5L, "Correcci\u00F3n de nombre"),
	CORRECCION_DE_DATOS_ESTADISTICOS(2L, 6L, "Correcci\u00F3n de datos estad\u00EDsticos"),
	CUENTA_ILOGICA(7L, 7L, "Cuenta Il\u00F3gica"),
	BLANQUEAMIENTO_CURP(8L, 8L, "Blanqueamiento de CURP");


	/**
	 * Identificador del Tipo de NSS para la aclaraci&oacute;n en BDTU de la 
	 * correcci&oacute; de datos del asegurado
	 * 
	 */
	private Long id;

	/**
	 * Clave del cat&aacute;logo del Tipo de NSS para la aclaraci&oacute;n en BDTU de la
	 * correcci&oacute; de datos del asegurado
	 *
	 */
	private Long clave;

	/**
	 * Descripci&oacute; del Tipo de NSS para la aclaraci&oacute;n en BDTU de la
	 * correcci&oacute; de datos del asegurado
	 *
	 */
	private String descripcion;

	/**
	 * Constructor de la enumeraci&oacute;n
	 * @param id del Tipo de NSS para la aclaraci&oacute;n
	 * @param clave Clave del cat&aacute;logo del Tipo de NSS para la aclaraci&oacute;n
	 * @param descripcion Descripci&oacute;n del Tipo de NSS para la aclaraci&oacute;n
	 *
	 */
	TipoNSSAclaracionEnum(Long id, Long clave, String descripcion) {
		this.id = id;
		this.clave = clave;
		this.descripcion = descripcion;
	}
	
	/**
     * Obtiene el identificador del Tipo de NSS para la aclaraci&oacute;n 
     * @return Long
     */
    public Long getId() {
        return this.id;
    }

	/**
	 * Obtiene la clave del Tipo de NSS para la aclaraci&oacute;n
	 * @return Long
	 */
	public Long getClave() {
		return this.clave;
	}

	/**
	 * Obtiene la descripci&oacute;n del Tipo de NSS para la aclaraci&oacute;n
	 * @return String
	 */
    public String getDescripcion() {
    	return this.descripcion;
    }

    /**
     * Obtiene el Tipo de NSS para la aclaraci&oacute;n por su identificador
     * @param id identificador del Tipo de NSS para la aclaraci&oacute;n  
     * @return el enum asociado o null si no se encuentra
     */
    public static TipoNSSAclaracionEnum fromId(Long id) {

        for(TipoNSSAclaracionEnum tipo : values()) {
            if (tipo.getId().equals(id)) {
                return tipo;
            }
        }
        return null;
    }

}
