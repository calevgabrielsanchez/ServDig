package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsignacionNSSParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EntidadFederativaParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EstadoCivilParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.SexoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo82;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo83;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo84;
import mx.gob.imss.ctirss.delta.persistence.DicArticulo82;
import mx.gob.imss.ctirss.delta.persistence.DicArticulo83;
import mx.gob.imss.ctirss.delta.persistence.DicArticulo84;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaDerechohabiente;

@Stateless(name = "articuloParserService", mappedName = "articuloParserService")
public class ArticuloParserService extends AbstractServiceUtility implements ArticuloParserServiceLocal{


	@Override
	public Articulo82 persisToModel(DicArticulo82 entrada) throws Exception {
		Articulo82 salida=null;
		
		if(entrada != null) {
			try {
				
				salida=new Articulo82();
				salida.setCveIdArticulo82(entrada.getCveIdArticulo82());
				salida.setDesArticulo(entrada.getDesArticulo());
				salida.setFecRegistroActualizado(entrada.getFecRegistroActualizado());
				salida.setFecRegistroAlta(entrada.getFecRegistroAlta());
				salida.setFecRegistroBaja(entrada.getFecRegistroBaja());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DERECHOHABIENTE+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	@Override
	public Articulo83 persisToModel83(DicArticulo83 entrada) throws Exception {
		Articulo83 salida=null;
		
		if(entrada != null) {
			try {
				
				salida=new Articulo83();
				salida.setCveIdArticulo83(entrada.getCveIdArticulo83());
				salida.setDesArticulo(entrada.getDesArticulo());
				salida.setTiempoEspera(entrada.getTiempoEspera());
				salida.setFecRegistroActualizado(entrada.getFecRegistroActualizado());
				salida.setFecRegistroAlta(entrada.getFecRegistroAlta());
				salida.setFecRegistroBaja(entrada.getFecRegistroBaja());
				
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DERECHOHABIENTE+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	@Override
	public Articulo84 persisToModel(DicArticulo84 entrada) throws Exception {
		Articulo84 salida=null;
		
		if(entrada != null) {
			try {
				
				salida=new Articulo84();
				salida.setCveIdArticulo84(entrada.getCveIdArticulo84());
				salida.setDesArticulo(entrada.getDesArticulo());
				salida.setFecRegistroActualizado(entrada.getFecRegistroActualizado());
				salida.setFecRegistroAlta(entrada.getFecRegistroAlta());
				salida.setFecRegistroBaja(entrada.getFecRegistroBaja());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DERECHOHABIENTE+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}

}
