package mx.gob.imss.ctirss.delta.model.gestion.solicitud;

import java.util.HashMap;
import java.util.Map;

public enum OrigenSolicitudEnum {

	VENTANILLA			(1L, "VENTANILLA"), 
	INTERNET			(2L, "INTERNET"), 
	MOVILES				(5L, "MOVILES"), 
	PORTAL_CIUDADANO	(6L, "PORTAL_CIUDADANO"),
	VENTANILLA_UNICA	(7L, "VENTANILLA \u00DANICA"),
	ECONOMIA			(8L, "SECRETARIA ECONOMIA"),
	VENTANILLA_TSPI		(9L, "VENTANILLA TSPI"),
	INTERNET_TSPI		(10L, "INTERNET TSPI"),
	PORTAL_IMSS_LLAVE	(11L, "PORTAL IMSS LLAVE"),
	IMSS_BIENESTAR		(12L, "IMSS BIENESTAR");

	private OrigenSolicitudEnum(Long id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	private Long id;
	private String desc;

	private static final Map<Long, OrigenSolicitudEnum> origenes = new HashMap<Long, OrigenSolicitudEnum>();
	private static final Map<String, OrigenSolicitudEnum> origenesByDesc = new HashMap<String, OrigenSolicitudEnum>();
	
	static {
		for (OrigenSolicitudEnum origen : OrigenSolicitudEnum.values()) {
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
	
	public static OrigenSolicitudEnum getById (Long id) {
		return origenes.get(id);
	}
	
	public static OrigenSolicitudEnum getByDesc (String origen) {
		return origenesByDesc.get(origen);
	}

}
