package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Caracter;
import mx.gob.imss.ctirss.delta.persistence.DicCaracter;

import org.apache.log4j.Logger;

public class CaracterParser {
	private static final Logger logger = Logger.getLogger(CaracterParser.class);
	
	public static DicCaracter modelToPersist(Caracter entrada) throws DerechohabientesBusinessException{
		DicCaracter salida=null;
		if(entrada!=null){
			try {
				salida=new DicCaracter();
				salida.setCveIdCaracter(entrada.getIdCaracter());
				salida.setDesCaracter(entrada.getDescripcion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_CARACTER,e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CARACTER+" | "+e.getMessage());
			}	
			
		}
		return salida;
		
	}
	public static Caracter persisToModel(DicCaracter entrada) throws DerechohabientesBusinessException{
		
		Caracter  salida=null;
		if(entrada!=null){
			try {
				salida=new Caracter();
				salida.setIdCaracter(entrada.getCveIdCaracter());
				salida.setDescripcion(entrada.getDesCaracter());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS,e);			
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CARACTER+" | "+e.getMessage());
			}	
			
		}
		
		return salida;
		
	}
}	
