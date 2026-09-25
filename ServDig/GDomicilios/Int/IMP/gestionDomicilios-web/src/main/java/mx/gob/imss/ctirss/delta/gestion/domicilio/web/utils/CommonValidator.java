package mx.gob.imss.ctirss.delta.gestion.domicilio.web.utils;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

public class CommonValidator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected static final String KEY_ORIGEN_CONTEXT = "ORIGEN_APP";
	
	public boolean validarSolicitudMismoOrigen(Solicitud solicitud, Long origenSolicitud){
		boolean solicitudMismoOrigen = false;
		if(solicitud.getOrigenSolicitud().getIdTipoSolicitud().equals(origenSolicitud)){
			solicitudMismoOrigen = true;
		}
		return solicitudMismoOrigen;		
	}
	
	public boolean esSolicitudInternet(Long origenSolicitud){
		boolean esInternet=false;
		OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(origenSolicitud);
		if(origen.equals(OrigenSolicitudEnum.INTERNET)){
			esInternet = true;
		}
		return esInternet;		
	}

	public Long getOrigenContext(HttpServletRequest request){
		String origenContext = (String)request.getSession()
			.getServletContext().getInitParameter(KEY_ORIGEN_CONTEXT);
		OrigenSolicitudEnum origenSolicitudEnum = OrigenSolicitudEnum.INTERNET;
		if(StringUtils.isNotEmpty(origenContext) && StringUtils.isNotBlank(origenContext)){
			origenSolicitudEnum = OrigenSolicitudEnum.getById(new Long(origenContext));
		}
		return origenSolicitudEnum.getId();
	}
	
}
