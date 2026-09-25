package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;


import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPension;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPension;


public class TipoPensionParser { 
	
	private static final Logger logger = Logger.getLogger(TipoPensionParser.class);

	public static DicTipoPension modelToPersist(TipoPension entrada) throws DerechohabientesBusinessException{
		DicTipoPension salida=null;
		if(entrada!=null){
			try {
				salida=new DicTipoPension();
				
				salida.setCveIdTipoPension(entrada.getIdTipoPension());
				salida.setRefMarcaPension(entrada.getMarcaPension());
				salida.setDesTipoPension(entrada.getDesTipoPension());		
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TIPO_PENSION, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_PENSION+" | "+e.getMessage());
			}	
							
		}
		return salida;
		
	}
	public static TipoPension persisToModel(DicTipoPension entrada) throws DerechohabientesBusinessException{
		
		TipoPension  salida=null;
		if(entrada!=null){
			try {
				salida=new TipoPension();
				salida.setIdTipoPension(entrada.getCveIdTipoPension());
				salida.setMarcaPension(entrada.getRefMarcaPension());
				salida.setDesTipoPension(entrada.getDesTipoPension());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_PENSION+" | "+e.getMessage());
			}	
						
		}
		
		return salida;
		
	}

}
