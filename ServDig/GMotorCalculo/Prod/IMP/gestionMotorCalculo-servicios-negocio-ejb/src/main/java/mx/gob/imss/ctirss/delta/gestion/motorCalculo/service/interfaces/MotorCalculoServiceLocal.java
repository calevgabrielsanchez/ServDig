/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;

/**
 * Servicio para calcular las cuotas de un empleado a partir de sus periodos,
 * beneficios  y movimientos generados
 * @author NOVUTECK1
 *
 */
@Local
public interface MotorCalculoServiceLocal {
    
    /**
     * Calcula la cuota de un empleado dada sus valores de periodos,
     * descuentos, movimientos y factores de calculo
     * @param valores los valores para el calculo de cuotas
     * @return las cuotas generadas paa el empleado
     * @throws SUAException 
     */
    EmpleadoCuota calculaCuota(ValoresCalculoEmpleado valores) throws SUAException;

}
