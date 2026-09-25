package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAcuerdoDh;
import mx.gob.imss.ctirss.delta.persistence.DitAcuerdoDH;

@Local
public interface AcuerdoParserServiceLocal {
	
	TramiteAcuerdoDh convertEntityToModel(DitAcuerdoDH ditAcuerdo);
	DitAcuerdoDH converModelToEntity(TramiteAcuerdoDh tramite);
}
