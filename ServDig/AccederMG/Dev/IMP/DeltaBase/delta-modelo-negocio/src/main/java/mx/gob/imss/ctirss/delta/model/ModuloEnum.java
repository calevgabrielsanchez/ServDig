package mx.gob.imss.ctirss.delta.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Hugo Martinez
 *
 */
public enum ModuloEnum {
	PERSONAS(1, "GESTIÓN PERSONAS"), ASIGNACION_NSS(2, "GESTIÓN ASIGNACIÓN NSS"), 
	PATRONES(3, "GESTIÓN PATRONAL"), DERECHOHABIENTES(4, "DERECHOHABIENTES"), 
	GESTION_CLASIFICACION(5, "GESTIÓN CLASIFICACIÓN EMPRESAS");
	
	private Integer codigo;
	private String descripcion;
	
	private final static Map<String, ModuloEnum> hashNames = new HashMap<String, ModuloEnum>();
	private final static Map<Integer,ModuloEnum> hashCodes = new HashMap<Integer,ModuloEnum>();
	private final static Map<String ,ModuloEnum> hashDesc = new HashMap<String,ModuloEnum>();
	
	static{ 
		for(ModuloEnum propietario : ModuloEnum.values()){
			hashNames.put(propietario.name(), propietario);
			hashDesc.put(propietario.getDescripcion(), propietario);
			hashCodes.put(propietario.getCodigo(), propietario);
			
		}
	}
	
	public static  ModuloEnum getEnumByDesc(String desc){
		if (hashDesc.get(desc) != null ) {
			return (ModuloEnum)hashDesc.get(desc); 
		} else {
			return null;
		}
	}
	
	ModuloEnum(int codigo, String desc){
		this.codigo=codigo;
		this.descripcion= desc;
		
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	

}
