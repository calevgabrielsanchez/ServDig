package mx.imss.ctirss.service.ejb.dao;

import javax.ejb.Local;

import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Local
public interface CatalogoDAOLocal<T extends AbstractModel> extends ICatalogoDAO<T>{

}
