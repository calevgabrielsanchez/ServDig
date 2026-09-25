package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

/**
 * Servicio para obtener las ramas (cuotas) que se deben pagar por parte de un patron y trabajador
 * @author NOVUTECK1
 *
 */
@Local
public interface DicFactorModalidadRamaEntityLocal {
    
    /**
     * Obtiene todas las cuotas a calcular para un trabajador
     * @param modalidad id de la modalidad a la que pertenece el empleador
     * @param registroPatronal numero de registro patronal
     * @return Rmas de calculo encontradas para los valores ingresados
     * @throws SUAException Error al buscar ramas por registro patronal  que no exista
     */
    List<RamaCalculo> buscarRamasCalculo(long modalidad, String registroPatronal) throws SUAException;
    
    /**
     * Busca la lista de de ramas asociadas a una modalidad
     * @param modalidad Id de la modalidad del empleador
     * @return lista de cuotas que aplican a la modalidad
     */
    List<RamaCalculo> buscarRamasModalidad(long modalidad);

    /**
     * BUsca la cuota para la prima de riesgo de trabajo
     * @param registroPatronal numero de registro patronal
     * @return Cuota de prima de riesgo que aplica para el patron
     * @throws SUAException Error al buscar ramas por registro patronal  que no exista
     */
    RamaCalculo buscarRamaSRT(String registroPatronal) throws SUAException;
}
