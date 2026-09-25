package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocEstatusObra;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoPatron;
import mx.gob.imss.csdiss.sdroc.orm.dao.EstatusObraDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoPatronDao;

public class EstatusObraDaoTest extends BaseDaoTest {
	
	@Autowired
	private EstatusObraDao estatusObraDao;
	
	@Test
	public void InstanciaEstatusPatronDaoTest(){   
		Assert.assertNotNull(estatusObraDao);
	}
	
	@Test
	public void consultarEstatusObrasTest(){
		

		List<RocEstatusObra> listaEstatusObras = null;
		
		listaEstatusObras = (List<RocEstatusObra>) estatusObraDao.findAll();
		Assert.assertFalse(listaEstatusObras.isEmpty());
		

	}
}
