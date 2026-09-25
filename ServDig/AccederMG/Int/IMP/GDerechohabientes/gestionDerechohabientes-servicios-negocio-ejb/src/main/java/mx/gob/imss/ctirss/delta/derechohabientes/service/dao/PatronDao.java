package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AseguradoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AseguradoPensionParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.PatronSujetoObligadoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.AseguradoPension;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicPatronInstEdu;
import mx.gob.imss.ctirss.delta.persistence.DitAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitAseguradoPension;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


/**
 * @author Mario Teran Blanco,VictorCamacho
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Stateless(name = "patronDao", mappedName = "patronDao")
public class PatronDao extends AbstractServiceEntity implements PatronDaoLocal {
	
	private static final Logger logger = Logger.getLogger(PatronDao.class);

	/**
	 * Recupera un objeto PatronSujetoObligando en base a un NSS
	 */
	@Override
	public SujetoObligado getPatronSujeto(long idPatronSujetoObligado) throws DerechohabientesBusinessException, Exception{
		DitPatronSujetoObligado unPatronSujetoObligado = null;
		try {						
			unPatronSujetoObligado = em.find(DitPatronSujetoObligado.class, idPatronSujetoObligado);						
		}catch (NoResultException e){
			unPatronSujetoObligado = null;
		}catch (Exception e) {
			logger.error("Error - getPatronSujeto", e);
			throw e;
		}
		
		return PatronSujetoObligadoParser.persisToModel(unPatronSujetoObligado);
	}

	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public String getRegistroPatronal(long idPatronSujetoObligado) throws Exception{
		DitPatronGeneral unPatronGeneral = null;
		
		Criteria criteria = this.getSession().createCriteria(DitPatronGeneral.class);
		criteria.createAlias("ditPatronSujetoObligado", "patSO");
		criteria.add(Restrictions.eq("patSO.cveIdPatronSujetoObligado", idPatronSujetoObligado));

		try{
			unPatronGeneral = (DitPatronGeneral)criteria.uniqueResult();
		}catch (NoResultException e){
			return null;
		}catch (Exception e) {
			logger.error("Error - getRegistroPatronal", e);
			throw e;
		}	
		
		
		return unPatronGeneral.getRegPatron()+""+unPatronGeneral.getDigVer();
	}

	@Override
	public String getRegistroPatronalSinDV(long idPatronSujetoObligado)
			throws Exception {
		DitPatronGeneral unPatronGeneral = null;
		try{
			
			Criteria queryPatron = this.getSession().createCriteria(DitPatronGeneral.class);
			queryPatron.createAlias("ditPatronSujetoObligado", "sujetoO");
			queryPatron.add(Restrictions.eq("sujetoO.cveIdPatronSujetoObligado", idPatronSujetoObligado));
			
			unPatronGeneral = (DitPatronGeneral) queryPatron.uniqueResult();
			
			/*CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitPatronGeneral> query = cb.createQuery(DitPatronGeneral.class);//resulado
			Root<DitPatronGeneral> root = query.from(DitPatronGeneral.class);//from
			query.select(root);//select
			Predicate conj=cb.conjunction();
			//
			conj.getExpressions().add(cb.equal(root.get("ditPatronSujetoObligado").get("cveIdPatronSujetoObligado").as(Integer.class), idPatronSujetoObligado));
			
			query.where(conj);
			unPatronGeneral = em.createQuery(query).getSingleResult();	*/
		}catch (NoResultException e){
			return null;
		}catch (Exception e) {
			logger.error("Error - getRegistroPatronalSinDV", e);
			throw e;
		}	
		
		
		return unPatronGeneral.getRegPatron();
	}
	
	@Override
	public String getModalidad(long idModalidad) throws DerechohabientesBusinessException,Exception {
		DicModalidad unaModalidad = null;
		try {
			unaModalidad = em.find(DicModalidad.class,idModalidad);		
		}catch (NoResultException e){
			return null;
		}catch (Exception e) {
			logger.error("Error - getModalidad", e);
			throw e;
		}
				
		return unaModalidad.getNumModalidad();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Asegurado> getAseguradoList(long idAsignacionNss) throws DerechohabientesBusinessException,Exception{
		List<DitAsegurado> miDitAseguradoList = null;
		try {
			Query query = em.createNamedQuery("buscaAseguradoRegistrado");
			query.setParameter("idAsignacionNss",idAsignacionNss);
			miDitAseguradoList = query.getResultList();
		}catch (Exception e) {
			logger.error("Error - getAsegurado", e);
			throw e;
		}				
		return AseguradoParser.persisToModelList(miDitAseguradoList);
	}
	
	@Override
	public Long getIdAsegurado(long idAsignacionNss, long idPatronSujetoObligado) throws DerechohabientesBusinessException, Exception {
		DitAsegurado unDitAsegurado = null;
		Long idAsegurado = null;
		try {
			Query query = em.createNamedQuery("buscaAsegurado");
			query.setParameter("idAsignacionNss",idAsignacionNss);
			query.setParameter("idPatronSujeto",idPatronSujetoObligado);
			unDitAsegurado = (DitAsegurado) query.getSingleResult();
		}catch (NoResultException e){
			idAsegurado = null;
		}catch (Exception e) {
			logger.error("Error - getAsegurado", e);
			throw e;
		}				
		idAsegurado = unDitAsegurado.getCveIdAsegurado();
		return idAsegurado;
	}	
	
	@Override
	public Asegurado getAsegurado(long idAsignacionNss, long idPatronSujetoObligado)
			throws DerechohabientesBusinessException, Exception {
		DitAsegurado unDitAsegurado = null;
		
		try {
			Query query = em.createNamedQuery("buscaAsegurado");
			query.setParameter("idAsignacionNss",idAsignacionNss);
			query.setParameter("idPatronSujeto",idPatronSujetoObligado);
			unDitAsegurado = (DitAsegurado) query.getSingleResult();
		}catch (NoResultException e){
			unDitAsegurado = null;
		}catch (Exception e) {
			logger.error("Error - getAsegurado", e);
			throw e;
		}				
		
		return AseguradoParser.persisToModel(unDitAsegurado);
	}

	@Override
	public AseguradoPension getPensionado(long idAsegurado) throws DerechohabientesBusinessException, Exception{		
		DitAseguradoPension miDitAsegPension = null;
		try {
			Criteria criteria = this.getSession().createCriteria(DitAseguradoPension.class);
			criteria.createAlias("ditAsegurado", "ase");
			criteria.add(Restrictions.eq("ase.cveIdAsegurado", idAsegurado));
			miDitAsegPension = (DitAseguradoPension) criteria.uniqueResult();
						
		} catch (NoResultException e){
			miDitAsegPension = null;
			logger.error("Error - NoResultException", e);
		} catch (NonUniqueResultException e){
				miDitAsegPension = null;
			logger.error("Error - NonUniqueResultException", e);
		}catch (Exception e) {
			logger.error("Error - getAsegurado", e);
			throw e;
		}				
		return AseguradoPensionParser.persisToModel(miDitAsegPension);
	}

	/**
	 * Consulta que valida si un patron se encuentra en la tabla de patrones de instituciones educativas
	 * @param cveNRP
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public boolean isRegistroPatronalnstitucionEducativa(String cveNRP) {
		logger.error("llegue a la llamada para consultar al PATRON" + cveNRP);
		boolean isPatronInstitucionEducativa = false;
		DicPatronInstEdu patronInstEdu = null;
			try {						
				patronInstEdu = em.find(DicPatronInstEdu.class, cveNRP);
				logger.error("regrese de la consulta con " + patronInstEdu);
						 
				if(patronInstEdu != null)
					isPatronInstitucionEducativa = true;
			}catch (NoResultException e){
				logger.error("no se localizo el NRP" ,e);
			}catch (Exception e) {
				logger.error("ocurrio un error al consultar a los patrones institucion educativa" ,e);
			}
		
		return isPatronInstitucionEducativa;
	}
	
}
