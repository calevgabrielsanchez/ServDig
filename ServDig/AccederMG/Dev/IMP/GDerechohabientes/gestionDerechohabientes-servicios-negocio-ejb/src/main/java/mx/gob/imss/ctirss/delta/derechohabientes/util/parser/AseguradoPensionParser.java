package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.asegurado.AseguradoPension;
import mx.gob.imss.ctirss.delta.persistence.DitAseguradoPension;

public class AseguradoPensionParser {
	private static final Logger logger = Logger.getLogger(AseguradoPensionParser.class);
	
	public static DitAseguradoPension modelToPersist(AseguradoPension entrada) throws DerechohabientesBusinessException{
		DitAseguradoPension salida=null;
		if(entrada !=null){
			try {
				salida=new DitAseguradoPension();
				salida.setCveIdAseguradoPension(entrada.getIdAseguradoPension());
				salida.setDitAsegurado(AseguradoParser.modelToPersist(entrada.getAsegurado()));
				salida.setDicTipoPension(TipoPensionParser.modelToPersist(entrada.getTipoPension()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_ASEGURADO_PENSION, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASEGURADO_PENSION+" | "+e.getMessage());
			}
		}
		
		return salida;	
	}
	
	public static AseguradoPension persisToModel(DitAseguradoPension entrada) throws DerechohabientesBusinessException{
		AseguradoPension salida=null;
		if(entrada!=null){
			try {
				salida=new AseguradoPension();
				salida.setIdAseguradoPension(entrada.getCveIdAseguradoPension());
				salida.setAsegurado(AseguradoParser.persisToModel(entrada.getDitAsegurado()));
				salida.setTipoPension(TipoPensionParser.persisToModel(entrada.getDicTipoPension()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASEGURADO_PENSION+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}