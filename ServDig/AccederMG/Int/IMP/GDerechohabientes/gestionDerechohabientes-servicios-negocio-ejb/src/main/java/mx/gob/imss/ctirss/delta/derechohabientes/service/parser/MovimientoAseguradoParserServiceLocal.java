package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.MovimientoAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitMovimientoAsegurado;

@Local
public interface MovimientoAseguradoParserServiceLocal {

	DitMovimientoAsegurado modelToPersist(MovimientoAsegurado entrada) throws DerechohabientesBusinessException;
	MovimientoAsegurado persistToModel(DitMovimientoAsegurado entrada) throws DerechohabientesBusinessException;
}
