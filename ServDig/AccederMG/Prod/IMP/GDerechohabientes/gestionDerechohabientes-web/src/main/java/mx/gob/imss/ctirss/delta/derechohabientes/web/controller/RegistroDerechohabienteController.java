package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.TramiteUtil;
import mx.gob.imss.ctirss.delta.derechohabientes.web.validator.RegistroDerechohabientesValidator;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.DocumentosEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.VarianteRegistroEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/tramite/registro/")
public class RegistroDerechohabienteController extends AbstractController {

	//Variables estaticas
	private static final String VISTA_INICIO_TRAMITE = "inicioRegistroDerechohabiente";
	private static final String VISTA_CONFIRMACION = "confirmacionRegistro";
	private static final String VISTA_FINALIZACION_TRAMITE = "finalizacionRegistro";
	private static final String KEY_ESTADO_VALIDACION = "correcto";
	private static final String KEY_MENSAJE_VALIDACION = "mensaje";
	private static final String KEY_SOLICITUD_ACTIVA = "solicitudActiva";
	private static final String KEY_TRAMITE_REGISTRO = "registro";
	private static final String KEY_VALIDACIONES = "validaciones";
	private static final String KEY_IS_RETOMAR = "isRetomar";
	private static final String KEY_IS_REGISTRO_ASEGURADO = "isRegistroAsegurado";
	private static final String KEY_DOMICILIO_ASEGURADO_SESSION = "domicilioAsegurado";
	
	//EJBS Usados
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusinessRemote;
	@Autowired
	private RegistroDerechohabienteServiceRemote registroDerechohabienteServiceRemote;
	@Autowired
	private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@Autowired
	private CatalogosServiceRemote catalogosServiceRemote;
	@Autowired
	private SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	@Autowired
	private PersonaBusinessRemote personaBusinessRemote;
	@Autowired
	private ProcesosAsincronosGrupoFamiliar procesosAsincronosGrupoFamiliar;
	
	@Autowired
	private FileUploadController fileUploadController;
	
	@RequestMapping("/iniciarTramite")
	public String iniciaTramite(HttpServletRequest request, HttpSession session, Model model) {
		//Map para las validaciones que se hagan
		Map<String, Object> validaciones = null;
		//Tramite de registro de derechohabiente registrado o nuevo
		TramiteRegistroDerechohabiente registro = null;
		//Bandera de validaciones
		Boolean correcto = true;
		//solicitud activa
		Solicitud solicitudActiva = null;
		//Se recupera de la session el nss
		AsignacionNSS nss =(AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		//Cabeza grupo familiar
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		//Usuario
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		//Checamos si ya esta lo solicitud
		solicitudActiva = (Solicitud) session.getAttribute(KEY_SOLICITUD_ACTIVA);
		//asegurado registrado
		GrupoFamiliar asegurado = (GrupoFamiliar) session.getAttribute("miGrupoFamiliar");
		
		if(asegurado != null) {
			session.setAttribute(KEY_DOMICILIO_ASEGURADO_SESSION, asegurado.getDomicilio());
		}
		
		if(solicitudActiva == null) {
			log.debug("No se encontro ninguna solicitud en la sesion al iniciar el tramite, el asegurado se encuentra registrado? : " + asegurado.getIndRegistrado());
			/**
			 * En caso de que el asegurado no este registrado buscaremos si ya existe la solicitud de registro para que en ese caso
			 * la retomemos y no creemos otra
			 */
			if(asegurado.getIndRegistrado() != 1) {
				log.debug("El asegurado con NSS "+nss.getNss()+" no se encuentra registrado, por lo que se procede a buscar alguna solicitud pendiente para retomarla");
				validaciones = requisitosMinimosServiceRemote.obtenerSolicitudRegistroAseguradoPensionado(nss, OrigenSolicitudEnum.VENTANILLA.getId());
				//Verificamos si las validaciones son correctas
				correcto = this.getEstadoValidaciones(validaciones);
				// se obtiene la solicitud
				solicitudActiva = (Solicitud) validaciones.get(KEY_SOLICITUD_ACTIVA);
			} 
			//Si encontramos la solicitud obtenemos el tramite
			if(solicitudActiva != null) {
				//Se retoma el tramite
				registro = TramiteUtil.getTramiteRegistroFromSolicitud(solicitudActiva);
			} 
			
			//si las validaciones con correctas
			if(correcto) {
				if(registro == null){
					//se crea el tramite
					registro = this.llenarTramiteRegistro(nss, cabezaGrupo, usuario, asegurado, session);
				}
			}
			
			//Solo si la solicitud activa es nula se crea una nueva y se le asocia el tramite que creamos
			if(solicitudActiva == null) {
				solicitudActiva = new Solicitud();
				solicitudActiva.setTramites(new ArrayList<Tramite>());
				solicitudActiva.getTramites().add(registro);
			}
		} else {
			log.debug("Se encontro la solicitud en session");
			//Si la solicitud activa ya esta en sesion sacamos el tramite que tiene
			registro = (TramiteRegistroDerechohabiente) solicitudActiva.getTramites().get(0);
		}
		
		//Agregamos las validaciones al modelo
		model.addAttribute("aseguradoConDomicilio", asegurado.getDomicilio() != null);
		model.addAttribute(KEY_VALIDACIONES,validaciones);
		model.addAttribute(KEY_TRAMITE_REGISTRO, registro);
		model.addAttribute(KEY_IS_REGISTRO_ASEGURADO, registroAsegurado(registro));
		session.setAttribute(KEY_SOLICITUD_ACTIVA, solicitudActiva);
		
		return VISTA_INICIO_TRAMITE;
	}
	
	/**
	 * Metodo para saber si el registro es del asegurado o pensionado
	 * @param registro
	 * @return
	 */
	private Boolean registroAsegurado(TramiteRegistroDerechohabiente registro) {
		Boolean registroAsegurado = false;
		if(registro.getParentesco() != null && registro.getParentesco().getIdParentesco() != null) {
			Long idParentesco = registro.getParentesco().getIdParentesco();
			
			if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId())) {
				registroAsegurado= true;
			}
		}
		
		return registroAsegurado;
	}
	
	@RequestMapping("/retomar/{idSolicitud}")
	public String retomarTramite(@PathVariable Long idSolicitud,Model model, HttpSession session, HttpServletRequest request) {
		
		//Tramite de registro de derechohabiente
		TramiteRegistroDerechohabiente tramite = null;
		Solicitud solicitud = new Solicitud(idSolicitud);
		//Se recupera de la session el nss
		AsignacionNSS nss =(AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		//asegurado registrado
		GrupoFamiliar asegurado = (GrupoFamiliar) session.getAttribute("miGrupoFamiliar");
		//Seteamos la variables que indica que estamos retomando la solicitud
		session.setAttribute(KEY_IS_RETOMAR, true);
		
		if(asegurado == null || !asegurado.getDerechohabiente().getIdPersona().equals(nss.getIdPersona())) {
			try {
				//Concultamos al asegurado
				asegurado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(nss.getIdAsignacionNSS(), nss.getIdPersona());
				session.setAttribute("miGrupoFamiliar", asegurado);
			} catch (Exception e) {
				log.error("Ocurrio un error al obtener al asegurado", e);
			}
		}
		
		//Si el asegurado es nulo
		if(asegurado != null) {
			//Seteamos el domicilio del asegurado en sesion
			Domicilio domicilioRegistro = asegurado.getDomicilio();
			session.setAttribute(KEY_DOMICILIO_ASEGURADO_SESSION, domicilioRegistro);
		}
		
		try {

			//Se consulta la solicitud
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			
			//Se verifica que no sea nula 
			if(solicitud != null) {
				model.addAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
				tramite = (TramiteRegistroDerechohabiente) solicitud.getTramites().get(0);
				session.setAttribute(KEY_SOLICITUD_ACTIVA, solicitud);
			} else {
				request.setAttribute("error", "No fue posible recuperar la solicitud");
			}
		} catch(Exception e) {
			solicitud = new Solicitud();
			request.setAttribute("error", "No fue posible recuperar la solicitud");
		}
		
		//Agregamos las validaciones al modelo
		model.addAttribute("aseguradoConDomicilio", asegurado.getDomicilio()!= null);
		model.addAttribute(KEY_SOLICITUD_ACTIVA, solicitud);
		model.addAttribute(KEY_TRAMITE_REGISTRO, tramite);
		model.addAttribute(KEY_IS_REGISTRO_ASEGURADO, registroAsegurado(tramite));
		
		
		return VISTA_INICIO_TRAMITE;
	}
	
	@RequestMapping(value = "/getDomicilioAsegurado")
	public @ResponseBody Domicilio getDomicilioAsegurado(HttpSession session) {
		//obtenemos el domicilio de la sesseion
		GrupoFamiliar asegurado = (GrupoFamiliar) session.getAttribute("miGrupoFamiliar");
		Domicilio domicilioAsegurado = asegurado.getDomicilio();
		
		return domicilioAsegurado;
	}
	
	@RequestMapping(value ="/validacionesDomicilio")
	public @ResponseBody Map<String, Object> validacionesEleccionDomcilio(
			@RequestBody TramiteRegistroDerechohabiente registro, HttpServletRequest request, HttpSession session){
		Map<String, Object> datosDomicilio = null;
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		Boolean isPatronImss = cabeza != null ? (cabeza.getPatronImss() != null ? cabeza.getPatronImss().equals(1) : false): false;
		GrupoFamiliar asegurado = (GrupoFamiliar) session.getAttribute("miGrupoFamiliar");
		Domicilio domicilioAsegurado = asegurado.getDomicilio();
		
		try {
			datosDomicilio  = requisitosMinimosServiceRemote.validarEleccionDeDomicilioYUmfPorPersonaParentesco(
					registro.getFisica(), registro.getParentesco().getIdParentesco(), domicilioAsegurado, isPatronImss);
			Boolean setDomicilioAsegurado = (Boolean) datosDomicilio.get("setDomicilioAsegurado");
			
			PersonaDomicilio personaDomicilio = (PersonaDomicilio) datosDomicilio.get("personaDomicilio");
			if(personaDomicilio != null) {
				if(personaDomicilio.getDomicilio() == null) {
					personaDomicilio.setDomicilio(domicilioAsegurado);
					datosDomicilio.put("personaDomicilio", personaDomicilio);
				} else {
					if(setDomicilioAsegurado) {
						personaDomicilio.setDomicilio(domicilioAsegurado);
						datosDomicilio.put("personaDomicilio", personaDomicilio);
					}
				}
			} else {
				personaDomicilio = new PersonaDomicilio();
				personaDomicilio.setDomicilio(domicilioAsegurado);
				datosDomicilio.put("personaDomicilio", personaDomicilio);
			}
		} catch(Exception e) {
			log.error("Ocurrio un error al hacer las validaciones con respecto al domicilio y los roles de la persona",e);
		}
		return datosDomicilio;
	}
	
	@RequestMapping(value = "/confirmacion", method = RequestMethod.POST)
	public String confirmacionTramite(@ModelAttribute TramiteRegistroDerechohabiente registro,HttpServletRequest request, HttpSession session, Model model) {
		//Cabeza grupo familiar
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		//Validamos los requisitos minimos para el registro
		Map<String, Object> validaciones = null;
		//solicitud activa
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD_ACTIVA);
		//Tramite de la session
		TramiteRegistroDerechohabiente tramiteXML = (TramiteRegistroDerechohabiente) solicitud.getTramites().get(0); 
		//Usuario
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		//modalidades
		List<Modalidad> modalidades = this.obtenerModalidadesPatrones(session);
		//ids modalidades
		List<Long> idsModalidades = this.getModalidadesActivas(modalidades);
		try {
			//Seteamos la persona que se envia del formulario de registro
			tramiteXML.setFisica(registro.getFisica());
			//se envia el parentesco que se envia
			tramiteXML.setParentesco(registro.getParentesco());
			tramiteXML.setRazonRegistro(registro.getRazonRegistro());
			tramiteXML.setDomicilio(registro.getDomicilio());
			//se setea el indicador de hijos procreados
			tramiteXML.setIndHijosProcreados(registro.getIndHijosProcreados());
			//se setea el indicador de adopcion 
			tramiteXML.setVarianteRegistro(registro.getVarianteRegistro());
			
			TipoTramite tipoTramite =tramiteXML.getTipoTramite();
			
			//Integer idTipoTramiteActual = tipoTramite == null ? null : (tipoTramite.getIdTipoTramite() == null ? null : tipoTramite.getIdTipoTramite());			
			//En caso de que el parentesco cambie volvemos a setear el tipo de tramite
			Integer idTipoTramite = this.getTipoTramitePorParentesco(registro.getParentesco().getIdParentesco());
			//Solo si cambio el parentesco se actualiza el tipo de tramite
			//if(idTipoTramiteActual == null || !idTipoTramite.equals(idTipoTramiteActual)) {
			tipoTramite  = catalogosServiceRemote.getCatalogoTipoTramite(idTipoTramite.longValue());
			//si el tipo de tramite cambio, actualizamos el tramite
			if(tramiteXML.getTramiteId() != null) {
				log.debug("actualizamos el tipo de tramite, ya que el parentesco cambio");
				solicitudBusinessRemote.actualizaTipoTramite(tramiteXML.getTramiteId(), tipoTramite.getIdTipoTramite().longValue());
			}
			//}
			
			tramiteXML.setTipoTramite(tipoTramite);
			Boolean validacionesCorrectas = true;
			
			//Si es recien nacido la persona a registrar no se validara si ya existe la persona en el grupo familiar
			if(!tramiteXML.getRazonRegistro().getIdRazonRegistro().equals(RazonRegistroEnum.RECIEN_NACIDO.getId())) {
				//Validamos si la persona ya existe
				validaciones = requisitosMinimosServiceRemote.validaPersonaRegistrada(tramiteXML.getFisica(), tramiteXML.getDatosAsegurado().getIdAsignacionNSS());		
				validacionesCorrectas = this.getEstadoValidaciones(validaciones);
			}
			//Solo si la persona no existe validaremos los requisitos minimos
			if(validacionesCorrectas) {
				if(validaciones != null) {
					Fisica personaLocalizada = (Fisica) validaciones.get("personaLocalizada");
					tramiteXML.setFisica(personaLocalizada);
				} 
				
				//validamos si se puede realizar el registro
				validaciones =requisitosMinimosServiceRemote.requisitosMinimosRegistro(tramiteXML, cabezaGrupo, OrigenSolicitudEnum.VENTANILLA.getId(),usuario.getIdUmf(), false,idsModalidades);
			}
			
			//Si la solicitud es diferente de null solo actualizamos el tramite
			if(solicitud != null) {
				solicitud.setTramites(new ArrayList<Tramite>());
				solicitud.getTramites().add(tramiteXML);
			}
			
			//y la solicitud ya esta creada
			if(solicitud.getSolicitudId() != null) {
				//actualizamos el tramite en el xml
				solicitudBusinessRemote.actualizarXmlTramite(tramiteXML);
			} else if(this.getEstadoValidaciones(validaciones)){
				//de lo contrario si las validaciones estan bien y la solicitud no ha sido creada la guardamos en bdtu
				solicitud = registroDerechohabienteServiceRemote.registraSolicitud(tramiteXML, OrigenSolicitudEnum.VENTANILLA.getId());
				Long idTramite = solicitud.getTramites().get(0).getTramiteId();
				tramiteXML.setTramiteId(idTramite);
			}
				

		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			validaciones = new HashMap<String, Object>();
			validaciones.put(KEY_ESTADO_VALIDACION, false);
			validaciones.put(KEY_MENSAJE_VALIDACION, e.getSituacion());
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
			validaciones = new HashMap<String, Object>();
			validaciones.put(KEY_ESTADO_VALIDACION, false);
			validaciones.put(KEY_MENSAJE_VALIDACION, e.getMessage());
		} catch(SolicitudNoValidaException e) {
			e.printStackTrace();
			validaciones = new HashMap<String, Object>();
			validaciones.put(KEY_ESTADO_VALIDACION, false);
			validaciones.put(KEY_MENSAJE_VALIDACION, e.getMessage());
		}
		
		session.setAttribute(KEY_TRAMITE_REGISTRO, tramiteXML);
		session.removeAttribute(KEY_SOLICITUD_ACTIVA);
		session.setAttribute(KEY_SOLICITUD_ACTIVA, solicitud);
		model.addAttribute(KEY_TRAMITE_REGISTRO, tramiteXML);
		model.addAttribute(KEY_VALIDACIONES, validaciones);
		
		return VISTA_CONFIRMACION;
	}
	
	@RequestMapping("/datosAdscripcion")
	public String datosAdscripcion(HttpServletRequest request, HttpSession session, Model model) {
		log.debug("Entro al controler de datos de adfscripcion");
		//obtenemos la solicitud de la session
		Solicitud solicitudActiva = (Solicitud) session.getAttribute(KEY_SOLICITUD_ACTIVA);
		//sacamos el tramite de registro
		TramiteRegistroDerechohabiente registro = TramiteUtil.getTramiteRegistroFromSolicitud(solicitudActiva);
		//Validamos si el tramite requiere documentos
		validarRequerimientoDocumentos(model, registro);
		
		
		if(registro.getFisica().getActaNacimiento() != null) {
			log.debug("Se encontro el acta de nacimiento de la persona" + registro.getFisica().getActaNacimiento());
			solicitudActiva.setTramites(new ArrayList<Tramite>());
			solicitudActiva.getTramites().add(registro);
			session.setAttribute(KEY_SOLICITUD_ACTIVA, solicitudActiva);
		}
		//se manda el tramite a la pantalla
		model.addAttribute(KEY_TRAMITE_REGISTRO, registro);
		//Se pintan en log
		log.debug("La fecha de nacimiento es: " + registro.getFisica().getFechaNacimiento());
		log.debug("El mes de nacimiento es: " + registro.getFisica().getMesRegistroNac());
		log.debug("El anio de nacimiento es: " + registro.getFisica().getAnioRegistroNac());
		
		return VISTA_FINALIZACION_TRAMITE;
	}
	
	/**
	 * Lista los tipos de documentos que no pediran para concluir el trámite
	 * 
	 * @param correccion
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String, String> quitarTiposDocumentos(TramiteRegistroDerechohabiente registro){
		Map<String,String> doctosAQuitar = new HashMap<String, String>();
		//ids de los documentos a quitar en caso de que el registro sea por adopcion
		String doctosAQuitarAdopcion = "";
		//ids de los documentos a quitar en caso de que el registro sea por reconocimiento
		String doctosAQuitarReconocimiento = "";
		//ids de los documentos a quitar en caso de que el registro sea normal
		String doctosAQuitarNoral = "";
		//isd de las categorias a quitar
		String idCategoriasQuitar = "";
		//ids de los docuemtnso a quitar
		String idDoctosQuitar = "";
		
		//Se obtiene a la persona a registrar
		Fisica fisica = registro.getFisica();
		//verificamos si el registro es de conyuge
		Boolean registroConyuge = registro.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REGISTRO_CONYUGUE.getCodigo());
		//Solo si el tipo de tramite es registro de hijos validaremos si se eligio adopcion o reconocimiento
		//para quitar los documentos necesarios
		if(registro.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REGISTRO_HIJOS.getCodigo())) {
			String idActaAdopcion = "" + DocumentosEnum.ACTA_ADOPCION.getId();
			String idActaReconocimiento = "" + DocumentosEnum.ACTA_RECONOCIMIENTO.getId();
			String idActaNacimiento = "" + DocumentosEnum.ACTA_NACIMIENTO.getId();
			
			if(registro.getVarianteRegistro() == VarianteRegistroEnum.ADOPCION.getId()) {
				doctosAQuitarAdopcion = idActaNacimiento + "," + idActaReconocimiento;
			} else if(registro.getVarianteRegistro() == VarianteRegistroEnum.RECONOCIMIENTO.getId()) {
				doctosAQuitarReconocimiento = idActaNacimiento + "," + idActaAdopcion;
			} else if(registro.getVarianteRegistro() == VarianteRegistroEnum.NORMAL.getId()) {
				doctosAQuitarNoral = idActaReconocimiento + "," + idActaAdopcion;
			}
		}
		//junatamos las variables de adopcion y reconocimiento
		idDoctosQuitar = ""+doctosAQuitarAdopcion + doctosAQuitarReconocimiento;
		//si esxiste persona
		if(fisica != null) {
			//se valida que se tenga la fecha de nacimiento
			if(fisica.getFechaNacimiento() != null || (fisica.getAnioRegistroNac() != null || fisica.getMesRegistroNac() != null)) {
				idCategoriasQuitar = TramiteUtil.quitarTipoDocumentosMenoresEdadad(fisica.getFechaNacimiento());
			}
			//validamos si tiene acta de nacimiento y si cuenta con la curp
			if(fisica.getActaNacimiento() == null && StringUtils.isNotBlank(fisica.getCurp())){
				//persona consultada en renapo
				Fisica fisicaR = null;
				try {
					log.debug("Se consultara renapo con la siguiente curp: " + fisica.getCurp());
					fisicaR = personaBusinessRemote.buscarPersonaFisicaPorCurpEnRenapo(fisica.getCurp());
					//si se encontro a la persona y tiene acta de nacimiento se la seteamos a la que tiene el tramite
					if(fisicaR != null && fisicaR.getActaNacimiento() != null){
						registro.getFisica().setActaNacimiento(fisicaR.getActaNacimiento());
					}
				} catch (ClienteWebserviceRenapoCurpException e) {
					//si ocurre un error al consultar a renapo solo se imprime el error en consola
					log.debug("el servicio de renapo fallo",e);
				}			
			}
			//si no es registro de conyuge
			if(!registroConyuge) {
				//si no hay adopcion o reconocimiento y se encontro la curp se quitan todas las actas
				if(registro.getFisica().getActaNacimiento() != null) {
					if(StringUtils.isBlank(doctosAQuitarAdopcion) && StringUtils.isBlank(doctosAQuitarReconocimiento)) {
						idCategoriasQuitar+=","+String.valueOf(TipoDocumentoProbatorioEnum.ACTAS.getId());
					}
				} else {
					if(StringUtils.isNotBlank(doctosAQuitarNoral)) {
						idDoctosQuitar += doctosAQuitarNoral;
					}
				}
			} else {//si es registro de conyuge
				//Se valida si tienen el mismo sexo el asegurado y el o la conyuge a registrar
				Boolean mismoSexo = registro.getFisica().getSexo().getIdSexo().equals(registro.getDatosAsegurado().getSexo().getIdSexo());
				//se setea un separador
				String separador = StringUtils.isBlank(idDoctosQuitar) ? "" : ",";
				//id del acta de matrimonio
				String idActaMatrimonio = "" + DocumentosEnum.ACTA_MATRIMONIO.getId();
				//id del acta de pacto de solidaridad civil
				String idPactoSolidaridad = "" + DocumentosEnum.ACTA_PACTO_CIVIL.getId();
				//si es el mismo sexo 
				if(mismoSexo) {
					//si son del mismo sexo se quita el acta de matrimonio
					idDoctosQuitar += ""+separador+idActaMatrimonio;
				} else {
					//si son de diferente sexo de quita el acta del pacto de solidaridad civil
					idDoctosQuitar += ""+separador+idPactoSolidaridad;
				}
			}
		}
		
		doctosAQuitar.put("doctos", idDoctosQuitar);
		doctosAQuitar.put("categorias", idCategoriasQuitar);
		
		return doctosAQuitar;
	}
	
	@SuppressWarnings("unchecked")
	private List<Modalidad> obtenerModalidadesPatrones(HttpSession session) {
		//obtenemos los patrones de la session
		List<SujetoObligado> sujetos = (List<SujetoObligado>) session.getAttribute("patrones");
		//obtenemos la cabeza de grupo familiar de la session
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		//creamos la lista de modalidades
		List<Modalidad> modalidades = new ArrayList<Modalidad>();
		
		//si los patrones no son nulos, añadiremos sus modalidades
		if(sujetos != null && !sujetos.isEmpty()) {
			for(SujetoObligado sujeto: sujetos) {
				modalidades.add(sujeto.getModalidad());
			}
		} else {
			SujetoObligado sujetoO = cabeza.getPatronSujetoObligado();
			//si los patrones son nulos o vacios verificamos si la cabeza de grupo familiar tiene patron
			if(sujetoO != null && sujetoO.getModalidad() != null && sujetoO.getModalidad().getIdModalidad() != null) {
				//si tiene patron anadimos la modalidad dle patron del ultimo movimiento
				modalidades.add(cabeza.getPatronSujetoObligado().getModalidad());
			}
		}
		
		return modalidades;
	}
	
	private List<Long> getModalidadesActivas(List<Modalidad> modalidades) {
		List<Long> idsModalidades = new ArrayList<Long>();
		
		//verificamos si las modalidades vienen distintas de nulas y que no sean vacias
		if(modalidades == null || !modalidades.isEmpty()) {
			//si no son vacias agregamos los ids en la lista
			for(Modalidad mod: modalidades) {
				idsModalidades.add(mod.getIdModalidad());
			}
		}
		
		return idsModalidades;
	}
	
	@RequestMapping(value = "/finalizaSolicitud", method = RequestMethod.POST)
	public Object finalizaSolicitudRegistro(@ModelAttribute TramiteRegistroDerechohabiente registro, HttpServletRequest request,HttpSession session, Model model) {
		
		Boolean existeError = false;
		String exception = null;
		String error = null;
		
		ImpresionReporteDto reporte = new ImpresionReporteDto();
		Solicitud solicitudActiva = (Solicitud) session.getAttribute(KEY_SOLICITUD_ACTIVA);
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		TramiteRegistroDerechohabiente tramiteXml = TramiteUtil.getTramiteRegistroFromSolicitud(solicitudActiva);
		//objeto que guardara al integrante afectado
		GrupoFamiliar nuevoIntegrante = null;
		//Seteamos el medico que se selecciono en la pantalla
		tramiteXml.setMedicoEnTurno(registro.getMedicoEnTurno());
		//seteamos la fecha de cambio de consultorio
		tramiteXml.setFechaCambioMedico(registro.getFechaCambioMedico());
		//seteamos las observaciones
		tramiteXml.setObservacion(registro.getObservacion());
		//seteamos el indicador
		tramiteXml.setIndSeleccionMedico(registro.getIndSeleccionMedico());
		//verificamos si la persona se va a crear
		Boolean nuevaPersona = tramiteXml.getFisica().getIdPersona() == null;
		//verificamos si es patron IMSS
		Boolean patronIMSS = cabezaGrupo.getPatronImss() != null ? cabezaGrupo.getPatronImss().intValue() == 1 : false;
		
		//Seteamos los nuevos valores del tramite en la solicitud
		solicitudActiva.setTramites(new ArrayList<Tramite>());
		solicitudActiva.getTramites().add(tramiteXml);
		
		try {
			
			//DerechohabientesBusinessException.throwException("Error provocado", "prueba para verificar que redireccione a la pagina de error");
			List<Modalidad> modalidades = this.obtenerModalidadesPatrones(session);
			//obtenemos el acta
			Nacimiento acta = tramiteXml.getFisica().getActaNacimiento();
			//verificamos si la curp no esta vacia
			if(StringUtils.isNotBlank(tramiteXml.getFisica().getCurp())) {
				if(acta != null) {
					acta.setFechaSuceso(tramiteXml.getFisica().getFechaNacimiento());
				}
			} else {
				//En caso de que la curp este vacia seteamos el acta en null
				acta = null;
				//y los documentos probatorios de la persona en null tambien
				tramiteXml.getFisica().setDocumentosProbatorios(null);
			}
			
			//finalizamos la solicitud de registro de derechohabiente
			Map<String, Object> result = registroDerechohabienteServiceRemote.guardarRegistroDerechohabiente(solicitudActiva, cabezaGrupo, modalidades);
			//obtenemos la solicitud del map de resultado
			solicitudActiva = (Solicitud) result.get("solicitud");
			//obtenemos al integrante guardado
			nuevoIntegrante = (GrupoFamiliar) result.get("grupo");
			//obtenemos el XML del tramite
			tramiteXml = TramiteUtil.getTramiteRegistroFromSolicitud(solicitudActiva);
			//volvemos a setear el acta de nacimiento en la persona
			tramiteXml.getFisica().setActaNacimiento(acta);
			
			//se realiza un proceso asincrono para el guardao de medios de contacto 
			log.debug("haciendo la llamada a los asincronos" + new Date());
				procesosAsincronosGrupoFamiliar.guardaDocsMediosMarcaParentescoSimilarRegistro(tramiteXml, nuevaPersona, session);
			log.debug("pasando  la llamada a los asincronos" + new Date());
			
			//actualizamos el xml del tramite para que ya tenga todos los cambios
			solicitudBusinessRemote.actualizarXmlTramite(tramiteXml);
			
			//se guarda la circunscripcion o cambio de clinica
			try {
				Long idParentesco = tramiteXml.getParentesco().getIdParentesco();
				Boolean isConcubina = idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId());
				Boolean isPadre = idParentesco.equals(ParentescoEnum.PADRES.getId());
				//solo se hace la validacion de cambio de clinica cuando no es padre o concubina o cuando es padre y tiene patron imss
				if((!isPadre && !isConcubina) || (isPadre && patronIMSS)) {
					log.debug("Se verificsara si es necesario realizar cambio de clinica a las: " + new Date());
					registroDerechohabienteServiceRemote.guardarCircunscripcionCambioUmf(solicitudActiva, cabezaGrupo, nuevoIntegrante);
				}
			} catch(Exception e) {
				log.error("No fue posible guardar el cambio de clinica o circunscripcion para la persona " + nuevoIntegrante.getDerechohabiente().getIdPersona() + " "
						+ " en el tramite " + tramiteXml.getTramiteId());
				e.printStackTrace();
			}
			
			//Se actualiza la solicitud a concluida
			solicitudBusinessRemote.actualizaAConcluida(solicitudActiva);
			
		
			log.debug("finalizo el tramite de registro a las " +  new Date());
		} catch(ImpactaAlmacenesWSException e) {
			existeError = true;
			exception= "Ocurri&oacute; un error al calcular la vigencia";
			/*if(e.getCodigo() != null) {
				exception += " (" +e.getCodigo() + " , " + e.getSituacion() + " ).";
			}*/
			error = "Int&eacute;ntelo m&aacute;s tarde retomando la solicitud, en caso de ser el <strong>registro" +
					" del asegurado o pensionado</strong> seleccione la opci&oacute;n de <strong>'Registro'</strong> nuevamente, " +
					"si la solicitud es de registro de alg&uacute;n beneficiario" +
					" es necesario que en la secci&oacute;n de <strong>'Solicitudes Registradas'</strong> ubique la solicitud " +
					"de registro y de clic en el bot&oacute;n <strong>'Detalle'</strong>, una " +
					"vez que el detalle se despliegue deber&aacute; dar clic en el bot&oacute;n <strong>Validar tr&aacute;mite</strong>. ";
			
		} catch(SolicitudEnProcesoException e) {
			existeError = true;
			exception="Solicitud procesada";
			error=  e.getSituacion();
		}catch (DerechohabientesBusinessException e) {
			existeError = true;
			exception = e.getMessage();
			error =  e.getSituacion();
		} catch (SolicitudNoEncontradaException e) {
			existeError = true;
			exception = e.getMessage();
			error = e.getSituacion();
		} catch (TramiteNoEncontradoException e) {
			existeError = true;
			exception = e.getMessage();
			error = e.getSituacion();
		}  catch (SolicitudNoValidaException e) {
			existeError = true;
			exception = e.getMessage();
			error = e.getSituacion();
		} catch (SolicitudException e) {
			existeError = true;
			exception = e.getMessage();
			error = e.getSituacion();
		} catch(Exception e) {
			existeError = true;
			exception = e.getMessage();
		}
		
		//si no existe error redirecciondamos a la pagina de finalizado
		//hacemos redireccion para que en dado caso de que refresquen la pantalla
		//la peticion a BD y demas no se vuelva a hacer
		if(!existeError) {
			reporte.setIdPersona(tramiteXml.getFisica().getIdPersona());
			reporte.setIdTramite(tramiteXml.getTramiteId());
			reporte.setRechazado(false);
			reporte.setTipoTramite(tramiteXml.getTipoTramite());
			
			//los ponemos en session ya que al hacer el redireccionamiento 
			//Se sobreescribe el recuest y el model
			session.setAttribute("reporte", reporte);	
			session.setAttribute("solicitud", solicitudActiva);
			
			return new RedirectView("/solicitud/finalizada", true);
		} else {
			
			session.setAttribute("mostrarBoton", true);
			session.setAttribute("exception", exception);
			session.setAttribute("error", error);
			
			return new RedirectView("/solicitud/errorFinalizado", true);
		}
	}
	
	private void validarRequerimientoDocumentos(Model model, TramiteRegistroDerechohabiente registro) {
		Boolean requiereDocumentos = false;
		
		try {
			//requiereDocumentos = documentoProbatorioServiceBusinessRemote.requiereDocumentos(registro.getTipoTramite().getIdTipoTramite().longValue());
			requiereDocumentos = fileUploadController.requiereDocumentosTramite(model, registro.getTipoTramite().getIdTipoTramite());
		} catch (Exception e) {
			log.error("Ocurrio un error al consultar si el tramite requiere documentos",e);
		}
		
		if(requiereDocumentos) {
			Map<String, String> doctosAQuitar = this.quitarTiposDocumentos(registro);
			//Se quitan los documentos que no sean necesarios
			model.addAttribute("doctosNoMostrar",doctosAQuitar.get("categorias"));
			model.addAttribute("documentosAQuitar", doctosAQuitar.get("doctos"));
			//model.addAttribute(Constants.KEY_REQUIERE_DOCS, requiereDocumentos);
		}
	}
	
	@RequestMapping("/combo/parentesco")
	public @ResponseBody Map<String,Object> getParentescosPermitidos(HttpSession session) {
		Map<String,Object> result = new HashMap<String, Object>();
		
		//Se recupera de la session el nss
		AsignacionNSS nss = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		List<Parentesco> parentescos = registroDerechohabienteServiceRemote.getListaParentescoDisponiblesPorIdAsignacionNSS(nss.getIdAsignacionNSS());
		
		result.put("parentesco", parentescos);
		
		return result;
	}
	
	@RequestMapping("/combo/razonRegistro")
	public @ResponseBody Map<String,Object> getRazonRegistro(@RequestBody TramiteRegistroDerechohabiente registro) {
		Map<String,Object> result = new HashMap<String, Object>();
		
		Long idRazonRegistro = registro.getRazonRegistro() != null ? (registro.getRazonRegistro().getIdRazonRegistro() != null ? 
				registro.getRazonRegistro().getIdRazonRegistro() : null) : null;
		Long idParentesco = registro.getParentesco() != null ? (registro.getParentesco().getIdParentesco() != null ? registro.getParentesco().getIdParentesco() : null): null;
		
		List<RazonRegistro> razones = registroDerechohabienteServiceRemote.getListaRazonRegistro(idRazonRegistro, idParentesco);
		
		result.put("razonRegistro", razones);
		
		return result;
	}
	
	@RequestMapping(value = "/validaciones/datosPersonales", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarDatosPersonales(final @RequestBody TramiteRegistroDerechohabiente oForm, final HttpServletResponse response, final HttpSession session) {
        log.trace("entramos para validar los datos personales del registro");

        Map<String, Object> result = new HashMap<String, Object>();
        final Errors errors = new BindException(oForm, "model");
        
        new RegistroDerechohabientesValidator().validate(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        } 
        
        return result;
	}

	@RequestMapping(value = "/validaciones/datosAdscripcion", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarDatosUmf(final @RequestBody TramiteRegistroDerechohabiente oForm, final HttpServletResponse response, final HttpSession session) {
        log.trace("entramos a validar los datos de adscripcion");

        Map<String, Object> result = new HashMap<String, Object>();
        final Errors errors = new BindException(oForm, "model");
        
        new RegistroDerechohabientesValidator().validaDatosAdscripcion(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        } 
        
        return result;
	}
	
	/**
	 * 
	 * @param asignacionNss
	 * @return
	 */
	private Boolean aseguradoRegistrado(AsignacionNSS asignacionNss) {
		Boolean existeAsegurado = false;
		//Si encontramos el nss y la cabeza de grupo familiar verificaremos si el asegurado esta registrados
		List<Long> parentescosAsegurados = new ArrayList<Long>();
		parentescosAsegurados.add(ParentescoEnum.ASEGURADO.getId());
		parentescosAsegurados.add(ParentescoEnum.PENSIONADO.getId());
		
		//se valida si ya cuenta con registro como derechohabiente
		existeAsegurado = !grupoFamiliarServiceRemote.getNumeroDeIntegrantesPorListParentesco(asignacionNss.getIdAsignacionNSS(), parentescosAsegurados).equals(0L);
		
		return existeAsegurado;
	}
	
	/**
	 * Metodo para obtener si las validaciones son correctas
	 * @param validaciones
	 * @return
	 */
	private Boolean getEstadoValidaciones(Map<String, Object> validaciones) {
		Boolean correcto = false;
		
		correcto = (Boolean) validaciones.get(KEY_ESTADO_VALIDACION);
		
		return correcto;
	}
	
	/**
	 * Metodo para llenar el Tramite de registro cuando se crea uno nuevo
	 * @param asignacionNss
	 * @param cabeza
	 * @param usuario
	 * @return
	 */
	private TramiteRegistroDerechohabiente llenarTramiteRegistro(AsignacionNSS asignacionNss, CabezaGrupoFamiliar cabeza, 
			Usuario usuario, GrupoFamiliar asegurado,HttpSession session) {
		TramiteRegistroDerechohabiente registro = new TramiteRegistroDerechohabiente();
		TipoTramite tipoTramite = null;
		//Establecemos la razon de registro
		RazonRegistro razonRegistro = new RazonRegistro();
		Boolean existeAsegurado = false;
		Domicilio domicilioRegistro = null;
		
		registro.setDatosAsegurado(asignacionNss);
		registro.setUsuario(usuario);
		
		//Se crea un nuevo tramite
		existeAsegurado = this.aseguradoRegistrado(asignacionNss);
		//Si ya existe el asegurado 
		if(!existeAsegurado){
			tipoTramite = new TipoTramite();
			tipoTramite.setIdTipoTramite(this.getTipoTramitePorParentesco(cabeza.getCalidadParentesco().getIdParentesco()));
			
			Fisica fisica = this.getPersonaConMedios(asignacionNss.getIdPersona());
			registro.setFisica(fisica);
			registro.setParentesco(cabeza.getCalidadParentesco());
			if(fisica.getFechaNacimiento() == null) {
				registro.setConFechaNacimiento(0);
			}
			razonRegistro.setIdRazonRegistro(RazonRegistroEnum.NORMAL.getId());
			PersonaDomicilio personaDomicilio = null;
			try {
				personaDomicilio = registroDerechohabienteServiceRemote.getPersonaDom(asignacionNss.getIdPersona(), TipoDomicilioEnum.PARTICULAR.getCodigo());
			} catch (DerechohabientesBusinessException e) {
				log.error("Ocurrio un error al consultar el domicilio de la persona",e);
			} catch (Exception e) {
				log.error("Ocurrio un error al consultar el domicilio", e);
			}
			
			if(personaDomicilio != null && personaDomicilio.getDomicilio() != null) {
				domicilioRegistro = personaDomicilio.getDomicilio();
			}
		} else {
			
			if(asegurado == null || !asegurado.getDerechohabiente().getIdPersona().equals(asignacionNss.getIdPersona())) {
				try {
					asegurado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNss.getIdAsignacionNSS(), asignacionNss.getIdPersona());
				} catch (Exception e) {
					log.error("Ocurrio un error al obtener al asegurado", e);
				}
			}
			
			if(asegurado != null) {
				domicilioRegistro = asegurado.getDomicilio();
				session.setAttribute(KEY_DOMICILIO_ASEGURADO_SESSION, domicilioRegistro);
			}
		}
		
		registro.setDomicilio(domicilioRegistro);
		registro.setRazonRegistro(razonRegistro);
		registro.setTipoTramite(tipoTramite);
		
		return registro;
	}
	
	/**
	 * Validaciones de estado de la cabeza de grupo familiar
	 * @param cabeza
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String,Object> validacionesEstadosValidos(CabezaGrupoFamiliar cabeza) {
		
		Map<String,Object> result = new HashMap<String, Object>();
		
		Boolean correcto = true;
		String mensaje = "";
		
		List<Long> listaEstadosNoValidos = new ArrayList<Long>();
		listaEstadosNoValidos.add(EstadoDerechohabienteEnum.BAJA.getId());
		//si es asegurado e3el estado fallecido sera invalido tambien, para pensionados no hay problema con el 
		//Estado fallecido
		if(cabeza.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId())) {
			listaEstadosNoValidos.add(EstadoDerechohabienteEnum.FALLECIDO.getId());
		}
		
		Long idEstadoCabeza = cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente();
		
		if(listaEstadosNoValidos.contains(idEstadoCabeza)) {
			correcto = false;
			mensaje = "El asegurado / pensionado no cuenta con un estado v&aacute;lido para realizar el registro";
		}
		
		result.put(KEY_ESTADO_VALIDACION, correcto);
		result.put(KEY_MENSAJE_VALIDACION, mensaje);
		
		return result;
	}
	
	
	private Integer getTipoTramitePorParentesco(Long idParentesco) {
		Integer tipoTramite = 0;
		//Verificamos si el parentesco es MADRE de ser asi, lo cambiamos por padres
		idParentesco = idParentesco.equals(ParentescoEnum.MADRE.getId()) ? ParentescoEnum.PADRES.getId() : idParentesco;
		//verificamos si el parentesco es concubia
		idParentesco = idParentesco.equals(ParentescoEnum.CONCUBINA.getId()) ? ParentescoEnum.CONCUBINARIO.getId() : idParentesco;
		
		if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId())) {
			tipoTramite = TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo();
		} else if(idParentesco.equals(ParentescoEnum.PENSIONADO.getId())) {
			tipoTramite = TipoTramiteEnum.REGISTRO_PENSIONADO.getCodigo();
		}else if(idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
			tipoTramite = TipoTramiteEnum.REGISTRO_HIJOS.getCodigo();
		}else if(idParentesco.equals(ParentescoEnum.CONYUGE.getId())) {
			tipoTramite = TipoTramiteEnum.REGISTRO_CONYUGUE.getCodigo();
		}else if(idParentesco.equals(ParentescoEnum.PADRES.getId())) {
			
			tipoTramite = TipoTramiteEnum.REGISTRO_PADRES.getCodigo();
		}else {
			tipoTramite = TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo();
		}
		
		return tipoTramite;
	}
	
	/**
	 * Metodo para buscar 
	 * @param asignacion
	 * @return
	 */
	private Fisica getPersonaConMedios(Long idPersona){
		Fisica fisica = null;
		//Obtenemos los datos personales que ya se tienen del asegurado/pensionado
		try {
			
			fisica =serviciosPersonaBusinessRemote.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
			
			if(fisica != null) {
				this.procesarMedios(fisica);
			}
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error("No se encontro al asegurado pensionado",e);
		}
		
		return fisica;
	}
	
	/**
	 * LLena los medios de contacto de la persona
	 * @param fisica
	 */
	private void procesarMedios(Fisica fisica) {

		Object[] lista =  fisica.getMediosContacto().toArray();
		
		if(lista != null && lista.length != 0) {
			log.debug("La lista no esta vacia");
			for(Object medio :  lista) {
				MedioContacto medioA = (MedioContacto) medio;
				
				if(medioA instanceof TelefonoFijo){
					
					TelefonoFijo telefonoFijo = (TelefonoFijo)medioA;
					fisica.setTelefonoFijo(telefonoFijo);
					log.debug("se encontro telefono fijo" + fisica.getTelefonoFijo());
				}else if ( medioA instanceof TelefonoMovil){

					TelefonoMovil telefonoMovil = (TelefonoMovil)medioA;
					fisica.setTelefonoMovil(telefonoMovil);

					log.debug("se encontro telefono movil" + fisica.getTelefonoMovil());
				}else if (medioA instanceof CorreoElectronico){
					CorreoElectronico correoElectronico = (CorreoElectronico) medioA;
					fisica.setCorreoElectronico(correoElectronico);
					
					log.debug("se encontro correo" + fisica.getCorreoElectronico());
				}else if (medioA instanceof Facebook){
					Facebook facebook = (Facebook) medioA;
					fisica.setFacebook(facebook);

					log.debug("se encontro facebook" + fisica.getFacebook());
				}else if (medioA instanceof Twitter){
					Twitter twitter = (Twitter) medioA;
					fisica.setTwitter(twitter);
					
					log.debug("se encontro twitter" + fisica.getTwitter());
				} 
			}
		}
	}
}