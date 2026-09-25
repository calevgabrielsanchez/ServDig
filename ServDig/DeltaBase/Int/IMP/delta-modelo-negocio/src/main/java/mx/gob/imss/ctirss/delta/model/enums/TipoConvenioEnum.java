package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoConvenioEnum {
	SIN_CONVENIO(0), URBANO(1), CAMPO(2), MIXTO(3);
	
	private Integer codigo;
	
	private TipoConvenioEnum(Integer value){
		this.codigo=value;
	}
	
	private final static Map<Integer,TipoConvenioEnum> hashCodes = new HashMap<Integer,TipoConvenioEnum>();
	
	static{ 
		for(TipoConvenioEnum propietario : TipoConvenioEnum.values()){
			hashCodes.put(propietario.getCodigo(), propietario);
		}
	}
	
	public static TipoConvenioEnum obternerEnumById(Integer codigo){
		return hashCodes.get(codigo);
	}
	
	private Integer getCodigo(){
		return this.codigo;
	}
}
