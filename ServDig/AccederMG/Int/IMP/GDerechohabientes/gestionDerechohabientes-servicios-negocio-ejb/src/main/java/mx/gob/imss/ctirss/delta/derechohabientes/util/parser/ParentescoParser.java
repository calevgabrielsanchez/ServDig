package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco;

public class ParentescoParser {
	private static final Logger logger = Logger.getLogger(ParentescoParser.class);

	public static DicCalidadParentesco modelToPersist(Parentesco entrada) throws DerechohabientesBusinessException{
		DicCalidadParentesco salida=null;
		if(entrada !=null){
			try {
				salida=new DicCalidadParentesco();
				salida.setCveIdCalidadParentesco(entrada.getIdParentesco());
				salida.setDesParentesco(entrada.getDescripcion());
				salida.setDesTipificacionFemenina(entrada.getDescripcionFemenina());
				salida.setDesTipificacionMasculina(entrada.getDescripcionMasculina());
				salida.setCalidadMaxima(entrada.getCalidadMaxima().longValue());
				salida.setCalidadMinima(entrada.getCalidadMinima().longValue());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_PARENTESCO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PARENTESCO+" | "+e.getMessage());
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
				salida.setDescripcionFemenina(entrada.getDesTipificacionFemenina());
				salida.setDescripcionMasculina(entrada.getDesTipificacionMasculina());
				salida.setCalidadMaxima(new BigDecimal(""+entrada.getCalidadMaxima()));
				salida.setCalidadMinima(new BigDecimal(""+entrada.getCalidadMinima()));		
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PARENTESCO+" | "+e.getMessage());
			}
							
		}
		return salida;
	}
	public static List<Parentesco> persisToModelList(List<DicCalidadParentesco> entrada) throws DerechohabientesBusinessException{
		List<Parentesco> salida=new ArrayList<Parentesco>();
		if(entrada!=null && entrada.size() > 0){
		   for (DicCalidadParentesco parentesco : entrada) {
			   salida.add(persisToModel(parentesco));
		   }							
		}
		return salida;
	}
	
	
	
	
}