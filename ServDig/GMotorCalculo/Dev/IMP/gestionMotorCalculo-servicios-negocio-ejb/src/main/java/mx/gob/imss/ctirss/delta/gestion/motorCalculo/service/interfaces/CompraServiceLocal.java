/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.DatosCompra;
import mx.gob.imss.digital.modelo.cobranza.Pago;

/**
 * Servicio local para el manejo de compras
 * @author NOVUTECK1
 *
 */
@Local
public interface CompraServiceLocal {

    /**
     * Busca una compra a partir de su id 
     * @param cveIdompra el id de la compra
     * @return la compra encontrada
     * @throws SUAException Errores al buscar la compra
     */
    Compra findCompraById(long cveIdompra) throws  SUAException;
    
    /**
     * GUarda una compra en la base de datos, es tos a partir de los datos 
     * del modelo xml, guarda la informacion en el modelo de datos
     * @param compra la compra a guardar 
     * @return la compra con su id de persistencia y sus pagos persistidos
     * @throws SUAException errore al generar la persistencia de la compra
     */
    Compra guardaCompra(Compra compra) throws SUAException;
    
    /**
     * Busca un pago a partir de su id 
     * @param cveIdPago el id del pago peristido
     * @return el pago encontrado en la base de datos
     * @throws SUAException errore al realizar la consulta
     */
    Pago findPagoById(long cveIdPago) throws SUAException;
    
    /**
     * Actualiza los valores de linea de captura en un pago en BD
     * @param pago al pago a ser actualizado
     * @return el pago actualizadp
     * @throws SUAException errore sna la actualizacion
     */
    Pago actualizaPagoLC(Pago pago) throws SUAException;
    
    /**
     * Actualiaz una lista de pagos su linea de captura en la BD
     * @param pagos la lista de pagos a ser actualizada
     * @return la lista de pago actualizada
     * @throws SUAException errores en la actualizacion
     */
    List<Pago> actualizaPagosLC(List<Pago> pagos) throws SUAException;
    
    /**
     * Dada una ista de lineas de captura las marca como pagadas, tambien marca la compra
     * si todos sus pagos ya fueron realizados y es anual o si es bimestral y el pago realizado es
     * sobre el bimestre actual
     * @param lineasCaptura las referencias sobre las cuales se buscan los pagos a marcar
     * @return la lista de compras afectadas que ya son validas para vigencia de compra
     * y la lista de lineas de captura que tubieron un error en la actualizacion (Si aplica)
     * 
     */
    ActualizacionCompra pagosPagados(List<String> lineasCaptura);
    
    /**
     * Actualiza la lista de pagos a vencidas cuando aplica
     * @return lal ista de compras que  fueron vencidas
     */
    List<DatosCompra> pagosVencidos();
    
    /**
     * Publica la lista de pagos a vencidas cuando aplica y publica el resultado 
     * en un queue (jms/cobranzaConnectionFactory jms/vencimientoComprasQueue)
     * @return La lista de compras vencidas
     */
    List<DatosCompra> pagosVencidosQueue();
    
	/**
	 * Camiba los pagos relacionados a la compraOrigen a la compraDestino
	 * 
	 * @param compraDestino
	 * @param compraOrigen
	 */
	void cambiarPagosDeCompra(Compra compraDestino, Compra compraOrigen);
	
	/**
     * Actualiza la lista de pagos a vencidas cuando aplica, para la modalidad 40
     * @return La lista de compras que fueron vencidas
     */
	List<DatosCompra> pagosVencidosMod40();

    public Fisica findPersonabyPago(long cveIdPago)throws SUAException ;

}
