package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;

public class EstadoTramiteParser {

	private static final Logger logger = Logger.getLogger(EstadoTramiteParser.class);
    
	public static DicEstadoTramite modelToPersist(EstadoTramite entrada) throws DerechohabientesBusinessException{
		DicEstadoTramite salida =null;
		
		
		if(entrada!=null){
			try {
				salida = new DicEstadoTramite();
				salida.setCveIdEstadoTramite(entrada.getIdEstadoTramitePersona().longValue());
				salida.setDesEstadoTramite(entrada.getDescripcion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_ESTADO_TRAMITE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_TRAMITE+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	
	public static EstadoTramite persisToModel(DicEstadoTramite entrada) throws DerechohabientesBusinessException{
		EstadoTramite salida=null;
		if(entrada!=null){
			try {
				salida = new EstadoTramite();
				salida.setDescripcion(entrada.getDesEstadoTramite());
				salida.setIdEstadoTramitePersona(entrada.getCveIdEstadoTramite().intValue());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_TRAMITE+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	
}
