package mx.gob.imss.ctirss.domiciliosInegi.service.ejb.dao;

import javax.ejb.Local;

import mx.imss.ctirss.framework.base.model.AbstractModel;

@Local
public interface DomiciliosInegiDAOLocal<T extends AbstractModel> extends DomiciliosInegiDAO<T> {

}
