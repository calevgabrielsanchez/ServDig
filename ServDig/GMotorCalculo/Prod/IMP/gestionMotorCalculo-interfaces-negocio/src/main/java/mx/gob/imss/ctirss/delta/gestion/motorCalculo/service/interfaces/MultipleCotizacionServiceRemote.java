/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

/**
 * DEclaracion del servicio para generar multiples cotizaciones a partir de una 
 * con una lista de trabajadores
 * @author NOVUTECK1
 *
 */
@Remote
public interface MultipleCotizacionServiceRemote {

    /**
     * Genera una cotizacion por cada trabajador recibido en la cotizacion original
     * @param cotizacion la cotizacion a ser partida por cada trabajador
     * @return la lista de cotizaciones 
     * @throws SUAException errores al generar las cotizaciones
     */
    Cotizacion[] generaCotizaciones(Cotizacion cotizacion) throws SUAException;
}
