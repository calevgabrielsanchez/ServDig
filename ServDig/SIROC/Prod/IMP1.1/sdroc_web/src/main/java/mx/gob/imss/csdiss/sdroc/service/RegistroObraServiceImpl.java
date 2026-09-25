/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import mx.gob.imss.csdiss.sdroc.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionObraService;
import mx.gob.imss.csdiss.sdroc.util.CadenaOriginaQR;
import mx.gob.imss.csdiss.sdroc.util.CargarParametrosReporte;
import mx.gob.imss.csdiss.sdroc.util.GeneraReporte;
import mx.gob.imss.csdiss.sdroc.util.IncidenciasEnum;
import mx.gob.imss.csdiss.sdroc.util.ReporteEnum;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;

/**
 * @author daniel.hernandez
 *
 */
@Service
@Configuration
@PropertySource("classpath:messages.properties")
public class RegistroObraServiceImpl implements RegistroObraService {

	@Autowired
	SolicitudTramiteService solicitudTramiteService;
	
	@Autowired
	RegistroIncidenciaService registroIncidenciaService;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private Environment env;

	/**
	 * Path base para localizacion de servicios.
	 */
	
	private String PROTOCOL = "http://";
	private String CONTEXT = "/sdroc-rest/";

	@Autowired 
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	@Autowired
	private ConsultaObraService consultaObraService;
	@Autowired
	InformacionObraService informacionObraServiceEjb;
	
	
	private final Integer ID_TRAMITE_REGISTRO_OBRA = 137;
	private final Integer ID_TRAMITE_REGISTRO_AVISO = 140;
	
	/**
	 * Recupera la IP del servidor donde se encuentra desplegado el servicio
	 * @return String
	 */
	private String recoveryIP() {
		return env.getProperty("endpoint.rest");
	}

	public Object consultarObrasPorRFCyRP(String cveRfc, String cveRegPatronal) {
		List<InformacionObraDTO> listaObrasRegistradas = new ArrayList<InformacionObraDTO>();
		listaObrasRegistradas = informacionObraServiceEjb.consultarInformacionObrasPorCveRfcYCveRegPatronal(cveRfc, cveRegPatronal);
		
		for (InformacionObraDTO obra : listaObrasRegistradas) {

			String bimestre = "";
			bimestre = consultarReporteBimestralPresentar(obra.getCveInformacionObra());

			if (verificaReporteBimestralCorrientePresentado(obra.getFecIniObra(), obra.getFecFinObra())
					|| bimestre.equals("00-0000")) {
				obra.setNumEvaluacionD32(0);
			} else {
				obra.setNumEvaluacionD32(1);
			}

		}
		
		return listaObrasRegistradas;
	}

	private boolean verificaReporteBimestralCorrientePresentado(Date fechaInicio, Date fechaFin) {
		Date hoy = new Date();
		boolean banderaBimestre = false;

		Long bimInicio = registroIncidenciaService.bimestreCorrespondiente(String.valueOf(fechaInicio.getMonth() + 1));
		Long bimFin = registroIncidenciaService.bimestreCorrespondiente(String.valueOf(fechaFin.getMonth() + 1));
		Long bimAct = registroIncidenciaService.bimestreCorrespondiente(String.valueOf(hoy.getMonth() + 1));

		if (fechaInicio.after(hoy)) {
			banderaBimestre = true;
		}

		if (fechaFin.after(hoy)) {
			if ((fechaInicio.getYear() + 1900) == (hoy.getYear() + 1900)) {
				if (bimInicio == bimAct) {
					banderaBimestre = true;
				}
			}
		}

		if ((fechaInicio.getYear() + 1900) == (fechaFin.getYear() + 1900)) {
			if (bimInicio.intValue() == bimFin.intValue()) {
				banderaBimestre = true;
			}
		}

		return banderaBimestre;
	}

	@Override
	public List<MotivoDTO> obtenerMotivosPorTipoIncidencia(String idTipoIncidencia) throws IOException {
		List<MotivoDTO> listaMotivosPorIncidencia = new ArrayList<MotivoDTO>();
		
		
		MotivoDTO[] listaMotivos = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("motivosPorIncidencia/").concat(idTipoIncidencia), MotivoDTO[].class);

		for (MotivoDTO motivo : listaMotivos) {
			if (motivo.getCveMotivo() != 1) {
				listaMotivosPorIncidencia.add(motivo);
			}
		}
		return listaMotivosPorIncidencia;
	}

	@Override
	public List<TipoObraDTO> consultarTiposObra(String idTipoObra) {
		
		List<TipoObraDTO> listaTiposObra = new ArrayList<TipoObraDTO>();
		
		TipoObraDTO[] listaTiposObraResponse = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("tipoObrasPorClasificacion/{cveClasificacionObra}"), TipoObraDTO[].class,
				idTipoObra);
		
		listaTiposObra = Arrays.asList(listaTiposObraResponse);

		return listaTiposObra;
	}

	@Override
	public List<ObjetoContratoDTO> consultarObjetoContrato() {
		List<ObjetoContratoDTO> listaObjetosContrato = new ArrayList<ObjetoContratoDTO>();
		
		ObjetoContratoDTO[] listaObjetosResponse = restTemplate.getForObject(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("objetosContrato/"),
				ObjetoContratoDTO[].class);
		
		listaObjetosContrato = Arrays.asList(listaObjetosResponse);
		return listaObjetosContrato;
	}

	@Override
	public InformacionObraDTO obtenerInformacionObraPorNumRegObra(String cveRegistroObra) {
		InformacionObraDTO registroObra = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarObraPorCveRegistroObra/{cveInformacionObra}"), InformacionObraDTO.class,
				cveRegistroObra);
		
		return registroObra;
	}

	public Object consultaObrasRegistradasPorRegistroPatronal(String cveRegPatronal) {
		List<InformacionObraDTO> listaObrasRegistradas = new ArrayList<InformacionObraDTO>();
		
		InformacionObraDTO[] listaObras = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarObrasPorCveRegPatronal/").concat("{cveRegPatronal}"),
				InformacionObraDTO[].class, cveRegPatronal);
		
		listaObrasRegistradas = Arrays.asList(listaObras);
		return listaObrasRegistradas;
	}

	public Object consultaObrasRegistradasPorRegistroPatronalReporte(String cveRegPatronal) {
		List<InformacionObraDTO> listaObrasRegistradas = new ArrayList<InformacionObraDTO>();
		
		InformacionObraDTO[] listaObras = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarReporteInformacionObrasPorCveRegPatronal/").concat("{cveRegPatronal}"),
				InformacionObraDTO[].class, cveRegPatronal);
		
		listaObrasRegistradas = Arrays.asList(listaObras);

		return listaObrasRegistradas;
	}

	@Override
	public HashMap<String, Object> registrarObra(InformacionObraDTO datosRegistroObra, AvisoObraDTO datosRegistroAviso,
			String pathRegistroObraAcuse, String pathImg, FirmaElectronica firmaElectronica) {

		HashMap<String, Object> informacionObra = null;

		SolicitudTramiteDTO solicitudTramiteDTO = null;
		try {
			// Obtenemos el id del tipo de tramite a realizar dependiendo de lo
			// que se vaya a registrar
			Integer idTipoTramite = datosRegistroAviso == null ? ID_TRAMITE_REGISTRO_OBRA : ID_TRAMITE_REGISTRO_AVISO;
			solicitudTramiteDTO = solicitudTramiteService.crearSolicitudTramite(idTipoTramite, firmaElectronica);

			if (datosRegistroAviso == null) {
				datosRegistroObra.setRefCadenaOriginal(firmaElectronica.getCadenaOriginal());
                System.out.println("Datos de la firma... " + firmaElectronica.getCadenaOriginal() );
				System.out.println("DATOS DE REGITRO DE OBRA CADENA... " + datosRegistroObra.getRefCadenaOriginal());
				datosRegistroObra.setRefSelloDigital(firmaElectronica.getRecibo());
				datosRegistroObra.setNumSeqNotaria(firmaElectronica.getReciboNotarial());
				datosRegistroObra.setCveIdTramite(solicitudTramiteDTO.getCveIdTramite());
				datosRegistroObra.setFolio(solicitudTramiteDTO.getFolioSolicitud());
				
				informacionObra = registroObra(datosRegistroObra, pathRegistroObraAcuse, pathImg);
				informacionObra.put("tipoObra", "registro");

			} else {
				datosRegistroAviso.setRefSeqNotarial(firmaElectronica.getReciboNotarial());
				datosRegistroAviso.setRefCadenaOriginal(firmaElectronica.getCadenaOriginal());
                System.out.println("Datos de la firma... " + firmaElectronica.getCadenaOriginal() );
				System.out.println("DATOS DE REGITRO DE AVISO CADENA... " + datosRegistroAviso.getRefCadenaOriginal());
				datosRegistroAviso.setRefSelloDigital(firmaElectronica.getRecibo());
				datosRegistroAviso.setCveIdTramite(solicitudTramiteDTO.getCveIdTramite());
				datosRegistroAviso.setNumSeqNotaria(firmaElectronica.getReciboNotarial());
				datosRegistroAviso.setFolio(solicitudTramiteDTO.getFolioSolicitud());
				
				informacionObra = registrarAvisoObra(datosRegistroAviso, pathRegistroObraAcuse, pathImg);
				informacionObra.put("tipoObra", "aviso");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return informacionObra;
	}
	
	public void actualizaObra(InformacionObraDTO datosRegistroObra) {
		System.out.println("*********************voy a rest de actualizar obra");
			restTemplate.postForEntity(
					PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("actualizarInformacionObra"), datosRegistroObra, InformacionObraDTO.class);
	}

	private HashMap<String, Object> registroObra(InformacionObraDTO datosRegistroObra, String pathRegistroObraAcuse, String pathImg) {
		byte[] reportePDF = null;

		HashMap<String, Object> respuestaGuardado = new HashMap<String, Object>();
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
			ObjectMapper objectMapper = new ObjectMapper();
			dateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
			objectMapper.setDateFormat(dateFormat);

			List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
			MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
			jsonConverter.setObjectMapper(objectMapper);
			converters.add(jsonConverter);
			restTemplate.setMessageConverters(converters);
			
			ResponseEntity<InformacionObraDTO> informacionRespuesta = restTemplate.postForEntity(
					PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("guardarInformacionObra"), datosRegistroObra, InformacionObraDTO.class);
			
			InformacionObraDTO informacionObraDTO = informacionRespuesta.getBody();

			if (informacionObraDTO.getCveInformacionObra() != 0) {
				reportePDF = generaAcuseRegistroObra(informacionObraDTO, pathRegistroObraAcuse, pathImg, new Date(), true);
				
				respuestaGuardado.put("reporte", reportePDF);
				respuestaGuardado.put("claveObra", informacionObraDTO.getCveRegistroObra());				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return respuestaGuardado;
	}
	
	@Override
	public byte[] generaAcuseRegistroObra(InformacionObraDTO informacionObraDTO, String pathRegistroObraAcuse, String pathImg, Date date, Boolean generarCadena) {
		byte[] reportePDF = null;
		GeneraReporte generadorReporte = new GeneraReporte();
		CargarParametrosReporte paramReporte = new CargarParametrosReporte();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
		String fechaFormato = sdf.format(date);
		System.out.println("=================================");
        System.out.println("DATE         = " + date);
        System.out.println("FECHA FORMATO = " + fechaFormato);
        System.out.println("=================================");
        
		CadenaOriginaQR cadenaOriginaQR = new CadenaOriginaQR();
        
        String cadena;

            cadena = cadenaOriginaQR.formatCadenaOriginalQR(
                    informacionObraDTO.getRefCadenaOriginal(),
                    informacionObraDTO.getCveRegistroObra(),
                    "Registro de Obra de Construccion",
                    fechaFormato
            );
       
        System.out.println("CADENA FINAL:"); 
        System.out.println(cadena);
        informacionObraDTO.setRefCadenaOriginal(cadena);
        BufferedImage image_qr = cadenaOriginaQR.construirQR(cadena, 200, 200);

		HashMap<String, Object> paramAcuse = paramReporte.cargarParametrosReporte(informacionObraDTO, null, ReporteEnum.REGISTRO_OBRA_ACUSE.getValor(), pathImg, image_qr,fechaFormato);
		
		reportePDF = solicitudTramiteService.guardarArchivoNotaria(informacionObraDTO.getNumSeqNotaria(), generadorReporte, pathRegistroObraAcuse, "registroObraAcuse", ReporteEnum.ACUSE.getNombre(), paramAcuse);
		
		return reportePDF;
		
		
	}
	
	@Override
	public byte[] generaAcuseAvisoObra(AvisoObraDTO avisoObraDTO, String pathRegistroObraAcuse, String pathImg, Date date, Boolean generarCadena) {
		byte[] reportePDF = null;
		GeneraReporte generadorReporte = new GeneraReporte();
		CargarParametrosReporte paramReporte = new CargarParametrosReporte();

		CadenaOriginaQR cadenaOriginaQR = new CadenaOriginaQR();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
		String fechaFormato = sdf.format(date);
		String cadena = generarCadena  ?cadenaOriginaQR.formatCadenaOriginalQR(avisoObraDTO.getRefCadenaOriginal(), avisoObraDTO.getCveRegistroAvisoObra(), "Aviso de ubicacion de obra de construccion", fechaFormato) : avisoObraDTO.getRefCadenaOriginal();
		
        cadena = cadena.replaceFirst("%NOMBRE_TRAMITE%", "|Tr\u00E1mite: Aviso de ubicacion de obra de construcci\u00F3n");

		cadena = cadena.replace(
		        "%FECHA_ACTUAL%",
		        "Fecha: " + fechaFormato + "|");

		cadena = cadena.replace(
		        "%NUM_REG_OBRA%",
		        "N\u00FAmero de registro de obra: "
		                + avisoObraDTO.getCveRegistroAvisoObra());
        
        avisoObraDTO.setRefCadenaOriginal(cadena);
        BufferedImage image_qr =   cadenaOriginaQR.construirQR(cadena, 99, 86);
		
		HashMap<String, Object> paramAcuse = paramReporte.cargarParametrosReporte(avisoObraDTO, null, ReporteEnum.AVISO_UBICACION_OBRA_ACUSE.getValor(), pathImg, image_qr, fechaFormato);
		reportePDF = solicitudTramiteService.guardarArchivoNotaria(avisoObraDTO.getNumSeqNotaria(), generadorReporte,	pathRegistroObraAcuse, "ubicacionObraAcuse", ReporteEnum.ACUSE.getNombre(), paramAcuse);
		
		
		return reportePDF;
		
		
	}
	
	private HashMap<String, Object> registrarAvisoObra(AvisoObraDTO datosRegistroAviso, String pathRegistroObraAcuse, String pathImg) {
		byte[] reportePDF = null;
		HashMap<String, Object> informacionObra = new HashMap<String, Object>();

		try {
			RestTemplate template = new RestTemplate();
			
			ResponseEntity<AvisoObraDTO> informacionRespuesta = template
					.postForEntity(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("guardarAvisoObra"), datosRegistroAviso, AvisoObraDTO.class);

			AvisoObraDTO avisoObraDTO = informacionRespuesta.getBody();

			if (avisoObraDTO.getCveAvisoObra() != 0) {

						
				reportePDF = generaAcuseAvisoObra(avisoObraDTO, pathRegistroObraAcuse, pathImg, new Date(), true);
				
				informacionObra.put("reporte", reportePDF);
				informacionObra.put("cveAviso", avisoObraDTO.getCveAvisoObra());
				informacionObra.put("cveObra", avisoObraDTO.getCveRegistroAvisoObra());

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return informacionObra;
	}

	@Override
	public Object consultaRegObrasPorNRO(String cveRegistroObra) {
		
		InformacionObraDTO informacionObra = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarObraPorCveRegistroObra/{cveRegistroObra}"), InformacionObraDTO.class,
				cveRegistroObra);
		
		return informacionObra;
	}

	@Override
	public Object consultaBimestrePorNRO(Long cveInformacionObra) {
		InformacionIncidenciaDTO bimestralObraDTO = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarUltimoReporteBimestralPorCveInformacionObra/{cveInformacionObra}"),
				InformacionIncidenciaDTO.class, cveInformacionObra);
		return bimestralObraDTO;
	}

	@Override
	public List<InformacionIncidenciaDTO> consultaIncidentesPorNRO(Long cveInformacionObra) {
		List<InformacionIncidenciaDTO> listaIncidencias = new ArrayList<InformacionIncidenciaDTO>();
		
		InformacionIncidenciaDTO[] listaInforIncidencias = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarIncidenciasPorTipoIncidenciaPorCveInformacionObra/{cveInformacionObra}"),
				InformacionIncidenciaDTO[].class, cveInformacionObra);

		if (listaInforIncidencias != null) {
			listaIncidencias = Arrays.asList(listaInforIncidencias);
		}

		return listaIncidencias;
	}

	@Override
	public Object consultarObrasPorRFC(String rfc) throws IOException {

		List<RegistroPatronalDTO> listaObrasRegistradas = new ArrayList<RegistroPatronalDTO>();
		
		RegistroPatronalDTO[] listaObras = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarformacionObraAgrupadaByCveRfc/{cveRfc}"), RegistroPatronalDTO[].class,
				rfc);

		listaObrasRegistradas = Arrays.asList(listaObras);

		return listaObrasRegistradas;
	}

	@Override
	public byte[] generaResumenObra(InformacionObraDTO informacionObra, String pathResumenObra, String pathImg) {
		
		try {
			List<InformacionIncidenciaDTO> listInformacionIncidencias = new ArrayList<InformacionIncidenciaDTO>();
			
			InformacionIncidenciaDTO bimestralObraDTO = consultaBimRepCveInfoObra(informacionObra.getCveInformacionObra());
			
			String cadenaBimestre = obtenercadenaBimestre(bimestralObraDTO);

			listInformacionIncidencias = consultaIncidentesPorNRO(informacionObra.getCveInformacionObra());

			List<InformacionIncidenciaDTO> listaIncMod = new ArrayList<InformacionIncidenciaDTO>();
			if (listInformacionIncidencias.size() > 0) {
				for (InformacionIncidenciaDTO infoIncidencia : listInformacionIncidencias) {
					if (infoIncidencia.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO()
							.getCveTipoIncidencia() == IncidenciasEnum.REANUDACION.getValor()) {
						if (infoIncidencia.getImpObra() == null || infoIncidencia.getImpObra() == 0) {
							infoIncidencia.setImpObra(informacionObra.getImpObra());
						}
						if (infoIncidencia.getRefSupConstruccion() == null
								|| infoIncidencia.getRefSupConstruccion() == 0) {
							infoIncidencia.setRefSupConstruccion(informacionObra.getRefSupConstruccion());
						}
						if (infoIncidencia.getFecFinObra() == null) {
							infoIncidencia.setFecFinObra(informacionObra.getFecFinObra());
						}
						if (infoIncidencia.getImpEjercido() == null) {
							infoIncidencia.setImpEjercido(informacionObra.getImpEjercido());
						}
					}
					if (infoIncidencia.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO()
							.getCveTipoIncidencia() == IncidenciasEnum.ACTUALIZACION.getValor()) {
						if (infoIncidencia.getImpObra() == null || infoIncidencia.getImpObra() == 0) {
							infoIncidencia.setImpObra(informacionObra.getImpObra());
						}
						if (infoIncidencia.getRefSupConstruccion() == null
								|| infoIncidencia.getRefSupConstruccion() == 0) {
							infoIncidencia.setRefSupConstruccion(informacionObra.getRefSupConstruccion());
						}
						if (infoIncidencia.getFecFinObra() == null) {
							infoIncidencia.setFecFinObra(informacionObra.getFecFinObra());
						}
					}
					listaIncMod.add(infoIncidencia);
				}
			}

			GeneraReporte generadorReporte = new GeneraReporte();
			CargarParametrosReporte paramReporte = new CargarParametrosReporte();

			String cveRegObraPrincipal = obtenercveRegObraPrincipal(informacionObra);

			HashMap<String, Object> param = paramReporte.cargarParametrosResumenObra(informacionObra, listaIncMod, cadenaBimestre, pathImg, cveRegObraPrincipal);

			return generadorReporte.generaReportePDF(pathResumenObra, "resumenObra", ReporteEnum.REGISTRO.getNombre(),
					param);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	private String obtenercveRegObraPrincipal(InformacionObraDTO informacionObra){
		String result = "";
		if (informacionObra.getCveRegistroObraPrincipal() != null) {
			InformacionObraDTO infoPrincipal = obtenerInformacionObraPorCveInformacionObra(informacionObra.getCveRegistroObraPrincipal());
			if (infoPrincipal != null) {
				result = infoPrincipal.getCveRegistroObra();
			}
		}
		return result;
	}
	
	private String obtenercadenaBimestre(InformacionIncidenciaDTO bimestralObraDTO){
		String result = "";
		if (bimestralObraDTO != null) {
			result = "0".concat(String.valueOf(bimestralObraDTO.getCalendarioReporteDTO().getCveBimCalendario())).concat("-").concat(bimestralObraDTO.getNumAnio().toString());
		}
		return result;
	}

	@Override
	public Object consultaReporteAvisosUbicacion(String cveRfc) {
		List<AvisoObraDTO> listaAvisoObra = null;
		try {
			AvisoObraDTO[] avisoObraDTO = restTemplate.getForObject(
					PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarAvisoObraPorCveRfc/{cveRfc}"), AvisoObraDTO[].class, cveRfc);
			
			listaAvisoObra = Arrays.asList(avisoObraDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaAvisoObra;
	}

	@Override
	public Boolean validaCpUbicacionObra(String cveRp, String cveCodigoPostal) {
		Boolean isValido = null;

		try {
			ResponseEntity<Boolean> resultadoVal = restTemplate.getForEntity(
					PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("isValidoCpUbicacionObra/{cveRp}/{cveCodigoPostal}"), Boolean.class, cveRp,
					cveCodigoPostal);
			isValido = resultadoVal.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isValido;
	}
	
	
	
	
	@Override
	public Boolean validCircunscripcion(String codigoPostal, Long idDelegacion,
			Long idSubdelegacion) {
		Boolean isValido = null;

		try {
			ResponseEntity<Boolean> resultadoVal = restTemplate.getForEntity(
					PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("validaCircunscripcionCp/{codigoPostal}/{idDelegacion}/{idSubdelegacion}"), Boolean.class, codigoPostal,
					idDelegacion, idSubdelegacion );
			isValido = resultadoVal.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isValido;
	}

	@Override
	public InformacionObraDTO obtenerInformacionObraPorCveInformacionObra(Long cveRegistroObra) {
		
		InformacionObraDTO registroObra = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarObraPorCveInformacionObra/{cveInformacionObra}"),
				InformacionObraDTO.class, cveRegistroObra);
		
		return registroObra;
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] exportarExcelRegistrosPatronalesPorRP(String cveRegPatronal, String rutaPlantilla,
			String nombreReporte, String nombreSubReporte, HttpSession session) {
		GeneraReporte generaReporte = new GeneraReporte();
		List<InformacionObraDTO> listaObrasRegistradas = (List<InformacionObraDTO>) consultaObrasRegistradasPorRegistroPatronalReporte(
				cveRegPatronal);

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("razonSocial", session.getAttribute("rs"));
		parametros.put("rfc", session.getAttribute("rfc"));
		parametros.put("registroPatronal", cveRegPatronal);

		return generaReporte.generarReporteExcel(rutaPlantilla, nombreReporte, nombreSubReporte, parametros,
				listaObrasRegistradas);
	}

	@Override
	public byte[] exportarExcelRegistroGeneralObraPorRFCAnio(String rfc, String anio, String rutaPlantilla,
			String nombreReporte, String nombreSubReporte, HttpSession session) {
		GeneraReporte generaReporte = new GeneraReporte();
		List<InformacionObraDTO> listaObrasRegistradas = (List<InformacionObraDTO>) consultaObraService
				.consultarInformacionObrasPorCveRfcyAnio(rfc, anio);

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("razonSocial", session.getAttribute("rs"));
		parametros.put("rfc", session.getAttribute("rfc"));
		parametros.put("ejercicio", anio);

		return generaReporte.generarReporteExcel(rutaPlantilla, nombreReporte, nombreSubReporte, parametros,
				listaObrasRegistradas);
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] exportarExcelSubcontratos(String cveRP, String rutaPlantilla, String nombreReporte,
			String nombreSubReporte, HttpSession session, String cveRPContratante) {
		GeneraReporte generaReporte = new GeneraReporte();
		List<InformacionObraDTO> listaObrasRegistradas = (List<InformacionObraDTO>) consultaObraService
				.consultaSubcontratosPorNumRegistroObra(cveRP);
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("razonSocial", session.getAttribute("rs"));
		parametros.put("rfc", session.getAttribute("rfc"));
		parametros.put("registroPatronal", cveRPContratante);

		return generaReporte.generarReporteExcel(rutaPlantilla, nombreReporte, nombreSubReporte, parametros,
				listaObrasRegistradas);
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] exportarExcelAvisoUbicacionObra(String rutaPlantilla, String nombreReporte, String nombreSubReporte,
			HttpSession session) {
		GeneraReporte generaReporte = new GeneraReporte();
		List<AvisoObraDTO> listaAvisoObra = (List<AvisoObraDTO>) consultaReporteAvisosUbicacion(
				session.getAttribute("rfc").toString());

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("razonSocial", session.getAttribute("rs"));
		parametros.put("rfc", session.getAttribute("rfc"));
		parametros.put("registroPatronal", session.getAttribute("rp"));

		return generaReporte.generarReporteExcel(rutaPlantilla, nombreReporte, nombreSubReporte, parametros,
				listaAvisoObra);
	}

	@Override
	public InformacionIncidenciaDTO obtenerInformacionIncidenciaPorCveObra(Long cveInformacionObra) {
		List<InformacionIncidenciaDTO> listaIncidencias = new ArrayList<InformacionIncidenciaDTO>();
		
		InformacionIncidenciaDTO[] incidencia = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarIncidenciasPorTipoIncidenciaPorCveInformacionObra/{cveInformacionObra}"),
				InformacionIncidenciaDTO[].class, cveInformacionObra);
		
		listaIncidencias = Arrays.asList(incidencia);
		if (!listaIncidencias.isEmpty()) {
			return listaIncidencias.get(0);
		} else {
			return null;
		}
	}

	@Override
	public InformacionIncidenciaDTO consultaBimRepCveInfoObra(Long cveInformacionObra) {
		InformacionIncidenciaDTO bimestralObraDTO = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarUltimoReporteBimestralPresentado/{cveInformacionObra}"),
				InformacionIncidenciaDTO.class, cveInformacionObra);
		return bimestralObraDTO;
	}

	@Override
	public List<SubDelegacionDTO> obtenerSubDelegacionesPorCodigoPostal(String cveCodigoPostal) {
		List<SubDelegacionDTO> subdelegaciones = null;

		try {
			SubDelegacionDTO[] resultadoVal = restTemplate.getForObject(
					PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarSubdelegacionesImssPorCp/{cveCodigoPostal}"),
					SubDelegacionDTO[].class, cveCodigoPostal);
			
			subdelegaciones = Arrays.asList(resultadoVal);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return subdelegaciones;
	}

	@Override
	public List<InformacionIncidenciaDTO> consultaIncidenciasPorCveInformacionObra(String cveInformacionObra) {
		List<InformacionIncidenciaDTO> incidencias = null;

		try {
			InformacionIncidenciaDTO[] resultadoVal = restTemplate.getForObject(
					PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarIncidenciasPorCveInformacionObra/{cveInformacionObra}"),
					InformacionIncidenciaDTO[].class, cveInformacionObra);
			incidencias = Arrays.asList(resultadoVal);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return incidencias;
	}

	@Override
	public String consultarReporteBimestralPresentar(Long cveInformacionObra) {
		String bimestre = "00-0000";
		RestTemplate template = new RestTemplate();
		
		InformacionIncidenciaDTO informacionIncidencia = template.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarUltimoReporteBimestralPorCveInformacionObra/{cveInformacionObra}"),
				InformacionIncidenciaDTO.class, cveInformacionObra);
		
		if (informacionIncidencia != null) {
			if (informacionIncidencia.getCalendarioReporteDTO() != null) {
				if (informacionIncidencia.getCalendarioReporteDTO().getCveBimCalendario() != null
						&& informacionIncidencia.getNumAnio() != null) {
					bimestre = "0"
							.concat(informacionIncidencia.getCalendarioReporteDTO().getCveBimCalendario().toString())
							.concat("-").concat(informacionIncidencia.getNumAnio().toString());
				}
			}
		}

		return bimestre;
	}

	@Override
	public AvisoObraDTO consultaAvisoUbicacionObra(String numRegObra, String rfc, String registroPatronal) {
		AvisoObraDTO avisoObra = restTemplate.getForObject(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarAvisoObraPorCveAvisoObra/{cveAvisoObra}"), AvisoObraDTO.class, numRegObra);
		
		if(avisoObra != null){
			
			if(avisoObra.getInformacionPatronDTO().getCveRfc().equals(rfc) && avisoObra.getInformacionPatronDTO().getCveRegPatronal().equals(registroPatronal) && avisoObra.getRefEstadoReg() == Boolean.TRUE){
				avisoObra.setCodigoRespuesta("29");
			}
			
			
			if(!avisoObra.getInformacionPatronDTO().getCveRfc().equals(rfc) ){
				avisoObra.setCodigoRespuesta("30");
			}
			
			if(avisoObra.getInformacionPatronDTO().getCveRegPatronal() != null && !avisoObra.getInformacionPatronDTO().getCveRegPatronal().equals(registroPatronal)){
				avisoObra.setCodigoRespuesta("31");
			}
			
			if(avisoObra.getRefEstadoReg() != null && !avisoObra.getRefEstadoReg() == Boolean.TRUE){
				avisoObra.setCodigoRespuesta("32");
			}
			
		}else{
			avisoObra = new AvisoObraDTO();
			avisoObra.setCodigoRespuesta("33");
		}
		
		return avisoObra;
	}
	
	@Override
	public AvisoObraDTO consultaAvisoUbicacionObra(String numRegObra) {
		AvisoObraDTO avisoObra = restTemplate.getForObject(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("consultarAvisoObraPorCveAvisoObra/{cveAvisoObra}"), AvisoObraDTO.class, numRegObra);
		
		if(avisoObra == null){
			avisoObra = new AvisoObraDTO();
			avisoObra.setCodigoRespuesta("33");
		}
		
		return avisoObra;
	}

	@Override
	public void actualizarAvisoObra(Long cveAvisoObra) {
		 restTemplate.getForObject(PROTOCOL.concat(recoveryIP()).concat(CONTEXT).concat("actualizaAvisoObra/{cveAvisoObra}"), String.class, cveAvisoObra);
	}
}
