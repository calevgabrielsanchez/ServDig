package mx.gob.imss.ctirss.delta.riesgosTrabajo.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.CausaDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.MateriaDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultalRiesgoTrabajoServiceRemote;
import mx.gob.imss.distss.delta.rtt.service.interfaces.EscritoDesacuerdoBusinessRemote;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/escrito/wizard")
public class WizardEscritoDesacuerdo extends AbstractController {
	
	private final String VISTA_INICIO_WIZARD = "inicialEscrito";
	private final String VISTA_INICIO_REGISTRO_VENTANILLA = "inicioEscritoVentanilla";
	private final String VISTA_CONTENIDO_VENTANILLA = "contenidoEscritoVentanilla";
	private final String VIEW_FINALIZADO_VENTANILLA = "finalizacionVentanilla";
	private final String VISTA_CONTENIDO = "contenidoEscrito";
	private final String VISTA_SOL_EXISTENTE="solicitudEscritoEncontrada";
	private final String KEY_REDITECT_INICIO_VENT="/escrito/wizard";
	private final String KEY_REDIRECT_INICIAR_TRAM = "/escrito/wizard/iniciarTramite";
	private final String KEY_REDIRECT_SOL_EXISTENTE = "/escrito/wizard/solicitudExistente";
	
	private final String KEY_PATRON_SES= "patronEscrito";
	private final String KEY_SOLICITUD = "solicitudEscrito";
	private final String KEY_REPORTE = "reporteEscritoDesacuerdo";
	private final String KEY_FOLIO_RECEPCION = "folioRecepcionDesacuerdo";
	private final String KEY_FIRMA_ELECTRONICA= "firmaElectronicaDesacuerdo";
	private final String KEY_RETOMANDO = "retomandoSolicitud";
	private final String KEY_ERROR = "error";
	private final String KEY_CAUSAS = "keyCausasSession";
	
	@Autowired
	ConsultalRiesgoTrabajoServiceRemote consultalRiesgoTrabajoServiceRemote;
	@Autowired
	EscritoDesacuerdoBusinessRemote escritoDesacuerdoBusinessRemote;
	@Autowired
	SolicitudBusinessRemote solicitudBusinessRemote;
	
	@RequestMapping("")
	public String initEscritoVentanilla(Model model) {
		
		model.addAttribute("patron", new PatronRiesgosTrabajo());
	
		return VISTA_INICIO_REGISTRO_VENTANILLA;
	}

	@RequestMapping("/NPIE/{code}")
	public Object initEscritoIDSE(Model model,HttpSession session, @PathVariable String code, HttpServletRequest request) {
		//Desencriptamos la informacion
		Base64 decoder = new Base64();
        byte[] decodedByteArray = (byte[]) decoder.decode(code.getBytes());
        String datos = new String(decodedByteArray);
        String[] tokens = datos.split("\\|");
        
		return this.iniciarRegistroEscrito(model, session, tokens[2], request);
		
	}
	
	
	@RequestMapping("/{rp}")
	public Object initWizardEscrito(Model model,HttpSession session, @PathVariable String rp, HttpServletRequest request) {
		
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
	private Object iniciarRegistroEscrito(Model model,HttpSession session, String rp, HttpServletRequest request) {
		
		this.limpiarSession(session);
		Long idOrigenTramite = this.getOrigenApp(request);
		Object vista = VISTA_INICIO_WIZARD;
		String errorPatron = null;
		Solicitud solicitudEscrito = null;
		PatronRiesgosTrabajo patron = null;
		List<CausaDesacuerdo> causas = (List<CausaDesacuerdo>) session.getAttribute(KEY_CAUSAS);
		
		if(causas == null || causas.isEmpty()) {
			causas = escritoDesacuerdoBusinessRemote.getCausasDesacuerdo(2L);
			session.setAttribute(KEY_CAUSAS, causas);
		}
		
		try {
			patron = consultalRiesgoTrabajoServiceRemote.findPatron(rp, null, null);
			log.debug("El patron tiene el id " + patron.getIdPatronSujetoObligado());
		} catch (RiesgosTrabajoException e) {
			e.printStackTrace();
		}
		
		if(patron == null) {
			errorPatron = "No existe el registro patronal <strong>"+rp+"</strong>.";
		} else if(!validaCircunscripcionPatron(session, idOrigenTramite, patron)) {
			errorPatron = "Registro patronal no corresponde a la delegaci&oacute;n y/o subdelegaci&oacute;n.";
		}
		
		if(errorPatron != null) {
			session.setAttribute(KEY_ERROR, errorPatron);
			return new RedirectView(KEY_REDITECT_INICIO_VENT, true);
		}
		
		solicitudEscrito = escritoDesacuerdoBusinessRemote.validarTramiteExistente(patron.getIdPatronSujetoObligado());
		
		session.setAttribute(KEY_PATRON_SES, patron);
		session.setAttribute(KEY_SOLICITUD, solicitudEscrito);
		session.setAttribute(KEY_RETOMANDO, solicitudEscrito != null);
		
		if(idOrigenTramite.equals(OrigenSolicitudEnum.VENTANILLA.getId())) {
			vista = solicitudEscrito != null ? new RedirectView(KEY_REDIRECT_SOL_EXISTENTE, true) : new RedirectView(KEY_REDIRECT_INICIAR_TRAM, true);
		}
		
		return vista;
	}
	
	private boolean validaCircunscripcionPatron(HttpSession session, Long idOrigenTramite, PatronRiesgosTrabajo patron) {
		Boolean resultado = true;
		if(idOrigenTramite.equals(OrigenSolicitudEnum.VENTANILLA.getId())) {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			Long idPerfilUsuario = usuario.getPerfilUsuario().getIdPerfilUsuario();
			Long idCircunscripcionUsuario = null;
			Long idCircunscripcionPatron = null;
			log.debug("El perfil de usuario es " + idPerfilUsuario);
			if(idPerfilUsuario.equals(2L)) {//validamos a nivel delegacion
				idCircunscripcionUsuario = usuario.getUsuarioFuncionario().getDelegacion().getId();
				idCircunscripcionPatron = patron.getSubdelegacion().getDelegacion().getId();
			} else if(idPerfilUsuario.equals(3L)) {//validamos a nivel subdelegacion
				idCircunscripcionUsuario = usuario.getUsuarioFuncionario().getSubdelegacion().getId();
				idCircunscripcionPatron = patron.getSubdelegacion().getId();
			}
			
			log.debug("La circunscripcion del usuario es " + idCircunscripcionUsuario +  " y la del patron es " + idCircunscripcionPatron);
			
			//si el perfil no es central y no corresponde la circunscripcion retornamos false
			if(!idPerfilUsuario.equals(1L) && !idCircunscripcionPatron.equals(idCircunscripcionUsuario)){
				resultado = false;
			}
			
		}
		
		return resultado;
		
	}
	
	@RequestMapping(value = "/iniciarTramite")
	public String iniciarTramiteWizardEscrito(Model model,HttpSession session, HttpServletRequest request) {
		
		String vista = null;
		Long idOrigenSolicitud = this.getOrigenApp(request);
		Boolean retomandoSolicitud = (Boolean) session.getAttribute(KEY_RETOMANDO);
		Solicitud solicitudEscrito = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute(KEY_PATRON_SES);
		
		request.setAttribute("idOrigenPeticion", idOrigenSolicitud);
		vista = idOrigenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA.getId()) ? VISTA_CONTENIDO_VENTANILLA : VISTA_CONTENIDO;
		
		if(!retomandoSolicitud) {
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
		
		if(idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())) {
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
		escrito.setCapturaMotivo(1);
		escrito.setCausaDesacuerdo(new CausaDesacuerdo(3L, null, new MateriaDesacuerdo(2L)));
		
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
		String mensaje = "OK";
		Solicitud solicitudCreada = this.procesarCambios(session, escrito);
		
		try {
			if(idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId()) && solicitudCreada.getFirmaElectronica() == null) {
				mensaje = "No haz firmado la solicitud";
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
		
		result.put("correcto", correcto);
		result.put("mensaje", mensaje);
		result.put("solicitud", solicitudCreada);
		
		return result;
	}
	
	@RequestMapping("/tramiteFinalizado")
	public String tramiteFinalizado(Model model,HttpSession session, HttpServletRequest request) {
		
		return VIEW_FINALIZADO_VENTANILLA;
	}
	
	private TramiteEscritoDesacuerdo getDesacuerdoFromSolicitud(Solicitud solicitud) {
		
		
		TramiteEscritoDesacuerdo desacuerdo = null;
		if(solicitud != null && solicitud.getTramites() != null) {
			for(Tramite tramite: solicitud.getTramites()) {
				if(tramite instanceof TramiteEscritoDesacuerdo) {
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
		byte[] documento = (byte[])session.getAttribute(KEY_REPORTE);
		//dependiendo de la manera ponemos el nombre del reporte
		String name = "escritoDesacuerdo.pdf";
		//preparamos los encabezados
		try {
			response.addHeader("Accept-Ranges","bytes");
			response.addHeader("Cache-Control","public");
			response.addHeader("Cache-Control","must-revalidate");
			response.addHeader("Pragma","public");
			response.addHeader("expires","0");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename = "+name);
			response.getOutputStream().write(documento);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/limpiarSession", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session){
		
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
	public @ResponseBody Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}
	
	@RequestMapping(value ="/guardarTramite", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> guardarTramiteEscrito(@RequestBody TramiteEscritoDesacuerdo guardar, HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();
		Boolean correcto = false;
		String mensaje = "No fue posible guardar la solicitud.";
		Solicitud solicitudEscrito = this.procesarCambios(session, guardar);
		
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
	
	@RequestMapping(value ="/cancelarTramite", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> cancelarTramiteEscrito(@RequestBody Solicitud solicitud, HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudA = (Solicitud) session.getAttribute(KEY_SOLICITUD);

		if(solicitud.getNoFolioSolicitud().equals(solicitudA.getNoFolioSolicitud())) {
			result = this.cancelarsolicitud(solicitudA, "Solicitud cancelada a peticion del usuario");
			result.put("mensaje","La solicitud con folio "+solicitudA+" ha sido cancelada.");
			session.removeAttribute(KEY_SOLICITUD);
		} else {
			result.put("correcto", false);
			result.put("mensaje", "El id de la solicitud a cancelar no coincide con la que se tiene en session");
		}
		
		return result;
	}
	
	@RequestMapping(value ="/retomar", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> retomarTramiteEscrito(HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudCreada = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		result.put("tramite", this.getDesacuerdoFromSolicitud(solicitudCreada));
		
		return result;
	}
	
	private Map<String, Object> cancelarsolicitud(Solicitud solicitud, String observaciones) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(solicitud != null) {
			try {
				solicitudBusinessRemote.cancelarSolicitud(solicitud.getSolicitudId(),5L,1L,null, observaciones);
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
		Solicitud solicitudCreada = (Solicitud)session.getAttribute(KEY_SOLICITUD);
		FirmaElectronica firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);

		//seteamos el patron en session
		TramiteEscritoDesacuerdo escritoXML = this.getDesacuerdoFromSolicitud(solicitudCreada);
		escritoXML.setCausaDesacuerdo(escrito.getCausaDesacuerdo());
		escritoXML.setFolioImpugnado(escrito.getFolioImpugnado());
		escritoXML.setMail(escrito.getMail());
		escritoXML.setCapturaMotivo(escrito.getCapturaMotivo());
		escritoXML.setMotivoDesacuerdo(escrito.getMotivoDesacuerdo());
		escritoXML.setPatron(patron);

		solicitudCreada.setTramites(new ArrayList<Tramite>());
		solicitudCreada.getTramites().add(escritoXML);
		solicitudCreada.setFirmaElectronica(firma);
		
		return solicitudCreada;
	}
}
