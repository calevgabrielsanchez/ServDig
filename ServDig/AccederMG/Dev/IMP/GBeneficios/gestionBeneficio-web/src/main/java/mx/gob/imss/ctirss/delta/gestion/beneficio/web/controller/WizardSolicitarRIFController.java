package mx.gob.imss.ctirss.delta.gestion.beneficio.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.PersonaNoValidaBeneficioRissException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceImssRissException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.ReportesBeneficiosBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.web.utils.CommonValidator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.enums.TipoBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

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


@Controller
@RequestMapping(value = "/wizard/tramite/solicitar/riss")
public class WizardSolicitarRIFController extends AbstractController {

	@Autowired
	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusiness;
	@Autowired
	private ReportesBeneficiosBusinessRemote reportesBeneficiosBusiness;
	
	//variables del tipo de tramite y solicitud
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_ATRIBUTE_BENEFICIO = "beneficio";
	private static final String KEY_ATRIBUTE_SOLICITUD = "solicitud";
	private static final String KEY_ATRIBUTE_MENSAJE = "mensaje";
	private static final String KEY_ATRIBUTE_ERROR = "error";	
	//Pantallas
	private static final String INICIO_WIZARD_RISS = "wizardSolicitarRissInit";
	private static final String CONTENIDO_WIZARD_RISS = "wizardSolicitarRissContenido";
	//Mensajes
	private static final String MSG_FINALIZAR_SOLICITUD = "Tu solicitud ha finalizado correctamente";
	private static final String MSG_CANCELAR_SOLICITUD = "La solicitud fue cancelada correctamente";
	private static final String MSG_CANCELAR_SOLICITUD_ERROR = "Error al cancelar la solicitud: ";
	
	
	
	@RequestMapping(value = "/{rfc}/{idPersona}", method = RequestMethod.GET)
	public String initSolicitarRIF(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable String rfc, @PathVariable Long idPersona) {
		
		Beneficio beneficio = new Beneficio();
		beneficio.setFisica(new Fisica());			
		beneficio.getFisica().setRfc(rfc);
		beneficio.getFisica().setIdPersona(idPersona);
		
		//Se obtiene origen desde request
		OrigenSolicitudEnum idOrigen =  new CommonValidator().getOrigenContext(request);
		
		Solicitud solicitudProceso = beneficioRissServiceBusiness.validarSolicitudRissEnProceso(beneficio.getFisica(), idOrigen);
		
		validarSolicitudEnProceso(request, solicitudProceso);
		
		model.addAttribute(KEY_ATRIBUTE_BENEFICIO, beneficio);
		
		return INICIO_WIZARD_RISS;
	}
	
	@RequestMapping(value = "/crear/solicitud", method = RequestMethod.POST)
	public String crearSolicitud(@ModelAttribute Beneficio beneficio,
			final HttpSession session, HttpServletRequest request,
			final Model model) {
		Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);
		Solicitud solicitud = null;
		OrigenSolicitudEnum idOrigen = null;
		
		try {
			//Se obtiene origen desde request
			idOrigen =  new CommonValidator().getOrigenContext(request);
			
			//Validar si cuenta con RPs Pendientes para notificar.
			String msgRPSPendientes = beneficioRissServiceBusiness
				.indicarRPsPendientes(beneficio.getFisica(), idOrigen);
			
			beneficio = beneficioRissServiceBusiness
				.obtenerPersonaBeneficio(beneficio.getFisica(), idOrigen.getId(), false);
			solicitud = this.beneficioRissServiceBusiness.crearSolicitudRiss(
				beneficio, usuario, idOrigen.getId());
			beneficio = beneficioRissServiceBusiness
				.validarSolicitudRiss(solicitud, beneficio);
			session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.INCORPORACION_BENEFICIO.getValor());
			session.setAttribute(KEY_DESC_TIPO_SOLICITUD, TipoBeneficioEnum.RIF.getDesc());
			List<Integer> listTipoTramite = new ArrayList<Integer>();
			listTipoTramite.add(TipoTramiteEnum.ALTA_RIF.getCodigo());
			session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);			
			
			if(idOrigen.getId() == OrigenSolicitudEnum.INTERNET.getId()){
				obtenerDatosAcuse(solicitud, beneficio.getFisica(), session);
				generarCadenaOriginal(solicitud, beneficio.getFisica(), session);
			}
			
			model.addAttribute(KEY_ATRIBUTE_BENEFICIO, beneficio);
			model.addAttribute(KEY_ATRIBUTE_SOLICITUD, solicitud);			
			session.setAttribute(KEY_ATRIBUTE_BENEFICIO, beneficio);			
			request.setAttribute("msgRPSPendientes", msgRPSPendientes);
			
		} catch (PersonaNoValidaBeneficioRissException e) {
			this.log.error(e);
			e.printStackTrace();
			request.setAttribute(KEY_ATRIBUTE_ERROR, e.getMessage());
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			e.printStackTrace();
			request.setAttribute(KEY_ATRIBUTE_ERROR, e.getMessage());
		} catch (BeneficioRissException e) {
			
			String msgMotivoRechazo = null;
			
			if (StringUtils.isBlank(e.getMessage())) {
				msgMotivoRechazo = "Ha ocurrido un error inesperado";
			} else {
				msgMotivoRechazo = e.getMessage();
			}
			
			try {
				beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
			} catch (AbstractException ae) {
				this.log.error(ae);
				ae.printStackTrace();
			}			
			this.log.error(e);
			e.printStackTrace();
			request.setAttribute(KEY_ATRIBUTE_ERROR, msgMotivoRechazo);
		} catch (ClienteWebserviceImssRissException e) {
			this.log.error(e);
			
			String msgMotivoRechazo = null;
			
			if (StringUtils.isBlank(e.getMessage())) {
				msgMotivoRechazo = "Ha ocurrido un error inesperado";
			} else {
				msgMotivoRechazo = e.getMessage();
			}
			
			try {
				beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
			} catch (AbstractException ae) {
				this.log.error(ae);
				ae.printStackTrace();
			}		
			request.setAttribute(KEY_ATRIBUTE_ERROR, msgMotivoRechazo);
		}
	
		return CONTENIDO_WIZARD_RISS;
	}

	@RequestMapping(value = "/finalizar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitudModificacionDatos(
			@PathVariable Long idSolicitud,
			@RequestBody Beneficio beneficio, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();		
		OrigenSolicitudEnum idOrigen =  new CommonValidator().getOrigenContext(request);
		FirmaElectronica firma = null;
		
		if(idOrigen.getId() == OrigenSolicitudEnum.INTERNET.getId()){
			firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
		}
		
		beneficio = (Beneficio) session.getAttribute(KEY_ATRIBUTE_BENEFICIO);
		
		try {
			beneficioRissServiceBusiness.encolarSolicitudRiss(idSolicitud, firma);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_FINALIZAR_SOLICITUD);
		} catch (AbstractException e) {
			this.log.error(e);
			result.put(KEY_ATRIBUTE_MENSAJE, e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitudModificacionDatos(
			@PathVariable Long idSolicitud, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> result = cancelarSolicitud(idSolicitud,"SOLICITUD CANCELADA POR USUARIO DE INTERNET");		
		return result;
	}
	
	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		log.info("Se almacenan los datos de la firma digital de forma temporal " + firmaElectronica);
		return null;
	}
	
	private void obtenerDatosAcuse(Solicitud solicitud, Persona persona, HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosAcuse = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		datosAcuse.setFechaElectronicaFormateada(strFechaElectronica);
		datosAcuse.setFechaElectronica(Calendar.getInstance().getTime());		
		
		// RFC
		datosAcuse.setRfc(persona.getRfc());
		if (persona instanceof Fisica) {
			// Nombre
			StringBuffer sbnombre = new StringBuffer();
			sbnombre.append(((Fisica)persona).getNombre().trim()).append(" ");
			sbnombre.append(((Fisica)persona).getPrimerApellido().trim()).append(" ");
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
			datosAcuse.setNombreCompleto(sbnombre.toString());
			datosAcuse.setCurp(((Fisica)persona).getCurp());			
		}

		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosAcuse);
	}
	
	private void generarCadenaOriginal(Solicitud solicitud, Persona persona, HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(Calendar.getInstance().getTime());
		// RFC
		datosEntradaFirma.setRfc(persona.getRfc());
		//Datos de la persona fisica
		if (persona instanceof Fisica) {
			// Nombre
			StringBuffer sbnombre = new StringBuffer();
			sbnombre.append(((Fisica)persona).getNombre().trim()).append(" ");
			sbnombre.append(((Fisica)persona).getPrimerApellido().trim()).append(" ");
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}			
			datosEntradaFirma.setNombreCompleto(sbnombre.toString());			
			// CURP			
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());		
		}
		String cadenaOriginal = reportesBeneficiosBusiness.generarCadenaOriginal(solicitud, persona);
		session.setAttribute(KEY_CADENA_ORIGINAL, cadenaOriginal);
	}
	
	private Map<String, Object> cancelarSolicitud(Long idSolicitud, String causaCancelacion){		
		Map<String, Object> result = new HashMap<String, Object>();		
		try {
			Solicitud solicitud = this.beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(
				idSolicitud, causaCancelacion, false);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_CANCELAR_SOLICITUD);
			result.put(KEY_ATRIBUTE_SOLICITUD, solicitud);			
		} catch (AbstractException e) {
			this.log.error(e);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_CANCELAR_SOLICITUD_ERROR + e.getMessage());
		}	
		return result;
	}

	private void validarSolicitudEnProceso(HttpServletRequest request,Solicitud solicitudProceso){
		if(solicitudProceso!=null){
			log.warn("Solicitud en proceso " + solicitudProceso.getNoFolioSolicitud());
			request.setAttribute("solicitudEnProceso", true);
			int cveEdoSolicitud = solicitudProceso.getEstadoSolicitud().getIdEstadoSolicitud().intValue();
			if (cveEdoSolicitud == EstadoSolicitudEnum.REGISTRADA.getCodigo().intValue()) {
				request.setAttribute("msgError", "Ya cuentas con una solicitud registrada.");
				request.setAttribute("solicitudRegistrada", true);
				request.setAttribute("folioSolicitudRegistrada", solicitudProceso.getNoFolioSolicitud());
				request.setAttribute("idSolicitudRegistrada", solicitudProceso.getSolicitudId());
			}else{
				request.setAttribute("msgError", "Ya cuentas con una solicitud en proceso.");
			}
		}else{
			request.setAttribute("solicitudEnProceso", false);
		}
	}
	
}
