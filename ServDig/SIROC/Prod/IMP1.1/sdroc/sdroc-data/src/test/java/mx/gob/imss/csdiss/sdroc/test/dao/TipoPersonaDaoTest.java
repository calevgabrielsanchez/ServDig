package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocTipoPersona;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoPersonaDao;

public class TipoPersonaDaoTest extends BaseDaoTest {
	
	@Autowired
	private TipoPersonaDao tipoPersonaDao;
	
	@Test
	public void InstanciaTipoPersonaDaoTest(){   
		Assert.assertNotNull(tipoPersonaDao);
	}
	
	@Test
	public void consultarTipoPersonaTest(){
		

		List<RocTipoPersona> listaTipoPersona = null;
		
		listaTipoPersona = (List<RocTipoPersona>) tipoPersonaDao.findAll();
		Assert.assertFalse(listaTipoPersona.isEmpty());
		

	}
}
