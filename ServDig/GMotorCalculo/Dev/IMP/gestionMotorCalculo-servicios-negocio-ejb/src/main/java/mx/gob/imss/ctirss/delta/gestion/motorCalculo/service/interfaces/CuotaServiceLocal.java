/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;

/**
 * Servicio local para generar una cotizacion
 * @author NOVUTECK1
 *
 */
@Local
public interface CuotaServiceLocal {

    /**
     * Calcula y regresa las cuotas calculadas para cada rama
     *
     * @param datosCalculoCuota datos necesarios para hacer el calculo
     * @return List<RamaCalculo> factores de aportacion obrero y patron
     **/    
    public Cotizacion generaCotizacion(DatosCalculoCuota datosCalculoCuota) throws SUAException;
}
