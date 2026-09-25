package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsentamientoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UnidadMedicaFamiliarParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostalePK;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


@Stateless(name = "umfCodigoPostalDAO", mappedName = "umfCodigoPostalDAO")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UmfCodigoPostalDao extends AbstractServiceEntity implements UmfCodigoPostalDaoLocal {
	
	private static final Logger logger = Logger.getLogger(UmfCodigoPostalDao.class);
	@EJB private CodigoPostalDaoLocal codigoPostalDao;

	@Override
	public DicUmf getUMFDomicilio(DgDomicilioGeografico domicilioGeografico) throws DerechohabientesBusinessException,Exception  {
		DicUmf unDicUmf = null;
		try {
			unDicUmf = this.getUMFCodPosDomicilio(domicilioGeografico).getDicUmf();
		} catch (Exception e) {
			logger.error("Error - getUMFDomicilio", e);
			throw e;
		}
		
		return unDicUmf;
		
	}
	
	@Override
	public DitUmfCodPo getUMFCodPosDomicilio(DgDomicilioGeografico domicilioGeografico) throws Exception  {
		DitUmfCodPo miDitUmfCodPo = null;
		try {
			DgCodigosPostale dgCodigosPostale=this.codigoPostalDao.getCodigoByAsentamiento(domicilioGeografico.getDgAsentamiento());
			miDitUmfCodPo = this.getUmfCodPosByCodPos(dgCodigosPostale.getId().getCodigo());
		} catch (Exception e) {
			logger.error("Error - getUMFCodPosDomicilio", e);
			throw e;
		}
		return miDitUmfCodPo;
	}
	
	
	@Override
	public DitUmfCodPo updateUMFCodPos(DitUmfCodPo umfCodPo,Long idUmfNew) throws Exception   {		
		try {
			umfCodPo=em.find(DitUmfCodPo.class,umfCodPo.getCveIdUmfCodPos());
			umfCodPo.setDicUmf(new DicUmf());
			umfCodPo.getDicUmf().setCveIdUmf(idUmfNew);
			em.merge(umfCodPo);
		} catch (NoResultException e){
			umfCodPo = null;
		} catch (Exception e) {
			logger.error("Error - updateUMFCodPos", e);
			throw e;
		}
		return umfCodPo;
		
	}
	
	

	@Override
	public DitUmfCodPo getUmfCodPosByCodPos(String codigoPostal) throws Exception  {
		DitUmfCodPo ditUmfCodPo=null;
		try{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitUmfCodPo> cQuery = cb.createQuery(DitUmfCodPo.class);//resulado
			Root<DitUmfCodPo> root = cQuery.from(DitUmfCodPo.class);//from
			cQuery.select(root);//select
			Predicate conj=cb.conjunction();
			
			//delegacion
			conj.getExpressions().add(cb.equal(root.get("dgCodigosPostale").get("id").get("codigo"), codigoPostal));
			//fecha baja
			conj.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
			cQuery.where(conj);
		
			ditUmfCodPo =em.createQuery(cQuery).getResultList().get(0);
		}catch(IndexOutOfBoundsException e){
			ditUmfCodPo=null;
		}catch (Exception e){
			logger.error("Error - getUmfCodPosByCodPos", e);
			throw e;
		}
		return ditUmfCodPo;
	}
	
	/**
	 * Obtiene DitUmfCodPo a partir de el id de una UMF
	 * @throws Exception 
	 */
	@Override
	public DitUmfCodPo getUmfCodPosByIdUmf(Long cveIdUmf) throws Exception {
		DitUmfCodPo unDitUmfCodPo = null;
		try {
			unDitUmfCodPo = this.getUmfCodPosByIdUmfList(cveIdUmf).get(0);
		}catch (NoResultException e){
			unDitUmfCodPo = null;
		}catch (Exception e) {
			logger.error("Error - getUmfCodPosByIdUmf", e);
			throw e;
		}
		
		return unDitUmfCodPo;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitUmfCodPo> getUmfCodPosByIdUmfList(Long cveIdUmf) throws Exception {
		List<DitUmfCodPo> listDitUmfCodPo = null;
		try {
			
			Criteria queryUmfCP = this.getSession().createCriteria(DitUmfCodPo.class);
			queryUmfCP.createAlias("dicUmf", "umf");
			queryUmfCP.add(Restrictions.eq("umf.cveIdUmf", cveIdUmf));
			queryUmfCP.add(Restrictions.isNull("fecRegistroBaja"));
			listDitUmfCodPo = queryUmfCP.list();
			/*CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitUmfCodPo> cQuery = cb
					.createQuery(DitUmfCodPo.class);

			Root<DitUmfCodPo> root = cQuery
					.from(DitUmfCodPo.class);
			
			cQuery.select(root);

			List<Predicate> predicateList = new ArrayList<Predicate>();

			Predicate conjunction = root.get("dicUmf").get("cveIdUmf").as(Integer.class).in(cveIdUmf);
			predicateList.add(conjunction);
			predicateList.add(cb.isNull(root.get("fecRegistroBaja")));
			Predicate[] predicates = new Predicate[predicateList.size()];
			predicateList.toArray(predicates);

			cQuery.where(predicates);
			listDitUmfCodPo = em.createQuery(cQuery).getResultList();*/
		} catch (IndexOutOfBoundsException e){
			listDitUmfCodPo = null;
		}catch (Exception e) {
			logger.error("Error - getUmfCodPosByIdUmfList", e);
			throw e;
		}

		// Se toma el primer resultado
		return listDitUmfCodPo;
		
	}
	
	@SuppressWarnings("unchecked")
	public Boolean existeRelacionEntreUmfYCp(String codigoPostal, Long idUmf) {
		
		Criteria queryUmf = this.getSession().createCriteria(DitUmfCodPo.class);
		queryUmf.createAlias("dicUmf", "umf");
		queryUmf.add(Restrictions.eq("umf.cveIdUmf", idUmf));
		queryUmf.add(Restrictions.isNull("fecRegistroBaja"));
		
		Criteria queryAsen = queryUmf.createCriteria("dgCodigosPostale");
		queryAsen.add(Restrictions.eq("id.codigo", codigoPostal));
		
		List<DitUmfCodPo> listaCod = queryUmf.list();
		
		if(listaCod != null && !listaCod.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	/**
	 * Metodo que obtiene una lista de unidades medicas familiares que atienden un codigo postal
	 * recibiento los siguientes parametros:
	 * 
	 * @param codigoPostal String
	 * @return List<UnidadMedicaFamiliar>
	 * @throws DerechohabientesBusinessException 
	 */
	@Override
	public List<UnidadMedicaFamiliar> getUmfByCodigoPostal(String codigoPostal)  throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		
		//Entidades de persistencia encontradas
		List<DicUmf> umfs=null;
		//Entidades de negocio arrojadas cmo resultado
		List<UnidadMedicaFamiliar> encontradas = null;
		//Creamos la instancia de criteria
		CriteriaBuilder cb = em.getCriteriaBuilder();
		//se le indica que retornada objetos de tipo dicumf
		CriteriaQuery<DicUmf> query = cb.createQuery(DicUmf.class);
		
		//y que los seleccionara de la tabla ditUmfCodPo
		Root<DitUmfCodPo> tablaUmfCp = query.from(DitUmfCodPo.class);
		//le ndicamos que objeto seleccionaremos
		Path<DicUmf> seleccion = tablaUmfCp.get("dicUmf");
		//LE indicamos que seleccione las umfs distintas
		query.select(seleccion).distinct(true);
		
		Predicate parametros=cb.conjunction();
		
		//Agregamos las restricciones
		parametros.getExpressions().add(cb.equal(tablaUmfCp.get("dgCodigosPostale").get("id").get("codigo"), codigoPostal));
		parametros.getExpressions().add(cb.isNull(tablaUmfCp.get("fecRegistroBaja")));
		
		
		query.where(parametros);
		try {
			umfs = em.createQuery(query).getResultList();
		} catch (Exception e) {
			logger.error("Error - getUmfByCodigoPostal", e);
			throw e;
		}
		
		encontradas = UnidadMedicaFamiliarParser.persisToModelList(umfs);
		
		return encontradas;

	}
	
	
	

	@Override
	public List<UnidadMedicaFamiliar> getUmfByAsentamiento(
			Asentamiento asentamiento, Boolean incluirCFE)
			throws DerechohabientesBusinessException, Exception {
		//Entidades de persistencia encontradas
		List<DicUmf> umfs=null;
		//Entidades de negocio arrojadas cmo resultado
		List<UnidadMedicaFamiliar> encontradas = null;
		
		Criteria queryUmfs = this.getSession().createCriteria(DitUmfCodPo.class);
		queryUmfs.createAlias("dicUmf", "umf");
		queryUmfs.setProjection(Projections.distinct(Projections.property("dicUmf")));
		queryUmfs.add(Restrictions.isNull("fecRegistroBaja"));
		Criteria queryAsentamiento = queryUmfs.createCriteria("dgCodigosPostale");
		queryAsentamiento.add(Restrictions.eq("id.codigo", asentamiento.getCodigoPostal().getCodigoPostal()));
		queryAsentamiento.add(Restrictions.eq("id.cveAsen", asentamiento.getClave()));
		queryAsentamiento.add(Restrictions.eq("id.cveEnt", asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getClave()));
		queryAsentamiento.add(Restrictions.eq("id.cveMun", asentamiento.getLocalidad().getMunicipio().getClave()));
		
		if(incluirCFE != null && !incluirCFE){
			queryUmfs.add(Restrictions.isNull("umf.indUmfCfe"));
		}	
		
		try {
			umfs = queryUmfs.list();
		} catch (Exception e) {
			logger.error("Error - getUmfByCodigoPostal", e);
			throw e;
		}
		
		encontradas = UnidadMedicaFamiliarParser.persisToModelList(umfs);
		
		return encontradas;
	}

	/**
	 * Metodo que obtiene una lista de unidades medicas familiares que atienden un codigo postal y
	 * un indicador de CFE diferente del indicado
	 * 
	 * @param codigoPostal String
	 * @param notEqualIndUmfCfe BigDecimal 
	 * @return List<UnidadMedicaFamiliar>
	 * @throws DerechohabientesBusinessException 
	 */
	@Override
	public List<UnidadMedicaFamiliar> getUmfByCodigoPostal(String codigoPostal, Integer notEqualIndUmfCfe)  throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		
		//Entidades de persistencia encontradas
		List<DicUmf> umfs=null;
		//Entidades de negocio arrojadas cmo resultado
		List<UnidadMedicaFamiliar> encontradas = null;
		//Creamos la instancia de criteria
		CriteriaBuilder cb = em.getCriteriaBuilder();
		//se le indica que retornada objetos de tipo dicumf
		CriteriaQuery<DicUmf> query = cb.createQuery(DicUmf.class);
		
		//y que los seleccionara de la tabla ditUmfCodPo
		Root<DitUmfCodPo> tablaUmfCp = query.from(DitUmfCodPo.class);
		//le ndicamos que objeto seleccionaremos
		Path<DicUmf> seleccion = tablaUmfCp.get("dicUmf");
		//LE indicamos que seleccione las umfs distintas
		query.select(seleccion).distinct(true);
		
		Predicate parametros=cb.conjunction();
		
		//Agregamos las restricciones
		parametros.getExpressions().add(cb.equal(tablaUmfCp.get("dgCodigosPostale").get("id").get("codigo"), codigoPostal));
		parametros.getExpressions().add(cb.isNull(tablaUmfCp.get("fecRegistroBaja")));
		
		
		
		if(notEqualIndUmfCfe != null){
			Expression<Short> col1 = tablaUmfCp.get("dicUmf").get("indUmfCfe").as(Short.class);
			parametros.getExpressions().add(cb.notEqual(cb.coalesce(col1, 0),notEqualIndUmfCfe));
		}else{
			parametros.getExpressions().add(cb.isNotNull(tablaUmfCp.get("dicUmf").get("indUmfCfe")));
		}	
		
		query.where(parametros);
		
		
		
		try {
			umfs = em.createQuery(query).getResultList();
		} catch (Exception e) {
			logger.error("Error - getUmfByCodigoPostal", e);
			throw e;
		}
		
		encontradas = UnidadMedicaFamiliarParser.persisToModelList(umfs);
		
		return encontradas;

	}
	
	
	
	@Override
	public DitUmfCodPo getUmfCodPosByAsentamientoAndUmf(Long idUmf,
			Asentamiento asentamiento) throws Exception {
			DitUmfCodPo unDitUmfCodPo = null;
		try {
			Criteria queryUmfCP = this.getSession().createCriteria(DitUmfCodPo.class); 
			queryUmfCP.createAlias("dicUmf", "umf");
			queryUmfCP.add(Restrictions.eq("umf.cveIdUmf", idUmf));
			queryUmfCP.add(Restrictions.isNull("fecRegistroBaja"));
			
			Criteria queryAsent = queryUmfCP.createCriteria("dgCodigosPostale");
			//queryAsent.createAlias("id", "idAsen");
			queryAsent.add(Restrictions.eq("id.codigo", asentamiento.getCodigoPostal().getCodigoPostal()));
			queryAsent.add(Restrictions.eq("id.cveAsen", asentamiento.getClave()));
			//queryAsent.add(Restrictions.eq("id.cveLoc", asentamiento.getLocalidad().getClave()));
			queryAsent.add(Restrictions.eq("id.cveMun", asentamiento.getLocalidad().getMunicipio().getClave()));
			queryAsent.add(Restrictions.eq("id.cveEnt", asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getClave()));
			//queryAsent.add(Restrictions.eq("id.cvePeriodo", 1L));
			
			unDitUmfCodPo = (DitUmfCodPo) queryUmfCP.uniqueResult();
			/*CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitUmfCodPo> cQuery = cb.createQuery(DitUmfCodPo.class);

			Root<DitUmfCodPo> root = cQuery.from(DitUmfCodPo.class);
			
			cQuery.select(root);

			List<Predicate> predicateList = new ArrayList<Predicate>();
			predicateList.add(cb.equal(root.get("dicUmf").get("cveIdUmf").as(Integer.class), idUmf));
			predicateList.add(cb.equal(root.get("dgCodigosPostale").get("id").get("codigo").as(String.class), asentamiento.getCodigoPostal().getCodigoPostal()));
			predicateList.add(cb.equal(root.get("dgCodigosPostale").get("id").get("cveAsen").as(Integer.class), asentamiento.getClave()));
			predicateList.add(cb.equal(root.get("dgCodigosPostale").get("id").get("cveEnt").as(Integer.class), asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getClave()));
			predicateList.add(cb.equal(root.get("dgCodigosPostale").get("id").get("cveMun").as(Integer.class), asentamiento.getLocalidad().getMunicipio().getClave()));
			predicateList.add(cb.equal(root.get("dgCodigosPostale").get("id").get("cveLoc").as(Integer.class), asentamiento.getLocalidad().getClave()));
			predicateList.add(cb.equal(root.get("dgCodigosPostale").get("id").get("cvePeriodo").as(Integer.class), asentamiento.getPeriodo()));
			predicateList.add(cb.isNull(root.get("fecRegistroBaja")));
			
			Predicate[] predicates = new Predicate[predicateList.size()];
			predicateList.toArray(predicates);
			cQuery.where(predicates);
			unDitUmfCodPo = em.createQuery(cQuery).getSingleResult();*/
		} catch (Exception e) {
			logger.error("Error - getUmfCodPosByAsentamientoAndUmf", e);
			throw e;
		}
		
		return unDitUmfCodPo;
	}

	@Override
	public DitUmfCodPo bajaLogicaUmfCodPo(Long idUmf, Asentamiento asent) throws Exception {
		DitUmfCodPo unDitUmfCodPo = null;
		try {
				unDitUmfCodPo=getUmfCodPosByAsentamientoAndUmf(idUmf, asent);
				if(unDitUmfCodPo != null){
					unDitUmfCodPo.setFecRegistroBaja(new Date());
					em.merge(unDitUmfCodPo);
					
				}
		return unDitUmfCodPo;
		}  catch (Exception e) {
			logger.error("Error - updateUMFCodPos", e);
			throw e;
		}				
	}
	
	

	@Override
	public DitUmfCodPo insertaUMFCodPos(Long idUmf, Asentamiento asent) throws Exception {
		DitUmfCodPo unDitUmfCodPo = null;
		
		try {
			unDitUmfCodPo = getUmfCodPosByAsentamientoAndUmf(idUmf,asent);
			
			if(unDitUmfCodPo != null)
				return unDitUmfCodPo;
		} catch (Exception e) {
			unDitUmfCodPo = null;
		}
		
		try {
			unDitUmfCodPo = new DitUmfCodPo();
			DicUmf unDicUmf = new DicUmf();
			DgCodigosPostale unDgCodPos = new DgCodigosPostale();
			DgCodigosPostalePK id = new DgCodigosPostalePK();
			logger.debug("Codigo postal: " + asent.getCodigoPostal().getCodigoPostal());
			logger.debug("Asentamiento: " + asent.getClave());
			logger.debug("localidad: " + asent.getLocalidad().getClave());

			id.setCodigo(asent.getCodigoPostal().getCodigoPostal());
			id.setCveAsen(asent.getClave());
			id.setCveEnt(asent.getLocalidad().getMunicipio().getEntidadFederativa().getClave());
			//id.setCveLoc(asent.getLocalidad().getClave());
			id.setCveMun(asent.getLocalidad().getMunicipio().getClave());
			//id.setCvePeriodo(asent.getPeriodo());

			unDgCodPos.setId(id);
			DgAsentamiento asentamient = AsentamientoParser.modelToPersist(asent);
			unDgCodPos.setDgAsentamiento(asentamient);

			unDicUmf.setCveIdUmf(idUmf);
			unDitUmfCodPo.setDicUmf(unDicUmf);
			unDitUmfCodPo.setDgCodigosPostale(unDgCodPos);
			unDitUmfCodPo.setFecRegistroAlta(new Date());

			em.persist(unDitUmfCodPo);
			em.flush();
			return unDitUmfCodPo;
			
		} catch (Exception e) {
			logger.error("Error - insertCambioUMFCodPos", e);
			throw e;
		}
	}
}
