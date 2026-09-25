/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.ws;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.Pagos;

/**
 * @author NOVUTECK1
 *
 */
@Stateless(name = "actualizaPagoWsBusiness", mappedName = "actualizaPagoWsBusiness")
@WebService(name = "pagoService", portName = "pagoServicePort", serviceName = "pagoService", 
targetNamespace = SUAConstants.SUA_NAMESSPACE)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class ActualizaPagoWsBusiness implements ActualizaPagoWs {

    /**
     * Servicio para la acualizacion de lineas de captura
     */
    @EJB
    private CompraServiceLocal compraServiceLocal;
    /**
     * Actualiza la linea de captura de un pago
     * @param pagos la lista de pagos a actualizar su linea de captura
     * @return la lista de pagos actualizados
     * @throws SUAException Errore en la actualizacion de las lineas de captura
     */
    @WebMethod
    @WebResult(name = "pagos", targetNamespace = SUAConstants.SUA_NAMESSPACE)
    public Pagos actualizaPagos(@WebParam(name = "pagos", 
            targetNamespace = SUAConstants.SUA_NAMESSPACE) Pagos pagos) throws SUAException {
               
        List<Pago> pagosA = compraServiceLocal.actualizaPagosLC(Arrays.asList(pagos.getPago()));
        Pagos p = new Pagos();
        p.setPago(pagosA.toArray(new Pago[pagosA.size()]));
        return p;
    }

}
