package mx.gob.imss.csdiss.sdroc.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.csdiss.sdroc.dto.AvisoObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.service.RegistroIncidenciaService;
import mx.gob.imss.csdiss.sdroc.service.RegistroObraService;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Controller
@RequestMapping("/api-reportes")
public class ReportesApiControllers {
	
	@Autowired
	RegistroObraService registroObraService;
	@Autowired
	ServletContext context;
	@Autowired
	SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	RegistroIncidenciaService registroIncidenciaService;
	@Autowired
	FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;

	@RequestMapping(value="/obra/{cveObra}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getInfoObra(@PathVariable String cveObra) {
		Map<String, Object> result = new HashMap<String, Object>();
		InformacionObraDTO obraRegistrada = null;
		AvisoObraDTO avisoObra = null;
		
		if(cveObra.startsWith("C")){
			obraRegistrada = registroObraService.obtenerInformacionObraPorNumRegObra(cveObra);
			result.put("obra", obraRegistrada);
			result.put("isAviso", false);
		}else{
			avisoObra = registroObraService.consultaAvisoUbicacionObra(cveObra);
			result.put("obra", avisoObra);
			result.put("isAviso", true);
		}
		
		return result;
	}
	
	@RequestMapping(value="/reportes/{obra}", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> consultarReportesBimestrales(HttpServletResponse response, @PathVariable("obra") String obra) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<InformacionIncidenciaDTO> reportes =  new ArrayList<InformacionIncidenciaDTO>();
		List<InformacionIncidenciaDTO> incidencias = registroObraService.consultaIncidenciasPorCveInformacionObra(obra);
		
		for(InformacionIncidenciaDTO incidencia: incidencias) {
			
			System.out.println("El id de la incidencia es : "+ incidencia.getCveInformacionIncidencia() +" Y el monto de la obra es " + incidencia.getImpObra() + " y el monto ejercido es " + incidencia.getImpEjercido());
			if(incidencia.getMotivoTipoIncidenciaDTO().getCveMotivoTipoIncidencia().intValue() == 14) {
				reportes.add(incidencia);
			}
		}
		
		if(reportes != null && !reportes.isEmpty()) {
			result.put("reportes", reportes);
		}
		
		return result;
		
	}
	
	@RequestMapping("/reporte/{obra}/{idIncidencia}")
	public ResponseEntity<byte[]> consultarAcuseBimestral(HttpServletResponse response, @PathVariable("obra") String obra, @PathVariable("idIncidencia") Long idIncidencia) {

		Long idTramite = null;
		Date fechaSolicitud = null;
		String pathRegistroObraAcuse = context.getRealPath(File.separator + "static" + File.separator + "report");
		String pathImg = context.getRealPath(File.separator + "static" + File.separator + "images");
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		
		List<InformacionIncidenciaDTO> incidencias = registroObraService.consultaIncidenciasPorCveInformacionObra(obra);
		
		for(InformacionIncidenciaDTO incidencia: incidencias) {
			System.out.println("El id de la incidencia es : "+ incidencia.getCveInformacionIncidencia() +" Y el monto de la obra es " + incidencia.getImpObra() + " y el monto ejercido es " + incidencia.getImpEjercido());
			if(incidencia.getCveInformacionIncidencia().longValue() == idIncidencia.longValue()) {
				System.out.println("ENCONTRADO : El id de la incidencia es : "+ incidencia.getCveInformacionIncidencia() +" Y el monto de la obra es " + incidencia.getImpObra() + " y el monto ejercido es " + incidencia.getImpEjercido());
				informacionIncidenciaDTO = incidencia;
				break;
			}
		}
		idTramite = informacionIncidenciaDTO.getCveIdTramite();
		
		try {
			System.out.println("ENCONTRADO : El id de la incidencia es : "+ informacionIncidenciaDTO.getCveInformacionIncidencia() +" Y el monto de la obra es " + informacionIncidenciaDTO.getImpObra() + " y el monto ejercido es " + informacionIncidenciaDTO.getImpEjercido());
			
			Solicitud solicitud = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
			fechaSolicitud = solicitud.getFechaConclusion();
			FirmaElectronica firma = firmaDigitalBusinessRemote.getFirmaElectronica(solicitud);
			
			informacionIncidenciaDTO.setFolio(solicitud.getNoFolioSolicitud());
			informacionIncidenciaDTO.setNumSeqNotaria(firma.getSecuenciaNotaria());
			informacionIncidenciaDTO.setRefCadenaOriginal(firma.getCadenaOriginal());
			informacionIncidenciaDTO.setRefSelloDigital(firma.getRecibo());
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		
		byte[] acuse = registroIncidenciaService.getAcuseIncidencia(informacionIncidenciaDTO, pathRegistroObraAcuse, pathImg, fechaSolicitud, false);
		
		return this.descargarArchivo(acuse);
	}
	
	
	@RequestMapping(value = "/acuseRegistro/{num_obra}")
	public ResponseEntity<byte[]> consultarAcuseRegistroObra(HttpServletResponse response, @PathVariable("num_obra") String num_obra) {
		InformacionObraDTO obraRegistrada = null;
		AvisoObraDTO avisoObra = null;
		Long idTramite = null;
		Date fechaSolicitud = null;
		String pathRegistroObraAcuse = context.getRealPath(File.separator + "static" + File.separator + "report");
		String pathImg = context.getRealPath(File.separator + "static" + File.separator + "images");
		
		if(num_obra.startsWith("C")){
			obraRegistrada = registroObraService.obtenerInformacionObraPorNumRegObra(num_obra);
			idTramite = obraRegistrada.getCveIdTramite();
		}else{
			avisoObra = registroObraService.consultaAvisoUbicacionObra(num_obra);
			idTramite = avisoObra.getCveIdTramite();
		}
		
		try {
			Log.error("El id del tramite relacionado a la obra es " + idTramite);
			
			Solicitud solicitud = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
			fechaSolicitud = solicitud.getFechaConclusion();
			FirmaElectronica firma = firmaDigitalBusinessRemote.getFirmaElectronica(solicitud);
			
			if(obraRegistrada != null) {
				obraRegistrada.setFolio(solicitud.getNoFolioSolicitud());
				obraRegistrada.setNumSeqNotaria(firma.getSecuenciaNotaria());
				obraRegistrada.setRefCadenaOriginal(firma.getCadenaOriginal());
				obraRegistrada.setRefNumSerie(firma.getSerialCertificado());
				obraRegistrada.setRefSelloDigital(firma.getRecibo());
			} else {
				avisoObra.setFolio(solicitud.getNoFolioSolicitud());
				avisoObra.setNumSeqNotaria(firma.getSecuenciaNotaria());
				avisoObra.setRefCadenaOriginal(firma.getCadenaOriginal());
				avisoObra.setRefNumSerie(firma.getSerialCertificado());
				avisoObra.setRefSelloDigital(firma.getRecibo());
			}
			
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		
		byte[] acuse = null;
		if(obraRegistrada != null) {
			acuse = registroObraService.generaAcuseRegistroObra(obraRegistrada, pathRegistroObraAcuse, pathImg, fechaSolicitud, false);
		} else {
			acuse = registroObraService.generaAcuseAvisoObra(avisoObra, pathRegistroObraAcuse, pathImg, fechaSolicitud, false);
		}
		
		
		return this.descargarArchivo(acuse);
	}
	
	@RequestMapping(value = "/acuseAvisoUbicacion/{num_obra}")
	public ResponseEntity<byte[]> consultarAcuseAvisoUbicacion(HttpServletResponse response, @PathVariable("num_obra") String num_obra) {
		AvisoObraDTO avisoObra = null;
		Long idTramite = null;
		Date fechaSolicitud = null;
		String pathRegistroObraAcuse = context.getRealPath(File.separator + "static" + File.separator + "report");
		System.out.println("Path del acuse de registro de obra");
		String pathImg = context.getRealPath(File.separator + "static" + File.separator + "images");
		
			avisoObra = registroObraService.consultaAvisoUbicacionObra(num_obra);
			idTramite = avisoObra.getCveIdTramite();
		
		try {
			Log.info("El id del tramite relacionado a la obra es " + idTramite);
			
			Solicitud solicitud = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
			fechaSolicitud = solicitud.getFechaConclusion();
			FirmaElectronica firma = firmaDigitalBusinessRemote.getFirmaElectronica(solicitud);
			
				avisoObra.setFolio(solicitud.getNoFolioSolicitud());
				avisoObra.setNumSeqNotaria(firma.getSecuenciaNotaria());
				avisoObra.setRefCadenaOriginal(firma.getCadenaOriginal());
				avisoObra.setRefNumSerie(firma.getSerialCertificado());
				avisoObra.setRefSelloDigital(firma.getRecibo());
			
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		
		byte[] acuse = null;
		 String cveAvisoObra = avisoObra.getCveRegistroAvisoObra();
			acuse = registroObraService.generaAcuseAvisoObra(avisoObra, pathRegistroObraAcuse, pathImg, fechaSolicitud, false);
		return this.descargarArchivoAcuse(acuse,cveAvisoObra);
	}
	
	@RequestMapping(value = "/acuseTerminacionObra/{obra}")
	public ResponseEntity<byte[]> consultarAcuseTerminacion(@PathVariable("obra") Long obra) {

		Long idTramite = null;
		Date fechaSolicitud = null;
		String pathRegistroObraAcuse = context.getRealPath(File.separator + "static" + File.separator + "report");
		String pathImg = context.getRealPath(File.separator + "static" + File.separator + "images");
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		
		informacionIncidenciaDTO = registroIncidenciaService.consultarUltimaIncidenciaRegistrada(obra, 3);
		idTramite = informacionIncidenciaDTO.getCveIdTramite();
		
		try {
			Log.error("El id del tramite relacionado a la obra es " + idTramite);
			
			Solicitud solicitud = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
			fechaSolicitud = solicitud.getFechaConclusion();
			FirmaElectronica firma = firmaDigitalBusinessRemote.getFirmaElectronica(solicitud);
			
			informacionIncidenciaDTO.setFolio(solicitud.getNoFolioSolicitud());
			informacionIncidenciaDTO.setNumSeqNotaria(firma.getSecuenciaNotaria());
			informacionIncidenciaDTO.setRefCadenaOriginal(firma.getCadenaOriginal());
			informacionIncidenciaDTO.setRefSelloDigital(firma.getRecibo());
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		byte[] acuse = registroIncidenciaService.getAcuseIncidencia(informacionIncidenciaDTO, pathRegistroObraAcuse, pathImg, fechaSolicitud, false);

		return this.descargarArchivo(acuse);	
	}
	
	private ResponseEntity<byte[]> descargarArchivo(byte[] acuse) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    // Here you have to set the actual filename of your pdf
	    String filename = "output.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(acuse, headers, HttpStatus.OK);
	    return response;
	}
	
	private ResponseEntity<byte[]> descargarArchivoAcuse(byte[] acuse,String cveAvisoObra) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    String filename = "AvisoUbicacionObra_"+ cveAvisoObra + ".pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(acuse, headers, HttpStatus.OK);
	    return response;
	}
	
}
