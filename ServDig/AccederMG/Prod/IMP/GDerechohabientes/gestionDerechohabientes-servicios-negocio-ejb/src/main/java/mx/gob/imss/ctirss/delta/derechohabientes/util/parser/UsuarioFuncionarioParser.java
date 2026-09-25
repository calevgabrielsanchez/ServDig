package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.persistence.DitUsuarioFuncionario;

public class UsuarioFuncionarioParser {
	private static final Logger logger = Logger.getLogger(UsuarioFuncionarioParser.class);

	public static DitUsuarioFuncionario modelToPersist(UsuarioFuncionario entrada) throws DerechohabientesBusinessException{
		DitUsuarioFuncionario salida=null;
		if(entrada !=null){
			try {
				salida = new DitUsuarioFuncionario();
				salida.setCveIdUsuarioFuncionario(entrada.getIdUsuarioFuncionario());
				salida.setDesCargo(entrada.getDescCargo());
				salida.setDicDelegacion(DelegacionIMSSParser.modelToPersist(entrada.getDelegacion()));
				salida.setDicSubdelegacion(SubdelegacionIMSSParser.modelToPersist(entrada.getSubdelegacion()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_USUARIO_FUNCIONARIO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_USUARIO_FUNCIONARIO+" | "+e.getMessage());
			}
						
		}
		
		return salida;	
	}
	
	public static UsuarioFuncionario persisToModel(DitUsuarioFuncionario entrada) throws DerechohabientesBusinessException{
		UsuarioFuncionario salida=null;
		if(entrada!=null){
			try {
				salida=new UsuarioFuncionario();
				salida.setIdUsuarioFuncionario(entrada.getCveIdUsuarioFuncionario());
				salida.setDescCargo(entrada.getDesCargo());
				salida.setDelegacion(DelegacionIMSSParser.persisToModel(entrada.getDicDelegacion()));
				salida.setSubdelegacion(SubdelegacionIMSSParser.persisToModel(entrada.getDicSubdelegacion()));
				salida.setUnidadMedicaFamiliar(UnidadMedicaFamiliarParser.persisToModel(entrada.getDicUmf() != null ? entrada.getDicUmf() : null));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_USUARIO_FUNCIONARIO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}