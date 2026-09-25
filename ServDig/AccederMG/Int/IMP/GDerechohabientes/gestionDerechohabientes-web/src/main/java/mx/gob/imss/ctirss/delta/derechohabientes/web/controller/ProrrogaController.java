/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ProrrogaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.DateUtils;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Caracter;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.ProrrogasDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RechazoDto;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileUploadVB;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Obstetrico;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.VigenciaTemporal;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Juan Manuel Marquez
 * 
 */
@Controller
@RequestMapping(value = "/prorroga/*")
public class ProrrogaController extends AbstractController {

	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	@Autowired
	private ProrrogaServiceRemote prorrogaService;
	@Autowired
	private UmfServiceRemote umfService;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private SolicitudServiceRemote solicitudServiceRemote;
	@Autowired
	private GuardaDocumentosAsincrono guardaDocumentosAsincrono;
	
	private static final String SESSION_BEAN=FileUploadVB.SES_NAME;
	
	String errorInesperado = "internalError";
	
	@RequestMapping(value = "/vigenciaPermanente")
	public String getProrrogaPorVigenciaPermanente(Model model,
			HttpServletRequest request, HttpSession session) {
		
		String vista = "consultaProrrogas";
		request = validaRNGD0227(TipoTramiteEnum.PRORROGA_POR_VIGENCIA_PERMANENTE.getCodigo().longValue(), session, request, model); 

		return vista;
	}
	
	@RequestMapping(value = "/acuerdos")
	public String getProrrogaPorAcuerdos(Model model,
			HttpServletRequest request, HttpSession session) {
		
		String vista = "consultaProrrogas";
		request = validaRNGD0227(TipoTramiteEnum.PRORROGA_POR_ACUERDOS_HCCD_HCT.getCodigo().longValue(), session, request, model);
			
		return vista;
	}
	
	@RequestMapping(value = "/obstetricos")
	public String getProrrogaPorObstetricos(Model model,
			HttpServletRequest request, HttpSession session) {
		String vista = "registroProrrogas";
		
		request = validaRNGD0227(TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo().longValue(),session,request, model);

		return vista;
	}
	
	@RequestMapping(value = "/enfermedad")
	public String getProrrogaPorEnfermedad(Model model,
			HttpServletRequest request, HttpSession session) {
		
		String vista = "consultaProrrogas";

		request = validaRNGD0227(TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA.getCodigo().longValue(), session,request, model);

		return vista;
	}
	
	
	@RequestMapping(value = "/estudios")
	public String getProrrogaPorEstudios(Model model,
			HttpServletRequest request, HttpSession session) {

		String vista = "consultaProrrogas";

		request = validaRNGD0227(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo().longValue(), session,request, model);

		return vista;
	}

	@RequestMapping(value = "/laudo")
	public String getProrrogaPorLaudo(Model model, HttpServletRequest request,
			HttpSession session) {
		
		String vista = "consultaProrrogas";
		
		request = validaRNGD0227(TipoTramiteEnum.PRORROGA_POR_LAUDO.getCodigo().longValue(), session,request, model);

		return vista;
	}

	@RequestMapping(value = "/vigenciaTemporal")
	public String getProrrogaPorVigenciaTemporal(Model model,
			HttpServletRequest request, HttpSession session) {
		
		String vista = "registroProrrogas";

		request = validaRNGD0227(TipoTramiteEnum.PRORROGA_POR_VIGENCIA_TEMPORAL.getCodigo().longValue(), session,request, model);

		return vista;
	}	

	
	@RequestMapping(value = "/beneficiario/prorrogas/{idPersona}/{tipoTramite}")
	public String getBeneficiarioProrrogas(
			@PathVariable("idPersona") Long idPersona,
			@PathVariable("tipoTramite") Long tipoTramite,Model model,
			HttpServletRequest request, HttpSession session) {
		ProrrogasDto prorrogaDto = new ProrrogasDto();
		prorrogaDto.setProrroga(new TramiteProrroga());
		
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		Long UMF = usuario.getIdUmf();
		

		try {
			integrante = prorrogaService.getIntegranteProrroga(
					tipoTramite,
					idPersona, UMF, asignacionNSS, false);

		}catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getSituacion());
			request.setAttribute("error", e.getMessage());
			return "consultaProrrogas";
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("exception", "exception.general");
			request.setAttribute("error", e.getCause());
			return errorInesperado;
		}
		
		if(tipoTramite.longValue() == TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA.getCodigo().longValue()){
			
			try{
				long edad = 0;
			
				
				if( integrante.getDerechohabiente().getFechaNacimiento() != null  ){
					edad = DateUtils.getEdad(integrante.getDerechohabiente().getFechaNacimiento());
				}else{
					
					// -------------------------------------------------------------------
					// Tomará el primer día del siguiente mes como la fecha de nacimiento
					// -------------------------------------------------------------------
					edad = DateUtils.getEdadSinDia(integrante.getDerechohabiente().getMesRegistroNac(),
							integrante.getDerechohabiente().getAnioRegistroNac());
					
				}
			
			
				if (edad >= 25) {
					session.setAttribute("documentoP",
						mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA_25
								.getCodigo());
				} else {
					session.setAttribute("documentoP",
							mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA
									.getCodigo());
				}
			
			}catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("exception", "exception.general");
				request.setAttribute("error", e.getCause());
				return errorInesperado;
			}
			
			
		}
		
		session.setAttribute("hijo", integrante);
		session.setAttribute("modo", "registro");
		session.setAttribute("tipoTramite", tipoTramite);
		if(tipoTramite.longValue() == TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo().longValue()){			
			session.setAttribute("documentos", 2L);
		}
		model.addAttribute("datos", prorrogaDto);
		model.addAttribute("fechaDeHoy", new Date());
		
		return "registroProrrogas";
	}
	
	@RequestMapping(value = "/guardarProrrogas")
	public String saveProrrogas(@ModelAttribute("datos") ProrrogasDto prDto, HttpServletRequest request, HttpSession session) {
		
		String vista = "finalizacionTramite";
		String descripcionP = "";
		String observaciones = "";
		
		try{
			log.debug("la prorroga dto es " + prDto.toString()); 
			observaciones = prDto.getProrroga().getTramite().getObservacion();
		}catch(Exception e){
			log.error("corurrio un error al recuperar la observacion" , e);
		}
		
		Solicitud solicitudProrroga = null;
		Tramite tramite = null;
		String fechaInicioS = DateUtils.dateToStringConFormato(prDto.getProrroga().getFechaInicioProrroga(), "dd/MM/yyyy");
		String fechaFinS = DateUtils.dateToStringConFormato(prDto.getProrroga().getFechaFinProrroga(), "dd/MM/yyyy");
		boolean pendAut = false;
		boolean rechazo = false;
		
		
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		Long tipoTramite = (Long) session.getAttribute("tipoTramite");
		
		
		
		log.debug("la fecha de fin es [" + fechaFinS +"]");
		
		// Se obtiene el documento probatorio de la session
		FileUploadVB sessionBean = (FileUploadVB) session
				.getAttribute(FileUploadVB.SES_NAME);
		
		try {
			if(tipoTramite.longValue() == TipoTramiteEnum.PRORROGA_POR_VIGENCIA_PERMANENTE.getCodigo().longValue()){
				solicitudProrroga = guardaVigenciaPermanente(sessionBean, observaciones, tramite, session);
				descripcionP ="prórroga por vigencia permanente";
				pendAut = false;
			}
			if(tipoTramite.longValue() == TipoTramiteEnum.PRORROGA_POR_ACUERDOS_HCCD_HCT.getCodigo().longValue()){
				solicitudProrroga = guardaAcuerdos(sessionBean,fechaInicioS, fechaFinS, prDto.getProrroga().getCaracter().getIdCaracter().toString(), observaciones, tramite, session);
				descripcionP ="prórroga por acuerdo";
				pendAut = false;
			}
			if(tipoTramite.longValue() == TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo().longValue()){
				solicitudProrroga = guardaObtetricos(sessionBean,fechaInicioS, fechaFinS, prDto.getProrroga().getCaracter().getIdCaracter().toString(), observaciones, tramite, session);
				descripcionP ="prórroga por obstetricos";
				pendAut = false;
			}
			if(tipoTramite.longValue() == TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA.getCodigo().longValue()){
				Boolean mayor25 = session.getAttribute("documentoP").equals(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA_25.getCodigo()) ? true : false;
				solicitudProrroga = guardaEnfermedad(sessionBean, fechaInicioS,fechaFinS, prDto.getProrroga().getCaracter().getIdCaracter().toString(), observaciones, tramite,mayor25, session);
				descripcionP ="prórroga por enfermedad";
				pendAut = false;
			}
			if(tipoTramite.longValue() == TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo().longValue()){
				solicitudProrroga = guardaEstudios(sessionBean,fechaInicioS, fechaFinS, prDto.getProrroga().getCaracter().getIdCaracter().toString(), observaciones, tramite, session);
				descripcionP ="prórroga por estudios";
				pendAut = false;
			}
			if(tipoTramite.longValue() == TipoTramiteEnum.PRORROGA_POR_LAUDO.getCodigo().longValue()){
				solicitudProrroga = guardaLaudo(sessionBean,fechaInicioS, fechaFinS, prDto.getProrroga().getCaracter().getIdCaracter().toString(),observaciones, tramite, session);
				descripcionP ="prórroga por laudo";
				pendAut = false;
			}
			if(tipoTramite.longValue() == TipoTramiteEnum.PRORROGA_POR_VIGENCIA_TEMPORAL.getCodigo().longValue()){
				solicitudProrroga = guardaVigenciaTemporal(sessionBean,fechaInicioS, fechaFinS, prDto.getProrroga().getCaracter().getIdCaracter().toString(), observaciones, tramite, session);
				descripcionP ="prórroga por vigencia temporal";
				pendAut = false;
			}
			
			if(solicitudProrroga != null) {
				tramite = solicitudProrroga.getTramites().get(0);
			}
			
			
			
		} catch (DerechohabientesBusinessException e) {
			
			e.printStackTrace();
			vista = "internalError";
			request.setAttribute("exception", e.getMessage());
			return vista;
			
		}catch( DocumentoException e ){
			
			// -------------------------------------------------------------
			// Error al generar firma electrónica
			// -------------------------------------------------------------
			vista = "internalError";
			request.setAttribute("exception", e.getMessage());
			return vista;
			
			
		}catch( SolicitudNoValidaException e){
			
			// -------------------------------------------------------------
			// Error al persistir la solicitud
			// Error al generar los documentos resultantes
			// -------------------------------------------------------------
			vista = "internalError";
			request.setAttribute("exception", e.getMessage());
			return vista;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error desconocido", e);
			request.setAttribute("error", e);
			vista = "internalError";
			request.setAttribute("exception", "exception.general");
			return vista;
		}
			
		((FileUploadVB)session.getAttribute(FileUploadVB.SES_NAME)).setIdTramite(new Long(tramite.getTramiteId()));
		session.setAttribute("idPersona", integrante.getDerechohabiente().getIdPersona());
		try {
			salvaDocumentos(session);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			vista = "internalError";
			request.setAttribute("exception", e.getMessage());
			return vista;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error desconocido", e);
			//request.setAttribute("error", e);
			//vista = "internalError";
			//request.setAttribute("exception", "exception.general");
			//return vista;
		}
		try{
			
			// ----------------------------------------------------------
			// Si se genero la solicitud
			// ----------------------------------------------------------
			if(solicitudProrroga != null) {
				
				// -----------------------------------------------------
				// Asigna al tramite los documentos que se generaran
				// -----------------------------------------------------
				log.debug("=========== CONSULTAR SOLICITUD PRORROGA =================================");
				solicitudProrroga = solicitudBusinessRemote.consultar(solicitudProrroga);
				
				// ------------------------------------------------------
				// Genera los documentos resultantes
				// ------------------------------------------------------
				log.debug("=========== GUARDAR DOCUMENTOS RESULTANTES PRORROGA =========================");
				solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitudProrroga);
				
			}
			
		}catch(Exception e){
			log.error("No fue posible guardar los documentos resultantes", e);
		}
		request.setAttribute("reporte", cargaReporte(tramite.getTramiteId(), tipoTramite, pendAut, rechazo,descripcionP, session));
		request.setAttribute("solicitud", solicitudProrroga);
		
		return vista;	
		
	}
	
	@RequestMapping(value = "/getFechasInicioFinProrroga", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getDocumentoProrroga(@RequestBody TipoTramite tipoTramite,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		FileUploadVB sessionBean = (FileUploadVB) session.getAttribute(FileUploadVB.SES_NAME);
		Date fechaInicio = null;
		Date fechaFin = null;
		
		
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		
		
		try {
			if(tipoTramite.getIdTipoTramite() != null){
				if(tipoTramite.getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo())) {
					ConstanciaEstudio prorroga  = (ConstanciaEstudio) ((sessionBean.getDocumenProbatorioCapturaList().get(0)).getCaptura());
					
					if(prorroga != null) {
						fechaInicio = prorroga.getFechaInicioPeriodo();
						fechaFin = prorroga.getFechaFinPeriodo();
					}
				} else if(tipoTramite.getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo())) {
					Obstetrico obstetricoDocto = (Obstetrico) ((sessionBean.getDocumenProbatorioCapturaList().get(0)).getCaptura());
					
					if(obstetricoDocto!=null) {
						if(cabeza.getFechaFinVigencia() != null) {
							fechaInicio = DateUtils.sumarDiasFecha(cabeza.getFechaFinVigencia(), 1);
						}
						fechaFin = DateUtils.sumarDiasFecha(obstetricoDocto.getFechaParto(), 60);
					}
				}
			}
		} catch(Exception e) {
			log.error("Ocurrio un error al intentar obtener la constancia de estudios");
		}
		
		result.put("fechaInicio", fechaInicio);
		result.put("fechaFin", fechaFin);
		
		return result;
	}
	

	
	private Solicitud guardaEstudios(FileUploadVB sessionBean, String fechaInicio, String fechaFin, String caracter, String observaciones, Tramite tramite, HttpSession session) 
			throws DerechohabientesBusinessException, Exception{
	
		
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		

		ConstanciaEstudio prorroga = (ConstanciaEstudio) ((sessionBean
		.getDocumenProbatorioCapturaList().get(0)).getCaptura());

		prorroga.setFechaInicioPeriodo(prorroga.getFechaInicioPeriodo());
		prorroga.setFechaFinPeriodo(prorroga.getFechaFinPeriodo());
		prorroga.setObservaciones(observaciones.toUpperCase());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(prorroga.getFechaInicioPeriodo());
		int anioInicio = calendar.get(Calendar.YEAR);
		calendar.setTime(prorroga.getFechaFinPeriodo());
		int anioFin = calendar.get(Calendar.YEAR);
		
		if(anioFin > (anioInicio+1)) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			DerechohabientesBusinessException.throwException("La fecha de fin de periodo capturada " + formatter.format(prorroga.getFechaFinPeriodo()) + " no es v&aacute;lida.");
		}
		
		Solicitud solicitudProrroga = prorrogaService.saveProrrogaEstudios(prorroga,
				integrante, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
		
		return solicitudProrroga;
	}
	
	
	
	private Solicitud guardaLaudo(FileUploadVB sessionBean, String fechaInicio, String fechaFin, String caracter, String observaciones, Tramite tramite, HttpSession session) 
			throws DerechohabientesBusinessException, Exception{
		
		
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		

		
		Solicitud solicitud = null;
		Acuerdo acuerdo = new Acuerdo();
		Acuerdo acuerdoDocto = (Acuerdo) ((sessionBean
				.getDocumenProbatorioCapturaList().get(0)).getCaptura());

		Date fechaInicioD = DateUtils.stringToDate("dd/MM/yyyy",
				fechaInicio);
		Date fechaFinD = DateUtils.stringToDate("dd/MM/yyyy", fechaFin);

		// new ProrrogaEstudiosValidator().validate(prorroga, result);
		if (acuerdo.getProrroga() == null) {
			acuerdo.setProrroga(new TramiteProrroga());
		}

		acuerdo.getProrroga().setFechaInicioProrroga(fechaInicioD);
		acuerdo.getProrroga().setFechaFinProrroga(fechaFinD);
		acuerdo.getProrroga().setObservacion(observaciones.toUpperCase());
		acuerdo.setObservaciones(observaciones.toUpperCase());
		
		acuerdo.setFechaExpedicion(acuerdoDocto.getFechaExpedicion());
		Caracter c = new Caracter();
		c.setIdCaracter(new Long(caracter));
		acuerdo.getProrroga().setCaracter(c);

	
		solicitud = prorrogaService.saveProrrogaLaudos(acuerdo,
					integrante, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
		return solicitud;
	}
	
	
	private Solicitud guardaObtetricos(FileUploadVB sessionBean, String fechaInicio, String fechaFin, String caracter, String observaciones, Tramite tramite, HttpSession session) 
			throws DerechohabientesBusinessException, Exception{

		
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		

		
		Solicitud solicitud = null;
//		Obstetrico obstetrico =  new Obstetrico();
		Obstetrico obstetricoDocto = (Obstetrico) ((sessionBean
				.getDocumenProbatorioCapturaList().get(0)).getCaptura());
		if (obstetricoDocto.getProrroga() == null) {
			obstetricoDocto.setProrroga(new TramiteProrroga());
		}
		
		
		
		
		if(cabeza.getFechaFinVigencia() != null ){
			
			obstetricoDocto.getProrroga().setFechaFinProrroga(DateUtils.sumarDiasFecha(obstetricoDocto.getFechaParto(), 60));
//			obstetrico.getProrroga().setFechaInicioProrroga(ObstetricoDocto.getFechaProbableConcepcion());
			obstetricoDocto.getProrroga().setFechaInicioProrroga(DateUtils.sumarDiasFecha(cabeza.getFechaFinVigencia(), 1));
			obstetricoDocto.getProrroga().setObservacion(observaciones.toUpperCase());
			obstetricoDocto.setObservaciones(observaciones.toUpperCase());
			
//				if( cabeza.getFechaFinVigencia().before(ObstetricoDocto.getFechaProbableConcepcion())	){			
//					throw new DerechohabientesBusinessException("No es posible otorgar la pr\u00F3rroga debido a que la fecha probable de concepci\u00F3n no se encuentra dentro del periodo de aseguramiento.","exception.obstetricos.msg03");
//				}
				
//				if(cabeza.getFechaFinVigencia().before(ObstetricoDocto.getFechaExpedicion())){
//					throw new DerechohabientesBusinessException("No es posible otorgar la pr\u00F3rroga debido a que la fecha de expedici\u00F3n no se encuentra dentro del periodo de aseguramiento.","exception.obstetricos.msg04");
//				}
		
				
				solicitud = prorrogaService.saveProrrogaServiciosObstetricos(obstetricoDocto,integrante, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
		}
		return solicitud;
	}
	
	
	
	private Solicitud guardaVigenciaTemporal(FileUploadVB sessionBean, String fechaInicio, String fechaFin, String caracter, String observaciones, Tramite tramite, HttpSession session) 
			throws DerechohabientesBusinessException, Exception{

		
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		

		Solicitud solicitud = null;
		
		VigenciaTemporal prorroga =  new VigenciaTemporal();
		VigenciaTemporal vigenciaDocto = (VigenciaTemporal) ((sessionBean
				.getDocumenProbatorioCapturaList().get(0)).getCaptura());
		
		prorroga.setObservaciones(observaciones.toUpperCase());
		prorroga.setFechaExpedicion(vigenciaDocto.getFechaExpedicion());
		
		solicitud = prorrogaService.saveProrrogaVigenciaTemporal(
				prorroga, integrante, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
		
		return solicitud;
	}
	
	
	
	private Solicitud guardaEnfermedad(FileUploadVB sessionBean, String fechaInicio, String fechaFin, String caracter, String observaciones, Tramite tramite, boolean mayor25, HttpSession session) 
			throws DerechohabientesBusinessException, Exception{

		
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		

		
		Solicitud solicitud = null;
		DictamenIntegranteIncapacitado dictamen = new DictamenIntegranteIncapacitado();
		
		Date fechaIni = null;
		Date fechaFinal = null;
		Boolean existeIncapacidad = false;
		
		//String tipoDoc = (String) session.getAttribute("documentoP");
			
		if (mayor25) {
			dictamen = (DictamenIntegranteIncapacitado) ((sessionBean
					.getDocumenProbatorioCapturaList().get(0)).getCaptura());

			//fechaIni = dictamen.getFechaInicioEnfermedad();
			
			existeIncapacidad = dictamen.getExisteEstadoIncapacidad().equals("0") ? false : true;


		} else {

			existeIncapacidad =  true;
		}

		
		
		if (dictamen.getProrroga() == null) {
			dictamen.setProrroga(new TramiteProrroga());
		}
		
		
		if(StringUtils.isNotBlank(fechaInicio)){
			fechaIni = DateUtils.stringToDate("dd/MM/yyyy", fechaInicio);
		}
		
		if(StringUtils.isNotBlank(fechaInicio)){
			fechaFinal = DateUtils.stringToDate("dd/MM/yyyy", fechaFin); 
		}

		
		
		
		
		
		dictamen.getProrroga().setFechaFinProrroga(fechaFinal);
		dictamen.getProrroga().setFechaInicioProrroga(fechaIni);
		dictamen.getProrroga().setObservacion(observaciones);
		dictamen.setObservaciones(observaciones.toUpperCase());
		dictamen.setFechaInicioEnfermedad(fechaIni);
		Caracter c = new Caracter();
		c.setIdCaracter(new Long(caracter));
		dictamen.getProrroga().setCaracter(c);
		
		if(!existeIncapacidad){			
			throw new DerechohabientesBusinessException("No es posible otorgar la pr\u00F3rroga debido a que no existe estado de incapacidad.", "No es posible otorgar la pr\u00F3rroga debido a que no existe estado de incapacidad.");			
		}else{			
			solicitud = prorrogaService.saveProrrogaEnfermedad(dictamen,integrante, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
		}
		
		return solicitud;
	}
		
	
	
	
	private Solicitud guardaAcuerdos(FileUploadVB sessionBean, String fechaInicio, String fechaFin, String caracter, String observaciones, Tramite tramite, HttpSession session) 
			throws DerechohabientesBusinessException, Exception{

		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		
		Acuerdo acuerdo = new Acuerdo();
		Acuerdo acuerdoDocto = (Acuerdo) ((sessionBean
				.getDocumenProbatorioCapturaList().get(0)).getCaptura());

		Solicitud solicitud = null;
		
		Date fechaInicioD = DateUtils.stringToDate("dd/MM/yyyy",
				fechaInicio);
		Date fechaFinD = DateUtils.stringToDate("dd/MM/yyyy", fechaFin);
		// Date fechaExpedicion=DateUtils.stringToDate("dd/MM/yyyy", );

		if (acuerdo.getProrroga() == null) {
			acuerdo.setProrroga(new TramiteProrroga());
		}
		
		acuerdo.getProrroga().setFechaFinProrroga(fechaFinD);
		acuerdo.getProrroga().setFechaInicioProrroga(fechaInicioD);
		acuerdo.setFechaExpedicion(acuerdoDocto.getFechaExpedicion());
		acuerdo.getProrroga().setObservacion(observaciones);
		acuerdo.setObservaciones(observaciones.toUpperCase());
		
		Caracter c = new Caracter();
		c.setIdCaracter(new Long(caracter));
		acuerdo.getProrroga().setCaracter(c);

		solicitud = prorrogaService.saveProrrogaAcuerdos(acuerdo,
				integrante, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
		
		return solicitud;
	}
	
	
	
	private Solicitud guardaVigenciaPermanente(FileUploadVB sessionBean, String observaciones, Tramite tramite, HttpSession session) 
			throws DerechohabientesBusinessException, Exception{
		
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		
		Solicitud solicitud = null;
		
		Acta acta  = new Acta();
		Acta actaDocto = (Acta) ((sessionBean
				.getDocumenProbatorioCapturaList().get(0)).getCaptura());
		
		acta.setFechaSuceso(actaDocto.getFechaSuceso());
		acta.setFechaExpedicion(actaDocto.getFechaExpedicion());
		acta.setObservaciones(observaciones.toUpperCase());
	
		if( cabeza.getFechaFinVigencia() != null && 
				cabeza.getFechaFinVigencia().before(acta.getFechaSuceso())){
			new DerechohabientesBusinessException("exception", "exception.prorroga");
		}else{
				solicitud = prorrogaService
							.saveProrrogaVigenciaPermanente(acta, integrante, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
		}
			return solicitud;
	}
	
	private void salvaDocumentos(HttpSession ses ) throws Exception{
		FileUploadVB cargadorDocumentosVB=(FileUploadVB) ses.getAttribute(SESSION_BEAN);
		Long idPersona = (Long) ses.getAttribute("idPersona");
		
		guardaDocumentosAsincrono.salvaDocumentosProbatoriosSincrono(ses, cargadorDocumentosVB.getIdTramite(), idPersona);
		
	}

	private ImpresionReporteDto cargaReporte(Long idTramite, Long tipoTramite, boolean pendienteAut, boolean rechazado, String tramiteDesc, HttpSession session){
		
		
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		
		ImpresionReporteDto reporte = new ImpresionReporteDto();
		reporte.setIdPersona(integrante.getDerechohabiente().getIdPersona());
		reporte.setIdTramite(idTramite);
		reporte.setRechazado(rechazado);
		reporte.setPendienteAut(pendienteAut);
		if(pendienteAut)
			reporte.setMensaje("Los cambios se verón reflejados una vez que el trómite sea autorizado");
		TipoTramite tipoTram = new TipoTramite();
		tipoTram.setIdTipoTramite(tipoTramite.intValue());
		tipoTram.setDescripcion(tramiteDesc);
		reporte.setTipoTramite(tipoTram);
		return reporte;
	}
	
	
	private void colocarDatosReporte(HttpSession session, Long numDocumentos,
			Tramite tramite) {
		session.setAttribute("documentos", numDocumentos);
		session.setAttribute("idPersona", tramite.getPersona().getIdPersona());
		session.setAttribute("idTramite", tramite.getTramiteId());
//		session.setAttribute("idTipoTramite", tramite.getTipoTramite()
//				.getIdTipoTramite());

	}

	/**
	 * Valida las relas de negocio RNGD0227
	 * 
	 * @param tipoProrroga
	 * @return
	 */
	private HttpServletRequest validaRNGD0227(Long tipoProrroga, HttpSession session, HttpServletRequest request, Model model) {
		
		/*
		 * Valida la regla de negocio [RNGD0227 Modalidades bajo las cuales se
		 * puede solicitar una prórroga de servicios medicos] [RNGD0138
		 * Modalidades que permiten el registro de derechohabientes hijos] 10,
		 * 13, 14, 30, 34, 35, 36, 38, 42, 43, 44, Pensión(27)
		 */
		
		
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		GrupoFamiliar grupoFamiliar = (GrupoFamiliar) session.getAttribute("miGrupoFamiliar");
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute("hijo");
		
		
		List<GrupoFamiliar> integrantes = null;

			log.debug("La modalidad permite el registro de la prorroga, busco a los candidatos, cabeza: " + cabeza.getAsignacionNSS());
			try {
				integrantes = prorrogaService.getCandidatosProrroga(cabeza,
						grupoFamiliar,tipoProrroga,cabeza.getAsignacionNSS(), usuario, true);
				
				if(tipoProrroga.longValue() == TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo().longValue() ||
						tipoProrroga.longValue() == TipoTramiteEnum.PRORROGA_POR_VIGENCIA_TEMPORAL.getCodigo().longValue()){
					ProrrogasDto prorrogaDto = new ProrrogasDto();
					prorrogaDto.setProrroga(new TramiteProrroga());
					
					integrante = null;
					
					if(integrantes != null && integrantes.size() > 0){
						integrante = integrantes.get(0);
					}
					
					session.setAttribute("hijo", integrante);
					session.setAttribute("modo", "registro");										
					if(tipoProrroga.longValue() == TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo().longValue()){						
						prorrogaDto.setDocumentos(2L);
						session.setAttribute("idPersona", integrante.getDerechohabiente().getIdPersona());
					}	
					model.addAttribute("datos", prorrogaDto);
				}else{
					request.setAttribute("hijos", integrantes);					
				}
				session.setAttribute("tipoTramite", tipoProrroga);
								
			} catch (DerechohabientesBusinessException e) {
				e.printStackTrace();
				request.setAttribute("errores", e.getSituacion());
				request.setAttribute("error", e.getMessage());
				session.setAttribute("tipoTramite", tipoProrroga);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errores", "exception.general");
				request.setAttribute("error", e.getCause());
				session.setAttribute("tipoTramite", tipoProrroga);
			}

		return request;
	}

	
	
	@RequestMapping(value = "/getProrroga/{idSolicitud}")
	public String getProrroga(
			@PathVariable("idSolicitud") Long idSolicitud, Model model,
			HttpServletRequest request, HttpSession session) {
		
		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;
		GrupoFamiliar integrante = null;
		GrupoFamiliar miGrupoFamiliar = null;
		AsignacionNSS asignacion = null;
		List<SujetoObligado> patrones = null;
		TramiteProrroga prorroga =  null;
		String conDetalleS = Constants.SIN_DETALLE_SITUACION;
		session.setAttribute("conDetalleS", conDetalleS);
		ProrrogasDto prorrogasDto = new ProrrogasDto();
		prorrogasDto.setRechazo(new RechazoDto());
		//inicializaVariables(session);
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		String view = "";
		
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		Long UMF = usuario.getIdUmf();
		
		
		try{
			//ser recupera a la cabeza del grupo familiar para poder recuperar la info del tramite
			
			asignacion = solicitudServiceRemote.getAsignacionByIdSolicitud(idSolicitud);
			solicitud =	solicitudBusinessRemote.consultar(solicitud);
		
			Tramite tramite = solicitud.getTramites().get(0);
			if(tramite instanceof TramiteProrroga) {
				prorroga = (TramiteProrroga) tramite;
				log.debug("entre a parsear la prorroga" + prorroga.toString());
			} else {
				DerechohabientesBusinessException.throwException("Inconsistencia en los datos de la solicitud");
			}
			
	
			//Obtener se session la Asignacion Nss
			//Tramite miTramite = solicitudService.getTramiteAutorizar(idTramite);
			//Solicitud sol = this.obtenerSolicitud(idTramite);
			//Solicitud sol = solicitudServiceRemote.consultar(solicitudBaja)
			
			
			
			
			
			cabezaGrupoFamiliar = grupoFamiliarService.cabezaGrupoFamiliar(asignacion.getIdAsignacionNSS());
			session.setAttribute("cabezaGrupoFamiliar", cabezaGrupoFamiliar);
			patrones  = grupoFamiliarService.getPatronesAsegurado(asignacion);
			session.setAttribute("patrones", patrones);
				
			miGrupoFamiliar = grupoFamiliarService.getIntegranteGrupoFamiliarByEstados(asignacion.getIdPersona(), null, asignacion.getIdAsignacionNSS());
			
			//ya debe de venir seteado el tramite de prorroga
			//prorroga = solicitudService.recuperaProrrogaXML(idTramite);
			
			integrante = prorrogaService.getIntegranteProrroga(prorroga.getTipoTramite().getIdTipoTramite().longValue(), prorroga.getPersona().getIdPersona(), UMF,  asignacion, true);
			
			if(prorroga.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_ACUERDOS_HCCD_HCT.getCodigo()))  {
				this.log.debug("entre a prorroga por acuerdo");
				session.setAttribute("tipoTramite", TipoTramiteEnum.PRORROGA_POR_ACUERDOS_HCCD_HCT.getCodigo().longValue());
				prorrogasDto.setDocumentos(1L);				
				prorrogasDto.setProrroga(prorroga);
				model.addAttribute("datos", prorrogasDto);				
				view = "registroProrrogas";
			}
			else if(prorroga.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_LAUDO.getCodigo()))  {
				
				session.setAttribute("documentos", 1L);
				view = "registroLaudo";
			}
			else if(prorroga.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA.getCodigo())) {
				
				session.setAttribute("documentos", 2L);
				view = "registroEnfermedad";
			}
			else if(prorroga.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo())) {
				
				session.setAttribute("documentos", 2L);
				view = "registroEstudios";
			}
			else if(prorroga.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo())){
				
				session.setAttribute("documentos", 2L);
				view = "prorrogasObstetricos";
			}
			else if(prorroga.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_VIGENCIA_PERMANENTE.getCodigo())){
				
				session.setAttribute("documentos", 1L);
				session.setAttribute("tipoTramite", TipoTramiteEnum.PRORROGA_POR_VIGENCIA_PERMANENTE.getCodigo().longValue());
				prorrogasDto.setProrroga(prorroga);				
				model.addAttribute("datos", prorrogasDto);
				view = "registroProrrogas";
			}
			else if(prorroga.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_VIGENCIA_TEMPORAL.getCodigo())){
				
				session.setAttribute("documentos", 1L);
				prorrogasDto.setProrroga(prorroga);				
				model.addAttribute("datos", prorrogasDto);
				view = "registroProrrogas";
			}
			
			
			
			session.setAttribute("idSolicitud", solicitud.getSolicitudId());
			session.setAttribute("modo", "validar");
			session.setAttribute("idTramite", prorroga.getTramiteId());
			session.setAttribute("idPersona", integrante.getDerechohabiente().getIdPersona());
			session.setAttribute("prorroga", prorroga);
			session.setAttribute("hijo", integrante);
			request.setAttribute("miGrupoFamiliar", miGrupoFamiliar);
			
		}catch(DerechohabientesBusinessException e){
			request.setAttribute("exception", e.getSituacion());
			request.setAttribute("error", e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("exception", "exception.general");
			request.setAttribute("error", e.getCause());
		}
		
		return view;
	}
	
	
	

	@RequestMapping(value = "/limpiarSession")
	public @ResponseBody String limpiaSession(HttpSession session) {
		
		this.limpiarSesion(session);
		
		return "OK";
	}
	
	private void limpiarSesion(HttpSession session)  {
		
		session.removeAttribute("idTramite");
		session.removeAttribute("idSolicitud");
		session.removeAttribute("documentos");
		session.removeAttribute("idPersona");
		session.removeAttribute("hijo");
		session.removeAttribute("modo");
		session.removeAttribute("prorroga");
		session.removeAttribute(FileUploadVB.SES_NAME);
		
	}
	 
	
	@RequestMapping(value = "/validaProrroga/")
	public String validarTramite( HttpSession session, Model model, HttpServletRequest request) {
		
		Long idTramite = (Long) session.getAttribute("idTramite");
		Tramite tramite = null;
		AsignacionNSS asignacion = null;
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		try{			
			//Tramite miTramite = solicitudService.getTramiteAutorizar(idTramite);
			Solicitud sol = this.obtenerSolicitud(idTramite);
			asignacion = solicitudServiceRemote.getAsignacionByIdSolicitud(sol.getSolicitudId());
			
			tramite = prorrogaService.validaTramite(sol, asignacion, usuario);
			
			colocarDatosReporte(session, 2L,tramite);
			

			ImpresionReporteDto reporte = new ImpresionReporteDto();
			//Objeto para generar el reporte
			reporte.setIdPersona(tramite.getPersona().getIdPersona());
			reporte.setIdTramite(tramite.getTramiteId());
			reporte.setRechazado(false);
			TipoTramite tipoTram = new TipoTramite();
			tipoTram.setIdTipoTramite(tramite.getTipoTramite().getIdTipoTramite());
			tipoTram.setDescripcion("prórroga");
			reporte.setTipoTramite(tipoTram);
			model.addAttribute("reporte", reporte);
			limpiarSesion(session);
			return "finalizacionTramite";
		}catch (Exception e) {
			log.error("", e);
			model.addAttribute("exception", "error.actualizar.tramite");
			model.addAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
	}
	
	@RequestMapping(value = "/setDatosProrrogaEstudios/")
	public @ResponseBody TramiteProrroga setDatosProrroga( HttpSession session ) {
		
		TramiteProrroga p =  new TramiteProrroga();
		
		// Se obtiene el documento probatorio de la session
		FileUploadVB sessionBean = (FileUploadVB) session
				.getAttribute(FileUploadVB.SES_NAME);
		ConstanciaEstudio prorroga = (ConstanciaEstudio) ((sessionBean
				.getDocumenProbatorioCapturaList().get(0)).getCaptura());

		p.setFechaInicioProrroga(prorroga.getFechaInicioPeriodo());
		p.setFechaFinProrroga(DateUtils.sumarDiasFecha(prorroga.getFechaFinPeriodo(), 30));
		
		return p;
	}
	
	@RequestMapping(value = "/setDatosProrrogaVigenciaPermanente/")
	public @ResponseBody TramiteProrroga setDatosProrrogaPermanente( HttpSession session ) {
		
		TramiteProrroga p =  new TramiteProrroga();
		
		// Se obtiene el documento probatorio de la session
		FileUploadVB sessionBean = (FileUploadVB) session
				.getAttribute(FileUploadVB.SES_NAME);
		Acta prorroga = (Acta) ((sessionBean
				.getDocumenProbatorioCapturaList().get(0)).getCaptura());

		p.setFechaInicioProrroga(prorroga.getFechaSuceso());
		
		
		return p;
	}

	@RequestMapping(value = "/setDatosProrrogaObstetrico/")
	public @ResponseBody TramiteProrroga setDatosProrrogaObstetrico( HttpSession session ) {
		
		TramiteProrroga p =  new TramiteProrroga();				
		
		// Se obtiene el documento probatorio de la session
		FileUploadVB sessionBean = (FileUploadVB) session
				.getAttribute(FileUploadVB.SES_NAME);
		Obstetrico ObstetricoDocto = (Obstetrico) ((sessionBean
				.getDocumenProbatorioCapturaList().get(0)).getCaptura());
		

		p.setFechaInicioProrroga(ObstetricoDocto.getFechaProbableConcepcion());
		p.setFechaFinProrroga(DateUtils.sumarDiasFecha(ObstetricoDocto.getFechaParto(), 60));
		
		
		return p;
	}

	
	


	private Solicitud obtenerSolicitud(Long idTramite) {
		
		Solicitud encontrada = null;
		try {
			encontrada = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
		}catch(Exception e) {
			log.error("No se pudo consutar la solicitud", e);
		}
		return encontrada;
	}
}
