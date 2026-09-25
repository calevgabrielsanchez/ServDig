package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Remote
public interface CambioMedicoServiceRemote {
	/**
	 * Bandera para saber si es posible o no realizar el cambio de medico consultorio y o turno
	 * @param idPersona
	 * @param nss
	 * @return
	 */
	Boolean isCambioMedicoPosible(Long idPersona, AsignacionNSS nss);
	
	/**
	 * Metodo para finaliza una solicitud de cambio de medico consultorio y turno
	 * @param solicitud
	 * @param nss
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Solicitud finalizaSolicitudCambioMedico(Solicitud solicitud, AsignacionNSS nss) throws DerechohabientesBusinessException, ImpactaAlmacenesWSException;
	/**
	 * Metodo para obtener a todos los candidatos a cambio de medico
	 * @param nss
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarCambioMedico(AsignacionNSS nss,Usuario usuario) throws DerechohabientesBusinessException;
	
	/**
	 * Metodo para crear la solicitud de cambio de medico y turno
	 * @param integranteAfectado
	 * @param usuario
	 * @param nss
	 * @param correccion
	 * @param origen
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Solicitud crearSolicitudCambioMedico(GrupoFamiliar integranteAfectado,Usuario usuario, AsignacionNSS nss, 
			TramiteCorreccionDerechohabiente correccion,OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
}
