/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;

/**
 * Servicio para la consulta de parametros de ivro
 * @author NOVUTECK1
 *
 */
@Local
public interface ParametrosEntityLocal {
    
    /**
     * Obtieen la lista de dias feriados
     * 
     * @return la lista de dias feriados
     */
    List<Date> getDiasFeriados();

    BigDecimal getSalarioMinimoIVRO(String zona) throws SUAException;

    /**
     * Obtiene el salario minimo asociado a una zona salarial al dia actual 
     * @param zona zona salarial
     * @return el salario minimo asociado a al zona zalarial
     */
    BigDecimal getSalarioMinimoCRVO(String zona) throws SUAException;
    
    BigDecimal getSalarioMinimoCDMX(String zonaSalarial, Calendar fecha)
            throws SUAException;


    /**
     * Obtiene la UMA a una fecha determinada
     * @param fecha
     * @return BigDecimal UMA asociado a la fecha
     */
    BigDecimal getUma(String fecha) throws SUAException;

    String obtenZonaSalarialOriginal(String zonaSalarial, ModalidadEnum modalidad);
}
