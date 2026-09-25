/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import mx.gob.imss.csdiss.sdroc.service.interfaces.BloqueoObraService;
import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.MotivoDTO;
import mx.gob.imss.csdiss.sdroc.service.RegistroIncidenciaService;
import mx.gob.imss.csdiss.sdroc.service.RegistroObraService;
import mx.gob.imss.csdiss.sdroc.util.CorreoTemplate;
import mx.gob.imss.csdiss.sdroc.util.ExtensionEnum;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

/**
 * @author daniel.hernandez
 * 
 */
@Controller
public class RegistroIncidenciaController {

	@Autowired
	RegistroObraService registroObraService;

	@Autowired
	EmailServiceRemote emailServiceRemote;

	@Autowired
	RegistroIncidenciaService registroIncidenciaService;

	@Autowired
	BloqueoObraService bloqueObraService;
	
	@Autowired
	SolicitudBusinessRemote solicitudBusinessRemote;
	
	@Autowired
	FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;

	private final String KEY_FIRMA_E = "firmaElectronicaSession";
	@Autowired
	ServletContext context;

	String patAccion = "[0-7]";

	private String calcularUltimoDiaPresentacion(InformacionIncidenciaDTO reporteBimestral) {
		String ultimoDiaPaPresentar = "";
		ultimoDiaPaPresentar = reporteBimestral.getCalendarioReporteDTO().getFecFinPerDeclarado().getDate() + "/"
				+ (reporteBimestral.getCalendarioReporteDTO().getFecFinPerDeclarado().getMonth() + 1) + "/"
				+ reporteBimestral.getNumAnio();

		return ultimoDiaPaPresentar;
	}

	private Date resolverFechaSuspension(InformacionIncidenciaDTO incidencia) {

		if (incidencia != null) {
			return incidencia.getFecSuspencion();
		} else {
			return null;
		}
	}

	private Date resolverFechaReanudacion(InformacionIncidenciaDTO incidencia) {

		if (incidencia != null) {
			return incidencia.getFecReanudacion();
		} else {
			return null;
		}
	}

	@RequestMapping(value = { "/reporteBimestral/{cveRegObra}/{idAccion}", "/cancelar/{cveRegObra}/{idAccion}",
			"/suspender/{cveRegObra}/{idAccion}", "/terminar/{cveRegObra}/{idAccion}",
			"/actualizar/{cveRegObra}/{idAccion}", "/reanudar/{cveRegObra}/{idAccion}", "/remplazar/{cveRegObra}/{idAccion}" })
	public ModelAndView poolIncidencia(@PathVariable("cveRegObra") String numRegistroObra,
			@PathVariable("idAccion") String idAccion, ModelAndView modelandview, HttpSession session)
			throws IOException {
		try {
			List<MotivoDTO> listaMotivosPorIncidencia = new ArrayList<MotivoDTO>();

			InformacionObraDTO obra = new InformacionObraDTO();
			InformacionIncidenciaDTO incidencia = new InformacionIncidenciaDTO();
			InformacionIncidenciaDTO ultimaReanudacion = new InformacionIncidenciaDTO();
			InformacionIncidenciaDTO ultimaSuspencion = new InformacionIncidenciaDTO();
			InformacionIncidenciaDTO reporteBimestral = new InformacionIncidenciaDTO();
			modelandview = new ModelAndView("/registro/incidencia/registraIncidencia");
			String txtTitulo = "";
			String txtBoton = "";
			String bimestre = "";

			session.getAttribute("rfc");

			if (Pattern.matches(patAccion, idAccion)) {

				obra = registroObraService.obtenerInformacionObraPorNumRegObra(numRegistroObra);
				listaMotivosPorIncidencia = registroObraService.obtenerMotivosPorTipoIncidencia(idAccion);
				if (idAccion.equals("1")) {
					txtTitulo = "cancelación";
					txtBoton = "Registrar Cancelación";
					ultimaSuspencion = registroIncidenciaService
							.consultarUltimaIncidenciaRegistrada(obra.getCveInformacionObra(), 2);
					modelandview.addObject("ultimaSuspencion", resolverFechaSuspension(ultimaSuspencion));
				} else if (idAccion.equals("2")) {
					txtTitulo = "suspensión";
					txtBoton = "Registrar Suspensión";
					ultimaReanudacion = registroIncidenciaService
							.consultarUltimaIncidenciaRegistrada(obra.getCveInformacionObra(), 5);
					modelandview.addObject("fecReanudacion", resolverFechaReanudacion(ultimaReanudacion));
				} else if (idAccion.equals("3")) {
					txtTitulo = "terminación";
					txtBoton = "Registrar Terminación";
				} else if (idAccion.equals("4")) {
					txtTitulo = "actualización";
					txtBoton = "Registrar Actualización";
					reporteBimestral = registroIncidenciaService
							.consultarUltimaIncidenciaRegistrada(obra.getCveInformacionObra(), 6);
					ultimaReanudacion = registroIncidenciaService
							.consultarUltimaIncidenciaRegistrada(obra.getCveInformacionObra(), 5);
					modelandview.addObject("fecReanudacion", resolverFechaReanudacion(ultimaReanudacion));
					modelandview.addObject("reporteBimestralPresentar",
							reporteBimestral == null ? 0 : calcularUltimoDiaPresentacion(reporteBimestral));
				} else if (idAccion.equals("5")) {
					txtTitulo = "reanudación";
					txtBoton = "Registrar Reanudación";
					reporteBimestral = registroIncidenciaService
							.consultarUltimoReporteBimetralReportado(obra.getCveInformacionObra(), 6);
					incidencia = registroObraService
							.obtenerInformacionIncidenciaPorCveObra(obra.getCveInformacionObra());
					ultimaReanudacion = registroIncidenciaService
							.consultarUltimaIncidenciaRegistrada(obra.getCveInformacionObra(), 5);
					modelandview.addObject("fecSuspension", incidencia.getFecSuspencion());
					modelandview.addObject("fecReanudacion", resolverFechaReanudacion(ultimaReanudacion));
					modelandview.addObject("reporteBimestralPresentar",
							reporteBimestral == null ? 0 : calcularUltimoDiaPresentacion(reporteBimestral));
				} else if (idAccion.equals("6")) {
					txtTitulo = "reporte bimestral";
					txtBoton = "Registrar Reporte Bimestral";
					bimestre = consultarUltimoReporteBimestral(obra.getCveInformacionObra());
					modelandview.addObject("bimIni",
							"" + registroIncidenciaService
									.bimestreCorrespondiente(String.valueOf(obra.getFecIniObra().getMonth() + 1)) + ","
									+ (obra.getFecIniObra().getYear() + 1900));
					modelandview.addObject("bimTer",
							"" + registroIncidenciaService
									.bimestreCorrespondiente(String.valueOf(obra.getFecFinObra().getMonth() + 1)) + ","
									+ (obra.getFecFinObra().getYear() + 1900));
					modelandview
							.addObject("bimAct",
									"" + registroIncidenciaService
											.bimestreCorrespondiente(String.valueOf(new Date().getMonth() + 1)) + ","
											+ (new Date().getYear() + 1900));
				} else if (idAccion.equals("7")) {
					txtTitulo = "remplazo";
					txtBoton = "Registrar Remplazo de Obra";
				}
			}

			modelandview.addObject("cveInformacionObra", obra.getCveInformacionObra());
			modelandview.addObject("idAccion", idAccion);
			modelandview.addObject("obra", obra);
			modelandview.addObject("motivos", listaMotivosPorIncidencia);
			modelandview.addObject("idAction", idAccion);
			modelandview.addObject("accion", txtTitulo);
			modelandview.addObject("btnAccion", txtBoton);
			modelandview.addObject("bimestre", bimestre);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return modelandview;
	}

	@RequestMapping(value = "/registroIncidencia")
	public @ResponseBody String registroIncidencia(@RequestBody String datosIncidencia, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) throws IOException {
		String idSession = (String) request.getSession().getAttribute("ID_SESSION_BLOQUEO");
		String cveRegistroObra = (String) request.getSession().getAttribute("NUMERO_REGISTRO_OBRA");

		String pathCancelaAcuse = "";
		String pathImg = "";
		byte[] reportePDF;
		// obtenemos los datos de la firma electronica uuid
		FirmaElectronica datosFirma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_E);
		Gson parseJson = new Gson();

		InformacionIncidenciaDTO informacion = parseJson.fromJson(datosIncidencia, InformacionIncidenciaDTO.class);
		
		int estado = bloqueObraService.validaBloqueoObraService(cveRegistroObra, idSession);
		if (estado == 0) {
		
			return "/errorObra";
		}

		pathCancelaAcuse = context.getRealPath(File.separator + "static" + File.separator + "report");
		pathImg = context.getRealPath(File.separator + "static" + File.separator + "images");

		HashMap<String, Object> informacionIncidencia = registroIncidenciaService.registraIncidencia(informacion, pathCancelaAcuse, pathImg, datosFirma);
		String correo = (String) session.getAttribute("correoEnvio");

		
		
		if (informacionIncidencia.get("reporte") != null) {	

			try {
				envioCorreo(informacionIncidencia, "registroIncidenciaAcuse", correo);
			} catch (MessagingException e) {

				e.printStackTrace();
			}
		}

		session.setAttribute("registroIncidenciaArchivoPDF", informacionIncidencia.get("reporte"));

		return "/getRegistroIncidenciaPDF"; 
	}
	
	@RequestMapping("/getReporteBimestral/{obra}/{idIncidencia}")
	public void consultarAcuseBimestral(HttpServletResponse response, @PathVariable("obra") String obra, @PathVariable("idIncidencia") Long idIncidencia) {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		byte[] acuse = registroIncidenciaService.getAcuseIncidencia(informacionIncidenciaDTO, pathRegistroObraAcuse, pathImg, fechaSolicitud, false);
		String nombreAcuse = null;
		
		
		try {
			generaArchivoRespuesta(response, acuse,nombreAcuse, ExtensionEnum.PDF);
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getAcuseIncidencia/{obra}/{tipoIncidencia}")
	public void consultarAcuseIncidencia(HttpServletResponse response, @PathVariable("obra") Long obra, @PathVariable("tipoIncidencia") Integer tipoIncidencia) {

		Long idTramite = null;
		Date fechaSolicitud = null;
		String pathRegistroObraAcuse = context.getRealPath(File.separator + "static" + File.separator + "report");
		String pathImg = context.getRealPath(File.separator + "static" + File.separator + "images");
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		
		informacionIncidenciaDTO = registroIncidenciaService.consultarUltimaIncidenciaRegistrada(obra, tipoIncidencia);
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
		String nombreAcuse = null;
		
		
		try {
			generaArchivoRespuesta(response, acuse,nombreAcuse, ExtensionEnum.PDF);
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Forma correo electronico a enviar en el registro de obra
	 * 
	 * @param nombreReporte
	 * @throws MessagingException
	 */
	private void envioCorreo(HashMap<String, Object> informacionIncidencia, String nombreAdjunto, String mail) throws MessagingException {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		byte[] reportePDF = (byte[]) informacionIncidencia.get("reporte");
		String mensaje = null;
		String titulo = null;
		
		informacionIncidencia.get("tipoIncidencia");
			titulo = "Acuse de incidencia de obra.";
			mensaje = "Se remite Acuse de recibo, incidencia ".concat(String.valueOf(informacionIncidencia.get("tipoIncidencia"))).concat(", de la obra con número: ").concat(
					String.valueOf(informacionIncidencia.get("cveObraAviso"))).concat(", de fecha ").concat(format.format(new Date()));

		nombreAdjunto = "incidenciaObra.pdf";
		
		String template = CorreoTemplate.construirPlantillaCorreo(titulo, mensaje);
		
		try {
			Map<String, byte[]> archivosAdjuntos = new HashMap<String, byte[]>();
			archivosAdjuntos.put(nombreAdjunto, reportePDF);
			//destinatario, cc, asunto, contenido,adjuntos
			emailServiceRemote.enviaCorreoConDocumentoAdjunto(mail,null,titulo,template, archivosAdjuntos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @return
	 */
	private String consultarUltimoReporteBimestral(Long cveInformacionObra) {
		String bimestre = "";
		bimestre = registroObraService.consultarReporteBimestralPresentar(cveInformacionObra);

		return bimestre;
	}

	private void generaArchivoRespuesta(HttpServletResponse response, byte[] reporte, String nombreReporte,
			ExtensionEnum extension) throws IOException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		nombreReporte = formatter.format(new Date(System.currentTimeMillis()));

		if (reporte != null) {
			// Si el archivo se genero lo regresamos para su descarga
			response.setContentType(extension.getContentType());
			response.setHeader("Content-Disposition", (extension.equals(ExtensionEnum.PDF) ? "inline" : "attachment")
					+ "; filename=" + nombreReporte + extension.getExtension());

			response.setContentLength(reporte.length);
			OutputStream ouputStream = response.getOutputStream();
			ouputStream.write(reporte, 0, reporte.length);
			ouputStream.flush();
			ouputStream.close();
		} else if (reporte == null) {
			// Si el archivo no se genero mandamos un mensaje
			response.reset();
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/html; charset=UTF-8");

			PrintWriter out = response.getWriter();
			out.println(
					"<link type=\"text/css\" href=\"/delta/resources/estilos/bootstrap/bootstrap.min.css\" rel=\"stylesheet\" />");
			out.println("<html><body><div style=\"text-align: center;\" class=\"alert alert-danger\"><h4>"
					+ "no se pudo crear el reporte" + "</h4></div></body></html>");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

	@RequestMapping(value = "/getRegistroIncidenciaPDF", method = RequestMethod.GET)
	public void getRegistroIncidenciaPDF(HttpServletResponse response, HttpServletRequest request,
			HttpSession session) {

		byte[] reportePDF = (byte[]) session.getAttribute("registroIncidenciaArchivoPDF");
		try {

			generaArchivoRespuesta(response, reportePDF, "registroIncidencia", ExtensionEnum.PDF);
			session.removeAttribute("registroIncidenciaArchivoPDF");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@RequestMapping(value = "/errorObra", method = RequestMethod.GET)
	public ModelAndView errorObra(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		
		return new ModelAndView("error");
	}

	@RequestMapping(value = "/validaRegistroObra")
	public @ResponseBody int validaRegistroObra(@RequestBody String registroObra, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) throws IOException {
		

		String idSession = (String) request.getSession().getAttribute("ID_SESSION_BLOQUEO");
		
		if (idSession == null) {
			idSession = getSaltString();
			request.getSession().setAttribute("ID_SESSION_BLOQUEO", idSession);

		}
		request.getSession().setAttribute("NUMERO_REGISTRO_OBRA", registroObra);
		
		return bloqueObraService.validaBloqueoObraService(registroObra, idSession);
	}

	@RequestMapping(value = "/liberaRegistroObra")
	public @ResponseBody void liberaRegistroObra(@RequestBody String registroObra, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) throws IOException {
		
		bloqueObraService.liberaRegistroObra(registroObra);
	}

	@RequestMapping(value = "/reiniciarRegistroObra")
	public @ResponseBody void reiniciarRegistroObra(@RequestBody String registroObra, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) throws IOException {
		
		String idSession = (String) request.getSession().getAttribute("ID_SESSION_BLOQUEO");
		bloqueObraService.reiniciarRegistroObra(registroObra, idSession);
	}

	@RequestMapping(value = "/sensaObra")
	public @ResponseBody int sensaObra(@RequestBody String registroObra, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) throws IOException {
		

		String idSession = (String) request.getSession().getAttribute("ID_SESSION_BLOQUEO");

		return bloqueObraService.sensaTiempoObra(registroObra, idSession);
	}

	protected String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 19) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}
}
