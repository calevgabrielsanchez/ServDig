/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;

/**
 * Validacion de trabajadores domesticos
 * @author NOVUTECK1
 *
 */
@Remote
public interface ValidaTrabajadorDomesticoRemote {

    /**
     * Valida que un trabajador domestico no cuente con la compra de un seguro para el mismo patron
     * @param nrp
     * @param nss
     * @return
     */
    RespuestaValidacionTrabajador validaTrabajadorSeguroAnterior(Long idEmpleador, String nrp, String nss);
    
    /**
     * Valida que un trabajador domestico no cuente con la compra de un seguro para el mismo patron
     * @param idEmpleador
     * @param nrp
     * @param nss
     * @return
     */
    RespuestaValidacionTrabajador validaTrabajadorSeguroRenueva(Long idEmpleador, String nrp, String nss);
}
