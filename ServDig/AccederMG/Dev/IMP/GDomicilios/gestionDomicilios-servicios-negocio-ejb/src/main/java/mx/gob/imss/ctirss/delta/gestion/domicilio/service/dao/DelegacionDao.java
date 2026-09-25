package mx.gob.imss.ctirss.delta.gestion.domicilio.service.dao;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility.AsentamientoUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility.EntidadFederativaUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility.MunicipioUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility.UMFUtilityLocal;
import mx.gob.imss.ctirss.delta.model.derechohabiente.AsentamientoUMF;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;
import mx.gob.imss.ctirss.delta.persistence.DitMunicipioImssInegi;
import mx.gob.imss.ctirss.delta.persistence.DitMunicipioSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "delegacionDAO", mappedName = "delegacionDAO")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DelegacionDao extends AbstractServiceEntity implements DelegacionDaoLocal {
	
	@EJB
	private AsentamientoUtilityLocal asentamientoUtilityLocal;
	@EJB
	private EntidadFederativaUtilityLocal entidadFederativaUtilityLocal;
	@EJB
	private MunicipioUtilityLocal municipioUtilityLocal;
	@EJB
	private UMFUtilityLocal umfUtilityLocal;
	
	/**
	 * @author mario.teran
	 * MEtodo paraencontrar las entidades federativas relacionadas con una Delegacion
	 * @param idDelegacion el id de la delegacion
	 * @return List<EntidadFederativa> Lista con lasentidades que atiende esa delegacion
	 */
	@Override
	public List<EntidadFederativa> findEntidaFederativaByDelegacion(
			Long idDelegacion) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DgCatEstado> cq = cb.createQuery(DgCatEstado.class);
		Root<DitMunicipioSubdelegacion> root = cq.from(DitMunicipioSubdelegacion.class);
		Path<DgCatEstado> path = root.get("dicMunicipioImss").get("dgCatEstado");
		cq.select(path).distinct(true);
		
		Predicate conj = cb.conjunction();
		conj.getExpressions().add(cb.equal(root.get("dicSubdelegacion").get("dicDelegacion").get("cveIdDelegacion").as(Integer.class), idDelegacion));
		cq.where(conj);
		List<DgCatEstado> estados = em.createQuery(cq).getResultList();
		
		return entidadFederativaUtilityLocal.persistToModelList(estados);
	}

	/**
	 * @author mario.teran
	 * Metodo para encontrar los mucinicipios que corresponde a una delegacion y aun estado
	 * @param idDelegacion la delegacion a la que pertenece el municipio
	 * @param idEstado el estado al que pertenece el municipio
	 * @return List<Municipio> la lista de municipios que estan en la delegacion y el estado indicados
	 */
	@Override
	public List<Municipio> findMunicipiosByDelegacionEstado(
			Long idDelegacion, Long idEstado) {
		
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
		
		List<DgCatMunicipio> municipios = em.createQuery(cq).getResultList();
		return municipioUtilityLocal.persistToModelList(municipios);
	}

	/**
	 * @author mario.teran
	 * Metodo para obtener los asentamientos que atiende una delegacion en base a los siguientes parametros
	 * @param idDelegacion el is de l delegacion
	 * @param idMunicipio el id del municipio
	 * @param idEntidad el id del estado
	 * @return List<Asentamiento> La lista de asentamientos atendidos en un municipio que pertenece a una delegacion
	 */
	@Override
	public List<Asentamiento> findAsentamientoByDelegacionMunicipioEntidad(
			Long idDelegacion, Long idMunicipio, Long idEntidad) {
		// TODO Auto-generated method stub

		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<DgCodigosPostale> queryPrincipalA = cb.createQuery(DgCodigosPostale.class);
		Root<DgCodigosPostale> asentamientos = queryPrincipalA.from(DgCodigosPostale.class);
		queryPrincipalA.select(asentamientos);
		
		Predicate whereAsentamientos = cb.conjunction();
		whereAsentamientos.getExpressions().add(cb.equal(asentamientos.get("id").get("cveEnt").as(Integer.class), idEntidad));
		whereAsentamientos.getExpressions().add(cb.equal(asentamientos.get("id").get("cveMun").as(Integer.class), idMunicipio));
		
		queryPrincipalA.where(whereAsentamientos);
		
		List<DgCodigosPostale> entrada = em.createQuery(queryPrincipalA).getResultList();
		List<Asentamiento> salida = new ArrayList<Asentamiento>();
		
		for(DgCodigosPostale codigo: entrada) {
			Asentamiento encontrado = asentamientoUtilityLocal.persisToModel(codigo.getDgAsentamiento());
			encontrado.setCodigoPostal(new CodigoPostal());
			encontrado.getCodigoPostal().setCodigoPostal(codigo.getId().getCodigo());
			salida.add(encontrado);
		}
		
		
		return salida;
	}
	
	@Override
	public List<Asentamiento> findAsentamientosByUmf(Long idUmf){
		// TODO Auto-generated method stub
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DitUmfCodPo> query= cb.createQuery(DitUmfCodPo.class);
		Root<DitUmfCodPo> root = query.from(DitUmfCodPo.class); 
		query.select(root);
		
		Predicate conjunction = cb.conjunction();
		List<Asentamiento> salida = new ArrayList<Asentamiento>();

			conjunction.getExpressions().add(cb.equal(root.get("dicUmf").get("cveIdUmf").as(Integer.class), idUmf)); 
			conjunction.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
			query.where(conjunction);
			
			List<DitUmfCodPo> entrada = em.createQuery(query).getResultList();
			
			
			for(DitUmfCodPo codigo: entrada) {
				//salida.add(CodigoPostalParser.persistToModel(codigo.getDgCodigosPostale()));
				Asentamiento encontrado = asentamientoUtilityLocal.persisToModel(codigo.getDgCodigosPostale().getDgAsentamiento());
				encontrado.setCodigoPostal(new CodigoPostal());
				encontrado.getCodigoPostal().setCodigoPostal(codigo.getDgCodigosPostale().getId().getCodigo());
				salida.add(encontrado);
			}
		
		
		return salida;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Asentamiento> findAsentamientosByUmfCodPos(Long idUmf,
			String codigoPostal) {
		List<Asentamiento> salida = null;
		
		Criteria query = this.getSession().createCriteria(DitUmfCodPo.class);
		query.setProjection(Projections.distinct(Projections.property("dgCodigosPostale")));
		query.createAlias("dicUmf", "umf");
		query.add(Restrictions.eq("umf.cveIdUmf", idUmf));
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		Criteria queryAsen = query.createCriteria("dgCodigosPostale");
		queryAsen.add(Restrictions.eq("id.codigo", codigoPostal));
		
		List<DgCodigosPostale> codigos = query.list();
		
		if(codigos != null && !codigos.isEmpty()) {
			salida = new ArrayList<Asentamiento>();
			
			for(DgCodigosPostale codigo: codigos) {
				Asentamiento encontrado = asentamientoUtilityLocal.persisToModel(codigo.getDgAsentamiento());
				encontrado.setCodigoPostal(new CodigoPostal());
				encontrado.getCodigoPostal().setCodigoPostal(codigo.getId().getCodigo());
				salida.add(encontrado);
			}
			
		}
		/*
		//return salida;
		Criteria query = this.getSession().createCriteria(DicUmfCodigoPostal.class);
		query.createAlias("dicUmf", "umf");
		query.add(Restrictions.eq("umf.cveIdUmf", idUmf));
		query.add(Restrictions.eq("numCodigoPostal", codigoPostal));
		query.add(Restrictions.isNull("fecRegistroBaja"));
	
		List<Asentamiento> salida = null;

		List<DicUmfCodigoPostal> entrada = query.list();

		if(entrada.size() >0) {
			salida = new ArrayList<Asentamiento>();
			
			Criteria queryAsentamientos = this.getSession().createCriteria(DgCodigosPostale.class);
			queryAsentamientos.createAlias("id", "id");
			queryAsentamientos.add(Restrictions.eq("id.codigo", codigoPostal));
			
			List<DgCodigosPostale> cps = queryAsentamientos.list();
			
			if(cps.size() != 0) {
				salida = new ArrayList<Asentamiento>();
				
				for(DgCodigosPostale dgCp: cps) {
					salida.add(asentamientoUtilityLocal.persisToModel(dgCp.getDgAsentamiento()));
				}
			}

		}
		*/

		return salida;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Asentamiento> findAsentamientoByDelegacionCpYUmfsOrigenDestino(
			List<Long> idDelegacion, String cp, Long idUmfOrigen,
			Long idUmfDestino) {
		List<Asentamiento> salida = null;
		List<Long> idsUmf = new ArrayList<Long>();
		if(idUmfOrigen != null && idUmfOrigen.intValue() != 0) {
			idsUmf.add(idUmfOrigen);
		}
		if(idUmfDestino != null && idUmfDestino.intValue() != 0 && !idsUmf.contains(idUmfDestino)) {
			idsUmf.add(idUmfDestino);
		}
		
		Criteria query = this.getSession().createCriteria(DitUmfCodPo.class);
		query.setProjection(Projections.distinct(Projections.property("dgCodigosPostale")));
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		Criteria queryAsen = query.createCriteria("dgCodigosPostale");
		queryAsen.add(Restrictions.eq("id.codigo", cp));
		
		Criteria queryUmf = query.createCriteria("dicUmf");
		
		if(!idsUmf.isEmpty()) {
			if(idsUmf.size() == 1) {
				queryUmf.add(Restrictions.eq("cveIdUmf", idUmfDestino));
			} else {
				queryUmf.add(Restrictions.eq("cveIdUm", idsUmf));
			}
		}
		
		if(idDelegacion != null && !idDelegacion.isEmpty()) {
			Criteria querySub = queryUmf.createCriteria("dicSubdelegacion");
			querySub.createAlias("dicDelegacion", "del");
			
			if(idDelegacion.size() > 1) {
				querySub.add(Restrictions.in("del.cveIdDelegacion", idDelegacion));
			} else {
				querySub.add(Restrictions.eq("del.cveIdDelegacion", idDelegacion.get(0)));
			}
		}
		
		
		List<DgCodigosPostale> dgCodigos = query.list();
		
		if(dgCodigos != null && !dgCodigos.isEmpty()) {
			salida = new ArrayList<Asentamiento>();
			
			for(DgCodigosPostale codigo: dgCodigos) {
				Asentamiento encontrado = asentamientoUtilityLocal.persisToModel(codigo.getDgAsentamiento());
				encontrado.setCodigoPostal(new CodigoPostal());
				encontrado.getCodigoPostal().setCodigoPostal(codigo.getId().getCodigo());
				salida.add(encontrado);
			}
			
		}
		
		return salida;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Asentamiento> findAsentamientoByDelegacionCp(List<Long> idDelegacion,
			String cp) {
		List<Asentamiento> salida = null;
		
		Criteria query = this.getSession().createCriteria(DitUmfCodPo.class);
		query.setProjection(Projections.distinct(Projections.property("dgCodigosPostale")));
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		Criteria queryAsen = query.createCriteria("dgCodigosPostale");
		queryAsen.add(Restrictions.eq("id.codigo", cp));
		
		Criteria queryUmf = query.createCriteria("dicUmf");
		Criteria querySub = queryUmf.createCriteria("dicSubdelegacion");
		querySub.createAlias("dicDelegacion", "del");
		
		if(idDelegacion.size() > 1) {
			querySub.add(Restrictions.in("del.cveIdDelegacion", idDelegacion));
		} else {
			querySub.add(Restrictions.eq("del.cveIdDelegacion", idDelegacion.get(0)));
		}
		
		
		List<DgCodigosPostale> dgCodigos = query.list();
		
		if(dgCodigos != null && !dgCodigos.isEmpty()) {
			salida = new ArrayList<Asentamiento>();
			
			for(DgCodigosPostale codigo: dgCodigos) {
				Asentamiento encontrado = asentamientoUtilityLocal.persisToModel(codigo.getDgAsentamiento());
				encontrado.setCodigoPostal(new CodigoPostal());
				encontrado.getCodigoPostal().setCodigoPostal(codigo.getId().getCodigo());
				salida.add(encontrado);
			}
			
		}
		
		/*
		Criteria queryUmfCp = this.getSession().createCriteria(DicUmfCodigoPostal.class);
		queryUmfCp.add(Restrictions.eq("numCodigoPostal", cp));
		Criteria queryUmf = queryUmfCp.createCriteria("dicUmf");
		Criteria querySub = queryUmf.createCriteria("dicSubdelegacion");
		querySub.createAlias("dicDelegacion", "del");
		querySub.add(Restrictions.in("del.cveIdDelegacion", idDelegacion));
		queryUmfCp.add(Restrictions.isNull("fecRegistroBaja"));
		
		queryUmfCp.setProjection(Projections.property("numCodigoPostal"));
		
		List<String> cps = queryUmfCp.list();
		List<Asentamiento> salida = null;
		
		if(!cps.isEmpty()) {
			salida = new ArrayList<Asentamiento>();
			
			Criteria queryAsentamientos = this.getSession().createCriteria(DgCodigosPostale.class);
			queryAsentamientos.add(Restrictions.eq("id.codigo", cp));
			
			List<DgCodigosPostale> dgCps = queryAsentamientos.list();
			
			if(dgCps.size() != 0) {
				salida = new ArrayList<Asentamiento>();
				
				for(DgCodigosPostale dgCp: dgCps) {
					salida.add(asentamientoUtilityLocal.persisToModel(dgCp.getDgAsentamiento()));
				}
			}
		}
		*/
		
		return salida;
	}

	@Override
	public List<AsentamientoUMF> findAsentamientosUmfByCP(String codigoPostal) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DitUmfCodPo> query= cb.createQuery(DitUmfCodPo.class);
		Root<DitUmfCodPo> root = query.from(DitUmfCodPo.class); 
		
		Predicate conjunction = cb.conjunction();
		List<AsentamientoUMF> salida = new ArrayList<AsentamientoUMF>();

		conjunction.getExpressions().add(cb.equal(root.get("dgCodigosPostale").get("id").get("codigo"), codigoPostal));
		conjunction.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
		query.where(conjunction);
			
		List<DitUmfCodPo> entrada = em.createQuery(query).getResultList();
			
			
		for(DitUmfCodPo codigo: entrada) {
			//salida.add(CodigoPostalParser.persistToModel(codigo.getDgCodigosPostale()));
			AsentamientoUMF encontrado = new AsentamientoUMF();
			encontrado.setAsentamiento(asentamientoUtilityLocal.persisToModel(codigo.getDgCodigosPostale().getDgAsentamiento()));
			encontrado.getAsentamiento().setCodigoPostal(new CodigoPostal());
			encontrado.getAsentamiento().getCodigoPostal().setCodigoPostal(codigo.getDgCodigosPostale().getId().getCodigo());
			encontrado.setUmf(umfUtilityLocal.persisToModel(codigo.getDicUmf()));
			salida.add(encontrado);
		}
		
		
		return salida;
	}
}
