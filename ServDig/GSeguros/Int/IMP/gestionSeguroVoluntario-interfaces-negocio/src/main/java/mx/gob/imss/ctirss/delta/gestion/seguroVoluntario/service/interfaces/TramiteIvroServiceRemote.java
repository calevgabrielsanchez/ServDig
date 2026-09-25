/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;

/**
 * Servicio para el manejo de los tramites de IVRO
 * @author NOVUTECK1
 *
 */
@Remote
public interface TramiteIvroServiceRemote {

    /**
     * Genera un segur ivro a partir de su ramite
     * @param tramite
     * @return
     */
    SeguroIvro generaSeguro(TramiteSeguroIvro tramite)  throws IvroException;
    
    /**
     * Genera una lista de seguros a partir de su tramite y las cotizaciones a asociar
     * @param tramite el tramite del seguro
     * @param compras las compras de cada empleado
     * @return lal ista de seguros generados
     * @throws IvroException Errores al generar el seguro
     */
    SegurosIvro generaSegurosDomesticos(TramiteSeguroIvro tramite, Compra[] compra) 
            throws IvroException;
    
    /**
     * Genera una lista de seguros a partir de su tramite y las cotizaciones a asociar
     * @param tramite el tramite del seguro
     * @param compras las compras de cada empleado
     * @return lal ista de seguros generados
     * @throws IvroException Errores al generar el seguro
     */
    SegurosIvro generaSegurosFamiliares(TramiteSeguroIvro tramite, Compra[] compra) 
            throws IvroException;
}
