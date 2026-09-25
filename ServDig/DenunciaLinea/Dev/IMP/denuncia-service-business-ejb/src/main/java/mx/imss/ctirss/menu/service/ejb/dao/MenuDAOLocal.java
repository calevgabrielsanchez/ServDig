package mx.imss.ctirss.menu.service.ejb.dao;

import javax.ejb.Local;

import mx.imss.ctirss.framework.base.model.AbstractModel;

@Local
public interface MenuDAOLocal<T extends AbstractModel> extends MenuDAO<T> {

}
