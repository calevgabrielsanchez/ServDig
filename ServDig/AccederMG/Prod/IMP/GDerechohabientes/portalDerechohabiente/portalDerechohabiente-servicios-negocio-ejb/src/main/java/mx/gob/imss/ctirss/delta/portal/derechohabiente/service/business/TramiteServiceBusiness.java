package mx.gob.imss.ctirss.delta.portal.derechohabiente.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.service.entity.TramiteServiceEntityLocal;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.service.interfaces.TramiteServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.service.utility.TramiteServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

/**
 * 
 * @author Lucio Duran Silva
 * 
 * 
 */
@Stateless(name = "tramiteServiceBusiness", mappedName = "tramiteServiceBusiness")
public class TramiteServiceBusiness extends AbstractServiceBusiness implements
		TramiteServiceBusinessRemote {

	@EJB
	private TramiteServiceEntityLocal tramiteServiceEntity;
	@EJB
	private TramiteServiceUtilityLocal tramiteServiceUtility;

	@Override
	public List<Tramite> obtenerTramitesDePersona(Fisica persona)
			throws PersonaNoEncontradaException {

		List<Tramite> model = null;
		List<DitTramite> entities = this.tramiteServiceEntity
				.getTramitesPorPersona(persona);

		if (entities == null || entities.isEmpty()) {
			throw new PersonaNoEncontradaException(persona.getIdPersona());
		} else {

			model = this.tramiteServiceUtility
					.convertirListaEntityToModel(entities);

		}
		return model;
	}

}
