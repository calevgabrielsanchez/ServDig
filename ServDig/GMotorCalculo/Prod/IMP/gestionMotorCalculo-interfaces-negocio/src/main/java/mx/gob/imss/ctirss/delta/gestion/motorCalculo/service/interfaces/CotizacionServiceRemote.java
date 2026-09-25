/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

/**
 * Servicio para el manejo de cotizaciones persitentes
 * @author NOVUTECK1
 *
 */
@Remote
public interface CotizacionServiceRemote {

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
     * Actualiza una cotizacion en la BD a partir de los datos de la cotizacion
     * 
     * @param cotizacion los datos de la cotizacion a ser guardados
     * @return la cotizacion ya persistida
     * @throws SUAException Error al guardar la cotizacion
     */
    Cotizacion actualizaCotizacion(Cotizacion cotizacion) throws SUAException;
}
