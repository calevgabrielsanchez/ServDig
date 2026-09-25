package mx.gob.imss.ctirss.delta.model.gestion.seguro;

import java.util.HashMap;
import java.util.Map;

public enum ProcesaSeguroEnum {

    BAJA_MENSUAL_CVRO(1), BAJA_POR_MORA(2), NOTIFICA_NUEVO_PAGO_CVRO(3);

    private int codigo;

    private ProcesaSeguroEnum(int codigo){
        this.codigo=codigo;
    }

    private final static Map<String, ProcesaSeguroEnum> hashNames = new HashMap<String, ProcesaSeguroEnum>();
    private final static Map<Integer,ProcesaSeguroEnum> hashCodes = new HashMap<Integer,ProcesaSeguroEnum>();

    static{
        for(ProcesaSeguroEnum enu : ProcesaSeguroEnum.values()){
            hashNames.put(enu.name(), enu);
            hashCodes.put(enu.getCodigo(), enu);
        }
    }
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public static ProcesaSeguroEnum valueOf(Integer codigo){
        return hashCodes.get(codigo);
    }
}
