package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;


import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;


public class TipoTramiteParser { 
	
	private static final Logger logger = Logger.getLogger(TipoTramiteParser.class);

	public static DicTipoTramite modelToPersist(TipoTramite entrada) throws DerechohabientesBusinessException{
		DicTipoTramite salida=null;
		if(entrada!=null){
			try {
				salida=new DicTipoTramite();
				
				salida.setCveIdTipoTramite(entrada.getIdTipoTramite().longValue());
				salida.setDesTipoTramite(entrada.getDescripcion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TIPO_TRAMITE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_TRAMITE+" | "+e.getMessage());
			}	
			
		}
		return salida;
		
	}
	public static TipoTramite persisToModel(DicTipoTramite entrada) throws DerechohabientesBusinessException{
		
		TipoTramite  salida=null;
		if(entrada!=null){
			try {
				salida=new TipoTramite();
				salida.setIdTipoTramite(entrada.getCveIdTipoTramite().intValue());
				salida.setDescripcion(entrada.getDesTipoTramite());
				salida.setHomoclave(entrada.getRefHomoclave());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_TRAMITE+" | "+e.getMessage());
			}	
			
		}
		
		return salida;
		
	}

	public static TipoTramite persisToModelConGuia(DicTipoTramite entrada) throws DerechohabientesBusinessException{
		
		TipoTramite  salida=null;
		if(entrada!=null){
			try {
				salida=new TipoTramite();
				salida.setIdTipoTramite(entrada.getCveIdTipoTramite().intValue());
				salida.setDescripcion(entrada.getDesTipoTramite());
				salida.setGuiaDetallada(entrada.getRefGuiaDetallada());
				salida.setGuiaRapida(entrada.getRefGuiaRapida());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_TRAMITE+" | "+e.getMessage());
			}	
			
		}
		
		return salida;
		
	}
}
