package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaInteresadaSol;


@Local
public interface PersonaInteresadaSolParserLocal {

	/**
	 * Convierte un objeto de negocio a objeto de persistencia
	 * @param entrada
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	DitPersonaInteresadaSol modelToPersist(PersonaInteresadaSolicitud entrada) throws DerechohabientesBusinessException;	
	
	/**
	 * Convierte de un objeto de persistencia a un objeto de negocio
	 * @param entrada
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	PersonaInteresadaSolicitud persisToModel(DitPersonaInteresadaSol entrada) throws DerechohabientesBusinessException;
	
	/**
	 * Convierte una lista de objetos de persistencia a una lista de objetos de negocio
	 * @param entrada
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<PersonaInteresadaSolicitud> persisToModelList(List<DitPersonaInteresadaSol> entrada) throws DerechohabientesBusinessException;
	
}
