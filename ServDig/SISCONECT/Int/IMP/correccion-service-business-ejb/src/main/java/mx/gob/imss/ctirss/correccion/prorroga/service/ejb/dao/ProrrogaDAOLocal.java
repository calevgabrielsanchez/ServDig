package mx.gob.imss.ctirss.correccion.prorroga.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface ProrrogaDAOLocal <T extends AbstractModel> extends ProrrogaDAO <T>{

	
}
