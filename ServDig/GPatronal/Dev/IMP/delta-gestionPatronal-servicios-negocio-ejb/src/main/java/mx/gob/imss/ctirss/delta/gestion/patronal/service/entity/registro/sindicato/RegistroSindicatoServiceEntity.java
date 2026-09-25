package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.registro.sindicato;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.ServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.registro.sindicato.RegistroSindicatoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.persistence.DitActaConstitutiva;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitSindicato;

@Stateless
public class RegistroSindicatoServiceEntity extends ServiceEntity implements
		RegistroSindicatoServiceEntityLocal {
	
	@EJB
	RegistroSindicatoServiceUtilityLocal registroSindicatoServiceUtilityLocal;

	@Override
	public RegistroSindicato actualizarRegistroSindicato(
			RegistroSindicato registroSindicato)
			throws GestionPatronalBusinessException {
		
		DitSindicato ditSindicato = null;
		
		try {
			
			// cargamos registroSindicato existente
			if (registroSindicato.getCveRegistroSindicato() != null&&registroSindicato.getCveRegistroSindicato() != 0) {
				ditSindicato = (DitSindicato) this.getSession().load(
						DitSindicato.class,
						registroSindicato.getCveRegistroSindicato());
			}
			ditSindicato = this.registroSindicatoServiceUtilityLocal.asignarvaloresFaltantes(
					registroSindicato,
					ditSindicato);
			
			DitPersonaMoral pMoral = (DitPersonaMoral)this.getSession().load(DitPersonaMoral.class, 
					ditSindicato.getDitPersonaMoral().getCveIdPersonaMoral());
			ditSindicato.setDitPersonaMoral(pMoral);
			this.getSession()
					.saveOrUpdate(ditSindicato);
			
			if(pMoral.getDitActaConstitutivas()!=null&&!pMoral.getDitActaConstitutivas().isEmpty())
				for(DitActaConstitutiva acta:pMoral.getDitActaConstitutivas())
					this.getSession().delete(acta);
				
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// TODO yorch: definir el codigo de excepcion a tratar
			throw new GestionPatronalBusinessException();
		}
		return registroSindicato;
	}

}
