package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;

@Local
public interface ProrrogaServiceLocal {
	
	public void bajaProrroga(TramiteProrroga prorroga);
	public TramiteProrroga getProrrogaActiva(Long idAsignacionNss,Long idPersona);
	/**
	 * Metodo para finalizar una solicitud de prorroga
	 * @param solicitud - Solicitud , el objeto debe contener al menos el id de la solicitud
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudException
	 */
	 Solicitud finalizarSolicitudProrroga(Solicitud solicitud)
			throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException;
	
	 /**
	  * Metodo encargado de validar la prorroga por estudios y las regla de negocio relacionadas con prorrogas anteriores y vigencia
	  * @param integrante
	  * @param idAsignacioNSS
	  * @return
	  * @throws DerechohabientesBusinessException
	  * @throws Exception
	  */
	 String validaIntegrantePrrogaEstudiosTSPI(GrupoFamiliar integrante, Long idAsignacioNSS) throws DerechohabientesBusinessException, Exception;
	 
	 /**
	  * Metodo encargado de guardar la solicitud de prrorroga por esutdios , si el origen es INTERNET_TSPI la solicitud no se concluye
	  * solo guarda e solicitud y tramite
	  * @param constancia
	  * @param grupoFamiliar
	  * @param usuario
	  * @param idOrigenSolicitud
	  * @return
	  * @throws DerechohabientesBusinessException
	  * @throws Exception
	  */
	 Solicitud saveProrrogaEstudios(ConstanciaEstudio constancia,GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud) throws DerechohabientesBusinessException, Exception;
	 
	 /**
	  * Metodo que guarda una solicitud de prorroga por enfermedad impactando almacenes y BDTU
	  * @param dictamen
	  * @param grupoFamiliar
	  * @param usuario
	  * @param idOrigenSolicitud
	  * @return
	  * @throws DerechohabientesBusinessException
	  * @throws Exception
	  */
	 Solicitud saveProrrogaEnfermedad(
				DictamenIntegranteIncapacitado dictamen,
				GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud)
				throws DerechohabientesBusinessException, Exception;

}
