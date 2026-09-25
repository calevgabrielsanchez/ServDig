package mx.gob.imss.ctirss.correccion.menu.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface MenuDAOLocal<T extends AbstractModel> extends MenuDAO<T> {

}
