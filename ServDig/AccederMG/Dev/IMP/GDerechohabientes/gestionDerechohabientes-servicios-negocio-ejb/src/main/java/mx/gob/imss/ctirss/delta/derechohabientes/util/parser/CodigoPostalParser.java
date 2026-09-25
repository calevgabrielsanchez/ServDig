package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;


public class CodigoPostalParser {
	
	private static final Logger logger = Logger.getLogger(CodigoPostalParser.class);
	
	public static CodigoPostal persistToModel(DgCodigosPostale entrada) throws DerechohabientesBusinessException {
		CodigoPostal salida=null;
		if(entrada!=null){
			try {
				salida=new CodigoPostal();
				salida.setCodigoPostal(entrada.getId().getCodigo());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CODIGO_POSTAL+" | "+e.getMessage());
			}			
		}		
		return salida;
	}


	

}
