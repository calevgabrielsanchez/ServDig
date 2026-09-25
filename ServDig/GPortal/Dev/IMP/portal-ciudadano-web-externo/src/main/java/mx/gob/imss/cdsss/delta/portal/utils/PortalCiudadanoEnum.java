package mx.gob.imss.cdsss.delta.portal.utils;

import java.util.HashMap;
import java.util.Map;

public enum PortalCiudadanoEnum {

	PORTAL_CIUDADANO_GENERAL			(1L, PortalCiudadanoUtils.URL_PORTAL_CIUDADANO_GENERAL),
	PORTAL_CIUDADANO_ALTA_PATRONAL		(2L, PortalCiudadanoUtils.URL_PORTAL_CIUDADANO_ALTA_PATRONAL),
	PORTAL_CIUDADANO_IVRO_INDIVIDUAL	(3L, PortalCiudadanoUtils.URL_PORTAL_CIUDADANO_SEGURO_INDIVIDUAL),;
	
	private Long id;
	private String urlLogin;
	
	private static final Map<Long, PortalCiudadanoEnum> origenes = new HashMap<Long, PortalCiudadanoEnum>();
	
	private PortalCiudadanoEnum(Long id, String urlLogin) {
		this.id = id;
		this.urlLogin = urlLogin;
	}
	
	public Long getId(){
		return id;
	}
	
	public String getUrlLogin(){
		return urlLogin;
	}
	
	public static PortalCiudadanoEnum getById(Long id){
		return origenes.get(id);
	}
	
	static {
		for (PortalCiudadanoEnum origen : PortalCiudadanoEnum.values()){
			origenes.put(origen.getId(), origen);
		}
	}
	
}
