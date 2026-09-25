package mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface RegularizacionDAOLocal<T extends AbstractModel> extends RegularizacionDAO<T> {

}
