package mx.gob.imss.cit.gestion.solicitud.flujo.service.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaYaAsignadaAlUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.EstadoInstanciaEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.EstadoTareaUsuarioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.*;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.utility.FlujoTrabajoUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.utility.XmlUtilityLocal;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.EstadoInstancia;

@Stateless(name = "flujoTrabajoBusiness", mappedName = "flujoTrabajoBusiness")
public class FlujoTrabajoBusiness implements FlujoTrabajoRemote {

    private static final Logger LOGGERBPM = LoggerFactory.getLogger(FlujoTrabajoBusiness.class);

    @EJB
    private FlujoTrabajoLocal flujoTrabajoEntity;

    @EJB
    private FlujoTrabajoUtilityLocal flujoTrabajoUtility;

    @EJB
    private XmlUtilityLocal xmlUtility;

    @EJB(name = "componentesExternosBusiness", mappedName = "componentesExternosBusiness")
    private ComponentesExternosBusinessRemote componentesExternosBusiness;

    @Override
    public Long iniciarWorkFlow(Long bp, InicioTramite inicioTramite)
            throws TareaInicialException, TereaSinUsuarioAsignadoException {
        LOGGERBPM.info("iniciarWorkFlow [{}]", inicioTramite);

        LOGGERBPM.info("Se valida si existe el proceso y la tarea");
        Tarea tarea = flujoTrabajoUtility.validarTareaInicial(flujoTrabajoEntity.obtenerTareaInicial(bp));

        LOGGERBPM.info("Se genera la instancia");
        Instancia instancia = flujoTrabajoEntity
                .guardarInstancia(flujoTrabajoUtility.crearInstancia(tarea.getProceso(), inicioTramite));

        LOGGERBPM.info("Se genera la asigacion");
        flujoTrabajoEntity.guardarAsignaciones(
                flujoTrabajoUtility.crearAsignaciones(bp, instancia.getBpId(), inicioTramite.getParticipantes()));

        LOGGERBPM.info("Se genera la tarea de usuario");
        TareaUsuario tareaUsuario = flujoTrabajoEntity
                .guardarTareaUsuario(flujoTrabajoUtility.crearTareaUsuario(instancia, tarea));

        LOGGERBPM.info("terminaWorkFlow [{}]", tareaUsuario);

        return instancia.getBpId();
    }
	
    public Long iniciarWorkFlow(Long bp, InicioTramite inicioTramite, Long idTarea)
            throws TareaInicialException, TereaSinUsuarioAsignadoException {
        LOGGERBPM.info("iniciarWorkFlow [{}]", inicioTramite);

        
        Tarea tarea = flujoTrabajoEntity.obtenerTareaPorId(idTarea);

        LOGGERBPM.info("Se genera la instancia");
        Instancia instancia = flujoTrabajoEntity
                .guardarInstancia(flujoTrabajoUtility.crearInstancia(tarea.getProceso(), inicioTramite));

        LOGGERBPM.info("Se genera la asigacion");
        flujoTrabajoEntity.guardarAsignaciones(
                flujoTrabajoUtility.crearAsignaciones(bp, instancia.getBpId(), inicioTramite.getParticipantes()));

        LOGGERBPM.info("Se genera la tarea de usuario");
        TareaUsuario tareaUsuario = flujoTrabajoEntity
                .guardarTareaUsuario(flujoTrabajoUtility.crearTareaUsuario(instancia, tarea));

        LOGGERBPM.info("terminaWorkFlow [{}]", tareaUsuario);

        return instancia.getBpId();
    }

    @Override
    public void completarTarea(Long idTareaUsuario, MensajeTarea mensajeTarea)
            throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
            NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException {
        completarTarea(idTareaUsuario, null, mensajeTarea);
    }

    @Override
    public void completarTarea(Long idTareaUsuario, String usuario, MensajeTarea mensajeTarea)
            throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
            NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException {
        LOGGERBPM.info("completarTarea [{}-{}]", idTareaUsuario, mensajeTarea);
        LOGGERBPM.info("Estado [{},{}]", idTareaUsuario, mensajeTarea.getEstado());

        TareaUsuario tareaUsuario = flujoTrabajoEntity.buscarTareaUsuarioById(idTareaUsuario);
        LOGGERBPM.info("validarTareaUsuario [{}]", tareaUsuario);
        flujoTrabajoUtility.validarTareaUsuario(tareaUsuario);
        flujoTrabajoUtility.prepararTareaUsuario(usuario, tareaUsuario, mensajeTarea);
        LOGGERBPM.info("Estado", idTareaUsuario, mensajeTarea.getEstado());

        LOGGERBPM.info("Se avanza la tarea de usuario [{}]", tareaUsuario);

        LOGGERBPM.info("Se valida si se debe actualizar las asignaciones");
        flujoTrabajoUtility.validarSiActualizaAsignacion(tareaUsuario.getInstancia(), mensajeTarea.getParticipantes());

        LOGGERBPM.info("Se actualizan los atributos del bdoc");
        LOGGERBPM.info("Estado [{}-{}]", idTareaUsuario, mensajeTarea.getEstado());
        flujoTrabajoUtility.actualizarDatosBDocInstancia(tareaUsuario, mensajeTarea);

        String bDocUltimaTarea = flujoTrabajoUtility.obtenerBDocUltimaTarea(tareaUsuario.getInstancia().getBpId());

        Boolean existenTareasActivas = flujoTrabajoUtility.existenTaresActivas(tareaUsuario.getInstancia().getBpId(),
                true);

        avanzaWorkFlow(tareaUsuario.getInstancia(), tareaUsuario.getTarea().getIdTarea(),
                mensajeTarea.getTipoTransicion(), bDocUltimaTarea, existenTareasActivas);

    }

    private void avanzaWorkFlow(Instancia instancia, final Long idTarea, final String tipoTransicion, String bDocTarea,
            final Boolean existenTareasActivas)
            throws TereaSinUsuarioAsignadoException, NoExisteTransicionParaTareaUsuarioException {
        LOGGERBPM.info("Se inicia la validacion para saber si hay mas tareas que activar");
        if (!flujoTrabajoUtility.existenTaresActivas(instancia.getBpId(), false)) {
            List<TareaTransicion> lstTareaTransicion = flujoTrabajoEntity.buscarTareaTransicion(idTarea,
                    tipoTransicion);
            flujoTrabajoUtility.validarTareaTransicion(lstTareaTransicion);
            List<TransicionTarea> lstTransicionTarea = flujoTrabajoEntity
                    .buscarTransicionTarea(lstTareaTransicion.get(0).getTransicion().getIdTransicion());
            if (lstTransicionTarea == null || lstTransicionTarea.isEmpty()) {
                flujoTrabajoUtility.prepararTerminarInstancia(instancia);
                LOGGERBPM.info("Se termina la instancia [{}]", instancia);
                flujoTrabajoEntity.actualizarInstancia(instancia);
            } else {
                LOGGERBPM.info("Se generan las tareas de sistema");
                // EjecutarTareaSistema
                LOGGERBPM.info("Se generan las tareas de usuario");
                flujoTrabajoEntity
                        .guardarTareasUsuario(flujoTrabajoUtility.crearTareasUsuario(lstTransicionTarea, instancia));
            }
        } else {
            LOGGERBPM.info("existen tareas paralelas activas");
        }
    }

    public void reasignarTarea(final String usuario, final Long idTarea, MensajeTarea mensajeTarea) throws NoExisteTareaUsuarioException,
            EstadoTareaUsuarioNoValidoException, TareaYaAsignadaAlUsuarioException {
        LOGGERBPM.info("reasignarTarea usuario [{}], idTarea [{}]", usuario, idTarea);
        TareaUsuario tareaUsuario = flujoTrabajoEntity.buscarTareaUsuarioById(idTarea);
        flujoTrabajoUtility.validarTareaUsuarioReasignar(tareaUsuario, usuario);
        TareaUsuario tareaAnterior = tareaUsuario;
        flujoTrabajoUtility.prepararTareaReasignada(tareaUsuario);

        LOGGERBPM.info("se actualiza el estado a la tarea actual");
        flujoTrabajoUtility.prepararbDocAnterior(tareaAnterior, usuario);
        flujoTrabajoEntity.guardarTareaUsuario(tareaAnterior);

        LOGGERBPM.info("se cambiar la asignacion al nuevo usuario");
        Asignacion asignacion = flujoTrabajoUtility.prepararCambioUsuarioAsignacion(tareaUsuario.getInstancia().getBpId(),
                tareaUsuario.getTarea().getParticipante().getIdParticipante(), usuario);
        flujoTrabajoEntity.guardarAsignacion(asignacion);

        LOGGERBPM.info("se asigna tarea usuario al nuevo usuario");
        TareaUsuario tareaUsuarioAsignar = flujoTrabajoUtility.crearTareaReasignarUsuario(tareaUsuario.getInstancia(),
                tareaUsuario.getTarea(), usuario, mensajeTarea);
        flujoTrabajoEntity.guardarTareaUsuario(tareaUsuarioAsignar);

        LOGGERBPM.info("Se actualizan los atributos del bdoc");

        flujoTrabajoUtility.actualizarDatosBDocInstancia(tareaUsuario, mensajeTarea);
    }

    @Override
    @SuppressWarnings("unchecked")
    public DataPage obtenerTareasPorUsuario(DataPage dataPage, String usuario, List<Long> idsProcesos) {
        dataPage = flujoTrabajoEntity.obtenerTareasPorUsuario(dataPage, usuario, flujoTrabajoUtility.prepararEstadosValidosBandejas(), idsProcesos);
        dataPage = flujoTrabajoUtility.prepararRespuestaBandejas(dataPage);
        return dataPage;
    }

    @Override
    public DataPage obtenerBitacoraEstatus(DataPage dataPage, final Integer idTramite) {
        dataPage = flujoTrabajoEntity.obtenerBitacoraEstatus(dataPage, idTramite, flujoTrabajoUtility.prepararEstadosValidosBitacora());
        flujoTrabajoUtility.prepararListaEstatus(dataPage);
        return dataPage;
    }

    @Override
    public DataPage obtenerTareasPorSubdelegacion(DataPage dataPage, String subdelegacion, List<Long> idsProcesos) {
        LOGGERBPM.info("Usuario {}", subdelegacion);
        dataPage = flujoTrabajoEntity.obtenerTareasPorSubdelegacion(dataPage, subdelegacion, flujoTrabajoUtility.prepararEstadosValidosBandejas(),
                idsProcesos);
        dataPage = flujoTrabajoUtility.prepararRespuestaBandejas(dataPage);
        return dataPage;
    }

    @Override
    public DataPage obtenerTramitesPorSubdelegacion(DataPage dataPage, String subdelegacion, List<Long> idsProcesos){
        LOGGERBPM.info("Usuario {}", subdelegacion);
        dataPage = flujoTrabajoEntity.obtenerTramitesPorSubdelegacion(dataPage, subdelegacion, flujoTrabajoUtility.prepararEstadosValidosBandejas(),
                idsProcesos);
        dataPage = flujoTrabajoUtility.prepararRespuestaTramites(dataPage);
        return dataPage;
    }
    
    @Override
    public DataPage obtenerTareasPorSubdelegacionIncluyendoCompletadas(DataPage dataPage, String subdelegacion, List<Long> idsProcesos) {
        LOGGERBPM.info("Usuario {}", subdelegacion);
        dataPage = flujoTrabajoEntity.obtenerTareasPorSubdelegacionIncluyendoCompletadas(dataPage,
                subdelegacion, flujoTrabajoUtility.prepararEstadosValidosBandejasIncluyendoCompletadas(),
                idsProcesos);
        dataPage = flujoTrabajoUtility.prepararRespuestaBandejas(dataPage);
        return dataPage;
    }
    
	@Override
	public DataPage obtenerTareasPorUsuarioNormativo(DataPage dataPage, List<Long> idsProcesos) {
		LOGGERBPM.info("Usuario {}");
		dataPage = flujoTrabajoEntity.obtenerTareasPorUsuarioNormativo(dataPage,
				flujoTrabajoUtility.prepararEstadosValidosBandejasIncluyendoCompletadas(), idsProcesos);
		dataPage = flujoTrabajoUtility.prepararRespuestaBandejaNormativos(dataPage);
		return dataPage;
	}

    /**
     * Metodo para obtener una lista de tareas por usuario
     */
    @Override
    public DataPage obtenerInstanciasHistoricasPorUsuario(DataPage dataPage, String usuario, List<Long> idsProcesos) {
        DataPage dataPageReturn = null;
        dataPageReturn = flujoTrabajoEntity.obtenerInstanciasHistoricasPorUsuario(dataPage, usuario, idsProcesos);
        dataPageReturn = flujoTrabajoUtility.prepararRespuestaBandejasHistorico(dataPage);
        return dataPageReturn;
    }
	
	@Override
	public DataPage obtenerInstanciasHistoricasCDA(DataPage dataPage,final String usuario,final String subdelegacion, Long idProceso) {
        DataPage dataPageReturn = null;
        dataPageReturn = flujoTrabajoEntity.obtenerInstanciasHistoricasCDA(dataPage, usuario, subdelegacion, idProceso);
        dataPageReturn = flujoTrabajoUtility.prepararRespuestaBandejasHistorico(dataPage);
        return dataPageReturn;
    }
    /**
     * Metodo para obtener una lista de tareas por subdelegacion
     */
    @Override
    public DataPage obtenerInstanciasHistoricasPorSubdelegacion(DataPage dataPage, String subdelegacion, List<Long> idsProcesos) {
        DataPage dataPageReturn = null;
        dataPageReturn = flujoTrabajoEntity.obtenerInstanciasHistoricasPorSubdelegacion(dataPage, subdelegacion, idsProcesos);
        dataPageReturn = flujoTrabajoUtility.prepararRespuestaBandejasHistorico(dataPage);
        return dataPageReturn;
    }

    @Override
    public Map<String, String> obtenerParticipantesUltimaTarea(final Long idTarea) throws NoExisteTareaUsuarioException {
        LOGGERBPM.debug("Obteniendo ultima Tarea {}", idTarea);
        TareaUsuario tareaUsuario = flujoTrabajoEntity.buscarTareaUsuarioById(idTarea);

        return flujoTrabajoUtility.obtenerParticipantesUltimaTarea(tareaUsuario.getInstancia().getBpId());

    }

    @Override
    public Map<String, String> obtenerParticipantesUltimaTareaByTramite(final Long idTramite) throws NoExisteTareaUsuarioException {
        LOGGERBPM.debug("Obteniendo ultima Tarea Usuario {}", idTramite);

        Instancia instancia = flujoTrabajoEntity.buscarInstanciaByTramite(idTramite);

        return flujoTrabajoUtility.obtenerParticipantesUltimaTarea(instancia.getBpId());
    }

    @Override
    public void solicitarInformacion(Long idTareaUsuario, MensajeTarea mensajeTarea) throws NoExisteTareaUsuarioException,
            EstadoTareaUsuarioNoValidoException {
        LOGGERBPM.info("Actualizar BDoc [{}-{}]", idTareaUsuario, mensajeTarea);
        LOGGERBPM.info("Estado [{},{}]", idTareaUsuario, mensajeTarea.getEstado());

        TareaUsuario tareaUsuario = flujoTrabajoEntity.buscarTareaUsuarioById(idTareaUsuario);

        LOGGERBPM.info("validarTareaUsuario [{}]", tareaUsuario);
        flujoTrabajoUtility.validarTareaUsuario(tareaUsuario);

        flujoTrabajoUtility.prepararTareaUsuario(tareaUsuario, mensajeTarea);

        flujoTrabajoEntity.guardarTareaUsuario(tareaUsuario);

        //Agregar validacion de instancia estado
        LOGGERBPM.info("Se actualizan los atributos del bdoc");
        LOGGERBPM.info("Estado [{}-{}]", idTareaUsuario, mensajeTarea.getEstado());
        flujoTrabajoUtility.actualizarDatosBDocInstancia(tareaUsuario, mensajeTarea);

    }

    @Override
    public void actualizarBDocInstancia(Long idTareaUsuario, MensajeTarea mensajeTarea) throws NoExisteTareaUsuarioException,
            EstadoTareaUsuarioNoValidoException {
        LOGGERBPM.info("Actualizar BDoc [{}-{}]", idTareaUsuario, mensajeTarea);
        LOGGERBPM.info("Estado [{},{}]", idTareaUsuario, mensajeTarea.getEstado());

        TareaUsuario tareaUsuario = flujoTrabajoEntity.buscarTareaUsuarioById(idTareaUsuario);

        LOGGERBPM.info("validarTareaUsuario [{}]", tareaUsuario);
        flujoTrabajoUtility.validarTareaUsuario(tareaUsuario);

        //Agregar validacion de instancia estado
        LOGGERBPM.info("Se actualizan los atributos del bdoc");
        LOGGERBPM.info("Estado [{}-{}]", idTareaUsuario, mensajeTarea.getEstado());
        flujoTrabajoUtility.actualizarDatosBDocInstancia(tareaUsuario, mensajeTarea);

    }
    
    @Override
    public void actualizarBDocInstancia(Long idTareaUsuario, MensajeTarea mensajeTarea, String usuario) throws NoExisteTareaUsuarioException,
            EstadoTareaUsuarioNoValidoException {
        LOGGERBPM.info("Actualizar BDoc [{}-{}]", idTareaUsuario, mensajeTarea);
        LOGGERBPM.info("Estado [{},{}]", idTareaUsuario, mensajeTarea.getEstado());

        TareaUsuario tareaUsuario = flujoTrabajoEntity.buscarTareaUsuarioById(idTareaUsuario);
        tareaUsuario.setUsuario(usuario);
        flujoTrabajoEntity.guardarTareaUsuario(tareaUsuario);

        LOGGERBPM.info("validarTareaUsuario [{}]", tareaUsuario);
        flujoTrabajoUtility.validarTareaUsuario(tareaUsuario);

        //Agregar validacion de instancia estado
        LOGGERBPM.info("Se actualizan los atributos del bdoc");
        LOGGERBPM.info("Estado [{}-{}]", idTareaUsuario, mensajeTarea.getEstado());
        flujoTrabajoUtility.actualizarDatosBDocInstancia(tareaUsuario, mensajeTarea);

    }

    @Override
    public String nombrePersona(String curp) {
        String nombreCompleto = "";
        try {
            Usuario nombre = componentesExternosBusiness.recuperaUsuarioEsquemaSeguridadByCURP(curp);
            if (nombre != null && nombre.getFisica() != null && nombre.getFisica().getNombreCompleto() != null) {
                nombreCompleto = nombre.getFisica().getNombreCompleto();
            }

        } catch (EsquemaSegurdiadException e) {
            LOGGERBPM.error("---CDA--- Error al obtener nombre del funcionario por CURP:" + curp, e);
        } catch (UsuarioNoEncontradoException e) {
            LOGGERBPM.error("---CDA--- Error al obtener nombre del funcionario por CURP:" + curp, e);
        }
        return nombreCompleto;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerTareasPorUsuarioResponsable(DataPage dataPage, String usuario, List<Long> idsProcesos) {
        List<Long> estadosTareaUsuario = new ArrayList<Long>();
        estadosTareaUsuario.add(EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
        estadosTareaUsuario.add(EstadoTareaUsuarioEnum.ACTIVA.getClave());
        estadosTareaUsuario.add(EstadoTareaUsuarioEnum.COMPLETADA.getClave());

        dataPage = flujoTrabajoEntity.obtenerTareasPorUsuario(dataPage, usuario, estadosTareaUsuario, idsProcesos);

        LOGGERBPM.info("Se obtuvieron {} datos", dataPage.getData() != null ? dataPage.getData().size() : 0);
        dataPage = flujoTrabajoUtility.prepararRespuestaBandejas(dataPage);
        return dataPage;
    }
	
	@SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerTareasCDA(DataPage dataPage, String usuario, String subdelegacion, Long idProceso) {

        dataPage = flujoTrabajoEntity.consultaTareasCDA(dataPage, usuario, subdelegacion, idProceso);

        LOGGERBPM.info("Se obtuvieron {} datos", dataPage.getData() != null ? dataPage.getData().size() : 0);
        dataPage = flujoTrabajoUtility.prepararRespuestaBandejas(dataPage);
        return dataPage;
    }
	
    @Override
    public TareaBandeja getTareaPorIdTramite(Long idTramite, String usuario) {

        DataPage dataPage = new DataPage();
        dataPage.setPageSize(5);
        dataPage.setCurrentPage(1L);

        dataPage = flujoTrabajoEntity.obtenerTareasPorUsuarioTramite(dataPage, usuario,
                Arrays.asList(EstadoTareaUsuarioEnum.ACTIVA.getClave(),
                        EstadoTareaUsuarioEnum.DISPONIBLE.getClave(),
                        EstadoTareaUsuarioEnum.COMPLETADA.getClave()), idTramite);
        TareaBandeja tareaBandeja = null;
        if (dataPage.getTotalOfRecords() > 0) {
            tareaBandeja = new TareaBandeja();
            flujoTrabajoUtility.preparaTareaSeguimiento((TareaUsuario) dataPage.getData().toArray()[0], tareaBandeja);
        }
        return tareaBandeja;
    }

    @Override
    public TareaBandeja getTareaActivaPorIdTramite(Long idTramite) {

        DataPage dataPage = new DataPage();
        dataPage.setPageSize(5);
        dataPage.setCurrentPage(1L);

        dataPage = flujoTrabajoEntity.obtenerTareasPorUsuarioTramite(dataPage, null, flujoTrabajoUtility.prepararEstadosValidosBandejas(),
                idTramite);
        TareaBandeja tareaBandeja = null;
        if (dataPage.getTotalOfRecords() > 0) {
            tareaBandeja = new TareaBandeja();
            flujoTrabajoUtility.preparaTareaSeguimiento((TareaUsuario) dataPage.getData().toArray()[0], tareaBandeja);
        }
        return tareaBandeja;
    }

    @Override
    public List<TareaBandeja> getTareasActivasPorIdTramite(Long idTramite) {

        DataPage dataPage = new DataPage();
        dataPage.setPageSize(5);
        dataPage.setCurrentPage(1L);
        LOGGERBPM.info("Entrando a obtener tareas activas por el idTramite: []" + idTramite);
        dataPage = flujoTrabajoEntity.obtenerTareasPorUsuarioTramite(dataPage, null, flujoTrabajoUtility.prepararEstadosValidosBandejas(),
                idTramite);
        List<TareaBandeja> tareasBandeja = null;
        if (dataPage.getTotalOfRecords() > 0) {
            tareasBandeja = new ArrayList<TareaBandeja>();
            flujoTrabajoUtility.preparaTareasSeguimiento((List<TareaUsuario>) dataPage.getData(), tareasBandeja);
        }
        return tareasBandeja;
    }

    @Override
    public String getCurpResponsable(String subdelegacion) {
        LOGGERBPM.info("Subdelegacion a buscar: {} " + subdelegacion);
        DataPage dataPage = new DataPage();
        dataPage.setPageSize(5);
        dataPage.setCurrentPage(1L);
        String curpResponable = "";
        dataPage = flujoTrabajoEntity.obtenerUltimaInstanciaPorSubdelegacion(dataPage, subdelegacion);
        if (dataPage.getData() != null && !dataPage.getData().isEmpty()) {
            curpResponable = flujoTrabajoUtility.obtenerResponsableSubdelegacion(dataPage);
        }

        LOGGERBPM.info("El curp del ultimo responsable asignado es: {} " + curpResponable);
        return curpResponable;
    }

    public TareaBandeja obtenerTareaPorIdTramite(Long idTramite) {

        return flujoTrabajoUtility.obtenerTareaByInstancia(flujoTrabajoEntity.buscarInstanciaByTramite(idTramite));

    }
	
	@Override
	public DataPage obtenerConstanciasPorUsuarioNormativo(DataPage dataPage) {
		LOGGERBPM.info("obtenerConstanciasPorUsuarioNormativo");
		dataPage = flujoTrabajoEntity.obtenerConstanciasPorUsuarioNormativo(dataPage);
		dataPage = flujoTrabajoUtility.prepararRespuestaBandejaNormativosConstancias(dataPage);
		return dataPage;
	}
    
   

        @Override
	public DataPage obtenerHistoricoTareasPorIdInstancia(DataPage dataPage) {
		LOGGERBPM.info("obtenerConstanciasPorUsuarioNormativo");
		dataPage = flujoTrabajoEntity.obtenerHistoricoTareasPorIdInstancia(dataPage);
		dataPage = flujoTrabajoUtility.prepararRespuestaHistoricoTareas(dataPage);
		return dataPage;
	}

    @Override
    public void cancelarInstanciaDeTramite(Long idTramite){
        Instancia instancia = flujoTrabajoEntity.buscarInstanciaByTramite(idTramite);
        if(instancia != null){
            EstadoInstancia estadoInstancia = new EstadoInstancia();
            estadoInstancia.setIdEstadoInstancia(EstadoInstanciaEnum.TERMINADA.getClave());
            instancia.setEstadoInstancia(estadoInstancia);
            flujoTrabajoEntity.actualizarInstancia(instancia);
        }
		
		
    }
	
	@Override
		public DataPage obtenerReportePrincipalCDA(DataPage dataPage) {
			LOGGERBPM.info("obtenerReportePrincipal");
			dataPage = flujoTrabajoEntity.obtenerReportePrincipalCDA(dataPage, Boolean.TRUE);
			dataPage = flujoTrabajoEntity.obtenerReportePrincipalCDA(dataPage, Boolean.FALSE);
			dataPage = flujoTrabajoUtility.transformObjectToReporteCDA(dataPage);
			return dataPage;
		}

		@Override
		public DataPage obtenerConteoReportePrincipalCDA(DataPage dataPage) {
			dataPage = flujoTrabajoEntity.obtenerConteoReportePrincipalCDA(dataPage);
			dataPage = flujoTrabajoUtility.transformObjectToConteoReporteCDA(dataPage);
			return dataPage;
		}

		@Override
		public DataPage obtenerConteoOrigenesReportePrincipalCDA(DataPage dataPage) {			
			dataPage = flujoTrabajoEntity.obtenerConteoOrigenesReportePrincipalCDA(dataPage);
			dataPage = flujoTrabajoUtility.transformObjectToOrigenesConteoReporteCDA(dataPage);
			return dataPage;
		}

    @Override
    public DataPage obtenerPortabilidadImssIsssteUsNormativo(DataPage dataPage, List<Long> idsEdoSolicitud) {
        dataPage = flujoTrabajoEntity.obtenerPortabilidadImssIsssteUsNormativo(dataPage, idsEdoSolicitud);
        dataPage = flujoTrabajoUtility.prepararPortabilidadBandejaNormativos(dataPage);
        return dataPage;
    }

    @Override
    public DataPage obtenerPortabilidadIsssteImssUsNormativo(DataPage dataPage) {
        dataPage = flujoTrabajoEntity.obtenerPortabilidadIsssteImssUsNormativo(dataPage);
        dataPage = flujoTrabajoUtility.prepararPortabilidadIsssteImssBandejaNormativos(dataPage);
        return dataPage;
    }

}
