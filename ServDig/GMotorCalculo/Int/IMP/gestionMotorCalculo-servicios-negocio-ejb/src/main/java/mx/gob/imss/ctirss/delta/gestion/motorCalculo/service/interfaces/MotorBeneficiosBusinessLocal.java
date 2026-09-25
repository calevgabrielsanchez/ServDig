package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;

/**
 * Interfaz para el manejo de los beneficios en el motor de calculo
 * @author NOVUTECK1
 *
 */
@Local
public interface MotorBeneficiosBusinessLocal {
    
    /**
     * Obtiene el beneficio en un periodo de tiempo asociado al trabajador o patron 
     * dependiendo de la modalidad asociada
     * 
     * @param valores los valores para realizar la consulta
     * @return El Beneficio del trabajador encontrado
     */
    Beneficio buscarBeneficioTrabajador(ValoresCalculoEmpleado valores);
}
