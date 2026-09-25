package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPerInteresadaSol;

public class TipoPerInteresadaSolParser {
	
	private static final Logger logger = Logger.getLogger(TipoPerInteresadaSolParser.class);

	
	public static DicTipoPerInteresadaSol modelToPersist(TipoPerInteresadaSol entrada) throws DerechohabientesBusinessException{
		DicTipoPerInteresadaSol salida=null;
		if(entrada!=null){
			try {
				salida=new DicTipoPerInteresadaSol();
				salida.setCveTipoInteresadaSol(entrada.getCveTipoInteresadaSol());
				salida.setDesTipoInteresadoSolicitud(entrada.getDesTipoInteresadoSolicitud());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TIPO_PER_INTERESADA_SOL, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_PER_INTERESADA_SOL+" | "+e.getMessage());
			}	
			
			
		}
		return salida;
		
	}
	public static TipoPerInteresadaSol persisToModel(DicTipoPerInteresadaSol entrada) throws DerechohabientesBusinessException{
		
		TipoPerInteresadaSol  salida=null;
		if(entrada!=null){
			try {
				salida=new TipoPerInteresadaSol();
				salida.setDesTipoInteresadoSolicitud(entrada.getDesTipoInteresadoSolicitud());
				salida.setCveTipoInteresadaSol(entrada.getCveTipoInteresadaSol());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_PER_INTERESADA_SOL+" | "+e.getMessage());
			}	
			
		}
		
		return salida;
		
	}
}
