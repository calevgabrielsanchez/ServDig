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
import mx.gob.imss.digital.modelo.cobranza.Pagos;

/**
 * Actualiza los pagos con los datos obtenidos
 * @author NOVUTECK1
 *
 */
@Remote
@WebService(name = "pagoService", portName = "pagoServicePort", serviceName = "pagoService", 
targetNamespace = SUAConstants.SUA_NAMESSPACE)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ActualizaPagoWs {

    /**
     * Actualiza la linea de captura de un pago
     * @param pagos la lista de pagos a actualizar su linea de captura
     * @return la lista de pagos actualizados
     * @throws SUAException Errore en la actualizacion de las lineas de captura
     */
    @WebMethod
    @WebResult(name = "pagos", targetNamespace = SUAConstants.SUA_NAMESSPACE)
    Pagos actualizaPagos(@WebParam(name = "pagos", 
            targetNamespace = SUAConstants.SUA_NAMESSPACE) Pagos pagos) throws SUAException;
    
}
