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

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.usuario.ActualizaUsuarioEsquemaSeguridadException;
import mx.gob.imss.ctirss.delta.exception.usuario.ActualizaUsuarioEsquemaSeguridadMensajeException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoRegistradoEnEsquemaDeSeguridadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioRegistradoSSOException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.UsuarioCurpValidator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;

@Controller
@RequestMapping(value = "/wizard/tramite/renovacion/fiel")
public class WizardRenovacionFielController extends AbstractController {

	@Autowired
	ConsultaPersonaFisicaServiceBusinessRemote consultaFisicaServiceBusinessRemote;
	@Autowired
	SolicitudPersonaBusinessRemote solicitudPersonaBusinessRemote;
	
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String DESC_TIPO_SOLICITUD = "ACTUALIZACION DE CUENTA DE USUARIO";
	private static final String VISTA_INICIO = "wizardRenovacionFielInit";
	private static final String VISTA_CONTENIDO = "wizardRenovacionFielContenido";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_USUARIO_ACTUALIZAR = "datosUsuaroActualizar";
	
	@RequestMapping(value = "/init")
	public String inicial(Model model, HttpServletRequest request, HttpSession session) {
		
		model.addAttribute("usuario", new Usuario());
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.REGISTRO_USUARIOS_SSO.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.ALTA_USUARIOS_SSO.getCodigo());
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);

		return VISTA_INICIO;
		
	}
	
	@RequestMapping(value = "/validaciones/negocio")
	public String validacionesDeNegocio(@ModelAttribute Usuario usuario,Model model, HttpServletRequest request, HttpSession session) {
	
		Fisica fisica = null;
		Fisica fisicaN = new Fisica();
		Boolean cambioCurp = false;
		Boolean cambioRfc = false;
		String mensajeError = null;
		String mensajeErrorNegocio = null;

		log.error("Usuaro actual: " + usuario.getUsuario());
		log.error("Numero de serie del certificado: " + usuario.getPassword());
		log.error("Curp del certificado: " + usuario.getFisica().getCurp());
		log.error("Rfc del certificado: " + usuario.getFisica().getRfc());
		try {
			
			fisica = consultaFisicaServiceBusinessRemote.validaActualizarUsuarioEnEsquemaSeguridad(usuario);
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (ClienteWebserviceSatRfcException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (ErrorComparacionDatosSATException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (UsuarioRegistradoSSOException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (UsuarioNoRegistradoEnEsquemaDeSeguridadException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (EsquemaSegurdiadException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (UsuarioNoEncontradoException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (ActualizaUsuarioEsquemaSeguridadException e) {
			log.error(e);
			mensajeError = e.getMessage();
		} catch (ActualizaUsuarioEsquemaSeguridadMensajeException e) {
			log.error(e);
			mensajeErrorNegocio = e.getMessage();
		}
		
		if (mensajeError!= null) {
			model.addAttribute("msgError",mensajeError);
			model.addAttribute("usuario", usuario);
			
			return VISTA_INICIO;
		}
		
		if (mensajeErrorNegocio != null) {
			model.addAttribute("msgErrorNegocio",mensajeErrorNegocio);
			model.addAttribute("usuario", usuario);
			
			return VISTA_INICIO;
		}
		
		if(!fisica.getCurp().equalsIgnoreCase(usuario.getFisica().getCurp())) {
			cambioCurp = true;
			fisicaN.setCurp(usuario.getFisica().getCurp());
		} 
		
		if(!fisica.getRfc().equalsIgnoreCase(usuario.getFisica().getRfc())){
			cambioRfc = true;
			fisicaN.setRfc(usuario.getFisica().getRfc());
		}
		
		Fisica fisicaUsuario = this.copiarFisica(fisica);
		fisicaUsuario.setCurp(usuario.getFisica().getCurp());
		
		usuario.setFisica(fisicaUsuario);
		
		session.setAttribute(KEY_USUARIO_ACTUALIZAR, usuario);
		generarCadenaOriginal(fisica, session);
		
		model.addAttribute("cambioCurp", cambioCurp);
		model.addAttribute("cambioRfc", cambioRfc);
		model.addAttribute("fisica", fisica);
		model.addAttribute("fisicaN", fisicaN);
		
		return VISTA_CONTENIDO;
	}
	
	
	@RequestMapping(value = "/validar/datos", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validarDatosConsulta( @RequestBody Usuario usuario,  Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		
		this.log.debug("entre a validar los datos" + ReflectionToStringBuilder.toString(usuario, ToStringStyle.MULTI_LINE_STYLE));
		
		Errors errors = new BindException(usuario, "model");
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			
			new UsuarioCurpValidator().validate(usuario, errors);
			
			if( errors.hasErrors()){
				this.procesaErroresDeCaptura(errors, result, response);
				model.addAttribute("usuario", usuario);
				return result;
			}
			
			usuario.setUsuario(usuario.getUsuario().toUpperCase());
		}catch(Exception e){
			this.log.error("ocurrio un error no cachado", e);
		}
			
		return result;
	}
	
	@RequestMapping(value = "/guardar/solicitud", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object>  initConsultaPersonaUsuario(Model model, HttpSession session,
			HttpServletRequest request,  @RequestBody Solicitud solicitud) {
		String strMsg = null;

		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO_ACTUALIZAR);
		
		log.debug("los datos de la forma son sello ["+ solicitud.getSelloDigital() +"] " +
				" secuencia ["+ solicitud.getSecuenciaDeNotaria()+ "]" + solicitud.getSolicitudId());
		
		if (usuario == null){
			return null;
		}
		
		FirmaElectronica firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
		try {
			solicitudPersonaBusinessRemote.actualizaUsuarioEsquemaSeguridad(usuario, firmaElectronica);
			this.log.debug("se actualiza el usuario en sso");	
			strMsg = "La actualizacion de la cuenta de usuario fue correcta";
			result.put("error",false);
		} catch (ActualizaUsuarioEsquemaSeguridadException e) {
			log.error(e);
			strMsg = e.getMessage();
			result.put("error",true);
		} catch (EsquemaSegurdiadException e) {
			log.error(e);
			strMsg = e.getMessage();
			result.put("error",true);
		} catch(Exception e){
			this.log.error("otro error no cachado", e);
			strMsg = "Ocurrio un error al actualizar la cuenta de usuario";
			result.put("error",true);
		}
		
		result.put("mensaje", strMsg);
		session.removeAttribute(KEY_USUARIO_ACTUALIZAR);
		
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
	
	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody Solicitud limpiarSession(final HttpSession session) {
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_USUARIO_ACTUALIZAR);

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
			sbnombre.append(((Fisica)persona).getNombre().trim()).append(" ");
			if(StringUtils.isNotBlank(((Fisica)persona).getPrimerApellido())) {
				sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			}
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral)persona).getRazonSocial());
		}
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(sbnombre.toString()).append("|");
		datosEntradaFirma.setNombreCompleto(sbnombre.toString());

		if (persona instanceof Fisica) {
			contenidoAFirmar.append("CURP:");
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		}
		
		contenidoAFirmar.append("|");

		// Registro Patronal(No aplica)
		//contenidoAFirmar.append("Registro Patronal:|");

		// NSS(No aplica)
		//contenidoAFirmar.append("Numero de Seguridad Social:||");

		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
	
	private Fisica copiarFisica(Fisica fisica) {
		Fisica nss = new Fisica();
		
		nss.setCveFisica(fisica.getCveFisica());
		nss.setIdPersona(fisica.getIdPersona());
		nss.setNombre(fisica.getNombre());
		nss.setPrimerApellido(fisica.getPrimerApellido());
		nss.setSegundoApellido(fisica.getSegundoApellido());
		nss.setSexo(fisica.getSexo());
		nss.setFechaNacimiento(fisica.getFechaNacimiento());
		nss.setNss(fisica.getNss());
		nss.setCurp(fisica.getCurp());
		nss.setLugarNacimiento(fisica.getLugarNacimiento());
		
		return nss;
	}
}
