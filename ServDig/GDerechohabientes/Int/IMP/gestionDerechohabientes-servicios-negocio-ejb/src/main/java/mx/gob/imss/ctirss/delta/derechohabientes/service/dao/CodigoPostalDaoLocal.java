package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;

@Local
public interface CodigoPostalDaoLocal {
	public DgCodigosPostale getCodigoByAsentamiento(DgAsentamiento dgAsentamiento) throws DerechohabientesBusinessException, Exception;
}