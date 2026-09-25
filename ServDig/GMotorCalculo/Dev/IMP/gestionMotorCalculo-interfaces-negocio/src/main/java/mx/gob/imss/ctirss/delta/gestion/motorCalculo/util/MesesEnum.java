/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.util;


/**
 * Enum con los valores de los meses
 * @author NOVUTECK1
 *
 */
public enum MesesEnum {
	ENERO(0, "1"),
    FEBRERO(1, "2"),
    MARZO(2, "3"),
    ABRIL(3, "4"),
    MAYO(4, "5"),
    JUNIO(5, "6"),
    JULIO(6, "7"),
    AGOSTO(7, "8"),
    SEPTIEMBRE(8, "9"),
    OCTUBRE(9, "10"),
    NOVIEMBRE(10, "11"),
    DICIEMBRE(11, "12");

    /**
     * Identificador del mes
     */
    private long id;

    /**
     * descripcion del mes
     */
    private String descripcion;

    /**
     * Constructor de la clase
     * @param id
     */
    MesesEnum(long id, String descripcion){
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
    public static MesesEnum fromId(long id) {
        MesesEnum[] formas = values();
        for(MesesEnum forma:formas) {
            if (forma.getId() == id) {
                return forma;
            }
        }
        return null;
    }
}
