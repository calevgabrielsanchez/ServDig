package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;

@Local
public interface ProcesosTrabajoServiceEntityLocal {
	/**
	 * Inserta un nuevo proceso en BD.
	 * El proceso consta de tareas en cada fase:
	 * INICIAL, INTERMEDIA y FINAL.
	 * El identificador del registro insertado es devuelto en el
	 * objeto Proceso
	 * @author Hugo Armando Martínez Chamónica
	 * @param proceso
	 * @return Proceso
	 */
	Proceso agregarProceso(Proceso proceso);
	
	/**
	 * Modifica la información del proceso:
	 * INICIAL, INTERMEDIO Y FINAL
	 * asociado con el sujeto obligado proporcionado
	 * @author Hugo Armando Martínez Chamónica
	 * @param proceso
	 * void
	 */
	void actualizarProceso(Proceso proceso);
	
	/**
	 * Obtiene la información del proceso en base
	 * a su identificador
	 * @author Hugo Armando Martínez Chamónica
	 * @param claveProceso Identificador del proceso
	 * @return Proceso
	 */
	Proceso consultarProceso(Long claveProceso);

}
