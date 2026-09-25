package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocTipoPatron;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoRegistro;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionPatron;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionPatronDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoRegistroDao;

public class InformacionPatronDaoTest extends BaseDaoTest {
	
	@Autowired
	private InformacionPatronDao informacionPatronDao;
	
	@Test
	public void InstanciaInformacionPatronDaoTest(){   
		Assert.assertNotNull(informacionPatronDao);
	}
	
	@Test
	public void consultarInformacionPatronesTest(){
		

		List<RotInformacionPatron> listaInformacionPatrones = null;
		
		listaInformacionPatrones = (List<RotInformacionPatron>) informacionPatronDao.findAll();
		Assert.assertFalse(listaInformacionPatrones.isEmpty());
		

	}
}
