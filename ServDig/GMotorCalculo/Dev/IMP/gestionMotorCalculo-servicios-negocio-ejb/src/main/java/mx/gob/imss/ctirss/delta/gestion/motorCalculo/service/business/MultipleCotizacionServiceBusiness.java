/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MultipleCotizacionServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MultipleCotizacionServiceRemote;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

/**
 * Implementacion de los servicio para la particion de cotizaciones por empleado
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "multipleCotizacionServiceBusiness",  mappedName= "multipleCotizacionServiceBusiness")
public class MultipleCotizacionServiceBusiness implements MultipleCotizacionServiceRemote {

    /**
     * Servicio para la particion de cotizaciones
     */
    @EJB
    private MultipleCotizacionServiceLocal cotizacionServiceLocal;

    /*
     * (non-Javadoc)
     * 
     * @see mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.
     * MultipleCotizacionServiceRemote
     * #generaCotizaciones(mx.gob.imss.digital.modelo.cobranza.Cotizacion)
     */
    @Override
    public Cotizacion[] generaCotizaciones(Cotizacion cotizacion) throws SUAException {
        List<Cotizacion> cotizaciones = cotizacionServiceLocal.generaCotizaciones(cotizacion);
        return cotizaciones.toArray(new Cotizacion[cotizaciones.size()]);
    }

}
