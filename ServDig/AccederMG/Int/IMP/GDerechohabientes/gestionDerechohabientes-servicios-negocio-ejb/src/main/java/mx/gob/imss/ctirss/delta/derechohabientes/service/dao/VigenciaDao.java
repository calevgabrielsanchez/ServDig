/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.EstadoDerechohabienteParserService;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.EstadoDerechohabienteParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.ModServPresDerechohabParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.PatronSujetoObligadoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.ServicioPrestDerechohabParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ModServPresDerechohab;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ServicioPrestDerechohab;
import mx.gob.imss.ctirss.delta.model.enums.ServiciosPrestacionesEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DicServicioPrestDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DitModServPresDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

/**
 * @author JUAN MANUEL MÁRQUEZ
 * 
 */
@Stateless(name = "vigenciaDao", mappedName = "vigenciaDao")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class VigenciaDao extends AbstractServiceEntity implements VigenciaDaoLocal {
	
	@EJB GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB PatronDaoLocal patronDao;
	@EJB EstadoDerechohabienteParserServiceLocal estadoDerechohabienteParserServiceLocal;
	private final static Logger log = Logger.getLogger(VigenciaDao.class);
	
	@Override
	public EstadoDerechohabiente getEstadoDerechohabiente(long idPersona,
			long idAsignacionNss) throws Exception {
		DicEstadoDerechohabiente unDicEstadoDerecho = new DicEstadoDerechohabiente();
		try {
			Query query = em
					.createNamedQuery("getGrupoFamiliarEstadoDerechohabiente");
			query.setParameter("idAsignacionNss", idAsignacionNss);
			query.setParameter("idPersona", idPersona);
			unDicEstadoDerecho = (DicEstadoDerechohabiente) query
					.getSingleResult();
		} catch (NoResultException e) {
			unDicEstadoDerecho = null;
		} catch (Exception e){
			log.error("Error - getEstadoDerechohabiente", e);
			throw e;
		}
		return estadoDerechohabienteParserServiceLocal.persisToModel(unDicEstadoDerecho);
	}

	@Override
	public SujetoObligado getPatronSujetoObligado(long idPatronSujetoObligado)
			throws Exception {
		DitPatronSujetoObligado miPatronSujeto = null;
		try {
			miPatronSujeto = em.find(DitPatronSujetoObligado.class,idPatronSujetoObligado);
		} catch (NoResultException e) {
			miPatronSujeto = null;
		} catch (Exception e){
			log.error("Error - getPatronSujetoObligado", e);
			throw e;
		}
		
		SujetoObligado so = PatronSujetoObligadoParser.persisToModel(miPatronSujeto);
		return so;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean getDerechoSM(long idModalidad) throws Exception{
		boolean conServicio = false;
		
		try {
			Query query = em.createNamedQuery("getConDerecho");
			query.setParameter("idModalidad", idModalidad);
			query.setParameter("idServicio", ServiciosPrestacionesEnum.SERVICIO_MEDICO.getId());
			DitModServPresDerechohab ditModServ = (DitModServPresDerechohab) query.getSingleResult();
			conServicio = true;
		} catch (NoResultException e) {
			conServicio = false;
		} catch (Exception e){
			log.error("Error - getDerechoSM", e);
			throw e;
		}
		
		return conServicio;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean getDerechoINC(long idModalidad) throws Exception {
boolean conServicio = false;
		
		try {
			Query query = em.createNamedQuery("getConDerecho");
			query.setParameter("idModalidad", idModalidad);
			query.setParameter("idServicio", ServiciosPrestacionesEnum.EXPEDICION_INCAPACIDAD_TRABAJO.getId());
			DitModServPresDerechohab ditModServ = (DitModServPresDerechohab) query.getSingleResult();
			conServicio = true;
		} catch (NoResultException e) {
			conServicio = false;
		} catch (Exception e){
			log.error("Error - getDerechoINC", e);
			throw e;
		}		
		
		return conServicio;
	}
	
	@Override
	public List<ServicioPrestDerechohab> findServicios() throws Exception {
		
		List<DicServicioPrestDerechohab> dicServicios= null;
		List<ServicioPrestDerechohab> servicios= null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DicServicioPrestDerechohab> query = cb.createQuery(DicServicioPrestDerechohab.class);
			Root<DicServicioPrestDerechohab> root = query.from(DicServicioPrestDerechohab.class);
			
			query.select(root).distinct(true);
			dicServicios = em.createQuery(query).getResultList();
		} catch (Exception e) {
			log.error("Error - findServicios", e);
			throw e;
		}
		
		servicios= ServicioPrestDerechohabParser.persisToModelList(dicServicios);
		
		return servicios;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ModServPresDerechohab> getServiciosByAsegurado(Long idAsignacionNss) throws Exception {
		
		Criteria criteria = this.getSession().createCriteria(DitModServPresDerechohab.class);
		List<DitModServPresDerechohab> ditModServPresDerechohab= null;
		List<ModServPresDerechohab> servicios= null;
		try {
			
			Criteria queryPatron = criteria.createCriteria("dicModalidad").createCriteria("ditPatronSujetoObligados");
			Criteria queryAsegurado = queryPatron.createCriteria("ditAsegurados");
			queryAsegurado.createAlias("ditAsignacionNss", "nss");
			queryAsegurado.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaAltaD = (Date)format.parse("31/12/9999");

			Criterion fechaNula = Restrictions.isNull("fecBaja");
			Criterion fechaHoy = Restrictions.eq("fecBaja", new Date());
			Criterion fechaAlta = Restrictions.eq("fecBaja", fechaAltaD);
			Criterion fechas = Restrictions.or(fechaNula, fechaHoy);
			LogicalExpression orFechas = Restrictions.or(fechas, fechaAlta);
			queryAsegurado.add(orFechas);
			
			ditModServPresDerechohab = (List<DitModServPresDerechohab>) criteria.list();
		} catch (Exception e) {
			log.error("Error - getServiciosByAsegurado", e);
			throw e;
		}
		
		
		servicios= ModServPresDerechohabParser.persisToModelList(ditModServPresDerechohab);
		
		return servicios;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ModServPresDerechohab> getServiciosByPensionado(
			Long idAsignacionNss) throws DerechohabientesBusinessException,
			Exception {
		Criteria criteria = this.getSession().createCriteria(DitModServPresDerechohab.class);
		List<DitModServPresDerechohab> ditModServPresDerechohab= null;
		List<ModServPresDerechohab> servicios= null;
		try {
			
			Criteria queryPatron = criteria.createCriteria("dicModalidad").createCriteria("ditPatronSujetoObligados");
			Criteria queryAsegurado = queryPatron.createCriteria("ditAsegurados");
			queryAsegurado.createAlias("ditAseguradoPensions", "pensio");
			queryAsegurado.createAlias("ditAsignacionNss", "nss");
			queryAsegurado.add(Restrictions.eq("pensio.tipEstatus", new BigDecimal(1)));
			queryAsegurado.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));

			ditModServPresDerechohab = (List<DitModServPresDerechohab>) criteria.list();
		} catch (Exception e) {
			log.error("Error - getServiciosByAsegurado", e);
			throw e;
		}
		
		
		servicios= ModServPresDerechohabParser.persisToModelList(ditModServPresDerechohab);
		
		return servicios;
	}

	@Override
	public Modalidad getModalidMayorByAsignacionNss(Long idAsignacionNss)
			throws DerechohabientesBusinessException, Exception {
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ModServPresDerechohab> getServiciosPorModalidad(
			List<Long> idModalidades) throws DerechohabientesBusinessException,
			Exception {
		
		Criteria criteria = this.getSession().createCriteria(DitModServPresDerechohab.class);
		List<DitModServPresDerechohab> ditModServPresDerechohab= null;
		List<ModServPresDerechohab> servicios= null;
		try {
			criteria.add(Restrictions.isNull("fecRegistroBaja"));
			criteria.add(Restrictions.in("dicModalidad.cveIdModalidad", idModalidades));
			ditModServPresDerechohab = (List<DitModServPresDerechohab>) criteria.list();
		} catch (Exception e) {
			log.error("Error - getServiciosByAsegurado", e);
			throw e;
		}
		
		
		servicios= ModServPresDerechohabParser.persisToModelList(ditModServPresDerechohab);
		
		return servicios;
	}
}
