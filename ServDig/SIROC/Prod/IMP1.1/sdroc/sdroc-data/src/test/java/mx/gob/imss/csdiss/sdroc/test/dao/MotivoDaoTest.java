package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocMotivo;
import mx.gob.imss.csdiss.sdroc.orm.dao.MotivoDao;

public class MotivoDaoTest extends BaseDaoTest {
	
	@Autowired
	private MotivoDao motivoDao;
	
	@Test
	public void InstanciaMotivoDaoTest(){   
		Assert.assertNotNull(motivoDao);
	}
	
	@Test
	public void consultarMotivosTest(){
		List<RocMotivo> listaMotivos = null;
		
		listaMotivos = (List<RocMotivo>) motivoDao.findAll();
		Assert.assertFalse(listaMotivos.isEmpty());
	}
	
	@Test
	public void consultarMotivosPorTipoIncidenciaTest(){

		List<RocMotivo> listaMotivos = null;
		listaMotivos = (List<RocMotivo>) motivoDao.findByTipoIncidencia(new Long(1));
		Assert.assertFalse(listaMotivos.isEmpty());

	}
}
