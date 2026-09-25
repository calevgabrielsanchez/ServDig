package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsignacionNSSParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UsuarioFuncionarioParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UsuarioOrdinarioParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UsuarioParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioOrdinario;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitUsuario;
import mx.gob.imss.ctirss.delta.persistence.DitUsuarioFuncionario;
import mx.gob.imss.ctirss.delta.persistence.DitUsuarioOrdinario;

import org.apache.log4j.Logger;

@Stateless(name = "usuarioDao", mappedName = "usuarioDao")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDao extends AbstractServiceEntity implements UsuarioDaoLocal {
	
	private static final Logger logger = Logger.getLogger(UsuarioDao.class);

	/**
	@PersistenceContext()
	private EntityManager em;
	**/
	
	@EJB
	PatronDaoLocal patronDao;

	

	@Override
	public Usuario getUsuario(String nomUsuario) throws DerechohabientesBusinessException, Exception {		
		DitUsuario miDitUsuario = null;
		
		try{
			Query query = em.createNamedQuery("getUsuarioLogin");
			query.setParameter("nomUsuario", nomUsuario);
			miDitUsuario = (DitUsuario) query.getSingleResult();			
		}catch(NoResultException e){
			miDitUsuario = null;
		}catch (Exception e){
			logger.error("Error - getUsuario", e);
			throw e;
		}
		 
		return UsuarioParser.persisToModel(miDitUsuario);
	}



	@Override
	public UsuarioOrdinario getUsuarioOrdinario(long idUsuario) throws DerechohabientesBusinessException,Exception {
		DitUsuarioOrdinario miDitUsuarioOrd = null;
		try {
			Query query = em.createNamedQuery("getUsuarioOrdinario");
			query.setParameter("idUsuario", idUsuario);
			miDitUsuarioOrd = (DitUsuarioOrdinario) query.getSingleResult();	
		}catch(NoResultException e){
			miDitUsuarioOrd = null;
		} catch (Exception e) {
			logger.error("Error - getUsuarioOrdinario", e);
			throw e;
		}
			
		return UsuarioOrdinarioParser.persisToModel(miDitUsuarioOrd);
	}



	@Override
	public UsuarioFuncionario getUsuarioFuncionario(long idUsuario) throws DerechohabientesBusinessException,Exception {
		DitUsuarioFuncionario miDitUsuarioFun = null;
		try {
			Query query = em.createNamedQuery("getUsuarioFuncionario");
			query.setParameter("idUsuario", idUsuario);
			miDitUsuarioFun = (DitUsuarioFuncionario) query.getSingleResult();
		} catch(NoResultException e){
			miDitUsuarioFun = null;
		}catch (Exception e) {
			logger.error("Error - getUsuarioFuncionario", e);
			throw e;
		}
				
		return UsuarioFuncionarioParser.persisToModel(miDitUsuarioFun);
	}



	@Override
	public AsignacionNSS getAsignacionNss(long idPersona)
			throws DerechohabientesBusinessException, Exception {
		return null;
	//	DitAsignacionNss ditAsignacion = null;
		
//		try {
//			Query query = em.createNamedQuery("buscaAsignacionXpersona");			
//			query.setParameter("idPersona", idPersona);
//			ditAsignacion = (DitAsignacionNss) query.getSingleResult();
//		} catch (NoResultException e) {
//			ditAsignacion = null;
//		} catch (Exception e){
//			logger.error("Error - getAsignacionNss", e);
//			throw e;
//		}
//		
//		return AsignacionNSSParser.persisToModel(ditAsignacion);
	}

	
}
