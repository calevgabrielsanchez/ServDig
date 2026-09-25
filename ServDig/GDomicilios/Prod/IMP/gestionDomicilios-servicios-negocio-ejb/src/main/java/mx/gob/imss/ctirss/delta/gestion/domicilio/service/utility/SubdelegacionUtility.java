package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;

@Stateless( name = "subdelegacionUtility", mappedName = "subdelegacionUtility")
public class SubdelegacionUtility extends AbstractServiceUtility implements SubdelegacionUtilityLocal{
	
	@EJB
	private DelegacionUtilityLocal delegacionUtilityLocal;
	
	public DicSubdelegacion modelToPersist(Subdelegacion entrada) throws DerechohabientesBusinessException{
		DicSubdelegacion salida=null;
		if(entrada !=null){
			try {
				salida=new DicSubdelegacion();
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_SUBDELEGACION_IMSS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SUBDELEGACION_IMSS+" | "+e.getMessage());
			}						
		}
	
		
		return salida;	
	}
	
	public Subdelegacion persisToModel(DicSubdelegacion entrada) throws DerechohabientesBusinessException{
		Subdelegacion salida=null;
		if(entrada!=null){
			try {
				salida=new Subdelegacion();
				
				salida.setId(entrada.getCveIdSubdelegacion());
				salida.setDescripcion(entrada.getDesSubdelegacion());
				salida.setClave(entrada.getClaveSubdelegacion());
				
				Delegacion delegacion = delegacionUtilityLocal.persisToModel(entrada.getDicDelegacion());
				
				salida.setDelegacion(delegacion);
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SUBDELEGACION_IMSS+" | "+e.getMessage());
			}
			
			
		}
		return salida;
	}
}
