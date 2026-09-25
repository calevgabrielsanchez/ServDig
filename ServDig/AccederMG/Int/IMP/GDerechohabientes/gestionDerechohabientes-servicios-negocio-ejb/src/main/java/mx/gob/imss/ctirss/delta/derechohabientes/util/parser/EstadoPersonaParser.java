package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoPersona;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoPersona;

public class EstadoPersonaParser {
	private static final Logger logger = Logger.getLogger(EstadoPersonaParser.class);

	
	public static DicEstadoPersona modelToPersist(EstadoPersona entrada) throws DerechohabientesBusinessException{
		DicEstadoPersona salida=null;
		if(entrada !=null){
			try {
				salida=new DicEstadoPersona();
				salida.setCveEstadoPersona(entrada.getIdEstadoPersona().intValue());
				salida.setDesEstadoPersona(entrada.getDescripcion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_ESTADO_PERSONA, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_PERSONA+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static EstadoPersona persisToModel(DicEstadoPersona entrada) throws DerechohabientesBusinessException{
		EstadoPersona salida=null;
		if(entrada!=null){
			try {
				salida=new EstadoPersona();
				salida.setIdEstadoPersona(entrada.getCveEstadoPersona().longValue());
				salida.setDescripcion(entrada.getDesEstadoPersona());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_PERSONA+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}