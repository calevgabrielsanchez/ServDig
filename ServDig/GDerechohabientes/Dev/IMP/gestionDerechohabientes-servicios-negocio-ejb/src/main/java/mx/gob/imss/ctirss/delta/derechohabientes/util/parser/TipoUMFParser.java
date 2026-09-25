package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoUMF;
import mx.gob.imss.ctirss.delta.persistence.DicTipoUmf;

public class TipoUMFParser {
    
	private static final Logger logger = Logger.getLogger(TipoUMFParser.class);

	public static TipoUMF persistToModel(DicTipoUmf entrada) throws DerechohabientesBusinessException {
		TipoUMF salida=null;
		if(entrada!=null){
			try {
				salida=new TipoUMF();
				salida.setIdTipoUMF(new BigInteger(String.valueOf(entrada.getCveIdTipoUmf())));
				salida.setDescripcion(entrada.getDesTipoUmf());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_UMF+" | "+e.getMessage());
			}
			
		}
		return salida;
	}

}
