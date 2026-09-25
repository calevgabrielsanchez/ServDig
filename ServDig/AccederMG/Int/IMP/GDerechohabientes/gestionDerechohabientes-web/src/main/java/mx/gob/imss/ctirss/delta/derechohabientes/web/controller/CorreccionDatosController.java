package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.AsignacionDomicilioServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GestionDocumentalServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RequisitosDTO;
import mx.gob.imss.ctirss.delta.model.enums.DocumentosEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

@Controller
@RequestMapping( value = "/derechohabiente/correccion/datosPersonales/")
public class CorreccionDatosController extends AbstractController{
	
	@Autowired
	private CorreccionDerechohabienteServiceRemote correccionDerechohabienteServiceRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	@Autowired
	private GestionDocumentalServiceRemote gestionDocumental;
	@Autowired
	private TramiteDocumentosServiceRemote tramiteDocumentosService;
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	@Autowired
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	@Autowired
	private ProcesosAsincronosGrupoFamiliar procesosAsincronosGrupoFamiliar;
	@Autowired
	private BajaDerechohabienteServiceRemote bajaDerechohabienteService;
	@Autowired
	private AsignacionDomicilioServiceRemote asignacionDomicilioServiceRemote;
	@Autowired
	private CatalogosServiceRemote catalogosServiceRemote;
	@Autowired
	private FileUploadController fileUploadController;
	
	//Variables alojadas en sesion
	private static final String SOLICITUD_KEY = "datosSolicitudSession";
	//variable de session
	private static final String KEY_SESSION_INTEGRANTE_CORRECCION = "datosIntegranteCorreccionSession";
	//variable para saber si es patron
	private static final String KEY_SESSION_IS_APRL = "isAseguradoPAtronORL";
	
	@RequestMapping( value = "/")
	public String correccionDatosHome(Model model,HttpServletRequest request, HttpSession session) {
		
		session.removeAttribute(KEY_SESSION_INTEGRANTE_CORRECCION);
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		//lista de candidatos a correccion de datos
		List<GrupoFamiliar> candidatos = null;
		try {
			//buscamos a los candidatos a correccion
			candidatos = correccionDerechohabienteServiceRemote.findGrupoFamiliarCorreccion(asignacionNSS,usuario);
			//DerechohabientesBusinessException.throwException("Ocurrio un error al consultar a los candidatos");
			//mandamos el indicador de patron imss a la vista
			model.addAttribute("patronImss",cabezaGrupo.getPatronImss());
			//seteamos los candidatos a correccion
			model.addAttribute("candidatos", candidatos);
			//mandamos la descripcion
			model.addAttribute("descripcionTipoTramite","CORRECCIÓN DE DATOS");
			model.addAttribute("idTipoTramite",1);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			model.addAttribute("descripcionTipoTramite","CORRECCIÓN DE DATOS");
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e){
			e.printStackTrace();
			model.addAttribute("descripcionTipoTramite","CORRECCIÓN DE DATOS");
			request.setAttribute("errores", e.getMessage());
		}
		
		return Constants.LISTA_CORRECCION_FORWARD;
	}
	
	/**
	 * Muestra los datos actuales del derechohabiente y permite capturar ó modificarlos. 
	 * 
	 * @param idDerechohabiente
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/datos_n/{idDerechohabiente}")
	public String correccionDerechohabienteNuevo(@PathVariable(value = "idDerechohabiente") Long idDerechohabiente,
			Model model, HttpSession session, HttpServletRequest request) {
		
		this.inicioCorreccion(idDerechohabiente, model, session, request);
		
		return "datosCorreccionDerechohabienteView";
	}
	
	/**
	 * Muestra los datos actuales del derechohabiente y permite capturar ó modificarlos. 
	 * 
	 * @param idDerechohabiente
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/datos/{idDerechohabiente}")
	public String correccionDerechohabiente(@PathVariable(value = "idDerechohabiente") Long idDerechohabiente,
			Model model, HttpSession session, HttpServletRequest request) {
		
		this.inicioCorreccion(idDerechohabiente, model, session, request);
		
		return Constants.CAMBIO_DATOS_FORWARD;
	}
	
	private void inicioCorreccion(Long idDerechohabiente,
			Model model, HttpSession session, HttpServletRequest request) {
		try {
			
			AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
			
			//verificamos si la persona tiene algun rol dentro del instituto
			Boolean existeComoAseguradoPatron = grupoFamiliarService.esAseguradoOPatronORepresentanteLegal(idDerechohabiente);
			
			//buscamos al integrante afectado por la correccion
			GrupoFamiliar integranteAfectado = grupoFamiliarService.getIntegranteGrupoFamiliarPorIdPersona(asignacionNSS.getIdAsignacionNSS(), idDerechohabiente);
			
			
			integranteAfectado = this.obtenerMediosDeContacto(integranteAfectado);
			
			//transformamos al derechohabientes en un objeto correccion
			TramiteCorreccionDerechohabiente correccion = TramiteUtil.convetirGrupoCorreccion(integranteAfectado);
			correccion.setTipoTramite(new TipoTramite());
			correccion.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CORRECCION_DATOS.getCodigo());
			TramiteCorreccionDerechohabiente datosActuales = TramiteUtil.convetirGrupoCorreccion(integranteAfectado);
			
			// ----------------------------------------------------------------------------------------------------------
			// Validamos el tipo de baja, por que ahora los hijos en baja(cualquier baja) pueden corregir sus datos
			// ----------------------------------------------------------------------------------------------------------
			//boolean enBajaAdministrativa = bajaDerechohabienteService.tieneBajaAdministrativaActiva(asignacionNSS.getIdAsignacionNSS(), idDerechohabiente);
			//Se cambia la validacion de baja administrativa por suspension asi que se hace contra el estado 
			boolean enBajaAdministrativa = false;
				if(integranteAfectado.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().longValue() ==
						SubestadoDerechohabienteEnum.SUSPENCION_ADMINISTRATIVA.getId()){
					enBajaAdministrativa = true;
				}
			
			session.setAttribute(KEY_SESSION_INTEGRANTE_CORRECCION, integranteAfectado);
			session.setAttribute(KEY_SESSION_IS_APRL, existeComoAseguradoPatron);
			
			this.quitarAgregarDocumentos(model, datosActuales, correccion);
			correccion.setDomicilioAnterior(correccion.getDomicilio());
			model.addAttribute("datosActuales", datosActuales);
			model.addAttribute("derechohabiente", correccion);
			model.addAttribute("hijo", integranteAfectado);
			model.addAttribute("validacion", 0);
			model.addAttribute("asegurado",existeComoAseguradoPatron);
			model.addAttribute("enBajaAdministrativa",enBajaAdministrativa);
			
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping( value = "/guardar", method = RequestMethod.POST)
	public String guardarCorreccion(@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente derechohabiente,
			HttpSession session, HttpServletRequest request, Model model){
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		GrupoFamiliar integranteAfectado = (GrupoFamiliar) session.getAttribute(KEY_SESSION_INTEGRANTE_CORRECCION);
		
		String vista = Constants.CAMBIO_DATOS_FORWARD;
		String lnuevosDocsRequeridos = "";
		
		//En dado caso de que se mandara el parentesco madre, se cambia por padrss
		if(derechohabiente.getParentesco()!= null && derechohabiente.getParentesco().getIdParentesco().equals(ParentescoEnum.MADRE.getId())){
			derechohabiente.getParentesco().setIdParentesco(ParentescoEnum.PADRES.getId());
		}
		
		try {
			
			//verificamos si la perosna cuenta con algun rol dentro del instituto
			Boolean existe = (Boolean) session.getAttribute(KEY_SESSION_IS_APRL);
			
			//verificanos si el afectado no estaba en session o si el id de la persona no corresponde con el del tramite se vuelve a consultar
			if(integranteAfectado == null || !integranteAfectado.getDerechohabiente().getIdPersona().equals(derechohabiente.getIdPersona())) {
				integranteAfectado = grupoFamiliarService.getIntegranteGrupoFamiliarPorIdPersona(asignacionNSS.getIdAsignacionNSS(), derechohabiente.getIdPersona());
				integranteAfectado = this.obtenerMediosDeContacto(integranteAfectado);
			}
			// -----------------------------------------------------------------------
			// En caso de no indicar la UMF, se asigna la clínica en la que se
			// realiza el trámite para que se puede retomar
			// -----------------------------------------------------------------------
			derechohabiente.setMedicoEnTurno(this.getDatosMedicoParaCita(usuario));
			
			//Se crea la solicitud
			Solicitud solicitud = correccionDerechohabienteServiceRemote.saveCorreccionDatosDerechohabiente(
					derechohabiente.getIdPersona(), integranteAfectado,usuario, asignacionNSS, derechohabiente,OrigenSolicitudEnum.VENTANILLA, null);
			
			//se construyen los objetos que se mandan a la vista
			TramiteCorreccionDerechohabiente correccion = TramiteUtil.obtenerCorreccion(solicitud);
			TramiteCorreccionDerechohabiente datosActuales = TramiteUtil.convetirGrupoCorreccion(integranteAfectado);
			
			try {
				lnuevosDocsRequeridos = this.documentosRequeridos(datosActuales, correccion);
			} catch (Exception e) {
				log.error(e);
			}
			
			if(correccion.getParentesco() != null) {
				correccion.setParentesco(grupoFamiliarService.obtenerParentesco(correccion.getParentesco().getIdParentesco()));
			}
			
			
			
			model.addAttribute("lnuevosDocsRequeridos",lnuevosDocsRequeridos);
			model.addAttribute("datosActuales", datosActuales);
			model.addAttribute("derechohabiente", correccion);
			model.addAttribute("hijo", integranteAfectado);
			model.addAttribute("asegurado",existe);
			//agregar al derechohabiente
			model.addAttribute("validacion", 1);
			model.addAttribute("solicitud",solicitud);
			model.addAttribute("tipoDocsNoMostrar", this.quitarTiposDocumentos(correccion));
			model.addAttribute("idsDocsNoMostrar", "");
			
			session.setAttribute(SOLICITUD_KEY, solicitud);
			
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errores", e.getMessage());
		}
		
		return vista;
	}

	// -----------------------------------------------------------------------
	// En caso de no indicar la UMF, se asigna la clínica en la que se
	// realiza el trámite para que se puede retomar
	// -----------------------------------------------------------------------
	private MedicoEnTurno getDatosMedicoParaCita(Usuario usuario) {
		// -----------------------------------------------------------------------
		// En caso de no indicar la UMF, se asigna la clínica en la que se
		// realiza el trámite para que se puede retomar
		// -----------------------------------------------------------------------
		MedicoEnTurno medico = new MedicoEnTurno();
		UnidadMedicaFamiliar unidadMedica = new UnidadMedicaFamiliar();
		unidadMedica.setIdUMF(usuario.getIdUmf());
		medico.setUnidadMedicaFamiliar(unidadMedica);
		Turno turno = new Turno();
		turno.setIdTurno(1L);
		medico.setTurno(turno);
		
		return medico;
	}
	/**
	 * Metodo para iniciar la validacion del tramite de correccion de datos del derechohabiente
	 * @param idTramite
	 * @param model
	 * @param request
	 * @param session
	 * @return vista a la cual se redigira
	 */
	@RequestMapping( value = "/validar/{idSolicitud}")
	public String validarCorreccionDerechohabiente(
			@PathVariable("idSolicitud") Long idSolicitud,
			Model model, HttpServletRequest request, HttpSession session){
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		Solicitud solicitudCorreccion = null;
		TramiteCorreccionDerechohabiente tramiteCorreccion = null;
		GrupoFamiliar integranteAfectado = null;
		
		
		
		try {
			solicitudCorreccion = correccionDerechohabienteServiceRemote.inicioValidacionCorreccion(idSolicitud,asignacionNSS.getIdAsignacionNSS());
			//se obtiene el tramite de correccion de la solicitud
			tramiteCorreccion = TramiteUtil.obtenerCorreccion(solicitudCorreccion);
			//Se obtienen los datos del integrante a validar
			integranteAfectado = grupoFamiliarService.getIntegranteGrupoFamiliarPorIdPersona(asignacionNSS.getIdAsignacionNSS(), 
					tramiteCorreccion.getIdPersona());
			//verificamos si la persona cuenta con algun rol dentro del instituto
			Boolean existe = grupoFamiliarService.esAseguradoOPatronORepresentanteLegal(tramiteCorreccion.getIdPersona());
			//convertimos el objeto en un objeto correccion para mandarlo a la vista
			TramiteCorreccionDerechohabiente datosActuales = TramiteUtil.convetirGrupoCorreccion(integranteAfectado);
			//checamos el parentesco
			if(tramiteCorreccion.getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())
					&& tramiteCorreccion.getSexo().getIdSexo().equals(SexoEnum.MUJER.getId())){
				tramiteCorreccion.getParentesco().setIdParentesco(ParentescoEnum.MADRE.getId());
			}
			
			//seteamos el catalogo de parentescos
			if(tramiteCorreccion.getParentesco() != null) {
				tramiteCorreccion.setParentesco(grupoFamiliarService.obtenerParentesco(tramiteCorreccion.getParentesco().getIdParentesco()));
			}
			
			
			this.quitarAgregarDocumentos(model, datosActuales, tramiteCorreccion);
			model.addAttribute("derechohabiente",tramiteCorreccion);
			model.addAttribute("solicitud",solicitudCorreccion);
			model.addAttribute("validacion", 1);
			model.addAttribute("datosActuales", datosActuales);
			model.addAttribute("hijo", integranteAfectado);
			model.addAttribute("asegurado",existe);
			
			
			session.setAttribute(SOLICITUD_KEY, solicitudCorreccion);
			session.setAttribute(KEY_SESSION_INTEGRANTE_CORRECCION, integranteAfectado);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("exception", e.getMessage());
			request.setAttribute("error", e.getSituacion());
			return "internalError";
		} catch (Exception e) {
			request.setAttribute("exception", ExceptionMessages.ERROR_ACTUALIZA_SOLICITUD);
			request.setAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
		
		
		return Constants.CAMBIO_DATOS_FORWARD;
	}
	
	
	private void quitarAgregarDocumentos(Model model, TramiteCorreccionDerechohabiente datosActuales, TramiteCorreccionDerechohabiente tramiteCorreccion) {
		String lnuevosDocsRequeridos = "";
		Boolean requiereDocumentos = false;
		//Se checan los documentos que se pediran
		try {
			
			requiereDocumentos = fileUploadController.requiereDocumentosTramite(model, tramiteCorreccion.getTipoTramite().getIdTipoTramite());
			if(requiereDocumentos) {
				lnuevosDocsRequeridos = this.documentosRequeridos(datosActuales, tramiteCorreccion);
				model.addAttribute("lnuevosDocsRequeridos",lnuevosDocsRequeridos);
				model.addAttribute("tipoDocsNoMostrar", this.quitarTiposDocumentos(tramiteCorreccion));
				model.addAttribute("idsDocsNoMostrar", "");	
			}
			//model.addAttribute(Constants.KEY_REQUIERE_DOCS, requiereDocumentos);
		} catch (Exception e) {
			log.error(e);
		}
		
		
		
		
	}
	
	/**
	 * Metodo para guardar la validacion de la correccion de datos de derechohabiente
	 * @param correccion
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/validacion/guardar", method = RequestMethod.POST)
	public Object guardarValidacionCorreccionDatos(
			@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente correccion,
			HttpSession session, HttpServletRequest request, Model model) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		ImpresionReporteDto reporte = new ImpresionReporteDto();
		Solicitud solicitudCorreccion = (Solicitud) session.getAttribute(SOLICITUD_KEY);
		TramiteCorreccionDerechohabiente tramiteCorreccion = TramiteUtil.obtenerCorreccion(solicitudCorreccion);
		GrupoFamiliar afectado = (GrupoFamiliar) session.getAttribute(KEY_SESSION_INTEGRANTE_CORRECCION);
		//lista de padres y concubinas en caso de que el cambio sea para el asegurado
		List<GrupoFamiliar> padresConcubinas = null;
		//lista de personas sin domicilio que tienen que ser cambiadas junto con el asegurado
		List<GrupoFamiliar> personasSinDomcilioEnUMF = null;
		String exception = null;
		String error = null;
		
		try {
			log.debug("El sexo que viene al controller es " + correccion.getSexo());
			
			if(!tramiteCorreccion.getTramiteId().equals(correccion.getTramiteId())) {
				//Seteamos los datos del asignacionNSs en el tramite
				correccion.setIdAsignacionNss( asignacionNSS.getIdAsignacionNSS() );
				correccion.setNss(asignacionNSS.getNssStr());
				//verificamos si el parentesco es madre para pasarlo al generico
				if(correccion.getParentesco().getIdParentesco().equals(ParentescoEnum.MADRE.getId())) {
					correccion.getParentesco().setIdParentesco(ParentescoEnum.PADRES.getId());
				}
				
			} else {
				//Seteamos los datos del asignacionNSs en el tramite
				tramiteCorreccion.setIdAsignacionNss( asignacionNSS.getIdAsignacionNSS() );
				tramiteCorreccion.setNss(asignacionNSS.getNssStr());
				tramiteCorreccion.setObservacion(correccion.getObservacion());
				tramiteCorreccion.setObservaciones(correccion.getObservaciones());
				
				correccion = tramiteCorreccion;
			}
		
			boolean isAsegurado = isAsegurado(afectado);
			boolean cambioDomicilio = correccion.getDomicilio().getClave() == null;
			
			//solo si es el asegurado
			if(isAsegurado && cambioDomicilio) {
				Long idAsignacionNSS = asignacionNSS.getIdAsignacionNSS();
				Long idUmfAsegurado = afectado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF();
				List<Long> idsPersonasExcluir = new ArrayList<Long>();
				idsPersonasExcluir.add(afectado.getDerechohabiente().getIdPersona());
				
				try {
					log.debug("Busco a las concubinas y padre");
					//consultamos a los padres y concubinas
					padresConcubinas = asignacionDomicilioServiceRemote.getPadresConcubinasParaCambio(asignacionNSS, cabezaGrupo.getPatronImss());
					//en caso de que encontremos padres y concubinar los anadiremos en la lista de personas a exluir de la consulta de personas sin domicilio
					if(padresConcubinas != null && !padresConcubinas.isEmpty()) {
						log.debug("Existen " + padresConcubinas.size() + " padres y concubina");
						for(GrupoFamiliar integrante: padresConcubinas) {
							idsPersonasExcluir.add(integrante.getDerechohabiente().getIdPersona());
						}
					}
				} catch(Exception e) {
					log.error("Ocurrio un error al obtener a los padres y concubinas para el nss " + asignacionNSS.getIdAsignacionNSS());
				}
				
				try {
					log.debug("Voy a hacer la consulta de personas sin domicilio en la misma umf");
					personasSinDomcilioEnUMF = asignacionDomicilioServiceRemote.findPersonasSinDomicilioEnUmf(idAsignacionNSS, idUmfAsegurado, idsPersonasExcluir, null);
					
					if(personasSinDomcilioEnUMF != null && !personasSinDomcilioEnUMF.isEmpty()) {
						log.debug("hay " + personasSinDomcilioEnUMF.size() + " que no tienen domicilio y estan en la misma umf que el asegurado");
					}
				} catch (Exception e) {
					log.error("Ocurrio un error al buscar a las personas sin domicilio en la misma umf que el asegurado " + idAsignacionNSS);
				}
			}
			//obtenemos el tipo de tramite para que vaya con la descripcion
			TipoTramite tipoTramite = catalogosServiceRemote.getCatalogoTipoTramite(correccion.getTipoTramite().getIdTipoTramite().longValue());
			//Seteamos el tipo de tramite
			correccion.setTipoTramite(tipoTramite);
			//Seteamos los tramites en la solicitud
			solicitudCorreccion.setTramites(new ArrayList<Tramite>());
			solicitudCorreccion.getTramites().add(correccion);
			//finalizamos el tramite de correccion
			TramiteCorreccionDerechohabiente resultado = correccionDerechohabienteServiceRemote.guardarValidacionCorreccionDatos(solicitudCorreccion, 
					afectado, asignacionNSS,cabezaGrupo,usuario.getFisica(), padresConcubinas, personasSinDomcilioEnUMF);
			
			//se mandan a llamar los procesos sincronos para la correccion
			procesosAsincronosGrupoFamiliar.guardaDocsMediosCorreccion(correccion, resultado, session);
			
			//Objeto para generar el reporte
			reporte.setIdPersona(resultado.getIdPersona());
			reporte.setIdTramite(resultado.getTramiteId());
			reporte.setRechazado(false);
			TipoTramite tipoTram = new TipoTramite();
			tipoTram.setIdTipoTramite(TipoTramiteEnum.CORRECCION_DATOS.getCodigo());
			tipoTram.setDescripcion("Corrección de Datos".toLowerCase());
			reporte.setTipoTramite(tipoTram);
			
			//removemos los datos de la sesion
			this.limpiarSession(session);
			
			log.debug("se hizo la correccion al asegurado? " + isAsegurado + " y hubo cambio de domicilio " + cambioDomicilio);
			/*
			 * Redireccionadamos para evistar que en caso de que se presione f5 o se refresque la pantalla
			 * no se vuelvan a hacer todas las peticiones
			 */
			session.setAttribute("reporte", reporte);
			session.setAttribute("solicitud", solicitudCorreccion);

			return new RedirectView("/solicitud/finalizada", true);
			
		} catch(ImpactaAlmacenesWSException e) {
			log.error("error al actualizar los almcenes",e);
			exception = "Ocurri&oacute; un error al calcular la vigencia. " + e.getMessage();
			
			error = "Int&eacute;ntelo m&aacute;s tarde retomando la solicitud, para esto" +
					" es necesario que en la secci&oacute;n de <strong>'Solicitudes Registradas'</strong> ubique la solicitud " +
					"y de clic en el bot&oacute;n <strong>'Detalle'</strong>, una " +
					"vez que el detalle se despliegue deber&aacute; dar clic en el bot&oacute;n <strong>Validar tr&aacute;mite</strong>. ";
		}catch (DerechohabientesBusinessException e) {
			log.error("error al actualizar los almcenes",e);
			exception = e.getMessage();
			error = e.getSituacion();
		} catch (IllegalArgumentException e) {
			log.error("error al actualizar los almcenes",e);
			exception = e.getMessage();
		} catch (Exception e) {
			log.error("error al actualizar los almcenes",e);
			exception = e.getMessage();
		}
		
		session.setAttribute("mostrarBoton", true);
		session.setAttribute("exception", exception);
		session.setAttribute("error", error);
		
		return new RedirectView("/solicitud/errorFinalizado", true);
	}
	
	private void limpiarSession(HttpSession session) {
		session.removeAttribute(SOLICITUD_KEY);
		session.removeAttribute(KEY_SESSION_INTEGRANTE_CORRECCION);
	}
	
	
	@RequestMapping( value = "/requisitos")
	public @ResponseBody RespuestaJSON<RequisitosDTO> requisitosCorreccion(@RequestBody GrupoFamiliar integrante,Model model,
			HttpServletRequest request, HttpSession session) {
		
		
		RespuestaJSON<RequisitosDTO> respuesta = new RespuestaJSON<RequisitosDTO>();
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		//modalidades
		List<Modalidad> modalidades = this.obtenerModalidadesPatrones(session);
		//ids modalidades
		List<Long> idsModalidades = this.getModalidadesActivas(modalidades);
		try {
			log.debug("mandando a llamar los requisitos minimos ");
			RequisitosDTO requisitos = correccionDerechohabienteServiceRemote.requisitosCorreccion(integrante , asignacionNSS, cabezaGrupo, false, idsModalidades);
			respuesta.setModelo(requisitos);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			RequisitosDTO requisitos = new RequisitosDTO();
			requisitos.setAprobado(Constants.NO_APROBADO);
			requisitos.setMotivo("Ocurrio un error al verificar los requisitos");
			respuesta.setModelo(requisitos);
		} catch (Exception e) {
			e.printStackTrace();
			RequisitosDTO requisitos = new RequisitosDTO();
			requisitos.setAprobado(Constants.NO_APROBADO);
			requisitos.setMotivo("Ocurrio un error al verificar los requisitos");
			respuesta.setModelo(requisitos);
		}
		return respuesta;
	}
	
	/**
	 * Metodo para saber que documentos son obligatorios de acuerdo a los datos que se hayan cambiado
	 * @param datosActuales
	 * @param solicitudCorreccion
	 * @return
	 */
	private String documentosRequeridos(TramiteCorreccionDerechohabiente datosActuales, TramiteCorreccionDerechohabiente solicitudCorreccion) {
		String lnuevosDocsRequeridos = "";
		
		if(datosActuales.getCurpCap()!=null) {
			if(datosActuales.getCurpCap().trim().length() >0) {
				//Cambio Curp
				if(!(datosActuales.getCurpCap().equals(solicitudCorreccion.getCurpCap()))){
					lnuevosDocsRequeridos += DocumentosEnum.CURP.getId().toString()+",";
				}
			}
		}
		//Datos personales para hijos
		if(datosActuales.getParentesco().getIdParentesco().equals(ParentescoEnum.HIJOS.getId())){
			
			if(	datosActuales.getFechaNacimiento().compareTo(solicitudCorreccion.getFechaNacimiento()) != 0 ||
				!(datosActuales.getNombre().equals(solicitudCorreccion.getNombre())) ||
				!(datosActuales.getPrimerApellido().equals(solicitudCorreccion.getPrimerApellido())) ||
				!(datosActuales.getSegundoApellido().equals(solicitudCorreccion.getSegundoApellido())) ||
				!(datosActuales.getSexo().getIdSexo().equals(solicitudCorreccion.getSexo().getIdSexo()))){

				lnuevosDocsRequeridos += DocumentosEnum.ACTA_NACIMIENTO.getId().toString()+","; 
				lnuevosDocsRequeridos += DocumentosEnum.ACTA_RECONOCIMIENTO.getId().toString()+",";
				lnuevosDocsRequeridos += DocumentosEnum.ACTA_ADOPCION.getId().toString()+",";
			}
			
		}
		//Datos domicilio
		if(solicitudCorreccion.getDomicilio().getClave() == null){
			
			lnuevosDocsRequeridos += DocumentosEnum.ESCRITURA_DE_PROPIEDAD_INMOBILIARIA.getId().toString()+",";
			lnuevosDocsRequeridos += DocumentosEnum.PAGO_DE_PREDIAL.getId().toString()+",";
			lnuevosDocsRequeridos += DocumentosEnum.PAGO_DE_TENENCIA_VEHICULAR.getId().toString()+",";
			lnuevosDocsRequeridos += DocumentosEnum.RECIBO_DE_AGUA.getId().toString()+",";
			lnuevosDocsRequeridos += DocumentosEnum.RECIBO_DE_GAS.getId().toString()+",";
			lnuevosDocsRequeridos += DocumentosEnum.RECIBO_DE_LUZ.getId().toString()+",";
			lnuevosDocsRequeridos += DocumentosEnum.RECIBO_DE_TELEFONO.getId().toString()+",";
			
		}
		
		return lnuevosDocsRequeridos;
	}
	
	/**
	 * Si el beneficiario es menor de edad se elimina la identificaci&oacute;n de los
	 * documentos probatorios
	 * 
	 * @param fechaNacimiento Date Fecha de nacimiento del beneficiario
	 * @return
	 */
	private String quitarTipoDocumentosMenoresEdadad(Date fechaNacimiento){
		
		String tiposDocumentos = "";
		
		if(DateUtils.getEdad(fechaNacimiento) < 18 ){
			return String.valueOf(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
		}
		
		return tiposDocumentos;
	}
	
	@SuppressWarnings("unchecked")
	private List<Modalidad> obtenerModalidadesPatrones(HttpSession session) {
		List<SujetoObligado> sujetos = (List<SujetoObligado>) session.getAttribute("patrones");
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		List<Modalidad> modalidades = new ArrayList<Modalidad>();
		
		if(sujetos != null && !sujetos.isEmpty()) {
			for(SujetoObligado sujeto: sujetos) {
				modalidades.add(sujeto.getModalidad());
			}
		} else {
			
			try{
				modalidades.add(cabeza.getPatronSujetoObligado().getModalidad());
			}catch(NullPointerException e){
				// -------------------------------------------
				// El pensionado puede no tener patrón
				// -------------------------------------------
				log.debug("obtenerModalidadesPatrones -> Sujeto obligado NULL" );
			}
		}
		
		return modalidades;
	}
	
	private List<Long> getModalidadesActivas(List<Modalidad> modalidades) {
		List<Long> idsModalidades = new ArrayList<Long>();
		
		if(modalidades == null || !modalidades.isEmpty()) {
		for(Modalidad mod: modalidades) {
			idsModalidades.add(mod.getIdModalidad());
		}
		}
		
		return idsModalidades;
	}
	
	
	/**
	 * Obtiene los medios de contacto para el integrante del grupo familiar.
	 * 
	 * @param integrante
	 * @return
	 */
	private GrupoFamiliar obtenerMediosDeContacto( GrupoFamiliar integrante ){
		
		try{
			List<MedioContacto> mediosContacto = mediosContactoServiceBusinessRemote.consultarMedioDeContactoPersona(integrante.getDerechohabiente());
			  if(mediosContacto != null){ 
				  
				  Derechohabiente derechohabiente = integrante.getDerechohabiente();
				  
				  for(MedioContacto medio: mediosContacto) {
					  if(medio instanceof TelefonoFijo){
	                      TelefonoFijo telefonoFijo = (TelefonoFijo)medio;
	                      telefonoFijo.setClaveLada(telefonoFijo.getClaveLada() == null ? "" : telefonoFijo.getClaveLada().trim());
	                      telefonoFijo.setNumero(telefonoFijo.getNumero() == null ? "" : telefonoFijo.getNumero().trim());
	                      telefonoFijo.setExtension(telefonoFijo.getExtension() == null ? "": telefonoFijo.getExtension().trim());
	                      derechohabiente.setTelefonoFijo(telefonoFijo);
	                  }else if ( medio instanceof TelefonoMovil){
	                          TelefonoMovil telefonoMovil = (TelefonoMovil)medio;
	                          derechohabiente.setTelefonoMovil(telefonoMovil);
	                  }else if (medio instanceof CorreoElectronico){
	                          CorreoElectronico correoElectronico = (CorreoElectronico)medio;
	                          derechohabiente.setCorreoElectronico(correoElectronico);
	                  }else if (medio instanceof Facebook){
	                          Facebook facebook = (Facebook)medio;
	                          derechohabiente.setFacebook(facebook);
	                  }else if ( medio instanceof Twitter){
	                          Twitter twitter = (Twitter)medio;
	                          derechohabiente.setTwitter(twitter);
	                  } 
				  }
				  
				  
				  integrante.setDerechohabiente(derechohabiente);
			  }
		}catch(PersonaSinMedioDeContactoException e){
			log.debug("La persona no tiene medios de contacto");
		}
		
		
		return integrante;
		
	}
	
	/**
	 * Lista los tipos de documentos que no pediran para concluir el trámite
	 * 
	 * @param correccion
	 * @return
	 */
	private String quitarTiposDocumentos( TramiteCorreccionDerechohabiente correccion ){
		
		String idTipoTramites = "";
		
		if(correccion.getFechaNacimiento() != null) {
			idTipoTramites = this.quitarTipoDocumentosMenoresEdadad(correccion.getFechaNacimiento());
		}
		// ------------------------------------------------------------------
		// Si se consulto la CURP y ya tenia acta, no se le piden
		// ------------------------------------------------------------------
		if(correccion.getIndICATeniaActa() == 1 ){
			idTipoTramites+=","+String.valueOf(TipoDocumentoProbatorioEnum.ACTAS.getId());
		}
		
		return idTipoTramites;
	}
	
	/**
	 * Metodo para saber si es asegurado
	 * @param afectado
	 * @return
	 */
	private Boolean isAsegurado(GrupoFamiliar afectado) {
		if(afectado != null) {
			Long idParentesco = afectado.getParentesco().getIdParentesco();
			
			if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId())) {
				return true;
			}
		}
		
		return false;
	}
}
