package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import mx.gob.imss.ctirss.correccion.model.CrtProrroga;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericDAO;

public interface ProrrogaCorreccionDAO extends GenericDAO<CrtProrroga, Long> {
	
	
	public CrtProrroga getByClaveSolCorr(Integer claveSolCorr);

}
