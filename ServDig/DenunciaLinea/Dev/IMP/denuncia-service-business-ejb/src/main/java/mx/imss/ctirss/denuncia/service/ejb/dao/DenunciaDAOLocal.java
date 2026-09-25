package mx.imss.ctirss.denuncia.service.ejb.dao;

import javax.ejb.Local;

import mx.imss.ctirss.framework.base.model.AbstractModel;

@Local
public interface DenunciaDAOLocal<T extends AbstractModel> extends DenunciaDAO<T> {

}
