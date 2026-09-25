package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Remote
public interface AutorizacionCircunscripcionServiceRemote {
	/**
	 * 
	 * @param nss
	 * @param cabeza
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarCircunscripcion(AsignacionNSS nss, CabezaGrupoFamiliar cabeza,Usuario usuario) throws DerechohabientesBusinessException;
	
	/**
	 * 
	 * @param idDerechohabiente
	 * @param afectado
	 * @param usuario
	 * @param nss
	 * @param correccion
	 * @param origen
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Solicitud saveCircunscripcionAutorizacionDerechohabiente(Long idDerechohabiente, GrupoFamiliar afectado,Usuario usuario, 
			AsignacionNSS nss,TramiteCorreccionDerechohabiente correccion,OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	
	Solicitud finalizaSolicitudCircunscripcion(Solicitud solicitud,
			AsignacionNSS nss, GrupoFamiliar afectado, Map<String, Object> validacionesCambioMedico) throws DerechohabientesBusinessException;
}
