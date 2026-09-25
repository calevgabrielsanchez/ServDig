/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.services.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.cobranza.dto.PagoReferenciadoDTO;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorCancelarTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionComprobanteException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionXMLPagoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnServicioTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.model.LoteCFDI;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaCancelacionCfdi;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaServicioTimbradoCFDI;

/**
 * @author Lucio Duran Silva
 *
 */
@Remote
public interface GenerarTimbradoCFDIServiceBusinessRemote {

	/**
	 * 
	 * @param pago
	 * @return
	 * @throws ErrorEnGeneracionXMLPagoException
	 * @throws ErrorEnGeneracionComprobanteException
	 */
	public RespuestaServicioTimbradoCFDI generaComprobantePago(PagoReferenciadoDTO pago) throws ErrorEnGeneracionXMLPagoException, ErrorEnGeneracionComprobanteException, ErrorEnServicioTimbradoException;
	
	/**
	 * 
	 * @param registros
	 * @return
	 * @throws ErrorCancelarTimbradoException
	 */
	public RegistroCFDI[] cancelarPagosTimbrados(RegistroCFDI[] registros) throws ErrorCancelarTimbradoException;
	
	/**
	 * 
	 * @param lotes
	 * @return
	 * @throws ErrorCancelarTimbradoException
	 */
	 public LoteCFDI[] cancelarPagosTimbradosPorLote(LoteCFDI[] lotesFolios) throws ErrorCancelarTimbradoException;
	 
	 public RespuestaCancelacionCfdi solicitarCancelacionCfdi(String uuid);
}
