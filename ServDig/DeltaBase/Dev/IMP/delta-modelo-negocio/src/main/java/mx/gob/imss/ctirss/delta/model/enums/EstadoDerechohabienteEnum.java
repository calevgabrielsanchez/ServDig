package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum EstadoDerechohabienteEnum implements Serializable{

	VIGENTE(1),CONSERVACION_DERECHOS(2),BAJA(3),PENSION_TRAMITE(4),CON_DERECHO(5),FALLECIDO(6), VIGENTE_POR_PRORRGA(7);
	
	private long id;
	
	private final static Map<String, EstadoDerechohabienteEnum> names = new HashMap<String, EstadoDerechohabienteEnum>();
	private final static Map<Long,EstadoDerechohabienteEnum> ids = new HashMap<Long,EstadoDerechohabienteEnum>();
	
	EstadoDerechohabienteEnum(long id) {
		this.id=id;
	}
	
	static{ 
		for(EstadoDerechohabienteEnum tramite : EstadoDerechohabienteEnum.values()){
			names.put(tramite.name(), tramite);
			ids.put(tramite.getId(), tramite);
		}
	}
	
	public long getId(){
		return this.id;
	}
	
	public static EstadoDerechohabienteEnum getById(long id) {
		return ids.get(id);
	}
	
	public static EstadoDerechohabienteEnum getByName(String name){
		return names.get(name);
	}
}