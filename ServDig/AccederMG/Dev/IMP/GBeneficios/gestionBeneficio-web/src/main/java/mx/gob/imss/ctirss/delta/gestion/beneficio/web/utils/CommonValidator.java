package mx.gob.imss.ctirss.delta.gestion.beneficio.web.utils;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;

public class CommonValidator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected static final String KEY_ORIGEN_CONTEXT = "ORIGEN_APP";	
	protected static final String KEY_USUARIO_SSO = "usuarioSSO";
	
	
	public OrigenSolicitudEnum getOrigenContext(HttpServletRequest request){
		String origenContext = (String)request.getSession().getServletContext().getInitParameter(KEY_ORIGEN_CONTEXT);
		OrigenSolicitudEnum origenSolicitudEnum = OrigenSolicitudEnum.INTERNET;
		if(StringUtils.isNotEmpty(origenContext) && StringUtils.isNotBlank(origenContext)){
			origenSolicitudEnum = OrigenSolicitudEnum.getById(new Long(origenContext));
		}
		return origenSolicitudEnum;
	}
	
}
