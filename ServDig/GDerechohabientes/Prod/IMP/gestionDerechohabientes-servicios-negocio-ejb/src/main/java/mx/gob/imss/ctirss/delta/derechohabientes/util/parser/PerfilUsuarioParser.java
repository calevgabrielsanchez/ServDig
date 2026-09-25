package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.persistence.DicPerfilUsuario;

public class PerfilUsuarioParser {
	private static final Logger logger = Logger.getLogger(PerfilUsuarioParser.class);

	public static DicPerfilUsuario modelToPersist(PerfilUsuario entrada) throws DerechohabientesBusinessException{
		DicPerfilUsuario salida=null;
		if(entrada !=null){
			try {
				salida = new DicPerfilUsuario();
				salida.setCveIdPerfilUsuario(entrada.getIdPerfilUsuario());
				salida.setDesPerfilUsuario(entrada.getDescripcion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_PERFIL_USUARIO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PERFIL_USUARIO+" | "+e.getMessage());
			}
			
		}
		
		
		return salida;	
	}
	
	public static PerfilUsuario persisToModel(DicPerfilUsuario entrada) throws DerechohabientesBusinessException{
		PerfilUsuario salida=null;
		if(entrada!=null){
			try {
				salida=new PerfilUsuario();
				salida.setIdPerfilUsuario(entrada.getCveIdPerfilUsuario());
				salida.setDescripcion(entrada.getDesPerfilUsuario());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PERFIL_USUARIO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}