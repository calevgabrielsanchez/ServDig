package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
@Local
public interface medicoDaoLocal {

	List<MedicoFamiliar> findAllMedicos() throws DerechohabientesBusinessException, Exception;

}