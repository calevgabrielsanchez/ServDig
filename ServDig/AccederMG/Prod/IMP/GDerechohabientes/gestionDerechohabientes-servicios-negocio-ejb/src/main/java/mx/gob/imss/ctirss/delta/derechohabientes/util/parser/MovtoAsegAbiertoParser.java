package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.asegurado.MovtoAsegAbierto;
import mx.gob.imss.ctirss.delta.persistence.DitMovtoAsegAbierto;

public class MovtoAsegAbiertoParser {
	private static final Logger logger = Logger.getLogger(MovtoAsegAbiertoParser.class);

	
	public static DitMovtoAsegAbierto modelToPersist(MovtoAsegAbierto entrada) throws DerechohabientesBusinessException{
		DitMovtoAsegAbierto salida=null;
		if(entrada !=null){
			try {
				salida=new DitMovtoAsegAbierto();
				salida.setCveIdMovimientoAsegurado(entrada.getIdMovimientoAsegurado());
				salida.setDesOcupacion(entrada.getDesOcupacion());
				salida.setSalarioArt33(entrada.getSalarioArt33());
				salida.setSalarioBase(entrada.getSalarioBase());
				salida.setSalarioReal(entrada.getSalarioReal());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_MOVTO_ASEG_ABIERTO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MOVTO_ASEG_ABIERTO+" | "+e.getMessage());
			}
			
		}
		return salida;	
	}
	
	public static MovtoAsegAbierto persistToModel(DitMovtoAsegAbierto entrada) throws DerechohabientesBusinessException{
		MovtoAsegAbierto salida=null;
		if(entrada !=null){
			try {
				salida=new MovtoAsegAbierto();
				salida.setIdMovimientoAsegurado(entrada.getCveIdMovimientoAsegurado());
				salida.setDesOcupacion(entrada.getDesOcupacion());
				salida.setSalarioArt33(entrada.getSalarioArt33());
				salida.setSalarioBase(entrada.getSalarioBase());
				salida.setSalarioReal(entrada.getSalarioReal());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MOVTO_ASEG_ABIERTO+" | "+e.getMessage());
			}
			
		}
		return salida;	
	}
	
	
}
