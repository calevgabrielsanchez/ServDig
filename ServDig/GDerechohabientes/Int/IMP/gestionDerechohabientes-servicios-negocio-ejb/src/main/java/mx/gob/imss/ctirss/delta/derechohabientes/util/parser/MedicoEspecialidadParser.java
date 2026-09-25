package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEspecialidad;
import mx.gob.imss.ctirss.delta.persistence.DicEspecialidadMedico;

public class MedicoEspecialidadParser {
	private static final Logger logger = Logger.getLogger(MedicoEspecialidadParser.class);

	public static DicEspecialidadMedico modelToPersist(MedicoEspecialidad entrada) throws DerechohabientesBusinessException{
		DicEspecialidadMedico salida=null;
		if(entrada!=null){
			try {
				 salida = new DicEspecialidadMedico();
				 salida.setCveEspecialidad(entrada.getIdMedicoEspacialidad());
				 salida.setDesEspecialidad(entrada.getDescripcion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_MEDICO_ESPECIALIDAD, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MEDICO_ESPECIALIDAD+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static MedicoEspecialidad persisToModel(DicEspecialidadMedico entrada) throws DerechohabientesBusinessException{
		MedicoEspecialidad salida=null;
		if(entrada!=null){
			try {
				salida=new MedicoEspecialidad();
				salida.setDescripcion(entrada.getDesEspecialidad());
				salida.setIdMedicoEspacialidad(entrada.getCveEspecialidad());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MEDICO_ESPECIALIDAD+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
}
