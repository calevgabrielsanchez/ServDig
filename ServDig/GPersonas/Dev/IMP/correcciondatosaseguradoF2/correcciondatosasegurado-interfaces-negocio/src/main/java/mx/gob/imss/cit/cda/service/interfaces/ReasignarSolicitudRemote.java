package mx.gob.imss.cit.cda.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface ReasignarSolicitudRemote {

    void reasignarTareaAutorizador(Solicitud solicitud,
            Map<String, String> tareas, String observacion, String usuario)
            throws SolicitudNoEncontradaException,
            TramiteNoEncontradoException, BPMException;
}
