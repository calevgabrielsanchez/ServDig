package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DicSubestadoDerechohabiente;

@Stateless(name = "subEstadoDerechohabienteParserService", mappedName = "subEstadoDerechohabienteParserService")
public class SubEstadoDerechohabienteParserService extends AbstractServiceUtility implements SubEstadoDerechohabienteParserServiceLocal{
	
	public DicSubestadoDerechohabiente modelToPersist(SubEstadoDerechohabiente entrada) throws DerechohabientesBusinessException{
		DicSubestadoDerechohabiente salida=null;
		if(entrada !=null){
			try {
				salida=new DicSubestadoDerechohabiente();
				salida.setCveSubestadoDerechohabiente(entrada.getIdSubEstadoDerechohabiente());
				salida.setDesSubestadoDerechohabiente(entrada.getDescripcion());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_SUBESTADO_DERECHOHABIENTE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SUBESTADO_DERECHOHABIENTE+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public SubEstadoDerechohabiente persisToModel(DicSubestadoDerechohabiente entrada) throws DerechohabientesBusinessException{
		SubEstadoDerechohabiente salida=null;
		if(entrada!=null){
			try {
				salida=new SubEstadoDerechohabiente();
				salida.setIdSubEstadoDerechohabiente(entrada.getCveSubestadoDerechohabiente());
				salida.setDescripcion(entrada.getDesSubestadoDerechohabiente());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SUBESTADO_DERECHOHABIENTE+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}