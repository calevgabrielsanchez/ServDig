package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.serviciosDigitales;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.persistence.util.PersistenceUnitSISCOBServiceEntity;

@Local(value = IServiciosDigiatlesEntityLocal.class)
@Stateless
public class ServiciosDigiatlesEntity extends PersistenceUnitSISCOBServiceEntity implements IServiciosDigiatlesEntityLocal{

	private static final Logger log = LoggerFactory.getLogger(ServiciosDigiatlesEntity.class);

	@Override
	public void getMockTRest(String test) throws Exception {
		// TODO Auto-generated method stub
		
	}


}
