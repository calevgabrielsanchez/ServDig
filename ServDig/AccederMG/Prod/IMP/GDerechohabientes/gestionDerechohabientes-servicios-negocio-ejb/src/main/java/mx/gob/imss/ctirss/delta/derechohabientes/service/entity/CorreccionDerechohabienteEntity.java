package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.CorreccionParserServiceLocal;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionDatoDerechohab;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "correccionDerechohabienteEntity", mappedName = "correccionDerechohabienteEntity")
public class CorreccionDerechohabienteEntity extends AbstractServiceEntity implements
		CorreccionDerechohabienteEntityLocal {

	@EJB(name = "correccionDatoDerechohabienteParser") CorreccionParserServiceLocal correccionParser;
	
	@Override
	public void saveCorreccionDerechohabiente(TramiteCorreccionDerechohabiente correccion) throws DerechohabientesBusinessException, Exception {
		
		DitCorreccionDatoDerechohab ditCorreccion = null;
		
		Criteria queryCorreccion = this.getSession().createCriteria(DitCorreccionDatoDerechohab.class);
		queryCorreccion.createAlias("ditTramite", "tramite");
		queryCorreccion.add(Restrictions.eq("tramite.cveIdTramite", correccion.getTramiteId()));
		
		List<DitCorreccionDatoDerechohab> ditCorrecciones = queryCorreccion.list();
		
		if(ditCorrecciones == null || ditCorrecciones.isEmpty()) {
			ditCorreccion = correccionParser.modelToPersist(correccion);
			
			try {
				em.persist(ditCorreccion);
				em.flush();
			} catch (Exception e) {
				log.error("saveCorreccionDerechohabiente", e);
				throw e;
			}
		}

	}

}
