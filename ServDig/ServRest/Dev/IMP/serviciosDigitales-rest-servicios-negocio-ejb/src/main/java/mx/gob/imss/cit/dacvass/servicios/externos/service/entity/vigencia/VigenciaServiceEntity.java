package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.vigencia;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.persistence.util.PersistenceUnitSISCOBServiceEntity;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.vigencia.MgtInfincasegvig;

@Local(value = VigenciaServiceEntityLocal.class)
@Stateless
//@Stateless(name = "vigenciaServiceEntity", mappedName = "vigenciaServiceEntity")
public class VigenciaServiceEntity extends PersistenceUnitSISCOBServiceEntity implements VigenciaServiceEntityLocal{

	private static final Logger log = LoggerFactory.getLogger(VigenciaServiceEntity.class);
	@Override
	public MgtInfincasegvig getMgtInfincasegvig(String nss) throws Exception {
		
		log.debug("llegando a hacer la consulta de getCptCreinc14ImssRcv con nrp {}", nss);
		try {
			Criteria criteria = getSession().createCriteria(MgtInfincasegvig.class);
			criteria.add(Restrictions.eq("nss10", nss.substring(0, 10)));
			@SuppressWarnings("unchecked")
			List<MgtInfincasegvig> listMgtInfincasegvig = criteria.list();
			if(listMgtInfincasegvig != null && !listMgtInfincasegvig.isEmpty())
				return (MgtInfincasegvig)listMgtInfincasegvig.get(0);
			else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un erro en la consulta del asegurado getMgtInfincasegvig" ,e );
			throw e;
		}
		
	}
}
