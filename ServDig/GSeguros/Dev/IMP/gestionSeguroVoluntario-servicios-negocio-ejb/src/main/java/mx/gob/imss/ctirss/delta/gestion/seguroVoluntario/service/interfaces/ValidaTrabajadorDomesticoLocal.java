/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;

/**
 * @author NOVUTECK1
 *
 */
@Local
public interface ValidaTrabajadorDomesticoLocal {

    /**
     * valida que un trabajador domestico no cuente con otro seguro contratado para elmismo nrp
     * @param nrp registro patronal actual 
     * @param nss nuemero de seguridad social
     * @return respuesta de la validacion
     */
    RespuestaValidacionTrabajador validaTrabajadorSeguroAnterior(Long idEmpleador, String nrp, String nss);
    
    RespuestaValidacionTrabajador validaTrabajadorSeguroRenueva(Long idEmpleador, String nrp, String nss);
}
