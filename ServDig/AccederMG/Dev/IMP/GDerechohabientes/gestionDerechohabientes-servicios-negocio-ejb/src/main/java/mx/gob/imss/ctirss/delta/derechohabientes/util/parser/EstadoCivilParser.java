package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil;

public class EstadoCivilParser {
	private static final Logger logger = Logger.getLogger(EstadoCivilParser.class);

	
	public static DicEstadoCivil modelToPersist(EstadoCivil entrada) throws DerechohabientesBusinessException{
		DicEstadoCivil salida=null;
		if(entrada !=null){
			try {
				salida=new DicEstadoCivil();
				salida.setCveIdEstadoCivil(entrada.getIdEstadoCivil().longValue());
				salida.setDesEstadoCivil(entrada.getDescripcion());
			} catch (Exception e) {			
				logger.error(ExceptionMessages.ERROR_PARSER_ESTADO_CIVIL, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_CIVIL+" | "+e.getMessage());
			}
		}
			
		
		return salida;	
	}
	
	public static EstadoCivil persisToModel(DicEstadoCivil entrada) throws DerechohabientesBusinessException{
		EstadoCivil salida=null;
		if(entrada!=null){
			try {
				salida=new EstadoCivil();
				//salida.setIdEstadoCivil(entrada.getCveIdEstadoCivil());
				salida.setIdEstadoCivil(new Integer(""+entrada.getCveIdEstadoCivil()));
				salida.setDescripcion(entrada.getDesEstadoCivil());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_CIVIL+" | "+e.getMessage());
			}			
		}
		return salida;
	}
	
	public static List<EstadoCivil> persistToModelList(List<DicEstadoCivil> entrada) throws DerechohabientesBusinessException {
		List<EstadoCivil> salida = null; 
				
		if(entrada.size() > 0){
			salida = new ArrayList<EstadoCivil>();
			try {
				for(DicEstadoCivil estado: entrada) {
					salida.add(persisToModel(estado));
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ESTADO_CIVIL+" | "+e.getMessage());		
			}
		}
		
		return salida;
	}
	
}