package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocTipoObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoObraDao;

public class TipoObraDaoTest extends BaseDaoTest {
	
	@Autowired
	private TipoObraDao tipoObraDao;
	
	@Test
	public void InstanciaTipoObraDaoTest(){   
		Assert.assertNotNull(tipoObraDao);
	}
	
	@Test
	public void consultarTiposObraTest(){
		

		List<RocTipoObra> listaTipoObra = null;
		
		listaTipoObra = (List<RocTipoObra>) tipoObraDao.findAll();
		Assert.assertFalse(listaTipoObra.isEmpty());
		

	}
	
	@Test
	public void consultarTiposObraPorCveClasificacionTest(){
		

		List<RocTipoObra> listaTipoObra = null;
		
		listaTipoObra = (List<RocTipoObra>) tipoObraDao.findByCveClasificacionObra(new Long(1));
		Assert.assertFalse(listaTipoObra.isEmpty());
		

	}
}
