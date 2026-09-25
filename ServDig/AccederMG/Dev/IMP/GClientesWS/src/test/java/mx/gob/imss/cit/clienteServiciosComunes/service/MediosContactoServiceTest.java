package mx.gob.imss.cit.clienteServiciosComunes.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.cit.clienteServiciosComunes.model.MedioContacto;
import mx.gob.imss.cit.clienteServiciosComunes.services.MediosContactoService;
import mx.gob.imss.cit.test.BaseTest;

public class MediosContactoServiceTest extends BaseTest {

	@Autowired
	private MediosContactoService mediosContactoService;

	@Test
	public void recuperaMediosContacto() {
		List<MedioContacto> lstMediosContacto = mediosContactoService.recuperaMediosContacto("CAMA4509114Q2");
		Assert.assertNotNull(lstMediosContacto);
		System.out.println(lstMediosContacto.get(0).getDesFormaContacto());
	}

}
