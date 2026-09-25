package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;

public class SexoParser {
	private static final Logger logger = Logger.getLogger(SexoParser.class);

	public static DicSexo modelToPersist(Sexo entrada) throws DerechohabientesBusinessException{
		DicSexo salida=null;
		if(entrada !=null && entrada.getIdSexo() != null && entrada.getIdSexo() != -1){
			salida=new DicSexo();
			try {
				salida.setCveIdSexo(entrada.getIdSexo().longValue());
				salida.setDesSexo(entrada.getDescripcion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_SEXO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SEXO+" | "+e.getMessage());
			}			
		}
		
		return salida;	
	}
	
	public static Sexo persisToModel(DicSexo entrada) throws DerechohabientesBusinessException{
		Sexo salida=null;
		if(entrada!=null){
			salida=new Sexo();
			try {
				salida.setIdSexo(new Integer(""+entrada.getCveIdSexo()));
				salida.setDescripcion(entrada.getDesSexo());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SEXO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	
	public static List<Sexo> persisToModelList(List<DicSexo> entrada) throws DerechohabientesBusinessException{
		List<Sexo> salida= null;
		if(entrada!=null && entrada.size() > 0){
			 salida=new ArrayList<Sexo>();
		   for (DicSexo sexo : entrada) {
			   salida.add(persisToModel(sexo));
		   }							
		}
		return salida;
	}
	
}