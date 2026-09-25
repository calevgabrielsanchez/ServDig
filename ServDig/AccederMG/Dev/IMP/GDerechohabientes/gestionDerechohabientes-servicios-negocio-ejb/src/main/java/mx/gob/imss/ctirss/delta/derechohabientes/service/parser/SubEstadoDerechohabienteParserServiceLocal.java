package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DicSubestadoDerechohabiente;

@Local
public interface SubEstadoDerechohabienteParserServiceLocal {

	DicSubestadoDerechohabiente modelToPersist(SubEstadoDerechohabiente entrada) throws DerechohabientesBusinessException;
	SubEstadoDerechohabiente persisToModel(DicSubestadoDerechohabiente entrada) throws DerechohabientesBusinessException;
}
