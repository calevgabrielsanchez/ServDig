package mx.gob.imss.cit.cda.service.cuentaindividual;

import java.util.HashMap;
import java.util.Map;

public enum TipoListaCapturaEnum {
    
    NUEVOS(1L, "AGREGADOS"),
    ELIMINADOS(2L, "ELIMINADOS"),
    INCLUIDOS(3L, "INCLUIDOS"),
    MODIFICADOS(4L, "MODIFICADOS"),
    ORIGINALES (5L, "ORIGINALES");

    private TipoListaCapturaEnum(Long id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    private final Long id;
    private final String desc;

    private static final Map<Long, TipoListaCapturaEnum> origenes = new HashMap<Long, TipoListaCapturaEnum>();
    private static final Map<String, TipoListaCapturaEnum> origenesByDesc = new HashMap<String, TipoListaCapturaEnum>();

    static {
        for (TipoListaCapturaEnum origen : TipoListaCapturaEnum.values()) {
            origenes.put(origen.getId(), origen);
            origenesByDesc.put(origen.getDesc(), origen);
        }
    }

    public Long getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public static TipoListaCapturaEnum getById(Long id) {
        return origenes.get(id);
    }

    public static TipoListaCapturaEnum getByDesc(String origen) {
        return origenesByDesc.get(origen);
    }

}
