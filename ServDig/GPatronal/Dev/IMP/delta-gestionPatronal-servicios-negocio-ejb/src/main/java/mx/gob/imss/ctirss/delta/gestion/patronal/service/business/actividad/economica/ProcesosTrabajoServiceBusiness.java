package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.ProcesosTrabajoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.ProcesosTrabajoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;

@Stateless(name="procesosTrabajoServiceBusiness", mappedName="procesosTrabajoServiceBusiness")
public class ProcesosTrabajoServiceBusiness extends AbstractServiceBusiness
		implements ProcesosTrabajoServiceBusinessRemote,
		ProcesosTrabajoServiceBusinessLocal {
	
	@EJB
	ProcesosTrabajoServiceEntityLocal entity;
	
	@Override
	public Proceso getProceso(Proceso proceso) throws Exception {
		if(proceso.getClave()==null){
			throw new GestionPatronalBusinessException("La clave del proceso a obtener es requerida");
		}
		proceso = entity.consultarProceso(proceso.getClave()); 
		System.out.println("Proceso retornado: "+proceso);
		return proceso;
	}

	@Override
	public Proceso modificarProceso(Proceso proceso) throws Exception {
		if(proceso.getClave()==null){
			throw new GestionPatronalBusinessException("La clave del proceso a modificar es requerida");
		}
		entity.actualizarProceso(proceso);
		return proceso;
	}

	@Override
	public Proceso capturarProceso(Proceso proceso) throws Exception {
		return entity.agregarProceso(proceso);
	}


}
