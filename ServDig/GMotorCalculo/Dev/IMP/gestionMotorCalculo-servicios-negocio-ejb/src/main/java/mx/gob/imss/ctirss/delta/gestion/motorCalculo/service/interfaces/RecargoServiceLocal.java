/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;

import java.math.BigDecimal;

/**
 * @author NOVUTECK1
 *
 */
@Local
public interface RecargoServiceLocal {
    
    /**
     * Dado el calculo de cuota se de un perdiodo completo se calculan los recargos 
     * que apliquen a dicho periodo.
     * 
     * @param calculoCuota los datos con los valores de las cuotas ya calculadas, sobre las cuales se
     * generan los recargos
     * @return El calculo de las cuotas con el extra de los importes de recargos y actualizacion a pagar 
     * @throws SUAException Errors al generar los recargos y validaciones propias del servicio
     */
    CalculoCuota generaRecargos(CalculoCuota calculoCuota) throws SUAException;

    CalculoCuota generaActualizacionesRecargos(CalculoCuota calculoCuota, BigDecimal porcentajeLey168,BigDecimal actualizacionLey168) throws SUAException;

    CalculoCuota generaRecargosLey168(CalculoCuota calculoCuota, BigDecimal porcentajeLey168, BigDecimal actualizacionLey168) throws SUAException;
}
