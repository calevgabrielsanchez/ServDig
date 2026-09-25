package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;

public class SubdelegacionIMSSParser {
	
	private static final Logger logger = Logger.getLogger(SubdelegacionIMSSParser.class);

	public static DicSubdelegacion modelToPersist(Subdelegacion entrada) throws DerechohabientesBusinessException{
		DicSubdelegacion salida=null;
		if(entrada !=null){
			try {
				salida=new DicSubdelegacion();
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_SUBDELEGACION_IMSS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SUBDELEGACION_IMSS+" | "+e.getMessage());
			}						
		}
	
		
		return salida;	
	}
	
	public static Subdelegacion persisToModel(DicSubdelegacion entrada) throws DerechohabientesBusinessException{
		Subdelegacion salida=null;
		if(entrada!=null){
			try {
				salida=new Subdelegacion();
				
				salida.setId(entrada.getCveIdSubdelegacion());
				salida.setDescripcion(entrada.getDesSubdelegacion());
				salida.setClave(entrada.getClaveSubdelegacion());
				
				Delegacion delegacion = DelegacionIMSSParser.persisToModel(entrada.getDicDelegacion());
				
				salida.setDelegacion(delegacion);
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SUBDELEGACION_IMSS+" | "+e.getMessage());
			}
			
			
		}
		return salida;
	}
}
