/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;

@Stateless( name = "delegacionUtility", mappedName = "delegacionUtility")
public class DelegacionUtility extends AbstractServiceUtility implements DelegacionUtilityLocal{
	
	private static final Logger LOGGER = Logger.getLogger(DelegacionUtility.class);
	
	public DicDelegacion modelToPersist(Delegacion entrada) throws DerechohabientesBusinessException{
		DicDelegacion salida=null;
		if(entrada !=null){
			try {
				salida=new DicDelegacion();
				
				salida.setCveIdDelegacion(entrada.getId());
			} catch (Exception e) {
				LOGGER.error(ExceptionMessages.ERROR_PARSER_DELEGACION_IMSS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DELEGACION_IMSS+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public Delegacion persisToModel(DicDelegacion entrada) throws DerechohabientesBusinessException{
		Delegacion salida=null;		
		if(entrada!=null){
			try {
				salida=new Delegacion();
				salida.setDescripcion(entrada.getDesDeleg());
				salida.setId(entrada.getCveIdDelegacion());
				salida.setClave(entrada.getClaveDelegacion());
			} catch (Exception e) {
				LOGGER.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DELEGACION_IMSS+" | "+e.getMessage());
			}
			
			
		}
		return salida;
	}

}
