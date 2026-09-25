/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.ws;

import java.util.Date;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.cobranza.LineasCapturaPagadas;

/**
 * Interfaz para el ws que actualiza las compras pagadas
 * @author NOVUTECK1
 *
 */
@Remote
@WebService(name = "compraService", portName = "compraServicePort", serviceName = "compraService", 
targetNamespace = SUAConstants.SUA_NAMESSPACE)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ActualizaCompraWs {

    /**
     * Actualiza los pagos de una compra dada la lista de lineas de captura ya pagadas
     * @param lineasCaptura lista de lineas de captura ya pagadas
     * @return la lista de compras ya pagadas completamente o parcial dependiendo del tipo de pagos (si aplican)
     */
    @WebMethod
    @WebResult(name = "comprasPagadas", targetNamespace = SUAConstants.SUA_NAMESSPACE)
    ActualizacionCompra pagosPagados( @WebParam(name = "lineasCapturaPagadas", 
            targetNamespace = SUAConstants.SUA_NAMESSPACE)LineasCapturaPagadas lineasCaptura);
    
    /**
     * Actualiza la lista de pagos a vencidas cuando aplica
     * @return lal ista de compras que  fueron vencidas
     */
    @WebMethod
    @WebResult(name = "comprasVencidas", targetNamespace = SUAConstants.SUA_NAMESSPACE)
    ActualizacionCompra pagosVencidos(@WebParam(name = "fecha", targetNamespace = SUAConstants.SUA_NAMESSPACE) Date fecha);
    
    /**
     * Actualiza la lista de pagos a vencidas cuando aplica
     * Publicando la ista de compras que  fueron vencidas 
     * en un queue (jms/cobranzaConnectionFactory jms/vencimientoComprasQueue)
     */
    @WebMethod
    @WebResult(name = "resultadocomprasVencidasQueue", targetNamespace = SUAConstants.SUA_NAMESSPACE)
    ActualizacionCompra comprasVencidasQueue(@WebParam(name = "fecha", targetNamespace = SUAConstants.SUA_NAMESSPACE) Date fecha);
}
