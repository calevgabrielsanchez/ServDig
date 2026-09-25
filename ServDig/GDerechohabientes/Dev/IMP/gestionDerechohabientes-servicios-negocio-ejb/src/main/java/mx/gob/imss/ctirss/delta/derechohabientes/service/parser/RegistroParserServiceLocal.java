package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitRegistroDerechohabiente;

@Local
public interface RegistroParserServiceLocal {

	DitRegistroDerechohabiente modelToPersist(TramiteRegistroDerechohabiente entrada) throws DerechohabientesBusinessException;
	TramiteRegistroDerechohabiente persisToModel(DitRegistroDerechohabiente entrada) throws DerechohabientesBusinessException;
	List<TramiteRegistroDerechohabiente> persisToModelList(List<DitRegistroDerechohabiente> entrada) throws DerechohabientesBusinessException;
}
