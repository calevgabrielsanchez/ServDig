package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;

@Local
public interface DelegacionUtilityLocal {

	DicDelegacion modelToPersist(Delegacion entrada) throws DerechohabientesBusinessException;
	Delegacion persisToModel(DicDelegacion entrada) throws DerechohabientesBusinessException;
}
