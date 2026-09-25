/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.Pagos;

/**
 * Interfaz con la definicion del web service del proceso sua
 * @author NOVUTECK1
 *
 */
@WebService(name = "generaLCPagoService", portName = "generaLCPagoServicePort", serviceName = "generaLCPagoService", 
targetNamespace = SUAConstants.SUA_NAMESSPACE)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface GeneraLineaCapturaWs {

    /**
     * GEnera las lineas de captura para un pago o pagos
     * @param pagos la lista de pagos a generarles sus lineas de captura
     * @return lal ista de pagos con sus lineas de captura
     * @throws SUAException errores al generar las lineas de captura
     */
    @WebMethod
    @WebResult(name = "pagos", targetNamespace = SUAConstants.SUA_NAMESSPACE)    
    Pagos generaLineaPago(@WebParam(name = "pagos", 
            targetNamespace = SUAConstants.SUA_NAMESSPACE) Pagos pagos) throws SUAException;

}
