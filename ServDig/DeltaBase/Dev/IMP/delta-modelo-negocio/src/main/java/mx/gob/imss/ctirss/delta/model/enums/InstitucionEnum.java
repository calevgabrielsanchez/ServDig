package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Enumeraci&oacute;n que incluye las instituciones a las cuales pertenecen los diferentes
 * motivos de aclaraci&oacute;n de correcci&oacute;n de datos de un asegurado.
 * 
 * @author softtek
 *
 */
public enum InstitucionEnum {

	IMSS(1),
	INFONAVIT(2),
	AFORE(3),
	OTRO(4);
	
	/**
	 * Identificador de la Instituci&oacute;n en BDTU
	 */
	private long id;
	
	/**
	 * Constructor de la enumeraci&oacute;n 
	 * @param id identificador de la Instituci&oacute;n en BDTU
	 */
	private InstitucionEnum(long id) {
		this.id = id;
	}
	
	/**
     * Obtiene el identificador de la Instituci&oacute;n
     * @return
     */
    public long getId() {
        return this.id;
    }

    /**
     * Obtiene la Instituci&oacute;n por su identificador
     * @param id identificador de la Instituci&oacute;n 
     * @return el enum asociado o null si no se encuentra
     */
    public static InstitucionEnum fromId(long id) {
        InstitucionEnum[] instituciones = values();
        for(InstitucionEnum institucion : instituciones) {
            if (institucion.getId() == id) {
                return institucion;
            }
        }
        return null;
    }
	
}
