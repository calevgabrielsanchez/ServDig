package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocTipoRegistro;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoRegistroDao;

public class TipoRegistroDaoTest extends BaseDaoTest {
	
	@Autowired
	private TipoRegistroDao tipoRegistroDao;
	
	@Test
	public void InstanciaTipoRegistroDaoTest(){   
		Assert.assertNotNull(tipoRegistroDao);
	}
	
	@Test
	public void consultarTipoRegistroTest(){
		

		List<RocTipoRegistro> listaTiposRegistro = null;
		
		listaTiposRegistro = (List<RocTipoRegistro>) tipoRegistroDao.findAll();
		Assert.assertFalse(listaTiposRegistro.isEmpty());
		

	}
}
