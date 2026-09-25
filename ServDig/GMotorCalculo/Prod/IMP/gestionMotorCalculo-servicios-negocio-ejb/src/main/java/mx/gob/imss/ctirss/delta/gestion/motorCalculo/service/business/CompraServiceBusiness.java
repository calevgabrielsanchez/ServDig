/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.DatosCompra;
import mx.gob.imss.digital.modelo.cobranza.Pago;

/**
 * Implementacion de los servicio de compras
 * @author NOVUTECK1
 *
 */
@Stateless(name = "compraServiceBusiness", mappedName = "compraServiceBusiness")
public class CompraServiceBusiness implements CompraServiceRemote {

    /**
     * Servicio local para el manejo de las compras
     */
    @EJB
    private CompraServiceLocal compraServiceLocal;
    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompraServiceBusiness.class);
    
    /**
     * Busca una compra a partir de su id 
     * @param cveIdompra el id de la compra
     * @return la compra encontrada
     * @throws SUAException Errores al buscar la compra
     */
    public Compra findCompraById(long cveIdompra) throws  SUAException {
        return compraServiceLocal.findCompraById(cveIdompra);
    }
    
    /**
     * GUarda una compra en la base de datos, es tos a partir de los datos 
     * del modelo xml, guarda la informacion en el modelo de datos
     * @param compra la compra a guardar 
     * @return la compra con su id de persistencia y sus pagos persistidos
     * @throws SUAException errore al generar la persistencia de la compra
     */
    public Compra guardaCompra(Compra compra) throws SUAException {
        return compraServiceLocal.guardaCompra(compra);
    }
    
    /**
     * Busca un pago a partir de su id 
     * @param cveIdPago el id del pago peristido
     * @return el pago encontrado en la base de datos
     * @throws SUAException errore al realizar la consulta
     */
    public Pago findPagoById(long cveIdPago) throws SUAException {
        return compraServiceLocal.findPagoById(cveIdPago);
    }
    
    /**
     * Actualiza los valores de un pago en BD
     * @param pago al pago a ser actualizado
     * @return el pago actualizadp
     * @throws SUAException errore sna la actualizacion
     */
    public Pago actualizaPagoLC(Pago pago) throws SUAException {
        return compraServiceLocal.actualizaPagoLC(pago);
    }
    
    /**
     * Actualiaz una lista de pagos en la BD
     * @param pagos la lista de pagos a ser actualizada
     * @return la lista de pago actualizada
     * @throws SUAException errores en la actualizacion
     */
    public Pago[] actualizaPagosLC(Pago[] pagos) throws SUAException {
        LOGGER.debug("Actualizando pagos {}", pagos.length);
        List<Pago> pagosA = compraServiceLocal.actualizaPagosLC(Arrays.asList(pagos));
        LOGGER.debug("Pagos actualizados {}", pagosA.size());
        return pagosA.toArray(new Pago[pagosA.size()]);
    }

    /**
     * Dada una ista de lineas de captura las marca como pagadas, tambien marca la compra
     * si todos sus pagos ya fueron realizados y es anual o si es bimestral y el pago realizado es
     * sobre el bimestre actual
     * @param lineasCaptura las referencias sobre las cuales se buscan los pagos a marcar
     * @return la lista de compras afectadas que ya son validas para vigencia de compra
     * y la lista de lineas de captura que tubieron un error en la actualizacion (Si aplica)
     * 
     */
    public ActualizacionCompra pagosPagados(String[] lineasCaptura) {
        return compraServiceLocal.pagosPagados(Arrays.asList(lineasCaptura));
    }
    
    /**
     * Actualiza la lista de pagos a vencidas cuando aplica
     * @return lal ista de compras que  fueron vencidas
     */
    public DatosCompra[] pagosVencidos() {
        List<DatosCompra> compras = compraServiceLocal.pagosVencidos();
        return compras.toArray(new DatosCompra[compras.size()]);
    }
    
    /**
     * Publica la lista de pagos a vencidas cuando aplica y publica el resultado 
     * en un queue (jms/cobranzaConnectionFactory jms/vencimientoComprasQueue)
     * @return La lista de compras vencidas
     */
    public DatosCompra[] pagosVencidosQueue() {
        List<DatosCompra> compras = compraServiceLocal.pagosVencidosQueue();
        return compras.toArray(new DatosCompra[compras.size()]);
    }
    
    @Override
	public void cambiarPagosDeCompra(Compra compraDestino, Compra compraOrigen) {
    	this.compraServiceLocal.cambiarPagosDeCompra(compraDestino, compraOrigen);
    }
    
    @Override
    public DatosCompra[] pagosVencidosMod40(){
    	List<DatosCompra> compras = compraServiceLocal.pagosVencidosMod40();
    	return compras.toArray(new DatosCompra[compras.size()]);
    }
}
