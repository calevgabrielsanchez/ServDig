package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;


import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ClavePresupuestal;
import mx.gob.imss.ctirss.delta.persistence.DicClavePresupuestal;


public class ClavePresupuestalParser {
	
	private static final Logger logger = Logger.getLogger(ClavePresupuestalParser.class);
	
	public static DicClavePresupuestal modelToPersist(ClavePresupuestal entrada) throws DerechohabientesBusinessException{
		DicClavePresupuestal salida=null;
		if(entrada !=null){
			try {
				salida=new DicClavePresupuestal();
				salida.setCveIdClavePresupuestal(entrada.getIdClavePresupuestal());			
				salida.setDesClavePresupuestal(entrada.getDescripcion());	
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_CLAVE_PRESUPUESTAL, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CLAVE_PRESUPUESTAL+" | "+e.getMessage());
			}
					
		}
		
		return salida;	
	}
	
	public static ClavePresupuestal persisToModel(DicClavePresupuestal entrada) throws DerechohabientesBusinessException{
		ClavePresupuestal salida=null;
		if(entrada!=null){
			try {
				salida=new ClavePresupuestal();
				salida.setIdClavePresupuestal(entrada.getCveIdClavePresupuestal());
				salida.setClavePresupuestal(entrada.getCvePresupuestal());
				salida.setDescripcion(entrada.getDesClavePresupuestal());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CLAVE_PRESUPUESTAL+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}