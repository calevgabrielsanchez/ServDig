package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocCalendarioReporte;
import mx.gob.imss.csdiss.sdroc.orm.dao.CalendarioReporteDao;

public class CalendarioReporteDaoTest extends BaseDaoTest {
	
	@Autowired
	private CalendarioReporteDao calendarioReporteDao;
	
	@Test
	public void InstanciaCalendarioReporteDaoTest(){   
		Assert.assertNotNull(calendarioReporteDao);
	}
	
	@Test
	public void consultarCalendarioReporteTest(){
		

		List<RocCalendarioReporte> listaCalendarioReporte = null;
		
		listaCalendarioReporte = (List<RocCalendarioReporte>) calendarioReporteDao.findAll();
		Assert.assertFalse(listaCalendarioReporte.isEmpty());
		

	}
}
