package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.firma.ErrorEnInvocacionRecursoRemotoException;
import mx.gob.imss.ctirss.delta.exception.firma.ModelAccessException;
import mx.gob.imss.ctirss.delta.exception.firma.RecursoRemotoNoDisponibleException;
import mx.gob.imss.ctirss.delta.exception.firma.RegistroPatronalInvalidoEnCertificadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.firma.FirmaElectronicaBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.MedioContactoDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.SolicitudDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.SujetoObligadoDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.ReporteParam;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value = "/afiliacion")
public class AfiliacionController extends AbstractController {

	public static final Logger LOG = Logger.getLogger(AfiliacionController.class);
	
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;
 
	@EJB
	private AfiliacionServiceBusinessRemote afiliacionService;

	@Autowired
	private SolicitudServiceBusinessRemote solicitudService;
	
	@Autowired
	private ManejadorReportesRemote manejadorReportesBusiness;
	
	@Autowired
	private FirmaElectronicaBusinessRemote firmaElectronicaBusiness;
	
	@Autowired
	SujetoObligadoController patronController;
	
	@Autowired
	LoginController loginController;
	
	@Autowired
	SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	@Autowired
	private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusiness;
	
	@RequestMapping(value = "/actualizarTramite", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> administrarTramite(
			@RequestBody SujetoObligado inputObject,
			@RequestParam("tipoTramite") Integer tipoTramite,
			@RequestParam("idSolicitud") Long idSolicitud,
			HttpServletResponse response, HttpSession session, Locale locale) {
		System.err.println("ACTUALIZARE TRAMITE DENOMINACION SOCIAL");
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		System.err.println("Input Object: " + inputObject);
		TipoTramiteEnum tipoTramiteSolicitado = TipoTramiteEnum.obternerEnumById(tipoTramite);
		System.err.println("Tramite Solicitado: "+tipoTramiteSolicitado);
		if(tipoTramiteSolicitado.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
			Object objModificacion = session.getAttribute("datosICA");
			ICADatosRespuesta datosIca = null;
			MDMDatosEntrada datosMdm = null;
			if(objModificacion!=null){
				datosIca = (ICADatosRespuesta)objModificacion;
				inputObject.setDatosICA(datosIca);
			}
			objModificacion = session.getAttribute("datosMDM");
			if(objModificacion!=null){
				datosMdm = (MDMDatosEntrada)objModificacion;
				inputObject.setDatosMDM(datosMdm);
			}
			System.err.println("Datos ICA: "+datosIca);
			System.err.println("Datos MDM: "+datosMdm);
		}
		Map<String, Object> mapAccion=new HashMap<String, Object>();
		try {
			mapAccion = afiliacionService
					.gestionarTramiteActualizacionAfiliacion(idSolicitud,
							inputObject,
							tipoTramiteSolicitado,
							usuario, false);
		} catch (GestionPatronalBusinessException e) {
			String mensaje = messageSource.getMessage(e.getMessage(),null,locale);
			result.put("idSolicitud", 0);
			result.put("mensajeError", mensaje);
			e.printStackTrace();
			return result;
		}
		String mensaje = construirMensaje(mapAccion, locale, tipoTramiteSolicitado);
		result.put("mensajeExito", mensaje);
		result.put("idSolicitud", mapAccion.get("idSolicitud"));
		result.put("folio", mapAccion.get("folio"));
		return result;
	}    
	
	@RequestMapping(value = "/actualizarTramiteRepresentanteLegal", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> actualizarTramiteRepresentanteLegal(
			@RequestBody SujetoObligado inputObject,
			@RequestParam("idSolicitud") Long idSolicitud,
			HttpServletResponse response, HttpSession session, Locale locale) {
		System.err.println("ACTUALIZARE TRAMITE REPRESENTANTE LEGAL");
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		System.err.println("Input Object: " + inputObject);

		// corregir para integrar ese dato desde que se sube a la sesion
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		sujetoTramite.setTipoPersonaFiscal(inputObject.getTipoPersonaFiscal());
		if (sujetoTramite.getTipoPersonaFiscal().equals(
				TipoPersonaFiscal.FISICA)) {
			sujetoTramite.getFisica().setIdPersona(
					inputObject.getFisica().getIdPersona());
		} else {
			sujetoTramite.getMoral().setIdPersona(
					inputObject.getMoral().getIdPersona());
		}

		Map<String, Object> mapAccion;
		try {
			mapAccion = afiliacionService
					.gestionarTramiteActualizacionAfiliacion(idSolicitud,
							sujetoTramite,
							TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL,
							usuario, false);
		} catch (GestionPatronalBusinessException e) {
			String mensaje = messageSource.getMessage(e.getMessage(),null,locale);
			result.put("mensajeError", mensaje);
			e.printStackTrace();
			return result;
		}
		String mensaje = construirMensaje(mapAccion, locale, TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL);
		result.put("mensajeExito", mensaje);
		result.put("idSolicitud", mapAccion.get("idSolicitud"));
		result.put("folio", mapAccion.get("folio"));
		return result;
	}
	
	@RequestMapping(value = "/actualizarTramiteSocio", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> actualizarTramiteSocio(
			@RequestBody SujetoObligado inputObject,
			@RequestParam("idSolicitud") Long idSolicitud,
			HttpServletResponse response, HttpSession session, Locale locale) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		sujetoTramite.setTipoPersonaFiscal(inputObject.getTipoPersonaFiscal());
		if (sujetoTramite.getTipoPersonaFiscal().equals(
				TipoPersonaFiscal.FISICA)) {
			sujetoTramite.getFisica().setIdPersona(
					inputObject.getFisica().getIdPersona());
		} else {
			sujetoTramite.getMoral().setIdPersona(
					inputObject.getMoral().getIdPersona());
		}

		Map<String, Object> mapAccion;
		try {
			mapAccion = afiliacionService
					.gestionarTramiteActualizacionAfiliacion(idSolicitud,
							sujetoTramite,
							TipoTramiteEnum.ACTUALIZACION_SOCIO,
							usuario, false);
		} catch (GestionPatronalBusinessException e) {
			String mensaje = messageSource.getMessage(e.getMessage(),null,locale);
			result.put("mensajeError", mensaje);
			e.printStackTrace();
			return result;
		}
		String mensaje = construirMensaje(mapAccion, locale, TipoTramiteEnum.ACTUALIZACION_SOCIO);
		result.put("mensajeExito", mensaje);
		result.put("idSolicitud", mapAccion.get("idSolicitud"));
		result.put("folio", mapAccion.get("folio"));
		return result;
	}

	@RequestMapping(value = "/enviarSolicitud", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> enviarSolicitud(
			@RequestBody SujetoObligado inputObject,
			HttpServletResponse response, HttpSession session, Locale locale) {
		System.err.println("Se envía la solicitud a ventanilla");
		Map<String, Object> result = new HashMap<String, Object>();
		System.err.println("Input Object: " + inputObject);
		String tipoDocumento=ReporteParam.TIPO_DOCUMENTO_ACUSE;
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		CodigoRolTemporal rolSolicitante = CodigoRolTemporal.PATRON_SUJETO_OBLIGADO;
		Long idSolicitante = null;
		if(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().equals(
				usuario.getPerfilUsuario().getIdPerfilUsuario().intValue())){
			rolSolicitante = CodigoRolTemporal.REPRESENTANTE_LEGAL;
			idSolicitante = usuario.getFisica().getIdPersona();
		}else{//ES PATRON
			if(inputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
				idSolicitante = usuario.getFisica().getIdPersona();
			else
				idSolicitante = usuario.getMoral().getIdPersona();
		}
		
		gestionarSolicitanteConclusion(session, inputObject, rolSolicitante, idSolicitante);
		String mensaje = "";
		Boolean fueFirmada = (Boolean)session.getAttribute("fueFirmada");
		FirmaElectronica datosFirma = null;
		try {
			System.err.println("fue firmada? "+fueFirmada);
			if(fueFirmada!=null && fueFirmada){
				datosFirma = (FirmaElectronica)session.getAttribute("datosFirma");
				System.err.println("Solicitud firmada... "+datosFirma);
			}
			
			Solicitud solicitud  = afiliacionService.enviarSolicitudAlInstituto(inputObject,
					TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, datosFirma);

			session.setAttribute("idSolicitud", solicitud.getSolicitudId());
			
			if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo())){
				mensaje = messageSource.getMessage("msg.confirmacion.envio.solicitud",null,locale);
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())){
				mensaje = messageSource.getMessage("msg.confirmacion.conclusion.solicitud",null,locale);
				tipoDocumento=ReporteParam.TIPO_DOCUMENTO_AVISO;
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo())){
				mensaje = messageSource.getMessage("msg.validacion.solicitud",null,locale);
			}
			
//			mensaje = "Su solicitud ha sido enviada al Instituto, "
//					+ "por favor presentese en ventanilla con la documentación "
//					+ "requerida para finalizar su trámite";						
						
			String rfc = "";
			if(inputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				rfc = inputObject.getFisica().getRfc();
			}else{
				rfc = inputObject.getMoral().getRfc();
			}
			session.removeAttribute("sujetoTramite");
			session.setAttribute("rfcActual", rfc);
			session.removeAttribute("fueFirmada");
			session.removeAttribute("datosFirma");
			result.put("tipoDocumento", tipoDocumento);
			result.put("mensajeExito", mensaje);
		} catch (GestionPatronalBusinessException e) {
			mensaje = e.getMessage();
			result.put("mensajeError", mensaje);
			e.printStackTrace();
		} 
		
		return result;
	}

	@RequestMapping(value = "/enviarSolicitudCentroTrabajo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> enviarSolicitudCentroTrabajo(
			@RequestBody SujetoObligado inputObject,
			HttpServletResponse response, HttpSession session, Locale locale) {
		return enviarSolicitudAlInstituto(inputObject, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, response, session, locale);
	}

	private Map<String , ? extends Object> enviarSolicitudAlInstituto(SujetoObligado inputObject, 
			TipoSolicitudEnum tipoSolicitud,
			HttpServletResponse response, HttpSession session, Locale locale){
		System.err.println("Se envía la solicitud a ventanilla");
		Map<String, Object> result = new HashMap<String, Object>();
		System.err.println("Input Object: " + inputObject);
		String tipoDocumento=ReporteParam.TIPO_DOCUMENTO_ACUSE;
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		CodigoRolTemporal rolSolicitante = CodigoRolTemporal.PATRON_SUJETO_OBLIGADO;
		Long idSolicitante = null;
		if(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().equals(
				usuario.getPerfilUsuario().getIdPerfilUsuario().intValue())){
			rolSolicitante = CodigoRolTemporal.REPRESENTANTE_LEGAL;
			idSolicitante = usuario.getFisica().getIdPersona();
		}else{//ES PATRON
			if(inputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
				idSolicitante = usuario.getFisica().getIdPersona();
			else
				idSolicitante = usuario.getMoral().getIdPersona();
		}
		
		gestionarSolicitanteConclusion(session, inputObject, rolSolicitante, idSolicitante);
		String mensaje = "";
		Boolean fueFirmada = (Boolean)session.getAttribute("fueFirmada");
		FirmaElectronica datosFirma = null;
		try {
			System.err.println("fue firmada? "+fueFirmada);
			if(fueFirmada!=null && fueFirmada){
				datosFirma = (FirmaElectronica)session.getAttribute("datosFirma");
				System.err.println("Solicitud firmada... "+datosFirma);
			}
			
			Solicitud solicitud  = afiliacionService.enviarSolicitudAlInstituto(inputObject,
					tipoSolicitud, datosFirma);

			session.setAttribute("idSolicitud", solicitud.getSolicitudId());
			
			if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo())){
				mensaje = messageSource.getMessage("msg.confirmacion.envio.solicitud",null,locale);
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())){
				mensaje = messageSource.getMessage("msg.confirmacion.conclusion.solicitud",null,locale);
				tipoDocumento=ReporteParam.TIPO_DOCUMENTO_AVISO;
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo())){
				mensaje = messageSource.getMessage("msg.validacion.solicitud",null,locale);
			}
			
						
			String rfc = "";
			if(inputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				rfc = inputObject.getFisica().getRfc();
			}else{
				rfc = inputObject.getMoral().getRfc();
			}
			session.removeAttribute("sujetoTramite");
			session.setAttribute("rfcActual", rfc);
			session.removeAttribute("fueFirmada");
			session.removeAttribute("datosFirma");
			result.put("tipoDocumento", tipoDocumento);
			result.put("mensajeExito", mensaje);
		} catch (GestionPatronalBusinessException e) {
			mensaje = e.getMessage();
			result.put("mensajeError", mensaje);
			e.printStackTrace();
		} 
		
		return result;
	}
	
	@RequestMapping(value = "/validarTramiteActivo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarTramiteActivo(
			@RequestBody SujetoObligado inputObject,
			@RequestParam("tipoTramite") Integer tipoTramite,
			@RequestParam("idSolicitud") Long idSolicitud,
			@RequestParam("rfc") String rfc,
			HttpServletResponse response, HttpSession session, Locale locale) {
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		
		try {
			afiliacionService.validaCondicionesDeActualizacionDeSolicitud(idSolicitud, usuario, rfc);
		} catch (GestionPatronalBusinessException e) {
			String mensaje = messageSource.getMessage(e.getMessage(),null,locale);
			result.put("mensajeError", mensaje);
			return result;
		}
		
		TipoTramiteEnum tipoTramiteSolicitado = TipoTramiteEnum.obternerEnumById(tipoTramite);
		System.err.println("Tipo Tramite Solicitado para validar: "+tipoTramiteSolicitado);
		Map<String, Object> mapAccion = afiliacionService.validarTramiteAfiliacionActivo(
				idSolicitud, inputObject, tipoTramiteSolicitado,
				usuario);
		
		String mensaje=construirMensaje(mapAccion, locale, tipoTramiteSolicitado);
		result.put("mensajeExito", mensaje);
		return result;
	}
	
	@RequestMapping(value = "/validarReglasDelegDatosContacto", method = RequestMethod.POST)		
	public @ResponseBody
	Map<String, ? extends Object> validarReglasDelegDatosContacto(
			@RequestBody SujetoObligado inputObject,			
			HttpServletResponse response, HttpSession session, Locale locale) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		boolean resultadoValidacion=true;
		String mensaje="";
		//Usuario usuario = (Usuario) session.getAttribute("usuario");				
		//Validar regla negocio: por lo menos un dato de contacto		
		mensaje=afiliacionService.validarMediosContacto(inputObject.getCntroTrabajo().getMediosContacto());
		//Validar regla negocio: Subdelegación Origen, Subdelegación Destino
		try {
			afiliacionService.validarSubdOrigenSubDestino(inputObject);
		} catch (GestionPatronalBusinessException e) {
			mensaje=this.messageSource.getMessage(e.getMessage(), null, locale);
		}										
		if (!mensaje.equals(""))			
			resultadoValidacion=false;				
		result.put("resultadoValidacion", resultadoValidacion);
		result.put("mensaje", mensaje);
		return result;
	}
		
		
	@RequestMapping(value = "/ratificarTramite", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> ratificar(
			@RequestBody SujetoObligado inputObject,
			@RequestParam("idSolicitud") Long idSolicitud,
			@RequestParam("tipoTramite") String nombreTipoTramite,
			HttpServletResponse response, HttpSession session, Locale locale) {

		System.err.println("Se ratificará el tramite " + nombreTipoTramite);
		TipoTramiteEnum tipoTramite = TipoTramiteEnum
				.obtenerEnumByName(nombreTipoTramite);
		
		/*if (TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.name().equals(nombreTipoTramite)){
			if(inputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				inputObject.getFisica().setRepresentantesLegales((List<RepresentanteLegal>) session.getAttribute("repLegalListFromDBForValidations"));
			}else{
				inputObject.getMoral().setRepresentantesLegales((List<RepresentanteLegal>) session.getAttribute("repLegalListFromDBForValidations"));
			}
		} else if (TipoTramiteEnum.ACTUALIZACION_SOCIO.name().equals(nombreTipoTramite)){
			if(inputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				inputObject.getFisica().setSocios((List<Socio>) session.getAttribute("sociosListFromDBForValidations"));
			}else{
				inputObject.getMoral().setSocios((List<Socio>) session.getAttribute("sociosListFromDBForValidations"));
			}
		}*/
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Map<String, Object> mapAccion;
		try {
			mapAccion = afiliacionService.gestionarTramiteActualizacionAfiliacion(idSolicitud,
					inputObject,
			tipoTramite, usuario, true);
		} catch (GestionPatronalBusinessException e) {
			String mensaje = messageSource.getMessage(e.getMessage(),null,locale);
			result.put("mensajeError", mensaje);
			e.printStackTrace();
			return result;
		}
		
		result.put("mensajeExito",
				construirMensaje(mapAccion, locale, tipoTramite));
		result.put("idSolicitud", mapAccion.get("idSolicitud"));
		result.put("folio", mapAccion.get("folio"));
		return result;
	}

	@RequestMapping(value = "/inicializarMediosFiscales", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<MedioContacto> inicializarMediosFiscales(
			@RequestBody MedioContactoDataTable params, HttpSession session, Locale locale) {
	
		DatosEntradaPaginador<MedioContacto> input = new DatosEntradaPaginador<MedioContacto>();
		input.setModelo(params.getoForm());
		input.parserArray(params.getAoData());
		
		DatosSalidaPaginador<MedioContacto> output = new DatosSalidaPaginador<MedioContacto>();
		Integer iTotalDisplayRecords = 0;
		Integer iTotalRecords = 0;
		List<MedioContacto> aaData = new ArrayList<MedioContacto>();
		
		@SuppressWarnings("unchecked")
		List<MedioContacto> mediosContactoFiscales = (List<MedioContacto>)session.getAttribute("listaMediosContactoFiscales");
		if(mediosContactoFiscales!=null)
			aaData = mediosContactoFiscales;
		
		session.removeAttribute("listaMediosContactoFiscales");
		output.setsEcho(input.getsEcho());
		output.setiTotalDisplayRecords(iTotalDisplayRecords);
		output.setiTotalRecords(iTotalRecords);
		output.setAaData(aaData);
		return output;
	}
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 09/08/2012
	 * @param params
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/cargaSolicitudesEnProceso", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<Solicitud> cargarSolicitudesEnProceso(
			@RequestBody SolicitudDataTable params, HttpSession session, Locale locale) {
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		boolean esTramitador=
				usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()) 
				? true 
				: false;
		DatosSalidaPaginador<Solicitud> output = new DatosSalidaPaginador<Solicitud>();
		List<Solicitud> solicitudesEnProceso = afiliacionService
				.listarSolicitudesEnProceso(params.getoForm()
						.getSujetoObligado(), esTramitador);
		
		DatosEntradaPaginador<Solicitud> input = new DatosEntradaPaginador<Solicitud>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		input.setiDisplayStart(0);
		List<Solicitud> solicitudesActualizadas = new ArrayList<Solicitud>();
		solicitudesActualizadas = ajustarDescripcionDeTramites(solicitudesEnProceso, locale);
		
		
//		for(Solicitud solicitud : solicitudesEnProceso){
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//			String fecha = sdf.format(solicitud.getFechaSolicitud());
//			solicitud.setFechaSolicitudParse(fecha);
//			System.err.println("Fecha de solicitud after: "+fecha);
//			System.err.println(">>> solicitud en proceso, FechaSolicitud: " + solicitud.getFechaSolicitud() + ", FechaActualizacion:" + solicitud.getFechaActualizacion());
//			List<Tramite> tramitesActualizados = new ArrayList<Tramite>();
//			for(Tramite tramite : solicitud.getTramites()){
//				System.err.println("Es trámite ratificado: "+tramite.getIndRatificado());
//				boolean ratificado = tramite.getIndRatificado()!=null ? tramite.getIndRatificado() : false;
//				if(ratificado){
//					String mensajeRatificacion="";
//					if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())){
//						System.err.println("Asignando mensaje de ratificacion");
//						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.denominacion",null,locale);
//					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())){
//						System.err.println("Asignando mensaje de ratificacion");
//						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.contacto",null,locale);
//					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())){
//						System.err.println("Asignando mensaje de ratificacion");
//						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.representante",null,locale);
//					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo())){
//						System.err.println("Asignando mensaje de ratificacion");
//						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.socio",null,locale);
//					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo())){
//						System.err.println("Asignando mensaje de ratificacion");
//						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.acta.constitutiva",null,locale);
//					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo())){
//						System.err.println("Asignando mensaje de ratificacion");
//						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.registro.sindicato",null,locale);
//					}
//					tramite.getTipoTramite().setDescripcion(mensajeRatificacion);
//					System.err.println("Descripcion tipo de tramite: "+tramite.getTipoTramite().getDescripcion());
//				}
//				tramitesActualizados.add(tramite);
//			}	
//			solicitud.setTramites(tramitesActualizados);
//			solicitudesActualizadas.add(solicitud);
//		}
		
		output.setiTotalDisplayRecords(input.getiDisplayStart());
		output.setiTotalRecords(solicitudesEnProceso.size());
		output.setAaData(solicitudesActualizadas);
		output.setsEcho(input.getsEcho());
		
		return output;
	}

	/**
	 * Carga el grid de registros patronales asociados a un rfc
	 * 
	 * @param params
	 * @param session
	 * @return JSON Object
	 */
	@RequestMapping(value = "/cargaRegistrosPatronales", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<SujetoObligado> listarRegistrosPatronales(
			@RequestBody SujetoObligadoDataTable params, HttpSession session) {
		System.err.println("Cargando registros patronales.....");
		
		DatosEntradaPaginador<SujetoObligado> input = new DatosEntradaPaginador<SujetoObligado>(); 
		input.setModelo(params.getoForm());
		input.parserArray(params.getAoData());
		System.err.println("Modelo: "+input.getModelo());
		System.err.println("Param: "+params.getoForm());
		DatosSalidaPaginador<SujetoObligado> output = new DatosSalidaPaginador<SujetoObligado>();
		output = sujetoObligadoService.listarRegistrosPatronales(input);
		output.setsEcho(input.getsEcho());
		return output;
	}

	@RequestMapping(value = "/concluirSolicitud", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> concluirSolicitud(
			@RequestBody SujetoObligado inputObject,
			@RequestParam("idSolicitud") Long idSolicitud,
			@RequestParam("rolSolicitante") CodigoRolTemporal rolSolicitante,
			@RequestParam("idSolicitante") Long idSolicitante,
			HttpServletResponse response, HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		if(idSolicitud == null || idSolicitud <= 0){
			result.put("mensajeError", "No existe ninguna solicitud en proceso.");
			return result;
		}
		System.err.println("gestionando solicitante ");
		gestionarSolicitanteConclusion(session, inputObject, rolSolicitante, idSolicitante);
		
		String mensaje = null;
		try {
			afiliacionService.concluirSolicitud(idSolicitud, inputObject, usuario);
			mensaje = "La solicitud se ha finalizado satisfactoriamente";
			result.put("mensajeExito", mensaje);
			String rfc = "";
			if(inputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				rfc = inputObject.getFisica().getRfc();
			}else{
				//Se requiere el dato para paginar los socios en la pantalla principal
				System.err.println("cveIdPatron subida para socios: "+inputObject.getMoral().getIdPersona());
				session.setAttribute("cveIdPatronSO",inputObject.getMoral().getIdPersona());
				rfc = inputObject.getMoral().getRfc();
			}
			session.removeAttribute("sujetoTramite");
			session.setAttribute("rfcActual", rfc);
			session.setAttribute("idSolicitud", idSolicitud);
		} catch (GestionPatronalBusinessException e) {
			mensaje = e.getMessage();
			result.put("mensajeError", mensaje);
		}
		return result;
	}
	
	@RequestMapping(value = "/cancelarSolicitud", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitud(
			@RequestBody Solicitud inputObject,
			HttpServletResponse response, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		
//		Solicitud solicitud = afiliacionService.obtenerSolicitudEnProceso(inputObject.getSujetoObligado(), TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES);
//		Long idSolicitud = solicitud!= null ? solicitud.getSolicitudId() : null;
		
		Long idSolicitud = inputObject.getSolicitudId();
		if(idSolicitud == null || idSolicitud <= 0){
			result.put("mensajeError", "No existe ninguna solicitud en proceso asociada con modificación datos patronales para ser cancelada.");
			return result;
		}
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		inputObject.setSolicitante(usuario);
		Solicitud solicitudCancelada = afiliacionService.cancelarSolicitud(inputObject);
		String rfc = "";
		SujetoObligado sujetoTramite = inputObject.getSujetoObligado();
		if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			rfc = sujetoTramite.getFisica().getRfc();
		}else{
			//Se requiere el dato para paginar los socios en la pantalla principal
			System.err.println("cveIdPatron subida para socios: "+sujetoTramite.getMoral().getIdPersona());
			session.setAttribute("cveIdPatronSO",sujetoTramite.getMoral().getIdPersona());
			rfc = sujetoTramite.getMoral().getRfc();
		}
		session.removeAttribute("sujetoTramite");
		session.setAttribute("rfcActual", rfc);
		
		result.put("mensajeExito", "La solicitud con folio " + solicitudCancelada.getNoFolioSolicitud() + " ha sido cancelada.");
		return result;
	}
	
	@RequestMapping(value = "/rechazarSolicitud", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> rechazarSolicitud(
			@RequestBody Solicitud inputObject,
			HttpServletResponse response, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		System.err.println("Sujeto Tramite rechazar Inicial: "+inputObject.getSujetoObligado());
		SujetoObligado sujetoTramite = inputObject.getSujetoObligado();
		Long idSolicitud = inputObject.getSolicitudId();
		if(idSolicitud == null || idSolicitud <= 0){
			result.put("mensajeError", "No existe ninguna solicitud en proceso asociada con modificación datos patronales para ser cancelada.");
			return result;
		}
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		inputObject.setSolicitante(usuario);
		
		Long idRazonCancelacion = inputObject.getRazonCancelacion()!= null ? inputObject.getRazonCancelacion().getIdRazonCancelacion() : null;
		if(inputObject.getRazonCancelacion() == null)
			inputObject.setRazonCancelacion(new RazonCancelacion());
		inputObject.getRazonCancelacion().setIdRazonCancelacion(idRazonCancelacion);
		inputObject.setSolicitante(usuario);
		inputObject = afiliacionService.rechazarSolicitud(inputObject);
		solicitudService.actualizarDatosGeneralesDeSolicitud(inputObject);
		
		String rfc = "";
		
		System.err.println("Sujeto Tramite rechazar: "+sujetoTramite);
		if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			rfc = sujetoTramite.getFisica().getRfc();
		}else{
			//Se requiere el dato para paginar los socios en la pantalla principal
			System.err.println("cveIdPatron subida para socios: "+sujetoTramite.getMoral().getIdPersona());
			session.setAttribute("cveIdPatronSO",sujetoTramite.getMoral().getIdPersona());
			rfc = sujetoTramite.getMoral().getRfc();
		}
		session.removeAttribute("sujetoTramite");
		session.setAttribute("rfcActual", rfc);
		
		result.put("mensajeExito", "La solicitud con folio " + inputObject.getNoFolioSolicitud() + " ha sido rechazada.");
		return result;
	}
	
	
	
	@RequestMapping(value = "/mostrarDetalleRegistroPatronal", method = RequestMethod.POST)
	public String mostrarDetalleRP(@ModelAttribute SujetoObligado inputObject, Model model, 
			HttpSession session) {
		log.error("inpuObject registroPatronal: "+inputObject.getNumeroRegistroPatronal());
		log.error("inpuObject Tipo Persona: "+inputObject.getTipoPersonaFiscal());
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		SujetoObligado outputObject = afiliacionService.obtenerDetalleDeRegistroPatronal(inputObject);
		gestionarDatosTramite(model, outputObject, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO,TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.name(),"tramiteCentroTrabajoActivo", "tramiteCentroTrabajoRatificado", session,false);
		Solicitud solicitud = solicitudService.obtenerSolicitudActiva(outputObject, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, usuario, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO);
		
		if(solicitud!=null){
			model.addAttribute("idSolicitudCT", solicitud.getSolicitudId());
		}
		model.addAttribute("sujetoObligado", outputObject);
		model.addAttribute("idTramite", TipoTramiteEnum.ACTIVIDAD_ECONOMICA);		
		return "detalle.registropatronal.afiliacion";
	}
	
	@RequestMapping(value = "/evaluarSeleccionDeSolicitud", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> mostrarDetalleTramite(
			@RequestBody Solicitud inputObject,
			Model model, 
			HttpSession session) {
		System.err.println("Evaluando selección de solicitud......... ");
		Map<String, Object> result = new HashMap<String, Object>();
		System.err.println("Tipo Persona: "+inputObject.getSujetoObligado().getTipoPersonaFiscal());
		Long solicitudId = Long.valueOf(inputObject.getSolicitudId());
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Solicitud solicitud = solicitudService.consultarSolicitudPorId(solicitudId);
		Integer tipoOperacion=null;
		if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().longValue())){
			
			if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
				Long idSubdelegacionTramitador = usuario.getCveIdSubdelegacion();
				Long idSubdelegacionSolicitud = solicitud.getSubdelegacion()!=null ? solicitud.getSubdelegacion().getId() : null;
				System.err.println("idSubdelegacionTramitador: "+idSubdelegacionTramitador);
				System.err.println("idSubdelegacionSolicitud: "+idSubdelegacionSolicitud);
				
				if(idSubdelegacionTramitador!=null && idSubdelegacionSolicitud!=null && !idSubdelegacionSolicitud.equals(idSubdelegacionTramitador)){
					tipoOperacion = TipoAccionAfectacionEnum.ACCESO_NO_AUTORIZADO.getValor().intValue();
					result.put("tipoOperacion",tipoOperacion);		
					return result;
				}
			}
				
			
			System.err.println("Registro Patronal: "+inputObject.getSujetoObligado().getNumeroRegistroPatronal());
			SujetoObligado sujetoObligado = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(inputObject.getSujetoObligado());
			
			if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.REGISTRADA.getCodigo())){// EN CAPTURA
				if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
						|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())
					){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_SRT.getValor().intValue();
				}else if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_SRT.getValor().intValue();
				}
				
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())
					 ||solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo())
					 ||solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo())
					 ){//EN PROCESO
				if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
							|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())
					){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_ACUSE_DE_MODIFICACION_SRT.getValor().intValue();
					session.setAttribute("sujetoObligado", sujetoObligado);
					session.setAttribute("idSolicitud", inputObject.getSolicitudId());
				}else if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_SRT.getValor().intValue();
				}
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())){
				tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_AVISO_DE_MODIFICACION_SRT.getValor().intValue();
				session.setAttribute("sujetoObligado", sujetoObligado);
				session.setAttribute("idSolicitud", inputObject.getSolicitudId());
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo())
					||solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo())){
				tipoOperacion = TipoAccionAfectacionEnum.SOLICITAR_ASIGNACION.getValor().intValue();
			}
		}else if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor().longValue())){
			if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.REGISTRADA.getCodigo())){// EN CAPTURA
				if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
						|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())
					){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_DATOS_PATRONALES.getValor().intValue();
				}else if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_DATOS_PATRONALES.getValor().intValue();
				}
				
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())
					||solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo())
					||solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo())
					 ){//EN PROCESO
				if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
							|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())
					){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_ACUSE_DATOS_PATRONALES.getValor().intValue();
				}else if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_DATOS_PATRONALES.getValor().intValue();
				}
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())){
				tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_ACUSE_DATOS_PATRONALES.getValor().intValue();
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo())
					||solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo())){
				
				
				if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
						|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())
				){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_ACUSE_DATOS_PATRONALES.getValor().intValue();
				}else{
					tipoOperacion = TipoAccionAfectacionEnum.SOLICITAR_ASIGNACION.getValor().intValue();
				}
			}
		}else if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue())){
			
			if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.REGISTRADA.getCodigo())){// EN CAPTURA
				if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
						|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())
					){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_CENTRO_TRABAJO.getValor().intValue();
				}else if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_CENTRO_TRABAJO.getValor().intValue();
				}
				
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())
					||solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo())
					||solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo())
					){//EN PROCESO
				if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
							|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())
					){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_ACUSE_CENTRO_TRABAJO.getValor().intValue();
				}else if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_CENTRO_TRABAJO.getValor().intValue();
				}
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())){
				tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_ACUSE_CENTRO_TRABAJO.getValor().intValue();
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo())
					||solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo())){
				if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
						|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())
				){
					tipoOperacion = TipoAccionAfectacionEnum.MOSTRAR_ACUSE_CENTRO_TRABAJO.getValor().intValue();
				}else{
					tipoOperacion = TipoAccionAfectacionEnum.SOLICITAR_ASIGNACION.getValor().intValue();
				}
			}
		}
		System.err.println("Tipo de Operación a ejecutar: "+tipoOperacion);
		result.put("tipoOperacion",tipoOperacion);		
		return result;
	}
	
	@RequestMapping(value = "/actualizarTramiteCentroTrabajo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> administrarTramiteCentroTrabajo(
			@RequestBody SujetoObligado inputObject,
			@RequestParam("idSolicitud") Long idSolicitud,
			HttpServletResponse response, HttpSession session, Locale locale) {		
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		System.err.println("Input Object: " + inputObject);
//		if (inputObject.getMoral()!=null){
//			inputObject.getMoral().setIdPersona(inputObject.getCveIdSujetoObligado());
//			inputObject.getMoral().setCveMoral(inputObject.getCveIdSujetoObligado());
//		}

		Map<String, Object> mapAccion;
		try {
			mapAccion = afiliacionService
					.gestionarTramiteActualizacionAfiliacion(idSolicitud,
							inputObject,
							TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO,
							usuario, false);
		} catch (GestionPatronalBusinessException e) {
			String mensaje = messageSource.getMessage(e.getMessage(),null,locale);
			result.put("mensajeError", mensaje);
			e.printStackTrace();
			return result;
		}
		String mensaje = construirMensaje(mapAccion, locale, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO);
		log.debug("<OTIKA>CentroTrabajo:"+mensaje);		
		result.put("idSolicitudCT", mapAccion.get("idSolicitud"));
		result.put("folioSolicitud", mapAccion.get("folio"));
		result.put("mensajeExito", mensaje);
		return result;
	}
	
	@RequestMapping(value = "/validarTramiteCentroTrabajo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarTramiteCentroTrabajo(
			@RequestBody SujetoObligado inputObject,
			@RequestParam ("idSolicitud") Long idSolicitud,
			HttpServletResponse response, HttpSession session, Locale locale) {
		
		if (inputObject.getMoral()!=null){
			inputObject.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
			inputObject.getMoral().setIdPersona(inputObject.getCveIdSujetoObligado());
			inputObject.getMoral().setCveMoral(inputObject.getCveIdSujetoObligado());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Map<String, Object> mapAccion = afiliacionService.validarTramiteAfiliacionActivo(
				idSolicitud,
				inputObject, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO,
				usuario);
		
		String mensaje=construirMensaje(mapAccion, locale, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO);
		log.debug("<OTIKA>CentroTrabajo:"+mensaje);
		result.put("mensajeExito", mensaje);
		return result;
	}
	
	
	private void gestionarDatosTramite(Model model, SujetoObligado sujetoObligado, TipoTramiteEnum tipoTramite, String varNombreTramite, String varStatusTramite, String varTramiteRatificado, HttpSession session, boolean esNuevaSolicitud){
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Map<String,Object> result = afiliacionService.gestionarDatosDeTramite(sujetoObligado, tipoTramite, usuario, esNuevaSolicitud);
		model.addAttribute(varStatusTramite,(Boolean)result.get("tramiteActivo"));
		model.addAttribute(varNombreTramite, (SujetoObligado)result.get("tramiteData"));
		model.addAttribute(varTramiteRatificado, (Boolean)result.get("tramiteRatificado"));
		
		if (TipoTramiteEnum.ACTUALIZACION_SOCIO.name().equals(varNombreTramite)){
			session.setAttribute(varNombreTramite, (SujetoObligado)result.get("tramiteData"));
		}
		if (TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.name().equals(varNombreTramite)){
			SujetoObligado sujetoTramite = (SujetoObligado) result.get("tramiteData");
			sujetoTramite.setTipoPersonaFiscal(sujetoObligado.getTipoPersonaFiscal());
			session.setAttribute("sujetoTramiteForRepLegal", sujetoTramite);
			session.setAttribute(varNombreTramite, sujetoTramite);
		}
		
		
		
		if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA)){
			SujetoObligado sujetoTr=(SujetoObligado)result.get("tramiteData");
			
			if(sujetoTr!= null && sujetoTr.getMoral()!=null && sujetoTr.getMoral().getEscrituraConstitutiva()!= null 
					&& sujetoTr.getMoral().getEscrituraConstitutiva().getLugarExpedicion()!= null){
			
			model.addAttribute("claveMun",sujetoTr.getMoral().getEscrituraConstitutiva().getLugarExpedicion().getClave());
			model.addAttribute("claveEdo",sujetoTr.getMoral().getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa().getClave());
			}
		}
		if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
			
			Long idTipoSociedad = 0L;
			List<MedioContacto> mFiscales = null;
			try{
				SujetoObligado sujetoTr=(SujetoObligado)result.get("tramiteData");
				this.log.error("Tipo Persona Tramite: "+ sujetoObligado.getTipoPersonaFiscal());
				System.err.println("Tipo Persona Tramite: "+ sujetoObligado.getTipoPersonaFiscal());
				if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
					idTipoSociedad = sujetoTr.getMoral().getTipoSociedad().getIdTipoSociedad();
					System.err.println("TIPO SOCIEDAD DE DATOS GENERALES..."+idTipoSociedad);
					model.addAttribute("idTipoSociedadVar", idTipoSociedad);	
					mFiscales = sujetoTr.getMoral().getMediosContactoFiscales() !=null ?
							sujetoTr.getMoral().getMediosContactoFiscales() : new ArrayList<MedioContacto>();
					System.err.println("Medios Fiscales: "+sujetoTr.getMoral().getMediosContactoFiscales());
					session.setAttribute("listaMediosContactoFiscales", mFiscales);
					model.addAttribute("listaMediosContactoFiscales",mFiscales);
				}else{
					mFiscales = sujetoTr.getFisica().getMediosContactoFiscales() !=null ?
							sujetoTr.getFisica().getMediosContactoFiscales() : new ArrayList<MedioContacto>();
					session.setAttribute("listaMediosContactoFiscales", mFiscales);
					model.addAttribute("listaMediosContactoFiscales",mFiscales);
				}
			}catch(NullPointerException npe){
				log.error("Error al tratar Tipo Sociedad para persona fisica, parece que no sabes!: " + npe.getMessage());
			} finally{
				model.addAttribute("idTipoSociedadVar", idTipoSociedad);
			}
		}
	}
	
	@RequestMapping(value = "/validaRegistroPatronalPermitido", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validaRegistroPatronalPermitido(
			@RequestBody SujetoObligado inputObject,
			Model model, 
			HttpSession session, Locale locale) {
		System.err.println("Evaluando selección de solicitud......... ");
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		System.err.println("Tipo Persona: "+inputObject.getTipoPersonaFiscal());
		result = afiliacionService.validaRegistroPatronalValidoPorRFC(inputObject, usuario);
		
		if(result.get("mensajeErrorKey")!= null){
			Object[] args = null;
			String parametros = (String)result.get("parametrosMensaje");
			if(parametros!= null){
				args = parametros.split(",");
			}
			String mensaje = messageSource.getMessage((String)result.get("mensajeErrorKey"), args, locale);
			result.put("mensajeError", mensaje);
		}
		
		return result;
	}
	
	private String construirMensaje(Map<String, Object> mapAccion, Locale locale, TipoTramiteEnum tramite){
		StringBuffer mensajebfr=new StringBuffer();
		String mensaje ="";
		TipoAccionAfectacionEnum operacion = (TipoAccionAfectacionEnum)mapAccion.get("operacion");
		String nombreTramite = obtenerNombreTramite(tramite, locale);
		if(operacion.equals(TipoAccionAfectacionEnum.AGREGAR_SOLICITUD)){
			mensajebfr.append(messageSource.getMessage("msg.agregar.solicitud",new Object[]{nombreTramite},locale)+"<br><br>");
			agregarDetalleDeProcesoAMensaje(mensajebfr, tramite, locale);
		}else if(operacion.equals(TipoAccionAfectacionEnum.AGREGAR_TRAMITE_SOLICITUD)){
			mensajebfr.append(messageSource.getMessage("msg.agregar.tramite.solicitud",new Object[]{nombreTramite, mapAccion.get("folio")},locale)+"<br><br>");
			agregarDetalleDeProcesoAMensaje(mensajebfr, tramite, locale);
		}else if(operacion.equals(TipoAccionAfectacionEnum.ACTUALIZAR_TRAMITE)){
			mensajebfr.append(messageSource.getMessage("msg.actualizar.tramite.solicitud",new Object[]{nombreTramite, mapAccion.get("folio")},locale)+"<br><br>");
			agregarDetalleDeProcesoAMensaje(mensajebfr, tramite, locale);
		}else if(operacion.equals(TipoAccionAfectacionEnum.MOSTRAR_MENSAJE_SOLICITUD_CREADA)){
			mensajebfr.append(messageSource.getMessage("msg.solicitud.creada",new Object[]{mapAccion.get("folio"), nombreTramite },locale)+"<br><br>");
		}else if(operacion.equals(TipoAccionAfectacionEnum.MOSTRAR_MENSAJE_TRAMITE_ACTUALIZADO)){
			mensajebfr.append(messageSource.getMessage("msg.tramite.actualizado.solicitud",new Object[]{nombreTramite, mapAccion.get("folio")},locale)+"<br><br>");
		}else if(operacion.equals(TipoAccionAfectacionEnum.MOSTRAR_MENSAJE_RATIFICACION)){
			mensajebfr.append(messageSource.getMessage("msg.confirmacion.ratificacion",new Object[]{nombreTramite, mapAccion.get("folio")},locale));
		}
		
		mensaje = mensajebfr.toString();
		return mensaje;
	}
	
	private String obtenerNombreTramite(TipoTramiteEnum tramite, Locale locale){
		String nombreTramite="";
		if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
			nombreTramite = messageSource.getMessage("label.tramite.razon.social",null,locale);
		}else if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO)){
			nombreTramite = messageSource.getMessage("label.tramite.datos.contacto",null,locale);
		}else if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA)){
			nombreTramite = messageSource.getMessage("label.tramite.escritura.constitutiva",null,locale);
		}else if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO)){	
			nombreTramite = messageSource.getMessage("label.tramite.registro.sindicato",null,locale);
		}else if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL)){
			nombreTramite = messageSource.getMessage("label.tramite.respresentante.legal",null,locale);
		}else if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO)){
			nombreTramite = messageSource.getMessage("label.tramite.socio",null,locale);
		}else if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO)){
			nombreTramite = messageSource.getMessage("label.tramite.centro.trabajo",null,locale);
		}
			
		
		return nombreTramite;
	}
	
	private StringBuffer agregarDetalleDeProcesoAMensaje(StringBuffer mensajebfr, TipoTramiteEnum tramite, Locale locale){
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.encabezado",null,locale)+"<br>");
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.uno",null,locale)+"<br>");
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.dos",null,locale)+"<br>");
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.tres",null,locale)+"<br>");
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.cuatro",null,locale)+"<br>");
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.cinco",null,locale)+"<br>");
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.seis",null,locale)+"<br>");
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.siete",null,locale)+"<br>");
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.ocho",null,locale)+"<br>");
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.nueve",null,locale)+"<br>");
		mensajebfr.append(messageSource.getMessage("msg.tramite.proceso.diez",null,locale)+"<br><br>");
		if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)
				|| tramite.equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA)
				|| tramite.equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO)
				|| tramite.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO))
			mensajebfr.append(messageSource.getMessage("msg.req.datos.generales",null,locale)+"<br>");
		else if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL))
			mensajebfr.append(messageSource.getMessage("msg.req.representante",null,locale)+"<br>");
		else if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO))
			mensajebfr.append(messageSource.getMessage("msg.req.socio",null,locale)+"<br>");
		else if(tramite.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO))
			mensajebfr.append(messageSource.getMessage("msg.req.centro.trabajo",null,locale)+"<br>");
		
		return mensajebfr;
	}
	
	@RequestMapping(value = "/procesarInformacionAcuseAfiliacion", method = RequestMethod.POST)
	public Map<String,Object> procesarInformacionAcuseAfiliacion(HttpServletResponse response, HttpSession session, @RequestParam("origen") String origen){
//		Long idSolicitud=2243L;
		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		LOG.info("/**** OBTENER MAP PARA REPORTE DE MODIFICCION PATRONAL ****/"+idSolicitud+" :: "+origen);
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
		TramiteSujetoObligado tso = afiliacionService.inicializarInformacionSujetoTramite(solicitud.getTramites().get(0));
		Map<String,Object> mapData = new HashMap<String, Object>();
//		Map<String,Object> mapData = afiliacionService.obtenerInformacionDeSolicitud(idSolicitud);
//		for(String key : mapData.keySet()){
//			LOG.info(key + " :: "+mapData.get(key));
//		}
		solicitud.setSujetoObligado(tso.getSujetoObligado());
		mapData.put("solicitudData", solicitud);
		
		
//		TramiteSujetoObligado tramiteSujetoObligado = (TramiteSujetoObligado)solicitud.getTramites().get(0);
//		SujetoObligado sujetoObligadoInformacionActual = afiliacionService.obtenerDatosFiscales(inicializarPersonaAConsultar(tramiteSujetoObligado));		
//		mapData.put("sujetoObligado", sujetoObligadoInformacionActual);
		mapData.put("varTitulo", origen);
		
//		LOG.info("SujetoActual: "+sujetoObligadoInformacionActual);
		LOG.info("Folio: "+solicitud.getNoFolioSolicitud());
		LOG.info("Usuario reporte: "+usuario);
//		if(origen.equalsIgnoreCase("ACUSE")){
//			solicitud.setSolicitante(usuario);
//		}else{
			Usuario solicitante = (Usuario)session.getAttribute("solicitante");
			solicitud.setSolicitante(solicitante);
//		}
		LOG.info("Solicitante solicitud: "+solicitud.getSolicitante());
//		solicitud.setSujetoObligado(sujetoObligadoInformacionActual);
		mapData.put("solicitudData",solicitud);
		byte[] reporte = manejadorReportesBusiness.ejecutaAcuseModificacionDatosPatronales(mapData);
		String nombreArchivo = "AcuseModificacionPatronal.pdf";
		
		if(reporte != null){			
			try {
				log.debug("EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR");
				response.setContentType("application/pdf"); 
				response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo); 
				response.getOutputStream().write(reporte);
				response.getOutputStream().close();
				session.removeAttribute("idSolicitud");
				LOG.error("Se enviara el correo electronico");
				afiliacionService.notificarPorCorreoElectronico(solicitud.getSujetoObligado(), idSolicitud, TipoAccionAfectacionEnum.ATTACH_ACUSE.getValor().intValue());
			} catch (IOException e) {				
				e.printStackTrace();
			}catch(GestionPatronalBusinessException gpe){
				gpe.printStackTrace();
			}		
		}else{
			log.debug("EL REPORTE ES NULO Y NO SE DEBE IMPRIMIR ");
		}			
//		session.removeAttribute("idSolicitud");
		return mapData;
	}
	
	
	@RequestMapping(value = "/iniciaProcesoFirmaDigital", method = RequestMethod.POST)
	public String getDatosFirmaSolicitud (
			Model model,
			@RequestParam String idSolicitudActiva,
			@RequestParam String idSujetoObligado,@RequestParam String numRegPatronal, HttpSession session) {			
    		log.debug("<OTIKA>El id de la solicitud es:"+idSolicitudActiva);
    		log.debug("<OTIKA>El id del sujeto obligado:"+idSujetoObligado);
    		log.debug("<OTIKA>El rp del sujeto obligado:"+numRegPatronal);    		            	
        	FirmaElectronica firmaElectronica = new FirmaElectronica();
        	SujetoObligado sujetoObligado = new SujetoObligado();
        	sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
    		sujetoObligado.setCveIdSujetoObligado(new Long(idSujetoObligado));		
    		sujetoObligado=sujetoObligadoService.obtenerDetalleRP(sujetoObligado);
    		log.debug("<OTIKA>En el inicio de proceso de firma digital, id sujetoobligado"+idSujetoObligado);
    		log.debug("<OTIKA>En el inicio de proceso de firma digital"+sujetoObligado);
    		try {
    			//firmaElectronica.setCadenaOriginal(firmaElectronicaBusiness.obtenerCadenaAFirmar(solicitud,sujetoObligado));
    			firmaElectronica.setCadenaOriginal("CADENA|ORIGINAL|PRUEBA|CONTROLLER");    			    		    			
    			firmaElectronica.setIdSolicitud(new BigDecimal(idSolicitudActiva));
    	    	firmaElectronica.setRegistroPatronal(numRegPatronal);					
    			model.addAttribute("firmaElectronicaModel",firmaElectronica);
    		} catch (Exception e) {
    			e.printStackTrace();
    			log.debug("<OTIKA>"+e.getMessage());
    			//model.addAttribute("errorMessage", e.getMessage());
    		}
    					
        	log.debug("<OTIKA>La cadena original es:"+firmaElectronica.getCadenaOriginal());
        	session.setAttribute("firmaDigital",firmaElectronica);    			        	    		    	  			    	    	        	                				           
			model.addAttribute("idSujetoObligado",idSujetoObligado);
			return "captura.datos.firmaAfiliacion";
	}
	
	@RequestMapping(value = "/procesoFirmaDigitalNPIE", method = RequestMethod.POST)
	public String procesoFirmaDigitalNPIE (Model model,@ModelAttribute FirmaElectronica firmaElectronicaModel,
			@RequestParam String idSujetoObligado,
			HttpSession session) {
		log.debug("<OTIKA>procesoFirmaDigital: El id de la solicitud es:"+firmaElectronicaModel.getIdSolicitud());
		log.debug("<OTIKA>procesoFirmaDigital: El id del sujeto obligado:"+firmaElectronicaModel.getCadenaOriginal());		
    	Solicitud solicitud = new Solicitud();
    	Solicitud solicitudActualizada= new Solicitud();
    	SujetoObligado sujetoObligado = new SujetoObligado();
    	sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		sujetoObligado.setCveIdSujetoObligado(new Long(idSujetoObligado));		
		sujetoObligado=sujetoObligadoService.obtenerDetalleRP(sujetoObligado);
    	try{
	    	log.debug("FirmarSolicitud: El proceso de firma ha comenzado.");
			solicitud=firmaElectronicaBusiness.firmarSolicitud(firmaElectronicaModel);
	    	log.debug("FirmarSolicitud: El proceso de firma ha TERMINADO - AHORA REDIRECCIONA A --> redirect:/exitoSolicitudFirma?idSolicitud="
					+ firmaElectronicaModel.getIdSolicitud());								
			solicitudActualizada.setSolicitudId( new Long( (String.valueOf(firmaElectronicaModel.getIdSolicitud()))));
			solicitudActualizada = solicitudService.consultarSolicitudPorId(new Long(firmaElectronicaModel.getIdSolicitud().toString()));
			solicitudActualizada.setCadenaOriginal(solicitud.getCadenaOriginal());
			solicitudActualizada.setSecuenciaDeNotaria(solicitud.getSecuenciaDeNotaria());
			solicitudActualizada.setSelloDigital(solicitud.getSelloDigital());
			log.debug("<OTIKA>Se actualizará esta solicitud:"+solicitudActualizada);			
		}catch (RecursoRemotoNoDisponibleException e) {
			log.error("##### ERROR RecursoRemotoNoDisponibleException: " + e.getMessage());
		} catch (ErrorEnInvocacionRecursoRemotoException e) {
			log.error("##### ERROR ErrorEnInvocacionRecursoRemotoException: " + e.getMessage());			
		} catch (ModelAccessException e) {
			log.error("##### ERROR ModelAccessException: " + e.getMessage());
			e.printStackTrace();		
		} catch (RegistroPatronalInvalidoEnCertificadoException e) {
			log.error("##### ERROR Exception: " + e.getMessage());
		} catch (Exception e) {
			log.error("##### ERROR Exception: " + e.getMessage());
			log.debug("##### OCURRIO LA EXCEPTION RegistroPatronalInvalidoEnCertificadoException");
			e.printStackTrace();		
		}
		log.debug("<OTIKA>Ahora se firma la solicitud:"+solicitudActualizada.getCadenaOriginal());
		log.debug("<OTIKA>Ahora se firma la solicitud:"+solicitudActualizada.getSelloDigital());
		log.debug("<OTIKA>Ahora se firma la solicitud:"+solicitudActualizada.getSecuenciaDeNotaria());
		model.addAttribute("firmaElectronicaModel", firmaElectronicaModel);
		model.addAttribute("solicitudModel", solicitudActualizada);
		model.addAttribute("idSujetoObligado", idSujetoObligado);
		model.addAttribute("tipoPersonaFiscal", sujetoObligado.getTipoPersonaFiscal());
		if (sujetoObligado.getFisica()!=null){			
			model.addAttribute("personaFisica", true);
			model.addAttribute("fisicaIdPersona", sujetoObligado.getFisica().getIdPersona());
			model.addAttribute("moralRfcPersona", ((Usuario)session.getAttribute("usuario")).getFisica().getRfc());
		}
		else{			
			log.debug("<OTIKA>Persona::"+sujetoObligado);			
			model.addAttribute("personaFisica", false);
			model.addAttribute("moralIdPersona", sujetoObligado.getMoral().getIdPersona());
			model.addAttribute("moralRfcPersona", ((Usuario)session.getAttribute("usuario")).getMoral().getRfc());
		}						
		return "exito.Firma.SolicitudAfiliacion";
	}		

	@RequestMapping(value = "/validarSolicitudCompletes", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarSolicitud(
			@RequestBody Long idSolicitud,
			HttpServletResponse response, HttpSession session, Locale locale) {
		String mensaje = "";
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			afiliacionService.validaCondicionesDeActualizacionDeSolicitud(idSolicitud, usuario, null);
			result.put("mensajeError", "valido");
		} catch (GestionPatronalBusinessException e) {
			mensaje = messageSource.getMessage(e.getMessage(),null,locale);
			result.put("mensajeError", mensaje);
			e.printStackTrace();
		}
		System.err.println("Se envía la solicitud a ventanilla");		
		
		return result;
	}
	
	@RequestMapping(value = "/crearSolicitud", method = RequestMethod.POST)
	public String crearSolicitud(
			@ModelAttribute SujetoObligado sujetoObligado, Model model,
			HttpServletResponse response, HttpSession session) {
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		boolean bFisica = sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA);
		List<SujetoObligado> sujetosObligados;
		try {
			sujetosObligados = sujetoObligadoService
					.obtenerDetalleSujetoObligado(sujetoObligado);
			if (sujetosObligados.isEmpty()) {
				System.out.println("\n\n\n\n\n\n\n NO HAY SUJETOS OBLIGADOS ASOCIADOS A ESE RP");
				return "buscar.patron.rfc";
			}
			sujetoObligado = sujetosObligados.get(0);
			sujetoObligado.setSujetosObligados(sujetosObligados);
			sujetoObligado.setMoral(sujetosObligados.get(0).getMoral());
			sujetoObligado.setFisica(sujetosObligados.get(0).getFisica());
		} catch (AbstractException e) {
			e.printStackTrace();
			return "buscar.patron.rfc";
		}
		model.addAttribute("sujetoObligado", sujetoObligado);

		session.setAttribute("sujetoTramiteForRepLegal", sujetoObligado);
		gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL,TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.name(),"tramiteDenominacionActivo", "tramiteDenominacionRatificado", session,true);
		gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO,TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.name(),"tramiteDatosContactoActivo", "tramiteDatosContactoRatificado", session,true);
		//Los datos de REPRESENTANTE LEGAL Y SOCIOS se gestionan porque se debe saber si existe un trámite y si esta ratificado para ocultar o mostrar ciertos botones
		System.err.println("Se gestionan datos del tramite de rep legal_1");
		gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL,TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.name(),"tramiteRepresentanteLegalActivo", "tramiteRepresentanteLegalRatificado",session,true);
		
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_SOCIO,TipoTramiteEnum.ACTUALIZACION_SOCIO.name(),"tramiteSocioActivo", "tramiteSocioRatificado",session,true);
			gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA,TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.name(),"tramiteEscrituraConstitutivaActivo", "tramiteEscrituraConstitutivaRatificado", session,true);
			gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO,TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.name(),"tramiteRegistroSindicatoActivo", "tramiteRegistroSindicatoRatificado", session,true);
			obtenerRegistroVigente(model,sujetoObligado);			
		}
		
		System.err.println("evaluando si este usuario es un RL con actos de admon. o dominio");
		if (usuario != null && usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue()) ){
			boolean cuentaRLConActosDeAdmonDominio = this.cuentaRLConActosDeAdmonDominio(bFisica ? sujetoObligado.getFisica().getIdPersona() : sujetoObligado.getMoral().getIdPersona(), bFisica ? TipoPersonaEnum.FISICA : TipoPersonaEnum.MORAL, usuario );
			System.err.println("Este usuario es RL, actos de admon. o dominio: " + cuentaRLConActosDeAdmonDominio);
			session.setAttribute("cuentaRLConActosDeAdmonDominio", cuentaRLConActosDeAdmonDominio);
		} else {
			// aun si no es el usuario un RL se manda true para que se muestre la opcion de agregar nuevo rl.
			System.err.println("Este usuario NO es RL.");
			session.setAttribute("cuentaRLConActosDeAdmonDominio", true);
		}
		
		model.addAttribute("bFisica", bFisica);
		model.addAttribute("socio", new Socio());
		model.addAttribute("isOperadosIMSS",usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()));
		model.addAttribute("idSolicitud", null);
		
		TipoPersonaEnum tipoPersona = null;
		Long idPersona=null;

		if(bFisica){
			tipoPersona=TipoPersonaEnum.FISICA;
			idPersona = sujetoObligado.getFisica().getIdPersona();
		}else{
			tipoPersona=TipoPersonaEnum.MORAL;
			idPersona = sujetoObligado.getMoral().getIdPersona();
		}
		System.err.println("Obteniendo representantes para solicitado por....");
		List<RepresentanteLegal> representantesLegales = representanteLegalServiceBusiness.obtenerRepresentantesLegalesConActosAdmonPorPersona(idPersona, tipoPersona);
		model.addAttribute("listaRepresentantesSolicitantes",representantesLegales);
		System.err.println("Finaliza obteniendo representantes para solicitado por...."+representantesLegales);
		return "vista.tramite";
	}

	
	@RequestMapping(value = "/mostrarTramites", method = RequestMethod.POST)
	public String cargarVistaTramite(
			@ModelAttribute SujetoObligado sujetoObligado, Model model,
			@RequestParam("idSolicitud") Long idSolicitud,
			HttpServletResponse response, HttpSession session) {
		
		System.err.println("Cargando solicitud");
		System.err.println("TipoPersona: "+sujetoObligado.getTipoPersonaFiscal());
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		boolean bFisica = sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA);
		List<SujetoObligado> sujetosObligados;
		try {
			sujetosObligados = sujetoObligadoService
					.obtenerDetalleSujetoObligado(sujetoObligado);
			if (sujetosObligados.isEmpty()) {
				System.out.println("\n\n\n\n\n\n\n NO HAY SUJETOS OBLIGADOS ASOCIADOS A ESE RP");
				return "buscar.patron.rfc";
			}
			sujetoObligado = sujetosObligados.get(0);
			sujetoObligado.setSujetosObligados(sujetosObligados);
			sujetoObligado.setMoral(sujetosObligados.get(0).getMoral());
			sujetoObligado.setFisica(sujetosObligados.get(0).getFisica());
		} catch (AbstractException e) {
			e.printStackTrace();
			return "buscar.patron.rfc";
		}
		model.addAttribute("sujetoObligado", sujetoObligado);

		session.setAttribute("sujetoTramiteForRepLegal", sujetoObligado);
		Solicitud solicitud = solicitudService.consultarSolicitudPorId(idSolicitud);
		this.log.error("Solicitud Activa: "+solicitud);
		if(solicitud!=null){
			model.addAttribute("idSolicitud", solicitud.getSolicitudId());
			model.addAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
		}
		
		Persona persona = new Persona();
		TipoPersona tipoPersona = new TipoPersona();
		Long cveIdPersona = null;
		TipoPersonaEnum tipoPersonaRepresentada=null;
		List<RepresentanteLegal> representantesLegales=null;
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			persona.setIdPersona(sujetoObligado.getFisica().getIdPersona());
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			persona.setTipoPersona(tipoPersona);
			cveIdPersona = sujetoObligado.getFisica().getIdPersona();
			tipoPersonaRepresentada = TipoPersonaEnum.FISICA;
		}else{
			persona.setIdPersona(sujetoObligado.getMoral().getIdPersona());
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			persona.setTipoPersona(tipoPersona);
			cveIdPersona = sujetoObligado.getMoral().getIdPersona();
			tipoPersonaRepresentada = TipoPersonaEnum.MORAL;
		}
		
		cargarDatosTramite(persona, model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL,TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.name(),"tramiteDenominacionActivo", "tramiteDenominacionRatificado", session,idSolicitud);
		cargarDatosTramite(persona, model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO,TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.name(),"tramiteDatosContactoActivo", "tramiteDatosContactoRatificado", session,idSolicitud);
		//Los datos de REPRESENTANTE LEGAL Y SOCIOS se gestionan porque se debe saber si existe un trámite y si esta ratificado para ocultar o mostrar ciertos botones
		System.err.println("Se gestionan datos del tramite de rep legal_1");
		cargarDatosTramite(persona, model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL,TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.name(),"tramiteRepresentanteLegalActivo", "tramiteRepresentanteLegalRatificado",session,idSolicitud);
		
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			System.err.println("Cargando tramites de persona moral");
			cargarDatosTramite(persona, model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_SOCIO,TipoTramiteEnum.ACTUALIZACION_SOCIO.name(),"tramiteSocioActivo", "tramiteSocioRatificado",session,idSolicitud);
			cargarDatosTramite(persona, model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA,TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.name(),"tramiteEscrituraConstitutivaActivo", "tramiteEscrituraConstitutivaRatificado", session,idSolicitud);
			cargarDatosTramite(persona, model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO,TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.name(),"tramiteRegistroSindicatoActivo", "tramiteRegistroSindicatoRatificado", session,idSolicitud);
			obtenerRegistroVigente(model,sujetoObligado);			
		}
		boolean existeSolicitud = idSolicitud!=null && idSolicitud>0 ? true : false;
		log.debug("<OTIKA>!!!!!!!!!!!!!");
		model.addAttribute("bFisica", bFisica);
		model.addAttribute("socio", new Socio());
		model.addAttribute("solicitudEnProceso", existeSolicitud);
//		gestionarTramitePendienteDeAutorizar(model, sujetoObligado);
		model.addAttribute("isOperadosIMSS",usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()));
		if (usuario != null && usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())){
			boolean cuentaRLConActosDeAdmonDominio = this.cuentaRLConActosDeAdmonDominio(bFisica ? sujetoObligado.getFisica().getIdPersona() : sujetoObligado.getMoral().getIdPersona(), bFisica ? TipoPersonaEnum.FISICA : TipoPersonaEnum.MORAL, usuario );
			System.err.println("Este usuario es RL, actos de admon. o dominio: " + cuentaRLConActosDeAdmonDominio);
			session.setAttribute("cuentaRLConActosDeAdmonDominio", cuentaRLConActosDeAdmonDominio);
		} else {
			// aun si no es el usuario un RL se manda true para que se muestre la opcion de agregar nuevo rl.
			System.err.println("Este usuario NO es RL.");
			session.setAttribute("cuentaRLConActosDeAdmonDominio", true);
		}
		
		System.err.println("Obteniendo representantes para solicitado por....");
		representantesLegales = representanteLegalServiceBusiness.obtenerRepresentantesLegalesConActosAdmonPorPersona(cveIdPersona, tipoPersonaRepresentada);
		model.addAttribute("listaRepresentantesSolicitantes",representantesLegales);
		System.err.println("Finaliza representantes para solicitado por.... size: "+representantesLegales.size());

		
		return "vista.tramite";
	}
	
		
	private void obtenerRegistroVigente(Model model, SujetoObligado sujetoObligado){
		if (sujetoObligado.getMoral().getRegistroSindicato()!=null){
			if (sujetoObligado.getMoral().getRegistroSindicato().getCveRegistroSindicato()!=null)				
				model.addAttribute("vigenteRegistroSindicatoActivo", true);
		}
		if (sujetoObligado.getMoral().getEscrituraConstitutiva()!=null){
			if (sujetoObligado.getMoral().getEscrituraConstitutiva().getCveEscrituraConstitutiva()!=null)				
				model.addAttribute("vigenteActaConstitutivaActivo", true);
		}
	}
	
	private void cargarDatosTramite(Persona persona, Model model, SujetoObligado sujetoObligado, TipoTramiteEnum tipoTramite, String varNombreTramite, String varStatusTramite, String varTramiteRatificado, HttpSession session, Long idSolicitud){
//		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		Map<String,Object> result = afiliacionService.cargarDatosDeTramite(tipoTramite, persona, idSolicitud);
		System.err.println("subiendo al model tramiteActivo"+ (Boolean)result.get("tramiteActivo"));
		System.err.println("subiendo al model tramiteData"+(SujetoObligado)result.get("tramiteData"));
		System.err.println("subiendo al model tramiteRatificado"+(Boolean)result.get("tramiteRatificado"));
		model.addAttribute(varStatusTramite,(Boolean)result.get("tramiteActivo"));
		model.addAttribute(varNombreTramite, (SujetoObligado)result.get("tramiteData"));
		model.addAttribute(varTramiteRatificado, (Boolean)result.get("tramiteRatificado"));
		model.addAttribute("folioSolicitud", (String)result.get("folioSolicitud"));
		if (TipoTramiteEnum.ACTUALIZACION_SOCIO.name().equals(varNombreTramite)){
			System.err.println("Subiendo socio  sesion");
			session.setAttribute(varNombreTramite, (SujetoObligado)result.get("tramiteData"));
		}
		if (TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.name().equals(varNombreTramite)){
			SujetoObligado sujetoTramite = (SujetoObligado) result.get("tramiteData");
			sujetoTramite.setTipoPersonaFiscal(sujetoObligado.getTipoPersonaFiscal());
			session.setAttribute("sujetoTramiteForRepLegal", sujetoTramite);
			session.setAttribute(varNombreTramite, sujetoTramite);
		}
		
		
		
		if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA)){
			SujetoObligado sujetoTr=(SujetoObligado)result.get("tramiteData");
			
			if(sujetoTr!= null && sujetoTr.getMoral()!=null && sujetoTr.getMoral().getEscrituraConstitutiva()!= null 
					&& sujetoTr.getMoral().getEscrituraConstitutiva().getLugarExpedicion()!= null){
			
			model.addAttribute("claveMun",sujetoTr.getMoral().getEscrituraConstitutiva().getLugarExpedicion().getClave());
			model.addAttribute("claveEdo",sujetoTr.getMoral().getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa().getClave());
			}
		}
		if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
			
			Long idTipoSociedad = 0L;
			List<MedioContacto> mFiscales = null;
			try{
				SujetoObligado sujetoTr=(SujetoObligado)result.get("tramiteData");
				this.log.error("Tipo Persona Tramite: "+ sujetoObligado.getTipoPersonaFiscal());
				System.err.println("Tipo Persona Tramite: "+ sujetoObligado.getTipoPersonaFiscal());
				session.setAttribute("datosMDM", sujetoTr.getDatosMDM());
				session.setAttribute("datosICA", sujetoTr.getDatosICA());
				if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
					idTipoSociedad = sujetoTr.getMoral().getTipoSociedad().getIdTipoSociedad();
					System.err.println("TIPO SOCIEDAD DE DATOS GENERALES..."+idTipoSociedad);
					model.addAttribute("idTipoSociedadVar", idTipoSociedad);	
					mFiscales = sujetoTr.getMoral().getMediosContactoFiscales() !=null ?
							sujetoTr.getMoral().getMediosContactoFiscales() : new ArrayList<MedioContacto>();
					System.err.println("Medios Fiscales: "+sujetoTr.getMoral().getMediosContactoFiscales());
					session.setAttribute("listaMediosContactoFiscales", mFiscales);
					model.addAttribute("listaMediosContactoFiscales",mFiscales);
				}else{
					mFiscales = sujetoTr.getFisica().getMediosContactoFiscales() !=null ?
							sujetoTr.getFisica().getMediosContactoFiscales() : new ArrayList<MedioContacto>();
					session.setAttribute("listaMediosContactoFiscales", mFiscales);
					model.addAttribute("listaMediosContactoFiscales",mFiscales);
				}
			}catch(NullPointerException npe){
				log.error("Error al tratar Tipo Sociedad para persona fisica, parece que no sabes!: " + npe.getMessage());
			} finally{
				model.addAttribute("idTipoSociedadVar", idTipoSociedad);
			}
		}
	}
	
	@RequestMapping(value = "/visualizarDetalleRFC", method = RequestMethod.POST)
	public String mostrarDetalleRFC(
			@ModelAttribute SujetoObligado inputObject, Model model,
			SessionStatus status, HttpServletResponse response, HttpSession session, Locale locale, HttpServletRequest request) {
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())
			|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())	){
			Socio sujetoOrigen = new Socio();		
			String rfc = inputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA) ? inputObject.getFisica().getRfc() : inputObject.getMoral().getRfc();
			sujetoOrigen.setRfc(rfc);
			System.err.println("sujeto al visualizar: "+inputObject);
			System.err.println("rfc al visualizar: "+rfc);
			DataBinder dataBinder = new DataBinder(sujetoOrigen);   
			BindingResult result = dataBinder.getBindingResult();
			model.addAttribute("socio", sujetoOrigen);
			return patronController.buscarSujetoObligadoPorRFC(sujetoOrigen, result, model, session);
		}else{
			DataBinder dataBinder = new DataBinder(usuario);   
			BindingResult result = dataBinder.getBindingResult();
			return loginController.entrar(usuario, result, model, status, session, request);
		}
	}
	
	/**
	 * Ajusta la descripción de los mensajes del trámite si es que fueron ratificados
	 * 
	 * 
	 * @author Hugo Martinez
	 * @Date 08/11/2012
	 * @param solicitudesEnProceso
	 * @param locale
	 * @return
	 */
	private List<Solicitud> ajustarDescripcionDeTramites(List<Solicitud> solicitudesEnProceso,  Locale locale){
		List<Solicitud> solicitudesActualizadas = new ArrayList<Solicitud>();
		for(Solicitud solicitud : solicitudesEnProceso){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
			String fecha = sdf.format(solicitud.getFechaSolicitud());
			solicitud.setFechaSolicitudParse(fecha);
			System.err.println("Fecha de solicitud after: "+fecha);
			
			
			String fechaPresentacionSolicitud = solicitud.getFechaPresentacion()!= null ? sdf.format(solicitud.getFechaPresentacion()) : "";
			String fechaConclusionSolicitud = solicitud.getFechaConclusion()!= null ? sdf.format(solicitud.getFechaConclusion()) : "";
			solicitud.setFechaPresentacionParse(fechaPresentacionSolicitud);
			solicitud.setFechaConclusionParse(fechaConclusionSolicitud);
			
			
			List<Tramite> tramitesActualizados = new ArrayList<Tramite>();
			for(Tramite tramite : solicitud.getTramites()){
				System.err.println("Es trámite ratificado: "+tramite.getIndRatificado());
				boolean ratificado = tramite.getIndRatificado()!=null ? tramite.getIndRatificado() : false;
				if(ratificado){
					String mensajeRatificacion="";
					if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.denominacion",null,locale);
					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.contacto",null,locale);
					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.representante",null,locale);
					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.socio",null,locale);
					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.acta.constitutiva",null,locale);
					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.registro.sindicato",null,locale);
					}
					tramite.getTipoTramite().setDescripcion(mensajeRatificacion);
					System.err.println("Descripcion tipo de tramite: "+tramite.getTipoTramite().getDescripcion());
				}
				tramitesActualizados.add(tramite);
			}
			solicitud.setTramites(tramitesActualizados);
			solicitudesActualizadas.add(solicitud);
		}
		
		return solicitudesActualizadas;
	}
	
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 09/08/2012
	 * @param params
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/listarTotalDeSolicitudesEnProceso", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<Solicitud> listarTodasLasSolicitudesEnProceso(
			@RequestBody SolicitudDataTable params, HttpSession session, Locale locale) {
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		DatosSalidaPaginador<Solicitud> output = new DatosSalidaPaginador<Solicitud>();
		List<Solicitud> solicitudesEnProceso = solicitudService.listarSolicitudesGlobalesEnProceso(usuario.getCveIdSubdelegacion());
		
		DatosEntradaPaginador<Solicitud> input = new DatosEntradaPaginador<Solicitud>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		input.setiDisplayStart(0);
		List<Solicitud> solicitudesActualizadas = new ArrayList<Solicitud>();
		solicitudesActualizadas = ajustarDescripcionDeTramites(solicitudesEnProceso, locale);
				
		output.setiTotalDisplayRecords(input.getiDisplayStart());
		output.setiTotalRecords(solicitudesEnProceso.size());
		output.setAaData(solicitudesActualizadas);
		output.setsEcho(input.getsEcho());
		
		return output;
	}
	
	
	@RequestMapping(value = "/validaRegistroPatronalExistente", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validaRegistroPatronalExistente(
			@RequestBody SujetoObligado inputObject,
			Model model, 
			HttpSession session, Locale locale) {
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		System.err.println("Evaluando selección de solicitud......... ");
		Map<String, Object> result = new HashMap<String, Object>();
		System.err.println("Tipo Persona: "+inputObject.getTipoPersonaFiscal());
		inputObject = sujetoObligadoService.consultarPorNumeroRegistroPatronal(inputObject.getNumeroRegistroPatronal());
		
		if(inputObject == null){
			Object[] args = null;
			String mensaje = messageSource.getMessage("error.registro.patronal.inexistente", args, locale);
			result.put("mensajeError", mensaje);
		}
		
//		System.err.println("Se evalua subdelegacion de atencion");
//		System.err.println("Subdelegacion de usuario: "+usuario.getCveIdSubdelegacion());
//		System.err.println("Subdelegacion de rp: "+inputObject.getSubdelegacion().getId());
		
		if(usuario.getCveIdSubdelegacion()!=null && usuario.getCveIdSubdelegacion() > 0 
				&& inputObject!=null 
				&& !inputObject.getSubdelegacion().getId().equals(usuario.getCveIdSubdelegacion()) ){
			Object[] args = new Object[]{inputObject.getSubdelegacion().getDescripcion()};
			String mensaje = messageSource.getMessage("error.registro.patronal.externo", args, locale);
			result.put("mensajeError", mensaje);
			
		}
		
		
		return result;
	}
	
	@RequestMapping(value = "/validaTramiteCentroTrabajoExistente", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarExisteTramiteCentroTrabajo(
			@RequestBody Long cveIdPatronSujetoObligado, HttpServletResponse response, HttpSession session) {
		System.err.println("Estoy validando.....");
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		boolean esTramitador = 
				usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()) 
				? true : false;
		SujetoObligado sujeto = new SujetoObligado();
		sujeto.setCveIdSujetoObligado(cveIdPatronSujetoObligado);
		boolean tramiteCentroTrabajoExistente = false;
		
		if(!esTramitador)
			if(solicitudServiceBusiness.obtenerSolicitudActiva(sujeto, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO,usuario, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO)!=null)
				tramiteCentroTrabajoExistente = true;
		
		result.put("tramiteEnCurso", tramiteCentroTrabajoExistente);
		return result;
	}
	
	@RequestMapping(value = "/almacenarTemporalmenteDatosICA", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosICA(
			@RequestBody ICADatosRespuesta datosICA, HttpServletResponse response, HttpSession session) {
		System.err.println("Subiendo datos ICA.....");
		Map<String, Object> result = new HashMap<String, Object>();
		session.setAttribute("datosICA", datosICA);
		session.removeAttribute("datosMDM");
		System.err.println("Datos ICA  a publicar: "+datosICA);
		result.put("datosICACorrectos", true);
		return result;
	}
	
	@RequestMapping(value = "/almacenarTemporalmenteDatosMDM", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosMDM(
			@RequestBody MDMDatosEntrada datosMDM, HttpServletResponse response, HttpSession session) {
		System.err.println("Subiendo datos MDM.....");
		Map<String, Object> result = new HashMap<String, Object>();
		session.setAttribute("datosMDM", datosMDM);
		session.removeAttribute("datosICA");
		System.err.println("Datos ICA  a publicar: "+datosMDM);
		result.put("datosMDMCorrectos", true);
		return result;
	}
	
	@RequestMapping(value = "/mostrarCentroTrabajo", method = RequestMethod.POST)
	public String mostrarCentroTrabajo(@ModelAttribute SujetoObligado inputObject, Model model, 
			HttpSession session) {
		log.error("inpuObject registroPatronal: "+inputObject.getNumeroRegistroPatronal());
		log.error("inpuObject Tipo Persona: "+inputObject.getTipoPersonaFiscal());
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		SujetoObligado outputObject = afiliacionService.obtenerDetalleDeRegistroPatronal(inputObject);
		gestionarDatosTramite(model, outputObject, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO,
				TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.name(),"tramiteCentroTrabajoActivo", "tramiteCentroTrabajoRatificado", 
				session,true);
		Solicitud solicitud = solicitudService.obtenerSolicitudActiva(outputObject, 
				TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, usuario, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO);
		
		if(solicitud!=null){
			model.addAttribute("idSolicitudCT", solicitud.getSolicitudId());
		}
		boolean isOperadorIMSS = usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());

		model.addAttribute("sujetoObligado", outputObject);
		model.addAttribute("isOperadorIMSS", isOperadorIMSS);
		return "centro.trabajo";
	}
	
	
	@RequestMapping(value = "/cargarTramiteCentroTrabajo", method = RequestMethod.POST)
	public String cargarTramiteCentroTrabajo(@ModelAttribute SujetoObligado inputObject, Model model,
			@RequestParam("idSolicitud") Long idSolicitud,
			HttpSession session) {
		log.error("inpuObject registroPatronal: "+inputObject.getNumeroRegistroPatronal());
		log.error("inpuObject Tipo Persona: "+inputObject.getTipoPersonaFiscal());
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		SujetoObligado outputObject = afiliacionService.obtenerDetalleDeRegistroPatronal(inputObject);
		Persona persona = new Persona();
		TipoPersona tipoPersona = new TipoPersona();
		if(outputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			persona.setIdPersona(outputObject.getFisica().getIdPersona());
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			persona.setTipoPersona(tipoPersona);
		}else{
			persona.setIdPersona(outputObject.getMoral().getIdPersona());
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			persona.setTipoPersona(tipoPersona);
		}

		cargarDatosTramite(persona, model, inputObject, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO, 
				TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.name(),"tramiteCentroTrabajoActivo", 
				"tramiteCentroTrabajoRatificado", session, idSolicitud);
//		Solicitud solicitud = solicitudService.obtenerSolicitudActiva(outputObject, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, usuario);
		
		if(idSolicitud!=null && idSolicitud > 0){
			model.addAttribute("idSolicitudCT", idSolicitud);
		}
		model.addAttribute("sujetoObligado", outputObject);
		
		boolean isOperadorIMSS = usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
		model.addAttribute("isOperadorIMSS", isOperadorIMSS);
		
		return "centro.trabajo";
	}
	
	@RequestMapping(value = "/cargarPantallaDeBaja", method = RequestMethod.GET)
	public String cargarPantallaBajaPatronal(Model model,
			HttpSession session) {
		
		return "vista.baja";
	}
	
	
	@RequestMapping(value="/validaPermisos", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody Map<String, ? extends Object> validarPermisosDeEjecucion(
			@RequestBody String rfc,
			HttpServletResponse response, HttpSession session, Locale locale) {
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		System.err.println("Estoy en action de validacion");
		System.err.println(rfc);
		try {
			afiliacionService.validaCondicionesDeActualizacionDeSolicitud(null, usuario, rfc);
			System.err.println("paso validacion");
		} catch (GestionPatronalBusinessException e) {
			System.err.println("No paso validacion");
			String mensaje = messageSource.getMessage(e.getMessage(),null,locale);
			result.put("mensajeError", mensaje);
			return result;
		}
		System.err.println("Se agrega mensaje de exito");
		result.put("mensajeExito","exito");
		return result;
	}
	
	private boolean cuentaRLConActosDeAdmonDominio(Long idPersona, TipoPersonaEnum tipoPersonaEnum, Usuario usuario){
		boolean result = false;
		RepresentanteLegal repLegalporIdPersonaFisica;
		
		try {
			repLegalporIdPersonaFisica = this.representanteLegalServiceBusiness.obtenerRLporTipoPatronYIdPersonaRepresentante(idPersona, tipoPersonaEnum, usuario.getFisica().getIdPersona());
			if (repLegalporIdPersonaFisica != null){
				result = repLegalporIdPersonaFisica.getIndActAdmonDominio().intValue() == 1 ? true : false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 05/02/2013
	 * @param session
	 * @param tipoSolicitante
	 * @param idSolicitante
	 */
	public void gestionarSolicitanteConclusion(HttpSession session, SujetoObligado sujetoTramite, CodigoRolTemporal tipoSolicitante, Long idSolicitante){
		Fisica fisica = null;
		Usuario usuario = new Usuario();
		if(tipoSolicitante.equals(CodigoRolTemporal.REPRESENTANTE_LEGAL)){
			fisica = (Fisica)sujetoObligadoService.obtenerPersonaPorIdentificador(idSolicitante);
			usuario.setFisica(fisica);
			usuario.setNomNombre(fisica.getNombre());
			usuario.setNomPaterno(fisica.getPrimerApellido());
			usuario.setNomMaterno(fisica.getSegundoApellido());
			usuario.setPerfilUsuario(new PerfilUsuario());
			usuario.getPerfilUsuario().setIdPerfilUsuario(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue());
			usuario.getPerfilUsuario().setDescripcion(CodigoRolTemporal.REPRESENTANTE_LEGAL.name());
		}else if(tipoSolicitante.equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO)){
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				usuario.setNomNombre(sujetoTramite.getFisica().getNombre());
				usuario.setNomPaterno(sujetoTramite.getFisica().getPrimerApellido());
				usuario.setNomMaterno(sujetoTramite.getFisica().getSegundoApellido());
				usuario.setFisica(sujetoTramite.getFisica());
			}else{
				usuario.setNomNombre(sujetoTramite.getMoral().getRazonSocial());
				usuario.setNomPaterno("");
				usuario.setNomMaterno("");
				usuario.setMoral(sujetoTramite.getMoral());
			}
			usuario.setPerfilUsuario(new PerfilUsuario());
			usuario.getPerfilUsuario().setIdPerfilUsuario(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue());
			usuario.getPerfilUsuario().setDescripcion(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.name());
		}
		System.err.println("Tipo Solicitante: "+tipoSolicitante);
		System.err.println("Solicitante: "+usuario);
		
		session.setAttribute("solicitante", usuario);
	}
	
	@RequestMapping(value = "/obtenerDetalleDePersona", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> obtenerDetallePersona(
			@RequestBody Long idPersona, HttpServletResponse response, HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Fisica fisica = new Fisica();
		fisica.setNombre("");
		fisica.setPrimerApellido("");
		fisica.setSegundoApellido("");
		fisica.setCurp("");
		fisica.setRfc("");
		if(idPersona > 0)
			fisica = (Fisica)sujetoObligadoService.obtenerPersonaPorIdentificador(idPersona);
		
		result.put("persona", fisica);
		
		return result;
	}
	
	@RequestMapping(value = "/obtenerRepresentantesDisponibles", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> obtenerRepresentantesDisponibles(
			@RequestBody Solicitud solicitud, HttpServletResponse response, 
			@RequestParam("esPatronFisico") boolean esPatronFisico,
			HttpSession session) {
		System.err.println("Obteniendo Representantes en Tramite");
		Map<String,Object> result = new HashMap<String, Object>();
		List<RepresentanteLegal> representantesEnTramite = obtenerRepresentantesDisponiblesEnTramite(esPatronFisico, solicitud);
		List<RepresentanteLegal> representantesActuales = obtenerRepresentantesDisponiblesActuales(solicitud, esPatronFisico);
		
		List<RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		
		representantesLegales.addAll(representantesActuales);
		representantesLegales.addAll(representantesEnTramite);
		
		result.put("representantesDisponibles", representantesLegales);
		return result;
	}
	
	private List<RepresentanteLegal> obtenerRepresentantesDisponiblesEnTramite(boolean esPatronFisico, Solicitud solicitud){
		List<RepresentanteLegal> representantesEnTramiteDisponibles = new ArrayList<RepresentanteLegal>();
		Persona persona = new Persona();
		persona.setTipoPersona(new TipoPersona());
		if(esPatronFisico){
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			persona.setIdPersona(solicitud.getSujetoObligado().getFisica().getIdPersona());
		}else{
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			persona.setIdPersona(solicitud.getSujetoObligado().getMoral().getIdPersona());
		}
		Map<String,Object> result = afiliacionService.cargarDatosDeTramite(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL, persona, solicitud.getSolicitudId());
		
		Boolean tramiteActivo = (Boolean)result.get("tramiteActivo");
		SujetoObligado datosTramite = (SujetoObligado)result.get("tramiteData");
		
		List<RepresentanteLegal> representantesEnTramite = new ArrayList<RepresentanteLegal>();
		if(tramiteActivo){
			if(esPatronFisico)
				representantesEnTramite = datosTramite.getFisica().getRepresentantesLegales();
			else
				representantesEnTramite = datosTramite.getMoral().getRepresentantesLegales();
			
			if(representantesEnTramite== null){
				representantesEnTramite = new ArrayList<RepresentanteLegal>();
			}else{
				for(RepresentanteLegal representante : representantesEnTramite){
					System.err.println("Analizando representante: "+representante);
					if(representante.getIndActAdmonDominio()!= null){
						System.err.println(" Comparando [ " +representante.getIndActAdmonDominio() +" ] ["+representante.getAccion()+"] ");
					}
					if(representante.getIndActAdmonDominio()!= null && representante.getIndActAdmonDominio().intValue()==1 && representante.getAccion().equals(TipoAccionAfectacionEnum.AGREGAR)){
						System.err.println("accion: "+representante.getAccion());
						System.err.println("actos: "+representante.getIndActAdmonDominio());
						System.err.println("representante: "+representante.getPersonaFisica());
						representantesEnTramiteDisponibles.add(representante);
					}
				}
			}
		}
		Integer nrepdisp = representantesEnTramiteDisponibles.size();
		System.err.println("Numero de representantes disponibles en tramite: "+nrepdisp);
		
		return representantesEnTramiteDisponibles;
	}
	
	private List<RepresentanteLegal> obtenerRepresentantesDisponiblesActuales(Solicitud solicitud, boolean esPatronFisico){
		List<RepresentanteLegal> representantesActuales = new ArrayList<RepresentanteLegal>();
		Long idPersona = null;
		TipoPersonaEnum tipoPersona=null;
		if(esPatronFisico){
			idPersona = solicitud.getSujetoObligado().getFisica().getIdPersona();
			tipoPersona = TipoPersonaEnum.FISICA;
		}else{
			idPersona = solicitud.getSujetoObligado().getMoral().getIdPersona();
			tipoPersona = TipoPersonaEnum.MORAL;
		} 
				
		representantesActuales = representanteLegalServiceBusiness.obtenerRepresentantesLegalesConActosAdmonPorPersona(idPersona, tipoPersona);
		
		if(representantesActuales==null)
			representantesActuales = new ArrayList<RepresentanteLegal>();
		
		Integer nrepdisp = representantesActuales.size();
		System.err.println("Numero de representantes disponibles actuales: "+nrepdisp);
		
		
		return representantesActuales;
	}
	
}
