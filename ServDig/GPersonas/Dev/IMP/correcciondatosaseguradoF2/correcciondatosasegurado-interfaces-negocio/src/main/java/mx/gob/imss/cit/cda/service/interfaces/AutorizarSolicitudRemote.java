package mx.gob.imss.cit.cda.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface AutorizarSolicitudRemote {
    
    /**
     * M&eacute;todo encargado de enviar la solicitud a SINDO
     * 
     * @param solicitud
     * @param tramitesTareas
     * @param usuario
     * @throws mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException
     * @throws mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException
     * @throws mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException
     * @throws mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException
     * @throws mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException
     * @throws mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException
     * @throws mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException
     * @throws mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException
     */
    void enviaCertificacionSINDO(Solicitud solicitud, Map<String, String> tramitesTareas,
            String usuario) throws SolicitudNoValidaException,
            SolicitudNoEncontradaException, TramiteNoEncontradoException,
            CURPNoLocalizadoEnEntidadExternaException,
            ClienteWebserviceRenapoCurpException,
            ErrorValidacionDatosConsultaEnEntidaExternaException,
            NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException;

}
