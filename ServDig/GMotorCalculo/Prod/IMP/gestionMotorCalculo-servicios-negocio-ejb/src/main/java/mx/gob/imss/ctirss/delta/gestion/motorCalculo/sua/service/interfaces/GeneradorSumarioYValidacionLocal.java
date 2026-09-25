/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.DatosValidacion;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.PeriodoSUA;
import mx.gob.imss.digital.modelo.cobranza.RegistroValidacion;
import mx.gob.imss.digital.modelo.cobranza.SumarioPatronal;

/**
 * Servicio para generar los valores del sumario y registro de validacion para
 * un archivo sua
 * 
 * @author NOVUTECK1
 * 
 */
@Local
public interface GeneradorSumarioYValidacionLocal {

    /**
     * Genera el sumario de los datos del trabajador
     * 
     * @param periodoSua el objeto que contiene los trabajadores y factores deactualizacion para el sumario
     * @return El registro con el sumario patronal
     */
    SumarioPatronal generaSumario(PeriodoSUA periodoSua);

    /**
     * Genera el registro de valiacion a partir del sumario y sus trabajadores
     * 
     * @param datos valores necesarios para generar el registro de validacion
     * @return El registro de validacion para el archivo SUA
     * @throws SUAException Error de la aplicacion al gnerar el regisro de validacion
     */
    RegistroValidacion generaRegistroValidacion(DatosValidacion datos) throws SUAException;
}
