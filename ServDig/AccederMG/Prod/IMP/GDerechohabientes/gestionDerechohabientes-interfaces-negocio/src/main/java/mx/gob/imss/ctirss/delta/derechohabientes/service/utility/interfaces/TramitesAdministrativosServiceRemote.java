package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface TramitesAdministrativosServiceRemote {
	
	/**
	 * Metodo para encontrar a los candidatos a suspencion administrativa
	 * @param nss
	 * @return List<GrupoFamiliar> lista con los integrantes candidatos al tramite
	 */
	List<GrupoFamiliar> findCandidatosSuspencionAdministrativa(AsignacionNSS nss) throws DerechohabientesBusinessException;
	
	/**
	 * Metodo para encontrar a los candidatos a suspencion administrativa
	 * @param nss
	 * @return List<GrupoFamiliar> lista con los integrantes candidatos al tramite
	 */
	List<GrupoFamiliar> findCandidatosBajaAdministrativa(AsignacionNSS nss) throws DerechohabientesBusinessException;
	
	/**
	 * Metodo para encontrar a los candidatos a suspencion administrativa
	 * @param nss
	 * @return List<GrupoFamiliar> lista con los integrantes candidatos al tramite
	 */
	List<GrupoFamiliar> findCandidatosReactivacionAdministrativa(AsignacionNSS nss) throws DerechohabientesBusinessException;
	
	/**
	 * Metodo para generar la solicitud de baja
	 */
	Solicitud crearSolicitudBajaSuspencion(GrupoFamiliar integrante, AsignacionNSS asegurado, Usuario usuario, OrigenSolicitudEnum origenSolicitud, TipoTramiteEnum tipoTramite) throws Exception;
	
	/**
	 * Metodo para generar la solicitud de baja
	 */
	Solicitud crearSolicitudReactivacion(GrupoFamiliar integrante, AsignacionNSS asegurado, Usuario usuario) throws Exception;
	
	/**
	 * Metodo para finalizar los trmites de baja o suspencion administrativa
	 * @param solicitud
	 * @param asignacionNSS
	 * @return
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudException
	 * @throws ImpactaAlmacenesWSException
	 */
	Solicitud finalizarTramiteBajaSuspencionAdministrativa(Solicitud solicitud, AsignacionNSS asignacionNSS) throws SolicitudNoValidaException, SolicitudException, ImpactaAlmacenesWSException;
	
	/**
	 * Metodo para finalizar los trmites de baja o suspencion administrativa
	 * @param solicitud
	 * @param asignacionNSS
	 * @return
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudException
	 * @throws ImpactaAlmacenesWSException
	 */
	Solicitud finalizarReactivacionAdministrativa(Solicitud solicitud, AsignacionNSS asignacionNSS) throws SolicitudNoValidaException, SolicitudException, ImpactaAlmacenesWSException;
	
}
