/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.Patron;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;

/**
 * Servicio para obtener los datos de un patron a partir de su registro patronal
 * y generar el objeto
 * 
 * @author NOVUTECK1
 * 
 */
@Local
public interface GeneradorDatosPatronLocal {

    /**
     * Obtiene los datos de un patron a partir de de su registro patronal
     * @param datos Datos del calculo para generar el registro
     * @param sujeto Empleador que genera el SUA
     * @param periodo periodo de calculo
     * @return El registro de un patron para un SUA
     * @throws SUAException Error al generar el registro
     */
    Patron generaPatron(CalculoCuota datos, SujetoObligado sujeto, PeriodoCuota periodo,
            EmpleadoCuota empleado, String nrp35) throws SUAException;

}
