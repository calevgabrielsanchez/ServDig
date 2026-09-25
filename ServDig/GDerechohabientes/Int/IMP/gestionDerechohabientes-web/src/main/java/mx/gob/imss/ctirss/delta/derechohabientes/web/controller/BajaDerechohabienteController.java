
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto.RechazoTramiteDto;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto.TramiteDto;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabientes.PropiedadesDocumento;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Controller
@RequestMapping( value = "/derechohabiente/baja/*")
public class BajaDerechohabienteController extends AbstractController{

	@Autowired
	private BajaDerechohabienteServiceRemote bajaDerechohabienteServiceRemote;
	@Autowired
	private DerechohabienteServiceRemote derechohabienteServiceRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private SolicitudServiceRemote solicitudServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private TramiteDocumentosServiceRemote tramiteDocumentosService;
	@Autowired
	private EmailServiceRemote emailServiceRemote;
	@Autowired
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@Autowired
	private GuardaDocumentosAsincrono guardaDocumentosAsincrono;
	
	@Autowired
	private FileUploadController fileUploadController;
	
	private static final String KEY_SOLICITUD = "solicitud";
	private static final String KEY_INTEGRANTE_BAJA = "personaADarDeBajaSession";
	private static final String KEY_REQUIERE_DOCS = "requiereDocs";
	
	
	/**
	 * Metodo para iniciar el tramite de baja de derechohabiente por defuncion
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/administrativa/home")
	public String bajaAdministrativaHome(HttpSession session, HttpServletRequest request, Model model) {
		
		Long perfil = 0L;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		if(usuario!=null){
			perfil = usuario.getPerfilUsuario().getIdPerfilUsuario();
		}
		
		
		
		List<GrupoFamiliar> candidatos = null;
		
		try {
			candidatos = bajaDerechohabienteServiceRemote.findGrupoFamiliarBajaAdministrativa(asignacionNSS.getIdAsignacionNSS(), usuario);
		} catch (DerechohabientesBusinessException e) {
			log.error("", e);
			request.setAttribute("errores", e.getSituacion());
		}
		model.addAttribute("candidatos", candidatos);
		//Agregamos el tipo de tramite y el perfil para mostrar la guia de tramite correpondiente
		request.setAttribute("tipoTramite", TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION.getCodigo().longValue());
		request.setAttribute("perfil", perfil);
		
		return Constants.LISTA_BAJA_ADMINISTRATIVA_FORDWARD;
	}
	
	/**
	 * Metodo para iniciar el tramite de baja de derechohabiente por defuncion
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/defuncion/home")
	public String bajaDefuncionHome(HttpSession session, HttpServletRequest request, Model model) {
		
		Long perfil = 0L;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		if(usuario!=null){
			perfil = usuario.getPerfilUsuario().getIdPerfilUsuario();
		}
		
		
		
		List<GrupoFamiliar> candidatos = null;
		
		try {
			candidatos = bajaDerechohabienteServiceRemote.findGrupoFamiliarBajaDefuncion(asignacionNSS.getIdAsignacionNSS(),OrigenSolicitudEnum.VENTANILLA.getId(),usuario);
		} catch (DerechohabientesBusinessException e) {
			log.error("", e);
			request.setAttribute("errores", e.getSituacion());
		}
		model.addAttribute("candidatos", candidatos);
		//Agregamos el tipo de tramite y el perfil para mostrar la guia de tramite correpondiente
		request.setAttribute("tipoTramite", TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION.getCodigo().longValue());
		request.setAttribute("perfil", perfil);
		
		return Constants.LISTA_BAJA_DEFUNCION_FORDWARD;
	}
	
	/**
	 * Metodo para iniciar el tramite de baja de derechohabiente por divorcio
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/divorcio/home")
	public String bajaDivorcioHome(HttpSession session, HttpServletRequest request, Model model) {
		
		Long perfil = 0L;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		if(usuario!=null){
			perfil = usuario.getPerfilUsuario().getIdPerfilUsuario();
		}
		
		
		List<GrupoFamiliar> candidatos = null;
		
		try {
			candidatos = bajaDerechohabienteServiceRemote.findGrupoFamiliarBajaDivorcio(asignacionNSS.getIdAsignacionNSS(),OrigenSolicitudEnum.VENTANILLA.getId(),usuario);
			request.setAttribute("idPersona", candidatos.get(0).getDerechohabiente().getIdPersona());
			request.setAttribute("hijo", candidatos.get(0));
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getSituacion());
			log.error("", e);
			model.addAttribute("candidatos", candidatos);
			//Agregamos el tipo de tramite y el perfil para mostrar la guia de tramite correpondiente
			request.setAttribute("tipoTramite", TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO.getCodigo().longValue());
			request.setAttribute("perfil", perfil);
		}
		
		
		/*model.addAttribute("candidatos", candidatos);
		
		//Agregamos el tipo de tramite y el perfil para mostrar la guia de tramite correpondiente
		request.setAttribute("tipoTramite", TipoTramiteEnum.BAJA_CONCUBINATO.getCodigo().longValue());
		request.setAttribute("perfil", perfil);*/
		
		//return Constants.LISTA_BAJA_DIVORCIO_FORDWARD;
		return Constants.INICIO_BAJA_DIVORCIO;
	}
	
	/**
	 * Metodo para iniciar el tramite de baja de derechohabiente por termino de concubinato
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/concubinato/home")
	public String bajaConcubinatoHome(HttpSession session, HttpServletRequest request, Model model) {
		
		Long perfil = 0L;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		if(usuario!=null){
			perfil = usuario.getPerfilUsuario().getIdPerfilUsuario();
		}
		
		
		List<GrupoFamiliar> candidatos = null;
		
		try {
			candidatos = bajaDerechohabienteServiceRemote.findGrupoFamiliarBajaConcubinato(asignacionNSS.getIdAsignacionNSS(), OrigenSolicitudEnum.VENTANILLA.getId(),usuario);
			request.setAttribute("idPersona", candidatos.get(0).getDerechohabiente().getIdPersona());
			request.setAttribute("hijo", candidatos.get(0));
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getSituacion());
			log.error("", e);
			model.addAttribute("candidatos", candidatos);
			//Agregamos el tipo de tramite y el perfil para mostrar la guia de tramite correpondiente
			request.setAttribute("tipoTramite", TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO.getCodigo().longValue());
			request.setAttribute("perfil", perfil);
		}
		
		
		/*model.addAttribute("candidatos", candidatos);
		
		//Agregamos el tipo de tramite y el perfil para mostrar la guia de tramite correpondiente
		request.setAttribute("tipoTramite", TipoTramiteEnum.BAJA_CONCUBINATO.getCodigo().longValue());
		request.setAttribute("perfil", perfil);*/
		
		//return Constants.LISTA_BAJA_CONCUBINATO_FORDWARD;
		return Constants.INICIO_BAJA_CONCUBINATO;
	}
	
	/**
	 * Metodo para iniciar el tramite de baja de derechohabiente por termino de unión civil
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/unionCivil/home")
	public String bajaUnionCivilHome(HttpSession session, HttpServletRequest request, Model model) {
		
		Long perfil = 0L;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		if(usuario!=null){
			perfil = usuario.getPerfilUsuario().getIdPerfilUsuario();
			log.debug("entra a baja union civil----------");
		}
		
		
		List<GrupoFamiliar> candidato = null;
		
		try {
			candidato = bajaDerechohabienteServiceRemote.findGrupoFamiliarBajaUnionCivil(asignacionNSS.getIdAsignacionNSS(), OrigenSolicitudEnum.VENTANILLA.getId(), usuario);
			log.debug("union civil--------------");
			request.setAttribute("idPersona", candidato.get(0).getDerechohabiente().getIdPersona());
			request.setAttribute("hijo", candidato.get(0));
		} catch (DerechohabientesBusinessException e) {
			request.setAttribute("errores", e.getSituacion());
			log.error("", e);
			log.debug("union civil2----------------");
			model.addAttribute("candidatos", candidato);
			//Agregamos el tipo de tramite y el perfil para mostrar la guia de tramite correpondiente
			request.setAttribute("tipoTramite", TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_UNION_CIVIL.getCodigo().longValue());
			request.setAttribute("perfil", perfil);

		}
		
		
		log.debug("union civil2----------------");
		return Constants.INICIO_BAJA_PERSONA_UNION_CIVIL;
	}
	
	/**
	 * Metodo para iniciar el tramite de baja de derechohabiente por termino de convivencia
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/convivencia/home")
	public String bajaConvivenciaHome(HttpSession session, HttpServletRequest request, Model model) {

		Long perfil = 0L;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		if(usuario!=null){
			perfil = usuario.getPerfilUsuario().getIdPerfilUsuario();
		}
		
		
		
		List<GrupoFamiliar> candidatos = null;
		
		try {
			candidatos = bajaDerechohabienteServiceRemote.findGrupoFamiliarBajaConvivencia(asignacionNSS.getIdAsignacionNSS(),OrigenSolicitudEnum.VENTANILLA.getId(),usuario);
		} catch (DerechohabientesBusinessException e) {
			log.error("", e);
			request.setAttribute("errores", e.getMessage());
		}
		model.addAttribute("candidatos", candidatos);
		
		//Agregamos el tipo de tramite y el perfil para mostrar la guia de tramite correpondiente
		request.setAttribute("tipoTramite", TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA.getCodigo().longValue());
		request.setAttribute("perfil", perfil);
		
		return Constants.LISTA_BAJA_CONVIVENCIA_FORDWARD;
	}
	
	/**
	 * Metodo para guardar la baja de derechohabiente por defuncion recibiendo los siguientes parametros
	 * @param idDerechohabiente El derechohabiente a dar de baja
	 * @param session 
	 * @param request
	 * @return String vista de la ventana de cita
	 */
	@RequestMapping( value = "/defuncion/guardar", method = RequestMethod.POST)
	public String guardaBajaDefuncion(
			@RequestParam("idDerechohabiente") Long idDerechohabiente, 
			Model model,
			HttpSession session,
			HttpServletRequest request) {
		
		return this.guardarSolicitudBaja(idDerechohabiente, TipoBajaDerechohabienteEnum.DEFUNCION, session, model, request);
	}
	
	@RequestMapping( value = "/administrativa/guardar", method = RequestMethod.POST)
	public String guardaBajaAdministrativa(
			@RequestParam("idDerechohabiente") Long idDerechohabiente, 
			Model model,
			HttpSession session,
			HttpServletRequest request) {
		
		return this.guardarSolicitudBaja(idDerechohabiente, TipoBajaDerechohabienteEnum.ADMINISTRATIVA, session, model, request);
	}
	
	/**
	 * Metodo para guardar la baja de derechohabiente por divorcio recibiendo los siguientes parametros
	 * @param idDerechohabiente El derechohabiente a dar de baja
	 * @param session 
	 * @param request
	 * @return String vista de la ventana de cita
	 */
	@RequestMapping( value = "/divorcio/guardar")
	public String guardaBajaDivorcio(
			@RequestParam("idDerechohabiente") Long idDerechohabiente, 
			Model model,
			HttpSession session,
			HttpServletRequest request) {
		
		return this.guardarSolicitudBaja(idDerechohabiente, TipoBajaDerechohabienteEnum.DIVORCIO, session, model, request);

	}
	
	/**
	 * Metodo para guardar la baja de derechohabiente por termino de concubinato recibiendo los siguientes parametros
	 * @param idDerechohabiente El derechohabiente a dar de baja
	 * @param session 
	 * @param request
	 * @return String vista de la ventana de cita
	 */	@RequestMapping( value = "/concubinato/guardar")
	public String guardaBajaConcubinato(
			@RequestParam("idDerechohabiente") Long idDerechohabiente, 
			Model model,
			HttpSession session,
			HttpServletRequest request) {
		 
		return this.guardarSolicitudBaja(idDerechohabiente, TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO, session, model, request);

	}
	 
		/**
		 * Metodo para guardar la baja de derechohabiente por termino unión civil recibiendo los siguientes parametros
		 * @param idDerechohabiente El derechohabiente a dar de baja
		 * @param session 
		 * @param request
		 * @return String vista de la ventana de cita
		 */
		@RequestMapping( value = "/unionCivil/guardar")
		public String guardaBajaUnionCivil(
				@RequestParam("idDerechohabiente") Long idDerechohabiente, 
				Model model,
				HttpSession session,
				HttpServletRequest request) {
			System.out.println("Aquí se esta mando a guardar baja union civil");
			log.debug("Aquí se esta mando a guardar baja union civil");
			
			return this.guardarSolicitudBaja(idDerechohabiente, TipoBajaDerechohabienteEnum.TERMINO_DE_UNION_CIVIL, session, model, request);

	}
	
	/**
	 * Metodo para guardar la baja de derechohabiente por termino de dependencia o convivencia recibiendo los siguientes parametros
	 * @param idDerechohabiente El derechohabiente a dar de baja
	 * @param session 
	 * @param request
	 * @return String vista de la ventana de cita
	 */
	@RequestMapping( value = "/convivencia/guardar")
	public String guardaBajaConvivencia(
			@RequestParam("idDerechohabiente") Long idDerechohabiente, 
			Model model,
			HttpSession session,
			HttpServletRequest request) {
		
		return this.guardarSolicitudBaja(idDerechohabiente, TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA, session, model, request);
	}
	
	private String guardarSolicitudBaja(Long idDerechohabiente,TipoBajaDerechohabienteEnum tipoBaja, HttpSession session, Model model, HttpServletRequest request){
		String fordward = Constants.TRAMITE_VALIDACION_BAJA;
		Map<String, Object> result = null;
		Solicitud solicitud = null;
		GrupoFamiliar afectado = null;
		TramiteBajaDerechohabiente tramite = null;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		try {
			result = bajaDerechohabienteServiceRemote.saveSolicitudBajaDerechohabiente(idDerechohabiente, tipoBaja,asignacionNSS, OrigenSolicitudEnum.VENTANILLA, usuario);			
			
			solicitud = (Solicitud) result.get("solicitud");
			afectado = (GrupoFamiliar) result.get("afectado");
			tramite = this.getTramiteBaja(solicitud);

			session.setAttribute(KEY_SOLICITUD, solicitud);
			session.setAttribute(KEY_INTEGRANTE_BAJA, afectado);
			
			model.addAttribute("solicitud",solicitud);
			model.addAttribute("tramite", tramite);
			
		} catch (DerechohabientesBusinessException e) {
			log.error("", e);
			request.setAttribute("errores",e.getSituacion());
		} catch (Exception e) {
			log.error("", e);
		}
		
		return fordward;
	}
	
	/**
	 * 
	 * @param derechohabiente
	 * @param tipo
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/validar/{idSolicitud}/{idPersona}")
	public String validarBajaDerechohabiente(
			@PathVariable("idSolicitud") Long idSolicitud,
			@PathVariable("idPersona") Long idPersona,
			Model model, HttpServletRequest request, HttpSession session) {
		
		TramiteBajaDerechohabiente tramiteBaja = null;
		//obtenemos la solicitud de baja de la session
		Solicitud solicitudBaja = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		//obtnemos el asignacionNSS de la session
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		//integrante de la baja afectado
		GrupoFamiliar derechohabiente = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_BAJA);
		
		try {
			//si no existe la solicitud en la session o si el id no corresponde con el que se manda, se consultara la solicitud
			if(solicitudBaja == null || !solicitudBaja.getSolicitudId().equals(idSolicitud)) {
				solicitudBaja = new Solicitud(idSolicitud);
				solicitudBaja = solicitudBusinessRemote.consultar(solicitudBaja);
			}
			
			//una vez que tenemos la solicitud, obtenemos el tramite de baja
			if(solicitudBaja != null) {
				tramiteBaja = this.getTramiteBaja(solicitudBaja);
			}
			
			//verificamos si el tramite requiere la captura de documentos probatorios
			//Boolean requiereDocumentos = false;
			//requiereDocumentos = documentoProbatorioServiceBusinessRemote.requiereDocumentos(tramiteBaja.getTipoTramite().getIdTipoTramite().longValue());
			fileUploadController.requiereDocumentosTramite(model, tramiteBaja.getTipoTramite().getIdTipoTramite());
			
			
			
			
			if(derechohabiente == null || !derechohabiente.getDerechohabiente().getIdPersona().equals(tramiteBaja.getPersona().getIdPersona())) {
				derechohabiente = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(), tramiteBaja.getPersona().getIdPersona());
			}
			
			//model.addAttribute(KEY_REQUIERE_DOCS, requiereDocumentos);
			model.addAttribute("solicitud",solicitudBaja);
			model.addAttribute("tramite", tramiteBaja);
			model.addAttribute("hijo", derechohabiente);
			model.addAttribute("fechaDefuncion",new Date());
			model.addAttribute("tipoDocsNoMostrar", TramiteUtil.quitarTipoDocumentosMenoresEdadad(derechohabiente.getDerechohabiente().getFechaNacimiento()));
			
			session.setAttribute(KEY_SOLICITUD, solicitudBaja);
		} catch (DerechohabientesBusinessException e) {
			log.error("", e);
			request.setAttribute("errores", e.getSituacion());
		} catch (Exception e) {
			log.error("", e);
		}
		
		return Constants.VALIDAR_BAJA_DERECHOHABIENTE_FORDWARD;
	}
	
	/**
	 * 
	 * @param derechohabiente
	 * @param tipo
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/autorizar/{idSolicitud}")
	public String autorizarBajaDerechohabiente(
			@PathVariable("idSolicitud") Long idSolicitud,
			Model model, HttpServletRequest request, HttpSession session) {
		
		Solicitud solicitudBaja = null;
		TramiteBajaDerechohabiente tramiteBaja = null;
		GrupoFamiliar miGrupoFamiliar = null;
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_INTEGRANTE_BAJA);
		
		try {
			if(asignacionNSS == null){
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(idSolicitud);
			}
			
			miGrupoFamiliar = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarByEstados(asignacionNSS.getIdPersona(), null, asignacionNSS.getIdAsignacionNSS());
			solicitudBaja = bajaDerechohabienteServiceRemote.inicioAutorizacionBaja(idSolicitud, asignacionNSS);
			
			tramiteBaja = (TramiteBajaDerechohabiente) solicitudBaja.getTramites().get(0);
			
			GrupoFamiliar derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(), tramiteBaja.getPersona().getIdPersona());
			model.addAttribute("solicitud",solicitudBaja);
			model.addAttribute("tramite", tramiteBaja);
			model.addAttribute("hijo", derechohabiente);
			request.setAttribute("miGrupoFamiliar", miGrupoFamiliar);
			
			session.setAttribute(KEY_SOLICITUD, solicitudBaja);
			session.setAttribute(KEY_INTEGRANTE_BAJA, derechohabiente);
		} catch (DerechohabientesBusinessException e) {
			log.error("", e);
			request.setAttribute("errores", e.getSituacion());
		} catch (Exception e) {
			log.error("", e);
		}
		
		return Constants.AUTORIZAR_BAJA_DERECHOHABIENTE_FORDWARD;
	}
	
	/**
	 * Metodo para guardar la validacion de la Baja
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/guardar/validacion")
	public String validarBajaDocumentacion(@ModelAttribute TramiteDto tramite, HttpServletRequest request,HttpSession session, Model model) {
		
		//Llenamos los objetos necesarios de la sesion 
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		ImpresionReporteDto reporte = new ImpresionReporteDto();
		Solicitud solicitudBaja = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		
		try {
			TramiteBajaDerechohabiente baja = this.getTramiteBaja(solicitudBaja);
			
			baja.setObservaciones(tramite.getObservaciones());
			if(StringUtils.isNotBlank(tramite.getFechaDefuncion()))
			baja.setFechaDefuncion(DateUtils.dateToDateConFormato(tramite.getFechaDefuncion(), "dd/MM/yyyy"));
			
			solicitudBaja.setTramites(new ArrayList<Tramite>());
			solicitudBaja.getTramites().add(baja);
			
			solicitudBusinessRemote.actualizarXmlTramite(baja);
			
			Solicitud solicitud = new Solicitud(tramite.getIdSolicitud());
			solicitud = bajaDerechohabienteServiceRemote.finalizarSolicitudBaja(solicitud,asignacionNSS);
			//Se guardan los documentos probatorios
			log.debug("Se guardaran los documentos probatorios asincronamente");
			guardaDocumentosAsincrono.salvaDocumentosProbatorios(session, baja.getTramiteId(), baja.getPersona().getIdPersona());
			TramiteBajaDerechohabiente tramiteBaja = this.getTramiteBaja(solicitud);
			
			Derechohabiente der = (Derechohabiente) tramiteBaja.getPersona();
			int identificadorReporte = tramiteBaja.getTipoTramite().getIdTipoTramite();
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdTramite(tramiteBaja.getTramiteId());
				
			
			
			if(tramite.getIdTipoTramite().equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_AUTORIDAD_NORMATIVA.getCodigo().longValue())) {
				try {
					byte[] documento = null;
					//La generacion del documento Sav002, se realiza a traves de TramiteDocumentosService
					documento = (byte[])tramiteDocumentosService.generaDocumentoConSelloDigital(asignacionNSS, solicitud, usuario, identificadorReporte, propiedades, null);

					if(documento != null) {
						//Se buscan los medios de contacto del nss
						this.llenaMediosContacto(der.getAsignacionNSS());
						//Se buscan los medios de contacto del beneficiario a dar d ebaja
						this.llenaMediosContacto(der);
						emailServiceRemote.enviarCorreoBajaNormativa(der, documento);
					}
				} catch(Exception e) {
					log.error("Ocurrio un error al enviar el mar de baja normativa",e);
				}
			}
			
			//Objeto para generar el reporte
			reporte.setIdPersona(tramite.getIdPersona());
			reporte.setIdTramite(tramite.getIdTramite());
			reporte.setIdSolicitud(tramite.getIdSolicitud());
			reporte.setRechazado(false);
			TipoTramite tipoTram = new TipoTramite();
			tipoTram.setIdTipoTramite(tramite.getIdTipoTramite().intValue());
			tipoTram.setDescripcion("Baja de derechohabiente");
			reporte.setTipoTramite(tipoTram);
			
			model.addAttribute("solicitud",solicitud);
			model.addAttribute("reporte", reporte);
			session.removeAttribute(KEY_SOLICITUD);
			session.removeAttribute(KEY_INTEGRANTE_BAJA);
			
			return "finalizacionTramite";
			
		} catch(ImpactaAlmacenesWSException e) {
			log.error("Ocurrio un error al calcular la vigencia", e);
			request.setAttribute("mostrarBoton", true);
			request.setAttribute("exception", "Ocurri&oacute; un error al calcular la vigencia");
			request.setAttribute("error", "Intentelo m&aacute;s tarde retomando la solicitud por favor.");
			
			return "internalError";
		} catch (Exception e) {
			log.error("Ocurrio un error al finalizar la baja", e);
			request.setAttribute("mostrarBoton", true);
			request.setAttribute("exception", "error.actualizar.tramite");
			request.setAttribute("error", e.getCause().getMessage());
			
			return "internalError";
		}
	}

	@RequestMapping(value = "/guardar/autorizacion")
	public String autorizacionBajaDocumentacion(@ModelAttribute TramiteDto tramite, HttpSession session, Model model) {
		
		//Llenamos los objetos necesarios de la sesion
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		
		try {
			if(asignacionNSS == null){
				asignacionNSS = solicitudServiceRemote.getAsignacionByIdSolicitud(tramite.getIdSolicitud());
			}
			
			Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
			TramiteBajaDerechohabiente baja = this.getTramiteBaja(solicitud);
			
			baja.setObservaciones(tramite.getObservaciones());
			baja.setFechaDefuncion(DateUtils.dateToDateConFormato(tramite.getFechaDefuncion(), "dd/MM/yyyy"));
			
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(baja);
			
			solicitudBusinessRemote.actualizarTramites(solicitud);
			
			solicitud = bajaDerechohabienteServiceRemote.finalizarSolicitudBaja(solicitud);
			
			/*
			bajaDerechohabienteServiceRemote.saveAutorizacionBaja(tramite.getIdSolicitud(), tramite.getIdPersona(),
					tramite.getIdTipoTramite(), tramite.getObservaciones(), asignacionNSS, tramite.getFechaDefuncion(),usuario.getFisica());
			*/
			
			ImpresionReporteDto reporte = new ImpresionReporteDto();
			//Objeto para generar el reporte
			reporte.setIdPersona(tramite.getIdPersona());
			reporte.setIdTramite(tramite.getIdTramite());
			reporte.setIdSolicitud(tramite.getIdSolicitud());
			reporte.setRechazado(false);
			TipoTramite tipoTram = new TipoTramite();
			tipoTram.setIdTipoTramite(tramite.getIdTipoTramite().intValue());
			tipoTram.setDescripcion("Baja de derechohabiente");
			reporte.setTipoTramite(tipoTram);
			model.addAttribute("reporte", reporte);
			
			session.removeAttribute(KEY_SOLICITUD);
			session.removeAttribute(KEY_INTEGRANTE_BAJA);
			
			return "finalizacionTramite";
		} catch (DerechohabientesBusinessException e) {
			log.error("", e);
			model.addAttribute("exception", e.getMessage());
			model.addAttribute("error", e.getSituacion());
			return "internalError";
		} catch (Exception e) {
			log.error("", e);
			model.addAttribute("exception", "error.actualizar.tramite");
			model.addAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
		
	
	}
	
	/**
	 * Metodo para rechazar una solicitud de baja
	 * @param rechazoTramite
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/rechazar", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<Solicitud> rechazarBaja(
			@RequestBody RechazoTramiteDto rechazoTramite, HttpServletRequest request,HttpSession session){
		
		//Creamos el objeto en el que mandaremos la respuesta a vista
		RespuestaJSON<Solicitud> respuesta = new RespuestaJSON<Solicitud>();
		Solicitud solicitud = null;
		Usuario miUsuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		//Rechazamos la solicitud y obtenemos el tramite relacionado con ella
		try {
			solicitud = bajaDerechohabienteServiceRemote.rechazarSolicitudBaja(rechazoTramite.getIdSolicitud(), rechazoTramite.getIdPersona(), 
					rechazoTramite.getIdTramite(), rechazoTramite.getIdRazonRechazo(), rechazoTramite.getObservaciones(),miUsuario.getFisica());
		} catch (DerechohabientesBusinessException e) {
			log.error("", e);
			//Si existe un error lo agregamos en la lista de errores de la respuesta
			respuesta.setErrores(new ArrayList<String>());
			respuesta.getErrores().add(e.getMessage());
		}
		
		//Colocamos le tramite encontrado en la respuesta
		respuesta.setModelo(solicitud);
		
		return respuesta;
	}
	
	
	private TramiteBajaDerechohabiente getTramiteBaja(Solicitud solicitud) {
		
		TramiteBajaDerechohabiente tramiteBaja = null;
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteBajaDerechohabiente) {
				tramiteBaja = (TramiteBajaDerechohabiente) tramite;
				break;
			}
		}
		
		return tramiteBaja;
	}
	
	private Fisica llenaMediosContacto(Fisica miFisica)
	throws Exception {
		TipoPersona unTipoPersona = new TipoPersona();
		unTipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		miFisica.setTipoPersona(unTipoPersona);
		List<MedioContacto> mediosContacto = null;
		try {
			mediosContacto = mediosContactoServiceBusinessRemote.consultarMedioDeContactoPersona(miFisica);
		} catch (PersonaSinMedioDeContactoException e) {
			log.error("Sin medios de contacto", e);
			mediosContacto = null;
		}

		if(mediosContacto != null){ 				 
			Iterator<MedioContacto> it =  mediosContacto.iterator();
			while(it.hasNext()){ 
				MedioContacto m = it.next(); 
				if(m instanceof TelefonoFijo){
					TelefonoFijo telefonoFijo = (TelefonoFijo)m;
					miFisica.setTelefonoFijo(telefonoFijo);
				}else if ( m instanceof TelefonoMovil){

					TelefonoMovil telefonoMovil = (TelefonoMovil)m;
					miFisica.setTelefonoMovil(telefonoMovil);

				}else if (m instanceof CorreoElectronico){
					CorreoElectronico correoElectronico = (CorreoElectronico)m;
					miFisica.setCorreoElectronico(correoElectronico);
				}else if (m instanceof Facebook){
					Facebook facebook = (Facebook)m;
					miFisica.setFacebook(facebook);

				}else if ( m instanceof Twitter){
					Twitter twitter = (Twitter)m;
					miFisica.setTwitter(twitter);
				} 
			}
		}			
		return miFisica;
	}
}