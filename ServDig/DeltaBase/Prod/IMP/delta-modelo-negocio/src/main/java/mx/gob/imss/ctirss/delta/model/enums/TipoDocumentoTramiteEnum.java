package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoDocumentoTramiteEnum {
	ACUSE(1), COMPROBANTE(2);
	
	private TipoDocumentoTramiteEnum(Integer codigo){
		this.codigo = codigo;
	}
	
	private Integer codigo;
	
	public Integer getCodigo(){
		return this.codigo;
	}
	
	
	private final static Map<String, TipoDocumentoTramiteEnum> hashNames = new HashMap<String, TipoDocumentoTramiteEnum>();
	private final static Map<Integer,TipoDocumentoTramiteEnum> hashCodes = new HashMap<Integer,TipoDocumentoTramiteEnum>();
	
	static{ 
		for(TipoDocumentoTramiteEnum tramite : TipoDocumentoTramiteEnum.values()){
			hashNames.put(tramite.name(), tramite);
			hashCodes.put(tramite.getCodigo(), tramite);
		}
	}
	
	public static TipoDocumentoTramiteEnum obtenerEnumByName(String name){
		return hashNames.get(name);
	}
	
	public static TipoDocumentoTramiteEnum obternerEnumById(Integer codigo){
		return hashCodes.get(codigo);
	}

	
}
