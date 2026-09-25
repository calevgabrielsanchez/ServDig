package mx.gob.imss.cit.cda.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

/**
 * Servicio para módulo de Confirmar solicitud de CDA
 * 
 * @author mon
 * 
 */
@Remote
public interface ConfirmarSolicitudRemote {
    
    void avanzarTareaResponsable(Solicitud solicitud,
            Map<String, String> tramitesTareas)
            throws SolicitudNoEncontradaException,
            TramiteNoEncontradoException, NoExisteTareaUsuarioException,
            EstadoTareaUsuarioNoValidoException,
            NoExisteTransicionParaTareaUsuarioException,
            TereaSinUsuarioAsignadoException, NumberFormatException;

}
