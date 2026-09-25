/**
 * RegistroDerechohabientesController.java
 * @author JUAN MANUEL MARQUEZ
 * @package mx.gob.imss.ctirss.delta.derechohabientes.web.controller
 * @project derechohabientes-web	
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GestionDocumentalServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.DateUtils;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RegistroDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.ValidacionRegDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorioCaptura;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileUploadVB;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @author Juan Manuel Marquez
 * @company Novutek
 * @date 05/03/2012
 */
@Controller
@RequestMapping(value = "/derechohabientes/*")
public class RegistroDerechohabientesController extends AbstractController {
	
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	@Autowired
	private RegistroDerechohabienteServiceRemote registroDerechohabienteService;
	@Autowired
	private GestionDocumentalServiceRemote gestionDocumental;
	@Autowired
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	@Autowired
	private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;
	
	
	private static final String SESSION_BEAN=FileUploadVB.SES_NAME;
	
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
	
	@RequestMapping(value = "/getDomicilioAsegurado")
	public @ResponseBody Domicilio getDomicilioAsegurado(HttpSession session) {
		Domicilio domicilioAsegurado = (Domicilio) session.getAttribute("domicilioAsegurado");
		
		return domicilioAsegurado;
	}
	
	@RequestMapping(value = "registro")
    public String home(HttpSession session, @ModelAttribute("datos") RegistroDto registro, Model model, HttpServletRequest request) {
				
		String forward=Constants.REGISTRO_DERECHOHABIENTE;		
		int requisito = registro.getRequisito();
		int proceso = 0;		
		
		if(registro.getTramiteRegistro() == null){
			registro.setTramiteRegistro(new TramiteRegistroDerechohabiente());
			registro.getTramiteRegistro().setFisica(new Fisica());
		}
		try {
			registro = datosGenerales(session,registro);
			log.debug("Variable del registro: " + registro.getConAsegurado());
			//registro = registroDerechohabienteService.mediosContactoAsegurado(registro);
		} catch (DerechohabientesBusinessException e) {
			forward = "internalError";
			request.setAttribute("exception",e.getSituacion());							
			log.error("Error reuniendo datos generales del derechohabiente |"+e.getMessage());	
			return forward;
		} catch (Exception e) {
			log.error("Error desconocido", e);
			request.setAttribute("error", e.getCause());
			forward = "internalError";
			request.setAttribute("exception", "exception.general");
			return forward;
		}
		
		
		if(registro.getProceso() == Constants.CORRECCION){		
			
			registro =(RegistroDto) session.getAttribute(RegistroDto.SES_NAME);
			
			// -----------------------------------------------------------------------------
			// Cuando si no fueron aprobados los datos se vuelve a capturar el domicilio
			// -----------------------------------------------------------------------------
			if( requisito != Constants.NO_APROBADO ){
				proceso = registro.getProceso();
			}
			
			registro.setProceso(proceso);
				
			try {
				long parentesco = registro.getTramiteRegistro().getParentesco().getIdParentesco();	
				long razonReg = registro.getTramiteRegistro().getRazonRegistro().getIdRazonRegistro();
				long sexo = registro.getTramiteRegistro().getFisica().getSexo().getIdSexo();
				 
			
				if(parentesco == ParentescoEnum.PADRES.getId() && sexo == SexoEnum.MUJER.getId()){
					registro.getTramiteRegistro().getParentesco().setIdParentesco(ParentescoEnum.MADRE.getId());
				}
				if(parentesco == ParentescoEnum.HIJOS.getId() && razonReg == RazonRegistroEnum.RECIEN_NACIDO.getId()){
					registro.getTramiteRegistro().getFisica().setIdPersona(null);
				}
				
				
				
				
			} catch (Exception e) {
				log.error("Error desconocido", e);
				request.setAttribute("error", e.getCause());
				forward = "internalError";
				request.setAttribute("exception", "exception.general");
				return forward;
			}
			
		}
		
		if(registro.getProceso() == 0){
			registro.setProceso(Constants.CAPTURA);													
		}
			
		
		model.addAttribute("datos",registro);										
        return forward;
    }
	
	
	
	@RequestMapping(value = "registro/confirmacion")
    public String confirmacion(HttpSession session,@ModelAttribute("datos") RegistroDto registro, Model model, HttpServletRequest request) {	
		String forward = "confirmacion";
		
		/*
		RequisitosDTO requisitos = new RequisitosDTO();
		requisitos.setAprobado(Constants.APROBADO);
		requisitos.setMotivo(Constants.MOTIVO_APROBADO);
		
		registro.setRequisitos(requisitos);
		
		try {
			registro = datosGenerales(session,registro);
		} catch (DerechohabientesBusinessException e) {
			forward = "internalError";
			request.setAttribute("exception",e.getSituacion());							
			log.error("Error reuniendo datos generales del derechohabiente |"+e.getMessage());	
			return forward;
		} catch (Exception e) {
			log.error("Error desconocido", e);
			request.setAttribute("error", e.getCause());
			forward = "internalError";
			request.setAttribute("exception", "exception.general");
			return forward;
		}
		
		log.debug("El proceso en el que esta es: " + registro.getProceso());
		if(registro.getProceso() == Constants.EDICION || registro.getProceso() == Constants.CORRECCION ){
			log.debug("esta en edicio correccion");
			
			RegistroDto registroDto = null;
			registroDto = (RegistroDto) session.getAttribute(RegistroDto.SES_NAME);
			if(registroDto == null){				
				forward = "internalError";
				request.setAttribute("exception", Constants.SESSION_ERROR);
				return forward;
			}
			log.debug("El tramite de registro en session es del tipo : " + registroDto.getTramiteRegistro().getTipoTramite().getIdTipoTramite());
			
			registro.getTramiteRegistro().setTramiteId(registroDto.getTramiteRegistro().getTramiteId());
			
		}
		
		log.debug("La razon del registro de registro es: " + registro.getTipoRegistro().getIdRazonRegistro());
		log.debug("El tipo de registro es: " + registro.getTramiteRegistro().getTipoTramite().getIdTipoTramite());
		log.debug("La razon del registro en el tramite es: " + registro.getTramiteRegistro().getRazonRegistro().getIdRazonRegistro());
		
		registro.getTramiteRegistro().setDomicilio(registro.getDomicilio());
		registro.getTramiteRegistro().setUsuario(registro.getMiUsuario());
		
		registro.getTramiteRegistro().setDatosAsegurado(registro.getDatosAsegurado());
		if(registro.getTramiteRegistro().getParentesco().getIdParentesco() == ParentescoEnum.MADRE.getId()){
			registro.getTramiteRegistro().getParentesco().setIdParentesco(ParentescoEnum.PADRES.getId());
		} 
		
		if(registro.getTramiteRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.HIJOS.getId())) {
			registro.getTramiteRegistro().setRazonRegistro(registro.getTipoRegistro());
		}
		
		if(registro.getTramiteRegistro().getFisica().getFechaNacimiento() == null){
			registro.getTramiteRegistro().getFisica().setFechaNacimiento(
					mx.gob.imss.ctirss.delta.derechohabientes.web.utils.DateUtils.stringToDate("dd/MM/yyyy",registro.getTramiteRegistro().getFisica().getFechaNacimientoFormateada()));
		}
		
		log.debug("El tramite de registro es del tipo : " + registro.getTramiteRegistro().getTipoTramite().getIdTipoTramite());
		
		try {
			
			
			RegistroDto registroValidado = registroDerechohabienteService.validaPersonaRegistrada(registro.getTramiteRegistro(), registro.getMiUsuario().getPerfilUsuario().getIdPerfilUsuario());
		
			log.debug("Los requisitos despues de validad a la persona son: " + registroValidado.getRequisitos());
			
			if(registroValidado.getRequisitos() != null) {
				if(registroValidado.getRequisitos().getAprobado() == Constants.APROBADO || registro.getRequisitos().getAprobado() == 0) {
					TramiteRegistroDerechohabiente tramiteRegistro = registroValidado.getTramiteRegistro();
					RequisitosDTO requisit = registroDerechohabienteService.requisitosMinimos(
							tramiteRegistro, tramiteRegistro.getDomicilio(), registro.getSujetoObligado(), registro.getDatosAsegurado(), registro.isPatronImss());
					registroValidado.setRequisitos(requisit);
				}
			} else {
				TramiteRegistroDerechohabiente tramiteRegistro = registroValidado.getTramiteRegistro();
				RequisitosDTO requisit = registroDerechohabienteService.requisitosMinimos(
						tramiteRegistro, tramiteRegistro.getDomicilio(), registro.getSujetoObligado(), registro.getDatosAsegurado(), registro.isPatronImss());
				registroValidado.setRequisitos(requisit);
			}
			
			Fisica fisica = registro.getTramiteRegistro().getFisica();
			registro.setRequisitos(registroValidado.getRequisitos());
			registro.setTramiteRegistro(registroValidado.getTramiteRegistro());
			registro.setErrorBusqueda(registroValidado.getErrorBusqueda());
			registro.getTramiteRegistro().setFisica(registroValidado.getTramiteRegistro().getFisica() != null ? registroValidado.getTramiteRegistro().getFisica() : fisica);
			
		} catch (DerechohabientesBusinessException e) {
			forward = "internalError";
			request.setAttribute("exception", "exception.general");
			request.setAttribute("error",e.getSituacion());							
			log.error("Error reuniendo datos generales del derechohabiente |"+e.getMessage());	
			return forward;
		} catch (Exception e) {
			log.error("Error desconocido", e);
			request.setAttribute("error", e.getCause());
			forward = "internalError";
			request.setAttribute("exception", "exception.general");
			return forward;
		}														
		
		model.addAttribute("datos",registro);
		session.removeAttribute(RegistroDto.SES_NAME);
		session.setAttribute(RegistroDto.SES_NAME, registro);
		
		log.debug("los requisitos que se envian a pantalla son: " + registro.getRequisitos());
		log.debug("Error busqueda: " + registro.getErrorBusqueda());
		
		if(registro.getRequisitos() != null && registro.getErrorBusqueda() != null) {
			registro.getRequisitos().setMotivo(registro.getRequisitos().getMotivo() + (registro.getErrorBusqueda() ? "<br> NO FUE POSIBLE VALIDAR A LA PERSONA EN RENAPO POR LO CUAL SERA CALIFICADO POR EL IMSS" : ""));
		}
		model.addAttribute("requisitos",registro.getRequisitos());		
		request.setAttribute("perfil", registro.getMiUsuario().getPerfilUsuario().getIdPerfilUsuario());		
*/		return forward;		
	}
	
	@RequestMapping( value = "registro/validar/{idSolicitud}/{idTramite}", method = RequestMethod.GET)
	public String validarSolicitud(@PathVariable("idSolicitud") Long idSolicitud,
			 @PathVariable("idTramite") Long idTramite,HttpSession session, HttpServletRequest request,Model model) {
		String forward=Constants.REGISTRO_DERECHOHABIENTE;
		return forward;		
	}
	
	@RequestMapping(value = "registro/cita")
    public String cita(HttpSession session,@ModelAttribute("registro") RegistroDto cancelacion, Model model, HttpServletRequest request) {	
		session.removeAttribute(FileUploadVB.SES_NAME);
		
		String forward = null;	
		
		return forward;
	}
	
	@RequestMapping(value = "registro/actualizar")
	public String actualizar(HttpSession session, HttpServletRequest request,@ModelAttribute("validacion") ValidacionRegDto validacionDTO) {
		String forward = "finalizacionTramite";
		
		return forward;		
	}
	
	@RequestMapping(value = "registro/autorizar/{idTramite}")
	public String autorizar(@PathVariable("idTramite") Long idTramite,
			Model model,HttpSession session, HttpServletRequest request) {
				
		String forward = "finalizacionTramite";

		return forward;
	}
	
	@RequestMapping(value = "registro/rechazar2/{idSolicitud}/{tipoTramite}/{razonRechazo}", method = RequestMethod.GET)
	public String rechazar2(@PathVariable("idSolicitud") Long idSolicitud,@PathVariable("tipoTramite") Long tipoTramite,
			@PathVariable("razonRechazo") Long razonRechazo,HttpSession session, HttpServletRequest request) {
		String forward = "finalizacionTramite";
		
		return forward;		
	}
		
	@RequestMapping( value = "registro/imprimeSolicitud/{idSolicitud}")
	public String imprimeSolicitud(@PathVariable("idSolicitud") Long idSolicitud,HttpSession session, HttpServletRequest request) {
		String forward = "finalizacionTramite";
		
		return forward;
	}
	
	
	
	@RequestMapping( value = "/getOtroAsegurado", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<Boolean> getOtroAsegurado(@RequestBody Fisica fisica) {
		RespuestaJSON<Boolean> respuesta = new RespuestaJSON<Boolean>();
		boolean existe = false;
		Long idPersona = fisica.getIdPersona();				
		CabezaGrupoFamiliar cGF = null;
		
		try {
			List<AsignacionNSS> unosNSS = grupoFamiliarService.getAsignacionNss(idPersona);
			if(unosNSS.size() > 0){
				for(AsignacionNSS unaAsignacion : unosNSS){
					cGF = grupoFamiliarService.cabezaGrupoFamiliar(unaAsignacion.getIdAsignacionNSS());
					if(cGF != null){
						existe = true;
						break;
					}					
				}
			}			
		} catch (DerechohabientesBusinessException e) {
			existe = false;
		}catch (Exception e){
			log.error("ocurrio un error inesperado0",e);
			existe = false;
		}
		respuesta.setModelo(existe);		
		return respuesta;
	}
	
	
	@RequestMapping( value = "/getValidaEdad", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<Long> getValidaEdad(@RequestBody Fisica fisica) {
		RespuestaJSON<Long> respuesta = new RespuestaJSON<Long>();
		Long edad = 0L;
		String fechaNacimientoFormateada = fisica.getFechaNacimientoFormateada();	
		log.debug("Fecha formateada : " + fechaNacimientoFormateada);
		Date unaFecha = DateUtils.stringToDate("dd/MM/yyyy", fechaNacimientoFormateada);
		log.debug("Fecha formateada convertida a Date: " + unaFecha);
		edad = DateUtils.getEdadRedondeadaEnAnios(unaFecha);
		
		respuesta.setModelo(edad);		
		return respuesta;
	}
	
	private RegistroDto datosGenerales(HttpSession session,RegistroDto registro) throws Exception{			
		
/*		List<SujetoObligado> patrones = (List<SujetoObligado>) session.getAttribute("patrones");
		List<Long> idModalidades = new ArrayList<Long>();
		registro.setMiUsuario((Usuario) session.getAttribute(Usuario.SES_NAME));
		registro.setDatosAsegurado((AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME));
		registro.setSujetoObligado((SujetoObligado) session.getAttribute(Constants.PATRON_SUJETO));
		registro.setCabezaGpoFam((CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION));
		
		if(patrones != null && !patrones.isEmpty()) {
			for(SujetoObligado patr : patrones) {
				idModalidades.add(patr.getModalidad().getIdModalidad());
			}
		}
		
		if(registro.getCabezaGpoFam() == null 
				|| registro.getDatosAsegurado() == null 
				|| registro.getSujetoObligado() == null
				|| registro.getMiUsuario() == null){
			
			throw new DerechohabientesBusinessException("Error en variables de session",Constants.SESSION_ERROR);
		}
		List<Long> estadosValidos =  new ArrayList<Long>();	
		if(registro.getMiUsuario().getPerfilUsuario().getIdPerfilUsuario()
				.equals(PerfilesEnum.ASEGURADO.getId() )){
			estadosValidos.add(EstadoDerechohabienteEnum.VIGENTE.getId());
			estadosValidos.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
			estadosValidos.add(EstadoDerechohabienteEnum.PENSION_TRAMITE.getId());
		}
		else if(registro.getMiUsuario().getPerfilUsuario().getIdPerfilUsuario()
				.equals(PerfilesEnum.PENSIONADO.getId()) ){
			estadosValidos.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
			
		}					
		else if(registro.getMiUsuario().getPerfilUsuario().getIdPerfilUsuario()
				.equals(PerfilesEnum.CONYUGE.getId()) ){
			estadosValidos.add(EstadoDerechohabienteEnum.VIGENTE.getId());						
		}else if(registro.getMiUsuario().getPerfilUsuario().getIdPerfilUsuario()
				.equals(PerfilesEnum.TRAMITADOR.getId()) ){
			estadosValidos = null;						
		}
		GrupoFamiliar miGrupoFamiliar = null;
		try{
			miGrupoFamiliar = grupoFamiliarService.getIntegranteGrupoFamiliarByEstados(registro.getDatosAsegurado().getIdPersona(), estadosValidos,registro.getDatosAsegurado().getIdAsignacionNSS());
		}catch(Exception e){
			log.debug("error al consultar a la persona en el grupo familiar" , e );
		}
		
		if(miGrupoFamiliar == null){
			if (!this.grupoFamiliarService
					.existeSolicitudRegistro(registro.getDatosAsegurado().getNssStr())) {					
				if(registro.getCabezaGpoFam().getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId())){
					if(registro.getMiUsuario().getPerfilUsuario().getIdPerfilUsuario().longValue() == PerfilesEnum.TRAMITADOR.getId().longValue()){						
						registro.setConAsegurado(Constants.SIN_ASEGURADO_INTERNO);
						registro.setElemento(Constants.INTERNO);
					}else{
						registro.setConAsegurado(Constants.SIN_ASEGURADO_EXTERNO);
						registro.setElemento(Constants.EXTERNO);
					}
				}else{
					if(registro.getMiUsuario().getPerfilUsuario().getIdPerfilUsuario().longValue() == PerfilesEnum.TRAMITADOR.getId().longValue()){						
						registro.setConAsegurado(Constants.SIN_PENSIONADO_INTERNO);
						registro.setElemento(Constants.INTERNO);
					}else{
						registro.setConAsegurado(Constants.SIN_PENSIONADO_EXTERNO);
						registro.setElemento(Constants.EXTERNO);
					}
				}					
			}else{
				registro.setConAsegurado(Constants.SIN_ASEG_PEN_CON_SOL);
			}
		}else{
			if(registro.getMiUsuario().getPerfilUsuario().getIdPerfilUsuario().longValue() == PerfilesEnum.TRAMITADOR.getId().longValue()){						
				registro.setConAsegurado(Constants.CON_ASEGURADO_INTERNO);
				registro.setElemento(Constants.INTERNO);
			}else{
				registro.setConAsegurado(Constants.CON_ASEGURADO_EXTERNO);
				registro.setElemento(Constants.EXTERNO);
			}
			
			if(registro.getDomicilio() == null){
				registro.setDomicilio(miGrupoFamiliar.getDomicilio());
				session.setAttribute("domicilioAsegurado", miGrupoFamiliar.getDomicilio());
			}
			
		}
		
		log.debug("La modalidad del patron es: " + registro.getSujetoObligado().getModalidad().getIdModalidad());
		if(registroDerechohabienteService.credencialRegistroD(
				registro.getMiUsuario(),
				registro.getDatosAsegurado().getIdAsignacionNSS(),
				idModalidades,
				registro.getCabezaGpoFam())){
			
			registro.setVigencia(Constants.CON_VIGENCIA);
		}else{
			registro.setVigencia(Constants.SIN_VIGENCIA);				
		}
		
//		registro.setPatronImss(patronService.getPatronIMSS(registro.getSujetoObligado()));
		if(registro.getCabezaGpoFam().getPatronImss() != 0){
			registro.setPatronImss(true);
		}else{
			registro.setPatronImss(false);
		}
		*/
									
		return registro;		
	}
	
	@RequestMapping(value = "pendientes")
    public String solPendientesAut(HttpSession session, Model model, HttpServletRequest request) {		
		return Constants.SOLICITUDES_PEN_AUT_FORWARD;
	}
	
	public void setGrupoFamiliarService(
			GrupoFamiliarServiceRemote grupoFamiliarService) {
		this.grupoFamiliarService = grupoFamiliarService;
	}

	public void setRegistroDerechohabienteService(
			RegistroDerechohabienteServiceRemote registroDerechohabienteService) {
		this.registroDerechohabienteService = registroDerechohabienteService;
	}

	public TramiteRegistroDerechohabiente getTramiteRegistro(Solicitud solicitud) {
		TramiteRegistroDerechohabiente registro = null;
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteRegistroDerechohabiente) {
				registro = (TramiteRegistroDerechohabiente) tramite;
			}
		}
		
		return registro;
	}
	
	@RequestMapping(value = "/getMediosContacto", method = RequestMethod.POST)
	public @ResponseBody Fisica getMediosContacto(@RequestBody Fisica persona, HttpServletResponse response, HttpServletRequest request) {
		
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		try{
			List<MedioContacto> mediosContacto = mediosContactoServiceBusinessRemote.consultarMedioDeContactoPersona(persona);
			  if(mediosContacto != null){ 
				  for(MedioContacto medio: mediosContacto) {
					  if(medio instanceof TelefonoFijo){
	                      TelefonoFijo telefonoFijo = (TelefonoFijo)medio;
	                      telefonoFijo.setClaveLada(telefonoFijo.getClaveLada() == null ? " " : telefonoFijo.getClaveLada());
	                      telefonoFijo.setNumero(telefonoFijo.getNumero() == null ? " " : telefonoFijo.getNumero());
	                      telefonoFijo.setExtension(telefonoFijo.getExtension() == null ? " ": telefonoFijo.getExtension());
	                      persona.setTelefonoFijo(telefonoFijo);
	                  }else if ( medio instanceof TelefonoMovil){
	                          TelefonoMovil telefonoMovil = (TelefonoMovil)medio;
	                          persona.setTelefonoMovil(telefonoMovil);
	                  }else if (medio instanceof CorreoElectronico){
	                          CorreoElectronico correoElectronico = (CorreoElectronico)medio;
	                          persona.setCorreoElectronico(correoElectronico);
	                  }else if (medio instanceof Facebook){
	                          Facebook facebook = (Facebook)medio;
	                          persona.setFacebook(facebook);
	                  }else if ( medio instanceof Twitter){
	                          Twitter twitter = (Twitter)medio;
	                          persona.setTwitter(twitter);
	                  } 
				  }
			  }
		}catch(PersonaSinMedioDeContactoException e){
			log.debug("La persona no tiene medios de contacto");
		}
		
		return persona;
	}
}
