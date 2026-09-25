/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoMovtoAsegurado;

@Stateless(name = "tipoMovimientoAseguradoParserService", mappedName = "tipoMovimientoAseguradoParserService")
public class TipoMovimientoAseguradoParserService extends AbstractServiceUtility implements TipoMovimientoAseguradoParserServiceLocal{
	
	public DicTipoMovtoAsegurado modelToPersist(TipoMovtoAsegurado entrada) throws DerechohabientesBusinessException{
		DicTipoMovtoAsegurado salida=null;
		if(entrada !=null){
			try {
				salida=new DicTipoMovtoAsegurado();
				salida.setCveIdTipoMovtoAsegurado(entrada.getIdTipoMvtoAsegurado());
				salida.setDesTipoMovimientoAsegurado(entrada.getDesTipoMvtoAsegurado());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_TIPO_MOVIMIENTO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_MOVIMIENTO+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public TipoMovtoAsegurado persisToModel(DicTipoMovtoAsegurado entrada) throws DerechohabientesBusinessException{
		TipoMovtoAsegurado salida=null;
		if(entrada!=null){
			try {
				salida=new TipoMovtoAsegurado();
				salida.setIdTipoMvtoAsegurado(entrada.getCveIdTipoMovtoAsegurado());
				salida.setDesTipoMvtoAsegurado(entrada.getDesTipoMovimientoAsegurado());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_MOVIMIENTO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
}
