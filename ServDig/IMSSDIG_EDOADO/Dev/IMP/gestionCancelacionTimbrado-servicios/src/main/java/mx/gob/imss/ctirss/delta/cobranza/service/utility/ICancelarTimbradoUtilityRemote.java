/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorAlConsultarTimbradosException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorConvertirXMLTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.NoExistenDatosParaCancelarException;
import mx.gob.imss.ctirss.delta.cobranza.model.LoteCFDI;
import mx.gob.imss.ctirss.delta.cobranza.model.ProcOdiCompFisc;

/**
 * @author vanderluk
 *
 */
@Local
public interface ICancelarTimbradoUtilityRemote {

	
	
	static final Long NUMERO_DE_REGISTROS_POR_LOTE = new Long(50L);
	static final int REGISTROS_POR_LOTE = 500; 
	
	/**
	 * Metodo que obtiene del xml del timbrado el UUID
	 * @param xmlTimbrado
	 * @return
	 * @throws ErrorConvertirXMLTimbradoException
	 */
	public String getUUIDFromXML(String xmlTimbrado) throws ErrorConvertirXMLTimbradoException;
		
	/**
	 * Servicio encargado de armar los lotes a procesar (cada lote con un maximo de 500 registros)
	 * @param fechaProceso  totalFolios
	 * @return
	 * @throws ErrorAlConsultarTimbradosException
	 * @throws NoExistenDatosParaCancelarException 
	 */	
	public LoteCFDI[] armaLotesRegistrosTimbrados(List<ProcOdiCompFisc> foliosParaCancelar) throws ErrorAlConsultarTimbradosException, NoExistenDatosParaCancelarException;
	
	/**
	 * Servicio encargado de armar los lotes a procesar (cada lote con un maximo de 500 registros)
	 * @param fechaProceso  totalFolios
	 * @return
	 * @throws ErrorAlConsultarTimbradosException
	 * @throws NoExistenDatosParaCancelarException 
	 */
	public void verificaNumeroDeFoliosPorRegistroCFDI(LoteCFDI[] lotesCFDI);
}
