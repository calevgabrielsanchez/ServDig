package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;

@Remote
public interface ProcesosTrabajoServiceBusinessRemote {
	
	Proceso getProceso(Proceso proceso) throws Exception;
	
	Proceso modificarProceso(Proceso roceso) throws Exception;

	Proceso capturarProceso(Proceso proceso) throws Exception;

}
