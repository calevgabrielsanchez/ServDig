/**
 * Servicio para la generacion del sello, certificado y numero de serie correspondiente
 * a las especificaciones del Anexo 20 del SAT CFDI.
 */
package mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.service.business;

import mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.exception.ErrorSelloCFDIException;
import mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.model.SelloCFDI;

/**
 * @author Lucio Duran Silva
 *
 */
public interface ISelloCFDIBusinessService {
	
	
	/**
	 * Metodo para genear el sello del CFDI.
	 * @return
	 * @throws ErrorSelloCFDIException
	 */

	SelloCFDI generarSelloCFDI(String xml) throws ErrorSelloCFDIException;

}
