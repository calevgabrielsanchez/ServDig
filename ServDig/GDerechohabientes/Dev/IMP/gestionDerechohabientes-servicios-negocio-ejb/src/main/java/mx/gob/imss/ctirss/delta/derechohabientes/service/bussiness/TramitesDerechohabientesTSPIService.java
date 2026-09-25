package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitesDerechohabientesTSPIRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.TramiteDerechoabienteTSPIException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.exception.movil.TramiteMovilException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.VarianteRegistroEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;


@Stateless(name = "tramitesDerechohabientesTSPIService", mappedName = "tramitesDerechohabientesTSPIService")
public class TramitesDerechohabientesTSPIService  extends AbstractServiceBusiness 
		implements TramitesDerechohabientesTSPIRemote{
	
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
	@EJB
	private ProrrogaServiceLocal prorrogaServiceLocal;
	
	
	

	//valores de los nombres de los atributos para los mapas
	private static final String KEY_ESTADO_VALIDACION = "correcto";
	private static final String KEY_MENSAJE_VALIDACION = "mensaje";
	private static final String KEY_ESTADO_REQUISITOS = "correcto";
	private static final String PERSONA_LOCALIZADA = "personaLocalizada";

 	

	private static final String  ERROR_VIGENCIA ="El asegurado no cuenta con una situacon de vigencia valida para realizar el registro  de beneficiarios";
	private static final String ERROR_DATOS_INCOMPLETOS = "Los datos requeridos para el tramite de registro estan incompletos";
	
	private static final String NSS_NO_ENCONTRADO = "No se localizo el nss con el id proporcionado";
	private static final String ERROR_BUSQUEDA_NSS = "Ocurrio un error al buscar el nss";
	private static final String ERROR_BUSQUEDA_CABEZA = "Ocurrio un error al consultar la cabeza de grupo familiar";
	private static final String ERROR_GUARDADO_SOLICITUD_REGISTRO = "Ocurrio un error al guardar la solicitud de registro";
	private static final String ERROR_WS = "Ocurrio un error al calcular la vigencia";
	private static final String ERROR_BUSQUEDA_SOLICITUD = "No fue posible localizar la solicitud";
	private static final String ERROR_BUSQUEDA_INTEGRANTE = "Ocurrio un error al buscar al derechohabiente";
	private static final String ERROR_BUSQUEDA_EXISTENCIA_ASEGURADO = "Ocurrio en error al verficar si el asegurado/pensionado se encontraba asignado a una clinica";
	private static final String ERROR_CAMBIO_ASEGURADO_NO_REGISTRADO = "El nss no se encuentra registrado en una umf";
	private static final String ERROR_FINALIZA_REGISTRO_DH ="Ocurrio un error al finalizar la solicitud de registro";
	
	private static final String ERROR_CONSULTA_VIGENCIA_ASEGURADO_PATRON_NULO_BDTU = "Los datos afiliatorios del asegurado presentan inconsistencias, favor de utilizar el servicio digital "
				+"de Corrección de Datos del Asegurado para actualizar su información o acudir a la Subdelegación más cercana a su domicilio.";
	
	

	
	
	
	/**
	 * Metodo encargado de hacer las validaciones de prerrequisitos antes de iniciar un tramite de regisro de beneficiarios
	 * @param idAsignacionNSS
	 * @return
	 * @throws TramiteDerechoabienteTSPIException
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	@Override
	public CabezaGrupoFamiliar validaPrerrequisitosAseguradoRegistro(Long idAsignacionNSS) throws TramiteDerechoabienteTSPIException, 
						DerechohabientesBusinessException, Exception{
		
		
		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;
		GrupoFamiliar asegurado = null;
		try{
			
			
			cabezaGrupoFamiliar = grupoFamiliarServiceLocal.cabezaGrupoFamiliar(idAsignacionNSS);
			asegurado = grupoFamiliarServiceLocal.getCabezaGrupoFamiliar(idAsignacionNSS);
			
			
			if(asegurado== null){
				throw new TramiteDerechoabienteTSPIException("RNGD0026", TramiteDerechoabienteTSPIException.RNGD0026);
			}
			
			//validaciones de vigencia para el asegurado
			if(cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() != EstadoDerechohabienteEnum.BAJA.getId()
				&& cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() != EstadoDerechohabienteEnum.FALLECIDO.getId()
				&& cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() != EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId()){
				throw new TramiteDerechoabienteTSPIException(ERROR_VIGENCIA, new Integer(1));
			}
			
			
			
		}catch(DerechohabientesBusinessException e){
			log.debug("Ocurrio un error al validar los prerrequisitos para registro erro de DH" , e);
			throw (e);
		}	
		catch(Exception e){
			log.debug("Ocurrio un error inesperado los prerrequisitos para registro" , e);
			throw (e);
		}
		return cabezaGrupoFamiliar;
		
	}
	
	/**
	 * Valida si ya existe la persona registrada en el grupo familiar
	 * @param fisica
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws TramiteDerechoabienteTSPIException
	 */
	@Override
	public Fisica validaPersonaRegistradaEnGrupoFamiliar(Fisica fisica, Long idAsignacionNss) throws DerechohabientesBusinessException, 
											TramiteDerechoabienteTSPIException{
		
		
		if (fisica.getLugarNacimiento() == null ||
			 StringUtils.isBlank(fisica.getLugarNacimiento().getClave())) {
			log.debug("LA PERSONA ENVIADA NO TRAE LUGAR DE NACIMIENTO ARROJARA UNA EXCEPCION");
		}

		Map<String, Object>  respuesta =  requisitosMinimosService.validaPersonaRegistrada(fisica, idAsignacionNss);
		if(!(Boolean) respuesta.get(KEY_ESTADO_REQUISITOS)){
			throw new TramiteDerechoabienteTSPIException(TramiteDerechoabienteTSPIException.ERROR_PERSONA_REGISTRADO_GRUPO_FAMILAIR, new Integer(1));
		}
		return  (Fisica) respuesta.get(PERSONA_LOCALIZADA);
	}
		
	/**
	 * Genera un maoa con las variantes que puede tener el registro de beneficiarios
	 * @param idParentesco
	 * @param idOrigen
	 * @return
	 */
	@Override
	public Set<VarianteRegistroEnum> getVarianteRegistro(int idParentesco, int idOrigen){
		Set<VarianteRegistroEnum> varianteRegistro = new HashSet<VarianteRegistroEnum>();
		if(idOrigen == OrigenSolicitudEnum.INTERNET.getId().intValue()){
			varianteRegistro.add(VarianteRegistroEnum.NORMAL);
		}else if(idParentesco == ParentescoEnum.PADRES.getId() ||
				idParentesco == ParentescoEnum.HIJOS.getId()){
			varianteRegistro.add(VarianteRegistroEnum.NORMAL);
			varianteRegistro.add(VarianteRegistroEnum.ADOPCION);
			varianteRegistro.add(VarianteRegistroEnum.RECONOCIMIENTO);
		}else{
			varianteRegistro.add(VarianteRegistroEnum.NORMAL);
			
		}
		
		return varianteRegistro;
		
	}
	
	public void validaRequisitosRegistroDerechohabiente(TramiteRegistroDerechohabiente tramiteRegistro,
			CabezaGrupoFamiliar cabeza, Long idOrigenSolicitud, Long idUmfUsuario) throws DerechohabientesBusinessException, 
																	TramiteDerechoabienteTSPIException{
		
		this.validaDatosObligatorios(tramiteRegistro);
		/**
		try{
			
			
		}catch Exception(e){
			log.error("ocurrio un erro al validar los requisitos", e);
			
		}
		**/
		
	}
	
	/**
	 * Metodo encargado de guardar y finalizar el registro de un derechohabiente identifica el origen para saber en que estatus
	 * dejar la solicitud, realiza las validaciones de negocio respectivas para el registro de beneficiarios
	 * @param tramiteRegistro
	 * @param cabeza
	 * @param idOrigenSolicitud
	 * @param idUmfUsuario
	 * @throws DerechohabientesBusinessException
	 * @throws TramiteDerechoabienteTSPIException
	 */
	@Override
	public Solicitud  guardaSolicitudRegisroDerechohabiente(TramiteRegistroDerechohabiente tramiteRegistro,
	CabezaGrupoFamiliar cabeza, Long idOrigenSolicitud)  throws DerechohabientesBusinessException, 
	TramiteDerechoabienteTSPIException{	
	
	Map<String, Object> validaciones = null;
	Solicitud solicitud = null;
	
	this.validaDatosObligatorios(tramiteRegistro);
	
	
			try {
				validaciones =	requisitosMinimosService.requisitosMinimosRegistro(tramiteRegistro, cabeza, idOrigenSolicitud, null, false, null);
				
				if(!this.getEstadoValidaciones(validaciones)){
					throw new TramiteDerechoabienteTSPIException((String)validaciones.get(KEY_MENSAJE_VALIDACION));
				}
				
				tramiteRegistro.setDomicilio(domicilioServiceBusinessRemote.complementarLocalidadDomicilioRecortado(tramiteRegistro.getDomicilio()));
				//se setea el CURP del usuario en el ID Usuario para homologar con los servicios de acceder
				if(tramiteRegistro.getUsuario() != null) {
					tramiteRegistro.getUsuario().setCveIdUsuario(tramiteRegistro.getUsuario().getUsuario());
				}
				solicitud = registroDerechohabienteServiceLocal.registraSolicitud(tramiteRegistro, idOrigenSolicitud);
				if(idOrigenSolicitud.longValue() == OrigenSolicitudEnum.VENTANILLA_TSPI.getId().longValue()){
					solicitud = registroDerechohabienteServiceLocal.finalizarSolicitudRegistroTSPI(solicitud, cabeza);
				}
				
			} catch (DerechohabientesBusinessException e) {
				log.error("Ocurrio un error al crear la solicitud de registro", e);
				throw new TramiteDerechoabienteTSPIException(ERROR_GUARDADO_SOLICITUD_REGISTRO);
			} catch (SolicitudNoValidaException e) {
				log.error("Ocurrio un error al crear la solicitud de registro", e);
				throw new TramiteDerechoabienteTSPIException(ERROR_GUARDADO_SOLICITUD_REGISTRO);
			} catch (DomicilioNoValidoException e) {
				log.error("Ocurrio un ileegal argument exception domicilio" , e);
				TramiteDerechoabienteTSPIException.throwException(TramiteDerechoabienteTSPIException.ERROR_DATOS_DOMICILIO_INCOMPLETO);
			} catch (DomicilioNoLocalizadoException e) {
				log.error("No se encontro la localizad", e);
				TramiteDerechoabienteTSPIException.throwException(TramiteDerechoabienteTSPIException.ERROR_DATOS_DOMICILIO_INCOMPLETO);
			} catch (SolicitudNoEncontradaException e) {
				log.error("No se encontro la solicitud previamente registrada", e);
				TramiteDerechoabienteTSPIException.throwException(e.getMessage());
			} catch (SolicitudException e) {
				log.error("Error en el componente de solicitud service", e);
				TramiteDerechoabienteTSPIException.throwException(e.getMessage());
			} catch (ImpactaAlmacenesWSException e) {
				log.error("Error al querer guardar el almacenes el registro", e);
				TramiteDerechoabienteTSPIException.throwException(e.getMessage());
			}catch (NullPointerException e) {
				log.error("Error de nullpointer que se pide se cache y se cambie el mesaje" , e);
				TramiteDerechoabienteTSPIException.throwException(e.getMessage());
			
			} catch (Exception e) {
				log.error("Error no identificado al generar el registro de la solicitud", e);
				TramiteDerechoabienteTSPIException.throwException(ERROR_CONSULTA_VIGENCIA_ASEGURADO_PATRON_NULO_BDTU);
			}
		
		log.debug("finalizo el registro y finalizacion de la solicitud con exito");
		
		return solicitud;
		
	}
	
	/**Metodo encargado de finalizar un registro de derechohabientes por TSPI por internet
	 * valida los requisitos minimos para dicho tramite
	 * @param folioSolicitud
	 * @return
	 */
	@Override
	public Solicitud finalizaSolicitudDerechohabientesTSPIInternet(String folioSolicitud)
			throws SolicitudNoEncontradaException, IllegalArgumentException, TramiteDerechoabienteTSPIException{
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioSolicitud);
		if(org.apache.commons.lang.StringUtils.isEmpty(folioSolicitud))
			throw new IllegalArgumentException("Folio vvacio o nulo");
		
		try {
			solicitud = solicitudBusinessRemote.consultarFolio(solicitud);
			if(solicitud == null)
				throw new SolicitudNoEncontradaException("No fue posible locaizar la solicitud con folio " + folioSolicitud);
			
			if((solicitud.getTipoSolicitud().getIdTipoSolicitud().longValue() != TipoSolicitudEnum.REGISTRO_DE_DERECHOHABIENTES.getId() &&
					solicitud.getTipoSolicitud().getIdTipoSolicitud().longValue() != TipoSolicitudEnum.PRORROGA.getId())
					|| solicitud.getOrigenSolicitud().getIdOrigenSolicitud() != OrigenSolicitudEnum.INTERNET_TSPI.getId().longValue()){
				TramiteDerechoabienteTSPIException.throwException("La solicitud no probiene de TSPI");
			}
			
			if(solicitud.getTipoSolicitud().getIdTipoSolicitud().longValue() == TipoSolicitudEnum.REGISTRO_DE_DERECHOHABIENTES.getId()){
				solicitud = registroDerechohabienteServiceLocal.finalizarSolicitudRegistroTSPI(solicitud, null);	
			}else if(solicitud.getTipoSolicitud().getIdTipoSolicitud().longValue() != TipoSolicitudEnum.PRORROGA.getId()){
				
				TramiteProrroga tramiteProrroga = null;
				//Verificamos que la solicitud contenga tramites de tipo prorroga
				for(Tramite tramite: solicitud.getTramites()) {
					if(tramite instanceof TramiteProrroga) {
						tramiteProrroga = (TramiteProrroga) tramite;
						break;
					}
				}
				
				GrupoFamiliar grupoFamiliar = tramiteProrroga.getGrupoFamiliar();
				String mensajeValidacion = prorrogaServiceLocal.validaIntegrantePrrogaEstudiosTSPI(grupoFamiliar, grupoFamiliar.getAsignacionNSS().getCveIdAsignacionNSS());
				if(!StringUtils.isEmpty(mensajeValidacion)){
					throw new TramiteDerechoabienteTSPIException(mensajeValidacion);
				}
			
				
				solicitud = prorrogaServiceLocal.finalizarSolicitudProrroga(solicitud);
			}
			
		} catch (SolicitudNoEncontradaException e) {
			solicitud = null;
			log.error("No fue posible locaizar la solicitud con folio " + folioSolicitud);
			throw e;
		} catch (DerechohabientesBusinessException e) {
			log.error("Error en el componente de solicitud service excepion de derechohabientes", e);
			TramiteDerechoabienteTSPIException.throwException(e.getMessage());
		}catch (SolicitudException e) {
			log.error("Error en el componente de solicitud service", e);
			TramiteDerechoabienteTSPIException.throwException(e.getMessage());
		} catch (ImpactaAlmacenesWSException e) {
			log.error("Error al querer guardar el almacenes el registro", e);
			TramiteDerechoabienteTSPIException.throwException(e.getMessage());
		} catch (Exception e) {
			log.error("Error no identificado al generar el registro de la solicitud", e);
			TramiteDerechoabienteTSPIException.throwException(e.getMessage());
		}
		return solicitud;
	}
	
	/**
	 * Metodo encargado de buscar una persona por CUPR en RENAPO y BDTU aplica las validacion y reglas de negocio para la busqueda de personas para asignaion por iternet
	 * cachando las exepciones de negocio y las de servicios externos
	 * @param curp
	 * @return Fisica con la informacion de la persona localizada en BDTU o RENAPO
	 * @throws IllegalArgumentException
	 * @throws TramiteDerechoabienteTSPIException
	 * @throws DerechohabientesBusinessException
	 * @throws ClienteWebserviceRenapoCurpException
	 */
	@Override
	public Fisica busquedaFisicaPorCuprTramiteRegistroTCPI(String curp)
			throws IllegalArgumentException, TramiteDerechoabienteTSPIException, DerechohabientesBusinessException, ClienteWebserviceRenapoCurpException{
		
		Fisica fisica = new Fisica();
		if(StringUtils.isEmpty(curp))
			throw new IllegalArgumentException("EL CURP no puede ser nullo");
		
		fisica.setCurp(curp);
		try {
		 fisica = this.serviceBusiness.validacionesNSSIncluyeCL3(fisica, false);
		} catch (PersonaConNSSException e) {
			log.debug("se encontro la fiscia de la excepcion  " + fisica.toString());
			fisica = e.getFisica();
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			TramiteDerechoabienteTSPIException.throwException("La CURP ingresada no se localizo en RENAPO");
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error("error en el ws de renapo ", e);
			throw e;
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			TramiteDerechoabienteTSPIException.throwException(e.getMessage());
		} catch (GenerarNSSException e) {
			TramiteDerechoabienteTSPIException.throwException("La persona localizada en el instituto tiene inconsistencias en su informacion [" +e.getMessage()+"]");
		} catch (ErrorComparacionDatosRENAPOException e) {
			TramiteDerechoabienteTSPIException.throwException("La persona localizada en el instituto tiene diferencias con la entidad externa RENAPO");
		}catch (DomicilioNoLocalizadoException e) {
			DerechohabientesBusinessException.throwException("Ocurrio un error no esperado al querer recuperar la informacion del domicilio de la persona localizada [" +curp+ "] error ["+ e.getMessage()+"]");
		} catch (UmfNoLocalizadaException e) {
			DerechohabientesBusinessException.throwException("Ocurrio un error no esperado al querer recuperar la informacion de la UMF de la persona localizada [" +curp+ "] error ["+ e.getMessage()+"]");
		}
		return fisica;
	}
	
	/**
	 * Metodo encargado de validar los requisitos para la prorroga y su gardado en base de datos cuando el origen es INTERNET TSPI 
	 * solo registra el tramite sin finalizarlo
	 * @param tramiteProrroga
	 * @param usuario
	 * @param idOrigenSolicitud
	 * @return
	 * @throws TramiteDerechoabienteTSPIException
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud guardaProrrogaTSPI(TramiteProrroga tramiteProrroga,
				Usuario usuario, Long idOrigenSolicitud) throws TramiteDerechoabienteTSPIException, DerechohabientesBusinessException{
		
		GrupoFamiliar grupoFamiliar = tramiteProrroga.getGrupoFamiliar();
		Long idAsignacionNSS = tramiteProrroga.getIdAsignacionNSS();
		
		try {
			
			if(tramiteProrroga.getTipoTramite().getIdTipoTramite().longValue() == TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo().longValue()){
				String mensajeValidacion = prorrogaServiceLocal.validaIntegrantePrrogaEstudiosTSPI(grupoFamiliar, idAsignacionNSS);
				if(!StringUtils.isEmpty(mensajeValidacion)){
					throw new TramiteDerechoabienteTSPIException(mensajeValidacion);
				}
				ConstanciaEstudio constancia = new ConstanciaEstudio();
				constancia.setFechaExpedicion(new Date());
				constancia.setFechaInicioPeriodo(tramiteProrroga.getFechaInicioProrroga());
				constancia.setFechaFinPeriodo(tramiteProrroga.getFechaFinProrroga());
				constancia.setObservaciones(tramiteProrroga.getObservaciones());
				return prorrogaServiceLocal.saveProrrogaEstudios(constancia, grupoFamiliar, usuario, idOrigenSolicitud);

			}else if(tramiteProrroga.getTipoTramite().getIdTipoTramite().longValue() == TipoTramiteEnum.PRORROGA_ENFERMEDAD.getCodigo().longValue()){
				
				DictamenIntegranteIncapacitado dictamen = new DictamenIntegranteIncapacitado();
				dictamen.setProrroga(tramiteProrroga);
				dictamen.setFechaExpedicion(new Date());
				return prorrogaServiceLocal.saveProrrogaEnfermedad(dictamen, grupoFamiliar, usuario, idOrigenSolicitud);
				
			}else{
				throw new TramiteDerechoabienteTSPIException("No se identifico el tipo de prorroga como valido");
			}
				
			
		} catch (DerechohabientesBusinessException e) {
			log.error("ocurrio un error al querer registrar la prorroga" , e);
			throw e;
		} catch (Exception e) {
			log.error("ocurrio un error desconocido al querer registrar la prorroga" , e);
			throw new DerechohabientesBusinessException(e.getMessage());
		}
		
	}
	
	
	
	/**
	 * Metodo encargado de validar los elementos minimos para el registro de derechohabientes en caso de falta algun dato arroja una exepecion
	 * @param tramiteRegistro
	 * @return boolean true en caso de que los datos sean correctos
	 * @throws TramiteDerechoabienteTSPIException
	 */
	private boolean validaDatosObligatorios(TramiteRegistroDerechohabiente tramiteRegistro) throws TramiteDerechoabienteTSPIException{
		boolean isRegistroValido = false;
		Long idAsignacionNSS  = tramiteRegistro.getDatosAsegurado().getCveIdAsignacionNSS();
		Fisica integrante =  tramiteRegistro.getFisica();
		Long idParentesco = tramiteRegistro.getParentesco().getIdParentesco();
		Long idConsultorio = tramiteRegistro.getMedicoEnTurno().getIdMedicoContultorioTurno();
		Domicilio dom = tramiteRegistro.getDomicilio();
		Long idRazonRegistro = tramiteRegistro.getRazonRegistro().getIdRazonRegistro();
		Long idVarianteRegistro = tramiteRegistro.getVarianteRegistro().longValue();
		
		
		if(idAsignacionNSS == null || integrante == null ||
				idParentesco == null || idConsultorio == null
				|| dom == null||  idRazonRegistro == null || idVarianteRegistro ==null) {
			throw new TramiteDerechoabienteTSPIException(ERROR_DATOS_INCOMPLETOS, TramiteMovilException.DATOS_ENTRADA_INVALIDOS);
		}
		
		return isRegistroValido;
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
	
	
	
	
}



