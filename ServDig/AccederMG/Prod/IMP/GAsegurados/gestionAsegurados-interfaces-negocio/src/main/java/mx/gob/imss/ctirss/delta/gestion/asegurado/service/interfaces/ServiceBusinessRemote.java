package mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AseguradoConRPAsignado;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.WsAntecedentesAseguradoExcpetion;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.AsignacionNssPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ArgumentosInvalidosException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.global.model.PersonaTO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AltaDatosAsignacionNSSType;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.TipoSerieEnum;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.AseguradoWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.ValidarAsignacionLocalizacionNssWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionMasivaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.nss.ModuloOrigenAsignacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
@Remote
public interface ServiceBusinessRemote {

	Object getAcuseActualizacionDatos(String folioSolicitud, String titulo, FirmaElectronica fe, TramiteActualizacionAsegurado tramite) throws Exception;
		
	Solicitud crearFinalizarTramiteActualizacionDatos(TramiteActualizacionAsegurado tramite)
	throws ImpactaAlmacenesWSException,IllegalArgumentException, Exception;
	
	Solicitud crearFinalizarTramiteActualizacionDatos(TramiteActualizacionAsegurado tramite, OrigenSolicitudEnum origenSolicitud)
			throws ImpactaAlmacenesWSException,IllegalArgumentException, Exception;
	
	List<AsignacionNSS> buscarAsignacionNssPorCurpODatosBasicos(Fisica fisica) 
	throws AsignacionNSSNoLocalizadoException, DatosInsuficientesParaConsultaException;
	
	String generaNss(Long idDelegacion, Long idSubDelegacion,
			Long numAnioNacimiento);

	Solicitud procesarTramiteSolicitud(Solicitud solicitud)
			throws SolicitudNoEncontradaException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			SolicitudEnProcesoException;

	String altaPersonaNss(Fisica fisica, Serie serie);

	// Soporta encolado de solicitud a fin de definir si se concluye o se envia
	// a procesamiento manual:
	void concluir(Solicitud solicitud) throws SolicitudEnProcesoException;

	/**
	 * 191807 021012 MEtodo encargado de cancelar una solocitud pendiente
	 * 
	 * @param solicitud
	 */
	void cancelar(Solicitud solicitud);

	/**
	 * Servicio que crea la solicitud para asignaci�n de NSS. Realiza las
	 * validaciones correspondientes.
	 * 
	 * @param fisica
	 * @param origenAsignacion
	 * @param moduloOrigen
	 * @return
	 * @throws SolicitudException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws GenerarNSSException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws PersonaConNSSException
	 * @throws SolicitudNoValidaException
	 * @throws DomicilioNoLocalizadoException
	 * @throws UmfNoLocalizadaException
	 */
	Map<String, Object> generarSolicitudAsignacionNSS(Fisica fisica,
			OrigenSolicitudEnum origenAsignacion,
			ModuloOrigenAsignacionEnum moduloOrigen) throws SolicitudException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, SolicitudNoValidaException,
			DomicilioNoLocalizadoException, UmfNoLocalizadaException;

	/**
	 * Servicio que guardar en base de datos la solicitud para la asignaci�n de
	 * NSS recibida. Recibe la solicitud y la UMF que corresponde al asegurado.
	 * 
	 * @param solicitud
	 * @return
	 * @throws SolicitudNoValidaException
	 */
	Solicitud guardarSolicitudAsignacionNSS(Solicitud solicitud,
			UnidadMedicaFamiliar umf, FirmaElectronica firmaElectronica)
			throws SolicitudNoValidaException;

	/**
	 * Servicio que procesa la solicitud de NSS.
	 * 
	 * @param idSolicitud
	 * @return AseguradoWrapper
	 * @throws SolicitudNoEncontradaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws RegistroPersonaFisicaException
	 * @throws NivelDeAsignacionSerieIndefinidoException
	 * @throws SeriesNoLocalizadasException
	 * @throws ErrorAlActivarSerieException
	 * @throws TramiteNoEncontradoException
	 * @throws DomicilioNoValidoException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws PersonaSinCalificacionesException
	 * @throws SolicitudNoValidaException 
	 */
	AseguradoWrapper procesarSolicitudAsignacionNSS(Long idSolicitud)
			throws SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			RegistroPersonaFisicaException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException, DomicilioNoValidoException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinCalificacionesException, SolicitudNoValidaException;

	/**
	 * @param fisica
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 */
	Fisica getDatosBasicosPersonaNueva(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException;

	/**
	 * @param fisica
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws DatosInsuficientesParaConsultaException
	 */
	List<Fisica> localizarPersonaReglasDerechohabiente(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			DatosInsuficientesParaConsultaException;
	
	/**
	 * @param fisica
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws DatosInsuficientesParaConsultaException
	 */
	List<Fisica> localizarPersonaFisica(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			DatosInsuficientesParaConsultaException;

	/**
	 * Servicio que contiene la l�gica y validaciones para la b�squeda de la
	 * persona a la que se le va a generar y asignar el NSS o en su defecto
	 * la persona nueva a crear
	 * 
	 * @param fisica
	 * @param validarSolicitudesActivas
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws GenerarNSSException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws PersonaConNSSException
	 * @throws DomicilioNoLocalizadoException
	 * @throws UmfNoLocalizadaException
	 */
	Fisica validacionesNSS(Fisica fisica, boolean validarSolicitudesActivas)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, DomicilioNoLocalizadoException,
			UmfNoLocalizadaException;

	/**
	 * Servicio que contiene la l�gica y validaciones para la b�squeda de la
	 * persona a la que se le va a generar y asignar el NSS en su defecto
	 * la persona nueva a crear. SIN CONSULTAR RENAPO, esta persona se debe
	 * mandar como paramentro de entrada
	 * 
	 * @param fisica
	 * @param fisicaRENAPO
	 * @param validarSolicitudesActivas
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws GenerarNSSException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws PersonaConNSSException
	 * @throws DomicilioNoLocalizadoException
	 * @throws UmfNoLocalizadaException
	 */
	Fisica validacionesNSSSinConsultaRENAPO(Fisica fisica, Fisica fisicaRENAPO,
			boolean validarSolicitudesActivas)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, DomicilioNoLocalizadoException,
			UmfNoLocalizadaException;

	Fisica validacionesNSSSinCalificacion(ValidarAsignacionLocalizacionNssWrapper validacionWrapper)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, DomicilioNoLocalizadoException,
			UmfNoLocalizadaException;

	/**
	 * Servicio que crea la solicitud para la asignaci�n de NSS. IMPORANTE: S�lo
	 * crea el objeto, NO GUARDA la solicitud en base de datos.
	 * 
	 * @param fisica
	 * @param tipoSerie
	 * @param origenAsignacion
	 * @param curpRENAPO
	 * @param nssCorreo
	 * @param usuario
	 * @param procesoOrigen
	 * @return
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudException
	 */
	Solicitud crearSolicitudAsignacionNSS(Fisica fisica, TipoSerieEnum tipoSerie,
			OrigenSolicitudEnum origenAsignacion, String curpRENAPO,
			SolicitudNssCorreo nssCorreo, Usuario usuario,
			ModuloOrigenAsignacionEnum procesoOrigen)
			throws SolicitudNoValidaException, SolicitudException;

	/**
	 * Servicio que crear una solicitud de recuperaci�n de NSS,
	 * 
	 * @param fisica
	 * @param origenAsignacion
	 * @param Usuario que realiza el tramite
	 * @return
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudException
	 */
	Map<String, Object> crearSolicitudRecuperacionNSS(Fisica fisica,
			OrigenSolicitudEnum origenAsignacion, Usuario usuario)
			throws SolicitudNoValidaException, SolicitudException;
	
	/**
	 * Servicio que se encarga de cambiar la relaci�n hacia la persona de un NSS
	 * 
	 * @param nss
	 * @param idPersonaDuenia
	 * @param idPersonaAsignar
	 */
	void cambiarDuenioNSS(String nss, Long idPersonaDuenia,
			Long idPersonaAsignar);

	/**
	 * Servicio que arma el correo para un NSS reci�n asignado o recuperado
	 * (depende de la bandera recibida) y encola el mensaje en el queue
	 * correspondiente para que el OSB sea el encargado de enviar el correo
	 * 
	 * @param fisica
	 * @param folioSolicitud
	 * @param isAsignacion
	 */
	void enviarCorreoNSS(Fisica fisica, String folioSolicitud,
			boolean isAsignacion, Long idSolicitud, Map<String, byte[]> adjuntos);

	/**
	 * Servicio para encolar al OSB la solicitud de asignaci�n de NSS
	 * 
	 * @param solicitud
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	void encolarSolicitudAsignacionNSS(Solicitud solicitud)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;

	/**
	 * Servicio para ejecutar la operaci�n recibida con el objeto nss-correo
	 * 
	 * @param nssCorreo
	 */
	void ejecutarOperacionValidacionCorreoCurp(SolicitudNssCorreo nssCorreo);

	/**
	 * Servicio para obtener una solicitud de asignaci�n en estatus registrada
	 * para la persona especificada
	 * 
	 * @param idPersona
	 * @return
	 * @throws SolicitudException
	 */
	Solicitud obtenerSolicitudRegistrada(Long idPersona)
			throws SolicitudException;

	/**
	 * Servicio para obtener una solicitud de asignaci�n en estatus
	 * pendiente_autorizacion para la persona especificada
	 * 
	 * @param idPersona
	 * @return
	 * @throws SolicitudException
	 */
	Solicitud obtenerSolicitudEnProceso(Long idPersona)
			throws SolicitudException;

	/**
	 * Metodo encargado de actualizar una persona con asignacion nss validando
	 * que no existan antecedentes, creando solicitud y tramite
	 * 
	 * @param infoPersona
	 * @throws DatosInsuficientesParaConsultaException
	 * @throws PersonaConNSSException
	 * @throws SolicitudNoValidaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	Solicitud actualizaAsignacionNSS(
			TramiteCambioInformacionPersona infoPersona, Usuario usuario)
			throws DatosInsuficientesParaConsultaException,
			PersonaConNSSException, SolicitudNoValidaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			SolicitudNoEncontradaException, TramiteNoEncontradoException;

	/**
	 * Metodo que valida las reglas de negocio para actualizar un asegurado,
	 * valida que exista el NSS en caso de tener curp que exista en renapo,
	 * valida que no sea un patr�n
	 * 
	 * @param fisicaNSS
	 * @return
	 * @throws ArgumentosInvalidosException
	 * @throws AsignacionNSSNoLocalizadoException
	 * @throws GestionPatronalBusinessException
	 * @throws AseguradoConRPAsignado
	 */
	AsignacionNSS validaExisteNSSActualizacion(Fisica fisicaNSS)
			throws ArgumentosInvalidosException,
			AsignacionNSSNoLocalizadoException,
			GestionPatronalBusinessException, AseguradoConRPAsignado,
			ClienteWebserviceRenapoCurpException;

	/**
	 * Servicio que procesa la solicitud de Asignaci&oacute;n de NSS que recibe,
	 * es decir, no la vuelva a consultar.
	 * 
	 * @param solicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws DomicilioNoValidoException
	 * @throws NivelDeAsignacionSerieIndefinidoException
	 * @throws SeriesNoLocalizadasException
	 * @throws ErrorAlActivarSerieException
	 * @throws TramiteNoEncontradoException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws PersonaSinCalificacionesException
	 * @throws SolicitudNoValidaException 
	 */
	AseguradoWrapper procesarSolicitudAsignacionNSSSinConsulta(
			Solicitud solicitud) throws SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			DomicilioNoValidoException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinCalificacionesException, SolicitudNoValidaException;
	
	/**
	 * Servicio que orquesta todos los servicios involucrados en la
	 * generaci&oacute;n del NSS.
	 * 
	 * @param fisica
	 * @param fisicaRENAPO
	 * @param moduloOrigen
	 * @param usuario
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws GenerarNSSException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudException
	 * @throws UmfNoLocalizadaException
	 * @throws SolicitudNoEncontradaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws DomicilioNoValidoException
	 * @throws NivelDeAsignacionSerieIndefinidoException
	 * @throws SeriesNoLocalizadasException
	 * @throws ErrorAlActivarSerieException
	 * @throws TramiteNoEncontradoException
	 * @throws PersonaSinCalificacionesException
	 */
	AseguradoWrapper generarNSSUnSoloPaso(Fisica fisica, Fisica fisicaRENAPO,
			ModuloOrigenAsignacionEnum moduloOrigen, Usuario usuario)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			SolicitudNoValidaException, SolicitudException,
			UmfNoLocalizadaException, SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			DomicilioNoValidoException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException, PersonaSinCalificacionesException;

	/**
	 * Servicio que recibe un objeto que proviene del XML de estudiantes,
	 * los transforma a un objeto Fisica para hacer uso del servicio
	 * generarNSSUnSoloPaso, tambi&eacute;n recibe el nombre del usuario
	 * al que se le van a asociar las solicitudes creadas
	 * 
	 * @param estudiante
	 * @param nombreUsuario
	 * @return
	 * @throws NivelDeAsignacionSerieIndefinidoException
	 * @throws SeriesNoLocalizadasException
	 * @throws ErrorAlActivarSerieException
	 */
	AseguradoWrapper generarNSSEstudiante(
			AltaDatosAsignacionNSSType estudiante, String nombreUsuario)
			throws NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException;

	/**
	 * Servicio que crea y guarda la solicitud de asignaci&oacute;n masiva de
	 * NSS desde SIE
	 * 
	 * @param wrapper
	 * @return
	 * @throws SolicitudNoValidaException
	 * @throws TramiteNoEncontradoException
	 * @throws SolicitudNoEncontradaException
	 */
	Solicitud crearEncolarSolicitudAsignacionMasivaSIE(
			AsignacionMasivaWrapper wrapper, FirmaElectronica firma)
			throws SolicitudNoValidaException, SolicitudNoEncontradaException,
			TramiteNoEncontradoException, SolicitudException;
	
	void generaNSSET(AltaDatosAsignacionNSSType estudiante, String nombreUsuario);
	
	AsignacionNSS obtenerAsignacionNss(String nss);
	
	/**
	 * Servicio que obtiene el cveAsignacionNss asociado a un NSS
	 * dentro de la BDTU, en caso de no encontrar el NSS se devuelve 
	 * nulo
	 * @param nss
	 * @return
	 */
	Long obtenerCveAsignacionNss(String nss);
	
	/**
	 * Servicio que contiene la l�gica y validaciones para la b�squeda de la
	 * persona con NSS y curp para validar si se genera reporte de vigencia.
	 * 
	 * @param fisica
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws PersonaConNSSException
	 * @throws AsignacionNSSNoLocalizadoException
	 */
	Fisica validacionesNSSConsultaVigencia(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			AsignacionNSSNoLocalizadoException;
	
	/**
	 * Servicio que arma el correo para un NSS y encola el mensaje en el queue
	 * correspondiente para que el OSB sea el encargado de enviar el correo
	 * @param fisica
	 * @param tipoTramite
	 * @param paramAdicionales
	 * @param subject
	 */
	void enviarCorreoPersonaFisica(Fisica fisica, TipoTramiteEnum tipoTramite,
			Map<String, String> paramAdicionales, String subject,
			Map<String, byte[]> adjuntos);
	void enviarCorreoPersonaFisicaContent(Fisica fisica,
			TipoTramiteEnum tipoTramite, Map<String, String> paramAdicionales,
			String subject, Map<String, byte[]> adjuntos, String content);
	
	/**
	 * Se agrega metodo para exponer un servicio global para la localizaci�n de NSS.
	 * Este servicio tiene como cliente la aplicaci�n m�vil
	 * @param curp
	 * @return NSS
	 */
	PersonaTO localizarNSSPorCurp(String curp, String correo) 
			throws CURPNoLocalizadoEnEntidadExternaException, 
			ClienteWebserviceRenapoCurpException, 
			ErrorValidacionDatosConsultaEnEntidaExternaException, 
			PersonaSinNSSException, ErrorComparacionDatosRENAPOException, 
			SolicitudNoValidaException, SolicitudException, AsignacionNssPersonaException;
	
	/**
	 * Obtiene el id de la persona cuyo NSS es proporcionado
	 * @param NSS
	 * @return
	 */
	Long obtenerIdPersonaPorNSS(String NSS) throws Exception;
	
	/**
	 * Metodo recortado para guardar el registro de asignacion NSS con tramite y solicitud 
	 * @param solicitud
	 * @param tramiteFisica
	 * @param tramiteAsegurado
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws DomicilioNoValidoException
	 * @throws NivelDeAsignacionSerieIndefinidoException
	 * @throws SeriesNoLocalizadasException
	 * @throws ErrorAlActivarSerieException
	 * @throws TramiteNoEncontradoException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws PersonaSinCalificacionesException
	 * @throws SolicitudNoValidaException
	 */
	Solicitud procesarSolicitudAsignacionNSSLigero (Solicitud solicitud, TramiteFisica tramiteFisica, TramiteAsegurado tramiteAsegurado)
			throws SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			DomicilioNoValidoException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinCalificacionesException, SolicitudNoValidaException;
	
	
	/**
	 * Metodo encargado de ubicar a la persona a la que se hara la actualizacion de NSS aplicando las reglas de negocio de busqueda 
	 * de persona y duplicidad de NSS y datos estad�sticos
	 * @param fisica
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws PersonaConNSSException
	 */
	TramiteActualizacionAsegurado validacionesNSSActualizaCURP(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			PersonaConNSSException,
			AsignacionNssPersonaException,
			GenerarNSSException,WsAntecedentesAseguradoExcpetion;
	
	/**
	 * Metodo encargado de ubicar a la persona a la que se hara la actualizacion de NSS aplicando las reglas de negocio de busqueda 
	 * de persona y duplicidad de NSS y datos estad�sticos
	 * @param fisica
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws PersonaConNSSException
	 */
	TramiteActualizacionAsegurado validacionesNSSActualizaCURPporNSS(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			PersonaConNSSException,
			AsignacionNSSNoLocalizadoException,
			AsignacionNssPersonaException,
			GenerarNSSException, WsAntecedentesAseguradoExcpetion;
	/**
	 * Metodo que busca la curp en renapo y compara contra los datos estadisticos en bdtu de la persona con NSS
	 * valida que la persona BDTU tenga CURP aunque sea historica
	 * @param strCurp
	 * @param strNSS
	 * @return
	 * @throws AsignacionNssPersonaException
	 */
	boolean validaDatosPersonaRenapoPersonaNSSBdtu(String strCurp, String strNSS) throws AsignacionNssPersonaException;
	
	/**
	 * Metodo que realiza las validaciones de unicidad de la persona, datos estad�sticos y si cuenta con NSS o no, en caso 
	 * de que no encuentre personas busca en CL3 y si es asegurado devuelve a la persona cuando los datos estadisticos conciden 
	 * sin considerar la fecha de nacimiento que viene nula.
	 * 
	 * @param curp
	 * @param correo
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws PersonaSinNSSException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudException
	 * @throws AsignacionNssPersonaException
	 */
	 PersonaTO validaAccesoPortalExterno(String curp, String correo)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinNSSException, ErrorComparacionDatosRENAPOException,
			SolicitudNoValidaException, SolicitudException,
			AsignacionNssPersonaException;
	 
	 
	 Fisica validacionesNSSIncluyeCL3(Fisica fisica,
				boolean validarSolicitudesActivas)
				throws CURPNoLocalizadoEnEntidadExternaException,
				ClienteWebserviceRenapoCurpException,
				ErrorValidacionDatosConsultaEnEntidaExternaException,
				GenerarNSSException, ErrorComparacionDatosRENAPOException,
				PersonaConNSSException, DomicilioNoLocalizadoException, UmfNoLocalizadaException;
	 
 	/**	
	 * Metodo encargado de buscar el NSS en BDTU de no encontrarse buscan en las tablas de legados
	 * recibe parametro de legados para saber si se incluyen aun cuando si se encuentre en BDTU el NSS
	 * @param nss String con el NSS a 11 posicioens
	 * @param legados boleano para indicar si se consultan los legados aun cuando se localice en NSS en BDTU
	 * @return Lista de personas con datos basicos y el NSS se seteara el origen donde se encontr� el NSS en los indicadores
	 */
	List<Fisica> getAseguradoByNSSLegadosyBDTU(String nss, Boolean legados) throws IllegalArgumentException;


	AsignacionNSS obtenerAseguradoPorNss(String nss);
	
    AsignacionNSS obtenerAseguradoPorNss(String nss, String curp);
	
	AsignacionNSS obtenerAseguradoPorIdAsignacion(Long idAsignacion);

	String obtenerEstadoPendienteConfirmar(String nss);	
	/**
	 * Busca una porsona con nss SIN validar la fecha de baja como filtro en base de datos
	 * @param nss
	 * @return
	 */
	AsignacionNSS obtenerAseguradoPorNssConBajaLogica(String nss);
}
