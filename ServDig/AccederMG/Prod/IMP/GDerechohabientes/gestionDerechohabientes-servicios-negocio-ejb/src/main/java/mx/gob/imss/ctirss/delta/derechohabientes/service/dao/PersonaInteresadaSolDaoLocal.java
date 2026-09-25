package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RequisitosDTO;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;

@Local
public interface PersonaInteresadaSolDaoLocal {
	PersonaInteresadaSolicitud getPersonaInteresada(Long idPersona, Long idSolicitud) throws DerechohabientesBusinessException, Exception;
	Long getIdPersonaInteresadaSol(Long idSolicitud) throws Exception;
	List<PersonaInteresadaSolicitud> getListPerssonaInteresadaSolicitudbyPersona(long idPersona) throws DerechohabientesBusinessException,Exception;
	
	List<PersonaInteresadaSolicitud> getListSolicitudbyPersonaInteresadayEstado(long idPersona, long  idEstadoSolicitud) throws Exception;
	
	RequisitosDTO validaTramiteRegistroConcubanaPadresPendiente(RequisitosDTO requisitos, Long idParentesco, Long sexoIntegrante, AsignacionNSS asignacionNss) 
			throws DerechohabientesBusinessException, Exception;
	
}
