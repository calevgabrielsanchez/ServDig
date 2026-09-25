package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacionPK;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalific;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalificPK;

/**
 * 111012
 * 
 * @author ICCSRG
 * 
 */
@Stateless(name = "calificacionesPersonaEntityService", mappedName = "calificacionesPersonaEntityService")
public class CalificacionesPersonaEntityService extends
		AbstractServiceEntity implements
		CalificacionesPersonaEntityServiceLocal {

	@Override
	public void registrar(DitHistPersonaCalificacion ditHistPersonaCalificacion) {

		Date fechaAlta = new Date();

		ditHistPersonaCalificacion.setFecRegistroAlta(fechaAlta);
		
		if (ditHistPersonaCalificacion.getId() == null) {
			DitHistPersonaCalificacionPK idCalificacion = new DitHistPersonaCalificacionPK();
			idCalificacion.setCveIdCalificacion(ditHistPersonaCalificacion
					.getDicPersonaCalificacion().getCveIdCalificacion());
			idCalificacion.setCveIdPersona(ditHistPersonaCalificacion
					.getDitPersona().getCveIdPersona());
	
			ditHistPersonaCalificacion.setId(idCalificacion);
		}

		em.persist(ditHistPersonaCalificacion);

	}
	
	@Override
	public void registrar(DitHistPersonaMoralCalific ditHistPersonaMoralCalific) {

		Date fechaAlta = new Date();

		ditHistPersonaMoralCalific.setFecRegistroAlta(fechaAlta);
		
		if (ditHistPersonaMoralCalific.getId() == null) {			
			DitHistPersonaMoralCalificPK idCalificacion = new DitHistPersonaMoralCalificPK();
			idCalificacion.setCveIdCalificacion(ditHistPersonaMoralCalific
					.getDicPersonaCalificacion().getCveIdCalificacion());
			idCalificacion.setCveIdPersonaMoral(ditHistPersonaMoralCalific
					.getDitPersonaMoral().getCveIdPersonaMoral());
			ditHistPersonaMoralCalific.setId(idCalificacion);
		}

		em.persist(ditHistPersonaMoralCalific);
	}
	
	@Override
	public void actualizar(DitHistPersonaCalificacion ditHistPersonaCalificacion) {

		ditHistPersonaCalificacion = this.em.find(
				DitHistPersonaCalificacion.class,
				ditHistPersonaCalificacion.getId());

		ditHistPersonaCalificacion.setFecRegistroActualizado(new Date());

	}
	
	@Override
	public void actualizar(DitHistPersonaMoralCalific ditHistPersonaMoralCalific) {
		
		ditHistPersonaMoralCalific = this.em.find(
				DitHistPersonaMoralCalific.class,
				ditHistPersonaMoralCalific.getId());
		
		ditHistPersonaMoralCalific.setFecRegistroActualizado(new Date());
		
	}
	
	@Override
	public void expirar(DitHistPersonaCalificacion ditHistPersonaCalificacion) {

		ditHistPersonaCalificacion = this.em.find(
				DitHistPersonaCalificacion.class,
				ditHistPersonaCalificacion.getId());

		ditHistPersonaCalificacion.setFecRegistroBaja(new Date());

	}
	
	@Override
	public void expirar(DitHistPersonaMoralCalific ditHistPersonaMoralCalific) {
		
		ditHistPersonaMoralCalific = this.em.find(
				DitHistPersonaMoralCalific.class,
				ditHistPersonaMoralCalific.getId());
		
		ditHistPersonaMoralCalific.setFecRegistroBaja(new Date());
		
	}
	
	@Override
	public void reactivar(DitHistPersonaCalificacion ditHistPersonaCalificacion) {

		ditHistPersonaCalificacion = this.em.find(
				DitHistPersonaCalificacion.class,
				ditHistPersonaCalificacion.getId());

		ditHistPersonaCalificacion.setFecRegistroActualizado(new Date());
		ditHistPersonaCalificacion.setFecRegistroBaja(null);

	}
	
	@Override
	public void reactivar(DitHistPersonaMoralCalific ditHistPersonaMoralCalific) {
		
		ditHistPersonaMoralCalific = this.em.find(
				DitHistPersonaMoralCalific.class,
				ditHistPersonaMoralCalific.getId());
		
		ditHistPersonaMoralCalific.setFecRegistroActualizado(new Date());
		ditHistPersonaMoralCalific.setFecRegistroBaja(null);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DitHistPersonaCalificacion> consultarCalificacionesPersona(
			Fisica fisica, boolean getSoloVigentes) {

		StringBuffer jpaQuery = new StringBuffer();

		jpaQuery.append("from DitHistPersonaCalificacion calif ");
		jpaQuery.append("where calif.ditPersona.cveIdPersona = :idPersona ");

		if (getSoloVigentes) {
			jpaQuery.append("and calif.fecRegistroBaja is null");
		}

		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idPersona", fisica.getIdPersona());

		List<DitHistPersonaCalificacion> calificaciones = query.getResultList();

		return calificaciones;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitHistPersonaMoralCalific> consultarCalificacionesPersonaMoral(
			Moral moral, boolean getSoloVigentes) {
		
		StringBuffer jpaQuery = new StringBuffer();

		jpaQuery.append("from DitHistPersonaMoralCalific calif ");
		jpaQuery.append("where calif.ditPersonaMoral.cveIdPersonaMoral = :idPersonaMoral ");

		if (getSoloVigentes) {
			jpaQuery.append("and calif.fecRegistroBaja is null");
		}

		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idPersonaMoral", moral.getCveMoral());

		List<DitHistPersonaMoralCalific> calificaciones = query.getResultList();

		return calificaciones;
	}
}
