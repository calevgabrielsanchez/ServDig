package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco;

public class CalidadParentescoParser {
	private static final Logger logger = Logger.getLogger(CalidadParentescoParser.class);
	
	public static DicCalidadParentesco modelToPersist(Parentesco entrada) throws DerechohabientesBusinessException{
		DicCalidadParentesco salida=null;
		if(entrada !=null){
			try {
				salida=new DicCalidadParentesco();
				salida.setCveIdCalidadParentesco(entrada.getIdParentesco());
				salida.setDesParentesco(entrada.getDescripcion());
				salida.setCalidadMaxima(entrada.getCalidadMaxima().longValue());
				salida.setCalidadMinima(entrada.getCalidadMinima().longValue());
				salida.setDesTipificacionFemenina(entrada.getDescripcionFemenina());
				salida.setDesTipificacionMasculina(entrada.getDescripcionMasculina());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_CALIDAD_PARENTESCO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CALIDAD_PARENTESCO+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static Parentesco persisToModel(DicCalidadParentesco entrada) throws DerechohabientesBusinessException{
		Parentesco salida=null;
		if(entrada!=null){
			try {
				salida=new Parentesco();
				salida.setIdParentesco(entrada.getCveIdCalidadParentesco());
				salida.setDescripcion(entrada.getDesParentesco());
				salida.setCalidadMaxima(new BigDecimal(""+entrada.getCalidadMaxima()));
				salida.setCalidadMinima(new BigDecimal(""+entrada.getCalidadMinima()));
				salida.setDescripcionFemenina(entrada.getDesTipificacionFemenina());
				salida.setDescripcionMasculina(entrada.getDesTipificacionMasculina());	
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CALIDAD_PARENTESCO+" | "+e.getMessage());
			}					
		}
		return salida;
	}
	
}