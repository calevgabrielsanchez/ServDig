package mx.gob.imss.cit.clienteServiciosComunes.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.cit.clienteServiciosComunes.model.RespuestaConsultaPatron;
import mx.gob.imss.cit.clienteServiciosComunes.services.ConsultaPatronService;
import mx.gob.imss.cit.test.BaseTest;

public class ConsultaPatronServiceTest extends BaseTest {

	@Autowired
	private ConsultaPatronService consultaPatronService;

	@Test
	public void obtenerInformacionPatronPorRFC() {
		RespuestaConsultaPatron respuestaConsultaPatron = consultaPatronService
				.obtenerInformacionPatronPorRFC("CAAR830312UN3");
		Assert.assertNotNull(respuestaConsultaPatron);
	}
}
