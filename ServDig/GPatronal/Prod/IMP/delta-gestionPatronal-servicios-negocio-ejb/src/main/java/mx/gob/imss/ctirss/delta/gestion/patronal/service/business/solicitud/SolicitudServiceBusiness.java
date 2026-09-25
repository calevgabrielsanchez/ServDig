/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.solicitud;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.SujetoObligadoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.ClasificacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud.SolicitudServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovtoPatSujetoObligadoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.GestionPatronalRol;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RolEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.MovtoPatSujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.tramite.service.entity.TramiteServiceEntityLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart�nez Cham�nica
 * @Proyecto: delta
 * @Archivo: SolicitudServiceBusiness.java
 * @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.business.solicitud
 * @Fecha: 10:23:33
 */
@Stateless(name = "solicitudServiceBusiness", mappedName = "solicitudServiceBusiness")
public class SolicitudServiceBusiness extends AbstractServiceBusiness implements
		SolicitudServiceBusinessRemote, SolicitudServiceBusinessLocal {

    private static final Logger log = LoggerFactory.getLogger(SolicitudServiceBusiness.class);

	@EJB
	private TramiteServiceEntityLocal tramiteEntity;

	@EJB
	private SolicitudBusinessRemote solicitudService;

	@EJB
	private SujetoObligadoServiceEntityLocal sujetoObligadoEntity;
	
	@EJB
	SolicitudServiceEntityLocal solicitudEntity;
	
	@EJB 
	SujetoObligadoServiceBusinessRemote sujetoObligadoService;

	@EJB
	AfiliacionServiceBusinessRemote afiliacionservice;
	
	@EJB
	private ClasificacionServiceEntityLocal claseEntity;

    @EJB
    private MovtoPatSujetoObligadoBusinessRemote movtoPatSujetoObligadoBusiness;
    
    @EJB
	SolicitudServiceBusinessLocal solicitudBusinessService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud
	 * .SolicitudServiceBusinessRemote
	 * #crearNuevaSolicitud(mx.gob.imss.ctirss.delta
	 * .model.gestion.patronal.Solicitud)
	 */
	@Override
	public Solicitud crearNuevaSolicitud(Solicitud solicitud) {
		try {
			return solicitudService.crear(solicitud);
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Genera una nueva solicitud con un tr�mite asociado.
	 * 
	 * @author Hugo Martinez
	 * 
	 */
	public Solicitud generarSolicitud(TipoSolicitudEnum tipoSolicitud,
			EstadoSolicitudEnum estadoSolicitud, Usuario usuario,
			TipoTramiteEnum tipoTramite, EstadoTramiteEnum estadoTramite,
			SujetoObligado sujetoObligado, boolean reintentoRPC, boolean rpcInvalido)
			throws GestionPatronalBusinessException {
		
//		if(!tipoTramite.equals(TipoTramiteEnum.CLASIFICACION_ANEXO_V))
//				validaTipoTramiteUnico(tipoTramite, sujetoObligado);
		
		if(!tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION) &&  !tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO))
			validaTipoTramiteUnico(tipoTramite, sujetoObligado);
		
		Solicitud solicitud = construirSolicitud(tipoSolicitud,
				estadoSolicitud, usuario);
		TramiteSujetoObligado tramite = construirTramite(tipoTramite,
				estadoTramite, sujetoObligado);
		
		if(tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION) || 
				tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO)){
			Subdelegacion subdelegacion=sujetoObligadoService.obtenerSubdelegacion(sujetoObligado.getCveIdSujetoObligado());
			solicitud.setSubdelegacion(subdelegacion);
			if(sujetoObligado.getClasificacion()!=null){
				tramite.setFechaEfecto(sujetoObligado.getClasificacion().getFecEfecto());
				tramite.setFechaPresentacion(sujetoObligado.getClasificacion().getFecPresentacion());
			}
		}
        fillFecPresentacionAltas(tipoSolicitud, sujetoObligado, tramite);
		
		List<Tramite> tramites = new ArrayList<Tramite>();
		tramites.add(tramite);
		solicitud.setTramites(tramites);
		try {
			solicitud=solicitudService.crear(solicitud);
			solicitud.setIndReintentoRpc(reintentoRPC);
			solicitud.setIndRpcInvalido(rpcInvalido);
			claseEntity.crearActualizarReintentoRPC(solicitud);
//			if(!estadoTramite.equals(EstadoTramiteEnum.INICIADO)){
//				actualizarSolicitud(solicitud.getSolicitudId(), estadoTramite, sujetoObligado);
//			}
			
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
		}
		System.out.print("SUJETO OBLIGADO SERVICE ID_SOLICITUD: "
				+ solicitud.getSolicitudId());
		System.out.print("SUJETO OBLIGADO SERVICE ID_TRAMITE: "
				+ solicitud.getTramites().get(0).getTramiteId());
		
		return solicitud;
	}

    private void fillFecPresentacionAltas(TipoSolicitudEnum tipoSolicitud, SujetoObligado sujetoObligado, TramiteSujetoObligado tramite) {
        if (TipoSolicitudEnum.ALTA_PATRONAL.equals(tipoSolicitud)) {
            log.debug("Tipo de solicitud: ALTA_PATRONAL");
            MovtoPatSujetoObligado movimiento = movtoPatSujetoObligadoBusiness.getMovtoPatSujetoObligadoFromRegPatornal(sujetoObligado.getNumeroRegistroPatronal());
            tramite.setFechaPresentacion(movimiento.getFecMovimiento());
            tramite.setFechaEfecto(movimiento.getFecMovimiento());
        }
    }

	/**
	 * Inserta una nueva solicitud del tipo y con el estado proporcionados
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param tipoSolicitud
	 * @param estadoSolicitud
	 * @param usuario
	 * @return Solicitud
	 */
	private Solicitud construirSolicitud(TipoSolicitudEnum tipoSolicitud,
			EstadoSolicitudEnum estadoSolicitud, Usuario usuario) {
		System.out.println("Iniciando la creaci�n de  la solicitud");
		Solicitud solicitud = new Solicitud();
		EstadoSolicitud estado = new EstadoSolicitud();
		estado.setIdEstadoSolicitud(estadoSolicitud.getCodigo());
		solicitud.setEstadoSolicitud(estado);
		solicitud.setFechaSolicitud(Calendar.getInstance().getTime());
		TipoSolicitud tipo = new TipoSolicitud();
		tipo.setIdTipoSolicitud(tipoSolicitud.getValor().longValue());
		solicitud.setTipoSolicitud(tipo);
		solicitud.setSolicitante(usuario);

		log.debug("Terminando de crear la solicitud");
		return solicitud;
	}

	/**
	 * Inicializa un objeto de tramites para modificaci�n de datos de registros patronales.
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param idPersona
	 * @param solicitud
	 * @param tipoPersona
	 * @return Tramite
	 */
	@Override
	public TramiteSujetoObligado construirTramite(TipoTramiteEnum tipo,
			EstadoTramiteEnum estado, SujetoObligado sujetoObligado) {
		TramiteSujetoObligado tramite = new TramiteSujetoObligado();
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(estado.getCodigo());
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(tipo.getCodigo());
		agregarRegistroPatronal(sujetoObligado);
		System.out.println("Registro Patronal Tramite: "
				+ sujetoObligado.getNumeroRegistroPatronal());
		tramite.setSujetoObligado(sujetoObligado);

		tramite.setEstadoTramite(estadoTramite);
		tramite.setTipoTramite(tipoTramite);
		// tramite.setSolicitud(solicitud);

		// Persona personaTramite = new Persona();
		// personaTramite.setIdPersona(idPersona);
		// tramite.setPersonaTramite(personaTramite);

		// tramiteService.crearNuevotramite(tramite, tipoPersona);
		return tramite;

	}

	/**
	 * Valida si existe un tr�mite activo(con estatus distinto a finalizado)
	 * asociado al patr�n, si este es el caso se notifica mediante una excepci�n
	 * de negocio
	 * 
	 * @Date 14/06/2012
	 * @param tipo
	 * @param sujetoObligado
	 */
	@Override
	public void validaTipoTramiteUnico(TipoTramiteEnum tipo,
			SujetoObligado sujetoObligado)
			throws GestionPatronalBusinessException {
		boolean existe = tramiteEntity
				.existeTramiteActivoPorSujetoObligadoYTipo(tipo,
						sujetoObligado.getCveIdSujetoObligado());

		if (existe) {
			throw new GestionPatronalBusinessException(
					"Actualmente existe un tr�mite del mismo tipo en curso, "
							+ "por favor espere a que el tr�mite sea conclu�do para iniciar uno nuevo");
		}
	}

	@Override
	public Solicitud consultarSolicitudPorId(Long idSolicitud) {
		Solicitud sol = new Solicitud();
		sol.setSolicitudId(idSolicitud);

		try {
			sol = solicitudService.consultar(sol);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}

		return sol;
	}

	@Override
	public Solicitud actualizarSolicitud(Solicitud solicitud,
			EstadoTramiteEnum estadoTramite, SujetoObligado sujetoObligado) {
			Solicitud solicitudActual = new Solicitud();
		try {
			solicitudActual.setSolicitudId(solicitud.getSolicitudId());
			solicitudActual = solicitudService.consultar(solicitudActual);
			solicitudActual.setObservacion(solicitud.getObservacion());
			solicitudActual.setFechaPresentacion(solicitud.getFechaPresentacion());
			TramiteSujetoObligado tso = (TramiteSujetoObligado) solicitudActual
					.getTramites().get(0);
			tso.getEstadoTramite().setIdEstadoTramitePersona(
					estadoTramite.getCodigo());
			if(sujetoObligado!= null){
				agregarRegistroPatronal(sujetoObligado);
				tso.setSujetoObligado(sujetoObligado);
				
				if(solicitudActual.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().longValue())
					|| solicitudActual.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue())	
					|| solicitudActual.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ALTA_PATRONAL.getValor().longValue())){
					tso.setFechaEfecto(sujetoObligado.getClasificacion().getFecEfecto());
					tso.setFechaPresentacion(sujetoObligado.getClasificacion().getFecPresentacion());
				}
				solicitudActual.getTramites().set(0, tso);
			}
			
			if (solicitud.getOrigenSolicitud() != null) {
				solicitudActual.setOrigenSolicitud(solicitud.getOrigenSolicitud());
			}
			
			solicitudService.actualizarTramites(solicitudActual);
			if (estadoTramite.equals(EstadoTramiteEnum.CERRADO)) {
				if(solicitudActual.getFechaCita()==null)
					solicitudActual.setFechaCita(Calendar.getInstance().getTime());
				solicitudActual.setFechaConclusion(Calendar.getInstance().getTime());
				solicitudActual.getEstadoSolicitud().setIdEstadoSolicitud(
						EstadoSolicitudEnum.ATENDIDA.getCodigo());
			}
			if (estadoTramite.equals(EstadoTramiteEnum.ACTIVO)) {
				solicitudActual.setFechaCita(Calendar.getInstance().getTime());
				if(tso.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo())){
					EstadoSolicitudEnum estadoSolicitudParaAsignar = null; 
					if(solicitudActual.getEstadoSolicitud().getIdEstadoSolicitud().equals(
							EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo()) || 
							solicitudActual.getEstadoSolicitud().getIdEstadoSolicitud().equals(
									EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo())){
						log.debug("Se conserva el estado");
					}else if(solicitudActual.getEstadoSolicitud().getIdEstadoSolicitud().equals(
							EstadoSolicitudEnum.REGISTRADA.getCodigo())){
						estadoSolicitudParaAsignar = EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA;
						
						if(solicitud.getEstadoSolicitud()!=null && solicitud.getEstadoSolicitud().getIdEstadoSolicitud()!=null){
							System.err.println("Este a obtener estado por id de enum");
							estadoSolicitudParaAsignar = EstadoSolicitudEnum.obternerEnumById(solicitud.getEstadoSolicitud().getIdEstadoSolicitud());
						}else if(solicitud.getSolicitante().getPerfilUsuario().getIdPerfilUsuario().equals(RolEnum.TRAMITADOR.getCodigo().longValue())){
							estadoSolicitudParaAsignar = EstadoSolicitudEnum.EDICION_VENTANILLA;
						}
					}else{
						estadoSolicitudParaAsignar = EstadoSolicitudEnum.PENDIENTE_AUTORIZACION;
					}
					
					if (estadoSolicitudParaAsignar==null)
						estadoSolicitudParaAsignar = EstadoSolicitudEnum.PENDIENTE_AUTORIZACION;
					System.err.println("estadoAsignar: "+estadoSolicitudParaAsignar);
					solicitudActual.getEstadoSolicitud().setIdEstadoSolicitud(
							estadoSolicitudParaAsignar.getCodigo());
					
				}else{
					solicitudActual.getEstadoSolicitud().setIdEstadoSolicitud(
						EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
				}
			}

			solicitudService.actualizarEstados(solicitudActual);
			solicitudBusinessService.actualizarDatosGeneralesDeSolicitud(solicitudActual);
			claseEntity.crearActualizarReintentoRPC(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
			solicitudActual = null;
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
			solicitudActual = null;
		}
		return solicitudActual;
	}

	@Override
	public void cancelarSolicitud(Long idSolicitud) throws SolicitudException {
		Solicitud solicitudActual = new Solicitud();
		solicitudActual.setSolicitudId(idSolicitud);
		try {
			solicitudActual = solicitudService.consultarSinDatosTramite(solicitudActual);
			
			if(solicitudActual.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo()))
				throw new SolicitudException("error.solicitud.concluida.previamente");
			
			solicitudActual.getEstadoSolicitud().setIdEstadoSolicitud(
				EstadoSolicitudEnum.CANCELADA.getCodigo());	
			
			for(Tramite tramite : solicitudActual.getTramites()){
				tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CANCELADO.getCodigo());
			}
			
			solicitudService.actualizarEstados(solicitudActual);

		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		} catch (TramiteNoEncontradoException e){
			e.printStackTrace();
		}
		
	}

	private void agregarRegistroPatronal(SujetoObligado sujetoObligado) {
		// Se agrega el registro patronal a todos los tr�mites de sujeto
		// obligado
		SujetoObligado sujetoObligadoAux = new SujetoObligado();
		sujetoObligadoAux.setTipoPersonaFiscal(sujetoObligado
				.getTipoPersonaFiscal());
		sujetoObligadoAux.setCveIdSujetoObligado(sujetoObligado
				.getCveIdSujetoObligado());
		sujetoObligadoAux = sujetoObligadoEntity
				.consultarDetalleSujetoObligado(sujetoObligadoAux);

		sujetoObligado.setNumeroRegistroPatronal(sujetoObligadoAux
				.getNumeroRegistroPatronal());
		sujetoObligado.setModalidad(sujetoObligadoAux.getModalidad());
		sujetoObligado.setDigVerificador(sujetoObligadoAux.getDigVerificador());
	}

	@Override
	public void validaTiposTramiteUnicos(List<TipoTramiteEnum> tipos,
			SujetoObligado sujetoObligado)
			throws GestionPatronalBusinessException {
		
		boolean existe = tramiteEntity
				.existeTramitesActivoPorSujetoObligadoYTipos(tipos,
						sujetoObligado.getCveIdSujetoObligado(), null);

		if (existe) {
			throw new GestionPatronalBusinessException(
					"Actualmente existe un tr�mite del mismo tipo en curso, "
							+ "por favor espere a que el tr�mite sea conclu�do para iniciar uno nuevo");
		}
	}

	@Override
	public boolean existeTramitesClasificacionActivos(
			Long cveIdPatronSujetoObligado, boolean esTramitador) {
		
		List<TipoTramiteEnum> listaTramitesClasificacion = new ArrayList<TipoTramiteEnum>();
		listaTramitesClasificacion.add(TipoTramiteEnum.ACTIVIDAD_ECONOMICA);
		listaTramitesClasificacion.add(TipoTramiteEnum.DISPOSICION_DE_LEY);
		listaTramitesClasificacion.add(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES);
		listaTramitesClasificacion.add(TipoTramiteEnum.COMPRA_DE_ACTIVOS);
		listaTramitesClasificacion.add(TipoTramiteEnum.COMODATO);
		listaTramitesClasificacion.add(TipoTramiteEnum.ENAJENACION);
		listaTramitesClasificacion.add(TipoTramiteEnum.ARRENDAMIENTO);
		listaTramitesClasificacion.add(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO);
		listaTramitesClasificacion.add(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO);
		
		List<Long> listaEstadosInvalidos = new ArrayList<Long>();
		listaEstadosInvalidos.add(EstadoTramiteEnum.CANCELADO.getCodigo().longValue());
		listaEstadosInvalidos.add(EstadoTramiteEnum.CERRADO.getCodigo().longValue());
		if(esTramitador)
			listaEstadosInvalidos.add(EstadoTramiteEnum.INICIADO.getCodigo().longValue());
		
		
		boolean existe = tramiteEntity
				.existeTramitesActivoPorSujetoObligadoYTipos(listaTramitesClasificacion,
						cveIdPatronSujetoObligado, listaEstadosInvalidos);


		return existe;
	}
	
	/**
	 * Verifica si la lista de tr�mites actuales es igual a la lista de tramites 
	 * que se esta enviando en caso de existir diferencia se eliminan los que no
	 * est�n en la nueva lista
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param solicitud
	 */
	public void actualizarTramites(Solicitud solicitud){
		try {
			Solicitud solicitudAlmacenada = solicitudService.consultar(solicitud);
			eliminarTramites(solicitudAlmacenada, solicitud);
			solicitudService.actualizarTramites(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		}
	}
	
	private List<Tramite> eliminarTramites(Solicitud solicitudAlmacenada, Solicitud solicitudActualizada){
	
		List<Tramite> tramitesAlmacenados = solicitudAlmacenada.getTramites();
		List<Tramite> tramitesActualizados = solicitudActualizada.getTramites();
		for(Tramite tramiteAlmacenado : tramitesAlmacenados){
			boolean tramiteEliminado = true;
			for( Tramite tramiteActualizado : tramitesActualizados ){
				if(tramiteAlmacenado.getTramiteId().equals(tramiteActualizado.getTramiteId())){
					tramiteEliminado = false;
				}
			}
			if(tramiteEliminado){
				solicitudEntity.eliminarTramite(tramiteAlmacenado.getTramiteId());
			}
		}
		
		return tramitesActualizados;
	}
	
	/**
	 * Obtiene la solicitud activa asociada a un patron, es decir con estatus de registrada.
	 * Si no hay solicitud activa retorna null
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	public Solicitud obtenerSolicitudEnCapturaDeSujetoObligado(Long idPatronSujetoObligado, 
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite){
		Solicitud solicitud = solicitudEntity.obtenerSolicitudEnCaptura(idPatronSujetoObligado,tipoSolicitud, tipoTramite);
		if(solicitud== null)
			return solicitud;
		try {
			solicitud = solicitudService.consultar(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		return solicitud;
	}
	
	/**
	 * Obtiene la solicitud activa asociada a un patron, es decir con estatus de registrada.
	 * Si no hay solicitud activa retorna null
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	public Solicitud obtenerSolicitudEnCapturaPorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud){
		Solicitud solicitud = solicitudEntity.obtenerSolicitudEnCapturaPorPersona(idPersona, tipo, tipoSolicitud);
		if(solicitud== null)
			return solicitud;
		try {
			solicitud = solicitudService.consultar(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		return solicitud;
	}
	
	@Override
	public Solicitud obtenerSolicitudActiva(SujetoObligado sujetoTramite, TipoSolicitudEnum tipoSolicitud, 
			Usuario usuario, TipoTramiteEnum tipoTramite) {
		Solicitud solicitudActiva = null;
		Long perfilUsuario =usuario.getPerfilUsuario().getIdPerfilUsuario();
		if(perfilUsuario.equals(GestionPatronalRol.TRAMITADOR.getCodigo().longValue())){
			solicitudActiva=obtenerSolicitudEnProceso(sujetoTramite, tipoSolicitud, tipoTramite);
			System.err.println("Obteniendo solicitud en proceso: "+solicitudActiva);
		}else if(perfilUsuario.equals(GestionPatronalRol.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
				|| perfilUsuario.equals(GestionPatronalRol.REPRESENTANTE_LEGAL.getCodigo().longValue())){
			
			solicitudActiva=obtenerSolicitudEnCaptura(sujetoTramite, tipoSolicitud, tipoTramite);
			System.err.println("Obteniendo solicitud en captura "+solicitudActiva);
		}
		System.err.println("obtenerSolicitudActiva SolicitudActiva: "+solicitudActiva);
		return solicitudActiva;
	}
	
	@Override
	public Solicitud obtenerSolicitudEnCaptura(SujetoObligado sujetoTramite,
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite) {
		Solicitud solicitudActiva = null;
		if(tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION) 
				|| tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO) ){
			solicitudActiva = obtenerSolicitudEnCapturaDeSujetoObligado(sujetoTramite.getCveIdSujetoObligado(), tipoSolicitud, tipoTramite);
		}else if(tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES)) {
			Long idPersona = null;
			TipoPersonaFiscal tipo = sujetoTramite.getTipoPersonaFiscal();
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
				idPersona = sujetoTramite.getFisica().getIdPersona();
			else
				idPersona = sujetoTramite.getMoral().getIdPersona();
			System.err.println("idPersona en Business: "+idPersona);
			solicitudActiva = obtenerSolicitudEnCapturaPorPersona(idPersona, tipo, tipoSolicitud);
		}

		return solicitudActiva;

	}
	
	@Override
	public TipoTramite consultarTipoTramite(Integer id) {
		return tramiteEntity.consultarTipoTramite(id);
	}

	@Override
	public void actualizarEstados(Solicitud solicitud) {
		try {
			solicitudService.actualizarEstados(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Tramite obtenerTramiteDeSolicitudActivaPorTipo(
			SujetoObligado sujetoTramite, TipoTramiteEnum tipoTramite, Usuario usuario) {
		System.err.println("Obteniendo solicitud activa para tramite: "+tipoTramite);
		TipoSolicitudEnum tipoSolicitud = obtenerTipoSolicitudEnBaseAlTipoTramite(tipoTramite);
		Solicitud solicitudActiva = obtenerSolicitudActiva(sujetoTramite, tipoSolicitud, usuario, tipoTramite);
		
		if(solicitudActiva == null )
			return null;
		System.err.println("Se obtuvo la solicitud activa con folio: "+solicitudActiva.getNoFolioSolicitud());
		return obtenerTramitePorTipo(solicitudActiva, tipoTramite);
	}
	
	@Override
	public Tramite obtenerTramitePorTipo(Solicitud solicitud, TipoTramiteEnum tipoTramite){
		
		for(Tramite tramite : solicitud.getTramites()){
			if(tramite.getTipoTramite().getIdTipoTramite().equals(tipoTramite.getCodigo())){
				return tramite;
			}
		}
		return null;
	}

	@Override
	public Solicitud obtenerSolicitudEnProceso(SujetoObligado sujetoTramite, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite) {
		Solicitud solicitudEnProceso = null;
		if(tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION) 
				|| tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO) ){
			System.out.println("Obteniendo solicitud en proceso para sujetoObligado");
			solicitudEnProceso = obtenerSolicitudEnProcesoPorSujetoObligado(sujetoTramite.getCveIdSujetoObligado(), tipoSolicitud, tipoTramite);
		}else if(tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES)) {
			Long idPersona = null;
			TipoPersonaFiscal tipo = sujetoTramite.getTipoPersonaFiscal();
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
				idPersona = sujetoTramite.getFisica().getIdPersona();
			else
				idPersona = sujetoTramite.getMoral().getIdPersona();
			System.err.println("idPersona en Business para solicitud en proceso: "+idPersona);
			solicitudEnProceso = obtenerSolicitudEnProcesoPorPersona(idPersona, tipo, tipoSolicitud);
		}

		return solicitudEnProceso;

	}
	
	
	@Override
	public Solicitud obtenerSolicitudEnProcesoPorSujetoObligado(Long cveIdSujetoObligado, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite){
		Solicitud solicitudEnProceso = solicitudEntity.obtenerSolicitudEnProceso(cveIdSujetoObligado, tipoSolicitud, tipoTramite);
		if(solicitudEnProceso == null)
			return solicitudEnProceso;
		try {
			solicitudEnProceso = solicitudService.consultar(solicitudEnProceso);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		return solicitudEnProceso;
	}
	
	@Override
	public Solicitud obtenerSolicitudEnProcesoPorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud){
		Solicitud solicitudEnProceso = solicitudEntity.obtenerSolicitudEnProcesoPorPersona(idPersona, tipo, tipoSolicitud);
		System.out.println("Solicitud en proceso para la persona: "+idPersona+" solicitud"+solicitudEnProceso);
		if(solicitudEnProceso== null)
			return solicitudEnProceso;
		try {
			solicitudEnProceso = solicitudService.consultar(solicitudEnProceso);
			System.err.println("Solicitud en proceso para la persona del modulo de solicitudes "+solicitudEnProceso);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		return solicitudEnProceso;
	}
	
	@Override
	public List<Solicitud> listarSolicitudesEnCaptura(
			SujetoObligado sujetoObligado) {
		List<Solicitud> solicitudEnCaptura = new ArrayList<Solicitud>();
		List<Solicitud> solicitudEnCapturaPorSujetoObligado = null;
		List<Solicitud >solicitudesEnCapturaPorPersona = null;
		if(sujetoObligado.getCveIdSujetoObligado() != null){
			solicitudEnCapturaPorSujetoObligado = solicitudEntity.listarSolicitudesActivasPorPatron(
					sujetoObligado.getCveIdSujetoObligado());
		}else{
			Long idPersona = null;
			TipoPersonaFiscal tipo = sujetoObligado.getTipoPersonaFiscal();
			if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
				idPersona = sujetoObligado.getFisica().getIdPersona();
			else
				idPersona = sujetoObligado.getMoral().getIdPersona();
			System.err.println("idPersona en Business para solicitud en proceso: "+idPersona);
			solicitudesEnCapturaPorPersona = solicitudEntity.listarSolicitudesActivasPorPersona(idPersona, tipo);
		}
		
		if(solicitudEnCapturaPorSujetoObligado!=null){
			solicitudEnCaptura.addAll(solicitudEnCapturaPorSujetoObligado);
		}
		if(solicitudesEnCapturaPorPersona!= null){
			solicitudEnCaptura.addAll(solicitudesEnCapturaPorPersona);
		}

		return solicitudEnCaptura;
	}

	@Override
	public List<Solicitud> listarSolicitudesEnProceso(
			SujetoObligado sujetoObligado, boolean esTramitador) {
		List<Solicitud> solicitudEnProceso = new ArrayList<Solicitud>();
		List<Solicitud> solicitudEnProcesoPorSujetoObligado = new ArrayList<Solicitud>();
		List<Solicitud >solicitudesEnProcesoPorPersona = null;
		
		//Obtiene todas las solicitudes asociadas al patron (es decir, para todos sus rp)
		try {
			log.error("buscando solicitudes para el patron... ");
			super.log.error("buscando solicitudes para el patron... ");
			List<SujetoObligado> rps = sujetoObligadoService.obtenerDetalleSujetoObligado(sujetoObligado);
			System.err.println("buscando solicitudes para el patron: ");
			for(SujetoObligado rp : rps){
				System.err.println("buscando solicitudes para el RP: "+rp.getNumeroRegistroPatronal());
				System.out.println("buscando solicitudes para el RP: "+rp.getNumeroRegistroPatronal());
				super.log.error("buscando solicitudes para el RP: "+rp.getNumeroRegistroPatronal());
				System.err.println("Se buscan solicitudes por patron");
				List<Solicitud> solicitudEnProcesoPorPatron = solicitudEntity.listarSolicitudesEnProcesoPorPatron(rp.getCveIdSujetoObligado(), esTramitador);
				System.err.println("Finaliza la b�squeda de solicitudes por patron");
				if(solicitudEnProcesoPorPatron!= null){
					solicitudEnProcesoPorSujetoObligado.addAll(solicitudEnProcesoPorPatron);
				}
			}
			
			
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
			
		Long idPersona = null;
		TipoPersonaFiscal tipo = sujetoObligado.getTipoPersonaFiscal();
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
			idPersona = sujetoObligado.getFisica().getIdPersona();
		else
			idPersona = sujetoObligado.getMoral().getIdPersona();
		System.err.println("idPersona en Business para solicitud en proceso: "+idPersona);
		solicitudesEnProcesoPorPersona = solicitudEntity.listarSolicitudesEnProcesoPorPersona(idPersona, tipo);

		if(solicitudEnProcesoPorSujetoObligado!=null){
			System.err.println("Solicitudes en proceso sujeto: "+solicitudEnProcesoPorSujetoObligado.size());
			solicitudEnProceso.addAll(solicitudEnProcesoPorSujetoObligado);
		}
		if(solicitudesEnProcesoPorPersona!= null){
			System.err.println("Solicitudes en proceso persona: "+solicitudesEnProcesoPorPersona.size());
			solicitudEnProceso.addAll(solicitudesEnProcesoPorPersona);
		}
		System.err.println("Solicitudes en proceso totales: "+solicitudEnProceso.size());
		return solicitudEnProceso;
	}
	
	@Override
	public TipoSolicitudEnum obtenerTipoSolicitudEnBaseAlTipoTramite(TipoTramiteEnum tipoTramite){
		TipoSolicitudEnum tipoSol = null;
		
		if(	tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO)
			|| tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)
			|| tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA)
			|| tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO)
			|| tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL)
			|| tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO)
			){			
			tipoSol = TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES;
		}else if(tipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA) ||
				tipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY) ||
				tipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES) ||
				tipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS) ||
				tipoTramite.equals(TipoTramiteEnum.COMODATO) ||
				tipoTramite.equals(TipoTramiteEnum.ENAJENACION) ||
				tipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO) ||
				tipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO) ||
				tipoTramite.equals(TipoTramiteEnum.ESCISION) ||
				tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL) ||
				tipoTramite.equals(TipoTramiteEnum.FUSION) ||
				tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES) ||
				tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION) ||
				tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO)
				){
			tipoSol = TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION;
		}else if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO)){
			tipoSol = TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO;
		}
		
		return tipoSol;
	}

	@Override
	public DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltro( DatosEntradaPaginador<Solicitud> input,  FiltroSolicitud filtro) {
		DatosSalidaPaginador<Solicitud> output = solicitudEntity.listarSolicitudesPorFiltro(input, filtro);
		if(output.getAaData()!= null && output.getAaData().size() >0){
			for(Solicitud solicitud : output.getAaData()){
				if(solicitud.getSujetoObligado().getSubdelegacion() == null){
					Subdelegacion subdelegacion = obtenerSubdelegacionDeSolicitud(solicitud);
					solicitud.getSujetoObligado().setSubdelegacion(subdelegacion);
				}
			}
		}
		
		
		return output;
	}
	
	@Override
	public DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltroParaPatron( DatosEntradaPaginador<Solicitud> input,  FiltroSolicitud filtro) {
		DatosSalidaPaginador<Solicitud> output = solicitudEntity.listarSolicitudesPorFiltroParaPatron(input, filtro);
		if(output.getAaData()!= null && output.getAaData().size() >0){
			for(Solicitud solicitud : output.getAaData()){
				if(solicitud.getSujetoObligado().getSubdelegacion() == null){
					Subdelegacion subdelegacion = obtenerSubdelegacionDeSolicitud(solicitud);
					solicitud.getSujetoObligado().setSubdelegacion(subdelegacion);
				}
			}
		}
		
		
		return output;
	}

	@Override
	public Solicitud consultarDetalleSolicitudPorIdentificador(Long idSolicitud) {
		Solicitud solicitud = solicitudEntity.consultarDetalle(idSolicitud);
		solicitud = solicitudEntity.publicarDocumentos(solicitud);
		if(solicitud.getSujetoObligado().getSubdelegacion()== null){
			Subdelegacion subdelegacion = obtenerSubdelegacionDeSolicitud(solicitud);
			solicitud.getSujetoObligado().setSubdelegacion(subdelegacion);
		}else{
			System.err.println("Este patr�n no tiene domicilio fiscal");
		}
		System.err.println("Estado solicitud en consultarDetalleSolicitudPorIdentificador "+solicitud.getEstadoSolicitud().getDescripcion());
		return solicitud;
	}
	
	@Override
	public Solicitud consultarDetalleSolicitudPorFolio(String folio)
			throws SolicitudNoEncontradaException {
		Solicitud solicitud = solicitudEntity.consultarDetalle(folio);
		solicitud = solicitudEntity.publicarDocumentos(solicitud);
		
		if(solicitud.getSujetoObligado().getSubdelegacion()== null){
			Subdelegacion subdelegacion = obtenerSubdelegacionDeSolicitud(solicitud);
			solicitud.getSujetoObligado().setSubdelegacion(subdelegacion);
		}else{
			super.log.warn("Este patr�n no tiene domicilio fiscal");
		}
		
		for(Tramite tramite: solicitud.getTramites() ) {
			tramite.setDocumentoPorTipos(tramiteEntity.getDocumentosResultantesPorTipoTramite(tramite.getTipoTramite().getIdTipoTramite().longValue()));
		}
		super.log.debug("Estado solicitud en consultarDetalleSolicitudPorIdentificador "+solicitud.getEstadoSolicitud().getDescripcion());
		
		return solicitud;
	}
	
	
	private Subdelegacion obtenerSubdelegacionDeSolicitud(Solicitud solicitud){
		Long idPersona = null;
		Long idTipoPersona = null;
		Subdelegacion subdelegacion = null;
		
		if(solicitud.getSujetoObligado() == null || ( solicitud.getSujetoObligado() != null && solicitud.getSujetoObligado().getTipoPersonaFiscal()==null)){
			return null;
		}
		
		if(solicitud.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			idPersona = solicitud.getSujetoObligado().getFisica().getIdPersona();
			idTipoPersona = TipoPersonaEnum.FISICA.getId();
		}else{
			idPersona = solicitud.getSujetoObligado().getMoral().getIdPersona();
			idTipoPersona = TipoPersonaEnum.MORAL.getId();
		}
		
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		TipoPersona tipo = new TipoPersona();
		tipo.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipo);
		DomicilioFiscal domicilioFiscal = sujetoObligadoService.obtenerDomicilioFiscalPatron(persona);
		if(domicilioFiscal != null){
			try {
				subdelegacion = afiliacionservice.obtenerSubdelegacionPorDomicilio(domicilioFiscal);
			} catch (GestionPatronalBusinessException e) {
				System.err.println("Error al obtener subdelegacion: "+e.getMessage());
				subdelegacion = null;
			}
		}
		return subdelegacion;
	}
	
	@Override
	public void actualizarEstatus(Solicitud solicitud) {
		try {
			solicitudService.actualizarEstados(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			System.err.println("Error al actualizar estados de la solicitud");
		} catch (TramiteNoEncontradoException e) {
			System.err.println("Error al actualizar estados de la solicitud");
		}
		
	}

	@Override
	public Solicitud obtenerSolicitudPendienteDeAsignar(
			SujetoObligado sujetoTramite, TipoSolicitudEnum tipoSolicitud) {
		Solicitud solicitudPorAsignar = null;
		System.err.println("Obteniendo solicitud pendiente de asignar");
		if(tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION) 
				|| tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO) ){
			System.err.println("Obteniendo solicitud pendiente para sujetoObligado");
			solicitudPorAsignar = obtenerSolicitudPendienteDeAsignarPorSujetoObligado(sujetoTramite.getCveIdSujetoObligado(), tipoSolicitud);
		}else if(tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES)) {
			Long idPersona = null;
			TipoPersonaFiscal tipo = sujetoTramite.getTipoPersonaFiscal();
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
				idPersona = sujetoTramite.getFisica().getIdPersona();
			else
				idPersona = sujetoTramite.getMoral().getIdPersona();
			System.err.println("idPersona en Business para solicitud pendiente de asignar: "+idPersona);
			solicitudPorAsignar = obtenerSolicitudPendienteDeAsignarPorPersona(idPersona, tipo, tipoSolicitud);
		}

		return solicitudPorAsignar;

	}
	
	private Solicitud obtenerSolicitudPendienteDeAsignarPorSujetoObligado(Long cveIdSujetoObligado, TipoSolicitudEnum tipoSolicitud){
		Solicitud solicitudPorAsignar = solicitudEntity.obtenerSolicitudPendienteDeAsignarPorPatron(cveIdSujetoObligado, tipoSolicitud);
		if(solicitudPorAsignar == null)
			return solicitudPorAsignar;
		try {
			solicitudPorAsignar = solicitudService.consultar(solicitudPorAsignar);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		return solicitudPorAsignar;
	}
	
	
	public Solicitud obtenerSolicitudPendienteDeAsignarPorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud){
		Solicitud solicitudPorAsignar = solicitudEntity.obtenerSolicitudPendienteDeAsignarPorPersona(idPersona, tipo, tipoSolicitud);
		System.out.println("Solicitud pendiente de asignar: "+solicitudPorAsignar);
		if(solicitudPorAsignar== null)
			return solicitudPorAsignar;
		try {
			solicitudPorAsignar = solicitudService.consultar(solicitudPorAsignar);
			System.err.println("Solicitud pendiente de asignar para la persona del modulo de solicitudes "+solicitudPorAsignar);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		return solicitudPorAsignar;
	}

	@Override
	public Solicitud obtenerSolicitudPendienteAsignacion(
			SujetoObligado sujetoTramite, TipoSolicitudEnum tipoSolicitud) {
		Solicitud solicitudEnProceso = null;
		if(tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION) 
				|| tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO) ){
			System.out.println("Obteniendo solicitud en proceso para sujetoObligado");
			solicitudEnProceso = obtenerSolicitudPendienteAsignacionPorSujetoObligado(sujetoTramite.getCveIdSujetoObligado(), tipoSolicitud);
		}else if(tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES)) {
			Long idPersona = null;
			TipoPersonaFiscal tipo = sujetoTramite.getTipoPersonaFiscal();
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
				idPersona = sujetoTramite.getFisica().getIdPersona();
			else
				idPersona = sujetoTramite.getMoral().getIdPersona();
			System.err.println("idPersona en Business para solicitud en proceso: "+idPersona);
			solicitudEnProceso = obtenerSolicitudPendienteAsignacionPorPersona(idPersona, tipo, tipoSolicitud);
		}

		return solicitudEnProceso;

	}
	
	private Solicitud obtenerSolicitudPendienteAsignacionPorSujetoObligado(Long cveIdSujetoObligado, TipoSolicitudEnum tipoSolicitud){
		Solicitud solicitudEnProceso = solicitudEntity.obtenerSolicitudPendientePorSujetoObligado(cveIdSujetoObligado, tipoSolicitud);
		if(solicitudEnProceso == null)
			return solicitudEnProceso;
		try {
			solicitudEnProceso = solicitudService.consultar(solicitudEnProceso);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		return solicitudEnProceso;
	}
	
	private Solicitud obtenerSolicitudPendienteAsignacionPorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud){
		Solicitud solicitudEnProceso = solicitudEntity.obtenerSolicitudPendienteDeAsignarPorPersona(idPersona, tipo, tipoSolicitud);
		System.out.println("Solicitud en proceso para la persona: "+solicitudEnProceso);
		if(solicitudEnProceso== null)
			return solicitudEnProceso;
		try {
			solicitudEnProceso = solicitudService.consultar(solicitudEnProceso);
			System.err.println("Solicitud en proceso para la persona del modulo de solicitudes "+solicitudEnProceso);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		return solicitudEnProceso;
	}

	@Override
	public void actualizarDocumentosDeSolicitud(Solicitud solicitud) {
		solicitudEntity.actualizarDocumentos(solicitud);
	}
 
	@Override
	public Solicitud consultarSolicitudPorFolio(String folio) {
		return solicitudEntity.consultarSolicitudPorFolio(folio);
	}

	@Override
	public void actualizarFechaDeConclusionDeTramites(Long idSolicitud,
			Date fechaConclusion) {
		solicitudEntity.actualizarFechaConclusionDeTramites(idSolicitud, fechaConclusion);
	}

	@Override
	public TipoTramite obtenerTipoTramite(Long codigo) {
		return solicitudEntity.consultarDetalleTipoTramite(codigo);
	}

	@Override
	public Solicitud publicarDocumentosDeSolicitud(Solicitud solicitud) {
		solicitud = consultarSolicitudPorId(solicitud.getSolicitudId());
		solicitud = solicitudEntity.publicarDocumentos(solicitud);
		return solicitud;
	}

	@Override
	public Solicitud obtenerSolicitudAnteriorPorTipo(Solicitud solicitud) {
		Solicitud solicitudActual = solicitudEntity.consultarDetalle(solicitud.getSolicitudId());
		
		Solicitud solicitudAnterior = solicitudEntity.obtenerSolicitudAnteriorPorTipo(solicitudActual);
		try {
			solicitudAnterior = solicitudService.consultar(solicitudAnterior);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		return solicitudAnterior;
	}

	@Override
	public void actualizarDatosGeneralesDeSolicitud(Solicitud solicitud) {
		solicitudEntity.actualizarDatosSolicitud(solicitud);
	}
	
	@Override
	public List<Solicitud> listarSolicitudesGlobalesEnProceso(Long cveIdSubdelegacion) {
		List<Solicitud> solicitudEnProceso = new ArrayList<Solicitud>();
		List<Solicitud> solicitudEnProcesoPorSujetoObligado = new ArrayList<Solicitud>();
		List<Solicitud >solicitudesEnProcesoPorPersonaFisica = null;
		List<Solicitud >solicitudesEnProcesoPorPersonaMoral = null;
		List<Solicitud >solicitudesAltaPatronal = null;
		
		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo().longValue());
		
		List<Long> idsTipoSolicitud = new ArrayList<Long>();
		idsTipoSolicitud.add(TipoSolicitudEnum.ALTA_PATRONAL.getValor().longValue());
		
		
		//Obtiene todas las solicitudes asociadas a un RP
		log.error("buscando solicitudes para el patron... ");
		solicitudEnProcesoPorSujetoObligado = solicitudEntity.listarSolicitudesDeRPEnProceso(cveIdSubdelegacion);
		solicitudesEnProcesoPorPersonaFisica = solicitudEntity.listarSolicitudesDePersonaEnProceso(TipoPersonaFiscal.FISICA,TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, cveIdSubdelegacion);
		solicitudesEnProcesoPorPersonaMoral = solicitudEntity.listarSolicitudesDePersonaEnProceso(TipoPersonaFiscal.MORAL,TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, cveIdSubdelegacion);
		solicitudesAltaPatronal = solicitudEntity.listarSolicitudesPorTipoYEstado(idsTipoSolicitud, idsEstadoSolicitud);
		
		if(solicitudEnProcesoPorSujetoObligado!=null){
			System.err.println("Solicitudes en proceso sujeto: "+solicitudEnProcesoPorSujetoObligado.size());
			solicitudEnProceso.addAll(solicitudEnProcesoPorSujetoObligado);
		}
		if(solicitudesEnProcesoPorPersonaFisica!= null){
			System.err.println("Solicitudes en proceso persona: "+solicitudesEnProcesoPorPersonaFisica.size());
			solicitudEnProceso.addAll(solicitudesEnProcesoPorPersonaFisica);
		}
		if(solicitudesEnProcesoPorPersonaMoral!= null){
			System.err.println("Solicitudes en proceso persona: "+solicitudesEnProcesoPorPersonaMoral.size());
			solicitudEnProceso.addAll(solicitudesEnProcesoPorPersonaMoral);
		}
		if(solicitudesAltaPatronal!=null){
			System.err.println("Solicitudes de alta patronal: "+solicitudesEnProcesoPorPersonaMoral.size());
			List<Solicitud> listaSolicitudesAlta = new ArrayList<Solicitud>();
			for(Solicitud solicitud : solicitudesAltaPatronal){
				try {
					Solicitud solicitudActual = solicitudService.consultar(solicitud);
					TramiteSujetoObligado tramiteso = (TramiteSujetoObligado)solicitudActual.getTramites().get(0);
					solicitudActual.setSujetoObligado(tramiteso.getSujetoObligado());
					listaSolicitudesAlta.add(solicitudActual);
				} catch (SolicitudNoEncontradaException e) {
					e.printStackTrace();
				}
			}
			solicitudEnProceso.addAll(listaSolicitudesAlta);
		}
		System.err.println("Solicitudes en proceso totales: "+solicitudEnProceso.size());
		return solicitudEnProceso;
	}
	
	
	/**
	 * Verifica si la lista de tr�mites actuales es igual a la lista de tramites 
	 * que se esta enviando en caso de existir diferencia se eliminan los que no
	 * est�n en la nueva lista
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param solicitud
	 */
	@Override
	public Solicitud actualizarTramiteAlta(Solicitud solicitud, SujetoObligado sujetoTramite){
		Solicitud solicitudAlmacenada = null;
		List<Tramite> listTramiteModif = new ArrayList<Tramite>();
		try {
			solicitudAlmacenada = solicitudService.consultar(solicitud);
			Date fechaPresentacionGeneral = null;
			for (Tramite tramiteActual : solicitud.getTramites()) {
				if (tramiteActual instanceof TramiteSujetoObligado) {
					TramiteSujetoObligado tramiteAlta = (TramiteSujetoObligado) tramiteActual;
					tramiteAlta.setSujetoObligado(sujetoTramite);
					fechaPresentacionGeneral = sujetoTramite.getClasificacion().getFecPresentacion();
					tramiteAlta.setFechaPresentacion(fechaPresentacionGeneral);//Se modifica la fecha de presentaci�n al guardar el tr�mite
					tramiteAlta.setFechaEfecto(sujetoTramite.getClasificacion().getFecEfecto());
					listTramiteModif.add(tramiteAlta);
				} else {
					continue;// no se toma en cuenta el tr�mite de datos generaales pues este no requiere de informaci�n actualizada en su detalle(XML)
					//Se omite pues genera un infinitely deep exception
					//istTramiteModif.add(tramiteActual);
				}
			}
			
			for(Tramite tramiteAlmacendao: solicitudAlmacenada.getTramites())
				if (!(tramiteAlmacendao instanceof TramiteSujetoObligado)){
					Date fechaActual = Calendar.getInstance().getTime();
					tramiteAlmacendao.setFechaEfecto(fechaActual);
					if(fechaPresentacionGeneral!=null)
						tramiteAlmacendao.setFechaPresentacion(fechaPresentacionGeneral);
					else
						tramiteAlmacendao.setFechaPresentacion(fechaActual);
					listTramiteModif.add(tramiteAlmacendao);//Se agrega el tramite de datos generales en su estado original
				}
			solicitudAlmacenada.setTramites(listTramiteModif);

			return solicitudService.actualizarTramites(solicitudAlmacenada);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		}
		return solicitudAlmacenada;
	}

	@Override
	public void agregarTramiteASolicitud(Long cveIdSolicitud, Tramite tramite) {
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(cveIdSolicitud);
		try {
			solicitud = solicitudService.consultar(solicitud);
			if (solicitud.getTramites() == null) {
				solicitud.setTramites(new ArrayList<Tramite>());
			}
			solicitud.getTramites().add(tramite);
			solicitudService.actualizarTramites(solicitud);
			
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cancelarSolicitudPorFolio(String folio) throws SolicitudException {
		Solicitud solicitudActual = solicitudEntity.consultarSolicitudPorFolio(folio);
		if(solicitudActual.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())){
			throw new SolicitudException("error.solicitud.concluida.previamente");
		}
		
		solicitudEntity.cancelarSolicitudPorFolio(folio);
	}

	@Override
	public void finalizarCapturaSolicitudClasificacion(Solicitud solicitud,
			EstadoTramiteEnum estadoTramite, SujetoObligado sujetoObligado,
			FirmaElectronica firmaElectronica)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException {
		actualizarSolicitud(solicitud, estadoTramite, sujetoObligado);
		solicitudService.enviarSolicitudAProceso(solicitud, firmaElectronica);
	}

	@Override
	public void generarDocumentos(String folio) throws Exception{
		try {
			Solicitud solicitudParam = new Solicitud();
			solicitudParam.setNoFolioSolicitud(folio);
			Solicitud solicitud = solicitudService.consultarFolio(solicitudParam);
			
			this.generarDocumentos(solicitud);
		} catch (Exception e ) {
			e.printStackTrace();
			log.error("**ocurrio un erro al generar los documentos **" ,e);
			throw e;
		}
		
	}
	
	@Override
	public void generarDocumentos(Solicitud solicitud) throws Exception{
		// TODO Auto-generated method stub
		for(Tramite tramite: solicitud.getTramites() ) {
			tramite.setDocumentoPorTipos(tramiteEntity.getDocumentosResultantesPorTipoTramite(tramite.getTipoTramite().getIdTipoTramite().longValue()));
		}
		try{
			solicitudService.guardarDocumentosResultantesPorSolicitud(solicitud);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("**ocurrio un erro al generar los documentos  generarDocumentos*" ,e);
			throw e;
		}
	}

	@Override
	public Solicitud obtenerSolicitudAltaPatronalPorNRP(Long idPatron) {
		Long idSolicitud = solicitudEntity.obtenerIdSolicitudAlta(idPatron);
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		
		try {
			solicitud = solicitudService.consultar(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		return solicitud;
	}
	
	@Override
	public List<DocumentoPorTipo> obtenerDocumentosResultantesPorTipoTramite (long idTipoTramite) {
		
		return this.tramiteEntity.getDocumentosResultantesPorTipoTramite(idTipoTramite);
		
	}
	
	
	public void procesarSolicitudContactoCentroTrabajo(Long idSolicitud, FirmaElectronica firma)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException {		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		solicitud = solicitudService.consultar(solicitud);
		solicitudService.enviarSolicitudAProceso(solicitud, firma);
	}
	
	public void finalizarSolicitudContactoCentroTrabajo(Long idSolicitud){
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		try {
			solicitud = solicitudService.consultar(solicitud);					
			if(solicitud!=null && !CollectionUtils.isEmpty(solicitud.getTramites())){
				for (Tramite tramite : solicitud.getTramites()) {
					TramiteSujetoObligado tramiteSO = (TramiteSujetoObligado)tramite;
					if(tramiteSO!=null && tramiteSO.getSujetoObligado()!=null 
							&& tramiteSO.getSujetoObligado().getCntroTrabajo()!=null){
						sujetoObligadoService.actualizarMediosContactoCentroTrabajo(tramiteSO.getSujetoObligado().getCntroTrabajo());
					}
				}
			}
			solicitudService.actualizarSolicitudAEstatusConcluida(solicitud.getSolicitudId());
		} catch (AbstractException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void actualizarTramiteDeAltaEnSolicitud(Tramite tramite)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
		System.err.println("Se actualiza tramite... ");
		Solicitud solicitud = solicitudService.consultarPorIdTramite(tramite.getTramiteId());
		SujetoObligado soActualizado = ((TramiteSujetoObligado)tramite).getSujetoObligado();
		for(Tramite t : solicitud.getTramites())
			if(t.getTramiteId().equals(tramite.getTramiteId())){
				System.err.println("Se actualiza tramite... "+soActualizado);
				((TramiteSujetoObligado)t).setSujetoObligado(soActualizado);
			}
		
		solicitudService.actualizarTramites(solicitud);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)	
	@Override
	public Solicitud validaEstadoSolicitudEnOSB(Solicitud solicitud) throws GestionPatronalBusinessException{
		log.debug("::: Revisando el estado de la solicitud " + solicitud.getSolicitudId() + ", " + new Date());
		solicitud = consultarSolicitudPorId(solicitud.getSolicitudId());
		for(Tramite tramiteAlmacenado: solicitud.getTramites()) {
			if(tramiteAlmacenado.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue() ||
					tramiteAlmacenado.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue()) {
				TramiteSujetoObligado tr = (TramiteSujetoObligado)tramiteAlmacenado;
				log.debug("::: Estado de Proceso en OSB: " + tr.getSolicitudEnProcesoOSB() + ", solicitud: "+solicitud.getSolicitudId()+", " + new Date());				
				//si el XML del tramite ya contiene el tag con este valor
				if(tr.getSolicitudEnProcesoOSB() == EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue()) {
					log.debug("::: La solicitud " + solicitud.getSolicitudId() + ", ya esta en proceso en el OSB, " + new Date());
					throw new GestionPatronalBusinessException("La solicitud se esta procesando, por favor espere");
				}else {
					log.debug("::: La solicitud " + solicitud.getSolicitudId() + " aun NO es procesada en el OSB, se agrega marca de proceso, " + new Date());
					actualizarTramiteAltaEnProcesoOSB(solicitud, EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue());
					log.debug("::: La solicitud "+solicitud.getSolicitudId()+" fue actualizada con la marca de proceso, " + new Date());						
				}
			}
		}
		return solicitud;
	}	
	
	/**
	 * Agrega marca a la solicitud para indicar que ya esta en proceso para terminar 
	 * @param solicitud
	 */
	@Override
	public Solicitud actualizarTramiteAltaEnProcesoOSB(Solicitud solicitudAlmacenada, int estadoOSB){
		log.debug("::: Se agregara la marca de proceso a la solicitud " + solicitudAlmacenada.getSolicitudId() + ", " + new Date());
		List<Tramite> listTramiteModif = new ArrayList<Tramite>();
		try {
			for(Tramite tramiteAlmacenado: solicitudAlmacenada.getTramites()) {
				if(tramiteAlmacenado.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue() ||
						tramiteAlmacenado.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue()) {
					TramiteSujetoObligado tr = (TramiteSujetoObligado)tramiteAlmacenado;
					tr.setSolicitudEnProcesoOSB(estadoOSB);
					listTramiteModif.add(tr);					
				}else {
					listTramiteModif.add(tramiteAlmacenado);
				}
			}
			solicitudAlmacenada.setTramites(listTramiteModif);
			return solicitudService.actualizarTramitesMarcaOSB(solicitudAlmacenada);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		}
		return solicitudAlmacenada;
	}
	
}
