package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.ActComplementaria;

@Remote
public interface ActividadComplementariaServiceBusinessRemote {
	
	ActComplementaria getActividadComplementaria(ActComplementaria actividadComplementaria) throws Exception;

	ActComplementaria modificarActividadComplementaria(ActComplementaria actividadComplementaria) throws Exception;

}
