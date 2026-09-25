/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util;


/**
 * Enum que contiene las versiones SUA Que utiliza IVRO
 * 
 * @author NOVUTECK1
 * 
 */
public enum VersionSUAIvroEnum {

    /**
     * Version para la modalidad 34
     */
    SUA_IVRO_34(16, "G389"), 
    /**
     * Version para la modalidad 35
     */
    SUA_IVRO_35(17, "G390"),
    /**
     * Version para la modalidad 43
     */
    SUA_IVRO_43(22, "G395"),
    /**
     * Version para la modalidad 44
     */
    SUA_IVRO_44(23, "G397"),
    
    /**
     * Version para la modalidad 40
     */
    SUA_IVRO_40(20, "G392"),
    /**
     * Version para la modalidad 33
     */
    SUA_IVRO_33(15, "G399");

    /**
     * Id del enum para su correspondiente version SUA, estos id se representan con el id de la modalidad
     */
    private long id;
    /**
     * Version del sua asociada al enum
     */
    private String version;

    /**
     * Constructor del enum para versiones sua de ivro
     * 
     * @param id identificador del enum
     * @param version CAdena con al version sua correspondiente
     */
    VersionSUAIvroEnum(long id, String version) {
        this.id = id;
        this.version =  version;
    }

    /**
     * Identificador del enum (este id esta asociado a la modalidad del patron)
     * 
     * @return el id del enum
     */
    public long getId() {
        return this.id;
    }
    

    /**
     * Obtiene la cadena con la version del sua
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Obtiene el enum de version sua dado el id de la modalidad de del patron
     * 
     * @param id identificador del enum
     * @return el enum que corresponda al identificador, null si no se encuentra
     */
    public static VersionSUAIvroEnum fromId(long id) {
        VersionSUAIvroEnum[] versiones = values();
        for (VersionSUAIvroEnum version : versiones) {
            if (version.getId() == id) {
                return version;
            }
        }
        return null;
    }

}
