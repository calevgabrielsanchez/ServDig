package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

public class TipoCuestionarioParser {
	
	private static final Logger logger = Logger.getLogger(TipoCuestionarioParser.class);
	/*
	public static DicTipoCuestionario modelToPersist(TipoCuestionario entrada) throws DerechohabientesBusinessException{
		DicTipoCuestionario salida=null;
		if(entrada!=null){
			try {
				salida = new DicTipoCuestionario();
				 salida.setCveIdTipoCuestionario(entrada.getIdTipoCuestionario());
				 salida.setDesTipoCuestionario(entrada.getDescripcion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TIPO_CUESTIONARIO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_CUESTIONARIO+" | "+e.getMessage());
			}
			 
		}
		
		return salida;	
	}
	
	public static TipoCuestionario persisToModel(DicTipoCuestionario entrada) throws DerechohabientesBusinessException{
		TipoCuestionario salida=null;
		if(entrada!=null){
			try {
				salida=new TipoCuestionario();
				salida.setDescripcion(entrada.getDesTipoCuestionario());
				salida.setIdTipoCuestionario(entrada.getCveIdTipoCuestionario());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_CUESTIONARIO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	*/
}	
