package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.persistence.DicPai;

public class PaisParser {

	private static final Logger logger = Logger.getLogger(PaisParser.class);

	public static DicPai modelToPersist(Pais entrada) throws DerechohabientesBusinessException{
		DicPai salida=null;
		if(entrada !=null && entrada.getIdPais() != null && entrada.getIdPais() != -1){
			salida=new DicPai();
			try {
				salida.setCveIdPais(entrada.getIdPais());
				salida.setDesPais(entrada.getDescripcion());
				salida.setDesNacionalidad(entrada.getNacionalidad());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_SEXO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SEXO+" | "+e.getMessage());
			}			
		}
		
		return salida;	
	}
	
	public static Pais persisToModel(DicPai entrada) throws DerechohabientesBusinessException{
		Pais salida=null;
		if(entrada!=null){
			salida= new Pais();
			try {
				salida.setIdPais(new Integer(""+entrada.getCveIdPais()));
				salida.setDescripcion(entrada.getDesPais());
				salida.setNacionalidad(entrada.getDesNacionalidad());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(e.getMessage());
			}
		}
		return salida;
	}
	
	
	public static List<Pais> persisToModelList(List<DicPai> entrada) throws DerechohabientesBusinessException{
		List<Pais> salida= null;
		if(entrada!=null && entrada.size() > 0){
			 salida=new ArrayList<Pais>();
		   for (DicPai dicPais : entrada) {
			   salida.add(persisToModel(dicPais));
		   }							
		}
		return salida;
	}

	
	
	
}
