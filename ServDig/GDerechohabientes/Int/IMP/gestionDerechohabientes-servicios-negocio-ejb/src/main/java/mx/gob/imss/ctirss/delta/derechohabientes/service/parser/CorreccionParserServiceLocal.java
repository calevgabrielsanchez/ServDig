package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionDatoDerechohab;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Local
public interface CorreccionParserServiceLocal {
	
	DitCorreccionDatoDerechohab modelToPersist(TramiteCorreccionDerechohabiente entrada) throws DerechohabientesBusinessException;
	TramiteCorreccionDerechohabiente persistToModel(DitCorreccionDatoDerechohab entrada) throws DerechohabientesBusinessException, Exception;
}
