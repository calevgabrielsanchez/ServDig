package mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface SeguimientoCorreccionDAOLocal <T extends AbstractModel> extends SeguimientoCorreccionDAO<AbstractModel> {

	
}
