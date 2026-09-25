package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsignacionNSSParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNssCL3;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "asignacionNssDao", mappedName = "asignacionNssDao")


public class AsignacionNssDao extends AbstractServiceEntity implements AsignacionNssDaoLocal {
	
	private static final Logger logger = Logger.getLogger(AsignacionNssDao.class);
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.derechohabientes.service.dao.AsignasionNssDaoLocal#existSolicitudRegistrobyNSS(java.lang.String)
	 */
	
	@Override
	public boolean existSolicitudRegistrobyNSS(String nss) throws Exception{
		boolean respuesta=false;
		String queryString="SELECT nss FROM DitAsignacionNss nss,DitPersonaInteresadaSol pis " +
				"WHERE nss.numNss=:numNss " +
				"AND pis.ditPersona.cveIdPersona=nss.ditPersona.cveIdPersona " +
				"AND pis.ditSolicitud.dicTipoSolicitud.cveIdTipoSolicitud=:tipoSolicitud " +
				"AND pis.ditSolicitud.fecRegistroBaja IS NULL " +
				"AND pis.ditSolicitud.dicEstadoSolicitud.cveIdEstadoSolicitud not in "+"("+EstadoSolicitudEnum.CANCELADA.getId().intValue()+","+EstadoSolicitudEnum.ATENDIDA.getId().intValue()+")";
		Query query = em.createQuery(queryString,DitAsignacionNss.class);
		query.setParameter("numNss", nss);
		query.setParameter("tipoSolicitud", 1);
//		query.setParameter("estadoSolicitud","("+EstadoSolicitudEnum.CANCELADA.getId().intValue()+","+EstadoSolicitudEnum.ATENDIDA.getId().intValue()+")");
		try{
	
			respuesta=!query.getResultList().isEmpty();
			
		}catch(NoResultException e){			
			respuesta=false;
		}catch(Exception e){
			logger.error("existSolicitudRegistrobyNSS", e);
			throw e;
		}
		
		return respuesta;
	}
	@Override
	public DitAsignacionNss getAsignacionNSSbyNSS(String nss) throws DerechohabientesBusinessException,Exception{
		DitAsignacionNss asignacionNss=null;
		try{
			
			CriteriaBuilder cb2 = em.getCriteriaBuilder(); 
			CriteriaQuery<DitAsignacionNss> cqry2= cb2.createQuery(DitAsignacionNss.class);  //Aqui se pone que tipo esperamos recivir
			Root<DitAsignacionNss> root2 = cqry2.from(DitAsignacionNss.class); //Step 2 //se crea el from
			cqry2.select(root2);
			cqry2.where(cb2.equal(root2.get("numNss"),nss));

			asignacionNss=em.createQuery(cqry2).getSingleResult();
		}catch(NoResultException e){
			asignacionNss=null;
		}catch(Exception e){
			logger.error("getAsignacionNSSbyNSS",e);
			throw e;
		}
	return asignacionNss;
	}

	@Override
	public AsignacionNSS getAsignacionNSSbyIdPersona(Long idPersona) throws DerechohabientesBusinessException,Exception{
		DitAsignacionNss asignacionNss=null;
		
		try{
			//Creamos la query
			Criteria consultaAsignacionNss = this.getSession().createCriteria(DitAsignacionNss.class);
			
			//Creamos un alias para el atributo de persona
			consultaAsignacionNss.createAlias("ditPersona", "persona");
			//ponemos el paramtro del id de la persona
			consultaAsignacionNss.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			asignacionNss = (DitAsignacionNss) consultaAsignacionNss.uniqueResult();
			
		}catch(NoResultException e){
			asignacionNss=null;
		}catch(Exception e){
			logger.error("error - getAsignacionNSSbyNSS",e);
			throw e;
		}
	return AsignacionNSSParser.persisToModel(asignacionNss);
	}
	
	
	@Override
	public AsignacionNSS getAsignacionNSS(Long idAsignacionNSS) throws DerechohabientesBusinessException{
		DitAsignacionNss asignacionNss=null;
		AsignacionNSS asignacion= null; 
		try{
			
			asignacionNss = em.find(DitAsignacionNss.class, idAsignacionNSS);
			
			asignacion = AsignacionNSSParser.persisToModel(asignacionNss);
		}catch(NoResultException e){
			DerechohabientesBusinessException.throwException(ExceptionMessages.NSS_NO_ENCONTRADO);
		}
	return asignacion;
	}
	
	@Override
	public AsignacionNSS getAsignacionNSSCL3(Long idAsignacionNSS) throws DerechohabientesBusinessException{
		DitAsignacionNssCL3 asignacionNss=null;
		AsignacionNSS asignacion= null; 
		try{
			asignacionNss = em.find(DitAsignacionNssCL3.class, idAsignacionNSS);
			asignacion = AsignacionNSSParser.persisCL3ToModel(asignacionNss);
		}catch(NoResultException e){
			DerechohabientesBusinessException.throwException(ExceptionMessages.NSS_NO_ENCONTRADO);
		}
	return asignacion;
	}


}
