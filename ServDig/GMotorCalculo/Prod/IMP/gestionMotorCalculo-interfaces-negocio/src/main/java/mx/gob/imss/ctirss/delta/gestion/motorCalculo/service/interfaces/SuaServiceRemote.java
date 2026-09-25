/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.SUAPago;

/**
 * Intefaz que expone las operaciona para completar los datos del 
 * sua a partir de los calculos realizados
 * @author NOVUTECK1
 *
 */
@Remote
public interface SuaServiceRemote {

    SUAPago[] generaDatosSua(CalculoCuota datosCalculo, String nrp35) throws SUAException;
}
