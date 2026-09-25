package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocTipoIncidencia;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoRegistro;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoIncidenciaDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoRegistroDao;

public class TipoIncidenciaDaoTest extends BaseDaoTest {
	
	@Autowired
	private TipoIncidenciaDao tipoIncidenciaDao;
	
	@Test
	public void InstanciaTipoIncidenciaDaoTest(){   
		Assert.assertNotNull(tipoIncidenciaDao);
	}
	
	@Test
	public void consultarTipoIncidenciaTest(){
		

		List<RocTipoIncidencia> listaTiposIncidencia = null;
		
		listaTiposIncidencia = (List<RocTipoIncidencia>) tipoIncidenciaDao.findAll();
		Assert.assertFalse(listaTiposIncidencia.isEmpty());
		

	}
}
