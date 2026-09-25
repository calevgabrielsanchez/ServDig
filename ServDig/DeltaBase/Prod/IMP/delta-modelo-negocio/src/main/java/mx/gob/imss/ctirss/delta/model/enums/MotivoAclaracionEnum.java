package mx.gob.imss.ctirss.delta.model.enums;

/**
 * 
 * Enumeraci&oacute;n que incluye los motivos de aclaraci&oacute;n permitidos para una 
 * correcci&oacute;n de datos del asegurado.
 * 
 * @author softtek
 *	
 */
public enum MotivoAclaracionEnum {
	
	COBRO_INCAPACIDAD(1),
	PENSION(2),
	RETIRO_DESEMPLEO(3),
	REGISTRO_BENEFICIARIOS(4),
	ADSCRIPCION_UMF(5),
	CAMBIO_UMF(6),
	GASTOS_MATRIMONIO(7),
	GASTOS_FUNERAL(8),
	OBTENER_CREDITO(9),
	CONCLUSION_CREDITO(10),
	PRORROGA_REESTRUCTURA_CREDITO(11),
	DESCUENTO_INDEBIDO_CREDITO(12),
	REGISTRO_AFORE(13),
	ACLARACION_SALDO_SUPUESTA_VIVIENDA(14),
	OTRO(15);
	
	/**
	 * Identificador del Motivo de aclaraci&oacute;n de la 
	 * correcci&oacute;n de datos del asegurado
	 * en BDTU
	 */
	private long id;
	
	/**
	 * Constructor de la enumeraci&oacute;n 
	 * @param id identificador del Motivo de aclaraci&oacute;n de la 
	 * correcci&oacute;n de datos del asegurado en BDTU
	 */
	private MotivoAclaracionEnum(long id) {
		this.id = id;
	}
	
	/**
     * Obtiene el identificador del Motivo de aclaraci&oacute;n
     * @return
     */
    public long getId() {
        return this.id;
    }

    /**
     * Obtiene el Motivo de aclaraci&oacute;n por su identificador
     * @param id identificador del Motivo de aclaraci&oacute;n 
     * @return el enum asociado o null si no se encuentra
     */
    public static MotivoAclaracionEnum fromId(long id) {
    	MotivoAclaracionEnum[] motivos = values();
        for(MotivoAclaracionEnum motivo : motivos) {
            if (motivo.getId() == id) {
                return motivo;
            }
        }
        return null;
    }

}
