package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;

@Local
public interface CabezaGrupoFamiliarDaoLocal {

	CabezaGrupoFamiliar getCabezaGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	void updateCabezaGrupoFamiliar(CabezaGrupoFamiliar cabezaGrupoFamiliar) throws DerechohabientesBusinessException, Exception;
}
