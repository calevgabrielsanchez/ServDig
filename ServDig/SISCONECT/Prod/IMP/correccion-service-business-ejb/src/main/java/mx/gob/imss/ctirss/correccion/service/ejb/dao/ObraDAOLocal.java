package mx.gob.imss.ctirss.correccion.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface ObraDAOLocal<T extends AbstractModel> extends IObraDAO<T>{

}
