package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo82;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo83;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo84;
import mx.gob.imss.ctirss.delta.persistence.DicArticulo82;
import mx.gob.imss.ctirss.delta.persistence.DicArticulo83;
import mx.gob.imss.ctirss.delta.persistence.DicArticulo84;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaDerechohabiente;

@Local
public interface ArticuloParserServiceLocal {
	public Articulo82 persisToModel(DicArticulo82 entrada) throws DerechohabientesBusinessException, Exception;
	public Articulo83 persisToModel83(DicArticulo83 entrada) throws DerechohabientesBusinessException, Exception;
	public Articulo84 persisToModel(DicArticulo84 entrada) throws DerechohabientesBusinessException, Exception;

}
