package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

public class PersonaParser {
	private static final Logger logger = Logger.getLogger(PersonaParser.class);

	public static DitPersona modelToPersist(Persona entrada) throws DerechohabientesBusinessException{
		DitPersona salida=null;
		if(entrada !=null){
			try {
				salida=new DitPersona();
				salida.setCveIdPersona(entrada.getIdPersona());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_PERSONA, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PERSONA+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static Persona persisToModel(DitPersona entrada) throws DerechohabientesBusinessException{
		Persona salida=null;
		if(entrada!=null){
			try {
				salida=new Persona();
				salida.setIdPersona(entrada.getCveIdPersona());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PERSONA+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}