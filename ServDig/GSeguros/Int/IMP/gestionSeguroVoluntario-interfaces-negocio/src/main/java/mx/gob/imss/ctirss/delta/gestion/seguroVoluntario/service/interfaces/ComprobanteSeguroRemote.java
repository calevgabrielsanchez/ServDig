/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.digital.modelo.seguros.ComprobantesSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;

/**
 * @author NOVUTECK1
 *
 */
@Remote
public interface ComprobanteSeguroRemote {

    /**
     * GEnera los datos para los comprobantes segurn los seguros recibidos
     * @param seguros
     * @return
     */
    ComprobantesSeguroReporte generaDatosComprobante(SegurosIvro seguros);
    
    /**
     * GEnera el documento resultante pa los comprobantes según los seguros recibidos
     * @param seguros seguros a generar su comprobante
     * @return el seguro generado
     */
    DocumentoSeguro generaComprobantes(SegurosIvro seguros);
}
