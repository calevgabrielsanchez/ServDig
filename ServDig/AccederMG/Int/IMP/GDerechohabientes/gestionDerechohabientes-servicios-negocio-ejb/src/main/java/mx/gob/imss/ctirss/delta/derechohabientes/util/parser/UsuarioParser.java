package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;



import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.persistence.DitUsuario;

public class UsuarioParser {
	
	private static final Logger logger = Logger.getLogger(UsuarioParser.class);

	//Carlos -  no teine todas las propiedades
	public static DitUsuario modelToPersist(Usuario entrada) throws DerechohabientesBusinessException{
		DitUsuario salida=null;
		if(entrada !=null){
			try {
				salida = new DitUsuario();
				salida.setCveIdUsuario(new Long(entrada.getCveIdUsuario()).longValue());
				salida.setNomUsuarioSistema(entrada.getUsuario());
				salida.setRefPassword(entrada.getPassword());
				salida.setDicPerfilUsuario(PerfilUsuarioParser.modelToPersist(entrada.getPerfilUsuario()));
				salida.setDitPersona(FisicaParser.modelToPersist(entrada.getFisica()));
				salida.setNomMaterno(entrada.getNomMaterno());
				salida.setNomPaterno(entrada.getNomPaterno());
				salida.setNomNombre(entrada.getNomNombre());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_USUARIO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_USUARIO+" | "+e.getMessage());
			}
			
		}
		
		
		return salida;	
	}
	
	//Carlos - np se ajusta al usuario local
	public static Usuario persisToModel(DitUsuario entrada) throws DerechohabientesBusinessException{
		Usuario salida=null;
		if(entrada!=null){
			try {
				salida=new Usuario();
				salida.setCveIdUsuario(entrada.getCveIdUsuario()+"");
				salida.setUsuario(entrada.getNomUsuarioSistema());
				salida.setPassword(entrada.getRefPassword());
				salida.setPerfilUsuario(PerfilUsuarioParser.persisToModel(entrada.getDicPerfilUsuario()));
				salida.setFisica(FisicaParser.persisToModel(entrada.getDitPersona()));
				salida.setNomMaterno(entrada.getNomMaterno());
				salida.setNomPaterno(entrada.getNomPaterno());
				salida.setNomNombre(entrada.getNomNombre());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_USUARIO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}