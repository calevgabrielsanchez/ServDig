package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Local
public interface CambioMedicoServiceLocal {

	Map<String, Object> crearTramiteCambioMedicoDependiente(Long idSolicitud,List<GrupoFamiliar> integrantesEnUmf,AsignacionNSS nss, 
			Boolean asignacionDomicilio, Boolean parentescoPersmitido, Boolean existeCambioMedico,
			TramiteCorreccionDerechohabiente origenCambioMedico) throws DerechohabientesBusinessException ;
	/**
	 * Metodo que agrega un tramite de cambibo de medico consultorio y turno
	 * @param correccion
	 * @param idSolicitud
	 * @param nss
	 * @param afectados
	 * @param asignacionDomicilio
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Map<String, Object> agregarTramiteCambioMedicoDependiente(TramiteCorreccionDerechohabiente correccion ,
			Long idSolicitud, AsignacionNSS nss, List<GrupoFamiliar> afectados, Boolean asignacionDomicilio)
			throws DerechohabientesBusinessException;
}
