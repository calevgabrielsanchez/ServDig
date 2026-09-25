package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesModificacionException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;

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
 * @author Marco Sánchez
 * 
 */

@Controller
@RequestMapping(value = "/wizard/tramite/modificar/medios/fiscales")
public class WizardMediosFiscalesController extends AbstractController {
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";

	private static final String DESC_TIPO_SOLICITUD = "ACTUALIZACION DE DATOS GENERALES";
	
	@Autowired
	PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@Autowired
	SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idTipoTramite
	 * @param idTipoPersona
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/{idTipoPersona}/{idPersona}", method = RequestMethod.GET)
	public String initModificarDatosPersona(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Integer idTipoPersona, @PathVariable Long idPersona) {

		String view = null;
		// Objeto para la forma auxiliar para invocar al servicio de modificacion manual
		MDMDatosEntrada mdmDatosEntrada = new MDMDatosEntrada();
		
		if(idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
			Fisica fisica = new Fisica();
			fisica.setIdPersona(idPersona);
			
			// Se busca la persona física relacionada a la persona
			try {
				Long cveFisica = this.personaFisicaServiceBusiness.obtenerIDPersonaFisica(idPersona);
				fisica.setCveFisica(cveFisica);
			} catch (PersonaFisicaNoEncontradaException e) {
				this.log.error(e);
			}
			
			mdmDatosEntrada.setPersonaFisica(fisica);
			
			mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.TRUE);
			mdmDatosEntrada.setIndCapturaMediosContactoFiscales(Boolean.TRUE);
			
			view = "wizardModifMediosFiscalesFisicaInit";
			
		} else {
			Moral moral = new Moral();
			moral.setCveMoral(idPersona);
			mdmDatosEntrada.setPersonaMoral(moral);

			mdmDatosEntrada.setIndCapturaRFC(Boolean.TRUE);
			mdmDatosEntrada.setIndCapturaDomicilioFiscal(Boolean.TRUE);
			mdmDatosEntrada.setIndCapturaMediosContactoFiscales(Boolean.TRUE);
			mdmDatosEntrada.setIndCapturaRazonSocial(Boolean.TRUE);
			mdmDatosEntrada.setIndCapturaFechaConstitucion(Boolean.TRUE);
			mdmDatosEntrada.setIndCapturaTipoSociedad(Boolean.TRUE);
			
			mdmDatosEntrada.setIndCapturaActaConstitutiva(Boolean.TRUE);
			mdmDatosEntrada.setIndCapturaRegistroSindicato(Boolean.TRUE);
			
			mdmDatosEntrada.setIndAutorizacion(Boolean.TRUE);
			
			view = "wizardModifMediosFiscalessMoralInit";
		}
		
		// Se checa que la persona tenga id fiscal
		if (mdmDatosEntrada.getPersonaFisica().getCveFisica() != null
				|| mdmDatosEntrada.getPersonaMoral().getCveMoral() != null) {	

			// Se busca si existen solicitudes en proceso pendientes
			boolean existeSolRegistrada = false;
			boolean existeSolProceso = false;
			Solicitud solicitudActiva = null;
			
			try {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudRegistrada(
						idPersona, idTipoPersona.longValue(),TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
						TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO);
			
				if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
					existeSolRegistrada = true;
				} else {
					solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(
							idPersona, idTipoPersona.longValue(),TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
							TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO);
					
					if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
						existeSolProceso = true;
					} else {
						solicitudActiva = new Solicitud();
					}
				}
			} catch (SolicitudException e) {
				this.log.error(e);
			}
			
			model.addAttribute("solicitudForm", solicitudActiva);
			request.setAttribute("existeSolRegistrada", existeSolRegistrada);
			request.setAttribute("existeSolProceso", existeSolProceso);
		} else {
			mdmDatosEntrada.setErrorFormGeneral("Usted no cuenta con carácter fiscal dentro del Instituto");
		}
		
		model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo());
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
		
		return view;
	}
	
	/**
	 * Compara los datos IMSS con las entidades externas (a través del ICA),
	 * genera la solicitud y tramite de actualización de datos sólo 
	 * en caso de existir diferencias, no afecta en la base de datos.
	 * 
	 * @param icaDatosConsulta
	 * @param session
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/crear/solicitud", method = RequestMethod.POST)
	public String crearSolicitudModificacionDatos(@ModelAttribute MDMDatosEntrada mdmDatosEntrada,
			final HttpSession session, HttpServletRequest request, final Model model) {
        
    	Solicitud solicitud = null;

    	log.debug("ID PERSONA -> " + mdmDatosEntrada.getPersonaFisica().getIdPersona());
    	
    	// Se agregan las banderas que requiere el servicio de la modificacion manual
		mdmDatosEntrada.setIndCapturaDatosRENAPO(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaNombre(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaCURP(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaSexo(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaFechaNacimiento(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaLugarNacimiento(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDocumentoProbatorio(Boolean.FALSE);
		
		mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaRFC(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDomicilioFiscal(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaMediosContactoFiscales(Boolean.TRUE);
		
		mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDomicilioParticular(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaMediosContactoParticular(Boolean.FALSE);
		
		mdmDatosEntrada.setIndAutorizacion(Boolean.FALSE);

		try {
			mdmDatosEntrada = this.personaFisicaServiceBusiness
					.modificacionManual(mdmDatosEntrada);
			Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
			solicitud = this.solicitudPersonaBusiness.crearTramiteModificacionDatosPersona(mdmDatosEntrada, usuario);
			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			request.setAttribute("isRetomar", false);
			
			// Datos para la firma digital
			generarCadenaOriginal(solicitud, mdmDatosEntrada.getPersonaFisica(), session);
			request.setAttribute(KEY_RFC_SOLICITANTE, mdmDatosEntrada.getPersonaFisica().getRfc());
		} catch (DatosInsuficientesModificacionException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DATOS_INSUFICIENTES_MDM
					.getCodigo(), e.getMessage());
			mdmDatosEntrada.setTraza(mensajes);
			
		} catch (PersonaNoEncontradaException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			mdmDatosEntrada.setTraza(mensajes);
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_FISICA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			mdmDatosEntrada.setTraza(mensajes);
		} catch (SolicitudNoValidaException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
		}

		model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
    	
    	return "wizardModifMediosFiscalesFisicaContenido";
    }
	
	@RequestMapping(value = "/retomar/solicitud", method = RequestMethod.POST)
	public String retomarSolicitudModificacionDatos(@ModelAttribute Solicitud solicitud,
			HttpSession session, HttpServletRequest request, final Model model) { 
	
		MDMDatosEntrada datosModif = null;
		
		try {
			Map<String, Object> resultado = this.solicitudPersonaBusiness
					.retomarSolicitudModificacionDatosPersona(solicitud.getSolicitudId());
			
			solicitud = (Solicitud) resultado.get("solicitud");
			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			request.setAttribute("isRetomar", true);
			
			datosModif = (MDMDatosEntrada) resultado.get("datosModif");
			
			// Datos para la firma digital
			generarCadenaOriginal(solicitud, datosModif.getPersonaFisica(), session);
			request.setAttribute(KEY_RFC_SOLICITANTE, datosModif.getPersonaFisica().getRfc());
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			datosModif = new MDMDatosEntrada();
			datosModif.setErrorFormGeneral(e.getMessage());
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			datosModif = new MDMDatosEntrada();
			datosModif.setErrorFormGeneral(e.getMessage());
		}
		
		model.addAttribute("mdmDatosEntrada", datosModif);
    	
    	return "wizardModifMediosFiscalesFisicaContenido";
	} 
	
	@RequestMapping(value = "/finalizar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> finalizarSolicitudModificacionDatos(@PathVariable Long idSolicitud,
			@RequestBody MDMDatosEntrada mdmDatosEntrada, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) { 
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Solicitud solicitudReq = new Solicitud();
		solicitudReq.setSolicitudId(idSolicitud);
		
		try {
			FirmaElectronica firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
			Solicitud solicitud = solicitudBusinessRemote.consultar(solicitudReq);

			solicitudPersonaBusiness.finalizarSolicitudModificacionDatosPersona(solicitud, mdmDatosEntrada, firmaElectronica);
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (SolicitudException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} 
		
		return result;
	}
	
	@RequestMapping(value = "/guardar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> guardarSolicitudModificacionnDatos(@PathVariable Long idSolicitud,
			@RequestBody MDMDatosEntrada datosModif, HttpSession session,
			HttpServletResponse response, HttpServletRequest request) { 
		Solicitud solicitudReq = new Solicitud();
		solicitudReq.setSolicitudId(idSolicitud);
	
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Solicitud solicitud = solicitudBusinessRemote.consultar(solicitudReq);
			this.solicitudPersonaBusiness.guardarSolicitudModificacionDatosPersona(solicitud, datosModif);
			result.put("mensaje", "La solicitud se ha guardado exitosamente.");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudModificacionDatos(@PathVariable Long idSolicitud,
			HttpServletResponse response, HttpServletRequest request) { 
	
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Solicitud solicitud = this.solicitudPersonaBusiness.cancelarSolicitud(idSolicitud);
			
			result.put("mensaje", "La solicitud fue cancelada correctamente");
			result.put("solicitud", solicitud);
			
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		}
		
		return result;
	}

	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody Solicitud limpiarICA(final HttpSession session) {
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_RFC_SOLICITANTE);
		
		return null;
	}

	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}

	private void generarCadenaOriginal(Solicitud solicitud, Persona persona, HttpSession session) {
		Date fechaSistema = Calendar.getInstance().getTime();
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();

		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");

		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(DESC_TIPO_SOLICITUD).append("|");

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(fechaSistema);
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(fechaSistema);

		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");

		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");
		datosEntradaFirma.setRfc(persona.getRfc());

		// Nombre, denominacion o razon social del interesado (y en su caso el de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica)persona).getNombre()).append(" ");
			sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral)persona).getRazonSocial());
		}
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(sbnombre.toString()).append("|");
		datosEntradaFirma.setNombreCompleto(sbnombre.toString());

		// CURP
		contenidoAFirmar.append("CURP:");
		if (persona instanceof Fisica) {
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		} else {
			contenidoAFirmar.append("|");
		}

		// Registro Patronal(No aplica)
		contenidoAFirmar.append("Registro Patronal:|");

		// NSS(No aplica)
		contenidoAFirmar.append("Numero de Seguridad Social:||");

		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
}
