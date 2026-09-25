package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocSubdelegacion;
import mx.gob.imss.csdiss.sdroc.orm.dao.SubdelegacionDao;

public class SubdelegacionDaoTest extends BaseDaoTest {
	
	@Autowired
	private SubdelegacionDao subdelegacionDao;
	
	@Test
	public void InstanciaSubdelegacionDaoTest(){   
		Assert.assertNotNull(subdelegacionDao);
	}
	
	@Test
	public void consultarSubdelegacionesTest(){
		

		List<RocSubdelegacion> listaSubdelegaciones = null;
		
		listaSubdelegaciones = (List<RocSubdelegacion>) subdelegacionDao.findAll();
		Assert.assertFalse(listaSubdelegaciones.isEmpty());
		

	}
	
	@Test
	public void consultarSubdelegacionesImssByCpTest(){
		

		List<Object[]> listaSubdelegaciones = null;
		
		listaSubdelegaciones = (List<Object[]>) subdelegacionDao.findSubdelegacionImssByCp("11800");
		Assert.assertFalse(listaSubdelegaciones.isEmpty());
		

	}
	
}
