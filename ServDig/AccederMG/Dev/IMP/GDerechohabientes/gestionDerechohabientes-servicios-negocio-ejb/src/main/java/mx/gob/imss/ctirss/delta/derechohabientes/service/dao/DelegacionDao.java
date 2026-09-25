package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsentamientoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EntidadFederativaParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.MunicipioParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitMunicipioImssInegi;
import mx.gob.imss.ctirss.delta.persistence.DitMunicipioSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "delegacionDAO", mappedName = "delegacionDAO")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DelegacionDao extends AbstractServiceEntity implements DelegacionDaoLocal {
	
	private static final Logger logger = Logger.getLogger(DelegacionDao.class);
	@EJB private UmfCodigoPostalDaoLocal umfCodigoPostalDao;
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DelegacionDaoLocal#findUmfCodigoPostalListByDelegacion(java.lang.Long)
	 */
	@Override
	public List<DitUmfCodPo> findUmfCodigoPostalListByDelegacion(DicDelegacion dicDelegacion) throws Exception{
		
		List<DitUmfCodPo> umfCodPoList=null;
		String codigoPostal=null;
		DitUmfCodPo ditUmfCodPoTemp=null;
	
		//(dic subdelegacion) por cada subdelegacion Buscar domicilio de las subdelegaciones
		umfCodPoList=new ArrayList<DitUmfCodPo>();
		if(dicDelegacion != null && dicDelegacion.getDicSubdelegacions().size() > 0){
			try {
				for(DicSubdelegacion dicSubdelegacion:dicDelegacion.getDicSubdelegacions()){
					//(dg-domicilio-geografico) tomar el codigo postal y el asentamiento para la colonia
					codigoPostal=dicSubdelegacion.getDgDomicilioGeografico().getDgCodigosPostale().getId().getCodigo();
								
					ditUmfCodPoTemp=this.umfCodigoPostalDao.getUmfCodPosByCodPos(codigoPostal);
					if(!this.existeUmfCodPos(umfCodPoList, ditUmfCodPoTemp.getCveIdUmfCodPos())){
						umfCodPoList.add(ditUmfCodPoTemp);
					}			
					 //(dit umf cod pos) buscar por codigo
				}
			} catch (Exception e) {
				logger.error("Error - findUmfCodigoPostalListByDelegacion", e);
				throw e;
			}
			
		}
		return umfCodPoList;
	}
	/**
	 *  pregunta si ya existe el id umf CodPostal
	 * @param entradaList
	 * @param idUmfCodPos
	 * @return
	 * @throws Exception 
	 */
	  
	  private boolean existeUmfCodPos(List<DitUmfCodPo> entradaList,long idUmfCodPos) throws Exception{
		  boolean salida=false;
		  try {
			  for(DitUmfCodPo entrada:entradaList){
				  if(entrada.getCveIdUmfCodPos()==idUmfCodPos){
					  return true;
				  }
			  }
		} catch (Exception e) {
			logger.error("Error - existeUmfCodPos", e);
			throw e;			
		}
		  
		  return salida;
	  }

	//Delegacion
	@Override
	public DicDelegacion findDelegacion(Long idDelegacion) throws Exception{
		DicDelegacion dicDelegacion=null;
		try {
			
			Criteria queryDelegacion = this.getSession().createCriteria(DicDelegacion.class);
			queryDelegacion.add(Restrictions.eq("claveDelegacion", idDelegacion));
			queryDelegacion.add(Restrictions.isNull("fecRegistroBaja"));
			
			
			/*CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DicDelegacion> cQuery = cb.createQuery(DicDelegacion.class);//resulado
			Root<DicDelegacion> root = cQuery.from(DicDelegacion.class);//from
			cQuery.select(root);//select
			Predicate conj=cb.conjunction();
			
			//delegacion
			conj.getExpressions().add(cb.equal(root.get("claveDelegacion").as(Integer.class), idDelegacion));
			//fecha baja
			conj.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
			cQuery.where(conj);
			
			dicDelegacion=em.createQuery(cQuery).getSingleResult();*/
			
			dicDelegacion = (DicDelegacion) queryDelegacion.uniqueResult();
		} catch (NoResultException e){
			dicDelegacion = null;
		}catch (Exception e) {
			logger.error("Error - findDelegacion", e);
			throw e;
		}
		
		return dicDelegacion;
	}
	
	/**
	 * @author mario.teran
	 * MEtodo paraencontrar las entidades federativas relacionadas con una Delegacion
	 * @param idDelegacion el id de la delegacion
	 * @return List<EntidadFederativa> Lista con lasentidades que atiende esa delegacion
	 * @throws DerechohabientesBusinessException 
	 */
	@Override
	public List<EntidadFederativa> findEntidaFederativaByDelegacion(
			Long idDelegacion) throws DerechohabientesBusinessException,Exception {

		List<EntidadFederativa> listaEntidades = null;
		try {
			
			Criteria queryEntidades = this.getSession().createCriteria(DitMunicipioSubdelegacion.class);
			queryEntidades.createAlias("dicMunicipioImss", "munIMSS");
			queryEntidades.setProjection(Projections.property("munIMSS.dgCatEstado"));
			Criteria querySubdelegacion = queryEntidades.createCriteria("dicSubdelegacion");
			querySubdelegacion.createAlias("dicDelegacion","delegacion");
			querySubdelegacion.add(Restrictions.eq("delegacion.cveIdDelegacion", idDelegacion));
			
			/*CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DgCatEstado> cq = cb.createQuery(DgCatEstado.class);
			Root<DitMunicipioSubdelegacion> root = cq.from(DitMunicipioSubdelegacion.class);
			Path<DgCatEstado> path = root.get("dicMunicipioImss").get("dgCatEstado");
			cq.select(path).distinct(true);
			
			Predicate conj = cb.conjunction();
			conj.getExpressions().add(cb.equal(root.get("dicSubdelegacion").get("dicDelegacion").get("cveIdDelegacion").as(Integer.class), idDelegacion));
			cq.where(conj);
			List<DgCatEstado> estados = em.createQuery(cq).getResultList();*/
			@SuppressWarnings("unchecked")
			List<DgCatEstado> estados = queryEntidades.list();
			listaEntidades = EntidadFederativaParser.persistToModelList(estados);
		} catch(IndexOutOfBoundsException e){
			listaEntidades = null;
		}catch (Exception e) {
			logger.error("Error - findEntidaFederativaByDelegacion", e);
			throw e;
		}
		
		return listaEntidades;
	}

	/**
	 * @author mario.teran
	 * Metodo para encontrar los mucinicipios que corresponde a una delegacion y aun estado
	 * @param idDelegacion la delegacion a la que pertenece el municipio
	 * @param idEstado el estado al que pertenece el municipio
	 * @return List<Municipio> la lista de municipios que estan en la delegacion y el estado indicados
	 * @throws DerechohabientesBusinessException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Municipio> findMunicipiosByDelegacionEstado(
			Long idDelegacion, Long idEstado) throws DerechohabientesBusinessException,Exception {
		List<DgCatMunicipio> municipios = null;
		try {
			Criteria queryMunicipios = this.getSession().createCriteria(DitMunicipioImssInegi.class);
			queryMunicipios.setProjection(Projections.distinct(Projections.property("dgCatMunicipio")));
			
			Criteria queryMunSub = this.getSession().createCriteria(DitMunicipioSubdelegacion.class);
			queryMunSub.createAlias("dicMunicipioImss", "munIMSS");
			queryMunSub.setProjection(Projections.property("munIMSS.cveIdMunicipioImss"));
			Criteria queryEstado = queryMunSub.createCriteria("dicMunicipioImss");
			queryEstado.createAlias("dgCatEstado", "estado");
			queryEstado.add(Restrictions.eq("estado.cveEnt", idEstado));
			
			Criteria queryDele = queryMunSub.createCriteria("dicSubdelegacion");
			queryDele.createAlias("dicDelegacion", "delegacion");
			queryDele.add(Restrictions.eq("delegacion.cveIdDelegacion", idDelegacion));
			
			
			queryMunicipios.createAlias("dicMunicipioImss", "dicMunIMSS");
			queryMunicipios.add(Restrictions.in("dicMunIMSS.cveIdMunicipioImss",queryMunSub.list()));
			
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DgCatMunicipio> cq = cb.createQuery(DgCatMunicipio.class);
			Root<DitMunicipioImssInegi> root = cq.from(DitMunicipioImssInegi.class);
			Path<DgCatMunicipio> path = root.get("dgCatMunicipio");
			cq.select(path).distinct(true);
			
			Subquery<Integer> sq = cq.subquery(Integer.class);
			Root<DitMunicipioSubdelegacion> municipioSub = sq.from(DitMunicipioSubdelegacion.class);
			Path<Integer> cvemunicipio = municipioSub.get("dicMunicipioImss").get("cveIdMunicipioImss");
			sq.select(cvemunicipio).distinct(true);
			Predicate parametrosSub = cb.conjunction();
			parametrosSub.getExpressions().add(cb.equal(municipioSub.get("dicSubdelegacion").get("dicDelegacion").get("cveIdDelegacion").as(Integer.class), idDelegacion));
			parametrosSub.getExpressions().add(cb.equal(municipioSub.get("dicMunicipioImss").get("dgCatEstado").get("cveEnt").as(Integer.class), idEstado));
			sq.where(parametrosSub);
			
			Predicate parametrosPrin = cb.conjunction();
			parametrosPrin.getExpressions().add(cb.in(root.get("dicMunicipioImss").get("cveIdMunicipioImss")).value(sq));
			cq.where(parametrosPrin);
			municipios = em.createQuery(cq).getResultList();
			*/
			
			municipios = queryMunicipios.list();
		} catch (Exception e) {
			logger.error("Error - findMunicipiosByDelegacionEstado", e);
			throw e;
		}
		
		return MunicipioParser.persistToModelList(municipios);
	}

	/**
	 * @author mario.teran
	 * Metodo para obtener los asentamientos que atiende una delegacion en base a los siguientes parametros
	 * @param idDelegacion el is de l delegacion
	 * @param idMunicipio el id del municipio
	 * @param idEntidad el id del estado
	 * @return List<Asentamiento> La lista de asentamientos atendidos en un municipio que pertenece a una delegacion
	 * @throws Exception 
	 */
	@Override
	public List<Asentamiento> findAsentamientoByDelegacionMunicipioEntidad(
			Long idDelegacion, Long idMunicipio, Long idEntidad) throws Exception {
		// TODO Auto-generated method stub
		List<Asentamiento> salida = new ArrayList<Asentamiento>();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DgCodigosPostale> queryPrincipal = cb.createQuery(DgCodigosPostale.class);
			Root<DitUmfCodPo> root = queryPrincipal.from(DitUmfCodPo.class);
			
			Path<DgCodigosPostale> pathPrincipal = root.get("dgCodigosPostale");
			queryPrincipal.select(pathPrincipal).distinct(true);
			
			Subquery<Integer> subqueryUmf = queryPrincipal.subquery(Integer.class);
			Root<DicUmf> tablaUmf = subqueryUmf.from(DicUmf.class);
			Path<Integer> seleccionUmf = tablaUmf.get("cveIdUmf");
			subqueryUmf.select(seleccionUmf).distinct(true);
			Predicate parametrosUmf = cb.conjunction();
			parametrosUmf.getExpressions().add(cb.equal(tablaUmf.get("dicSubdelegacion").get("dicDelegacion").get("cveIdDelegacion").as(Integer.class), idDelegacion));
			
			Predicate wherePrincipal = cb.conjunction();
			wherePrincipal.getExpressions().add(cb.in(root.get("dicUmf").get("cveIdUmf")).value(subqueryUmf));
			//Se agrega la validacion de fecha Null a la consulta
			wherePrincipal.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
			
			Subquery<Integer> subqueryMunicipio = subqueryUmf.subquery(Integer.class);
			Root<DitMunicipioSubdelegacion> tablaMunicipio= subqueryMunicipio.from(DitMunicipioSubdelegacion.class);
			Path<Integer> seleccionMunSub = tablaMunicipio.get("dicSubdelegacion").get("cveIdSubdelegacion");
			subqueryMunicipio.select(seleccionMunSub).distinct(true);
			
			parametrosUmf.getExpressions().add(cb.in(tablaUmf.get("dicSubdelegacion").get("cveIdSubdelegacion")).value(subqueryMunicipio));
			subqueryUmf.where(parametrosUmf);
			
			Subquery<Integer> subqueryImssInegi = subqueryMunicipio.subquery(Integer.class);
			Root<DitMunicipioImssInegi> tablaImssInegi = subqueryImssInegi.from(DitMunicipioImssInegi.class);
			Path<Integer> seleccionImssInegi = tablaImssInegi.get("dicMunicipioImss").get("cveIdMunicipioImss");
			subqueryImssInegi.select(seleccionImssInegi).distinct(true);
			Predicate parametrosImssInegi = cb.conjunction();
			parametrosImssInegi.getExpressions().add(cb.equal(tablaImssInegi.get("dgCatMunicipio").get("id").get("cveEnt").as(Integer.class),idEntidad));
			parametrosImssInegi.getExpressions().add(cb.equal(tablaImssInegi.get("dgCatMunicipio").get("id").get("cveMun").as(Integer.class),idMunicipio));
			subqueryImssInegi.where(parametrosImssInegi);
			
			Predicate parametrosMunicipio = cb.conjunction();
			parametrosMunicipio.getExpressions().add(cb.in(tablaMunicipio.get("dicMunicipioImss").get("cveIdMunicipioImss")).value(subqueryImssInegi));
			subqueryMunicipio.where(parametrosMunicipio);
			
			queryPrincipal.where(wherePrincipal);
			
			List<DgCodigosPostale> entrada = em.createQuery(queryPrincipal).getResultList();			
			
			for(DgCodigosPostale codigo: entrada) {
				Asentamiento encontrado = AsentamientoParser.persisToModel(codigo.getDgAsentamiento());
				encontrado.setCodigoPostal(new CodigoPostal());
				encontrado.getCodigoPostal().setCodigoPostal(codigo.getId().getCodigo());
				salida.add(encontrado);
			}
		} catch (Exception e) {
			logger.error("Error - findAsentamientoByDelegacionMunicipioEntidad", e);
			throw e;
		}

		return salida;
	}
	
}
