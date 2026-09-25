/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.Pago;

/**
 * Interfaz de ejb para ejecutar el ws del proceso sua
 * @author NOVUTECK1
 *
 */
@Remote
public interface GeneraLineaCapturaRemote {
    
    /**
     * Servicio para generar la linea de captura dados los pagos del servicio
     * @param pagos los datos el pago a realizar su linea de captura
     * @param urlServicio url donde se encuentra el web services del proceso sua
     * @return las lineas de captura generadas
     * @throws SUAException Errores generados en la ejecucion del servicios
     */
    Pago[] generaLineasCaptura(Pago[] pagos, String urlServicio) 
            throws SUAException;

}
