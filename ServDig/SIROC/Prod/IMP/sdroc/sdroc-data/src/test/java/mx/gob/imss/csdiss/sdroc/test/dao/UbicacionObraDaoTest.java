package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocTipoRegistro;
import mx.gob.imss.csdiss.sdroc.entity.RotUbicacionObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoRegistroDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.UbicacionObraDao;

public class UbicacionObraDaoTest extends BaseDaoTest {
	
	@Autowired
	private UbicacionObraDao ubicacionObraDao;
	
	@Test
	public void InstanciaUbicacionObraDaoTest(){   
		Assert.assertNotNull(ubicacionObraDao);
	}
	
	@Test
	public void consultarUbicacionesObraTest(){
		

		List<RotUbicacionObra> listaUbicacionObra = null;
		
		listaUbicacionObra = (List<RotUbicacionObra>) ubicacionObraDao.findAll();
		Assert.assertFalse(listaUbicacionObra.isEmpty());
		

	}
}
