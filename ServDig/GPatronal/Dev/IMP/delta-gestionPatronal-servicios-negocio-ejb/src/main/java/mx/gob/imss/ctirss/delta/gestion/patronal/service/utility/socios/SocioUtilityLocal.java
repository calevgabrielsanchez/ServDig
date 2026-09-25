package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.socios;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitSocio;

@Local
public interface SocioUtilityLocal {
	
	Socio convertirEntityToModelSocio(DitSocio ditSocio) throws Exception;
	
	DitSocio prepararAltaSocio(Socio socio);
	
	void validarSocioAlta(Socio socio)throws GestionPatronalBusinessException;
	
	Solicitud generarSolicitudAltaSocio(Socio socio, OrigenSolicitudEnum origenSolicitud, Usuario usuario);
	
	Solicitud generarSolicitudBajaSocio(Socio socio, List<Socio> listaSocios, 
		OrigenSolicitudEnum origenSolicitud, Usuario usuario);
	
	@Deprecated
	DitSocio convertirModelToEntity(Socio socio) throws Exception;
	@Deprecated
	DitSocio convertirModelToEntity1(Socio socio, DitPersonaFisica ditPersonaFisica) throws Exception;
	@Deprecated
	Socio convertirEntityToModelSocioFisico(DitSocio entity) throws Exception;
	
	
}
