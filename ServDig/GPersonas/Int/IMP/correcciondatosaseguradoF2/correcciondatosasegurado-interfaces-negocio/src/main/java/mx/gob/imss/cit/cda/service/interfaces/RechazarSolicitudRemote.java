package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface RechazarSolicitudRemote {

    void rechazarSolicitud(Solicitud solicitud, List<Long> idsTareasUsuario, String usuario)
            throws SolicitudNoEncontradaException,
            TramiteNoEncontradoException, BPMException;
    
}
