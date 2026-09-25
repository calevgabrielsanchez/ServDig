package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.CircunscripcionForaneaParserServiceLocal;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.persistence.DitCircunscripcionForanea;

@Stateless(name = "circunscripcionEntity", mappedName = "circunscripcionEntity")
public class CircunscripcionEntity extends AbstractServiceEntity implements CircunscripcionEntityLocal{

	@EJB(name = "circunscripcionForaneaService") CircunscripcionForaneaParserServiceLocal circunscripcionForaneaParserServiceLocal;
	
	/**
	 * Metodo que obtiene la circunscripcion foranea a partir del id de tramite
	 * @param idCircunscripcion - EL id del tramite de la circunscripcion
	 */
	@Override
	public TramiteCircunscripcionForanea getCircunscripcionForanea(
			Long idCircunscripcion) throws Exception{
		
		DitCircunscripcionForanea ditCircunscripcion = null;
		
		try {
			Criteria query = this.getSession().createCriteria(DitCircunscripcionForanea.class);
			query.createAlias("ditTramite", "tramite");
			query.add(Restrictions.eq("tramite.cveIdTramite", idCircunscripcion));
			ditCircunscripcion = (DitCircunscripcionForanea) query.uniqueResult();
			
		} catch (Exception e) {
			log.error("getCircunscripcionForanea", e);
			throw e;
		}
		
		return circunscripcionForaneaParserServiceLocal.persistToModel(ditCircunscripcion);
	}

	/**
	 * Metodo para obtener la circunscripcion familiar de un integrante de un grupo familiar a partir de su id de persona
	 * y el nss del asegurado
	 * @param Long idPersona - El id del beneficiario
	 * @param AsignacionNss - El objeto nss del asegurado
	 * @param boolean - Indicador para saber si esta activa o inactiva la circunscripcon
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TramiteCircunscripcionForanea getCircunscripcionForanea(
			Long idPersona, AsignacionNSS nss, boolean autorizacion)
			throws DerechohabientesBusinessException, Exception {
		
		DitCircunscripcionForanea circunscripcionF = null;
		
		try {
			Criteria queryCircunscripcion = this.getSession().createCriteria(DitCircunscripcionForanea.class);
			
			if(!autorizacion){
				queryCircunscripcion.createAlias("ditTramiteSuspension", "tramiteS");
				queryCircunscripcion.addOrder(Order.desc("fecFinCircunscripcion"));
			}
			
			if(autorizacion){
				queryCircunscripcion.add(Restrictions.isNull("fecFinCircunscripcion"));
				queryCircunscripcion.add(Restrictions.eq("indCircunscripcionActiva", 1));
			} else {
				queryCircunscripcion.add(Restrictions.isNotNull("tramiteS.cveIdTramite"));
			}
			
			Criteria queryTramite = queryCircunscripcion.createCriteria("ditTramite");
			Criteria queryTramitePersonaFisica = queryTramite.createCriteria("ditTramitePersonaFisica");
			queryTramitePersonaFisica.createAlias("ditPersona", "persona");
			queryTramitePersonaFisica.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			
			Criteria queryPersonaInteresada = queryTramite.createCriteria("ditSolicitud").createCriteria("ditPersonaInteresadaSols");
			queryPersonaInteresada.createAlias("dicTipoPersonaInteresadaSol", "tipoPerInt");
			queryPersonaInteresada.createAlias("ditPersona", "perInt");
			queryPersonaInteresada.add(Restrictions.eq("perInt.cveIdPersona", nss.getIdPersona()));
			queryPersonaInteresada.add(Restrictions.eq("tipoPerInt.cveTipoInteresadaSol", TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
			
			List<DitCircunscripcionForanea> circunscripciones = queryCircunscripcion.list();
			
			if(circunscripciones !=null && circunscripciones.size() > 0)
				circunscripcionF = circunscripciones.get(0);
		} catch (Exception e) {
			log.error("getCircunscripcionForanea", e);
			throw e;
		}
		
		
		return circunscripcionForaneaParserServiceLocal.persistToModel(circunscripcionF);
	}

	/**
	 * Metodo para obtener uns circunscripcion foranea a partir del id del tramite de suspencion
	 * Se consulta la tabla ditcircunscripcionforanea pero se compara el idTramiteSuspencion
	 */
	@Override
	public TramiteCircunscripcionForanea getSuspencionCircunscripcionForane(
			Long idTramiteSuspencion) throws Exception {
		DitCircunscripcionForanea ditCircunscripcion = null;
		
		try {
			
			Criteria queryCircunscripcion = this.getSession().createCriteria(DitCircunscripcionForanea.class);
			queryCircunscripcion.createAlias("ditTramiteSuspension", "suspension");
			queryCircunscripcion.add(Restrictions.eq("suspension.cveIdTramite", idTramiteSuspencion));
			
			ditCircunscripcion = (DitCircunscripcionForanea) queryCircunscripcion.uniqueResult();
			
		} catch (Exception e) {
			log.error("getSuspencionCircunscripcionForane", e);
			throw e;
		}
		
		return circunscripcionForaneaParserServiceLocal.persistToModel(ditCircunscripcion);
	}

	/**
	 * 
	 */
	@Override
	public void updateCircunscripcionForanea(
			TramiteCircunscripcionForanea circunscripcion)
			throws DerechohabientesBusinessException, Exception {
		DitCircunscripcionForanea ditCircunscripcionForanea = circunscripcionForaneaParserServiceLocal.modelToPersist(circunscripcion);
		try {
			em.merge(ditCircunscripcionForanea);
		} catch (Exception e) {
			log.error("updateCircunscripcionForanea", e);
			throw e;
		}
		
	}

	@Override
	public void saveCircunscripcionForanea(
			TramiteCircunscripcionForanea circunscripcion)
			throws DerechohabientesBusinessException, Exception {
		DitCircunscripcionForanea ditCircunscripcion = circunscripcionForaneaParserServiceLocal.modelToPersist(circunscripcion);
		try {
			em.persist(ditCircunscripcion);
			em.flush();
		} catch (Exception e) {
			log.error("saveCircunscripcionForanea", e);
			throw e;
		}
	}
	
	
}