package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoMovtoAsegurado;

@Local
public interface TipoMovimientoAseguradoParserServiceLocal {

	DicTipoMovtoAsegurado modelToPersist(TipoMovtoAsegurado entrada) throws DerechohabientesBusinessException;
	TipoMovtoAsegurado persisToModel(DicTipoMovtoAsegurado entrada) throws DerechohabientesBusinessException;
}
