/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Enum con los estados de compra
 * @author NOVUTECK1
 *
 */
public enum EstadoCompraEnum {

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
     * Identificador del estado de la compra
     */
    private long id;
    
    /**
     * Constructor de la clase
     * @param id
     */
    EstadoCompraEnum(long id){
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
    public static EstadoCompraEnum fromId(long id) {
        EstadoCompraEnum[] estados = values();
        for(EstadoCompraEnum estado:estados) {
            if (estado.getId() == id) {
                return estado;
            }
        }
        return null;
    }
}
