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

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoActualizadaRenapoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioRegistradoSSOException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaFisicaCURPValidator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller

@RequestMapping(value = "/wizard/tramite/registro/usuario")
@SessionAttributes(value={"fisica"})
public class WizardRegistroUsuarioController extends AbstractController {
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String FISICA_SESSION_KEY = "fisicaDatosRegistro";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";

	private static final String DESC_TIPO_SOLICITUD = "REGISTRO DE USUARIO CON CERTIFICADO DIGITAL";
	
	@Autowired
	ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaFisicaServiceBusiness;
	
	@Autowired
	SolicitudPersonaBusinessRemote solicitudPersonaBusiness;

	
	 
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String initRegistroUsuario(Model model, HttpSession session,
			HttpServletRequest request) {
		String view = "wizardRegistroUsuarioInit";
		session.removeAttribute(FISICA_SESSION_KEY);
		model.addAttribute("fisica", new Fisica());
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.REGISTRO_USUARIOS_SSO.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.ALTA_USUARIOS_SSO.getCodigo());
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);

		return view;
	}
	
	
	@RequestMapping(value = "/guardar/solicitud", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object>  initConsultaPersonaUsuario(Model model, HttpSession session,
			HttpServletRequest request,  @RequestBody Solicitud solicitud) {
		String strMsg = null;

		Map<String, Object> result = new HashMap<String, Object>();
		Fisica fisicaSession = (Fisica)session.getAttribute(FISICA_SESSION_KEY);
		
		log.debug("los datos de la forma son sello ["+ solicitud.getSelloDigital() +"] " +
				" secuencia ["+ solicitud.getSecuenciaDeNotaria()+ "]" + solicitud.getSolicitudId());
		
		if (fisicaSession == null){
			return null;
		}
		
		try {
			FirmaElectronica firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
			Solicitud objSolicitudSSO = this.llenaSolicitudRegistroUsuario(fisicaSession);
			
			objSolicitudSSO.setFirmadaDigitalmente(true);
			objSolicitudSSO.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			objSolicitudSSO.setSecuenciaDeNotaria(firmaElectronica.getReciboNotarial());
			objSolicitudSSO.setSelloDigital(firmaElectronica.getRecibo());
			objSolicitudSSO.setUrlAcuseFirma(firmaElectronica.getUrlAcuseFirma());
		
//			this.solicitudPersonaBusiness.generaSolicitudUsuarioSSO(objSolicitudSSO);
								
			this.log.debug("se GUARDA DIRECTO correctamente si !!!");
			
			TramiteFisica tramiteAltaPersona =this.solicitudPersonaBusiness.creaActualizaPersonaFisicaRegistroUsuario(objSolicitudSSO);
			if(tramiteAltaPersona != null) {
				mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite = objSolicitudSSO.getTramites().get(0);
				TramiteFisica tramiteFisicaSol = (TramiteFisica) tramite;
				tramiteFisicaSol.setFisica(tramiteAltaPersona.getFisica());
				objSolicitudSSO.getTramites().remove(0);
				objSolicitudSSO.getTramites().add(tramiteFisicaSol);
				objSolicitudSSO.getTramites().add(tramiteAltaPersona);
			}
			
			this.solicitudPersonaBusiness.creaCuentaUsuararioSSO(objSolicitudSSO, firmaElectronica);
			
			this.log.debug("se crea el usuario en SSO");	
			strMsg = "El registro de usuario concluy&oacute; exitosamente";
			result.put("error", false);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			this.log.error("error la solicitud esta vacia" ,e);
			strMsg = e.getMessage();
			result.put("error", true);
		}catch(Exception e){
			this.log.error("otro error no cachado", e);
			strMsg = "Ocurri&oacute; un error y no se pudo generar el usuario " + e.getMessage();
			result.put("error", true);
		}
		
		
		//objSolicitudUsuario.setTipoSolicitud(TipoSolicit)
		
		
		result.put("mensaje", strMsg);
		session.removeAttribute(FISICA_SESSION_KEY);
		return result;
	}
	
	
	/**
	 * 
	 * @param oForm de tipo @Fisca
	 * @param response
	 * @param session
	 * @return
	 */
	
	@RequestMapping(value = "/validar/datos", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validarDatosConsulta( @RequestBody Fisica fisica ,  Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		
		this.log.debug("entre a validar los datos" + ReflectionToStringBuilder.toString(fisica, ToStringStyle.MULTI_LINE_STYLE));
		
		Errors errors = new BindException(fisica, "model");
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
		
		new PersonaFisicaCURPValidator().validate(fisica, errors);
		
		//new PersonaFisicaRFCValidator().validate(fisica, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			model.addAttribute("fisica", fisica);
			return result;
		} else {
			fisica.setCurp(fisica.getCurp().toUpperCase());
			//fisica.setRfc(fisica.getRfc().toUpperCase());
		}
		}catch(Exception e){
			this.log.error("ocurrio un error no cachado", e);
		}
		
		return result;
	}

	
	/**
	 * 
	 * @param oForm de tipo @Fisca
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/recupera/informacion", method = RequestMethod.POST)
	public String recuperaInfoUsuario( @ModelAttribute Fisica fisica ,  Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
			
		String view ="";
		String msgError = null;
		
		session.removeAttribute(FISICA_SESSION_KEY);
		Fisica fisicaValidado = null;
		Errors errors = new BindException(fisica, "model");
		Map result = new HashMap<String, Object>();
		
		new PersonaFisicaCURPValidator().validate(fisica, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			view ="wizardRegistroUsuarioInit";
			model.addAttribute("fisica", fisica);
			return view;
		}
		
		this.log.info("entre con el siguiente RFC ["+ fisica.getRfc()+"]");
		System.out.println("entre con el siguiente RFC ["+ fisica.getRfc()+"]");
		try{
			
			 fisicaValidado = this.consultaPersonaFisicaServiceBusiness.validaPersonaRegistroUsuario(fisica);
		
		}catch(ErrorComparacionDatosSATException e){
			log.error(e);
				msgError = e.getMessage();
		}catch(EsquemaSegurdiadException e){
			log.error(e);
			msgError = "Ocurri&oacute; un error al consultar el CURP : " +fisica.getCurp() +" en el esquema de seguridad";
		}catch(UsuarioRegistradoSSOException e){
				log.error(e);
				msgError = "Ya existe un usuario registrado con la CURP " +fisica.getCurp();
		}
		catch (CURPNoLocalizadoEnEntidadExternaException e) {
				log.error(e);
				msgError = e.getMessage();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
				log.error(e);
				msgError = e.getMessage();
			} catch (ErrorComparacionDatosRENAPOException e) {
				log.error(e);
				msgError = e.getMessage();
			} catch (DiferenciasRENAPOContraSAT e) {
				log.error(e);
				msgError = e.getMessage();
			}catch(ClienteWebserviceRenapoCurpException e){
				log.error(e);
				msgError = e.getMessage();
			} catch (ClienteWebserviceSatRfcException e) {
				log.error(e);
				msgError = e.getMessage();
			} catch (CURPNoActualizadaRenapoException e) {
				log.error(e);
				msgError = e.getMessage();
			}
			catch(Exception e){
					log.error("ocurrio por exception " ,e);
					msgError = "Ocurri&oacute; un error inesperado al recuperar los datos del CURP " +e.getCause();
			}
		
		if (fisicaValidado != null) {
			model.addAttribute("fisica", fisicaValidado);
			generarCadenaOriginal(fisicaValidado, session);
			session.setAttribute(KEY_RFC_SOLICITANTE, fisicaValidado.getRfc());
			
			session.setAttribute(FISICA_SESSION_KEY, fisicaValidado);
			view ="wizardValidaUsuarioFirma";
		}
		else{
			view = "wizardRegistroUsuarioInit";
			model.addAttribute("msgError", msgError);
		}
	
		return view;
		
		
		
	}

	private Solicitud llenaSolicitudRegistroUsuario(Fisica fisica){
		Date fechaRegistro = new Date();
		Solicitud objSolicitudUsuario = new Solicitud();
		
		//llenado de las propiedades del objeto tramiteFisica
		TramiteFisica tramite = new TramiteFisica();
		EstadoTramiteEnum.INICIADO.getCodigo();
		EstadoTramite objEstadoTramite = new EstadoTramite();
		TipoTramite tipoTramite = new TipoTramite();
		
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.ALTA_USUARIOS_SSO.getCodigo());
		objEstadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
	
		tramite.setEstadoTramite(objEstadoTramite);
		tramite.setFechaPresentacion(fechaRegistro);
		tramite.setFechaTramite(fechaRegistro);
		tramite.setFisica(fisica);
		tramite.setTipoTramite(tipoTramite);
		
		List<Tramite> lstTramiteReg = new ArrayList();
		lstTramiteReg.add(tramite);
		
		//llenado de la solicitud
		TipoSolicitud objTipoSol = new TipoSolicitud();
		objTipoSol.setIdTipoSolicitud(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum.REGISTRO_USUARIOS_SSO.getValor().longValue());
	
		EstadoSolicitud objEstadoSol =new EstadoSolicitud();
		objEstadoSol.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		
		objSolicitudUsuario.setEstadoSolicitud(objEstadoSol);
		objSolicitudUsuario.setFechaSolicitud(fechaRegistro);
		objSolicitudUsuario.setFirmadaDigitalmente(true);
		objSolicitudUsuario.setTipoSolicitud(objTipoSol);
		objSolicitudUsuario.setSelloDigital("");
		objSolicitudUsuario.setTramites(lstTramiteReg);
		
		objSolicitudUsuario.setOrigenSolicitud(new OrigenSolicitud());
		objSolicitudUsuario.getOrigenSolicitud().setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		return objSolicitudUsuario;
	}
	
	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody Solicitud limpiarICA(final HttpSession session) {
		session.removeAttribute(FISICA_SESSION_KEY);
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
		log.info("Se almacenan los datos de la firma digital de forma temporal " + firmaElectronica);
		return null;
	}

	private void generarCadenaOriginal(Persona persona, HttpSession session) {
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

		
		// Folio (No aplica)
		//contenidoAFirmar.append("Folio:|");

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
		String nobmreCompletoParseado = sbnombre.toString().replace("'", "\\\'");
		log.debug("el nombre quedo como COMI [" +nobmreCompletoParseado +"]");
		
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(sbnombre.toString().replace("'", "\\\'")).append("|");
		
		datosEntradaFirma.setNombreCompleto(nobmreCompletoParseado);

		
		if (persona instanceof Fisica) {
			// CURP
			contenidoAFirmar.append("CURP:");
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		}

		// Registro Patronal(No aplica)
		//contenidoAFirmar.append("Registro Patronal:|");

		// NSS(No aplica)
		//contenidoAFirmar.append("Numero de Seguridad Social:||");
		contenidoAFirmar.append("|");

		this.log.debug("Contenido a firmar cadena retocada -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
}