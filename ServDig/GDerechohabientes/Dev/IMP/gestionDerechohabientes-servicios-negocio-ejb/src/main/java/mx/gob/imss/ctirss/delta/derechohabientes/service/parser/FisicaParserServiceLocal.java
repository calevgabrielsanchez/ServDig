package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

@Local
public interface FisicaParserServiceLocal {
	
	public DitPersona modelToPersist(Fisica entrada) throws DerechohabientesBusinessException;
	public Fisica persisToModel(DitPersona entrada) throws DerechohabientesBusinessException, Exception;
}
