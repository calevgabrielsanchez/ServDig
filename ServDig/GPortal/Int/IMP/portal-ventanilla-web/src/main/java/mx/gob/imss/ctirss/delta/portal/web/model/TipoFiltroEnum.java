package mx.gob.imss.ctirss.delta.portal.web.model;

import java.util.HashMap;
import java.util.Map;

public enum TipoFiltroEnum {

	CURP(1, "CURP"), 
	RFC_FISICA(2, "RFC_FISICA"), 
	RFC_MORAL(3, "RFC_MORAL"), 
	NSS(4, "NSS"), 
	NRP(5, "NRP");

	private int id;
	private String desc;

	private TipoFiltroEnum(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}
	
	private final static Map<String, TipoFiltroEnum> hashNames = new HashMap<String, TipoFiltroEnum>();
	private final static Map<Integer,TipoFiltroEnum> hashCodes = new HashMap<Integer,TipoFiltroEnum>();
	
	static{ 
		for(TipoFiltroEnum tramite : TipoFiltroEnum.values()){
			hashNames.put(tramite.name(), tramite);
			hashCodes.put(tramite.getId(), tramite);
		}
	}
	
	public static TipoFiltroEnum obtenerEnumByName(String name){
		return hashNames.get(name);
	}
	
	public static TipoFiltroEnum obternerEnumById(Integer codigo){
		return hashCodes.get(codigo);
	}

}
