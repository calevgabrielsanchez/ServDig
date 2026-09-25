/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Enum con los valores del estado de pago
 * @author NOVUTECK1
 *
 */
public enum EstadoPagoEnum {
    
    /**
     * Por pagar
     */
    POR_PAGAR(1),
    /**
     * Pagado
     */
    PAGADO(2),
    /**
     * Pago vencido
     */
    VENCIDO(3);
    /**
     * Identificador del estado de pago
     */
    private long id;
    
    /**
     * Constructor de la clase
     * @param id
     */
    EstadoPagoEnum(long id){
        this.id=id;
    }
    
    /**
     * Obtiene el identificador del enum
     * @return
     */
    public long getId() {
        return this.id;
    }

    /**
     * Obtiene el enum por su id 
     * @param id El identificador del enum
     * @return al enum asociado al id
     */
    public static EstadoPagoEnum fromId(long id) {
        EstadoPagoEnum[] estados = values();
        for(EstadoPagoEnum estado:estados) {
            if (estado.getId() == id) {
                return estado;
            }
        }
        return null;
    }

}
