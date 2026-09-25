package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.catalogo;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ConstantesComunesServiciosRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioImss;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioInegi;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EstadoCivil;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Pais;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Parentesco;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Sexo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Turno;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DgCatEstado;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DgCatMunicipio;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicCalidadParentesco;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicClase;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicDelegacion;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicDiasFestivo;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicDivision;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicEstadoCivil;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicFraccion;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicFraccionClase;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicGrupo;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicModalidad;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicMunicipioImss;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicPai;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicSexo;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicSubdelegacion;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicTipoPersona;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicTurno;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicUmf;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.util.PersistenceUnitCatalogosCacheServiceEntity;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ParserCatalogosEntityToModel;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.digital.modelo.domicilio.Delegacion;
import mx.gob.imss.digital.modelo.domicilio.Subdelegacion;

@Local(value = ICatalogoServiceEntityLocal	.class)
@Stateless
public class CatalogoServiceEntity extends PersistenceUnitCatalogosCacheServiceEntity implements ICatalogoServiceEntityLocal{

	private static final Logger log = LoggerFactory.getLogger(CatalogoServiceEntity.class);


	/**
	@Override
	public Sexo getCatalogoSexo(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalog de sexo " + idCatalogo);
		try {
			Statistics stats = getSession().getSessionFactory().getStatistics();
			stats.setStatisticsEnabled(true);
			printStats(stats, 1);
			Sexo sexo = em.createQuery(
			        "select new " +
			        "   mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Sexo(" +
			        "       sexo.cveIdSexo, " +
			        "       sexo.desSexo " +
			        "   ) " +
			        "from DicSexo sexo " +
			        "where sexo.cveIdSexo = :idCatalgo " +
			        "order by sexo.cveIdSexo asc ", Sexo.class)
				.setParameter("idCatalgo", idCatalogo)
			    .setHint(QueryHints.HINT_CACHEABLE, true)
			    .setHint(QueryHints.HINT_CACHE_REGION, "query.MunicipioEntity")
			    .setHint(QueryHints.HINT_CACHE_MODE, CacheMode.NORMAL)
			    .getSingleResult();

			printStats(stats, 2);
			return sexo;
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de sexo por id " + idCatalogo, e );
			throw e;
		}
	}	**/



	@Override
	public Sexo getCatalogoSexo(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalog de sexo " + idCatalogo);
		try {
			return ParserCatalogosEntityToModel.parserSexoRest(em.find(DicSexo.class,  new Long(idCatalogo)));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de sexo por id " + idCatalogo, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sexo> getCatalogoSexo() throws Exception {
		log.debug("llegue a consultar el catalog de sexo list " );
		try {
			Criteria criteria =getSession().createCriteria(DicSexo.class);
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CATALOGOS_GENERALES);

			return ParserCatalogosEntityToModel.parserSexoListRestList(criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de sexo list" , e );
			throw e;
		}
	}

	@Override
	public Parentesco getCatalogoParentesco(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalog de getCatalogoParentesco " + idCatalogo);
		try {
			return ParserCatalogosEntityToModel.parserParentescoRest(em.find(DicCalidadParentesco.class,  new Long(idCatalogo)));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de parentesco por id " + idCatalogo, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Parentesco> getCatalogoParentesco() throws Exception {
		log.debug("llegue a consultar el catalog de getCatalogoParentesco list " );
		try {
			Criteria criteria =getSession().createCriteria(DicCalidadParentesco.class);
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
			return ParserCatalogosEntityToModel.parserParentescoListRest(criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoParentesco list" , e );
			throw e;
		}
	}

	@Override
	public EntidadFederativa getCatalogoEntidadFed(String idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalog de getCatalogoEntidadFed " + idCatalogo);
		try {
			return ParserCatalogosEntityToModel.parserEntidadFederativaRest(em.find(DgCatEstado.class,  idCatalogo));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoEntidadFed por id " + idCatalogo, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntidadFederativa> getCatalogoEntidadFed() throws Exception {
		log.debug("llegue a consultar el catalog de getCatalogoEntidadFed list " );
		try {
			Criteria criteria =getSession().createCriteria(DgCatEstado.class, "edo");
			criteria.add(Restrictions.eq("edo.indEdoGeografico", new Boolean(true)));
			criteria.addOrder(Order.asc("edo.cveEnt"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
			return ParserCatalogosEntityToModel.parserEntidadFederativaListRest(criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoEntidadFed list" , e );
			throw e;
		}
	}

	@Override
	public EstadoCivil getCatalogoEstadoCivil(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoEstadoCivil " + idCatalogo);
		try {
			return ParserCatalogosEntityToModel.parserEdoCivilRest(em.find(DicEstadoCivil.class,  idCatalogo));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoEstadoCivil por id " + idCatalogo, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadoCivil> getCatalogoEstadoCivil() throws Exception {
		log.debug("llegue a consultar el catalog de getCatalogoEstadoCivil list " );
		try {
			Criteria criteria =getSession().createCriteria(DicEstadoCivil.class, "edo");
			criteria.addOrder(Order.asc("edo.cveIdEstadoCivil"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
			return ParserCatalogosEntityToModel.parserEdoCivilListRest(criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoEstadoCivil list" , e );
			throw e;
		}
	}

	@Override
	public Pais getCatalogoPais(Long idPais) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoPais " + idPais);
		try {
			return ParserCatalogosEntityToModel.parserPaisRest(em.find(DicPai.class,  idPais));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoPais por id " + idPais, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pais> getCatalogoPais() throws Exception {
		log.debug("llegue a consultar el catalog de getCatalogoPais list " );
		try {
			Criteria criteria =getSession().createCriteria(DicPai.class, "p");
			criteria.addOrder(Order.asc("p.cveIdPais"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
			return ParserCatalogosEntityToModel.parserPaisListRest(criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoPais list" , e );
			throw e;
		}
	}

	@Override
	public Turno getCatalogoTurnoByID(Long idTurno) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoTurnoByID " + idTurno);
		try {
			return ParserCatalogosEntityToModel.parserTurnoRest(em.find(DicTurno.class,  idTurno));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoTurnoByID por id " + idTurno, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Turno> getCatalogoTurno() throws Exception {
		log.debug("llegue a consultar el catalog de getCatalgoTurno list " );
		try {
			Criteria criteria =getSession().createCriteria(DicTurno.class, "t");
			criteria.addOrder(Order.asc("t.cveIdTurno"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
			return ParserCatalogosEntityToModel.parserTurnoListRest(criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalgoTurno list" , e );
			throw e;
		}
	}

	@Override
	public Delegacion getCatalogoDelegacionById(Long idDelegacion) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoDelegacionById " + idDelegacion);
		try {
			return ParserCatalogosEntityToModel.parserDelegacionRest(em.find(DicDelegacion.class,  idDelegacion));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoDelegacionById por id " + idDelegacion, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Delegacion> getCatalogoDelegacion() throws Exception {
		log.debug("llegue a consultar el catalog de getCatalogoDelegacion list " );
		try {
			Criteria criteria =getSession().createCriteria(DicDelegacion.class, "d");
			criteria.addOrder(Order.asc("d.cveIdDelegacion"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_DEL_SUBDEL);
			return ParserCatalogosEntityToModel.parserDelegacionListRest(criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoDelegacion list" , e );
			throw e;
		}
	}

	@Override
	public Subdelegacion getCatalogoSubDelegacionById(Long idSubDelegacion) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoSubDelegacionById " + idSubDelegacion);
		try {
			return ParserCatalogosEntityToModel.parserSubDelegacionRest(em.find(DicSubdelegacion.class,  idSubDelegacion));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoSubDelegacionById por id " + idSubDelegacion, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subdelegacion> getCatalogoSubDelegacionByIdDelegacion(Long idDelegacion) throws Exception {
		log.debug("llegue a consultar el catalog de getCatalogoSubDelegacionByIdDelegacion list " + idDelegacion );
		try {
			Criteria criteria =getSession().createCriteria(DicSubdelegacion.class, "sd");
			criteria.createAlias("sd.dicDelegacion", "d");
			criteria.add(Restrictions.eq("d.cveIdDelegacion", idDelegacion));
			criteria.addOrder(Order.asc("sd.cveIdSubdelegacion"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_DEL_SUBDEL);
			return ParserCatalogosEntityToModel.parserSubDelegacionListRest(criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoSubDelegacionByIdDelegacion list " + idDelegacion , e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadMedicaFamiliar> getCatalogoUMfBySubdelegacionNivelAtencion(Long idSubdelegacion, Long idNivelAtencion)
			throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoUMfBySubdelegacionNivelAtencion list " + idSubdelegacion );
		try {
			Criteria criteria = this.getSession().createCriteria(DicUmf.class);
			criteria.createAlias("dicSubdelegacion", "subDel");
			criteria.add(Restrictions.eq("subDel.cveIdSubdelegacion", idSubdelegacion));
			criteria.add(Restrictions.isNull("fecRegistroBaja"));
			if(idNivelAtencion != null) {
				criteria.createAlias("accNivelAtencion", "nivel");
				criteria.add(Restrictions.eq("nivel.cveIdNivelAtencion", idNivelAtencion));
			}
			criteria.addOrder(Order.asc("nomCorto"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_DEL_SUBDEL);
			List<DicUmf>  lstUmf = criteria.list();
			return ParserCatalogosEntityToModel.parserUmfEnityToModelList(lstUmf);

		} catch (Exception e) {
			log.error("Ocurrio unn Error al querer consultar las UMF - findUnidadesBySubdelegacionNivelAtencio", e);
			throw e;
		}
	}

	@Override
	public UnidadMedicaFamiliar getCatalogoUMfById(Long idUmf) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoUMfById " + idUmf);
		try {
			return ParserCatalogosEntityToModel.parserUmfEnityToModel(em.find(DicUmf.class,  idUmf));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoUMfById por id " + idUmf, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Modalidad> getCatalogoModalidad() throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoModalidad list " );
		try {
			Criteria criteria =getSession().createCriteria(DicModalidad.class, "m");
			criteria.addOrder(Order.asc("m.numModalidad"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
			List<DicModalidad> modalidadList  = criteria.list();
			return ParserCatalogosEntityToModel.parserModalidadEntityToModelList(modalidadList);
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoModalidad " , e );
			throw e;
		}
	}

	@Override
	public Modalidad getCatalogoModalidadByCveModalidad(String idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoModalidadByCveModalidad " + idCatalogo);
		try {
			Criteria criteria =getSession().createCriteria(DicModalidad.class, "m");
			criteria.add(Restrictions.eq("m.numModalidad", idCatalogo));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
			return ParserCatalogosEntityToModel.parserModalidadEntityToModel((DicModalidad)criteria.list().get(0));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoModalidadByCveModalidad por id " + idCatalogo, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoPersona> getCatalogoTipoPersona() throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoTipoPersona list " );
		try {
			Criteria criteria =getSession().createCriteria(DicTipoPersona.class, "tp");
			criteria.addOrder(Order.asc("tp.cveIdTipoPersona"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
			List<DicTipoPersona> entityList  = criteria.list();
			return ParserCatalogosEntityToModel.parserTipoPersonaEntityToModelList(entityList);
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoTipoPersona " , e );
			throw e;
		}
	}

	@Override
	public TipoPersona getCatalogoTipoPersonaByIdTipoPersona(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoTipoPersonaByIdTipoPersona " + idCatalogo);
		try {
			return ParserCatalogosEntityToModel.parserTipoPersonaEntityToModel(em.find(DicTipoPersona.class,  idCatalogo));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoTipoPersonaByIdTipoPersona por id " + idCatalogo, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Clase> getCatalogoClase() throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoClase " );
		try {
			Criteria criteria =getSession().createCriteria(DicClase.class, "entity");
			criteria.addOrder(Order.asc("entity.cveIdClase"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CLASIFICACION);
			return ParserCatalogosEntityToModel.parserClaseEntityToModelList((List<DicClase>)criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoClase" , e );
			throw e;
		}
	}

	@Override
	public Clase getCatalogoClaseByCveClase(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoClaseByCveClase " + idCatalogo);
		try {
			return ParserCatalogosEntityToModel.parserClaseEntityToModel(em.find(DicClase.class,  idCatalogo));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoClaseByCveClase por id " + idCatalogo, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fraccion> getCatalogoFraccionByIdClase(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoFraccionByIdClase idCkase" + idCatalogo);
		try {
			Criteria criteria =getSession().createCriteria(DicFraccion.class, "entity");
			criteria.createAlias("entity.dicFraccions", "fraccionClase");
			criteria.createAlias("fraccionClase.dicClase", "clase");
			criteria.add(Restrictions.eq("clase.cveIdClase", idCatalogo));
			criteria.addOrder(Order.asc("entity.numFraccion"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CLASIFICACION);
			return ParserCatalogosEntityToModel.parserFraccionEntityToModelList((List<DicFraccion>)criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoFraccionByIdClase" , e );
			throw e;
		}

	}

	@Override
	public Fraccion getCatalogoFraccionByCveFraccion(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoFraccionByCveFraccion " + idCatalogo);
		try {
			return ParserCatalogosEntityToModel.parserFraccionEntityToModel(em.find(DicFraccion.class,  idCatalogo));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoFraccionByCveFraccion por id " + idCatalogo, e );
			throw e;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Division> getCatalogoDivision() throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoDivision " );
		try {
			Criteria criteria =getSession().createCriteria(DicDivision.class, "entity");
			criteria.addOrder(Order.asc("entity.numDivision"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CLASIFICACION);
			return ParserCatalogosEntityToModel.parserDivisionEntityToModelList((List<DicDivision>)criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoDivision" , e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grupo> getCatalogoGrupoByIdDivision(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoGrupoByIdDivision idCkase" + idCatalogo);
		try {
			Criteria criteria =getSession().createCriteria(DicGrupo.class, "entity");
			criteria.createAlias("entity.dicDivision", "dvision");
			criteria.add(Restrictions.eq("dicDivision.cveIdDivision", idCatalogo));
			criteria.addOrder(Order.asc("entity.numGrupo"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CLASIFICACION);
			return ParserCatalogosEntityToModel.parserGrupoEntityToModelList((List<DicGrupo>)criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoGrupoByIdDivision" , e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fraccion> getCatalogoFraccionByIdGrupo(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoFraccionByIdGrupo idCkase" + idCatalogo);
		try {
			Criteria criteria =getSession().createCriteria(DicFraccion.class, "entity");
			criteria.createAlias("entity.dicGrupo", "grupo");
			criteria.add(Restrictions.eq("grupo.cveIdGrupo", idCatalogo));
			criteria.addOrder(Order.asc("entity.numFraccion"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CLASIFICACION);
			return ParserCatalogosEntityToModel.parserFraccionEntityToModelList((List<DicFraccion>)criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoFraccionByIdGrupo" , e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fraccion> getCatalogoFraccionConClaseActivaByIdGrupo(Long idCatalogo) throws Exception {
		log.debug("llegue a consultar el catalogo de getCatalogoFraccionConClaseActivaByIdGrupo grupo" + idCatalogo);
		try {
			Criteria criteria =getSession().createCriteria(DicFraccionClase.class, "entity");
			criteria.createAlias("entity.dicFraccion", "fraccion");
			criteria.createAlias("fraccion.dicGrupo", "grupo");
			criteria.add(Restrictions.isNull("entity.fecFin"));
			criteria.add(Restrictions.eq("grupo.cveIdGrupo", idCatalogo));
			criteria.addOrder(Order.asc("fraccion.numFraccion"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CLASIFICACION);
			return ParserCatalogosEntityToModel.parserFraccionClaseEntityToModelList((List<DicFraccionClase>)criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatalogoFraccionConClaseActivaByIdGrupo" , e );
			throw e;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subdelegacion> getSubDelegacionesPorCodigoPostal(String codigoPostal) throws Exception {
		log.debug("llegue a consultar el catalogo de getSubDelegacionesPorCodigoPostal cp " + codigoPostal);
		try {
			StringBuffer strQuery = new StringBuffer();
			strQuery.append("select distinct sub ");
			strQuery.append("from  DicMunicipioImss muni, DitMunicipioImssInegi ine, DgCodigosPostale cod,  "
					+ " DitMunicipioSubdelegacion musu, DicSubdelegacion sub  ");
			strQuery.append(" where sub.cveIdSubdelegacion = musu.dicSubdelegacion.cveIdSubdelegacion");
			strQuery.append("	and sub.fecRegistroBaja is null");
			strQuery.append("	and musu.dicMunicipioImss.cveIdMunicipioImss = muni.cveIdMunicipioImss");
			strQuery.append("	and muni.cveIdMunicipioImss = ine.dicMunicipioImss.cveIdMunicipioImss ");
			strQuery.append("	and muni.dgCatEstado.cveEnt = ine.dgCatMunicipio.id.cveEnt ");
			strQuery.append("	and ine.dgCatMunicipio.id.cveMun = cod.id.cveMun ");
			strQuery.append("	and ine.dgCatMunicipio.id.cveEnt = cod.id.cveEnt ");
			strQuery.append("	and cod.id.codigo = :codigoPostal ");
			strQuery.append("	and muni.cveMunicipio <> :cveMunLosAng'");
			log.debug("el query a ejecutar es " + strQuery.toString());

			Query query = this.getSession().createQuery(strQuery.toString());
			query.setCacheable(true);
			query.setCacheMode(CacheMode.NORMAL);
			query.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_DEL_SUBDEL);
			query.setParameter("codigoPostal", codigoPostal);
			query.setParameter("cveMunLosAng", "Z28");
			return ParserCatalogosEntityToModel.parserSubDelegacionListRest((List<DicSubdelegacion>)query.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getSubDelegacionesPorCodigoPostal" , e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MunicipioInegi> getCatalogoMunicipioInegi(String cveEntidadFed) throws Exception {

		log.debug("llegando a la consulta getCatalogoMunicipioInegi {cveEntidadFed}" , cveEntidadFed );
		try {
			Criteria criteria = getSession().createCriteria(DgCatMunicipio.class, "mun");
			criteria.createAlias("mun.dgCatEstado", "edo");
			criteria.add(Restrictions.eq("edo.cveEnt", cveEntidadFed ));
			criteria.addOrder(Order.asc("mun.id.cveMun"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion("ConstantesComunesServiciosRest.REGION_CACHE_QUERY_DOMICILIO)");
			return ParserCatalogosEntityToModel.parserMunicipioInegiEntityToModelList((List<DgCatMunicipio>)criteria.list());
		}catch(Exception e) {
			log.error("ocurio un error al consultar getCatalogoMunicipioInegi " + cveEntidadFed, e );
			throw e;
		}
	}


	@Override
	public MunicipioInegi getMunicipioInegi(String cveEntidadFed, String cveMunicipioInegi) throws Exception {
		log.debug("llegando a la consulta getMunicipioInegi {cveEntidadFed}" , cveEntidadFed );
		try {
			Criteria criteria = getSession().createCriteria(DgCatMunicipio.class, "mun");
			criteria.createAlias("mun.dgCatEstado", "edo");
			criteria.add(Restrictions.eq("edo.cveEnt", cveEntidadFed ));
			criteria.add(Restrictions.eq("mun.id.cveMun", cveMunicipioInegi ));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_DOMICILIO);
			return ParserCatalogosEntityToModel.parserMunicipioInegiEntityToModel((DgCatMunicipio)criteria.uniqueResult());
		}catch(Exception e) {
			log.error("ocurio un error al consultar getMunicipioInegi " + cveEntidadFed, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Date> getDiasinhabilesByAnio(Long numAnio) throws Exception {
		log.debug("llegue a consultar el catalogo de getDiasinhabilesByAnio" + numAnio);
		try {
			StringBuffer strQuery = new StringBuffer();
			strQuery.append("select dia ");
			strQuery.append("from  DicDiasFestivo dia " ); 
			strQuery.append(" where to_char(dia.fecDiaFestivo, 'yyyy') = :numAnio'");
			log.debug("el query a ejecutar es " + strQuery.toString());
			Query query = this.getSession().createQuery(strQuery.toString());
			query.setParameter("numAnio", numAnio);
			query.setCacheable(true);
			query.setCacheMode(CacheMode.NORMAL);
			query.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
			return ParserCatalogosEntityToModel.parserDiaFestivoEntityToModelList((List<DicDiasFestivo>)query.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getSubDelegacionesPorCodigoPostal" , e );
			throw e;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Asentamiento> getAsentamientoPorCodigoPosta(String codigo)
			throws Exception {
		log.debug("consultando los asentamientos por codigo postal [" + codigo +"]");
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select new mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento( asent.id.cveAsen,  asent.nomAsen , asent.id.cveEnt, asent.id.cveMun, estado.nomEnt,mun.nomMun )");
			sql.append(" from DgCodigosPostale code join code.dgAsentamiento as asent "
					+ " join asent.dgCatMunicipio as mun"
					+ " join mun.dgCatEstado as estado");
			sql.append(" where code.id.codigo = :codigo");
			sql.append(" order by asent.nomAsen");
			Query query = this.getSession().createQuery(sql.toString());
			query.setParameter("codigo", codigo);
			query.setCacheable(true);
			query.setCacheMode(CacheMode.NORMAL);
			query.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_DOMICILIO);
			return query.list();

		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getAsentamientoPorCodigoPosta " , e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.Localidad> getLocalidadesPorMunicipio(MunicipioInegi municipio)
			throws Exception {
		log.debug("consultando las localidades por municipio  [" + municipio.getCveMunicipio() + "] y entidad " + municipio.getCveEntidadFed() );
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select new mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.Localidad( local.id.cveLoc, local.nomLoc , "
						+ "	local.id.cveEnt, estado.nomEnt, local.id.cveMun, mun.nomMun )");
			sql.append(" from DgCatLocalidad local " 
						+ " join local.dgCatMunicipio as mun"
						+ " join mun.dgCatEstado as estado");
			sql.append(" where local.id.cveMun = :municipio");
			sql.append(" and local.id.cveEnt = :entidad");
			sql.append(" order by local.nomLoc");

			Query query = this.getSession().createQuery(sql.toString());
			query.setParameter("municipio", municipio.getCveMunicipio());
			query.setParameter("entidad", municipio.getCveEntidadFed());
			query.setCacheable(true);
			query.setCacheMode(CacheMode.NORMAL);
			query.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_DOMICILIO);
			return query.list();


		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de DG_LOCALIDAD " , e );
			throw e;
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MunicipioImss getMunicipioImss(String cveMunicipioImss)throws Exception {
		log.debug("llegue a consultar el catalog de getMunicipioImss" );
		try {
			Criteria criteria =getSession().createCriteria(DicMunicipioImss.class, "muni");
			criteria.add(Restrictions.eq("muni.cveMunicipio", cveMunicipioImss));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_DEL_SUBDEL);
			List<DicMunicipioImss> entradaList = criteria.list();
			ValidacionesComunesUtil.validaListaNulaVacia(entradaList, "La consulta de municipio IMSS no econtro registros");
			return ParserCatalogosEntityToModel.parserMunicipioImssEntityToModel(entradaList.get(0));
		}catch (Exception e) {
			log.error("ocurio un error al consultar el municipio IMSS" , e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MunicipioImss> getMunicipioImssByDelegacionSubdelegacion(Long cveDelegacionImss,
			Long cveSubDelegacionImss) throws Exception {
		log.debug("llegue a consultar el catalog de getMunicipioImssByDelegacionSubdelegacion" );
		try {
			Criteria criteria =getSession().createCriteria(DicMunicipioImss.class, "mun");
			criteria.createAlias("mun.ditMunicipioSubdelegacions", "ms");
			criteria.createAlias("ms.dicSubdelegacion", "sbd");
			criteria.createAlias("sbd.dicDelegacion", "dlg");
	
			criteria.add(Restrictions.eq("dlg.claveDelegacion",String.valueOf(cveDelegacionImss)));
			criteria.add(Restrictions.eq("sbd.claveSubdelegacion", String.valueOf(cveSubDelegacionImss)));
			criteria.add(Restrictions.isNull("ms.fecRegistroBaja"));
			criteria.add(Restrictions.isNull("mun.fecRegistroBaja"));
			
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_DEL_SUBDEL);

			return ParserCatalogosEntityToModel.parserMunicipioImssEntityToModelList(criteria.list());
		}catch (Exception e) {
			log.error("ocurio un error al consultar el municipio IMSS" , e );
			throw e;
		}
	}

}
