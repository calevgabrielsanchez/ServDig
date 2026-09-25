package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Remote
public interface AsignacionDomicilioServiceRemote {
	
	Solicitud guardaAsignacionDeDomicilioSimplificado(Long idSolicitud,AsignacionNSS nss,CabezaGrupoFamiliar cabeza,GrupoFamiliar afectado,
			TramiteCorreccionDerechohabiente correccion, List<GrupoFamiliar> padres,
			Map<String, Object> validacionesCambios) throws DerechohabientesBusinessException, ImpactaAlmacenesWSException, Exception;
	Map<String, Object> guardaAsignacionDeDomicilioSimpleAsegurado(TramiteCorreccionDerechohabiente correccion, GrupoFamiliar asegurado) throws DerechohabientesBusinessException, Exception ;

	Solicitud finalizaSolicitudAsignacionDomicilio(
			Solicitud solicitud, GrupoFamiliar registroAsegurado,CabezaGrupoFamiliar cabeza,Boolean consultar,
			Boolean consultarPadresConcubinas, List<GrupoFamiliar> padresConcubinas) throws DerechohabientesBusinessException,
			SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException;
	
	/**
	 * Obtiene a los padres, sus ids y sus personas
	 * @param nss
	 * @param patImss
	 * @param nuevoDomicilio
	 * @param nuevoMedico
	 * @param fechaCambioTurno
	 * @return
	 * @throws Exception
	 */
	Map<String, List<? extends Object>> getPadresConcubinasParaCambio(AsignacionNSS nss, Integer patImss, MedicoEnTurno nuevoMedico) throws Exception;
	
	/**+
	 * Metodo para obtener a los padres y concubinas que se tienen que ir con el asegurado
	 * @param nss
	 * @param patronIMSS
	 * @return
	 * @throws Exception
	 */
	List<GrupoFamiliar> getPadresConcubinasParaCambio(AsignacionNSS nss, Integer patronIMSS) throws Exception;
	
	List<GrupoFamiliar> findPersonasSinDomicilioEnUmf(Long idAsignacionNSS, Long idUmf, List<Long> idsPersonasExcluir, List<Long> idsEstados) throws Exception;
}
