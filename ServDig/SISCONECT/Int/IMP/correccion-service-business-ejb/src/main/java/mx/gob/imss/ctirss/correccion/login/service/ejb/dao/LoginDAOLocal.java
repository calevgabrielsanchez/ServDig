package mx.gob.imss.ctirss.correccion.login.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface LoginDAOLocal<T extends AbstractModel> extends LoginDAO<T> {

}
