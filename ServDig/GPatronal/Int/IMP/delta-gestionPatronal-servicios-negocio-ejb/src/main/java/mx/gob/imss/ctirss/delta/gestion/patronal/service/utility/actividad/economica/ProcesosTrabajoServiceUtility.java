package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitProceso;

@Stateless
public class ProcesosTrabajoServiceUtility extends AbstractServiceUtility
		implements ProcesosTrabajoServiceUtilityLocal {

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.ProcesosTrabajoServiceUtilityLocal#convertirEntityToModelProceso(mx.gob.imss.ctirss.delta.persistence.DitProceso)
	 */
	@Override
	public Proceso convertirEntityToModelProceso(DitProceso entity) {
		Proceso model = new Proceso();
		model.setClave(entity.getCveIdProceso());
		model.setDesInicial(entity.getDesInicial());
		model.setDesIntermedio(entity.getDesIntermedio());
		model.setDesFinal(entity.getDesFinal());
		
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setCveIdSujetoObligado(
				entity.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
		model.setSujetoObligado(sujetoObligado);
		
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.ProcesosTrabajoServiceUtilityLocal#convertirModelToEntityProceso(mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso)
	 */
	@Override
	public DitProceso convertirModelToEntityProceso(Proceso model) {
		DitProceso entity = new DitProceso();
		if(model.getClave() != null){
			entity.setCveIdProceso(model.getClave());
		}
		
		entity.setDesInicial(model.getDesInicial());
		entity.setDesIntermedio(model.getDesIntermedio());
		entity.setDesFinal(model.getDesFinal());
		
		if(model.getSujetoObligado() != null){
			DitPatronSujetoObligado so = new DitPatronSujetoObligado();
			so.setCveIdPatronSujetoObligado(model.getSujetoObligado().getCveIdSujetoObligado());
			entity.setDitPatronSujetoObligado(so);
		}
		
		return entity;
	}


}
