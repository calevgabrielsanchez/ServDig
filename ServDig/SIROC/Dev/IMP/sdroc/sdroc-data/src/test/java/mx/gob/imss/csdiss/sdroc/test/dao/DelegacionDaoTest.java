package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocDelegacion;
import mx.gob.imss.csdiss.sdroc.orm.dao.DelegacionDao;

public class DelegacionDaoTest extends BaseDaoTest {
	
	@Autowired
	private DelegacionDao delegacionDao;
	
	@Test
	public void InstanciaDelegacionDaoTest(){   
		Assert.assertNotNull(delegacionDao);
	}
	
	@Test
	public void consultarDelegacionesTest(){
		

		List<RocDelegacion> listaDelegaciones = null;
		
		listaDelegaciones = (List<RocDelegacion>) delegacionDao.findAll();
		Assert.assertFalse(listaDelegaciones.isEmpty());
		

	}
}
