package mx.gob.imss.ctirss.correccion.deteccion.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface DeteccionDAOLocal<T extends AbstractModel> extends DeteccionDAO<T> {

}
