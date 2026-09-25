package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CuotaServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CuotaServiceRemote;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;

/**
 * Servicio para generar las cuotas a pagar por un trabajador en caso de querer adquirir un seguro 
 * @author NOVUTECK1
 *
 */
@Stateless(name = "cuotaServiceBusiness", mappedName = "cuotaServiceBusiness")
public class CuotaServiceBusiness implements CuotaServiceRemote {

    /**
     * Servicio para la generacion de cotizaciones
     */
    @EJB
    private CuotaServiceLocal cuotaServiceLocal;
    /**
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CuotaServiceRemote#generaCotizacion(mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota)
     */
    @Override
    public Cotizacion generaCotizacion(DatosCalculoCuota datosCalculoCuota) throws SUAException {
        return cuotaServiceLocal.generaCotizacion(datosCalculoCuota);
    }
    
    
     

}
