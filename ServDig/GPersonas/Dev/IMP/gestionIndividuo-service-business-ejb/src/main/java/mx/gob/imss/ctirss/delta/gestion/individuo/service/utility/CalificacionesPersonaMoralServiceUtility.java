package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DicPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalific;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;

@Stateless(name = "calificacionesPersonaMoralServiceUtility", mappedName = "calificacionesPersonaMoralServiceUtility")
public class CalificacionesPersonaMoralServiceUtility extends
		AbstractServiceUtility implements
		CalificacionesPersonaMoralServiceUtilityLocal {

	@Override
	public PersonaCalificacion transformarAModelo(
			DitHistPersonaMoralCalific ditHistPersonaMoralCalific) {

		return null;
	}

	@Override
	public DitHistPersonaMoralCalific transformarAEntidad(
			PersonaCalificacion personaCalificacion, Moral moral) {

		DitHistPersonaMoralCalific ditHistPersonaMoralCalific = null;
		DitPersonaMoral ditPersonaMoral = null;

		if (personaCalificacion != null) {

			DicPersonaCalificacion dicPersonaCalificacion = new DicPersonaCalificacion();
			dicPersonaCalificacion.setCveIdCalificacion(personaCalificacion
					.getCalificacion().getIdCalificacion());

			ditHistPersonaMoralCalific = new DitHistPersonaMoralCalific();
			ditHistPersonaMoralCalific
					.setDicPersonaCalificacion(dicPersonaCalificacion);

			ditPersonaMoral = new DitPersonaMoral();
			ditPersonaMoral.setCveIdPersonaMoral(moral.getCveMoral());

			ditHistPersonaMoralCalific.setDitPersonaMoral(ditPersonaMoral);

			ditPersonaMoral.getDitHistPersonaMoralCalifics().add(
					ditHistPersonaMoralCalific);

		}

		return ditHistPersonaMoralCalific;
	}

}
