/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.ProrrogaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.ProrrogaParserServiceLocal;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.enums.EstadoProrrogaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.persistence.DitProrroga;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author ghdolores
 *
 */
@Stateless(name = "prorrogaDao", mappedName = "prorrogaDao")
public class ProrrogaDao extends AbstractServiceEntity implements ProrrogaDaoLocal{
	
	@EJB ProrrogaParserServiceLocal prorrogaParserServiceLocal;
	
	@Override
	public TramiteProrroga saveProrroga(TramiteProrroga prorroga) throws DerechohabientesBusinessException,Exception {
		DitProrroga ditProrroga=prorrogaParserServiceLocal.modelToPersist(prorroga);
		try {
			em.persist(ditProrroga);
			em.flush();
		} catch (Exception e) {
			log.error("saveProrroga", e);
			throw e;
		}
		
		
		return prorroga;
	}
	
	@Override
	public ConstanciaEstudio saveConstanciaEstudios(ConstanciaEstudio constancia){
		/*DitConstanciaEstudio ditConstancia = ConstanciaEstudioParser.modelToPersist(constancia);
		
		try {
			em.persist(ditConstancia);
			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteProrroga> getProrrogasActivas(Long idAsignacionNss,
			Long idPersona, Long tipoProrroga)
			throws DerechohabientesBusinessException, Exception {
		List<TramiteProrroga> prorrogas = null;
		Long estadoProrroga = EstadoProrrogaEnum.ACTIVA.getId();
		
		Criteria queryProrroga = this.getSession().createCriteria(DitProrroga.class);
		queryProrroga.createAlias("dicEstadoProrroga", "estadoPro");
		queryProrroga.add(Restrictions.eq("estadoPro.cveIdEstadoProrroga", estadoProrroga.intValue()));
		
		Criteria queryGrupo = queryProrroga.createCriteria("ditGrupoFamiliar");
		queryGrupo.add(Restrictions.eq("id.cveIdAsignacionNss", idAsignacionNss));
		queryGrupo.add(Restrictions.eq("id.cveIdPersonaIntegrante", idPersona));
		
		if(tipoProrroga != null) {
			queryProrroga.createAlias("dicTipoProrroga", "tipoP");
			queryProrroga.add(Restrictions.eq("tipoP.cveIdTipoProrroga", tipoProrroga));
		}
		
		
		List<DitProrroga> ditProrrogas = queryProrroga.list();
		
		if(ditProrrogas != null && !ditProrrogas.isEmpty()) {
			prorrogas = prorrogaParserServiceLocal.persistToModelList(ditProrrogas);
		}
		
		return prorrogas;
	}

	@SuppressWarnings("unchecked")
	public TramiteProrroga getProrrogaActivaPersona(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException, Exception{
		TramiteProrroga prorroga = null;
		Long estadoProrroga = EstadoProrrogaEnum.ACTIVA.getId();
		
		Criteria queryProrroga = this.getSession().createCriteria(DitProrroga.class);
		queryProrroga.createAlias("dicEstadoProrroga", "estadoPro");
		queryProrroga.add(Restrictions.eq("estadoPro.cveIdEstadoProrroga", estadoProrroga.intValue()));
		
		Criteria queryGrupo = queryProrroga.createCriteria("ditGrupoFamiliar");
		queryGrupo.add(Restrictions.eq("id.cveIdAsignacionNss", idAsignacionNss));
		queryGrupo.add(Restrictions.eq("id.cveIdPersonaIntegrante", idPersona));
		
		queryProrroga.addOrder(Order.desc("fecFinProrroga"));
		queryProrroga.setMaxResults(1);
		
		List<DitProrroga> prorrogas = queryProrroga.list();
		
		if(prorrogas != null && !prorrogas.isEmpty()) {
			prorroga = prorrogaParserServiceLocal.persisToModel(prorrogas.get(0));
		}
		
		return prorroga;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TramiteProrroga getProrrogaActiva(Long idAsignacionNSS,Long idPersona,Long idCaracter) throws DerechohabientesBusinessException,Exception{
		TramiteProrroga resultado =null;
		List<DitProrroga> resultados =null;
		Long estadoProrroga = EstadoProrrogaEnum.ACTIVA.getId();
		try {
			
			Criteria queryProrroga = this.getSession().createCriteria(DitProrroga.class);
			queryProrroga.createAlias("dicEstadoProrroga", "estadoPro");
			queryProrroga.add(Restrictions.eq("estadoPro.cveIdEstadoProrroga",estadoProrroga.intValue()));
			if(idCaracter != null) {
				queryProrroga.createAlias("dicCaracter", "caracter");
				queryProrroga.add(Restrictions.eq("caracter.cveIdCaracter", idCaracter));
			}
			
			Criteria queryGrupo = queryProrroga.createCriteria("ditGrupoFamiliar");
			queryGrupo.add(Restrictions.eq("id.cveIdAsignacionNss", idAsignacionNSS));
			queryGrupo.add(Restrictions.eq("id.cveIdPersonaIntegrante", idPersona));
			
			try{
				//resultados=em.createQuery(query).getResultList();
				resultados = queryProrroga.list();
			}catch (NoResultException e) {
				return null;
			}
			if(resultados.size()>0)
			resultado = prorrogaParserServiceLocal.persisToModel(resultados.get(0));
		} catch (Exception e) {
			log.error("getProrrogaActiva",e);
			throw e;
		}
		
		
		return resultado;
		
	}

	@Override
	public void updateProrroga(TramiteProrroga prorroga) throws Exception {
		DitProrroga unDitProrroga = new DitProrroga();
		unDitProrroga = prorrogaParserServiceLocal.modelToPersist(prorroga);
		try {
			em.merge(unDitProrroga);
			//em.flush();
		} catch (Exception e) {
			log.error("Error - updateProrroga, idProrroga: " + prorroga.getTramiteId() + ", idNss: " + prorroga.getGrupoFamiliar().getAsignacionNSS().getIdAsignacionNSS()
			+ " , idPersonaIntegrante: " + prorroga.getGrupoFamiliar().getDerechohabiente().getIdPersona(), e);
			throw e;
		}
	}



}
