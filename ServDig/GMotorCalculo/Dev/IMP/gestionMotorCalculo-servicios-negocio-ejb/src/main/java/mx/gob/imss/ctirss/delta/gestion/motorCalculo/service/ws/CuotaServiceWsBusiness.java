package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CuotaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;

/**
 * Servicio para generar las cuotas a pagar por un trabajador en caso de querer adquirir un seguro 
 * @author NOVUTECK1
 *
 */
@Stateless(name = "cuotaServiceWsBusiness", mappedName = "cuotaServiceWsBusiness")
@WebService(name = "cuotaService", portName = "cuotaServicePort", serviceName = "cuotaService", 
targetNamespace = SUAConstants.SUA_NAMESSPACE)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class CuotaServiceWsBusiness implements CuotaServiceRemote {
    
    /**
     * Servicio para la cuota de servicios
     */
    @EJB(mappedName = "cuotaServiceBusiness")
    private CuotaServiceRemote cuotaServiceRemote;


    /**
     * Genera los calculos ed las cuotas a pagar por un empleado en un periodo de cobro
     * @param datosCalculoCuota DAtos a partir de los cuales se generan los calculos de cuotas
     * @return EL resultado de los calculos de cobreo para un empleado
     * @throws SUAException Error al generar los calculos de cuotas
     */
    @WebMethod
    @WebResult(name = "cotizacion", targetNamespace = SUAConstants.SUA_NAMESSPACE)    
    public Cotizacion generaCotizacion(@WebParam(name = "datosCalculoCuota", 
    targetNamespace = SUAConstants.SUA_NAMESSPACE) DatosCalculoCuota datosCalculoCuota) 
            throws SUAException {
        
        return cuotaServiceRemote.generaCotizacion(datosCalculoCuota);
        
    }    

}
