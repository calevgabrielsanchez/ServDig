/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

/**
 * Servicio para genera las compras a partir de las cotizaciones 
 * @author NOVUTECK1
 *
 */
@Remote
public interface GeneradorCompraServiceRemote {

    /**
     * Servicio para generar compras de seguros a partir de su cotizacion
     * @param cotizacion la cotizacion a convertirse en compra
     * @return la compra generada para la cotizacion
     * @throws SUAException Errores al generar la compra
     */
    Compra generaCompra(Cotizacion cotizacion) throws SUAException;
    
}
