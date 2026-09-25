package mx.gob.imss.cit.cda.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionDatosAsegurado;

@Local
public interface TramiteCDAUtilityLocal {
	TramiteCorreccionCurp convertirEntityToModel(DitCorreccionDatosAsegurado entity);
}
