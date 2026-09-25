/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.ws;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

/**
 * Servicio para genera las compras a partir de las cotizaciones 
 * @author NOVUTECK1
 *
 */
@Remote
@WebService(name = "generadorCompraService", portName = "generadorCompraServicePort", 
serviceName = "generadorCompraService", targetNamespace = SUAConstants.SUA_NAMESSPACE)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface GeneradorCompraWs {

    /**
     * Servicio para generar compras de seguros a partir de su cotizacion
     * @param cotizacion la cotizacion a convertirse en compra
     * @return la compra generada para la cotizacion
     * @throws SUAException Errores al generar la compra
     */
    @WebMethod()
    @WebResult(name = "compra", targetNamespace = SUAConstants.SUA_NAMESSPACE)
    Compra generaCompra(@WebParam(name = "cotizacion", targetNamespace = SUAConstants.SUA_NAMESSPACE)
        Cotizacion cotizacion) throws SUAException;
    
}
