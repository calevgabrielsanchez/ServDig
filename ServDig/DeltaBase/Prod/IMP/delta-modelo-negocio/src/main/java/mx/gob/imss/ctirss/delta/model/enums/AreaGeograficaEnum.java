/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Enum que contendra los valores de las areas geograficas
 * @author NOVUTECK1
 *
 */
public enum AreaGeograficaEnum {
    
    /**
     * Zona Geografica A
     */
    ZONA_A("A", 1), 
    /**
     * Zona geografica B
     */
    ZONA_B("B", 2), 
    /**
     * Zona GEografica C
     */
    ZONA_C("C", 3),
    /**

     * Zona Geografica D

     */

    ZONA_D("D", 4);

    /**
     * Clave de la zona
     */
    private String clave;
    /**
     * Id de la zona
     */
    private int id;

    /**
     * Constructor del enum
     * @param clave
     * @param id
     */
    private AreaGeograficaEnum(String clave, int id) {
        this.clave = clave;
        this.id = id;
    }

    /**
     * Obtiene el vaor de la clave para el enum
     * @return
     */
    public String getClave() {
        return clave;
    }
    
    /**
     * Obtiene el valor del id para la zon
     * @return
     */
    public int getId() {
        return id;
    }
    
    /**
     * Obtiene el enum del area geografica por id
     * @param id
     * @return
     */
    public static AreaGeograficaEnum geFromId(int id) {
        AreaGeograficaEnum[] valores = values();
        for(AreaGeograficaEnum modalidad:valores) {
            if (modalidad.getId() == id) {
                return modalidad;
            }
        }
        return null;
    }
    
    /**
     * Obtiene el enum del area geografica por clave
     * @param clave
     * @return
     */
    public static AreaGeograficaEnum geFromClave(String clave) {
        AreaGeograficaEnum[] valores = values();
        for(AreaGeograficaEnum modalidad:valores) {
            if (modalidad.getClave().equalsIgnoreCase(clave)) {
                return modalidad;
            }
        }
        return null;
    }

}
