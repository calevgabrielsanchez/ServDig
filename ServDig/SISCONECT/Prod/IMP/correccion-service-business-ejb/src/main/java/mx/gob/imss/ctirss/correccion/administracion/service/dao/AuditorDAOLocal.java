package mx.gob.imss.ctirss.correccion.administracion.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface AuditorDAOLocal <T extends AbstractModel> extends AuditorDAO<T>{

}
