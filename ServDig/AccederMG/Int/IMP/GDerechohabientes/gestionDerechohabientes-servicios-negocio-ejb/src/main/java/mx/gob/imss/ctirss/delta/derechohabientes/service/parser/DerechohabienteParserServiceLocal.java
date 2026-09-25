package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaDerechohabiente;

@Local
public interface DerechohabienteParserServiceLocal {
	public DitPersonaDerechohabiente modelToPersist(Derechohabiente entrada) throws DerechohabientesBusinessException;
	public Derechohabiente persisToModel(DitPersonaDerechohabiente entrada) throws DerechohabientesBusinessException, Exception;
}
