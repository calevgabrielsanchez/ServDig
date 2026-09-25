package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public enum ParentescoEnum implements Serializable{
	
	TITULAR(0),PADRES(1),HIJOS(2),CONYUGE(3),CONCUBINARIO(4),ASEGURADO(5),PENSIONADO(6),CONCUBINA(7),MADRE(8);
	
	private long id;
	
	private final static Map<String, ParentescoEnum> hashNames = new HashMap<String, ParentescoEnum>();
	private final static Map<Long,ParentescoEnum> hashCodes = new HashMap<Long,ParentescoEnum>();
	
	static{ 
		for(ParentescoEnum parentesco : ParentescoEnum.values()){
			hashNames.put(parentesco.name(), parentesco);
			hashCodes.put(parentesco.getId(), parentesco);
		}
	}
	
	
	
	ParentescoEnum(long id) {
		this.id=id;
	}
	
	public long getId(){
		return this.id;
	}
	
	public static ParentescoEnum obtenerEnumByName(String name){
		return hashNames.get(name);
	}
	
	public static ParentescoEnum obternerEnumById(Long codigo){
		return hashCodes.get(codigo);
	}
	
}
