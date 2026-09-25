package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocMotivoIncidencia;
import mx.gob.imss.csdiss.sdroc.orm.dao.MotivoTipoIncidenciaDao;

public class MotivoTipoIncidenciaDaoTest extends BaseDaoTest {
	
	@Autowired
	private MotivoTipoIncidenciaDao motivoTipoIncidenciaDao;
	
	@Test
	public void InstanciaMotivoTipoIncidenciaDaoTest(){   
		Assert.assertNotNull(motivoTipoIncidenciaDao);
	}
	
	@Test
	public void consultarMotivosTiposIncidenciasTest(){
		

		List<RocMotivoIncidencia> listaMotivosTiposIncidencias = null;
		
		listaMotivosTiposIncidencias = (List<RocMotivoIncidencia>) motivoTipoIncidenciaDao.findAll();
		Assert.assertFalse(listaMotivosTiposIncidencias.isEmpty());
		

	}
}
