/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

/**
 * Servicio para envir notificaciones dado los estaod s del os segurs
 * @author NOVUTECK1
 *
 */
@Remote
public interface NotificacionSegurosRemote {

    /**
     * Notifica os seguros que se encuentran pen periodo de renovacion
     */
    void notificaRenovacion();
    
    /**
     * Notifica los seguros que estan proximos avencer su fecha de pago
     */
    void notificaProximoVencimiento();
    
    void enviaCorreo(SeguroIvro seguro, List<String> correos, int tipo, Map<String, byte[]>adjuntos);
    
    void enviaCorreo(SeguroIvro seguro, int tipo);

    void enviaCorreoTipoOperacion(SeguroIvro seguro, int tipoOperacion) throws Exception;

    String procesoEnviaCorreosDiario();
}
