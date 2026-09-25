/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.digital.modelo.seguros.ComprobantesSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.CuestionariosSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;

/**
 * @author NOVUTECK1
 *
 */
@Local
public interface ComprobanteSeguroLocal {
    
    /**
     * Obtiene el documento resultante del comprobante del seguro
     * @param seguros la lista de seguros a generar su comprobante de compra
     * @return el documento con ls comprobantes del seguro
     */
    DocumentoSeguro generaComprobantes(SegurosIvro seguros);
    
    /**
     * Genera los datos del comprobante de seguro
     * @param seguros los seguros a generar sus comprobantes
     * @return los comprobantes de los seguros
     */
    ComprobantesSeguroReporte generaDatosComprobante(SegurosIvro seguros);
    
    /**
     * Genera los datos del comprobante de cuetionarios aplicados a un unos seguros
     * @param seguros los seguros a generar sus comprobantes
     * @return los comprobantes de los seguros
     */
    CuestionariosSeguroReporte generaDatosCuetionario(SegurosIvro seguros);
}
