package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

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

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PasoRegistroEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.EstadoCivilEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
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
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller.validator.RegistroDerechohabientesValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

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
@RequestMapping( value = "/wizard/registro/")
public class WizardRegistroDerechohabienteController extends AbstractController {

	@Autowired 
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired 
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired 
	private ServiciosPersonaBusinessRemote serviciosPersonaBusinessRemote;
	@Autowired 
	private RegistroDerechohabienteServiceRemote registroDerechohabienteServiceRemote;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@Autowired
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	@Autowired
	private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;

	private static final String KEY_USUARIO = "usuarioSession";
	private static final String KEY_PARENTESCO_REGISTRO = "parentescoARegistrarSession";
	private static final String KEY_CABEZA_GRUPO_FAMILIAR = "cabezaGrupoGamiliarSession";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_ASIGNACION_NSS = "asignacionNssSession";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_PATRON_IMSS = "isPatronImss";
	private static final String VIEW_DATOS_PERSONA = "wizardRegistroCapturaPersona";
	private static final String VIEW_DOMICILIO = "wizardRegistroCapturaDomicilio";
	private static final String VIEW_UMF = "wizardRegistroCapturaUmf";
	private static final String KEY_IS_RETOMAR = "isRetomar";
	private static final String KEY_REQUIERE_DOCS = "requiereDocs";
	private static final String KEY_SOLICITUD = "solicitudRegistro";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_PERMITE_ELECCION_DOMICILIO = "permiteEleccionDomicilioSession";
	private static final String KEY_DATOS_ASEGURADO = "datosAdscripcionAseguradoSession";
	private static final String KEY_VALIDACIONES_DOM = "validacionesDom";
	private static final String KEY_IDS_MODALIDADES_ASEGURADO = "idsModalidadesSession";
	private static final String KEY_MODALIDADES_SESSION = "modalidadesSession";
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idAsignacionNss
	 * @param parentesco
	 * @param idRazonRegistro
	 * @return
	 */
	@RequestMapping(value = "/tramite/{idAsignacionNss}/{nss}/{parentesco}")
	public String initWizardRegistro(Model model, HttpSession session, HttpServletRequest request, 
			@PathVariable Long idAsignacionNss, @PathVariable String nss, @PathVariable Long parentesco) {

		Solicitud solicitudActiva = null;;
		AsignacionNSS asignacionNss = null;
		Boolean solicitudCreada = false;
		Boolean mismoOrigen = true;
		Long idTipoTramite = 0L;
		TipoTramite tipoTramiteCreado = new TipoTramite();
		CabezaGrupoFamiliar cabeza = null;
		String descripcionTramite = "Registro de asegurado / pensionado";
		Boolean requiereDocumentos = false;

		try {
			//Se obtiene la cabeza de grupo famliar para saber si es patron imss
			cabeza = grupoFamiliarServiceRemote.cabezaGrupoFamiliar(idAsignacionNss);
			//Se obtiene el objeto de asignacion de nss
			asignacionNss = grupoFamiliarServiceRemote.getAsignacionNssByIdAsignacion(idAsignacionNss);
			
			//Verificamos que el nss no sea nulo
			if(asignacionNss == null) {
				model.addAttribute("error", "No fue posible localizar los datos relacionados al NSS");
			} else {
				//Si no es nulo lo ponemos en sesion
				session.setAttribute(KEY_ASIGNACION_NSS, asignacionNss);
			}
			
			//Verificamos que la cabeza de grupo familiar no sea nulo
			if(cabeza != null) {
				//Ponemos en sesion si es patron IMSS
				log.debug("La informacion de cabeza de grupo familiar es: " + cabeza.getPatronSujetoObligado());
				session.setAttribute(KEY_CABEZA_GRUPO_FAMILIAR, cabeza);
				//Seteamos en sesion si el patron del asegurado es patron imss
				Boolean isPatronImss = cabeza.getPatronImss().equals(1);
				session.setAttribute(KEY_PATRON_IMSS, isPatronImss);
				
				//Buscamos los datos del asegurado dentro del grupo familiar
				try {
					GrupoFamiliar asegurado = grupoFamiliarServiceRemote.getCabezaGrupoFamiliar(idAsignacionNss);
					//Si el asegurado no es nulo lo seteamos en sesion
					if(asegurado != null) {
						session.setAttribute(KEY_DATOS_ASEGURADO, asegurado);
					}
				} catch(DerechohabientesBusinessException e) {
					log.error("Ocurrio un error al consultart al asegurado", e);
				} catch(Exception e) {
					log.error("Ocurrio un error al consultart al asegurado", e);
				}
				
			} else {
				model.addAttribute("error", "No fue posible localizar la cabeza de grupo familiar");
			}
		} catch(DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al consultar el nss", e);
			model.addAttribute("error", e.getMessage());
		} catch(Exception e) {
			log.error("Ocurrio un error al consultar el nss", e);
			model.addAttribute("error", e.getMessage());
		}
		
		//Buscamos las solicitudes activas, para que en caso de haberla retomarla
		try {
			solicitudActiva = solicitudBusinessRemote.getUltimaSolicitudActivaDeRegistroPorIdAsignacionNss(idAsignacionNss);
			//Si ya existe una solicitud de registro activa
			if(solicitudActiva != null) {
				solicitudCreada = true;
				//Verificamos el tipo de tramite
				tipoTramiteCreado = solicitudActiva.getTramites().get(0).getTipoTramite();
				idTipoTramite = tipoTramiteCreado.getIdTipoTramite().longValue();
				mismoOrigen = solicitudActiva.getOrigenSolicitud().getIdTipoSolicitud().equals(OrigenSolicitudEnum.INTERNET.getId());
				//Verficamos si el tramite requiere de documentos probatorios
				try {
					requiereDocumentos = documentoProbatorioServiceBusinessRemote.requiereDocumentos(idTipoTramite);
				} catch (Exception e) {
					log.error("Ocurrio un error al consultar si el tramite requiere documentos",e);
				}
			} else {//Si no encontramos solicitudes activas ponemos banderas
				solicitudActiva = new Solicitud();
				solicitudCreada = false;
			}
		} catch (Exception e) {
			log.error("Ocurrio un error al consultar la ultima solicitud de registro", e);
			model.addAttribute("error", "No fue posible consultar las solicitudes de registro");
		}
		
		if(parentesco.equals(0L)) {
			descripcionTramite = "Registro de beneficiarios";
			
			try {
				Boolean mostrarPadres = requisitosMinimosServiceRemote.validarNumeroIntegrantesPorParentesco(asignacionNss.getIdAsignacionNSS(), ParentescoEnum.PADRES.getId());
				model.addAttribute("mostrarPadres", mostrarPadres);
			} catch (DerechohabientesWebSserviceException e) {
				e.printStackTrace();
				model.addAttribute("mostrarPadres", false);
			}
			
			
		}


		session.setAttribute(KEY_TIPO_SOLICITUD, idTipoTramite);
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, tipoTramiteCreado.getDescripcion() == null ? descripcionTramite : tipoTramiteCreado.getDescripcion() );
		session.setAttribute(KEY_PARENTESCO_REGISTRO, parentesco);
		session.setAttribute(KEY_SOLICITUD,solicitudActiva);
		session.setAttribute(KEY_REQUIERE_DOCS, requiereDocumentos);

		List<Long> idtiposTramite = new ArrayList<Long>();
		idtiposTramite.add(idTipoTramite);
		session.setAttribute(KEY_TIPO_TRAMITE, idtiposTramite);
		model.addAttribute("tipoTramiteCreado", tipoTramiteCreado);
		model.addAttribute("solicitudCreada", solicitudCreada);
		model.addAttribute("solicitudForm", solicitudActiva);
		model.addAttribute("mismoOrigen", mismoOrigen);

		return "wizardRegistroDerechohabienteInit";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/iniciarTramite/{idParentesco}")
	public String iniciarTramiteRegistro(Model model, HttpServletRequest request,HttpSession session, @PathVariable Long idParentesco) {

		session.setAttribute(KEY_PARENTESCO_REGISTRO, idParentesco);
		
		Boolean requiereDocumentos = false;
		Boolean isAsegurado = idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId());
		TramiteRegistroDerechohabiente tramite = new TramiteRegistroDerechohabiente();
		AsignacionNSS asignacion = (AsignacionNSS) session.getAttribute(KEY_ASIGNACION_NSS);
		Solicitud solicitudCreada = null;
		Fisica fisica = new Fisica();
		Domicilio domicilio = new Domicilio();
		Usuario usuario = this.getUusuarioSession(request, session);
		Boolean permiteEleccionDomicilio = true;
		
		//Establecemos los datos del asegurado
		tramite.setDatosAsegurado(asignacion);
		//Verificamos si el registro es el del asegurado o pensionado
		if(isAsegurado) {
			try {
				//Obtenemos los datos personales que ya se tienen del asegurado/pensionado
				fisica =serviciosPersonaBusinessRemote.buscarPersonaFisicayDPyDyMCEnIMSS(asignacion.getIdPersona());

				
				if(fisica != null) {
					//Llenamos lso medios de contacto del asegurado/pensionado
					this.procesarMedios(fisica);
				}

			} catch (PersonaFisicaNoEncontradaException e) {
				e.printStackTrace();
			}
		}  else {
			GrupoFamiliar asegurado = (GrupoFamiliar) session.getAttribute(KEY_DATOS_ASEGURADO);//grupoFamiliarServiceRemote.getCabezaGrupoFamiliar(asignacion.getIdAsignacionNSS());
			if(asegurado != null) {
				//Si no es el asegurado se pondra el domicilio por default
				domicilio = asegurado.getDomicilio();
				//se comenta linea ya que la relacion de persona domicilio sera unica para cada integrante
				//tramite.setCvePersonaDomicilio(asegurado.getCvePersonaDomicilio());
			} else {
				model.addAttribute("error","No fue posible consultar a la cabeza del grupo familiar");
			}
		}
		
		//Establecemos los datos de la persona a registrar
		tramite.setFisica(fisica);
		//Establecemos el domicilio de la persona
		tramite.setDomicilio(domicilio);
		//Establecemos el paso de captura de datos personales ya que es la pantalla en la que estaremos
		tramite.setPaso(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId());
		//Establcemos el parentesco que queremos registrar
		tramite.setParentesco(new Parentesco());
		tramite.getParentesco().setIdParentesco(idParentesco);
		tramite.setIndSeleccionMedico(1);
		//Establecemos el estado civil
		Long idEstadoCivil = -1L;
		//Establecemos la razon de registro
		RazonRegistro razonRegistro = new RazonRegistro();
		//Verificamos el parentesco a registrar para establecer la razon de registro y el estado civil
		if(idParentesco.equals(ParentescoEnum.HIJOS.getId())){
			//Si el parentesco es hijo, el estado civil debe ser soltero
			idEstadoCivil = EstadoCivilEnum.SOLTERO.getId();
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
		
		//Seteamos la razon del registro
		tramite.setRazonRegistro(razonRegistro);
		//Seteamos el estado civil 
		tramite.getFisica().setEstadoCivil(new EstadoCivil());
		tramite.getFisica().getEstadoCivil().setIdEstadoCivil(idEstadoCivil.intValue());
			
		//se hace esto porque el objeto usuareio no se puede guardar en el XMl porque se cicla
		Usuario usuarioInter = new Usuario();
		if(usuario != null) {
			usuarioInter.setCveIdUsuario(usuario.getCveIdUsuario());
			usuarioInter.setUsuario(usuario.getUsuario());
		} else {
			usuarioInter.setCveIdUsuario(asignacion.getCurp());
			usuarioInter.setUsuario(asignacion.getCurp());
		}
		tramite.setUsuario(usuarioInter);

		log.debug("El usuario que hace el registro es: " + asignacion.getCurp());
		try {
			//Registramos la solicitud de registro de derechohabiente
			solicitudCreada = registroDerechohabienteServiceRemote.registraSolicitud(tramite, OrigenSolicitudEnum.INTERNET.getId());
			//Si la solicitud fue creada correctamente
			if(solicitudCreada != null) {
				//Obtenemos el tramite que se creo
				tramite = (TramiteRegistroDerechohabiente) solicitudCreada.getTramites().get(0);
				//Verificamos el tipo de tramite que se creo
				Long tipoTramiteR = tramite.getTipoTramite().getIdTipoTramite().longValue();
				List<Long> idtiposTramite = new ArrayList<Long>();
				idtiposTramite.add(tipoTramiteR);
				
				//Verificamos si el tramite requiere documentos
				try {
					requiereDocumentos = documentoProbatorioServiceBusinessRemote.requiereDocumentos(tipoTramiteR);
				} catch (Exception e) {
					log.error("Ocurrio un error al consultar si el tramite requiere documentos",e);
				}
				
				session.setAttribute(KEY_TIPO_TRAMITE, idtiposTramite);
				session.setAttribute(KEY_TIPO_SOLICITUD, tramite.getTipoTramite().getIdTipoTramite());
				session.setAttribute(KEY_DESC_TIPO_SOLICITUD, tramite.getTipoTramite().getDescripcion());
				model.addAttribute("folioSolicitud", solicitudCreada.getNoFolioSolicitud());
				// Datos del acuse
				obtenerDatosAcuse(solicitudCreada, asignacion, session);

				// Datos para la firma digital
				generarCadenaOriginal(solicitudCreada, asignacion, session, asignacion.getNssStr());
				
			} else {
				model.addAttribute("error", "Error al crear la solicitud");
			}
		} catch(SolicitudNoValidaException e) {
			log.error("Error al intentar crear la solicitud: " + e.getMessage(),e);
			model.addAttribute("error", "Error al crear la solicitud");
		} catch (DerechohabientesBusinessException e) {
			log.error("Error al intentar crear la solicitud" + e.getSituacion(), e);
			model.addAttribute("error", "Error al crear la solicitud");
		} catch (Exception e) {
			log.error("Error al intentar crear la solicitud", e);
			model.addAttribute("error", "Error al crear la solicitud");
		}
		
		model.addAttribute("registro", tramite);
		model.addAttribute("parentesco", idParentesco);
		model.addAttribute("isAsegurado", isAsegurado);
		session.setAttribute(KEY_SOLICITUD, solicitudCreada);
		session.setAttribute(KEY_IS_RETOMAR, false);
		session.setAttribute(KEY_REQUIERE_DOCS, requiereDocumentos);
		session.setAttribute(KEY_PERMITE_ELECCION_DOMICILIO, permiteEleccionDomicilio);

		return VIEW_DATOS_PERSONA;

	}
	
	/**
	 * 
	 * @param model
	 * @param solicitud
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/retomar")
	public String retomarSolicitud(Model model,@ModelAttribute(value="solicitudForm") Solicitud solicitud, HttpSession session, HttpServletRequest request) {

		AsignacionNSS asignacion = (AsignacionNSS) session.getAttribute(KEY_ASIGNACION_NSS);
		TramiteRegistroDerechohabiente tramite = null;
		Boolean requiereDocumentos = false;
		String view = null;
		Boolean permiteEleccionDomicilio = true;
		
		
		model.addAttribute("solicitudCreada", true);
		session.setAttribute(KEY_IS_RETOMAR, true);
		
		try {

			//Se consulta la solicitud
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			
			// Datos del acuse
			obtenerDatosAcuse(solicitud, asignacion, session);

			// Datos para la firma digital
			generarCadenaOriginal(solicitud, asignacion, session, asignacion.getNssStr());
			
			//Se verifica que no sea nula 
			if(solicitud != null) {
				model.addAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
				tramite = (TramiteRegistroDerechohabiente) solicitud.getTramites().get(0);
				session.setAttribute(KEY_SOLICITUD, solicitud);
				
				//permiteEleccionDomicilio = this.permiteEleccionDomicilio(tramite.getParentesco().getIdParentesco(), isPatronImss);
				try {
					requiereDocumentos = documentoProbatorioServiceBusinessRemote.requiereDocumentos(tramite.getTipoTramite().getIdTipoTramite().longValue());
				} catch (Exception e) {
					log.error("Ocurrio un error al consultar si el tramite requiere documentos",e);
				}
			} else {
				request.setAttribute("error", "No fue posible recuperar la solicitud");
			}
		} catch(Exception e) {
			solicitud = new Solicitud();
			request.setAttribute("error", "No fue posible recuperar la solicitud");
		}
		
		model.addAttribute("solicitud", solicitud);
		model.addAttribute("registro", tramite);
		session.setAttribute(KEY_REQUIERE_DOCS, requiereDocumentos);
		session.setAttribute(KEY_PERMITE_ELECCION_DOMICILIO, permiteEleccionDomicilio);
		
		if(tramite != null) {
			Long paso = tramite.getPaso();
			
			if(paso.equals(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId())) {
				view = VIEW_DATOS_PERSONA;
			} else if(paso.equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId())) {
				//Verificamos si no es el asegurado para buscar el domicilio particular y establecerlo
				this.validarRolesDomicilioParentesco(tramite, model, session);
				view = VIEW_DOMICILIO;
			} else if(paso.equals(PasoRegistroEnum.CAPTURA_UMF.getId())) {
				this.setMismaUmfAsegurago(tramite, model, session);
				//Se verifica si es registro de conyuge del mismo sexo
				this.verificarRegistroMismoSexo(tramite, model);
				//Se redirige a la vista de umf
				view = VIEW_UMF;
			}
		}
		
		return view;
	}
	
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> guardarSolicitudRegistro(@RequestBody TramiteRegistroDerechohabiente tramite, HttpSession session, 
			HttpServletRequest request) {
		
		Map<String, Object> result = this.procesarSiguienteGuardadoRegreso(tramite, 1, session, null);
		
		return result;
	}
	
	@RequestMapping(value = "/regresar", method = RequestMethod.POST)
	public String regresarPaso(@ModelAttribute TramiteRegistroDerechohabiente registro,Model model,HttpSession session, HttpServletRequest request) {
		
		Map<String, Object> result = this.procesarSiguienteGuardadoRegreso(registro, 2, session, model);
		
		return (String)result.get("view");
	}
	
	@RequestMapping( value ="/siguiente", method = RequestMethod.POST)
	public String siguientePaso(@ModelAttribute TramiteRegistroDerechohabiente registro, Model model,HttpSession session, HttpServletRequest request) {
		
		//La vista a donde iremos dependiendo del paso
		Map<String, Object> result= this.procesarSiguienteGuardadoRegreso(registro, 3, session, model);
		
		return (String)result.get("view");
	}
	
	private void validarRolesDomicilioParentesco(TramiteRegistroDerechohabiente registro, Model model, HttpSession session) {
		
		Boolean isPatronIMSS = (Boolean) session.getAttribute(KEY_PATRON_IMSS);
		GrupoFamiliar datosAsegurado = (GrupoFamiliar) session.getAttribute(KEY_DATOS_ASEGURADO);
		Domicilio domicilioAsegurado = datosAsegurado != null ? datosAsegurado.getDomicilio() : null;
		Map<String,Object> result = requisitosMinimosServiceRemote.validarEleccionDeDomicilioYUmfPorPersonaParentesco(
				registro.getFisica(),registro.getParentesco().getIdParentesco(), domicilioAsegurado,isPatronIMSS);
		
		PersonaDomicilio personaDomicilio = (PersonaDomicilio) result.get("personaDomicilio");
		Boolean setdomicilioAsegurado = (Boolean) result.get("setDomicilioAsegurado");

		if(!setdomicilioAsegurado) {
			if(registro.getDomicilio() == null) {	
				registro.setDomicilio(personaDomicilio.getDomicilio());
			}
				registro.setCvePersonaDomicilio(personaDomicilio.getCvePersonaDomicilio());
		}

		session.setAttribute(KEY_VALIDACIONES_DOM, result);
	
	}
	
	/**
	 * Metodo para saber si usaremos las mismos datos de adscripcion del asegurado
	 * @param model
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	private void setMismaUmfAsegurago(TramiteRegistroDerechohabiente tramiteXml,Model model, HttpSession session) {
		
		Long idParentesco = tramiteXml.getParentesco().getIdParentesco();
		Boolean isAsegurado = idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId());
		
		Map<String, Object> validacionesDom = null;
		GrupoFamiliar asegurado = null;
		Boolean isPatronIMSS = null;
		MedicoEnTurno medico = null;
		
		if(isAsegurado) {
			tramiteXml.setMedicoEnTurno(null);
			tramiteXml.setIndSeleccionMedico(1);
			tramiteXml.setFechaCambioMedico(null);
			return;
		} else {
			validacionesDom = (Map<String, Object>) session.getAttribute(KEY_VALIDACIONES_DOM);
			asegurado = (GrupoFamiliar) session.getAttribute(KEY_DATOS_ASEGURADO);
			isPatronIMSS = (Boolean) session.getAttribute(KEY_PATRON_IMSS);
			medico = asegurado == null ? null : asegurado.getMedicoEnTurno(); 
			
			if(validacionesDom == null) {
				validacionesDom = requisitosMinimosServiceRemote.validarEleccionDeDomicilioYUmfPorPersonaParentesco(tramiteXml.getFisica(),
						tramiteXml.getParentesco().getIdParentesco(), asegurado.getDomicilio(), isPatronIMSS);
				session.setAttribute(KEY_VALIDACIONES_DOM, validacionesDom);
			}
			
			Boolean setUmfAsegurado = (Boolean) validacionesDom.get("setUmfAsegurado");
			
			if(setUmfAsegurado) {
				if(medico != null) {
					if(medico != null) {
						tramiteXml.setMedicoEnTurno(medico);
						tramiteXml.setIndSeleccionMedico(0);
						tramiteXml.setFechaCambioMedico(asegurado.getFechaCambioTurnoMedico());
						return;
					}
				}
			} else {
				tramiteXml.setMedicoEnTurno(null);
				tramiteXml.setIndSeleccionMedico(1);
				tramiteXml.setFechaCambioMedico(null);
				return;
			}
			
		}
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
	
	/**
	 * 
	 * @param registro
	 * @param operacion 1 - guardado, 2 - regreso, 3 - siguiente
	 * @param session
	 * @param model
	 * @return
	 */
	private Map<String, Object> procesarSiguienteGuardadoRegreso(TramiteRegistroDerechohabiente registro,int operacion, HttpSession session, Model model) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		//La vista a donde iremos dependiendo del paso
		String view = null;
		//Recuperamos la solicitud
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		//Recuperamos el Paso del que provenimos
		Long pasoActual = registro.getPaso();
		//Recuperamos el tramite de la solicitud
		TramiteRegistroDerechohabiente tramiteXml = (TramiteRegistroDerechohabiente) solicitud.getTramites().get(0);
		//
		Boolean guardado = operacion == 1;

		if(operacion == 1) { //si estamos guardando
			//Verificamos que pantalla vamos a guardar para establecer solo los datos necesarios y respetar los demas
			if(pasoActual.equals(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId())) {
				//Actualizamos los datos personaes
				tramiteXml.setFisica(registro.getFisica());
				//Actualizamos la razon de registro
				tramiteXml.setRazonRegistro(registro.getRazonRegistro());
				tramiteXml.setIndHijosProcreados(registro.getIndHijosProcreados());
			} else if(pasoActual.equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId())) {
				//Actualizamos el domicilio
				tramiteXml.setDomicilio(registro.getDomicilio());
			} else if(pasoActual.equals(PasoRegistroEnum.CAPTURA_UMF.getId())) {
				//Actualizamos los datos de la UMF
				tramiteXml.setMedicoEnTurno(registro.getMedicoEnTurno());
				//actualizamos el indicador para saber si el derechohabiente eligio o no eligio un nuevo medico
				tramiteXml.setIndSeleccionMedico(registro.getIndSeleccionMedico());
				//actualizamos la fecha de cambio de medico
				tramiteXml.setFechaCambioMedico(registro.getFechaCambioMedico());
			}
		} else if(operacion == 2) {//si estamos regresando
			//Si el paso del que venimos fue del de los datos de adscripcion
			if(pasoActual.equals(PasoRegistroEnum.CAPTURA_UMF.getId())) {
				//indicamos que al paso al que regresaremos es a la captura de domicilio
				tramiteXml.setPaso(PasoRegistroEnum.CAPTURA_DOMICILIO.getId());
				//Y actualizamos los datos de adscripcion
				tramiteXml.setMedicoEnTurno(registro.getMedicoEnTurno());
				//actualizamos el indicador para saber si el derechohabiente eligio o no eligio un nuevo medico
				tramiteXml.setIndSeleccionMedico(registro.getIndSeleccionMedico());
				//actualizamos la fecha de cambio de medico
				tramiteXml.setFechaCambioMedico(registro.getFechaCambioMedico());
				//Establecemos la vista como la de captura de domicilio
				view = VIEW_DOMICILIO;
			}//Si el paso previo fue el de captura de domicilio 
			else if(pasoActual.equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId())){
				//el paso al que regresaremos sera a la captura de datos personales
				tramiteXml.setPaso(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId());
				//guardamos el domicilio de la persona
				tramiteXml.setDomicilio(registro.getDomicilio());
				//Establecemos la vista de captura de datos personales
				view = VIEW_DATOS_PERSONA;
			}
		} else if(operacion == 3) {//si estamos avanzando
			//Si el paso del que venimos fue del de captura de datos personales
			if(pasoActual.equals(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId())) {
				//guardamos el siguiente paso que seria el de captura de domicilio
				tramiteXml.setPaso(PasoRegistroEnum.CAPTURA_DOMICILIO.getId());
				//guardamos la razon de registro
				tramiteXml.setRazonRegistro(registro.getRazonRegistro());
				//Y actualizamos los datos de la persona
				tramiteXml.setFisica(registro.getFisica());
				tramiteXml.setIndHijosProcreados(registro.getIndHijosProcreados());
				//Verificamos si no es el asegurado para buscar el domicilio particular y establecerlo
				this.validarRolesDomicilioParentesco(tramiteXml, model, session);
				//Establecemos la vista como la de captura de domicilio
				view = VIEW_DOMICILIO;
			}//Si el paso previo fue el de captura de domicilkio 
			else if(pasoActual.equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId())){
				//el siguiente paso sera la captura de la umf y los establecemos
				tramiteXml.setPaso(PasoRegistroEnum.CAPTURA_UMF.getId());
				//guardamos el domicilio de la persona
				tramiteXml.setDomicilio(registro.getDomicilio());
				//Se verifica si es registro de conyuge del mismo sexo
				this.verificarRegistroMismoSexo(tramiteXml, model);
				//Se setea la umf del asegurado
				this.setMismaUmfAsegurago(tramiteXml,model, session);
				
				//Establecemos la vista de captura de UMF
				view = VIEW_UMF;
			}
		}
		
		try {
			//Se actualiza el xml del tramite
			tramiteXml = (TramiteRegistroDerechohabiente) solicitudBusinessRemote.actualizarXmlTramite(tramiteXml);
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(tramiteXml);
			
			if(guardado) {
				//seteamos el mensaje de que todo fue guardado bien
				result.put("mensaje", "Se han guardado correctamente los cambios");
			} else {
				//Se setean los atributos en el modelo y en la session
				model.addAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			}
			
			//seteamos los atributos de la solicitud en session
			session.removeAttribute(KEY_SOLICITUD);
			session.setAttribute(KEY_SOLICITUD, solicitud);
			
		} catch (TramiteNoEncontradoException e) {
			this.log.error("error tramite",e);
			//Verificamos donde debemos poner el error
			if(!guardado) {
				model.addAttribute("error", "Ocurri&oacute; un error al intentar guardar los cambios");
			} else {
				result.put("error", "Ocurri&oacute; un error al intentar guardar los cambios");
			}
		} catch (Exception e) {
			this.log.error("error desconocido",e);
			//Verificamos donde debemos poner el error
			if(!guardado) {
				model.addAttribute("error", "Ocurri&oacute; un error al intentar guardar los cambios");
			} else {
				result.put("error", "Ocurri&oacute; un error al intentar guardar los cambios");
			}
		}
		
		if(!guardado) {
			//Seteamos el xml en el modelo
			model.addAttribute("registro", tramiteXml);
		}
		
		//ponemos la vista en el mapa
		result.put("view", view);
		//Retornamos el map
		return result;
	}
	
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitudRegistro(@RequestBody TramiteRegistroDerechohabiente tramite,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) { 
	
		FirmaElectronica firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(KEY_CABEZA_GRUPO_FAMILIAR);
		List<Modalidad> modalidades = null;
		
		TramiteRegistroDerechohabiente tramiteXml = (TramiteRegistroDerechohabiente) solicitud.getTramites().get(0);
		
		//Obtenemos el paso que se va a guardar
		Long pasoAGuardar = tramite.getPaso();
		
		//Verificamos que pantalla vamos a guardar para establecer solo los datos necesarios y respetar los demas
		if(pasoAGuardar.equals(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId())) {
			//Actualizamos los datos personaes
			tramiteXml.setFisica(tramite.getFisica());
			//Actualizamos la razon de registro
			tramiteXml.setRazonRegistro(tramite.getRazonRegistro());
		} else if(pasoAGuardar.equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId())) {
			//Actualizamos el domicilio
			tramiteXml.setDomicilio(tramite.getDomicilio());
		} else if(pasoAGuardar.equals(PasoRegistroEnum.CAPTURA_UMF.getId())) {
			//Actualizamos los datos de la UMF
			tramiteXml.setMedicoEnTurno(tramite.getMedicoEnTurno());
			//actualizamos el indicador para saber si el derechohabiente eligio o no eligio un nuevo medico
			tramiteXml.setIndSeleccionMedico(tramite.getIndSeleccionMedico());
			//actualizamos la fecha de cambio de medico
			tramiteXml.setFechaCambioMedico(tramite.getFechaCambioMedico());
		}
		
		try {
			log.debug("Firma Electronica" + firma);
			log.debug("secuencia: " + firma.getSecuenciaNotaria());
			modalidades = this.getModalidades(cabeza, session);
			
			//Actualizamos el XML del tramite y lo seteamos en la solicitud ya que lo regresa con los documentos capturados
			tramiteXml = (TramiteRegistroDerechohabiente) solicitudBusinessRemote.actualizarXmlTramite(tramiteXml);
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(tramiteXml);
			solicitud.setFirmaElectronica(firma);
			
			//finalizamos la solicitud
			Map<String, Object> resultado = registroDerechohabienteServiceRemote.guardarRegistroDerechohabiente(solicitud, cabeza, modalidades);
			//obtenemos la solicitud con los ultimos cambios y en su caso los nuevos tramites como pueden ser cambio de clinica y autorizacion
			solicitud = (Solicitud) resultado.get("solicitud");
			//agregamos los ids de los documentos resultantes del tramite
			solicitud = solicitudBusinessRemote.agregarListadosDocumentosATramites(solicitud);
			//generamos los documentos resultantes
			solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
			//indicamos que la solicitud se finalio correctamente
			result.put("error", false);
			result.put("mensaje", "Tu solicitud ha finalizado correctamente");
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
		} catch(DerechohabientesBusinessException e){
			this.log.error("error tramite",e);
			result.put("error", true);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios - " + e.getSituacion());
		}catch (Exception e) {
			this.log.error("error desconocido",e);
			result.put("error", true);
			result.put("mensaje", "Ocurri&oacute; un error al intentar guardar los cambios");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/solicitud/cancelar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudBaja(@RequestBody Solicitud solicitud,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) { 
	
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudA = (Solicitud) session.getAttribute(KEY_SOLICITUD);

		if(solicitudA != null && solicitud.getSolicitudId().equals(solicitudA.getSolicitudId())) {
			log.debug("Existe una solicitud que se cancelara");
			try {
				solicitudBusinessRemote.cancelarSolicitud(solicitudA.getSolicitudId(), 5L,1L,null, "Solicitud cancelada a peticion del derechohabiente"); 
				result.put("mensaje", "La solicitud fue cancelada correctamente");
			} catch (SolicitudException e) {
				this.log.error(e);
				result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
			} 
		} else {
			result.put("mensaje", "El id de la solicitud a cancelar no coincide con la que se tiene en session");
		}

		result.put("solicitud", solicitud);
		
		return result;
	}
	
	public Usuario getUusuarioSession(HttpServletRequest request, HttpSession session) {
		
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		
		if(usuario == null) {
			UsuarioSSO usuarioSSO = this.procesarUsuarioSSO(request);
			usuario = this.getInformacionUsuario(usuarioSSO);
			session.setAttribute(KEY_USUARIO, usuario);
		}
		
		return usuario;
	}
	
	public Usuario getInformacionUsuario(UsuarioSSO usuariosso) {
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
		
		try {
			Fisica fisica = serviciosPersonaBusinessRemote.buscarPersonaFisicayDPyDyMCEnIMSS(usuariosso.getIdPersona().longValue());
			usuario.setFisica(fisica);
		} catch(Exception e) {
			log.error("No fue posible consultar a la persona relacionada con el usuario");
		}
		
		return usuario;
	}
	
	private List<Modalidad> getModalidades(CabezaGrupoFamiliar cabeza, HttpSession session) throws DerechohabientesBusinessException {
		@SuppressWarnings("unchecked")
		List<Modalidad> modalidades = (List<Modalidad>)session.getAttribute(KEY_MODALIDADES_SESSION);
		
		if(modalidades == null || modalidades.isEmpty()) {
			modalidades = grupoFamiliarServiceRemote.getModalidadesActivas(cabeza.getAsignacionNSS());
			session.setAttribute(KEY_MODALIDADES_SESSION, modalidades);
		}

		//Si no se obtienen modalidades se lanza una exception ciempre yh cuando el parnetesco de la cabeza sea asegurado
		if((modalidades == null || modalidades.isEmpty()) && cabeza.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId()) ) {
			log.debug("las modalidades estan vacias");
			DerechohabientesBusinessException.throwException(
					Constants.MENSAJE_MODALIDAD_NO_VALIDAD, Constants.MENSAJE_MODALIDAD_NO_VALIDAD);
		}
				
		return modalidades;
		
	}
	
	private List<Long> getIdsModalidades(CabezaGrupoFamiliar cabeza, HttpSession session) throws DerechohabientesBusinessException {
		@SuppressWarnings("unchecked")
		List<Long> idsModalidades = (List<Long>) session.getAttribute(KEY_IDS_MODALIDADES_ASEGURADO);
		
		if(idsModalidades == null || idsModalidades.isEmpty()) {
			idsModalidades = requisitosMinimosServiceRemote.obtenerModalidadesAsegurado(cabeza);
			session.setAttribute(KEY_IDS_MODALIDADES_ASEGURADO, idsModalidades);
		}
		return idsModalidades;
	}
	
	@RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody TramiteRegistroDerechohabiente oForm, final HttpServletResponse response, final HttpSession session) {
        log.debug("entramos a WizardRegistroDerechohabiente para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(KEY_CABEZA_GRUPO_FAMILIAR);
        Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
        Map<String, Object> result = new HashMap<String, Object>();
        TramiteRegistroDerechohabiente tramite = (TramiteRegistroDerechohabiente) solicitud.getTramites().get(0);
        
        final Errors errors = new BindException(oForm, "model");
        
        new RegistroDerechohabientesValidator().validate(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        } else {
        	
        	try{
        		List<Long> idsModalidades = this.getIdsModalidades(cabeza, session);
        		
        		//Se setean los datos del asegurado para las validaciones
	        	oForm.setDatosAsegurado(tramite.getDatosAsegurado());
	        	oForm.setTipoTramite(tramite.getTipoTramite());
	        	//Verificamos si el paso que validaremos es la captura de datos personales
	        	if(oForm.getPaso().equals(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId())) {
	        		
	        			result = requisitosMinimosServiceRemote.validaPersonaRegistrada(oForm.getFisica(), oForm.getDatosAsegurado().getIdAsignacionNSS());		
		        		if(result != null) {
		        			log.debug("Se superaron las validaciones de persona registrada");
		
		        			Boolean isCorrecto = (Boolean)result.get("correcto");
		        			log.debug("La validadcion de personas arrojo : " + isCorrecto);
		        			
		        			if(isCorrecto) {
		        				oForm.setTramiteId(tramite.getTramiteId());
		        				result = requisitosMinimosServiceRemote.requisitosMinimosRegistro(oForm, cabeza,OrigenSolicitudEnum.INTERNET.getId(),null, false, idsModalidades);
		        			}
		        		
		        		}
	        		
	        	} //Verificamos si validaremos el paso en el que se captura el domicilio 
	        	else if(oForm.getPaso().equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId())) {
	        		if(oForm.getParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId()) 
	        				|| oForm.getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()) ) {
	        			
	        			tramite.setPaso(oForm.getPaso());
	        			tramite.setDomicilio(oForm.getDomicilio());
	        			result = requisitosMinimosServiceRemote.requisitosMinimosRegistro(tramite, cabeza,OrigenSolicitudEnum.INTERNET.getId(),null, false, idsModalidades);
	        		} else {
	        			result.put("correcto", true);
	        		}
	        		
	        	}
        	
        	} catch(DerechohabientesBusinessException e) {
    			e.printStackTrace();
    			result.put("correcto", false);
    			result.put("mensaje", e.getMessage());
    		} catch(Exception e) {
    			e.printStackTrace();
    			result.put("correcto", false);
    			result.put("mensaje", e.getMessage());
    		}
        	
        }
        

        return result;
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
	
	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}

	@RequestMapping( value = "/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {

		session.removeAttribute(KEY_PARENTESCO_REGISTRO);
		session.removeAttribute(KEY_ASIGNACION_NSS);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_IS_RETOMAR);
		session.removeAttribute(KEY_CABEZA_GRUPO_FAMILIAR);
		session.removeAttribute(KEY_REQUIERE_DOCS);
		session.removeAttribute(KEY_PERMITE_ELECCION_DOMICILIO);
		session.removeAttribute(KEY_VALIDACIONES_DOM);
		session.removeAttribute(KEY_IDS_MODALIDADES_ASEGURADO);

		return null;
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
		contenidoAFirmar.append("Trámite:");
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
		contenidoAFirmar.append("Nombre o Razón Social:");
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
		contenidoAFirmar.append("Número de Seguridad Social:"+nss+"||");

		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
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