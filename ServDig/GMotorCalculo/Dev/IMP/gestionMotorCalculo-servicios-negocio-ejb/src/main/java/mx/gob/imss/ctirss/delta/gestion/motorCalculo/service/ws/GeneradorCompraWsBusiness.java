/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.GeneradorCompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

/**
 * @author NOVUTECK1
 *
 */
@Stateless(name = "generadorCompraWsBusiness", mappedName = "generadorCompraWsBusiness")
@WebService(name = "generadorCompraService", portName = "generadorCompraServicePort", 
serviceName = "generadorCompraService", targetNamespace = SUAConstants.SUA_NAMESSPACE)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class GeneradorCompraWsBusiness implements GeneradorCompraWs {

    /**
     * servicio para el manejo de compras
     */
    @EJB(mappedName = "generadorCompraServiceBusiness")
    private GeneradorCompraServiceRemote generadorCompraServiceRemote;
    
    /**
     * Servicio para generar compras de seguros a partir de su cotizacion
     * @param cotizacion la cotizacion a convertirse en compra
     * @return la compra generada para la cotizacion
     * @throws SUAException Errores al generar la compra
     */
    @WebMethod()
    @WebResult(name = "compra", targetNamespace = SUAConstants.SUA_NAMESSPACE)
    public Compra generaCompra(@WebParam(name = "cotizacion", targetNamespace = SUAConstants.SUA_NAMESSPACE)
        Cotizacion cotizacion) throws SUAException {        
        return generadorCompraServiceRemote.generaCompra(cotizacion);        
    }

}
