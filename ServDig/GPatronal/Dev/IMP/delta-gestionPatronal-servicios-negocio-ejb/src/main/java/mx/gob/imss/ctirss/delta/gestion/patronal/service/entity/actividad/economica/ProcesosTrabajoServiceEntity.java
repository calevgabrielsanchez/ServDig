package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.ProcesosTrabajoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitProceso;

@Stateless
public class ProcesosTrabajoServiceEntity extends AbstractServiceEntity
		implements ProcesosTrabajoServiceEntityLocal {
	
	@EJB
	ProcesosTrabajoServiceUtilityLocal utility;
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.ProcesosTrabajoServiceEntityLocal#actualizarProceso(mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso)
	 */
	@Override
	public void actualizarProceso(Proceso proceso) {
		DitProceso entity = utility.convertirModelToEntityProceso(proceso);
		DitPatronSujetoObligado sujetoObligado = (DitPatronSujetoObligado)this.getSession().load(
				DitPatronSujetoObligado.class, proceso.getSujetoObligado().getCveIdSujetoObligado());
		DitProceso actualEntity = (DitProceso)this.getSession().load(
				DitProceso.class, proceso.getClave());
		
		actualEntity.setDesFinal(entity.getDesFinal());
		actualEntity.setDesInicial(entity.getDesInicial());
		actualEntity.setDesIntermedio(entity.getDesIntermedio());
		actualEntity.setFecRegistroActualizado(Calendar.getInstance().getTime());
		
		actualEntity.setDitPatronSujetoObligado(sujetoObligado);
		
		this.getSession().update(actualEntity);
		
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.ProcesosTrabajoServiceEntityLocal#agregarProceso(mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso)
	 */
	@Override
	public Proceso agregarProceso(Proceso proceso) {
		DitProceso entity = utility.convertirModelToEntityProceso(proceso);
		entity.setFecRegistroAlta(Calendar.getInstance().getTime());
		DitPatronSujetoObligado patron = this.em.find(DitPatronSujetoObligado.class, proceso.getSujetoObligado().getCveIdSujetoObligado());
		entity.setDitPatronSujetoObligado(patron);
		this.em.persist(entity);
		proceso.setClave(entity.getCveIdProceso());
		return proceso;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.ProcesosTrabajoServiceEntityLocal#consultarProceso(java.lang.Long)
	 */
	@Override
	public Proceso consultarProceso(Long claveProceso) {
		DitProceso entity = this.em.find(DitProceso.class, claveProceso);
		return utility.convertirEntityToModelProceso(entity);
	}

	

}
