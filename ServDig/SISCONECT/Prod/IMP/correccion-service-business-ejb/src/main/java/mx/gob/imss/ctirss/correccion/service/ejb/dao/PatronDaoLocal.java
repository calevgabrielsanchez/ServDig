package mx.gob.imss.ctirss.correccion.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * 
 * @author Jorge Castorena
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 12/10/2011
 */

@Local
public interface PatronDaoLocal<T extends AbstractModel> extends IPatronDAO<T>{


}
