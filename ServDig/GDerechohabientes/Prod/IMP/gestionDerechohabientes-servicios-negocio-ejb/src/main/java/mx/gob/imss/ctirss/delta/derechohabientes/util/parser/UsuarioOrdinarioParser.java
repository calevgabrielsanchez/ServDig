package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioOrdinario;
import mx.gob.imss.ctirss.delta.persistence.DitUsuarioOrdinario;

public class UsuarioOrdinarioParser {
	
	private static final Logger logger = Logger.getLogger(UsuarioOrdinarioParser.class);

	public static DitUsuarioOrdinario modelToPersist(UsuarioOrdinario entrada) throws DerechohabientesBusinessException{
		DitUsuarioOrdinario salida=null;
		if(entrada !=null){
			try {
				salida = new DitUsuarioOrdinario();
				salida.setCveIdUsuarioOrdinario(entrada.getIdUsuarioOrdinario());
				salida.setDitPatronSujetoObligado(PatronSujetoObligadoParser.modelToPersist(entrada.getSujetoObligado()));
				salida.setDitUsuario(UsuarioParser.modelToPersist(entrada.getUsuario()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_USUARIO_ORDINARIO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_USUARIO_ORDINARIO+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static UsuarioOrdinario persisToModel(DitUsuarioOrdinario entrada) throws DerechohabientesBusinessException{
		UsuarioOrdinario salida=null;
		if(entrada!=null){
			try {
				salida=new UsuarioOrdinario();
				salida.setIdUsuarioOrdinario(entrada.getCveIdUsuarioOrdinario());
				salida.setSujetoObligado(PatronSujetoObligadoParser.persisToModel(entrada.getDitPatronSujetoObligado()));
				salida.setUsuario(UsuarioParser.persisToModel(entrada.getDitUsuario()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_USUARIO_ORDINARIO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}