/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Valores para el catalogo de concepros
 * @author NOVUTECK1
 *
 */
public enum ConceptoEnum {
    /**
     * Concepto para el seguro ivro
     */
    SEGURO_IVRO(1);

    /**
     * Identificador del concepto
     */
    private long id;
    
    /**
     * Constructor de la clase
     * @param id
     */
    ConceptoEnum(long id){
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
    public static ConceptoEnum fromId(long id) {
        ConceptoEnum[] estados = values();
        for(ConceptoEnum estado:estados) {
            if (estado.getId() == id) {
                return estado;
            }
        }
        return null;
    }
}
