/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.RecargoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.RecargoServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;

/**
 * Servicio que se encargara de calcular los recagos a generados por las cuotas
 * de un seguro IVRO
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "recargoIvroServiceBusiness", mappedName = "recargoIvroServiceBusiness")
@WebService(name = "recargosIvroService", portName = "recargosIvroServicePort", 
serviceName = "recargosIvroService", targetNamespace = SUAConstants.SUA_NAMESSPACE)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class RecargoIvroServiceBusiness implements RecargoServiceRemote {
   
    /**
     * Servicio para el calculo de recargos
     */
    @EJB
    private RecargoServiceLocal recargoServiceLocal;

    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.RecargoServiceRemote#generaRecargos(mx.gob.imss.digital.modelo.cobranza.CalculoCuota)
     */
    @Override
    public CalculoCuota generaRecargos(CalculoCuota calculoCuota) throws SUAException {
        return recargoServiceLocal.generaRecargos(calculoCuota);
    }
    

}
