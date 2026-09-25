package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.persistence.DitProceso;

@Local
public interface ProcesosTrabajoServiceUtilityLocal {
	
	/**
	 * Genera un objeto DitProceso con la información
	 * proporcionada
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return DitProceso
	 */
	DitProceso convertirModelToEntityProceso(Proceso model);
	
	/**
	 * Genera un objeto Proceso con la información
	 * proporcionada
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * Proceso
	 */
	Proceso convertirEntityToModelProceso(DitProceso entity);

}
