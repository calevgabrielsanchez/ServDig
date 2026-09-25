package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;

@Local
public interface AsentamientoUtilityLocal {
	DgAsentamiento modelToPersist(Asentamiento entrada);
	Asentamiento persisToModel(DgAsentamiento entrada);
}
