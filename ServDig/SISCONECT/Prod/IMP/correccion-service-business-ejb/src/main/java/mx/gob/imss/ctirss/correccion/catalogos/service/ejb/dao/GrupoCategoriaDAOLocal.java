package mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface GrupoCategoriaDAOLocal <T extends AbstractModel> extends GrupoCategoriaDAO<T>{

}
