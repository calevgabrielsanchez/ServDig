package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;




import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;

import org.apache.log4j.Logger;



@Stateless(name = "codigoPostalDAO", mappedName = "codigoPostalDAO")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CodigoPostalDao extends AbstractServiceEntity implements CodigoPostalDaoLocal  {
	
	private static final Logger logger = Logger.getLogger(CodigoPostalDao.class);
	
	/**
	@PersistenceContext()
	private EntityManager em;
	**/
	

	@Override
	public DgCodigosPostale getCodigoByAsentamiento(DgAsentamiento dgAsentamiento) 
	throws DerechohabientesBusinessException, Exception{
		
		DgCodigosPostale dgCodigosPostale=null;
		Query q = em.createNamedQuery("codigoPostaleByAsentamiento");

		q.setParameter("cveAsen", dgAsentamiento.getId().getCveAsen());
		q.setParameter("cveMun", dgAsentamiento.getId().getCveMun());
		q.setParameter("cveEnt", dgAsentamiento.getId().getCveEnt());
		/*
		q.setParameter("cveLoc", dgAsentamiento.getId().getCveLoc());
		q.setParameter("cvePeriodo", dgAsentamiento.getId().getCvePeriodo());*/
	        
	        //Se Obtiene el resultado
		try {
			dgCodigosPostale= (DgCodigosPostale) q.getSingleResult();
		} catch (NoResultException e) {
			dgCodigosPostale=null;
		} catch (Exception e) {
			logger.error("Error - getCodigoByAsentamiento", e);
			throw e;
		}

		return dgCodigosPostale;
	
		
	}

	
}
