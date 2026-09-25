/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorAlConsultarTimbradosException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorCancelarTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.NoExistenDatosParaCancelarException;
import mx.gob.imss.ctirss.delta.cobranza.model.LoteCFDI;

/**
 * @author vanderluk
 *
 */
@Remote
public interface ICancelacionTimbradoServiceRemote {

	
	
	/**
	 * Metodo que realiza la cancelacion de los timbrados a partir de una fecha
	 * de proceso.
	 * @param fechaProceso
	 * @throws ErrorCancelarTimbradoException
	 */
	public LoteCFDI[] cancelaTimbradoPorFechaDeProceso(String fechaProceso) throws ErrorAlConsultarTimbradosException, NoExistenDatosParaCancelarException;
	
	/**
	 * Metodo que realiza la cancelacion de los timbrados a partir de una fecha
	 * de proceso.
	 * @param fechaProceso
	 * @throws ErrorCancelarTimbradoException
	 */
	public LoteCFDI[] obtenerFoliosParaCancelarPorFecha(String fechaProceso);
	
	/**
	 * Metodo que realiza la cancelacion de los timbrados a partir de una fecha
	 * de proceso.
	 * @param fechaProceso
	 * @throws ErrorCancelarTimbradoException
	 */
	public void actualizaRegistroCFDICancelado(LoteCFDI[] lotesCancelados);
	
}
