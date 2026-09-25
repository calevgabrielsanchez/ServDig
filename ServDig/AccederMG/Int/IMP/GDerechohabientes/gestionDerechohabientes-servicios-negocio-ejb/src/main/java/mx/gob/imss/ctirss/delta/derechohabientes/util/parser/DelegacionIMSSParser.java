/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;

/**
 * @author ghdolores
 *
 */
public class DelegacionIMSSParser {
	private static final Logger logger = Logger.getLogger(DelegacionIMSSParser.class);
	
	public static DicDelegacion modelToPersist(Delegacion entrada) throws DerechohabientesBusinessException{
		DicDelegacion salida=null;
		if(entrada !=null){
			try {
				salida=new DicDelegacion();
				
				salida.setCveIdDelegacion(entrada.getId());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_DELEGACION_IMSS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DELEGACION_IMSS+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static Delegacion persisToModel(DicDelegacion entrada) throws DerechohabientesBusinessException{
		Delegacion salida=null;		
		if(entrada!=null){
			try {
				
				salida=new Delegacion();
				salida.setDescripcion(entrada.getDesDeleg());
				salida.setId(entrada.getCveIdDelegacion());
				salida.setClave(entrada.getClaveDelegacion());
				salida.setCiz(entrada.getCveCiz());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DELEGACION_IMSS+" | "+e.getMessage());
			}
			
			
		}
		return salida;
	}

}
