/**
  *  Copyright (c)  IMSS - Instituto Mexicano del Seguro Social. Todos los derechos reservados
 */

package mx.gob.imss.csdiss.sdroc.test.service;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.dto.MotivoDTO;
import mx.gob.imss.csdiss.sdroc.dto.SubDelegacionDTO;
import mx.gob.imss.csdiss.sdroc.service.interfaces.MotivoService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.SubdelegacionService;

public class SubdelegacionServiceTest extends BaseServicioTest {
	
	@Autowired
	private SubdelegacionService subdelegacionService;

	@Test
	public void SubdelegacionServicioDependenciaTest(){
		Assert.assertNotNull(subdelegacionService);
	}
	
	@Test
	public void consultarTodasSubdelegacionesServiceTest(){
		List<SubDelegacionDTO> listaSubdelegacionDTO = subdelegacionService.consultarTodasSubdelegaciones();
		Assert.assertFalse(listaSubdelegacionDTO.isEmpty());
	}
	
	@Test
	public void consultarSubdelegacionesImssByCpServiceTest(){
		List<SubDelegacionDTO> listaSubdelegacionDTO = subdelegacionService.consultarSubdelegacionesImssByCp("11800");
		Assert.assertFalse(listaSubdelegacionDTO.isEmpty());
	}
}
