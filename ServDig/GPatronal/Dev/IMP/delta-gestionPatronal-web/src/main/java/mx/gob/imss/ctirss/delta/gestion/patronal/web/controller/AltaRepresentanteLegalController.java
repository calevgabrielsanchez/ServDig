package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalYaExisteException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.framework.util.RegexValidatorUtil;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.RepresentanteLegalDataTable;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSocios;
import mx.gob.imss.ctirss.delta.web.validator.PersonaRFCValidator;
import mx.gob.imss.ctirss.delta.web.validator.PersonaValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.functors.InstanceofPredicate;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Proyecto: IMSS Digital - ${artifactId}
 */
@Controller
@RequestMapping("/wizard/alta/representeLegal/")
public class AltaRepresentanteLegalController extends AbstractController{
	
	private static final String KEY_REPRESENTADO_SESSION 	= "_RepresentadoSession";	
	private static final String KEY_BUSQUEDA_PERSONA 		= "busquedaPersona";
	private static final String KEY_RL_SESSION 				= "repLegal";
	private static final String KEY_ERROR_GENERAL			= "errorFormGeneral";
	private static final String KEY_OFORM 					= "oForm";
	private static final String KEY_NEGOCIO 				= "negocio";	
	private static final String KEY_PROCESADO 				= "procesado";
	private static final String VIEW_AGREGAR_RL 			= "viewAgregarRL";
	
	private static final String KEY_USUARIO_SSO = "usuarioSSO";
	private static final String KEY_ATRIBUTE_RESPUESTA = "respuesta";
	private static final String KEY_ATRIBUTE_MSGERROR = "msgError";
	private static final String KEY_RL_REQUERIDO = "rlRequerido";
	private static final String KEY_ATRIBUTE_MENSAJE = "mensaje";
	private static final String KEY_ATRIBUTE_ERROR = "error";
	private static final String KEY_ATRIBUTE_SOLICITUD = "solicitud";
	
	//Mensajes
	private static final String MSG_FINALIZAR_SOLICITUD = "Su solicitud ha finalizado correctamente";
	private static final String MSG_OBSERVACION_CANCELACION ="Solicitud cancelada por el usuario ";
	private static final String MSG_CANCELAR_SOLICITUD = "La solicitud fue cancelada correctamente";
	private static final String MSG_CANCELAR_SOLICITUD_ERROR = "Error al cancelar la solicitud: ";
	
	private static final String MOSTRAR_RL_VENTANILLA = "viewRepresentantesLegales";
	
	//@Autowired 
	//private IndividuoServiceBusinessRemote individuoServiceBusiness;
	@Autowired 
	private PersonaBusinessRemote personaBusiness;
	@Autowired 
	private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusinessRemote;
	@Autowired 
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	@Autowired 
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@Autowired 
	private SolicitudBusinessRemote solicitudBusinessRemote;
	
	@RequestMapping(value = "inicio", method = {RequestMethod.GET, RequestMethod.POST })
	public String mostrarCartaTerminos(Model model, HttpSession session, 
			HttpServletRequest request) {
		//Persona persona = new Persona();
		Fisica persona = new Fisica();
		model.addAttribute(KEY_BUSQUEDA_PERSONA, persona);
		session.setAttribute(KEY_BUSQUEDA_PERSONA, persona);
		request.setAttribute(KEY_PROCESADO, false);
		return VIEW_AGREGAR_RL;
	}
	
    @RequestMapping(value = "validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody Fisica oForm, 
    		final HttpServletResponse response, final HttpSession session) {        
    	log.trace("AltaRepresentanteLegalController para validar el objeto de formulario --> " 
        	+ ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
    	final Map<String, Object> result = new HashMap<String, Object>();
        final Errors errors = new BindException(oForm, "model");
        new PersonaValidator().validate(oForm, errors);
        if(!errors.hasErrors()){
        	new PersonaRFCValidator().validate(oForm, errors);
        	RegexValidatorUtil.validaCURPVista("curp", errors, oForm.getCurp());
        }        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put(KEY_OFORM, oForm);
        result.put(KEY_NEGOCIO, this.getErroresNegocio(session, oForm));        
        return result;

    }
	
	@RequestMapping("localizar")
	public String localizarRL(Model model,@ModelAttribute(value=KEY_BUSQUEDA_PERSONA) Fisica busquedaPersona, 
			BindingResult result, HttpServletRequest request, HttpSession session) {		
		request.setAttribute(KEY_PROCESADO, false);
		RepresentanteLegal representanteLegal = (RepresentanteLegal) session.getAttribute(KEY_RL_SESSION);
		try {
			representanteLegalServiceBusinessRemote.altaRepresentanteLegal(representanteLegal);
			request.setAttribute(KEY_PROCESADO, true);
		} catch (GestionPatronalBusinessException e) {
			request.setAttribute(KEY_ERROR_GENERAL,  e.getMessage());
			session.removeAttribute(KEY_RL_SESSION);
		}
		return VIEW_AGREGAR_RL;
	}
	
	@RequestMapping(value = "limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody void limpiarSesion(final HttpSession session) {
		session.removeAttribute(KEY_RL_SESSION);
	}
	
	@RequestMapping(value = "cargarPaginacionRL", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody DatosSalidaPaginador<RepresentanteLegal> cargarRLsPagina(@RequestBody RepresentanteLegalDataTable aoData,
			Model model, HttpServletRequest request, HttpSession session) {
		DatosEntradaPaginador<RepresentanteLegal> send = new DatosEntradaPaginador<RepresentanteLegal>();
		DatosSalidaPaginador<RepresentanteLegal> reply = new DatosSalidaPaginador<RepresentanteLegal>();
		send.parserArray(null);
        
        Persona representado = (Persona) session.getAttribute(KEY_REPRESENTADO_SESSION);
        RepresentanteLegal rl = new RepresentanteLegal();
		if(representado!=null){
			Long idPersonaFM = null;	//CveMoral o CveFisica del Representado
			Long idTipoPersona = TipoPersona.TIPO_PERSONA_FISICA;
			if(representado instanceof Fisica){
				idPersonaFM = ((Fisica) representado).getCveFisica();
			}else{
				idPersonaFM = representado.getIdPersona();
				idTipoPersona = TipoPersona.TIPO_PERSONA_MORAL;
			}	
			rl.setCveIdPersona(idPersonaFM);
			rl.setTipoPersonaRepresentada(new TipoPersona());
			rl.getTipoPersonaRepresentada().setIdTipoPersona(idTipoPersona);
			
	        send.setModelo(rl);
	        reply = sujetoObligadoServiceBusiness
	        	.paginarRepresentanteLegal(send);
			reply.setsEcho(send.getsEcho());
		}
		return reply;
	}
	
	@RequestMapping(value = "{idSolicitud}/{idTipoTramite}", method = {RequestMethod.GET, RequestMethod.POST })
	public String obtenerRepresenantesLegales(final Model model, HttpSession session, HttpServletRequest request,
			@PathVariable Long idSolicitud, @PathVariable Integer idTipoTramite) {		
		
		procesarRepresenantesLegales(model,request, session, idSolicitud, idTipoTramite);
		return MOSTRAR_RL_VENTANILLA;
	}
	
	@RequestMapping(value = "/validarRL/{cveIdPersona}", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> validaRL(HttpSession session, HttpServletRequest request, 
			@PathVariable Long cveIdPersona) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			sujetoObligadoServiceBusiness
				.validaRepresentanteLegalExistente(cveIdPersona, TipoPersona.TIPO_PERSONA_MORAL);
			result.put(KEY_ATRIBUTE_RESPUESTA, 0);
		} catch (AbstractException e) {
			result.put(KEY_ATRIBUTE_RESPUESTA, 1);
			result.put(KEY_ATRIBUTE_MSGERROR, e.getMessage());
		}
		return 	result;
	}
	
	@RequestMapping(value = "finalizarVentanilla/{idSolicitud}/{cveIdPersona}/{idTipoTramite}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitudVentanilla(HttpSession session,
			@PathVariable Long idSolicitud, @PathVariable Long cveIdPersona, @PathVariable Integer idTipoTramite) {
		Map<String, Object> result = new HashMap<String, Object>();		
		try {
			Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
			TipoTramiteEnum eTipoTramite = TipoTramiteEnum.obternerEnumById(idTipoTramite);
			Object tramiteInicial = null;
			InstanceofPredicate tramitePredicate = null;
			
			//TODO:	Acorde al tramite, Almacenar RL seleccionado:
			
			if(eTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO) || 
					eTipoTramite.equals(TipoTramiteEnum.BAJA_SOCIO)){				
				tramitePredicate = new InstanceofPredicate(TramiteSocios.class);
				tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
				TramiteSocios tramiteHelper = (TramiteSocios) tramiteInicial;
				//Setear RL en atributo de TRAMITE
				tramiteHelper.setIdPersonaRL(cveIdPersona);
				
			}else if (eTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL)){
				tramitePredicate = new InstanceofPredicate(TramiteRepresentanteLegal.class);
				tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
				TramiteRepresentanteLegal tramiteHelper = (TramiteRepresentanteLegal) tramiteInicial;
				//Setear RL en atributo de TRAMITE
				if (cveIdPersona != null && cveIdPersona > 0) {
					tramiteHelper.setIdPersonaRL(cveIdPersona);
				} else {
					Long cveIdPersonaTramite = tramiteHelper.getFisica().getIdPersona();
					tramiteHelper.setIdPersonaRL(cveIdPersonaTramite);
				}
				
			}else if (eTipoTramite.equals(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL)	||
					eTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES) ||
					eTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
				tramitePredicate = new InstanceofPredicate(TramiteFisica.class);
				tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
				if(tramiteInicial!=null){
					TramiteFisica tramiteHelper = (TramiteFisica) tramiteInicial;
					//Setear RL en atributo de TRAMITE
					if (cveIdPersona != null && cveIdPersona > 0) {
						tramiteHelper.setIdPersonaRL(cveIdPersona);
					} else {
						Long cveIdPersonaTramite = tramiteHelper.getFisica().getIdPersona();
						tramiteHelper.setIdPersonaRL(cveIdPersonaTramite);
					}
				}else{
					tramitePredicate = new InstanceofPredicate(TramiteMoral.class);
					tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
					if(tramiteInicial!=null){						
						TramiteMoral tramiteHelper = (TramiteMoral) tramiteInicial;
						//Setear RL en atributo de TRAMITE
						tramiteHelper.setIdPersonaRL(cveIdPersona);
					}
				}				
			}			
			
			solicitudBusinessRemote.finalizarCapturaSolicitud(solicitud, null);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_FINALIZAR_SOLICITUD);
		} catch (AbstractException e) {
			this.log.error(e);
			result.put(KEY_ATRIBUTE_ERROR, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitudModificacionDatos(
			@PathVariable Long idSolicitud, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();		
		try {
			Solicitud solicitud = new Solicitud();
			solicitud.setEstadoSolicitud(new EstadoSolicitud());
			solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
			solicitud.setSolicitudId(idSolicitud);
			solicitud.setSolicitante(getUsuarioSession(session));
			solicitud.setObservacion(MSG_OBSERVACION_CANCELACION + ((solicitud.getSolicitante() != null 
				&& solicitud.getSolicitante().getUsuario() != null) ? solicitud.getSolicitante().getUsuario() : ""));
			solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_CANCELAR_SOLICITUD);
			result.put(KEY_ATRIBUTE_SOLICITUD, solicitud);			
		} catch (AbstractException e) {
			log.error(e);
			result.put(KEY_ATRIBUTE_MENSAJE, MSG_CANCELAR_SOLICITUD_ERROR + e.getMessage());
		}	
		return result;
	}

	private Persona getErroresNegocio(HttpSession session, Fisica rl) {
		Persona resultado = new Persona();
		Persona representado = (Persona) session.getAttribute(KEY_REPRESENTADO_SESSION);
		if(representado!=null){
			//Localizar a la persona Representada
			if(representado instanceof Fisica){
				Long cveFisica = ((Fisica) representado).getCveFisica();
				representado = personaBusiness.getPersonaFisica(
					representado.getIdPersona() != null ? representado.getIdPersona():0L);
				if(representado!=null){
					representado.setTipoPersona(new TipoPersona());
					representado.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
					((Fisica) representado).setCveFisica(cveFisica);
				}
			}else{
				representado = personaMoralBusiness.getPersonaMoral(
					representado.getIdPersona() != null ? representado.getIdPersona():0L);
				if(representado!=null){
					representado.setTipoPersona(new TipoPersona());
					representado.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				}	
			}
			if(representado==null) {
				resultado.setErrorFormGeneral("No se localizo a la persona representada.");
				return resultado;
			}						
			Persona rlEncontrado = null;
			//El RL y la persona Representada no deben ser la misma.
			if(rl.getRfc().equals(representado.getRfc())) {
				resultado.setErrorFormGeneral("No es posible registrar el RFC como representante del mismo.");
				return resultado;
			}
			
			try {	
				rlEncontrado = representanteLegalServiceBusinessRemote.localizarRL(rl);	
			} catch (AbstractException e) {
				resultado.setErrorFormGeneral(e.getMessage());
				e.printStackTrace();
				return resultado;
			}
			if(rlEncontrado != null) {
				//SE crea un objeto representante legal para saber si ya existe la relacion
				RepresentanteLegal representanteLegal = new RepresentanteLegal();
				representanteLegal.setAccion(TipoAccionAfectacionEnum.AGREGAR);
				representanteLegal.setIndActAdmonDominio(BigDecimal.ONE);				
				//RepresentanteLegal
				representanteLegal.setPersonaFisica((Fisica)rlEncontrado);
				//Representado (PF/PM). Persona que requiere agregar al RL
				representanteLegal.setCveIdPersona(representado.getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(representado.getTipoPersona());				
				//Verificamos que no exista la relacion como epresentante
				try {					
					//Si la persona no existe la validación no se aplica
					if(rlEncontrado != null && rlEncontrado.getIdPersona()!=null){
						representanteLegalServiceBusinessRemote
							.existeRelacionRepresentanteLegalPorIdentificadores(representanteLegal);
					}
					representanteLegal.setCveIdTipoPoder(
						rl.getTipoPoder()!=null ? rl.getTipoPoder().getIdTipoPoder().longValue() : 3L);
					
					session.setAttribute(KEY_RL_SESSION, representanteLegal);
				}catch (RepresentanteLegalYaExisteException e) {
					resultado.setErrorFormGeneral("Ya existe la relación con el Representante Legal");
					return resultado;
				}
				return (Fisica)rlEncontrado;
			}
		}else{
			resultado.setErrorFormGeneral("No se localizo a la persona representada.");
		}
		return resultado;
	}
	
	private void procesarRepresenantesLegales(Model model,HttpServletRequest request, HttpSession session,
			Long idSolicitud, Integer idTipoTramite){
		//List<RepresentanteLegal> listaRL = new ArrayList<RepresentanteLegal>();
		Object tramiteInicial = null;
		InstanceofPredicate tramitePredicate = null;
		Long idPersonaFM = null;	//CveMoral o CveFisica del Representado
		Long idPersona 	 = null;	//IdPersona del Representado
		Long idTipoPersona =  TipoPersona.TIPO_PERSONA_MORAL;
		Long idPersonaRL = null;	//IdPersona del Representante Legal
		Boolean rlRequerido = false;			
		Persona persona = null;		//RL que se empleara en el dilogo de Agregar RL
		
		//Obtener solicitud
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
		if(solicitud!=null){
			//TODO:	Acorde al tramite, obtener:
			//idPersonaFM 		Id de la persona fisica o moral
			//idPersonaRL		Id del representante legal seleccionado (si aplica).
			//idTipoPersona		Por default es Persona Moral.
			//rlRequerido		por default el indicador es FALSO. Indica que los REPRESENTANTE es requerido.
			
			TipoTramiteEnum eTipoTramite = TipoTramiteEnum.obternerEnumById(idTipoTramite);
			if(eTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO) || 
					eTipoTramite.equals(TipoTramiteEnum.BAJA_SOCIO)){
				tramitePredicate = new InstanceofPredicate(TramiteSocios.class);
				tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
				TramiteSocios tramiteHelper = (TramiteSocios) tramiteInicial;
				idPersonaFM = tramiteHelper.getPatron().getIdPersona(); //CveMoral o IdPersona para PM
				idPersona = idPersonaFM;
				idPersonaRL = tramiteHelper.getIdPersonaRL();
				rlRequerido = true;
				persona 	= tramiteHelper.getPatron();
				
			}else if (eTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL)){
				tramitePredicate = new InstanceofPredicate(TramiteRepresentanteLegal.class);
				tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
				TramiteRepresentanteLegal tramiteHelper = (TramiteRepresentanteLegal) tramiteInicial;
				idPersonaFM =  getCveFisica(tramiteHelper.getFisica()); //Obtener cveFisica
				idPersona	= tramiteHelper.getFisica().getIdPersona();
				idPersonaRL = tramiteHelper.getIdPersonaRL();
				idTipoPersona =  TipoPersona.TIPO_PERSONA_FISICA;
				persona 	= tramiteHelper.getFisica();
								
			}else if (eTipoTramite.equals(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL)	||
					eTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES) ||
					eTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
				tramitePredicate = new InstanceofPredicate(TramiteFisica.class);
				tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
				if(tramiteInicial!=null){					
					TramiteFisica tramiteHelper = (TramiteFisica) tramiteInicial;
					idPersonaFM = getCveFisica(tramiteHelper.getFisica()); //Obtener cveFisica
					idPersona	= tramiteHelper.getFisica().getIdPersona();
					idPersonaRL = tramiteHelper.getIdPersonaRL();
					idTipoPersona =  TipoPersona.TIPO_PERSONA_FISICA;
					persona 	= tramiteHelper.getFisica();
					
				}else{
					tramitePredicate = new InstanceofPredicate(TramiteMoral.class);
					tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramitePredicate);
					if(tramiteInicial!=null){						
						TramiteMoral tramiteHelper = (TramiteMoral) tramiteInicial;
						idPersonaFM = tramiteHelper.getMoral().getIdPersona(); //CveMoral o IdPersona para PM
						idPersona   = idPersonaFM;
						idPersonaRL = tramiteHelper.getIdPersonaRL();
						rlRequerido = true;
						persona 	= tramiteHelper.getMoral();
					}
				}				
			}
			
			idPersonaFM = idPersonaFM != null ? idPersonaFM : 0L;
			idPersona = idPersona != null ? idPersona : 0L;
			idPersonaRL = idPersonaRL != null ? idPersonaRL : 0L;
			
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
		}
		
		persona.setIdPersona(idPersona);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(idTipoPersona);
		if(persona instanceof Fisica){
			((Fisica)persona).setCveFisica(idPersonaFM);			
		}	
		session.setAttribute(KEY_REPRESENTADO_SESSION, persona);
		
		
		request.setAttribute(KEY_RL_REQUERIDO, rlRequerido);
		//model.addAttribute(KEY_LISTA_RLS, listaRL);
		
	}
	
	private Long getCveFisica(Fisica fisica){
		Long cveFisica = null;
		if(fisica!=null){
			try {
				if (fisica.getCveFisica() != null){
					cveFisica = fisica.getCveFisica();
				}else{
					cveFisica = personaFisicaServiceBusiness.
						obtenerIDPersonaFisica(fisica.getIdPersona());
				}
			} catch (AbstractException ae) {
				this.log.error(ae);
				cveFisica = null;
			}
		}		
		return cveFisica;
	}
	
	public Usuario getUsuarioSession(HttpSession session){
		Usuario usuario = new Usuario();
		UsuarioSSO sso = (UsuarioSSO)session.getAttribute(KEY_USUARIO_SSO);
		if(sso!=null && sso.getNombre()!=null){
			if(sso.getNombre()!=null)
				usuario.setUsuario(sso.getNombre().toUpperCase());
			
			if(sso.getSubdelegacion()!=null){
				usuario.setUsuarioFuncionario(new UsuarioFuncionario());
				usuario.getUsuarioFuncionario().setSubdelegacion(new Subdelegacion());				
				usuario.getUsuarioFuncionario()
					.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			}
		}		
		return usuario;	
	}
	
}
