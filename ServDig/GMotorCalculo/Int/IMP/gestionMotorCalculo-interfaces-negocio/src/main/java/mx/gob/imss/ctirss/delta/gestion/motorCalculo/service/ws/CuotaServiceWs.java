package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.ws;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;

@Remote
@WebService(name = "cuotaService", portName = "cuotaServicePort", serviceName = "cuotaService", 
targetNamespace = SUAConstants.SUA_NAMESSPACE)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CuotaServiceWs {

    /**
     * Calcula y regresa las cuotas calculadas para cada rama
     *
     * @param datosCalculoCuota datos necesarios para hacer el calculo
     * @return List<RamaCalculo> factores de aportacion obrero y patron
     **/ 
    @WebMethod
    @WebResult(name = "cotizacion", targetNamespace = SUAConstants.SUA_NAMESSPACE)    
    public Cotizacion generaCotizacion(@WebParam(name = "datosCalculoCuota", 
    targetNamespace = SUAConstants.SUA_NAMESSPACE) DatosCalculoCuota datosCalculoCuota) throws SUAException;
}
