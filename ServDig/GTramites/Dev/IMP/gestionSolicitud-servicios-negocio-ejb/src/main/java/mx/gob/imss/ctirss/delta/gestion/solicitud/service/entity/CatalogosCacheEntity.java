package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.persistence.catalogos.cache.DicRemitenteCorreo;
import mx.gob.imss.ctirss.delta.persistence.util.ConstantesCacheCatalogoName;
import mx.gob.imss.ctirss.delta.persistence.util.PersistenceUnitCatalogosCacheServiceEntity;


@Stateless(mappedName = "catalogosCacheEntity")
public class CatalogosCacheEntity extends PersistenceUnitCatalogosCacheServiceEntity implements CatalogosCacheEntityLocal {

	private static final Logger log = LoggerFactory.getLogger(CatalogosCacheEntity.class);
	@Override
	public DicRemitenteCorreo getRemitenteCorreo(String remitente) throws  Exception {
		if(StringUtils.isEmpty(remitente))
			return getRemitenteCorreoDefault();
		try {
			 Criteria criteria = this.getSession().createCriteria(
					 DicRemitenteCorreo.class , "remi");
			 criteria.add(Restrictions.eq("remi.refRemitenteCorreo", remitente));
	         criteria.setCacheable(true);
	         criteria.setCacheMode(CacheMode.NORMAL);
	         criteria.setCacheRegion(ConstantesCacheCatalogoName.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
	         return (DicRemitenteCorreo) criteria.uniqueResult();
		}catch(HibernateException e) {
			log.error("error en la consulta de hibernate de remintente"  + remitente, e);
			throw new Exception(e);
		}catch(Exception e) {
			log.error("error al consultar el catalogo de DicRemitenteCorreo remitente : " + remitente, e);
			throw e;
		}
	}
	@Override
	public DicRemitenteCorreo getRemitenteCorreoDefault() throws Exception {
		try {
			 Criteria criteria = this.getSession().createCriteria(
					 DicRemitenteCorreo.class , "remi");
			 criteria.add(Restrictions.eq("remi.indRemitentePorDefecto", new Boolean(true)));
	          criteria.setCacheable(true);
		         criteria.setCacheMode(CacheMode.NORMAL);
		         criteria.setCacheRegion(ConstantesCacheCatalogoName.REGION_CACHE_QUERY_CATALOGOS_GENERALES);
	          return (DicRemitenteCorreo) criteria.uniqueResult();
		}catch(HibernateException e) {
			log.error("error en la consulta de hibernate de remintente default" , e);
			throw new Exception(e);
		}catch(Exception e) {
			log.error("error al consultar el catalogo de DicRemitenteCorreo remitente default : " , e);
			throw e;
		}
	}

}
