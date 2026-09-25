package mx.gob.imss.cit.gestion.solicitud.flujo.model.enums;

/**
 * Enumerador que representa la lista de los tipos de transicion
 * @author softtek
 *
 */
public enum TipoTransicionEnum {

    PRINCIPAL("EST_TRANS_PRL"), TIMMER("EST_TRANS_TIM"), ALTERNATIVA1("EST_TRANS_AL1"), ALTERNATIVA2("EST_TRANS_AL2"),
    ALTERNATIVA3("EST_TRANS_AL3");

    /**
     * Identificador de la transicion
     * @param id
     */
    private TipoTransicionEnum(String id) {
        this.id = id;
    }

    /**
     * Identificador
     */
    private String id;

    /**
     * Metodo para obtener el identificador
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Metodo que busca y devuelve el tipo de transicion
     * @param id
     * @return
     */
    public static TipoTransicionEnum find(String id) {
        for (TipoTransicionEnum tarea : TipoTransicionEnum.values()) {
            if (tarea.getId().equals(id)) {
                return tarea;
            }
        }
        return null;
    }

}
