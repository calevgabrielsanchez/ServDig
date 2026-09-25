package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoDerechohabiente;

@Stateless( name = "estadoDerechohabienteParserService", mappedName = "estadoDerechohabienteParserService")
public class EstadoDerechohabienteParserService extends AbstractServiceUtility implements EstadoDerechohabienteParserServiceLocal{
	
	public DicEstadoDerechohabiente modelToPersist(EstadoDerechohabiente entrada) throws DerechohabientesBusinessException{
		DicEstadoDerechohabiente salida=null;
		if(entrada !=null){
			try {
				salida=new DicEstadoDerechohabiente();
				salida.setCveEstadoDerechohabiente(entrada.getIdEstadoDerechohabiente());
				salida.setDesEstadoDerechohabiente(entrada.getDescripcion());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_ESTADO_DERECHOHABIENTE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_DERECHOHABIENTE+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public EstadoDerechohabiente persisToModel(DicEstadoDerechohabiente entrada) throws DerechohabientesBusinessException{
		EstadoDerechohabiente salida=null;
		if(entrada!=null){
			try {
				salida=new EstadoDerechohabiente();
				salida.setIdEstadoDerechohabiente(entrada.getCveEstadoDerechohabiente());
				salida.setDescripcion(entrada.getDesEstadoDerechohabiente());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_DERECHOHABIENTE+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}