/*
 * Esta clase es el controller encargada de administrar las peticiones de los tramites de prorrogas
 * La funcionalidad que cubre este Controller es:
 * 1.Obtener informacion del derechohabiente
 * 2.Iniciar tramite
 * 3.Retomar
 * 4.Guardar
 * 5. Finalizar
 * 6. Cancelar
 */
package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ProrrogaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Caracter;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.ProrrogasDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.CaracterEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoSituacionCritica;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Obstetrico;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller.validator.ProrrogaValidator;

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
@RequestMapping(value = "/wizard/prorroga/")
public class WizardProrrogaDerechohabienteController extends AbstractController {

	@Autowired
	private ProrrogaServiceRemote prorrogaService;
	@Autowired
	private DerechohabienteServiceRemote derechohabienteServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;

	// Variables de la session
	private final String KEY_INTEGRANTE_PRORROGA = "integranteProrrogaSession";
	private final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private final String KEY_ASIGNACION_NSS = "asignacionNssSession";
	private final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_REQUIERE_DOCS = "requiereDocs";
	private static final String KEY_SOLICITUD = "solicitudProrrogaDerechohabiente";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_ESTADO_REQUISITOS = "correcto";
	private static final String KEY_MENSAJE_REQUISITOS = "mensaje";	

	// vistas
	private static final String VIEW_INICIAL = "wizardProrrogaDerechohabienteInit";
	private static final String VIEW_INICIAL_CIUDADANO = "wizardProrrogaDerechohabienteCiudadanoInit";
	private static final String VIEW_CONTENIDO = "wizardProrrogaDerechohabienteContent";

	private static final Locale LOCALE_MX = new Locale("es", "mx");;

	/**
	 * Este metodo sirve para obtener la informacion del derechohabiente
	 * @param model
	 * @param session
	 * @param request
	 * @param idAsignacionNss
	 * @param nss
	 * @param tipoProrroga
	 * @param idIntegrante
	 * @return
	 */
	@RequestMapping(value = "/tramite/{idAsignacionNss}/{nss}/{tipoProrroga}/{idIntegrante}")
	public String initWizardTipoProrrogaDerechohabiente(Model model,
			HttpSession session, HttpServletRequest request,
			@PathVariable Long idAsignacionNss, @PathVariable String nss,
			@PathVariable Long tipoProrroga, @PathVariable Long idIntegrante) {

		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudActiva = new Solicitud();
		Boolean mismoOrigen = true;
		Boolean solicitudCreada = false;
		Boolean otroTipoTramite = false;

		GrupoFamiliar integrante = null;

		ProrrogasDto prorrogaDto = new ProrrogasDto();
		prorrogaDto.setProrroga(new TramiteProrroga());
		
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		
		if(idOrigenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())) {
			TramiteProrroga prorroga = prorrogaDto.getProrroga();
			prorroga.setPersona(new Fisica());
			prorroga.getPersona().setCurp("");
			prorroga.setIdAsignacionNSS(idAsignacionNss);
			prorroga.setTipoTramite(new TipoTramite());
			prorroga.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo());
			model.addAttribute("datos", prorroga);
			return VIEW_INICIAL_CIUDADANO;
		}		

		try {
			integrante = derechohabienteServiceRemote
					.detalleDerechohabienteGrupoFamiliar(idAsignacionNss,
							idIntegrante);
			if (integrante != null) {
				session.setAttribute(KEY_ASIGNACION_NSS,
						integrante.getAsignacionNSS());
			}
		} catch (DerechohabientesBusinessException e) {
			log.error("Error al obtener al derechohabiemnte", e);
			model.addAttribute("error", "No fue posible obtener los datos del integrante del grupo familiar");
			e.printStackTrace();
		}
		
		this.requiereDocumentos(session, tipoProrroga);
		
		if(tipoProrroga.equals(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo().longValue())) {
            try {
                Map<String, Object> resp = prorrogaService.validateProrrogaEstudios(integrante, idAsignacionNss, this.getUsuarioSesion(this.procesarUsuarioSSO(request)));
                if(!(Boolean)resp.get("valido")){
                    model.addAttribute("solicitudForm", new Solicitud());
                    model.addAttribute("error",resp.get("mensaje").toString());
                    return VIEW_INICIAL;
                }
            } catch(DerechohabientesBusinessException e) {
                log.error("Ocurrio un error al intentar validar el integrante", e);
                model.addAttribute("solicitudForm", new Solicitud());
                model.addAttribute("error",e.getMessage());
            } catch(Exception e) {
                log.error("Ocurrio un error no esperado al validar el integrante: " + e.getMessage(), e);
                model.addAttribute("solicitudForm", new Solicitud());
                model.addAttribute("error",e.getMessage());
            }

//            if(integrante.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().equals(SubestadoDerechohabienteEnum.FALLECIMIENTO.getId())){
//                model.addAttribute("solicitudForm", new Solicitud());
//                model.addAttribute("error","No es posible aplicar la prorroga ya que el candidato tiene baja por fallecimiento.");
//                return VIEW_INICIAL;
//            }
		}

		result = this.validacionesSolicitudAbierta(nss, idIntegrante, tipoProrroga,idOrigenSolicitud, session);
		
		if(result != null) {
			solicitudActiva = (Solicitud) result.get("solicitud");
			solicitudCreada = (Boolean) result.get("solicitudCreada");
			mismoOrigen = (Boolean) result.get("mismoOrigen");
			otroTipoTramite = (Boolean) result.get("otroTipoTramite");
			
			session.setAttribute(KEY_SOLICITUD, solicitudActiva);
		}
		
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD,this.getTipoTramite(tipoProrroga));
		
		this.setTipoTramiteSession(session, tipoProrroga);
		session.setAttribute(KEY_INTEGRANTE_PRORROGA, integrante);
		
		model.addAttribute("solicitudCreada", solicitudCreada);
		model.addAttribute("otroTipoTramite", otroTipoTramite);
		model.addAttribute("mismoOrigen", mismoOrigen);
		model.addAttribute("solicitudForm", solicitudActiva != null ? solicitudActiva : new Solicitud());

		if(otroTipoTramite || solicitudCreada) {
			model.addAttribute("tipoTramiteCreado", solicitudActiva.getTramites().get(0).getTipoTramite());
		}

		model.addAttribute("datos", prorrogaDto);

		return VIEW_INICIAL;
	}
	
	private Map<String, Object> validacionesSolicitudAbierta(String nss, Long idPersona, Long idTipoTramiteARealizar, Long idOrigenSoliciutd, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean correcto = false;
		String mensaje = "Todos los requisitos son correctos";
		
		Boolean solicitudCreada = false;
		Boolean mismoOrigen = true;
		Boolean otroTipoTramite = false;
		
		List<Solicitud> solicitudes = null;
		Solicitud solicitudActiva = null;
		
		try {
			solicitudes = solicitudBusinessRemote.getSolicitudeActivasGrupoFamiliarEIntegrante(nss,idPersona,1L, null);
			
			if(solicitudes != null && !solicitudes.isEmpty()) {
				solicitudActiva = solicitudes.get(0);
				TipoTramite tipoCreado = solicitudActiva.getTramites().get(0).getTipoTramite();
				result.put("solicitud", solicitudActiva);
				
				log.debug("El tipo de tramite a realizar es : " + idTipoTramiteARealizar + " y el que ya existe es : " + tipoCreado.getIdTipoTramite());
				
				if(tipoCreado.getIdTipoTramite().equals(idTipoTramiteARealizar.intValue())) {
					solicitudCreada = true;
					mismoOrigen = solicitudActiva.getOrigenSolicitud().getIdTipoSolicitud().equals(idOrigenSoliciutd);
					if(mismoOrigen){
						session.setAttribute(KEY_SOLICITUD, solicitudActiva);
					}
				} else {
					otroTipoTramite = true;
					result.put("error", "No es posible realizar el tr&aacute;mite debido a que la persona cuenta con "
							+ "otro de tipo " + solicitudActiva.getTramites().get(0).getTipoTramite().getDescripcion());
				}
				
			} else {
				correcto = true;
			}
		}catch(Exception e) {
			log.error("Ocurrio un error al consultar las solicitudes", e);
			mensaje = "No fue posible consultar si el integrante del grupo familiar cuenta con solicitudes registradas";
		}
		
		result.put("correcto", correcto);
		result.put("mensaje", mensaje);
		result.put("solicitudCreada",solicitudCreada);
		result.put("mismoOrigen",mismoOrigen);
		result.put("otroTipoTramite",otroTipoTramite);
		
		
		return result;
	}
	
	@RequestMapping( value = "/iniciarTramiteCiudadano")
	public String crearTramiteCiudadano(@ModelAttribute TramiteProrroga prorroga, Model model,HttpSession session, HttpServletRequest request) {
		
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		Long idTipo = prorroga.getTipoTramite().getIdTipoTramite().longValue();
		Usuario usuario = this.getUsuarioSesion(this.procesarUsuarioSSO(request));
		Map<String, Object> result =  this.validacionesInicioCiudadano(prorroga, usuario);
		GrupoFamiliar integrante = null;
		
		if(this.getEstadoValidaciones(result)) {
			this.setTipoTramiteSession(session, idTipo);
			this.requiereDocumentos(session, idTipo);
			session.setAttribute(KEY_INTEGRANTE_PRORROGA, result.get("integrante"));
			session.setAttribute(KEY_TIPO_SOLICITUD, prorroga.getTipoTramite().getIdTipoTramite().longValue());
		}
		
		//checamos si el objeto de validaciones no viene nulo
		if(result != null) {
			//Obtenemos el indicador de las validaciones
			Boolean correcto = this.getEstadoValidaciones(result);
			//Si todo es correcto
			if(correcto) {
				//agregamos al integrante a session
				integrante = (GrupoFamiliar) result.get("integrante");
				
				//Checamos si existen solicitudes abiertas
				result = this.validacionesSolicitudAbierta(integrante.getAsignacionNSS().getNss(), integrante.getDerechohabiente().getIdPersona(), idTipo, idOrigenSolicitud, session);
				//checamos los requisitos
				correcto = this.getEstadoValidaciones(result);
				if(!result.containsKey("error")) {
					
					if(correcto) {
						session.setAttribute(KEY_INTEGRANTE_PRORROGA, integrante);
					} else {
						model.addAttribute("validaciones", result);
					}
				} else {
					//mandamos el mensaje de error que venga en las validaciones
					model.addAttribute("error", result.get("error"));
					model.addAttribute("baja", new TramiteBajaDerechohabiente());
				}
			} else {// de lo contrario
				//mandamos el mensaje de error que venga en las validaciones
				model.addAttribute("error", result.get("mensaje"));
				model.addAttribute("baja", new TramiteBajaDerechohabiente());
			}
			
			if(!correcto) {
				return VIEW_INICIAL_CIUDADANO;
			}

		}
		
		return this.guardarTramiteProrrogaInterno(integrante, model, session, request, idOrigenSolicitud);
	}	

	/**
	 * Este metodo se encarga de iniciar el tramite de prorroga de acuerdo al tipo seleccionado en la pantalla
	 * Crea una solicitud y tramite con los campos mínimos requeridos
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/iniciarTramite")
	public String crearTramiteProrroga(Model model, HttpSession session, HttpServletRequest request){
		
		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_PRORROGA);
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		
		return this.guardarTramiteProrrogaInterno(integrante, model, session, request, idOrigenSolicitud);
	}
	
	private void setTipoTramiteSession(HttpSession session, Long idTipo) {
		List<Long> idtiposTramite = new ArrayList<Long>();
		idtiposTramite.add(idTipo);
		session.setAttribute(KEY_TIPO_TRAMITE, idtiposTramite);
		session.setAttribute(KEY_TIPO_SOLICITUD, idTipo);
		
		return ;
	}
	
	private Boolean getEstadoValidaciones(Map<String, Object> result) {
		return (Boolean) result.get(KEY_ESTADO_REQUISITOS);
	}
	
	/**
	 * Metodo para guardar la solicitud de baja de derechohabientes para el portal ciudadano e imss digital
	 * @param integrante
	 * @param model
	 * @param session
	 * @param request
	 * @param result
	 * @return
	 */
	private String guardarTramiteProrrogaInterno(GrupoFamiliar integrante, Model model, HttpSession session, HttpServletRequest request, Long idOrigenSolicitud) {
			
		Long idTipoTramite = (Long) session.getAttribute(KEY_TIPO_SOLICITUD);
		Usuario usuario = this.getUsuarioSesion(this.procesarUsuarioSSO(request));
		Solicitud solicitud = null;
		TramiteProrroga prorroga = null;
		
		try {
		
			if (integrante != null) {				
				//Creamos la solicitud
				solicitud = prorrogaService.guardaTramiteProrroga(
						integrante.getDerechohabiente().getIdPersona(),
						usuario, integrante.getAsignacionNSS(),
						OrigenSolicitudEnum.getById(idOrigenSolicitud),
						TipoTramiteEnum.obternerEnumById(idTipoTramite.intValue()));
		
				if (solicitud != null) {
					
					if(idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())) {
						this.generarCadenaOriginal(solicitud,integrante.getDerechohabiente(), session);
						this.obtenerDatosAcuse(solicitud,integrante.getDerechohabiente(), session);
					}
					
					prorroga = this.getTramiteProrroga(solicitud);
					prorroga.setCaracter(new Caracter()) ;
					prorroga.getCaracter().setIdCaracter(CaracterEnum.PROVISIONAL.getId());
					
					session.setAttribute(KEY_SOLICITUD, solicitud);
					model.addAttribute("tramite", prorroga);
				}
			}
		
			
		} catch (DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al consultar al derehcohabiente", e);
		}
		
		//mandamos a pantalla el origen de la solicitud
		model.addAttribute("idOrigenSolicitud", idOrigenSolicitud);
		//mandamos al integrante al que le estamos haciendo la solicitud
		model.addAttribute("integrante", integrante);
		//enviamos la solicitud que acabamos de crear
		model.addAttribute("solicitud", solicitud);
		//indicamos que la siolicitud no se esta retomando
		model.addAttribute("isRetomar", false);
		
		return VIEW_CONTENIDO;
		
	}

	/**
	 * Este metodo se encarga de consultar la soilicitud no concluida de prorroga 
	 * para mostrarla en pantalla
	 * @param model
	 * @param solicitud
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/retomar")
	public String retomarSolicitud(Model model, @ModelAttribute(value = "solicitudForm") Solicitud solicitud, HttpSession session, HttpServletRequest request) {

		GrupoFamiliar integrante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_PRORROGA);
		TramiteProrroga prorroga = null;

		model.addAttribute("solicitudCreada", true);
		model.addAttribute("isRetomar", true);
		model.addAttribute("integrante", integrante);
		try {

			// Se consulta la solicitud
			solicitud = solicitudBusinessRemote.consultar(solicitud);

			// Datos del acuse
			obtenerDatosAcuse(solicitud, integrante.getDerechohabiente(), session);

			// Datos para la firma digital
			generarCadenaOriginal(solicitud, integrante.getDerechohabiente(), session);

			// Se verifica que no sea nula
			if (solicitud != null) {
				prorroga = this.getTramiteProrroga(solicitud);
				
				if(prorroga.getCaracter() == null || prorroga.getCaracter().getIdCaracter() == null || prorroga.getCaracter().getIdCaracter().equals(-1L)) {
					prorroga.setCaracter(new Caracter());
					prorroga.getCaracter().setIdCaracter(CaracterEnum.PROVISIONAL.getId());
				}
				session.setAttribute(KEY_SOLICITUD, solicitud);
				model.addAttribute("solicitud", solicitud);
				model.addAttribute("tramite", prorroga);

			} else {
				request.setAttribute("error", "No fue posible recuperar la solicitud");
			}
		} catch (Exception e) {
			solicitud = new Solicitud();
			request.setAttribute("error", "No fue posible recuperar la solicitud");
		}

		model.addAttribute("solicitud", solicitud);
		return VIEW_CONTENIDO;
	}

	/**
	 * Este metodo se encarga de guardar los la solicitud y tramite con la información capturada
	 * en pantalla
	 * @param tramite, contiene la informacion capturada en pantalla
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> guardarSolicitudProrroga(@RequestBody TramiteProrroga tramite, HttpSession session, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		GrupoFamiliar integrante = (GrupoFamiliar) session
				.getAttribute(KEY_INTEGRANTE_PRORROGA);

		// Se asigna el estado del tramite creado en /iniciarTramite
		tramite.setEstadoTramite(solicitud.getTramites().get(0)
				.getEstadoTramite());
		tramite.setPersona(integrante.getDerechohabiente());

		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramite);

		try {
			// Actualizamos la solicitud con los nuevos patrones seleccionados
			solicitudBusinessRemote.actualizarTramites(solicitud);
			result.put("mensaje", "Se han guardado correctamente los cambios");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error("error solicitud", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (TramiteNoEncontradoException e) {
			this.log.error("error tramite", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (Exception e) {
			this.log.error("error desconocido", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		}

		return result;
	}

	/**
	 * Este método se encarga de finalizar la solicitud y tramite de prorroga.
	 * Dependiendo del tipo de prorroga se haran ciertas validaciones, ademas de esto
	 * se guardan los documentos capturados y se generan los documentos resultantes.
	 * @param tramite
	 * @param response
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/solicitud/finalizar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> finalizarSolicitudProrroga(
			@RequestBody TramiteProrroga tramite, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {
		
		//Obtenemos el origen en el que estamos
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		Boolean isInternet = idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId());
		FirmaElectronica firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		GrupoFamiliar integrante = (GrupoFamiliar) session
				.getAttribute(KEY_INTEGRANTE_PRORROGA);
		tramite.setPersona(integrante.getDerechohabiente());
		tramite.setGrupoFamiliar(integrante);

		// Se asigna el estado del tramite creado en /iniciarTramite
		tramite.setEstadoTramite(solicitud.getTramites().get(0)
				.getEstadoTramite());

		solicitud.setTramites(new ArrayList<Tramite>());		

		try {
			//Actualizamos el tramite de baja
			tramite = (TramiteProrroga)solicitudBusinessRemote.actualizarXmlTramite(tramite);
			solicitud.getTramites().add(tramite);
			//solo si es internet habra elementos de firma en la sesion
			if(isInternet) {
				log.debug("Firma Electronica" + firma);
				log.debug("secuencia: " + firma.getSecuenciaNotaria());
				solicitud.setFirmaElectronica(firma);
			}
			
			/*
			 * Seccion que finaliza el tramite de acuerdo al tipo de prorroga seleccionada
			 */
			//Inicia prorroga por estudios
			if ( solicitud.getTramites().get(0).getTipoTramite()
					.getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo()) ) {
				tramite = (TramiteProrroga) solicitud.getTramites().get(0);
				
				ConstanciaEstudio constancia = (ConstanciaEstudio) solicitud
						.getTramites().get(0).getDocumentosProbatorios().get(0);

				
				Date fechaFinPeriodo = constancia.getFechaFinPeriodo();
				Date fechaFinVigencia = getFechaFinVigencia(integrante
						.getDerechohabiente().getFechaNacimiento(), 25);
				Date fechaNac25 = sumaAnios(integrante.getDerechohabiente()
						.getFechaNacimiento(), 25);

				// Verifica que la fecha fin de la prorroga no sea mayor a la
				// fecha de
				// vencimiento de vigencia
				if (fechaFinVigencia.before(fechaFinPeriodo)) {
					fechaFinPeriodo = fechaFinVigencia;
				}

				// Asigna la fecha de fin 30 dias naturales despues de la fecha
				tramite.setFechaInicioProrroga(constancia
						.getFechaInicioPeriodo());

				tramite.setFechaFinProrroga(sumarDiasFecha(fechaFinPeriodo, 30));
				if (tramite.getFechaFinProrroga().after(fechaNac25)) {
					tramite.setFechaFinProrroga(fechaNac25);
				}
				
				//EFM
				GrupoFamiliar grupo = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(integrante.getAsignacionNSS().getIdAsignacionNSS(), integrante.getAsignacionNSS().getIdPersona());
				if (grupo.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()) {
					if(grupo.getFechaFinVigencia() != null){
						tramite.setFechaFinProrroga(grupo.getFechaFinVigencia());
					}
				}

				tramite.setObservacion(constancia.getObservaciones());
				//El caracter es provisional
				tramite.getCaracter().setIdCaracter( CaracterEnum.PROVISIONAL.getId() );
			}
			//Fin prorroga por estudios 
			
			//Inicio de prorroga por enfermedad
			else if (solicitud.getTramites().get(0).getTipoTramite()
					.getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA.getCodigo())) {
				tramite = (TramiteProrroga) solicitud.getTramites().get(0);
				
				DictamenIntegranteIncapacitado dictamen = new DictamenIntegranteIncapacitado();
				CertificadoSituacionCritica certificado = new CertificadoSituacionCritica();
				
				Boolean existeIncapacidad = false;
				
				//Se obtiene la edad del derechohabiente
				Long edad = getEdadRedondeadaEnAnios(integrante.getDerechohabiente().getFechaNacimiento());
				int posicionDictamen = 0;
				if(  edad > 25 ){
					//Se debe iterar sobre los documentos probatorios del tramite
					//para determinar su tipo
					for( int i = 0; i <= tramite.getDocumentosProbatorios().size(); i++ ){
						
						try{
							
							DocumentoProbatorio documento = tramite.getDocumentosProbatorios().get(i);
							
							if( documento instanceof DictamenIntegranteIncapacitado ){
								dictamen = (DictamenIntegranteIncapacitado) documento;
								posicionDictamen = i;
							}
							else if( documento instanceof CertificadoSituacionCritica  ){
								certificado = (CertificadoSituacionCritica)documento;
							}
							
						}catch(IndexOutOfBoundsException e){
							// -----------------------------------------------------------------
							// La firma ya no pide adjuntar el documento probatorio y cuando
							// llega a esta punto lanza la excepcion
							// -----------------------------------------------------------------
						}
					}
				}
				else{
					certificado = (CertificadoSituacionCritica) tramite.getDocumentosProbatorios().get(0);
					existeIncapacidad =  true;
				}

				tramite.setFechaFinProrroga(certificado.getFechaTerminoIncapacidad());
				tramite.setFechaInicioProrroga(certificado.getFechaProbableInicio());
				tramite.setObservacion(tramite.getObservaciones());
				tramite.setObservaciones(tramite.getObservaciones().toUpperCase());
				
				dictamen.setFechaInicioEnfermedad(certificado.getFechaProbableInicio());
				
				tramite.setCaracter( tramite.getCaracter() );
				
				if(!existeIncapacidad){			
					throw new DerechohabientesBusinessException("error", "No es posible otorgar la pr\u00F3rroga debido a que no existe estado de incapacidad.");			
				}
				
				if( tramite.getCaracter().getIdCaracter() == CaracterEnum.DEFINITIVO.getId() ){
					tramite.setFechaFinProrroga( fechaDefaultVigenciaPermanente() );
				}
				

				//Si edad > 25 indica que hay 2 documentos
				if( edad > 25 ){
					tramite.getDocumentosProbatorios().set(posicionDictamen, dictamen);
				}
				else{
					tramite.getDocumentosProbatorios().add(dictamen);
				}
				
				//Se agrega el tramite a la solicitud
				solicitud.getTramites().set(0, tramite);
			}
			//Fin de prorroga por enfermedad
			
			//Inicio de prorroga por obstetricos
			else if (solicitud.getTramites().get(0).getTipoTramite()
					.getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo())) {
				tramite = (TramiteProrroga) solicitud.getTramites().get(0);
				GrupoFamiliar grupo = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(integrante.getAsignacionNSS().getIdAsignacionNSS(), integrante.getAsignacionNSS().getIdPersona());
				//Se obtiene el documento probatorio
				Obstetrico obstetrico = (Obstetrico) solicitud
						.getTramites().get(0).getDocumentosProbatorios().get(0);
				
				tramite.setFechaFinProrroga( sumarDiasFecha( obstetrico.getFechaParto(), 60 ) );
//				tramite.setFechaInicioProrroga( obstetrico.getFechaProbableConcepcion() );
				tramite.setFechaInicioProrroga( sumarDiasFecha( grupo.getFechaFinVigencia(), 1 ) );
				
				if( tramite.getObservaciones() != null && !tramite.getObservaciones().trim().equals("") ){
					tramite.setObservaciones( tramite.getObservaciones().toUpperCase() );
				}
				
				//Se valida la vigencia del grupo familiar, la concepcion tiene que estar dentro del periodo de vigencia
//				if(integrante.getFechaFinVigencia() != null && (
//						integrante.getFechaFinVigencia().before(obstetrico.getFechaExpedicion()) || 
//						integrante.getFechaFinVigencia().before(obstetrico.getFechaProbableConcepcion()))	){			
//					
//					throw new DerechohabientesBusinessException("error", "No es posible otorgar la pr\u00F3rroga debido a que la fecha probable de concepci\u00F3n no se encuentra dentro del periodo de aseguramento.");						
//				}
				
				//El caracter es provisional
				tramite.getCaracter().setIdCaracter( CaracterEnum.PROVISIONAL.getId() );
				
				//Se agrega el documento probatorio al tramite 
				tramite.getDocumentosProbatorios().set(0, obstetrico);
				
				solicitud.getTramites().set(0, tramite);
			}
			//Fin de prorroga por obstetricos
			solicitud = prorrogaService.finalizarSolicitudProrroga(solicitud);
			
			//si es internet se generan los documentos resultantes en la misma transaccion
			if(isInternet) {
				solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
			}
//			solicitudBusinessRemote.enviarSolicitudAProceso(solicitud,firma);// OSB
			result.put("mensaje", "Su solicitud ha finalizado correctamente");
			
			session.removeAttribute(KEY_SOLICITUD);
		} catch( DerechohabientesBusinessException e ){
			this.log.error("error solicitud", e);
			result.put("mensaje",e.getSituacion());
		} catch (SolicitudNoEncontradaException e) {
			this.log.error("error solicitud", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (TramiteNoEncontradoException e) {
			this.log.error("error tramite", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (Exception e) {
			this.log.error("error desconocido", e);
			result.put("mensaje",
					"Ocurri&oacute; un error al intentar guardar los cambios");
		}

		return result;
	}
	

	@RequestMapping(value = "/getConstanciaEstudios" , method = RequestMethod.POST)
	public @ResponseBody ConstanciaEstudio getConstanciaEstudios(HttpSession session) {
		
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		ConstanciaEstudio constancia = null;
		
		try {
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			TramiteProrroga tramite = this.getTramiteProrroga(solicitud);
			
			if(tramite != null) {
				DocumentoProbatorio constan = this.getDocumento(tramite, ConstanciaEstudio.class.getName());
				if(constan != null) {
					constancia = (ConstanciaEstudio) constan;
				}
			}
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return constancia;
	}
	
	private DocumentoProbatorio getDocumento(TramiteProrroga prorroga, String className) {
		
		log.debug("Se recibe el nombre de clase" + className);
		
		List<DocumentoProbatorio> documentos = prorroga.getDocumentosProbatorios();
		if(documentos != null && !documentos.isEmpty()) {
			log.debug("El tramite tiene documentos probatorios");
			for(DocumentoProbatorio docProb : documentos) {
				log.debug("El nombre de la clase del documento es: " + docProb.getClass().getName());
				if(docProb.getClass().getName().equals(className)) {
					return docProb;
				}
			}
		}
		
		return null;
	}
	
	private TramiteProrroga getTramiteProrroga(Solicitud solicitud) {
		TramiteProrroga prorroga = null;
		
		if(solicitud != null) {
			if(solicitud.getTramites() != null) {
				for(Tramite tramite: solicitud.getTramites()) {
					if(tramite instanceof TramiteProrroga) {
						prorroga = (TramiteProrroga) tramite;
						break;
					}
				}
			}
		}
		
		return prorroga;
	}
	
	@RequestMapping(value = "/validaInicioCiudadano", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validarInicioCiudadano(final @RequestBody TramiteProrroga oForm, final HttpServletResponse response) {
        log.trace("entramos a WizardProrrogaDerechohabienteController para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        
        final Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");
        new ProrrogaValidator().validateCiudadado(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("datos", oForm);

        return result;
    }
	
 	@RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody TramiteProrroga oForm, final HttpServletResponse response) {
        log.trace("entramos a WizardBajaDerechohabienteController para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        
        final Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");
        new ProrrogaValidator().validate(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("oForm", oForm);

        return result;
    }

	/**
	 * Este metodo se encarga de cancelar la solicitud de prorroga que no ha sido finalizada
	 * @param solicitud
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/solicitud/cancelar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitudProrroga(
			@RequestBody Solicitud solicitud, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudA = (Solicitud) session.getAttribute(KEY_SOLICITUD);

		if(solicitud.getSolicitudId().equals(solicitudA.getSolicitudId())) {

			result = this.cancelarsolicitud(solicitudA, "Solicitud cancelada a peticion del usuario");
			session.removeAttribute(KEY_SOLICITUD);
		} else {
			result.put("mensaje", "El id de la solicitud a cancelar no coincide con la que se tiene en session");
		}

		return result;
	}
	
	private Map<String, Object> cancelarsolicitud(Solicitud solicitud, String observaciones) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(solicitud != null) {
			log.debug("Existe una solicitud que se cancelara");
			try {
				
				solicitudBusinessRemote.cancelarSolicitud(solicitud.getSolicitudId(), 5L,1L,null, observaciones); 
				
				result.put("mensaje", "La solicitud fue cancelada correctamente");
			} catch (SolicitudException e) {
				this.log.error(e);
				result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
			} 
			
			result.put("solicitud", solicitud);
		} else {
			log.debug("No existia la solicitud");
		}
		
		return result;
	}

	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(
			@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}

	@RequestMapping(value = "/limpiar-session")
	public @ResponseBody
	Map<String, Object> limpiarSession(HttpSession session) {
		session.removeAttribute(KEY_INTEGRANTE_PRORROGA);
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

	private String getTipoTramite(Long tipoProrroga) {
		String descTipoTramite = "";
		if (tipoProrroga.equals(TipoTramiteEnum.PRORROGA_POR_VIGENCIA_PERMANENTE
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR VIGENCIA PERMANENTE";
		} else if (tipoProrroga.equals(TipoTramiteEnum.PRORROGA_POR_ACUERDOS_HCCD_HCT
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR ACUERDO";
		} else if (tipoProrroga.equals(TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR INCAPACIDAD OBSTETRICA";
		} else if (tipoProrroga.equals(TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR INCAPACIDAD FISICA O PSIQUICA";
		} else if (tipoProrroga.equals(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR ESTUDIOS";
		} else if (tipoProrroga.equals(TipoTramiteEnum.PRORROGA_POR_LAUDO
				.getCodigo().longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR LAUDO";
		} else if (tipoProrroga
				.equals(TipoTramiteEnum.PRORROGA_POR_VIGENCIA_TEMPORAL.getCodigo()
						.longValue())) {
			descTipoTramite = "PRORROGA DE DERECHOHABIENTE POR VIGENCIA TEMPORAL";
		}

		return descTipoTramite;
	}

	public Usuario getUsuarioSesion(UsuarioSSO usuariosso) {
		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());

		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(usuariosso.getNombre());
		usuario.setPerfilUsuario(pu);

		// Se crean los objetos necesarios para ligar el usuario con la
		// subdelegacion y delegacion.

		if (usuariosso.getDelegacion() != null
				&& usuariosso.getSubdelegacion() != null) {

			// LUDS Se agrego esta validacion para que si es en caso de un
			// usuario EXTERNO no le llega la delegacion.
			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(usuariosso.getDelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.setUsuario(usuario);
			uf.getSubdelegacion().setId(
					usuariosso.getSubdelegacion().longValue());
			usuario.setUsuarioFuncionario(uf);
		}

		usuario.setCveIdUsuario(usuariosso.getCurp());

		return usuario;
	}

	private void obtenerDatosAcuse(Solicitud solicitud, Derechohabiente persona,
			HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosAcuse = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat(
				"dd 'de' MMMM yyyy, HH:mm:ss", locMEX);

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance()
				.getTime());
		datosAcuse.setFechaElectronicaFormateada(strFechaElectronica);
		datosAcuse.setFechaElectronica(Calendar.getInstance().getTime());

		// RFC
		datosAcuse.setRfc(persona.getRfc());

		// Nombre, denominacion o razon social del interesado (y en su caso el
		// de su representante o persona autorizada)
		datosAcuse.setNombreCompleto(persona.getNombreCompleto());

		// CURP
		datosAcuse.setCurp(persona.getCurp());

		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosAcuse);
	}

	private void generarCadenaOriginal(Solicitud solicitud, Derechohabiente persona,
			HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat(
				"dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();

		String tipoSolicitud = (String) session
				.getAttribute(KEY_DESC_TIPO_SOLICITUD);
		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");

		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(tipoSolicitud).append("|");

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance()
				.getTime());
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(Calendar.getInstance().getTime());

		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");

		if(StringUtils.isNotBlank(persona.getRfc())) {
			// RFC
			contenidoAFirmar.append("RFC:");
			contenidoAFirmar.append(persona.getRfc()).append("|");
			datosEntradaFirma.setRfc(persona.getRfc());
		}

		// Nombre, denominacion o razon social del interesado (y en su caso el
		// de su representante o persona autorizada)
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(persona.getNombreCompleto()).append("|");
		datosEntradaFirma.setNombreCompleto(persona.getNombreCompleto());

		// CURP
		contenidoAFirmar.append("CURP:");
		contenidoAFirmar.append(persona.getCurp()).append("|");
		datosEntradaFirma.setCurp(persona.getCurp());

		// NSS(No aplica)
		contenidoAFirmar.append("Numero de Seguridad Social:");
		contenidoAFirmar.append(persona.getAsignacionNSS().getNss());
		contenidoAFirmar.append("||");
		

		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
	}
	
	private void requiereDocumentos(HttpSession session, Long tipo) {
		Boolean requiereDocumentos = false;
		try {
			requiereDocumentos = documentoProbatorioServiceBusinessRemote.requiereDocumentos(tipo);
		} catch (Exception e) {
			log.error("Ocurrio un error al consultar si el tramite requiere documentos",e);
		}
		
		session.setAttribute(KEY_REQUIERE_DOCS, requiereDocumentos);
		
	}
	
	/**
	 * Metodo para validaciones de inicio de tramite desde el portal ciudadano
	 * @param prorroga
	 * @return
	 */
	private Map<String, Object> validacionesInicioCiudadano(TramiteProrroga prorroga, Usuario usuario) {
		//Verificamos si existe la persona con curp
		Map<String, Object> result =  requisitosMinimosServiceRemote.validacionExistenciaPersonaTramiteProrrogaCiudadano(prorroga.getIdAsignacionNSS(), prorroga.getPersona().getCurp());
		//si las validaciones son correctas
		if(this.getEstadoValidaciones(result)) {
			GrupoFamiliar integrante = (GrupoFamiliar) result.get("integrante");
			result = this.validarConsistenciaBajaParentesco(integrante, prorroga.getTipoTramite().getIdTipoTramite().longValue(), 
																OrigenSolicitudEnum.PORTAL_CIUDADANO.getId(), prorroga.getIdAsignacionNSS(), usuario);
			result.put("integrante", integrante);
		}
		
		return result;
	}
	
	private Map<String, Object> validarConsistenciaBajaParentesco(GrupoFamiliar grupo, Long idTipoTramite, Long idOrigen, Long idAsignacioNSS, Usuario usuario) {
		Long idParentesco = grupo.getParentesco().getIdParentesco();
		
		if(idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo().longValue())) {
			if(!idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
				return this.getMapResquisitos(false, "La baja por t&eacute;rmino de convivencia solo aplica para beneficiarios con parentesco hijos.");
			}else{
				try {
					Map<String, Object> resp = prorrogaService.validateProrrogaEstudios(grupo, idAsignacioNSS, usuario);
					if(!(Boolean)resp.get("valido")){
						return this.getMapResquisitos(false, resp.get("mensaje").toString());
					}
				} catch(DerechohabientesBusinessException e) {
					log.error("Ocurrio un error al intentar validar el integrante", e);
					return this.getMapResquisitos(false, e.getMessage());
				} catch(Exception e) {
					log.error("Ocurrio un error no esperado al validar el integrante: " + e.getMessage(), e);
					return this.getMapResquisitos(false,"Ocurrio un error no esperado al validar el integrante");
				}
			}
		} else {
			return this.getMapResquisitos(false, "El tipo de tr&aacute;mite no corresponde con ninguna prorroga (" + idTipoTramite + ")");
		}
		
		return this.getMapResquisitos(true, "Las validaciones son correctas");
	}
	
	private Map<String, Object> getMapResquisitos(Boolean correcto, String mensaje) {
		Map<String,Object> resultado = new HashMap<String, Object>();
		
		resultado.put(KEY_ESTADO_REQUISITOS, correcto);
		resultado.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		return resultado;
	}

	/*
	 * Esta sección debe ser parte del DateUtils
	 */
	private Date fechaDefaultVigenciaPermanente() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 01);
		calendar.set(Calendar.MONTH, 01);
		calendar.set(Calendar.YEAR, 3000);

		return calendar.getTime();
	}

	private Date getFechaFinVigencia(Date fechaNacimiento, int edadFinVigenica) {
		Calendar c = Calendar.getInstance();
		c.setTime(fechaNacimiento);
		c.add(Calendar.YEAR, edadFinVigenica);

		return c.getTime();
	}

	private Date sumaAnios(Date fecha, int anios) {
		Date fechaNew = null;
		int years = 0;
		String fns = dateToStringConFormato(fecha, "dd/MM/yyyy");
		int as = Integer.parseInt(fns.substring(6));
		years = as + anios;
		String nuevaFecha = fns.substring(0, 6) + years;
		fechaNew = dateToDateConFormato(nuevaFecha, "dd/MM/yyyy");

		return fechaNew;
	}

	private Date sumarDiasFecha(Date fecha, Integer dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, dias);
		return cal.getTime();
	}

	public static String dateToStringConFormato(final Date fecha,
			final String formatoFecha) {
		String dateAsString = null; // NOPMD
		if (fecha != null && StringUtils.isNotBlank(formatoFecha)) {
			dateAsString = new SimpleDateFormat(formatoFecha, LOCALE_MX)
					.format(fecha);
		}
		return dateAsString;
	}

	public static Date dateToDateConFormato(final String fecha,
			final String formatoFecha) {
		Date dateAsDate = null; // NOPMD
		if (fecha != null && StringUtils.isNotBlank(formatoFecha)) {
			try {
				dateAsDate = new SimpleDateFormat(formatoFecha, LOCALE_MX)
						.parse(fecha);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dateAsDate;
	}
	
	public long getEdadRedondeadaEnAnios(Date fechaNacimiento) {
		Date hoy = new Date();
		Calendar fechaHoy = new GregorianCalendar();
		Calendar fechaNacimientoC = new GregorianCalendar();
		fechaHoy.setTime(hoy);
		fechaNacimientoC.setTime(fechaNacimiento);

		int restar = 0;
		long resultado = 0;
		int sumar =0;

		if (fechaHoy.get(Calendar.MONTH) < fechaNacimientoC.get(Calendar.MONTH)) {
			restar += 1;
		}
		else
		if (fechaHoy.get(Calendar.MONTH) == fechaNacimientoC.get(Calendar.MONTH)) {
			if (fechaHoy.get(Calendar.DATE) < fechaNacimientoC.get(Calendar.DATE)) {
				restar += 1;
			}
		}
	
		
		if (fechaHoy.get(Calendar.MONTH) > fechaNacimientoC.get(Calendar.MONTH)) {
			sumar += 1;
		}
		else
		if (fechaHoy.get(Calendar.MONTH) == fechaNacimientoC.get(Calendar.MONTH)) {
			if (fechaHoy.get(Calendar.DATE) > fechaNacimientoC.get(Calendar.DATE)) {
				sumar += 1;
			}
		}
		
		resultado = fechaHoy.get(Calendar.YEAR)	- fechaNacimientoC.get(Calendar.YEAR);
		resultado -= restar;
		resultado += sumar;

		return resultado;

	}
	
	//Fin
}
