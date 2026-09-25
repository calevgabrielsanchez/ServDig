package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.movPat.wizard;

import java.io.IOException;
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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.movPat.MovPatClasificacionController;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.DELTA_SESSION_VARIABLES;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramiteOrigenSol;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.BuzonClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

@Controller
@RequestMapping(value = "/movPat/wizard/tramite/clasificacion")
public class WizardClasificacionMovPatController extends AbstractController {


    private static final Logger log = LoggerFactory.getLogger(WizardClasificacionMovPatController.class);

	private static final String CODIGO_DISPOSICION_DE_LEY = TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo().toString();
	private static final long CODIGO_PATRON_SUJETO_OBLIGADO = CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue();
	private String DESC_TIPO_SOLICITUD = "MODIFICACION AL SRT";
	

	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	@Autowired
	private AfiliacionServiceBusinessRemote afiliacionBusiness;
	@Autowired
	private MovPatClasificacionController clasificacionController;
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired
	private ActividadEcServiceRemote actividadEcService;
	
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	

	/**
	 * @param model
	 * @param session
	 * @param request
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@RequestMapping(value = "/{numeroRegistroPatronal}/{tipoTramite}", method = RequestMethod.GET)
	public String initModificarDatosPersona(Model model, HttpSession session,
			HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal, @PathVariable Integer tipoTramite) {
		log.debug("::: En initModificarDatosPersona, numeroRegistroPatronal: " + numeroRegistroPatronal);
		String view;
		limpiarSesion(session);
		// Se busca si existen solicitudes en proceso pendientes
		boolean existenPendientes = false;
		boolean existenEnProceso = false;
		boolean modalidadValida = false;
		boolean existeMsjBuzon = false;
		boolean existeRegistradaFiel = false;
		
		SujetoObligado sujetoTramite = new SujetoObligado();
		BuzonClasificacion buzon = null;
		String msjBuzon = "";

		//Si el registro patronal tiene una modalidad valida
		if (numeroRegistroPatronal.length() > 8 && 
				(numeroRegistroPatronal.substring(8, 10).equals(ModalidadEnum.DIEZ.getNumModalidad())
						|| numeroRegistroPatronal.substring(8, 10).equals(ModalidadEnum.TRECE.getNumModalidad())
						|| numeroRegistroPatronal.substring(8, 10).equals(ModalidadEnum.DIECIOCHO.getNumModalidad()))) {
			modalidadValida = true;
			//Buscamos mensajes en el buzon de clasificacion
			buzon = actividadEcService.consultaMensajeBuzon(numeroRegistroPatronal);
			if(buzon != null && buzon.getMensaje() != null && buzon.getMensaje().trim().length() > 0) {
				existeMsjBuzon = true;
				msjBuzon = buzon.getMensaje().trim();
			}			
		}else{
			log.debug("::: El registro patronal numeroRegistroPatronal: " + numeroRegistroPatronal + " tiene modalidad invalida"); 
		}

		sujetoTramite.setNumeroRegistroPatronal(numeroRegistroPatronal);
        log.debug("numeroRegistroPatronal: {}", numeroRegistroPatronal);
		sujetoTramite = sujetoObligadoService.obtenerSujetoObligadoActividadEconomica(sujetoTramite);
        log.debug("sujeto tramite obtenido");
 
		
        Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);
        DESC_TIPO_SOLICITUD = this.getDescripcionTipoTramite(tipoTramite);
		TipoSolicitudEnum tipoSolicitud;
		TipoTramiteEnum tipoTramiteEnum=null;
		if (tipoTramite.equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor())) {
			tipoSolicitud = TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION;

			session.setAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_SOLICITUD, tipoSolicitud.getValor());
			session.setAttribute(DELTA_SESSION_VARIABLES.KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);

			view = "wizardModifPatronClasificacionInitVentanilla";
		} else {
			tipoSolicitud = TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO;
			tipoTramiteEnum = TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO;
			session.setAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_SOLICITUD, tipoSolicitud.getValor());
			session.setAttribute(DELTA_SESSION_VARIABLES.KEY_DESC_TIPO_SOLICITUD, "MODIFICACION DEL CENTRO DE TRABAJO");

			view = "wizardModifPatronCentroTrabajoInitVentanilla";
		}
		
		//se adiciona validacion para  solicitudes registraadas por el portal con FIEL registradas
		Solicitud solicitudRegistrada = solicitudServiceBusiness.
				obtenerSolicitudEnCaptura(sujetoTramite, tipoSolicitud, tipoTramiteEnum);
		if(solicitudRegistrada != null && solicitudRegistrada.getOrigenSolicitud().getIdOrigenSolicitud().intValue()
				== OrigenSolicitudEnum.INTERNET.getId().intValue()) {
			log.debug("ya existe una solicitud registrada en el portal con fiel folop " + solicitudRegistrada.getNoFolioSolicitud());
			existeRegistradaFiel = true;
			Tramite tramiteVigente = solicitudRegistrada.getTramites().get(0);

			model.addAttribute("idSolicitud", solicitudRegistrada.getSolicitudId());
			model.addAttribute("folioSolicitud", solicitudRegistrada.getNoFolioSolicitud());
			session.setAttribute("folioSolicitud", solicitudRegistrada.getNoFolioSolicitud());
			model.addAttribute("idTipoTramite", tramiteVigente.getTipoTramite().getIdTipoTramite());
			model.addAttribute("sujetoTramite", sujetoTramite);
			model.addAttribute("desTipoTramiteVigente", tramiteVigente.getTipoTramite().getDescripcion());
		
			request.setAttribute("existenPendientes", existenPendientes);
			request.setAttribute("existenEnProceso", existenEnProceso);
			request.setAttribute("modalidadValida", modalidadValida);
			request.setAttribute("existeMsjBuzon", existeMsjBuzon);
			request.setAttribute("msjBuzon", msjBuzon);
			request.setAttribute("existeRegistradaFiel", existeRegistradaFiel);		
			return view;
		}


		Solicitud solicitudEnCaptura = solicitudServiceBusiness
				.obtenerSolicitudActiva(sujetoTramite, tipoSolicitud, usuario, tipoTramiteEnum);
		
		if (solicitudEnCaptura == null) {
			solicitudEnCaptura = solicitudServiceBusiness
					.obtenerSolicitudActiva(sujetoTramite, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, usuario, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO);
		}
		
		log.debug("solicitudEnCaptura :" + solicitudEnCaptura);
		if (solicitudEnCaptura != null) {
			Tramite tramiteVigente = solicitudEnCaptura.getTramites().get(0);
			model.addAttribute("idSolicitud", solicitudEnCaptura.getSolicitudId());
			model.addAttribute("folioSolicitud", solicitudEnCaptura.getNoFolioSolicitud());
			session.setAttribute("folioSolicitud", solicitudEnCaptura.getNoFolioSolicitud());
			model.addAttribute("idTipoTramite", tramiteVigente.getTipoTramite().getIdTipoTramite());
			model.addAttribute("sujetoTramite", sujetoTramite);
			model.addAttribute("desTipoTramiteVigente", tramiteVigente.getTipoTramite().getDescripcion());
			if(solicitudEnCaptura.getOrigenSolicitud().getIdOrigenSolicitud().intValue()
					== OrigenSolicitudEnum.INTERNET.getId().intValue()) 
				existeRegistradaFiel = true;
			else 
				existenPendientes = true;
		
			
		
			session.setAttribute("keyPatronesSustitucionFusion", ((TramiteSujetoObligado) tramiteVigente).getSujetoObligado().getSujetosObligados());
		} else {
			Solicitud solicitudProceso = solicitudServiceBusiness
					.obtenerSolicitudEnProceso(sujetoTramite, tipoSolicitud, tipoTramiteEnum);
			
			if (solicitudProceso == null) {
				solicitudProceso = solicitudServiceBusiness
						.obtenerSolicitudEnProceso(sujetoTramite, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO);
			}

			if (solicitudProceso != null) {
				Tramite tramiteVigente = solicitudProceso.getTramites().get(0);

				model.addAttribute("idSolicitud", solicitudProceso.getSolicitudId());
				model.addAttribute("folioSolicitud", solicitudProceso.getNoFolioSolicitud());
				session.setAttribute("folioSolicitud", solicitudProceso.getNoFolioSolicitud());
				model.addAttribute("idTipoTramite", tramiteVigente.getTipoTramite().getIdTipoTramite());
				model.addAttribute("sujetoTramite", sujetoTramite);
				model.addAttribute("desTipoTramiteVigente", tramiteVigente.getTipoTramite().getDescripcion());
				existenEnProceso = true;
				session.setAttribute("keyPatronesSustitucionFusion", ((TramiteSujetoObligado) tramiteVigente).getSujetoObligado().getSujetosObligados());
			}

		}
		
		

		model.addAttribute("sujetoTramite", sujetoTramite);
		
		request.setAttribute("existenPendientes", existenPendientes);
		request.setAttribute("existenEnProceso", existenEnProceso);
		request.setAttribute("modalidadValida", modalidadValida);
		request.setAttribute("existeMsjBuzon", existeMsjBuzon);
		request.setAttribute("msjBuzon", msjBuzon);
		request.setAttribute("existeRegistradaFiel", existeRegistradaFiel);
		
		return view;
	}
	
	@RequestMapping(value = "/patronesFusionSust", method = RequestMethod.POST)
	@ResponseBody
	public List<SujetoObligado> getSujetosFusion(HttpSession session) {
		List<SujetoObligado> sujetos = (List<SujetoObligado>)session.getAttribute("keyPatronesSustitucionFusion");
		
		return sujetos;
		
	}

	/**
	 * @param sujetoTramite
	 * @param session
	 * @param request
	 * @param response
	 * @param locale
	 * @param model
	 * @param idTipoTramite
	 * @return
	 */
	@RequestMapping(value = "/generarSolicitud/{idTipoTramite}", method = RequestMethod.POST)
	public Object generaSolicitud(@ModelAttribute SujetoObligado sujetoTramite,
			final HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Locale locale, final Model model,
			@PathVariable Integer idTipoTramite) throws PersonaFisicaNoEncontradaException {
		UsuarioSSO sso = this.procesarUsuarioSSO(request);

		String numeroRegistroPatronal = sujetoTramite.getNumeroRegistroPatronal();
		log.debug(":: Numero Registro Patronal para generar solicitud: " + numeroRegistroPatronal);
		log.debug(":: Identificador tipo tramite: " + idTipoTramite);
		System.err.println(":: Numero Registro Patronal para generar solicitud: " + numeroRegistroPatronal);
		System.err.println(":: Identificador tipo tramite: " + idTipoTramite);
		
		
		TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(idTipoTramite);
		sujetoTramite = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
		if(sujetoTramite.getClasificacion()!= null) {
			sujetoTramite.getClasificacion().setSujetoObligado(sujetoTramite);
		}else {
			Clasificacion clasificacion = new Clasificacion();
			clasificacion.setSujetoObligado(sujetoTramite);
			sujetoTramite.setClasificacion(clasificacion);
		}
		
		Fisica patronSujetoObligadoFisica = sujetoTramite.getFisica();
		Moral patronSujetoObligadoMoral = sujetoTramite.getMoral();
		
		sujetoTramite = inicializaInformacionActividadEconomica(sujetoTramite);
		session.setAttribute("sujetoTramite", sujetoTramite);
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_TRAMITE, sujetoTramite);
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_TRAMITE, tipoTramite);

		//Map<String, ? extends Object> trModSrt = clasificacionController.guardarClasificacion(inicializarObjetoDeTramite(sujetoTramite, model), false, false, response, session, locale);
		Map<String, ? extends Object> trModSrt = clasificacionController.guardarClasificacion(sujetoTramite.getClasificacion(), false, false, response, session, locale);
		log.debug("trModSrt: " + trModSrt);
		Long idSolicitud = (Long) trModSrt.get("idSolicitud");
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
				
		log.debug("Id solicitud creada: " + idSolicitud);
		inicializa(sujetoTramite, idTipoTramite, model, idSolicitud, session);
		session.setAttribute("isRetomar", false);
		session.setAttribute("folioSolicitud", trModSrt.get("folioSolicitud"));
		session.setAttribute("mensajeError", trModSrt.get("mensajeError"));
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_ORIGEN_SOLICITUD, OrigenSolicitudEnum.VENTANILLA.getId());
		
		// Datos del acuse TODO: Quitar lo comentado
//		Fisica personaRecuperada = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
//
//        log.info("{}", ToStringBuilder.reflectionToString(personaRecuperada));
//		obtenerDatosAcuse(solicitud, personaRecuperada, session, numeroRegistroPatronal);

		// Datos del Sujeto Tramite Involucrado
		DESC_TIPO_SOLICITUD = this.getDescripcionTipoTramite(idTipoTramite);
		if (patronSujetoObligadoMoral != null) {
			generarCadenaOriginal(solicitud, patronSujetoObligadoMoral, session, numeroRegistroPatronal);
			session.setAttribute(DELTA_SESSION_VARIABLES.KEY_RFC_SOLICITANTE, patronSujetoObligadoMoral.getRfc());
		} else {
			generarCadenaOriginal(solicitud, patronSujetoObligadoFisica, session, numeroRegistroPatronal);
			session.setAttribute(DELTA_SESSION_VARIABLES.KEY_RFC_SOLICITANTE, patronSujetoObligadoFisica.getRfc());
		}
		
		getDocumentosRequeridos(model, idTipoTramite);
		/**
		OrigenSolicitud origenSolicitud = new OrigenSolicitud();
		origenSolicitud.setIdOrigenSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
		origenSolicitud.setDescripcion(OrigenSolicitudEnum.VENTANILLA.getDesc());
		solicitud.setOrigenSolicitud(origenSolicitud );
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		estadoSolicitud.setDescripcion(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getDescripcion());
		solicitud.setEstadoSolicitud(estadoSolicitud );
		solicitud = solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.ACTIVO, sujetoTramite);
		**/
		model.addAttribute("origenInternet",false);
		
		return "wizardModifPatronClasificacionContenidoVentanilla";
	}
	
	@RequestMapping(value="/capturaInformacion")
	public String redirectVistaInicial(){
		
		log.debug("Entro al redirect de captura de informacion");
		return "wizardModifPatronClasificacionContenidoVentanilla";
	}
	
	/**
	 * Inicializa con valores vac�os los datos concernientes a medios de contacto del centro de trabajo, 
	 * en caso de no existir informaci�n. Si existe informaci�n la agrega a las variables correspondientes
	 * para ser desplegada en la vista.
	 * 
	 * @param sujetoTramite
	 * @param model
	 */
	private void inicializarMediosContactoCentroTrabajo(SujetoObligado sujetoTramite, Model model){
		String telefonoFijo="";
		String telefonoFijo2="";
		String correoElectronico="";
		
		if(sujetoTramite.getCntroTrabajo()!=null && sujetoTramite.getCntroTrabajo().getMediosContacto()!=null){
			
			int index = 0; 
			for(MedioContacto medio :sujetoTramite.getCntroTrabajo().getMediosContacto()){
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
					if((medio.getIdVista() != null && medio.getIdVista()==1) || index == 0)
						telefonoFijo = medio.getDesFormaContacto();
					else if((medio.getIdVista() != null && medio.getIdVista()==2) || index == 1)
						telefonoFijo2 = medio.getDesFormaContacto();
				}
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
					correoElectronico=medio.getDesFormaContacto();
				}
				index++;
			}
		}
		
		model.addAttribute("ctTelefonoFijo",telefonoFijo);
		model.addAttribute("ctTelefonoFijo2",telefonoFijo2);
		model.addAttribute("ctCorreoElectronico",correoElectronico);
	}
	/**
	 * Elimina la informaci�n previa que pudiese existir para los campos de captura de un 
	 * nuevo tr�mite.
	 * @param sujetoTramite
	 * @return Clasificacion
	 */
	private Clasificacion inicializarObjetoDeTramite(SujetoObligado sujetoTramite, Model model){
		Clasificacion clasificacion = sujetoTramite.getClasificacion();
		clasificacion.setGiro("");
		Division division = new Division();
		Grupo grupo = new Grupo();
		grupo.setDivision(division);
		Fraccion fraccion = new Fraccion();
		fraccion.setClase(new Clase());
		fraccion.setGrupo(grupo);
		clasificacion.setFraccion(fraccion);
		clasificacion.getSujetoObligado().setProceso(new Proceso());
		clasificacion.getSujetoObligado().setDesAfectacion("");
		clasificacion.getSujetoObligado().setDesUsosBienes("");
		inicializarMediosContactoCentroTrabajo(sujetoTramite, model);
		return clasificacion;
	}
	
	private SujetoObligado inicializaInformacionActividadEconomica(SujetoObligado sujetoTramite){
		if(sujetoTramite.getBienes().isEmpty()) {
			sujetoTramite.setBienes(new ArrayList<Bien>());
		}
		if(sujetoTramite.getEquipos().isEmpty()) {
			sujetoTramite.setEquipos(new ArrayList<MaquinariaEquipo>());
		}
		if(sujetoTramite.getEquiposTransporte().isEmpty()) {
			sujetoTramite.setEquiposTransporte(new ArrayList<EquipoTransporte>());
		}
		if(sujetoTramite.getMateriaPrimaMateriales().isEmpty()) {
			sujetoTramite.setMateriaPrimaMateriales(new ArrayList<MateriaPrima>());
		}
		if(sujetoTramite.getPersonal().isEmpty()) {
			sujetoTramite.setPersonal(new ArrayList<Personal>());
		}
		if(sujetoTramite.getProductos().isEmpty()) {
			sujetoTramite.setProductos(new ArrayList<Producto>());
		}
		if(sujetoTramite.getCntroTrabajo() == null) {
			sujetoTramite.setCntroTrabajo(crearCentroTrabajoVacio());
		}
		if(sujetoTramite.getMunicipioIMSS().equals(null)) {
			sujetoTramite.setMunicipioIMSS(crearMunicipioImss());
		}
		
		return sujetoTramite;
	}
	
	private MunicipioIMSS crearMunicipioImss() {
		MunicipioIMSS municipioIMSS = new MunicipioIMSS();
		municipioIMSS.setSubdelegacion(new Subdelegacion());
		municipioIMSS.setTipoAmbito(new TipoAmbito());
		return municipioIMSS;
	}

	private CentroTrabajo crearCentroTrabajoVacio(){
		CentroTrabajo ct=new CentroTrabajo();
		ct.setAsentamiento(crearAsentamientoVacio());
		ct.setCodigoPostal(new CodigoPostal());
		ct.setVialidadPrimaria(crearVialidadVacia());
		ct.setVialidadReferenciaPrimaria(crearVialidadVacia());
		ct.setVialidadReferenciaSecundaria(crearVialidadVacia());
		ct.setVialidadReferenciaPosterior(crearVialidadVacia());
		return ct;
	}
	
	private Asentamiento crearAsentamientoVacio(){
		Asentamiento asent = new Asentamiento();
		asent.setLocalidad(new Localidad());
		asent.getLocalidad().setMunicipio(new Municipio());
		asent.getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
		return asent;
	}
	
	private Vialidad crearVialidadVacia(){
		Vialidad vialidad = new Vialidad();
		vialidad.setTipoVialidad(new TipoVialidad());
		return vialidad;
	}

	private void inicializa(SujetoObligado sujetoObligado, Integer idTramite,
			Model model, Long idSolicitud, HttpSession session) {
		log.debug("ENTRANDO AL METODO INICIO DE CLASIFICACIONCONTROLER");
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		session.setAttribute(KEY_USUARIO, usuario);

		Long idPerfilUusario = usuario.getPerfilUsuario().getIdPerfilUsuario();
		boolean esOperador = idPerfilUusario
				.equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
		model.addAttribute("esOperador", esOperador);
        log.info("esOperador: {}", esOperador);

		if (sujetoObligado.getNumeroRegistroPatronal() == null) {
			if (idSolicitud != null && idSolicitud > 0) {
				Solicitud sol = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
				TramiteSujetoObligado tso = (TramiteSujetoObligado) sol.getTramites().get(0);
				sujetoObligado = tso.getSujetoObligado();

				StringBuffer sbNumeroRegistroPatronal = new StringBuffer();
				sbNumeroRegistroPatronal.append(sujetoObligado.getNumeroRegistroPatronal())
						.append(sujetoObligado.getModalidad().getNumModalidad())
						.append(sujetoObligado.getDigVerificador());
				sujetoObligado.setNumeroRegistroPatronal(sbNumeroRegistroPatronal.toString());
			}
		}

		sujetoObligado = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(idTramite);
		SujetoObligado sujetoTramite = new SujetoObligado();

		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_OBLIGADO, null);
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_TRAMITE, null);
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_TRAMITE, null);
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_ID_SOLICITUD, null);

		if (idTramite.toString().equals(CODIGO_DISPOSICION_DE_LEY)
				&& idPerfilUusario.equals(CODIGO_PATRON_SUJETO_OBLIGADO)
				|| (TipoTramiteEnum.DISPOSICION_DE_LEY.toString().equals(idTramite.toString())
						&& idPerfilUusario.equals(CODIGO_PATRON_SUJETO_OBLIGADO))) {
			session.setAttribute("showFinalizarFD", "1");
		} else {
			session.setAttribute("showFinalizarFD", "0");
		}

		log.debug("El tramite quedo como: [" + session.getAttribute("showFinalizarFD") + "]");
		log.debug("CLASIFICACION ID_SOLICITUD: [" + idSolicitud + "]");

		if (idSolicitud != null && idSolicitud > 0) {
			Solicitud sol = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
			TramiteSujetoObligado tso = (TramiteSujetoObligado) sol.getTramites().get(0);
			sujetoTramite = tso.getSujetoObligado();

			if (sol.getEstadoSolicitud().getIdEstadoSolicitud().intValue()
					== EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue()) {
				sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());
			}

			tipoTramite = TipoTramiteEnum.obternerEnumById(tso.getTipoTramite().getIdTipoTramite());
			log.debug("SUJETO TRAMITE: [" + sujetoTramite + "]");
			sujetoTramite = inicializaSujetoObligadoPresentacion(sujetoTramite);
			//Termina Codigo de prueba
			inicializarMediosContactoCentroTrabajo(sujetoTramite, model);
			model.addAttribute("idSolicitud", sol.getSolicitudId());
			model.addAttribute("indRPCInvalido", sol.isIndRpcInvalido());
			model.addAttribute("indReintento", sol.isIndReintentoRpc());

			session.setAttribute(DELTA_SESSION_VARIABLES.KEY_ID_SOLICITUD, sol.getSolicitudId());
		} else {
			log.error("INICIALIZA EL SUJETO TRAMITE CON EL REGISTRO PATRONAL: " + sujetoObligado.getNumeroRegistroPatronal());
			System.err.println("INICIALIZA EL SUJETO TRAMITE CON EL REGISTRO PATRONAL: " + sujetoObligado.getNumeroRegistroPatronal());
			System.err.println("CLASIFICACION: " + sujetoObligado.getClasificacion());

			sujetoTramite.setNumeroRegistroPatronal(sujetoObligado.getNumeroRegistroPatronal());
			sujetoTramite.setModalidad(sujetoObligado.getModalidad());
			sujetoTramite.setDigVerificador(sujetoObligado.getDigVerificador());

			System.err.println("Se inicializa PSP de clasificacion: "
					+ sujetoObligado.getClasificacion().getIndPrestaServicioPersonal());
			sujetoTramite.getClasificacion().setIndRegPatClase(
					sujetoObligado.getClasificacion().getIndRegPatClase());

			model.addAttribute("indRPCInvalido", false);
			model.addAttribute("indReintento", false);
		}

		sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());
		if( TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO == tipoTramite) {

			CentroTrabajo cntroTrabajo = sujetoObligadoService.getCentroTrabajo(sujetoObligado.getCveIdSujetoObligado());
			sujetoObligado.setCntroTrabajo(cntroTrabajo);
			sujetoTramite.setCntroTrabajo(cntroTrabajo);
		}

		model.addAttribute("idTramite", tipoTramite);
		model.addAttribute("sujetoObligado", sujetoObligado);
		model.addAttribute("sujetoTramite", sujetoTramite);

		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_OBLIGADO, sujetoObligado);
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_TRAMITE, sujetoTramite);
		System.out.println(DELTA_SESSION_VARIABLES.KEY_SUJETO_TRAMITE + " : " + sujetoTramite);
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_TRAMITE, tipoTramite);

		String descripcionTipoTramite = this.getDescripcionTipoTramite(idTramite);
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_DESCRIPCION_TIPO_TRAMITE, descripcionTipoTramite);
		model.addAttribute(DELTA_SESSION_VARIABLES.KEY_DESCRIPCION_TIPO_TRAMITE, descripcionTipoTramite);
		
	}

	private SujetoObligado inicializaSujetoObligadoPresentacion(
			SujetoObligado so) {
		if (so.getBienes() == null) {
			so.setBienes(new ArrayList<Bien>());
		}
		if (so.getEquipos() == null) {
			so.setEquipos(new ArrayList<MaquinariaEquipo>());
		}
		if (so.getEquiposTransporte() == null) {
			so.setEquiposTransporte(new ArrayList<EquipoTransporte>());
		}
		if (so.getMateriaPrimaMateriales() == null) {
			so.setMateriaPrimaMateriales(new ArrayList<MateriaPrima>());
		}
		if (so.getPersonal() == null) {
			so.setPersonal(new ArrayList<Personal>());
		}
		if (so.getProductos() == null) {
			so.setProductos(new ArrayList<Producto>());
		}

		return so;
	}
	
	/**
	 * @param sujetoTramite
	 * @param idSolicitud
	 * @param response
	 * @param session
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitudClasificacion(
			@PathVariable Long idSolicitud, HttpServletResponse response,
			Locale locale) {
		String message = "";

		Map<String, Object> result = new HashMap<String, Object>();
		log.debug("CANCELAR SOLICITUD [ " + idSolicitud + " ]");

		if (idSolicitud != null) {
			try {
				log.debug("Se cancela la solicitud [ " + idSolicitud + " ]");
				solicitudServiceBusiness.cancelarSolicitud(idSolicitud);

				result.put("mensaje", "La solicitud fue cancelada correctamente");
				result.put("solicitud", idSolicitud);
			} catch(SolicitudException se){
				message = messageSource.getMessage(se.getSituacion(), null, locale);
				result.put("mensaje", message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "Ocurrio un error al intentar cancelar la solicitud.";
				result.put("mensaje", message);
			}
		}

		return result;
	}

	/**
	 * @param sujetoObligado
	 * @param session
	 * @param request
	 * @param idTipoTramite
	 * @param idSolicitud
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/retomar/solicitud/{idTipoTramite}/{idSolicitud}", method = RequestMethod.POST)
	public String cargarSolicitudSRT(
			@ModelAttribute SujetoObligado sujetoObligado, HttpSession session,
			HttpServletRequest request, @PathVariable Integer idTipoTramite,
			@PathVariable Long idSolicitud, final Model model) throws PersonaFisicaNoEncontradaException {
		UsuarioSSO sso = this.procesarUsuarioSSO(request);
		//TODO: descomentar cuando tengamos usuario valido
//		Fisica personaRecuperada = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());

        inicializa(sujetoObligado, idTipoTramite, model, idSolicitud, session);
		request.setAttribute("isRetomar", true);
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_ORIGEN_SOLICITUD, OrigenSolicitudEnum.VENTANILLA.getId());
		
		// Datos del acuse 		//TODO: descomentar cuando tengamos usuario valido
//		obtenerDatosAcuse(solicitud, personaRecuperada, session, sujetoObligado.getNumeroRegistroPatronal());
		
		SujetoObligado sujetoTramite = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		Fisica patronSujetoObligadoFisica = sujetoTramite.getFisica();
		Moral patronSujetoObligadoMoral = sujetoTramite.getMoral();

		DESC_TIPO_SOLICITUD = this.getDescripcionTipoTramite(idTipoTramite);
		if (patronSujetoObligadoMoral != null) {
			generarCadenaOriginal(solicitud, patronSujetoObligadoMoral, session, sujetoObligado.getNumeroRegistroPatronal());
			request.setAttribute(DELTA_SESSION_VARIABLES.KEY_RFC_SOLICITANTE, patronSujetoObligadoMoral.getRfc());
		} else {
			generarCadenaOriginal(solicitud, patronSujetoObligadoFisica, session, sujetoObligado.getNumeroRegistroPatronal());
			request.setAttribute(DELTA_SESSION_VARIABLES.KEY_RFC_SOLICITANTE, patronSujetoObligadoFisica.getRfc());
		}
		model.addAttribute("descripcionTipoTramite", DESC_TIPO_SOLICITUD);
		session.setAttribute("keyPatronesSustitucionFusion", ((TramiteSujetoObligado) solicitud.getTramites().get(0)).getSujetoObligado().getSujetosObligados());
		getDocumentosRequeridos(model, idTipoTramite);
		model.addAttribute("origenInternet",false);
		return "wizardModifPatronClasificacionContenidoVentanilla";
	}

	

	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody
	Solicitud limpiarSesion(final HttpSession session) {
		session.removeAttribute("keyPatronesSustitucionFusion");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_ID_SOLICITUD);
		session.removeAttribute("folioSolicitud");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_TRAMITE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_TRAMITE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_OBLIGADO);
		session.removeAttribute("showFinalizarFD");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_DESCRIPCION_TIPO_TRAMITE);
		session.removeAttribute("mensajeError");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_SOLICITUD);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_RFC_SOLICITANTE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_CADENA_ORIGINAL);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_FIRMA_ELECTRONICA);

		return null;
	}

	@RequestMapping(value = "/mostrarConfirmacion", method = {RequestMethod.POST})
	public String mostrarConfirmacion(@RequestParam("fwd") String fwd, 
			@RequestParam("rfc") String rfc, 
			HttpServletResponse response, 
			Model model, HttpSession session){
		Socio socio = new Socio();
		socio.setRfc(rfc);
		model.addAttribute("socio", socio);
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));

		if (StringUtils.isNotBlank(fwd) && fwd.equals("modificacion.aviso")) {
			return "wizardModifPatronClasificacionAvisoVentanilla";
		} else {
			return "wizardModifPatronClasificacionContenidoVentanilla";
		}
	}
	
	private String getDescripcionTipoTramite(Integer idTramite) {
		TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(idTramite);
		String descripcionTipoTramite = "MODIFICACION AL SRT";
		switch (tipoTramite) {
			case ACTIVIDAD_ECONOMICA: 
				descripcionTipoTramite = "Cambio de actividad";
				break;
			case DISPOSICION_DE_LEY: 
				descripcionTipoTramite = "Cambio por disposici&oacute;n de Ley, o del RACERF";
				break;
			case INCORPORACION_DE_ACTIVIDADES: 
				descripcionTipoTramite = "Incorporaci&oacute;n de actividades";
				break;
			case COMPRA_DE_ACTIVOS: 
				descripcionTipoTramite = "Compra de activos";
				break;
			case COMODATO: 
				descripcionTipoTramite = "Comodato";
				break;
			case ENAJENACION: 
				descripcionTipoTramite = "Enajenaci&oacute;n";
				break;
			case ARRENDAMIENTO: 
				descripcionTipoTramite = "Arrendamiento";
				break;
			case FIDEICOMISO_TRASLATIVO: 
				descripcionTipoTramite = "Fideicomiso traslativo";
				break;
			case ACTUALIZACION_CENTRO_TRABAJO:
				descripcionTipoTramite = "Cambio de domicilio";
				break;
			case FUSION: 
				descripcionTipoTramite = "Fusi&oacute;n";
				break;
			case ESCISION: 
				descripcionTipoTramite = "Escisi&oacute;n";
				break;
			case REANUDACION_DE_ACTIVIDADES: 
				descripcionTipoTramite = "Reanudaci&oacute;n de actividades";
				break;
			case SUSTITUCION_PATRONAL: 
				descripcionTipoTramite = "Sustituci&oacute;n patronal";
				break;
			case SUSTITUCION_PATRONAL_SUBCONTRATACION: 
				descripcionTipoTramite = "Sustituci&oacute;n patronal por subcontrataci&oacute;n";
				break;
			case CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO: 
				descripcionTipoTramite = "Modificaci&oacute;n en SRT por cambio de domicilio diferentes municipios";
				break;
				
			default:
				break;
		}
		
		log.debug("MSRT - El tipo de tramite es "+idTramite+"\nLa descripcion del tramite es " + descripcionTipoTramite);
	
		
		return descripcionTipoTramite;
	}

	private void generarCadenaOriginal(Solicitud solicitud, Persona persona, HttpSession session, String numeroRegistroPatronal) {
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

        String nombreRazonSocial = sbnombre.toString();

        contenidoAFirmar.append("Nombre o Razon Social:");
		/*
		 * Se sustituyen las comillas con el caracter especial HTML &quot; para
		 * que el valor en el input hidden sea correcto y no se despu�s el
		 * applet de la firma funcione correctamente
		 */
        contenidoAFirmar.append(nombreRazonSocial.replace("\"", "&quot;")).append("|");

        datosEntradaFirma.setNombreCompleto(nombreRazonSocial.toString());



        if (persona instanceof Fisica) {
			// CURP
			if(((Fisica)persona).getCurp() != null) {
				contenidoAFirmar.append("CURP:");
				contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
				datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
			}
		}

		if(numeroRegistroPatronal != null && numeroRegistroPatronal.trim().length() > 0){
			// Registro Patronal
			contenidoAFirmar.append("Registro Patronal:");
			contenidoAFirmar.append(numeroRegistroPatronal).append("|");
		}

		contenidoAFirmar.append("|");
		// NSS(No aplica)
		//contenidoAFirmar.append("Numero de Seguridad Social:||");

		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
	}

	private void obtenerDatosAcuse(Solicitud solicitud, Persona persona, HttpSession session, String numeroRegistroPatronal) {
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

		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_FIRMA_ELECTRONICA, datosAcuse);
	}
	
	private void getDocumentosRequeridos(Model model, Integer idTipoTramite) {
		
		try {
		List<DoctoReqTramiteOrigenSol> documentos = documentoProbatorioServiceBusinessRemote.getDocumentosRequeridosPorTipoTramiteOrigenSol(
				Long.parseLong(idTipoTramite+""), OrigenSolicitudEnum.VENTANILLA.getId());
		Long documentosRequeridos = documentos != null ? documentos.size() : 0L;
		String tooltipDomicilio = "";
		for (DoctoReqTramiteOrigenSol doctoReqTramiteOrigenSol : documentos) {
			if (doctoReqTramiteOrigenSol.getCveIdDoctoReqTramOrgSol().equals(12L)) {// 12 - Domicilio
				String[] arrayDoc = doctoReqTramiteOrigenSol.getRefDetalleDoctoRequerido().split("[|]");
				for (String doc : arrayDoc) {
					tooltipDomicilio += "<li>" + doc + "</li>";
				}
				doctoReqTramiteOrigenSol.setRefDetalleDoctoRequerido(tooltipDomicilio);
				break;
			}
		}
		
		model.addAttribute("documentos", documentos);
		model.addAttribute("documentosRequeridos", documentosRequeridos);
		
		log.info("En documentoProbatorioServiceBusinessRemote ---------> " + documentosRequeridos);
		}catch (Exception e) {
			log.error("error al consultar los documentos requeridos para el tramite " ,e );
		}
	}
	
	private String obtenerNombreCompletoPersonaFisica(Fisica fisica){

		StringBuffer nombreCompleto = new StringBuffer();
		if(fisica.getPrimerApellido()!=null && fisica.getPrimerApellido()!="null"){
			nombreCompleto.append(fisica.getPrimerApellido());
			nombreCompleto.append(" ");
		}
		if(fisica.getSegundoApellido()!=null && fisica.getSegundoApellido()!="null"){
			nombreCompleto.append(fisica.getSegundoApellido());
			nombreCompleto.append(" ");
		}
		if(fisica.getNombre()!=null && fisica.getNombre()!="null")
			nombreCompleto.append(fisica.getNombre());
		
		return nombreCompleto.toString();
	}
	
	/**
	 * @param sujetoObligado
	 * @param session
	 * @param request
	 * @param idTipoTramite
	 * @param idSolicitud
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/retomar/solicitud/{registroPatronal}/{idTipoTramite}/{idSolicitud}", method = RequestMethod.GET)
	public String cargarSolicitudPorFolioSRT(HttpSession session,
			HttpServletRequest request, @PathVariable String registroPatronal, @PathVariable Integer idTipoTramite,
			@PathVariable Long idSolicitud, final Model model) throws PersonaFisicaNoEncontradaException {
		SujetoObligado sujetoTramite = new SujetoObligado();
		sujetoTramite.setNumeroRegistroPatronal(registroPatronal);
        log.debug("numeroRegistroPatronal: {}", registroPatronal);
		sujetoTramite = sujetoObligadoService.obtenerSujetoObligadoActividadEconomica(sujetoTramite);
		return  cargarSolicitudSRT( sujetoTramite, session, request, idTipoTramite, idSolicitud, model);
	}
}
