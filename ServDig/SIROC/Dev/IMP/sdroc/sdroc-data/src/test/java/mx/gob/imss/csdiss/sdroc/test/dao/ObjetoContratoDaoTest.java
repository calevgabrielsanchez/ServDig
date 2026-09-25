package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocObjetoContrato;
import mx.gob.imss.csdiss.sdroc.orm.dao.ObjetoContratoDao;

public class ObjetoContratoDaoTest extends BaseDaoTest {
	
	@Autowired
	private ObjetoContratoDao objetoContratoDao;
	
	@Test
	public void InstanciaObjetoContratoDaoTest(){   
		Assert.assertNotNull(objetoContratoDao);
	}
	
	@Test
	public void consultarObjetosContratoTest(){
		

		List<RocObjetoContrato> listaObjetosContratos = null;
		
		listaObjetosContratos = (List<RocObjetoContrato>) objetoContratoDao.findAll();
		Assert.assertFalse(listaObjetosContratos.isEmpty());
		

	}
}
