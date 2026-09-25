package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Servicio;
import mx.gob.imss.ctirss.delta.persistence.DicServicio;

public class ServiciosParser {
	private static final Logger logger = Logger.getLogger(ServiciosParser.class);


	public static DicServicio modelToPersist(Servicio entrada) throws DerechohabientesBusinessException{
		DicServicio salida = null;
		if(entrada != null){
			try {
				salida=new DicServicio();
				salida.setDesServicio(entrada.getDescripcion());
				salida.setCveIdServicio(entrada.getIdServicio());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_SERVICIOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SERVICIOS+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	public static Servicio persisToModel(DicServicio entrada) throws DerechohabientesBusinessException{
		Servicio salida= null;
		if(entrada != null){
			try {
				salida=new Servicio();
				salida.setIdServicio(entrada.getCveIdServicio());
				salida.setDescripcion(entrada.getDesServicio());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SERVICIOS+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	public static List<Servicio> persisToModelList(List<DicServicio> entrada) throws DerechohabientesBusinessException{
		List<Servicio> salida= null;
		if(entrada != null && entrada.size() > 0){
			salida=new ArrayList<Servicio>();
			for (DicServicio dicServicio : entrada) {
				salida.add(persisToModel(dicServicio));
			}
			
		}
		return salida;
	}
	
}
