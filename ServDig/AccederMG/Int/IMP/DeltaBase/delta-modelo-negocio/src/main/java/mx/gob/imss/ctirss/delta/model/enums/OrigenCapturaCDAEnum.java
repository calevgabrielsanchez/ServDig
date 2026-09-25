package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum OrigenCapturaCDAEnum {
    SOLICITUD(1L, "Solicitud"), RESPONSABLE(2L, "Responsable"), SISTEMA(3L, "Sistema");

    private final Long clave;
    private final String descripcion;
    

    private final static Map<Long,OrigenCapturaCDAEnum> CLAVES = new HashMap<Long,OrigenCapturaCDAEnum>();
    
	static{ 
		for(OrigenCapturaCDAEnum origen : OrigenCapturaCDAEnum.values()){
			CLAVES.put(origen.getClave(), origen);
		}
	}
	
    
    private OrigenCapturaCDAEnum(Long clave, String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    public Long getClave() {
        return clave;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    
    public static OrigenCapturaCDAEnum getOrigenbyClave(Long clave){
    	return CLAVES.get(clave);
    }
    
}
