package mx.gob.imss.cit.cda.service.business;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.entity.CorreccionDatosAseguradoLocal;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.TipoTransicionEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "responsableTareaBusiness", mappedName = "responsableTareaBusiness")
public class ResponsableTareaBusiness extends AbstractServiceUtility implements ResponsableTareaRemote {

	@EJB(name = "flujoTrabajoBusiness", mappedName = "flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoRemote;

	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;

	@EJB
	private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;
	
	@EJB
	private CorreccionDatosAseguradoLocal correccionDatosAseguradoEntity;

	private final Logger log = LoggerFactory.getLogger(ResponsableTareaBusiness.class);

	private static final String TRAMITE_REASIGNADO = "REASIGNADA";
	
	@Override
	public void avanzarSolitudInformacion(Solicitud solicitud, String idTarea, String observacion, String usuario, int tipoUsr)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, BPMException {
		
		solicitudBusiness.actualizarEstados(solicitud);
		
		solicitudBusiness.actualizarXmlTramite((TramiteCorreccionCurp)solicitud.getTramites().get(0));
		
		
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setFechaActualizacion(correccionAseguradoUtilityLocal.convertDateToString(new Date()));
		mensajeTarea.setEstado(solicitud.getTramites().get(0).getEstadoTramite().getDescripcion());
		mensajeTarea.setObservacion(observacion);
		mensajeTarea.setUsuario(usuario);
		
		
		flujoTrabajoRemote.solicitarInformacion(Long.parseLong(idTarea),mensajeTarea);
		correccionDatosAseguradoEntity.insertarResponableAutorizadorCorreccion(solicitud.getTramites().get(0).getTramiteId(),tipoUsr);
		
	}

	public void rechazarSolicitud(Solicitud solicitud, String idTarea,String usuario)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, BPMException {
		solicitudBusiness.actualizarEstados(solicitud);
		solicitudBusiness.actualizarXmlTramite((TramiteCorreccionCurp)solicitud.getTramites().get(0));
		completarTareaUsuario(solicitud, idTarea, TipoTransicionEnum.PRINCIPAL,
				solicitud.getRazonCancelacion().getDescripcion(),usuario);
	}
	
	public void autorizarSolicitud(Solicitud solicitud, String idTarea,String usuario)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, BPMException {
		completarTareaUsuario(solicitud, idTarea, TipoTransicionEnum.ALTERNATIVA1,
				null,usuario);
	}

	@Override
	public void avanzarTareaResponsable(Solicitud solicitud, String idTarea) throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException, NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException, NumberFormatException {
		log.debug("Recibiendo los datos de la Solicitud id {} Tramite id {} Tarea id {}",
				new Object[] { solicitud.getSolicitudId(), solicitud.getTramites().get(0).getTramiteId(), idTarea });

		solicitudBusiness.actualizarEstados(solicitud);
		completarTarea(solicitud, idTarea);
		
		Solicitud solBase = solicitudBusiness.consultarPorIdTramite(solicitud.getTramites().get(0).getTramiteId());
		TramiteCorreccionCurp tramiteBase = (TramiteCorreccionCurp)solBase.getTramites().get(0);
		
		//Actualizar los datos de captura del responsable respecto al tipo de correccion que realiza
		tramiteBase.setCertificacionNSS(((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getCertificacionNSS());
		tramiteBase.setTipoRegularizacion(((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getTipoRegularizacion());
		if(((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getObservacionesSubdelegacion() != null){
			tramiteBase.getObservacionesSubdelegacion().addAll(((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getObservacionesSubdelegacion());
		}
		tramiteBase.setDocumentosProbatorios(null);
		
		solicitudBusiness.actualizarXmlTramite(tramiteBase);
		
	}

	@Override
	public void cancelarTareaResponsable(Solicitud solicitud, String idTarea)
			throws SolicitudException, NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException, NumberFormatException,
			SolicitudNoEncontradaException,TramiteNoEncontradoException{
		log.debug("Recibiendo los datos de la Solicitud id {} Tramite id {} Tarea id {}",
				new Object[] { solicitud.getSolicitudId(), solicitud.getTramites().get(0).getTramiteId(), idTarea });			
		solicitudBusiness.cancelarSolicitud(solicitud.getSolicitudId(), null,
				solicitud.getRazonCancelacion().getIdRazonCancelacion(),
				solicitud.getPersonaInteresada().getIdPersona().toString(), solicitud.getObservacion());
		solicitudBusiness.actualizarEstados(solicitud);
		solicitudBusiness.actualizarXmlTramite((TramiteCorreccionCurp)solicitud.getTramites().get(0));
		completarTarea(solicitud, idTarea);
	}

	private void completarTarea(Solicitud solicitud, String idTarea)
			throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException, NumberFormatException {
		MensajeTarea mensajeTarea = new MensajeTarea();
		String tipoTransicion = "";
		if (solicitud.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona()
				.equals(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo())) {
			mensajeTarea.setObservacion(solicitud.getRazonCancelacion().getDescripcion());
			tipoTransicion = TipoTransicionEnum.ALTERNATIVA1.getId();
		} else if (solicitud.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona()
				.equals(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo())
				|| solicitud.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona()
						.equals(EstadoTramiteEnum.RECHAZADO.getCodigo())) {
			tipoTransicion = TipoTransicionEnum.PRINCIPAL.getId();
		}
		mensajeTarea.setTipoTransicion(tipoTransicion);
		mensajeTarea.setEstado(EstadoNegocioEnum.obtenerDescripcionNegocio(solicitud.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona()));
		mensajeTarea.setFechaActualizacion(correccionAseguradoUtilityLocal.convertDateToString(new Date()));
		//mensajeTarea.setUsuario(solicitud.getSolicitante().getCveIdUsuario());
		
		if(((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getTipoRegularizacion()!= null){
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		mapa.put("tipoRegularizacion", ((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getTipoRegularizacion().getIdTipoRegularizacion());
		mensajeTarea.setData(correccionAseguradoUtilityLocal.generarJsonDataWf(mapa));
		log.debug("tipo Transiccion {} ", tipoTransicion);
		}

		flujoTrabajoRemote.completarTarea(Long.parseLong(idTarea), mensajeTarea);

	}
	
	private void completarTareaUsuario(Solicitud solicitud, String idTarea, TipoTransicionEnum tipoTransicionEnum,
			String observacion, Map<String, String> participantes,String usuario) throws BPMException {
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setObservacion(StringUtils.isNotBlank(observacion)?observacion:"");
		mensajeTarea.setTipoTransicion(tipoTransicionEnum.getId());
		mensajeTarea.setEstado(solicitud.getTramites().get(0).getEstadoTramite().getDescripcion());
		mensajeTarea.setFechaActualizacion(correccionAseguradoUtilityLocal.convertDateToString(new Date()));
		if(participantes!= null && !participantes.isEmpty()){
			mensajeTarea.setParticipantes(participantes);
		}
		flujoTrabajoRemote.completarTarea(Long.parseLong(idTarea),usuario, mensajeTarea);

	}
	
	private void completarTareaUsuario(Solicitud solicitud, String idTarea, TipoTransicionEnum tipoTransicionEnum,
			String observacion, String usuario) throws BPMException {
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setObservacion(StringUtils.isNotBlank(observacion)?observacion:"");
		mensajeTarea.setTipoTransicion(tipoTransicionEnum.getId());
		mensajeTarea.setEstado(EstadoNegocioEnum.obtenerDescripcionNegocio(solicitud.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona()));
		mensajeTarea.setFechaActualizacion(correccionAseguradoUtilityLocal.convertDateToString(new Date()));
		
		
		if(solicitud.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo())){
			log.info("Agregando Autorizador "+  usuario);
			Map<String, String> participantes = new HashMap<String, String>();
			participantes.put(ParticipantesEnum.AUTORIZADOR.getDescripcion().toString(), usuario);
			mensajeTarea.setParticipantes(participantes);
			mensajeTarea.setUsuario(usuario);
		}
		
		
		log.debug("Mensaje Tarea {}",mensajeTarea.getEstado());
		flujoTrabajoRemote.completarTarea(Long.parseLong(idTarea),usuario, mensajeTarea);

	}

	@Override
	public Long iniciarWorkFlow(Long bp, InicioTramite inicioTramite, Solicitud solicitud)
			throws TareaInicialException, TereaSinUsuarioAsignadoException {

		Long wf = flujoTrabajoRemote.iniciarWorkFlow(bp, inicioTramite);
		log.debug("---CDA--- Usuario Solicitud {}",solicitud.getSolicitante().getCveIdUsuario());
		solicitudBusiness.actualizarUsuarioSolicitud(solicitud);
		

		return wf;
	}

	@Override
	public void reasignarTareaAutorizador(Solicitud solicitud, String idTarea, String observacion,String usuario)
			throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException, BPMException {
		solicitudBusiness.actualizarUsuarioSolicitud(solicitud);
		if (solicitud.getSolicitante() != null) {
			log.debug("---CDA--- Usuario Solicitante {} Sol {}",solicitud.getSolicitante().getCveIdUsuario(), solicitud.getSolicitudId());
		}
		solicitudBusiness.actualizarEstados(solicitud);		
		solicitudBusiness.actualizarXmlTramite((TramiteCorreccionCurp)solicitud.getTramites().get(0));
		
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setFechaActualizacion(correccionAseguradoUtilityLocal.convertDateToString(new Date()));
		String nombre = flujoTrabajoRemote.nombrePersona(usuario);
		mensajeTarea.setUsuario(nombre);
		mensajeTarea.setEstado(TRAMITE_REASIGNADO);
		mensajeTarea.setObservacion(observacion);
		Map<String, String> participantes = new HashMap<String, String>();
		participantes.put(ParticipantesEnum.RESPONSABLE.getDescripcion().toString(), usuario);
		mensajeTarea.setParticipantes(participantes);
		
		flujoTrabajoRemote.reasignarTarea(usuario, Long.parseLong(idTarea),mensajeTarea);
	}
	
	@Override
	public void cancelarTareaAutorizador(Solicitud solicitud, String idTarea,String usuario)
			throws SolicitudException, NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException, NumberFormatException,BPMException,
			SolicitudNoEncontradaException,TramiteNoEncontradoException{
		log.debug("Recibiendo los datos de la Solicitud id {} Tramite id {} Tarea id {}",
				new Object[] { solicitud.getSolicitudId(), solicitud.getTramites().get(0).getTramiteId(), idTarea });		
		solicitudBusiness.cancelarSolicitud(solicitud.getSolicitudId(), null,
				solicitud.getRazonCancelacion().getIdRazonCancelacion(),null, solicitud.getObservacion());
		solicitudBusiness.actualizarEstados(solicitud);
		solicitudBusiness.actualizarXmlTramite((TramiteCorreccionCurp)solicitud.getTramites().get(0));
		completarTareaUsuario(solicitud, idTarea, TipoTransicionEnum.ALTERNATIVA1, solicitud.getRazonCancelacion().getDescripcion(), usuario);
	}
	
	@Override
	public void actualizarBdocInstanciaCertificacion(String idTarea,String estado, String usuario) throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException{
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setFechaActualizacion(correccionAseguradoUtilityLocal.convertDateToString(new Date()));
		mensajeTarea.setEstado(estado);
			Map<String, String> participantes = new HashMap<String, String>();
		participantes.put(ParticipantesEnum.AUTORIZADOR.getDescripcion(), usuario);
		mensajeTarea.setParticipantes(participantes);
		actualizarEstadoBdocInstancia(idTarea, mensajeTarea);		
	}
	
	@Override
	public void actualizarEstadoBdocInstancia(String idTarea,String estado,Date fecha) throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException{
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setFechaActualizacion(fecha != null ?correccionAseguradoUtilityLocal.convertDateToString(fecha):correccionAseguradoUtilityLocal.convertDateToString(new Date()));
		mensajeTarea.setEstado(estado);
		actualizarEstadoBdocInstancia(idTarea, mensajeTarea);		
	}
	
	private void actualizarEstadoBdocInstancia(String idTarea,MensajeTarea mensajeTarea) throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException{
		flujoTrabajoRemote.actualizarBDocInstancia(Long.parseLong(idTarea), mensajeTarea);
		
	}
	
	
	
	

}
