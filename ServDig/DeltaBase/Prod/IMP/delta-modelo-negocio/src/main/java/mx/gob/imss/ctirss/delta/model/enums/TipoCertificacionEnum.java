package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Enumeraci&oacute;n que incluye los Tipos de certificaci&oacute;n permitidos en una 
 * correcci&oacute;n de datos del asegurado.
 * 
 * @author softtek
 *
 */
public enum TipoCertificacionEnum {
	
	CERTIFICADOR_CORRECCION_NOMBRE(1,1,1),
	CERTIFICADOR_CORRECCION_DATOS_EST(2,1,2),
	ASOCIADO_CERT_CANC_DUPLICIDAD(3,2,3),
	ASOCIADO_CERT_CORRECCION_NOMBRE(4,2,1),
	ASOCIADO_CERT_CORREC_DATOS_EST(5,2,2),
	CORRESP_OTRA_PERSONA_CORRESP_HOMONIMO(6,3,4),
	CORRESP_OTRA_PERSONA_CORRESP_OTRO_ASEG(7,3,5),
	NO_EXISTE_EN_CANASE(8,4,6);

	
	/**
	 * Identificador del Tipo de certificaci&oacute;n en BDTU
	 */
	private long id;
	private long idTipoNssCorreccion;
	private long idTipoNssAclaracion;
	
	/**
	 * Constructor de la enumeraci&oacute;n 
	 * @param id identificador del Tipo de certificaci&oacute;n
	 */
	private TipoCertificacionEnum(long id, long idTipoNssCorreccion, long idTipoNssAclaracion) {
		this.id = id;
		this.idTipoNssCorreccion = idTipoNssCorreccion;
		this.idTipoNssAclaracion = idTipoNssAclaracion;
	}
	
	/**
     * Obtiene el identificador del Tipo de certificaci&oacute;n
     * @return
     */
    public long getId() {
        return this.id;
    }

    public long getIdTipoNssCorreccion() {
		return idTipoNssCorreccion;
	}

	public void setIdTipoNssCorreccion(long idTipoNssCorreccion) {
		this.idTipoNssCorreccion = idTipoNssCorreccion;
	}

	public long getIdTipoNssAclaracion() {
		return idTipoNssAclaracion;
	}

	public void setIdTipoNssAclaracion(long idTipoNssAclaracion) {
		this.idTipoNssAclaracion = idTipoNssAclaracion;
	}

	/**
     * Obtiene el Tipo de certificaci&oacute;n por su identificador
     * @param id del Tipo de certificaci&oacute;n 
     * @return el enum asociado o null si no se encuentra
     */
    public static TipoCertificacionEnum fromId(long id) {
    	TipoCertificacionEnum[] tipos = values();
        for(TipoCertificacionEnum tipo : tipos) {
            if (tipo.getId() == id) {
                return tipo;
            }
        }
        return null;
    }

}
