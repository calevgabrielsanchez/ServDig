/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaTrabajadorDomesticoLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaTrabajadorDomesticoRemote;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;

/**
 * Verifica que un trabajaor sea valido para obtener el seguro a partir del nrp del solicitante
 * @author NOVUTECK1
 *
 */
@Stateless(name = "validaTrabajadorDomesticoBusiness", mappedName = "validaTrabajadorDomesticoBusiness")
public class ValidaTrabajadorDomesticoBusiness implements ValidaTrabajadorDomesticoRemote {

    /**
     * Servicio local para la validacion de trabajadores domesticos
     */
    @EJB
    private ValidaTrabajadorDomesticoLocal validaTrabajador;
    /* (non-Javadoc)
     * @see mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaTrabajadorDomesticoRemote#
     * validaTrabajadorSeguroAnterior(java.lang.String, java.lang.String)
     */
    @Override
    public RespuestaValidacionTrabajador validaTrabajadorSeguroAnterior(Long idEmpleador, String nrp, String nss) {
        return validaTrabajador.validaTrabajadorSeguroAnterior(idEmpleador, nrp, nss);
    }
    
    @Override
    public  RespuestaValidacionTrabajador validaTrabajadorSeguroRenueva(Long idEmpleador, String nrp, String nss){
        return validaTrabajador.validaTrabajadorSeguroRenueva(idEmpleador, nrp, nss);
    }

}
