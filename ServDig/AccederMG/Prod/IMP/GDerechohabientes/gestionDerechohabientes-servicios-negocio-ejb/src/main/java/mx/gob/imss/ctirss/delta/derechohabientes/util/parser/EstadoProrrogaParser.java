package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoProrroga;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoProrroga;

import org.apache.log4j.Logger;

public class EstadoProrrogaParser {

	private static final Logger logger = Logger.getLogger(EstadoProrrogaParser.class);
    
	public static DicEstadoProrroga modelToPersist(EstadoProrroga entrada) throws DerechohabientesBusinessException{
		DicEstadoProrroga salida =null;
		
		
		if(entrada!=null){
			try {
				salida = new DicEstadoProrroga();
				salida.setCveIdEstadoProrroga(entrada.getIdEstadoProrroga().intValue());
				salida.setDesEstadoProrroga(entrada.getDescripcion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_ESTADO_PRORROGA, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_PRORROGA+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	
	public static EstadoProrroga persisToModel(DicEstadoProrroga entrada) throws DerechohabientesBusinessException{
		EstadoProrroga salida=null;
		if(entrada!=null){
			try {
				salida = new EstadoProrroga();
				salida.setDescripcion(entrada.getDesEstadoProrroga());
				salida.setIdEstadoProrroga(entrada.getCveIdEstadoProrroga().longValue());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_PRORROGA+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	
}
