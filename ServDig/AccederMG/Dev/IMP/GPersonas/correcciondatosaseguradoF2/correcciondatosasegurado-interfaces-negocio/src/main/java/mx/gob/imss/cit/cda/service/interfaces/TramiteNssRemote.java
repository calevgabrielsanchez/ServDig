package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Remote
public interface TramiteNssRemote {
    
    /**
     * Método para solicitar informacion adicional de la solicitud, tramites y avanzar tramite en BPM
     *
     * 
     */
    List<TramiteCorreccionCurp> obtenerTramitesSolicitudByOrigen(String folioId,Long idOrigen) throws SolicitudNoEncontradaException,
    TramiteNoEncontradoException;

}
