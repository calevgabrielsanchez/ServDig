package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.DiasFestivos;
import mx.gob.imss.ctirss.delta.persistence.DicDiasFestivo;

public class DiasFestivosParser {
	private static final Logger logger = Logger.getLogger(DiasFestivosParser.class);
	
	public static DicDiasFestivo modelToPersist(DiasFestivos entrada) throws DerechohabientesBusinessException{
		
		DicDiasFestivo salida=null;
		
		if(entrada!=null){
			try {
				
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_DIAS_FESTIVOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DIAS_FESTIVOS+" | "+e.getMessage());
			}
			salida=new DicDiasFestivo();
			salida.setFecDiaFestivo(entrada.getFecha());
		}
		
		return salida;	
	}
	
	public static DiasFestivos persisToModel(DicDiasFestivo entrada) throws DerechohabientesBusinessException{
		
		DiasFestivos salida=null;
		
		if(entrada!=null){
			try {
				salida=new DiasFestivos();
				salida.setFecha(entrada.getFecDiaFestivo());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DIAS_FESTIVOS+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	public static List<DiasFestivos> persistToModelList(List<DicDiasFestivo> entradaList) throws DerechohabientesBusinessException{
		List<DiasFestivos> salidaList= new ArrayList<DiasFestivos>();
		if(entradaList.size() > 0){
			try {
				for(DicDiasFestivo salida:entradaList){
					salidaList.add(persisToModel(salida));
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DIAS_FESTIVOS+" | "+e.getMessage());
			}
			
		}
		
		return salidaList;
	}
}