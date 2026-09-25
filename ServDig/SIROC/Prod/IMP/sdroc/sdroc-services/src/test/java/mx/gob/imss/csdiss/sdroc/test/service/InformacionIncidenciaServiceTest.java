/**
 *  Copyright (c)  IMSS - Instituto Mexicano del Seguro Social. Todos los derechos reservados
 */

package mx.gob.imss.csdiss.sdroc.test.service;


import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.dto.ClasificacionObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.DelegacionDTO;
import mx.gob.imss.csdiss.sdroc.dto.EstatusObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionPatronDTO;
import mx.gob.imss.csdiss.sdroc.dto.MotivoDTO;
import mx.gob.imss.csdiss.sdroc.dto.MotivoTipoIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.ObjetoContratoDTO;
import mx.gob.imss.csdiss.sdroc.dto.SubDelegacionDTO;
import mx.gob.imss.csdiss.sdroc.dto.TipoIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.TipoObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.TipoPatronDTO;
import mx.gob.imss.csdiss.sdroc.dto.TipoPersonaDTO;
import mx.gob.imss.csdiss.sdroc.dto.TipoRegistroDTO;
import mx.gob.imss.csdiss.sdroc.dto.UbicacionObraDTO;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoPersonaDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionIncidenciaService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionObraService;

public class InformacionIncidenciaServiceTest extends BaseServicioTest {
	
	@Autowired
	private InformacionIncidenciaService informacionIncidenciaService;
	@Autowired
	private InformacionObraService  informacionObraService;

	@Test
	public void InformacionIncidenciaServiceDependenciaTest(){
		Assert.assertNotNull(informacionIncidenciaService);
	}

	
	@Test
	public void consultarInformacionIncidenciaTiposCveInformacionObraTest(){
		
		List<InformacionIncidenciaDTO> listaIncidencias = null;
		
		listaIncidencias = informacionIncidenciaService.consultarUltimasIncidenciaPorTipoIncidenciaPorCveInformacionObra(new Long(16843));
		
		Assert.assertNotNull(listaIncidencias);
	}	
	
	@Test
	public void insertarInformacionObraServiceTest(){
		
		InformacionObraDTO obra = new InformacionObraDTO();
		
		DelegacionDTO delegacion = new DelegacionDTO();
		delegacion.setCveDelegacion(1L);
		delegacion.setNomDelegacion("");
		
		SubDelegacionDTO subdelegacion = new SubDelegacionDTO();
		subdelegacion.setCveSubdelegacion(1L);
		//subdelegacion.setCveCodigo((short)1);
		subdelegacion.setDelegacionDTO(delegacion);
		
		ClasificacionObraDTO claficiacion = new ClasificacionObraDTO();
		claficiacion.setCveClasificacionObra(1L);
		
		TipoObraDTO tipoObra = new TipoObraDTO();
		tipoObra.setClasificacionObraDTO(claficiacion);
		tipoObra.setCveTipoObra(20L);
		
		TipoPersonaDTO tipoPersona = new TipoPersonaDTO();
		tipoPersona.setCveTipoPersona(1L);
		
		TipoPatronDTO tipoPatron = new TipoPatronDTO();
		tipoPatron.setCveTipoPatron(1L);
		
		InformacionPatronDTO informacionPatron = new InformacionPatronDTO();
		informacionPatron.setNomPatron("nompatron");
		informacionPatron.setRefApellidoPaterno("ramirez");
		informacionPatron.setRefApellidoMaterno("hernandez");
		informacionPatron.setRefRazonSocial("razonsocialalgo");
		informacionPatron.setTipoPatronDTO(tipoPatron);
		informacionPatron.setTipoPersonaDTO(tipoPersona);
		
		EstatusObraDTO estatus = new EstatusObraDTO();
		estatus.setCveEstatusObra(new Long(1));
		
		UbicacionObraDTO ubicacion = new UbicacionObraDTO();
		ubicacion.setCalle("calle algo");
		ubicacion.setNumExterior("234");
		ubicacion.setCodigoPostal("57140");
		
		ObjetoContratoDTO objetoContrato = new ObjetoContratoDTO();
		objetoContrato.setCveObjetoContrato(1L);
		
		obra.setSubDelegacionDTO(subdelegacion);
		obra.setCveRegistroObra(new String("123456789012345"));
		obra.setTipoObraDTO(tipoObra);
		obra.setInformacionPatronDTO(informacionPatron);
		obra.setEstatusObraDTO(estatus);
		obra.setUbicacionObraDTO(ubicacion);
		obra.setObjetoContratoDTO(objetoContrato);
		
		informacionObraService.insertarInformacionObra(obra);
	}
	
//	@Test
//	public void insertarInformacionIncidenciaServiceTest(){
//
//		InformacionIncidenciaDTO informacionIncidenciaDTO = new InformacionIncidenciaDTO();
//		
//		informacionIncidenciaDTO.setCveInformacionObra((short)25);
//		informacionIncidenciaDTO.setFecFinObra(new Date());
//		informacionIncidenciaDTO.setImpEjercido(new Double("34.5"));
//		informacionIncidenciaDTO.setImpObra(new Double ("55.7"));
//		informacionIncidenciaDTO.setRefMotivoAct("Actualizacion");
//		informacionIncidenciaDTO.setRefSupConstruccion((short)89);
//		
//		TipoRegistroDTO tipoRegistroDTO = new TipoRegistroDTO();
//		tipoRegistroDTO.setCveTipoRegistro((short)1);
//		
//		informacionIncidenciaDTO.setTipoRegistroDTO(tipoRegistroDTO);
//		
//		MotivoTipoIncidenciaDTO motivoTipoIncidenciaDTO = new MotivoTipoIncidenciaDTO();
//		
//		MotivoDTO motivoDTO = new MotivoDTO();		
//		motivoDTO.setCveMotivo((short)1);
//		
//		TipoIncidenciaDTO tipoIncidenciaDTO = new TipoIncidenciaDTO();
//		tipoIncidenciaDTO.setCveTipoIncidencia((short)1);
//		
//		motivoTipoIncidenciaDTO.setMotivoDTO(motivoDTO);
//		motivoTipoIncidenciaDTO.setTipoIncidenciaDTO(tipoIncidenciaDTO);
//		
//		informacionIncidenciaDTO.setMotivoTipoIncidenciaDTO(motivoTipoIncidenciaDTO);
//		
//
//		informacionIncidenciaService.insertarInformacionIncidencia(informacionIncidenciaDTO);
//		Assert.assertNotNull(informacionIncidenciaDTO.getCveInformacionIncidencia());
//	}
//	
	
	@Test
	public void consultarIncidenciasPorCveInformacionObraTest(){
		
		List<InformacionIncidenciaDTO> listaIncidencias = null;
		
		listaIncidencias = informacionIncidenciaService.consultarIncidenciasPorCveInformacionObra("2143");
		
		Assert.assertNotNull(listaIncidencias);
	}	
	
	
	public void cargarBimestresExtemporaneosTest(){
		
		Date fecFinObra = new Date();
		Date fecInicioObra = new Date();
		Date fecRegistroObra = new Date();
		Long cveInformacionObra = null;
		
		informacionIncidenciaService.cargarBimestresExtemporaneos(fecInicioObra, fecFinObra, cveInformacionObra);
		
		
	}
	
}
