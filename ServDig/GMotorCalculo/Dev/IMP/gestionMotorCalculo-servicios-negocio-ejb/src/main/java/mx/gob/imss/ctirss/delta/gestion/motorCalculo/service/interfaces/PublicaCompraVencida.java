/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;

/**
 * Servicio para publicar mensajes jms con los datos de compras vencidas
 * @author NOVUTECK1
 *
 */
@Local
public interface PublicaCompraVencida {

    /**
     * MEtodo para publicar las compras vencidas en una cola de mensajes
     * @param compras la lista de compras vencidas
     */
    void publicaCompraVencida(ActualizacionCompra compras);
}
