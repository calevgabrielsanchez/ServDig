package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.catalogo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ConstantesComunesServiciosRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunDelegacion;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunEntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunUmf;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.catComun.CatDelegacion;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.catComun.CatEntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.catComun.CatUmf;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.util.PersistenceUnitCatComunesCacheServiceEntity;

@Local(value = ICatComunServiceEntityLocal	.class)
@Stateless
public class CatComunServiceEntity extends PersistenceUnitCatComunesCacheServiceEntity implements ICatComunServiceEntityLocal{

	private static final Logger log = LoggerFactory.getLogger(CatComunServiceEntity.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<CatComunUmf> getCatComunUmfList(Long cveNivelAtencion, Long cveTipoUmf,
			String cveDelegacion) throws Exception{
		log.debug("llegue a consultar el catalogo getCatComunUmfList cveNivelAtencion, cveTipoUmf,  cveEntidadFederativa" 
				+ cveNivelAtencion + cveTipoUmf+ cveDelegacion);
		List<CatComunUmf> lstUmf= null;
		try {		
			Criteria criteria =getSession().createCriteria(CatUmf.class, "umf");
			if(cveDelegacion != null)
				criteria.add(Restrictions.eq("umf.cveDelegacion", cveDelegacion ));
			if(cveNivelAtencion != null)
				criteria.add(Restrictions.eq("umf.cveNivelAtencion", BigDecimal.valueOf(cveNivelAtencion)));
			if(cveTipoUmf != null)
				criteria.add(Restrictions.eq("umf.tipoUmf", BigDecimal.valueOf(cveTipoUmf )));

			criteria.addOrder(Order.asc("umf.nomUnidadMed"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CAT_COMUNES);
			List<CatUmf> listResultset = criteria.list();

			if(listResultset != null && !listResultset.isEmpty()) {
				lstUmf = new ArrayList<CatComunUmf>();
				for(CatUmf umf : listResultset) {
					CatComunUmf catUmf = new CatComunUmf();
					BeanUtils.copyProperties(umf, catUmf);
					lstUmf.add(catUmf);
				}
			}
			return lstUmf;
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatComunUmfList cveNivelAtencion, cveTipoUmf,  cveEntidadFederativa" 
					+ cveNivelAtencion+ cveTipoUmf+ cveDelegacion, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public CatComunUmf getCatComunUmf(String cvePresupuestal) throws Exception {
		log.debug("llegue a consultar el catalogo getCatComunUmf" + cvePresupuestal);
		CatComunUmf catUmf = null;
		try {		
			Criteria criteria =getSession().createCriteria(CatUmf.class, "umf");
			criteria.add(Restrictions.eq("umf.cvePresupuestal", cvePresupuestal ));
			//criteria.addOrder(Order.asc("umf.nomUnidadMed"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CAT_COMUNES);
			List<CatUmf> listResultset = criteria.list();
			if(listResultset != null && !listResultset.isEmpty()) {
				CatUmf umf = (CatUmf)listResultset.get(0);
				catUmf = new CatComunUmf();
				BeanUtils.copyProperties(umf, catUmf);
			}
			return catUmf;
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatComunUmf " + cvePresupuestal, e );
			throw e;
		}
	}

	@Override
	public CatComunDelegacion getCatComunDelegacion(String cveDelegacion) throws Exception {
		CatComunDelegacion catDelegacion = null;
		try {		
			CatDelegacion catDel = (CatDelegacion)getSession().load(CatDelegacion.class, cveDelegacion);
			if(catDel != null ) {
				catDelegacion = new CatComunDelegacion();
				BeanUtils.copyProperties(catDel, catDelegacion);
			}
			return catDelegacion;
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatComunDelegacion " + cveDelegacion, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatComunDelegacion> getCatComunDelegacionList() throws Exception {
		List<CatComunDelegacion> lstDelegacion= null;
		try {		
			Criteria criteria =getSession().createCriteria(CatDelegacion.class, "del");
			criteria.addOrder(Order.asc("del.cveDelegacion"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CAT_COMUNES);
			List<CatDelegacion> listResultset = criteria.list();

			if(listResultset != null && !listResultset.isEmpty()) {
				lstDelegacion = new ArrayList<CatComunDelegacion>();
				for(CatDelegacion del : listResultset) {
					CatComunDelegacion catDel = new CatComunDelegacion();
					BeanUtils.copyProperties(del, catDel);
					lstDelegacion.add(catDel);
				}
			}
			return lstDelegacion;
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatComunDelegacionList " , e );
			throw e;
		}
	}

	@Override
	public CatComunEntidadFederativa getCatComunEntidadFederativa(String cveEntidadFederativa) throws Exception {
		CatComunEntidadFederativa catEntidadFed = null;
		try {		
			CatEntidadFederativa catEnt = (CatEntidadFederativa)getSession().load(CatEntidadFederativa.class, cveEntidadFederativa);
			if(catEnt != null ) {
				catEntidadFed = new CatComunEntidadFederativa();
				BeanUtils.copyProperties(catEnt, catEntidadFed);
			}
			return catEntidadFed;
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatComunEntidadFederativa " + cveEntidadFederativa, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatComunEntidadFederativa> getCatComunEntidadFederativaList() throws Exception {
		List<CatComunEntidadFederativa> lstEntidadFed= null;
		try {		
			Criteria criteria =getSession().createCriteria(CatEntidadFederativa.class, "entidad");
			criteria.addOrder(Order.asc("entidad.cveEntidadFederativa"));
			criteria.setCacheable(true);
			criteria.setCacheMode(CacheMode.NORMAL);
			criteria.setCacheRegion(ConstantesComunesServiciosRest.REGION_CACHE_QUERY_CAT_COMUNES);
			List<CatEntidadFederativa> listResultset = criteria.list();

			if(listResultset != null && !listResultset.isEmpty()) {
				lstEntidadFed = new ArrayList<CatComunEntidadFederativa>();
				for(CatEntidadFederativa entidad : listResultset) {
					CatComunEntidadFederativa catEntidad = new CatComunEntidadFederativa();
					BeanUtils.copyProperties(entidad, catEntidad);
					lstEntidadFed.add(catEntidad);
				}
			}
			return lstEntidadFed;
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo de getCatComunEntidadFederativaList " , e );
			throw e;
		}
	}



}
