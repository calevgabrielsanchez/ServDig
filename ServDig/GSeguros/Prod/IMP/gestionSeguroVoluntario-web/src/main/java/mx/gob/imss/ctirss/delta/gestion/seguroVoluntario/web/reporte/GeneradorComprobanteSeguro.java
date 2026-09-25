/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.reporte;

import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;

/**
 * Interfaz para la generacion de los comprobantes de seguros.
 *
 * @author NOVUTECK1
 */
public interface GeneradorComprobanteSeguro {

    /**
     * Genera El comprobante de un seguro .
     *
     * @param seguro el seguro a generar su comprobante
     * @param origen the origen
     * @return el arreglo de bytes con el comprobante
     */
    DocumentoSeguro generaComprobante(SeguroIvro seguro, Long origen);
    
    /**
     * Genera los comprobantes para una lista de seguros.
     *
     * @param seguros los seguros a generar sus comprobantes
     * @param origen the origen
     * @return los comprobantes generados
     */
    DocumentoSeguro generaComprobantes(SegurosIvro seguros, Long origen);
    
    /**
     * Genera los cuestionarios para una lista de seguros.
     *
     * @param seguros los seguros a generar sus comprobantes
     * @return los cuestionarios generados
     */
    DocumentoSeguro generaCuestionarios(SegurosIvro seguros);
    
}
