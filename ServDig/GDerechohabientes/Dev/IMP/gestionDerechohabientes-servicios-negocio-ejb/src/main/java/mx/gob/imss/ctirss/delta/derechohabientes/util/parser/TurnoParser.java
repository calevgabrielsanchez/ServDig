package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;


import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.persistence.DicTurno;


public class TurnoParser {
	
	private static final Logger logger = Logger.getLogger(TurnoParser.class);

	public static DicTurno modelToPersist(Turno entrada) throws DerechohabientesBusinessException{
		DicTurno salida=null;
		if(entrada !=null){
			try {
				salida=new DicTurno();
				salida.setCveIdTurno(entrada.getIdTurno());
				salida.setDesDescripcion(entrada.getDescripcion());
				//TODO CHECAR EL CAMPO
				//salida.setRefHoraFinTurno(entrada.getHoraFinTurno());
				//salida.setRefHoraInicioTurno(entrada.getHoraInicioTurno());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TURNO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TURNO+" | "+e.getMessage());
			}
			
		}
		
		
		return salida;	
	}
	
	public static Turno persisToModel(DicTurno entrada) throws DerechohabientesBusinessException{
		Turno salida=null;
		if(entrada!=null){
			try {
				salida=new Turno();
				salida.setIdTurno(entrada.getCveIdTurno());
				salida.setDescripcion(entrada.getDesDescripcion());
				salida.setHoraInicioTurno(entrada.getRefHoraInicioTurno());
				salida.setHoraFinTurno(entrada.getRefHoraFinTurno());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TURNO+" | "+e.getMessage());
			}
						
		}
		return salida;
	}
	
}