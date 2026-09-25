package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.ServiciosParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Servicio;
import mx.gob.imss.ctirss.delta.persistence.DicServicio;

import org.apache.log4j.Logger;

@Stateless(name = "serviciosDao", mappedName = "serviciosDao")
public class ServiciosDao implements ServiciosDaoLocal{

	private static final Logger logger = Logger.getLogger(ServiciosDao.class);

	@PersistenceContext (unitName="deltaPersistenceUnit")
	private EntityManager em;
	
	@Override
	public List<Servicio> findServicios() throws DerechohabientesBusinessException,Exception {
		List<DicServicio> dicServicios=null;
		List<Servicio> resultado =null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DicServicio> query = cb.createQuery(DicServicio.class);//resulado
			Root<DicServicio> root = query.from(DicServicio.class);//from
			query.select(root);//select
			dicServicios=em.createQuery(query).getResultList();
		}catch (NoResultException e) {
			return null;
		}catch (Exception e) {
			logger.error("findServicios", e);
			throw e;
		}
		resultado = ServiciosParser.persisToModelList(dicServicios);
		
		return resultado;
	}

}
