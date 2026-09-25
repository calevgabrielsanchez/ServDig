package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.AcuerdoParserServiceLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAcuerdoDh;
import mx.gob.imss.ctirss.delta.persistence.DitAcuerdoDH;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "acuerdoDerechohabienteEntity", mappedName="acuerdoDerechohabienteEntity" )
public class AcuerdoDerechohabienteEntity extends AbstractServiceEntity implements AcuerdoDerechohabienteEntityLocal {

	@EJB
	private AcuerdoParserServiceLocal acuerdoParserServiceLocal;
	
	@Override
	public TramiteAcuerdoDh saveAcuerdoDerechohabiente(
			TramiteAcuerdoDh tramiteAcuerdo) {
		
		DitAcuerdoDH ditAcuerdo = acuerdoParserServiceLocal.converModelToEntity(tramiteAcuerdo);
		this.em.persist(ditAcuerdo);
		tramiteAcuerdo.setIdAcuerdo(ditAcuerdo.getCveIdAcuerdoDH());
		
		return tramiteAcuerdo;
	}
	
	
	
	@Override
	public TramiteAcuerdoDh getAcuerdoDerechohabiente(Long idAsignacionNSS,
			Long idPersona, Long idEstadoAcuerdo) {
		
		TramiteAcuerdoDh tramiteAcuerdo = null;
		Criteria criteria = this.getSession().createCriteria(DitAcuerdoDH.class);
		Criteria queryGrupo = criteria.createCriteria("ditGrupoFamiliar");
		queryGrupo.add(Restrictions.eq("id.cveIdPersonaIntegrante", idPersona));
		queryGrupo.add(Restrictions.eq("id.cveIdAsignacionNss", idAsignacionNSS));
		
		if(idEstadoAcuerdo != null) {
			criteria.createAlias("dicEstadoAcuerdoDH", "estado");
			criteria.add(Restrictions.eq("estado.cveIdEstadoAcuerdoDH", idEstadoAcuerdo));
		}
		
		List<DitAcuerdoDH> ditAcuerdos = criteria.list();
		
		if(ditAcuerdos != null && !ditAcuerdos.isEmpty()) {
			tramiteAcuerdo = acuerdoParserServiceLocal.convertEntityToModel(ditAcuerdos.get(0));
		}
		return tramiteAcuerdo;
	}



	@Override
	public TramiteAcuerdoDh getTramiteAcuerdoById(Long cveAcuerdo) {
		
		TramiteAcuerdoDh tramiteAcuerdo = null;
		
		DitAcuerdoDH ditAcuerdoDh = em.find(DitAcuerdoDH.class, cveAcuerdo);
		
		if(ditAcuerdoDh != null) {
			tramiteAcuerdo = acuerdoParserServiceLocal.convertEntityToModel(ditAcuerdoDh);
		}
		
		return tramiteAcuerdo;
	}



	@Override
	public Boolean tieneAcuerdoVigente(Long idAsignacionNSS, Long idPersona) {

		Long numeroAcuerdos = 0L;
		
		
		Criteria queryAcuerdo = this.getSession().createCriteria(DitAcuerdoDH.class);
		queryAcuerdo.setProjection(Projections.rowCount());
		Criteria queryGrupo = queryAcuerdo.createCriteria("ditGrupoFamiliar");
		queryGrupo.add(Restrictions.eq("id.cveIdPersonaIntegrante", idPersona));
		queryGrupo.add(Restrictions.eq("id.cveIdAsignacionNss", idAsignacionNSS));
		queryAcuerdo.createAlias("dicEstadoAcuerdoDH", "estado");
		queryAcuerdo.add(Restrictions.eq("estado.cveIdEstadoAcuerdoDH", 1L));

		try{
			numeroAcuerdos = (Long) queryAcuerdo.uniqueResult();
		}catch(Exception e) {
			log.error("No se pudo consultar la calidad mas alta",e);
		}

		return numeroAcuerdos.intValue() > 0;
	}
	
}