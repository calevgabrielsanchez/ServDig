/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro;

import org.apache.log4j.Logger;

/**
 * @author ghdolores
 *
 */
public class RazonRegistroParser {
	private static final Logger logger = Logger.getLogger(RazonRegistroParser.class);

	public static DicRazonRegistro modelToPersist(RazonRegistro entrada) throws DerechohabientesBusinessException{
		DicRazonRegistro salida=null;
		if(entrada!=null){
			try {
				salida = new DicRazonRegistro();
				 salida.setDesRazonRegistro(entrada.getDescripcion());
				 salida.setCveRazonRegistro(entrada.getIdRazonRegistro());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_RAZON_REGISTRO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_RAZON_REGISTRO+" | "+e.getMessage());
			}
			 
		}
		
		return salida;	
	}
	
	public static RazonRegistro persisToModel(DicRazonRegistro entrada) throws DerechohabientesBusinessException{
		RazonRegistro salida=null;
		if(entrada!=null){
			try {
				salida=new RazonRegistro();
				salida.setDescripcion(entrada.getDesRazonRegistro());
				salida.setIdRazonRegistro(entrada.getCveRazonRegistro());
			
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_RAZON_REGISTRO+" | "+e.getMessage());
			}
		}	
		return salida;
	}
	
	public static List<RazonRegistro> persisToModelList(List<DicRazonRegistro> dicRazones) {
		
		List<RazonRegistro> razones = null;
		if(dicRazones != null && !dicRazones.isEmpty()) {
			razones = new ArrayList<RazonRegistro>();
			for(DicRazonRegistro dicRazon: dicRazones) {
				try {
					razones.add(persisToModel(dicRazon));
				} catch (DerechohabientesBusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			
		return razones;
	}
	
}
