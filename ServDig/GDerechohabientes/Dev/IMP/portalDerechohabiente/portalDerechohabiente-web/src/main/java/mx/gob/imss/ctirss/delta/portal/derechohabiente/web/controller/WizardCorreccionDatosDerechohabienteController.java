package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

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

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitePersonaFisicaServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PasoRegistroEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller.validator.TramiteBajaValidator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping( value = "/wizard/correccion/")
public class WizardCorreccionDatosDerechohabienteController extends AbstractController {

	@Autowired
	private CorreccionDerechohabienteServiceRemote correccionDerechohabienteServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	@Autowired
	private DerechohabienteServiceRemote derechohabienteServiceRemote;
	@Autowired
	private TramitePersonaFisicaServiceRemote tramitePersonaFisicaService;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@Autowired
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusinessRemote;
	
	//Variables de la session
	private final String KEY_INTEGRANTE_CORRECCION = "integranteCorreccionSession";
	private final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private final String KEY_ASIGNACION_NSS = "asignacionNssSession";
	private final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_REQUIERE_DOCS = "requiereDocs";
	private static final String KEY_SOLICITUD = "solicitudCorreccionDerechohabiente";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";

	//vistas
	private static final String VIEW_INICIAL = "wizardCorreccionDerechohabienteInit";
	private static final String VIEW_CONTENIDO = "wizardCorreccionDerechohabienteContent";

	@RequestMapping( value = "/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {
		
		session.removeAttribute(KEY_INTEGRANTE_CORRECCION);
		session.removeAttribute(KEY_ASIGNACION_NSS);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_REQUIERE_DOCS);
		
		return null;
	}
	
	/**
	 * Metodo para realizar las validaciones necesarias para hacer la modificacion de datos del derechohabiente
	 * @param idPersona
	 * @param curp
	 * @param model
	 * @return
	 */
	private Boolean validacionesCorreccion(Long idPersona, String curp, Model model) {
		
		Boolean correcto = true;
		
		try {
			Fisica fisica = personaFisicaServiceBusinessRemote.getPersonaEnRenapo(curp);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			model.addAttribute("error", e.getMessage());
			correcto = false;
		} catch (ClienteWebserviceRenapoCurpException e) {
			model.addAttribute("error", e.getMessage());
			correcto = false;
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			model.addAttribute("error", e.getMessage());
			correcto = false;
		}
		
		
		if(correcto) {
			Map<String, Object> result = null;
			Boolean isAPRL = false;
			
			try {
				result = grupoFamiliarService.esAseguradoPatronORLConDescripcion(idPersona);
				isAPRL = (Boolean) result.get("isAPRL");
				
				if(isAPRL) {
					model.addAttribute("error", "El candidato seleccionado se encuentra registrado como " + result.get("rol") + 
							", por lo tanto no es posible realizar modificaciones a sus datos personales.");
				}
			} catch(Exception e) {
				log.error("Ocurrio un error al verificar si la persona es asegurado, patron o RL", e);
			}
		}
		
		return correcto;
	}
	
	@RequestMapping( value = "/tramite/{idAsignacionNss}/{nss}/{tipoTramite}/{idIntegrante}/{curpFromListOfCandidates}")
	public String initWizardCorreccionDatosDerechohabiente(Model model, HttpSession session, HttpServletRequest request, 
			@PathVariable Long idAsignacionNss, @PathVariable String nss, @PathVariable Long tipoTramite, @PathVariable Long idIntegrante, @PathVariable String curpFromListOfCandidates) {
		log.info("---------------------------------------------------------------------------------------------------------------");
		log.info("         WizardCorreccionDatosDerechohabienteController:initWizardCorreccionDatosDerechohabiente               ");
		log.info("---------------------------------------------------------------------------------------------------------------");
		log.info(">>> curpFromListOfCandidates: " + curpFromListOfCandidates);
		Solicitud solicitudActiva = new Solicitud();
		GrupoFamiliar integranteCorreccion = null;
		Boolean solicitudCreada = false;
		Boolean otroTipoTramite = false;
		Boolean requiereDocumentos = false;
		
		
		
		if(!validacionesCorreccion(idIntegrante, curpFromListOfCandidates, model)) {
			model.addAttribute("solicitudForm", solicitudActiva);
			return VIEW_INICIAL;
		}
		
		try {
			requiereDocumentos = documentoProbatorioServiceBusinessRemote.requiereDocumentos(tipoTramite);
		} catch (Exception e) {
			log.error("Ocurrio un error al consultar si el tramite requiere documentos",e);
		}
				
		try {
			integranteCorreccion = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(idAsignacionNss, idIntegrante);
			
			if(integranteCorreccion != null) {
				session.setAttribute(KEY_ASIGNACION_NSS, integranteCorreccion.getAsignacionNSS());
				model.addAttribute("derechohabiente", this.convetirGrupoCorreccion(integranteCorreccion));
			}
			
		}catch(DerechohabientesBusinessException e) {
			log.error("Error al obtener al derechohabiemnte", e);
			model.addAttribute("error", "No fue posible obtener los datos del integrante del grupo familiar");
		}
		
		List<Solicitud> solicitudes = null;
		try {
			solicitudes = solicitudBusinessRemote.getSolicitudeActivasGrupoFamiliarEIntegrante(nss,idIntegrante,1L, null);
			
			
			
			if(solicitudes != null && !solicitudes.isEmpty()) {
				
				solicitudActiva = solicitudes.get(0);
				
				if(solicitudActiva.getTramites().get(0).getTipoTramite().getIdTipoTramite().equals(tipoTramite.intValue())) {
					solicitudCreada = true;
					session.setAttribute(KEY_SOLICITUD, solicitudActiva);
				} else {
					
					otroTipoTramite = true;
					
					
				}
			}
			
		}catch(Exception e) {
			log.error("Ocurrio un error al consultar las solicitudes", e);
			model.addAttribute("error", "No fue posible consultar si el integrante del grupo familiar cuenta con solicitudes registradas");
		}
				
		session.setAttribute(KEY_TIPO_SOLICITUD, tipoTramite);
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, this.getTipoTramiteDesc(tipoTramite));
		session.setAttribute(KEY_REQUIERE_DOCS, requiereDocumentos);
		List<Long> idtiposTramite = new ArrayList<Long>();
		idtiposTramite.add(tipoTramite);
		session.setAttribute(KEY_INTEGRANTE_CORRECCION, integranteCorreccion);
		session.setAttribute(KEY_TIPO_TRAMITE, idtiposTramite);
		model.addAttribute("solicitudCreada", solicitudCreada);
		model.addAttribute("otroTipoTramite", otroTipoTramite);
		model.addAttribute("curpFromController", curpFromListOfCandidates);
		
		
		if(otroTipoTramite || solicitudCreada) {
			model.addAttribute("tipoTramiteCreado", solicitudActiva.getTramites().get(0).getTipoTramite());
		}
		model.addAttribute("solicitudForm", solicitudActiva);
		model.addAttribute("datosEntrada", new TramiteCorreccionDerechohabiente());  // modelAttribute para el form asignado al inicio de la solicitud.
				
		return VIEW_INICIAL;
	}
	
	@RequestMapping( value = "/iniciarTramite")
	public String iniciarTramiteCorreccion(Model model, HttpSession session, HttpServletRequest request) {
//		@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente derechohabiente
		log.info("---------------------------------------------------------------------------------------------------------------");
		log.info("         WizardCorreccionDatosDerechohabienteController:iniciarTramiteCorreccion                                 ");
		log.info("---------------------------------------------------------------------------------------------------------------");
		
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_CORRECCION);
		Long idTipoTramite = (Long) session.getAttribute(KEY_TIPO_SOLICITUD);
		Usuario usuario = this.getUsuarioSesion(this.procesarUsuarioSSO(request));
		Solicitud solicitud = null;

		try {
			/**
			 * Inicia tramite
			 */
			if(integrante != null) {
				/**
				 * funcionalidad para indicar variables en el modelo para los datos del derechohabiente
				 */
				Boolean existe = null;
				TramiteCorreccionDerechohabiente correccion = null;
				
				if(idTipoTramite.equals(TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE.getCodigo().longValue())) {
					
					try {
						correccion = this.convetirGrupoCorreccion(integrante);
						correccion.setFisica(integrante.getDerechohabiente().getPersona());
						correccion.setPersona(integrante.getDerechohabiente().getPersona());
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					solicitud = correccionDerechohabienteServiceRemote.saveCorreccionDatosDerechohabiente(
							integrante.getDerechohabiente().getIdPersona(),null,
							usuario,
							integrante.getAsignacionNSS(),
							//tramiteCorreccionDerechohabiente, 
							correccion,
							OrigenSolicitudEnum.INTERNET,
							false
						);
				}			
								
				if(solicitud != null) {
					
					// indicamos id del tramite y tipo de tramite
					correccion.setTramiteId(solicitud.getTramites().get(0).getTramiteId());
					correccion.setTipoTramite(solicitud.getTramites().get(0).getTipoTramite());
					
					session.setAttribute(KEY_SOLICITUD, solicitud);
					model.addAttribute("tramite", solicitud.getTramites().get(0));
					// Datos del acuse
					obtenerDatosAcuse(solicitud, integrante.getAsignacionNSS(), session);

					// Datos para la firma digital
					generarCadenaOriginal(solicitud, integrante.getAsignacionNSS(), session, integrante.getAsignacionNSS().getNssStr());
					
					TramiteCorreccionDerechohabiente datosActuales = this.convetirGrupoCorreccion(integrante);
					model.addAttribute("datosActuales", datosActuales);
					model.addAttribute("derechohabiente", correccion);
					model.addAttribute("hijo", integrante);
					model.addAttribute("validacion", 0);
					model.addAttribute("asegurado",existe);
				}
			}
		} catch (DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al consultar al derehcohabiente", e);
		}
		
		model.addAttribute("integrante", integrante);
		model.addAttribute("solicitud", solicitud);
		model.addAttribute("isRetomar", false);	
		
		return VIEW_CONTENIDO;
	}
	
	private String getTipoTramiteDesc(Long idTramite) {
		String descripcionTipoTramite = "";
		
		try{
			if(idTramite.equals(TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE.getCodigo().longValue())) {
				descripcionTipoTramite = this.tramitePersonaFisicaService.getDescripcionTipoTramiteFromDic(idTramite);
			}
		} catch(Exception e){
			log.error(">>> Error al consultar datos del tipo de tramite por id de tramite.", e);
			descripcionTipoTramite = this.getTipoTramite(idTramite);
		}
		return descripcionTipoTramite;
	}

	@RequestMapping(value="/retomar")
	public String retomarSolicitud(Model model,@ModelAttribute(value="solicitudForm") Solicitud solicitud, HttpSession session, HttpServletRequest request) {
		log.info("---------------------------------------------------------------------------------------------------------------");
		log.info("         solicitud               "+solicitud);
		log.info("         solicitud ID               "+solicitud.getSolicitudId());
		log.info("---------------------------------------------------------------------------------------------------------------");
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_CORRECCION);
		
		model.addAttribute("solicitudCreada", true);
		model.addAttribute("isRetomar", true);
		model.addAttribute("integrante", integrante);
		try {

			//Se consulta la solicitud
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			
			// Datos del acuse
			obtenerDatosAcuse(solicitud, integrante.getDerechohabiente(), session);

			// Datos para la firma digital
			generarCadenaOriginal(solicitud, integrante.getAsignacionNSS(), session, integrante.getAsignacionNSS().getNssStr());
			
			//Se verifica que no sea nula 
			if(solicitud != null) {
				log.info("---------------------------------------------------------------------------------------------------------------");
				log.info("         solicitud               "+solicitud);
				log.info("         solicitud ID               "+solicitud.getSolicitudId());
				log.info("---------------------------------------------------------------------------------------------------------------");
				session.setAttribute(KEY_SOLICITUD, solicitud);
				log.info("---------------------------------------------------------------------------------------------------------------");
				log.info("         solicitud en sesion              "+(Solicitud) session.getAttribute(KEY_SOLICITUD));
				log.info("---------------------------------------------------------------------------------------------------------------");
				model.addAttribute("solicitud", solicitud);
				Tramite tramite = solicitud.getTramites().get(0);
				model.addAttribute("tramite", tramite);
				
				/**
				 * funcionalidad para indicar variables en el modelo para los datos del derechohabiente
				 */
				GrupoFamiliar derechohabiente = null;
				Boolean existe = null;
				
				try {
					existe = grupoFamiliarService.esAseguradoOPatronORepresentanteLegal(integrante.getAsignacionNSS().getIdPersona());
					derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(integrante.getAsignacionNSS().getIdAsignacionNSS(), integrante.getDerechohabiente().getIdPersona());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//TramiteCorreccionDerechohabiente correccion = this.convetirGrupoCorreccion(derechohabiente);
				TramiteCorreccionDerechohabiente datosActuales = this.convetirGrupoCorreccion(derechohabiente);
				model.addAttribute("datosActuales", datosActuales);
				model.addAttribute("derechohabiente", tramite);
				model.addAttribute("hijo", derechohabiente);
				model.addAttribute("validacion", 0);
				model.addAttribute("asegurado",existe);
				
			} else {
				request.setAttribute("error", "No fue posible recuperar la solicitud");
			}
		} catch(Exception e) {
			solicitud = new Solicitud();
			request.setAttribute("error", "No fue posible recuperar la solicitud");
		}
		
		model.addAttribute("solicitud", solicitud);
		return VIEW_CONTENIDO;
	}

	@RequestMapping(value = "/solicitud/cancelar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudCorreccion(@RequestBody Solicitud solicitud,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) { 
		
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudA = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		
		try {
			
			log.debug(">>> Cancelando la solicitud " + solicitud + " ...");
			
			if(solicitud.getSolicitudId().equals(solicitudA.getSolicitudId())) {
				solicitudA.setEstadoSolicitud(new EstadoSolicitud());
				solicitudA.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
				
				//y colocamos la razon del rechazo
				for(Tramite tramite : solicitudA.getTramites())
				{										
					tramite.setResultado(false);
					tramite.setRazonResultado(new RazonResultado());
					tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.SOLICITUD_CANCELADA.getCodigo().longValue());
					tramite.setEstadoTramite(new EstadoTramite());
					tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());				
					tramite.setObservacion("Solicitud cancelada a peticion del derechohabiente");
				}
			
				solicitudA.setFechaActualizacion(new Date());
				solicitudA.setObservacion("Solicitud cancelada a peticion del derechohabiente");
				
				solicitud = solicitudBusinessRemote.actualizarEstados(solicitudA);
				result.put("mensaje", "La solicitud fue cancelada correctamente");
			} else {
				result.put("mensaje", "El id de la solicitud a cancelar no coincide con la que se tiene en session");
			}
			
			result.put("solicitud", solicitud);
			
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		}
		
		return result;
		/*log.info("---------------------------------------------------------------------------------------------------------------");
		log.info("         solicitud               "+solicitud);
		log.info("         solicitud ID               "+solicitud.getSolicitudId());
		log.info("---------------------------------------------------------------------------------------------------------------");		
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudA = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		try {
			log.info("---------------------------------------------------------------------------------------------------------------");
			log.info("         solicitud en sesion              "+(Solicitud) session.getAttribute(KEY_SOLICITUD));
			log.info("         solicitud               "+solicitudA);
			log.info("---------------------------------------------------------------------------------------------------------------");
			if(solicitud.getSolicitudId().equals(solicitudA.getSolicitudId())) {
				solicitudA.setEstadoSolicitud(new EstadoSolicitud());
				solicitudA.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
				
				//y colocamos la razon del rechazo
				for(Tramite tramite : solicitud.getTramites())
				{										
					tramite.setResultado(false);
					tramite.setRazonResultado(new RazonResultado());
					tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.SOLICITUD_CANCELADA.getCodigo().longValue());
					tramite.setEstadoTramite(new EstadoTramite());
					tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());				
					tramite.setObservacion("Solicitud cancelada a peticion del derechohabiente");
				}
			
				solicitud.setFechaActualizacion(new Date());
				solicitud.setObservacion("Solicitud cancelada a peticion del derechohabiente");
				
				
				log.info("---------------------------------------------------------------------------------------------------------------");
				log.info("         solicitud               "+solicitud);
				log.info("         solicitud ID               "+solicitud.getSolicitudId());
				log.info("---------------------------------------------------------------------------------------------------------------");		
				solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
				result.put("mensaje", "La solicitud fue cancelada correctamente");
			} else {
				result.put("mensaje", "El id de la solicitud a cancelar no coincide con la que se tiene en session");
			}
			
			result.put("solicitud", solicitud);
			
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		}		
		
		return result;*/
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
		datosAcuse.setNombreCompleto(sbnombre.toString());

		// CURP
		if (persona instanceof Fisica) {
			datosAcuse.setCurp(((Fisica)persona).getCurp());
		}

		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosAcuse);
	}

	private void generarCadenaOriginal(Solicitud solicitud, Persona persona, HttpSession session, String nss) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();

		String tipoSolicitud = (String) session.getAttribute(KEY_DESC_TIPO_SOLICITUD);
		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");

		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(tipoSolicitud).append("|");

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(Calendar.getInstance().getTime());

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
			// CURP
			contenidoAFirmar.append("CURP:");
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		} else {
			contenidoAFirmar.append("|");
		}

		// NSS(No aplica)
		contenidoAFirmar.append("Numero de Seguridad Social:"+nss+"||");

		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
	}
	
	public Usuario getUsuarioSesion(UsuarioSSO usuariosso) {
		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());

		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(usuariosso.getNombre());
		usuario.setPerfilUsuario(pu);

		// Se crean los objetos necesarios para ligar el usuario con la
		// subdelegacion y delegacion.
		
		if(usuariosso.getDelegacion() != null  && usuariosso.getSubdelegacion() != null){
			
			// LUDS Se agrego esta validacion para que si es en caso de un usuario EXTERNO no le llega la delegacion.
			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(usuariosso.getDelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.setUsuario(usuario);
			uf.getSubdelegacion().setId(usuariosso.getSubdelegacion().longValue());
			usuario.setUsuarioFuncionario(uf);
		}
		
		
		usuario.setCveIdUsuario(usuariosso.getCurp());
		
		return usuario;
	}

	private String getTipoTramite(Long tipoTramite) {
		String descTipoTramite = "";
		if(tipoTramite.equals(TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE.getCodigo().longValue())) {
			//descTipoTramite= "CORRECCI&Oacute;N DE DATOS DEL DERECHOHABIENTE";
			descTipoTramite= TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE.name();
		}
		
		return descTipoTramite;
	}
	
	@RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody TramiteCorreccionDerechohabiente oForm, final HttpServletResponse response) {
        log.trace(">>> Entramos a WizardCorreccionDatosDerechohabienteController#validarFormulario para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        
        final Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");
        
        result.put("oForm", oForm);

        return result;
    }
	
	@RequestMapping(value = "/solicitud/finalizar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitudCorreccionDatos(@RequestBody TramiteCorreccionDerechohabiente tramite,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) { 
		
		log.debug(">>> Entramos a WizardCorreccionDatosDerechohabienteController#finalizarSolicitudCorreccionDatos.");
		
		FirmaElectronica firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_CORRECCION);
		AsignacionNSS nss = (AsignacionNSS) session.getAttribute(KEY_ASIGNACION_NSS);
		Usuario usuario = this.getUsuarioSesion(this.procesarUsuarioSSO(request));
		
		TramiteCorreccionDerechohabiente tramiteCorreccionDerechohabiente = (TramiteCorreccionDerechohabiente) solicitud.getTramites().get(0);
		tramiteCorreccionDerechohabiente.setPersona(integrante.getDerechohabiente());
//		tramiteCorreccionDerechohabiente.setFechaDefuncion(tramite.getFechaDefuncion());
		tramiteCorreccionDerechohabiente.setObservaciones(tramite.getObservaciones());
		
		/*tramiteXml.setPersona(integrante.getDerechohabiente());
		tramiteXml.setFisica(tramite.getFisica());
		
		tramiteXml.setCurpCap(tramite.getCurpCap());
		tramiteXml.setNombre(tramite.getNombre());
		tramiteXml.setPrimerApellido(tramite.getPrimerApellido());
		tramiteXml.setSegundoApellido(tramite.getSegundoApellido());
		tramiteXml.setFechaNacimiento(tramite.getFechaNacimiento());
		tramiteXml.setFechaNacimientoStr(tramite.getFechaNacimientoStr());
		tramiteXml.setLugarNacimiento(tramite.getLugarNacimiento());
		tramiteXml.setEstadoCivil(tramite.getEstadoCivil());
		tramiteXml.setParentesco(tramite.getParentesco());
		tramiteXml.setSexo(tramite.getSexo());
		tramiteXml.setCorreoElectronico(tramite.getCorreoElectronico());
		tramiteXml.setTelefonoFijo(tramite.getTelefonoFijo());
		tramiteXml.setTelefonoMovil(tramite.getTelefonoMovil());
		tramiteXml.setFacebook(tramite.getFacebook());
		tramiteXml.setTwitter(tramite.getTwitter());
		tramiteXml.setDomicilio(tramite.getDomicilio());
		tramiteXml.setObservaciones(tramite.getObservaciones());*/
		
		
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramiteCorreccionDerechohabiente);
		
		try {
			log.debug("Firma Electronica" + firma);
			log.debug("secuencia: " + firma.getSecuenciaNotaria());
			
			//Actualizamos la solicitud con los nuevos patrones seleccionados
			solicitudBusinessRemote.actualizarTramites(solicitud);
			solicitud.setFirmaElectronica(firma);
			solicitud = correccionDerechohabienteServiceRemote.finalizarSolicitudCorreccionDatos(solicitud);
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
			result.put("error", false);
			result.put("mensaje", "Su solicitud ha finalizado correctamente");
			log.info(">>> Su solicitud ha finalizado correctamente");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error("error solicitud",e);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (TramiteNoEncontradoException e) {
			this.log.error("error tramite",e);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (Exception e) {
			this.log.error("error desconocido",e);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		}
		
		
		
		return result;
	}
	
	/**
	 * Metodo para pasar los datos de un objeto de tipo GrupoFamiliar aun CorreccionDatoDerechohabiente
	 * @param integrante
	 * @return
	 */
	private TramiteCorreccionDerechohabiente convetirGrupoCorreccion(GrupoFamiliar integrante) {
		TramiteCorreccionDerechohabiente correccion = new TramiteCorreccionDerechohabiente();
		
		correccion.setIdPersona(integrante.getDerechohabiente().getIdPersona());
		correccion.setNombre(integrante.getDerechohabiente().getNombre());
		correccion.setPrimerApellido(integrante.getDerechohabiente().getPrimerApellido());
		correccion.setSegundoApellido(integrante.getDerechohabiente().getSegundoApellido());
		correccion.setCurpCap(integrante.getDerechohabiente().getCurp());
		if(integrante.getDerechohabiente().getSexo().getIdSexo().equals(SexoEnum.MUJER.getId()) && integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())){
			correccion.setSexo(new Sexo());
			correccion.getSexo().setIdSexo(Integer.parseInt(""+ParentescoEnum.MADRE.getId()));
		} else {
			correccion.setSexo(integrante.getDerechohabiente().getSexo());
		}
		correccion.setFechaNacimiento(integrante.getDerechohabiente().getFechaNacimiento());
		correccion.setLugarNacimiento(integrante.getDerechohabiente().getLugarNacimiento());
		correccion.setParentesco(integrante.getParentesco());
		correccion.setCalidad(integrante.getCalidad().toString());
		correccion.setNss(integrante.getAsignacionNSS().getNssStr());
		correccion.setDomicilio(integrante.getDomicilio());
		correccion.setMedicoEnTurno(integrante.getMedicoEnTurno());
		correccion.setEstadoCivil(integrante.getDerechohabiente().getEstadoCivil());
		correccion.setCorreoElectronico(integrante.getDerechohabiente().getCorreoElectronico());
		correccion.setFacebook(integrante.getDerechohabiente().getFacebook());
		correccion.setTwitter(integrante.getDerechohabiente().getTwitter());
		correccion.setTelefonoFijo(integrante.getDerechohabiente().getTelefonoFijo());
		correccion.setTelefonoMovil(integrante.getDerechohabiente().getTelefonoMovil());
		correccion.setMesRegistroNac(integrante.getDerechohabiente().getMesRegistroNac());
		correccion.setAnioRegistroNac(integrante.getDerechohabiente().getAnioRegistroNac());
		
		if(integrante.getMedicoEnTurno() != null && integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
			correccion.setIdUmfOrigen(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
		}
		
		return correccion;
	}
	
	
	/**
	 * Guarda los cambios realizados en el tramite de correccion de datos
	 * @param tramite
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> guardarSolicitudCorreccionDatos(@RequestBody TramiteCorreccionDerechohabiente tramite, HttpSession session, HttpServletRequest request) {
	//public @ResponseBody Map<String, ? extends Object> guardarSolicitudCorreccionDatos(@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente tramite, HttpSession session, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_CORRECCION);
		
		TramiteCorreccionDerechohabiente tramiteXml = (TramiteCorreccionDerechohabiente) solicitud.getTramites().get(0);
		
		tramiteXml.setPersona(integrante.getDerechohabiente());
		tramiteXml.setFisica(tramite.getFisica());
		
		tramiteXml.setCurpCap(tramite.getCurpCap());
		tramiteXml.setNombre(tramite.getNombre());
		tramiteXml.setPrimerApellido(tramite.getPrimerApellido());
		tramiteXml.setSegundoApellido(tramite.getSegundoApellido());
		tramiteXml.setFechaNacimiento(tramite.getFechaNacimiento());
		tramiteXml.setFechaNacimientoStr(tramite.getFechaNacimientoStr());
		tramiteXml.setLugarNacimiento(tramite.getLugarNacimiento());
		tramiteXml.setEstadoCivil(tramite.getEstadoCivil());
		tramiteXml.setParentesco(tramite.getParentesco());
		tramiteXml.setSexo(tramite.getSexo());
		tramiteXml.setCorreoElectronico(tramite.getCorreoElectronico());
		tramiteXml.setTelefonoFijo(tramite.getTelefonoFijo());
		tramiteXml.setTelefonoMovil(tramite.getTelefonoMovil());
		tramiteXml.setFacebook(tramite.getFacebook());
		tramiteXml.setTwitter(tramite.getTwitter());
		tramiteXml.setDomicilio(tramite.getDomicilio());
		tramiteXml.setObservaciones(tramite.getObservaciones());
		
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramiteXml);
		
		try {
			//Actualizamos la solicitud con los nuevos patrones seleccionados
			Solicitud solGuardada = solicitudBusinessRemote.actualizarTramites(solicitud);
			
			session.removeAttribute(KEY_SOLICITUD);
			session.setAttribute(KEY_SOLICITUD, solGuardada);
			
			result.put("mensaje", "Se han guardado correctamente los cambios");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error("error solicitud",e);
			result.put("error", "Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (TramiteNoEncontradoException e) {
			this.log.error("error tramite",e);
			result.put("error", "Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (Exception e) {
			this.log.error("error desconocido",e);
			result.put("error", "Ocurri&oacute; un error al intentar guardar los cambios");
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}
	
}

