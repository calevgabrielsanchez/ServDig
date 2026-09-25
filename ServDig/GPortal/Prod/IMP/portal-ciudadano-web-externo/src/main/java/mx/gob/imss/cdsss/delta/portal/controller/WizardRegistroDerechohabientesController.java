package mx.gob.imss.cdsss.delta.portal.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cdsss.delta.portal.controller.validator.RegistroDerechohabientesValidator;
import mx.gob.imss.cdsss.delta.portal.utils.Constants;
import mx.gob.imss.cdsss.delta.portal.utils.PropertiesOpciones;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PasoRegistroEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoCivilEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;

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
@RequestMapping("/wizard/registro")
public class WizardRegistroDerechohabientesController extends AbstractController{

	private static final String VIEW_SELECCION_PARENTESCO = "inicioRegistroBeneficiario";
	private static final String VIEW_CAPTURA_DATOS_PERSONALES = "datosPersonalesRegistro";
	private static final String KEY_ERROR = "error";
	
	private static final String KEY_TRAMITE_REGISTRO = "registro";
	private static final String KEY_VALIDACIONES_DOM = "validacionesDom";
	private static final String KEY_REQUIERE_DOCS = "requiereDocs";
	private static final String KEY_SOLICITUD = "solicitudRegistro";
	private static final String VIEW_DOMICILIO_UMF = "wizardRegistroCapturaDomicilioUMF";
	private static final String KEY_MAP_CORRECTO = "correcto";
	
	
	@Autowired
	private PropertiesOpciones propertiesOpciones;
	@Autowired
	private RegistroDerechohabienteServiceRemote registroDerechohabienteServiceRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusinessRemote;
	@Autowired
	private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;
	@Autowired
	private ServiceBusinessRemote serviceBusinessRemote;
	@Autowired
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@Autowired 
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	@Autowired
	private DocumentosServiceRemote documentosServiceRemote;
	
	@RequestMapping("/asegurado/{idAsignacionNSS}")
	public String inicioRegistroAsegurado(Model model,@PathVariable Long idAsignacionNSS, HttpSession session, HttpServletRequest request) {
		log.debug("iniciamos el tramite de registro de asegurado");
		
		return inicioRegistro(idAsignacionNSS,true,model, session,request);
	}
	
	@RequestMapping("/beneficiario/{idAsignacionNSS}")
	public String inicioRegistroBeneficiario(Model model, @PathVariable Long idAsignacionNSS, HttpSession session, HttpServletRequest request) {
		log.debug("iniciamos el tramite de registro de beneficiarios");
		
		return inicioRegistro(idAsignacionNSS,false, model, session, request);
	}
	
	@RequestMapping(value = "/capturaDatosPersonales", method = RequestMethod.POST)
	public String capturaDatosPersonales(@ModelAttribute TramiteRegistroDerechohabiente registro,Model model, HttpSession session) {
		
		Fisica fisica = registro.getFisica();
		fisica.setCurp(fisica.getCurp().toUpperCase());
		Parentesco parentesco = registro.getParentesco();
		GrupoFamiliar aseguradoPensionado = (GrupoFamiliar) session.getAttribute(Constants.KEY_DATOS_ASEGURADO);
		Boolean patronImss = (Boolean) session.getAttribute(Constants.KEY_PATRON_IMSS);
		List<Fisica> lista = null;
		
		try {
			
			Map<String, Object> validacionesTramite = this.validarExistenciaTramite(parentesco.getIdParentesco(), aseguradoPensionado.getAsignacionNSS().getIdPersona());
			
			if(this.getEstadoValidaciones(validacionesTramite)) {
				lista = serviceBusinessRemote.localizarPersonaReglasDerechohabiente(fisica);
				
				if(lista != null) {
					if(lista.size() == 1) {
						fisica = lista.get(0);
						this.getMediosContacto(fisica);
						registro = this.llenarTramiteRegistro(session, fisica, parentesco.getIdParentesco(), aseguradoPensionado.getAsignacionNSS(), patronImss);
					
						session.setAttribute(KEY_TRAMITE_REGISTRO, registro);
					} else {
						model.addAttribute(KEY_ERROR, "Se encontraron inconsistencia en los datos relacionados con la CURP, acuda a ventanilla a realizar el tr&aacute;mite");
					}
					
				}
			} else {
				model.addAttribute("registroAsegurado", false);
				model.addAttribute("validaciones", validacionesTramite);
				return VIEW_SELECCION_PARENTESCO;
			}
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error("el curp no fue localizado", e);
			model.addAttribute(KEY_ERROR, e.getSituacion());
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error("el cliente de renapo no se encuentra disponible", e);
			model.addAttribute(KEY_ERROR, e.getSituacion());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error("Error en la validacion de datos", e);
			model.addAttribute(KEY_ERROR, e.getSituacion());
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error("error al comparar renapo y datos enviados", e);
			model.addAttribute(KEY_ERROR, e.getSituacion());
		} catch (DatosInsuficientesParaConsultaException e) {
			log.error("datos insuficientes", e);
			model.addAttribute(KEY_ERROR, e.getSituacion());
		}
		
		model.addAttribute(KEY_TRAMITE_REGISTRO, registro);
		
		return VIEW_CAPTURA_DATOS_PERSONALES;
	}
	
	@RequestMapping( value ="/siguiente", method = RequestMethod.POST)
	public String siguientePaso(@ModelAttribute TramiteRegistroDerechohabiente registro, Model model,HttpSession session, HttpServletRequest request) {
		
		//Solicitud de registro
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		//Recuperamos el tramite de la solicitud
		TramiteRegistroDerechohabiente tramiteXml = (TramiteRegistroDerechohabiente) session.getAttribute(KEY_TRAMITE_REGISTRO);
		
		//guardamos el siguiente paso que seria el de captura de domicilio
		tramiteXml.setPaso(PasoRegistroEnum.CAPTURA_DOMICILIO.getId());
		tramiteXml.setFisica(registro.getFisica());
		
		if(solicitud == null) {
			try {
				solicitud = registroDerechohabienteServiceRemote.registraSolicitud(tramiteXml, OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
			} catch(Exception e) {
				e.printStackTrace();
			}
			if(solicitud != null) {
				tramiteXml = (TramiteRegistroDerechohabiente) solicitud.getTramites().get(0);
			}
		} else {
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(tramiteXml);
			
			try {
				solicitudBusinessRemote.actualizarXmlTramite(tramiteXml);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//Verificamos si no es el asegurado para buscar el domicilio particular y establecerlo
		this.validarRolesDomicilioParentesco(tramiteXml, model, session);
		this.verificarRegistroMismoSexo(tramiteXml, model);
		
		session.setAttribute(KEY_SOLICITUD, solicitud);
		session.setAttribute(KEY_TRAMITE_REGISTRO, tramiteXml);
		model.addAttribute(KEY_TRAMITE_REGISTRO, tramiteXml);
		
		return VIEW_DOMICILIO_UMF;
	}
	
	@RequestMapping(value = "/regresar", method = RequestMethod.POST)
	public String regresarPaso(@ModelAttribute TramiteRegistroDerechohabiente registro,Model model,HttpSession session, HttpServletRequest request) {
		
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		//Recuperamos el tramite de la solicitud
		TramiteRegistroDerechohabiente tramiteXml = (TramiteRegistroDerechohabiente) session.getAttribute(KEY_TRAMITE_REGISTRO);
		
		//el paso al que regresaremos sera a la captura de datos personales
		tramiteXml.setPaso(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId());
		//guardamos el domicilio de la persona
		tramiteXml.setDomicilio(registro.getDomicilio());
		tramiteXml.setMedicoEnTurno(registro.getMedicoEnTurno());
		tramiteXml.setIndSeleccionMedico(registro.getIndSeleccionMedico());
		tramiteXml.setFechaCambioMedico(registro.getFechaCambioMedico());
		
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramiteXml);
		
		try {
			solicitudBusinessRemote.actualizarXmlTramite(tramiteXml);
		} catch(Exception e) {
			e.printStackTrace();
		}
		tramiteXml = (TramiteRegistroDerechohabiente) solicitud.getTramites().get(0);
		
		session.setAttribute(KEY_SOLICITUD, solicitud);
		session.setAttribute(KEY_TRAMITE_REGISTRO, tramiteXml);
		model.addAttribute(KEY_TRAMITE_REGISTRO, tramiteXml);
		
		return VIEW_CAPTURA_DATOS_PERSONALES;
	}
	
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitudRegistro(@RequestBody TramiteRegistroDerechohabiente tramite,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) { 
	
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		 //se obtiene la cabeza de la sesion
        CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.KEY_CABEZA_GRUPO);
       //Se obtiene la lista de modalidades de los patrones en session
        List<Modalidad> modalidades = this.obtenerModalidadesPatrones(session);
		
		TramiteRegistroDerechohabiente tramiteXml = (TramiteRegistroDerechohabiente) solicitud.getTramites().get(0);
			
		//Actualizamos el domicilio
		tramiteXml.setDomicilio(tramite.getDomicilio());
		//Actualizamos los datos de la UMF
		tramiteXml.setMedicoEnTurno(tramite.getMedicoEnTurno());
		//actualizamos el indicador para saber si el derechohabiente eligio o no eligio un nuevo medico
		tramiteXml.setIndSeleccionMedico(tramite.getIndSeleccionMedico());
		//actualizamos la fecha de cambio de medico
		tramiteXml.setFechaCambioMedico(tramite.getFechaCambioMedico());

		
		try {
			//Se actualiza la informacion del XML
			tramiteXml = (TramiteRegistroDerechohabiente) solicitudBusinessRemote.actualizarXmlTramite(tramiteXml);
			
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(tramiteXml);
			//se finaliza el registro
			Map<String, Object> resultGuarda = registroDerechohabienteServiceRemote.guardarRegistroDerechohabiente(solicitud,cabeza,modalidades);
			GrupoFamiliar grupoRes = (GrupoFamiliar)resultGuarda.get("grupo");
			Solicitud solicitudRes = (Solicitud)resultGuarda.get("solicitud");
			FirmaElectronica fe = solicitudRes.getFirmaElectronica();
			String titulo = null;
			Integer idTipoTramite = 0;
			for (Tramite t : solicitudRes.getTramites()) {
				idTipoTramite = t.getTipoTramite().getIdTipoTramite();
				System.out.println("idTipoTramite:"+idTipoTramite);
				if(t instanceof TramiteRegistroDerechohabiente){
					titulo = t.getTipoTramite().getDescripcion();
					documentosServiceRemote.getDocumentoAcuseDeRecibo(solicitudRes.getNoFolioSolicitud(), titulo, fe, grupoRes);
					break;
				}
			}
			/*Se quita generado de documentos para que el finalizado no se tarde
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
			*/
			this.limpiarElementosSession(session);
			result.put("error", false);
			result.put("mensaje", "Su solicitud ha finalizado correctamente");
		} catch(ImpactaAlmacenesWSException e){
			result.put("error", true);
			result.put("mensaje", "<strong>Ocurri&oacute; un error al calcular la vigencia</strong>." +
					"Intentelo m&aacute;s tarde retomando la solicitud. ");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error("error solicitud",e);
			result.put("error", true);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (TramiteNoEncontradoException e) {
			this.log.error("error tramite",e);
			result.put("error", true);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		} catch (Exception e) {
			this.log.error("error desconocido",e);
			result.put("error", true);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/validacionesIniciales", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody TramiteRegistroDerechohabiente oForm, final HttpServletResponse response, final HttpSession session) {
        log.trace("entramos a WizardRegistroDerechohabiente para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        
        Map<String, Object> result = new HashMap<String, Object>();
        final Errors errors = new BindException(oForm, "model");
        
        new RegistroDerechohabientesValidator().validateInicial(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        

        return result;
    }
	
	@RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validaciones(final @RequestBody TramiteRegistroDerechohabiente oForm, final HttpServletResponse response, final HttpSession session) {
        log.trace("entramos a WizardRegistroDerechohabiente para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));

        Map<String, Object> result = new HashMap<String, Object>();
        //se obtiene la cabeza de la sesion
        CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.KEY_CABEZA_GRUPO);
       //Se obtiene la lista de modalidades de los patrones en session
        List<Modalidad> modalidades = this.obtenerModalidadesPatrones(session);
        //ids modalidades
		List<Long> idsModalidades = this.getModalidadesActivas(modalidades);
        TramiteRegistroDerechohabiente tramite = (TramiteRegistroDerechohabiente) session.getAttribute(KEY_TRAMITE_REGISTRO);
        Long idTramite = tramite == null ? null : tramite.getTramiteId();
  
        final Errors errors = new BindException(oForm, "model");
        
        new RegistroDerechohabientesValidator().validate(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        } else {
        	try{
        		//Se setean los datos del asegurado para las validaciones
	        	oForm.setDatosAsegurado(tramite.getDatosAsegurado());
	        	oForm.setTipoTramite(tramite.getTipoTramite());
	        	//Verificamos si el paso que validaremos es la captura de datos personales
	        	if(oForm.getPaso().equals(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId())) {
	        			oForm.setTramiteId(idTramite);
	        			result = requisitosMinimosServiceRemote.validaPersonaRegistrada(oForm.getFisica(), oForm.getDatosAsegurado().getIdAsignacionNSS());		
		        		if(result != null) {
		        			log.debug("Se superaron las validaciones de persona registrada");
		
		        			log.debug("La validadcion de personas arrojo : " + this.getEstadoValidaciones(result));
		        			
		        			if(this.getEstadoValidaciones(result)) {
		        				result = requisitosMinimosServiceRemote.requisitosMinimosRegistro(oForm, cabeza,OrigenSolicitudEnum.PORTAL_CIUDADANO.getId(),null, false, idsModalidades);
		        			}
		        		
		        		}
	        		
	        	} //Verificamos si validaremos el paso en el que se captura el domicilio 
	        	else if(oForm.getPaso().equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId())) {
	        		if(oForm.getParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId()) 
	        				|| oForm.getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()) ) {
	        			tramite.setPaso(oForm.getPaso());
	        			tramite.setDomicilio(oForm.getDomicilio());
	        			result = requisitosMinimosServiceRemote.requisitosMinimosRegistro(tramite, cabeza,OrigenSolicitudEnum.PORTAL_CIUDADANO.getId(),null, false, idsModalidades);
	        		} else {
	        			result.put(KEY_MAP_CORRECTO, true);
	        		}
	        		
	        	}
        	
        	} catch(DerechohabientesBusinessException e) {
    			e.printStackTrace();
    			result.put(KEY_MAP_CORRECTO, false);
    			result.put("mensaje", e.getMessage());
    		} catch(Exception e) {
    			e.printStackTrace();
    			result.put(KEY_MAP_CORRECTO, false);
    			result.put("mensaje", e.getMessage());
    		}
        	
        }
        

        return result;
    }
	
	@SuppressWarnings("unchecked")
	private List<Modalidad> obtenerModalidadesPatrones(HttpSession session) {
		//obtenemos los patrones de la session
		List<SujetoObligado> sujetos = (List<SujetoObligado>) session.getAttribute(Constants.KEY_PATRONES_ASEGURADO);
		//obtenemos la cabeza de grupo familiar de la session
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.KEY_CABEZA_GRUPO);
		//creamos la lista de modalidades
		List<Modalidad> modalidades = new ArrayList<Modalidad>();
		//si los patrones no son nulos, añadiremos sus modalidades
		if(sujetos != null && !sujetos.isEmpty()) {
			for(SujetoObligado sujeto: sujetos) {
				modalidades.add(sujeto.getModalidad());
			}
		} else {
			//si los patrones son nulos o vacios verificamos si la cabeza de grupo familiar tiene patron
			if(cabeza.getPatronSujetoObligado() != null) {
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
	
	private Boolean getEstadoValidaciones(Map<String, Object> validaciones) {
		return (Boolean) validaciones.get(KEY_MAP_CORRECTO);
	}
	/**
	 * metodo para verirficar si el registro es del mismo sexo y mandar las banderas correspondientes
	 * @param tramiteXml
	 * @param model
	 */
	private void verificarRegistroMismoSexo(TramiteRegistroDerechohabiente tramiteXml, Model model) {
		
		if(tramiteXml.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REGISTRO_CONYUGUE.getCodigo())) {
			model.addAttribute("registroConyuge", true);
			if(tramiteXml.getDatosAsegurado().getSexo().getIdSexo().equals(tramiteXml.getFisica().getSexo().getIdSexo())) {
				model.addAttribute("mismoSexo", true);
			} else {
				model.addAttribute("mismoSexo", false);
			}
		} else {
			model.addAttribute("registroConyuge", false);
			model.addAttribute("mismoSexo", false);
		}
	}
	
	@RequestMapping(value = "/solicitud/cancelar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudBaja(@RequestBody Solicitud solicitud,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) { 
	
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
				solicitud = solicitudBusinessRemote.consultar(solicitud);
				solicitud.setEstadoSolicitud(new EstadoSolicitud());
				solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
				
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
				
				solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
				result.put("mensaje", "La solicitud fue cancelada correctamente");
				result.put("solicitud", solicitud);
			
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping( value = "/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {

		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		
		this.cancelarsolicitud(solicitud);
		this.limpiarElementosSession(session);
	
		return null;
	}
	
	@RequestMapping( value = "/cancelarSolicitud")
	public @ResponseBody Map<String, Object> cancelarSolicitud(HttpSession session) {

		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		
		return this.cancelarsolicitud(solicitud);
	
	}
	
	private Map<String, Object> cancelarsolicitud(Solicitud solicitud) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(solicitud != null) {
			log.debug("Existe una solicitud que se cancelara");
			try {
				
				solicitudBusinessRemote.cancelarSolicitud(solicitud.getSolicitudId(), 5L,1L,null, "solicitud cancelada debido al cierra de ventana desde portal ciudadano");
				
			} catch (SolicitudException e) {
				this.log.error(e);
				result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
			} 
		} else {
			log.debug("No existia la solicitud");
		}
		
		return result;
	}
	
	private void limpiarElementosSession(HttpSession session) {
		//se quita el tramite
		session.removeAttribute(KEY_TRAMITE_REGISTRO);
		//se quitan las validaciones de domicilio
		session.removeAttribute(KEY_VALIDACIONES_DOM);
		//Se quita la bandera de documentos
		session.removeAttribute(KEY_REQUIERE_DOCS);
		//se quita la solicitud
		session.removeAttribute(KEY_SOLICITUD);
		//establecemos en session los datos del asegurado
		session.removeAttribute(Constants.KEY_DATOS_ASEGURADO);
		//Se setea en la session
		session.removeAttribute(Constants.KEY_ASIGNACION_NSS);
		//Datos de la cabeza de grupo familiar
		session.removeAttribute(Constants.KEY_CABEZA_GRUPO);
		//Se setea el patron imss
		session.removeAttribute(Constants.KEY_PATRON_IMSS);
		//Se establecen en sesion los patrones del asegurado
		session.removeAttribute(Constants.KEY_PATRONES_ASEGURADO);
		
		return;
	}
	
	private void validarRolesDomicilioParentesco(TramiteRegistroDerechohabiente registro, Model model, HttpSession session) {
		
		Long idParentesco = registro.getParentesco().getIdParentesco();
		Boolean isAsegurado = idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId());
		Boolean isPatronIMSS = (Boolean) session.getAttribute(Constants.KEY_PATRON_IMSS);
		GrupoFamiliar datosAsegurado = (GrupoFamiliar) session.getAttribute(Constants.KEY_DATOS_ASEGURADO);
		Domicilio domicilioAsegurado = datosAsegurado != null ? datosAsegurado.getDomicilio() : null;
		Map<String,Object> result = requisitosMinimosServiceRemote.validarEleccionDeDomicilioYUmfPorPersonaParentesco(
				registro.getFisica(),registro.getParentesco().getIdParentesco(), domicilioAsegurado,isPatronIMSS);
		
		PersonaDomicilio personaDomicilio = (PersonaDomicilio) result.get("personaDomicilio");
		Boolean setdomicilioAsegurado = (Boolean) result.get("setDomicilioAsegurado");
		Boolean setUmfAsegurado = (Boolean) result.get("setUmfAsegurado");

		if(!setdomicilioAsegurado) {
			//Se setea el id de la relacion persona-domicilio
			registro.setCvePersonaDomicilio(personaDomicilio.getCvePersonaDomicilio());
			//verificamos si permite cambio de domicilio
			registro.setDomicilio(personaDomicilio.getDomicilio());
		} else {
			registro.setDomicilio(domicilioAsegurado);
		}
		
		if(isAsegurado) {
			registro.setMedicoEnTurno(null);
			registro.setIndSeleccionMedico(1);
			registro.setFechaCambioMedico(null);
		} else {
			MedicoEnTurno medico = datosAsegurado.getMedicoEnTurno();
			
			if(setUmfAsegurado) {
				if(medico != null) {
					if(medico != null) {
						registro.setMedicoEnTurno(medico);
						registro.setIndSeleccionMedico(0);
						registro.setFechaCambioMedico(datosAsegurado.getFechaCambioTurnoMedico());
					}
				}
			} else {
				registro.setMedicoEnTurno(null);
				registro.setIndSeleccionMedico(1);
				registro.setFechaCambioMedico(null);
			}
		}

		session.setAttribute(KEY_VALIDACIONES_DOM, result);
	
	}

	private String inicioRegistro(Long idAsignacionNss,Boolean registroAsegurado, Model model, HttpSession session, HttpServletRequest request) {
		
		request.setAttribute("opciones", propertiesOpciones.getOpciones());
		String view = registroAsegurado ? VIEW_CAPTURA_DATOS_PERSONALES : VIEW_SELECCION_PARENTESCO;
		
		//Objeto para guardar la informacion del asegurado
		GrupoFamiliar aseguradoPensionado = null;
		//asignacionNSS
		AsignacionNSS asignacionNSS = null;
		//cabeza de grupo familiar
		CabezaGrupoFamiliar cabeza = null;
		//Fisica
		Fisica fisica = null;
		//Se crea el objeto del tramite de registro
		TramiteRegistroDerechohabiente tramite = new TramiteRegistroDerechohabiente();
		
		Boolean aseguradoRegistrado = false;
		Boolean patronImss = false;
		
		if(idAsignacionNss.intValue() == 0) {
			model.addAttribute(KEY_ERROR, Constants.ERROR_SIN_NSS);
		} else {
			
			Map<String, Object> valoresIniciales = this.getVariablesIniciales(idAsignacionNss, session);
			
			if(!valoresIniciales.containsKey(KEY_ERROR)) {
				cabeza = (CabezaGrupoFamiliar) valoresIniciales.get(Constants.KEY_CABEZA_GRUPO);
				asignacionNSS = (AsignacionNSS) valoresIniciales.get(Constants.KEY_ASIGNACION_NSS);
				aseguradoPensionado = (GrupoFamiliar) valoresIniciales.get(Constants.KEY_DATOS_ASEGURADO);
				aseguradoRegistrado = aseguradoPensionado.getIndRegistrado().equals(1);
			} else {
				model.addAttribute(KEY_ERROR, valoresIniciales.get(KEY_ERROR));
			}
		}
		
		//Verificamos si no hubo ningun error
		if(!model.containsAttribute(KEY_ERROR)) {
			//verificamos si encontramos la informacion de la cabeza de grupo familiar
			if(aseguradoPensionado != null) {
				//Verificamos que el estado del asegurado permita el registro de mas personas
				if(cabeza != null && (cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.BAJA.getId())){
					model.addAttribute(KEY_ERROR, Constants.ERROR_VIGENCIA);
				}else{//si se cuenta con un estado valido
					//validamos si el registro es el del asegurado
					if(registroAsegurado) {	
						//Si el registro es de asegurado y el asegurado ya se encuentra registrado mandamos un error
						if(aseguradoRegistrado) {
							model.addAttribute(KEY_ERROR, Constants.ERROR_YA_REGISTRADO);
						} else {
							//si el asegurado no se encuentra registrado
							try {
								Map<String, Object> validacionesSolicitud = this.validarExistenciaTramite(null, asignacionNSS.getIdPersona());
								
								if(!this.getEstadoValidaciones(validacionesSolicitud)) {
									model.addAttribute("registroAsegurado", registroAsegurado);
									model.addAttribute("validaciones", validacionesSolicitud);
									return VIEW_SELECCION_PARENTESCO;
								}
							} catch (Exception e) {
								log.error("Ocurrio un error al consultar las solicitudes activas", e);
								//model.addAttribute(KEY_ERROR, "No fue posible consultar sus solicitudes");
							}	
							//si el modelo no contiene ningun error continuamos
							if(!model.containsAttribute(KEY_ERROR)) {
								//Obtenemos los datos personales que ya se tienen del asegurado/pensionado
								fisica = getPersonaConMedios(asignacionNSS.getIdPersona());
								//llenamos el objeto tramite
								tramite = this.llenarTramiteRegistro(session, fisica, cabeza.getCalidadParentesco().getIdParentesco(), asignacionNSS,patronImss);
								//agregamos a la sesion el tramite
								session.setAttribute(KEY_TRAMITE_REGISTRO, tramite);
							}
						}
					} else {
						
						if(!aseguradoRegistrado) {
							model.addAttribute(KEY_ERROR, Constants.ERROR_REGISTRO_ASEGURADO_NECESARIO);
						}else if (aseguradoPensionado.getMedicoEnTurno() == null || aseguradoPensionado.getDomicilio() == null) {
							model.addAttribute(KEY_ERROR, Constants.ERROR_DOMICILIO_UMF_NECESARIO);
						}
					}
					
					
				}
				
			} else {
				model.addAttribute(KEY_ERROR, "No se localiz&oacute; informaci&oacute;n de la vigencia del a" +
						"segurado o pensionado por favor acuda a su Subdelegaci&oacute;n m&aacute;s cercana para aclarar la situaci&oacute;n.");
			}
		}
		
		model.addAttribute("registroAsegurado", registroAsegurado);
		model.addAttribute(KEY_TRAMITE_REGISTRO, tramite);
		
		
		return view;
	}
	

	/**
	 * Metodo para obtener las variables necesarias para el registro de derechohabientes
	 * @param idAsignacionNss - Long; es el id del nss con el que se va a hacer el tramite
	 * @param session - HttpSession; La session donde se pondran los objetod
	 * @return Map<String, Object> - Todos los datos se retornaran en un MAP
	 */
	private Map<String, Object> getVariablesIniciales(Long idAsignacionNss, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		//asignacionNSS
		AsignacionNSS asignacionNSS = null;
		//cabeza de grupo familiar
		CabezaGrupoFamiliar cabeza = null;
		//patrones del asegurado
		List<SujetoObligado> patronesAsegurado = null;
		//asegurado
		GrupoFamiliar aseguradoPensionado = null;
		//patron Imss
		Boolean patronImss = false;
		
		//consultaremos el asignacion nss en caso de que no este en sesion o en caso de que sea diferente el parametro que se envia
		log.debug("Se consultara el nss ya que no se encontraba en session");
		//Se consulta el idAsignacionNSS
		try {
			asignacionNSS = grupoFamiliarServiceRemote.getAsignacionNssByIdAsignacion(idAsignacionNss);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			return returnErrorMap(result,Constants.ERROR_CONSULTA_NSS + e.getSituacion());
		}
		

		log.debug("Se consultara la cabeza de grupo familiar");
		//Se obtiene la cabeza de grupo famliar para saber si es patron imss
		try {
			cabeza = grupoFamiliarServiceRemote.cabezaGrupoFamiliar(idAsignacionNss);
		} catch (DerechohabientesBusinessException e) {
			return returnErrorMap(result,Constants.ERROR_CONSULTA_CABEZA + e.getSituacion());
		} catch (Exception e) {
			e.printStackTrace();
			return returnErrorMap(result,Constants.ERROR_CONSULTA_CABEZA);
		}
		//boolean
		patronImss = cabeza.getPatronImss().equals(1);
		//Se valida que no sea estudiante
		if(cabeza.getEsEstudiante()){
			return returnErrorMap(result, Constants.ERROR_ESTUDIANTE);
		}
		
		//Se obtiene a los patrones
		try{
			patronesAsegurado = grupoFamiliarServiceRemote.getPatronesAsegurado(asignacionNSS);	
		}catch(DerechohabientesBusinessException e){
			log.error("Ocurrio un error al obtener a los patrones del asegurado", e);
			e.printStackTrace();
			return returnErrorMap(result, Constants.ERROR_PATRONES + "" + e.getSituacion());
		} catch(Exception e) {
			e.printStackTrace();
			return returnErrorMap(result, Constants.ERROR_PATRONES);
		}
		
		//obtenemos los datos de vigencias
		try {
			aseguradoPensionado = grupoFamiliarServiceRemote.getCabezaGrupaFamilarRegistrada(asignacionNSS,cabeza);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			return returnErrorMap(result, Constants.ERROR_CONSULTA_INTEGRANTE_ASEGURADO + e.getSituacion());
		} catch (Exception e) {
			e.printStackTrace();
			return returnErrorMap(result, Constants.ERROR_CONSULTA_INTEGRANTE_ASEGURADO);
		}
		
		//establecemos en session los datos del asegurado
		session.setAttribute(Constants.KEY_DATOS_ASEGURADO, aseguradoPensionado);
		result.put(Constants.KEY_DATOS_ASEGURADO, aseguradoPensionado);
		//Se setea en la session
		session.setAttribute(Constants.KEY_ASIGNACION_NSS, asignacionNSS);
		result.put(Constants.KEY_ASIGNACION_NSS, asignacionNSS);
		//Datos de la cabeza de grupo familiar
		session.setAttribute(Constants.KEY_CABEZA_GRUPO, cabeza);
		result.put(Constants.KEY_CABEZA_GRUPO, cabeza);
		//Se setea el patron imss
		session.setAttribute(Constants.KEY_PATRON_IMSS, patronImss);
		result.put(Constants.KEY_PATRON_IMSS, patronImss);
		//Se establecen en sesion los patrones del asegurado
		session.setAttribute(Constants.KEY_PATRONES_ASEGURADO, patronesAsegurado);
		result.put(Constants.KEY_PATRONES_ASEGURADO, patronesAsegurado);
		
		return result;
	}
	
	/**
	 * Funcion para retornar el map con el error
	 * @param result
	 * @param error
	 * @return
	 */
	private Map<String, Object> returnErrorMap(Map<String, Object> result,String error) {
		result.put(KEY_ERROR,error);
		
		return result;
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
	
	private Map<String, Object> validarExistenciaTramite(Long idParentesco, Long idPersonaAsegurado) {
		
		Map<String, Object> validaciones = new HashMap<String, Object>();
		Boolean correcto = true;
		
		//if(idParentesco == null || !idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
			List<Long> tiposTramite = new ArrayList<Long>();
			List<Long> estadosTramite = new ArrayList<Long>();
			estadosTramite.add(EstadoTramiteEnum.INICIADO.getCodigo().longValue());
			estadosTramite.add(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo().longValue());
			
			if(idParentesco == null) {
				tiposTramite.add(TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue());
				tiposTramite.add(TipoTramiteEnum.REGISTRO_PENSIONADO.getCodigo().longValue());
			} else {
				tiposTramite.add(this.getTipoTramitePorParentesco(idParentesco).longValue());
			}
			
			try {
			//Buscamos las solicitudes dependiento del parentesco
			List<Solicitud> solicitudesRegistro = solicitudTramiteBusinessRemote.getSolicitudesPersona(null,
				tiposTramite, estadosTramite, null, null, idPersonaAsegurado, true, 1, true);
			
			if(solicitudesRegistro != null && !solicitudesRegistro.isEmpty()) {
				Solicitud solicitud = solicitudesRegistro.get(0);
				validaciones.put("solicitud", solicitud);
				correcto = false;
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
		//}
		
		validaciones.put(KEY_MAP_CORRECTO, correcto);
		
		return validaciones;
	}
	
	private TramiteRegistroDerechohabiente llenarTramiteRegistro(HttpSession session,Fisica fisica, Long idParentesco, AsignacionNSS nss, Boolean patronImss) {
		TramiteRegistroDerechohabiente tramite = new TramiteRegistroDerechohabiente();
		Boolean requiereDocumentos = false;
		
		tramite.setDatosAsegurado(nss);
		//Establecemos los datos de la persona a registrar
		tramite.setFisica(fisica);
		//Establecemos el paso de captura de datos personales ya que es la pantalla en la que estaremos
		tramite.setPaso(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId());
		//Establcemos el parentesco que queremos registrar
		tramite.setParentesco(new Parentesco());
		tramite.getParentesco().setIdParentesco(idParentesco);
		tramite.setIndSeleccionMedico(1);
		
		tramite.setUsuario(new Usuario());
		tramite.getUsuario().setUsuario(nss.getCurp());
		//Establecemos el estado civil
		Long idEstadoCivil = -1L;
		//Establecemos la razon de registro
		RazonRegistro razonRegistro = new RazonRegistro();
		//Verificamos el parentesco a registrar para establecer la razon de registro y el estado civil
		if(idParentesco.equals(ParentescoEnum.HIJOS.getId())){
			//Si el parentesco es hijo, el estado civil debe ser soltero
			idEstadoCivil = EstadoCivilEnum.SOLTERO.getId();
			
			razonRegistro = this.getRazonRegistroHijos(fisica, patronImss);
		} else if(idParentesco.equals(ParentescoEnum.CONYUGE.getId())) {
			//Si el parentesco es conyuge, el estado civil debe ser casado
			idEstadoCivil = EstadoCivilEnum.CASADO.getId();
			//Y la razon de registro debe ser normal
			razonRegistro.setIdRazonRegistro(RazonRegistroEnum.NORMAL.getId());
		} else if(idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())) {
			//Si el parentesco es concubina el estado civil debe ser concubinato
			idEstadoCivil = EstadoCivilEnum.CONCUBINATO.getId();
			razonRegistro.setIdRazonRegistro(RazonRegistroEnum.NORMAL.getId());
		} else {
			//Si el parentesco es asegurado, pensionado o padres el estado civil 
			//no se manda para que se pueda elegir en la pantalla, mientras que 
			//la razon de registro es normal
			razonRegistro.setIdRazonRegistro(RazonRegistroEnum.NORMAL.getId());
		}
		
		tramite.setPaso(1L);
		//Seteamos la razon del registro
		tramite.setRazonRegistro(razonRegistro);
		//Seteamos el estado civil 
		tramite.getFisica().setEstadoCivil(new EstadoCivil());
		tramite.getFisica().getEstadoCivil().setIdEstadoCivil(idEstadoCivil.intValue());
		tramite.setTipoTramite(this.getTipoTramite(tramite.getParentesco()));
		
		//Verificamos si el tramite requiere documentos
		try {
			requiereDocumentos = documentoProbatorioServiceBusinessRemote.requiereDocumentos(tramite.getTipoTramite().getIdTipoTramite().longValue());
			session.setAttribute(KEY_REQUIERE_DOCS, requiereDocumentos);
		} catch (Exception e) {
			log.error("Ocurrio un error al consultar si el tramite requiere documentos",e);
		}
		
		return tramite;
	}
	
	private TipoTramite getTipoTramite(Parentesco parentesco) {
		TipoTramite tipoTramite = new TipoTramite();
		
		//En caso de que el parentesco cambie volvemos a setear el tipo de tramite
		Integer idTipoTramite = this.getTipoTramitePorParentesco(parentesco.getIdParentesco());
		tipoTramite.setIdTipoTramite(idTipoTramite);
		
		return tipoTramite;
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
		} else if(idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())){
			tipoTramite = TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo();
		} else {
			tipoTramite = TipoTramiteEnum.REGISTRO_PADRES.getCodigo();
		}
		
		return tipoTramite;
	}
	
	
	private RazonRegistro getRazonRegistroHijos(Fisica fisica, Boolean patronIMSS) {
		RazonRegistro razonRegistro = new RazonRegistro();
		long edad = this.getEdadRedondeadaEnAnios(fisica.getFechaNacimiento());
		Long idRazonRegistro = null;
		if(edad >= 0 && edad <= 16){		
			idRazonRegistro = RazonRegistroEnum.HASTA_16.getId();
		}

		if(edad > 16 && edad <= 25){
			Long idSexo = fisica.getSexo().getIdSexo().longValue();
			
			if(patronIMSS) {
				if(idSexo.equals(SexoEnum.MUJER.getId())) {
					idRazonRegistro = RazonRegistroEnum.NORMAL.getId();
					
				} else {
					idRazonRegistro = edad < 18 ? RazonRegistroEnum.NORMAL.getId() : RazonRegistroEnum.HASTA_25.getId();
				}
			} else {
				idRazonRegistro = RazonRegistroEnum.HASTA_25.getId();
			} 
		}

		if(edad > 25){			
			idRazonRegistro = RazonRegistroEnum.MAYOR_A_25.getId();
		}
		
		razonRegistro.setIdRazonRegistro(idRazonRegistro);
		
		return razonRegistro;
	}
	
	private long getEdadRedondeadaEnAnios(Date fechaNacimiento) {
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
	
	private void getMediosContacto(Fisica persona) {
		
		if(persona.getIdPersona() != null) {
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
			
		}
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
					
				}else if ( medioA instanceof TelefonoMovil){

					TelefonoMovil telefonoMovil = (TelefonoMovil)medioA;
					fisica.setTelefonoMovil(telefonoMovil);

				}else if (medioA instanceof CorreoElectronico){
					CorreoElectronico correoElectronico = (CorreoElectronico) medioA;
					fisica.setCorreoElectronico(correoElectronico);
				}else if (medioA instanceof Facebook){
					Facebook facebook = (Facebook) medioA;
					fisica.setFacebook(facebook);

				}else if (medioA instanceof Twitter){
					Twitter twitter = (Twitter) medioA;
					fisica.setTwitter(twitter);
				} 
			}
		}
	}
	
}
