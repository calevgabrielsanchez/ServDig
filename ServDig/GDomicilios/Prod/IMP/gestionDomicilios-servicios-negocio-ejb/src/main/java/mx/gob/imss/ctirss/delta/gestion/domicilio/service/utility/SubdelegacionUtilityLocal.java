package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;

@Local
public interface SubdelegacionUtilityLocal {

	DicSubdelegacion modelToPersist(Subdelegacion entrada) throws DerechohabientesBusinessException;
	Subdelegacion persisToModel(DicSubdelegacion entrada) throws DerechohabientesBusinessException;
}
