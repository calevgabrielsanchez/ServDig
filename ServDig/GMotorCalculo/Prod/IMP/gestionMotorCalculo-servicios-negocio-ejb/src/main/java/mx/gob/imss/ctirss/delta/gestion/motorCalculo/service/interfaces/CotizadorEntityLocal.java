/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

/**
 * Servicio para la persistencia de una cotizacion, asi como la transformacion final de los datos
 * @author NOVUTECK1
 *
 */
@Local
public interface CotizadorEntityLocal {
    
    /**
     * GEnera una cotizacion y la persiste a partir de los datos del calculo realizados
     * @param calculos los datos de calculo para la cotizacion
     * @return Una cotizacion en el modelo xml 
     * @throws SUAException Errores en la generacion o persistencia de una cotizacion
     */
    Cotizacion generaYGuardaCotizacion(CalculoCuota calculos) throws SUAException;

    /**
     * Busca las cotizacion asociada al id ingresado como parametro
     * @param cveIdCotizacion Id de la cotizacion persitente
     * @return LA cotizacion encontrada
     * @throws SUAException Error al no encontrar una cotizacion
     */
    Cotizacion findCotizacion(long cveIdCotizacion) throws SUAException;
    
    /**
     * Guarda una cotizacion en la BD a partir de los datos de la cotizacion
     * @param cotizacion los datos de la cotizacion a ser guardados
     * @return la cotizacion ya persistida
     * @throws SUAException Error al guardar la cotizacion
     */
    Cotizacion guardaCotizacion(Cotizacion cotizacion) throws SUAException;
    
    /**
     * Actualiza una cotizacion
     * @param cotizacion la cotizacion a actualizar
     * @return la cotizacion actualizada
     * @throws SUAException errores en la actualizacion
     */
    Cotizacion actualizaCotizacion(Cotizacion cotizacion) throws SUAException;
}
