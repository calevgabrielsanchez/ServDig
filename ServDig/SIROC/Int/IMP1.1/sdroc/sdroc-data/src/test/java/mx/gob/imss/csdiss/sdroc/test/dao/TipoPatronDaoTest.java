package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocTipoPatron;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoPatronDao;

public class TipoPatronDaoTest extends BaseDaoTest {
	
	@Autowired
	private TipoPatronDao tipoPatronDao;
	
	@Test
	public void InstanciaTipoPatronDaoTest(){   
		Assert.assertNotNull(tipoPatronDao);
	}
	
	@Test
	public void consultarTipoPatronTest(){
		

		List<RocTipoPatron> listaTipoPatron = null;
		
		listaTipoPatron = (List<RocTipoPatron>) tipoPatronDao.findAll();
		Assert.assertFalse(listaTipoPatron.isEmpty());
		

	}
}
