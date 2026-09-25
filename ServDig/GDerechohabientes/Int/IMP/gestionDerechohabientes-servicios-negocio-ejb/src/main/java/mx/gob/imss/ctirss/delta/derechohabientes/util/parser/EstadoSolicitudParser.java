package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud;

public class EstadoSolicitudParser {
	private static final Logger logger = Logger.getLogger(EstadoSolicitudParser.class);

	public static DicEstadoSolicitud modelToPersist(EstadoSolicitud entrada) throws DerechohabientesBusinessException{
		DicEstadoSolicitud salida=null;
		if(entrada!=null){
			try {
				 salida = new DicEstadoSolicitud();
				 salida.setDesEstadoSolicitud(entrada.getDescripcion());
				 salida.setCveIdEstadoSolicitud(entrada.getIdEstadoSolicitud().longValue());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_ESTADO_SOLICITUD, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_SOLICITUD+" | "+e.getMessage());
			}
			 
		}
		
		
		
		return salida;	
	}
	
	public static EstadoSolicitud persisToModel(DicEstadoSolicitud entrada) throws DerechohabientesBusinessException{
		EstadoSolicitud salida=null;
		if(entrada!=null){
			try {
				salida=new EstadoSolicitud();
				salida.setDescripcion(entrada.getDesEstadoSolicitud());
				salida.setIdEstadoSolicitud(entrada.getCveIdEstadoSolicitud().intValue());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_SOLICITUD+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
}
