package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.fusionsust;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitPatronFusion;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;

@SuppressWarnings("unchecked")
@Stateless(name = "patronFusionEntity" , mappedName = "patronFusionEntity")
public class PatronFusionEntity extends AbstractServiceEntity implements PatronFusionEntityLocal {

	
	@Override
	public Boolean existeFusion(Long idPatronGeneral, Long idPatronGeneralFusionado) {

		Criteria query = this.getSession().createCriteria(DitPatronFusion.class);
		query.add(Restrictions.eq("ditPatronGeneral1.cveIdPatronGeneral", idPatronGeneral));
		query.add(Restrictions.eq("ditPatronGeneral2.cveIdPatronGeneral", idPatronGeneralFusionado));
		query.setProjection(Projections.property("cveIdPatronFusion"));
		
		List<Long> resultado = (List<Long>) query.list();
		
		log.debug("Se encontro la relacion con id " + resultado);
		
		return resultado != null && !resultado.isEmpty();
	}

	@Override
	public void insertarFusion(Long idPatron, Long idPatronFusionado) {
		
		DitPatronFusion ditPatronFusion = new DitPatronFusion();
		ditPatronFusion.setDitPatronGeneral1(new DitPatronGeneral());
		ditPatronFusion.getDitPatronGeneral1().setCveIdPatronGeneral(idPatron);
		ditPatronFusion.setDitPatronGeneral2(new DitPatronGeneral());
		ditPatronFusion.getDitPatronGeneral2().setCveIdPatronGeneral(idPatronFusionado);
		ditPatronFusion.setFecRegistroAlta(new Date());
		
		this.em.persist(ditPatronFusion);
		
	}

	@Override
	public Long getCveIdPatronGeneralPorIdSujetoObligado(Long idPatronSujetoObligado) {
		Long idPatronGeneral = null;
		
		Criteria queryPatronGeneral = this.getSession().createCriteria(DitPatronGeneral.class);
		queryPatronGeneral.createAlias("ditPatronSujetoObligado", "sujetoO");
		queryPatronGeneral.add(Restrictions.eq("sujetoO.cveIdPatronSujetoObligado",idPatronSujetoObligado));
		queryPatronGeneral.setProjection(Projections.property("cveIdPatronGeneral"));

		idPatronGeneral = (Long) queryPatronGeneral.uniqueResult();
		
		
		return idPatronGeneral;
	}

	@Override
	public Boolean validarPatronExisteComoFusionado(Long idPatronAValidar) {
		Criteria query = this.getSession().createCriteria(DitPatronFusion.class);
		query.add(Restrictions.eq("ditPatronGeneral2.cveIdPatronGeneral", idPatronAValidar));
		query.setProjection(Projections.property("cveIdPatronFusion"));
		
		List<Long> patronesRelacionados = (List<Long>)query.list();
		
		log.error("Se encuentran las relaciones " + patronesRelacionados);
		
		return patronesRelacionados != null && !patronesRelacionados.isEmpty();
	}

}
