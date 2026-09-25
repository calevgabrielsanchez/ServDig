package mx.gob.imss.cit.gestion.solicitud.flujo.service.utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaYaAsignadaAlUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DatosTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DatosTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.EstadoInstanciaEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.EstadoTareaUsuarioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.TipoTransicionEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Asignacion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.EstadoInstancia;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.EstadoTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Instancia;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Proceso;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Tarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaTransicion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaUsuario;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TransicionTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoLocal;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ConteoReporteCDA;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadisticasReporteCDA;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ReporteCDA;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;

@Stateless(mappedName = "flujoTrabajoUtility", name = "flujoTrabajoUtility")
public class FlujoTrabajoUtility implements FlujoTrabajoUtilityLocal {
	
	private static final Logger LOGGERBPM = LoggerFactory.getLogger(FlujoTrabajoUtility.class);
	
    public static final int CERO =0;
    public static final int UNO =1;
    public static final int DOS =2;
    public static final int TRES =3;
    public static final int CUATRO =4;
    public static final int CINCO =5;
    public static final int SEIS =6;
    public static final int SIETE =7;
    public static final int OCHO =8;
    public static final int NUEVE =9;
    public static final int DIEZ =10;
    public static final int ONCE =11;
    public static final int DOCE =12;
    public static final int TRECE =13;
    public static final int CATORCE =14;
    public static final int QUINCE =15;
    public static final int DIECISEIS =16;
	
	/**
	 * Inyeccion del Helper de BPM
	 */
	@EJB
	private FlujoTrabajoComunUtilityLocal flujoTrabajoComunUtility;

	/**
	 * Inyeccion del repositorio BPM
	 */
	@EJB
	private FlujoTrabajoLocal flujoTrabajoEntity;

	/**
	 * Inyeccion del Helper de XML
	 */
	@EJB
	private XmlUtilityLocal xmlUtility;
	
	@EJB(name = "componentesExternosBusiness" , mappedName = "componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	

	/**
	 * Metodo para la validacion del la tarea inicial
	 * 
	 * @param tareas
	 * @return
	 * @throws TareaInicialException
	 */
	public Tarea validarTareaInicial(List<Tarea> tareas) throws TareaInicialException {
		if (tareas == null || tareas.isEmpty()) {
			throw new TareaInicialException("NoExisteConfiguracion");
		} else if (tareas.size() > 1) {
			throw new TareaInicialException("ExisteMasDeUnaTareaInicial");
		}
		return tareas.get(0);
	}

	/**
	 * Metodo para la creacion de una instancia
	 * 
	 * @param proceso
	 * @param bDoc
	 * @return
	 */
	public Instancia crearInstancia(Proceso proceso, InicioTramite inicioTramite) {
		Instancia instancia = new Instancia();
		instancia.setProceso(proceso);
		instancia.setEstadoInstancia(new EstadoInstancia(EstadoInstanciaEnum.ACTIVA.getClave()));
		instancia.setbDoc(xmlUtility.convertToXMLWithNoReferences(inicioTramite));
		instancia.setIdTramite(inicioTramite.getIdTramite());
		return instancia;
	}

	/**
	 * Metodo para la creacion de asignaciones
	 * 
	 * @param bpId
	 * @param inicioTramite
	 * @return
	 */
	public List<Asignacion> crearAsignaciones(final Long bp, final Long bpId, Map<String, String> participantes) {
		List<Asignacion> asignaciones = new ArrayList<Asignacion>();
		List<Tarea> lstTareas = flujoTrabajoEntity.obtenerTareasUsuarioByProceso(bp);
		for (Tarea tarea : lstTareas) {
			flujoTrabajoComunUtility.llenarAsignacion(asignaciones, bpId, tarea.getParticipante().getIdParticipante(),
					participantes.get(tarea.getParticipante().getNombre()));
		}
		return asignaciones;
	}

	/**
	 * Metodo para la creacion de una tarea de usuario
	 * 
	 * @param instancia
	 * @param tarea
	 * @return
	 * @throws TereaSinUsuarioAsignadoException
	 */
	public TareaUsuario crearTareaUsuario(final Instancia instancia, final Tarea tarea)
			throws TereaSinUsuarioAsignadoException {

		Asignacion asignacion = flujoTrabajoEntity.buscarAsignacion(
				flujoTrabajoComunUtility.llenarAsignacion(instancia.getBpId(), tarea.getParticipante().getIdParticipante()));

		if (asignacion == null) {
			throw new TereaSinUsuarioAsignadoException("NoExisteUsuarioAsignado");
		}
		
		InicioTramite inicioTramite = (InicioTramite) xmlUtility.convertToModel(instancia.getbDoc());
		Map<String, Object> datos = generarJavaDataWf(inicioTramite.getData());

		TareaUsuario tareaUsuario = new TareaUsuario();
		tareaUsuario.setInstancia(instancia);
		tareaUsuario.setTarea(tarea);
		tareaUsuario.setUsuario(asignacion.getUsuario());
		tareaUsuario.setFechaInicioTarea(new Date());
		tareaUsuario.setEstadoTarea(new EstadoTarea(EstadoTareaUsuarioEnum.DISPONIBLE.getClave()));
		MensajeTarea mensaje = new MensajeTarea();
		mensaje.setEstado(inicioTramite.getEstatus());
                if(datos.get("Responsable")!=null){
                	LOGGERBPM.info("responsable BPM................... "+datos.get("Responsable"));
                	 mensaje.setUsuario("BPM_ADMIN21");
//                    mensaje.setUsuario(nombrePersona((String)datos.get("Responsable")));
                }
		tareaUsuario.setbDoc(xmlUtility.convertToXMLWithNoReferences(mensaje));
		if (tarea.getNombreVariableTimmer() != null)
			tareaUsuario.setTimeout(buscarAtributoTimmer(instancia, tarea.getNombreVariableTimmer()));
		return tareaUsuario;
	}

	/**
	 * Metodo para la busqueda de atributis Timmer
	 * 
	 * @param instancia
	 * @param nombreAtributo
	 * @return
	 */
	private Long buscarAtributoTimmer(Instancia instancia, String nombreAtributo) {
		Long atributo = null;
		InicioTramite inicioTramite = (InicioTramite) xmlUtility.convertToModel(instancia.getbDoc());
		if (inicioTramite != null && inicioTramite.getParametros() != null) {
			atributo = inicioTramite.getParametros().get(nombreAtributo);
		}
		return atributo;
	}

	/**
	 * Meotodo para la reasignacion de tarea de usuario
	 * 
	 * @param instancia
	 * @param tarea
	 * @param usuario
	 * @return
	 */
	public TareaUsuario crearTareaReasignarUsuario(final Instancia instancia, final Tarea tarea, final String usuario, MensajeTarea mensaje) {

		TareaUsuario tareaUsuario = new TareaUsuario();
		tareaUsuario.setInstancia(instancia);
		tareaUsuario.setTarea(tarea);
		tareaUsuario.setUsuario(usuario);
		tareaUsuario.setFechaInicioTarea(new Date());
		tareaUsuario.setEstadoTarea(new EstadoTarea(EstadoTareaUsuarioEnum.DISPONIBLE.getClave()));
		tareaUsuario.setbDoc(xmlUtility.convertToXMLWithNoReferences(mensaje));
		return tareaUsuario;
	}

	/**
	 * Metodo para la creacion de tareas de usuario
	 * 
	 * @param lstTransicionTarea
	 * @param instancia
	 * @return
	 * @throws TereaSinUsuarioAsignadoException
	 */
	public List<TareaUsuario> crearTareasUsuario(final List<TransicionTarea> lstTransicionTarea,
			final Instancia instancia) throws TereaSinUsuarioAsignadoException {
		List<TareaUsuario> lstTareasUsuario = new ArrayList<TareaUsuario>();
		for (TransicionTarea transicionTarea : lstTransicionTarea) {
			if (!transicionTarea.getTarea().getParticipante().getIdParticipante()
					.equals(ParticipantesEnum.SISTEMA.getClave())) {
				lstTareasUsuario.add(crearTareaUsuario(instancia, transicionTarea.getTarea()));
			}
		}
		return lstTareasUsuario;
	}

	/**
	 * Metodo para crear lista de tareas de usuario
	 * 
	 * @param lstTransicionTarea
	 * @param instancia
	 * @param bDoc
	 * @return
	 * @throws TereaSinUsuarioAsignadoException
	 */
	public List<TareaUsuario> crearTareasUsuario(final List<TransicionTarea> lstTransicionTarea,
			final Instancia instancia, final String bDoc) throws TereaSinUsuarioAsignadoException {
		List<TareaUsuario> lstTareasUsuario = new ArrayList<TareaUsuario>();
		TareaUsuario tareaUsuario = null;
		for (TransicionTarea transicionTarea : lstTransicionTarea) {
			if (!transicionTarea.getTarea().getParticipante().getIdParticipante()
					.equals(ParticipantesEnum.SISTEMA.getClave())) {
				tareaUsuario = crearTareaUsuario(instancia, transicionTarea.getTarea());
				tareaUsuario.setbDoc(bDoc);
				lstTareasUsuario.add(tareaUsuario);
			}
		}
		return lstTareasUsuario;
	}

	/**
	 * Metodo para crear lista de tareas de sistema
	 * 
	 * @param lstTransicionTarea
	 * @return
	 */
	public List<Tarea> crearTareasSistema(final List<TransicionTarea> lstTransicionTarea) {
		List<Tarea> lstTareasSistema = new ArrayList<Tarea>();
		for (TransicionTarea transicionTarea : lstTransicionTarea) {
			if (transicionTarea.getTarea().getParticipante().getIdParticipante()
					.equals(ParticipantesEnum.SISTEMA.getClave())) {
				lstTareasSistema.add(transicionTarea.getTarea());
			}
		}
		return lstTareasSistema;
	}

	/**
	 * Metodo que valida una taarea de usuario
	 * 
	 * @param tareaUsuario
	 * @throws NoExisteTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 */
	public void validarTareaUsuario(TareaUsuario tareaUsuario)
			throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException {
		if (tareaUsuario == null) {
			throw new NoExisteTareaUsuarioException("NoExisteTareaUsuario");
		}
		if (!(tareaUsuario.getEstadoTarea().getIdEstadoTarea() == EstadoTareaUsuarioEnum.DISPONIBLE.getClave())
				|| tareaUsuario.getEstadoTarea().getIdEstadoTarea() == EstadoTareaUsuarioEnum.ACTIVA.getClave()) {
			throw new EstadoTareaUsuarioNoValidoException("EstadoInvalido");
		}
	}

	/**
	 * metodo que valida la reasignacion de una tarea a un usuario
	 * 
	 * @param tareaUsuario
	 * @param usuario
	 * @throws NoExisteTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 * @throws TareaYaAsignadaAlUsuarioException
	 */
	public void validarTareaUsuarioReasignar(TareaUsuario tareaUsuario, String usuario)
			throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			TareaYaAsignadaAlUsuarioException {
		validarTareaUsuario(tareaUsuario);

		if (tareaUsuario.getUsuario().equals(usuario)) {
			throw new TareaYaAsignadaAlUsuarioException("usuarioYaTieneAsociadaLaTarea");
		}
	}

	/**
	 * Metodo para preparar la tarea de usuario
	 * 
	 * @param tareaUsuario
	 * @param bDoc
	 */
	public void prepararTareaUsuario(String usuario, TareaUsuario tareaUsuario, MensajeTarea mensajeTarea) {	
		
		tareaUsuario.setObservacion(mensajeTarea.getObservacion());
		if(usuario != null){
			if(tareaUsuario.getUsuario().contains("BPM_ADMIN"))
				tareaUsuario.setUsuario(usuario);
			mensajeTarea.setUsuario(nombrePersona(usuario));
		}		
		mensajeTarea.setObservacion(null);		
		tareaUsuario.setEstadoTarea(new EstadoTarea(EstadoTareaUsuarioEnum.COMPLETADA.getClave()));
		tareaUsuario.setFechaFinTarea(new Date());
		tareaUsuario.setbDoc(xmlUtility.convertToXMLWithNoReferences(mensajeTarea));
	}

	/**
	 * Metodo que valida la tarea de transicion
	 * 
	 * @param lstTareaTransicion
	 * @throws NoExisteTransicionParaTareaUsuarioException
	 */
	public void validarTareaTransicion(List<TareaTransicion> lstTareaTransicion)
			throws NoExisteTransicionParaTareaUsuarioException {
		if (lstTareaTransicion == null || lstTareaTransicion.isEmpty()) {
			throw new NoExisteTransicionParaTareaUsuarioException("NoExisteTransicionConfigurada");
		}
		if (lstTareaTransicion.size() > 1) {
			throw new NoExisteTransicionParaTareaUsuarioException("ErrorEnLaConfiguracionDeLaTransicion");
		}
	}

	/**
	 * Metodo para verificar la existencia de tareas activas
	 * 
	 * @param bpId
	 * @param subProceso
	 * @return
	 */
	public boolean existenTaresActivas(final Long bpId, boolean subProceso) {
		boolean existen = false;
		List<Long> estadosTareaUsuario = new ArrayList<Long>();
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.ACTIVA.getClave());
		List<TareaUsuario> lstTareasUsuario = flujoTrabajoEntity.buscarTareasActivasByBpId(bpId, estadosTareaUsuario,
				subProceso);
		if (lstTareasUsuario != null && lstTareasUsuario.size() > 0)
			existen = true;
		return existen;
	}

	/**
	 * Metodo para terminar la instancia
	 * 
	 * @param instancia
	 */
	public void prepararTerminarInstancia(Instancia instancia) {
		instancia.setEstadoInstancia(new EstadoInstancia(EstadoInstanciaEnum.TERMINADA.getClave()));
	}

	/**
	 * Metodo que devuelve una lista de estados validos de las bandejas
	 * 
	 * @return
	 */
	public List<Long> prepararEstadosValidosBandejas() {
		List<Long> estadosTareaUsuario = new ArrayList<Long>();
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.ACTIVA.getClave());
		return estadosTareaUsuario;
	}

        	/**
	 * Metodo que devuelve una lista de estados validos de las bandejas
	 * 
	 * @return
	 */
	public List<Long> prepararEstadosValidosBandejasIncluyendoCompletadas() {
		List<Long> estadosTareaUsuario = new ArrayList<Long>();
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.ACTIVA.getClave());
                estadosTareaUsuario.add(EstadoTareaUsuarioEnum.COMPLETADA.getClave());
		return estadosTareaUsuario;
	}

        
	/**
	 * Metodo que devuelve una lista de tareas de bandeja
	 * 
	 * @param lstTareasUsuario
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DataPage prepararRespuestaBandejas(DataPage dataPage) {
		List<TareaBandeja> lstTareasBandejas = null;
		TareaBandeja tareaBandeja = null;
		if (dataPage.getData() != null) {
			lstTareasBandejas = new ArrayList<TareaBandeja>();
			for (TareaUsuario tareaUsuario : (List<TareaUsuario>) dataPage.getData()) {
				tareaBandeja = new TareaBandeja();
				tareaBandeja.setIdTareaUsuario(tareaUsuario.getIdTareaUsuario());
				tareaBandeja.setIdTarea(tareaUsuario.getTarea().getIdTarea());
				tareaBandeja.setNombreTarea(tareaUsuario.getTarea().getNombre());
				tareaBandeja.setIdInstancia(tareaUsuario.getInstancia().getBpId());
				tareaBandeja.setIdProceso(tareaUsuario.getInstancia().getProceso().getBp());
				tareaBandeja.setNombreProceso(tareaUsuario.getInstancia().getProceso().getNombre());
				tareaBandeja.setIdTramite(tareaUsuario.getInstancia().getIdTramite());
                                tareaBandeja.setFechaInicioTarea(tareaUsuario.getFechaInicioTarea());
				if (tareaUsuario.getInstancia().getbDoc() != null) {
					tareaBandeja.setInicioTramite(
							(InicioTramite) xmlUtility.convertToModel(tareaUsuario.getInstancia().getbDoc()));
				}
				if (tareaUsuario.getbDoc() != null) {
					tareaBandeja.setMensajeTarea((MensajeTarea) xmlUtility.convertToModel(tareaUsuario.getbDoc()));
				}
				tareaBandeja.setUsuario(tareaUsuario.getUsuario());
				lstTareasBandejas.add(tareaBandeja);
			}
		}
		dataPage.setData(lstTareasBandejas);
		return dataPage;
	}
       	   
        
        /**
	 * Metodo que devuelve una lista de tramites
	 * 
	 * @param lstT
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DataPage prepararRespuestaTramites(DataPage dataPage) {
		List<DatosTramite> lstTramites = null;
		DatosTramite datosTramite = null;
		if (dataPage.getData() != null) {
			lstTramites = new ArrayList<DatosTramite>();
			List<Object[]> datosQuery = (List<Object[]>) dataPage.getData();
			for (Object[] lstDatos : datosQuery) {
				datosTramite = new DatosTramite();
				datosTramite.setFolio((String) lstDatos[0]);
				datosTramite.setNss((String) lstDatos[1]);
				datosTramite.setTipoTramite((String) lstDatos[2]);
				datosTramite.setFechaSolicitud((String) lstDatos[3]);
				datosTramite.setEstadoTramite(lstDatos[4].toString());
				if(lstDatos.length>=6){
					datosTramite.setSubdelegacion(lstDatos[5] != null ? lstDatos[5].toString() : null);
				}
				lstTramites.add(datosTramite);
			}
		}
		dataPage.setData(lstTramites);
		return dataPage;
	}

	@SuppressWarnings("unchecked")
	public DataPage prepararRespuestaBandejaNormativos(DataPage dataPage) {
		List<DatosTarea> lstTareas = null;
		DatosTarea datosTarea = null;
		if (dataPage.getData() != null) {
			LOGGERBPM.info("LLenando bean DatosTramite");
			lstTareas = new ArrayList<DatosTarea>();
			List<Object[]> datosQuery = (List<Object[]>) dataPage.getData();
			for (Object[] lstDatos : datosQuery) {
				datosTarea = new DatosTarea();
				datosTarea.setFolio((String) lstDatos[0]);
				datosTarea.setIdSolicitud(lstDatos[1].toString());
				datosTarea.setIdTareaUsuario(Long.parseLong(lstDatos[2].toString()));
				datosTarea.setIdInstancia(Long.parseLong(lstDatos[3].toString()));
				datosTarea.setIdTramite(Integer.parseInt(lstDatos[4].toString()));
				datosTarea.setIdEstadoTramite(Integer.parseInt(lstDatos[5].toString()));
				datosTarea.setIdEstadoTarea(Integer.parseInt(lstDatos[6].toString()));
				datosTarea.setIdTarea(Long.parseLong(lstDatos[7].toString()));
				datosTarea.setUsuario((String) lstDatos[8]);
				datosTarea.setMensajeTarea((MensajeTarea) xmlUtility.convertToModel((String)lstDatos[9]));
				datosTarea.setInicioTramite((InicioTramite) xmlUtility.convertToModel((String)lstDatos[10]));
				datosTarea.setFecRegistroActualizado(lstDatos[11].toString());
				datosTarea.setFecRegistroAlta(lstDatos[12].toString());
				datosTarea.setFecIniTarea(lstDatos[13].toString());
				datosTarea.setFecFinTarea(lstDatos[14] != null ? lstDatos[14].toString() : null);
				datosTarea.setFecSolicitud(lstDatos[15].toString());
				datosTarea.setIdSubdelegacion(lstDatos[16] != null ? Long.parseLong(lstDatos[16].toString()) : null);
				datosTarea.setDescDelegacion(lstDatos[17] != null ? lstDatos[17].toString() : null);
				datosTarea.setDescSubdelegacion(lstDatos[18] != null ? lstDatos[18].toString() : null);
				lstTareas.add(datosTarea);
			}

		}
		dataPage.setData(lstTareas);
		return dataPage;
	}
	
	@SuppressWarnings("unchecked")
	public DataPage prepararRespuestaBandejaNormativosConstancias(DataPage dataPage) {
		List<DatosTarea> lstTareas = null;
		DatosTarea datosTarea = null;
		if (dataPage.getData() != null) {
			LOGGERBPM.info("LLenando bean DatosTramite Constancias");
			lstTareas = new ArrayList<DatosTarea>();
			List<Object[]> datosQuery = (List<Object[]>) dataPage.getData();
			for (Object[] lstDatos : datosQuery) {
				datosTarea = new DatosTarea();
				datosTarea.setDescSubdelegacion(lstDatos[0] != null ? lstDatos[0].toString() : null);
				datosTarea.setDescDelegacion(lstDatos[1] != null ? lstDatos[1].toString() : null);
				datosTarea.setNss(lstDatos[2] != null ? lstDatos[2].toString() : null);
				datosTarea.setCurp(lstDatos[3] != null ? lstDatos[3].toString() : null);
				datosTarea.setDescTipoSolicitud(lstDatos[4] != null ? lstDatos[4].toString() : null);
				datosTarea.setFecSolicitud(lstDatos[5] != null ? lstDatos[5].toString() : null);
				lstTareas.add(datosTarea);
			}

		}
		dataPage.setData(lstTareas);
		return dataPage;
	}
	
        public DataPage prepararRespuestaHistoricoTareas(DataPage dataPage){
            List<DatosTarea> lstTareas = null;
		DatosTarea datosTarea = null;
		if (dataPage.getData() != null) {
			LOGGERBPM.info("LLenando bean HistoricoTareas");
			lstTareas = new ArrayList<DatosTarea>();
			List<Object[]> datosQuery = (List<Object[]>) dataPage.getData();
			for (Object[] lstDatos : datosQuery) {
				datosTarea = new DatosTarea();
				datosTarea.setIdTarea(Long.parseLong(lstDatos[0].toString()));
				datosTarea.setEstadoTarea((lstDatos[1]!=null)?lstDatos[1].toString():"");
				datosTarea.setUsuario(lstDatos[2]!=null?(String) lstDatos[2]:"");
				datosTarea.setFecRegistroAlta(lstDatos[3].toString());
				datosTarea.setInicioTramite((InicioTramite) xmlUtility.convertToModel((String)lstDatos[4]));
				datosTarea.setMensajeTarea((MensajeTarea) xmlUtility.convertToModel((String)lstDatos[5]));
				datosTarea.setIdTareaUsuario(Long.parseLong(lstDatos[6].toString()));
				if(lstDatos[7] != null)
					datosTarea.setFecFinTarea(lstDatos[7].toString());

				lstTareas.add(datosTarea);
			}
		}
		dataPage.setData(lstTareas);
		return dataPage;

        }

	public void prepararListaEstatus(DataPage dataPage) {
		List<HashMap<String, Object>> listEstatus = new ArrayList<HashMap<String,Object>>();
		if(dataPage.getData()!=null){
			for(TareaUsuario t : (List<TareaUsuario>) dataPage.getData()){				
				HashMap<String,Object> estatus = new HashMap<String, Object>();
				
				MensajeTarea mensaje = new MensajeTarea();
				if(t.getbDoc() !=null){
					mensaje = (MensajeTarea) xmlUtility.convertToModel(t.getbDoc());
				
				estatus.put("fechaModificacion", t.getFechaInicioTarea());
				estatus.put("usuario", mensaje.getUsuario()!=null?mensaje.getUsuario():"");
				estatus.put("asignado", t.getUsuario()!=null?t.getUsuario():"");
				estatus.put("observacion", t.getObservacion()!=null?t.getObservacion():" ");
				listEstatus.add(estatus);
				}
			}
		}
		dataPage.setData(listEstatus);
	}

	/**
	 * Metodo reasigna una tarea de usuario
	 * 
	 * @param tareaUsuario
	 */
	public void prepararTareaReasignada(TareaUsuario tareaUsuario) {
		tareaUsuario.setEstadoTarea(new EstadoTarea(EstadoTareaUsuarioEnum.REASIGNADA.getClave()));
		tareaUsuario.setFechaAsignacion(new Date());
	}

	/**
	 * Metodo para la cambiar la asignacion a otro usuario
	 * 
	 * @param bpId
	 * @param idParticipante
	 * @param usuario
	 * @return
	 */
	public Asignacion prepararCambioUsuarioAsignacion(Long bpId, Long idParticipante, final String usuario) {
		Asignacion asignacion = flujoTrabajoEntity
				.buscarAsignacionByInsPart(flujoTrabajoComunUtility.llenarAsignacion(bpId, idParticipante));
		asignacion.setUsuario(usuario);
		return asignacion;
	}

	/**
	 * Metodo para verificar si se actualiza la asignacion
	 * 
	 * @param bpId
	 * @param mensajeTarea
	 */
	public void validarSiActualizaAsignacion(Instancia instancia, Map<String, String> participantes) {
		LOGGERBPM.info("Instancia {} {}",instancia.getBpId(),participantes);
		if (participantes != null && participantes.size() > 0) {
			for (Map.Entry<String, String> participante : participantes.entrySet()) {
				actualizaAsignacion(instancia, participante.getKey(), participante.getValue());
			}
		}
	}

	/**
	 * Metodo para actualizar la asignacion
	 * 
	 * @param bpId
	 * @param idParticipante
	 * @param usuario
	 */
	private void actualizaAsignacion(Instancia instancia, String rol, String usuario) {
		LOGGERBPM.info("Rol {},{},{}",new Object[]{instancia.getBpId(),rol,usuario});
		if (usuario != null && !usuario.isEmpty()) {
			Asignacion asignacion = flujoTrabajoEntity
					.buscarAsignacionByInsRol(flujoTrabajoComunUtility.llenarAsignacion(instancia.getBpId(), rol));
			LOGGERBPM.info("Asignacion {}",asignacion);
			if (asignacion == null) {
				List<Tarea> lstTareas = flujoTrabajoEntity.buscarTareaPorNombre(instancia.getProceso().getBp(), rol);
				LOGGERBPM.info("Tareas {}",lstTareas);
				if (lstTareas != null && lstTareas.size() > 0) {
					LOGGERBPM.info("Participante {}",lstTareas.get(0).getParticipante().getIdParticipante());
					asignacion = flujoTrabajoComunUtility.llenarAsignacion(instancia.getBpId(),
							lstTareas.get(0).getParticipante().getIdParticipante(), usuario);
				}
			} else{
				asignacion.setUsuario(usuario);
			}
			
			LOGGERBPM.info("Asignacion Usuario {}",asignacion.getUsuario());
			flujoTrabajoEntity.guardarAsignacion(asignacion);
		}
	}

	/**
	 * Metodo que devuelveun lista de estados validos del Timmer
	 * 
	 * @return
	 */
	public List<Long> prepararEstadosValidosTimmer() {
		List<Long> estadosTareaUsuario = new ArrayList<Long>();
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.ACTIVA.getClave());
		return estadosTareaUsuario;
	}

	/**
	 * Metodo que asigna un mensaje de tarea
	 * 
	 * @return
	 */
	public MensajeTarea prepararBDocTimmer() {
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setTipoTransicion(TipoTransicionEnum.TIMMER.getId());
		return mensajeTarea;
	}

	/**
	 * Metodo que actualiza los datos BDoc de la instancia
	 * 
	 * @param tareaUsuario
	 * @param mensajeTarea
	 */
	public void actualizarDatosBDocInstancia(TareaUsuario tareaUsuario, MensajeTarea mensajeTarea) {
		InicioTramite inicioTramite = (InicioTramite) xmlUtility.convertToModel(tareaUsuario.getInstancia().getbDoc());
		inicioTramite.setEstatus(StringUtils.isNotBlank(mensajeTarea.getEstado())?mensajeTarea.getEstado():inicioTramite.getEstatus());
		inicioTramite.setFechaActualizacion(StringUtils.isNotBlank(mensajeTarea.getFechaActualizacion())?mensajeTarea.getFechaActualizacion():inicioTramite.getFechaActualizacion());
		if (StringUtils.isNotBlank(mensajeTarea.getData())){
			LOGGERBPM.info("Bdoc Instancia {} ",tareaUsuario.getInstancia().getbDoc());
			LOGGERBPM.info("Bdoc Tarea {} ",tareaUsuario.getbDoc());			
			Map<String, Object> mapa = generarJavaDataWf(inicioTramite.getData());
			mapa.putAll(generarJavaDataWf(mensajeTarea.getData()));
			inicioTramite.setData(generarJsonDataWf(mapa));
			LOGGERBPM.info("Bdoc conjunto {}",inicioTramite.getData());
		}		
		if (mensajeTarea.getParametros() != null) {
			for (Map.Entry<String, Long> param : mensajeTarea.getParametros().entrySet()) {
				inicioTramite.getParametros().put(param.getKey(), param.getValue());
			}
		}
		LOGGERBPM.debug("Participantes : {}",mensajeTarea.getParticipantes());
		if (mensajeTarea.getParticipantes() != null) {
			for (Map.Entry<String, String> particip : mensajeTarea.getParticipantes().entrySet()) {
				inicioTramite.getParticipantes().put(particip.getKey(), particip.getValue());
			}
		}
		
		LOGGERBPM.debug("XML Generado: {}",xmlUtility.convertToXMLWithNoReferences(inicioTramite));
		tareaUsuario.getInstancia().setbDoc(xmlUtility.convertToXMLWithNoReferences(inicioTramite));
		tareaUsuario.getInstancia().getDatosAuditoria().setFecRegistroActualizado(new Date());
		flujoTrabajoEntity.actualizarInstancia(tareaUsuario.getInstancia());
		LOGGERBPM.info("INSTANCIA [{}]", tareaUsuario.getInstancia());
		LOGGERBPM.info("FECHA* [{}]", tareaUsuario.getInstancia().getDatosAuditoria().getFecRegistroActualizado());
	}

	/**
	 * Metodo que obtiene la ultima tarea del bdoc
	 * 
	 * @param bpId
	 * @return
	 */
	public String obtenerBDocUltimaTarea(final Long bpId) {
		TareaUsuario tareaUsuario = flujoTrabajoEntity.obtenerUltimaTareaCompletada(bpId);
		if (tareaUsuario != null)
			return tareaUsuario.getbDoc();
		return null;

	}
	
	/**
	 * Metodo que devuelve una lista de tareas de bandeja
	 * 
	 * @param lstTareasUsuario
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DataPage prepararRespuestaBandejasHistorico(DataPage dataPage) {		
		List<TareaBandeja> lstTareasBandejas = new ArrayList<TareaBandeja>();
		TareaBandeja tareaBandeja = null;
		if (dataPage.getData() != null) {
			for (Instancia instancia : (List<Instancia>) dataPage.getData()) {
				tareaBandeja = new TareaBandeja();
				tareaBandeja.setIdInstancia(instancia.getBpId());
				tareaBandeja.setIdProceso(instancia.getProceso().getBp());
				tareaBandeja.setNombreProceso(instancia.getProceso().getNombre());
				tareaBandeja.setIdTramite(instancia.getIdTramite());
				if (instancia.getbDoc() != null) {
					tareaBandeja.setInicioTramite((InicioTramite) xmlUtility
							.convertToModel(instancia.getbDoc()));		
					if(tareaBandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion())!= null
							&& tareaBandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()).contains("BPM_ADMIN") && flujoTrabajoEntity.obtenerUltimaTareaCompletada(instancia.getBpId()) != null){
					tareaBandeja.getInicioTramite().getParticipantes().put(ParticipantesEnum.AUTORIZADOR.getDescripcion(), flujoTrabajoEntity.obtenerUltimaTareaCompletada(instancia.getBpId()).getUsuario());
					}
					
				}
				lstTareasBandejas.add(tareaBandeja);
			}
		}
		dataPage.setData(lstTareasBandejas);
		return dataPage;
	}

	/**
	 * Metodo que obtiene la ultima tarea 
	 * 
	 * @param bpId
	 * @return
	 */
	@Override	
	public Map<String, String> obtenerParticipantesUltimaTarea(Long bpId) {
		TareaUsuario tareaUsuario = flujoTrabajoEntity.obtenerUltimaTareaCompletada(bpId);
		if (tareaUsuario != null){
			Map<String,String> participantes=new HashMap<String, String>();
			participantes.put(ParticipantesEnum.AUTORIZADOR.getDescripcion(), tareaUsuario.getUsuario());
			InicioTramite inicioTramite = (InicioTramite) xmlUtility
					.convertToModel(tareaUsuario.getInstancia().getbDoc());
			participantes.put(ParticipantesEnum.RESPONSABLE.getDescripcion(), inicioTramite.getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
			return participantes;
		}
		return null;
	}

	private static Map<String, Object> generarJavaDataWf(String json){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
		} catch (JsonParseException e) {
			LOGGERBPM.error("Error al generar el objeto Java del workflow", e);
		} catch (JsonMappingException e) {
			LOGGERBPM.error("Error al generar el objeto Java del workflow", e);
		} catch (IOException e) {
			LOGGERBPM.error("Error al generar el objeto Java del workflow", e);
		}
		return map;
	}
	
	public String generarJsonDataWf(Map<String, Object> data){
		String jsonParams ="";
		
		ObjectMapper mapper = new ObjectMapper();		
		try {
			jsonParams = mapper.writeValueAsString(data);
		} catch (JsonGenerationException e) {
			LOGGERBPM.error("Error al parsear el Json del workflow", e);
		} catch (JsonMappingException e) {
			LOGGERBPM.error("Error al parsear el Json del workflow", e);
		} catch (IOException e) {
			LOGGERBPM.error("Error al parsear el Json del workflow", e);
		}
		return jsonParams;
	}
	
	@Override
	public String nombrePersona(String curp){
		String nombreCompleto="";
		try {
			Usuario nombre = componentesExternosBusiness.recuperaUsuarioEsquemaSeguridadByCURP(curp);
			if(nombre!=null){
				nombreCompleto = nombre.getFisica().getNombreCompleto();
			}
		} catch (EsquemaSegurdiadException e) {
			LOGGERBPM.error("Error al recuperar usuario {}",e);
		} catch (UsuarioNoEncontradoException e) {
			LOGGERBPM.error("Error al recuperar usuario {}",e);
		}
		return nombreCompleto;
	}
	
	@Override
	public void prepararbDocAnterior(TareaUsuario tareaAnterior, String usuario){
		if(tareaAnterior.getbDoc() != null){
			MensajeTarea mensaje = (MensajeTarea) xmlUtility.convertToModel(tareaAnterior.getbDoc());
//			if(usuario !=null){
//				mensaje.setAsignado(nombrePersona(usuario));
//			}
			tareaAnterior.setbDoc(xmlUtility.convertToXMLWithNoReferences(mensaje));
		}
	}
	
	/**
	 * Metodo para preparar la tarea de usuario
	 * 
	 * @param tareaUsuario
	 * @param bDoc
	 */
	@Override
	public void prepararTareaUsuario(TareaUsuario tareaUsuario, MensajeTarea mensajeTarea) {	
		
		tareaUsuario.setObservacion(mensajeTarea.getObservacion());	
		mensajeTarea.setObservacion(null);
		//mensajeTarea.setAsignado(nombrePersona(""));
		//mensajeTarea.setUsuario(nombrePersona(mensajeTarea.getUsuario()));
		tareaUsuario.setbDoc(xmlUtility.convertToXMLWithNoReferences(mensajeTarea));
	}
	
	/**
	 * Metodo que devuelve una lista de estados validos de las bandejas
	 * 
	 * @return
	 */
	@Override
	public List<Long> prepararEstadosValidosBitacora() {
		List<Long> estadosTareaUsuario = new ArrayList<Long>();
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.ACTIVA.getClave());
		estadosTareaUsuario.add(EstadoTareaUsuarioEnum.COMPLETADA.getClave());
		return estadosTareaUsuario;
	}
	
	@Override
	public void preparaTareaSeguimiento(TareaUsuario tareaUsuario, TareaBandeja tareaBandeja){
		
		tareaBandeja.setIdTareaUsuario(tareaUsuario.getIdTareaUsuario());
		tareaBandeja.setIdTarea(tareaUsuario.getTarea().getIdTarea());
		tareaBandeja.setNombreTarea(tareaUsuario.getTarea().getNombre());
		tareaBandeja.setIdInstancia(tareaUsuario.getInstancia().getBpId());
		tareaBandeja.setIdProceso(tareaUsuario.getInstancia().getProceso().getBp());
		tareaBandeja.setNombreProceso(tareaUsuario.getInstancia().getProceso().getNombre());
		tareaBandeja.setIdTramite(tareaUsuario.getInstancia().getIdTramite());
		if (tareaUsuario.getInstancia().getbDoc() != null) {
			tareaBandeja.setInicioTramite(
					(InicioTramite) xmlUtility.convertToModel(tareaUsuario.getInstancia().getbDoc()));
		}
		if (tareaUsuario.getbDoc() != null) {
			tareaBandeja.setMensajeTarea((MensajeTarea) xmlUtility.convertToModel(tareaUsuario.getbDoc()));
		}
	}
	
	@Override
	public void preparaTareasSeguimiento(List<TareaUsuario> tareasUsuario, List<TareaBandeja> tareasBandeja){
		TareaBandeja tareaBandeja = null;
		LOGGERBPM.info("Entrando a llenar la lista de tareas");
		for(TareaUsuario tareaUsuario : tareasUsuario){
			tareaBandeja = new TareaBandeja();
			tareaBandeja.setIdTareaUsuario(tareaUsuario.getIdTareaUsuario());
			tareaBandeja.setIdTarea(tareaUsuario.getTarea().getIdTarea());
			tareaBandeja.setNombreTarea(tareaUsuario.getTarea().getNombre());
			tareaBandeja.setIdInstancia(tareaUsuario.getInstancia().getBpId());
			tareaBandeja.setIdProceso(tareaUsuario.getInstancia().getProceso().getBp());
			tareaBandeja.setNombreProceso(tareaUsuario.getInstancia().getProceso().getNombre());
			tareaBandeja.setIdTramite(tareaUsuario.getInstancia().getIdTramite());
			if (tareaUsuario.getInstancia().getbDoc() != null) {
				LOGGERBPM.info("Se obtienen los datos del BDoc de la instancia");
				tareaBandeja.setInicioTramite((InicioTramite) xmlUtility.convertToModel(tareaUsuario.getInstancia().getbDoc()));
			}
			if (tareaUsuario.getbDoc() != null) {
				LOGGERBPM.info("Se obtienen los datos del BDoc de la tarea usuario ");
				tareaBandeja.setMensajeTarea((MensajeTarea) xmlUtility.convertToModel(tareaUsuario.getbDoc()));
			}
			tareasBandeja.add(tareaBandeja);
			LOGGERBPM.info("Termina el flujo");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public String obtenerResponsableSubdelegacion(DataPage dataPage) {
		String curp="";
			
				if (((List<Instancia>) dataPage.getData()).get(0).getbDoc() != null) {
					 InicioTramite inicioTramite= (InicioTramite) xmlUtility.convertToModel(((List<Instancia>) dataPage.getData()).get(0).getbDoc());
					 if(inicioTramite!= null){
						 curp =  inicioTramite.getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion());
					 }
			}
		return curp;
	}
	
	public TareaBandeja obtenerTareaByInstancia(Instancia instancia) {	
		TareaBandeja tareaBandeja = null;
		if (instancia != null) {
				tareaBandeja = new TareaBandeja();
				tareaBandeja.setIdInstancia(instancia.getBpId());
				tareaBandeja.setIdProceso(instancia.getProceso().getBp());
				tareaBandeja.setNombreProceso(instancia.getProceso().getNombre());
				tareaBandeja.setIdTramite(instancia.getIdTramite());
				if (instancia.getbDoc() != null) {
					tareaBandeja.setInicioTramite((InicioTramite) xmlUtility.convertToModel(instancia.getbDoc()));		
					}	
		}
		return tareaBandeja;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataPage transformObjectToReporteCDA(DataPage dataPage){
		List<Object[]> query = (List<Object[]>) dataPage.getData();
		List<ReporteCDA> lista = new ArrayList<ReporteCDA>();
		BigDecimal tmp = null;
		for(Object []  obj : query){
			ReporteCDA nuevoRegistro = new ReporteCDA();
			tmp = (BigDecimal) obj[CERO];
			nuevoRegistro.setIdTramite(tmp.toString());
			tmp = (BigDecimal) obj[UNO];
            nuevoRegistro.setIdSolicitud(tmp.toString());
            nuevoRegistro.setFolio( obj[DOS]  !=null  ? obj[DOS].toString() : null );
            nuevoRegistro.setCurp( obj[TRES]  !=null  ? (String)obj[TRES] : null );
            nuevoRegistro.setNssInvolucrado( obj[CUATRO]  !=null  ? obj[CUATRO].toString() : null );
            nuevoRegistro.setVencido( obj[CINCO]  !=null  ? obj[CINCO].toString() : null );
            nuevoRegistro.setDelegacion( obj[SEIS]  !=null  ? obj[SEIS].toString() : null );
            nuevoRegistro.setSubdelegacion( obj[SIETE]  !=null  ? obj[SIETE].toString() : null );
            nuevoRegistro.setAutorizo( obj[OCHO]  !=null  ? obj[OCHO].toString() : null );
            nuevoRegistro.setResponsable( obj[NUEVE]  !=null  ? obj[NUEVE].toString() : null );
            nuevoRegistro.setOrigen( obj[DIEZ]  !=null  ? obj[DIEZ].toString() : null );
            nuevoRegistro.setTipoTramite( obj[ONCE]  !=null  ? obj[ONCE].toString() : null );
            nuevoRegistro.setFechaSolicitud( obj[DOCE]  !=null  ? obj[DOCE].toString() : null );
            nuevoRegistro.setFechaFinalizacion( obj[TRECE]  !=null  ? obj[TRECE].toString() : null );
            nuevoRegistro.setFechaActualizacion( obj[CATORCE]  !=null  ? obj[CATORCE].toString() : null );
            tmp = (BigDecimal) obj[QUINCE];
            nuevoRegistro.setEstado(tmp.toString());
            nuevoRegistro.setReasignado(obj[DIECISEIS]  !=null  ? obj[DIECISEIS].toString() : null );

			lista.add(nuevoRegistro);
		}
		dataPage.setData(lista);
		
		return dataPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataPage transformObjectToConteoReporteCDA(DataPage dataPage) {
		List<Object[]> query = (List<Object[]>) dataPage.getData();
		List<ConteoReporteCDA> lista = new ArrayList<ConteoReporteCDA>();
		for(Object []  obj : query){
			ConteoReporteCDA nuevoRegistro = new ConteoReporteCDA();
			nuevoRegistro.setDescripcion(obj[CERO]  !=null  ? obj[CERO].toString() : null);
			nuevoRegistro.setTotal(obj[UNO]  !=null  ? obj[UNO].toString() : null);
			lista.add(nuevoRegistro);
		}
		dataPage.setData(lista);
		
		return dataPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataPage transformObjectToOrigenesConteoReporteCDA(DataPage dataPage) {
		List<Object[]> query = (List<Object[]>) dataPage.getData();
		List<EstadisticasReporteCDA> lista = new ArrayList<EstadisticasReporteCDA>();
		EstadisticasReporteCDA nuevoRegistro = new EstadisticasReporteCDA();
		BigDecimal tmp = null;
		for(Object []  obj : query){
			if(obj[CERO].equals("INTERNET")){
				tmp = (BigDecimal) obj[UNO];
				nuevoRegistro.setNumeroInternet(tmp.toString());
			}
			if(obj[CERO].equals("VENTANILLA")){
				tmp = (BigDecimal) obj[UNO];
				nuevoRegistro.setNumeroVentanilla(tmp.toString());
			}	
		}
		if(nuevoRegistro.getNumeroVentanilla() == null || nuevoRegistro.getNumeroVentanilla().isEmpty()){
			nuevoRegistro.setNumeroVentanilla("0");
		}
		if(nuevoRegistro.getNumeroInternet() == null || nuevoRegistro.getNumeroInternet().isEmpty()){
			nuevoRegistro.setNumeroInternet("0");
		}
		lista.add(nuevoRegistro);
		dataPage.setData(lista);
		
		return dataPage;
	}

	@SuppressWarnings("unchecked")
	public DataPage prepararPortabilidadBandejaNormativos(DataPage dataPage) {
		List<DatosTarea> lstTareas = null;
		DatosTarea datosTarea = null;
		if (dataPage.getData() != null) {
			LOGGERBPM.info("Llenando bean DatosTramitePortabilidad");
			lstTareas = new ArrayList<DatosTarea>();
			List<Object[]> datosQuery = (List<Object[]>) dataPage.getData();
			for (Object[] lstDatos : datosQuery) {
				datosTarea = new DatosTarea();
				datosTarea.setFolio((String) lstDatos[0]);
				datosTarea.setIdSolicitud(lstDatos[1].toString());
				datosTarea.setIdTramite(Integer.parseInt(lstDatos[2].toString()));
				datosTarea.setIdEstadoTramite(Integer.parseInt(lstDatos[3].toString()));
				datosTarea.setIdSubdelegacion(lstDatos[4] != null ? Long.parseLong(lstDatos[4].toString()) : null);
				datosTarea.setDescDelegacion(lstDatos[5] != null ? lstDatos[5].toString() : null);
				datosTarea.setDescSubdelegacion(lstDatos[6] != null ? lstDatos[6].toString() : null);
				datosTarea.setFecSolicitud(lstDatos[7] != null ? lstDatos[7].toString() : null);
				datosTarea.setFecRegistroActualizado(lstDatos[8] != null ? lstDatos[8].toString() : null);
				datosTarea.setIdEstadoTarea(lstDatos[9] != null ? Integer.parseInt(lstDatos[9].toString()) : null);
				datosTarea.setEstadoTarea(lstDatos[10] != null ? lstDatos[10].toString() : null);
				datosTarea.setNss(lstDatos[11] != null ? lstDatos[11].toString() : null);
				datosTarea.setCurp(lstDatos[12] != null ? lstDatos[12].toString() : null);
				datosTarea.setIdTarea(lstDatos[13] != null ? Long.parseLong(lstDatos[13].toString()) : null);
				datosTarea.setDescTipoSolicitud(lstDatos[14] != null ? lstDatos[14].toString() : null);
				Map<Long, String> observaciones = new HashMap<Long, String>();
				observaciones.put(1L, lstDatos[15] != null ? lstDatos[15].toString() : ""); //"observSolicitud"
				observaciones.put(2L, lstDatos[16] != null ? lstDatos[16].toString() : ""); //"observTramite"
				MensajeTarea mt = new MensajeTarea();
				mt.setSubProcesos(observaciones);
				mt.setIdSubProceso(lstDatos[17] != null ? Long.parseLong(lstDatos[17].toString()) : null);
				datosTarea.setMensajeTarea(mt);
				lstTareas.add(datosTarea);
			}
		}

		dataPage.setData(lstTareas);
		return dataPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataPage prepararPortabilidadIsssteImssBandejaNormativos(DataPage dataPage) {
		List<DatosTarea> lstTareas = null;
		DatosTarea datosTarea = null;
		if (dataPage.getData() != null) {
			LOGGERBPM.info("LLenando bean DatosTramitePortabilidad");
			lstTareas = new ArrayList<DatosTarea>();
			List<Object[]> datosQuery = (List<Object[]>) dataPage.getData();
			for (Object[] lstDatos : datosQuery) {
				datosTarea = new DatosTarea();
				datosTarea.setFolio((String) lstDatos[0]);
				datosTarea.setIdSolicitud(lstDatos[1].toString());
				datosTarea.setIdTramite(Integer.parseInt(lstDatos[2].toString()));
				datosTarea.setIdEstadoTramite(Integer.parseInt(lstDatos[3].toString()));
				datosTarea.setIdEstadoTarea(lstDatos[4] != null ? Integer.parseInt(lstDatos[4].toString()) : null);
				datosTarea.setEstadoTarea(lstDatos[5] != null ? lstDatos[5].toString() : null);
				datosTarea.setFecSolicitud(lstDatos[6] != null ? lstDatos[6].toString() : null);
				datosTarea.setNss(lstDatos[7] != null ? lstDatos[7].toString() : null);
				datosTarea.setCurp(lstDatos[8] != null ? lstDatos[8].toString() : null);
				datosTarea.setDescTipoSolicitud(lstDatos[9] != null ? lstDatos[9].toString() : null);
				MensajeTarea mt = new MensajeTarea();
				// mt.setObservacion(lstDatos[10] != null ? lstDatos[10].toString() : null);
				datosTarea.setMensajeTarea(mt);
				lstTareas.add(datosTarea);
			}

		}
		dataPage.setData(lstTareas);
		return dataPage;
	}

}
