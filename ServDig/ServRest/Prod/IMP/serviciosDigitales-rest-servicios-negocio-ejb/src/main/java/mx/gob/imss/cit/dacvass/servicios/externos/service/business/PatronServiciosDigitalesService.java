package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes.PatronVigenteVO;
import mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes.RespuestaPatronesVigentes;
import mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes.WSPatronesVigentesService;
import mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto.ConsultaPatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion.ActividadEcononica;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion.ActualizacionClasificaionPatronalBdtuSindoDto;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosEmpresaPermisoConvid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatronPermisoCovid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatronQuery;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosSatDetallePatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DetallePatronClasifMovPat;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DetallePatronClasifMovPatQuery;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.MovimientoRegistroPatronal;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.NumTrabajadoresVigentes;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Identificacion;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.RespuestaWSSat;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.PatronPlataformaResponse;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.patron.IPatronServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaServiciosExternosServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IPatronServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ParserPatronServiciosToRest;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.cit.semanascotizadas.common.model.DatosHuelga;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoMovtoPatSujObligEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

@Stateless(name = "patronServiciosDigitalesService", mappedName = "patronServiciosDigitalesService")
public class PatronServiciosDigitalesService extends AbstractServiceBusiness implements IPatronServiciosDigitalesServiceRemote{

	private static final Logger log = LoggerFactory
			.getLogger(PatronServiciosDigitalesService.class);

	@EJB(mappedName="sujetoObligadoServiceBusiness")
	SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	
	
	@EJB(mappedName = "movimientoPatronalBusiness")
	MovimientoPatronalBusinessRemote  movimientoPatronalBusiness;

	@EJB(mappedName = "solicitudBusiness")	
	private SolicitudBusinessRemote solicitudBusiness;

	@EJB(mappedName = "clasificacionActividadEconomicaServiceBusiness")
	private ActividadEcServiceRemote clasificacionActividadEconomicaBusiness;

	@EJB(mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;

	@EJB
	private IPatronServiceEntityLocal patronServiceEntity;

	@EJB(mappedName = "registroPatronalServiceBusiness")
	private RegistroPatronalServiceBusinessRemote registroPatronalServiceBusinessRemote;
	
	@EJB(mappedName = "consultaServiciosExternosService")
	private IConsultaServiciosExternosServiceRemote consultaServiciosExternosService;



	private static final Integer APLICACION_MOV_SINDO_CLASIFICACION = Integer.valueOf(1);

	public static final String PERIODO_ABIERTO_HUELGA = "0001-01-01";

	/**
	 * Metodo encargado de recuperar la infomración detallada de un patron a traves de su NRP
	 * @param nrp String con el NRP de 10 a 11 posiciónes
	 * @return
	 * @throws ServiciosRestException
	 */

	@Override
	public SujetoObligado consultaDetallePatronSujetoObligadoByRP(String nrp) throws ServiciosRestException {
		nrp =ValidacionesComunesUtil.validaEstructuraNRP(nrp);
		SujetoObligado sujeto = new SujetoObligado();
		try {
			log.debug("pase la validacion y el patron queda ");
			sujeto.setNumeroRegistroPatronal(nrp.substring(0,10));
			//return sujetoObligadoServiceBusiness.consultarPorNumeroRegistroPatronal(nrp);

			SujetoObligado sujetoRespuesta = sujetoObligadoServiceBusiness.obtenerDetalleSujetoObligadoActividadEconomica(sujeto);
			log.debug("pase la llamada del patron en servicio");
			if(sujetoRespuesta == null) {
				log.debug("patron nulo");
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
						"No se encontro información relacionada con el NRP", "No se encontro información relacionada con el NRP"));
			}
			ParserPatronServiciosToRest.toNullInfoPatronServiciosRestFolioPAC(sujetoRespuesta);
			return sujetoRespuesta;

		}catch  (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("Ocurrio un erro al realizar la busqueda de patron ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado en la consulta de patron", e.getMessage()));
		}


	}

	/**
	 * Metodo encargado de recuperar la infomración detallada de un patron a traves de su NRP
	 * @param nrp String con el NRP de 10 a 11 posiciónes
	 * @return
	 * @throws ServiciosRestException
	 */

	@Override
	public SujetoObligado consultaDetallePatronSujetoObligadoByRPPMC(String nrp) throws ServiciosRestException {
		nrp = ValidacionesComunesUtil.validaEstructuraNRP(nrp);
		try {
			return patronServiceEntity.obtenerPatronPMC(nrp.substring(0,10));
		} catch (Exception e) {
			log.error("Ocurrio un erro al realizar la busqueda de patron ", e);
			throw new ServiciosRestException(
					new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consulta de patron", e.getMessage()));
		}

	}

	/**
	 * Metodo encargado de encolar el movimiento que se va a SINDO por servicios digitales
	 * @param movimiento MovimientoPatronalType
	 * @throws ServiciosRestException
	 */
	@Override
	public void encolaMomvimientoModificacionPatronalSINDO(MovimientoPatronalType movimiento)
			throws ServiciosRestException {

		log.debug("llegue a la llamada del patron en servicio encola movimiento");
		try {
			movimientoPatronalBusiness.enviarModificacionPatronal(movimiento);
		}catch(Exception e) {
			log.error("ocurrio un error al mandar a encolar el movimiento " , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado en encolar el movimiento patronal en SINDO " , e.getMessage()));
		}

	}

	/**
	 * Metodo encargado de guardar el cambio de clasificación en bdtu y generar el movimiento para SINDO
	 * @param movimientoCalsificacion
	 * @throws ServiciosRestException
	 */
	@Override
	public String actualizarClasidifacionFuentesBdtuSINDO(
			ActualizacionClasificaionPatronalBdtuSindoDto movimientoCalsificacion) throws ServiciosRestException{
		Solicitud solicitud = null;

		log.debug("llegue a la llamada del patron en servicio actualizarClasidifacionFuentesBdtuSINDO");
		if(movimientoCalsificacion.getCveCausa() == null ||  movimientoCalsificacion.getCveFolioPac() == null ||
				movimientoCalsificacion.getCveUsuario() == null || movimientoCalsificacion.getSujetoObligado() == null||
				movimientoCalsificacion.getFecSurteEfecto() == null) {
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"No puede ser niguno de los atributos nulo", "No puede ser niguno de los atributos nulo"));
		}
		SujetoObligado patron = movimientoCalsificacion.getSujetoObligado();
		//seccion de guardado de la solicitud, tramite y detalle tramite

		try {
			patron.getClasificacion().setSujetoObligado(patron);
			clasificacionActividadEconomicaBusiness.actualizarClasificacion(patron.getClasificacion(),
					movimientoCalsificacion.getCveCausa(), TipoMovtoPatSujObligEnum.CAMBIO_DE_CLASIFICACION_PATRONAL.getId());
			log.debug("pase la actualización de la Clasificacion ");
		}catch(GestionPatronalBusinessException e) {
			log.error("ocurrio un error en la validacion de los atribuos para realizar la actualizacion" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en la validacion de los datos para realizar la actualizacion de la  clasificacion por folios PAC" , e.getMessage()));
		}catch(Exception e) {
			log.error("ocurrio un error al  actualizar la clasificacion por folios PAC no cachado" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado actuaalizar la  clasificacion por folios PAC" , e.getMessage()));
		}
		try {
			//TODO definir el origen de la solicitud
			patron.getClasificacion().setSujetoObligado(null);
			solicitud = setSolicitudActualizacionClasificacion(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION, 
					movimientoCalsificacion.getCveIdSubdelegacionUsuario(), EstadoSolicitudEnum.ATENDIDA, 
					movimientoCalsificacion.getCveUsuario(), movimientoCalsificacion.getCveFolioPac(), OrigenSolicitudEnum.VENTANILLA);
			TramiteSujetoObligado tramite = setTramiteActualizacionClasificacion(TipoTramiteEnum.CLASIFICACION_ANEXO_V, EstadoTramiteEnum.CERRADO, 
					patron, movimientoCalsificacion.getFecSurteEfecto());
			List<Tramite> lstTramites = new ArrayList<Tramite>(); 
			lstTramites.add(tramite);
			solicitud.setTramites(lstTramites);
			solicitud = solicitudBusiness.crear(solicitud);
			log.debug("pase la creación de la solicitud");
		}catch(Exception e) {
			log.error("ocurrio un error al  guardar la solicitud de cambio de clasificacion por folios PAC" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado al  guardar la solicitud de cambio de clasificacion por folios PAC" , e.getMessage()));
		}

		try {
			String folio = buildNumeroFolio(patron.getSubdelegacion().getClave());
			clasificacionActividadEconomicaBusiness.ejecutarProcesoSincronizacionSINDO(folio,	patron, movimientoCalsificacion.getFecSurteEfecto(),
					movimientoCalsificacion.getCveCausa().longValue(), APLICACION_MOV_SINDO_CLASIFICACION, TipoMovtoPatSujObligEnum.CAMBIO_DE_CLASIFICACION_PATRONAL.getId(), 
					TipoMovtoPatSujObligEnum.CAMBIO_DE_CLASIFICACION_PATRONAL.getId());
			log.debug("pase el envio de movimiento a SINDO de la Clasificacion ");  
		}catch(Exception e) {
			log.error("ocurrio un error al generar el movimiento a SINDO de clasificacion por folios PAC" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado al generar el movimiento a SINDO de clasificacion por folios PAC" , e.getMessage()));
		}

		return solicitud.getNoFolioSolicitud();

	}


	/**
	 * Metodo que consulta registros patronales que puedan tener baja por articulo 251 en tabla DIT_MOVTO_PAT_SUJ_OBLIG
	 * @param lstRegPatronales
	 * @return List<MovimientoRegistroPatronal>
	 * @throws Exception
	 */
	@Override
	public List<MovimientoRegistroPatronal> consultaPatronBaja(List<String> lstRegPatronales, boolean indBaja251 )
			throws ServiciosRestException {
		ValidacionesComunesUtil.validaListaNoVacia(lstRegPatronales, 1000L, "lista registros patronales");
		try {
			return patronServiceEntity.consultaPatronBaja(lstRegPatronales, indBaja251);
		}catch (Exception e) {
			throw ValidacionesComunesUtil.getServiciosRestException(e, "ocurrio un error la consultar los patrones con baja 251");
		}
	}

	/**
	 * Metodo encargado de conultar la actividad de un patron by NRP
	 * @param nrp
	 * @return ActividadEcononica con los datos de la división grupo y fraccion
	 * @throws ServiciosRestException
	 */
	@Override
	public ActividadEcononica getActividadEconocimaByRegPatronal(String nrp) throws ServiciosRestException{
		log.debug(" llegue al servicio getActividadEconocimaByRegPatronal" + nrp);
		nrp = ValidacionesComunesUtil.validaEstructuraNRP(nrp);
		ActividadEcononica actividad= null;
		try {
			actividad = patronServiceEntity.getActividadEconocimaByRegPatronal(nrp.substring(0,10));
			log.debug("pase la llamada de la consulta de actividad economica");
		}catch (Exception e) {
			log.error("Ocurrio un erro al realizar la busqueda de la actividad economica del patron", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado en la consulta de la actividad economica del patron", e.getMessage()));
		}
		ValidacionesComunesUtil.validaObjetoNulo(actividad, "No se encontro información relacionada a la actividad economica con el NRP "+ nrp);
		return actividad;

	}

	@Override
	public NumTrabajadoresVigentes getTrabajadoresVigentesByRegPatronal(String nrp) throws ServiciosRestException {

		log.debug(" llegue al servicio getTrabajadoresVigentesByRegPatronal" + nrp);
		nrp = ValidacionesComunesUtil.validaEstructuraNRP(nrp);
		NumTrabajadoresVigentes trabajadores= null;
		try {
			trabajadores = patronServiceEntity.getTrabajadoresVigentesByRegPatronal(nrp.substring(0,10));
			log.debug("pase la llamada de la consulta de actividad economica");
		}catch (Exception e) {
			log.error("Ocurrio un erro al realizar la consulta de los trabajadores Vigentes del patron", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado en la consulta de los trabajadores Vigentes del patron", e.getMessage()));
		}
		ValidacionesComunesUtil.validaObjetoNulo(trabajadores, "No se encontro información relacionada a los trabajadores vigentes con el NRP "+ nrp);
		return trabajadores;
	}



	/**
	 * Inserta una nueva solicitud del tipo y con el estado proporcionados
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param tipoSolicitud
	 * @param estadoSolicitud
	 * @param usuario
	 * @return Solicitud
	 */
	private static Solicitud setSolicitudActualizacionClasificacion(TipoSolicitudEnum tipoSolicitud, Integer idSubdelegacion,
			EstadoSolicitudEnum estadoSolicitud, String strUsuario, String cveFolioPac, OrigenSolicitudEnum idOrigenSolicitud ) {
		Solicitud solicitud = new Solicitud();
		EstadoSolicitud estado = new EstadoSolicitud();
		estado.setIdEstadoSolicitud(estadoSolicitud.getCodigo());
		solicitud.setEstadoSolicitud(estado);
		solicitud.setFechaSolicitud(Calendar.getInstance().getTime());
		TipoSolicitud tipo = new TipoSolicitud();
		tipo.setIdTipoSolicitud(tipoSolicitud.getValor().longValue());
		solicitud.setTipoSolicitud(tipo);
		Usuario usuario = new Usuario();
		usuario.setUsuario(strUsuario);
		solicitud.setSolicitante(usuario);
		solicitud.setObservacion(cveFolioPac);
		OrigenSolicitud origen = new OrigenSolicitud();
		origen.setIdOrigenSolicitud(idOrigenSolicitud.getId());
		solicitud.setOrigenSolicitud(origen);
		solicitud.setFechaConclusion(new Date());
		Subdelegacion subdel = new Subdelegacion();
		subdel.setId(idSubdelegacion.longValue());
		solicitud.setSubdelegacion(subdel);
		log.debug("Terminando de setear la solicitud");
		return solicitud;
	}

	/**
	 * Inicializa un objeto de tramites para modificación de datos de registros patronales.
	 * @param idPersona
	 * @param solicitud
	 * @param tipoPersona
	 * @return Tramite
	 */
	private static TramiteSujetoObligado setTramiteActualizacionClasificacion(TipoTramiteEnum tipo,
			EstadoTramiteEnum estado, SujetoObligado sujetoObligado, Date fecEfecto) {
		TramiteSujetoObligado tramite = new TramiteSujetoObligado();
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(estado.getCodigo());
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(tipo.getCodigo());
		tramite.setSujetoObligado(sujetoObligado);
		tramite.setEstadoTramite(estadoTramite);
		tramite.setTipoTramite(tipoTramite);
		tramite.setFechaTramite(new Date());
		tramite.setFechaPresentacion(new Date());
		tramite.setFechaEfecto(fecEfecto);
		tramite.setFechaConclusion(new Date());
		log.debug("Terminando de setear el trámite");
		return tramite;

	}

	private String buildNumeroFolio(String claveSubdel) {
		Calendar calendar=Calendar.getInstance();
		StringBuilder juliano=new StringBuilder();
		juliano.append(calendar.get(Calendar.DAY_OF_YEAR));
		String folio = claveSubdel
				+ (juliano.length()==1?"00"+juliano:juliano.length()==2?"0"+juliano:juliano);
		log.debug(String.format("Folio con dia juliano: %s", folio));
		folio = claveSubdel + "411";
		log.debug(String.format("Folio corregido con 411: %s", folio));
		return folio;
	}


	@Override
	public DatosGeneralesPatron getDatosGeneralesPatron(String nrp) throws ServiciosRestException {

		log.debug(" llegue al servicio getDatosGeneralesPatron" + nrp);
		nrp = ValidacionesComunesUtil.validaEstructuraNRP(nrp);
		DatosGeneralesPatron datosPatron = null;
		Domicilio domicilioDelta = new Domicilio();
		try {
			DatosGeneralesPatronQuery patronEntity  = patronServiceEntity.getDatosGeneralesPatron(nrp);
			ValidacionesComunesUtil.validaObjetoRespuestaNulo(patronEntity, "No se localizo informacion "
					+ " para el registro patronal " + nrp);
			datosPatron = new DatosGeneralesPatron();
			BeanUtils.copyProperties(patronEntity,datosPatron);
			datosPatron.setNombreRazonSocial(datosPatron.getNombreRazonSocial().trim());
			if(patronEntity.getDomicilioId() != null) {
				domicilioDelta.setClave(patronEntity.getDomicilioId().intValue());
				try {
					domicilioDelta =domicilioServiceBusinessRemote.consultarDomicilio(domicilioDelta);
					if(domicilioDelta != null) {
						if(patronEntity.getDesDomicilio() != null)
							domicilioDelta.setDescripcion(patronEntity.getDesDomicilio() );
						if(patronEntity.getNombreLocalidad()!= null)
							domicilioDelta.setDescripcion(domicilioDelta.getDescripcion() +" "+ patronEntity.getNombreLocalidad() );
						datosPatron.setDomicilio(domicilioDelta);
					}else {
						domicilioDelta = new Domicilio();
						if(patronEntity.getDesDomicilio() != null)
							domicilioDelta.setDescripcion(patronEntity.getDesDomicilio() );
						if(patronEntity.getNombreLocalidad()!= null)
							domicilioDelta.setDescripcion(domicilioDelta.getDescripcion() +" "+ patronEntity.getNombreLocalidad() );
						if(patronEntity.getCodigoPostal()!= null) {
							CodigoPostal cp = new CodigoPostal();
							cp.setCodigoPostal(patronEntity.getCodigoPostal());
							domicilioDelta.setCodigoPostal(cp);
						}
					}
				}catch(Exception e){
					log.error("ocurrio un error al consultar el domicilio en servicios digiales" , e);
				}
			}else {
				domicilioDelta = new Domicilio();
				if(patronEntity.getDesDomicilio() != null)
					domicilioDelta.setDescripcion(patronEntity.getDesDomicilio() );
				if(patronEntity.getNombreLocalidad()!= null)
					domicilioDelta.setDescripcion(domicilioDelta.getDescripcion() +" "+ patronEntity.getNombreLocalidad() );
				if(patronEntity.getCodigoPostal()!= null) {
					CodigoPostal cp = new CodigoPostal();
					cp.setCodigoPostal(patronEntity.getCodigoPostal());
					domicilioDelta.setCodigoPostal(cp);
				}
				datosPatron.setDomicilio(domicilioDelta);
			}
			return datosPatron;
		}catch (ServiciosRestException e) {
			throw e;
		}catch(Exception e) {
			log.error("ocurrio un error al consultar los datos generales del patron" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado al consultar los datos generales del patron " + nrp , e.getMessage()));
		}
	}

	@Override
	public DatosHuelga getInfoPatronHuelga(String regPatron) throws ServiciosRestException {
		log.debug("llege la la consulta getInfoPatronHuelga " + regPatron);
		regPatron =ValidacionesComunesUtil.validaEstructuraNRP(regPatron);
		List<String> lstNrp = new ArrayList<String>();
		lstNrp.add(regPatron);
		try {
			Date fechaHuelga = patronServiceEntity.getFechaHuelgaPatron(regPatron);
			DatosHuelga huelga = new DatosHuelga(); 

			huelga.setRegistroPatronal(regPatron.substring(0, 8));
			huelga.setClaveModalidad(regPatron.substring(8));
			huelga.setIndHuelga(false);
			if (fechaHuelga != null) {
				String fechaFormateada = new SimpleDateFormat("yyyy-MM-dd").format(fechaHuelga);
				if (!fechaFormateada.equals(PERIODO_ABIERTO_HUELGA)) {
					huelga.setIndHuelga(true);
					huelga.setFechaInicioHuelga(fechaHuelga);
				}
			}
			return huelga;
		}catch(Exception e) {
			log.error("corruio un error al consumir el servicio de  getInfoPatronHuelga " + regPatron , e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "corruio un error al consumir el servicio de  getInfoPatronHuelga " +  regPatron);
		}
	}

	@Override
	public DatosEmpresaPermisoConvid getDatosEmpresaPermisoCovid(String rfc) throws ServiciosRestException {

		log.debug(" llegue al servicio getDatosEmpresaPermisoCovid" + rfc);
		rfc = ValidacionesComunesUtil.validaEstructuraRfc(rfc);

		try {
			return  patronServiceEntity.getDatosEmpresaPermisoCovid(rfc);

		}catch (Exception e) {
			log.error("Ocurrio un erro al realizar la consulta de los trabajadores Vigentes del patron", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado en la consulta de los trabajadores Vigentes del patron", e.getMessage()));
		}

	}

	@Override
	public List<DatosGeneralesPatronPermisoCovid> getDatosGeneralesPatronPernisoCovid(Long cveIdAsignacionNSS) throws ServiciosRestException {

		log.debug(" llegue al servicio getDatosGeneralesPatron" + cveIdAsignacionNSS);

		ValidacionesComunesUtil.validaObjetoNulo(cveIdAsignacionNSS, "el cveIdAsingacionNSS no puede er nulo");
		List<DatosGeneralesPatronPermisoCovid> lstDatosPatron = new ArrayList<DatosGeneralesPatronPermisoCovid>();
		List<Long> idsPatronGeneral = new ArrayList<Long>();
		RespuestaPatronesVigentes wsRespuesta = null;
		try {
			try {
				wsRespuesta = WSPatronesVigentesService.getService().getConsultaPatronesVigentes(cveIdAsignacionNSS+"");
				log.debug("regrese de consultar a los patrones vigentes");
			}catch (Exception e) {
				log.error("ocurrio un erro al consultar los patrones vigentes del asegurado en almacenes" + cveIdAsignacionNSS , e);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"ocurrio un erro al consultar los patrones vigentes del asegurado en almacenes" + cveIdAsignacionNSS + " "  + e.getMessage() , e.getMessage()),e);
			}
			ValidacionesComunesUtil.validaObjetoRespuestaNulo(wsRespuesta, "No se encontraron patrones vigentes para el asegurado");
			ValidacionesComunesUtil.validaListaNulaVacia(wsRespuesta.getPatronesActivos(), "No se encontraron patrones vigentes para el asegurado");

			for(PatronVigenteVO wsPatronVO: wsRespuesta.getPatronesActivos()) {
				idsPatronGeneral.add(new Long(wsPatronVO.getCveIdPatronGeneral()));
			}
			List<DatosGeneralesPatronQuery> lstPatronEntity  = patronServiceEntity.getDatosGeneralesPatron(idsPatronGeneral);
			ValidacionesComunesUtil.validaListaNulaVacia(lstPatronEntity, "no se encontraron patrones para el asegurado con el patron general");


			for(DatosGeneralesPatronQuery patronEntity : lstPatronEntity ) {
				DatosGeneralesPatronPermisoCovid datosPatron = new DatosGeneralesPatronPermisoCovid();
				BeanUtils.copyProperties(patronEntity,datosPatron);
				datosPatron.setNombreRazonSocial(datosPatron.getNombreRazonSocial().trim());
				if(StringUtils.isNotEmpty(datosPatron.getRfc())) {
					datosPatron.setDatosEmpresaPermisoConvid(getDatosEmpresaPermisoCovid(datosPatron.getRfc()));
				}
				lstDatosPatron.add(datosPatron);
			}
			return lstDatosPatron;
		}catch (ServiciosRestException e) {
			throw e;
		}catch(Exception e) {
			log.error("ocurrio un error al consultar los datos patrones vigentes del asegurado" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar los datos de patrones vigentes del asegurado " + cveIdAsignacionNSS , e.getMessage()));
		}
	}

	@Override
	public List<String> getRegistrosPatronalesByRfc(String rfc) throws ServiciosRestException{
		log.debug(" llegue al servicio getRegistrosPatronalesByRfc" + rfc);
		rfc = ValidacionesComunesUtil.validaEstructuraRfc(rfc);
		try {
			return patronServiceEntity.getRegistrosPatronalesByRfc(rfc);
		}catch(Exception e) {
			log.error("ocurrio un error al consultar los NRP por RFC" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar los NRP por RFC " + rfc , e.getMessage()));
		}
	}
	
	@Override
	public DatosSatDetallePatron getDatosSatDetallePatron(ConsultaPatron consultaPat) throws ServiciosRestException {
		log.debug(" llegue al servicio getDatosSatDetallePatron" + consultaPat);
		boolean isBusquedaNrp = false;
		boolean isBusquedaRfc = false;
		boolean isBusquedaListPatrones = false;
		boolean isBusquedaVacia = true;
		
		if(!ValidacionesComunesUtil.isStringNuloVacio(consultaPat.getRegistroPatronal()))
			isBusquedaNrp =true;
		if(!ValidacionesComunesUtil.isStringNuloVacio(consultaPat.getRfc()))
			isBusquedaRfc =true;
		if(consultaPat.isConsultaRegPatronales())
			isBusquedaListPatrones =true;
		if(isBusquedaNrp || isBusquedaRfc )
			isBusquedaVacia = false;
		if(isBusquedaVacia)
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"Todos los parametros de busqueda son nulos o vacios", "Todos los parametros de busqueda son nulos o vacios"));
		if(isBusquedaNrp)
			consultaPat.setRegistroPatronal(ValidacionesComunesUtil.validaEstructuraNRP(consultaPat.getRegistroPatronal()));
		if(isBusquedaRfc)
			consultaPat.setRfc(ValidacionesComunesUtil.validaEstructuraRfc(consultaPat.getRfc()));
		
		try {
			List<DetallePatronClasifMovPatQuery> lstPatronesQuery = null;
			
			DatosSatDetallePatron datosSat = patronServiceEntity.getRfcTipoPersona(consultaPat);
			ValidacionesComunesUtil.validaObjetoRespuestaNulo(datosSat, "No se localizÃ³ informacion del RFC de la persona con los datos de busqueda " + consultaPat );
			consultaPat.setIdTipoPersona(datosSat.getTipiPersona().getIdTipoPersona());
			consultaPat.setRfc(datosSat.getRfc());
			
			RespuestaWSSat respuestaSat = consultaServiciosExternosService.getDatosSatByRfc(datosSat.getRfc());
			ValidacionesComunesUtil.validaObjetoRespuestaNulo(datosSat, "No se localizÃ³ informacion del RFC en SAT " + datosSat.getRfc() );
			log.debug("pase la consulta del servicio de SAT" + datosSat.getRfc());
			datosSat.setActividad(respuestaSat.getActividad());
			datosSat.setUbicacion(respuestaSat.getUbicacion());
			
			if(datosSat.getTipiPersona().getIdTipoPersona().longValue() == TipoPersonaEnum.FISICA.getId()) 
				datosSat.setNombreRazonSocial(armaNOmbrePersonaFisicaSat(respuestaSat.getIdentificacion().get(0)));
			else
				datosSat.setNombreRazonSocial(respuestaSat.getIdentificacion().get(0).getRazonSoc());
			
			if(isBusquedaListPatrones || isBusquedaNrp) { 
				if (isBusquedaListPatrones) 
					consultaPat.setRegistroPatronal(null);
				else 
					consultaPat.setRfc(null);
				lstPatronesQuery = patronServiceEntity.getDetallePatronClasifMovPat(consultaPat);
				if(lstPatronesQuery != null && !lstPatronesQuery.isEmpty() ) {
					List<DetallePatronClasifMovPat> lstPatronesDom = new ArrayList<DetallePatronClasifMovPat>();
					for (DetallePatronClasifMovPatQuery patronEntityQuery : lstPatronesQuery) {
						DetallePatronClasifMovPat patronEntity = new DetallePatronClasifMovPat();
						BeanUtils.copyProperties(patronEntityQuery,patronEntity);
						
						if(patronEntityQuery.getDomicilioId() != null) {
							Domicilio domicilioDelta = new Domicilio();
							domicilioDelta.setClave(patronEntityQuery.getDomicilioId().intValue());
							try {
								domicilioDelta =domicilioServiceBusinessRemote.consultarDomicilio(domicilioDelta);
								if(domicilioDelta == null) {
									domicilioDelta = new Domicilio();
									if(patronEntityQuery.getDesDomicilio() != null)
										domicilioDelta.setDescripcion(patronEntityQuery.getDesDomicilio() );
									if(patronEntityQuery.getNombreLocalidad()!= null) {
										Localidad localidad = new Localidad();
										localidad.setNombre(patronEntityQuery.getNombreLocalidad());
										domicilioDelta.setLocalidad(localidad);
									}
									if(patronEntityQuery.getCodigoPostal()!= null) {
										CodigoPostal cp = new CodigoPostal();
										cp.setCodigoPostal(patronEntityQuery.getCodigoPostal());
										domicilioDelta.setCodigoPostal(cp);
									}
								}
								patronEntity.setDomicilio(domicilioDelta);
							}catch(Exception e){
								log.error("ocurrio un error al consultar el domicilio en servicios digiales" , e);
							}
						}else {
							Domicilio domicilioDelta = new Domicilio();
							if(patronEntityQuery.getDesDomicilio() != null)
								domicilioDelta.setDescripcion(patronEntityQuery.getDesDomicilio() );
							if(patronEntityQuery.getNombreLocalidad()!= null) {
								Localidad localidad = new Localidad();
								localidad.setNombre(patronEntityQuery.getNombreLocalidad());
								domicilioDelta.setLocalidad(localidad);
							}
							if(patronEntityQuery.getCodigoPostal()!= null) {
								CodigoPostal cp = new CodigoPostal();
								cp.setCodigoPostal(patronEntityQuery.getCodigoPostal());
								domicilioDelta.setCodigoPostal(cp);
							}
							patronEntity.setDomicilio(domicilioDelta);
						}
						lstPatronesDom.add(patronEntity);
					}
					datosSat.setLstDetallePatron(lstPatronesDom);
				}
			}
				return datosSat;
	
		}catch(ServiciosRestException e) {
			log.error("error ya manejado " , e);
			throw e;
		}catch(Exception e) {
			log.error("ocurrio un error al consultar el detalle de patrones getDatosSatDetallePatron" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar el detalle de la empresa " + consultaPat , e.getMessage()));
		}
		
	}
		
		private String armaNOmbrePersonaFisicaSat(Identificacion identi) {
			String nombrePf = null;
			nombrePf = identi.getNombre();
			nombrePf += (identi.getApPaterno()==null)? "" : " " +identi.getApPaterno();
			nombrePf += (identi.getApMaterno()==null)? "" : " " +identi.getApMaterno();
			return nombrePf;
			
		}

		@Override
		public PatronPlataformaResponse validaPatronPlataforma(String nrp) throws ServiciosRestException {
			log.debug(" llegue al servicio getDatosGeneralesPatron" + nrp);
			PatronPlataformaResponse response = new PatronPlataformaResponse();
			try {
				nrp = ValidacionesComunesUtil.validaEstructuraNRP(nrp);
				response = patronServiceEntity.validaPatronPlataforma(nrp);
			}catch (ServiciosRestException e) {
				response.setCodigoRespuesta("1");
				response.setDetalleRespuesta(e.getErrorBean().getBusinessMessage());;
			}catch (Exception e) {
				response.setCodigoRespuesta("2");
				if(e.getMessage().length()>100)
					response.setDetalleRespuesta(e.getMessage().substring(0,100));
				else 
					response.setDetalleRespuesta(e.getMessage());
			}
			return response;
		}

}
