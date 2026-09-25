package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;

@Local
public interface ProcesosTrabajoServiceBusinessLocal {
	
	Proceso getProceso(Proceso proceso) throws Exception;
	
	Proceso modificarProceso(Proceso roceso) throws Exception;

	Proceso capturarProceso(Proceso proceso) throws Exception;

}
