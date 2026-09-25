package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.ActComplementaria;

@Local
public interface ActividadComplementariaServiceBusinessLocal {
	
	ActComplementaria getActividadComplementaria(ActComplementaria actividadComplementaria) throws Exception;

	ActComplementaria modificarActividadComplementaria(ActComplementaria actividadComplementaria) throws Exception;

}
