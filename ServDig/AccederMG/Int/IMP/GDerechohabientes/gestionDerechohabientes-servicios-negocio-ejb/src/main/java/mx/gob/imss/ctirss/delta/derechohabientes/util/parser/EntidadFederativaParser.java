package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;

public class EntidadFederativaParser {
	private static final Logger logger = Logger.getLogger(EntidadFederativaParser.class);

	public static DgCatEstado modelToPersist(EntidadFederativa entrada) throws DerechohabientesBusinessException{
		DgCatEstado salida=null;
		if(entrada !=null){
			try {
				salida=new DgCatEstado();
				salida.setCveEnt(entrada.getClave());
				salida.setNomEnt(entrada.getNombre());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_ENTIDAD_FEDERATIVA, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ENTIDAD_FEDERATIVA+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static EntidadFederativa persisToModel(DgCatEstado entrada) throws DerechohabientesBusinessException{
		EntidadFederativa salida=null;
		if(entrada!=null){
			try {
				salida=new EntidadFederativa();
				salida.setClave(entrada.getCveEnt());
				salida.setNombre(entrada.getNomEnt());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ENTIDAD_FEDERATIVA+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	public static List<EntidadFederativa> persistToModelList(List<DgCatEstado> entrada) throws DerechohabientesBusinessException {
		List<EntidadFederativa> salida = new ArrayList<EntidadFederativa>();
		if(entrada.size() > 0){
			try {
				for(DgCatEstado estado: entrada) {
					salida.add(persisToModel(estado));
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ENTIDAD_FEDERATIVA+" | "+e.getMessage());		
			}
		}
		
		return salida;
	}
	
}