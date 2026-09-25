package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.persistence.DitCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;

@Local
public interface CircunscripcionForaneaParserServiceLocal {
	
	public DitCircunscripcionForanea modelToPersist(TramiteCircunscripcionForanea entrada) throws DerechohabientesBusinessException;
	public TramiteCircunscripcionForanea persistToModel(DitCircunscripcionForanea entrada) throws DerechohabientesBusinessException, Exception;
	
}
