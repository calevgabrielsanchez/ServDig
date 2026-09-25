package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;


import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.NivelAtencion;
import mx.gob.imss.ctirss.delta.persistence.AccNivelAtencion;



public class NivelAtencionParser {
	private static final Logger logger = Logger.getLogger(NivelAtencionParser.class);

	public static AccNivelAtencion modelToPersist(NivelAtencion entrada) throws DerechohabientesBusinessException{
		AccNivelAtencion salida=null;
		if(entrada !=null){
			try {
				salida=new AccNivelAtencion();
				salida.setCveNivelAtencion(entrada.getIdNivelAtencion().toString());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_NIVEL_ATENCION, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_NIVEL_ATENCION+" | "+e.getMessage());
			}
			

		}
		
		return salida;	
	}
	
	public static NivelAtencion persisToModel(AccNivelAtencion entrada) throws DerechohabientesBusinessException{
		NivelAtencion salida=null;
		if(entrada!=null){
			try {
				salida=new NivelAtencion();
				salida.setIdNivelAtencion(new Long(entrada.getCveNivelAtencion()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_NIVEL_ATENCION+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}