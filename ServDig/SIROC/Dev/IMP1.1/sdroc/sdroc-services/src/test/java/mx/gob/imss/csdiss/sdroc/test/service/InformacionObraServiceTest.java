/**
 *  Copyright (c)  IMSS - Instituto Mexicano del Seguro Social. Todos los derechos reservados
 */

package mx.gob.imss.csdiss.sdroc.test.service;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionObraService;

public class InformacionObraServiceTest extends BaseServicioTest{
	
	@Autowired
	private InformacionObraService informacionObraService;

	@Test
	public void MotivoServicioDependenciaTest(){
		Assert.assertNotNull(informacionObraService);
	}
	
	@Test
	public void consultarTodasInformacionObraServiceTest(){
		List<InformacionObraDTO> listainformacionDTO = informacionObraService.consultarTodasInformacionObras();
		Assert.assertFalse(listainformacionDTO.isEmpty());
	}
	
	@Test
	public void insertarInformacionObraServiceTest(){
		
		InformacionObraDTO informacionObraDTO = new InformacionObraDTO();
		informacionObraDTO.setCveRegistroObra(new String("1611110001"));
		
		
		informacionObraService.insertarInformacionObra(informacionObraDTO);
		Assert.assertNotNull(informacionObraDTO.getCveRegistroObra());
	}
	
	@Test
	public void consultarRerporteInformacionObrasTest(){
		
		List<InformacionObraDTO> listaInformacionObra = new ArrayList<InformacionObraDTO>();

		listaInformacionObra = informacionObraService.consultarReporteGeneralObrasPorCveRfcYAnio("OERC8710217B9","2016");
		

		Assert.assertNotEquals(listaInformacionObra.size(), 0);
	}	
	
}
