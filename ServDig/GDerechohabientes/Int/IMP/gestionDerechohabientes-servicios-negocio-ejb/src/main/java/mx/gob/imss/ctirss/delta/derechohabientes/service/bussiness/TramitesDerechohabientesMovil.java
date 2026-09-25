package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitesDerechohabientesMovilRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.AsignacionNssPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PortalCiudadanoException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.exception.movil.TramiteMovilException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
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
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.TramiteDerechohabientesMovilDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.CodigoRespuestaServiciosExternosEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.CambioClinicaResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.EnvioCorreoResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.RegistroDerechohabienteResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.ValidaRequisitosResponse;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.ValidarAsignacionLocalizacionNssWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CiudadanoCurpCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

@Stateless(name = "tramitesDerechohabientesMovil", mappedName = "tramitesDerechohabientesMovil")
public class TramitesDerechohabientesMovil extends AbstractServiceBusiness
		implements TramitesDerechohabientesMovilRemote {
	
	@EJB
	private GrupoFamiliarServiceLocal grupoFamiliarServiceLocal;
	@EJB
	private RegistroDerechohabienteServiceLocal registroDerechohabienteServiceLocal;
	@EJB
	private CambioClinicaServiceLocal cambioClinicaServiceLocal;
	@EJB(mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	@EJB(mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB
	private EMailServiceLocal emailServiceLocal;
	@EJB
	private UmfServiceLocal umfServiceLocal;
	@EJB( mappedName = "portalCiudadanoServiceBusiness")
	private PortalCiudadanoServiceBusinessRemote portalCiudadanoServiceBusiness;
	@EJB(mappedName = "serviceBusiness")
	private ServiceBusinessRemote serviceBusiness;
	@EJB 
	private SolicitudTramiteBusinessRemote solicitudTramiteBusiness;
	@EJB
	private RequisitosMinimosServiceLocal requisitosMinimosService;
	@EJB
	private DocumentosServiceLocal documentosServiceLocal;
	
	
	private static final String NSS_NO_ENCONTRADO = "No se localizo el NSS con el id proporcionado";
	private static final String NSS_PERSONA_DIFERENTE = "El NSS no corresponde con la persona";
	private static final String ERROR_BUSQUEDA_NSS = "Ocurrio un error al buscar el nss";
	private static final String ERROR_DATOS_INCOMPLETOS = "Los datos para realizar el tramite estan incompletos";
	private static final String ERROR_BUSQUEDA_CABEZA = "Ocurrio un error al consultar la cabeza de grupo familiar";
	private static final String ERROR_GUARDADO_SOLICITUD_REGISTRO = "Ocurrio un error al guardar la solicitud de registro";
	private static final String ERROR_GUARDADO_SOLICITUD_CAMBIO_CLINICA = "Ocurrio un error al guardar la solicitud de cambio de clinica";
	private static final String ERROR_DESCONOCIDO = "Ocurrio un error desconocido al intentar guardar los cambios";
	private static final String ERROR_WS = "Ocurrio un error al calcular la vigencia";
	private static final String ERROR_BUSQUEDA_SOLICITUD = "No fue posible localizar la solicitud";
	private static final String ERROR_BUSQUEDA_INTEGRANTE = "Ocurrio un error al buscar al derechohabiente";
	private static final String ERROR_BUSQUEDA_EXISTENCIA_ASEGURADO = "Ocurrio en error al verficar si el asegurado/pensionado se encontraba asignado a una clinica";
	private static final String ERROR_REGISTRO_ASEGURADO_EXISTENTE = "El NSS ya se encuentra registrado en una UMF";
	private static final String ERROR_CAMBIO_ASEGURADO_NO_REGISTRADO = "El NSS no se encuentra registrado en una UMF";
	
	private static final String ERROR_SIN_NSS = "La CURP capturada no cuenta con un Número de Seguridad Social.";
	private static final String ERROR_SIN_VIGENCIA = "El asegurado/pensionado se encuentra en baja, su situaci\u00F3n de vigencia no permite realizar la solicitud";
	private static final String ERROR_ASEGURADO_REGISTRADO = "Ya se encuentra registrado el NSS en alguna cl\u00EDnica.";
	private static final String ERROR_ASEGURADO_NO_REGISTRADO = "Para poder realizar el cambio de cl\u00EDnica es necesario que el NSS cuenta con una cl\u00EDnica asignada.";
	private static final String ERROR_ASEGURADO_SIN_CAMBIOS_CLINICA = "Usted ya realiz\u00F3 un cambio de cl\u00EDnica hace menos de 6 meses, si actualmente requiere este tr\u00E1mite, podr\u00E1 acudir a la cl\u00EDnica a la cual desea realizar el cambio.";
	private static final String ERROR_ESTUDIANTE = "No es posible realizar el tr\u00E1mite por internet para estudiantes.";
	private static final String ERROR_CONSULTA_CABEZA = "Ocurri\u00F3 un error al consultar la cabeza de grupo familiar.";
	private static final String ERROR_SIN_UMF_ANTERIOR = "No es posible realizar el tr\u00E1mite por este medio ya que no se cuenta con la cl\u00EDnica anterior, si actualmente requiere este tr\u00E1mite, podr\u00E1 acudir a la cl\u00EDnica a la cual desea realizar el cambio.";
	private static final String ERROR_MODALIDAD_NO_PERMITE_REGISTRO = "No cuenta con una relaci\u00F3n laboral que le otorgue servicio m\u00E9dico. Con lo cual no tiene acceso al registro en cl\u00EDnica.";
	
	private static final String MENSAJE_GENERICO_ACCESO_APPS_MOVILES = "“ Los datos registrados en el IMSS asociados a la CURP, presentan alguna inconsistencia, por favor acude a tu Subdelegación para obtener tu Número de Seguridad Social; presentando: CURP, Acta de Nacimiento e Identificación Oficial.";

	
	private final String MENSAJE_CANCELACION_SOLICITUD = "Se cancela la solicitud debido a que se inicia una nueva en App Movil";
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([hmHM]{1}[a-zA-Z]{2}"
			+ "[b-df-hj-np-tv-zB-DF-HJ-NP-TV-Z]{3}[a-zA-Z0-9]{1}[0-9]{1})$";
	private static final int LONGITUD_CURP = 18;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";



	/**
	 * 
	 */
	@Override
	public RegistroDerechohabienteResponse ejecutaRegistroDerechohabiente(TramiteDerechohabientesMovilDto datosTramite, mx.gob.imss.digital.modelo.domicilio.Domicilio domicilioDig, String correo){
		
		String folio = null;
		//NSS del asegurado
		AsignacionNSS nss = null;
		//Cabeza de grupo familiar
		CabezaGrupoFamiliar cabeza = null;
		
		//validamos que los datos necesarios para el tramite vengan
		try {
			this.validaDatosObligatorios(datosTramite, domicilioDig, correo);
		} catch (TramiteMovilException e) {
			return new RegistroDerechohabienteResponse(null, ""+e.getCodigo(), e.getMessage());
		}
		//validamos si el asegurado se encuentra registrado en caso de que si, se manda una excepcion
		try {
			this.validaExistenciaAsegurado(datosTramite, true);
		} catch (TramiteMovilException e) {
			return new RegistroDerechohabienteResponse(null, ""+e.getCodigo(), e.getMessage());
		}
		
		//Le seteamos la valiadad primaria ninguno al domicilio
		Domicilio domicilio = null;
		try {
			domicilio = this.complementarDomicilio(domicilioDig);
		} catch(TramiteMovilException e){
			return new RegistroDerechohabienteResponse(null, ""+e.getCodigo(), e.getMessage());
		}
		
		//obtenemos el nss
		try {
			nss = this.obtenerNSS(datosTramite);
		} catch (TramiteMovilException e1) {
			return new RegistroDerechohabienteResponse(null, ""+e1.getCodigo(), e1.getMessage());
		}

		//Obtenemos la cabeza de grupo familiar
		try {
			cabeza = this.getCabeza(nss.getIdAsignacionNSS());
		} catch (TramiteMovilException e1) {
			return new RegistroDerechohabienteResponse(null, ""+e1.getCodigo(), e1.getMessage());
		}
		
		//Consultamos el objeto medico en turno
		MedicoEnTurno medico = new MedicoEnTurno(datosTramite.getIdConsultorio());
		
		//Creamos el tramite de registro de derechohabientes
		TramiteRegistroDerechohabiente tramite = this.llenarTramiteRegistro(nss, datosTramite.getIdParentesco(), nss, 
				cabeza.getPatronImss().equals(1), domicilio, medico);
		Solicitud solicitud = null;
		//creamos la solicitud
		try {
			solicitud = registroDerechohabienteServiceLocal.registraSolicitud(tramite, OrigenSolicitudEnum.MOVILES.getId());
		} catch (DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al crear la solicitud de registro");
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_GUARDADO_SOLICITUD_REGISTRO, TramiteMovilException.ERROR_DE_SISTEMA);
			return new RegistroDerechohabienteResponse(null,CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_GUARDADO_SOLICITUD_REGISTRO);
		} catch (SolicitudNoValidaException e) {
			log.error("Ocurrio un error al crear la solicitud de registro");
			//TramiteMovilException.throwException(ERROR_GUARDADO_SOLICITUD_REGISTRO+"(1)", TramiteMovilException.ERROR_DE_SISTEMA);
			return new RegistroDerechohabienteResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_GUARDADO_SOLICITUD_REGISTRO);
		}
		//validamos que la solicitud no este vacia
		try {
			this.validaSolicitud(solicitud, ERROR_GUARDADO_SOLICITUD_REGISTRO);
		} catch (TramiteMovilException e1) {
			return new RegistroDerechohabienteResponse(null, ""+e1.getCodigo(), e1.getMessage());
		}
		//obtenemos el folio
		folio = solicitud.getNoFolioSolicitud();
		//finalizamos el registro del derechohabiente
		try {
			solicitud = registroDerechohabienteServiceLocal.finalizarSolicitudRegistroMovil(solicitud, cabeza);
		} catch (DerechohabientesBusinessException e) {
			log.debug("Ocurrio un error al guardar la solicitud",e);
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_GUARDADO_SOLICITUD_REGISTRO + "(3), "+ e.getMessage(), TramiteMovilException.ERROR_DE_SISTEMA);
			return new RegistroDerechohabienteResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_GUARDADO_SOLICITUD_REGISTRO);
		} catch (SolicitudNoValidaException e) {
			log.debug("Ocurrio un error al guardar la solicitud",e);
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_BUSQUEDA_SOLICITUD + "(2)", TramiteMovilException.ERROR_DE_SISTEMA);
			return new RegistroDerechohabienteResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_BUSQUEDA_SOLICITUD);
		} catch (SolicitudNoEncontradaException e) {
			log.debug("Ocurrio un error al guardar la solicitud",e);
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_BUSQUEDA_SOLICITUD + "(2)", TramiteMovilException.ERROR_DE_SISTEMA);
			return new RegistroDerechohabienteResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_BUSQUEDA_SOLICITUD);
		} catch (SolicitudException e) {
			log.debug("Ocurrio un error al guardar la solicitud",e);
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_GUARDADO_SOLICITUD_REGISTRO + "(2)", TramiteMovilException.ERROR_DE_SISTEMA);
			return new RegistroDerechohabienteResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_GUARDADO_SOLICITUD_REGISTRO);
		} catch (ImpactaAlmacenesWSException e) {
			log.debug("Ocurrio un error desconocido al enviar el movimiento de vigencia para el id " + datosTramite.getIdAsignacionNSS(),e);
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_WS, TramiteMovilException.ERROR_ALMACEN_VIGENCIA);
			return new RegistroDerechohabienteResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_ALMACEN_VIGENCIA.getCodigo(), ERROR_WS);
		} catch (Exception e) {
			log.debug("Ocurrio un error desconocido al finalizar el registro",e);
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_DESCONOCIDO, TramiteMovilException.ERROR_DE_SISTEMA);
			return new RegistroDerechohabienteResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_DESCONOCIDO);
		}
		
		try {
			this.validaSolicitud(solicitud, ERROR_GUARDADO_SOLICITUD_REGISTRO);
		} catch (TramiteMovilException e) {
			return new RegistroDerechohabienteResponse(null, ""+e.getCodigo(), e.getMessage());
		}
		
		this.verificaroInsertarCorreoCiudadano(nss.getCurp(), correo);

		return new RegistroDerechohabienteResponse(folio);
	}
	
	@Override
	public CambioClinicaResponse ejecutaCambioClinicaDerechohabiente(TramiteDerechohabientesMovilDto datosTramite,mx.gob.imss.digital.modelo.domicilio.Domicilio domicilioDig, String correo){
		
		String folio = null;
		//NSS del asegurado
		AsignacionNSS nss = null;
		//Cabeza de grupo familiar
		CabezaGrupoFamiliar cabeza = null;
		//validamos que los datos necesarios para el tramite vengan
		try {
			this.validaDatosObligatorios(datosTramite, domicilioDig, correo);
		} catch (TramiteMovilException e) {
			return new CambioClinicaResponse(null, ""+e.getCodigo(), e.getMessage());
		}
		//validamos si el asegurado se encuentra registrado en caso de que si, se manda una excepcion
		try {
			this.validaExistenciaAsegurado(datosTramite, false);
		} catch (TramiteMovilException e) {
			return new CambioClinicaResponse(null, ""+e.getCodigo(), e.getMessage());
		}
		//Le seteamos la valiadad primaria ninguno al domicilio
		Domicilio domicilio = null;
		try {
			domicilio = this.complementarDomicilio(domicilioDig);
		} catch(TramiteMovilException e){
			return new CambioClinicaResponse(null, ""+e.getCodigo(), e.getMessage());
		}
		
		//obtenemos el nss
		try {
			nss = this.obtenerNSS(datosTramite);
		} catch (TramiteMovilException e1) {
			return new CambioClinicaResponse(null, ""+e1.getCodigo(), e1.getMessage());
		}

		//Obtenemos la cabeza de grupo familiar
		try {
			cabeza = this.getCabeza(nss.getIdAsignacionNSS());
		} catch (TramiteMovilException e1) {
			return new CambioClinicaResponse(null, ""+e1.getCodigo(), e1.getMessage());
		}
		//integrante para cambio
		GrupoFamiliar integrante = null;
		//Solicitud
		Solicitud solicitud = null;
		try {
			integrante = grupoFamiliarServiceLocal.getIntegranteGrupoFamiliarSinVigencia(nss.getIdAsignacionNSS(), datosTramite.getIdPersona());
		} catch (Exception e) {
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_BUSQUEDA_INTEGRANTE, TramiteMovilException.ERROR_DE_SISTEMA);
			return new CambioClinicaResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_BUSQUEDA_INTEGRANTE);
		}
		
		if(integrante == null) {
			//TramiteMovilException.throwException(ERROR_BUSQUEDA_INTEGRANTE, TramiteMovilException.ERROR_DE_SISTEMA);
			return new CambioClinicaResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_BUSQUEDA_INTEGRANTE);
		}
		
		TramiteCorreccionDerechohabiente correccion = this.llenarTramiteCambioClinica(integrante, nss, datosTramite, domicilio);
		Usuario usuario = new Usuario();
        usuario.setUsuario(nss.getCurp());
        
		try {
			solicitud = cambioClinicaServiceLocal.crearSolicitudCambioClinica(correccion, integrante, nss, cabeza,
			        true, null, usuario, OrigenSolicitudEnum.MOVILES);
			folio = solicitud.getNoFolioSolicitud();
		} catch (Exception e) {
			log.debug("Ocurrio un error al guardar la solicitud");
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_GUARDADO_SOLICITUD_CAMBIO_CLINICA, TramiteMovilException.ERROR_DE_SISTEMA);
			return new CambioClinicaResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_GUARDADO_SOLICITUD_CAMBIO_CLINICA);
		}
		
		try {
			this.validaSolicitud(solicitud, ERROR_GUARDADO_SOLICITUD_CAMBIO_CLINICA);
		} catch (TramiteMovilException e) {
			return new CambioClinicaResponse(null, ""+e.getCodigo(), e.getMessage());
		}
		
		try {
			solicitud = cambioClinicaServiceLocal.finalizaSolicitudCambioClinica(solicitud, nss, cabeza, null);
		} catch (ImpactaAlmacenesWSException e) {
			log.debug("Ocurrio un error desconocido al enviar el movimiento de vigencia para el id " + datosTramite.getIdAsignacionNSS(),e);
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_WS, TramiteMovilException.ERROR_ALMACEN_VIGENCIA);
			return new CambioClinicaResponse(null,  CodigoRespuestaServiciosExternosEnum.ERROR_ALMACEN_VIGENCIA.getCodigo(), ERROR_WS);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			log.debug("Ocurrio un error desconocido al finalizar el registro",e);
			e.printStackTrace();
			//TramiteMovilException.throwException(ERROR_DESCONOCIDO, TramiteMovilException.ERROR_DE_SISTEMA);
			return new CambioClinicaResponse(null, CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), ERROR_DESCONOCIDO);
		}
		

		this.verificaroInsertarCorreoCiudadano(nss.getCurp(), correo);
		
		return new CambioClinicaResponse(folio);
	}

	private Domicilio complementarDomicilio(mx.gob.imss.digital.modelo.domicilio.Domicilio domicilioDig) throws TramiteMovilException{
		//Le seteamos la valiadad primaria ninguno al domicilio
		Domicilio domicilio = null;
		try {
			domicilio = domicilioServiceBusinessRemote.complementarLocalidadDomicilioDigRecortado(domicilioDig);
		} catch (DomicilioNoValidoException e) {
			log.error("Ocurrio un ileegal argument exception domicilio");
			e.printStackTrace();
			TramiteMovilException.throwException(e.getMessage(),  TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
		} catch (DomicilioNoLocalizadoException e) {
			log.error("No se encontro la localizad");
			e.printStackTrace();
			TramiteMovilException.throwException(e.getMessage(),  TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
		}
		
		return domicilio;
	}
	/**
	 * Metodo para obtener los datos del nss y validar que la persona corresponda
	 * @param datosTramite
	 * @return
	 * @throws TramiteMovilException
	 */
	private AsignacionNSS obtenerNSS(TramiteDerechohabientesMovilDto datosTramite) throws TramiteMovilException{
		AsignacionNSS nss = null;
		//buscamos el nss con el id que se envia en los datos del tramite
		try {
			nss = grupoFamiliarServiceLocal.getAsignacionNssByIdAsignacion(datosTramite.getIdAsignacionNSS());
		} catch (DerechohabientesBusinessException e) {
			//en caso de ocurrir un error retoranamos una excepcion
			e.printStackTrace();
			TramiteMovilException.throwException(ERROR_BUSQUEDA_NSS + datosTramite.getIdAsignacionNSS(), TramiteMovilException.ERROR_DE_SISTEMA);
		}
		
		//Si es asegurado o pensionado nos aseguramos que el id de la persona corresponda con el
		//del NSS
		if(datosTramite.getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId()) ||
				datosTramite.getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())) {
			//si no hubo ningun error, validamos que la persona sea la misma del nss
			this.validaCorrespondenciaNssPersona(nss, datosTramite);
		}
		
		return nss;
	}

	/**
	 * Metodo para validar si la solicitud no es nula
	 * @param solicitud
	 * @param mensaje
	 * @throws TramiteMovilException
	 */
	private void validaSolicitud(Solicitud solicitud, String mensaje) throws TramiteMovilException {
		if(solicitud == null) {
			TramiteMovilException.throwException(mensaje,TramiteMovilException.ERROR_DE_SISTEMA);
		}
	}
	
	/**
	 * Metodo para buscar a la cabeza de grupo familiar
	 * @param idAsignacionNSS
	 * @return
	 * @throws TramiteMovilException
	 */
	private CabezaGrupoFamiliar getCabeza(Long idAsignacionNSS) throws TramiteMovilException{
		CabezaGrupoFamiliar cabeza = null;
		
		try {
			cabeza = grupoFamiliarServiceLocal.cabezaGrupoFamiliar(idAsignacionNSS);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			TramiteMovilException.throwException(ERROR_BUSQUEDA_CABEZA, TramiteMovilException.ERROR_DE_SISTEMA);
		} catch (Exception e) {
			e.printStackTrace();
			TramiteMovilException.throwException(ERROR_BUSQUEDA_CABEZA, TramiteMovilException.ERROR_DE_SISTEMA);
		}
		
		if(cabeza == null) {
			TramiteMovilException.throwException(ERROR_BUSQUEDA_CABEZA, TramiteMovilException.ERROR_DE_SISTEMA);
		}
		
		return cabeza;
	}
	
	/**
	 * Metodo para validar que el objeto traiga todos los datos necesarios para el tramite
	 * @param datosTramite
	 * @throws TramiteMovilException
	 */
	private void validaDatosObligatorios(TramiteDerechohabientesMovilDto datosTramite, mx.gob.imss.digital.modelo.domicilio.Domicilio domicilio, String correo) throws TramiteMovilException{
		
		Long idAsignacionNSS  = datosTramite.getIdAsignacionNSS();
		Long idPersona = datosTramite.getIdPersona();
		Long idParentesco = datosTramite.getIdParentesco();
		Long idConsultorio = datosTramite.getIdConsultorio();
		
		if(idAsignacionNSS == null || idPersona == null ||
				idParentesco == null || idConsultorio == null
				|| domicilio == null || StringUtils.isBlank(correo)) {
			TramiteMovilException.throwException(ERROR_DATOS_INCOMPLETOS, TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
		}
	}
	
	/**
	 * Metodo para validar que los datos del nss correspondan con la persona 
	 * @param nss
	 * @param datosTramite
	 * @throws TramiteMovilException
	 */
	private void validaCorrespondenciaNssPersona(AsignacionNSS nss, TramiteDerechohabientesMovilDto datosTramite) throws TramiteMovilException{
		//verificamos si encontramos el nss con el id proporcionado
		if(nss == null) {
			TramiteMovilException.throwException(NSS_NO_ENCONTRADO, TramiteMovilException.NSS_NO_ENCONTRADO);
		}
		
		//verificamos si la persona dueña del nss es la misma que a la que se le quiere hacer el tramite
		if(!nss.getIdPersona().equals(datosTramite.getIdPersona())) {
			TramiteMovilException.throwException(NSS_PERSONA_DIFERENTE, TramiteMovilException.NSS_PERSONA_DIFERENTE);
		}
	}
	
	/**
	 * Metodo para crear el tramite derechohabiente
	 * @param session
	 * @param fisica
	 * @param idParentesco
	 * @param nss
	 * @param patronImss
	 * @return
	 */
	private TramiteRegistroDerechohabiente llenarTramiteRegistro(Fisica fisica, Long idParentesco, AsignacionNSS nss, Boolean patronImss,
			Domicilio domicilio, MedicoEnTurno medico) {
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
		//Establecemos la razon de registro
		RazonRegistro razonRegistro = new RazonRegistro();
		razonRegistro.setIdRazonRegistro(RazonRegistroEnum.NORMAL.getId());
		
		tramite.setPaso(1L);
		//Seteamos la razon del registro
		tramite.setRazonRegistro(razonRegistro);
		tramite.setTipoTramite(this.getTipoTramitePorParentesco(idParentesco));
		tramite.setDomicilio(domicilio);
		tramite.setMedicoEnTurno(medico);
		
		tramite.setUsuario(new Usuario());
		tramite.getUsuario().setCveIdUsuario(nss.getCurp());
		tramite.getUsuario().setUsuario(nss.getCurp());
		
		return tramite;
	}
	
	private TipoTramite getTipoTramitePorParentesco(Long idParentesco) {
		Integer idTipoTramite = 0;
		//Verificamos si el parentesco es MADRE de ser asi, lo cambiamos por padres
		idParentesco = idParentesco.equals(ParentescoEnum.MADRE.getId()) ? ParentescoEnum.PADRES.getId() : idParentesco;
		//verificamos si el parentesco es concubia
		idParentesco = idParentesco.equals(ParentescoEnum.CONCUBINA.getId()) ? ParentescoEnum.CONCUBINARIO.getId() : idParentesco;
		
		if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId())) {
			idTipoTramite = TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo();
		} else if(idParentesco.equals(ParentescoEnum.PENSIONADO.getId())) {
			idTipoTramite = TipoTramiteEnum.REGISTRO_PENSIONADO.getCodigo();
		}else if(idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
			idTipoTramite = TipoTramiteEnum.REGISTRO_HIJOS.getCodigo();
		}else if(idParentesco.equals(ParentescoEnum.CONYUGE.getId())) {
			idTipoTramite = TipoTramiteEnum.REGISTRO_CONYUGUE.getCodigo();
		} else if(idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())){
			idTipoTramite = TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo();
		} else {
			idTipoTramite = TipoTramiteEnum.REGISTRO_PADRES.getCodigo();
		}
		
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(idTipoTramite);
		
		return tipoTramite;
	}

	/**
	 * Metodo para enviar el correo con los documentos generados por el tramite
	 */
	@Override
	public EnvioCorreoResponse enviarCorreoConDocumentos(String folioSolicitud, String correo){
		
		Solicitud solicitud = null;
		MedicoEnTurno medicoEnTurno = null;
		
		if(StringUtils.isBlank(folioSolicitud) || StringUtils.isBlank(correo)) {
			//TramiteMovilException.throwException("Es necesario el folio de la solicitud y el correo", TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
			return new EnvioCorreoResponse(CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(), "Es necesario el folio de la solicitud y el correo");
		}
		
		solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioSolicitud);
		
		try {
			solicitud = solicitudBusinessRemote.consultarFolio(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			solicitud = null;
			log.error("No fue posible locaizar la solicitud con folio " + folioSolicitud);
			e.printStackTrace();
		}
		//Checamos que la solicitud no sea nula
		try {
			this.validaSolicitud(solicitud, "No fue posible localizar la solicitud con el folio " + folioSolicitud);
		} catch (TramiteMovilException e1) {
			return new EnvioCorreoResponse(""+e1.getCodigo(), e1.getMessage());
		}
		//Checamos que la solicitud contenga tramites validos
		try {
			this.validaTramitesMovilesValidos(solicitud);
		} catch (TramiteMovilException e1) {
			return new EnvioCorreoResponse(""+e1.getCodigo(), e1.getMessage());
		}
		
		Map<String, byte[]>  doctosGenerados = new HashMap<String, byte[]>();
		String descripcionTipoTramite = null;
		
		for(Tramite tramite: solicitud.getTramites()) {
			for(DocumentoPorTipo docto: tramite.getDocumentoPorTipos()) {
				try {
					byte[] doctoGen = solicitudBusinessRemote.obtenerDocumentoResultante(solicitud, tramite.getTramiteId(), docto.getIdDocumentoPorTipo().intValue());
					
					if(doctoGen != null) {
						log.debug("Se genero el documento " + docto.getDocumento().getDesDocumento());
						doctosGenerados.put(docto.getDocumento().getDesDocumento()+".pdf", doctoGen);
					} else {
						log.debug("No se genero el documento " + docto.getDocumento().getDesDocumento());
					}
				} catch(Exception e) {
					log.error("ocurrio un error al generar el documento " + docto.getDocumento().getDesDocumento(), e);
				}
			}
		}
		
		try {
			String strFechaOperacion = DateFormat.getDateInstance(
					DateFormat.FULL, new Locale("es", "MX")).format(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			String strFechaSolicitud = sdf.format(solicitud.getFechaSolicitud());

			Map<String, String> mailAttr = new HashMap<String, String>();
			mailAttr.put("fechaOperacion", strFechaOperacion);
			mailAttr.put("folio", solicitud.getNoFolioSolicitud());
			mailAttr.put("idSolicitud", solicitud.getSolicitudId().toString());
			
			InstanceofPredicate tramiteRegDhabPredicate = new InstanceofPredicate(TramiteRegistroDerechohabiente.class);
			InstanceofPredicate tramiteCorrDhabPredicate = new InstanceofPredicate(TramiteCorreccionDerechohabiente.class);
			Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramiteRegDhabPredicate);
			Object tramiteModifInicial = CollectionUtils.find(solicitud.getTramites(), tramiteCorrDhabPredicate);
			
			if (tramiteInicial != null) {
				descripcionTipoTramite = "Alta en Cl\u00EDnica o UMF (Unidad de Medicina Familiar) con CURP";
				TramiteRegistroDerechohabiente tramiteRegistro = (TramiteRegistroDerechohabiente) tramiteInicial;
				medicoEnTurno = tramiteRegistro.getMedicoEnTurno();
				mailAttr.put("idTipoTramite", tramiteRegistro.getTipoTramite().getIdTipoTramite().toString());
				mailAttr.put("nombreEmpaquetado", "registroDerechoHabiente_" + tramiteRegistro.getTramiteId() + ".pdf");
				try{
					byte[] acuseRegistro = (byte[])documentosServiceLocal.getAcuseRegistroMoviles(solicitud);
					doctosGenerados.put("acuseRegistro.pdf", acuseRegistro);
				} catch(Exception e) {
					log.error("Ocurio un error al generar el acuse de registro");
					e.printStackTrace();
				}
			} else if (tramiteModifInicial != null) {
				descripcionTipoTramite = "Cambio de Cl\u00EDnica o UMF (Unidad de Medicina Familiar) con CURP";
				TramiteCorreccionDerechohabiente tramiteCorreccion = (TramiteCorreccionDerechohabiente) tramiteModifInicial;
				medicoEnTurno = tramiteCorreccion.getMedicoEnTurnoNuevo();
				mailAttr.put("idTipoTramite", tramiteCorreccion.getTipoTramite().getIdTipoTramite().toString());
				mailAttr.put("nombreEmpaquetado", "cambioClinica_" + tramiteCorreccion.getTramiteId() + ".pdf");
				try{
				byte[] acuseRegistro = (byte[])documentosServiceLocal.getAcuseCambioClinicaMoviles(solicitud);
				doctosGenerados.put("acuseCambioClinica.pdf", acuseRegistro);
				} catch(Exception e) {
					log.error("Ocurio un error al generar el acuse de registro");
					e.printStackTrace();
				}
			}
			
			// Datos de umf (si no se encuentran en sesion)
			UnidadMedicaFamiliar umf = medicoEnTurno.getUnidadMedicaFamiliar();
			if (StringUtils.isBlank(umf.getDesDireccion()) || StringUtils.isBlank(umf.getDescripcion())) {
				try {
					UnidadMedicaFamiliar umfLocalizada = umfServiceLocal.getUnidadMedicaFamiliarById(umf.getIdUMF());
					umf.setDesDireccion(umfLocalizada.getDesDireccion());
					umf.setDescripcion(umfLocalizada.getDescripcion());
				} catch (DerechohabientesBusinessException e) {
					umf.setDesDireccion("No disponible");
					umf.setDescripcion("No disponible");
				}
			}

			
			mailAttr.put("fechaSolicitud", strFechaSolicitud);

			if (medicoEnTurno != null) {
				mailAttr.put("clinicaAsignada", medicoEnTurno.getUnidadMedicaFamiliar().getDescripcion());
				mailAttr.put("direccionClinica", medicoEnTurno.getUnidadMedicaFamiliar().getDesDireccion());
				mailAttr.put("turno", medicoEnTurno.getTurno().getDescripcion());
				mailAttr.put("consultorio", medicoEnTurno.getConsultorio().getIdConsultorio().toString());
			}
			
			/*
			if (ciudadano != null
					&& StringUtils.isNotBlank(ciudadano.getNombreCompleto())) {
				mailAttr.put("nombreCompleto", ciudadano.getNombreCompleto());
			} else {
				mailAttr.put("nombreCompleto", " ");
			}*/

			if (doctosGenerados.isEmpty()) {
				doctosGenerados = null;
			}

			if(StringUtils.isNotBlank(descripcionTipoTramite)){
				mailAttr.put("descripcionTipoTramite", descripcionTipoTramite);
			} else {
				mailAttr.put("descripcionTipoTramite", " ");
			}

			log.info("Parametros de correo: " + mailAttr);
			
			try {
			emailServiceLocal.enviarCorreoCambioRegistroClinicaByQueue(correo,
					null, descripcionTipoTramite,
					doctosGenerados, mailAttr);
			} catch(Exception e) {
				log.error("No fue posible enviar el mail");
				e.printStackTrace();
			}
		} catch (Exception e) {
			log.error("No fue posible mandar el correo", e);
		}
		
		return new EnvioCorreoResponse();
	}
	
	private TramiteCorreccionDerechohabiente llenarTramiteCambioClinica(GrupoFamiliar asegurado, AsignacionNSS asignacionNSS, TramiteDerechohabientesMovilDto datosTramite, Domicilio domicilio) {
		TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();

		tramite.setIdPersona(asegurado.getDerechohabiente().getIdPersona());

		tramite.setDatosAsegurado(asignacionNSS);
		//Establecemos los datos de la persona a registrar
		tramite.setFisica(asignacionNSS);

		tramite.setUsuario(new Usuario());
		tramite.getUsuario().setUsuario(asignacionNSS.getCurp());

		tramite.setPaso(1L);

		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo());
		tramite.setTipoTramite(tipoTramite);

		//Consultamos el objeto medico en turno
		MedicoEnTurno medico = new MedicoEnTurno(datosTramite.getIdConsultorio());
		tramite.setMedicoEnTurno(medico);
		tramite.setDomicilio(domicilio);
		
		tramite.setDomicilioAnterior(domicilio);
		tramite.setMedicoEnTurnoNuevo(medico);

		return tramite;
	}
	
	private void validaExistenciaAsegurado(TramiteDerechohabientesMovilDto datosTramite, Boolean registro) throws TramiteMovilException{
		if(this.esElAsegurado(datosTramite.getIdParentesco())) {
			Boolean registrado = this.seEncuentraAseguradoRegistrado(datosTramite.getIdAsignacionNSS());
			if(registrado && registro) {
				TramiteMovilException.throwException(ERROR_REGISTRO_ASEGURADO_EXISTENTE, TramiteMovilException.ASEGURADO_REGISTRADO);
			} else if(!registrado && !registro) {
				TramiteMovilException.throwException(ERROR_CAMBIO_ASEGURADO_NO_REGISTRADO, TramiteMovilException.ASEGURADO_NO_REGISTRADO);
			}
		}
	}
	/**
	 * Metodo para verificar si el asegurado o pensionado ya se encuentra registrado
	 * @param idAsignacion
	 * @return
	 * @throws TramiteMovilException
	 */
	private Boolean seEncuentraAseguradoRegistrado(Long idAsignacion) throws TramiteMovilException{
		Boolean registrado = false;
		List<Long> idsParentescos = new ArrayList<Long>();
		idsParentescos.add(ParentescoEnum.ASEGURADO.getId());
		idsParentescos.add(ParentescoEnum.PENSIONADO.getId());
		
		Long numero = null;
		
		try {
			numero = grupoFamiliarServiceLocal.getNumeroDeIntegrantesPorListParentesco(idAsignacion, idsParentescos);
			if(numero.intValue() > 0) {
				registrado = true;
			}
		} catch(Exception e) {
			TramiteMovilException.throwException(ERROR_BUSQUEDA_EXISTENCIA_ASEGURADO, TramiteMovilException.ERROR_DE_SISTEMA);
		}
		return registrado;
	}
	
	private Boolean esElAsegurado(Long idParentesco) {
		if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) ||
				idParentesco.equals(ParentescoEnum.PENSIONADO.getId())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Metodo para insertar la relacion curp correo de ciudadano
	 * en caso de que ya exista no se hara nada
	 * @param curp
	 * @param correo
	 */
	private void verificaroInsertarCorreoCiudadano(String curp, String correo) {
		
		log.debug("Se verificara la relacion de correo " + correo + " con la CURP " + curp);
		CiudadanoCurpCorreo ciudadanoCurp = null;
		log.debug("voy a hacer la llamada a la validacion");
		try {
			ciudadanoCurp = portalCiudadanoServiceBusiness.validaRegistroCurpCorreoCiudadano(curp, correo);
		} catch (PortalCiudadanoException e) {
			e.printStackTrace();
		}
		
		if(ciudadanoCurp== null) {
			log.debug("la relacion " + correo + ", " + curp + " no se encuentra registrada");
			try {
				portalCiudadanoServiceBusiness.validarInicioCurpCorreo(curp,correo, true);
			} catch (PortalCiudadanoException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static class InstanceofPredicate implements Serializable, Predicate {
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("rawtypes")
		private final Class iType;

		@SuppressWarnings({"rawtypes","unused"})
		public static Predicate getInstance(Class type) {
			if (type == null) {
				throw new IllegalArgumentException("The type to check instanceof must not be null");
			}

			return new InstanceofPredicate(type);
		}

		@SuppressWarnings("rawtypes")
		public InstanceofPredicate( Class type) {
			this.iType = type;
		}

		public boolean evaluate(Object object) {
			return this.iType.isInstance(object);
		}
		
		@SuppressWarnings({"rawtypes","unused"})
		public Class getType() {
			return this.iType;
		}

	}
	
	@Override
	public ValidaRequisitosResponse validaRequisitosTramite(String strCurp, String strCorreo, int idTipoTramite){


		TramiteDerechohabientesMovilDto tramite = new TramiteDerechohabientesMovilDto();

		// Validacion del formato de la CURP
		if (StringUtils.isBlank(strCurp) || strCurp.length() != LONGITUD_CURP) {
			//throw new TramiteMovilException("La CURP tiene que ser de " + LONGITUD_CURP + " caracteres", TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
			return new ValidaRequisitosResponse(tramite,CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(),"La CURP tiene que ser de " + LONGITUD_CURP + " caracteres");
		} else if (!strCurp.matches(REGEX_CURP_FISICA)) {
			//throw new TramiteMovilException("La CURP no cumple con el formato requerido", TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
			return new ValidaRequisitosResponse(tramite,CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(),"La CURP no cumple con el formato requerido");
		}

		// Validacion del formato de correo
		if (StringUtils.isBlank(strCorreo)) {
			//throw new TramiteMovilException("El correo no debe ser nulo o vac\u00EDo", TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(),"El correo no debe ser nulo o vac\u00EDo");
		} else if (!strCorreo.matches(EMAIL_PATTERN)) {
			//throw new TramiteMovilException("El correo electronico no cumple con el formato requerido", TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(),"El correo electronico no cumple con el formato requerido");
		}
		if (idTipoTramite != TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().intValue() &&
				idTipoTramite != TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().intValue()){
			//throw new TramiteMovilException("El tipo de tr\u00E1mite no es v\u00E1lido ", TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(),"El tipo de tr\u00E1mite no es v\u00E1lido");
		} 

		Fisica fisicaIMSS = null;
		Fisica fisica = new Fisica();
		fisica.setCurp(strCurp);
		CorreoElectronico correo = new CorreoElectronico();
		correo.setCorreo(strCorreo);
		fisica.setCorreoElectronico(correo);


		GrupoFamiliar aseguradoPensionado = null;
		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;
		AsignacionNSS nss = null;


		try{
			
			log.debug("voy a hacer la llamada a la validacion de correo - curp ["+ strCurp+ "] correo [" +strCorreo + "]" );

			ValidarAsignacionLocalizacionNssWrapper validacionWrapper = new ValidarAsignacionLocalizacionNssWrapper();
			validacionWrapper.setFisica(fisica);
			validacionWrapper.setValidarDifSoloFecNac(true);
			List<AsignacionNSS> listaNSS = null;
			try{
				fisicaIMSS = serviceBusiness.validacionesNSS(fisica, true);
			} catch (PersonaConNSSException e) {
				fisicaIMSS = e.getFisica();
			}

			
			if(fisicaIMSS.getIdPersona() != null){
				listaNSS = grupoFamiliarServiceLocal.getAsignacionNss(fisicaIMSS.getIdPersona());
			}else{
				return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.PERSONA_INCONSISTENTE.getCodigo(),"La persona no se encuentra registrada en el IMSS");
			}
			
		//	List<AsignacionNSS> listaNSS = grupoFamiliarServiceLocal.getAsignacionNss(fisicaIMSS.getIdPersona());
			if (listaNSS != null && listaNSS.size() == 1) {
				nss = listaNSS.get(0);

				cabezaGrupoFamiliar = grupoFamiliarServiceLocal.cabezaGrupoFamiliar(nss.getIdAsignacionNSS());
				if(cabezaGrupoFamiliar.getEsEstudiante()) {
					//throw new TramiteMovilException(ERROR_ESTUDIANTE, TramiteMovilException.NO_CUMPLE_REQUISITOS_TRAMITE);
					return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.NO_CUMPLE_REQUISITOS_TRAMITE.getCodigo(),ERROR_ESTUDIANTE);
				}

				if(cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA.getId()
						|| cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.FALLECIDO.getId()){
					//throw new TramiteMovilException(ERROR_SIN_VIGENCIA,  TramiteMovilException.NO_CUMPLE_REQUISITOS_TRAMITE);
					return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.NO_CUMPLE_REQUISITOS_TRAMITE.getCodigo(),ERROR_SIN_VIGENCIA);
				}


				//obtenemos los datos de vigencias
				aseguradoPensionado = grupoFamiliarServiceLocal.getCabezaGrupaFamilarRegistrada(nss,cabezaGrupoFamiliar);

				//checamos si el tramite es de registro y si ya esta registrado
				if(idTipoTramite == TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().intValue()) {
					if(aseguradoPensionado.getIndRegistrado() == 1) {
						log.debug(ERROR_ASEGURADO_REGISTRADO);
						//throw new TramiteMovilException(ERROR_ASEGURADO_REGISTRADO, TramiteMovilException.NO_CUMPLE_REQUISITOS_TRAMITE);
						return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.NO_CUMPLE_REQUISITOS_TRAMITE.getCodigo(),ERROR_ASEGURADO_REGISTRADO);
					}else{
						List<Modalidad> modalidades = null;
						List <Long> idsModalidades = new ArrayList<Long>();
						if(!cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())){
							try {
								modalidades = grupoFamiliarServiceLocal.getModalidadesActivas(nss.getIdAsignacionNSS());
								if(modalidades != null && !modalidades.isEmpty()) {
									for(Modalidad mod: modalidades) {
										idsModalidades.add(mod.getIdModalidad());
									}
								}

								/**
								 * En caso de que sea el registro de asegurado se valida que la modalidad permita el registro
								 * de lo contrario de le mandara un mensaje de error
								 */

								Map<String,Object> resultado = requisitosMinimosService.tramitePermitidoParaAseguradoPensionado(cabezaGrupoFamiliar, TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue(), false,idsModalidades);
								Boolean correcto = (Boolean) resultado.get("correcto");
								if(!correcto) {
									log.debug(ERROR_MODALIDAD_NO_PERMITE_REGISTRO);
									//throw new TramiteMovilException(ERROR_MODALIDAD_NO_PERMITE_REGISTRO, TramiteMovilException.NO_CUMPLE_REQUISITOS_TRAMITE);
									return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.NO_CUMPLE_REQUISITOS_TRAMITE.getCodigo(),ERROR_MODALIDAD_NO_PERMITE_REGISTRO);
								}

							}catch (Exception e) {
								e.printStackTrace();
								return new ValidaRequisitosResponse(tramite,CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(),e.getMessage());
							}
						}

					}
					//Verificamos la existencia de solicitud
					try{
						Map<String, Object> requisitos = requisitosMinimosService.obtenerSolicitudRegistroAseguradoPensionado(nss, OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
						//verificamos la existencia de solicitudes abiertas para el registro de asegurado o pensionado
						if(requisitos != null && requisitos.get("solicitudActiva") != null) {
							Solicitud solicitudActiva = (Solicitud) requisitos.get("solicitudActiva");
							solicitudBusinessRemote.cancelarSolicitud(solicitudActiva.getSolicitudId(), 5L,null,null, MENSAJE_CANCELACION_SOLICITUD);
						}
					}catch(Exception e){
						log.error("ocurrio un error al querer consultar o cancelar solicitudes activas" , e);
					}

				} else{
					if(aseguradoPensionado.getIndRegistrado() == 0) {
						//si es otro tramite deberia de ser de cambio de clinica y ya deberia estar registrado
						log.debug(ERROR_ASEGURADO_NO_REGISTRADO);
						//throw new TramiteMovilException(ERROR_ASEGURADO_NO_REGISTRADO, TramiteMovilException.NO_CUMPLE_REQUISITOS_TRAMITE);
						return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.NO_CUMPLE_REQUISITOS_TRAMITE.getCodigo(),ERROR_ASEGURADO_NO_REGISTRADO);

					}else{

						//verificamos que el asegurado tenga una UMF anterior
						if(aseguradoPensionado.getMedicoEnTurno() == null ||
								aseguradoPensionado.getMedicoEnTurno().getUnidadMedicaFamiliar() == null ||
								aseguradoPensionado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF() == null) {
							log.debug(ERROR_SIN_UMF_ANTERIOR);
							//throw new TramiteMovilException(ERROR_SIN_UMF_ANTERIOR, TramiteMovilException.NO_CUMPLE_REQUISITOS_TRAMITE);
							return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.NO_CUMPLE_REQUISITOS_TRAMITE.getCodigo(),ERROR_SIN_UMF_ANTERIOR);

						} else {
							// -----------------------------------------------------------------------------
							// No puede realizar cambio de clinica mas de dos veces en el a?o en curso
							// -----------------------------------------------------------------------------
							List<Tramite> tramitesCambiosDeClinica = this.obtenerTramitesCambioClinica(aseguradoPensionado.getDerechohabiente().getIdPersona());
							if ((tramitesCambiosDeClinica != null) && (tramitesCambiosDeClinica.size() >= 1)) {
								log.debug(ERROR_ASEGURADO_SIN_CAMBIOS_CLINICA);
								//throw new TramiteMovilException(ERROR_ASEGURADO_SIN_CAMBIOS_CLINICA, TramiteMovilException.NO_CUMPLE_REQUISITOS_TRAMITE);
								return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.NO_CUMPLE_REQUISITOS_TRAMITE.getCodigo(),ERROR_ASEGURADO_SIN_CAMBIOS_CLINICA);

							}

							try{
								Map<String, Object> requisitos = requisitosMinimosService.obtenerSolicitudCambioClinica(nss, OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
								//verificamos la existencia de solicitudes abiertas para el registro de asegurado o pensionado
								if(requisitos != null && requisitos.get("solicitudActiva") != null) {
									Solicitud solicitudActiva = (Solicitud) requisitos.get("solicitudActiva");
									solicitudBusinessRemote.cancelarSolicitud(solicitudActiva.getSolicitudId(), 5L,null, null,MENSAJE_CANCELACION_SOLICITUD);
								}
							}catch(Exception e){
								log.error("ocurrio un error al querer consultar o cancelar solicitudes activas" , e);
							}

						}
					}
				}

				tramite.setIdAsignacionNSS(nss.getIdAsignacionNSS());
				tramite.setIdPersona(nss.getIdPersona());
				tramite.setIdParentesco(cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco());

			} else if(listaNSS == null || listaNSS.isEmpty()){
				log.debug(ERROR_SIN_NSS);
				//throw new TramiteMovilException(ERROR_SIN_NSS, TramiteMovilException.NO_CUMPLE_REQUISITOS_TRAMITE);
				return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.NO_CUMPLE_REQUISITOS_TRAMITE.getCodigo(),ERROR_SIN_NSS);
			}else if (listaNSS != null && listaNSS.size() > 1) {

				log.debug(ERROR_SIN_NSS);
				//throw new TramiteMovilException(ERROR_SIN_NSS, TramiteMovilException.PERSONA_INCONSISTENTE);
				return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.NO_CUMPLE_REQUISITOS_TRAMITE.getCodigo(),ERROR_SIN_NSS);
			}
			return  new ValidaRequisitosResponse(tramite);

		}catch (PortalCiudadanoException e){
			this.log.error(e);
			//throw new TramiteMovilException(e.getMessage(), TramiteMovilException.CURP_CORREO_INVALIDO );
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.CURP_CORREO_INVALIDO.getCodigo(),e.getMessage());
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			this.log.error(e);
			//throw new TramiteMovilException(e.getMessage(), TramiteMovilException.DATOS_INCONSISTENTES_RENAPO);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.DATOS_INCONSISTENTES_RENAPO.getCodigo(),e.getMessage());

		} catch (ClienteWebserviceRenapoCurpException e) {
			this.log.error(e);
			//throw new TramiteMovilException(e.getMessage(), TramiteMovilException.ERROR_CONSULTA_RENAPO);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.ERROR_CONSULTA_RENAPO.getCodigo(),e.getMessage());

		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			this.log.error(e);
			//throw new TramiteMovilException(e.getMessage() , TramiteMovilException.PERSONA_INCONSISTENTE);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.PERSONA_INCONSISTENTE.getCodigo(),e.getMessage());

		} catch (ErrorComparacionDatosRENAPOException e) {
			this.log.error(e);
			//throw new TramiteMovilException(e.getMessage() , TramiteMovilException.DATOS_INCONSISTENTES_RENAPO );
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.DATOS_INCONSISTENTES_RENAPO.getCodigo(),e.getMessage());

		} catch ( GenerarNSSException e){
			this.log.error(e);
			//throw new TramiteMovilException(e.getMessage(), TramiteMovilException.PERSONA_INCONSISTENTE);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.PERSONA_INCONSISTENTE.getCodigo(), MENSAJE_GENERICO_ACCESO_APPS_MOVILES);

		}catch (AsignacionNssPersonaException e){
			this.log.error(e);
			//throw new TramiteMovilException(e.getMessage(), TramiteMovilException.PERSONA_INCONSISTENTE);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.PERSONA_INCONSISTENTE.getCodigo(),MENSAJE_GENERICO_ACCESO_APPS_MOVILES);

		}catch (AsignacionNSSNoLocalizadoException e){
			this.log.error(e);
			//throw new TramiteMovilException(e.getMessage(), TramiteMovilException.PERSONA_INCONSISTENTE);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.PERSONA_INCONSISTENTE.getCodigo(),MENSAJE_GENERICO_ACCESO_APPS_MOVILES);

		} catch (DerechohabientesBusinessException e) {
			log.error("ocurrio un error de consultas de derechohabiente " ,e );
			//throw new TramiteMovilException(ERROR_CONSULTA_CABEZA,  TramiteMovilException.ERROR_DE_SISTEMA);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(),ERROR_CONSULTA_CABEZA);

		} catch(NullPointerException e){
			log.error("ocurrio un nullpointer exception " ,e );
			//throw new TramiteMovilException( e.getMessage(),  TramiteMovilException.ERROR_DE_SISTEMA);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(),"Ocurrio un error en el sistema.");
		} catch (Exception e) {
			log.error("ocurrio un error no tipificado " ,e );
			//throw new TramiteMovilException( e.getMessage(),  TramiteMovilException.ERROR_DE_SISTEMA);
			return new ValidaRequisitosResponse(null,CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(),"Ocurrio un error en el sistema.");
		}


	}

	private List<Tramite> obtenerTramitesCambioClinica(Long idPersona){

		try{
			// ---------------------------------------------
			// Inicio dia hace medio año
			// ---------------------------------------------
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -6);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			Long[] origenes = {OrigenSolicitudEnum.PORTAL_CIUDADANO.getId(),OrigenSolicitudEnum.MOVILES.getId()};

			return this.solicitudTramiteBusiness.obtenerTramitesCerrados(
					Arrays.asList(origenes), TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE.getValor(), idPersona, cal.getTime(), TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * Metodo para validar que los tipos de tramite dentro de la solicitud sea de registro o de cambio de clinica
	 * @param solicitud
	 * @throws TramiteMovilException
	 */
	private void validaTramitesMovilesValidos(Solicitud solicitud) throws TramiteMovilException{
		List<Integer> tiposTramiteValidos = new ArrayList<Integer>();
		tiposTramiteValidos.add(TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo());
		tiposTramiteValidos.add(TipoTramiteEnum.REGISTRO_PENSIONADO.getCodigo());
		tiposTramiteValidos.add(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo());
		
		//Verificamos si al menos uno de los tramites es valido
		for(Tramite tramite: solicitud.getTramites()) {
			if(tiposTramiteValidos.contains(tramite.getTipoTramite().getIdTipoTramite())) {
				return;
			}
		}
		
		
		TramiteMovilException.throwException("La solicitud con folio " + solicitud.getNoFolioSolicitud() + " no contiene un tramite válido", TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
	}

}