/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud;

/**
 * @author ghdolores
 *
 */
public class TipoSolicitudParser {
	
	private static final Logger logger = Logger.getLogger(TipoSolicitudParser.class);

	public static DicTipoSolicitud modelToPersist(TipoSolicitud entrada) throws DerechohabientesBusinessException{
		DicTipoSolicitud salida=null;
		if(entrada!=null){
			try {
				 salida = new DicTipoSolicitud();
				 salida.setDesTipoSolicitud(entrada.getDescripcion());
				 salida.setCveIdTipoSolicitud(entrada.getIdTipoSolicitud());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TIPO_SOLICITUD, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_SOLICITUD+" | "+e.getMessage());
			}
			 
		}
		
		
		
		return salida;	
	}
	
	public static TipoSolicitud persisToModel(DicTipoSolicitud entrada) throws DerechohabientesBusinessException{
		TipoSolicitud salida=null;
		if(entrada!=null){
			try {
				salida=new TipoSolicitud();
				salida.setDescripcion(entrada.getDesTipoSolicitud());
				salida.setIdTipoSolicitud(entrada.getCveIdTipoSolicitud());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_SOLICITUD+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
}
