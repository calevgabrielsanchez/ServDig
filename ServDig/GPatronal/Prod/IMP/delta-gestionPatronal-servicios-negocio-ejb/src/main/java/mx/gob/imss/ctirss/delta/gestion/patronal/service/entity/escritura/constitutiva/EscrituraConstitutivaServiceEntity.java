package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.escritura.constitutiva;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.ServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.escritura.constitutiva.EscrituraConstitutivaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.persistence.DitActaConstitutiva;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitSindicato;

@Stateless
public class EscrituraConstitutivaServiceEntity extends ServiceEntity implements
		EscrituraConstitutivaServiceEntityLocal {
	
	@EJB
	EscrituraConstitutivaServiceUtilityLocal escrituraConstitutivaServiceUtilityLocal;

	@Override
	public EscrituraConstitutiva actualizarEscrituraConstitutiva(
			EscrituraConstitutiva escrituraConstitutiva)
			throws GestionPatronalBusinessException {
		
		DitActaConstitutiva ditActaConstitutiva = null;

		try {
			// cargamos escirtura constitutiva existente
			if (escrituraConstitutiva.getCveEscrituraConstitutiva() != null&&escrituraConstitutiva.getCveEscrituraConstitutiva() !=0){
				ditActaConstitutiva = (DitActaConstitutiva) this
						.getSession().load(
								DitActaConstitutiva.class,
								new Integer(escrituraConstitutiva
										.getCveEscrituraConstitutiva()
										.toString()));
			}
			ditActaConstitutiva = this.escrituraConstitutivaServiceUtilityLocal
									.asignarvaloresFaltantes(escrituraConstitutiva,
											ditActaConstitutiva);
			DitPersonaMoral persona = (DitPersonaMoral)this.getSession().load(DitPersonaMoral.class, 
					ditActaConstitutiva.getDitPersonaMoral().getCveIdPersonaMoral());
			ditActaConstitutiva.setDitPersonaMoral(persona);
			this.getSession().saveOrUpdate(ditActaConstitutiva);
			if(persona.getDitSindicatos()!=null && !persona.getDitSindicatos().isEmpty())
				for(DitSindicato sindicato:persona.getDitSindicatos())
					this.getSession().delete(sindicato);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new GestionPatronalBusinessException(e.getMessage());
		}
		return escrituraConstitutiva;
		
	}
	
	@Override
	public EscrituraConstitutiva consultarEscritura(Long idActa){
		EscrituraConstitutiva escritura =null;
		DitActaConstitutiva ditActa= this.getEntityManager().find(DitActaConstitutiva.class, idActa.intValue());
		escritura = escrituraConstitutivaServiceUtilityLocal.convertirEntityToModel(ditActa);
		return escritura;
	}

}
