/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.cobranza.Trabajador;

/**
 * Servicio encargado de generar los datos de un trabajador a partir de los
 * datos de sus cuotas y nss
 * 
 * @author NOVUTECK1
 * 
 */
@Local
public interface GeneradorDatosTrabajadorLocal {

    
    /**
     * GEnera una entidad trabajador a partir de los datos obtenidos en las
     * cuotas
     * @param empleado CUotas calculadas para un empleado
     * @param persona REpresentacion de un empleado
     * @param periodo periodo de calculo 
     * @return El registro para las cuotas de un empleado y sus movmientos
     * @throws SUAException Si ocurre un error al generar el registro
     */
    Trabajador generaTrabajador(EmpleadoCuota empleado, Fisica persona, PeriodoCuota periodo, Long idModalidad)
            throws SUAException;

}
