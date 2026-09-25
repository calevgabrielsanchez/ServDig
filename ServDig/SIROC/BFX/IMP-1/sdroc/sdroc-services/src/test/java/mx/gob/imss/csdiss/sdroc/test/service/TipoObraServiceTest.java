/**
 *  Copyright (c)  IMSS - Instituto Mexicano del Seguro Social. Todos los derechos reservados
 */

package mx.gob.imss.csdiss.sdroc.test.service;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.dto.TipoObraDTO;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoObraService;

public class TipoObraServiceTest extends BaseServicioTest {
	
	@Autowired
	private TipoObraService tipoObraService;

	@Test
	public void TipoObraServicioDependenciaTest(){
		Assert.assertNotNull(tipoObraService);
	}
	
	@Test
	public void consultarTodasPersonaServiceTest(){
		List<TipoObraDTO> listaTipoObras = tipoObraService.consultarTiposDeObras();
		Assert.assertFalse(listaTipoObras.isEmpty());
	}
}
