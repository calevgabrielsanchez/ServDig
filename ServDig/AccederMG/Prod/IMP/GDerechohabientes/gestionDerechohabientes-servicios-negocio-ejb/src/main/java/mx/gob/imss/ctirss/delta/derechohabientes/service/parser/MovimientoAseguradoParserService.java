package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.MovtoAsegAbiertoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.asegurado.MovimientoAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitMovimientoAsegurado;

@Stateless(name = "movimientoAseguradoParserService", mappedName = "movimientoAseguradoParserService")
public class MovimientoAseguradoParserService extends AbstractServiceUtility implements MovimientoAseguradoParserServiceLocal{
	
	@EJB TipoMovimientoAseguradoParserServiceLocal tipoMovimientoAseguradoParserServiceLocal;
	
	public DitMovimientoAsegurado modelToPersist(MovimientoAsegurado entrada) throws DerechohabientesBusinessException{
		DitMovimientoAsegurado salida=null;
		if(entrada !=null){
			try {
				salida=new DitMovimientoAsegurado();
				salida.setCveIdMovimientoAsegurado(entrada.getIdMovimientoAsegurado());
				salida.setDicTipoMovtoAsegurado(tipoMovimientoAseguradoParserServiceLocal.modelToPersist(entrada.getTipoMovtoAsegurado()));
				salida.setFecMovimiento(entrada.getFechaMovimiento());
				salida.setDitMovtoAsegAbierto(MovtoAsegAbiertoParser.modelToPersist(entrada.getMovtoAsegAbierto()));
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_MOVIMIENTO_ASEGURADO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MOVIMIENTO_ASEGURADO+" | "+e.getMessage());
			}
			
		}
		return salida;	
	}
	
	public MovimientoAsegurado persistToModel(DitMovimientoAsegurado entrada) throws DerechohabientesBusinessException{
		MovimientoAsegurado salida=null;
		if(entrada !=null){
			try {
				salida=new MovimientoAsegurado();
				salida.setIdMovimientoAsegurado(entrada.getCveIdMovimientoAsegurado());
				salida.setTipoMovtoAsegurado(tipoMovimientoAseguradoParserServiceLocal.persisToModel(entrada.getDicTipoMovtoAsegurado()));
				salida.setFechaMovimiento(entrada.getFecMovimiento());
				salida.setMovtoAsegAbierto(MovtoAsegAbiertoParser.persistToModel(entrada.getDitMovtoAsegAbierto()));
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MOVIMIENTO_ASEGURADO+" | "+e.getMessage());
			}
			
		}
		return salida;	
	}
	
}