package mx.gob.imss.cit.cda.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

/**
 * Servicio para módulo de Solicitar Información Adicional de Solicitud de CDA
 * 
 * @author mon
 * 
 */
@Remote
public interface SolicitarInformacionRemote {
    
    
    /**
     * Método para solicitar informacion adicional de la solicitud, tramites y avanzar tramite en BPM
     *
     * 
     */
    void avanzarSolicitarInformacion(Solicitud solicitud,
            Map<String, String> tramitesTareas, String observacion,
            String usuario, Boolean isAutorizador) throws SolicitudNoEncontradaException,
            TramiteNoEncontradoException, BPMException;
    
}
