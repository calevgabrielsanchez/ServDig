package mx.gob.imss.ctirss.delta.escritoDesacuerdo.web.controller;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.*;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultaEscritoDesacuerdoServiceRemote;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultalRiesgoTrabajoServiceRemote;
import mx.gob.imss.distss.delta.rtt.service.interfaces.EscritoDesacuerdoBusinessRemote;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
//import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;

@Controller
@RequestMapping("/escrito/wizard")
public class WizardEscritoDesacuerdo extends AbstractController {

	private final String VISTA_INICIO_WIZARD = "inicialEscrito";
	private final String VISTA_INICIO_REGISTRO_VENTANILLA = "inicioEscritoVentanilla";
	private final String VISTA_CONTENIDO_VENTANILLA = "contenidoEscritoVentanilla";
	private final String VIEW_FINALIZADO_VENTANILLA = "finalizacionVentanilla";
	private final String VISTA_CONTENIDO = "contenidoEscrito";
	private final String VISTA_SOL_EXISTENTE = "solicitudEscritoEncontrada";
	private final String KEY_REDITECT_INICIO_VENT = "/escrito/wizard";
	private final String KEY_REDIRECT_INICIAR_TRAM = "/escrito/wizard/iniciarTramite";
	private final String KEY_REDIRECT_SOL_EXISTENTE = "/escrito/wizard/solicitudExistente";

	private final String KEY_PATRON_SES = "patronEscrito";
	private final String KEY_SOLICITUD = "solicitudEscrito";
	private final String KEY_REPORTE = "reporteEscritoDesacuerdo";
	private final String KEY_FOLIO_RECEPCION = "folioRecepcionDesacuerdo";
	private final String KEY_FIRMA_ELECTRONICA = "firmaElectronicaDesacuerdo";
	private final String KEY_RETOMANDO = "retomandoSolicitud";
	private final String KEY_ERROR = "error";
	private final String KEY_CAUSAS = "keyCausasSession";
	private final String KEY_MOTIVOS = "motivosLst";
	private final String KEY_FRACCLASE = "fracClaseLst";
	private final String KEY_ESCRITO = "escritoDes";
	private final String KEY_DOMICILIO = "domicilios";
	private final String COMBO_FRACCION = "cargaFraccion";

	@Autowired
	ConsultalRiesgoTrabajoServiceRemote consultalRiesgoTrabajoServiceRemote;
	@Autowired
	EscritoDesacuerdoBusinessRemote escritoDesacuerdoBusinessRemote;
	@Autowired
	SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	ConsultaEscritoDesacuerdoServiceRemote consultaEscritoDesacuerdoServiceRemote;

	//@EJB(name = "documentoProbatorioServiceBusiness",mappedName = "documentoProbatorioServiceBusiness")
	//private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;

	private static final Logger LOGGER = LoggerFactory.getLogger(WizardEscritoDesacuerdo.class);

	@RequestMapping("")
	public String initEscritoVentanilla(Model model) {

		model.addAttribute("patron", new PatronRiesgosTrabajo());

		return VISTA_INICIO_REGISTRO_VENTANILLA;
	}

	@RequestMapping("/NPIE/{code}")
	public Object initEscritoIDSE(Model model, HttpSession session, @PathVariable String code, HttpServletRequest request) {
		//Desencriptamos la informacion
		Base64 decoder = new Base64();
		byte[] decodedByteArray = (byte[]) decoder.decode(code.getBytes());
		String datos = new String(decodedByteArray);
		String[] tokens = datos.split("\\|");

		return this.iniciarRegistroEscrito(model, session, tokens[2], request);

	}


	@RequestMapping("/{rp}")
	public Object initWizardEscrito(Model model, HttpSession session, @PathVariable String rp, HttpServletRequest request) {

		return this.iniciarRegistroEscrito(model, session, rp, request);

	}

	@RequestMapping(value = "/valida")
	public Object validarEscritoPorRegistroPatronal(@ModelAttribute(value = "patron") PatronRiesgosTrabajo patron, Model model, HttpSession session, HttpServletRequest request) {

		return iniciarRegistroEscrito(model, session, patron.getNrp(), request);
	}

	@RequestMapping("/solicitudExistente")
	public String solicitudExistenteVentanilla(Model model) {


		return VISTA_SOL_EXISTENTE;
	}

	@SuppressWarnings("unchecked")
	private Object iniciarRegistroEscrito(Model model, HttpSession session, String rp, HttpServletRequest request) {

		this.limpiarSession(session);
		Long idOrigenTramite = this.getOrigenApp(request);
		Object vista = VISTA_INICIO_WIZARD;
		String errorPatron = null;
		Solicitud solicitudEscrito = null;
		PatronRiesgosTrabajo patron = null;
		List<CausaDesacuerdo> causas = (List<CausaDesacuerdo>) session.getAttribute(KEY_CAUSAS);

		try{
			List<MotivosDesacuerdo> optMotivos = consultaEscritoDesacuerdoServiceRemote.getMotivosDesacuerdoList(99);
			session.setAttribute(KEY_MOTIVOS, optMotivos);
            LOGGER.debug("Se listan los motivos "+optMotivos);
        }catch(RiesgosTrabajoException e){
            e.printStackTrace();
        }

		try{
			List<MotivosDesacuerdo> fracClase = consultaEscritoDesacuerdoServiceRemote.getFraccionClaseList("1");
			session.setAttribute(KEY_FRACCLASE, fracClase);
			LOGGER.debug("Se listan las fracciones siendo: "+fracClase.size());
		}catch(RiesgosTrabajoException e){
			e.printStackTrace();
		}

		if (causas == null || causas.isEmpty()) {
			causas = escritoDesacuerdoBusinessRemote.getCausasDesacuerdo(2L);
			session.setAttribute(KEY_CAUSAS, causas);
		}

		try {
			patron = consultalRiesgoTrabajoServiceRemote.findPatron(rp, null, null);
			log.debug("El patron tiene el id " + patron.getIdPatronSujetoObligado());
		} catch (RiesgosTrabajoException e) {
			e.printStackTrace();
		}

		if (patron == null) {
			errorPatron = "No existe el registro patronal <strong>" + rp + "</strong>.";
		} else if (!validaCircunscripcionPatron(session, idOrigenTramite, patron)) {
			errorPatron = "Registro patronal no corresponde a la delegaci&oacute;n y/o subdelegaci&oacute;n.";
		}

		if (errorPatron != null) {
			session.setAttribute(KEY_ERROR, errorPatron);
			return new RedirectView(KEY_REDITECT_INICIO_VENT, true);
		}

		solicitudEscrito = escritoDesacuerdoBusinessRemote.validarTramiteExistente(patron.getIdPatronSujetoObligado());

		session.setAttribute(KEY_PATRON_SES, patron);
		session.setAttribute(KEY_SOLICITUD, solicitudEscrito);
		session.setAttribute(KEY_RETOMANDO, solicitudEscrito != null);

		if (idOrigenTramite.equals(OrigenSolicitudEnum.VENTANILLA.getId())) {
			vista = solicitudEscrito != null ? new RedirectView(KEY_REDIRECT_SOL_EXISTENTE, true) : new RedirectView(KEY_REDIRECT_INICIAR_TRAM, true);
		}

		return vista;
	}

	private boolean validaCircunscripcionPatron(HttpSession session, Long idOrigenTramite, PatronRiesgosTrabajo patron) {
		Boolean resultado = true;
		if (idOrigenTramite.equals(OrigenSolicitudEnum.VENTANILLA.getId())) {

			Usuario usuario = (Usuario) session.getAttribute("usuario");
			Long idPerfilUsuario = usuario.getPerfilUsuario().getIdPerfilUsuario();
			Long idCircunscripcionUsuario = null;
			Long idCircunscripcionPatron = null;
			log.debug("El perfil de usuario es " + idPerfilUsuario);
			if (idPerfilUsuario.equals(2L)) {//validamos a nivel delegacion
				idCircunscripcionUsuario = usuario.getUsuarioFuncionario().getDelegacion().getId();
				idCircunscripcionPatron = patron.getSubdelegacion().getDelegacion().getId();
			} else if (idPerfilUsuario.equals(3L)) {//validamos a nivel subdelegacion
				idCircunscripcionUsuario = usuario.getUsuarioFuncionario().getSubdelegacion().getId();
				idCircunscripcionPatron = patron.getSubdelegacion().getId();
			}

			log.debug("La circunscripcion del usuario es " + idCircunscripcionUsuario + " y la del patron es " + idCircunscripcionPatron);

			//si el perfil no es central y no corresponde la circunscripcion retornamos false
			if (!idPerfilUsuario.equals(1L) && !idCircunscripcionPatron.equals(idCircunscripcionUsuario)) {
				resultado = false;
			}

		}

		return resultado;

	}

	@RequestMapping(value = "/iniciarTramite")
	public String iniciarTramiteWizardEscrito(Model model, HttpSession session, HttpServletRequest request) {

		String vista = null;
		Long idOrigenSolicitud = this.getOrigenApp(request);
		Boolean retomandoSolicitud = (Boolean) session.getAttribute(KEY_RETOMANDO);
		Solicitud solicitudEscrito = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute(KEY_PATRON_SES);

        try{
			List<MotivosDesacuerdo> optMotivos = consultaEscritoDesacuerdoServiceRemote.getMotivosDesacuerdoList(99);
			session.setAttribute(KEY_MOTIVOS, optMotivos);
			LOGGER.debug("Se listan los motivos "+optMotivos);
		}catch(RiesgosTrabajoException e){
			e.printStackTrace();
		}

		try{
			List<MotivosDesacuerdo> fracClase = consultaEscritoDesacuerdoServiceRemote.getFraccionClaseList("1");
			session.setAttribute(KEY_FRACCLASE, fracClase);
			LOGGER.debug("Se listan las fracciones siendo: "+fracClase.size());
		}catch(RiesgosTrabajoException e){
			e.printStackTrace();
		}

		request.setAttribute("idOrigenPeticion", idOrigenSolicitud);
		vista = idOrigenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA.getId()) ? VISTA_CONTENIDO_VENTANILLA : VISTA_CONTENIDO;

		if (!retomandoSolicitud) {
			solicitudEscrito = this.crearObjetoSolicitud(session, request);
			try {
				solicitudEscrito = escritoDesacuerdoBusinessRemote.crearSolicitudEscrito(solicitudEscrito, false);
				log.debug("Se crea la solicitud");
				session.setAttribute(KEY_SOLICITUD, solicitudEscrito);
			} catch (SolicitudNoValidaException e) {
				e.printStackTrace();
			} catch (SolicitudNoEncontradaException e) {
				e.printStackTrace();
			}
		}

		if (idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())) {
			request.setAttribute("cadenaOriginal", this.generarCadenaOriginal(patron.getNrp(), patron.getRazonSocial(), solicitudEscrito.getNoFolioSolicitud()));
		}

		request.setAttribute("idTramite", this.getDesacuerdoFromSolicitud(solicitudEscrito).getTramiteId());


		return vista;
	}

	private Solicitud crearObjetoSolicitud(HttpSession session, HttpServletRequest request) {
		Solicitud solicitudCreada = new Solicitud();
		PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute(KEY_PATRON_SES);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		solicitudCreada.setOrigenSolicitud(new OrigenSolicitud());
		solicitudCreada.getOrigenSolicitud().setIdOrigenSolicitud(this.getOrigenApp(request));
		solicitudCreada.setSolicitante(usuario);

		TramiteEscritoDesacuerdo escrito = new TramiteEscritoDesacuerdo();
		escrito.setPatron(patron);
		//escrito.setMotivosDesacuerdo(new MotivosDesacuerdo(1L, null));
		//escrito.setCausaDesacuerdo(new CausaDesacuerdo(3L, null, new MateriaDesacuerdo(2L)));

        escrito.setMotivosDesacuerdo(new MotivosDesacuerdo(null, null));
        escrito.setCausaDesacuerdo(new CausaDesacuerdo(null, null, new MateriaDesacuerdo()));
		solicitudCreada.setTramites(new ArrayList<Tramite>());
		solicitudCreada.getTramites().add(escrito);

		return solicitudCreada;
	}

	@RequestMapping("/finalizarTramite")
	@ResponseBody
	public Map<String, Object> finalizarTramite(@RequestBody TramiteEscritoDesacuerdo escrito, HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Long idOrigenSolicitud = this.getOrigenApp(request);
		Boolean correcto = false;
		String folioRecepcion = null;
		byte[] reporteRecepcion = null;
		String mensaje = "OK" ;

		LOGGER.debug("Objeto escrito " + escrito);

		Solicitud solicitudCreada = this.procesarCambios(session, escrito);
		TramiteEscritoDesacuerdo duplicado = new TramiteEscritoDesacuerdo();
		try {
			duplicado = consultaEscritoDesacuerdoServiceRemote.getTramoDuplicado(escrito.getFolioImpugnado(), escrito.getAnVigencia());
		}catch(RiesgosTrabajoException e){
			e.printStackTrace();
		}


		if(duplicado == null){

		try {
            //idOrigenSolicitud = 1L;
			if (idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId()) && solicitudCreada.getFirmaElectronica() == null) {
            //if ("1" != "1") {
				mensaje = "No haz firmado la solicitud" ;
			} else {
				solicitudCreada = escritoDesacuerdoBusinessRemote.finalizarSolicitudEscrito(solicitudCreada);
				folioRecepcion = this.getDesacuerdoFromSolicitud(solicitudCreada).getFolioRecepcion();

				try {
					reporteRecepcion = escritoDesacuerdoBusinessRemote.generarAcuseEscritoDesacuerdoPDF(solicitudCreada);
					session.setAttribute(KEY_REPORTE, reporteRecepcion);
				} catch (RiesgosTrabajoException e) {
					e.printStackTrace();
				}

				session.setAttribute(KEY_FOLIO_RECEPCION, folioRecepcion);
				session.setAttribute(KEY_SOLICITUD, solicitudCreada);
				TramiteEscritoDesacuerdo getTramiteEsDes = (TramiteEscritoDesacuerdo) solicitudCreada.getTramites().get(0);

				session.setAttribute(KEY_ESCRITO, getTramiteEsDes);
				result.put("traEscDes", getTramiteEsDes);
				correcto = true;
			}
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mensaje = e.getMessage();
		}
	}else{
			mensaje = "No es posible registrar dos Escritos de Desacuerdo en contra del mismo acto";
		}

		result.put("correcto", correcto);
		result.put("mensaje", mensaje);
		result.put("solicitud", solicitudCreada);

		return result;
	}


	@RequestMapping("/registraDomEscrito")
	@ResponseBody
	public Map<String, Object> registraDomEscrito(@RequestBody DomicilioEscritoDesacuerdo domEscrito, HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean correcto = false;
		String mensaje = "OK";

		PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute(KEY_PATRON_SES);
		LOGGER.debug("Muestra el domicilio a guardar "+ domEscrito.getIdDomEscrito());

		try {
			if(domEscrito != null && domEscrito.getIdDomEscrito() != null){
				domEscrito.setCveIdTipoDomicilio(patron.getTipoPersona());

				LOGGER.debug("Se consulta si existen domicilio");
				if(consultaEscritoDesacuerdoServiceRemote.findDomEscritoDesacuerdo(domEscrito.getIdDomEscrito()) != null){
					domEscrito.setFecactualiza(new Date());
					LOGGER.debug("Se Actualizan el domicilio");
					correcto = consultaEscritoDesacuerdoServiceRemote.saveDomEscritoDes(domEscrito, 2);
				}else {
					LOGGER.debug("Se Ingresa nuevo domicilio");
					domEscrito.setFechAlta(new Date());
					domEscrito.setFecactualiza(new Date());
					correcto =  consultaEscritoDesacuerdoServiceRemote.saveDomEscritoDes(domEscrito, 1);
				}

				if (correcto){
					mensaje = "El domicilio se ha guardado con exito";
				}
			}
		} catch (RiesgosTrabajoException e) {
			e.printStackTrace();
			mensaje = "Ocurrió un error al intentar guardar el domicilio";
		}

		result.put("correcto", correcto);
		result.put("mensaje", mensaje);

		return result;
	}

	@RequestMapping("/findDomEscrito")
	@ResponseBody
	public Map<String, Object> findDomEscrito(@RequestBody DomicilioEscritoDesacuerdo domEscrito, HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean correcto = false;
		String mensaje = "OK";

		DomicilioEscritoDesacuerdo domEscritoDesEncon = new DomicilioEscritoDesacuerdo();
		try {
			if(domEscrito != null && domEscrito.getIdDomEscrito() != null){
				LOGGER.debug("Se consulta si existen domicilio");
				domEscritoDesEncon = consultaEscritoDesacuerdoServiceRemote.findDomEscritoDesacuerdo(Long.valueOf(domEscrito.getIdDomEscrito()));
				correcto = true;
			}
		} catch (RiesgosTrabajoException e) {
			e.printStackTrace();
			mensaje = "Ocurrió un error al buscar el domicilio";
		}

		result.put("correcto", correcto);
		result.put("mensaje", mensaje);
		result.put(KEY_DOMICILIO, domEscritoDesEncon);

		return result;
	}


	@RequestMapping("/tramiteFinalizado")
	public String tramiteFinalizado(Model model, HttpSession session, HttpServletRequest request) {

		return VIEW_FINALIZADO_VENTANILLA;
	}

	private TramiteEscritoDesacuerdo getDesacuerdoFromSolicitud(Solicitud solicitud) {
		TramiteEscritoDesacuerdo desacuerdo = null;
		if (solicitud != null && solicitud.getTramites() != null) {
			for (Tramite tramite : solicitud.getTramites()) {
				if (tramite instanceof TramiteEscritoDesacuerdo) {
					desacuerdo = (TramiteEscritoDesacuerdo) tramite;
					break;
				}
			}
		}
		return desacuerdo;
	}

	@RequestMapping(value = "/getComprobante", method = RequestMethod.POST)
	public void getComprobanteDesacuerdo(HttpSession session, HttpServletResponse response) {
		//ontenemos de la session el documento que necesitamos
		byte[] documento = (byte[]) session.getAttribute(KEY_REPORTE);
		//dependiendo de la manera ponemos el nombre del reporte
		String name = "escritoDesacuerdo.pdf";
		//preparamos los encabezados
		try {
			response.addHeader("Accept-Ranges", "bytes");
			response.addHeader("Cache-Control", "public");
			response.addHeader("Cache-Control", "must-revalidate");
			response.addHeader("Pragma", "public");
			response.addHeader("expires", "0");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename = " + name);
			response.getOutputStream().write(documento);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/limpiarSession", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> limpiarSession(HttpSession session) {

		log.debug("Entro a limpiar la session");

		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_PATRON_SES);
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_REPORTE);
		session.removeAttribute(KEY_FOLIO_RECEPCION);
		session.removeAttribute(KEY_ERROR);

		return null;
	}

	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
																   HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}

	@RequestMapping(value = "/guardarTramite", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarTramiteEscrito(@RequestBody TramiteEscritoDesacuerdo guardar, HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();
		Boolean correcto = false;
		String mensaje = "No fue posible guardar la solicitud.";
		Solicitud solicitudEscrito = this.procesarCambios(session, guardar);

		log.debug("Lo que se guarda del trámite" + guardar);

		try {
			solicitudBusinessRemote.actualizarXmlTramite(this.getDesacuerdoFromSolicitud(solicitudEscrito));
			correcto = true;
			mensaje = "La solicitud ha sido guardada correctamente.";
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		result.put("correcto", correcto);
		result.put("mensaje", mensaje);

		return result;
	}

	@RequestMapping(value = "/cancelarTramite", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> cancelarTramiteEscrito(@RequestBody Solicitud solicitud, HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudA = (Solicitud) session.getAttribute(KEY_SOLICITUD);

		if (solicitud.getNoFolioSolicitud().equals(solicitudA.getNoFolioSolicitud())) {
			result = this.cancelarsolicitud(solicitudA, "Solicitud cancelada a peticion del usuario");
			result.put("mensaje", "La solicitud con folio " + solicitudA + " ha sido cancelada.");
			session.removeAttribute(KEY_SOLICITUD);
		} else {
			result.put("correcto", false);
			result.put("mensaje", "El id de la solicitud a cancelar no coincide con la que se tiene en session");
		}

		return result;
	}

	@RequestMapping(value = "/retomar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> retomarTramiteEscrito(HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudCreada = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		result.put("tramite", this.getDesacuerdoFromSolicitud(solicitudCreada));

		return result;
	}

	private Map<String, Object> cancelarsolicitud(Solicitud solicitud, String observaciones) {

		Map<String, Object> result = new HashMap<String, Object>();
		if (solicitud != null) {
			try {
				solicitudBusinessRemote.cancelarSolicitud(solicitud.getSolicitudId(), 5L, 1L, null, observaciones);
				result.put("correcto", true);
				result.put("mensaje", "La solicitud fue cancelada correctamente");
			} catch (SolicitudException e) {
				this.log.error(e);
				result.put("correcto", false);
				result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
			}

			solicitud.setSolicitante(null);
			result.put("solicitud", solicitud);
		} else {
			log.debug("No existia la solicitud");
		}

		return result;
	}

	private String generarCadenaOriginal(String registroPatronal, String nombreRS, String folioSolicitud) {
		StringBuffer contenidoAFirmar = new StringBuffer();
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");
		contenidoAFirmar.append("Tipo de tramite: ESCRITO DE DESACUERDO").append("|");
		contenidoAFirmar.append("Fecha del tramite : ").append((new SimpleDateFormat("dd 'de' MMMM yyyy',' HH:mm:ss", new Locale("es", "MX"))).format(new Date())).append("|");
		contenidoAFirmar.append("Folio: ").append(folioSolicitud).append("|");
		contenidoAFirmar.append("Nombre o Razon Social: ").append(nombreRS).append("|");
		contenidoAFirmar.append("Numero Registro Patronal: ").append(registroPatronal).append("||");
		return contenidoAFirmar.toString();
	}


	private Long getOrigenApp(HttpServletRequest request) {
		return new Long(request.getSession().getServletContext().getInitParameter("ID_ORIGEN_APP"));
	}

	private Solicitud procesarCambios(HttpSession session, TramiteEscritoDesacuerdo escrito) {

		PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute(KEY_PATRON_SES);
		Solicitud solicitudCreada = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		FirmaElectronica firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);

		//seteamos el patron en session
		TramiteEscritoDesacuerdo escritoXML = this.getDesacuerdoFromSolicitud(solicitudCreada);
		escritoXML.setCausaDesacuerdo(escrito.getCausaDesacuerdo());
		escritoXML.setFolioImpugnado(escrito.getFolioImpugnado());
		escritoXML.setMail(escrito.getMail());
		escritoXML.setMotivosDesacuerdo(escrito.getMotivosDesacuerdo());
		escritoXML.setPatron(patron);
		escritoXML.setAnVigencia(escrito.getAnVigencia());
		escritoXML.setClaseAnterior(escrito.getClaseAnterior());
		escritoXML.setFracAnterior(escrito.getFracAnterior());
		escritoXML.setPrimAnterior(escrito.getPrimAnterior());
		escritoXML.setTrabajadorProm(escrito.getTrabajadorProm());
		escritoXML.setFechNotRes(escrito.getFechNotRes());

        escritoXML.setMotivoDesacuerdo(escrito.getMotivoDesacuerdo());
        escritoXML.setMotivoDesacuerdo1(escrito.getMotivoDesacuerdo1());
        escritoXML.setMotivoDesacuerdo2(escrito.getMotivoDesacuerdo2());
        escritoXML.setMotivoDesacuerdo3(escrito.getMotivoDesacuerdo3());
        escritoXML.setMotivoDesacuerdo4(escrito.getMotivoDesacuerdo4());
        escritoXML.setMotivoDesacuerdo5(escrito.getMotivoDesacuerdo5());
        escritoXML.setMotivoDesacuerdo6(escrito.getMotivoDesacuerdo6());
        escritoXML.setMotivoDesacuerdo7(escrito.getMotivoDesacuerdo7());
        escritoXML.setMotivoDesacuerdo8(escrito.getMotivoDesacuerdo8());
        escritoXML.setMotivoDesacuerdo9(escrito.getMotivoDesacuerdo9());

		solicitudCreada.setTramites(new ArrayList<Tramite>());
		solicitudCreada.getTramites().add(escritoXML);
		solicitudCreada.setFirmaElectronica(firma);

		return solicitudCreada;
	}

	private boolean validarFolioImpugnado(String folioImpugnado) {
		String REG_EX_FOLIO_RECEPCION = "ED\\-L\\-\\d{4}\\-\\d{4}\\/\\d{2}";
		//boolean respuesta = true;
		boolean respuesta = false;

		/*if (StringUtils.isBlank(folioImpugnado)) {
			RiesgosTrabajoException.throwException(1, "El folio de recepcion no puede ser nulo o vacio");
		}

		if (folioRecepcion.length() != 17) {
			RiesgosTrabajoException.throwException(1, "El folio de recepcion debe ser de 17 posiciones");
		}

		if (!Pattern.matches(REG_EX_FOLIO_RECEPCION, folioImpugnado)) {
			RiesgosTrabajoException.throwException(1, "El formato del folio de recepcion es incorrecto");
		}

		TramiteEscritoDesacuerdo tramiteEscrito = consultaEscritoDesacuerdoLocal.getEscritoSimple(folioImpugnado);

		if (tramiteEscrito == null || tramiteEscrito.isEmpty()) respuesta = false;*/

		return respuesta;
	}

    @RequestMapping("/tramiteDuplicado")
    @ResponseBody
    public Map<String, Object> tramiteDuplicado(@RequestBody TramiteEscritoDesacuerdo escrito, HttpSession session, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Boolean correcto = false;
        String mensaje = "OK" ;

        TramiteEscritoDesacuerdo duplicado = new TramiteEscritoDesacuerdo();
        try {
            duplicado = consultaEscritoDesacuerdoServiceRemote.getTramoDuplicado(escrito.getFolioImpugnado(), escrito.getAnVigencia());
        }catch(RiesgosTrabajoException e){
            e.printStackTrace();
        }

        if(duplicado == null){
            correcto = true;
        }else{
            mensaje = "No es posible registrar dos Escritos de Desacuerdo en contra del mismo acto";
        }
        result.put("correcto", correcto);
        result.put("mensaje", mensaje);

        return result;
    }

	@RequestMapping(value = COMBO_FRACCION, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cargarComboMunicipios(@RequestParam("clase") String clase, HttpSession session) {
		LOGGER.info("Se buscan las fracciones para mostrar");
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			List<MotivosDesacuerdo> fracClase = consultaEscritoDesacuerdoServiceRemote.getFraccionClaseList(clase);
			result.put(KEY_FRACCLASE, fracClase);
			LOGGER.debug("Se listan las fracciones siendo: "+fracClase.size());
		}catch(RiesgosTrabajoException e){
			e.printStackTrace();
			LOGGER.error("Ocurrio un error al realizar la consulta:", e);
			result.put("mensajeError", "Ocurrio un error al obtener los datos del cat&aactue;logo< Fracci&oactue;n Clase.");
		}

		return result;
	}



}
