package mx.imss.ctirss.login.service.ejb.dao;

import javax.ejb.Local;

import mx.imss.ctirss.framework.base.model.AbstractModel;

@Local
public interface LoginDAOLocal<T extends AbstractModel> extends LoginDAO<T> {

}
