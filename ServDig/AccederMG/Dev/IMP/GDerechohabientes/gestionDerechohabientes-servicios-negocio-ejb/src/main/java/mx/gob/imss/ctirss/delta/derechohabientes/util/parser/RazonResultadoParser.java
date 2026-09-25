/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;


import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;

/**
 * @author ghdolores
 *
 */
public class RazonResultadoParser {
	private static final Logger logger = Logger.getLogger(RazonResultadoParser.class);

	public static DicRazonResultado modelToPersist(RazonResultado  entrada) throws DerechohabientesBusinessException{
		DicRazonResultado salida =null;
		
		if(entrada!=null){
			try {
				salida = new DicRazonResultado();
				salida.setCveIdRazonResultado(entrada.getIdRazonResultado()) ;
				salida.setDesRazonResultado(entrada.getDescripcion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_RAZON_RESULTADO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_RAZON_RESULTADO+" | "+e.getMessage());
			}
			
			
		}
		
		return salida;
	}
	
	
	public static RazonResultado persisToModel(DicRazonResultado entrada) throws DerechohabientesBusinessException{
		RazonResultado salida=null;
		if(entrada!=null){
			try {
				salida = new RazonResultado();
				salida.setDescripcion(entrada.getDesRazonResultado());
				salida.setIdRazonResultado(entrada.getCveIdRazonResultado());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_RAZON_RESULTADO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	

}
