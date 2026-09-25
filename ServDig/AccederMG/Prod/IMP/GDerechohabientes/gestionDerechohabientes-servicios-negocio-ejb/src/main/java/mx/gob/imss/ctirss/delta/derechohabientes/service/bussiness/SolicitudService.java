package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.AsignacionNssDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CabezaGrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DerechohabienteDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.PersonaInteresadaSolDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.SolicitudDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TramitePersonaFisicaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UmfDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.SolicitudParserLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DeltaUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsignacionNSSParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TramiteParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Caracter;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UMFTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudNssDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudesPendientesAutorizacionDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonCancelacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.log4j.Logger;

/**
 * @author Mario Teran Blanco,Victor Manuel Camacho Guerra
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Stateless( name = "solicitudService", mappedName = "solicitudService")
public class SolicitudService implements SolicitudServiceRemote, SolicitudServiceLocal{

	@EJB(name = "solicitudDao") SolicitudDaoLocal solicitudDaoLocal;
	@EJB(name = "tramitePersonaFisicaDao") TramitePersonaFisicaDaoLocal tramitePersonaFisicaDaoLocal;
	@EJB(name = "subDelegacionDAO") UmfDaoLocal umfDao;
	@EJB(name = "personaInteresadaSolDao") PersonaInteresadaSolDaoLocal personaInteresadaSolDaoLocal;
	@EJB(name = "derechohabienteDao") DerechohabienteDaoLocal derechohabienteDaoLocal;
	@EJB(name = "grupoFamiliarDao") GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB(name = "agendarCitaService") AgendarCitaServiceLocal agendarCitaServiceLocal;
	@EJB(name = "tramiteService") TramiteServiceLocal tramiteServiceLocal;
	@EJB AsignacionNssDaoLocal asignacionNssDao;
	@EJB CabezaGrupoFamiliarDaoLocal cabezaGrupoFamiliarDao;
	@EJB CatalogosDaoLocal catalogosDao;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness") SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB(name = "solicitudTramiteBusiness", mappedName = "solicitudTramiteBusiness") SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	@EJB SolicitudParserLocal solicitudParserLocal;
	

	
	private static final Logger log = Logger.getLogger(SolicitudService.class);
	
	@Override
	public Boolean tramitePosible(Long idPersona, Long tipoTramite,
			AsignacionNSS nss) {
		List<Solicitud> solicitudes = null;
		List<Long> personas = new ArrayList<Long>();
		personas.add(idPersona);   
		List<Long> tipoTramites = new ArrayList<Long>();
		tipoTramites.add(tipoTramite);
		List<Long> estadoTramites = new ArrayList<Long>();
		estadoTramites.add(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo().longValue());
		estadoTramites.add(EstadoTramiteEnum.INICIADO.getCodigo().longValue());
		try {
			solicitudes = solicitudTramiteBusinessRemote.getSolicitudesPersona(personas, tipoTramites, estadoTramites, null, null, nss.getIdPersona(), true, 1,true);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(solicitudes == null)
			return true;
		if(solicitudes.size() == 0 )
			return true;
		
		return false;
	}

	@Override
	public Tramite tramiteAbierto(Long idPersona, AsignacionNSS nss) throws Exception{
		Tramite tramite = null;
		try { 
			List<Tramite> tramites = solicitudTramiteBusinessRemote.getTramitesAbierto(Arrays.asList(idPersona), nss.getIdPersona(), Arrays.asList(4L));
			log.debug("tramite  Abierto............");
			if(tramites!=null && !tramites.isEmpty() ){
				tramite = tramites.get(0);
			}
			
			//DerechohabientesBusinessException.throwException("Error al consultar las solicitudes");
//			Se cambio 	
//			tramite = tramitePersonaFisicaDaoLocal.getUltimoTramiteAbierto(idPersona, nss.getIdPersona());
		} catch (Exception e) {
			log.error("Error al recuperar el ultimo tramite abierto o pendiente de autorizacion", e);
			throw e;
		}
		
		return tramite;
	}
	/**
	 * @author Mario Teran Blanco
	 * 
	 * Metodo para buscar una solicitud pasandolelos siguientes parametros
	 * 
	 * @param idSolicitud - id de la solicitud a mostar
	 * @return Solicitud - Solicitud encontrada
	 */
	@Override
	public Solicitud detalleSolicitud(Long idSolicitud) throws DerechohabientesBusinessException {
		Solicitud resultado = null;
		
		resultado = new Solicitud();
		resultado.setSolicitudId(idSolicitud);
		
		
		//Buscamos la solicitud con el id de la solicitud pasado como parametro
		try {
			resultado = solicitudBusinessRemote.consultar(resultado);
		} catch (Exception e) {
			//En caso de ocurrir una excepcion mandamos un error diciendo que no esxiste informacion
			log.error("Ocurrio un error inesperado", e);
			DerechohabientesBusinessException.throwException(ExceptionMessages.SIN_INFORMACION, e.getMessage());
		}
		
		if(resultado == null) {
			DerechohabientesBusinessException.throwException(ExceptionMessages.SIN_INFORMACION, "No existe una solicitud con el folio indicado");
		}
		
		return resultado;
	}

	/**
	 * @author Mario Teran Blanco
	 * 
	 * Metodo para cancelar una solicitud, dicha cancelacion la llevara a cabo el derechohabiente y 
	 * necesitara de los siguientes parametros:
	 * 
	 * @param idSolicitud - id de la solicitud a cancelar
	 * @return void
	 */
	@Override
	public void cancelarSolicitud(Long idSolicitud, Fisica personaUsuario) throws DerechohabientesBusinessException {
		log.debug("se cancela la solicitud apuntando al metodo que de solicitudes");
		
		this.rechazarSolicitud(idSolicitud, 5L, "Solicitud cancelada a peticion del derechohabiente", personaUsuario);
		
		/*
		//recobramos la solicitud a ser cancelada con el id que se nos paso como parametro
		Solicitud solicitud = new Solicitud(idSolicitud);
		
		try {
			solicitud = solicitudBusinessRemote.consultar(solicitud);
		} catch (SolicitudNoEncontradaException e1) {
			DerechohabientesBusinessException.throwException(ExceptionMessages.ERROR_CONSULTA_SOLICITUD, e1.getSituacion());
		}
		
		//Recorremos la lista de tramites asiciada a la solicitud
		for(Tramite tramite : solicitud.getTramites())
		{
			//para cada tramite cambiamos los estados necesarios
			tramite.setResultado(false);
			tramite.setRazonResultado(new RazonResultado());
			tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.SOLICITUD_CANCELADA.getCodigo().longValue());
			tramite.setEstadoTramite(new EstadoTramite());
			tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
			tramite.setFechaConclusion(new Date());
			
		}
		
		//Cambiamos los estados de la solicitud y la ponemos como cancelada a peticion del derechohabiente
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
		solicitud.setRazonCancelacion(new RazonCancelacion());
		solicitud.getRazonCancelacion().setIdRazonCancelacion(RazonCancelacionEnum.PETICION_DERECHOHABIENTE.getId());
		solicitud.setFechaConclusion(new Date());
		
		try {
			solicitudBusinessRemote.actualizarEstados(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TramiteNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	/**
	 * @author Mario Teran Blanco
	 * 
	 * Metodo para obtener el detelle de la correccion del derechohabiente
	 * 
	 * @param idSolicitud el id de la solicitud en la que se encuentra el tramite
	 * @param idPersona el id de la persona afectada por el tramite
	 * @return CorreccioDatoDerechohabiente tramite de correccion
	 * @throws Exception 
	 */
	@Override
	public TramiteCorreccionDerechohabiente detalleCorreccionDerechohabiente(
			Long idTramite)
			throws DerechohabientesBusinessException,Exception {
		TramiteCorreccionDerechohabiente resultado = null;
		
		//Obtenemos EL detalle de un tramite de tipo correccion datos de derechohabiente
		resultado = tramiteServiceLocal.getCorreccion(idTramite);
		
		return resultado;
	}

	/**
	 * @author Mario Teran Blanco
	 * 
	 * Metodo para obtener el detalle del registro del derechohabiente
	 * 
	 * @param idSolicitud el id de la solicitud en la que se encuentra el tramite
	 * @param idPersona el id de la persona afectada por el tramite
	 * @return RegistroDerechohabiente detalle del registro de un derechohabiente
	 * @throws Exception 
	 */
	@Override
	public TramiteRegistroDerechohabiente detalleRegistroDerechohabiente(
			Long idTramite)
			throws Exception {
		TramiteRegistroDerechohabiente resultado = null;
		
		//Obtenemos el detalle de un tramite de tipo registro de derechohabiente
		resultado = tramitePersonaFisicaDaoLocal.getTramiteRegistro(idTramite);
		
		//Si no encontramos informacion mandamos una excepcion
		if(resultado == null)
			throw new DerechohabientesBusinessException(ExceptionMessages.SIN_INFORMACION);
		
		return resultado;
	}
	
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public TramiteProrroga detalleProrroga(Long idTramite)
			throws Exception {
		TramiteProrroga resultado = null;
		
		//Obtenemos el detalle de un tramite de prorroga
		resultado = tramitePersonaFisicaDaoLocal.getTramiteProrroga(idTramite);
		
		//Si no encontramos informacion mandamos una excepcion
		if(resultado == null)
			throw new DerechohabientesBusinessException(ExceptionMessages.SIN_INFORMACION);		
		return resultado;
	}

	/**
	 * Metodo para obtener el detalle de una circunscripcion
	 * @throws Exception 
	 */
	@Override
	public TramiteCircunscripcionForanea detalleCircunscripcionForanea(Long idTramite)
			throws Exception {
		TramiteCircunscripcionForanea resultado = null;
		
		//Obtenemos el detalla del tramite de circunscripcion
		resultado = tramitePersonaFisicaDaoLocal.getCircunscripcionForanea(idTramite);
		
		//Si no encontramos informacion mandamos una excepcion
		if(resultado == null)
			throw new DerechohabientesBusinessException(ExceptionMessages.SIN_INFORMACION);
		
		return resultado;
	}

	
	@Override
	public TramiteCircunscripcionForanea detalleSuspencionCircunscripcion(
			Long idTramiteSuspencion) throws Exception {
		TramiteCircunscripcionForanea resultado = null;
		
		//Obtenemos el detalla del tramite de circunscripcion
		resultado = tramitePersonaFisicaDaoLocal.getSuspencionCircunscripcionForane(idTramiteSuspencion);
		
		//Si no encontramos informacion mandamos una excepcion
		if(resultado == null)
			throw new DerechohabientesBusinessException(ExceptionMessages.SIN_INFORMACION);
		
		return resultado;
	}

	@SuppressWarnings("unused")
	private void registroTramite(Solicitud solicitud,TramiteRegistroDerechohabiente registro) throws Exception{
		Tramite tramite = new Tramite();
		
		EstadoTramite et = new EstadoTramite();
		et.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		
		TipoTramite tt = new TipoTramite();
		tt.setIdTipoTramite(TipoTramiteEnum.REGISTRO_DE_DERECHOHABIENTE.getCodigo());
		
		//tramite.setSolicitud(solicitud);
		tramite.setPersona(registro.getFisica());
		tramite.setEstadoTramite(et);
		tramite.setTipoTramite(tt);
		tramite.setFechaTramite(new Date());
		tramite.setDetalleTramiteXml("Guardado de XML");		
		tramitePersonaFisicaDaoLocal.saveTramite(tramite);
	}

	@Override
	public DatosSalidaPaginador<SolicitudNssDto> listSolicitudesPendAut(
			SolicitudesPendientesAutorizacionDto solicitudesPenAutDto) throws Exception {
		List<SolicitudNssDto> solicitudes=null;
		List<DitSolicitud> ditSolicituds=null;
		
		DatosSalidaPaginador<SolicitudNssDto> salidaPaginador=new DatosSalidaPaginador<SolicitudNssDto>();
		
		//obtener la UMF por subdelegacion
		//ya no se requiere si se toma del usuario tramitador
		//dicUmf=this.umfDao.findUmfbySubDelagacionDelegacion(solicitudesPenAutDto.getIdSubDelegacion()); 
		//solicitudesPenAutDto.setIdUmf(dicUmf.getCveIdUmf());
		//obtener las solicitudes por umf pendientes de Autorizacion con umf de la subdelegacion,nss y folio
	
		ditSolicituds=this.solicitudDaoLocal.solicitudesPendientesDeAutorizacion(solicitudesPenAutDto);
		
		
		solicitudes=solicitudParserLocal.PersistToModelListPartialNss(ditSolicituds);	
		
		for(SolicitudNssDto sol : solicitudes) {
			log.debug("El tipo de solicitud del id : " + sol.getSolicitudId() + " es: " + sol.getTipoSolicitud().getIdTipoSolicitud() + " - " + sol.getTipoSolicitud().getDescripcion());
			Long idTipoSolicitud = sol.getTipoSolicitud().getIdTipoSolicitud();
			
			if(!idTipoSolicitud.equals(TipoSolicitudEnum.REGISTRO_DE_DERECHOHABIENTES.getValor().longValue())){
				Long idPersona = sol.getTramites().get(0).getPersonas() == null ? sol.getTramites().get(0).getPersona().getIdPersona()
						: sol.getTramites().get(0).getPersonas().get(0).getIdPersona();
				
				Parentesco parentesco = grupoFamiliarDaoLocal.getParentescoIntegrante(sol.getNumNss(), idPersona);
				sol.setParentesco(parentesco);
				
			} else {

				TramiteRegistroDerechohabiente registro = derechohabienteDaoLocal.getRegistroDerechohabiente(sol.getTramites().get(0).getTramiteId());
				sol.setParentesco(registro.getParentesco());
			}
		}
		
		salidaPaginador.setAaData(solicitudes);
		salidaPaginador.setiTotalRecords(solicitudesPenAutDto.getDatosTotales().intValue());
		salidaPaginador.setiTotalDisplayRecords(solicitudesPenAutDto.getDatosMostrados().intValue());
		if(salidaPaginador.getAaData().size()==0){
			//this.nssFolioNoEncontrado();
		}
		return salidaPaginador;
	}

	
	@Override
	public AsignacionNSS getAsignacionNssPorNss(String nss) throws Exception{
		AsignacionNSS asignacionNSS=null;
		
		asignacionNSS=AsignacionNSSParser.persisToModel(this.asignacionNssDao.getAsignacionNSSbyNSS(nss));
	
		return asignacionNSS;
	}
	
	@SuppressWarnings("unused")
	private void nssFolioNoEncontrado() throws DerechohabientesBusinessException{
		DerechohabientesBusinessException e=new DerechohabientesBusinessException("El NSS o folio  proporcionado no existen o no pertenecen a la UMF");
		throw e;
	}

	
	@SuppressWarnings("unused")
	private String folioSolicitud(long tipoSolicitud) {
		Date fechaActual = new Date();
		String folio = "";
		
		folio = ""+tipoSolicitud+fechaActual;
		return folio;
	}


	
	@SuppressWarnings("unused")
	private void nssNoEncontrado() throws DerechohabientesBusinessException{
		DerechohabientesBusinessException e=new DerechohabientesBusinessException("No Existen Registros");
		throw e;
	}

	@Override
	public DatosSalidaPaginador<Solicitud> solicitudesDerechohabientes(
			SolicitudDto solicitudDto) throws Exception {
		List<Solicitud> solicitudes=null;
		List<DitSolicitud> ditSolicituds=null;
		DatosSalidaPaginador<Solicitud> salidaPaginador=new DatosSalidaPaginador<Solicitud>();
		//DAO
		ditSolicituds=this.solicitudDaoLocal.solicitudesDerechohabientes(solicitudDto);
		//PErsistencia
		solicitudes=solicitudParserLocal.PersistToModelListPartial(ditSolicituds);
		//paginacion
		salidaPaginador.setAaData(solicitudes);
		salidaPaginador.setiTotalRecords(solicitudDto.getPaginacionDto().getDatosTotales().intValue());
		salidaPaginador.setiTotalDisplayRecords(solicitudDto.getPaginacionDto().getDatosMostrados().intValue());
		//error
		if(salidaPaginador.getAaData().size()==0){
			//this.nssNoEncontrado();
		}
		
		return salidaPaginador;
	}
	
	
	@Override
	public List<Tramite> findTramites(Long idSolicitud) throws DerechohabientesBusinessException, Exception {
		List<Tramite> tramites = solicitudDaoLocal.findTramites(idSolicitud);
		return tramites;
	}
	
	/**
	 * @author Mario Teran Blanco
	 * Metodo para obtener un tramite especifico pasandole los siguientes parametros
	 * @param idSolicitud la solicitud a la cual pertenece el tramite
	 * @param idPErsona el id de la persona afectada por el tramite
	 * @param idTipoTramite el id del tipo de tramite a buscar
	 */
	@Override
	public Tramite getTramiteSolicitud(Long idSolicitud, Long idPersona,
			Long idTipoTramite) throws DerechohabientesBusinessException {
		Tramite tramite = null;
		
		//Buscamos el tramite especificado
		try {
			tramite = solicitudDaoLocal.getTramite(idSolicitud, idPersona);
			
			//Si el tramite es nulo mandamos una excepcion indicanco que no hay informacio
			if(tramite == null)
				throw new DerechohabientesBusinessException(ExceptionMessages.SIN_INFORMACION);
		} catch(Exception e) {
			//Si ocurrio algun error mandamos unaexcepcion indicando que no hay informacion
			throw new DerechohabientesBusinessException(ExceptionMessages.SIN_INFORMACION);
		}
		
		return tramite;
	}

	/**
	 * @author Mario Teran Blanco
	 * Metodo para rechazar una solicitud y guardar sus nuevos estados
	 * @param idSolicitud el id de la solicitud a rechazar
	 * @param razonRechazo la razon por la cual se rechaza la solicitud
	 * @return void
	 */
	@Override
	public Solicitud rechazarSolicitud(Long idSolicitud, Long idRazonRechazo, String observaciones,Fisica personaUsuario)
			throws DerechohabientesBusinessException {
		
		//Obtenemos la solicitud a rechazar
		Solicitud solicitud = null;
		
		try {
			solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			
			solicitudBusinessRemote.cancelarSolicitud(idSolicitud,idRazonRechazo,null, null,observaciones);
		} catch (SolicitudException e1) {
			log.error("no se pudo cancelar la solicitud",e1);
			DerechohabientesBusinessException.throwException(e1.getSituacion(), e1.getSituacion());
		}
		
		return solicitud;
		
	}
	
	@Override
	public AsignacionNSS getAsignacionByIdSolicitud(Long idSolicitud) throws DerechohabientesBusinessException, Exception {
		AsignacionNSS asignacionNSS = null;	
		
		long idPersonaInteresada = personaInteresadaSolDaoLocal.getIdPersonaInteresadaSol(idSolicitud);			
		asignacionNSS = asignacionNssDao.getAsignacionNSSbyIdPersona(idPersonaInteresada);
		
		return asignacionNSS;
	}
	
	@Override
	public AsignacionNSS getSolicitudFolio(String folioSolicitud) throws Exception {		
		long idPersonaInteresada;
		AsignacionNSS asignacionNSS = null;		
		Solicitud sol = solicitudDaoLocal.getSolicitudByFolio(folioSolicitud);
		if(sol == null){
			throw new DerechohabientesBusinessException(ExceptionMessages.SIN_FOLIO_SOLICITUD);
		}else{
			long idSolicitud = sol.getSolicitudId();
			
			idPersonaInteresada = personaInteresadaSolDaoLocal.getIdPersonaInteresadaSol(idSolicitud);	
			
			asignacionNSS = asignacionNssDao.getAsignacionNSSbyIdPersona(idPersonaInteresada);
			
			
		}	
		return asignacionNSS;
	}

	/**
	 * @author Mario Teran Blanco
	 * Metodo para guardar una solicitud pasandole los siguientes parametros
	 * @param derechohabiente El integrante afectado por el tramite
	 * @param tipo El tipo de tramite
	 * @param usuario El usuario que solicita el tramite
	 * @param nss objeto de tipo asignacionNSS que representa al asegurado cabeza de grupo familiar
	 * @param tipoSolicitud el tipo de la solicitud, es decir, baja, correccion, registro, etc
	 * @return Solicitud la solicitud guardada
	 * @throws Exception 
	 */
	@Override
	public Solicitud guardarSolicitud(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TipoSolicitudEnum tipoSolicitud, String observaciones, Domicilio domicilio, Tramite tramite)
			throws Exception {
		
		//Creamos la solicitud a guardar
		Solicitud solicitud = new Solicitud();
		OrigenSolicitud origen = new OrigenSolicitud();
		UMFTurno umfTurno = null;
		
		//Colocamos todos los estados necesarios de la nueva solicitud
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(tipoSolicitud.getValor().longValue());
		solicitud.setFechaSolicitud(new Date());
		solicitud.setFechaCita(new Date());
		solicitud.setSolicitante(usuario);
		solicitud.setFechaActualizacion(new Date());
	
		solicitud.setObservacion(observaciones);
		//obtenemos el perfil del usuario para verificar si se genera cita o no
		Long perfilUsuario = usuario.getPerfilUsuario().getIdPerfilUsuario();
		
		//Checamos si el perfil de usuario no es tramitador o un nivel mas alto
		//de ser asi generamos una cita y se la asignamos a la solicitud actual
		if(perfilUsuario.longValue() != PerfilesEnum.TRAMITADOR.getId().longValue()) {
			origen.setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
			try {
				CitaSolicitud cita= agendarCitaServiceLocal.getCita(domicilio);
				solicitud.setCitaSolicitud(cita);
				solicitud.setFechaCita(cita.getFechaHora());
			} catch (DerechohabientesBusinessException e) {
				log.error("", e);
			}	
		
		}else {
			origen.setIdTipoSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
			// si el usuario es un tramitador o nivel mas alto no hay necesidad de generar una cita
			// y asignamos a ese apartado el dia de hoy asi como la umf actual del derechohabiente
			
			umfTurno = new UMFTurno();
			if(tramite instanceof TramiteCircunscripcionForanea) {
				TramiteCircunscripcionForanea circuns = (TramiteCircunscripcionForanea) tramite;
				
				// ---------------------------------------------------------------------------------------
				// El origen y el destino estan "AL REVÉZ" para la suspencion relizada desde la clinica 
				// distinta a la del asegurado y no puede validar la solicitud.
				// ---------------------------------------------------------------------------------------
				if( tipoTramite.equals(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.SUSPENSION_SERVICIOS_CIRCUNSCRIPCION_FORANEA) ){
					GrupoFamiliar asegurado=grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(derechohabiente.getAsignacionNSS().getIdAsignacionNSS(), derechohabiente.getAsignacionNSS().getIdPersona());
					umfTurno.setTurno(asegurado.getMedicoEnTurno().getTurno());
					umfTurno.setUnidadMedicaFamiliar(asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar());
				}else{
					if(circuns.getMedicoEnTurnoDestino() != null) {
						umfTurno.setTurno(circuns.getMedicoEnTurnoDestino().getTurno());
						umfTurno.setUnidadMedicaFamiliar(circuns.getMedicoEnTurnoDestino().getUnidadMedicaFamiliar());
					}
				}
				
				
			}else{
				if(derechohabiente.getMedicoEnTurno()!=null){
					umfTurno.setTurno(derechohabiente.getMedicoEnTurno().getTurno());
					umfTurno.setUnidadMedicaFamiliar(derechohabiente.getMedicoEnTurno().getUnidadMedicaFamiliar());
				}else{
					
					GrupoFamiliar asegurado=grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(derechohabiente.getAsignacionNSS().getIdAsignacionNSS(), derechohabiente.getAsignacionNSS().getIdPersona());
					umfTurno.setTurno(asegurado.getMedicoEnTurno().getTurno());
					umfTurno.setUnidadMedicaFamiliar(asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar());
				}
			}
			
			
		}
		
		
		CitaSolicitud cita = new CitaSolicitud();
		if (umfTurno != null){
			cita.setUmf(umfTurno.getUnidadMedicaFamiliar());
			cita.setTurno(umfTurno.getTurno());
		}
		
		solicitud.setCitaSolicitud(cita);
		solicitud.setOrigenSolicitud(origen);
		//Creamos el tramite para agregarlo a la solicitud con sus respectivos estados
		List<Tramite> tramites = new ArrayList<Tramite>();
		if(tramite == null) {
			tramite = new Tramite();
		}
		tramite.setRazonResultado(new RazonResultado());
		tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
		tramite.setPersona(derechohabiente.getDerechohabiente());
		tramite.setEstadoTramite(new EstadoTramite());
		tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		tramite.setTipoTramite(new TipoTramite());
		tramite.getTipoTramite().setIdTipoTramite(tipoTramite.getCodigo());
		tramite.setFechaTramite(new Date());
		tramite.setFechaPresentacion(new Date());
		tramite.setObservacion(observaciones);
		tramites.add(tramite);
		
		solicitud.setTramites(tramites);
		
		//Establecemos la persona interesada en el tramite
		PersonaInteresadaSolicitud personaIntSol = new PersonaInteresadaSolicitud();
		Fisica fisica = new Fisica();
		fisica.setIdPersona(nss.getIdPersona());
		personaIntSol.setPersona(fisica);
		TipoPerInteresadaSol tipoPersona = new TipoPerInteresadaSol();
		tipoPersona.setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
		personaIntSol.setTipoPersonaInteresadaSol(tipoPersona);
		
		solicitud.setPersonaInteresadaSolicitud(personaIntSol);
		
		solicitud = solicitudBusinessRemote.crear(solicitud);
		
		return solicitud;
		
	}

	/**
	 * @author Mario Teran Blanco
	 * Metodo para cambiar de estatus la solicitud cuando se atiendecorrectmente
	 * asi como marcar todos sus tramites como cerrados
	 * @param idSolicitud el id de la solicitud a ser cerrada
	 * @return void
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public void marcarAtendidaSolictud(Long idSolicitud, String observaciones, Fisica personaUsuario)
			throws DerechohabientesBusinessException {
		//Obtenemos la solicitud y su tramite
	
		Solicitud solicitud = new Solicitud(idSolicitud);
		solicitud.setObservacion(observaciones);

		try {
			solicitudBusinessRemote.actualizaAConcluidaDH(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			DerechohabientesBusinessException.throwException(ExceptionMessages.ERROR_ACTUALIZA_SOLICITUD,e.getSituacion());
		}
		
	}
	
	
	
	@Override
	public void cambiarEdoSolicitud(Long idSolicitud,
			EstadoSolicitudEnum estadoSol, EstadoTramiteEnum estadoTram,
			Boolean resultado, RazonResultadoEnum razonRes,
			String observaciones, Fisica personaUsuario)
			throws DerechohabientesBusinessException {
		// TODO Auto-generated method stub
		Solicitud solicitud = null;
		
		try {
			solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			solicitud = solicitudBusinessRemote.consultar(solicitud);
		} catch (Exception e1) {
			log.error("Error al consultar la solicitud", e1);
			DerechohabientesBusinessException.throwException(ExceptionMessages.ERROR_CONSULTA_SOLICITUD, "ocurrio un error al consultar la solicitud");
		}
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(estadoSol.getCodigo());
		solicitud.setFechaActualizacion(new Date());
		solicitud.setObservacion(observaciones);
		
		//Le cambiamos el estado a cada uno de los tramites relacionados con la solicitud
		for(Tramite tramite: solicitud.getTramites()) {
			
			tramite.setResultado(resultado);
			tramite.setRazonResultado(new RazonResultado());
			tramite.getRazonResultado().setIdRazonResultado(razonRes.getCodigo().longValue());
			tramite.setEstadoTramite(new EstadoTramite());
			tramite.getEstadoTramite().setIdEstadoTramitePersona(estadoTram.getCodigo());
			tramite.setFechaRegistroActualizacion(new Date());
			tramite.setObservacion(observaciones);
			
		}
		
		try {
			solicitudBusinessRemote.actualizarEstados(solicitud);
		} catch (Exception e1) {
			log.error("Error al consultar la solicitud", e1);
			DerechohabientesBusinessException.throwException(ExceptionMessages.ERROR_ACTUALIZA_SOLICITUD, "ocurrio un error al actualizar la solicitud");
		}
	}

	
	@Override
	public void marcarPendienteAutorizacion(Solicitud solicitud,
			String observaciones, Fisica personaUsuario)
			throws DerechohabientesBusinessException, Exception {
		
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		//Ponemos la fecha de actualizacion
		solicitud.setFechaActualizacion(new Date());
		//Colocamos las observaciones
		solicitud.setObservacion(observaciones);
		
		//Le cambiamos el estado a cada uno de los tramites relacionados con la solicitud
		for(Tramite tramite: solicitud.getTramites()) {
			
			//Colocamos los tramites en espera de autoriacion
			tramite.setEstadoTramite(new EstadoTramite());
			tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo());
			tramite.setRazonResultado(null);
			//Colocamos la fecha de actualizacion del tramite
			//tramite.setFechaRegistroActualizacion(new Date());
			//Colocamos las observaciones
			tramite.setObservacion(observaciones);
			
		}
		
		solicitudBusinessRemote.actualizarEstados(solicitud);
	}

	@Override
	public void marcarPendienteAutorizacion(Long idSolicitud,
			String observaciones, Fisica personaUsuario) throws Exception {
		
		//Obtenemos la solicitud y su tramite
		Solicitud solicitud = null;
		
		try {
			solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			this.marcarPendienteAutorizacion(solicitud, observaciones, personaUsuario);
		} catch (Exception e1) {
			log.error("Error al consultar la solicitud", e1);
			DerechohabientesBusinessException.throwException(ExceptionMessages.ERROR_CONSULTA_SOLICITUD, "ocurrio un error al consultar la solicitud");
		}
		
	}

	/**
	 * Metodo para rechazar una solicitud de baja recibiendo los siguientes parametros
	 * @param idSolicitud el id de la solicitud a rechazar
	 * @param idPersona el id de la persona involucrada en el tramite
	 * @param idTipoTramite el id del tipo del tramite
	 * @param idRechazo la razon por la cual se rechaza la solicitud
	 * @return Tramite el tramite que se cancelo de baja
	 */
	@Override
	public Tramite rechazarSolicitud(Long idSolicitud, Long idPersona,
			Long idTramite, Long idRechazo, String observaciones, Fisica usuario) throws DerechohabientesBusinessException {
		
		Tramite tramite = null;
		
		//Guardamos el rechazo de la solicitud
		try {
			rechazarSolicitud(idSolicitud, idRechazo, observaciones, usuario);
		} catch(Exception e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException("No pudo ser rechazada la solicitud: " + e.getCause().getMessage(),ExceptionMessages.ERROR_GUARDADO_SOLCITUD);
		}
		
		/*//Obtenemos el tramite cancelado
		try {
			tramite = solicitudDaoLocal.getTramite(idTramite, idPersona);
		} catch(Exception e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException("No fue encontrado el tramite" + e.getCause().getMessage(),ExceptionMessages.SIN_INFORMACION);
		}*/
		
		return tramite;
	}
	/**
	 * Obtiene la solicitud a partir de un folio
	 * @throws Exception 
	 */
	@Override
	public Solicitud getSolicitud(String folioSolicitud) throws DerechohabientesBusinessException, Exception {		
		
				
		Solicitud sol = null;
		sol = new Solicitud();
		sol.setNoFolioSolicitud(folioSolicitud);
		
		sol = solicitudBusinessRemote.consultarFolio(sol);
		
		if(sol == null){
			throw new DerechohabientesBusinessException(ExceptionMessages.SIN_FOLIO_SOLICITUD);
		}
			
		return sol;
	}

	@Override
	public Tramite getTramiteAutorizar(Long idTramite) throws Exception {		
		return solicitudDaoLocal.getTramite(idTramite);
	}

	@Override
	public void guardarTramiteDependienteCorreccion(Long idSolicidud,
			Long idPersona, TipoTramiteEnum tipoTramite, String observaciones) throws DerechohabientesBusinessException {

		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicidud);
		
		Tramite tramite = new Tramite();
		tramite.setRazonResultado(new RazonResultado());
		tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
		tramite.setPersona(new Fisica());
		tramite.getPersona().setIdPersona(idPersona);
		tramite.setEstadoTramite(new EstadoTramite());
		tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		tramite.setTipoTramite(new TipoTramite());
		tramite.getTipoTramite().setIdTipoTramite(tipoTramite.getCodigo());
		tramite.setFechaTramite(new Date());
		//tramite.setFechaRegistroAlta(new Date());
		tramite.setObservacion(observaciones);
		//TODO Verificar como afecta el hecho de que el tramite ya no contenga la solicitud
		//tramite.setSolicitud(solicitud);;
		
		//guardamos el folio y los tramites
		try {
			tramite = solicitudDaoLocal.saveTramite(tramite);
		} catch (Exception e) {
			DerechohabientesBusinessException.throwException("exception.tramite.errorGuardar", e.getCause().getMessage());
		}


	}

	@Override
	public TramiteProrroga recuperaProrrogaXML(Long idTramite)
			throws DerechohabientesBusinessException, Exception {
		
		TramiteProrroga prorroga = new TramiteProrroga();		
		Tramite miTramite = new Tramite();	
		
		miTramite = tramitePersonaFisicaDaoLocal.getTramiteInternet(idTramite);
		prorroga = (TramiteProrroga) JaxbUtil.xmlToObject(miTramite.getDetalleTramiteXml());
		
		Caracter miCaracter = catalogosDao.getCatalogoCaracter(prorroga.getCaracter().getIdCaracter());
		prorroga.getCaracter().setDescripcion(miCaracter.getDescripcion());
		return prorroga;
	}

	@Override
	public Boolean tramitesAdemasDeRegistro(Long idPersona, AsignacionNSS nss) {
		List<Tramite> tramites = null;
		List<Long> personas = new ArrayList<Long>();
		personas.add(idPersona);
		List<Long> tiposTramites = new ArrayList<Long>();
		tiposTramites.add(TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue());
		tiposTramites.add(TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo().longValue());
		tiposTramites.add(TipoTramiteEnum.REGISTRO_CONYUGUE.getCodigo().longValue());
		tiposTramites.add(TipoTramiteEnum.REGISTRO_HIJOS.getCodigo().longValue());
		tiposTramites.add(TipoTramiteEnum.REGISTRO_PADRES.getCodigo().longValue());
		tiposTramites.add(TipoTramiteEnum.REGISTRO_PENSIONADO.getCodigo().longValue());
		
		List<Long> estadosTramite = new ArrayList<Long>();
		estadosTramite.add(EstadoTramiteEnum.CERRADO.getCodigo().longValue());
		
		try {
			tramites = tramitePersonaFisicaDaoLocal.getTramitePersona(personas, tiposTramites, 
					estadosTramite, new Long(1), null, nss.getIdPersona(), false);
		} catch (Exception e) {
			log.error(e);
		}
		
		if(tramites.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public void actualizarXmlTramite(Tramite tramite) {
		try {
			solicitudBusinessRemote.actualizarXmlTramite(tramite);
		} catch (TramiteNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public long obtenerIdAsignacionNssPorNss(String nss) {
		long idAsignacionNss = -1;
		DitAsignacionNss ditAsignacionNss;
		
		try {
			if (nss.length() == 10) {
				log.info("Obteniendo Id de AsignacionNss por el NSS: "+nss);
				ditAsignacionNss = this.asignacionNssDao.getAsignacionNSSbyNSS(nss + DeltaUtils.generaDigitoVerificador(nss));
				
				if (ditAsignacionNss != null) {
					idAsignacionNss = ditAsignacionNss.getCveIdAsignacionNss();
				}
			}
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idAsignacionNss;
	}
	
	
}
