package mx.gob.imss.cdsss.delta.portal.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cdsss.delta.portal.controller.validator.DomicilioUmfValidator;
import mx.gob.imss.cdsss.delta.portal.utils.Constants;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PasoRegistroEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.UmfDomicilioDTO;
import mx.gob.imss.ctirss.delta.model.enums.EstadoCivilEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/derechohabientes/tramite/registro")
public class RegistroDerechohabienteDomicilio extends AbstractController{
	
	@Autowired
	private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private RegistroDerechohabienteServiceRemote registroDerechohabienteServiceRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired 
	private GeneraDocumentosAsincronos generaDocumentosAsincronos;
	@Autowired
	private DocumentosServiceRemote documentosServiceRemote;
	@Autowired
	private ServiceBusinessRemote serviceBusinessRemote;
	
	
	private final String MENSAJE_CANCELACION_SOLICITUD = "Se cancela la solicitud debido a que se inicia una nueva en portal ciudadano";
	
	@RequestMapping("/testPantalla")
	public String testPantalla(Model model, HttpSession session) {
		//mandamos el titulo del tramite
		model.addAttribute("tituloTramite", "derechohabientes.tramite.registro.ciudadano");
		model.addAttribute("tramite","registro");
		model.addAttribute(Constants.KEY_TIPO_TRAMITE, 48);
		//retornamos a la vista
		return "seleccionUmf";
	}
	
	@RequestMapping("/testPantalla2")
	public String testPantallaConDomicilio(Model model, HttpSession session) {
		//mandamos el titulo del tramite
		model.addAttribute("tituloTramite", "derechohabientes.tramite.registro.ciudadano");
		model.addAttribute(Constants.KEY_TIPO_TRAMITE, 48);
		model.addAttribute("tramite","registro");
		//retornamos a la vista
		return "seleccionUmfConDomicilio";
	}
	
	@RequestMapping("/inicio")
	public String iniciarRegistro(Model model, HttpSession session) {
		
		//Obtenemos los datos de session
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.KEY_CABEZA_GRUPO);
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.KEY_ASIGNACION_NSS);
		Long cveTipoTramite = (Long) session.getAttribute(Constants.KEY_TIPO_TRAMITE);
		boolean isRegistroAsegurado = cveTipoTramite.equals(TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue());
		
		//si es el registro de asegurado verificaremos si existe una solicitud
		if(isRegistroAsegurado) {
			//Verificamos la existencia de solicitud
			Map<String, Object> requisitos = requisitosMinimosServiceRemote.obtenerSolicitudRegistroAseguradoPensionado(asignacionNSS, OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
			//verificamos la existencia de solicitudes abiertas para el registro de asegurado o pensionado
			if(requisitos != null && requisitos.get("solicitudActiva") != null) {
				Solicitud solicitudActiva = (Solicitud) requisitos.get("solicitudActiva");
				this.cancelarSolicitud(solicitudActiva, MENSAJE_CANCELACION_SOLICITUD);
			}
		}
		
		//mandamos el titulo del tramite
		model.addAttribute("tituloTramite", isRegistroAsegurado ? "derechohabientes.tramite.registro.ciudadano" : "derechohabientes.tramite.registroHijos.title");
		
		//retornamos a la vista
		return "seleccionUmfConDomicilio";
	}
	
	@RequestMapping(value = "/validacionesCP", method = RequestMethod.POST) 
	public @ResponseBody Map<String, ? extends Object> validarCodigoPostal(final @RequestBody UmfDomicilioDTO oForm, final HttpServletResponse response, final HttpSession session) {
	
		Map<String, Object> result = new HashMap<String, Object>();
		final Errors errors = new BindException(oForm, "model");
		new DomicilioUmfValidator().validateCodigoPostal(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
		return result;
	}
	
	@RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody UmfDomicilioDTO oForm, final HttpServletResponse response, final HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		final Errors errors = new BindException(oForm, "model");
		//Validamos el formulario
		new DomicilioUmfValidator().validateVersionDomicilio(oForm, errors);

		if (errors.hasErrors()) {
			procesaErroresDeCaptura(errors, result, response);
			return result;
		} else {
			Fisica fisica = (Fisica) session.getAttribute("keyPersonaBeneficiario");
			Fisica medios = oForm.getFisica();

			boolean isRegistroAsegurado = ((Long) session.getAttribute(Constants.KEY_TIPO_TRAMITE)).equals(TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue());

			if(!isRegistroAsegurado && medios != null && fisica != null) {
				log.debug("Entro a setear los medios en la persona");
				fisica.setCorreoElectronico(medios.getCorreoElectronico());
				fisica.setTelefonoFijo(medios.getTelefonoFijo());
			}

			result = this.validaRequisitosRegistro(session, fisica);
			if(this.getEstadoValidaciones(result)) {
				TramiteRegistroDerechohabiente tramite = (TramiteRegistroDerechohabiente) result.get("tramite");
				tramite.setMedicoEnTurno(oForm.getMedicoEnTurno());
				tramite.setDomicilio(oForm.getDomicilio());
				session.setAttribute(Constants.KEY_TRAMITE, tramite);
			}
		}


		return result;
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/finalizar", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> finalizarRegistroDerechohabiente(@RequestBody UmfDomicilioDTO umfDomicilio, HttpSession session) {
		
		TramiteRegistroDerechohabiente tramiteXml = (TramiteRegistroDerechohabiente) session.getAttribute(Constants.KEY_TRAMITE);
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.KEY_CABEZA_GRUPO);
        List<Modalidad> modalidades = (List<Modalidad>) session.getAttribute(Constants.KEY_MODALIDADES_ACTIVAS);
        
        Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = null;
        
		//Se setean los datos de medico consultorio y turno
		tramiteXml.setMedicoEnTurno(umfDomicilio.getMedicoEnTurno());
		//Se setean los datos del domicilio
		tramiteXml.setDomicilio(umfDomicilio.getDomicilio());
		
		log.debug("El tramite que se mandara a finalizar es: " + tramiteXml);
		
		try {
			//se crea la solicitud de registro de derechohabiente
			solicitud = registroDerechohabienteServiceRemote.registraSolicitud(tramiteXml, OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
		} catch(Exception e) {
			e.printStackTrace();
			return this.procesarErrorNegocio("ocurrio un error al crear la solicitud");
		}
		
		if(solicitud != null) {
			String error = null;
			
			try {
				//finalizamos la solicitud
				Map<String, Object> resultado = registroDerechohabienteServiceRemote.guardarRegistroDerechohabiente(solicitud, cabeza, modalidades);
				//obtenemos la solicitud del map resultante
				solicitud = (Solicitud)resultado.get("solicitud");
				GrupoFamiliar grupo = (GrupoFamiliar)resultado.get("grupo");
				FirmaElectronica firmaElectronica = solicitud.getFirmaElectronica();
				generaDocumentosAsincronos.generaDocumentosAsincrono(solicitud, session);
				try{
					for (Tramite t : solicitud.getTramites()) {
						Integer idTipoTramite = t.getTipoTramite().getIdTipoTramite();
						System.out.println("idTipoTramite:"+idTipoTramite);
						if(t instanceof TramiteRegistroDerechohabiente){
							String homoclaveTramite = t.getTipoTramite().getHomoclave();
							String titulo = t.getTipoTramite().getDescripcion();
							byte[] docto = (byte[])documentosServiceRemote.getDocumentoAcuseDeRecibo(solicitud.getNoFolioSolicitud(), titulo, firmaElectronica, grupo);
							session.setAttribute(Constants.KEY_ACUSE_TRAMITE, docto);
							session.setAttribute(Constants.KEY_HOMOCLAVE, homoclaveTramite);
							break;
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			} catch (DerechohabientesBusinessException e) {
				log.error("Ocurrio un error de derechohabientes", e);
				e.printStackTrace();
				error = e.getSituacion();
			} catch (SolicitudNoValidaException e) {
				log.error("Ocurrio un error de solicitud", e);
				e.printStackTrace();
				error = e.getMessage();
			} catch (SolicitudNoEncontradaException e) {
				log.error("Ocurrio un error de solicitud", e);
				e.printStackTrace();
				error = e.getMessage();
			} catch (SolicitudException e) {
				log.error("Ocurrio un error de solicitud", e);
				e.printStackTrace();
				error = e.getMessage();
			} catch (ImpactaAlmacenesWSException e) {
				log.error("Ocurrio un error al impactar el ws de vigencia", e);
				e.printStackTrace();
				error = e.getMessage();
			} catch (SolicitudEnProcesoException e) {
				log.error("Ocurrio un error de solicitud", e);
				e.printStackTrace();
				error = e.getMessage();
			} catch (Exception e) {
				log.error("Ocurrio un error desconocido", e);
				e.printStackTrace();
				error = e.getMessage();
			}
			
			if(error != null) {
				return this.procesarErrorNegocio(error);
			}
		}
		
		session.setAttribute(Constants.KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
		session.setAttribute(Constants.KEY_SOLICITUD, solicitud);
		session.setAttribute(Constants.KEY_DATOS_DOM_UMF, umfDomicilio);
		result.put("correcto", true);
		result.put("mensaje", "todo ok");
		
		return result;
	}
	
	private Map<String, Object> procesarErrorNegocio(String error) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("correcto", false);
		result.put("mensaje", error);
		
		return result;
	}
	
	private TramiteRegistroDerechohabiente llenarTramiteRegistro(Fisica fisica, Long idParentesco, AsignacionNSS nss, Boolean patronImss) {
		TramiteRegistroDerechohabiente tramite = new TramiteRegistroDerechohabiente();

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
		//Y la razon de registro debe ser normal
		razonRegistro.setIdRazonRegistro(RazonRegistroEnum.NORMAL.getId());
		//Verificamos el parentesco a registrar para establecer la razon de registro y el estado civil
		if(idParentesco.equals(ParentescoEnum.HIJOS.getId())){
			//Si el parentesco es hijo, el estado civil debe ser soltero
			idEstadoCivil = EstadoCivilEnum.SOLTERO.getId();
			razonRegistro = this.getRazonRegistroHijos(fisica, patronImss);
		} else if(idParentesco.equals(ParentescoEnum.CONYUGE.getId())) {
			//Si el parentesco es conyuge, el estado civil debe ser casado
			idEstadoCivil = EstadoCivilEnum.CASADO.getId();
		} else if(idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())) {
			//Si el parentesco es concubina el estado civil debe ser concubinato
			idEstadoCivil = EstadoCivilEnum.CONCUBINATO.getId();
		}
		
		tramite.setPaso(1L);
		//Seteamos la razon del registro
		tramite.setRazonRegistro(razonRegistro);
		//Seteamos el estado civil 
		tramite.getFisica().setEstadoCivil(new EstadoCivil());
		tramite.getFisica().getEstadoCivil().setIdEstadoCivil(idEstadoCivil.intValue());
		tramite.setTipoTramite(this.getTipoTramite(tramite.getParentesco()));
		
		return tramite;
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
		log.debug(" la razon de registro quedo como : [" +razonRegistro.getIdRazonRegistro()+"]");
		
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
	
	private Boolean getEstadoValidaciones(Map<String, Object> validaciones) {
		return (Boolean) validaciones.get("correcto");
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
	
	private void cancelarSolicitud(Solicitud solicitud, String observaciones) {
		
		if(solicitud != null && solicitud.getSolicitudId() != null) {
			log.debug("Se cancelara la solicitud con id " + solicitud.getSolicitudId());
			try {
				solicitudBusinessRemote.cancelarSolicitud(solicitud.getSolicitudId(), 5L,1L,null, observaciones);
			} catch (SolicitudException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private Long getCveParentescoPorTipoTramite(Integer tipoTramite) {
		Long idParentesco = null;
		
		if(tipoTramite.equals(TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo())) {
			idParentesco = ParentescoEnum.ASEGURADO.getId();
		} else if(tipoTramite.equals(TipoTramiteEnum.REGISTRO_PENSIONADO.getCodigo())) {
			idParentesco = ParentescoEnum.PENSIONADO.getId();
		}else if(tipoTramite.equals(TipoTramiteEnum.REGISTRO_HIJOS.getCodigo())) {
			idParentesco=ParentescoEnum.HIJOS.getId();
		}else if(tipoTramite.equals(TipoTramiteEnum.REGISTRO_CONYUGUE.getCodigo())) {
			idParentesco = ParentescoEnum.CONYUGE.getId();
		} else if(tipoTramite.equals(TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo())){
			idParentesco=ParentescoEnum.CONCUBINARIO.getId();
		} else {
			idParentesco=ParentescoEnum.PADRES.getId();
		}
		return idParentesco;
	}
	
	@RequestMapping(value = "/busquedaPersona", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> busquedPersona(HttpSession session,@RequestBody Fisica fisica, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		String mensaje = "OK";
		Fisica fisicaEncontrada = null;

		final Errors errors = new BindException(fisica, "model");
		//Validamos el formulario
		new DomicilioUmfValidator().validatePersona(fisica, errors);

		if (errors.hasErrors()) {
			procesaErroresDeCaptura(errors, result, response);
			result.put("error", true);
			return result;
		}

		try {
			
			if(fisica.getCurp() != null) {
				List<Fisica> lista = serviceBusinessRemote.localizarPersonaReglasDerechohabiente(fisica);
				
				if(lista != null) {
					if(lista.size() == 1) {
						fisicaEncontrada = lista.get(0);
						//validamos los requitos para el tramite
						result = this.validaRequisitosRegistro(session, fisicaEncontrada);
						if(!this.getEstadoValidaciones(result)) {
							mensaje = (String) result.get("mensaje");
						} else {
							session.setAttribute("keyPersonaBeneficiario",fisicaEncontrada);
						}
					} else {
						mensaje = "Se encontraron inconsistencia en los datos relacionados con la CURP, acuda a ventanilla a realizar el tr&aacute;mite";
					}
				}
			} else {
				mensaje = "Es necesario proporcionar la CURP para realizar la b&uacute;squeda";
			}
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error("el curp no fue localizado", e);
			mensaje = e.getSituacion();
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error("el cliente de renapo no se encuentra disponible", e);
			mensaje = e.getSituacion();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error("Error en la validacion de datos", e);
			mensaje = e.getSituacion();
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error("error al comparar renapo y datos enviados", e);
			mensaje = e.getSituacion();
		} catch (DatosInsuficientesParaConsultaException e) {
			log.error("datos insuficientes", e);
			mensaje = e.getSituacion();
		}
		
		result.put("error", !mensaje.equals("OK"));
		result.put("mensaje", mensaje);
		result.put("fisica", fisicaEncontrada);
		
		return result;
	}
	
	@RequestMapping(value="/getTramiteRegistro", method = RequestMethod.POST)
	public @ResponseBody TramiteRegistroDerechohabiente getTramiteRegistro(HttpSession session) {
		TramiteRegistroDerechohabiente registro = (TramiteRegistroDerechohabiente) session.getAttribute(Constants.KEY_TRAMITE);
	
		return registro;
	}
	
	@RequestMapping(value = "/validacionesMedios", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody Persona oForm, final HttpServletResponse response, final HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		final Errors errors = new BindException(oForm, "model");
		new DomicilioUmfValidator().validateMedios(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
		return result;
		
	}
	
    @SuppressWarnings("unchecked")
	private Map<String, Object> validaRequisitosRegistro(HttpSession session, Fisica fisica) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	//pobtenemos las variables de session
        CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.KEY_CABEZA_GRUPO);
        List<Long> idsModalidades = (List<Long>) session.getAttribute(Constants.KEY_IDS_MODALIDADES);
        AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.KEY_ASIGNACION_NSS);
        Long cveTipoTramite = (Long) session.getAttribute(Constants.KEY_TIPO_TRAMITE);
		boolean isRegistroAsegurado = cveTipoTramite.equals(TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue());
		Long idParentesco = isRegistroAsegurado ? cabeza.getCalidadParentesco().getIdParentesco() : getCveParentescoPorTipoTramite(cveTipoTramite.intValue());
        //llenamos el tramite con la informacion
      	if(asignacionNSS != null) {
      		String correo = (String) session.getAttribute(Constants.KEY_CORREO_CIUDADANO);
      		asignacionNSS.setCorreoElectronico(new CorreoElectronico(correo));
      	}
      	//llenamos el objeto de registro de derechohabientes, para realizar las validaciones
		TramiteRegistroDerechohabiente tramite = this.llenarTramiteRegistro(isRegistroAsegurado ? asignacionNSS : fisica , idParentesco, asignacionNSS, cabeza.getPatronImss().equals(1));
    	try {
    		//Validamos si la persona se encuentra registrada dentro del grupo familiar
    		result = requisitosMinimosServiceRemote.validaPersonaRegistrada(tramite.getFisica(), tramite.getDatosAsegurado().getIdAsignacionNSS());
			log.debug("Se superaron las validaciones de persona registrada, la validadcion de personas arrojo : " + this.getEstadoValidaciones(result));
			//si la persona no se encuentra registrada validaremos las otras reglas de negocio
			if(this.getEstadoValidaciones(result)) {
				//validamos las reglas de negocio dependiendo el tipo de tramite que se realizo
				result = requisitosMinimosServiceRemote.requisitosMinimosRegistro(tramite, cabeza,OrigenSolicitudEnum.PORTAL_CIUDADANO.getId(),null, false, idsModalidades);
				//si no existe ningun error en las reglas, anadimos el tramite al map
				if(this.getEstadoValidaciones(result)) {
					result.put("tramite", tramite);
				}
			}
    	} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			result.put("correcto", false);
			result.put("mensaje", e.getMessage());
    	}
        
    	return result;
    }

}