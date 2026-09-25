package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ServicioPrestDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DicServicioPrestDerechohab;

public class ServicioPrestDerechohabParser {
	private static final Logger logger = Logger.getLogger(ServicioPrestDerechohabParser.class);

	public static DicServicioPrestDerechohab modelToPersist(ServicioPrestDerechohab entrada) throws DerechohabientesBusinessException{
		DicServicioPrestDerechohab salida=null;
		if(entrada !=null){
			try {
				salida=new DicServicioPrestDerechohab();
				salida.setCveIdServicioDerechohab(entrada.getCveIdServicioDerechohab());
				salida.setIndServicioPension(entrada.getIndServicioPension());
				salida.setNomServicioDerechohab(entrada.getNomServicioDerechohab());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_SERVICIO_PREST_DERECHOHAB, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SERVICIO_PREST_DERECHOHAB+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static ServicioPrestDerechohab persisToModel(DicServicioPrestDerechohab entrada) throws DerechohabientesBusinessException{
		ServicioPrestDerechohab salida=null;
		if(entrada!=null){
			try {
				salida=new ServicioPrestDerechohab();			
				salida.setCveIdServicioDerechohab(entrada.getCveIdServicioDerechohab());
				salida.setIndServicioPension(entrada.getIndServicioPension());
				salida.setNomServicioDerechohab(entrada.getNomServicioDerechohab());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SERVICIO_PREST_DERECHOHAB+" | "+e.getMessage());
			}
			
			
		}
		return salida;
	}
	
	public static List<ServicioPrestDerechohab> persisToModelList(List<DicServicioPrestDerechohab> entrada) throws DerechohabientesBusinessException{
		List<ServicioPrestDerechohab> salida=null;
		if(entrada!=null && entrada.size() > 0){
			salida = new ArrayList<ServicioPrestDerechohab>();
			for (DicServicioPrestDerechohab dicServicio : entrada) {
				salida.add(persisToModel(dicServicio));
			}
		}
		return salida;
	}
	
}
