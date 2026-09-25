package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AseguradoParser;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.persistence.DitAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;

@Stateless(name = "aseguradoDao", mappedName = "aseguradoDao")


public class AseguradoDao extends AbstractServiceEntity implements AseguradoDaoLocal {
	
	private static final Logger logger = Logger.getLogger(AseguradoDao.class);
	/**
	@PersistenceContext()
	private EntityManager em;
	**/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Asegurado> getAsegurados(AsignacionNSS nss) throws Exception{
		List<DitAsegurado> ditAsegurados = null;
		List<Asegurado> asegurados = null;
		
	    String queryString="SELECT a FROM DitAsegurado a "+
		"WHERE a.ditAsignacionNss.cveIdAsignacionNss = :nss";
		try {
			Query query = em.createQuery(queryString, DitAsegurado.class);
			query.setParameter("nss", nss.getIdAsignacionNSS());
				
			ditAsegurados = query.getResultList();
			
			if(ditAsegurados != null && ditAsegurados.size() > 0){
				asegurados =  new ArrayList<Asegurado>();
				for(DitAsegurado d :  ditAsegurados){
					asegurados.add(AseguradoParser.persisToModel(d));
				}
			}
		} catch (Exception e) {
			logger.error("getAsegurados", e);
			throw e;
		}
	    
		
		return asegurados;
	}
	
	
	public boolean existSolicitudRegistrobyNSS(String nss) throws Exception{
		boolean respuesta=false;
		Query query  = null;
		try {
			String queryString="SELECT nss FROM DitAsignacionNss nss,DitPersonaInteresadaSol pis " +
			"WHERE nss.numNss=:numNss " +
			"AND pis.ditPersona.cveIdPersona=nss.ditPersona.cveIdPersona " +
			"AND pis.ditSolicitud.dicTipoSolicitud.cveIdTipoSolicitud=:tipoSolicitud " +
			"AND pis.ditSolicitud.fecRegistroBaja IS NULL " +
			"AND pis.ditSolicitud.dicEstadoSolicitud.cveIdEstadoSolicitud!=:estadoSolicitud";
			query = em.createQuery(queryString,DitAsignacionNss.class);
			query.setParameter("numNss", nss);
			query.setParameter("tipoSolicitud", 1);
			query.setParameter("estadoSolicitud", EstadoSolicitudEnum.CANCELADA.getId().intValue());						
		} catch (Exception e) {
			logger.error("existSolicitudRegistrobyNSS", e);
			throw e;
		}
		try{
			respuesta=!query.getResultList().isEmpty();			
		}catch(NoResultException e){
			respuesta=false;
		}
		
		return respuesta;
	}

}
