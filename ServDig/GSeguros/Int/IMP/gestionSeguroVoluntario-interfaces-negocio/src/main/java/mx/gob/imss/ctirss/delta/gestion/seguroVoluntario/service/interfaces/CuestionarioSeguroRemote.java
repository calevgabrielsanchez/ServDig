/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.digital.modelo.seguros.CuestionariosSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;

/**
 * @author NOVUTECK1
 *
 */
@Remote
public interface CuestionarioSeguroRemote {
    
    /**
     * Genera los datos del comprobante de cuetionarios aplicados a un unos seguros
     * @param seguros los seguros a generar sus comprobantes
     * @return los comprobantes de los seguros
     */
    CuestionariosSeguroReporte generaDatosCuetionario(SegurosIvro seguros);

    /**
     * GEnera el documento resultante pa los comprobantes según los seguros recibidos
     * @param seguros seguros a generar su comprobante
     * @return el seguro generado
     */
    DocumentoSeguro generaCuestionariosSeguro(SegurosIvro seguros);
}
