package mx.gob.imss.ctirss.delta.service.ejb.dao;

import javax.ejb.Local;
 
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Local
public interface CatalogoDAOLocal<T extends AbstractModel> extends ICatalogoDAO<T>{

}
