package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;

public class ModalidadParser {
	private static final Logger logger = Logger.getLogger(ModalidadParser.class);

	public static DicModalidad modelToPersist(Modalidad entrada) throws DerechohabientesBusinessException{
		DicModalidad salida=null;
		if(entrada != null){
			try {
				salida=new DicModalidad();
				salida.setCveIdModalidad(entrada.getIdModalidad());
				salida.setDesModalidad(entrada.getDescripcion());
				
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_MODALIDAD, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MODALIDAD+" | "+e.getMessage());
			}
			
		}
		return salida;	
	}
	
	public static Modalidad persisToModel(DicModalidad entrada) throws DerechohabientesBusinessException{
		Modalidad salida=null;
		if(entrada !=null){
			try {
				salida=new Modalidad();
				salida.setIdModalidad(entrada.getCveIdModalidad());
				salida.setDescripcion(entrada.getDesModalidad());
				salida.setNumModalidad(entrada.getNumModalidad());
				salida.setSiglaAgregadoMedico(entrada.getSiglaAgregadoMedico());
				salida.setDesCorta(entrada.getDesNomModalidadCorto());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MODALIDAD+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}