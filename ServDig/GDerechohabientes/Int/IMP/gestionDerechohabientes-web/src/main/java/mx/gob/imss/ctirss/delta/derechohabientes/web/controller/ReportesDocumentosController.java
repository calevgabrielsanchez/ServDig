package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabientes.PropiedadesDocumento;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Controller
@RequestMapping(value = "/reportesDocumentos/*")
public class ReportesDocumentosController extends AbstractController {

	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	DocumentosServiceRemote documentosService;
	@Autowired
	CorreccionDerechohabienteServiceRemote correccion;
	@Autowired
	TramiteDocumentosServiceRemote tramiteDocumentosService;
	
	private Boolean autorizacion = false; 

	/**
	 * Metodo para iniciar el tramite de baja de derechohabiente por defuncion
	 * 
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/documentoSAV001")
	public String documentoSAV001(HttpSession session,
			HttpServletRequest request, Model model) {

		try {
			AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
			Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
			
//			ByteArrayOutputStream os = (ByteArrayOutputStream) documentosService
//					.getCartillaNacionalSalud(491L,asignacionNSS);
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(491L);;
			int identificadorReporte = 5; //Valor de prueba
			Solicitud solicitud = null;
			
			ByteArrayOutputStream os = (ByteArrayOutputStream) tramiteDocumentosService.generaDocumentoConSelloDigital(asignacionNSS, solicitud, usuario, identificadorReporte, propiedades, null);
			session.setAttribute("jasper", new String(os.toByteArray()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "impresionDocs";
	}

	@RequestMapping(value = "/documentoSAV002")
	public void documentoSAV002(@RequestParam("idTramite") Long idTramite, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		response.addHeader("Set-Cookie", "fileDownload=true;Path=/");
		try {
			//Identificadores para el tipo de tramite, solicitud
			int identificadorReporte = TipoTramiteEnum.REIMPRESION_SAV002.getCodigo(); //Valor de prueba
			Solicitud solicitud = null;
			Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
			identificadoresMap.put("tramite", TipoTramiteEnum.REIMPRESION_SAV002
					.getCodigo());
			identificadoresMap.put("solicitud", TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE
					.getValor());
			
			//La generacion del documento Sav002, se realiza a traves de TramiteDocumentosService
			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(asignacionNSS, solicitud, usuario, identificadorReporte, null, identificadoresMap);

			response.addHeader("Accept-Ranges","bytes");
			response.addHeader("Cache-Control","public");
			response.addHeader("Cache-Control","must-revalidate");
			response.addHeader("Pragma","public");
			response.setContentType("application/pdf");
			response.addHeader("expires","0");
			response.addHeader("Content-disposition", "attachment;filename=\"sav002" +asignacionNSS.getNssStr() + ".pdf\""); 
			response.setContentLength(res.length);
			response.getOutputStream().write(res);
			response.flushBuffer();

		} catch (Exception e) {
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			e.printStackTrace();
		}
		
	}

	
	
	@RequestMapping(value = "/documentoSAV005")
	public String documentoSAV005(HttpSession session,
			HttpServletRequest request, Model model) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);

		List<GrupoFamiliar> candidatos = null;
		List<GrupoFamiliar> candidatosFinales = new ArrayList<GrupoFamiliar>();
		long umfFuncionario = usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().getIdUMF();
		
		try {
			candidatos = documentosService.getGrupoFamiliarSav005(asignacionNSS.getIdAsignacionNSS());
			if(candidatos.size() > 0){
				for(GrupoFamiliar gf : candidatos){
					if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF().longValue() ==  umfFuncionario){
						candidatosFinales.add(gf);
					}
				}
			}
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
			log.debug(e.getMessage());
		}catch (Exception e) {
			request.setAttribute("errores", e.getMessage());
			log.debug(e.getMessage());
		}

		model.addAttribute("candidatos", candidatosFinales);

		return Constants.URL_SAV005;
	}
	
	@RequestMapping(value = "/generaDocumentoSAV005/{idDerechohabiente}")
	public void generaDocumentoSAV005(
			@PathVariable("idDerechohabiente") Long idDerechohabiente,
			HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		
		response.addHeader("Set-Cookie", "fileDownload=true;Path=/");
		
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		try {
			//FirmaElectronica firma = new FirmaElectronica();
			FirmaElectronica firma = null;
			byte[] res = (byte[]) documentosService
					.getDocumentoSav005(asignacionNSS, firma, idDerechohabiente, EstadoTramiteEnum.CERRADO.getId(), OrigenSolicitudEnum.VENTANILLA.getId(), usuario);

			response.setContentType("application/pdf");
			response.setContentLength(res.length);
			response.setHeader("Content-Disposition", "attachment;filename = sav005.pdf");

			response.getOutputStream().write(res);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (DerechohabientesBusinessException e) {
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			log.debug(e.getMessage());
		} catch (Exception e) {
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			log.debug(e.getMessage());
		}

		
	}

	@RequestMapping(value = "/documentoSAV006")
	public String documentoSAV006(HttpSession session,
			HttpServletRequest request, Model model) {

		
		return "grupoFamiliar";
	}

	@RequestMapping(value = "/documentoSAV007")
	public String documentoSAV007(HttpSession session,
			HttpServletRequest request, Model model) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");

		List<GrupoFamiliar> candidatos = null;

		
		try {
			candidatos = documentosService.getGrupoFamiliarSav007(asignacionNSS.getIdAsignacionNSS());

		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
			log.debug(e.getMessage());
		}catch (Exception e) {
			request.setAttribute("errores", e.getMessage());
			log.debug(e.getMessage());
		}

		model.addAttribute("candidatos", candidatos);

		return Constants.URL_SAV007;
	}

	@RequestMapping(value = "/generaDocumentoSAV007/{idDerechohabiente}")
	public void generaDocumentoSAV007(
			@PathVariable("idDerechohabiente") Long idDerechohabiente,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		
		response.addHeader("Set-Cookie", "fileDownload=true;Path=/");
		try {
			
			//FirmaElectronica firma = new FirmaElectronica();
			FirmaElectronica firma = null;
			byte[] res = (byte[]) documentosService.getDocumentoSav007(asignacionNSS, firma, idDerechohabiente);
			
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename = sav007.pdf");
			response.getOutputStream().write(res);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			log.debug(e.getMessage());
		}
		
	}

	@RequestMapping(value = "/documentoSAV010")
	public void documentoSAV010(HttpSession session,
			HttpServletRequest request, Model model,
			HttpServletResponse response) {

		response.addHeader("Set-Cookie", "fileDownload=true;Path=/");
		try {
			byte[] res = (byte[]) documentosService.getDocumentoSav010(1L);
			
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename = sav010.pdf");
			response.getOutputStream().write(res);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			log.debug(e.getMessage());
		}

		
	}

	@RequestMapping(value = "/generaDocumentoSAV017/{idPersona}")
	public void generaDocumentoSAV017(
			@PathVariable("idPersona") Long idPersona,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		response.addHeader("Set-Cookie", "fileDownload=true;Path=/");	
		try {
			byte[] res = (byte[]) documentosService.getDocumentoSav017(idPersona , asignacionNSS, null, autorizacion, null, OrigenSolicitudEnum.VENTANILLA.getId());
			if(res != null){
				
				
				response.setContentType("application/pdf");
				response.setContentLength(res.length);
				response.setHeader("Content-Disposition", "attachment;filename = sav017.pdf");
				response.getOutputStream().write(res);
				
			}else{
				response.getOutputStream().write("No hay datos que mosttrar".getBytes());
				
			}
			
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			log.debug(e.getMessage());
		}
		
	}

	
	
	@RequestMapping(value = "/documentoSAV017A")
	public String documentoSAV017A(
			HttpSession session,
			HttpServletRequest request, Model model) {

	
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		List<GrupoFamiliar> candidatos = null;

		
		try {
				candidatos = correccion.findGrupoFamiliarSuspencionCircunscripcion( asignacionNSS , true, usuario);
				autorizacion = true;

		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
			log.debug(e.getMessage());
		}

		model.addAttribute("candidatos", candidatos);

		return Constants.URL_SAV017A;
	}
	
	@RequestMapping(value = "/documentoSAV017S")
	public String documentoSAV017S(
			HttpSession session,
			HttpServletRequest request, Model model) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		List<GrupoFamiliar> candidatos = null;

		
		try {
				candidatos = correccion.findGrupoFamiliarSuspencionCircunscripcion( asignacionNSS , false, usuario);
				autorizacion = false;

		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
			log.debug(e.getMessage());
		}

		model.addAttribute("candidatos", candidatos);

		return Constants.URL_SAV017S;
	}
	
	
	@RequestMapping(value = "/cartillaNacionalSalud")
	public String cartillaNacionalSalud(HttpSession session,
			HttpServletRequest request, Model model) {


		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);

		List<GrupoFamiliar> candidatos = null;
		List<GrupoFamiliar> candidatosFinales = new ArrayList<GrupoFamiliar>();
		List<Long> idEstados =new ArrayList<Long>();
		idEstados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
		idEstados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
		idEstados.add(EstadoDerechohabienteEnum.PENSION_TRAMITE.getId());
		idEstados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
		idEstados.add(EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId());
		
		long umfFuncionario = usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().getIdUMF();

		try {
			candidatos = grupoFamiliarServiceRemote.findGrupoFamiliarPorEstados(asignacionNSS.getIdAsignacionNSS(), idEstados);
			
			if(candidatos.size() > 0){
				for(GrupoFamiliar gf : candidatos){
					if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF().longValue() ==  umfFuncionario){
						candidatosFinales.add(gf);
					}
				}
			}
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getMessage());
			log.debug(e.getMessage());
		}catch (Exception e) {
			request.setAttribute("errores", e.getMessage());
			log.debug(e.getMessage());
		}

		
		
		if(candidatosFinales != null && candidatosFinales.size() > 0){
			model.addAttribute("candidatos", candidatosFinales);
		}else{
			request.setAttribute("errores", "No hay informaci\u00F3n para mostrar");
		}

		return Constants.URL_CATILLA_NACIONAL_SALUD;

	}

	@RequestMapping(value = "/documento4305A")
	public String documento4305A(HttpSession session,
			HttpServletRequest request, Model model) {

		return "grupoFamiliar";
	}

	/**
	 * Genera la cartilla Nacional de Salud
	 * 
	 * @param derechohabiente
	 * @param tipo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/generarCartillaNacionalSalud/{idDerechohabiente}")
	public void generarCartillaNacionalSalud(
			@PathVariable("idDerechohabiente") Long idDerechohabiente,
			Model model, HttpServletRequest request,
			HttpServletResponse response,HttpSession session) {

		response.setHeader("Set-Cookie", "fileDownload=true;Path=/");
		try {
			AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
			Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(idDerechohabiente);
			Solicitud solicitud = null;
			int identificadorReporte = TipoTramiteEnum.CARTILLA_NACIONAL_DE_SALUD.getCodigo();
			
			Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
			identificadoresMap.put("tramite", TipoTramiteEnum.CARTILLA_NACIONAL_DE_SALUD
					.getCodigo());
			identificadoresMap.put("solicitud", TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE
					.getValor());
			
			
			CabezaGrupoFamiliar miCabezaGF = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
			if( miCabezaGF != null ){
				asignacionNSS.setEstudiante(miCabezaGF.getEsEstudiante());
					
			}
			
			
			byte[] res = (byte[])tramiteDocumentosService.generaDocumentoConSelloDigital(asignacionNSS, solicitud, usuario, identificadorReporte, propiedades, identificadoresMap);
			
//			byte[] res = (byte[]) documentosService
//					.getCartillaNacionalSalud(idDerechohabiente,asignacionNSS, firma);

			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition","attachment;filename = catillaNacional.pdf");
			response.getOutputStream().write(res);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}

		
	}
}