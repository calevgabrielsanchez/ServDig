/**
 *  Copyright (c)  IMSS - Instituto Mexicano del Seguro Social. Todos los derechos reservados
 */

package mx.gob.imss.csdiss.sdroc.test.service;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.dto.AvisoObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionPatronDTO;
import mx.gob.imss.csdiss.sdroc.dto.MotivoDTO;
import mx.gob.imss.csdiss.sdroc.dto.SubDelegacionDTO;
import mx.gob.imss.csdiss.sdroc.service.interfaces.AvisoObraService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.MotivoService;

public class MotivoServiceTest extends BaseServicioTest {
	
	@Autowired
	private AvisoObraService avisoObraService;

	@Test
	public void MotivoServicioDependenciaTest(){
		Assert.assertNotNull(avisoObraService);
	}
	
	@Test
	public void consultarTodasPersonaServiceTest(){
		//List<MotivoDTO> listaMotivosDTO = motivoService.consultarMotivosPorTipoIncidencia(new Long(1));
		//Assert.assertFalse(listaMotivosDTO.isEmpty());
	}
	
	

	@Test
	public void registroAvisoTest(){
		AvisoObraDTO aviso = new AvisoObraDTO();
		
		SubDelegacionDTO subdelegacion = new SubDelegacionDTO();
		subdelegacion.setCveSubdelegacion(1L);
		
		InformacionPatronDTO patron = new InformacionPatronDTO();
		
		
		aviso.setCveRegistroAvisoObra("2477900460548");
		aviso.setSubDelegacionDTO(subdelegacion);
		
		aviso  = avisoObraService.insertarAvisoObra(aviso);
		Assert.assertNotNull(aviso);
	}
}
