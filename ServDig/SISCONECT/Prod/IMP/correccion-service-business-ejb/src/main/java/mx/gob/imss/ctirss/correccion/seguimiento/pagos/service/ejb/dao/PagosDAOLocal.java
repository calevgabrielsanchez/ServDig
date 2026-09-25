package mx.gob.imss.ctirss.correccion.seguimiento.pagos.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface PagosDAOLocal <T extends AbstractModel> extends PagosDAO<T>{

}
