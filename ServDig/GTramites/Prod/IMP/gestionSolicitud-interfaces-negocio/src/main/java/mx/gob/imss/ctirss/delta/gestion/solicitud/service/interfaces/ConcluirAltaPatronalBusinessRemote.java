package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface ConcluirAltaPatronalBusinessRemote {

    /* 
     * Genera y encola mensaje en queue queueSindo para dar retro alimentacion
     * Respecto a movimientos de alta patronal
     *
     * @Date 03/07/2013
     * @param String registroPatronal
     * @param Integer idSolicitud
     */
    void concluirAltaPatronal(String registroPatronal, Long idSolicitud);
    
    /* 
     * Genera y encola mensaje en queue queueSindo para dar retro alimentacion
     * Respecto a movimientos de alta patronal.
     * Este metodo debe ser empleado si se ha consultado previamente la solicitud
     * ello con la finalidad de evitar consultas duplicadas a la base en un mismo proceso
     *
     * @Date 03/07/2013
     * @param String registroPatronal
     * @param Integer idSolicitud
     */
    void concluirAltaPatronal(String registroPatronal, Solicitud solicitud);
    
    /**
     * Reporta el alta patronal de un movimiento de al modalidad 14
     * @param registroPatronal
     * @param sujetoTramite
     */
    void reportarMovimientoAltaPatronal(String registroPatronal,
			SujetoObligado sujetoTramite);
}
