/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.ws;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.cobranza.DatosCompra;
import mx.gob.imss.digital.modelo.cobranza.LineasCapturaPagadas;

/**
 * @author NOVUTECK1
 *
 */
@Stateless(name = "actualizaCompraWsBusiness", mappedName = "actualizaCompraWsBusiness")
@WebService(name = "compraService", portName = "compraServicePort", serviceName = "compraService", 
targetNamespace = SUAConstants.SUA_NAMESSPACE)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class ActualizaCompraWsBusiness implements ActualizaCompraWs {

    @EJB
    private CompraServiceLocal compraService;
    /**
     * Actualiza los pagos de una compra dada la lista de lineas de captura ya pagadas
     * @param lineasCaptura lista de lineas de captura ya pagadas
     * @return la lista de compras ya pagadas completamente o parcial dependiendo del tipo de pagos (si aplican)
     */
    @WebMethod
    @WebResult(name = "comprasPagadas", targetNamespace = SUAConstants.SUA_NAMESSPACE)
    public ActualizacionCompra pagosPagados(@WebParam(name = "lineasCapturaPagadas", 
            targetNamespace = SUAConstants.SUA_NAMESSPACE)LineasCapturaPagadas lineasCaptura) {
        String[] lineas = lineasCaptura.getLineaCaptura();
        return compraService.pagosPagados(Arrays.asList(lineas));
    }
    
    /**
     * Actualiza la lista de pagos a vencidas cuando aplica
     * @param fecha Fecha de ejecucion
     * @return lal ista de compras vecida, si es que hubo alguna
     */
    @WebMethod
    @WebResult(name = "comprasVencidas", targetNamespace = SUAConstants.SUA_NAMESSPACE)
    public ActualizacionCompra pagosVencidos(@WebParam(name = "fecha", 
    targetNamespace = SUAConstants.SUA_NAMESSPACE) Date fecha) {
        List<DatosCompra> vencidos = compraService.pagosVencidos();
        return armaDatosCompra(vencidos);
    }
    
    /**
     * Actualiza la lista de pagos a vencidas cuando aplica
     * Publicando la ista de compras que  fueron vencidas 
     * en un queue (jms/cobranzaConnectionFactory jms/vencimientoComprasQueue)
     * @param fecha Fecha de ejecucion
     * @return lal ista de compras vecida, si es que hubo alguna
     */
    @WebMethod
    @WebResult(name = "resultadocomprasVencidasQueue", targetNamespace = SUAConstants.SUA_NAMESSPACE)
    public ActualizacionCompra comprasVencidasQueue(@WebParam(name = "fechaActual", 
    targetNamespace = SUAConstants.SUA_NAMESSPACE) Date fecha) {
        List<DatosCompra> vencidos = compraService.pagosVencidosQueue();
        return armaDatosCompra(vencidos);
    }

    /**
     * Servicio privado para generar un objeto <code>ActualizacionCompra</code> a partir de una lista
     * de datos compra
     * @param datos la lista de datos compra
     * @return El objeto <code>ActualizacionCompra</code>
     */
    private ActualizacionCompra armaDatosCompra(List<DatosCompra> datos) {        
        ActualizacionCompra actualizaCompra = new ActualizacionCompra();
        actualizaCompra.setCompras(datos.toArray(new DatosCompra[datos.size()]));
        return actualizaCompra;
    }

}
