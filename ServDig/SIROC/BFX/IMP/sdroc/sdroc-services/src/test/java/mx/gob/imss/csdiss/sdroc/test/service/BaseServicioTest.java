/**
 *  Copyright (c)  IMSS - Instituto Mexicano del Seguro Social. Todos los derechos reservados
 */

package mx.gob.imss.csdiss.sdroc.test.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.CalendarioReporteDTO;
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
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionIncidenciaService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionObraService;

/**
 * Esta clase establece por medio de anotaciones la configuracion para ejecutar los tests.
 * 
 * - Primero se indica que JUnit sera el encargado de ejecutar las mismas y evaluar los resultados.
 * - Despues el archivo que contiene la configuracion de spring para iniciar el contendor y sus beans.
 * - Se activa el profile con el que se debe iniciar el contenedor.
 * - Se indica el nombre del bean que se encargara de manejar la transaccion. Ademas para el caso de los tests, por definicion
 *   siempre debemos regresar al estado de los datos en que iniciamos, asi que al terminar cada test se hara rollback.
 * - Por ultimo indicamos con @Transactional que todas los test, a menos que indiquemos lo contrario, iniciaran una transaccion.
 * 
 * @author Brian Hernandez Garcia
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/mx/gob/imss/csdiss/sdroc/service/config/application-context-test.xml")
@ActiveProfiles("testing")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public abstract class BaseServicioTest {
	
	@Autowired
	private InformacionObraService informacionObraService;
	
	@Autowired
	private InformacionIncidenciaService informacionIncidenciaService;
	
	
	public void eliminarBimestresNP(Long cveRegistroObra){
		informacionIncidenciaService.eliminaReportesBimestralesNoPresentados(cveRegistroObra);
	}
	
	public short insertaRegistroObra(Date fecIniObra, Date fecFinObra){
		InformacionObraDTO rotInformacionObra1 = new InformacionObraDTO();
		
		rotInformacionObra1.setFecIniContrato(new Date());
		rotInformacionObra1.setFecFinContrato(new Date());
		
		rotInformacionObra1.setFecIniObra(fecIniObra);
		rotInformacionObra1.setFecFinObra(fecFinObra);
		
		rotInformacionObra1.setImpContratado(new Double("100"));
		rotInformacionObra1.setImpEjercido(new Double("200"));
		rotInformacionObra1.setImpObra(new Double("250"));
		rotInformacionObra1.setNumLicitacion("345345");
		rotInformacionObra1.setNumProcedimiento("029384");
		rotInformacionObra1.setRefAcuseReg("234234");
		rotInformacionObra1.setRefObservacion("observacion");
		rotInformacionObra1.setRefSupConstruccion(new Double("324234"));
		rotInformacionObra1.setCveIdTramite(new Long("23"));
		rotInformacionObra1.setNumSeqNotaria("32423");
		rotInformacionObra1.setNumActualiza(0);
		
		EstatusObraDTO estatusObraDTO = new EstatusObraDTO();
		estatusObraDTO.setCveEstatusObra(new Long(1));
		rotInformacionObra1.setEstatusObraDTO(estatusObraDTO);
		
		DelegacionDTO delegacion = new DelegacionDTO();
		delegacion.setCveDelegacion(1L);
		delegacion.setNomDelegacion("");
		
		SubDelegacionDTO subDelegacionDTO = new SubDelegacionDTO();
		subDelegacionDTO.setCveSubdelegacion(1L);
		subDelegacionDTO.setDelegacionDTO(delegacion);
		
		rotInformacionObra1.setSubDelegacionDTO(subDelegacionDTO);
		
		ClasificacionObraDTO claficiacion = new ClasificacionObraDTO();
		claficiacion.setCveClasificacionObra(2L);
		claficiacion.setDesClasificacionObra("Privada");
		
		TipoObraDTO tipoObraDTO = new TipoObraDTO();
		tipoObraDTO.setClasificacionObraDTO(claficiacion);
		tipoObraDTO.setCveTipoObra(1L);
		tipoObraDTO.setDesTipoObra("descripcion");
		rotInformacionObra1.setTipoObraDTO(tipoObraDTO);
		
		ObjetoContratoDTO objetoContratoDTO = new ObjetoContratoDTO();
		objetoContratoDTO.setCveObjetoContrato(1L);
		
		rotInformacionObra1.setObjetoContratoDTO(objetoContratoDTO);
		
		TipoPersonaDTO tipoPersona = new TipoPersonaDTO();
		tipoPersona.setCveTipoPersona(1L);
		
		TipoPatronDTO tipoPatron = new TipoPatronDTO();
		tipoPatron.setCveTipoPatron(1L);
		
		InformacionPatronDTO informacionPatronDTO = new InformacionPatronDTO();
		informacionPatronDTO.setNomPatron("nompatron");
		informacionPatronDTO.setRefApellidoPaterno("ramirez");
		informacionPatronDTO.setRefApellidoMaterno("hernandez");
		informacionPatronDTO.setRefRazonSocial("razonsocialalgo");
		informacionPatronDTO.setTipoPatronDTO(tipoPatron);
		informacionPatronDTO.setTipoPersonaDTO(tipoPersona);
		informacionPatronDTO.setCveRegPatronal("Y6467489107");
		informacionPatronDTO.setCveRfc("OISD661006P5A");
		
		rotInformacionObra1.setInformacionPatronDTO(informacionPatronDTO);
		
		UbicacionObraDTO ubicacion = new UbicacionObraDTO();
		ubicacion.setCalle("calle algo");
		ubicacion.setNumExterior("234");
		ubicacion.setCodigoPostal("57140");
		ubicacion.setRefColonia("colonia");
		
		rotInformacionObra1.setUbicacionObraDTO(ubicacion);
		
		InformacionObraDTO obra = informacionObraService.insertarInformacionObra(rotInformacionObra1);
		return obra.getCveInformacionObra().shortValue();
	}
	
	public void insertaReporteBimestral(Long mesPresentar, Integer anioPresentar, Long numObra){
		
		InformacionIncidenciaDTO informacionIncidenciaDTO = new InformacionIncidenciaDTO();
		informacionIncidenciaDTO.setCveInformacionObra(numObra);
		informacionIncidenciaDTO.setFecFinObra(new Date());
		informacionIncidenciaDTO.setImpEjercido(new Double("34.5"));
		informacionIncidenciaDTO.setImpObra(new Double ("55.7"));
		informacionIncidenciaDTO.setRefMotivoAct("reporte bimestral");
		informacionIncidenciaDTO.setRefSupConstruccion(new Double("253"));
		
		TipoRegistroDTO tipoRegistroDTO = new TipoRegistroDTO();
		tipoRegistroDTO.setCveTipoRegistro(1L);
		
		informacionIncidenciaDTO.setTipoRegistroDTO(tipoRegistroDTO);
		
		
		
		//ACTUALIZACION(4), {4,4,4}
		//CANCELACION(1),{7,1,7}
		//REANUDACION(5), {20,5,20}
		//REPORTE_BIMESTRAL(6), {14,6,13}
		//SUSPENCION(2), {13,2,17}
		//TERMINACION(3); {22,3,22}
		MotivoTipoIncidenciaDTO motivoTipoIncidenciaDTO = new MotivoTipoIncidenciaDTO();
		
		MotivoDTO motivoDTO = new MotivoDTO();		
		motivoDTO.setCveMotivo(13L);
		motivoDTO.setDesMotivo("motivo");

		TipoIncidenciaDTO tipoIncidenciaDTO = new TipoIncidenciaDTO();
		tipoIncidenciaDTO.setCveTipoIncidencia(new Long("6"));
		tipoIncidenciaDTO.setDesTipoIncidencia("tipoincidencia");
		
		motivoTipoIncidenciaDTO.setCveMotivoTipoIncidencia(14L);
		motivoTipoIncidenciaDTO.setMotivoDTO(motivoDTO);
		motivoTipoIncidenciaDTO.setTipoIncidenciaDTO(tipoIncidenciaDTO);
		
		CalendarioReporteDTO calendarioReporteDTO = new CalendarioReporteDTO();
		calendarioReporteDTO.setCveBimCalendario(mesPresentar);
		informacionIncidenciaDTO.setNumAnio(anioPresentar);
		informacionIncidenciaDTO.setCalendarioReporteDTO(calendarioReporteDTO);
		informacionIncidenciaDTO.setMotivoTipoIncidenciaDTO(motivoTipoIncidenciaDTO);

		informacionIncidenciaService.insertarInformacionIncidencia(informacionIncidenciaDTO);
		//calcularSemaforo();
	}
	
	public void insertarActualizacion(Date nuevaFecFinObra, Long numObra){
				
		InformacionIncidenciaDTO informacionIncidenciaDTO = new InformacionIncidenciaDTO();
		
		informacionIncidenciaDTO.setCveInformacionObra(numObra);
		informacionIncidenciaDTO.setFecFinObra(nuevaFecFinObra);
		informacionIncidenciaDTO.setImpEjercido(new Double("34.5"));
		informacionIncidenciaDTO.setImpObra(new Double ("55.7"));
		informacionIncidenciaDTO.setRefMotivoAct("Actualizacion");
		informacionIncidenciaDTO.setRefSupConstruccion(new Double("253"));
		
		TipoRegistroDTO tipoRegistroDTO = new TipoRegistroDTO();
		tipoRegistroDTO.setCveTipoRegistro(1L);
		
		informacionIncidenciaDTO.setTipoRegistroDTO(tipoRegistroDTO);
		
		
		
		//ACTUALIZACION(4), {4,4,4}
		//CANCELACION(1),{7,1,7}
		//REANUDACION(5), {20,5,20}
		//REPORTE_BIMESTRAL(6), {14,6,13}
		//SUSPENCION(2), {13,2,17}
		//TERMINACION(3); {22,3,22}
		MotivoTipoIncidenciaDTO motivoTipoIncidenciaDTO = new MotivoTipoIncidenciaDTO();
		
		MotivoDTO motivoDTO = new MotivoDTO();		
		motivoDTO.setCveMotivo(4L);
		motivoDTO.setDesMotivo("motivo");

		TipoIncidenciaDTO tipoIncidenciaDTO = new TipoIncidenciaDTO();
		tipoIncidenciaDTO.setCveTipoIncidencia(new Long("4"));
		tipoIncidenciaDTO.setDesTipoIncidencia("tipoincidencia");
		
		motivoTipoIncidenciaDTO.setCveMotivoTipoIncidencia(4L);
		motivoTipoIncidenciaDTO.setMotivoDTO(motivoDTO);
		motivoTipoIncidenciaDTO.setTipoIncidenciaDTO(tipoIncidenciaDTO);
		
		informacionIncidenciaDTO.setMotivoTipoIncidenciaDTO(motivoTipoIncidenciaDTO);

		informacionIncidenciaService.insertarInformacionIncidencia(informacionIncidenciaDTO);
	}
	
	public void insertarSuspension(Date fecSuspension, Long numObra){
		
		InformacionIncidenciaDTO informacionIncidenciaDTO = new InformacionIncidenciaDTO();
		
		informacionIncidenciaDTO.setCveInformacionObra(numObra);
		informacionIncidenciaDTO.setFecSuspencion(fecSuspension);
		informacionIncidenciaDTO.setImpEjercido(new Double("34.5"));
		informacionIncidenciaDTO.setImpObra(new Double ("55.7"));
		informacionIncidenciaDTO.setRefMotivoAct("Actualizacion");
		informacionIncidenciaDTO.setRefSupConstruccion(new Double("253"));
		
		TipoRegistroDTO tipoRegistroDTO = new TipoRegistroDTO();
		tipoRegistroDTO.setCveTipoRegistro(1L);
		
		informacionIncidenciaDTO.setTipoRegistroDTO(tipoRegistroDTO);
		
		
		
		//ACTUALIZACION(4), {4,4,4}
		//CANCELACION(1),{7,1,7}
		//REANUDACION(5), {20,5,20}
		//REPORTE_BIMESTRAL(6), {14,6,13}
		//SUSPENCION(2), {13,2,17}
		//TERMINACION(3); {22,3,22}
		MotivoTipoIncidenciaDTO motivoTipoIncidenciaDTO = new MotivoTipoIncidenciaDTO();
		
		MotivoDTO motivoDTO = new MotivoDTO();		
		motivoDTO.setCveMotivo(17L);
		motivoDTO.setDesMotivo("motivo");

		TipoIncidenciaDTO tipoIncidenciaDTO = new TipoIncidenciaDTO();
		tipoIncidenciaDTO.setCveTipoIncidencia(new Long("2"));
		tipoIncidenciaDTO.setDesTipoIncidencia("tipoincidencia");
		
		motivoTipoIncidenciaDTO.setCveMotivoTipoIncidencia(13L);
		motivoTipoIncidenciaDTO.setMotivoDTO(motivoDTO);
		motivoTipoIncidenciaDTO.setTipoIncidenciaDTO(tipoIncidenciaDTO);
		
		informacionIncidenciaDTO.setMotivoTipoIncidenciaDTO(motivoTipoIncidenciaDTO);

		informacionIncidenciaService.insertarInformacionIncidencia(informacionIncidenciaDTO);
	}
	
	public void insertarReanudacion(Date fecReanudacion, Date nuevaFecTermino, Long numObra){
		
		InformacionIncidenciaDTO informacionIncidenciaDTO = new InformacionIncidenciaDTO();
		
		informacionIncidenciaDTO.setCveInformacionObra(numObra);
		informacionIncidenciaDTO.setFecReanudacion(fecReanudacion);
		informacionIncidenciaDTO.setFecFinObra(nuevaFecTermino);
		informacionIncidenciaDTO.setImpEjercido(new Double("34.5"));
		informacionIncidenciaDTO.setImpObra(new Double ("55.7"));
		informacionIncidenciaDTO.setRefMotivoAct("reanudacion");
		informacionIncidenciaDTO.setRefSupConstruccion(new Double("253"));
		
		TipoRegistroDTO tipoRegistroDTO = new TipoRegistroDTO();
		tipoRegistroDTO.setCveTipoRegistro(1L);
		
		informacionIncidenciaDTO.setTipoRegistroDTO(tipoRegistroDTO);

		//ACTUALIZACION(4), {4,4,4}
		//CANCELACION(1),{7,1,7}
		//REANUDACION(5), {20,5,20}
		//REPORTE_BIMESTRAL(6), {14,6,13}
		//SUSPENCION(2), {13,2,17}
		//TERMINACION(3); {22,3,22}
		MotivoTipoIncidenciaDTO motivoTipoIncidenciaDTO = new MotivoTipoIncidenciaDTO();
		
		MotivoDTO motivoDTO = new MotivoDTO();		
		motivoDTO.setCveMotivo(20L);
		motivoDTO.setDesMotivo("motivo");

		TipoIncidenciaDTO tipoIncidenciaDTO = new TipoIncidenciaDTO();
		tipoIncidenciaDTO.setCveTipoIncidencia(new Long("5"));
		tipoIncidenciaDTO.setDesTipoIncidencia("tipoincidencia");
		
		motivoTipoIncidenciaDTO.setCveMotivoTipoIncidencia(20L);
		motivoTipoIncidenciaDTO.setMotivoDTO(motivoDTO);
		motivoTipoIncidenciaDTO.setTipoIncidenciaDTO(tipoIncidenciaDTO);
		
		informacionIncidenciaDTO.setMotivoTipoIncidenciaDTO(motivoTipoIncidenciaDTO);

		informacionIncidenciaService.insertarInformacionIncidencia(informacionIncidenciaDTO);
	}
	
	public String consultarReporteBimestral(Long cveInformacionObra){
		
		String bimestre = "00-0000";
		InformacionIncidenciaDTO informacionIncidencia =  informacionIncidenciaService.consultarUltimoReporteBimestralPorCveInformacionObra(cveInformacionObra);
		
		if(informacionIncidencia != null){ 
			if(informacionIncidencia.getCalendarioReporteDTO() != null){
	        	 if(informacionIncidencia.getCalendarioReporteDTO().getCveBimCalendario() != null && informacionIncidencia.getNumAnio() != null){
	        		 bimestre =   "0".concat(informacionIncidencia.getCalendarioReporteDTO().getCveBimCalendario().toString()).concat("-").concat(informacionIncidencia.getNumAnio().toString());
	        	 }
	        }
        }
		return bimestre;
	}
	
	
	public void calcularSemaforo(){
		
		List<InformacionObraDTO> listaObrasRegistradas = new ArrayList<InformacionObraDTO>();
		
		 listaObrasRegistradas = informacionObraService.consultarInformacionObrasPorCveRfcYCveRegPatronal("OISD661006P5A", "Y6467489107");

		for(InformacionObraDTO obra : listaObrasRegistradas){
			
			
			if(obra.getNumIncumplimientos() == 0){
			}
			if(obra.getNumIncumplimientos() == 1){
			}
			if(obra.getNumIncumplimientos() >= 2){
			}
			
			String reporteBim = consultarReporteBimestral(obra.getCveInformacionObra());
			
		}

	}

}
