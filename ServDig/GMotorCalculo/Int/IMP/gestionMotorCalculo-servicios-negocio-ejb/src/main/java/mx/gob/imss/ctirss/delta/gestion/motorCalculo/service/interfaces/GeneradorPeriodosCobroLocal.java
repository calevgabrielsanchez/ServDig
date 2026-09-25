/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.PeriodoCalculoCuota;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;

/**
 * Servico que se encargara de descomponer los datos de entrada para el motor en 
 * periodos de tiempo y valores de cuota para cada uno y asi poder generar calculos 
 * especificos por unidades de tiempo y condiciones de moviminetos y salarios
 * @author NOVUTECK1
 *
 */
@Local
public interface GeneradorPeriodosCobroLocal {
    
    /**
     * Obtiene la lista de perios sobre los cuales va a calcular 
     * las cuotas a pagar
     * @param valores Los valores para poder parir en periodos de cobreo
     * @param beneficio El beneficio asociado al cobre
     * @return la lista de periodos de cobrao a calcular
     */
    List<PeriodoCalculoCuota> generaPeriodosCalculo(ValoresCalculoEmpleado valores, Beneficio beneficio);

}
