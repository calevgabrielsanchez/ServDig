package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public enum TipoRegularizacionPeriodoEnum {
    
    AGREGAR("A", "AGREGAR"),
    ELIMINAR("E", "ELIMINAR"),
    INCLUIR("I", "INCLUIR"),
    MODIFICAR("M", "MODIFICAR"),
    INVALIDA("X", "INVALIDA");

    private TipoRegularizacionPeriodoEnum(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    private final String id;
    private final String desc;

    private static final Map<String, TipoRegularizacionPeriodoEnum> origenes = new HashMap<String, TipoRegularizacionPeriodoEnum>();
    private static final Map<String, TipoRegularizacionPeriodoEnum> origenesByDesc = new HashMap<String, TipoRegularizacionPeriodoEnum>();

    static {
        for (TipoRegularizacionPeriodoEnum origen : TipoRegularizacionPeriodoEnum.values()) {
            origenes.put(origen.getId(), origen);
            origenesByDesc.put(origen.getDesc(), origen);
        }
    }
    
    @JsonCreator
    public static TipoRegularizacionPeriodoEnum forValue(String value) {
        return origenes.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Entry<String, TipoRegularizacionPeriodoEnum> entry : origenes.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public static TipoRegularizacionPeriodoEnum getById(String id) {
        return origenes.get(id);
    }

    public static TipoRegularizacionPeriodoEnum getByDesc(String origen) {
        return origenesByDesc.get(origen);
    }
}
