package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ModServPresDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DitModServPresDerechohab;

public class ModServPresDerechohabParser {
	private static final Logger logger = Logger.getLogger(ModServPresDerechohabParser.class);

	
	public static DitModServPresDerechohab modelToPersist(ModServPresDerechohab entrada) throws DerechohabientesBusinessException{
		DitModServPresDerechohab salida=null;
		if(entrada !=null){
			try {
				salida=new DitModServPresDerechohab();
				if(entrada.getCveIdModServDerechohab()>0)
				salida.setCveIdModServDerechohab(entrada.getCveIdModServDerechohab());
				salida.setDicServicioPrestDerechohab(ServicioPrestDerechohabParser.modelToPersist(entrada.getServicioPrestDerechohab()));
				salida.setNumValorServicio(entrada.getNumValorServicio());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_MOD_SERV_PRES_DERECHOHAB, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MOD_SERV_PRES_DERECHOHAB+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static ModServPresDerechohab persisToModel(DitModServPresDerechohab entrada) throws DerechohabientesBusinessException{
		ModServPresDerechohab salida=null;
		if(entrada!=null){
			try {
				salida=new ModServPresDerechohab();
				if(entrada.getCveIdModServDerechohab()>0)
					salida.setCveIdModServDerechohab(entrada.getCveIdModServDerechohab());
				salida.setServicioPrestDerechohab(ServicioPrestDerechohabParser.persisToModel(entrada.getDicServicioPrestDerechohab()));
				salida.setNumValorServicio(entrada.getNumValorServicio());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MOD_SERV_PRES_DERECHOHAB+" | "+e.getMessage());
			}
			
			
		}
		return salida;
	}
	
	public static List<ModServPresDerechohab> persisToModelList(List<DitModServPresDerechohab> entrada) throws DerechohabientesBusinessException{
		List<ModServPresDerechohab> salida=null;
		if(entrada!=null && entrada.size() > 0){
			salida = new ArrayList<ModServPresDerechohab>();
			for (DitModServPresDerechohab dicServicio : entrada) {
				salida.add(persisToModel(dicServicio));
			}
		}
		return salida;
	}
}
