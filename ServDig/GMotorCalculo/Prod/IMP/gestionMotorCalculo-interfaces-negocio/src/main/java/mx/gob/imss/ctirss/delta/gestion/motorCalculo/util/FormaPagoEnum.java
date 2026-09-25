/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.util;


/**
 * Enum con los valores de forma de pago
 * @author NOVUTECK1
 *
 */
public enum FormaPagoEnum {

	/**
	 * Forma de pago anual anticipado
	 */
	ANUAL_ANTICIPADO(4, "Anual Anticipado"),
	
	/**
     * Forma de pago mensual
     */
    MENSUAL(3, "Mensual Anticipado"),
    
    /**
     * Forma de pago bimestral
     */
    BIMESTRAL(2, "Bimestral"),
    /**
     * Forma de pago anual
     */
    ANUAL(1, "Anual");
    /**
     * Identificador de la forma de pago
     */
    private long id;
    
    /**
     * descripcion del tipo de pago
     */
    private String descripcion;
    
    /**
     * Constructor de la clase
     * @param id
     */
    FormaPagoEnum(long id, String descripcion){
        this.id=id;
        this.descripcion = descripcion;
    }
    
    /**
     * Obtiene el identificador del enum
     * @return
     */
    public long getId() {
        return this.id;
    }

    
    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el enum por su id 
     * @param id El identificador del enum
     * @return al enum asociado al id
     */
    public static FormaPagoEnum fromId(long id) {
        FormaPagoEnum[] formas = values();
        for(FormaPagoEnum forma:formas) {
            if (forma.getId() == id) {
                return forma;
            }
        }
        return null;
    }
}
