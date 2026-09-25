package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CalificacionesPersonaMoralServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalific;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalificPK;

/**
 * 
 * @date 15/03/2013
 * @author Marco Sánchez
 * 
 */
@Stateless(name = "calificacionesPersonaMoralServiceEntity", mappedName = "calificacionesPersonaMoralServiceEntity")
public class CalificacionesPersonaMoralServiceEntity extends
		AbstractServiceEntity implements
		CalificacionesPersonaMoralServiceEntityLocal {

	@EJB
	private CalificacionesPersonaMoralServiceUtilityLocal calificacionesPersonaMoralServiceUtility;
	
	@Override
	public void registrar(PersonaCalificacion personaCalificacion, Moral moral) {

		Date fechaAlta = new Date();

		DitHistPersonaMoralCalific ditHistPersonaMoralCalific = this.calificacionesPersonaMoralServiceUtility
				.transformarAEntidad(personaCalificacion, moral);
		
		ditHistPersonaMoralCalific.setFecRegistroActualizado(fechaAlta);
		ditHistPersonaMoralCalific.setFecRegistroAlta(fechaAlta);

		DitHistPersonaMoralCalificPK idCalificacion = new DitHistPersonaMoralCalificPK();
		
		idCalificacion.setCveIdCalificacion(ditHistPersonaMoralCalific
				.getDicPersonaCalificacion().getCveIdCalificacion());
		idCalificacion.setCveIdPersonaMoral(ditHistPersonaMoralCalific
				.getDitPersonaMoral().getCveIdPersonaMoral());

		ditHistPersonaMoralCalific.setId(idCalificacion);

		em.persist(ditHistPersonaMoralCalific);

	}
}
