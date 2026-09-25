package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DetalleNssCda;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Remote
public interface GenerarCertificacionRemote {
    
    /**
     * Metodo encargado de realizar las acciones correspondientes al finalizar
     * el tramite de correccion de datos asegurado envia el movimiento a sindo,
     * actualiza los datos estadisticos del asegurado finaliza la solicitud e
     * impacta los datos del tramite
     * 
     * @param solicitudCorreccion
     * @param tareas
     * @param usuario
     * @param tramiteCDA
     * @return
     * @throws SolicitudNoValidaException
     * @throws SolicitudNoEncontradaException
     * @throws mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException
     * @throws mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException
     * @throws TramiteNoEncontradoException
     * @throws mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException
     * @throws mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException
     * @throws mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException
     */

    Solicitud finalizaTramiteCorreccionDatosBasicosAsegurado(
            Solicitud solicitudCorreccion, Map<String, String> tareas, String usuario, TramiteCorreccionCurp tramiteCDA, String nssCertificador)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException, BPMException, NssRelacionadoVariasPersonasException,
            PersonaSinCalificacionesException, PersonaNoEncontradaException, CorreccionDatosAseguradoException;
    
    /**
     * M&eacute;todo para generar la firma electronica
     * 
     * @param personaCorrecion
     * @param solicitud
     * @param parametrosCuentas
     * @param tramite
     * @return
     * @throws mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException
     */
    FirmaElectronica selloDigitalCertificacion(Fisica personaCorrecion,
            Solicitud solicitud,
            Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas,
            TramiteCorreccionCurp tramite)
            throws CorreccionDatosAseguradoException;
    
    void solicitarFirmaDigital(Solicitud solicitud, Fisica fisica);

    List<DetalleNssCda> obtenerListaNSS(Long idTramite);
}
