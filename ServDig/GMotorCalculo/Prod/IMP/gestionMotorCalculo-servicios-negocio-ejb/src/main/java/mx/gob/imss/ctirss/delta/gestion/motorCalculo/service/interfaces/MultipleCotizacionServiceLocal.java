/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

/**
 * Interfaz del servicio para generar multiples cotizaciones a partir de una 
 * con una lista de trabajadores
 * @author NOVUTECK1
 *
 */
@Local
public interface MultipleCotizacionServiceLocal {

    /**
     * Genera una cotizacion por cada trabajador recibido en la cotizacion original
     * @param cotizacion la cotizacion a ser partida por cada trabajador
     * @return la lista de cotizaciones 
     * @throws SUAException errores al generar las cotizaciones
     */
    List<Cotizacion> generaCotizaciones(Cotizacion cotizacion) throws SUAException;
}
