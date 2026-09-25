package mx.gob.imss.cit.gestion.solicitud.flujo.model.enums;

/**
 * Enumerador que represanta la lista de los tipos de requerimientos
 * @author softtek
 *
 */
public enum TipoRequerimientoEnum {

    CONTRIBUYENTE("REQ_CON"), AUTORIDAD("REA_AUT");

    /**
     * Constructor del enumerador
     * @param id
     */
    private TipoRequerimientoEnum(String id) {
        this.id = id;
    }

    /**
     * Identificador del tipo de requerimiento
     */
    private String id;

    /**
     * Obtiene el identificador
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Metodo que busca y devuelve el tipo de requerimiento
     * @param id
     * @return tarea
     */
    public static TipoRequerimientoEnum find(String id) {
        for (TipoRequerimientoEnum tarea : TipoRequerimientoEnum.values()) {
            if (tarea.getId().equals(id)) {
                return tarea;
            }
        }
        return null;
    }

}
