/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.service;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import mx.gob.imss.csdiss.sdroc.dto.CalendarioReporteDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.SolicitudTramiteDTO;
import mx.gob.imss.csdiss.sdroc.util.CadenaOriginaQR;
import mx.gob.imss.csdiss.sdroc.util.CargarParametrosReporte;
import mx.gob.imss.csdiss.sdroc.util.GeneraReporte;
import mx.gob.imss.csdiss.sdroc.util.ReporteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author daniel.hernandez
 *
 */
@Configuration
@PropertySource("classpath:messages.properties")
public class RegistroIncidenciaServiceImpl implements RegistroIncidenciaService {

	//private String PATH_BASE_URI = "http://serviciosdigitales-stage.imss.gob.mx/sdroc-rest/";
	private String PROTOCOL = "http://";
	private String CONTEXT = "/sdroc-rest/";
	
	
	//Ids de los tipos de solicitud y tramite utilizados en SDROC actualizacion
	private final Integer ID_TRAMITE_REGISTRO_INC = 141;
	
	@Autowired
	SolicitudTramiteService solicitudTramiteService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
	
	
	private String recoveryIP() {
		return env.getProperty("endpoint.rest");
	}
			
	@Override
	public HashMap<String, Object> registraIncidencia(InformacionIncidenciaDTO informacionIncidencia, String pathRegCancelaAcuse, String pathImg, FirmaElectronica firma) {
		
		try {
			
			SolicitudTramiteDTO datosSolicitud = solicitudTramiteService.crearSolicitudTramite(ID_TRAMITE_REGISTRO_INC, firma);
			
			//VALIDAR TIPO E REGISTRO  ORDINARIA O EXTEMPORANEA
			//Se asocia el id del tramite
			informacionIncidencia.setCveIdTramite(datosSolicitud.getCveIdTramite());
			//Se asocia la secuencia de notaria
			informacionIncidencia.setNumSeqNotaria(firma.getReciboNotarial());
			//se asocia el folio
			informacionIncidencia.setFolio(datosSolicitud.getFolioSolicitud());
			
			//ResponseEntity<InformacionIncidenciaDTO> response = restTemplate.postForEntity(PATH_BASE_URI.concat("guardarInformacionIncidencia")	, informacionIncidencia, InformacionIncidenciaDTO.class);
			ResponseEntity<InformacionIncidenciaDTO> response = restTemplate.postForEntity(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("guardarInformacionIncidencia"), informacionIncidencia, InformacionIncidenciaDTO.class);
			
			HashMap<String, Object> informacionResponse = new HashMap<String, Object>();
			InformacionIncidenciaDTO InformacionIncidenciaResponse = response.getBody();
			
			if(InformacionIncidenciaResponse != null){
				
				
				
				ResponseEntity<InformacionObraDTO> informacionObra = restTemplate.getForEntity(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarObraPorCveInformacionObra/{cveInformacionObra}"), InformacionObraDTO.class, informacionIncidencia.getCveInformacionObra());
				
				InformacionObraDTO informacionObraDTO = informacionObra.getBody();
				
				GeneraReporte generadorReporte = new GeneraReporte();
				CargarParametrosReporte paramReporte = new CargarParametrosReporte();
				
				Long idTipoServicio =InformacionIncidenciaResponse.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO().getCveTipoIncidencia();
				int idReporteAcuse = 0;
				int idReporteRegistro = 0;
				String nombrePlantilla = "";
				String tipoServicio = "";
				
				if(idTipoServicio == 1){

					idReporteAcuse = ReporteEnum.CANCELACION_ACUSE.getValor();
					idReporteRegistro = ReporteEnum.CANCELACION.getValor();
					tipoServicio = ReporteEnum.CANCELACION.getNombre();
					nombrePlantilla = "cancelacion"; 
					
				} else if(idTipoServicio == 2){
					
					idReporteAcuse = ReporteEnum.SUSPENSION_ACUSE.getValor();
					idReporteRegistro = ReporteEnum.SUSPENSION.getValor();
					tipoServicio = ReporteEnum.SUSPENSION.getNombre();
					
					nombrePlantilla = "suspension"; 
				} else if(idTipoServicio == 3){
					
					idReporteAcuse = ReporteEnum.TERMINACION_ACUSE.getValor();
					idReporteRegistro = ReporteEnum.TERMINACION.getValor();
					tipoServicio = ReporteEnum.TERMINACION.getNombre();
					nombrePlantilla = "terminacion"; 
				} else if(idTipoServicio == 4){
					
					idReporteAcuse = ReporteEnum.ACTUALIZACION_ACUSE.getValor();
					idReporteRegistro = ReporteEnum.ACTUALIZACION.getValor();
					tipoServicio = ReporteEnum.ACTUALIZACION.getNombre();
					nombrePlantilla = "actualizacion"; 
				} else if(idTipoServicio == 5){
					
					idReporteAcuse = ReporteEnum.REANUDACION_ACUSE.getValor();
					idReporteRegistro = ReporteEnum.REANUDACION.getValor();
					tipoServicio = ReporteEnum.REANUDACION.getNombre();
					nombrePlantilla = "reanudacion"; 
					
				} else if(idTipoServicio == 6){
					
					idReporteAcuse = ReporteEnum.REPORTE_BIMESTRAL_ACUSE.getValor();
					idReporteRegistro = ReporteEnum.REPORTE_BIMESTRAL.getValor();
					tipoServicio = ReporteEnum.REPORTE_BIMESTRAL.getNombre();
					nombrePlantilla = "reporteBimestral"; 
				}

				InformacionIncidenciaResponse.setRefSelloDigital(firma.getRecibo());
				InformacionIncidenciaResponse.setRefCadenaOriginal(firma.getCadenaOriginal());				
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
				String fechaFormato = sdf.format(new Date());
				
				CadenaOriginaQR cadenaOriginaQR = new CadenaOriginaQR();
				
				String cadena;
                cadena = cadenaOriginaQR.formatCadenaOriginalQR(firma.getCadenaOriginal(), informacionObraDTO.getCveRegistroObra(), tipoServicio, fechaFormato);
                System.out.println("CADENA FINAL:"); 
                System.out.println(cadena);
                informacionObraDTO.setRefCadenaOriginal(cadena);
                BufferedImage image_qr = cadenaOriginaQR.construirQR(cadena, 200, 200);
                
				HashMap<String, Object> paramAcuse = paramReporte.cargarParametrosReporte(informacionObraDTO, InformacionIncidenciaResponse, idReporteAcuse, pathImg, image_qr, fechaFormato);
				HashMap<String, Object> param = paramReporte.cargarParametrosReporte(informacionObraDTO, InformacionIncidenciaResponse, idReporteRegistro, pathImg, image_qr, fechaFormato);
								
				generadorReporte.generaReportePDF(pathRegCancelaAcuse, nombrePlantilla, ReporteEnum.REGISTRO.getNombre(), param);
				byte[] reporte = solicitudTramiteService.guardarArchivoNotaria(firma.getReciboNotarial(), generadorReporte, pathRegCancelaAcuse, nombrePlantilla, ReporteEnum.ACUSE.getNombre(), paramAcuse);
				informacionResponse.put("cveObraAviso", informacionObraDTO.getCveRegistroObra());
				informacionResponse.put("reporte", reporte);
				informacionResponse.put("tipoIncidencia", tipoServicio);
				
				return informacionResponse;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public byte[] getAcuseIncidencia(InformacionIncidenciaDTO informacionIncidencia, String pathRegCancelaAcuse, String pathImg, Date fecha, Boolean generarCadena) {
        
        
        System.out.println("===== INICIO getAcuseIncidencia =====");
	    System.out.println("informacionIncidencia: " + informacionIncidencia);
	    System.out.println("pathRegCancelaAcuse: " + pathRegCancelaAcuse);
	    System.out.println("pathImg: " + pathImg);
	    System.out.println("fecha: " + fecha);
	    System.out.println("generarCadena: " + generarCadena);
       		
		ResponseEntity<InformacionObraDTO> informacionObra = restTemplate.getForEntity(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarObraPorCveInformacionObra/{cveInformacionObra}"), InformacionObraDTO.class, informacionIncidencia.getCveInformacionObra());
		       
		InformacionObraDTO informacionObraDTO = informacionObra.getBody();
		
		GeneraReporte generadorReporte = new GeneraReporte();
		CargarParametrosReporte paramReporte = new CargarParametrosReporte();
		
		Long idTipoServicio =informacionIncidencia.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO().getCveTipoIncidencia();
		int idReporteAcuse = 0;
		int idReporteRegistro = 0;
		String nombrePlantilla = "";
		String tipoServicio = "";
		
		if(idTipoServicio == 1){

			idReporteAcuse = ReporteEnum.CANCELACION_ACUSE.getValor();
			idReporteRegistro = ReporteEnum.CANCELACION.getValor();
			tipoServicio = ReporteEnum.CANCELACION.getNombre();
			nombrePlantilla = "cancelacion"; 
			
		} else if(idTipoServicio == 2){
			
			idReporteAcuse = ReporteEnum.SUSPENSION_ACUSE.getValor();
			idReporteRegistro = ReporteEnum.SUSPENSION.getValor();
			tipoServicio = ReporteEnum.SUSPENSION.getNombre();
			
			nombrePlantilla = "suspension"; 
		} else if(idTipoServicio == 3){
			
			idReporteAcuse = ReporteEnum.TERMINACION_ACUSE.getValor();
			idReporteRegistro = ReporteEnum.TERMINACION.getValor();
			tipoServicio = ReporteEnum.TERMINACION.getNombre();
			nombrePlantilla = "terminacion"; 
		} else if(idTipoServicio == 4){
			
			idReporteAcuse = ReporteEnum.ACTUALIZACION_ACUSE.getValor();
			idReporteRegistro = ReporteEnum.ACTUALIZACION.getValor();
			tipoServicio = ReporteEnum.ACTUALIZACION.getNombre();
			nombrePlantilla = "actualizacion"; 
		} else if(idTipoServicio == 5){
			
			idReporteAcuse = ReporteEnum.REANUDACION_ACUSE.getValor();
			idReporteRegistro = ReporteEnum.REANUDACION.getValor();
			tipoServicio = ReporteEnum.REANUDACION.getNombre();
			nombrePlantilla = "reanudacion"; 
			
		} else if(idTipoServicio == 6){
			
			idReporteAcuse = ReporteEnum.REPORTE_BIMESTRAL_ACUSE.getValor();
			idReporteRegistro = ReporteEnum.REPORTE_BIMESTRAL.getValor();
			tipoServicio = ReporteEnum.REPORTE_BIMESTRAL.getNombre();
			informacionObraDTO.setImpEjercido(informacionIncidencia.getImpEjercido());
			nombrePlantilla = "reporteBimestral"; 
		}
        
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
		String fechaFormato = sdf.format(fecha);	
		CadenaOriginaQR cadenaOriginaQR = new CadenaOriginaQR();
        
		String cadena;
                cadena = cadenaOriginaQR.formatCadenaOriginalQR(informacionIncidencia.getRefCadenaOriginal(), informacionObraDTO.getCveRegistroObra(), tipoServicio, fechaFormato);
                System.out.println("CADENA FINAL:"); 
                System.out.println(cadena);
                informacionObraDTO.setRefCadenaOriginal(cadena);
                BufferedImage image_qr = cadenaOriginaQR.construirQR(cadena, 200, 200);
		
		HashMap<String, Object> paramAcuse = paramReporte.cargarParametrosReporte(informacionObraDTO, informacionIncidencia, idReporteAcuse, pathImg, image_qr, fechaFormato);
		byte[] reporte = solicitudTramiteService.guardarArchivoNotaria(informacionIncidencia.getNumSeqNotaria(), generadorReporte, pathRegCancelaAcuse, nombrePlantilla, ReporteEnum.ACUSE.getNombre(), paramAcuse);
		
		return reporte;
	}

	@Override
	public InformacionIncidenciaDTO consultarUltimaIncidenciaRegistrada(Long cveInformacionObra, int idTipoIncidencia) {
			InformacionIncidenciaDTO incidencia = new InformacionIncidenciaDTO();
			incidencia = restTemplate.getForObject(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarIncidenciaPorTipoIncidenciaPorCveInformacionObra/{cveInformacionObra}/{idTipoIncidencia}"),InformacionIncidenciaDTO.class, cveInformacionObra, idTipoIncidencia);
		return incidencia;
	}
	
	@Override
	public InformacionIncidenciaDTO consultarUltimoReporteBimetralReportado(Long cveInformacionObra,
			int idTipoIncidencia) {
		InformacionIncidenciaDTO incidencia = new InformacionIncidenciaDTO();
		incidencia = restTemplate.getForObject(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarUltimoReporteBimestralPresentado/{cveInformacionObra}"),InformacionIncidenciaDTO.class, cveInformacionObra, idTipoIncidencia);
		return incidencia;
	}

	@Override
	public Long bimestreCorrespondiente(String mes) {
		Long bimestre = 0L;
		
		CalendarioReporteDTO calendarioReporteDTO = restTemplate.getForObject(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("/consultarBimestreCorrespondiente/{mes}"),CalendarioReporteDTO.class, mes);
		
        if(calendarioReporteDTO != null){ 			
			bimestre = calendarioReporteDTO.getCveBimCalendario();
        }		
		return bimestre;
	}
}