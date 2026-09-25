package mx.gob.imss.cit.cda.service.interfaces;

import java.util.Date;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface ResponsableTareaRemote {

    

    void autorizarSolicitud(Solicitud solicitud, String idTarea, String usuario)
            throws SolicitudNoEncontradaException,
            TramiteNoEncontradoException, BPMException;

    Long iniciarWorkFlow(Long bp, InicioTramite inicioTramite,
            Solicitud solicitud) throws TareaInicialException,
            TereaSinUsuarioAsignadoException;

    

    void actualizarEstadoBdocInstancia(String idTarea, String estado, Date fecha)
            throws NoExisteTareaUsuarioException,
            EstadoTareaUsuarioNoValidoException;

}
