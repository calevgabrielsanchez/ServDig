/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * Interfaz para los servicio de envio de correo en los seguros
 * @author NOVUTECK1
 *
 */
@Local
public interface EnviaCorreoLocal {

    /**
     * Envia el correo asoiado a los datos del seguro
     * @param seguro el seguro a enviar su correo 
     * @param tipo el tipo de mensaje a enviar
     */
    void enviaCorreo(SeguroIvro seguro, int tipo);
    
    /**
     * Envia una lista de correos uno asociado a cada seguro segun el tipo de notificacion
     * @param seguros los seguros a enviar sus notificaciones
     * @param tipo el tipo de notificacion a enviar
     */
    void enviaCorreos(List<SeguroIvro> seguros, int tipo);

    String obtenCorreo(Fisica titular);

    /**
     * Obtener UMF por ID de Persona
     * 
     * @param idPersona
     * @return
     */
    UnidadMedicaFamiliar getUmfByIdPersona(Long idPersona);
    
    void enviaCorreo(SeguroIvro seguro, List<String> correos, int tipo, Map<String, byte[]>adjuntos);

    void enviaNotificacion(SeguroIvro seguro, int tipoOperacion)  throws Exception;
    
    /**
     * Envía correo para plantillas simplificadas (61, 71, 85, 86).
     *
     * @param cveIdSeguroIvro ID del seguro
     * @param fechaEfectivaBaja Fecha de baja (ya calculada)
     * @param urlConfirmacion URL (solo para código 85, null para otros)
     * @param tipoPlantilla Código: 61, 71, 85, 86
     * @throws Exception Solo si es código 85 y hay error crítico
     */
    void enviaCorreo(Long cveIdSeguroIvro,
                     Date fechaEfectivaBaja,
                     String urlConfirmacion,
                     int tipoPlantilla) throws Exception;

}
