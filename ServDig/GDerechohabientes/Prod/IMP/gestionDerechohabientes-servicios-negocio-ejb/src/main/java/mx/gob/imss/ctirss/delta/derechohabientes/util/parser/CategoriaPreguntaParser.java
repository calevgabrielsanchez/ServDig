/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

/**
 * @author ghdolores
 *
 */
public class CategoriaPreguntaParser {
	private static final Logger logger = Logger.getLogger(CategoriaPreguntaParser.class);
	/*
	public static DicCategoriaPregunta modelToPersist(CategoriaPregunta entrada) throws DerechohabientesBusinessException{
		DicCategoriaPregunta salida=null;
		if(entrada!=null){
			try {
				salida = new DicCategoriaPregunta();
				 salida.setDesCategoriaPregunta(entrada.getDescripcion());
				 salida.setCveIdCategoriaPregunta(entrada.getIdCategoriaPregunta());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_CATEGORIA_PREGUNTA, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CATEGORIA_PREGUNTA+" | "+e.getMessage());
			}
			 
		}
		
		return salida;	
	}
	
	public static CategoriaPregunta persisToModel(DicCategoriaPregunta entrada) throws DerechohabientesBusinessException{
		CategoriaPregunta salida=null;
		if(entrada!=null){
			try {
				salida=new CategoriaPregunta();
				salida.setIdCategoriaPregunta(entrada.getCveIdCategoriaPregunta());
				salida.setDescripcion(entrada.getDesCategoriaPregunta());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CATEGORIA_PREGUNTA+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	*/
}
