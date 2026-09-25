package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocClasificacionObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.ClasificacionObraDao;

public class ClasificacionObraDaoTest extends BaseDaoTest {
	
	@Autowired
	private ClasificacionObraDao clasificacionObraDao;
	
	@Test
	public void InstanciaClasificacionObraDaoTest(){   
		Assert.assertNotNull(clasificacionObraDao);
	}
	
	@Test
	public void consultarClasificacionesObraTest(){
		

		List<RocClasificacionObra> listaClasificacionObra = null;
		
		listaClasificacionObra = (List<RocClasificacionObra>) clasificacionObraDao.findAll();
		Assert.assertFalse(listaClasificacionObra.isEmpty());
		

	}
	

}
