package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DescripcionesDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.SolicitudDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TramitePersonaFisicaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UMFTurno;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author mario.teran
 *
 */
@Stateless(name = "tramiteService", mappedName = "tramiteService")
public class TramiteService extends AbstractServiceBusiness implements TramiteServiceLocal,TramiteServiceRemote {

	
	@EJB(name = "solicitudDao") SolicitudDaoLocal solicitudDaoLocal;
	@EJB(name = "tramitePersonaFisicaDao") TramitePersonaFisicaDaoLocal tramitePersonaFisicaDaoLocal;
	@EJB(name = "grupoFamiliarDao") GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB(name = "agendarCitaService") AgendarCitaServiceLocal agendarCitaServiceLocal;
	@EJB(name = "descripcionesDao") DescripcionesDaoLocal descripcionesDaoLocal;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness") SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB CatalogosDaoLocal catalogosDaoLocal;
	
	
	@Override
	public Solicitud consultarSolicitud(Solicitud solicitud) throws SolicitudNoEncontradaException {
		// TODO Auto-generated method stub
		return solicitudBusinessRemote.consultar(solicitud);
	}

	@Override
	public void actualizarSolicitudAConcluida(Solicitud solicitud) throws SolicitudNoEncontradaException {
		solicitudBusinessRemote.actualizaAConcluida(solicitud);
	}

	@Override
	public void actualizaXMLTramite(Tramite tramite) throws TramiteNoEncontradoException, IllegalArgumentException {
		solicitudBusinessRemote.actualizarXmlTramite(tramite);
	}

	/**
	 * Metodo para saber si se puede o no relizar un tramite de acuerdo a la modalidad, el tipo de tramite y el parentesco
	 * @param idModalidad
	 * @param idParentesco
	 * @param idTipoTramite
	 */
	@Override
	public Boolean tramitePosiblePorModalidadParenteso(Long idModalidad,
			Long idParentesco, Long idTipoTramite){
		
		return null;
	}

	/**
	 * Metodo para guardar la baja de un derechohabiente recibiendo los siguientes parametros
	 * @param derechohabiente : GrupoFamiliar - Integrante que sera dado de baja
	 * @param tipoBaja: TipoTramiteEnum - Tipo del tramite que se guardar en este caso que tipo de baja
	 * @param usuario: Usuario - El usuario que esta haciendo la solicitud
	 * @param nss: AsignacionNSS - El asegurado cabeza de grupo familiar
	 * @param baja: BajaDerechohabiente - Objeto con los datos de la baja y que se guardara en el xml
	 * @return Solicitud - Solicitud guardada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud guardarBaja(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoBaja, Usuario usuario, AsignacionNSS nss,
			TramiteBajaDerechohabiente baja, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {
		baja.setPersona(derechohabiente.getDerechohabiente());
		Solicitud solicitud = this.guardarSolicitud(derechohabiente, tipoBaja, usuario, nss, TipoSolicitudEnum.BAJA, baja, origen);
		return solicitud;
	}

	/**
	 * Metodo para obtener el tramite de baja de derechohabiente recibiendo los siguientes parametros
	 * @param idTramite: Long - El id de la baja de derechohabiente
	 * @return BajaDerechohabiente
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public TramiteBajaDerechohabiente getBaja(Long idTramite)
			throws DerechohabientesBusinessException {
		// TODO Auto-generated method stub
		Tramite tramite = null;
		try {
			tramite = tramitePersonaFisicaDaoLocal.getTramite(idTramite);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.error("Error al recuperar el tramite de circunscipcion", e1);
			DerechohabientesBusinessException.throwException("error.busqueda.tramite", e1.getCause().getMessage());
		}
		
		TramiteBajaDerechohabiente baja = null;
		
		if(tramite != null) {
			baja = (TramiteBajaDerechohabiente) JaxbUtil.xmlToObject(tramite.getDetalleTramiteXml());
			//baja.setSolicitud(tramite.getSolicitud());
			baja.setTramiteId(tramite.getTramiteId());
			baja.setTipoTramite(tramite.getTipoTramite());
		}
		
		return baja;
	}
	
	/**
	 * Metodo para guardar la baja de un derechohabiente recibiendo los siguientes parametros
	 * @param derechohabiente : GrupoFamiliar - Integrante que sera dado de baja
	 * @param tipoCorreccion: TipoTramiteEnum - Tipo del tramite que se guardar en este caso que tipo de correccion
	 * @param usuario: Usuario - El usuario que esta haciendo la solicitud
	 * @param nss: AsignacionNSS - El asegurado cabeza de grupo familiar
	 * @param correccion: CorreccionDatoDerechohabiente - Objeto con los datos de la correccion y que se guardara en el xml
	 * @return Solicitud - Solicitud guardada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud guardarCorreccion(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {
		
		Solicitud solicitud = this.guardarSolicitud(derechohabiente, tipoTramite, usuario, nss, TipoSolicitudEnum.CORRECCION, correccion, origen);
		
		return solicitud;
	}
	
	@Override
	public Solicitud guardarCircunscripcion(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TramiteCircunscripcionForanea circunscripcion, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {
		
		Solicitud solicitud = this.guardarSolicitud(derechohabiente, tipoTramite, usuario, nss, TipoSolicitudEnum.CORRECCION, circunscripcion, origen);
		
		return solicitud;
	}

	/**
	 * Metodo para guardar la baja de un derechohabiente recibiendo los siguientes parametros
	 * @param derechohabiente : GrupoFamiliar - Integrante que sera dado de baja
	 * @param tipoCorreccion: TipoTramiteEnum - Tipo del tramite que se guardar en este caso que tipo de correccion
	 * @param usuario: Usuario - El usuario que esta haciendo la solicitud
	 * @param nss: AsignacionNSS - El asegurado cabeza de grupo familiar
	 * @param correccion: CorreccionDatoDerechohabiente - Objeto con los datos de la correccion y que se guardara en el xml
	 * @return Solicitud - Solicitud guardada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud guardarCorreccion(List<GrupoFamiliar> derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {
		
		Solicitud solicitud = guardarSolicitud(null,tipoTramite,usuario, nss,
				TipoSolicitudEnum.CORRECCION, correccion, origen,EstadoSolicitudEnum.REGISTRADA.getId(), EstadoTramiteEnum.INICIADO.getId(), true);
		
		return solicitud;
	}

	/**
	 * Metodo para obtener el tramite de correccion de datos de derechohabiente recibiendo los siguientes parametros
	 * @param idTramite: Long - El id de la correccion
	 * @return CorreccionDatoDerechohabiente
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public TramiteCorreccionDerechohabiente getCorreccion(Long idTramite)
			throws DerechohabientesBusinessException {
		
		Tramite tramite = null;
		try {
			tramite = tramitePersonaFisicaDaoLocal.getTramite(idTramite);
		} catch (Exception e1) {
			log.error("Error al recuperar el tramite de circunscipcion", e1);
			DerechohabientesBusinessException.throwException("error.busqueda.tramite", e1.getCause().getMessage());
		}
		
		if(tramite == null)
			DerechohabientesBusinessException.throwException("error.busqueda.tramite", "no se encontro el tramite");
		
		TramiteCorreccionDerechohabiente correccion = (TramiteCorreccionDerechohabiente) JaxbUtil.xmlToObject(tramite.getDetalleTramiteXml());
		//correccion.setSolicitud(tramite.getSolicitud());
		correccion.setTramiteId(tramite.getTramiteId());
		correccion.setTipoTramite(tramite.getTipoTramite());
		correccion.setObservacion(tramite.getObservacion());
		if(tramite.getPersonas() != null)
		{
			if(tramite.getPersonas().size() > 0)
				correccion.setPersonas(tramite.getPersonas());
		}
		
		return this.getDescripcionesCorreccion(correccion);
	}

	
	@Override
	public TramiteCircunscripcionForanea getCircunscripcion(Long idTramite)
			throws DerechohabientesBusinessException {
		Tramite tramite = null;
		try {
			tramite = tramitePersonaFisicaDaoLocal.getTramite(idTramite);
		} catch (Exception e1) {
			log.error("Error al recuperar el tramite de circunscipcion", e1);
			DerechohabientesBusinessException.throwException("error.busqueda.tramite", e1.getCause().getMessage());
		}
		TramiteCircunscripcionForanea circunscripcion = (TramiteCircunscripcionForanea) JaxbUtil.xmlToObject(tramite.getDetalleTramiteXml());
		circunscripcion.setTramiteId(tramite.getTramiteId());
		circunscripcion.setTipoTramite(tramite.getTipoTramite());
		
		return circunscripcion;
	}

	/**
	 * Metodo para actualizar una solicitud y el xml del tramite recibiendo los siguientes parametros
	 * @param idSolicitud - Long
	 * @param estadoSolicitud - EstadoSolicitudEnum
	 * @param estadoTramite - EstadoTramiteEnum
	 * @param resultado - Boolean
	 * @param razonResultado - RazonResultadoEnum
	 * @param observaciones - String
	 * @param tramiteb - Tramite: cualquier objeto que estienda de tramite para poder actuaizar el xml
	 */
	@Override
	public void actualizarSolicitud(Long idSolicitud, EstadoSolicitudEnum estadoSolicitud,EstadoTramiteEnum estadoTramite,Boolean resultado, RazonResultadoEnum razonResultado,
			String observaciones, Tramite tramiteb, Fisica personaUsuario) throws DerechohabientesBusinessException {
		
		//Obtenemos la solicitud y su tramite
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		
		try {
			solicitud = solicitudBusinessRemote.consultar(solicitud);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.error("No pudo localizar la solicitud", e1);
			throw new DerechohabientesBusinessException(e1.getCause().getMessage(), "error.busqueda.solicitud");
		}
		
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(estadoSolicitud.getId().intValue());
		solicitud.setFechaActualizacion(new Date());
		solicitud.setObservacion(observaciones);

		Usuario usuario = new Usuario();
		usuario.setFisica(personaUsuario);
		
		solicitud.setSolicitante(usuario);
		
		//Le cambiamos el estado a cada uno de los tramites relacionados con la solicitud
		for(Tramite tramite: solicitud.getTramites()) {

			tramite.setResultado(resultado);
			
			if(razonResultado == null)
				tramite.setRazonResultado(null);
			else{
				tramite.setRazonResultado(new RazonResultado());
				tramite.getRazonResultado().setIdRazonResultado(razonResultado.getId());
			}
			tramite.setEstadoTramite(new EstadoTramite());
			tramite.getEstadoTramite().setIdEstadoTramitePersona(Integer.valueOf(""+estadoTramite.getId()));
			tramite.setFechaRegistroActualizacion(new Date());
			tramite.setObservacion(observaciones);
			
			if(tramiteb instanceof TramiteBajaDerechohabiente) {
				TramiteBajaDerechohabiente nuevosDatos = (TramiteBajaDerechohabiente) tramiteb;
				if(tramite instanceof TramiteBajaDerechohabiente) {
					TramiteBajaDerechohabiente tramiteAnterior = (TramiteBajaDerechohabiente) tramite;
					tramiteAnterior.setFechaDefuncion(nuevosDatos.getFechaDefuncion());
					tramiteAnterior.setObservaciones(nuevosDatos.getObservaciones());
				}
			}
			
			/*
			//Verificamos de que tipo es el tramite para convertirlo al xml
			if(tramiteb instanceof TramiteBajaDerechohabiente )
				tramite.setDetalleTramiteXml(JAXB_UTIL.objectToXml((TramiteBajaDerechohabiente) this.covertirTramite(tramiteb, tramite)));
			else if(tramiteb instanceof TramiteCorreccionDerechohabiente)
				tramite.setDetalleTramiteXml(JAXB_UTIL.objectToXml((TramiteCorreccionDerechohabiente) this.covertirTramite(tramiteb, tramite)));
			else if(tramiteb instanceof TramiteCircunscripcionForanea)
				tramite.setDetalleTramiteXml(JAXB_UTIL.objectToXml((TramiteCircunscripcionForanea) this.covertirTramite(tramiteb, tramite)));*/
		}
		
		try {
			solicitudBusinessRemote.actualizarEstados(solicitud);
			solicitudBusinessRemote.actualizarTramites(solicitud);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.error("Error al actualizar tramite", e1);
			DerechohabientesBusinessException.throwException("error.actualizar.tramite", e1.getCause().getMessage());
		}
	}
	
	/**
	 * Metodo para crear y guardar una solicitud recibiendo los siguientes parametros
	 * @param derechohabiente - GrupoFamiliar : el integrante que sera afectado por el tramite
	 * @param tipoTramite - TipoTramiteEnum : el tipo del tramite
	 * @param usuario - Usuario : el usuario que esta realizando la solicitud
	 * @param nss - AsignacionNSS : El asegurado cabeza de grupo familiar
	 * @param tipoSolicitud - TipoSolicitudEnum - El tipo de la solicitud
	 * @param tramite - Tramite : Objeto que extiende de tramite para guardarlo como xml
	 * @return Solicitud
	 */
	@Override
	public Solicitud guardarSolicitud(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TipoSolicitudEnum tipoSolicitud, Tramite tramite, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException{
		
		//invocamos el metodo generico que guarda la solicitud
		return guardarSolicitud(derechohabiente, tipoTramite, usuario, nss, tipoSolicitud, tramite, origen, 
				EstadoSolicitudEnum.REGISTRADA.getId(), EstadoTramiteEnum.INICIADO.getId(), false);
		
	}
 	
	@Override
	public Solicitud guardarSolicitudEstado(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TipoSolicitudEnum tipoSolicitud, Tramite tramite,
			OrigenSolicitudEnum origen, Long idEstadoSolicitud,
			Long idEstadoTramite) throws DerechohabientesBusinessException {
		//invocamos el metodo generico que guarda la solicitud
		return guardarSolicitud(derechohabiente, tipoTramite, usuario, nss, tipoSolicitud, tramite, origen, idEstadoSolicitud, idEstadoTramite, false);
	}

	/**
	 * Metodo para crear y guardar una solicitud recibiendo los siguientes parametros
	 * @param derechohabiente - GrupoFamiliar : el integrante que sera afectado por el tramite
	 * @param tipoTramite - TipoTramiteEnum : el tipo del tramite
	 * @param usuario - Usuario : el usuario que esta realizando la solicitud
	 * @param nss - AsignacionNSS : El asegurado cabeza de grupo familiar
	 * @param tipoSolicitud - TipoSolicitudEnum - El tipo de la solicitud
	 * @param tramite - Tramite : Objeto que extiende de tramite para guardarlo como xml
	 * @return Solicitud
	 */
	@Override
	public Solicitud guardarSolicitudConInfoTramite(TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TipoSolicitudEnum tipoSolicitud, Tramite tramite, OrigenSolicitudEnum origenSol) throws DerechohabientesBusinessException{
		
		return guardarSolicitud(null,tipoTramite, usuario, nss, tipoSolicitud, tramite, origenSol,
				EstadoSolicitudEnum.REGISTRADA.getId(), EstadoTramiteEnum.INICIADO.getId(), true);
		
	}

	@Override
	public Tramite saveTramiteCorreccionDependiente(
			Tramite correccion, Long idSolicitud)
			throws DerechohabientesBusinessException {

		Date fechaConclusion = new Date();
		Long idEstado = EstadoTramiteEnum.CERRADO.getId();
		
		correccion.setResultado(true);
		correccion.setRazonResultado(new RazonResultado());
		correccion.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getId());
		correccion.setEstadoTramite(new EstadoTramite());
		correccion.getEstadoTramite().setIdEstadoTramitePersona(idEstado.intValue());
		correccion.setFechaTramite(fechaConclusion);
		correccion.setFechaPresentacion(fechaConclusion);
		correccion.setFechaConclusion(fechaConclusion);
		
		if(!(correccion instanceof TramiteCorreccionDerechohabiente) && !(correccion instanceof TramiteCircunscripcionForanea))
			DerechohabientesBusinessException.throwException("Parametros incorrectos",ExceptionMessages.ERROR_GUARDADO_SOLCITUD);
		
		//guardamos el folio y los tramites
		try {
			if(correccion.getTramiteId() != null) {
				log.debug("Se asocia la persona al tramite");
				solicitudBusinessRemote.agregarPersonaATramite(correccion.getTramiteId(), correccion.getPersona().getIdPersona());
			} else{
				log.debug("Se crea un nuevo tramite de: " + correccion.getTipoTramite().getIdTipoTramite());
				correccion = solicitudBusinessRemote.crearTramiteASolicitud(correccion, idSolicitud);
			}
		} catch(Exception e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException("Error al guardar la solicitud" + e.getCause().getMessage(), ExceptionMessages.ERROR_GUARDADO_SOLCITUD);
		}
		

		return correccion;
		
	}

	
	/**
	 * Este metodo se encarga de guardar el tramite y solicitud de prorroga
	 * @author juan.osorioal
	 * @param derechohabiente
	 * @param tipoBaja
	 * @param usuario
	 * @param nss
	 * @param tramite
	 * @param origen
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud guardarProrroga(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoBaja, Usuario usuario, AsignacionNSS nss,
			TramiteProrroga tramite, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {
		tramite.setPersona(derechohabiente.getDerechohabiente());
		Solicitud solicitud = this.guardarSolicitud(derechohabiente, tipoBaja, usuario, nss, TipoSolicitudEnum.PRORROGA, tramite, origen);
		return solicitud;
	}
	
	private Solicitud guardarSolicitud(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TipoSolicitudEnum tipoSolicitud, Tramite tramite, OrigenSolicitudEnum origen,
			Long idEstadoSolicitud, Long idEstadoTramite, boolean conDatosTramite) throws DerechohabientesBusinessException {
		//Creamos la solicitud a guardar
		Solicitud solicitud = new Solicitud();
		OrigenSolicitud origenSolicitud = new OrigenSolicitud();
		origenSolicitud.setIdTipoSolicitud(origen.getId());
		UMFTurno umfTurnoSol = null;
		//Colocamos todos los estados necesarios de la nueva solicitud
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(idEstadoSolicitud.intValue());
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(tipoSolicitud.getId());
		solicitud.setFechaSolicitud(new Date());
		solicitud.setFechaCita(new Date());
		solicitud.setSolicitante(usuario);
		solicitud.setFechaPresentacion(new Date());
		
		if(idEstadoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getId())) {
			solicitud.setFechaConclusion(new Date());
		}
		
		/*
		solicitud.setAseguradoPensionado(new Persona());
		solicitud.getAseguradoPensionado().setIdPersona(nss.getIdPersona());*/
		solicitud.setObservacion(tramite.getObservacion());
		//obtenemos el perfil del usuario para verificar si se genera cita o no
		//Long perfilUsuario = usuario.getPerfilUsuario().getIdPerfilUsuario();
		
		if(tramite instanceof TramiteCorreccionDerechohabiente) {
			TramiteCorreccionDerechohabiente correccionTram = ((TramiteCorreccionDerechohabiente) tramite);
			
			if(correccionTram.getMedicoEnTurno() != null) {
				umfTurnoSol = new UMFTurno();
				
				if( correccionTram.getMedicoEnTurno().getTurno() != null )
					umfTurnoSol.setTurno(correccionTram.getMedicoEnTurno().getTurno());
				
				if( correccionTram.getMedicoEnTurno().getUnidadMedicaFamiliar() != null )
					umfTurnoSol.setUnidadMedicaFamiliar(correccionTram.getMedicoEnTurno().getUnidadMedicaFamiliar());
			}
			
		} else if(tramite instanceof TramiteCircunscripcionForanea) {
			TramiteCircunscripcionForanea circuns = (TramiteCircunscripcionForanea) tramite;
			
			if(circuns.getMedicoEnTurnoDestino() != null) {
				umfTurnoSol = new UMFTurno();
				umfTurnoSol.setTurno(circuns.getMedicoEnTurnoDestino().getTurno());
				umfTurnoSol.setUnidadMedicaFamiliar(circuns.getMedicoEnTurnoDestino().getUnidadMedicaFamiliar());
			}
		} else if (derechohabiente != null && !conDatosTramite){
			umfTurnoSol = new UMFTurno();
			umfTurnoSol.setTurno(derechohabiente.getMedicoEnTurno().getTurno());
			umfTurnoSol.setUnidadMedicaFamiliar(derechohabiente.getMedicoEnTurno().getUnidadMedicaFamiliar());
		}
		
		
		//establecemos los datos de donde estamos generando la solicitud, para que de ner necesario
		//que se autoriza la podamos encontrar
		CitaSolicitud cita = new CitaSolicitud();
		if (umfTurnoSol != null){
			cita.setUmf(umfTurnoSol.getUnidadMedicaFamiliar());
			cita.setTurno(umfTurnoSol.getTurno());
		}
		
		//En caso se que el usuario venga nulo y el origen de la solicitud sea internet seteamos el curp del asegurado
		if(usuario == null || StringUtils.isBlank(usuario.getUsuario())) {
			if(origen.getId().equals(OrigenSolicitudEnum.INTERNET.getId())) {
				Persona personaTramite = tramite.getPersona();
				
				if(personaTramite != null && personaTramite instanceof Derechohabiente) {
					solicitud.setSolicitante(new Usuario());
					solicitud.getSolicitante().setUsuario(((Derechohabiente)personaTramite).getAsignacionNSS().getCurp());
				}
			}
		}
	
		solicitud.setCitaSolicitud(cita);
		solicitud.setOrigenSolicitud(origenSolicitud);
		//Creamos el tramite para agregarlo a la solicitud con sus respectivos estados
		List<Tramite> tramites = new ArrayList<Tramite>();
		
		tramite.setRazonResultado(new RazonResultado());
		tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getId());
		if (derechohabiente != null && !conDatosTramite){
			tramite.setPersona(derechohabiente.getDerechohabiente());
		}
		tramite.setEstadoTramite(new EstadoTramite());
		tramite.getEstadoTramite().setIdEstadoTramitePersona(idEstadoTramite.intValue());
		TipoTramite tipoTram = catalogosDaoLocal.getTipoTramite(tipoTramite.getCodigo().longValue());
		tramite.setTipoTramite(tipoTram);
		tramite.setFechaTramite(new Date());
		tramite.setFechaPresentacion(new Date());
		tramites.add(tramite);
		
		solicitud.setTramites(tramites);
		PersonaInteresadaSolicitud personaIntSol = new PersonaInteresadaSolicitud();
		Fisica fisica = new Fisica();
		fisica.setIdPersona(nss.getIdPersona());
		personaIntSol.setPersona(fisica);
		TipoPerInteresadaSol tipoPersona = new TipoPerInteresadaSol();
		tipoPersona.setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
		personaIntSol.setTipoPersonaInteresadaSol(tipoPersona);
		
		solicitud.setPersonaInteresadaSolicitud(personaIntSol);
		
		try{
			solicitud = solicitudBusinessRemote.crear(solicitud);
		} catch(Exception e) {
			e.printStackTrace();
			String error = "Error al guardar la solicitud (Origen : "+origen.getDesc()+" IdAsignacion: "+nss.getIdAsignacionNSS();
			if(derechohabiente != null) {
				error += "; idPersona: "+derechohabiente.getDerechohabiente().getIdPersona();
			}
			error += " TipoTramite: "+tipoTram.getDescripcion()+")"+ e.getCause().getMessage();
			DerechohabientesBusinessException.throwException(error, ExceptionMessages.ERROR_GUARDADO_SOLCITUD);
		}
		
		return solicitud;
	}

	/**
	 * MEtodo para poner las descripiciondes de un tramite e tipo correccion
	 * @param correccion
	 * @return
	 */
	private TramiteCorreccionDerechohabiente getDescripcionesCorreccion(TramiteCorreccionDerechohabiente correccion) throws DerechohabientesBusinessException{
		
		if(correccion.getSexo() != null)
			correccion.setSexo(descripcionesDaoLocal.getSexo(correccion.getSexo().getIdSexo().longValue()));
		if(correccion.getParentesco() != null)
			correccion.setParentesco(descripcionesDaoLocal.getParentesco(correccion.getParentesco().getIdParentesco()));
		if(correccion.getEstadoCivil() != null)
			correccion.setEstadoCivil(descripcionesDaoLocal.getEstadoCivil(correccion.getEstadoCivil().getIdEstadoCivil().longValue()));
		if(correccion.getLugarNacimiento() != null)
			correccion.setLugarNacimiento(descripcionesDaoLocal.getEntidadFederativa(correccion.getLugarNacimiento().getClave()));
		if(correccion.getDomicilio() != null)
			correccion.setDomicilio(this.getDescripcionesDomicilio(correccion.getDomicilio()));
		if(correccion.getMedicoEnTurno() != null)
			correccion.setMedicoEnTurno(descripcionesDaoLocal.getMedicoEnTurno(correccion.getMedicoEnTurno()));
		
		return correccion;
	}
	
	/**
	 * Metodo para obtener las descripciones de un objeto de tipo domicilio
	 * @param domicilio
	 * @return
	 */
	private Domicilio getDescripcionesDomicilio(Domicilio domicilio) throws DerechohabientesBusinessException{
		
		if(domicilio.getAsentamiento() != null)
			domicilio.setAsentamiento(descripcionesDaoLocal.getAsentamiento(domicilio.getAsentamiento()));
		if(domicilio.getVialidadPrimaria() != null)
			domicilio.setVialidadPrimaria(descripcionesDaoLocal.getVialidad(domicilio.getVialidadPrimaria().getClave()));
		if(domicilio.getVialidadReferenciaPrimaria() != null)
			domicilio.setVialidadReferenciaPrimaria(descripcionesDaoLocal.getVialidad(domicilio.getVialidadReferenciaPrimaria().getClave()));
		if(domicilio.getVialidadReferenciaSecundaria() != null)
			domicilio.setVialidadReferenciaSecundaria(descripcionesDaoLocal.getVialidad(domicilio.getVialidadReferenciaSecundaria().getClave()));
		if(domicilio.getVialidadReferenciaPosterior() != null)
			domicilio.setVialidadReferenciaPosterior(descripcionesDaoLocal.getVialidad(domicilio.getVialidadReferenciaPosterior().getClave()));
		
		return domicilio;
	}
}