package mx.gob.imss.cdsss.delta.portal.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cdsss.delta.portal.controller.validator.DomicilioClinicaValidator;
import mx.gob.imss.cdsss.delta.portal.utils.PropertiesOpciones;
import mx.gob.imss.cdsss.delta.portal.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.AsignacionDomicilioServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.FinalizaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficiarioBusinessException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.PatronNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.CodigoSinUmfException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ModalidadesException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.PatronImssException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.SolicitudesEnProcesoException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.VigenciaException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.carga.masiva.RegistroInvalidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ArgumentosInvalidosException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaFisicaServiceValidateRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;


/**
 * Controlador para peticiones de cambio de clinica
 * 
 * @author david.garciae
 *
 */
@Controller
@RequestMapping(value = "/clinica")
public class ClinicaController extends AbstractController {
	
	private Locale myLocale = Locale.getDefault();
	
	// ----------------------
	// SERVICIOS
	// ----------------------
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	
	@Autowired
    private ReloadableResourceBundleMessageSource messageSource;
	
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	
	@Autowired
	private FinalizaSolicitudServiceRemote finalizaSolicitudService;
	
	@Autowired
	private CorreccionDerechohabienteServiceRemote correccionDerechohabienteService;
	
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	
	@Autowired
	private PersonaFisicaServiceValidateRemote personaFisicaServiceValidate;
	
	@Autowired
	private ServiceBusinessRemote serviceBusinessRemote;
	
	@Autowired
	private DerechohabienteServiceRemote derechohabienteService;
	
	@Autowired
	private CatalogosServiceRemote catalogosService;
	
	@Autowired 
	private UmfServiceRemote umfService;
	
	@Autowired
	private PropertiesOpciones propertiesOpciones;
	
	@Autowired
	private CorreccionDerechohabienteServiceRemote correccionDerechohabienteServiceRemote;
	
	@Autowired
	private AsignacionDomicilioServiceRemote asignacionDomicilioServiceRemote;
	
	@Autowired
	private RequisitosMinimosServiceRemote requisitosMinimosService;
	
	@Autowired
	private SolicitudTramiteBusinessRemote solicitudTramiteBusiness;  
	
	@Autowired
	private DocumentosServiceRemote documentosServiceRemote;
	
	
	// ------------
	// SESION
	// ------------
	public static String PORTALC_CLINICA_IDASIGNACION 		= "PORTALC_CLINICA_IDASIGNACION";
	public static String PORTALC_CLINICA_ASEGURADO 			= "PORTALC_CLINICA_ASEGURADO";
	public static String PORTALC_CLINICA_CABEZAGRUPO 		= "PORTALC_CLINICA_CABEZAGRUPO";
	public static String PORTALC_CLINICA_UMFDELEGACION 		= "PORTALC_CLINICA_UMFDELEGACION";
	public static String PORTALC_CLINICA_PATRONES 			= "PORTALC_CLINICA_PATRONES";
	public static String PORTALC_CLINICA_MODALIDADES_IDS 	= "PORTALC_CLINICA_MODALIDADES_IDS";
	
	
	public static String PORTALC_CLINICA_DOMICILIO 		= "PORTALC_CLINICA_DOMICILIO";
	public static String KEY_SOLICITUD 					= "solicitudRegistro";
	public static String PORTALC_CLINICA_UMF 				= "PORTALC_CLINICA_UMF";
	public static String PORTALC_CLINICA_BENEFICIARIO 		= "PORTALC_CLINICA_BENEFICIARIO";
	public static String PORTALC_CLINICA_UMFSELECCIONADA   = "PORTALC_CLINICA_UMFSELECCIONADA";
	public static String PORTALC_CLINICA_CAMBIOINTEGRANTES = "PORTALC_CLINICA_CAMBIOINTEGRANTES";
	public static String PORTALC_CLINICA_ACTUALIZA_CABEZA = "PORTALC_CLINICA_ACTUALIZA_CABEZA";
	public static String KEY_SOLICITUD_REGISTRADA 			= "KEY_SOLICITUD_REGISTRADA";
	
	
	// ---------------------------
	// VISTAS
	// ---------------------------
	private String VIEW_INFORMACION_PERSONA = "clinicaInicio";
	private String VIEW_BENEFICIARIOS 		= "clinicaBeneficiarios";
	private String VIEW_SOLICITUD_EXISTENTE = "solicitudExistente";
	
	
	/**
	 * Inserta en las vistas las opciones de la aplicaci&oacute;n
	 * @return Map<String,Boolean>
	 */
	@ModelAttribute("opciones")
	public Map<String,Boolean> populateUser() {
		return  propertiesOpciones.getOpciones();
	}
	
	
	/**
	 * Carga la vista para introducir la CURP de beneficiarios
	 *  
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/clinicaBeneficiarios", method = { RequestMethod.GET })
	public String clinicaBeneficiarios(HttpSession session){
	
		session.removeAttribute(PORTALC_CLINICA_UMFSELECCIONADA);
		session.removeAttribute(PORTALC_CLINICA_CAMBIOINTEGRANTES);
		session.removeAttribute(PORTALC_CLINICA_BENEFICIARIO);
		session.removeAttribute(PORTALC_CLINICA_DOMICILIO);
		session.removeAttribute(PORTALC_CLINICA_UMF);
		session.removeAttribute(PORTALC_CLINICA_ACTUALIZA_CABEZA);
		session.removeAttribute(KEY_SOLICITUD_REGISTRADA);
		
		Solicitud solicitud = (Solicitud)session.getAttribute(KEY_SOLICITUD);
		
		this.cancelarSolicitud(solicitud);
		session.removeAttribute(KEY_SOLICITUD);
		
		return VIEW_BENEFICIARIOS;
		
	}
	
	
	/**
	 * Valida a la cabeza de familia antes de poder cambiar la cl�nica a sus beneficiarios
	 * 
	 * En caso de que no tenga cl�nica o domicilio se asignan. Si no contamos con los dos 
	 * se muestra el error.
	 * 
	 * @param idAsignacionNss
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inicial/{idAsignacionNss}", method = { RequestMethod.GET })
	public Object  informacion( @PathVariable("idAsignacionNss") Long idAsignacionNss, Model model, 
			HttpSession session, Locale locale, HttpServletRequest request) {

		
		String error = null;
		TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();
		Solicitud solicitud = new Solicitud();
		String vista = VIEW_BENEFICIARIOS;
		this.myLocale = locale;
		List<SujetoObligado> patronesAsegurado = null;
		
		try{
		
			limpiarSession(session);
			
			session.setAttribute(PORTALC_CLINICA_IDASIGNACION, idAsignacionNss);
			
			GrupoFamiliar asegurado =  grupoFamiliarService.getCabezaGrupaFamilarRegistrada(idAsignacionNss);
			session.setAttribute(PORTALC_CLINICA_ASEGURADO, asegurado);
			
			if( asegurado == null ){
				throw new DerechohabientesBusinessException();
			}
			
			
			CabezaGrupoFamiliar cabezaGrupo = grupoFamiliarService.cabezaGrupoFamiliar(idAsignacionNss);
			session.setAttribute(PORTALC_CLINICA_CABEZAGRUPO, cabezaGrupo);
		
			
			try{
				patronesAsegurado = grupoFamiliarService.getPatronesAsegurado(asegurado.getAsignacionNSS());
				session.setAttribute(PORTALC_CLINICA_PATRONES, patronesAsegurado);
			} catch(Exception e) {
				log.error("Ocurrio un error al obtener a los patrones del asegurado", e);
				e.printStackTrace();
				throw  new PatronNoEncontradoException();
			}
	
			List<Modalidad> modalidades = this.obtenerModalidadesPatrones(patronesAsegurado, cabezaGrupo);
			List<Long> idsModalidades = this.getModalidadesActivas(modalidades);
			session.setAttribute(PORTALC_CLINICA_MODALIDADES_IDS, idsModalidades);
    
			
			tramite =  validarAsegurado(asegurado, session, model, request );
				
			error = tramite.getErrorFormGeneral();
    	
		
		}catch( SolicitudesEnProcesoException e ){	
			// ----------------------------------------------------
			// Solicitud en proceso
			// ----------------------------------------------------
			solicitud = e.getSolicitud();
			session.setAttribute(KEY_SOLICITUD_REGISTRADA, solicitud);
			vista = VIEW_SOLICITUD_EXISTENTE;
			error = getMessage("SolicitudEnProcesoException");
			
		}catch( CodigoSinUmfException e ){	
			// ----------------------------------------------------
			// No tiene cl�nica ni domicilio
			// ----------------------------------------------------
			error = getMessage("clinicaDomicilioFaltantesException");
		
		}catch( DerechohabientesBusinessException e ){	
			// ---------------------------------------------------
			// No se encontro el grupo familiar en el WebService 
			// ---------------------------------------------------
			e.printStackTrace();
			error = getMessage("asignacionGrupoFamiliarNoEncontrados");
		
		}catch( VigenciaException e ){	
			// ---------------------------------------------------
			// El asegurado esta en baja o fallecido 
			// ---------------------------------------------------
			error = getMessage("enBajaOFallecido");
		
		}catch( RegistroInvalidoException e ){	
			// ----------------------------
			// No esta registrado  
			// ----------------------------
			error = getMessage("personaNoRegistrada");
			
		}catch(PatronNoEncontradoException e){
			
			// --------------------------------------------------
			// Error al consultar los patrones del asegurado 
			// --------------------------------------------------
			error = getMessage("PatronesException");
			
		}catch( ModalidadesException e ){
			
			// ----------------------------------------------------------------
			// Tipo de tr�mite no soportado por la modalidad del asegurado
			// ----------------------------------------------------------------
			error = e.getMessage();
			
		} catch (Exception e) {
			e.printStackTrace();
			error = "Ocurri� el siguente error inesperado: "+e.toString();
		}
		
		model.addAttribute("solicitud", solicitud);
		model.addAttribute("error",error);
		
		return vista;
		
	}

	
	
	
	/**
	 * Verifica si se activan los campos de medico y consultorio para el grupo familiar
	 *	
	 * @param request
	 * @param grupoFamiliar
	 * @return
	 */
	private Map<String,Object> buscarMedicoEnTurnoActivo(GrupoFamiliar grupoFamiliar) throws RestClientException{
		return grupoFamiliarService.buscarMedicoEnTurnoActivo(grupoFamiliar);
	}
	
	
	/**
	 * Obtiene el mensaje en base a su c�digo y la internacionalizaci�n asignada en el controlador
	 * 
	 * @param code
	 * @return
	 */
	private String getMessage(String code){
		return messageSource.getMessage(code, null, "", this.myLocale);
	}
	
	
	/**
	 * Obtiene el mensaje en base a su c�digo y la internacionalizaci�n asignada en el controlador
	 * 
	 * @param code
	 * @return
	 */
	private String getMessage(String code, Object[] args){
		return messageSource.getMessage(code, args, "", this.myLocale);
	}
	
	
	/**
	 * Valida no el estado es baja o fallecido
	 * 
	 * @param estado
	 * @return
	 */
	private boolean esVigente(EstadoDerechohabiente estado){
		
		if( estado.getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId()) )
			return false;
			
		if( estado.getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.FALLECIDO.getId()) )
			return false;
		
		
		return true;
		
	}
	
	
	/**
	 * Obtienen las delegaciones en las UMF's asignadas al CP y las compara con las delegaciones 
	 * guardadas en sesion
	 * 
	 * @param cp
	 * @param session
	 * @param request
	 * @return El id de la UMF 
	 */
	@RequestMapping(value = "/umfCp", method = { RequestMethod.POST})
	public @ResponseBody Map<String, ? extends Object> validarCodigoPostalUmf(
			@RequestBody Domicilio domicilio , HttpSession session, HttpServletRequest request) {
	
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", true);
		
		try {
		
			if( this.esDomicilioValido(domicilio) ){
				
				if( !this.esCabezaFamilia(session)){
	        	
					List<UnidadMedicaFamiliar> unidadMedicaFamiliarList = umfService.findUmfByCodigoPostal(domicilio.getCodigoPostal().getCodigoPostal());
					Long idDelegacionAsegurado = (Long)session.getAttribute(PORTALC_CLINICA_UMFDELEGACION);
					
					for( UnidadMedicaFamiliar umf : unidadMedicaFamiliarList ){
					
						if( idDelegacionAsegurado.equals(umf.getSubdelegacion().getDelegacion().getId())  ){
							session.setAttribute(PORTALC_CLINICA_DOMICILIO, domicilio);
							result.put("error", false);
							return result;
						}
							
					}
					
					result.put("mensaje", this.getMessage("DomicilioEnCircunscripcionForaneaException"));
					
				}else{
					session.setAttribute(PORTALC_CLINICA_DOMICILIO, domicilio);
					result.put("error", false);
					return result;
				}
				
			}else{
				result.put("mensaje", this.getMessage("DomicilioNoValidoException"));
			}
			
		}catch( UmfNoLocalizadaException e ){	
			result.put("mensaje", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("mensaje", e.getMessage());
		}
		
		session.removeAttribute(PORTALC_CLINICA_DOMICILIO);
		return result;
		
	}
	

	
	/**
	 * Obtiene las solicitudes registradas o pendientes de autorizaci&oacute;n
	 * @return
	 */
	private Solicitud obtenerSolicitudesRegistradas(Long idPersona){
		
		
		List<Solicitud> solicitudesNoFinalizadas 	= new ArrayList<Solicitud>();
		TipoPersonaFiscal tipoPersona 				= TipoPersonaFiscal.FISICA;
		Solicitud solicitudRegistrada				= null;
		
		
		List<Long> tiposSolicitud = new ArrayList<Long>();
		tiposSolicitud.add(TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE.getValor().longValue());
		
		List<Long> estadosSolicitud = new ArrayList<Long>();
		estadosSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().longValue());
		estadosSolicitud.add(EstadoSolicitudEnum.REGISTRADA.getCodigo().longValue());
		
		solicitudesNoFinalizadas = solicitudBusinessRemote.obtenerSolicitudPorPersona(idPersona, tipoPersona, tiposSolicitud, estadosSolicitud, false);
				
		if( !solicitudesNoFinalizadas.isEmpty() ){
			if( (solicitudRegistrada = continenTramitesDomicilio(solicitudesNoFinalizadas)) != null ){
				
				return solicitudRegistrada;
					
			}
		} 
					
		return null;
			
		
	}
	 
	
	/**
	 * Busca el tipo de tr�mite en las solicitudes
	 * 
	 * @param solicitudes
	 * @return Si encuentra tr�mites del tipo que domicilio o cl�nia regresa true
	 */
	private Solicitud continenTramitesDomicilio(List<Solicitud> solicitudes){
		
		for( Solicitud solicitud: solicitudes  ){
			
			List<Tramite> tramites = solicitud.getTramites();
			
			for( Tramite tramite : tramites  ){
			
				try{
					validaTipoTramitesPermitidos(tramite.getTipoTramite().getIdTipoTramite().longValue());
				}catch( ArgumentosInvalidosException e){
					
					// -------------------------------------------------------------
					// Si lanza la excepci�n es por que no contiene el tipo de
					// tr�mites que buscamos
					// -------------------------------------------------------------
					continue;
				}
				
				return solicitud;
				
			}
			
			
		}
		
		return null;
		
	}
	
	
	
	/**
	 * Valida el tipo de tramite requerido
	 * 
	 * Solamente tramites para cambio de domicilio a personas fisicas son permitidos.  
	 * 
	 * @param idTipoTramite Long Id del tipo de tramite
	 * @throws ArgumentosInvalidosException 
	 */
	private void validaTipoTramitesPermitidos( Long idTipoTramite ) throws ArgumentosInvalidosException{
		
		try{
		
			switch( TipoTramiteEnum.obternerEnumById(idTipoTramite.intValue())  ){
				case ACTUALIZACION_DOMICILIO_PARTICULAR:
				case ASIGNACION_DE_DOMICILIO_PARTICULAR_DH:
				case CAMBIO_CLINICA:
					break;	
				default: 
					log.debug( "======================= validaTipoTramitesPermitidos ========" + TipoTramiteEnum.obternerEnumById(idTipoTramite.intValue()) );
					throw new ArgumentosInvalidosException();
			}
			
		
		}catch(Exception e){
			log.debug(e);
			// ----------------------------------------------------------
			// En caso de que le tipo de tr�mite no se encuentre en la 
			// enumeraci�n, tambi�n se lanza la excepci�n.
			// ----------------------------------------------------------
			throw new ArgumentosInvalidosException();
		}
		
		
		
	}
	
	
	
	
	/**
	 * Guarda en la base la solicitud y el tramite iniciales
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param habilitarCP Boolean variable que indica si quiere actualizar el CP o no
	 * @param persona Fisica Persona a la que se le realizar&aacute; el tr&aacute;mite
	 * @return
	 * @throws PersonaFisicaNoEncontradaException 
	 */
	public Solicitud crearTramite(HttpSession session, Integer idTipoTramite, TramiteCorreccionDerechohabiente tramite) throws PersonaFisicaNoEncontradaException{

		
		Derechohabiente derechohabiente = (Derechohabiente)tramite.getPersona();
		GrupoFamiliar asegurado = (GrupoFamiliar)session.getAttribute(PORTALC_CLINICA_ASEGURADO);
		Long idPersonaInteresada = asegurado.getDerechohabiente().getIdPersona();
		Fisica persona = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(derechohabiente.getIdPersona());
		List<Tramite> tramites = new ArrayList<Tramite>();
		
		// -------------------------------
		// Solicitante
		// -------------------------------
		Usuario usuario = new Usuario();
		usuario.setUsuario(asegurado.getAsignacionNSS().getCurp());
		
		// -----------------------------------------------------------
		// Objeto para dar de alta una solicitud
		// -----------------------------------------------------------
		MDMDatosEntrada mdmDatosEntrada = new MDMDatosEntrada();
		mdmDatosEntrada.setIndCapturaDatosRENAPO(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaNombre(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaCURP(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaSexo(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaFechaNacimiento(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaLugarNacimiento(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDocumentoProbatorio(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaRFC(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDomicilioFiscal(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDomicilioParticular(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaMediosContactoFiscales(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaMediosContactoParticular(Boolean.FALSE);
		mdmDatosEntrada.setIndAutorizacion(Boolean.FALSE);
		mdmDatosEntrada.setIndAsignacionDomicilio(Boolean.FALSE);
		mdmDatosEntrada.setIndActualizacionDomicilioDerechohabiente(Boolean.FALSE);
		mdmDatosEntrada.setIndCambioClinica(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.TRUE);
		mdmDatosEntrada.setPersonaFisica(persona);
		mdmDatosEntrada.setIdTipoSolicitud(TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE.getValor().longValue());
		mdmDatosEntrada.setOrigen( OrigenSolicitudEnum.PORTAL_CIUDADANO.getId() );
		
		try {
			
			log.debug("Cambio cl�nica origen :"+mdmDatosEntrada.getOrigen());
					
			if( idTipoTramite.equals( TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH.getCodigo()) ){
				mdmDatosEntrada.setIndAsignacionDomicilio( Boolean.TRUE );
		
			}else if( idTipoTramite.equals( TipoTramiteEnum.CAMBIO_CLINICA.getCodigo()) ){
				mdmDatosEntrada.setIndCambioClinica(Boolean.TRUE);
			}
			
			
			Solicitud  solicitud = this.solicitudPersonaBusiness.crearTramiteModificacionDatosPersona(mdmDatosEntrada, usuario, idPersonaInteresada);
			TramiteFisica tramiteFisica = (TramiteFisica)solicitud.getTramites().get(0);
			
			
			log.debug("XXX:"+solicitud.getSolicitudId());
			
			
			// -------------------------------------------------------
			// Se necesita la descripci�n para la firma electr�nica
			// -------------------------------------------------------
			TipoTramite tipoTramite = catalogosService.getCatalogoTipoTramite(tramiteFisica.getTipoTramite().getIdTipoTramite().longValue());
			
			tramite.setFechaTramite(tramiteFisica.getFechaTramite());
			tramite.setFechaPresentacion(tramiteFisica.getFechaPresentacion());
			tramite.setEstadoTramite(tramiteFisica.getEstadoTramite());
			tramite.setTipoTramite( tipoTramite );
    		tramite.setFisica( persona );
    		tramite.setTramiteId( tramiteFisica.getTramiteId() );
    		
			tramites.add(tramite);
			solicitud.setTramites(tramites);
			
			session.setAttribute(KEY_SOLICITUD, solicitud);
			return solicitud;
			
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
		} catch( Exception e ){
			e.printStackTrace();
		}
	
		
		return null;
		
	}
	
	
	/**
	 * Cancela las solicitudes activas; limpia las variables de sesi�n
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping( value = "/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {

		
		session.removeAttribute(PORTALC_CLINICA_UMFDELEGACION);
		session.removeAttribute(PORTALC_CLINICA_IDASIGNACION);
		session.removeAttribute(PORTALC_CLINICA_ASEGURADO);
		session.removeAttribute(PORTALC_CLINICA_DOMICILIO);
		session.removeAttribute(PORTALC_CLINICA_UMF);
		session.removeAttribute(PORTALC_CLINICA_BENEFICIARIO);
		session.removeAttribute(PORTALC_CLINICA_CABEZAGRUPO);
		session.removeAttribute(PORTALC_CLINICA_UMFSELECCIONADA);
		session.removeAttribute(PORTALC_CLINICA_CAMBIOINTEGRANTES);
		session.removeAttribute(PORTALC_CLINICA_ACTUALIZA_CABEZA);
		session.removeAttribute(KEY_SOLICITUD_REGISTRADA);
		session.removeAttribute(PORTALC_CLINICA_PATRONES);
		session.removeAttribute(PORTALC_CLINICA_MODALIDADES_IDS);
		
		
		
		Solicitud solicitud = (Solicitud)session.getAttribute(KEY_SOLICITUD);
		this.cancelarSolicitud(solicitud);
		session.removeAttribute(KEY_SOLICITUD);
		
	
		
		return null;
	}
	
	
	/**
	 * Cancela la solicitud en sesi�n
	 * 
	 * @param session HttpSession
	 */
	private void cancelarSolicitud(Solicitud solicitud){
		
		if(solicitud != null) {

			try {
				
				solicitud.setEstadoSolicitud(new EstadoSolicitud());
				solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
					
					for(Tramite tramite : solicitud.getTramites()){										
						tramite.setResultado(false);
						tramite.setRazonResultado(new RazonResultado());
						tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.SOLICITUD_CANCELADA.getCodigo().longValue());
						tramite.setEstadoTramite(new EstadoTramite());
						tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());				
						tramite.setObservacion("Solicitud cancelada a peticion del derechohabiente");
					}
				
					solicitud.setFechaActualizacion(new Date());
					solicitud.setObservacion("Solicitud cancelada a peticion del derechohabiente");
					
					solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
			} catch (SolicitudNoEncontradaException e) {
				this.log.error(e);
			} catch (TramiteNoEncontradoException e) {
				this.log.error(e);
			}
		} 
	}
	
	
	/**
	 * Valida los datos de adscripci&oacute;n
	 * 
	 * @param oForm
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/validaAdscripcion", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validaAdscripcion(final @RequestBody TramiteCorreccionDerechohabiente oForm, 
    		final HttpServletResponse response, final HttpSession session) {
        
		Map<String, Object> result = new HashMap<String, Object>();
        final Errors errors = new BindException(oForm, "model");
        
        new DomicilioClinicaValidator().validaDatosAdscripcion(oForm, errors);
        
        // -------------------------
        // Beneficiarios
        // -------------------------  
        if( !errors.hasErrors() && (!this.esCabezaFamilia(session)) && (session.getAttribute(PORTALC_CLINICA_UMFDELEGACION) != null) ){
        	
    		Long idDelegacionAsegurado = (Long)session.getAttribute(PORTALC_CLINICA_UMFDELEGACION);
        	UnidadMedicaFamiliar umf = oForm.getMedicoEnTurno().getUnidadMedicaFamiliar();
        	
        	if( !umf.getSubdelegacion().getDelegacion().getId().equals(idDelegacionAsegurado) ){
        		errors.rejectValue("medicoEnTurno.unidadMedicaFamiliar.idUMF", "UmfNoValida");
        	}
        }
        
        
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            result.put("mensaje", this.getMessage("UmfNoValida"));
            session.removeAttribute(PORTALC_CLINICA_UMF);
        }else{
        	
        	session.setAttribute(PORTALC_CLINICA_UMF, oForm.getMedicoEnTurno());
        	
        	// -----------------------------------
        	// El asegurado no ten�a cl�nica
        	// -----------------------------------
        	if( (this.esCabezaFamilia(session)) && (session.getAttribute(PORTALC_CLINICA_UMFDELEGACION) == null) ){
        		session.setAttribute(PORTALC_CLINICA_UMFDELEGACION, oForm.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().getId());
        	}
        	
        }
        
        return result;
    }
	
	
	/**
	 * Valida el domicilio 
	 * 
	 * @param domicilio
	 * @return
	 */
	private boolean esDomicilioValido(Domicilio domicilio){
		
		TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();
		tramite.setDomicilio(domicilio);
		
		final Errors errors = new BindException(tramite, "model");
        new DomicilioClinicaValidator().validaDomicilio(tramite, errors);
        
        if (errors.hasErrors()) 
        	return false;
            
        
        return true;
		
	}

	
	
	/**
	 * Este m�todo se encarga de finalizar la solicitud y tramite de prorroga.
	 * Dependiendo del tipo de prorroga se haran ciertas validaciones, ademas de
	 * esto se guardan los documentos capturados y se generan los documentos
	 * resultantes.
	 * 
	 * @param tramite
	 * @param response
	 * @param request
	 * @param session
	 * @return
	 */
	
	private @ResponseBody Map<String, ? extends Object> finalizarSolicitud(@RequestBody TramiteCorreccionDerechohabiente tramiteClinica,
			HttpServletResponse response, HttpServletRequest request,	HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", true);
		
		try {
		
			GrupoFamiliar asegurado 							= (GrupoFamiliar) session.getAttribute(PORTALC_CLINICA_ASEGURADO);
			Domicilio domicilio 								= (Domicilio) session.getAttribute(PORTALC_CLINICA_DOMICILIO);
			Solicitud solicitud 								= (Solicitud) session.getAttribute(KEY_SOLICITUD);
			MedicoEnTurno medicoEnTurno	 						= (MedicoEnTurno) session.getAttribute(PORTALC_CLINICA_UMF);
			Long umfDelegacion									= (Long) session.getAttribute(PORTALC_CLINICA_UMFDELEGACION);
			Long umfSeleccionada								= (Long) session.getAttribute(PORTALC_CLINICA_UMFSELECCIONADA);
			TramiteCorreccionDerechohabiente tramiteCorreccion 	= (TramiteCorreccionDerechohabiente)solicitud.getTramites().get(0);
			Boolean actualizarIntegrantes 						= (Boolean)session.getAttribute(PORTALC_CLINICA_CAMBIOINTEGRANTES);
			CabezaGrupoFamiliar cabezaGrupo 					= (CabezaGrupoFamiliar)session.getAttribute(PORTALC_CLINICA_CABEZAGRUPO);
			GrupoFamiliar grupoFamiliar							= (GrupoFamiliar)session.getAttribute(PORTALC_CLINICA_BENEFICIARIO);
			
		
			
			if( (asegurado == null) || (domicilio == null) || (solicitud == null) || (medicoEnTurno == null) || (umfDelegacion == null)  ){
				throw new IllegalArgumentException();
			}
			
			
			if( !this.esDomicilioValido(domicilio) ){
				throw new DomicilioNoValidoException();
			}
			
			
			if( umfSeleccionada != null ){
				
				// -------------------------------------------
				// Ten�a una UMF antes de iniciar el tr�mite
				// -------------------------------------------
				if(  umfSeleccionada.equals(medicoEnTurno.getUnidadMedicaFamiliar().getIdUMF())  ){
					
					if( domicilio.getClave() != null  )
						throw new BeneficiarioBusinessException(this.getMessage("SinCambiosException"));
					
				}else{
					
					if( tramiteClinica.getFechaCambioMedico() != null )
						tramiteCorreccion.setFechaCambioMedico(tramiteClinica.getFechaCambioMedico());
					else
						tramiteCorreccion.setFechaCambioMedico(new Date());
						
				}
			}
			
			// ------------------------------------
			// Siempre se crea un domicilio nuevo
			// ------------------------------------
			domicilio.setClave(null);
			
			
			tramiteCorreccion.setIndSeleccionMedico(tramiteClinica.getIndSeleccionMedico());
			tramiteCorreccion.setDomicilio(domicilio);
			tramiteCorreccion.setMedicoEnTurno(medicoEnTurno);
			tramiteCorreccion.setPersona(grupoFamiliar.getDerechohabiente());
			
			
			solicitud.getTramites().set(0, tramiteCorreccion);
				
				
			// -------------------------------------------------------------
			// Actualizamos la solicitud con el nuevo domicilio
			// -------------------------------------------------------------
			this.actualizarSolicitud(solicitud);
			
			
			
			try {
				correccionDerechohabienteService.generaFirmaElectronica(asegurado.getAsignacionNSS(), solicitud, tramiteCorreccion.getTipoTramite().getDescripcion());
			} catch(DocumentoException e) {
				log.error("No fue posible guardar la firma digital",e);
			}
			
			
			if( actualizarIntegrantes != null && actualizarIntegrantes.equals(true) ){
				boolean tienePatronImss = cabezaGrupo.getPatronImss().equals(1)?true:false;
				
				tramiteCorreccion = this.agregarGrupoFamiliar(tramiteCorreccion, tienePatronImss, umfDelegacion);
				solicitud.getTramites().set(0, tramiteCorreccion);
			}
			
			
			solicitud = correccionDerechohabienteService.finalizarSolicitudDomicilioClinicaCircunscripcion(solicitud);
			session.removeAttribute(KEY_SOLICITUD);
			
			
			//WebService Vigencia
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			finalizaSolicitudService.finalizarSolicitudTramites(solicitud, "", asegurado.getAsignacionNSS());
			
			
			solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
			result.put("error", false);
			result.put("mensaje", "Su solicitud ha finalizado correctamente");
			
			return result;
		
		} catch( DomicilioNoValidoException e ){	
			result.put("mensaje",this.getMessage("DomicilioNoValidoException"));
		} catch( BeneficiarioBusinessException e){	
			result.put("mensaje",e.getMessage());
		} catch( IllegalArgumentException e ){	
			result.put("mensaje",this.getMessage("InformacionIncompletException"));
		} catch( DerechohabientesBusinessException e ){	
			this.log.error("Derechohabiente no encontrado", e);
			result.put("mensaje",this.getMessage("GuardarDatosException"));
		} catch(SolicitudNoEncontradaException e){	
			this.log.error("Solicitud no encontrada", e);
			result.put("mensaje",this.getMessage("GuardarDatosException"));
		} catch( TramiteNoEncontradoException e ){	
			this.log.error("Tramite no encontrado", e);
			result.put("mensaje",this.getMessage("GuardarDatosException"));
		} catch (Exception e) {
			this.log.error("error desconocido", e);
			result.put("mensaje",this.getMessage("GuardarDatosException"));
		}

		response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED );
		return result;
	}

	
	
	
	
	/**
	 * Este metodo se encarga de guardar el domicilio capturado y asignarlo al derechohabiente, una vez guardado el domicilio
	 * se redirecci�n a la pantalla que indica que el tramite ha finalizado
	 * @param correccion
	 * @param session
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> guardaAsignacionDomicilio(
			@RequestBody TramiteCorreccionDerechohabiente tramiteClinica, HttpServletResponse response, HttpSession session, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", true);
		

		
		try {
			
			
			GrupoFamiliar asegurado 							= (GrupoFamiliar) session.getAttribute(PORTALC_CLINICA_ASEGURADO);
			GrupoFamiliar beneficiario 							= (GrupoFamiliar)session.getAttribute(PORTALC_CLINICA_BENEFICIARIO);
			AsignacionNSS asignacionNSS 						= asegurado.getAsignacionNSS();
			
		
			Domicilio domicilio 								= (Domicilio) session.getAttribute(PORTALC_CLINICA_DOMICILIO);
			Solicitud solicitudCorreccion 						= (Solicitud) session.getAttribute(KEY_SOLICITUD);
			MedicoEnTurno medicoEnTurno	 						= (MedicoEnTurno) session.getAttribute(PORTALC_CLINICA_UMF);
			Long umfDelegacion									= (Long) session.getAttribute(PORTALC_CLINICA_UMFDELEGACION);
			Long umfSeleccionada								= (Long) session.getAttribute(PORTALC_CLINICA_UMFSELECCIONADA);
			TramiteCorreccionDerechohabiente tramiteCorreccion 	= (TramiteCorreccionDerechohabiente)solicitudCorreccion.getTramites().get(0);
			CabezaGrupoFamiliar cabeza       					= (CabezaGrupoFamiliar)session.getAttribute(PORTALC_CLINICA_CABEZAGRUPO);
		
	
			if( (asegurado == null) || (domicilio == null) || (solicitudCorreccion == null) || (medicoEnTurno == null) || (umfDelegacion == null) || (beneficiario == null) ){
				throw new IllegalArgumentException();
			}
			
			
			if( !this.esDomicilioValido(domicilio) ){
				throw new DomicilioNoValidoException();
			}
			
			
			if( umfSeleccionada != null ){
				
				// -------------------------------------------
				// Ten�a una UMF antes de iniciar el tr�mite
				// -------------------------------------------
				if(  umfSeleccionada.equals(medicoEnTurno.getUnidadMedicaFamiliar().getIdUMF())  ){
					
					if( domicilio.getClave() != null  )
						throw new BeneficiarioBusinessException(this.getMessage("SinCambiosException"));
					
				}else{
					
					if( tramiteClinica.getFechaCambioMedico() != null )
						tramiteCorreccion.setFechaCambioMedico(tramiteClinica.getFechaCambioMedico());
					else
						tramiteCorreccion.setFechaCambioMedico(new Date());
						
				}
			}
			
			// ------------------------------------
			// Siempre se crea un domicilio nuevo
			// ------------------------------------
			domicilio.setClave(null);
			
			
			tramiteCorreccion.setIndSeleccionMedico(tramiteClinica.getIndSeleccionMedico());
			tramiteCorreccion.setDomicilio(domicilio);
			tramiteCorreccion.setMedicoEnTurno(medicoEnTurno);
			tramiteCorreccion.setPersona(beneficiario.getDerechohabiente());
			
			if(beneficiario.getMedicoEnTurno() != null && beneficiario.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
				tramiteCorreccion.setIdUmfOrigen(beneficiario.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			}
			
			
			solicitudCorreccion.getTramites().set(0, tramiteCorreccion);
				
				
			// -------------------------------------------------------------
			// Actualizamos la solicitud con el nuevo domicilio
			// -------------------------------------------------------------
			solicitudCorreccion = this.actualizarSolicitud(solicitudCorreccion);
			FirmaElectronica fe = null;
	
			
			try {
				 fe = correccionDerechohabienteService.generaFirmaElectronica(asegurado.getAsignacionNSS(), solicitudCorreccion, tramiteCorreccion.getTipoTramite().getDescripcion());
			} catch(DocumentoException e) {
				log.error("No fue posible guardar la firma digital",e);
			}
			
			
			
			log.debug("Se procede a guardar la validacion de asignacion de domicilio");
			log.debug("el folio de la solicitud es: " + solicitudCorreccion.getNoFolioSolicitud());
			
			
			TramiteCorreccionDerechohabiente datosActuales = this.obtenerCorreccion(solicitudCorreccion);
			Map<String, Object> validacionesCambios = this.cambioClinica(beneficiario, datosActuales);
			List<GrupoFamiliar> padres =  this.getPadresConcubina(asignacionNSS, cabeza);
			
			
			
			Solicitud solicitud = asignacionDomicilioServiceRemote.guardaAsignacionDeDomicilioSimplificado(solicitudCorreccion.getSolicitudId(), asignacionNSS, cabeza, beneficiario,
					datosActuales, padres, validacionesCambios);
		
			
			if(solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
				solicitud.setNoFolioSolicitud(solicitudCorreccion.getNoFolioSolicitud());
				solicitudCorreccion = solicitud;
				for(Tramite t : solicitud.getTramites()){
					if(t.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo())){
						//Generamos el reporte de cambio de cl�nica
						TramiteCorreccionDerechohabiente tramite = (TramiteCorreccionDerechohabiente)t;
						tramite.setDomicilioAnterior(beneficiario.getDomicilio());
						documentosServiceRemote.getDocumentoCambioClinica(solicitud.getNoFolioSolicitud(),tramite.getTipoTramite().getDescripcion(),fe, tramite);
						break;
					}
				}
			}
			
			// -------------------------------------------------------------------------------------------
			// Cuando se cierra el wizard se limpia la sesi�n y si encuentra una Solicitud la cancela
			// -------------------------------------------------------------------------------------------
			session.removeAttribute(KEY_SOLICITUD);
			
			
			result.put("error", false);
			result.put("mensaje", "Su solicitud ha finalizado correctamente");
		
			
			return result;
			
		} catch(ImpactaAlmacenesWSException e) {
			result.put("mensaje", "<strong>Ocurri&oacute; un error al calcular la vigencia</strong>.");
		} catch( DomicilioNoValidoException e ){	
			result.put("mensaje",this.getMessage("DomicilioNoValidoException"));
		} catch( BeneficiarioBusinessException e){	
			result.put("mensaje",e.getMessage());
		} catch( IllegalArgumentException e ){	
			result.put("mensaje",this.getMessage("InformacionIncompletException"));
		} catch( DerechohabientesBusinessException e ){	
			this.log.error("Derechohabiente no encontrado", e);
			result.put("mensaje",this.getMessage("GuardarDatosException"));
		} catch(SolicitudNoEncontradaException e){	
			this.log.error("Solicitud no encontrada", e);
			result.put("mensaje",this.getMessage("GuardarDatosException"));
		} catch( TramiteNoEncontradoException e ){	
			this.log.error("Tramite no encontrado", e);
			result.put("mensaje",this.getMessage("GuardarDatosException"));
		} catch (Exception e) {
			this.log.error("error desconocido", e);
			result.put("mensaje",this.getMessage("GuardarDatosException"));
		}

		response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED );
		return result;
			
		
	}
	
	
	

	/**
	 * 
	 * Actualiza la solicitud en base
	 * 
	 * @param tramite contiene la informacion capturada en pantalla
	 * @param session
	 * @return
	 */
	private Solicitud actualizarSolicitud(Solicitud solicitud) 
					throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
   		return solicitudBusinessRemote.actualizarTramites(solicitud);
   
	}

	
	/**
	 * Valida la curp del beneficiario
	 * 
	 * @param tramiteCorreccion
	 * @param response
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/beneficiario", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<Boolean> beneficiarioCurp(@RequestBody TramiteCorreccionDerechohabiente tramiteCorreccion,
			HttpServletResponse response, HttpServletRequest request, HttpSession session, @ModelAttribute("opciones") Map<String, Boolean> opciones ) {
		
		Long idAsignacionNss = (Long)session.getAttribute(PORTALC_CLINICA_IDASIGNACION);
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar)session.getAttribute(PORTALC_CLINICA_CABEZAGRUPO);
		
		RespuestaJSON<Boolean> respuesta = new RespuestaJSON<Boolean>();
		respuesta.setEstado(false);
		respuesta.setModelo(new Boolean(false));
		
		if(  tramiteCorreccion.getPasoTramite() == null  ){
		
			String validacionCurp = personaFisicaServiceValidate.validarCURP(tramiteCorreccion.getCurpCap());
			
			if( validacionCurp.length() == 0){
				
				try {
					
					String curp = tramiteCorreccion.getCurpCap().toUpperCase();
					GrupoFamiliar grupoFamiliar = null;
					
					
					// ---------------------------------------------------------------
					// La cl�nica del asegurado debe estar registrada en la sesi�n
					// ---------------------------------------------------------------
					if( session.getAttribute("PORTALC_CLINICA_UMFDELEGACION") == null ){
						respuesta.setModelo(new Boolean(true));
						DerechohabientesBusinessException.throwException(this.getMessage("ClinicaAseguradoException"));
					}
					
					
					// ------------------------------------------------------------------------
					// Verificamos la bandera en opciones para saber si �nicamiente procesamos
					// a los asegurados.
					// ------------------------------------------------------------------------
					GrupoFamiliar asegurado = (GrupoFamiliar)session.getAttribute(PORTALC_CLINICA_ASEGURADO);
					
					if( !asegurado.getDerechohabiente().getCurp().equals(curp) ){
						session.removeAttribute(PORTALC_CLINICA_ACTUALIZA_CABEZA);
						
						if(  !opciones.get("ind_cambio_clinica_beneficiarios") ){	
							throw new Exception(this.getMessage("CurpAseguradoException"));
						}
					}else{
						session.setAttribute(PORTALC_CLINICA_ACTUALIZA_CABEZA, true);
					}
					
					List<Fisica> personas = personaBusiness.buscarEnPersonaYGrupoFamiliar(curp, idAsignacionNss);
					
					
					if( personas == null || personas.size() == 0 ){
						
						Fisica fisica = new Fisica();
						fisica.setCurp(curp);
						
						personas = serviceBusinessRemote.localizarPersonaFisica(fisica);
						
						if(personas == null || personas.isEmpty()){
							throw new PersonaFisicaNoEncontradaException(0L);
						}
						
						grupoFamiliar = this.buscaPersonaEnGrupoFamiliar(personas, idAsignacionNss);
						
					}else if( personas.size() == 1  ){
						
						try{
							grupoFamiliar = grupoFamiliarService.getIntegranteGrupoFamiliarPorIdPersona(idAsignacionNss, personas.get(0).getIdPersona());
						}catch( Exception e ){
							// -----------------------------------------------------------------------------
							// En caso de que la persona candidata no se encuentre en el WS o en BDTU
							// -----------------------------------------------------------------------------
						}
						
					}else{
						throw new BeneficiarioBusinessException(this.getMessage("CurpRepetidaEnGrupoException"));
					}
				
					
					
					
				
					if( grupoFamiliar == null ){
						DerechohabientesBusinessException.throwException(this.getMessage("personaNoEncontrada"));
					}
					
					
					grupoFamiliar.setIndRegistrado(1);
					
					if( grupoFamiliar.getParentesco().getIdParentesco().equals( ParentescoEnum.PADRES.getId() ) ){
						if(  !cabezaGrupo.getPatronImss().equals(1)){
							throw new PatronImssException(this.getMessage("PatronImssException"));
						}
					}
					
					if( grupoFamiliar.getParentesco().getIdParentesco().equals( ParentescoEnum.CONCUBINARIO.getId() ) ){
						throw new BeneficiarioBusinessException(this.getMessage("BeneficiarioBusinessException"));
					}
					
					
					if( derechohabienteService.tieneCircunscripcionForeanea(grupoFamiliar.getDerechohabiente())){
						throw new BeneficiarioBusinessException(this.getMessage("TramiteEnVentanillaException"));
					}
				
					
					// -----------------------------------------------------------------------------
					// No puede realizar cambio de cl�nica mas de dos veces en el a�o en curso
					// -----------------------------------------------------------------------------
					List<Tramite> tramitesCambiosDeClinica = this.obtenerTramitesCambioClinica(grupoFamiliar.getDerechohabiente().getIdPersona());
					if( (tramitesCambiosDeClinica != null) && (tramitesCambiosDeClinica.size() > 1)  ){
						
						String[] args = new String[2];
						args[0] = String.valueOf(tramitesCambiosDeClinica.size());
						args[1] = grupoFamiliar.getDerechohabiente().getNombreCompleto();
						
						throw new BeneficiarioBusinessException(this.getMessage("LimiteCambioClinicaAnio", args));
					}
					
					
					
					session.setAttribute(PORTALC_CLINICA_BENEFICIARIO, grupoFamiliar);
					respuesta.setEstado(true);
				
					
					
				} catch(BeneficiarioBusinessException e){
					respuesta.setMensaje(e.getMessage());	
				} catch(PatronImssException e){
					respuesta.setMensaje(e.getMessage());
				} catch( PersonaFisicaNoEncontradaException e ){
					respuesta.setMensaje(e.getLocalizedMessage());
					e.printStackTrace();
				} catch( DerechohabientesBusinessException e ){
					respuesta.setMensaje(e.getMessage());
					e.printStackTrace();
				} catch (CURPNoLocalizadoEnEntidadExternaException e) {
					respuesta.setMensaje(this.getMessage("personaNoEncontrada"));
					e.printStackTrace();
				} catch (ClienteWebserviceRenapoCurpException e) {
					respuesta.setMensaje(e.getLocalizedMessage());
					e.printStackTrace();
				} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
					respuesta.setMensaje(e.getLocalizedMessage());
					e.printStackTrace();
				} catch (Exception e) {
					respuesta.setMensaje(e.getMessage());
					e.printStackTrace();
				}
				
			
			}else{
				respuesta.setMensaje(validacionCurp);
			}
		
		}else{
			
			
			if( tramiteCorreccion.getPasoTramite().equals(3) ){
			
				// ----------------------------------
				// Se actualizara a todo el grupo
				// ----------------------------------
				session.setAttribute(PORTALC_CLINICA_CAMBIOINTEGRANTES,true);
				respuesta.setEstado(true);
			}
			
		}
		
		
		return respuesta;
		
	}
	
	
	
	
	/**
	 * Valida a la cabeza de familia antes de poder cambiar la cl�nica a sus beneficiarios
	 * 
	 * En caso de que no tenga cl�nica o domicilio se asignan. Si no contamos con los dos 
	 * se muestra el error.
	 * 
	 * @param idAsignacionNss
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/cambio", method = { RequestMethod.GET })
	public Object  inicioTramite( Model model, HttpSession session, Locale locale, HttpServletRequest request) {

		String error = null;
		GrupoFamiliar beneficiario = (GrupoFamiliar)session.getAttribute(PORTALC_CLINICA_BENEFICIARIO);
		Solicitud solicitud = new Solicitud();
		TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();
		Boolean actualizarIntegrantes = (Boolean)session.getAttribute(PORTALC_CLINICA_CAMBIOINTEGRANTES);
		String vista = VIEW_INFORMACION_PERSONA;
		
		model.addAttribute("muestraUmf",false);
		model.addAttribute("domicilio", new Domicilio());
		
		try{
			
			if( actualizarIntegrantes != null && actualizarIntegrantes.equals(true) ){
				beneficiario = (GrupoFamiliar) session.getAttribute(PORTALC_CLINICA_ASEGURADO);
			}
			
			this.myLocale = locale;
			
			tramite =  validarAsegurado(beneficiario, session, model, request );
    		
			if( tramite.getErrorFormGeneral() == null ){
				solicitud = this.crearTramite(session, TipoTramiteEnum.CAMBIO_CLINICA.getCodigo(), tramite);
	    		tramite = (TramiteCorreccionDerechohabiente)solicitud.getTramites().get(0);
	    		tramite.setPaso(1L);
			}
			
			
			error = tramite.getErrorFormGeneral();
			
			model.addAttribute("tramite", tramite);
		
			
			
		}catch( PersonaFisicaNoEncontradaException e ){	
			// ----------------------------------------------------
			// La persona no se encontr� en la base
			// ----------------------------------------------------
			error = getMessage("personaNoEncontrada");
			
		}catch( SolicitudesEnProcesoException e ){	
			// ----------------------------------------------------
			// No tiene cl�nica ni domicilio
			// ----------------------------------------------------
			vista = VIEW_SOLICITUD_EXISTENTE;
			solicitud = e.getSolicitud();
			session.setAttribute(KEY_SOLICITUD_REGISTRADA, solicitud);
			error = getMessage("SolicitudEnProcesoException");
			
		}catch( CodigoSinUmfException e ){	
			// ----------------------------------------------------
			// No tiene cl�nica ni domicilio
			// ----------------------------------------------------
			error = getMessage("clinicaDomicilioFaltantesException");
		
		}catch( VigenciaException e ){	
			// ---------------------------------------------------
			// El asegurado esta en baja o fallecido 
			// ---------------------------------------------------
			error = getMessage("enBajaOFallecido");
		
		}catch( RegistroInvalidoException e ){	
			// ----------------------------
			// No esta registrado  
			// ----------------------------
			error = getMessage("personaNoRegistrada");
		
		}catch( ModalidadesException e ){
			
			// ----------------------------------------------------------------
			// Tipo de tr�mite no soportado por la modalidad del asegurado
			// ----------------------------------------------------------------
			error = e.getMessage();
			
		} catch (Exception e) {
			e.printStackTrace();
			error = e.getMessage();
		}
		
		model.addAttribute("solicitud", solicitud);
		model.addAttribute("error",error);
		model.addAttribute("botonRegresar", true);
		
		
		return vista;
		
	}
	
	
	/**
	 * Valida:
	 * 		- Solicitudes registradas
	 * 		- Esta vigente
	 * 		- Esta registrado
	 * 		- Tiene domicilio
	 * 		- Tiene UMF; puede cambiar los datos de la UMF
	 * 
	 * 
	 * @param beneficiario
	 * @param session
	 * @param model
	 * @param request
	 * @return
	 * @throws VigenciaException
	 * @throws RegistroInvalidoException
	 * @throws SolicitudEnProcesoException
	 * @throws CodigoSinUmfException
	 */
	private TramiteCorreccionDerechohabiente validarAsegurado(GrupoFamiliar beneficiario, HttpSession session, Model model, HttpServletRequest request )
			throws VigenciaException, RegistroInvalidoException, SolicitudesEnProcesoException, CodigoSinUmfException, ModalidadesException{
		
		
		// -----------------------------------------------------------------------------------------
		// El controlador que registra el cambio de cl�nica, necesita un tr�mite de correcci�n
		// -----------------------------------------------------------------------------------------
		TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();
		tramite.setIndSeleccionMedico(1); 
		tramite.setIdAsignacionNss(beneficiario.getAsignacionNSS().getIdAsignacionNSS());
		tramite.setPaso(0L);
		tramite.setParentesco(beneficiario.getParentesco());
		tramite.setPersona(beneficiario.getDerechohabiente());
		
		
		Domicilio domicilio = null;
		long umfSeleccionada = -1;
		long turnoSeleccionado = -1;
		long consultorioSeleccionado = -1;
		String error = null;
		boolean enCircunscipcionForanea = false;
		boolean muestraUmf = false;
		final Errors errors = new BindException(tramite, "model");
		Solicitud solicitudRegistrada = null;
		Boolean modalidadPermitida = false;
		
		
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar)session.getAttribute(PORTALC_CLINICA_CABEZAGRUPO);
		
		@SuppressWarnings("unchecked")
		List<Long> idsModalidades = (List<Long>)session.getAttribute(PORTALC_CLINICA_MODALIDADES_IDS);
		
		if( !esVigente(beneficiario.getEstadoDerechohabiente()) ){
			throw new VigenciaException();
		}
		
		if( beneficiario.getIndRegistrado().equals(0) ){
			throw new RegistroInvalidoException();
		}
		
		boolean esCabezaFamilia = this.esCabezaFamilia(beneficiario.getParentesco());
		solicitudRegistrada = this.obtenerSolicitudesRegistradas(beneficiario.getDerechohabiente().getIdPersona());
		
		if( solicitudRegistrada != null ){
			throw new SolicitudesEnProcesoException(solicitudRegistrada);
		}
		
		
		if( beneficiario.getDomicilio() != null ){
			domicilio = beneficiario.getDomicilio();
			
			if( !esCabezaFamilia ){
				
				// -----------------------------------------------------------------------------
				// El beneficiario debe tener un c�digo postal con umf's en la delegaci�n 
				// del asegurado
				// -----------------------------------------------------------------------------
				
				Long idDelegacionAsegurado = (Long)session.getAttribute("PORTALC_CLINICA_UMFDELEGACION");
				CodigoPostal cp = new CodigoPostal();
				cp.setCodigoPostal(domicilio.getAsentamiento().getCodigoPostal().getCodigoPostal());
				List<UnidadMedicaFamiliar> umfs = this.getUmfsByCodigoPostal(cp, idDelegacionAsegurado, session);
				
				
				// --------------------------------------------------------------------------------
				// El c�digo postal del beneficiario no devolvera UMF's al combo de cl�nicas
				// --------------------------------------------------------------------------------
				if( umfs == null || umfs.size() == 0 ){
					enCircunscipcionForanea = true;
				}
			}
			
		}
		
		
		
		// ----------------------------------------------------------------------------------
		// Validamos si las modalidades del asegurado permiten realizar el tr�mite
		// ----------------------------------------------------------------------------------
		if( idsModalidades.isEmpty() ){
			
			// ------------------------------------------------------------
			// Los pensionados pueden no tener patr�n
			// ------------------------------------------------------------
			if( cabezaGrupo.getCalidadParentesco().getIdParentesco() != ParentescoEnum.PENSIONADO.getId() )
				throw new ModalidadesException(getMessage("ModalidadesException"));
			
		}else{
			
			try {
				Map<String, Object> result = requisitosMinimosService.tramitePermitidoParaAseguradoPensionado(cabezaGrupo,TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue(),false,idsModalidades);
				modalidadPermitida = (Boolean)result.get("correcto");
			} catch (DerechohabientesBusinessException e) {
				e.printStackTrace();
				log.debug("ClinicaController -> tramitePermitidoParaAseguradoPensionado : "+e.getMessage());
				throw new ModalidadesException(getMessage("ModalidadesException"));
			}
			
	        if( !modalidadPermitida ){
	        	throw new ModalidadesException(getMessage("ModalidadesException"));
	        }
	        
		}
        
        
		// ---------------------------------------------------------------------------
		// Se valida el turno y el consultorio que apareceran en la vista por default
		//
		// El asegurado siempre debe tiener UMF, ya que se necesita para validar la 
		// circunscripci�n for�nea. 
		// ---------------------------------------------------------------------------
		if( this.tieneUmf(beneficiario.getMedicoEnTurno()) ){
			
			UnidadMedicaFamiliar umf = beneficiario.getMedicoEnTurno().getUnidadMedicaFamiliar();
			
			umfSeleccionada = umf.getIdUMF();
			session.setAttribute(PORTALC_CLINICA_UMFSELECCIONADA, umfSeleccionada);
			
			
			
			if( beneficiario.getMedicoEnTurno().getTurno() != null ){
				turnoSeleccionado = beneficiario.getMedicoEnTurno().getTurno().getIdTurno();
			}
		
			if( beneficiario.getMedicoEnTurno().getConsultorio() != null){
				consultorioSeleccionado = beneficiario.getMedicoEnTurno().getConsultorio().getIdConsultorio();
			}
			
			
			// -------------------------------------------------------------------------------------------------
			// Si el �ltimo cambio de clinica es menor a 365 d�as, no puede cambiar el m�dico y consultorio
			// Si existen otros beneficiaros en la misma cl�nica, deben tener el mismo m�dico y consultorio 
			// -------------------------------------------------------------------------------------------------
			HashMap<String,Object> result = (HashMap<String, Object>) this.buscarMedicoEnTurnoActivo(beneficiario);
    			
	    	if( !(Boolean)result.get("error") ){
	    		if( !(Boolean)result.get("cambioPosible") ){
	    			tramite.setIndSeleccionMedico(0);
	    		}
	    	}
			
			
			tramite.setMedicoEnTurno(beneficiario.getMedicoEnTurno());
		    new DomicilioClinicaValidator().validaDatosAdscripcion(tramite, errors);
	        
	        if( !errors.hasErrors() ){
	        	session.setAttribute(PORTALC_CLINICA_UMF, beneficiario.getMedicoEnTurno());
	        }	
	        
	        
			if( esCabezaFamilia ){
				session.setAttribute(PORTALC_CLINICA_UMFDELEGACION, umf.getSubdelegacion().getDelegacion().getId());
    		}else{
    	
    			// ----------------------------------------------------------------
				// No se atienden circunscripciones for�neas
				// Los beneficiarios deben estar en la delegaci�n del asegurado
				// ----------------------------------------------------------------
				
    			Long idDelegacionAsegurado = (Long)session.getAttribute(PORTALC_CLINICA_UMFDELEGACION);
    			if( !umf.getSubdelegacion().getDelegacion().getId().equals(idDelegacionAsegurado) ){
    				enCircunscipcionForanea = true;
    			}
    			
    			
    		}
        
    		
			
		}
    		
		
		
		if( umfSeleccionada == -1){
			if(domicilio == null ){
    			// --------------------------------------------------------
    			// No tiene asignada cl�nica ni domicilio
    			// --------------------------------------------------------
    			CodigoSinUmfException.throwException();
			}else{
				muestraUmf = true;
			}
		
		}else{
			if( domicilio != null ){
				tramite.setPaso(1L);
				muestraUmf = true;
			}
		}
	
	
		if( enCircunscipcionForanea )
			muestraUmf = false;

	
		if( domicilio == null )
			domicilio = new Domicilio();
		
		session.setAttribute(PORTALC_CLINICA_DOMICILIO, domicilio);
		
		tramite.setErrorFormGeneral(error);
		
		model.addAttribute("domicilio", domicilio);
		model.addAttribute("umfSeleccionada", umfSeleccionada);
		model.addAttribute("turnoSeleccionado", turnoSeleccionado);
		model.addAttribute("consultorioSeleccionado", consultorioSeleccionado);
		model.addAttribute("error",error);
		model.addAttribute("muestraUmf",muestraUmf);
		model.addAttribute("enCircunscipcionForanea", enCircunscipcionForanea);
		
		return tramite;
		
	}
	
	
	/**
	 * Valida si el parentesco es asegurado o pensionado
	 * 
	 * @param parentesco
	 * @return
	 */
	private boolean esCabezaFamilia( Parentesco parentesco ){
		
		if( parentesco.getIdParentesco().equals( ParentescoEnum.ASEGURADO.getId() ) ){
			return true;
		}
		
		if( parentesco.getIdParentesco().equals( ParentescoEnum.PENSIONADO.getId() ) ){
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Valida si el tiene UMF
	 * 
	 * @param medicoEnTurno
	 * @return
	 */
	private boolean tieneUmf( MedicoEnTurno medicoEnTurno ){
		
		if( medicoEnTurno != null ){
			if( medicoEnTurno.getUnidadMedicaFamiliar() != null && medicoEnTurno.getUnidadMedicaFamiliar().getIdUMF() != null){
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Agrega todos los intregrantes vigentes del grupo familiar al tr&aacute;mite
	 * 
	 * @param tramite
	 * @param tienePatronImss
	 * @param umfDelegacion Delegaci�n de la cl�nica de la cabeza de familia
	 * 
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	private TramiteCorreccionDerechohabiente agregarGrupoFamiliar(TramiteCorreccionDerechohabiente tramite, boolean tienePatronImss, Long umfDelegacion ) 
			throws DerechohabientesBusinessException, Exception{
		
		Parentesco parentesco = tramite.getParentesco();
		Boolean isAsegurado = parentesco.equals(ParentescoEnum.ASEGURADO.getId()) || parentesco.equals(ParentescoEnum.PENSIONADO.getId());
		
		if( isAsegurado ){
		
			List<Long> ids = new ArrayList<Long>();
			List<Fisica> personas = new ArrayList<Fisica>();
			Derechohabiente derechohabiente = (Derechohabiente)tramite.getPersona();
			
			
			List<Long> parentescos = new ArrayList<Long>();
			parentescos.add(ParentescoEnum.PADRES.getId());
			parentescos.add(ParentescoEnum.CONCUBINARIO.getId());
			parentescos.add(ParentescoEnum.HIJOS.getId());
			parentescos.add(ParentescoEnum.CONYUGE.getId());
			
			
			List<GrupoFamiliar> integrantes = grupoFamiliarService.findGrupoFamiliarPorParentescos(tramite.getIdAsignacionNss(), parentescos, true);
			
			
			// ----------------
			// Asegurado
			// ----------------
			ids.add(derechohabiente.getIdPersona());
			personas.add(derechohabiente);
			
			if( integrantes != null ){
				for(GrupoFamiliar integrante: integrantes) {
					
					EstadoDerechohabiente estado = integrante.getEstadoDerechohabiente();
					Parentesco parentescoI = integrante.getParentesco();
					
					if( !derechohabienteService.tieneCircunscripcionForeanea(integrante.getDerechohabiente())){
					
						if(estado != null && estado.getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.VIGENTE.getId())) {
							
							if( (integrante.getDerechohabiente().getUmf() != null) &&  (umfDelegacion.equals(integrante.getDerechohabiente().getUmf().getIdUMF())) ){
							
								switch( ParentescoEnum.obternerEnumById(parentescoI.getIdParentesco()) ){
									case PADRES:
										if(!tienePatronImss ){
											ids.add(integrante.getDerechohabiente().getIdPersona());
											personas.add(integrante.getDerechohabiente());
										}
										break;
									
									case CONCUBINARIO:
									case HIJOS:
									case CONYUGE:
										ids.add(integrante.getDerechohabiente().getIdPersona());
										personas.add(integrante.getDerechohabiente());
										break;
										
									default:
										// -------
										// �?
										// -------
										break;
								}
								
							}
									
						}
						
					}
					
					
					
				}
			}
			
			tramite.setCandidatosCambioClinica(ids);
			tramite.setPersonas(personas);
			
		}
		
		
		return tramite;
		
		
	}
	
	
	

	/**
	 * Obtiene las umf's para un c&oacute;digo postal dado
	 *	
	 * @param request
	 * @param idDelegacionAsegurado
	 * @param codigoPostal
	 * @return List<UnidadMedicaFamiliar>
	 */
	private List<UnidadMedicaFamiliar> getUmfsByCodigoPostal(CodigoPostal codigoPostal, Long idDelegacionAsegurado  ,final HttpSession session) {
		List<UnidadMedicaFamiliar> umfs = null;
		try {
			
			List<UnidadMedicaFamiliar> _umfs = null;
			_umfs = umfService.findUmfByCodigoPostal(codigoPostal.getCodigoPostal().toString());
			Boolean esCabezaEnClinica = (Boolean)session.getAttribute(ClinicaController.PORTALC_CLINICA_ACTUALIZA_CABEZA);
			
			if(idDelegacionAsegurado == null)
				idDelegacionAsegurado = (Long)session.getAttribute(ClinicaController.PORTALC_CLINICA_UMFDELEGACION);
			
			if( idDelegacionAsegurado != null && esCabezaEnClinica == null ){
				umfs = new ArrayList<UnidadMedicaFamiliar>();
				for( UnidadMedicaFamiliar umf : _umfs ){
					if( umf.getSubdelegacion().getDelegacion().getId().equals(idDelegacionAsegurado) ){
						umfs.add(umf);
					}
				}
			}else{
				umfs = _umfs;
			}
			
			
			
		} catch (CodigoSinUmfException e) {
			e.printStackTrace();
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return umfs;
	}
	
	
	
	/**
	 * Valida en la sesi�n los datos del beneficiario y asegurado para saber si el la
	 * misma persona
	 * 
	 * @param session
	 * @return
	 */
	private boolean esCabezaFamilia(HttpSession session){
		
		GrupoFamiliar asegurado = (GrupoFamiliar)session.getAttribute(PORTALC_CLINICA_ASEGURADO);
		GrupoFamiliar beneficiario = (GrupoFamiliar)session.getAttribute(PORTALC_CLINICA_BENEFICIARIO);
		boolean esCabezaFamilia = true;
		
		try{
			if( !asegurado.getDerechohabiente().getIdPersona().equals(beneficiario.getDerechohabiente().getIdPersona()  )){
				esCabezaFamilia = false;
			}
		}catch(NullPointerException e){
			// ------------------------------------------------------------------------
			// Antes de modificar un beneficiario, se podr�an actualizar
			// los datos del asegurado y en ese momento no existiria beneficiario 
			// en la sesi�n
			// ------------------------------------------------------------------------
		}
		
		
		return esCabezaFamilia;
		
		
	}
	
	
	/**
	 * Busca en el grupo familiar y solamente una persona de la lista esta registrada
	 * 
	 * @param personas
	 * @param idAsignacionNss
	 * @return
	 */
	private GrupoFamiliar buscaPersonaEnGrupoFamiliar( List<Fisica> personas, Long idAsignacionNss ) throws BeneficiarioBusinessException {
		
		GrupoFamiliar integrante = null;
		
		try{
			if( personas.size() == 1 ){
				
				
				try{
					integrante = grupoFamiliarService.getIntegranteGrupoFamiliarPorIdPersona(idAsignacionNss, personas.get(0).getIdPersona());
				}catch( Exception e ){
					// -----------------------------------------------------------------------------
					// En caso de que la persona candidata no se encuentre en el WS o en BDTU
					// -----------------------------------------------------------------------------
				}
				
				
				
			}else if( personas.size() > 1 ){
				
				List<Long> idPersonas = new ArrayList<Long>();
				
				for( Fisica persona : personas ){
					idPersonas.add(persona.getIdPersona());
				}
				
				List<GrupoFamiliar> integrantes = grupoFamiliarService.obtenerIntegrantesEnLista(idPersonas, idAsignacionNss);
				
				if( integrantes != null ){
					
					if( integrantes.size() == 1 ){
						
						try{
							integrante = grupoFamiliarService.getIntegranteGrupoFamiliarPorIdPersona(idAsignacionNss, integrantes.get(0).getDerechohabiente().getIdPersona());
						}catch( Exception e ){
							// -----------------------------------------------------------------------------
							// En caso de que la persona candidata no se encuentre en el WS o en BDTU
							// -----------------------------------------------------------------------------
						}
						
					}else if( integrantes.size() > 1 ){
						new BeneficiarioBusinessException(this.getMessage("CurpRepetidaEnGrupoException"));
					}
					
				}
				
				
			}
			
		}catch( DerechohabientesBusinessException e ){
			// ------------------------------------------------------------------
			// Ocurrio un error al buscar la lista de personas en grupo familiar
			// ------------------------------------------------------------------
		}
		
		return integrante;
	}
	

	
	@RequestMapping(value = "/cancelarSolicitud", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<String> cancelarSolicitudRegistrada(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		
		RespuestaJSON<String> result = new RespuestaJSON<String>();
		result.setEstado(false);
	
		
		try{
			
		
			Long idAsignacionNss = (Long)session.getAttribute(PORTALC_CLINICA_IDASIGNACION);
			result.setModelo("inicial/"+idAsignacionNss);
			
			Solicitud solicitud = (Solicitud)session.getAttribute(KEY_SOLICITUD_REGISTRADA);
			this.cancelarSolicitud(solicitud);
			session.removeAttribute(KEY_SOLICITUD_REGISTRADA);
			
			GrupoFamiliar beneficiario = (GrupoFamiliar)session.getAttribute(PORTALC_CLINICA_BENEFICIARIO);
			if( beneficiario != null){
				result.setModelo("clinicaBeneficiarios");
			}
			
			result.setEstado(true);
		
		}catch(Exception e){
			result.setMensaje(e.getMessage());
		}
		
		return result;
	}

	
	

	
	private TramiteCorreccionDerechohabiente obtenerCorreccion(Solicitud solicitud){
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteCorreccionDerechohabiente) {
				return (TramiteCorreccionDerechohabiente) tramite;
			}
		}
		return null;
	}
	
	private Map<String, Object> cambioClinica(GrupoFamiliar antiguo, TramiteCorreccionDerechohabiente correccion) {
		Map<String, Object> validacionesClinica = new HashMap<String, Object>();
		Boolean existeCambio = false;
		Boolean existeCambioConsultorio = false;
		
		if(antiguo.getMedicoEnTurno() == null && correccion.getMedicoEnTurno() != null) {
			existeCambio =  true;
		}
		
		MedicoEnTurno morigen = antiguo.getMedicoEnTurno();
		MedicoEnTurno mdestino = correccion.getMedicoEnTurno();
		
		if(morigen != null && mdestino != null) {
			UnidadMedicaFamiliar origen = morigen.getUnidadMedicaFamiliar();
			UnidadMedicaFamiliar destino = mdestino.getUnidadMedicaFamiliar();
			
			if(origen == null && destino != null) {
				existeCambio =  true;
			}
			
			if((origen != null && destino != null) && (!origen.getIdUMF().equals(destino.getIdUMF()))) {
				existeCambio =  true;
			}
			
			if(!existeCambio && (morigen != null && mdestino != null && !morigen.getIdMedicoContultorioTurno().equals(mdestino.getIdMedicoContultorioTurno()))) {
				existeCambioConsultorio = true;
			}
		}
		
		validacionesClinica.put("existeCambioClinica", existeCambio);
		validacionesClinica.put("existeCambioConsultorio", existeCambioConsultorio);
		
		return validacionesClinica;
	}

	
	private List<GrupoFamiliar> getPadresConcubina(AsignacionNSS asignacion, CabezaGrupoFamiliar cabeza) {
		
		
		log.debug("1: "+asignacion);
		log.debug("2: "+cabeza.getPatronImss());
		
		List<GrupoFamiliar> grupoPadres = null;
		List<Long> parentescos = new ArrayList<Long>();
		parentescos.add(ParentescoEnum.PADRES.getId());
		parentescos.add(ParentescoEnum.CONCUBINARIO.getId());
		
		Long numeroIntegrantes = grupoFamiliarService.getNumeroDeIntegrantesPorListParentesco(asignacion.getIdAsignacionNSS(), parentescos);
		
		if(numeroIntegrantes != null && numeroIntegrantes.intValue() > 0) {
			try {
				grupoPadres =asignacionDomicilioServiceRemote.getPadresConcubinasParaCambio(asignacion, cabeza.getPatronImss());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return grupoPadres;
	}
	

	/**
	 * Obtiene las modialidades de los patrones del asegurado
	 * 
	 * @param sujetos
	 * @param cabeza
	 * @return
	 */
	private List<Modalidad> obtenerModalidadesPatrones(List<SujetoObligado> sujetos, CabezaGrupoFamiliar cabeza) {
		
		List<Modalidad> modalidades = new ArrayList<Modalidad>();
		
		if(sujetos != null && !sujetos.isEmpty()) {
			
			for(SujetoObligado sujeto: sujetos) {
				modalidades.add(sujeto.getModalidad());
			}
		} else {
			
			try{
				modalidades.add(cabeza.getPatronSujetoObligado().getModalidad());
			}catch(NullPointerException e){
				
				// ----------------------------------------------------
				// Los pensionados pueden no tener patr�n
				// ----------------------------------------------------
				if(  cabeza.getCalidadParentesco().getIdParentesco() != ParentescoEnum.PENSIONADO.getId() ){
					throw e;
				}
				
			}
		}
		
		return modalidades;
	}
	
	private List<Long> getModalidadesActivas(List<Modalidad> modalidades) {
		List<Long> idsModalidades = new ArrayList<Long>();
		
		if(modalidades != null && !modalidades.isEmpty()) {
			
			for(Modalidad mod: modalidades) {
				idsModalidades.add(mod.getIdModalidad());
			}
		}
		
		
		return idsModalidades;
	}
	
	
	/**
	 * Obtiene los tr&aacute;mites 
	 * 
	 * @param idPersona
	 * @return
	 */
	private List<Tramite> obtenerTramitesCambioClinica(Long idPersona){

		try{
			// ---------------------------------------------
			// Primer dia de anio
			// ---------------------------------------------
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			cal.set(year, 0, 1,0,0,0); 

			Long[] origenes = {OrigenSolicitudEnum.PORTAL_CIUDADANO.getId(),OrigenSolicitudEnum.MOVILES.getId()};

			return this.solicitudTramiteBusiness.obtenerTramitesCerrados(
					Arrays.asList(origenes),TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE.getValor(), idPersona, cal.getTime(), TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());
		}catch(Exception e){

		}

		return null;

	}
}
 