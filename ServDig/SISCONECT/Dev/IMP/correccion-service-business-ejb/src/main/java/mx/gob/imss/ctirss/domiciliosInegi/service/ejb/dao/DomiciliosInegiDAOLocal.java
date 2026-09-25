package mx.gob.imss.ctirss.domiciliosInegi.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface DomiciliosInegiDAOLocal<T extends AbstractModel> extends DomiciliosInegiDAO<T> {

}
