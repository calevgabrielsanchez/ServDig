package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.persistence.DitAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;

@Local
public interface AseguradoUtilityLocal {
	Asegurado convertirEntityToModel(DitAsegurado ditAsegurado);

	DitAsegurado convertirModelToEntity(Asegurado asegurado);

	AsignacionNSS convertirAsignacionNSSEntityToModel(
			DitAsignacionNss ditAsignacionNss);

	DitAsignacionNss convertirAsignacionNSSModelToEntity(
			AsignacionNSS asignacionNSS);
}
