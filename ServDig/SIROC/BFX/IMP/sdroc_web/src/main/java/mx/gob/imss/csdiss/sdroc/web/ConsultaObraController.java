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
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.JsonResponseDTO;
import mx.gob.imss.csdiss.sdroc.dto.MotivoDTO;
import mx.gob.imss.csdiss.sdroc.dto.MotivoTipoIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.service.ConsultaObraService;
import mx.gob.imss.csdiss.sdroc.service.RegistroObraService;
import mx.gob.imss.csdiss.sdroc.util.ExtensionEnum;
import mx.gob.imss.csdiss.sdroc.util.IncidenciasEnum;

/**
 * @author daniel.hernandez
 * 
 */
@Controller
public class ConsultaObraController {

	@Autowired
	RegistroObraService registroObraService; // = new RegistroObraServiceImpl();
	@Autowired
	ConsultaObraService consultaObraService;

	@Autowired
	ServletContext context;

	@RequestMapping(value = "/consulta", method = RequestMethod.GET)
	public ModelAndView consultaObra(HttpSession session, HttpServletRequest request) {
		ModelAndView modelandview = new ModelAndView("/consulta/consultaObra");
		try {
			modelandview.addObject("listaRegistrosPatronales",
					registroObraService.consultarObrasPorRFC(session
							.getAttribute("rfc").toString()));
			modelandview.addObject("rfc", session.getAttribute("rfc"));
			modelandview.addObject("rp", session.getAttribute("rp"));
			modelandview.addObject("razonSocial",
					session.getAttribute("razonSocial"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return modelandview;
	}
	
	@RequestMapping(value = "/fechaActual")
	public @ResponseBody Long obtenerFechaActualServer() {
		
		Date hoy = new Date();
		
		return hoy.getTime(); 
	}

	@RequestMapping(value = "/resumenRegistro/{cveRegObra}/{idreg}", method = RequestMethod.GET)
	public ModelAndView consultaRegistroObra(
			@PathVariable("cveRegObra") String cveRegObra,
			@PathVariable("idreg") String idRegistroObra, HttpSession session) {
		
		
		ModelAndView modelandview = new ModelAndView(
				"/consulta/consultaDatosObra");
		String cveRegPatronal = (String)session.getAttribute("rp");
		InformacionObraDTO datosRegistroObra = (InformacionObraDTO) registroObraService
				.consultaRegObrasPorNRO(cveRegObra);
		String descTipoObra = datosRegistroObra.getTipoObraDTO().getDesTipoObra();
		
		if(descTipoObra.toUpperCase().startsWith("SIN TIPO")) {
			descTipoObra = datosRegistroObra.getObjetoContratoDTO().getDesObjetoContrato();
			datosRegistroObra.getTipoObraDTO().setDesTipoObra(descTipoObra);
		}
		String cveRegObraPrincipal = "";
		if(datosRegistroObra.getCveRegistroObraPrincipal() != null){
			InformacionObraDTO infoPrincipal = registroObraService.obtenerInformacionObraPorCveInformacionObra(datosRegistroObra.getCveRegistroObraPrincipal());
			
			if(infoPrincipal != null){
				cveRegObraPrincipal = infoPrincipal.getCveRegistroObra();
			}
		}
			
		modelandview.addObject("cveInformacionObraPadre", cveRegObraPrincipal);
		modelandview.addObject("resumenRegistrosObra", datosRegistroObra);
		modelandview.addObject("cveInformacionObra", cveRegObra);
		modelandview.addObject("cveRegPatronal", cveRegPatronal);
		List<InformacionIncidenciaDTO> listaIncidencias = new ArrayList<InformacionIncidenciaDTO>();

		InformacionIncidenciaDTO auxCarga = new InformacionIncidenciaDTO();
		MotivoTipoIncidenciaDTO motivoTipoIncidenciaDTO = new MotivoTipoIncidenciaDTO();
		motivoTipoIncidenciaDTO.setMotivoDTO(new MotivoDTO());
		auxCarga.setMotivoTipoIncidenciaDTO(motivoTipoIncidenciaDTO);

		modelandview.addObject("mostrar", datosRegistroObra.getTipoObraDTO().getCveTipoObra() == 2 ? false : true);
		modelandview.addObject("bimestreUltimoRep", "");
		modelandview.addObject("incSuspen", auxCarga);
		modelandview.addObject("incSusFecha", "");
		modelandview.addObject("incReanuda", auxCarga);
		modelandview.addObject("incReaFecha", "");
		modelandview.addObject("incReaFechaTer", "");
		modelandview.addObject("incTermina", auxCarga);
		modelandview.addObject("incTerFecha", "");
		modelandview.addObject("incCancela", auxCarga);
		modelandview.addObject("incCanFecha", "");
		modelandview.addObject("incActualiza", auxCarga);
		modelandview.addObject("incActFecha", "");
		modelandview.addObject("incActFechaTer", "");
		String cadenaBimestre = "00-0000";
		if (!idRegistroObra.equals("valor")) {
			InformacionIncidenciaDTO bimestralObraDTO = registroObraService.consultaBimRepCveInfoObra(datosRegistroObra.getCveInformacionObra());
			if (bimestralObraDTO != null) {
				cadenaBimestre = "0".concat(String.valueOf(bimestralObraDTO.getCalendarioReporteDTO().getCveBimCalendario())).concat("-").concat(bimestralObraDTO.getNumAnio().toString());
			}
		}
		modelandview.addObject("bimestreUltimoRep", cadenaBimestre);
		if (!idRegistroObra.equals("valor")) {
			listaIncidencias = registroObraService.consultaIncidentesPorNRO(Long.parseLong(idRegistroObra));
		}

		if (listaIncidencias.size() > 0) {
			modelandview = agregarListaIncidencias(modelandview, listaIncidencias, datosRegistroObra);
		}
		
		generarResumeObra(datosRegistroObra, session);

		return modelandview;
	}


	private ModelAndView agregarListaIncidencias(ModelAndView modelandview, List<InformacionIncidenciaDTO> listaIncidencias, InformacionObraDTO datosRegistroObra){
		for (InformacionIncidenciaDTO infoIncidencia : listaIncidencias) {

			if (infoIncidencia.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO().getCveTipoIncidencia() == IncidenciasEnum.SUSPENSION.getValor()) {
				modelandview.addObject("incSuspen", infoIncidencia);
				modelandview.addObject("incSusFecha",obtenerFechaComoString(infoIncidencia.getFecSuspencion()));
			}
			if (infoIncidencia.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO().getCveTipoIncidencia() == IncidenciasEnum.REANUDACION.getValor()) {
				
				infoIncidencia = obtenerInformacionIncidenciaDTOParaReanudar(infoIncidencia, datosRegistroObra);
				
				modelandview.addObject("incReanuda", infoIncidencia);
				modelandview.addObject("incReaFecha", obtenerFechaComoString(infoIncidencia.getFecReanudacion()));
				modelandview.addObject("incReaFechaTer", obtenerFechaComoString(infoIncidencia.getFecFinObra()));
			}
			if (infoIncidencia.getMotivoTipoIncidenciaDTO()
					.getTipoIncidenciaDTO().getCveTipoIncidencia() == IncidenciasEnum.TERMINACION
					.getValor()) {
				modelandview.addObject("incTermina", infoIncidencia);
				modelandview.addObject("incTerFecha", obtenerFechaComoString(infoIncidencia.getFecFinObra()));
			}
			if (infoIncidencia.getMotivoTipoIncidenciaDTO()
					.getTipoIncidenciaDTO().getCveTipoIncidencia() == IncidenciasEnum.CANCELACION
					.getValor()) {
				modelandview.addObject("incCancela", infoIncidencia);
				modelandview.addObject("incCanFecha", obtenerFechaComoString(infoIncidencia.getFecCanObra()));
			}
			if (infoIncidencia.getMotivoTipoIncidenciaDTO()
					.getTipoIncidenciaDTO().getCveTipoIncidencia() == IncidenciasEnum.ACTUALIZACION
					.getValor()) {
				if(infoIncidencia.getImpObra()==null || infoIncidencia.getImpObra()==0){
					infoIncidencia.setImpObra(datosRegistroObra.getImpObra());
				}
				if(infoIncidencia.getRefSupConstruccion()==null || infoIncidencia.getRefSupConstruccion()==0){
					infoIncidencia.setRefSupConstruccion(datosRegistroObra.getRefSupConstruccion());
				}
				if(infoIncidencia.getFecFinObra()==null){
					infoIncidencia.setFecFinObra(datosRegistroObra.getFecFinObra());
				}
				modelandview.addObject("incActualiza", infoIncidencia);
				modelandview.addObject("incActFecha", obtenerFechaComoString(infoIncidencia.getFecActualizacion()));
				modelandview.addObject("incActFechaTer", obtenerFechaComoString(infoIncidencia.getFecFinObra()));
			}
		}
		return modelandview;
	}
	
	private String obtenerFechaComoString(Date fecSuspencion){
		String result = null;
		SimpleDateFormat forma = new SimpleDateFormat("dd/MM/yyyy");
		if(fecSuspencion != null){
			result = forma.format(fecSuspencion);
		}else{
			result = "";
		}
		return result;
	}
	
	
	private InformacionIncidenciaDTO obtenerInformacionIncidenciaDTOParaReanudar(InformacionIncidenciaDTO infoIncidencia, InformacionObraDTO datosRegistroObra){
		if(infoIncidencia.getImpObra()==null || infoIncidencia.getImpObra()==0){
			infoIncidencia.setImpObra(datosRegistroObra.getImpObra());
		}
		if(infoIncidencia.getRefSupConstruccion()==null || infoIncidencia.getRefSupConstruccion()==0){
			infoIncidencia.setRefSupConstruccion(datosRegistroObra.getRefSupConstruccion());
		}
		if(infoIncidencia.getFecFinObra()==null){
			infoIncidencia.setFecFinObra(datosRegistroObra.getFecFinObra());
		}
		if(infoIncidencia.getImpEjercido()==null){
			infoIncidencia.setImpEjercido(datosRegistroObra.getImpEjercido());
		}
		return infoIncidencia;
	}
	
	private void generarResumeObra(InformacionObraDTO datosRegistroObra, HttpSession session) {
		String pathImg = "";
		String pathResumenObra = "";
		byte[] reportePDF;
		pathResumenObra = context.getRealPath(File.separator + "static" + File.separator + "report");
		pathImg = context.getRealPath(File.separator + "static" + File.separator + "images");
		reportePDF = registroObraService.generaResumenObra(datosRegistroObra, pathResumenObra, pathImg);
		session.setAttribute("resumenObraArchivoPDF", reportePDF);
	}
	
	@RequestMapping(value = "/getResumenObraPDF", method=RequestMethod.GET)
	public void getResumenObraPDF(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		
		byte[] reportePDF = (byte[])session.getAttribute("resumenObraArchivoPDF");
		try {
			
			generaArchivoRespuesta(response, reportePDF, "registroObra", ExtensionEnum.PDF);
			session.removeAttribute("resumenObraArchivoPDF");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/avisosUbicacion", method = RequestMethod.GET)
	public ModelAndView reporteAvisosUbicacion(HttpSession httpSession) {
		ModelAndView modelandview = new ModelAndView(
				"/consulta/reporteAvisosUbicacion");
		modelandview.addObject("listaReporteAvisos",
				registroObraService.consultaReporteAvisosUbicacion(httpSession.getAttribute("rfc").toString()));
		return modelandview;
	}

	@RequestMapping(value = "/reporteGeneral", method = RequestMethod.GET)
	public ModelAndView reporteGeneral() {
		
		ModelAndView modelandview = new ModelAndView("/consulta/reporteGeneral");
		return modelandview;
	}

	@RequestMapping(value = "/subcontratos/{cveRegPatronal}/{idAction}")
	public ModelAndView consultaSubcontratos(
			@PathVariable("cveRegPatronal") String cveRegPatronal,
			@PathVariable("idAction") String idAction, HttpSession session) {
		
		ModelAndView modelandview = new ModelAndView("/consulta/subcontratos");
		modelandview.addObject("listaSubcontratos", consultaObraService.consultaSubcontratosPorNumRegistroObra(cveRegPatronal));
		modelandview.addObject("regPatronal",session.getAttribute("rp"));
		modelandview.addObject("cveRegPatronal",session.getAttribute("cveRegPatronalSubcontrato"));
		session.setAttribute("noInfObra", cveRegPatronal);
		modelandview.addObject("numeroObra",session.getAttribute("numeroObra"));
		return modelandview;
	}
	
	@RequestMapping(value = "/exportarExcel/subcontratos/{cveRPContratante}")
	public void exportarExcelSubcontratos(@PathVariable("cveRPContratante") String cveRPContratante, HttpSession session,
			HttpServletResponse response, HttpServletRequest request, Model model) {
		
			String noInfObra = (String)session.getAttribute("noInfObra");
			String rutaPlantilla = context.getRealPath(File.separator + "static"
					+ File.separator + "report" + File.separator + "plantilla");
			String nombreReporte = File.separator + "reporteSubcontratos";
			String nombreSubReporte = File.separator + "reporteSubcontratos_subreport";
			try {
				byte[] reporteExcel = registroObraService.exportarExcelSubcontratos(noInfObra, rutaPlantilla, nombreReporte, nombreSubReporte, session, cveRPContratante);
				generaArchivoRespuesta(response, reporteExcel, nombreReporte, ExtensionEnum.XLS);
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
	}

	/**
	 * Consulta los registros de obra a partir del numero de registro de obra
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@ResponseBody
	@RequestMapping(value = "/registroPatronal/{cveRegPatronal}")
	public ModelAndView consultaRegistrosPatronalesPorRP(
			@PathVariable("cveRegPatronal") String cveRegPatronal,
			HttpSession session) {
		
		ModelAndView modelandview = new ModelAndView(
				"/consulta/registrosPatronales");
		modelandview.addObject("regPatronal", cveRegPatronal);
		session.setAttribute("cveRegPatronalSubcontrato", cveRegPatronal);
		modelandview.addObject("listaRegistrosPatronales", registroObraService
				.consultaObrasRegistradasPorRegistroPatronal(cveRegPatronal));
		return modelandview;
	}

	@RequestMapping(value = "/consultaObrasRegistradasAnualesPorRFC/{rfc}/{anio}")
	public @ResponseBody
	JsonResponseDTO consultaObrasRegistradasAnioyRFC(
			@PathVariable("rfc") String rfc, @PathVariable("anio") String anio, HttpSession session) {
		
		JsonResponseDTO jsonResponse = new JsonResponseDTO();
		jsonResponse.setEstatus("SUCCESS");
		jsonResponse.setResultado(consultaObraService
				.consultaObrasRegistradasAnualesPorRFC(rfc, anio));

		return jsonResponse;
	}
	
	@ResponseBody
	@RequestMapping(value = "/acuses/{numSeqNotaria}/{cveInformacionObra}")
	public ModelAndView numeroObra(@PathVariable("numSeqNotaria") String numSeqNotaria, @PathVariable("cveInformacionObra") String cveInformacionObra, HttpSession session){
		
		ModelAndView modelandview = new ModelAndView("/consulta/acuses");
		modelandview.addObject("cveRegPatronal",session.getAttribute("cveRegPatronalSubcontrato"));
		modelandview.addObject("numSeqNotaria", numSeqNotaria);
		List<InformacionIncidenciaDTO> incidencias = registroObraService.consultaIncidenciasPorCveInformacionObra(cveInformacionObra);
		modelandview.addObject("listaIncidencias", incidencias);
		return modelandview;
	}
	
	@RequestMapping(value = "/guardarNumeroObra/{numeroObra}")
	public void numeroObra(@PathVariable("numeroObra") String numeroObra, HttpSession session){
		session.setAttribute("numeroObra", numeroObra);
	}
	
	@RequestMapping(value = "/reporteGeneralObras/exportaExcel/{rfc}/{anio}")
	public void generarExcelReporteGeneralObras(HttpServletResponse response, Model model, HttpSession session,
			@PathVariable("rfc") String rfc, @PathVariable("anio") String anio)
			throws IOException {
		
		String rutaPlantilla = context.getRealPath(File.separator + "static"
				+ File.separator + "report" + File.separator + "plantilla");
		String nombreReporte = File.separator + "reporteGeneralObra";
		String nombreSubReporte = File.separator + "reporteGeneralObra_subreport";
		
		try {
			byte[] reporteExcel = registroObraService.exportarExcelRegistroGeneralObraPorRFCAnio(rfc, anio, rutaPlantilla, nombreReporte, nombreSubReporte, session);
			generaArchivoRespuesta(response, reporteExcel, nombreReporte, ExtensionEnum.XLS);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/registroPatronal/exportaExcel/{cveRegPatronal}")
	public void generarExcelRegistroPatronal(HttpServletResponse response, Model model, HttpSession session, 
			@PathVariable("cveRegPatronal") String cveRegPatronal) {
		
		String rutaPlantilla = context.getRealPath(File.separator + "static"
				+ File.separator + "report" + File.separator + "plantilla");
		String nombreReporte = File.separator + "reporteRegistroPatronal";
		String nombreSubReporte = File.separator + "reporteRegistroPatronal_subreport1";
		try {
			byte[] reporteExcel = registroObraService.exportarExcelRegistrosPatronalesPorRP(cveRegPatronal, rutaPlantilla, nombreReporte, nombreSubReporte, session);
			generaArchivoRespuesta(response, reporteExcel, nombreReporte, ExtensionEnum.XLS);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/avisosUbicacion/exportaExcel")
	public void generarExcelAvisosUbicacion(HttpServletResponse response, Model model, HttpSession session) {
		
		String rutaPlantilla = context.getRealPath(File.separator + "static"
				+ File.separator + "report" + File.separator + "plantilla");
		String nombreReporte = File.separator + "reporteAvisoUbicacionObra";
		String nombreSubReporte = File.separator + "reporteAvisoUbicacionObra_subreport";
		try {
			byte[] reporteExcel = registroObraService.exportarExcelAvisoUbicacionObra(rutaPlantilla, nombreReporte, nombreSubReporte, session);
			generaArchivoRespuesta(response, reporteExcel, nombreReporte, ExtensionEnum.XLS);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void generaArchivoRespuesta(HttpServletResponse response, byte[] reporte, String nombreReporte, ExtensionEnum extension) throws IOException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		nombreReporte = formatter.format(new Date(System.currentTimeMillis()));
		
		if (reporte != null) {
			// Si el archivo se genero lo regresamos para su descarga
			response.setContentType(extension.getContentType());
			response.setHeader("Content-Disposition",
					(extension.equals(ExtensionEnum.PDF) ? "inline" : "attachment") +"; filename=" + nombreReporte + extension.getExtension());

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
			out.println("<link type=\"text/css\" href=\"/delta/resources/estilos/bootstrap/bootstrap.min.css\" rel=\"stylesheet\" />");
			out.println("<html><body><div style=\"text-align: center;\" class=\"alert alert-danger\"><h4>"
					+ "no se pudo crear el reporte"
					+ "</h4></div></body></html>");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
