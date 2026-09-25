package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.MovimientoAsegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;

@Local
public interface MovimientoAseguradoDaoLocal {
	
	MovimientoAsegurado getSalarioBaseUltimoMovBaja(Long idAsignacionNSS) throws DerechohabientesBusinessException, Exception;
	Double getSalarioMinimoPorDelegacion(Long idDelegacion) throws Exception;
	TipoMovtoAsegurado getTipoMovimiento(Long idTipoMovimiento) throws DerechohabientesBusinessException;

}
