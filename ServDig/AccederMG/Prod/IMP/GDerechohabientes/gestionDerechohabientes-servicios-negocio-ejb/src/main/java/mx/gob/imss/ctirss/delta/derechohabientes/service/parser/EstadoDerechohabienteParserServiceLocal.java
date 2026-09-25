package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoDerechohabiente;

@Local
public interface EstadoDerechohabienteParserServiceLocal {
	
	DicEstadoDerechohabiente modelToPersist(EstadoDerechohabiente entrada) throws DerechohabientesBusinessException;
	EstadoDerechohabiente persisToModel(DicEstadoDerechohabiente entrada) throws DerechohabientesBusinessException;
}
