package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.domicilio.AsentamientoNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.LocalidadNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.domicilio.VialidadesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.WsAntecedentesAseguradoExcpetion;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.AsignacionNssPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorValidacionIdentidadCertificacionException;
import mx.gob.imss.ctirss.delta.exception.individuo.PortalCiudadanoException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.AseguradoServiciosExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.global.model.UnidadMedicaFamiliarTO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCamino;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCarretera;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.CarreteraAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.DerechoTransitoEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.MargenEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TerminoGeneralEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSerieEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.CambioCurpResponse;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.TramiteCambioCurpDTO;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.ValidaRequisitosCambioCurpResponse;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.VerificarCambioCurpResponse;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.AseguradoWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CiudadanoCurpCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.nss.ModuloOrigenAsignacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.ws.asignacion.externo.ResponseNssPasoacambioResource;
import mx.gob.imss.ctirss.ws.asignacion.externo.WSNssPasoaCambio;
import mx.gob.imss.ctirss.ws.asignacion.externo.WSNssPasoaCambio_Service;
import mx.gob.imss.digital.modelo.domicilio.Asentamiento;
import mx.gob.imss.digital.modelo.domicilio.Camino;
import mx.gob.imss.digital.modelo.domicilio.Carretera;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.domicilio.EntidadFederativa;
import mx.gob.imss.digital.modelo.domicilio.Localidad;
import mx.gob.imss.digital.modelo.domicilio.Municipio;
import mx.gob.imss.digital.modelo.domicilio.TipoAdministracion;
import mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito;
import mx.gob.imss.digital.modelo.domicilio.TipoMargen;
import mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral;
import mx.gob.imss.digital.modelo.domicilio.TipoVialidad;
import mx.gob.imss.digital.modelo.domicilio.Vialidad;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

@Stateless(name="serviciosExternosAseguradosBusiness" ,mappedName="serviciosExternosAseguradosBusiness" )
public class ServiciosExternosAseguradosBusiness extends AbstractServiceBusiness implements AseguradoServiciosExternosRemote{
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([hmHM]{1}[a-zA-Z]{2}"
			+ "[b-df-hj-np-tv-zB-DF-HJ-NP-TV-Z]{3}[a-zA-Z0-9]{1}[0-9]{1})$";
	private static final int LONGITUD_CURP = 18;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String CODIGO_EXITO = "001";
	
	@EJB
	private SolicitudNssCorreoServiceBusinessRemote solicitudCorreoBusiness;
	
	@EJB
	private ServiceBusinessRemote serviceBusiness;

	@EJB
	private ComponentesExternosBusinessRemote componentesExternosBusiness;

	@EJB
	private SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusiness;
	
	@EJB(name = "solicitudServiceBusiness", mappedName = "solicitudServiceBusiness")
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;

	@EJB(name = "domicilioServiceBusiness", mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	
	@EJB(name = "grupoFamiliarService", mappedName = "grupoFamiliarService")
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB(name = "personaBusiness", mappedName = "personaBusiness")
	private PersonaBusinessRemote personaBusiness;
	@EJB(name = "portalCiudadanoServiceBusiness", mappedName = "portalCiudadanoServiceBusiness")
	private PortalCiudadanoServiceBusinessRemote portalCiudadanoServiceBusinessRemote;
	@EJB(name = "solicitudTramiteBusiness", mappedName = "solicitudTramiteBusiness")
	private SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	@EJB
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
	
	private static final String CURP_NO_LOCALIZADA = "No se localiz\u00F3 informaci\u00F3n en RENAPO con la CURP capturada";
	private static final String MENSAJE_ACTUALIZACION_GENERICO = "Los datos registrados en el IMSS asociados a la CURP, presentan alguna inconsistencia, por favor acude a tu Subdelegación para realizar la solicitud de regularización.";
	private static final String ERROR_ROL_EXISTENTE = "Sus datos no pueden ser actualizados por este medio, por favor acude a tu subdelegaci\u00F3n para realizar la solicitud de actualizaci\u00F3n.";
	private static final String ERROR_NSS_NO_LOCALIZADO = "No se localiz\u00F3 ningún registro con el NSS capturado";
	private static final String ERROR_PERFILES="No fue posible validar los perfiles de la persona.";
	private static final String ERROR_ALMACENES_VIGENCIA = "No fue posible realizar el calculo de vigencia.";
	private static final String ERROR_DESCONOCIDO = "No fue posible finalizar la solicitud de actualizaci\u00F3n de datos.";
	private static final String INFORMACION_INCORRECTA = "La informaci\u00F3n proporcionada para la solicitud es incorrecta.";
	private static final String INFORMACION_INCOMPLETA = "La informaci\u00F3n proporcionada para la solicitud es incompleta.";
	private static final String NSS_INEXISTENTE = "No existe el NSS proporcionado.";
	private static final String MENSAJE_ACTUALIZACION_CURP = "Los datos registrados en el IMSS asociados al NSS, presentan alguna inconsistencia, por favor acude a tu Subdelegación para realizar la solicitud de regularización.";
	
	@Override
	public int validarDerechoAConsultaNSS(String curp, String correo) throws SolicitudNssCorreoException {

		// Validacion del formato de la CURP
		if (StringUtils.isBlank(curp) || curp.length() != LONGITUD_CURP) {
			throw new SolicitudNssCorreoException("La CURP tiene que ser de " + LONGITUD_CURP + " caracteres");
		} else if (!curp.matches(REGEX_CURP_FISICA)) {
			throw new SolicitudNssCorreoException("La CURP no cumple con el formato requerido");
		}

		// Validacion del formato de correo
		if (StringUtils.isBlank(correo)) {
			throw new SolicitudNssCorreoException("El correo no debe ser nulo o vac\u00EDo");
		} else if (!correo.matches(EMAIL_PATTERN)) {
			throw new SolicitudNssCorreoException("El correo electronico no cumple con el formato requerido");
		}

		SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
		nssCorreo.setCurp(curp);
		CorreoElectronico correoObj = new CorreoElectronico();
		correoObj.setCorreo(correo);
		nssCorreo.setCorreo(correoObj);

		return solicitudCorreoBusiness.isConsultaRegistroNSSValid(nssCorreo, true);
	}



	@Override
	public void consultaNssAseguradoIdentidad(String curp, String nss)
			throws ErrorValidacionIdentidadCertificacionException  {
		
		//Creamos el objeto de Fisica
		Fisica fisica = new Fisica();
		fisica.setNss(nss);
		fisica.setCurp(curp);
		
		//Invocamos el servicio de validacion.
		try {
			this.serviceBusiness.validacionesNSSConsultaVigencia(fisica);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			throw new ErrorValidacionIdentidadCertificacionException("El CURP no fue localizado en la entidad externa RENAPO", 4);
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
			throw new ErrorValidacionIdentidadCertificacionException("Error en el servicio externo de RENAPO", 5);
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
			throw new ErrorValidacionIdentidadCertificacionException("Error en la validacion de los datos de la consulta de RENAPO", 6);
		} catch (ErrorComparacionDatosRENAPOException e) {
			e.printStackTrace();
			throw new ErrorValidacionIdentidadCertificacionException("Existen diferencias con el nombre del solicitante registrado en el IMSS. El solicitante deber\u00E1 acudir a la ventanilla del Instituto a aclarar la causa del rechazo", 16);
		} catch (AsignacionNSSNoLocalizadoException e) {
			e.printStackTrace();
			throw new ErrorValidacionIdentidadCertificacionException("NSS no localizado en el IMSS. El solicitante deber\u00E1 acudir a la ventanilla del Instituto a aclarar la causa del rechazo.", 40);
		}
		
	}

	@Override
	public String asignarNSS(String curp, String correo,
			UnidadMedicaFamiliarTO umfTO, Domicilio domicilio)
			throws AsignacionNssPersonaException, SolicitudException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException, SolicitudNoValidaException,
			SolicitudNoEncontradaException, AfectacionDatosPersonaException,
			PersonaNoEncontradaException, RegistroPersonaFisicaException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException, DomicilioNoValidoException,
			PersonaSinCalificacionesException {
		
		
		
		
		// Se asocia domicilio ingresado
					mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioSolicitud =
							new mx.gob.imss.ctirss.delta.model.domicilio.Domicilio();
		try {
			Asentamiento asentamiento = domicilio.getAsentamiento();
			Vialidad vialidadPrimaria = domicilio.getVialidadPrimaria();
			
			if (asentamiento == null || StringUtils.isBlank(asentamiento.getClave())) {
				throw new AsignacionNssPersonaException("Es necesario ingresar la informaci\u00F3n del Asentamiento");
			}
			
			if(domicilio.getAsentamiento().getLocalidad() == null && 
					vialidadPrimaria != null){
				validarDatosEntrada(curp, correo, umfTO, domicilio, true);
				convertirDomicilioSolicitud(domicilio, domicilioSolicitud);
			}else{
				validarDatosEntrada(curp, correo, umfTO, domicilio,false);
				domicilioSolicitud = domicilioServiceBusiness.complementarLocalidadDomicilioDigRecortado(domicilio);
			}
		} catch (DomicilioNoValidoException e1) {
			e1.printStackTrace();
			throw new AsignacionNssPersonaException("Se detectaron los siguientes errores al validar los datos ingresados:\n" + e1);
		} catch (DomicilioNoLocalizadoException e1) {
			e1.printStackTrace();
			throw new AsignacionNssPersonaException("Se detectaron los siguientes errores al validar los datos ingresados:\n" + e1);
		} catch (VialidadesNoLocalizadasException e) {
			e.printStackTrace();
			throw new AsignacionNssPersonaException("Se detectaron los siguientes errores al validar los datos ingresados:\n" + e);
		}

		// Se pasa a minúsculas el correo capturado
		String correoCapturado = correo.toLowerCase();

		// Se llena objeto con los datos de búsqueda
		Fisica fisica = new Fisica();
		fisica.setCurp(curp);
		CorreoElectronico correoElectronico = new CorreoElectronico();
		correoElectronico.setCorreo(correoCapturado);
		fisica.setCorreoElectronico(correoElectronico);
		

		// Validacion de consulta de NSS
		SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
		nssCorreo.setCorreo(fisica.getCorreoElectronico());
		nssCorreo.setCurp(fisica.getCurp());
		nssCorreo.setCveIdTipoSolicitud(TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue());

		// Se realiza consulta de la Persona
		Fisica fisicaEncontrada = null;
		try {
			// Validacion de consultas realizadas por correo
			int codigoRespuesta = solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, false);
			nssCorreo.setOperacionEjecutar(codigoRespuesta);

			// Validacion de Solicitudes y Documentos Probatorios
			fisicaEncontrada = serviceBusiness.validacionesNSSIncluyeCL3(fisica, true);

			if (fisicaEncontrada.getIdPersona() != null) {
				validarSolicitudesAsociadasAsegurado(fisicaEncontrada);

				//Se obtienen los documentos probatorios de la persona localizada, solo si fue localizada en el IMSS 
				fisicaEncontrada.setDocumentosProbatorios(componentesExternosBusiness
								.getDocumentosProbatoriosPersona(fisicaEncontrada));
				pasarDocumentosProbatorios(fisicaEncontrada);
			}

			// Se resetea medios de contacto para que solo sea tome en cuenta el correo para el proceso
			fisicaEncontrada.getMediosContacto().clear();
			fisicaEncontrada.setCorreoElectronico(correoElectronico);

			// Se crea la solicitud de asignacion con los datos encontrados en la validacion (BDTU y/o RENAPO)
			Solicitud solicitud = serviceBusiness.crearSolicitudAsignacionNSS(fisicaEncontrada, TipoSerieEnum.ORDINARIA,
					OrigenSolicitudEnum.MOVILES, curp, nssCorreo, null, ModuloOrigenAsignacionEnum.MOVILES);
			this.log.debug("Solicitud recien creada que se sube a sesion: " + solicitud);

			//se coloca la persona que esta realizando el tramite con su CURP como usuario y se actualiza solicitud
			Usuario usuario = new Usuario();
			usuario.setUsuario(curp);
			solicitud.setSolicitante(usuario);	
			solicitud.setFirmadaDigitalmente(false);
			
			

			// Se realiza conversion del domicilio ingresado en el servicio para
			// colocar el tipo de dato Domicilio correspondiente a la solcitud
			//convertirDomicilioSolicitud(domicilio, domicilioSolicitud);
			TipoDomicilio tipoDomicilio = new TipoDomicilio();
			tipoDomicilio.setClave(TipoDomicilioEnum.PARTICULAR.getCodigo().intValue());
			domicilioSolicitud.setDicTipoDomicilio(tipoDomicilio);

			// Se asocia UMF
			UnidadMedicaFamiliar umf = new UnidadMedicaFamiliar();
			BeanUtils.copyProperties(umfTO, umf);
			fisicaEncontrada.setUmf(umf);

			List<Tramite> tramites = solicitud.getTramites();
			for (Tramite tramite : tramites) {
				if (tramite instanceof TramiteFisica) {
					TramiteFisica tramiteAlta = (TramiteFisica) tramite;
					tramiteAlta.getFisica().getDomicilios().clear();
					tramiteAlta.getFisica().getDomicilios().add(domicilioSolicitud);
				} else if (tramite instanceof TramiteAsegurado) {
					this.log.debug("Se agrega domicilio a tramite de asignacion NSS desde Asignación externo");
					TramiteAsegurado tramiteAseguradoSession = (TramiteAsegurado) tramite;
					tramiteAseguradoSession.getFisica().getDomicilios().clear();
					tramiteAseguradoSession.getFisica().getDomicilios().add(domicilioSolicitud);
				}
			}

			solicitud = serviceBusiness.guardarSolicitudAsignacionNSS(solicitud, umf, null);

			AseguradoWrapper aseguradoWrapper = serviceBusiness
					.procesarSolicitudAsignacionNSS(solicitud.getSolicitudId());

			//solicitudServiceBusiness.generarDocumentos(solicitud.getNoFolioSolicitud());

			return aseguradoWrapper.getNss();
		} catch (GenerarNSSException e) {
			throw new AsignacionNssPersonaException(e.getMessage());
		} catch (PersonaConNSSException e) {
			throw new AsignacionNssPersonaException(e.getMessage());
		} catch (DomicilioNoLocalizadoException e) {
			throw new AsignacionNssPersonaException(e.getMessage());
		} catch (UmfNoLocalizadaException e) {
			throw new AsignacionNssPersonaException(e.getMessage());
		} catch (SolicitudNssCorreoException e) {
			throw new AsignacionNssPersonaException(e.getMessage());
		} 
	}

	
	@Override
	public void convertirDomicilioSolicitud(
			Domicilio domicilio,
			mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioSolicitud)
			throws VialidadesNoLocalizadasException,
			AsignacionNssPersonaException, DomicilioNoValidoException {
		mx.gob.imss.ctirss.delta.model.domicilio.Localidad localidadSolicitud =
				new mx.gob.imss.ctirss.delta.model.domicilio.Localidad();
		mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento asentamientoSolicitud =
				new mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento();

		// Conversion Asentamiento
		Asentamiento asentamiento = domicilio.getAsentamiento();
		Localidad localidad = asentamiento.getLocalidad();

		Long periodo = domicilio.getAsentamiento().getPeriodo();
		if (periodo == 0L) {
			periodo = 4L;
		}

		// Conversion de vialidad Primaria y Localidad
		Vialidad vialidadPrimaria = domicilio.getVialidadPrimaria();
		if (localidad == null) {
			mx.gob.imss.ctirss.delta.model.domicilio.Vialidad vialidadPrimariaSolicitud =
					new mx.gob.imss.ctirss.delta.model.domicilio.Vialidad();
			mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad tipoVialidadSolicitud =
					new mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad();

			// Si no se ingresa localidad se obtiene con los datos de la vialidad primaria
			if (vialidadPrimaria == null || vialidadPrimaria.getClave() == null || vialidadPrimaria.getClave() == 0) {
				throw new AsignacionNssPersonaException("No es posible determinar la Localidad ya que no existe informacion de la Vialidad Primaria");
			} else {
				vialidadPrimariaSolicitud.setClave(vialidadPrimaria.getClave());
				if (StringUtils.isNotBlank(vialidadPrimaria.getNombre())) {
					vialidadPrimariaSolicitud.setNombre(vialidadPrimaria.getNombre());
				} else {
					vialidadPrimariaSolicitud.setNombre("");
				}

				TipoVialidad tipoVialidad = vialidadPrimaria.getTipoVialidad();
				tipoVialidadSolicitud.setClave(tipoVialidad.getClave());
				tipoVialidadSolicitud.setDescripcion(tipoVialidad.getDescripcion());
				vialidadPrimariaSolicitud.setTipoVialidad(tipoVialidadSolicitud);

				mx.gob.imss.ctirss.delta.model.domicilio.Localidad localidadEncontrada = domicilioServiceBusiness
						.getLocalidadByVialidad(vialidadPrimariaSolicitud);
				mx.gob.imss.ctirss.delta.model.domicilio.Vialidad vialidadPrimariaArgs =
						domicilioServiceBusiness.getVialidad(vialidadPrimariaSolicitud);
				mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioEncontrado = domicilioServiceBusiness
						.obtenerVialidadElegida(localidadEncontrada, periodo.intValue(), vialidadPrimariaArgs);

				mx.gob.imss.ctirss.delta.model.domicilio.Localidad localidadSeleccionada = domicilioEncontrado.getLocalidad();
				vialidadPrimariaSolicitud = domicilioEncontrado.getVialidadPrimaria();

				localidadSolicitud.setClave(localidadSeleccionada.getClave());
				localidadSolicitud.setNombre(localidadEncontrada.getNombre());
				localidadSolicitud.setMunicipio(localidadEncontrada.getMunicipio());

				domicilioSolicitud.setVialidadPrimaria(vialidadPrimariaSolicitud);
			}
		} else {
			// Si se ingresa localidad, se realiza su conversion
			mx.gob.imss.ctirss.delta.model.domicilio.Municipio municipioSolicitud =
					new mx.gob.imss.ctirss.delta.model.domicilio.Municipio();
			mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa entidadFederativaSol =
					new mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa();

			Municipio municipio = localidad.getMunicipio();
			EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();

			entidadFederativaSol.setClave(entidadFederativa.getClave());
			entidadFederativaSol.setNombre(entidadFederativa.getNombre());

			municipioSolicitud.setClave(municipio.getClave());
			municipioSolicitud.setNombre(municipio.getNombre());
			municipioSolicitud.setEntidadFederativa(entidadFederativaSol);

			localidadSolicitud.setMunicipio(municipioSolicitud);
			//localidadSolicitud.setClave(localidad.getClave());

			// Conversion vialidades
			if (vialidadPrimaria != null) {
				mx.gob.imss.ctirss.delta.model.domicilio.Vialidad vialidadPrimariaSolicitud =
						new mx.gob.imss.ctirss.delta.model.domicilio.Vialidad();
				mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad tipoVialidadSolicitud =
						new mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad();

				TipoVialidad tipoVialidad = vialidadPrimaria.getTipoVialidad();
				tipoVialidadSolicitud.setClave(tipoVialidad.getClave());
				tipoVialidadSolicitud.setDescripcion(tipoVialidad.getDescripcion());
				vialidadPrimariaSolicitud.setTipoVialidad(tipoVialidadSolicitud);

				// Se determina si la vialidad primaria es nueva
				if (StringUtils.isNotBlank(vialidadPrimaria.getNombre())
						&& (vialidadPrimaria.getClave() == null || vialidadPrimaria.getClave() == 0)) {
					vialidadPrimariaSolicitud.setNombre("NINGUNO");

					mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioEncontrado = domicilioServiceBusiness
							.obtenerVialidadElegida(localidadSolicitud, periodo.intValue(), vialidadPrimariaSolicitud);
					vialidadPrimariaSolicitud = domicilioEncontrado.getVialidadPrimaria();
					mx.gob.imss.ctirss.delta.model.domicilio.Localidad localidadSeleccionada = domicilioEncontrado.getLocalidad();

					mx.gob.imss.ctirss.delta.model.domicilio.Localidad localidadEncontrada = domicilioServiceBusiness
							.getLocalidadByVialidad(vialidadPrimariaSolicitud);

					localidadSolicitud.setClave(localidadSeleccionada.getClave());
					localidadSolicitud.setNombre(localidadEncontrada.getNombre());
					vialidadPrimariaSolicitud.setNombre(vialidadPrimaria.getNombre());
				} else {
					vialidadPrimariaSolicitud.setClave(vialidadPrimaria.getClave());
					vialidadPrimariaSolicitud.setNombre(vialidadPrimaria.getNombre());
				}

				domicilioSolicitud.setVialidadPrimaria(vialidadPrimariaSolicitud);
			}
		}

		CodigoPostal codigoPostalSolicitud = new CodigoPostal();
		codigoPostalSolicitud.setCodigoPostal(asentamiento.getCodigoPostal());

		asentamientoSolicitud.setClave(asentamiento.getClave());
		asentamientoSolicitud.setNombre(asentamiento.getNombre());
		asentamientoSolicitud.setPeriodo(asentamiento.getPeriodo());
		asentamientoSolicitud.setCodigoPostal(codigoPostalSolicitud);

		// Conversion domicilio
		domicilioSolicitud.setCalle(domicilio.getCalle());
		domicilioSolicitud.setAsentamiento(asentamientoSolicitud);
		domicilioSolicitud.setDescripcion(domicilio.getDescripcion());
		domicilioSolicitud.setLatitud(domicilio.getLatitud());
		domicilioSolicitud.setLongitud(domicilio.getLongitud());
		domicilioSolicitud.setNumExterior1(domicilio.getNumExterior1());
		domicilioSolicitud.setNumExteriorAlf(domicilio.getNumExteriorAlf());
		domicilioSolicitud.setNumInterior(domicilio.getNumInterior());
		domicilioSolicitud.setNumInteriorAlf(domicilio.getNumInteriorAlf());
		
		// Conversion de Otras vialidades
		Vialidad vialidadRefPrimaria = domicilio.getVialidadReferenciaPrimaria();
		if (vialidadRefPrimaria != null && vialidadRefPrimaria.getClave() != null && vialidadRefPrimaria.getClave() != 0) {
			mx.gob.imss.ctirss.delta.model.domicilio.Vialidad vialidadRefPrimariaSolicitud =
					new mx.gob.imss.ctirss.delta.model.domicilio.Vialidad();
			mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad tipoVialidadSolicitud =
					new mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad();

			vialidadRefPrimariaSolicitud.setClave(vialidadRefPrimaria.getClave());
			vialidadRefPrimariaSolicitud.setNombre(vialidadRefPrimaria.getNombre());

			TipoVialidad tipoVialidad = vialidadRefPrimaria.getTipoVialidad();
			tipoVialidadSolicitud.setClave(tipoVialidad.getClave());
			tipoVialidadSolicitud.setDescripcion(tipoVialidad.getDescripcion());
			vialidadRefPrimariaSolicitud.setTipoVialidad(tipoVialidadSolicitud);
			domicilioSolicitud.setVialidadReferenciaPrimaria(vialidadRefPrimariaSolicitud);
		}

		Vialidad vialidadRefSecundaria = domicilio.getVialidadReferenciaSecundaria();
		if (vialidadRefSecundaria != null && vialidadRefSecundaria.getClave() != null && vialidadRefSecundaria.getClave() != 0) {
			mx.gob.imss.ctirss.delta.model.domicilio.Vialidad vialidadRefSecundariaSolicitud =
					new mx.gob.imss.ctirss.delta.model.domicilio.Vialidad();
			mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad tipoVialidadSolicitud =
					new mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad();

			vialidadRefSecundariaSolicitud.setClave(vialidadRefSecundaria.getClave());
			vialidadRefSecundariaSolicitud.setNombre(vialidadRefSecundaria.getNombre());

			TipoVialidad tipoVialidad = vialidadRefSecundaria.getTipoVialidad();
			tipoVialidadSolicitud.setClave(tipoVialidad.getClave());
			tipoVialidadSolicitud.setDescripcion(tipoVialidad.getDescripcion());
			vialidadRefSecundariaSolicitud.setTipoVialidad(tipoVialidadSolicitud);
			domicilioSolicitud.setVialidadReferenciaSecundaria(vialidadRefSecundariaSolicitud);
		}

		Vialidad vialidadRefPosterior = domicilio.getVialidadReferenciaPosterior();
		if (vialidadRefPosterior != null && vialidadRefPosterior.getClave() != null && vialidadRefPosterior.getClave() != 0) {
			mx.gob.imss.ctirss.delta.model.domicilio.Vialidad vialidadRefPosteriorSolicitud =
					new mx.gob.imss.ctirss.delta.model.domicilio.Vialidad();
			mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad tipoVialidadSolicitud =
					new mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad();

			vialidadRefPosteriorSolicitud.setClave(vialidadRefPosterior.getClave());
			vialidadRefPosteriorSolicitud.setNombre(vialidadRefPosterior.getNombre());

			TipoVialidad tipoVialidad = vialidadRefPosterior.getTipoVialidad();
			tipoVialidadSolicitud.setClave(tipoVialidad.getClave());
			tipoVialidadSolicitud.setDescripcion(tipoVialidad.getDescripcion());
			vialidadRefPosteriorSolicitud.setTipoVialidad(tipoVialidadSolicitud);
			domicilioSolicitud.setVialidadReferenciaPosterior(vialidadRefPosteriorSolicitud);
		}

		// Conversion camino-carretera
		Carretera carretera = domicilio.getCarretera();
		if (carretera != null) {
			TipoAdministracion administracion = carretera.getAdministracion();
			TipoDerechoTransito derechoTransito = carretera.getDerechoTransito();
			TipoTerminoGeneral terminoGeneral = carretera.getTerminoGeneral();

			if ((administracion != null && administracion.getClave() != null && administracion.getClave() != 0L)
					&& (derechoTransito != null && derechoTransito.getClave() != null && derechoTransito.getClave() != 0L)
					&& (terminoGeneral != null && terminoGeneral.getClave() != null && terminoGeneral.getClave() != 0L)
					&& StringUtils.isNotBlank(carretera.getCadenamiento()) && StringUtils.isNotBlank(carretera.getOrigen())
					&& StringUtils.isNotBlank(carretera.getDestino())
					&& (carretera.getCodigoCarretera() != null && carretera.getCodigoCarretera() != 0)) {
				DomicilioCarretera domicilioCarreteraSol = new DomicilioCarretera();
				mx.gob.imss.ctirss.delta.model.domicilio.TipoAdministracion administracionSolicitud =
						new mx.gob.imss.ctirss.delta.model.domicilio.TipoAdministracion();
				mx.gob.imss.ctirss.delta.model.domicilio.TipoDerechoTransito derechoTransitoSol =
						new mx.gob.imss.ctirss.delta.model.domicilio.TipoDerechoTransito();
				mx.gob.imss.ctirss.delta.model.domicilio.TipoTerminoGeneral terminoGeneralSol =
						new mx.gob.imss.ctirss.delta.model.domicilio.TipoTerminoGeneral();

				administracionSolicitud.setClave(administracion.getClave());
				administracionSolicitud.setDescripcion(administracion.getDescripcion());

				derechoTransitoSol.setClave(derechoTransito.getClave());
				derechoTransitoSol.setDescripcion(derechoTransito.getDescripcion());

				terminoGeneralSol.setClave(terminoGeneral.getClave());
				terminoGeneralSol.setDescripcion(terminoGeneral.getDescripcion());

				domicilioCarreteraSol.setAdministracion(administracionSolicitud);
				domicilioCarreteraSol.setDerechoTransito(derechoTransitoSol);
				domicilioCarreteraSol.setTerminoGeneral(terminoGeneralSol);
				domicilioCarreteraSol.setCadenamiento(carretera.getCadenamiento());
				domicilioCarreteraSol.setCodigoCarretera(carretera.getCodigoCarretera());
				domicilioCarreteraSol.setOrigen(carretera.getOrigen());
				domicilioCarreteraSol.setDestino(carretera.getDestino());
				domicilioSolicitud.setDomicilioCarretera(domicilioCarreteraSol);
				
				localidadSolicitud.setClave(localidad.getClave());
				localidadSolicitud.setNombre(localidad.getNombre());
			}
		}

		Camino camino = domicilio.getCamino();
		if (camino != null) {
			TipoMargen margen = camino.getMargen();
			TipoTerminoGeneral terminoGeneral = camino.getTerminoGeneral();

			if ((margen != null && margen.getClave() != null && margen.getClave() != 0L)
					&& (terminoGeneral != null && terminoGeneral.getClave() != null && terminoGeneral.getClave() != 0L)
					&& StringUtils.isNotBlank(camino.getCadenamiento()) && StringUtils.isNotBlank(camino.getOrigen())
					&& StringUtils.isNotBlank(camino.getDestino())) {
				DomicilioCamino domicilioCaminoSol = new DomicilioCamino();
				mx.gob.imss.ctirss.delta.model.domicilio.TipoTerminoGeneral terminoGeneralSol =
						new mx.gob.imss.ctirss.delta.model.domicilio.TipoTerminoGeneral();
				mx.gob.imss.ctirss.delta.model.domicilio.TipoMargen margenSolicitud =
						new mx.gob.imss.ctirss.delta.model.domicilio.TipoMargen();

				terminoGeneralSol.setClave(terminoGeneral.getClave());
				terminoGeneralSol.setDescripcion(terminoGeneral.getDescripcion());

				margenSolicitud.setClave(margen.getClave());
				margenSolicitud.setDescripcion(margen.getDescripcion());

				domicilioCaminoSol.setMargen(margenSolicitud);
				domicilioCaminoSol.setTerminoGeneral(terminoGeneralSol);
				domicilioCaminoSol.setCadenamiento(camino.getCadenamiento());
				domicilioCaminoSol.setOrigen(camino.getOrigen());
				domicilioCaminoSol.setDestino(camino.getDestino());
				domicilioSolicitud.setDomicilioCamino(domicilioCaminoSol);

				localidadSolicitud.setClave(localidad.getClave());
				localidadSolicitud.setNombre(localidad.getNombre());
			}
		}

		// Se asocia datos de localidad
		asentamientoSolicitud.setLocalidad(localidadSolicitud);
		domicilioSolicitud = agregarDescripcionesDomicilio(domicilioSolicitud);

		String nombreVialidad = construirNombreVialidadPrimaria(domicilioSolicitud);
		domicilioSolicitud.setCalle(nombreVialidad);

		if (domicilioSolicitud.getVialidadPrimaria() != null) {
			domicilioSolicitud.getVialidadPrimaria().setNombre(nombreVialidad);
		} else {
			domicilioSolicitud.setVialidadPrimaria(new mx.gob.imss.ctirss.delta.model.domicilio.Vialidad());
			domicilioSolicitud.getVialidadPrimaria().setNombre(nombreVialidad);
		}
	}

	private mx.gob.imss.ctirss.delta.model.domicilio.Domicilio agregarDescripcionesDomicilio(
			mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioSolicitud) {
		try {
			mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento asentamientoSolicitud = domicilioSolicitud.getAsentamiento();
			mx.gob.imss.ctirss.delta.model.domicilio.Localidad localidadSolicitud = asentamientoSolicitud.getLocalidad();
			mx.gob.imss.ctirss.delta.model.domicilio.Municipio municipioSolicitud = localidadSolicitud.getMunicipio();
			mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa entidadSolicitud = municipioSolicitud.getEntidadFederativa();

			Long periodo = asentamientoSolicitud.getPeriodo();
			if (periodo == 0L) {
				periodo = 4L;
			}

			// Se agrega descripciones en Asentamiento
			if (StringUtils.isBlank(asentamientoSolicitud.getNombre())) {
				mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento asentamientoEncontrado =
						domicilioServiceBusiness.getAsentamiento(asentamientoSolicitud);
				asentamientoSolicitud.setNombre(asentamientoEncontrado.getNombre());
			}

			mx.gob.imss.ctirss.delta.model.domicilio.Localidad localidadEncontrada =
					domicilioServiceBusiness.getLocalidad(localidadSolicitud);
			if (StringUtils.isBlank(localidadSolicitud.getNombre())) {
				localidadSolicitud.setNombre(localidadEncontrada.getNombre());
			}

			if (StringUtils.isBlank(municipioSolicitud.getNombre())) {
				mx.gob.imss.ctirss.delta.model.domicilio.Municipio municipioEncontrado =
						localidadEncontrada.getMunicipio();
				municipioSolicitud.setNombre(municipioEncontrado.getNombre());
			}

			if (StringUtils.isBlank(entidadSolicitud.getNombre())) {
				mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa entidadEncomtrada =
						localidadEncontrada.getMunicipio().getEntidadFederativa();
				entidadSolicitud.setNombre(entidadEncomtrada.getNombre());
			}

			// Se colocan los propiedades con la descripcion
			municipioSolicitud.setEntidadFederativa(entidadSolicitud);
			localidadSolicitud.setMunicipio(municipioSolicitud);
			asentamientoSolicitud.setLocalidad(localidadSolicitud);
			domicilioSolicitud.setAsentamiento(asentamientoSolicitud);

			// Se agrega descripciones en Camino
			DomicilioCamino camino = domicilioSolicitud.getDomicilioCamino();
			if (camino != null) {
				mx.gob.imss.ctirss.delta.model.domicilio.TipoTerminoGeneral terminoGeneral =
						camino.getTerminoGeneral();
				mx.gob.imss.ctirss.delta.model.domicilio.TipoMargen margen =
						camino.getMargen();

				if (MargenEnum.DERECHO.getClave() == margen.getClave().intValue()) {
					margen.setDescripcion(MargenEnum.DERECHO.getDescripcion());
				} else if (MargenEnum.IZQUIERDO.getClave() == margen.getClave().intValue()) {
					margen.setDescripcion(MargenEnum.IZQUIERDO.getDescripcion());
				}
				camino.setMargen(margen);

				if (TerminoGeneralEnum.BRECHA.getClave() == terminoGeneral.getClave().intValue()) {
					terminoGeneral.setDescripcion(TerminoGeneralEnum.BRECHA.getDescripcion());
				} else if (TerminoGeneralEnum.CAMINO.getClave() == terminoGeneral.getClave().intValue()) {
					terminoGeneral.setDescripcion(TerminoGeneralEnum.CAMINO.getDescripcion());
				} else if (TerminoGeneralEnum.CARRETERA.getClave() == terminoGeneral.getClave().intValue()) {
					terminoGeneral.setDescripcion(TerminoGeneralEnum.CARRETERA.getDescripcion());
				} else if (TerminoGeneralEnum.TERRACERIA.getClave() == terminoGeneral.getClave().intValue()) {
					terminoGeneral.setDescripcion(TerminoGeneralEnum.TERRACERIA.getDescripcion());
				} else if (TerminoGeneralEnum.VEREDA.getClave() == terminoGeneral.getClave().intValue()) {
					terminoGeneral.setDescripcion(TerminoGeneralEnum.VEREDA.getDescripcion());
				}
				camino.setTerminoGeneral(terminoGeneral);

				domicilioSolicitud.setDomicilioCamino(camino);
			}

			// Se agrega descripciones en Carretera
			DomicilioCarretera carretera = domicilioSolicitud.getDomicilioCarretera();
			if (carretera != null) {
				mx.gob.imss.ctirss.delta.model.domicilio.TipoAdministracion administracion =
						carretera.getAdministracion();
				mx.gob.imss.ctirss.delta.model.domicilio.TipoDerechoTransito derechoTransito =
						carretera.getDerechoTransito();
				mx.gob.imss.ctirss.delta.model.domicilio.TipoTerminoGeneral terminoGeneral =
						carretera.getTerminoGeneral();

				if (TerminoGeneralEnum.BRECHA.getClave() == terminoGeneral.getClave().intValue()) {
					terminoGeneral.setDescripcion(TerminoGeneralEnum.BRECHA.getDescripcion());
				} else if (TerminoGeneralEnum.CAMINO.getClave() == terminoGeneral.getClave().intValue()) {
					terminoGeneral.setDescripcion(TerminoGeneralEnum.CAMINO.getDescripcion());
				} else if (TerminoGeneralEnum.CARRETERA.getClave() == terminoGeneral.getClave().intValue()) {
					terminoGeneral.setDescripcion(TerminoGeneralEnum.CARRETERA.getDescripcion());
				} else if (TerminoGeneralEnum.TERRACERIA.getClave() == terminoGeneral.getClave().intValue()) {
					terminoGeneral.setDescripcion(TerminoGeneralEnum.TERRACERIA.getDescripcion());
				} else if (TerminoGeneralEnum.VEREDA.getClave() == terminoGeneral.getClave().intValue()) {
					terminoGeneral.setDescripcion(TerminoGeneralEnum.VEREDA.getDescripcion());
				}
				carretera.setTerminoGeneral(terminoGeneral);

				if (CarreteraAdministracionEnum.ESTATAL.getClave() == administracion.getClave().intValue()) {
					administracion.setDescripcion(CarreteraAdministracionEnum.ESTATAL.getDescripcion());
				} else if (CarreteraAdministracionEnum.FEDERAL.getClave() == administracion.getClave().intValue()) {
					administracion.setDescripcion(CarreteraAdministracionEnum.FEDERAL.getDescripcion());
				} else if (CarreteraAdministracionEnum.MUNICIPAL.getClave() == administracion.getClave().intValue()) {
					administracion.setDescripcion(CarreteraAdministracionEnum.MUNICIPAL.getDescripcion());
				} else if (CarreteraAdministracionEnum.PARTICULAR.getClave() == administracion.getClave().intValue()) {
					administracion.setDescripcion(CarreteraAdministracionEnum.PARTICULAR.getDescripcion());
				}
				carretera.setAdministracion(administracion);

				if (DerechoTransitoEnum.CUOTA.getClave() == derechoTransito.getClave().intValue()) {
					derechoTransito.setDescripcion(DerechoTransitoEnum.CUOTA.getDescripcion());
				} else if (DerechoTransitoEnum.LIBRE.getClave() == derechoTransito.getClave().intValue()) {
					derechoTransito.setDescripcion(DerechoTransitoEnum.LIBRE.getDescripcion());
				}
				carretera.setDerechoTransito(derechoTransito);

				domicilioSolicitud.setDomicilioCarretera(carretera);
			}

			// Se agrega descripciones en vialidades
			mx.gob.imss.ctirss.delta.model.domicilio.Vialidad vialidadReferenciaPrimaria =
					domicilioSolicitud.getVialidadReferenciaPrimaria();
			if (vialidadReferenciaPrimaria != null && StringUtils.isBlank(vialidadReferenciaPrimaria.getNombre())) {
				vialidadReferenciaPrimaria = domicilioServiceBusiness.getVialidad(vialidadReferenciaPrimaria);
				domicilioSolicitud.setVialidadReferenciaPrimaria(vialidadReferenciaPrimaria);
			}

			mx.gob.imss.ctirss.delta.model.domicilio.Vialidad vialidadReferenciaSecundaria =
					domicilioSolicitud.getVialidadReferenciaSecundaria();
			if (vialidadReferenciaSecundaria != null && StringUtils.isBlank(vialidadReferenciaSecundaria.getNombre())) {
				vialidadReferenciaSecundaria = domicilioServiceBusiness.getVialidad(vialidadReferenciaSecundaria);
				domicilioSolicitud.setVialidadReferenciaSecundaria(vialidadReferenciaSecundaria);
			}

			mx.gob.imss.ctirss.delta.model.domicilio.Vialidad vialidadReferenciaPosterior =
					domicilioSolicitud.getVialidadReferenciaPosterior();
			if (vialidadReferenciaPosterior != null && StringUtils.isBlank(vialidadReferenciaPosterior.getNombre())) {
				vialidadReferenciaPosterior = domicilioServiceBusiness.getVialidad(vialidadReferenciaPosterior);
				domicilioSolicitud.setVialidadReferenciaPosterior(vialidadReferenciaPosterior);
			}
		} catch (AsentamientoNoLocalizadoException e) {
			log.error(e);
		} catch (DomicilioNoLocalizadoException e) {
			log.error(e);
		} catch (LocalidadNoLocalizadoException e) {
			log.error(e);
		} catch (VialidadesNoLocalizadasException e) {
			log.error(e);
		} finally {
			return domicilioSolicitud;
		}
	}

	private void validarDatosEntrada(String curp, String correo,
			UnidadMedicaFamiliarTO umfTO, Domicilio domicilio, boolean validaDomicilio) throws AsignacionNssPersonaException {
		List<String> lstErrores = new ArrayList<String>();
		StringBuffer sbErrores = new StringBuffer();

		// Validacion del formato de la CURP
		if (StringUtils.isBlank(curp) || curp.length() != LONGITUD_CURP) {
			lstErrores.add("La CURP tiene que ser de " + LONGITUD_CURP + " caracteres");
		} else if (!curp.matches(REGEX_CURP_FISICA)) {
			lstErrores.add("La CURP no cumple con el formato requerido");
		}

		// Validacion del formato de correo
		if (StringUtils.isBlank(correo)) {
			lstErrores.add("El correo no debe ser nulo o vac\u00EDo");
		} else if (!correo.matches(EMAIL_PATTERN)) {
			lstErrores.add("El correo electronico no cumple con el formato requerido");
		}

		// Validacion de informacion de UMF
		if (umfTO == null || umfTO.getIdUMF() == null) {
			lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Unidad de Medicina Familiar");
		} else {
			Subdelegacion subdelegacion = umfTO.getSubdelegacion();
			if (subdelegacion == null || subdelegacion.getId() == null || subdelegacion.getId() == 0L) {
				lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Subdelegaci\u00F3n a la que pertenece la Unidad de Medicina Familiar");
			} else {
				Delegacion delegacion = subdelegacion.getDelegacion();

				if (StringUtils.isBlank(subdelegacion.getClave())) {
					lstErrores.add("Es necesario ingresar la clave de la Subdelegaci\u00F3n a la que pertenece la Unidad de Medicina Familiar");
				}
				if (delegacion == null || delegacion.getId() == null || delegacion.getId() == 0L) {
					lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Delegaci\u00F3n a la que pertenece la Unidad de Medicina Familiar");
				} else {
					if (StringUtils.isBlank(delegacion.getClave())) {
						lstErrores.add("Es necesario ingresar la clave de la Delegaci\u00F3n que a la que pertenece la Unidad de Medicina Familiar");
					}
					if (delegacion.getCiz() == null || delegacion.getCiz() == 0) {
						lstErrores.add("Es necesario ingresar el ciz de la Delegaci\u00F3n que a la que pertenece la Unidad de Medicina Familiar");
					}
				}
			}
		}

		
		
		if(validaDomicilio){
			// Validacion de informacion de Domicilio
			if (domicilio == null) {
				lstErrores.add("Es necesario ingresar la informaci\u00F3n del domicilio particular del asegurado");
			} else {
				// Validacion de Asentamiento

				Asentamiento asentamiento = domicilio.getAsentamiento();
				if (asentamiento == null || StringUtils.isBlank(asentamiento.getClave())) {
					lstErrores.add("Es necesario ingresar la informaci\u00F3n del Asentamiento");
				}
				//si no viene la localidad dentro del asentamiento se supone que es domicilio completo
				if(asentamiento.getLocalidad() == null){
					

					// Validacion codigo postal
					if (StringUtils.isBlank(domicilio.getCodigoPostal())
							&& (domicilio.getAsentamiento() != null && StringUtils.isBlank(domicilio.getAsentamiento().getCodigoPostal()))) {
						lstErrores.add("Es necesario ingresar la informaci\u00F3n del C\u00F3digo Postal");
					}

					// Validacion de numero
					if ((domicilio.getNumExterior1() == null || domicilio.getNumExterior1() == 0)
							&& StringUtils.isBlank(domicilio.getNumExteriorAlf())) {
						lstErrores.add("Es necesario ingresar al menos el n\u00FAmero Exterior (Num\u00E9rico o Alfanum\u00E9rico)");
					}

					// Validacion Vialidad Primaria
					try {
						if (isCaminoVacio(domicilio.getCamino(), asentamiento)
								&& isCarreteraVacia(domicilio.getCarretera(), asentamiento)
								&& isCalleVacia(domicilio.getCalle(), domicilio.getVialidadPrimaria(), asentamiento)) {
							lstErrores.add("Es necesario ingresar la Vialidad Primaria o Carretera o Camino");
						}
					} catch (AsignacionNssPersonaException e) {
						lstErrores.add(e.getMessage());
					}

					// Validacion otras vialidades
					Vialidad vialidadRefPrimaria = domicilio.getVialidadReferenciaPrimaria();
					if (vialidadRefPrimaria != null && vialidadRefPrimaria.getClave() != null && vialidadRefPrimaria.getClave() != 0) {
						TipoVialidad tipoVialidad = vialidadRefPrimaria.getTipoVialidad();
						if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
							lstErrores.add("Es necesario ingresar la informaci\u00F3n del Tipo de Vialidad (Vialidad Referencia Primaria)");
						}
					}

					Vialidad vialidadRefSecundaria = domicilio.getVialidadReferenciaSecundaria();
					if (vialidadRefSecundaria != null && vialidadRefSecundaria.getClave() != null && vialidadRefSecundaria.getClave() != 0) {
						TipoVialidad tipoVialidad = vialidadRefSecundaria.getTipoVialidad();
						if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
							lstErrores.add("Es necesario ingresar la informaci\u00F3n del Tipo de Vialidad (Vialidad Referencia Secundaria)");
						}
					}

					Vialidad vialidadRefPosterior = domicilio.getVialidadReferenciaPosterior();
					if (vialidadRefPosterior != null && vialidadRefPosterior.getClave() != null && vialidadRefPosterior.getClave() != 0) {
						TipoVialidad tipoVialidad = vialidadRefPosterior.getTipoVialidad();
						if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
							lstErrores.add("Es necesario ingresar la informaci\u00F3n del Tipo de Vialidad (Vialidad Referencia Posterior)");
						}
					}
				}
			}
		}	
			
			// Se enumeran los errores detectados durante la validacion
			int numErrores = lstErrores.size();
			if (numErrores > 0) {
				for (int index = 0; index < numErrores; index++) {
					if (index == numErrores - 1) {
						sbErrores.append(lstErrores.get(index));
					} else {
						sbErrores.append(lstErrores.get(index)).append(", \n");
					}
				}
		}

		String strErrores = sbErrores.toString();
		if(StringUtils.isNotBlank(strErrores)){
			throw new AsignacionNssPersonaException("Se detectaron los siguientes errores al validar los datos ingresados:\n" + strErrores);
		}
	}

	private boolean isCarreteraVacia(Carretera carretera, Asentamiento asentamiento) throws AsignacionNssPersonaException {
		List<String> lstErrores = new ArrayList<String>();
		StringBuffer sbErrores = new StringBuffer();

		boolean carreteraVacia = false;

		if (carretera == null) {
			carreteraVacia = true;
		} else {
			TipoAdministracion administracion = carretera.getAdministracion();
			TipoDerechoTransito derechoTransito = carretera.getDerechoTransito();
			TipoTerminoGeneral terminoGeneral = carretera.getTerminoGeneral();

			if ((administracion == null || administracion.getClave() == null || administracion.getClave() == 0L)
					&& (derechoTransito == null || derechoTransito.getClave() == null || derechoTransito.getClave() == 0L)
					&& (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L)
					&& StringUtils.isBlank(carretera.getCadenamiento()) && StringUtils.isBlank(carretera.getOrigen())
					&& StringUtils.isBlank(carretera.getDestino())
					&& (carretera.getCodigoCarretera() == null || carretera.getCodigoCarretera() == 0)) {
				carreteraVacia = true;
			} else {
				if (administracion == null || administracion.getClave() == null || administracion.getClave() == 0L) {
					lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Administraci\u00F3n de la Carretera");
				}
				if (derechoTransito == null || derechoTransito.getClave() == null || derechoTransito.getClave() == 0L) {
					lstErrores.add("Es necesario ingresar la informaci\u00F3n del Derecho de Transito de la Carretera");
				}
				if (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L) {
					lstErrores.add("Es necesario ingresar la informaci\u00F3n del Termino General de la Carretera");
				}
				if (StringUtils.isBlank(carretera.getCadenamiento())) {
					lstErrores.add("Es necesario ingresar el Cadenamiento de la Carretera");
				}
				if (StringUtils.isBlank(carretera.getOrigen())) {
					lstErrores.add("Es necesario ingresar el Origen de la Carretera");
				}
				if (StringUtils.isBlank(carretera.getDestino())) {
					lstErrores.add("Es necesario ingresar el Destino de la Carretera");
				}
				if (carretera.getCodigoCarretera() == null || carretera.getCodigoCarretera() == 0) {
					lstErrores.add("Es necesario ingresar el C\u00F3digo de la Carretera");
				}

				// Validacion de localidad, municipio, entidad federativa
				Localidad localidad = asentamiento.getLocalidad();
				if (localidad == null || StringUtils.isBlank(localidad.getClave())) {
					lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Localidad");
				} else {
					Municipio municipio = localidad.getMunicipio();
					if (municipio == null || StringUtils.isBlank(municipio.getClave())) {
						lstErrores.add("Es necesario ingresar la informaci\u00F3n del Municipio");
					} else {
						EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
						if (entidadFederativa == null || StringUtils.isBlank(entidadFederativa.getClave())) {
							lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Entidad Federativa");
						}
					}
				}
			}
		}

		int numErrores = lstErrores.size();
		if (numErrores > 0) {
			for (int index = 0; index < numErrores; index++) {
				if (index == numErrores - 1) {
					sbErrores.append(lstErrores.get(index));
				} else {
					sbErrores.append(lstErrores.get(index)).append(", \n");
				}
			}
		}

		String strErrores = sbErrores.toString();
		if(StringUtils.isNotBlank(strErrores)){
			throw new AsignacionNssPersonaException(strErrores);
		}

		return carreteraVacia;
	}

	private boolean isCalleVacia(String calle, Vialidad vialidadPrimaria, Asentamiento asentamiento) throws AsignacionNssPersonaException {
		List<String> lstErrores = new ArrayList<String>();
		StringBuffer sbErrores = new StringBuffer();

		boolean calleVacia = false;

		if (StringUtils.isBlank(calle)
				&& (vialidadPrimaria == null
						|| (vialidadPrimaria.getClave() == null && StringUtils.isBlank(vialidadPrimaria.getNombre()))
						|| (vialidadPrimaria.getClave() != null && vialidadPrimaria.getClave() == 0 && StringUtils.isBlank(vialidadPrimaria.getNombre())))) {
			calleVacia = true;
		} else if (vialidadPrimaria == null
				|| (vialidadPrimaria.getClave() == null && StringUtils.isNotBlank(vialidadPrimaria.getNombre()))
				|| (vialidadPrimaria.getClave() != null && vialidadPrimaria.getClave() == 0 && StringUtils.isNotBlank(vialidadPrimaria.getNombre()))) {
			// Validacion de localidad, municipio, entidad federativa
			Localidad localidad = asentamiento.getLocalidad();
			if (localidad == null) {
				lstErrores.add("Es necesario ingresar el Municipio como parametro de la Localidad");
			} else {
				Municipio municipio = localidad.getMunicipio();
				if (municipio == null || StringUtils.isBlank(municipio.getClave())) {
					lstErrores.add("Es necesario ingresar la informaci\u00F3n del Municipio");
				} else {
					EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
					if (entidadFederativa == null || StringUtils.isBlank(entidadFederativa.getClave())) {
						lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Entidad Federativa");
					}
				}
			}
		}

		if (vialidadPrimaria != null) {
			TipoVialidad tipoVialidad = vialidadPrimaria.getTipoVialidad();
			if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
				lstErrores.add("Es necesario ingresar el Tipo de Vialidad (Vialidad Primaria)");
			}
		}
		
		int numErrores = lstErrores.size();
		if (numErrores > 0) {
			for (int index = 0; index < numErrores; index++) {
				if (index == numErrores - 1) {
					sbErrores.append(lstErrores.get(index));
				} else {
					sbErrores.append(lstErrores.get(index)).append(", \n");
				}
			}
		}

		String strErrores = sbErrores.toString();
		if(StringUtils.isNotBlank(strErrores)){
			throw new AsignacionNssPersonaException(strErrores);
		}

		return calleVacia;
	}

	private boolean isCaminoVacio(Camino camino, Asentamiento asentamiento) throws AsignacionNssPersonaException {
		List<String> lstErrores = new ArrayList<String>();
		StringBuffer sbErrores = new StringBuffer();

		boolean caminoVacio = false;

		if (camino == null) {
			caminoVacio = true;
		} else {
			TipoMargen margen = camino.getMargen();
			TipoTerminoGeneral terminoGeneral = camino.getTerminoGeneral();

			if ((margen == null || margen.getClave() == null || margen.getClave() == 0L)
					&& (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L)
					&& StringUtils.isBlank(camino.getCadenamiento()) && StringUtils.isBlank(camino.getOrigen())
					&& StringUtils.isBlank(camino.getDestino())) {
				caminoVacio = true;
			} else {
				if (margen == null || margen.getClave() == null || margen.getClave() == 0L) {
					lstErrores.add("Es necesario ingresar la informaci\u00F3n del Margen del Camino");
				}
				if (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L) {
					lstErrores.add("Es necesario ingresar la informaci\u00F3n del Termino General del Camino");
				}
				if (StringUtils.isBlank(camino.getCadenamiento())) {
					lstErrores.add("Es necesario ingresar el Cadenamiento del Camino");
				}
				if (StringUtils.isBlank(camino.getOrigen())) {
					lstErrores.add("Es necesario ingresar el Origen del Camino");
				}
				if (StringUtils.isBlank(camino.getDestino())) {
					lstErrores.add("Es necesario ingresar el Destino del Camino");
				}

				// Validacion de localidad, municipio, entidad federativa
				Localidad localidad = asentamiento.getLocalidad();
				if (localidad == null || StringUtils.isBlank(localidad.getClave())) {
					lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Localidad");
				} else {
					Municipio municipio = localidad.getMunicipio();
					if (municipio == null || StringUtils.isBlank(municipio.getClave())) {
						lstErrores.add("Es necesario ingresar la informaci\u00F3n del Municipio");
					} else {
						EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
						if (entidadFederativa == null || StringUtils.isBlank(entidadFederativa.getClave())) {
							lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Entidad Federativa");
						}
					}
				}
			}
		}

		int numErrores = lstErrores.size();
		if (numErrores > 0) {
			for (int index = 0; index < numErrores; index++) {
				if (index == numErrores - 1) {
					sbErrores.append(lstErrores.get(index));
				} else {
					sbErrores.append(lstErrores.get(index)).append(", \n");
				}
			}
		}

		String strErrores = sbErrores.toString();
		if(StringUtils.isNotBlank(strErrores)){
			throw new AsignacionNssPersonaException(strErrores);
		}

		return caminoVacio;
	}

	private void validarSolicitudesAsociadasAsegurado(Fisica fisicaEncontrada)
			throws AsignacionNssPersonaException {
		// Se valida si la persona a asegurar tiene una solicitud asociada
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		Solicitud solicitudActiva = null;

		try {
			solicitudActiva = serviceBusiness.obtenerSolicitudRegistrada(fisicaEncontrada.getIdPersona());

			if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
			} else {
				solicitudActiva = serviceBusiness.obtenerSolicitudEnProceso(fisicaEncontrada.getIdPersona());

				if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;
				} else {
					solicitudActiva = new Solicitud();
				}
			}

			if (existeSolRegistrada) {
				throw new AsignacionNssPersonaException(
						"Usted ya cuenta con una solicitud para Generaci\u00F3n de N\u00FAmero de Seguridad Social registrada, cuyo folio es: "
								+ solicitudActiva.getNoFolioSolicitud());
			} else if (existeSolProceso) {
				throw new AsignacionNssPersonaException(
						"Usted ya cuenta con una solicitud para Generaci\u00F3n de N\u00FAmero de Seguridad Social en proceso, cuyo folio es: "
								+ solicitudActiva.getNoFolioSolicitud());
			}
		} catch (SolicitudException e) {
			this.log.error(e);
		}
	}

	private void pasarDocumentosProbatorios(Fisica fisica) {
		if (fisica.getDocumentosProbatorios() != null && !fisica.getDocumentosProbatorios().isEmpty()) {
			for (DocumentoProbatorio doc : fisica.getDocumentosProbatorios()) {
				Long idTipoDocumento = doc.getDocumentoPorTipo().getIdDocumentoPorTipo();

				this.log.debug("Tipo documento probatorio -> " + idTipoDocumento);

				if (idTipoDocumento.equals(DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId())) {
					fisica.setActaNacimientoAux((Nacimiento) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId())) {
					fisica.setCartaNaturalizacionAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId())) {
					fisica.setDocumentoMigratorioAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId())) {
					fisica.setNumeroUnicoExtranjeroAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId())) {
					fisica.setCertificadoNacionalidadMexicanaAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId())) {
					fisica.setOficioSolicitanteRefugiadoAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA	.getId())) {
					fisica.setFormaMigratoriaTuristaAux((CURP) doc);
				}
			}
		}
	}

	private String construirNombreVialidadPrimaria(mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio) {
		StringBuffer bufferVial = new StringBuffer();
		
		DomicilioCamino camino = domicilio.getDomicilioCamino();
		DomicilioCarretera carretera = domicilio.getDomicilioCarretera();
		String calle = domicilio.getCalle();

		if (domicilio.getVialidadPrimaria() == null) {
			domicilio.setVialidadPrimaria(new mx.gob.imss.ctirss.delta.model.domicilio.Vialidad());
		}

		if(domicilio.getVialidadPrimaria().getClave() == null){
			if(StringUtils.isBlank(calle)) {
				if(camino != null && camino.getTerminoGeneral() != null && camino.getTerminoGeneral().getClave() != null && camino.getTerminoGeneral().getClave()!=-1) {
					camino.setOrigen(camino.getOrigen().toUpperCase());
					camino.setDestino(camino.getDestino().toUpperCase());
					camino.setCadenamiento(camino.getCadenamiento().toUpperCase());
					
					bufferVial = new StringBuffer();
					
					bufferVial.append(camino.getTerminoGeneral().getDescripcion() + " ");
					bufferVial.append(camino.getOrigen() +"-" +camino.getDestino() +" ");
					bufferVial.append(camino.getMargen().getDescripcion() +" ");
					bufferVial.append(camino.getCadenamiento());	
					
					return bufferVial.toString();
				}
				
				if(carretera!= null && carretera.getTerminoGeneral() != null && carretera.getTerminoGeneral().getClave() != null && carretera.getTerminoGeneral().getClave() != -1) {
					bufferVial = new StringBuffer();
					
					carretera.setOrigen(carretera.getOrigen().toUpperCase());
					carretera.setDestino(carretera.getDestino().toUpperCase());
					carretera.setCadenamiento(carretera.getCadenamiento().toUpperCase());
					
					bufferVial.append(carretera.getTerminoGeneral().getDescripcion() + " ");
					bufferVial.append(carretera.getAdministracion().getDescripcion() + " ");
					bufferVial.append(carretera.getDerechoTransito().getDescripcion() + " ");
					bufferVial.append(carretera.getCodigoCarretera() +" ");
					bufferVial.append(carretera.getOrigen() +"-" +carretera.getDestino() +" ");
					bufferVial.append(carretera.getCadenamiento());
					
					return bufferVial.toString();
				}
			} else {
				return calle;
			}
		} else {
			if(StringUtils.isBlank(domicilio.getCalle())){
				return domicilio.getVialidadPrimaria().getNombre();
			} else {
				domicilio.setCalle(domicilio.getCalle().toUpperCase());
				return domicilio.getCalle();
			}
			
		}
		
		return "";
	}
	
	@Override
	 public boolean validaAseguradoConAntecedentePasoCambioAl(String strNSS) throws WsAntecedentesAseguradoExcpetion{
		 boolean tieneAtecedentes = false;
		 WSNssPasoaCambio_Service service = new WSNssPasoaCambio_Service();
		 WSNssPasoaCambio  ws = service.getWSNssPasoaCambioPort();
		 ResponseNssPasoacambioResource respuesta =  ws.getInfoByNss(strNSS);
		 if(respuesta != null && respuesta.getCode().equals(CODIGO_EXITO)){
			if(respuesta.getListInfoNssPasoacambio() != null &&
					respuesta.getListInfoNssPasoacambio().getListInfo() !=null &&
					!respuesta.getListInfoNssPasoacambio().getListInfo().isEmpty()){
				log.debug("entre con antecedentes " + respuesta.getListInfoNssPasoacambio().getListInfo().size());
				tieneAtecedentes = true;
			}
				
				
			return tieneAtecedentes;
					
		 }else{
			 if(respuesta != null)
				 throw new  WsAntecedentesAseguradoExcpetion(respuesta.getMessage());
			 else
				 throw new  WsAntecedentesAseguradoExcpetion();
			 
		 }
		 
		 
	 }

	/**
	 * Metodo para validar si es posible realizar el cambio de CURPs
	 */
	@Override
	public ValidaRequisitosCambioCurpResponse validarCambioCurp(String curp, String nss, String correo) {
		
		TramiteCambioCurpDTO tramiteDto = null;
		TramiteActualizacionAsegurado tramite = null;
		Long cveIdAsignacon = null;
		Fisica fisica = new Fisica();
		fisica.setCurp(curp);
		fisica.setNss(nss);
		fisica.setCorreoElectronico(new CorreoElectronico(correo));
		
		Fisica fisicaAnterior = null;
		Long idPersonaAnterior = null;
		Fisica fisicaNueva = null;
		ValidaRequisitosCambioCurpResponse respuesta = new ValidaRequisitosCambioCurpResponse();
		
		SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
		nssCorreo.setCorreo(fisica.getCorreoElectronico());
		nssCorreo.setCurp(fisica.getCurp());
		nssCorreo.setCveIdTipoSolicitud( TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue() );
		int codigoRespuesta = 0;
		try {
			
			codigoRespuesta = solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, false);
			
			if (codigoRespuesta < 1){
				nssCorreo.setCveIdTipoSolicitud( TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue() );
				codigoRespuesta = solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, false);
			}

		} catch (SolicitudNssCorreoException e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("013", e.getMessage());
		}
		
		CiudadanoCurpCorreo ciudadanoCurp = null;
		try {
			ciudadanoCurp = portalCiudadanoServiceBusinessRemote.validaRegistroCurpCorreoCiudadano(fisica.getCurp(), fisica.getCorreoElectronico().getCorreo());
		} catch (PortalCiudadanoException e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("014", e.getMessage());
		}
		
		try {
			//validamos que el tramite pueda realizarse
			tramite = serviceBusiness.validacionesNSSActualizaCURPporNSS(fisica);
			//Obtenemos a la persona que encontramos
			fisicaAnterior = tramite.getFisicaAnterior();
			//obtenemos el id de la persona anterior
			idPersonaAnterior = fisicaAnterior.getIdPersona();
			//obetenemos a la persona nueva
			fisicaNueva = tramite.getFisicaNueva();
			//obtenemos la cve_id_asignacion con el nss encontrado
			cveIdAsignacon = serviceBusiness.obtenerCveAsignacionNss(fisicaAnterior.getNss());
			
			//validamos los roles de la persona
			CabezaGrupoFamiliar cabeza = null;
			boolean isPensionado = false;
			boolean isBeneficiario = false;
			
			try {
				//buscamos la cabeza para saber si la persona es pensionada
				cabeza = grupoFamiliarServiceRemote.getCabezaWS(cveIdAsignacon);
				isPensionado = cabeza.getCalidadParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId();
				isBeneficiario = false;
				//si no es pensionado buscamos si es beneficiario del grupo familiar
				if(!isPensionado) {
					//buscaremos a la persona para validaer que no sea un beneficiario
					List <Long> parentesco = new ArrayList<Long>();
					parentesco.add(ParentescoEnum.PADRES.getId());
					parentesco.add(ParentescoEnum.HIJOS.getId());
					parentesco.add(ParentescoEnum.CONYUGE.getId());
					parentesco.add(ParentescoEnum.CONCUBINARIO.getId());
					//realizamos la busqueda
					isBeneficiario = grupoFamiliarServiceRemote.validaPersonaExisteEnGruposFamiliaresPorParentesco(idPersonaAnterior, parentesco);
				}
				
				//validamos que la persona no cuente con algun rol que no permita el cambio
				if (isBeneficiario || isPensionado || grupoFamiliarServiceRemote.esPatron(idPersonaAnterior) ||
						grupoFamiliarServiceRemote.esRepresentanteLegal(idPersonaAnterior) ||
						personaFisicaServiceBusiness.isSocio(idPersonaAnterior) ||
						personaFisicaServiceBusiness.isPersonaAutorizada(idPersonaAnterior)){
					
					log.error("La persona con curp " + curp + " y con NSS " + nss + " no puede actualizar la curp ya que cuenta con algun rol");
					//No es posible realizar el tramites si la persona es pensionado, beneficiario, patron, socio, representante o persona Autorizada
					return new ValidaRequisitosCambioCurpResponse("005", ERROR_ROL_EXISTENTE);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
				return new ValidaRequisitosCambioCurpResponse("011", ERROR_PERFILES);
			}
			
			tramite.getFisicaNueva().setNss(nss);
			
			tramiteDto = new TramiteCambioCurpDTO();
			tramiteDto.setIdPersona(idPersonaAnterior);
			tramiteDto.setIdAsignacionNSS(cveIdAsignacon);
			tramiteDto.setNss(nss);
			tramiteDto.setCurpAnterior(fisicaAnterior.getCurp());
			tramiteDto.setCurpNueva(tramite.getFisicaNueva().getCurp());
			tramiteDto.setFechaNacimientoAnterior(fisicaNueva.getFechaNacimiento());
			tramiteDto.setFechaNacimientoNueva(fisicaNueva.getFechaNacimiento());
			tramiteDto.setActualizaAsignacionCorreo(codigoRespuesta < 1);
			tramiteDto.setActualizaPortalCorreo(ciudadanoCurp != null);
			tramiteDto.setCorreo(correo);
			tramiteDto.setNombre(fisicaAnterior.getNombreCompleto());
		
			respuesta.setTramite(tramiteDto);
			
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("001", CURP_NO_LOCALIZADA);
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("002", e.getMessage());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("003", e.getMessage());
		} catch (ErrorComparacionDatosRENAPOException e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("004", MENSAJE_ACTUALIZACION_GENERICO);
		} catch (PersonaConNSSException e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("006", e.getMessage());
		} catch (AsignacionNSSNoLocalizadoException e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("007", ERROR_NSS_NO_LOCALIZADO);
		} catch (AsignacionNssPersonaException e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("008", e.getMessage());
		} catch (GenerarNSSException e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("009", e.getMessage());
		} catch (WsAntecedentesAseguradoExcpetion e) {
			e.printStackTrace();
			return new ValidaRequisitosCambioCurpResponse("010", e.getMessage());
		}
		
		return respuesta;
	}



	@Override
	public CambioCurpResponse finalizaActualizacionCurp(TramiteCambioCurpDTO tramite) {
		
		CambioCurpResponse response = null;
		
		if(tramite.getIdPersona() == null || tramite.getIdAsignacionNSS() == null || StringUtils.isBlank(tramite.getNss()) ||
				StringUtils.isBlank(tramite.getCurpNueva()) || StringUtils.isBlank(tramite.getCurpAnterior())) {
			return new CambioCurpResponse("012",INFORMACION_INCOMPLETA);
		}
		
		//obtenemos los datos de renapo con la curp anterior que es como se obtiene en el metodo completo de validaciones a la persona nueva
		Fisica fisicaNueva = null;
		Fisica fisicaAnterior = null;
		TramiteActualizacionAsegurado tramiteAsegurado = null;
		
		try {
			fisicaNueva = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(tramite.getCurpAnterior());
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		}
		
		fisicaAnterior = personaBusiness.getPersonaFisica(tramite.getIdPersona());
		
		tramiteAsegurado = new TramiteActualizacionAsegurado();
		tramiteAsegurado.setIdAsignacionNSS(tramite.getIdAsignacionNSS());
		tramiteAsegurado.setIdPersona(tramite.getIdPersona());
		tramiteAsegurado.setFisicaNueva(fisicaNueva);
		tramiteAsegurado.setFisicaAnterior(fisicaAnterior);
		
		try {
			
			Solicitud solicitud = serviceBusiness.crearFinalizarTramiteActualizacionDatos(tramiteAsegurado, OrigenSolicitudEnum.MOVILES);
			response = new CambioCurpResponse();
			response.setFolio(solicitud.getNoFolioSolicitud());
			
			try {
				tramiteAsegurado.setTipoTramite(solicitud.getTramites().get(0).getTipoTramite());
				serviceBusiness.getAcuseActualizacionDatos(solicitud.getNoFolioSolicitud(), tramiteAsegurado.getTipoTramite().getDescripcion(), solicitud.getFirmaElectronica(), tramiteAsegurado);
			} catch (Exception e1) {
				log.error("Ocurrio un error al generar el acuse de actualizacion de datos");
				e1.printStackTrace();
			}
			
		} catch (ImpactaAlmacenesWSException e) {
			e.printStackTrace();
			return new CambioCurpResponse("013", ERROR_ALMACENES_VIGENCIA);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new CambioCurpResponse("014", INFORMACION_INCORRECTA);
		} catch (Exception e) {
			e.printStackTrace();
			return new CambioCurpResponse("015", ERROR_DESCONOCIDO);
		}
		
		String correo = tramite.getCorreo();
		String curpNueva = tramite.getCurpNueva();
		
		try {
			if(tramite.isActualizaPortalCorreo()) {
				portalCiudadanoServiceBusinessRemote.actualizarCurpACorreo(correo,curpNueva);
			} else {
				portalCiudadanoServiceBusinessRemote.validarInicioCurpCorreo(curpNueva,correo, true);
			}
		} catch (PortalCiudadanoException e) {
			e.printStackTrace();
		}
		
		if(tramite.isActualizaAsignacionCorreo()) {
			try {
				solicitudNssCorreoServiceBusiness.actualizarCurpACorreo(correo, curpNueva);
			} catch (SolicitudNssCorreoException e) {
				e.printStackTrace();
			}
		}
		
		return response;
	}

	@Override
	public VerificarCambioCurpResponse validaCambioDatosCurp(String curp,
			String nss, String correo) {
		
		//verificamos que todos los campos necesarios vengan
		if((StringUtils.isBlank(curp) || curp.trim().length() < 18) || (StringUtils.isBlank(nss) || nss.trim().length() < 11) || StringUtils.isBlank(correo)) {
			return new VerificarCambioCurpResponse("012", INFORMACION_INCOMPLETA);
		}
		
		//buscamos el NSS
		AsignacionNSS asignacionNSS = serviceBusiness.obtenerAsignacionNss(nss);
		if(asignacionNSS == null) {
			return new VerificarCambioCurpResponse("016", NSS_INEXISTENTE);
		}
		
		if(StringUtils.isBlank(asignacionNSS.getCurp())) {
			return new VerificarCambioCurpResponse("017", MENSAJE_ACTUALIZACION_CURP);
		}
		
		if(!asignacionNSS.getCurp().equals(curp)) {
			return new VerificarCambioCurpResponse("018", "NSS o CURP incorrecto.");
		}
		
		VerificarCambioCurpResponse respuesta = new VerificarCambioCurpResponse();
		
		
		List<Long> tiposTramite = new ArrayList<Long>();
		tiposTramite.add(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.ACTUALIZACION_CURP_ASEGURADO.getCodigo().longValue());
		try {
			Solicitud solicitud = solicitudTramiteBusinessRemote.getUltimaSolicitudPorEstadoTramiteYTipoTramitegetUltimoTramiteByEstado(asignacionNSS.getIdPersona(), tiposTramite, EstadoTramiteEnum.CERRADO.getId());
			
			if(solicitud != null) {
				respuesta.setEstatus("true");
				respuesta.setFechaActualizacion(solicitud.getFechaConclusionParse());
				return respuesta;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//si llegamos a este punto es que no existe cambio de CURP
		respuesta.setEstatus("false");
		respuesta.setFechaActualizacion(null);
		
		return respuesta;
	}
	
}