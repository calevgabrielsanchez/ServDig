package mx.gob.imss.cit.cda.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

/**
 * Servicio para módulo de Cancelar solicitud de CDA
 * 
 * @author mon
 * 
 */
@Remote
public interface CancelarSolicitudRemote {
    
    
    /**
     * Método para cancelar la solicitud, tramites y avanzar tramite en BPM
     *
     * 
     * @param solicitud
     * @param tramitesTareas
     * @param usuario
     * @param isAutorizador
     * @throws mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException
     * @throws mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException
     * @throws mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException
     * @throws mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException
     * @throws mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException
     * @throws mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException
     * @throws mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException
     */
    void cancelarTarea(Solicitud solicitud,Map<String, String> tramitesTareas, String usuario,Boolean isAutorizador) throws SolicitudException,
    NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
    NoExisteTransicionParaTareaUsuarioException,
    TereaSinUsuarioAsignadoException, NumberFormatException,
    SolicitudNoEncontradaException, TramiteNoEncontradoException ;


}
