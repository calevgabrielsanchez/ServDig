package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Estados posibles de un seguro IVRO
 * @author NOVUTECK1
 *
 */
public enum EstadoSeguroIvroEnum {

    /**
     * Seguro solo creado
     */
    NUEVO(1),
    /**
     * Seguro ya pagado
     */
    ACTIVO(2),
    /**
     * Seguro no pagado
     */
    VENCIDO(3),
    /**
     * Seguro cancelado por RISS
     */
    CANCELADO_RISS(4),
    /**
     * Concluido
     */
    CONCLUIDO(5),
    /**
     * Baja de seguro por mora
     */
    BAJA_POR_MORA(6),
    /**
     * Baja de seguro por reingreso al Regimen Obligatorio
     */
    BAJA_POR_REINGRESO_RO(7),
    /**
     * Seguro cancelado
     */
    CANCELADO(8),
    
    BAJA_A_SOLICITUD_ASEGURADO(9);
    
    /**
     * Identificador del estado
     */
    private long id;
    /**
     * Constructor del enum
     * @param id
     */
    EstadoSeguroIvroEnum(long id){
        this.id=id;
    }
    /**
     * Obtiene el identificador del Estado
     * @return
     */
    public long getId() {
        return this.id;
    }

    /**
     * Obtiene el enum por su id
     * @param id identificador del enum
     * @return el enum asociado o null si no se encuentra
     */
    public static EstadoSeguroIvroEnum fromId(long id) {
        EstadoSeguroIvroEnum[] estados = values();
        for(EstadoSeguroIvroEnum estado : estados) {
            if (estado.getId() == id) {
                return estado;
            }
        }
        return null;
    }
}
